package com.chingiz.hospital;

public class Person {
    // Используем protected, чтобы наследники имели доступ (Требование 15)
    protected String name;
    protected int age;

    public Person(String name, int age) {
        // Вызываем сеттеры в конструкторе для валидации (Вопрос 3 из банка вопросов)
        setName(name);
        setAge(age);
    }

    // Инкапсуляция: Геттеры и Сеттеры с валидацией
    public String getName() { return name; }

    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        } else {
            this.name = "Unknown";
        }
    }

    public int getAge() { return age; }

    public void setAge(int age) {
        // Валидация данных (5 баллов)
        if (age > 0 && age < 120) {
            this.age = age;
        } else {
            System.out.println("Invalid age! Setting to default (18).");
            this.age = 18;
        }
    }

    // Метод для полиморфизма (7 баллов)
    public void performDuty() {
        System.out.println(name + " is doing general tasks.");
    }
}