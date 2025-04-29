package imd.ufrn;

import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

public class GwClient {
    
    GatewayCfg cfg;

    public GwClient(GatewayCfg config) {
        cfg = config;
    }

    public void sendMsg(String msg, String host, int port) {
        String webMode = cfg.getWebMode();
        switch (webMode) {
            case "UDP":
                sendMsgUDP(msg, host, port);
                break;
            case "TCP":
                sendMsgTCP(msg, host, port);
                break;
            case "HTTP":
                sendMsgHTTP(msg, host, port);
                break;
            default:
                throw new IllegalArgumentException("unknown mode: " + webMode);
        }
    }

    private void sendMsgUDP(String msg, String host, int port) {

        try(DatagramSocket socket = new DatagramSocket()) {

            InetAddress gwAddress = InetAddress.getByName(host);
            byte[] sendData = msg.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, gwAddress, port);
            socket.send(sendPacket);

        } catch (Exception e) {
            System.out.println("erro ao enviar msg UDP");
            e.printStackTrace();
        }
    }

    private void sendMsgTCP(String msg, String host, int port) {
        try (Socket socket = new Socket(host, port)) {
            OutputStream output = socket.getOutputStream();
            output.write(msg.getBytes());
            output.flush();
        } catch (Exception e) {
            System.out.println("erro ao enviar msg TCP");
            e.printStackTrace();
        }
    }

    private void sendMsgHTTP(String msg, String host, int port) {
        try {
            URL url = new URL("http://" + host + ":" + String.valueOf(port) + "/");

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "text/plain; charset=UTF-8");

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = msg.getBytes("UTF-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                System.out.println("erro HTTP, código: " + responseCode);
            }

        } catch (Exception e) {
            System.out.println("erro ao enviar msg HTTP");
            e.printStackTrace();
        }
    }

}
