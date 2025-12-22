package com.chingiz.hospital;

public class Patient {
    // 1. Приватные поля (минимум 4) [cite: 12, 57]
    private int patientId;
    private String fullName;
    private int age;
    private String bloodType;

    // 2. Конструктор со всеми параметрами [cite: 13, 67]
    public Patient(int patientId, String fullName, int age, String bloodType) {
        this.patientId = patientId;
        this.fullName = fullName;
        this.age = age;
        this.bloodType = bloodType;
    }

    // 3. Пустой конструктор (по желанию) [cite: 70]
    public Patient() {
        this.fullName = "Unknown";
    }

    // 4. Геттеры и Сеттеры для всех полей [cite: 13, 74, 79]
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getBloodType() { return bloodType; }
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }

    // 5. Дополнительные методы с логикой (минимум 2) [cite: 14, 206]
    public boolean isMinor() {
        return age < 18; // Возвращает true, если пациент несовершеннолетний
    }

    public String getAgeCategory() {
        if (age < 12) return "Child";
        if (age < 60) return "Adult";
        return "Senior";
    }

    // 6. Метод toString() [cite: 57, 91]
    @Override
    public String toString() {
        return "Patient{ID=" + patientId + ", Name='" + fullName + "', Age=" + age + ", Blood='" + bloodType + "'}";
    }
}