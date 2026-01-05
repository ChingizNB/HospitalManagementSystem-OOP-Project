package com.chingiz.hospital;

public class Nurse extends Person {
    private int shiftHours;

    public Nurse(String name, int age, int shiftHours) {
        super(name, age);
        this.shiftHours = shiftHours;
    }

    @Override
    public void performDuty() {
        System.out.println("Nurse " + name + " is assisting doctors during a " + shiftHours + "-hour shift.");
    }
}