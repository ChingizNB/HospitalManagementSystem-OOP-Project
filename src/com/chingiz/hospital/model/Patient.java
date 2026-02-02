package com.chingiz.hospital.model; // должно быть так

import java.time.LocalDate;

public class Patient {
    private int patientId;
    private String name;
    private int age;
    private String diagnosis;
    private LocalDate admissionDate;

    // Конструктор
    public Patient(int patientId, String name, int age, String diagnosis, LocalDate admissionDate) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.diagnosis = diagnosis;
        this.admissionDate = admissionDate;
    }

    // Геттеры и сеттеры
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public LocalDate getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(LocalDate admissionDate) { this.admissionDate = admissionDate; }

    @Override
    public String toString() {
        return "ID: " + patientId + ", Name: " + name + ", Age: " + age +
                ", Diagnosis: " + diagnosis + ", Admission Date: " + admissionDate;
    }
}