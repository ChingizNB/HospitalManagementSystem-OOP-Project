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

    // ДОБАВЬТЕ ЭТОТ МЕТОД:
    public String getSpecialization() {
        return specialization;
    }

    // И остальные геттеры, если их нет:
    public String getName() { return name; }

    // toString метод...
    @Override
    public String toString() {
        return "Doctor{Name='" + name + "', Specialization='" + specialization + "'}";
    }
}