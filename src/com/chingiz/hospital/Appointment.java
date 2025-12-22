package com.chingiz.hospital;

public class Appointment {
    private int appointmentId;
    private String patientName;
    private String doctorName;
    private String date;

    public Appointment(int appointmentId, String patientName, String doctorName, String date) {
        this.appointmentId = appointmentId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.date = date;
    }

    public int getAppointmentId() { return appointmentId; }
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public void reschedule(String newDate) {
        this.date = newDate;
    }

    public void cancel() {
        this.date = "Cancelled";
    }

    @Override
    public String toString() {
        return "Appointment{ID=" + appointmentId + ", Patient='" + patientName + "', Date='" + date + "'}";
    }
}