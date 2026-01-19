package com.chingiz.hospital.menu;

import com.chingiz.hospital.model.Doctor;
import com.chingiz.hospital.model.Nurse;
import com.chingiz.hospital.model.Person;
import java.util.ArrayList;
import java.util.Scanner;

public class HospitalMenu implements Menu {
    private ArrayList<Person> people = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private boolean running = true;

    @Override
    public void displayMenu() {
        System.out.println("\n=== Система управления больницей ===");
        System.out.println("1. Добавить врача");
        System.out.println("2. Добавить медсестру");
        System.out.println("3. Показать всех сотрудников");
        System.out.println("4. Заставить всех работать (полиморфизм)");
        System.out.println("0. Выход");
        System.out.print("Выберите опцию: ");
    }

    @Override
    public void run() {
        while (running) {
            displayMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                handleChoice(choice);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите число!");
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка валидации: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Неожиданная ошибка: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private void handleChoice(int choice) {
        switch (choice) {
            case 1:
                addDoctor();
                break;
            case 2:
                addNurse();
                break;
            case 3:
                showAll();
                break;
            case 4:
                makeAllWork();
                break;
            case 0:
                running = false;
                System.out.println("Выход из системы...");
                break;
            default:
                System.out.println("Неверный выбор!");
        }
    }

    private void addDoctor() {
        try {
            System.out.print("Введите имя врача: ");
            String name = scanner.nextLine();

            System.out.print("Введите возраст врача: ");
            int age = Integer.parseInt(scanner.nextLine());

            System.out.print("Введите специализацию врача: ");
            String specialization = scanner.nextLine();

            Doctor doctor = new Doctor(name, age, specialization);
            people.add(doctor);
            System.out.println("Врач добавлен успешно!");

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Возраст должен быть числом!");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка валидации: " + e.getMessage());
        }
    }

    private void addNurse() {
        try {
            System.out.print("Введите имя медсестры: ");
            String name = scanner.nextLine();

            System.out.print("Введите возраст медсестры: ");
            int age = Integer.parseInt(scanner.nextLine());

            System.out.print("Введите длительность смены (часы): ");
            int shiftHours = Integer.parseInt(scanner.nextLine());

            Nurse nurse = new Nurse(name, age, shiftHours);
            people.add(nurse);
            System.out.println("Медсестра добавлена успешно!");

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Возраст и смена должны быть числами!");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка валидации: " + e.getMessage());
        }
    }

    private void showAll() {
        if (people.isEmpty()) {
            System.out.println("Список сотрудников пуст.");
            return;
        }

        System.out.println("\n=== Список сотрудников ===");
        for (Person person : people) {
            System.out.println(person);
        }
    }

    private void makeAllWork() {
        if (people.isEmpty()) {
            System.out.println("Нет сотрудников для работы.");
            return;
        }

        System.out.println("\n=== Все сотрудники работают ===");
        for (Person person : people) {
            person.performDuty();
        }
    }
}