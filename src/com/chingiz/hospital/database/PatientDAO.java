package com.chingiz.hospital.database;

import com.chingiz.hospital.model.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    // ===== CREATE =====
    public boolean insertPatient(Patient p) {
        String sql = "INSERT INTO patient (name, age, diagnosis, admission_date) VALUES (?, ?, ?, ?)";
        Connection con = DatabaseConnection.getConnection();
        if (con == null) return false;

        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, p.getName());
            st.setInt(2, p.getAge());
            st.setString(3, p.getDiagnosis());
            st.setDate(4, Date.valueOf(p.getAdmissionDate()));

            return st.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Ошибка добавления: " + e.getMessage());
            return false;
        } finally {
            DatabaseConnection.closeConnection(con);
        }
    }

    // ===== READ ALL =====
    public List<Patient> getAllPatients() {
        return getList("SELECT * FROM patient ORDER BY patient_id");
    }

    // ===== UPDATE =====
    public boolean updatePatient(Patient p) {
        String sql = "UPDATE patient SET name=?, age=?, diagnosis=?, admission_date=? WHERE patient_id=?";
        Connection con = DatabaseConnection.getConnection();
        if (con == null) return false;

        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, p.getName());
            st.setInt(2, p.getAge());
            st.setString(3, p.getDiagnosis());
            st.setDate(4, Date.valueOf(p.getAdmissionDate()));
            st.setInt(5, p.getPatientId());

            return st.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Ошибка обновления: " + e.getMessage());
            return false;
        } finally {
            DatabaseConnection.closeConnection(con);
        }
    }

    // ===== DELETE =====
    public boolean deletePatient(int id) {
        String sql = "DELETE FROM patient WHERE patient_id=?";
        Connection con = DatabaseConnection.getConnection();
        if (con == null) return false;

        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            return st.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Ошибка удаления: " + e.getMessage());
            return false;
        } finally {
            DatabaseConnection.closeConnection(con);
        }
    }

    // ===== GET BY ID =====
    public Patient getPatientById(int id) {
        String sql = "SELECT * FROM patient WHERE patient_id=?";
        Connection con = DatabaseConnection.getConnection();
        if (con == null) return null;

        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return extract(rs);
            }

        } catch (Exception e) {
            System.out.println("Ошибка поиска: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(con);
        }
        return null;
    }

    // ===== SEARCH =====
    public List<Patient> searchByName(String name) {
        return getListWithParam(
                "SELECT * FROM patient WHERE name ILIKE ? ORDER BY name",
                "%" + name + "%"
        );
    }

    public List<Patient> searchByAgeRange(int min, int max) {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patient WHERE age BETWEEN ? AND ? ORDER BY age";
        Connection con = DatabaseConnection.getConnection();
        if (con == null) return list;

        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, min);
            st.setInt(2, max);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                list.add(extract(rs));
            }

        } catch (Exception e) {
            System.out.println("Ошибка поиска: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(con);
        }
        return list;
    }

    public List<Patient> searchByDiagnosis(String d) {
        return getListWithParam(
                "SELECT * FROM patient WHERE diagnosis ILIKE ? ORDER BY admission_date",
                "%" + d + "%"
        );
    }

    public List<Patient> searchByMinAge(int age) {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM patient WHERE age >= ? ORDER BY age DESC";
        Connection con = DatabaseConnection.getConnection();
        if (con == null) return list;

        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, age);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                list.add(extract(rs));
            }

        } catch (Exception e) {
            System.out.println("Ошибка поиска: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(con);
        }
        return list;
    }

    // ===== ОБЩИЙ МЕТОД ДЛЯ SELECT =====
    private List<Patient> getList(String sql) {
        List<Patient> list = new ArrayList<>();
        Connection con = DatabaseConnection.getConnection();
        if (con == null) return list;

        try {
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                list.add(extract(rs));
            }

        } catch (Exception e) {
            System.out.println("Ошибка получения данных: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(con);
        }
        return list;
    }

    private List<Patient> getListWithParam(String sql, String param) {
        List<Patient> list = new ArrayList<>();
        Connection con = DatabaseConnection.getConnection();
        if (con == null) return list;

        try {
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, param);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                list.add(extract(rs));
            }

        } catch (Exception e) {
            System.out.println("Ошибка поиска: " + e.getMessage());
        } finally {
            DatabaseConnection.closeConnection(con);
        }
        return list;
    }

    // ===== ВСПОМОГАТЕЛЬНЫЙ =====
    private Patient extract(ResultSet rs) throws SQLException {
        return new Patient(
                rs.getInt("patient_id"),
                rs.getString("name"),
                rs.getInt("age"),
                rs.getString("diagnosis"),
                rs.getDate("admission_date").toLocalDate()
        );
    }
}
