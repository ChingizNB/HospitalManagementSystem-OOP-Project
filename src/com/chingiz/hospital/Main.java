package com.chingiz.hospital;

import com.chingiz.hospital.menu.HospitalMenu;
import com.chingiz.hospital.menu.Menu;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== HOSPITAL MANAGEMENT SYSTEM ===");
        System.out.println("Week 7-8: JDBC & PostgreSQL Integration");
        System.out.println("=======================================\n");

        Menu menu = new HospitalMenu();
        menu.run();
    }
}
