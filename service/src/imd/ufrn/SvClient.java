package imd.ufrn;

import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

public class SvClient {
    
    private static final int GW_PORT = 9095;
    private static final String GW_HOST = "gateway"; 

    private volatile boolean sendingHeartBeat = false;
    ServiceCfg cfg;

    public SvClient(ServiceCfg config) {
        cfg = config;
        startHeartBeat();
    }

    public void sendMsg(String msg) {
        String webMode = cfg.getWebMode();
        switch (webMode) {
            case "UDP":
                sendMsgUDP(msg);
                break;
            case "TCP":
                sendMsgTCP(msg);
                break;
            case "HTTP":
                sendMsgHTTP(msg);
                break;
            default:
                throw new IllegalArgumentException("unknown mode: " + webMode);
        }
    }

    private void sendMsgUDP(String msg) {

        try(DatagramSocket socket = new DatagramSocket()) {

            InetAddress gwAddress = InetAddress.getByName(GW_HOST);
            byte[] sendData = msg.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, gwAddress, GW_PORT);
            socket.send(sendPacket);

        } catch (Exception e) {
            System.out.println("erro ao enviar msg UDP");
            e.printStackTrace();
        }
    }

    private void sendMsgTCP(String msg) {
        try (Socket socket = new Socket(GW_HOST, GW_PORT)) {
            OutputStream output = socket.getOutputStream();
            output.write(msg.getBytes());
            output.flush();
        } catch (Exception e) {
            System.out.println("erro ao enviar msg TCP");
            e.printStackTrace();
        }
    }

    private void sendMsgHTTP(String msg) {
        try {
            URL url = new URL("http://" + GW_HOST + ":" + GW_PORT + "/");

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

    private void startHeartBeat() {
        sendingHeartBeat = true;
        System.out.println("Enviando o heartbeat!");
        new Thread(() -> {
            while(sendingHeartBeat) {
                try {
                    sendMsg(String.format("status|200;mode|heartbeat;role|service;host|%s;port|%d;webmode|%s", cfg.getHost(), cfg.getPort(), cfg.getWebMode()));
                    Thread.sleep(3000);
                } catch (Exception e) {
                    sendingHeartBeat = false;
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
