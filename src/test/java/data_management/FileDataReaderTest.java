package data_management;

import com.data_management.DataStorage;
import com.data_management.FileDataReader;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileDataReaderTest {

    @Test
    void testReadDataFromFile() throws IOException {
        
        File tempDir = new File("test_mock_data");
        if (!tempDir.exists()) {
            tempDir.mkdir();
        }
        File tempFile = new File(tempDir, "test_output.txt");
        FileWriter writer = new FileWriter(tempFile);
        
        writer.write("Patient ID: 999, Timestamp: 1716242000000, Label: ECG, Data: 1.5\n");
        writer.close();

        
        DataStorage storage = DataStorage.getInstance();
        FileDataReader reader = new FileDataReader("test_mock_data");
        reader.readData(storage);

    
        var records = storage.getRecords(999, 1716242000000L, 1716242000000L);
        assertTrue(records.size() > 0, "Powinien zostać wczytany przynajmniej jeden rekord");
        assertEquals(1.5, records.get(0).getMeasurementValue(), "Wartość pomiaru powinna wynosić 1.5");
        assertEquals("ECG", records.get(0).getRecordType(), "Typ pomiaru powinien to być ECG");

        tempFile.delete();
        tempDir.delete();
    }
}