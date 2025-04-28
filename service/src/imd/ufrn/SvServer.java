package imd.ufrn;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class SvServer {
    
    private boolean running = false;

    public SvServer(ServiceCfg cfg) {
        String webMode = cfg.getWebMode();
        switch (webMode) {
            case "UDP":
                StartUDP(cfg);
                break;
            case "TCP":
                StartTCP(cfg);
                break;
            case "HTTP":
                StartHTTP(cfg);
                break;
            default:
                throw new IllegalArgumentException("unknown mode: " + webMode);
        }
    }

    private void StartUDP(ServiceCfg cfg) {
        System.out.println(String.format("UDP Gateway Started at port: %d",cfg.getPort()));
        running = true;
		try (DatagramSocket serverSocket = new DatagramSocket(cfg.getPort())) {
			while (running) {
				byte[] receiveMessage = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveMessage, receiveMessage.length);
				serverSocket.receive(receivePacket);

				String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                
				SvProcessor.processUDP(message);
			}
		} catch (IOException e) {
            e.printStackTrace();
            System.out.println("UDP Server Terminating");		
		} finally {
            System.out.println("UDP Server Terminating");
		}
        running = false;
    }

    private void StartHTTP(ServiceCfg cfg) {
        System.out.println("to-do HTTP");
    }

    private void StartTCP(ServiceCfg cfg) {
        System.out.println("to-do TCP");
    }

    public void Stop() {
        running = false;
    }

}
