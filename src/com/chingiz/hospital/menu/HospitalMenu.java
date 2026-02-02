package com.chingiz.hospital.menu;

import com.chingiz.hospital.model.Doctor;
import com.chingiz.hospital.model.Nurse;
import com.chingiz.hospital.model.Patient;
import com.chingiz.hospital.model.Person;
import com.chingiz.hospital.database.PatientDAO;
import com.chingiz.hospital.database.DatabaseConnection;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HospitalMenu implements Menu {
    private Scanner scanner = new Scanner(System.in);
    private boolean running = true;
    private PatientDAO patientDAO = new PatientDAO();

    // Для демонстрации полиморфизма (без базы данных)
    private ArrayList<Person> hospitalStaff = new ArrayList<>();

    @Override
    public void run() {
        System.out.println("🏥 Добро пожаловать в Hospital Management System!");
        System.out.println("Week 7-8: Полная интеграция с PostgreSQL\n");

        // Проверка подключения к БД при запуске
        testDatabaseConnection();

        // Инициализация тестовых данных персонала
        initializeStaff();

        while (running) {
            displayMenu();

            try {
                System.out.print("\nВаш выбор: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Очистить буфер

                handleChoice(choice);

            } catch (Exception e) {
                System.out.println("❌ Ошибка: введите число!");
                scanner.nextLine();
            }
        }

        System.out.println("✅ Программа завершена.");
        scanner.close();
    }

    @Override
    public void displayMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ГЛАВНОЕ МЕНЮ - БОЛЬНИЧНАЯ СИСТЕМА");
        System.out.println("=".repeat(50));
        System.out.println("=== ПАЦИЕНТЫ (База данных PostgreSQL) ===");
        System.out.println("1.  Добавить нового пациента");
        System.out.println("2.  Просмотреть всех пациентов");
        System.out.println("3.  Обновить данные пациента");
        System.out.println("4.  Удалить пациента");
        System.out.println("5.  Поиск пациента по имени");
        System.out.println("6.  Поиск пациента по возрасту");
        System.out.println("7.  Поиск пациентов по диагнозу");
        System.out.println("8.  Пациенты старше определенного возраста");
        System.out.println("\n=== ПЕРСОНАЛ (Полиморфизм - ArrayList) ===");
        System.out.println("9.  Добавить врача");
        System.out.println("10. Добавить медсестру");
        System.out.println("11. Показать весь персонал");
        System.out.println("12. Все работают (полиморфизм)");
        System.out.println("\n=== СИСТЕМНЫЕ ОПЕРАЦИИ ===");
        System.out.println("13. Проверить подключение к базе данных");
        System.out.println("14. Статистика базы данных");
        System.out.println("0.  Выход");
        System.out.println("=".repeat(50));
    }

    private void handleChoice(int choice) {
        switch (choice) {
            // Операции с пациентами (БД)
            case 1: addPatient(); break;
            case 2: viewAllPatients(); break;
            case 3: updatePatient(); break;
            case 4: deletePatient(); break;
            case 5: searchPatientByName(); break;
            case 6: searchPatientByAgeRange(); break;
            case 7: searchPatientByDiagnosis(); break;
            case 8: searchPatientsOlderThan(); break;

            // Операции с персоналом (ArrayList)
            case 9: addDoctor(); break;
            case 10: addNurse(); break;
            case 11: showAllStaff(); break;
            case 12: makeAllStaffWork(); break;

            // Системные операции
            case 13: testDatabaseConnection(); break;
            case 14: showDatabaseStats(); break;
            case 0:
                running = false;
                System.out.println("Выход из системы...");
                break;
            default:
                System.out.println("❌ Неверный выбор! Выберите 0-14");
        }
    }

    // ==================== МЕТОДЫ ДЛЯ ПАЦИЕНТОВ (БАЗА ДАННЫХ) ====================

    private void addPatient() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("ДОБАВЛЕНИЕ НОВОГО ПАЦИЕНТА");
        System.out.println("=".repeat(40));

        try {
            System.out.print("Введите имя пациента: ");
            String name = scanner.nextLine();

            System.out.print("Введите возраст пациента: ");
            int age = Integer.parseInt(scanner.nextLine());

            System.out.print("Введите диагноз: ");
            String diagnosis = scanner.nextLine();

            System.out.print("Введите дату поступления (ГГГГ-ММ-ДД): ");
            LocalDate admissionDate = LocalDate.parse(scanner.nextLine());

            Patient patient = new Patient(0, name, age, diagnosis, admissionDate);
            boolean success = patientDAO.insertPatient(patient);

            if (success) {
                System.out.println("✅ Пациент успешно добавлен в базу данных!");
            }

        } catch (NumberFormatException e) {
            System.out.println("❌ Ошибка: возраст должен быть числом!");
        } catch (Exception e) {
            System.out.println("❌ Ошибка: " + e.getMessage());
        }
    }

    private void viewAllPatients() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("СПИСОК ВСЕХ ПАЦИЕНТОВ");
        System.out.println("=".repeat(40));

        List<Patient> patients = patientDAO.getAllPatients();

        if (patients.isEmpty()) {
            System.out.println("📭 В базе данных нет пациентов.");
        } else {
            System.out.println("Всего пациентов: " + patients.size());
            System.out.println("-".repeat(40));

            for (Patient patient : patients) {
                System.out.println(patient);
            }
        }
    }

    private void updatePatient() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("ОБНОВЛЕНИЕ ДАННЫХ ПАЦИЕНТА");
        System.out.println("=".repeat(40));

        try {
            System.out.print("Введите ID пациента для обновления: ");
            int id = Integer.parseInt(scanner.nextLine());

            Patient existing = patientDAO.getPatientById(id);
            if (existing == null) {
                System.out.println("❌ Пациент с ID " + id + " не найден!");
                return;
            }

            System.out.println("\nТекущие данные пациента:");
            System.out.println(existing);
            System.out.println("\nВведите новые данные (оставьте пустым для сохранения текущих):");

            System.out.print("Новое имя [" + existing.getName() + "]: ");
            String name = scanner.nextLine();
            if (name.trim().isEmpty()) name = existing.getName();

            System.out.print("Новый возраст [" + existing.getAge() + "]: ");
            String ageInput = scanner.nextLine();
            int age = ageInput.isEmpty() ? existing.getAge() : Integer.parseInt(ageInput);

            System.out.print("Новый диагноз [" + existing.getDiagnosis() + "]: ");
            String diagnosis = scanner.nextLine();
            if (diagnosis.trim().isEmpty()) diagnosis = existing.getDiagnosis();

            System.out.print("Новая дата поступления [" + existing.getAdmissionDate() + "]: ");
            String dateInput = scanner.nextLine();
            LocalDate admissionDate = dateInput.isEmpty() ? existing.getAdmissionDate() : LocalDate.parse(dateInput);

            Patient updated = new Patient(id, name, age, diagnosis, admissionDate);
            boolean success = patientDAO.updatePatient(updated);

            if (success) {
                System.out.println("✅ Данные пациента обновлены!");
            }

        } catch (NumberFormatException e) {
            System.out.println("❌ Ошибка: ID и возраст должны быть числами!");
        } catch (Exception e) {
            System.out.println("❌ Ошибка: " + e.getMessage());
        }
    }

    private void deletePatient() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("УДАЛЕНИЕ ПАЦИЕНТА");
        System.out.println("=".repeat(40));

        try {
            System.out.print("Введите ID пациента для удаления: ");
            int id = Integer.parseInt(scanner.nextLine());

            Patient existing = patientDAO.getPatientById(id);
            if (existing == null) {
                System.out.println("❌ Пациент с ID " + id + " не найден!");
                return;
            }

            System.out.println("\nПациент для удаления:");
            System.out.println(existing);
            System.out.print("\n⚠️  Вы уверены? Это действие нельзя отменить! (yes/no): ");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("yes")) {
                boolean success = patientDAO.deletePatient(id);
                if (success) {
                    System.out.println("✅ Пациент удален из базы данных!");
                }
            } else {
                System.out.println("❌ Удаление отменено.");
            }

        } catch (NumberFormatException e) {
            System.out.println("❌ Ошибка: ID должен быть числом!");
        }
    }

    private void searchPatientByName() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("ПОИСК ПАЦИЕНТА ПО ИМЕНИ");
        System.out.println("=".repeat(40));

        System.out.print("Введите имя или часть имени для поиска: ");
        String name = scanner.nextLine();

        List<Patient> results = patientDAO.searchByName(name);

        if (results.isEmpty()) {
            System.out.println("🔍 Пациенты не найдены.");
        } else {
            System.out.println("\nНайдено пациентов: " + results.size());
            System.out.println("-".repeat(40));

            for (Patient patient : results) {
                System.out.println(patient);
            }
        }
    }

    private void searchPatientByAgeRange() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("ПОИСК ПАЦИЕНТОВ ПО ВОЗРАСТУ");
        System.out.println("=".repeat(40));

        try {
            System.out.print("Введите минимальный возраст: ");
            int minAge = Integer.parseInt(scanner.nextLine());

            System.out.print("Введите максимальный возраст: ");
            int maxAge = Integer.parseInt(scanner.nextLine());

            List<Patient> results = patientDAO.searchByAgeRange(minAge, maxAge);

            if (results.isEmpty()) {
                System.out.println("🔍 Пациенты в этом возрастном диапазоне не найдены.");
            } else {
                System.out.println("\nНайдено пациентов: " + results.size());
                System.out.println("-".repeat(40));

                for (Patient patient : results) {
                    System.out.println(patient);
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("❌ Ошибка: возраст должен быть числом!");
        }
    }

    private void searchPatientByDiagnosis() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("ПОИСК ПАЦИЕНТОВ ПО ДИАГНОЗУ");
        System.out.println("=".repeat(40));

        System.out.print("Введите диагноз или часть диагноза: ");
        String diagnosis = scanner.nextLine();

        List<Patient> results = patientDAO.searchByDiagnosis(diagnosis);

        if (results.isEmpty()) {
            System.out.println("🔍 Пациенты с таким диагнозом не найдены.");
        } else {
            System.out.println("\nНайдено пациентов: " + results.size());
            System.out.println("-".repeat(40));

            for (Patient patient : results) {
                System.out.println(patient);
            }
        }
    }

    private void searchPatientsOlderThan() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("ПАЦИЕНТЫ СТАРШЕ ОПРЕДЕЛЕННОГО ВОЗРАСТА");
        System.out.println("=".repeat(40));

        try {
            System.out.print("Введите минимальный возраст: ");
            int minAge = Integer.parseInt(scanner.nextLine());

            List<Patient> results = patientDAO.searchByMinAge(minAge);

            if (results.isEmpty()) {
                System.out.println("🔍 Пациенты старше " + minAge + " лет не найдены.");
            } else {
                System.out.println("\nНайдено пациентов: " + results.size());
                System.out.println("-".repeat(40));

                for (Patient patient : results) {
                    System.out.println(patient);
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("❌ Ошибка: возраст должен быть числом!");
        }
    }

    // ==================== МЕТОДЫ ДЛЯ ПЕРСОНАЛА (ARRAYLIST) ====================

    private void initializeStaff() {
        hospitalStaff.add(new Doctor("Доктор Айбек", 45, "Хирург"));
        hospitalStaff.add(new Doctor("Доктор Алия", 38, "Терапевт"));
        hospitalStaff.add(new Nurse("Медсестра Айгуль", 28, 8));
        hospitalStaff.add(new Nurse("Медсестра Дана", 32, 12));
    }

    private void addDoctor() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("ДОБАВЛЕНИЕ ВРАЧА");
        System.out.println("=".repeat(40));

        try {
            System.out.print("Введите имя врача: ");
            String name = scanner.nextLine();

            System.out.print("Введите возраст врача: ");
            int age = Integer.parseInt(scanner.nextLine());

            System.out.print("Введите специализацию: ");
            String specialization = scanner.nextLine();

            Doctor doctor = new Doctor(name, age, specialization);
            hospitalStaff.add(doctor);
            System.out.println("✅ Врач " + name + " добавлен в штат!");

        } catch (NumberFormatException e) {
            System.out.println("❌ Ошибка: возраст должен быть числом!");
        }
    }

    private void addNurse() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("ДОБАВЛЕНИЕ МЕДСЕСТРЫ");
        System.out.println("=".repeat(40));

        try {
            System.out.print("Введите имя медсестры: ");
            String name = scanner.nextLine();

            System.out.print("Введите возраст медсестры: ");
            int age = Integer.parseInt(scanner.nextLine());

            System.out.print("Введите длительность смены (часы): ");
            int shiftHours = Integer.parseInt(scanner.nextLine());

            Nurse nurse = new Nurse(name, age, shiftHours);
            hospitalStaff.add(nurse);
            System.out.println("✅ Медсестра " + name + " добавлена в штат!");

        } catch (NumberFormatException e) {
            System.out.println("❌ Ошибка: возраст и смена должны быть числами!");
        }
    }

    private void showAllStaff() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("ВЕСЬ МЕДИЦИНСКИЙ ПЕРСОНАЛ");
        System.out.println("=".repeat(40));

        if (hospitalStaff.isEmpty()) {
            System.out.println("📭 Персонал больницы пуст.");
        } else {
            System.out.println("Всего сотрудников: " + hospitalStaff.size());
            System.out.println("-".repeat(40));

            for (int i = 0; i < hospitalStaff.size(); i++) {
                System.out.println((i + 1) + ". " + hospitalStaff.get(i));
            }
        }
    }

    private void makeAllStaffWork() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("РАБОЧИЙ ДЕНЬ В БОЛЬНИЦЕ");
        System.out.println("=".repeat(40));

        if (hospitalStaff.isEmpty()) {
            System.out.println("👥 Нет персонала для работы.");
        } else {
            for (Person person : hospitalStaff) {
                person.performDuty();
            }
            System.out.println("\n✅ Все сотрудники выполнили свои обязанности!");
        }
    }

    // ==================== СИСТЕМНЫЕ МЕТОДЫ ====================

    private void testDatabaseConnection() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("ПРОВЕРКА ПОДКЛЮЧЕНИЯ К БАЗЕ ДАННЫХ");
        System.out.println("=".repeat(40));

        boolean connected = DatabaseConnection.testConnection();

        if (connected) {
            System.out.println("🎉 База данных готова к работе!");
        }

        System.out.print("\nНажмите Enter чтобы продолжить...");
        scanner.nextLine();
    }

    private void showDatabaseStats() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("СТАТИСТИКА БАЗЫ ДАННЫХ");
        System.out.println("=".repeat(40));

        List<Patient> patients = patientDAO.getAllPatients();
        System.out.println("📊 Общая статистика:");
        System.out.println("   • Всего пациентов: " + patients.size());

        if (!patients.isEmpty()) {
            double avgAge = patients.stream()
                    .mapToInt(Patient::getAge)
                    .average()
                    .orElse(0.0);
            System.out.println("   • Средний возраст: " + String.format("%.1f", avgAge));

            long critical = patients.stream()
                    .filter(p -> p.getDiagnosis() != null &&
                            p.getDiagnosis().toLowerCase().contains("критич"))
                    .count();
            System.out.println("   • Критических пациентов: " + critical);
        }

        System.out.print("\nНажмите Enter чтобы продолжить...");
        scanner.nextLine();
    }
}