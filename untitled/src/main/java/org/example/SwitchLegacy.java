/*SWITCH LEGACY - Java 8
JAVA 8 (switch traditional):
- Use case and requiere 'break'
- Risk of : " fall - through": if you forget the break,and execute the next case*/

package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SwitchLegacy {

    public void initLegacyMenu(){

        var scanner = new Scanner(System.in);

        int option = 0;

        do{
            try{
                System.out.println("\nMENU");
                System.out.println("1. Register employees (matrix)");
                System.out.println("2. See salary category");
                System.out.println("3. Exit");

                option = scanner.nextInt();

                switch (option){
                    case 1:
                        registerEmployees(scanner);
                        break;
                    case 2:
                        seeSalaryCategory(scanner);
                        break;
                    case 3:
                        System.out.println("Shutdown...");
                        break;
                    default:
                        System.out.println("Invalid option");
                        break;
                }

            } catch (InputMismatchException e){
                System.out.println("Error: invalid input type");
                scanner.nextLine(); // clear buffer
            }

        }while(option != 3);

        scanner.close();
    }

    private void registerEmployees(Scanner scanner){

        try{
            System.out.println("How many employees do you want to register?");
            int numEmployees = scanner.nextInt();

            // Validation of primitive type (int)
            if (numEmployees <= 0){
                System.out.println("Number of employees must be greater than 0");
                return;
            }

            // dynamic matrix: employees x 3 quarters
            double[][] scores = new double[numEmployees][3];

            for(int i = 0; i < scores.length; i++){
                System.out.println("\nEmployee " + (i+1));

                // Example of primitive validation (int - age)
                System.out.println("Enter age:");
                int age = scanner.nextInt();

                if (age < 18 || age > 65){
                    System.out.println("Invalid age (must be between 18 and 65)");
                    i--; // repeat this employee
                    continue;
                }

                for(int j = 0; j < scores[i].length; j++){
                    System.out.println("Enter score for trimester " + (j+1));

                    double score = scanner.nextDouble();

                    // Validation of primitive type (double)
                    if (score < 0 || score > 5){
                        System.out.println("Invalid score (must be between 0 and 5)");
                        j--; // repeat same trimester
                        continue;
                    }

                    scores[i][j] = score;
                }
            }

            // Average calculation
            for(int i = 0; i < scores.length; i++){
                double sum = 0;

                for(int j = 0; j < scores[i].length; j++){
                    sum += scores[i][j];
                }

                double average = sum / 3;

                // Explicit casting
                int simplifiedScore = (int) average;

                /*
                Casting double -> int:
                The decimal part is lost.
                Example: 4.8 → 4
                */

                System.out.println("\nEmployee " + (i+1));
                System.out.println("Average: " + average);
                System.out.println("Simplified score: " + simplifiedScore);

                // ternary operator
                String status = (average >= 3.5) ? "PROMOTED" : "NOT PROMOTED";
                System.out.println("Status: " + status);
            }

            /*
            Primitive type validation applied:
            - int (numEmployees, age): validated with logical ranges
            - double (scores): validated between 0.0 and 5.0
            This prevents invalid input and improves system robustness.
            */

        } catch (InputMismatchException e){
            System.out.println("Invalid data entered");
            scanner.nextLine();
        }
    }

    private void seeSalaryCategory(Scanner scanner){

        try{
            System.out.println("Enter salary:");
            double salary = scanner.nextDouble();

            // Validation of primitive type (double)
            if (salary < 0){
                System.out.println("Salary cannot be negative");
                return;
            }

            String category = SwitchModern.getSalaryCategory(salary);

            System.out.println("Category: " + category);

        } catch (InputMismatchException e){
            System.out.println("Invalid salary input");
            scanner.nextLine();
        }
    }
}