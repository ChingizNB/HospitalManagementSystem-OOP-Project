package com.chingiz.hospital.database;

import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/hospital_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Ncb87892007"; // ЗАМЕНИТЕ НА СВОЙ ПАРОЛЬ!

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("❌ Ошибка подключения к базе данных!");
            e.printStackTrace();
        }
        return connection;
    }

    public static boolean testConnection() {
        System.out.println("🔍 Тестируем подключение к базе данных...");
        System.out.println("   URL: " + URL);
        System.out.println("   Пользователь: " + USER);

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Подключение к PostgreSQL успешно!");

                // Проверяем наличие таблицы
                DatabaseMetaData meta = conn.getMetaData();
                ResultSet tables = meta.getTables(null, null, "patient", null);

                if (tables.next()) {
                    System.out.println("✅ Таблица 'patient' существует");
                } else {
                    System.out.println("⚠️  Таблица 'patient' не найдена!");
                }

                return true;
            }
        } catch (SQLException e) {
            System.out.println("❌ Ошибка подключения к PostgreSQL!");
            System.out.println("   Сообщение: " + e.getMessage());

            // Подсказки
            if (e.getMessage().contains("password")) {
                System.out.println("💡 Совет: Проверьте пароль в DatabaseConnection.java");
            } else if (e.getMessage().contains("does not exist")) {
                System.out.println("💡 Совет: Создайте базу данных: CREATE DATABASE hospital_db;");
            } else if (e.getMessage().contains("Connection refused")) {
                System.out.println("💡 Совет: Убедитесь, что PostgreSQL запущен");
            }
        }
        return false;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}