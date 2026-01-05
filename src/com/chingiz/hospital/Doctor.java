package com.chingiz.hospital;

public class Doctor extends Person { // Наследование (5 баллов)
    private String specialization;

    public Doctor(String name, int age, String specialization) {
        super(name, age); // Использование super() (5 баллов)
        this.specialization = specialization;
    }

    @Override // Переопределение (5 баллов)
    public void performDuty() {
        System.out.println("Doctor " + name + " (Specialization: " + specialization + ") is treating patients.");
    }

    // Специфический метод для демонстрации casting
    public void writePrescription() {
        System.out.println("Doctor " + name + " is writing a prescription.");
    }
}