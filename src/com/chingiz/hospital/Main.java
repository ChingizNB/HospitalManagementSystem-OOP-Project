package com.chingiz.hospital;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== HOSPITAL MANAGEMENT SYSTEM ===");

        Patient p1 = new Patient(101, "Ivan Ivanov", 25, "A+");
        Patient p2 = new Patient(102, "Alisa Selezneva", 12, "B-");
        Doctor d1 = new Doctor(1, "Dr. House", "Diagnostics", 15);
        Appointment app1 = new Appointment(501, "Ivan Ivanov", "Dr. House", "2025-12-25");

        System.out.println("\n--- INITIAL STATE ---");
        System.out.println(p1);
        System.out.println(d1);
        System.out.println(app1);

        System.out.println("\n--- TESTING GETTERS ---");
        System.out.println("Patient name: " + p1.getFullName());
        System.out.println("Doctor specialization: " + d1.getSpecialization());

        System.out.println("\n--- TESTING SETTERS ---");
        p1.setAge(26);
        System.out.println("Updated Patient 1: " + p1);

        System.out.println("\n--- TESTING LOGIC METHODS ---");
        System.out.println(p2.getFullName() + " is minor: " + p2.isMinor());
        app1.reschedule("2026-01-01");

        System.out.println("\n=== Program Complete ===");
    }
}