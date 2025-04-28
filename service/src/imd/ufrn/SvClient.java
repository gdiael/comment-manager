package imd.ufrn;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SvClient {
    
    private static final int GW_PORT = 9095;
    private static final String GW_HOST = "gateway"; 

    private boolean sendingHeartBeat = false;
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
            case "HTML":
                sendMsgHTML(msg);
                break;
            default:
                throw new IllegalArgumentException("unknown mode: " + webMode);
        }
    }

    private void sendMsgUDP(String msg) {

        try(DatagramSocket socket = new DatagramSocket(cfg.getPort())) {

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
        System.out.println("to-do HTML");
    }

    private void sendMsgHTML(String msg) {
        System.out.println("to-do HTML");
    }

    private void startHeartBeat() {
        sendingHeartBeat = true;
        while(sendingHeartBeat) {
            try {
                sendMsg("teste heartbeat [" + cfg.getPort() + "]");
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
