package imd.ufrn;

import java.util.HashMap;
import java.util.Map;

public class SvProcessor {
    
    public SvProcessor() {

    }

    public static String processUDP(String msg, SvClient client, SvProps props) {
        Map<String, String> dict = messageToMap(msg);
        String mode = dict.get("mode");
        if(mode.equals("newservice")) {
            // serviço foi cadastrado no gateway
            System.out.println("Serviço cadastrado como: " + dict.get("servicemode"));
            props.isLeader = true;
        }
        return "status|200";
    }

    public static String processTCP(String msg, SvClient client, SvProps props) {
        // System.out.println("todo: tokenizer TCP | msg: " + msg);
        return processUDP(msg, client, props);
    }

    public static String processHTTP(String msg, SvClient client, SvProps props) {
        // System.out.println("todo: tokenizer HTTP | msg: " + msg);
        return processUDP(msg, client, props);
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
