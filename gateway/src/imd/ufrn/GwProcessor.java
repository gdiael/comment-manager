package imd.ufrn;

import java.util.HashMap;
import java.util.Map;

public class GwProcessor {

    public GwProcessor() {
    }

    public static String processUDP(String msg, GwClient client, GwProps props) {
        Map<String, String> dict = messageToMap(msg);
        String mode = dict.get("mode");
        if (mode.equals("heartbeat")) {
            processHeartBeat(dict, client, props);
            return "status|200";
        }
        if (mode.equals("addcomment")) {
            processAddComment(dict, client, props);
            return "status|200";
        }
        return "status|200";
    }

    public static String processTCP(String msg, GwClient client, GwProps props) {
        // System.out.println("todo: tokenizer TCP | msg: " + msg);
        return processUDP(msg, client, props);
    }

    public static String processHTTP(String msg, GwClient client, GwProps props) {
        // System.out.println("todo: tokenizer HTTP | msg: " + msg);
        return processUDP(msg, client, props);
    }

    private static void processHeartBeat(Map<String, String> dict, GwClient client, GwProps props) {
        boolean isNewService = false;
        String serviceMode = "follower";
        if(props.services.size() == 0) {
            serviceMode = "leader";
            isNewService = true;
        } else {
            isNewService = true;
            for (Map<String,String> serv : props.services) {
                if(serv.get("port").equals(dict.get("port"))) {
                    isNewService = false;
                    break;
                }
            }
        }
        if (isNewService) {
            dict.put("servicemode", serviceMode);
            props.services.add(dict);
            String sendMsg = String.format("status|200;mode|newservice;servicemode|%s;", serviceMode);
            client.sendMsg(sendMsg, dict.get("host"), Integer.parseInt(dict.get("port")));
        }
    }

    private static void processAddComment(Map<String,String> dict, GwClient client, GwProps props) {
        for (Map<String, String> serv : props.services) {
            if(serv.get("servicemode").equals("leader")) {
                client.sendMsg(mapToMessage(dict), serv.get("host"), Integer.parseInt(serv.get("port")));
            }
        }
    }

    // Parsers

    // Converte uma String "chave|valor;chave|valor" para Map<String, String>
    public static Map<String, String> messageToMap(String input) {
        Map<String, String> result = new HashMap<>();

        if (input == null || input.trim().isEmpty()) {
            return result; // entrada nula ou vazia
        }

        String[] pairs = input.split(";"); // separa os pares

        for (String pair : pairs) {
            int separatorIndex = pair.indexOf('|');

            if (separatorIndex > 0 && separatorIndex < pair.length() - 1 && pair.indexOf('|', separatorIndex + 1) == -1) {
                // Tem exatamente um separador '|', e não no começo nem no final

                String key = pair.substring(0, separatorIndex).trim();
                String value = pair.substring(separatorIndex + 1).trim();

                result.put(key, value);
            }
            // Se não, ignora o par inválido
        }

        return result;
    }

    // Converte um Map<String, String> para uma String "chave|valor;chave|valor"
    public static String mapToMessage(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append(';');
            }
            sb.append(entry.getKey())
              .append('|')
              .append(entry.getValue());
        }

        return sb.toString();
    }
}
