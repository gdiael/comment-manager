package imd.ufrn;

import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import com.sun.net.httpserver.HttpServer;

public class SvServer {
    
    private boolean running = false;

    public SvServer(ServiceCfg cfg, SvClient client, SvProps props) {
        String webMode = cfg.getWebMode();
        switch (webMode) {
            case "UDP":
                StartUDP(cfg, client, props);
                break;
            case "TCP":
                StartTCP(cfg, client, props);
                break;
            case "HTTP":
                StartHTTP(cfg, client, props);
                break;
            default:
                throw new IllegalArgumentException("unknown mode: " + webMode);
        }
    }

    private void StartUDP(ServiceCfg cfg, SvClient client, SvProps props) {
        System.out.println(String.format("UDP Service Started at port: %d",cfg.getPort()));
        running = true;
		try (DatagramSocket serverSocket = new DatagramSocket(cfg.getPort())) {
			while (running) {
				byte[] receiveMessage = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveMessage, receiveMessage.length);
				serverSocket.receive(receivePacket);

				String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                
				SvProcessor.processUDP(message, client, props);
			}
		} catch (IOException e) {
            e.printStackTrace();	
		} finally {
            System.out.println("UDP Server Terminating");
		}
        running = false;
    }

    private void StartTCP(ServiceCfg cfg, SvClient client, SvProps props) {
        System.out.println(String.format("TCP Service Started at port: %d", cfg.getPort()));
        running = true;
        try (ServerSocket serverSocket = new ServerSocket(cfg.getPort())) {
            while (running) {
                Socket clientSocket = serverSocket.accept();

                try (InputStream input = clientSocket.getInputStream()) {
                    byte[] buffer = new byte[1024];
                    int bytesRead = input.read(buffer);
                    if (bytesRead != -1) {
                        String message = new String(buffer, 0, bytesRead);
                        SvProcessor.processTCP(message, client, props);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Erro durante comunicação TCP");
                } finally {
                    clientSocket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("TCP Server Terminating");
        } finally {
            System.out.println("TCP Server Terminating");
        }
        running = false;
    }

    private void StartHTTP(ServiceCfg cfg, SvClient client, SvProps props) {
        System.out.println(String.format("HTTP Service Started at port: %d", cfg.getPort()));
        running = true;
        
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(cfg.getPort()), 0);
            server.createContext("/", exchange -> {
                if ("POST".equals(exchange.getRequestMethod())) {
                    byte[] requestBody = exchange.getRequestBody().readAllBytes();
                    String message = new String(requestBody, "UTF-8");
    
                    SvProcessor.processHTTP(message, client, props);
    
                    String response = "Message received";
                    exchange.sendResponseHeaders(200, response.length());
                    exchange.getResponseBody().write(response.getBytes());
                    exchange.close();
                } else {
                    exchange.sendResponseHeaders(405, -1); // Method Not Allowed
                    exchange.close();
                }
            });
    
            server.start();
            while (running) {
                Thread.sleep(1000); // mantém a thread principal viva enquanto o servidor HTTP roda
            }
            server.stop(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            System.out.println("HTTP Server Terminating");
        }
        running = false;
    }

    public void Stop() {
        running = false;
    }

}
