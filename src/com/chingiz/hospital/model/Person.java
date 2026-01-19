package com.chingiz.hospital.model;

public abstract class Person {
    protected String name;
    protected int age;

    public Person(String name, int age) {
        setName(name);
        setAge(age);
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }

    public void setAge(int age) {
        if (age <= 0 || age > 120) {
            throw new IllegalArgumentException("Invalid age: " + age);
        }
        this.age = age;
    }

    public abstract void performDuty();

    @Override
    public String toString() {
        return "Name: " + name + ", Age: " + age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}