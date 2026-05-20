package com.data_management;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single patient within the data management system.
 * <p>
 * This class stores the unique identifier of the patient and maintains a 
 * history of their recorded health data.
 */
public class Patient {
    private int patientId;
    private List<PatientRecord> patientRecords;

    /**
     * Constructs a new Patient instance with the specified ID.
     *
     * @param patientId the unique integer identifier assigned to this patient
     */
    public Patient(int patientId) {
        this.patientId = patientId;
        this.patientRecords = new ArrayList<>();
    }

    /**
     * Adds a new record to this patient's list of medical records.
     * The record is created with the specified measurement value, record type, and
     * timestamp.
     *
     * @param measurementValue the measurement value to store in the record
     * @param recordType       the type of record, e.g., "HeartRate", "BloodPressure"
     * @param timestamp        the time at which the measurement was taken, in milliseconds since UNIX epoch
     */
    public void addRecord(double measurementValue, String recordType, long timestamp) {
        PatientRecord record = new PatientRecord(this.patientId, measurementValue, recordType, timestamp);
        this.patientRecords.add(record);
    }

    /**
     * Retrieves the health data records for the patient within a specified time range.
     *
     * @param startTime the start of the time range (inclusive), in milliseconds since the Unix epoch
     * @param endTime   the end of the time range (inclusive), in milliseconds since the Unix epoch
     * @return a list of {@code PatientRecord} objects falling within the given time range
     */
    public List<PatientRecord> getRecords(long startTime, long endTime) {
        List<PatientRecord> filteredRecords = new ArrayList<>();
        for (PatientRecord record : patientRecords) {
            if (record.getTimestamp() >= startTime && record.getTimestamp() <= endTime) {
                filteredRecords.add(record);
            }
        }
        return filteredRecords;
    }
}