package imd.ufrn;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
    
    private boolean running = false;

    public Server(AppConfig cfg) {
        String webMode = cfg.getWebMode();
        switch (webMode) {
            case "UDP":
                StartUDP(cfg);
                break;
            case "TCP":
                StartTCP(cfg);
                break;
            case "HTML":
                StartHTML(cfg);
                break;
            default:
                throw new IllegalArgumentException("unknown mode: " + webMode);
        }
    }

    private void StartUDP(AppConfig cfg) {
        System.out.println(String.format("UDP Gateway Started at port: %d",cfg.getPort()));
        running = true;
		try (DatagramSocket serverSocket = new DatagramSocket(cfg.getPort())) {
			while (running) {
				byte[] receiveMessage = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveMessage, receiveMessage.length);
				serverSocket.receive(receivePacket);

				String message = new String(receivePacket.getData(), 0, receivePacket.getLength());

				Tokenizer.tokenizeUDP(message);
			}
		} catch (IOException e) {
            e.printStackTrace();
            System.out.println("UDP Server Terminating");		
		} finally {
            System.out.println("UDP Server Terminating");
		}
        running = false;
    }

    private void StartHTML(AppConfig cfg) {
        System.out.println("to-do HTML");
    }

    private void StartTCP(AppConfig cfg) {
        System.out.println("to-do TCP");
    }

    public void Stop() {
        running = false;
    }

}
