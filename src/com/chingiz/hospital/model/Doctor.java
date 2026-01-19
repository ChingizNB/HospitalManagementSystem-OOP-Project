package com.chingiz.hospital.model;

public class Doctor extends Person implements MedicalProfessional {
    private String specialization;

    public Doctor(String name, int age, String specialization) {
        super(name, age);
        setSpecialization(specialization);
    }

    @Override
    public void performDuty() {
        System.out.println("Доктор " + getName() + " проводит обход пациентов по направлению: " + specialization);
    }

    @Override
    public void provideCare() {
        System.out.println("Доктор назначает лечение и выписывает рецепты.");
    }

    public void setSpecialization(String specialization) {
        if (specialization == null || specialization.trim().isEmpty()) {
            throw new IllegalArgumentException("Специализация не может быть пустой");
        }
        this.specialization = specialization;
    }

    public String getSpecialization() {
        return specialization;
    }
}