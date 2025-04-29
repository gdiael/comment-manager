package imd.ufrn;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GwProcessor {

    private static List<Map<String, String>> services = new LinkedList<>();

    public GwProcessor() {
    }

    public static String processUDP(String msg, GwClient client) {
        Map<String, String> dict = messageToMap(msg);
        String mode = dict.get("mode");
        if (mode.equals("heartbeat")) {
            processHeartBeat(dict, client);
        }
        return "status|200";
    }

    public static String processTCP(String msg, GwClient client) {
        // System.out.println("todo: tokenizer TCP | msg: " + msg);
        return processUDP(msg, client);
    }

    public static String processHTTP(String msg, GwClient client) {
        // System.out.println("todo: tokenizer HTTP | msg: " + msg);
        return processUDP(msg, client);
    }

    private static void processHeartBeat(Map<String, String> dict, GwClient client) {
        boolean isNewService = false;
        String serviceMode = "follower";
        if(services.size() == 0) {
            serviceMode = "leader";
            isNewService = true;
        } else {
            isNewService = true;
            for (Map<String,String> serv : services) {
                if(serv.get("port").equals(dict.get("port"))) {
                    isNewService = false;
                    break;
                }
            }
        }
        if (isNewService) {
            dict.put("servicemode", serviceMode);
            services.add(dict);
            String sendMsg = String.format("status|200;mode|newservice;servicemode|%s;", serviceMode);
            client.sendMsg(sendMsg, dict.get("host"), Integer.parseInt(dict.get("port")));
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
