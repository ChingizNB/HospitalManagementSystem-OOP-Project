package com.chingiz.hospital.database;

import com.chingiz.hospital.model.Patient;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    // ========== CREATE ==========
    public boolean insertPatient(Patient patient) {
        String sql = "INSERT INTO patient (name, age, diagnosis, admission_date) VALUES (?, ?, ?, ?)";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, patient.getName());
            statement.setInt(2, patient.getAge());
            statement.setString(3, patient.getDiagnosis());
            statement.setDate(4, Date.valueOf(patient.getAdmissionDate()));

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            System.out.println("❌ Ошибка при добавлении пациента: " + e.getMessage());
            return false;
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    // ========== READ ALL ==========
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient ORDER BY patient_id";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return patients;

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Patient patient = extractPatientFromResultSet(resultSet);
                patients.add(patient);
            }

        } catch (SQLException e) {
            System.out.println("❌ Ошибка при получении пациентов: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return patients;
    }

    // ========== UPDATE ==========
    public boolean updatePatient(Patient patient) {
        String sql = "UPDATE patient SET name = ?, age = ?, diagnosis = ?, admission_date = ? WHERE patient_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, patient.getName());
            statement.setInt(2, patient.getAge());
            statement.setString(3, patient.getDiagnosis());
            statement.setDate(4, Date.valueOf(patient.getAdmissionDate()));
            statement.setInt(5, patient.getPatientId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            System.out.println("❌ Ошибка при обновлении пациента: " + e.getMessage());
            return false;
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    // ========== DELETE ==========
    public boolean deletePatient(int patientId) {
        String sql = "DELETE FROM patient WHERE patient_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, patientId);

            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            System.out.println("❌ Ошибка при удалении пациента: " + e.getMessage());
            return false;
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
    }

    // ========== GET BY ID ==========
    public Patient getPatientById(int patientId) {
        String sql = "SELECT * FROM patient WHERE patient_id = ?";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, patientId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractPatientFromResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Ошибка при поиске пациента: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return null;
    }

    // ========== SEARCH BY NAME (ILIKE) ==========
    public List<Patient> searchByName(String name) {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient WHERE name ILIKE ? ORDER BY name";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return patients;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + name + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    patients.add(extractPatientFromResultSet(resultSet));
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Ошибка при поиске по имени: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return patients;
    }

    // ========== SEARCH BY AGE RANGE ==========
    public List<Patient> searchByAgeRange(int minAge, int maxAge) {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient WHERE age BETWEEN ? AND ? ORDER BY age";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return patients;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, minAge);
            statement.setInt(2, maxAge);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    patients.add(extractPatientFromResultSet(resultSet));
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Ошибка при поиске по возрасту: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return patients;
    }

    // ========== SEARCH BY DIAGNOSIS ==========
    public List<Patient> searchByDiagnosis(String diagnosis) {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient WHERE diagnosis ILIKE ? ORDER BY admission_date";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return patients;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + diagnosis + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    patients.add(extractPatientFromResultSet(resultSet));
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Ошибка при поиске по диагнозу: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return patients;
    }

    // ========== SEARCH BY MIN AGE ==========
    public List<Patient> searchByMinAge(int minAge) {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient WHERE age >= ? ORDER BY age DESC";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return patients;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, minAge);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    patients.add(extractPatientFromResultSet(resultSet));
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Ошибка при поиске по минимальному возрасту: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return patients;
    }

    // ========== ВСПОМОГАТЕЛЬНЫЙ МЕТОД ==========
    private Patient extractPatientFromResultSet(ResultSet resultSet) throws SQLException {
        return new Patient(
                resultSet.getInt("patient_id"),
                resultSet.getString("name"),
                resultSet.getInt("age"),
                resultSet.getString("diagnosis"),
                resultSet.getDate("admission_date").toLocalDate()
        );
    }
}