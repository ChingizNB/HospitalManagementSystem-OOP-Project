package com.chingiz.hospital;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        
        ArrayList<Person> hospitalList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) { 
            System.out.println("\n--- Hospital Management System ---");
            System.out.println("1. Add Doctor");
            System.out.println("2. Add Nurse");
            System.out.println("3. View All (Polymorphism)");
            System.out.println("4. Exit");
            System.out.print("Choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice == 1) {
                System.out.print("Name: "); String name = scanner.nextLine();
                System.out.print("Age: "); int age = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Specialization: "); String spec = scanner.nextLine();
                hospitalList.add(new Doctor(name, age, spec));
            } else if (choice == 2) {
                System.out.print("Name: "); String name = scanner.nextLine();
                System.out.print("Age: "); int age = scanner.nextInt();
                System.out.print("Shift Hours: "); int shift = scanner.nextInt();
                hospitalList.add(new Nurse(name, age, shift));
            } else if (choice == 3) {
                
                for (Person p : hospitalList) {
                    p.performDuty(); 

                    if (p instanceof Doctor) { 
                        ((Doctor) p).writePrescription();
                    }
                }
            } else if (choice == 4) break;
        }
    }
}
