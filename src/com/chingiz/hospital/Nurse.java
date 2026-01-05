package com.chingiz.hospital;

public class Nurse extends Person {
    private int shiftHours;

    public Nurse(String name, int age, int shiftHours) {
        super(name, age);
        this.shiftHours = shiftHours;
    }

    @Override 
    public void performDuty() {
        System.out.println("Nurse " + name + " is assisting with a " + shiftHours + "-hour shift.");
    }

    @Override 
    public String toString() {
        return "Nurse: " + name + " | Shift: " + shiftHours + " hours | Age: " + age;
    }
}
