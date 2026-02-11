package com.chingiz.hospital.menu;

import com.chingiz.hospital.model.*;
import com.chingiz.hospital.database.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HospitalMenu implements Menu {

    private Scanner sc = new Scanner(System.in);
    private PatientDAO dao = new PatientDAO();
    private List<Person> staff = new ArrayList<>();
    private boolean running = true;

    @Override
    public void run() {

        System.out.println("Hospital Management System");
        testDatabaseConnection();
        initStaff();

        while (running) {
            displayMenu();
            System.out.print("Выбор: ");

            try {
                int choice = Integer.parseInt(sc.nextLine());
                handleChoice(choice);
            } catch (Exception e) {
                System.out.println("Введите число!");
            }
        }

        sc.close();
    }

    @Override
    public void displayMenu() {
        System.out.println("------------------------------");
        System.out.println("1  Добавить пациента");
        System.out.println("2  Все пациенты");
        System.out.println("3  Обновить пациента");
        System.out.println("4  Удалить пациента");
        System.out.println("5  Поиск по имени");
        System.out.println("6  Поиск по возрасту");
        System.out.println("7  Поиск по диагнозу");
        System.out.println("8  Старше возраста");
        System.out.println("9  Добавить врача");
        System.out.println("10 Добавить медсестру");
        System.out.println("11 Показать персонал");
        System.out.println("12 Все работают");
        System.out.println("13 Проверить БД");
        System.out.println("14 Статистика");
        System.out.println("0  Выход");
        System.out.println("------------------------------");
    }

    private void handleChoice(int choice) {

        switch (choice) {
            case 1:
                addPatient();
                break;
            case 2:
                viewAllPatients();
                break;
            case 3:
                updatePatient();
                break;
            case 4:
                deletePatient();
                break;
            case 5:
                searchByName();
                break;
            case 6:
                searchByAge();
                break;
            case 7:
                searchByDiagnosis();
                break;
            case 8:
                searchByMinAge();
                break;
            case 9:
                addDoctor();
                break;
            case 10:
                addNurse();
                break;
            case 11:
                showStaff();
                break;
            case 12:
                makeAllWork();
                break;
            case 13:
                testDatabaseConnection();
                break;
            case 14:
                showStats();
                break;
            case 0:
                running = false;
                break;
            default:
                System.out.println("Неверный выбор!");
        }
    }

    // ================= ПАЦИЕНТЫ =================

    private void addPatient() {
        try {
            System.out.print("Имя: ");
            String name = sc.nextLine();

            System.out.print("Возраст: ");
            int age = Integer.parseInt(sc.nextLine());

            System.out.print("Диагноз: ");
            String diagnosis = sc.nextLine();

            System.out.print("Дата (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(sc.nextLine());

            Patient p = new Patient(0, name, age, diagnosis, date);

            if (dao.insertPatient(p)) {
                System.out.println("Пациент добавлен!");
            }

        } catch (Exception e) {
            System.out.println("Ошибка ввода!");
        }
    }

    private void viewAllPatients() {
        List<Patient> list = dao.getAllPatients();

        if (list.isEmpty()) {
            System.out.println("Нет пациентов.");
        } else {
            for (Patient p : list) {
                System.out.println(p);
            }
        }
    }

    private void updatePatient() {
        try {
            System.out.print("ID: ");
            int id = Integer.parseInt(sc.nextLine());

            Patient p = dao.getPatientById(id);
            if (p == null) {
                System.out.println("Пациент не найден.");
                return;
            }

            System.out.print("Новое имя: ");
            String name = sc.nextLine();

            System.out.print("Новый возраст: ");
            int age = Integer.parseInt(sc.nextLine());

            System.out.print("Новый диагноз: ");
            String diagnosis = sc.nextLine();

            System.out.print("Новая дата (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(sc.nextLine());

            Patient updated = new Patient(id, name, age, diagnosis, date);

            if (dao.updatePatient(updated)) {
                System.out.println("Обновлено!");
            }

        } catch (Exception e) {
            System.out.println("Ошибка!");
        }
    }

    private void deletePatient() {
        try {
            System.out.print("ID: ");
            int id = Integer.parseInt(sc.nextLine());

            if (dao.deletePatient(id)) {
                System.out.println("Удален!");
            }

        } catch (Exception e) {
            System.out.println("Ошибка!");
        }
    }

    private void searchByName() {
        System.out.print("Имя: ");
        String name = sc.nextLine();

        List<Patient> list = dao.searchByName(name);

        for (Patient p : list) {
            System.out.println(p);
        }
    }

    private void searchByAge() {
        try {
            System.out.print("Мин возраст: ");
            int min = Integer.parseInt(sc.nextLine());

            System.out.print("Макс возраст: ");
            int max = Integer.parseInt(sc.nextLine());

            List<Patient> list = dao.searchByAgeRange(min, max);

            for (Patient p : list) {
                System.out.println(p);
            }

        } catch (Exception e) {
            System.out.println("Ошибка!");
        }
    }

    private void searchByDiagnosis() {
        System.out.print("Диагноз: ");
        String d = sc.nextLine();

        List<Patient> list = dao.searchByDiagnosis(d);

        for (Patient p : list) {
            System.out.println(p);
        }
    }

    private void searchByMinAge() {
        try {
            System.out.print("Возраст: ");
            int age = Integer.parseInt(sc.nextLine());

            List<Patient> list = dao.searchByMinAge(age);

            for (Patient p : list) {
                System.out.println(p);
            }

        } catch (Exception e) {
            System.out.println("Ошибка!");
        }
    }

    // ================= ПЕРСОНАЛ =================

    private void initStaff() {
        staff.add(new Doctor("Айбек", 45, "Хирург"));
        staff.add(new Nurse("Айгуль", 30, 8));
    }

    private void addDoctor() {
        try {
            System.out.print("Имя: ");
            String name = sc.nextLine();

            System.out.print("Возраст: ");
            int age = Integer.parseInt(sc.nextLine());

            System.out.print("Специализация: ");
            String spec = sc.nextLine();

            staff.add(new Doctor(name, age, spec));

        } catch (Exception e) {
            System.out.println("Ошибка!");
        }
    }

    private void addNurse() {
        try {
            System.out.print("Имя: ");
            String name = sc.nextLine();

            System.out.print("Возраст: ");
            int age = Integer.parseInt(sc.nextLine());

            System.out.print("Смена (часы): ");
            int shift = Integer.parseInt(sc.nextLine());

            staff.add(new Nurse(name, age, shift));

        } catch (Exception e) {
            System.out.println("Ошибка!");
        }
    }

    private void showStaff() {
        for (Person p : staff) {
            System.out.println(p);
        }
    }

    private void makeAllWork() {
        for (Person p : staff) {
            p.performDuty();
        }
    }

    // ================= СИСТЕМА =================

    private void testDatabaseConnection() {
        if (DatabaseConnection.testConnection()) {
            System.out.println("БД подключена!");
        }
    }

    private void showStats() {
        List<Patient> list = dao.getAllPatients();

        int total = list.size();
        int sum = 0;

        for (Patient p : list) {
            sum += p.getAge();
        }

        if (total > 0) {
            System.out.println("Всего пациентов: " + total);
            System.out.println("Средний возраст: " + (sum / total));
        } else {
            System.out.println("Нет данных.");
        }
    }
}
