package com.chingiz.hospital.model;

public class Nurse extends Person implements MedicalProfessional {
    private int shiftHours;

    public Nurse(String name, int age, int shiftHours) {
        super(name, age);
        setShiftHours(shiftHours);
    }

    @Override
    public void performDuty() {
        System.out.println("Медсестра " + getName() + " проверяет состояние пациентов и ставит капельки");
    }

    @Override
    public void provideCare() {
        System.out.println("Медсестра оказывает первую помощь и следит за графиком приема лекарств.");
    }

    public void setShiftHours(int shiftHours) {
        if (shiftHours <= 0 || shiftHours > 24) {
            throw new IllegalArgumentException("Смена должна быть от 1 до 24 часов.");
        }
        this.shiftHours = shiftHours;
    }

    public int getShiftHours() {
        return shiftHours;
    }

    @Override
    public String toString() {
        return super.toString() + ", Длительность смены: " + shiftHours + ", ч.";
    }
}