package com.cardio_generator.outputs;

/**
 * Defines the contract for outputting generated patient health data.
 * <p>
 * Classes implementing this interface define specific destinations and 
 * formats for the simulated data, such as writing to a console, 
 * saving to a file, or transmitting over a network.
 */
public interface OutputStrategy {
    /**
     * Outputs the patient's simulated health data to the target destination.
     *
     * @param patientId the unique identifier of the patient
     * @param timestamp the time the data was recorded, in milliseconds since the Unix epoch
     * @param label     the category of the health data (e.g., "HeartRate", "BloodSaturation")
     * @param data      the numerical or formatted value of the health data
     */
    void output(int patientId, long timestamp, String label, String data);
}
