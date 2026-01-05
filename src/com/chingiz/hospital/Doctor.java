package com.chingiz.hospital;

public class Doctor extends Person {
    private String specialization;

    public Doctor(String name, int age, String specialization) {
        super(name, age);
        this.specialization = specialization;
    }

    @Override // Переопределение 1
    public void performDuty() {
        System.out.println("Doctor " + name + " is treating patients in " + specialization);
    }

    @Override // Переопределение 2
    public String toString() {
        return "Doctor: " + name + " | Spec: " + specialization + " | Age: " + age;
    }

    public void writePrescription() {
        System.out.println("Doctor " + name + " is writing a prescription.");
    }
}