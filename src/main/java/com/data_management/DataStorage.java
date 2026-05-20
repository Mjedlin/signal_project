package com.data_management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the storage and retrieval of patient data within the system.
 * <p>
 * This class serves as the central repository for all patient records, 
 * providing methods to add new data, retrieve specific records, and manage 
 * patient instances globally.
 */
public class DataStorage {
    private Map<Integer, Patient> patientMap;
    private static DataStorage instance;

    private DataStorage() {
        this.patientMap = new HashMap<>();
    }

    public static synchronized DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    public void addPatientData(int patientId, double measurementValue, String recordType, long timestamp) {
        Patient patient = patientMap.get(patientId);
        if (patient == null) {
            patient = new Patient(patientId);
            patientMap.put(patientId, patient);
        }
        patient.addRecord(measurementValue, recordType, timestamp);
    }

    public List<PatientRecord> getRecords(int patientId, long startTime, long endTime) {
        Patient patient = patientMap.get(patientId);
        if (patient != null) {
            return patient.getRecords(startTime, endTime);
        }
        return new ArrayList<>(); // Zwraca pustą listę, jeśli pacjent nie istnieje
    }

    public List<Patient> getAllPatients() {
        return new ArrayList<>(patientMap.values());
    }
}