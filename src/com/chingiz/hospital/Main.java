package com.chingiz.hospital;

public class Main {
    public static void main(String[] args) {
        // 1. Приветствие [cite: 177]
        System.out.println("=== HOSPITAL MANAGEMENT SYSTEM ===");

        // 2. Создание объектов (минимум 5) [cite: 177]
        Patient p1 = new Patient(101, "Ivan Ivanov", 25, "A+");
        Patient p2 = new Patient(102, "Alisa Selezneva", 12, "B-");
        Doctor d1 = new Doctor(1, "Dr. House", "Diagnostics", 15);
        Appointment app1 = new Appointment(501, "Ivan Ivanov", "Dr. House", "2025-12-25");

        // 3. Вывод начального состояния [cite: 187]
        System.out.println("\n--- INITIAL STATE ---");
        System.out.println(p1);
        System.out.println(d1);
        System.out.println(app1);

        // 4. Тестирование Геттеров [cite: 189]
        System.out.println("\n--- TESTING GETTERS ---");
        System.out.println("Patient name: " + p1.getFullName());
        System.out.println("Doctor specialization: " + d1.getSpecialization());

        // 5. Тестирование Сеттеров [cite: 192]
        System.out.println("\n--- TESTING SETTERS ---");
        p1.setAge(26);
        System.out.println("Updated Patient 1: " + p1);

        // 6. Тестирование логики [cite: 194]
        System.out.println("\n--- TESTING LOGIC METHODS ---");
        System.out.println(p2.getFullName() + " is minor: " + p2.isMinor());
        app1.reschedule("2026-01-01");

        // 7. Завершение [cite: 178, 205]
        System.out.println("\n=== Program Complete ===");
    }
}