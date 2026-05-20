package com.cardio_generator.outputs;

/**
 * Implements the {@link OutputStrategy} interface to print patient health data to the console.
 * <p>
 * This strategy is primarily used for debugging or direct observation of the 
 * simulated data stream in real-time within the terminal.
 */
public class ConsoleOutputStrategy implements OutputStrategy {
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        System.out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
    }
}
