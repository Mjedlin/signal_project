package data_management;

import com.data_management.DataStorage;
import com.data_management.WebSocketDataReader;
import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WebSocketDataReaderTest {
    private TestServer server;
    private DataStorage storage;
    private WebSocketDataReader reader;

    private class TestServer extends WebSocketServer {
        public TestServer(InetSocketAddress address) {
            super(address);
        }
        @Override public void onOpen(WebSocket conn, ClientHandshake handshake) {}
        @Override public void onClose(WebSocket conn, int code, String reason, boolean remote) {}
        @Override public void onMessage(WebSocket conn, String message) {}
        @Override public void onError(WebSocket conn, Exception ex) {}
        @Override public void onStart() {}
    }

    @BeforeEach
    void setUp() throws Exception {
        server = new TestServer(new InetSocketAddress("localhost", 8080));
        server.start();
        storage = DataStorage.getInstance();
        reader = new WebSocketDataReader("ws://localhost:8080");
        reader.readData(storage);
        Thread.sleep(1000); 
    }

    @AfterEach
    void tearDown() throws Exception {
        reader.disconnect();
        server.stop();
    }

    @Test
    void testRealTimeDataIntegration() throws Exception {
        server.broadcast("Patient ID: 1, Timestamp: 1716242000000, Label: ECG, Data: 1.5");
        server.broadcast("Patient ID: corrupted, Data: error");
        
        Thread.sleep(1000); 

        var records = storage.getRecords(1, 1716242000000L, 1716242000000L);
        assertTrue(records.size() > 0, "Powinno zapisac jeden rekord");
        assertEquals(1.5, records.get(0).getMeasurementValue());
    }
}