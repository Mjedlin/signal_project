package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

/*
 * Reads real-time health data from a WebSocket server and stores it in DataStorage.
 */
public class WebSocketDataReader implements DataReader {
    private String serverUri;
    private WebSocketClient client;

    public WebSocketDataReader(String serverUri) {
        this.serverUri = serverUri;
    }

    @Override
    public void readData(DataStorage dataStorage) {
        try {
            URI uri = new URI(serverUri);
            client = new WebSocketClient(uri) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    System.out.println("Connected to WebSocket server");
                }

                @Override
                public void onMessage(String message) {
                    try {
                        String[] parts = message.split(",");
                        if (parts.length == 4) {
                            int patientId = Integer.parseInt(parts[0].split(":")[1].trim());
                            long timestamp = Long.parseLong(parts[1].split(":")[1].trim());
                            String label = parts[2].split(":")[1].trim();
                            double data = Double.parseDouble(parts[3].split(":")[1].trim());

                            dataStorage.addPatientData(patientId, data, label, timestamp);
                        }
                    } catch (Exception e) {
                        System.err.println("Data corruption detected, skipping message: " + message);
                    }
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("Disconnected from server: " + reason);
                }

                @Override
                public void onError(Exception ex) {
                    System.err.println("WebSocket error occurred: " + ex.getMessage());
                }
            };
            client.connect();
        } catch (URISyntaxException e) {
            System.err.println("Invalid WebSocket URI: " + e.getMessage());
        }
    }
    
    public void disconnect() {
        if (client != null) {
            client.close();
        }
    }
}