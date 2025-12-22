package com.chingiz.hospital;

public class Doctor {
    private int doctorId;
    private String name;
    private String specialization; // Это поле должно быть здесь
    private int experienceYears;

    public Doctor(int doctorId, String name, String specialization, int experienceYears) {
        this.doctorId = doctorId;
        this.name = name;
        this.specialization = specialization;
        this.experienceYears = experienceYears;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getName() { return name; }

    @Override
    public String toString() {
        return "Doctor{Name='" + name + "', Specialization='" + specialization + "'}";
    }
}