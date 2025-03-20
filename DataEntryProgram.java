/*
Author: Thanh Phu Phan (21664322)
Unit: Programming Desgin and Implementation
Date: 26/04/2024
Submitted date: 05/05/2024
Purpose: Getting data and write to csv file
*/

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class DataEntryProgram {
    static final int DRIVERS_NUM = 2; // Constant variable for 2 drivers each team

    public static void main(String[] args) {
        // Declare and initialized variables
        char userChoice;
        int tempNumTeam = 0, numTeam = 0;
        boolean addMoreTeams = true;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the FIA F1 Data Entry Program");
        System.out.println("========================================");

        numTeam = getTeamNum(scanner); // Get number of teams

        // Create a new main teams object array
        Team[] teams = new Team[numTeam];
        // Ask user to input each team' details for main teams array
        getTeamDetails(teams, numTeam, scanner);

        while (addMoreTeams) {
            // Ask user if they want to add more data
            System.out.print("Would you like to add more data? (Yes/No): ");
            userChoice = scanner.next().charAt(0);
            scanner.nextLine();

            /* === YES === */
            if (userChoice == 'y' || userChoice == 'Y') {
                // Get temp number of teams
                tempNumTeam = getTeamNum(scanner);

                // Create a temp teams array
                Team[] tempTeams = new Team[tempNumTeam + numTeam];
                copyTeams(teams, tempTeams); // Copy main teams array to temp teams array
                // Ask user to input team' details for temp teams array
                getMoreTeam(tempTeams, numTeam, tempNumTeam, scanner);

                // Increment numTeam with tempNumTeam
                numTeam += tempNumTeam;

                teams = tempTeams; // Update the main array

                /* === NO === */
            } else if (userChoice == 'n' || userChoice == 'N') {
                System.out.println("The current data look like this");

                // Check if the main array is null or not
                if (teams != null) {
                    for (int i = 0; i < teams.length; i++) {
                        // Display all team details to user
                        teams[i].displayTeamDetails();
                    }
                }

                String fileName = getFileName(scanner); // Ask user for a file name
                // Write main teams array to the csv file with file name entered from user
                writeToFile(fileName, teams);
                addMoreTeams = false; // Change addMoreTeam boolean to false
                break;

                /* === INVALID YES/NO ANSWER === */
            } else {
                System.out.println("Invalid input. Please enter Y or N.");
            }
        }

        scanner.close();

    }

    /**
     * Ask user for number of teams as an integer and greater than 2
     * 
     * @param pScanner
     * @return teamNum
     */
    private static int getTeamNum(Scanner pScanner) {
        int teamNum;
        do {
            teamNum = validateIntInput("> Enter number of teams: ", pScanner);

            if (teamNum < 2) {
                System.out.println("Number of teams cannot be less than 2.");
            }
        } while (teamNum < 2);
        return teamNum;
    }

    /**
     * Ask user for team details
     * 
     * @param pTeams
     * @param pNoOfTeams
     * @param pScanner
     * @return none
     */
    private static void getTeamDetails(Team[] pTeams, int pNoOfTeams, Scanner pScanner) {
        boolean isDuplicate = false;

        do {
            for (int i = 0; i < pNoOfTeams; i++) {
                System.out.println("--------");
                System.out.println("Enter details for team " + (i + 1));

                // Ask user for team details
                String grandPrix = validateStringInput("> Grand Prix: ", pScanner);
                String teamName = validateStringInput("> Team Name: ", pScanner);
                String carCode = validateStringInput("> Car Code: ", pScanner);

                pTeams[i] = new Team(teamName, carCode, grandPrix);

                for (int j = 0; j < DRIVERS_NUM; j++) {
                    // Ask user for driver details
                    System.out.println();
                    System.out.println("Driver " + (j + 1) + " details:");
                    String driverName = validateStringInput("> Name: ", pScanner);

                    System.out.println("** If the driver does not finish, type -1 **");
                    int position = validateIntInput("> Position: ", pScanner);

                    // If driver dnf, set fastest lap to 205.50 seconds
                    double fastestLap = 0;
                    if (position == -1) {
                        fastestLap = 205.50;
                    } else {
                        fastestLap = validateDoubleInput("> Fastest Lap: ", pScanner);
                    }

                    pTeams[i].getDrivers()[j] = new Driver(driverName, position, fastestLap);
                }
            }

            // Check for duplicate teams
            for (int i = 0; i < pNoOfTeams; i++) {
                for (int j = i + 1; j < pNoOfTeams; j++) {
                    if (pTeams[i].equals(pTeams[j])) {
                        isDuplicate = true;
                        System.out.println("Duplicate teams detected. Please re-enter unique team details.");
                    } else {
                        isDuplicate = false;
                    }
                }
            }

        } while (isDuplicate);
    }

    /**
     * Ask user for adding more team details
     * 
     * @param pTeams
     * @param pNoOfTeams
     * @param pAddNumTeams
     * @param pScanner
     * @return none
     */
    private static void getMoreTeam(Team[] pTeams, int pNoOfTeams, int pAddNumTeams, Scanner pScanner) {
        boolean isDuplicate = false;

        do {
            for (int i = 0; i < pAddNumTeams; i++) {
                System.out.println("--------");
                System.out.println("Enter details for team " + (i + 1));

                // Ask user for team details
                String grandPrix = validateStringInput("> Grand Prix: ", pScanner);
                String teamName = validateStringInput("> Team Name: ", pScanner);
                String carCode = validateStringInput("> Car Code: ", pScanner);

                pTeams[i + pNoOfTeams] = new Team(teamName, carCode, grandPrix);

                for (int j = 0; j < DRIVERS_NUM; j++) {
                    // Ask user for driver details
                    System.out.println();
                    System.out.println("Driver " + (j + 1) + " details:");
                    String driverName = validateStringInput("> Name: ", pScanner);

                    System.out.println("** If the driver does not finish, type -1 **");
                    int position = validateIntInput("> Position: ", pScanner);

                    // If driver dnf, set fastest lap to 205.50 seconds
                    double fastestLap = 0;
                    if (position == -1) {
                        fastestLap = 205.50;
                    } else {
                        fastestLap = validateDoubleInput("> Fastest Lap: ", pScanner);
                    }

                    pTeams[i + pNoOfTeams].getDrivers()[j] = new Driver(driverName, position, fastestLap);
                }
            }

            // Check for duplicate teams
            for (int i = 0; i < pAddNumTeams; i++) {
                for (int j = i + 1; j < pAddNumTeams; j++) {
                    if (pTeams[i + pNoOfTeams].equals(pTeams[j + pNoOfTeams])) {
                        isDuplicate = true;
                        System.out.println("Duplicate teams detected. Please re-enter unique team details.");
                    } else {
                        isDuplicate = false;
                    }
                }
            }

        } while (isDuplicate);
    }

    /**
     * Copy old object to a new object
     * 
     * @param pOldTeams
     * @param pNewTeams
     * @return none
     */
    private static void copyTeams(Team[] pOldTeams, Team[] pNewTeams) {
        // Copy temp teams objects to new teams objects
        for (int i = 0; i < pOldTeams.length; i++) {
            pNewTeams[i] = new Team(pOldTeams[i]);
        }
    }

    /**
     * Write the teams object to csv file
     * 
     * @param pFileName
     * @param pTeams
     * @return none
     */
    private static void writeToFile(String pFileName, Team[] pTeams) {

        File csvFile = new File(pFileName); // Create a new file

        try (PrintWriter writer = new PrintWriter(csvFile)) {

            // Write header to the file
            writer.println("teamName,grandPrix,carCode,driverName,position,fastestLap");

            // Loop for each object array and write data to the file
            for (int i = 0; i < pTeams.length; i++) {
                for (int j = 0; j < DRIVERS_NUM; j++) {
                    writer.write(
                            pTeams[i].getTeamName() + "," + pTeams[i].getGrandPrix() + "," + pTeams[i].getCarCode()
                                    + ","
                                    + pTeams[i].getDrivers()[j].getDriverName() + ","
                                    + pTeams[i].getDrivers()[j].getPosition() + ","
                                    + pTeams[i].getDrivers()[j].getFastestLap() + "\n");
                }
            }
            System.out.println("Successfully written to " + csvFile.getName() + " file");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Ask user for a file name to the csv file
     * 
     * @param pScanner
     * @return fileName
     */
    private static String getFileName(Scanner pScanner) {
        // Ask user for the file name
        System.out.println("=====");
        System.out.println("What would you like to name the csv file?");
        String fileName = validateStringInput("> Enter without the .csv: ", pScanner);

        fileName += ".csv"; // Add .csv to the file name
        return fileName;
    }

    /**
     * Check for non-integer input
     * 
     * @param pPrompt
     * @param pScanner
     * @return input
     */
    private static int validateIntInput(String pPrompt, Scanner pScanner) {
        int input;

        while (true) {
            System.out.print(pPrompt);

            // Check for non-integer input
            if (pScanner.hasNextInt()) {
                input = pScanner.nextInt();
                pScanner.nextLine(); // Skip to next line
                break;
            } else {
                System.out.println("Input must be a number. Please re-enter.");
                pScanner.next(); // Consume the invalid input
            }
        }
        return input;
    }

    /**
     * Check for empty string input
     * 
     * @param pPrompt
     * @param pScanner
     * @return input
     */
    public static String validateStringInput(String pPrompt, Scanner pScanner) {
        String input;

        do {
            System.out.print(pPrompt);
            input = pScanner.nextLine().trim();

            if (input == null || input.trim().isEmpty()) {
                System.out.println("Input cannot be blank. Re-enter below.");
            }
        } while (input == null || input.trim().isEmpty());

        return input.toLowerCase(); // Turn input to lower case for comparing string easier
    }

    /**
     * Check for a positive real input
     * 
     * @param pPrompt
     * @param pScanner
     * @return input
     */
    private static double validateDoubleInput(String pPrompt, Scanner pScanner) {
        double input = 0.0;

        while (true) {
            System.out.print(pPrompt);

            // Check if user enter a double or not
            if (pScanner.hasNextDouble()) {
                input = pScanner.nextDouble();
                // Check for non-negative double
                if (Double.compare(input, 0.000) > 0) {
                    pScanner.nextLine();// Skip to next line
                    break;
                } else {
                    System.out.println("Input must be a positive real number. Please re-enter.");
                }
            } else {
                System.out.println("Input must be a real number. Please re-enter.");
                pScanner.next(); // Consume the invalid input
            }
        }
        return input;
    }
}