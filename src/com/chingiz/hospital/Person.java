package com.chingiz.hospital;

public class Person {
    protected String name;
    protected int age;

    public Person(String name, int age) {
        setName(name);
        setAge(age);
    }

    public void setName(String name) {
        if (name != null && !name.isEmpty()) this.name = name;
        else this.name = "Unknown";
    }

    public void setAge(int age) {
        if (age > 0 && age < 120) this.age = age;
        else {
            System.out.println("Invalid age! Set to 18.");
            this.age = 18;
        }
    }

    
    public void performDuty() {
        System.out.println("Person is performing general hospital tasks.");
    }

    
    @Override
    public String toString() {
        return "Person [Name: " + name + ", Age: " + age + "]";
    }
}
