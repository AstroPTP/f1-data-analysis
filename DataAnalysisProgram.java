import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class DataAnalysisProgram {
    static final int DRIVERS_NUM = 2; // Constant variable for 2 drivers each team

    public static void main(String[] args) {
        // Declare variables
        String fileName = "";
        char userOption;
        boolean exitProgram = false;
        boolean fileExists = false;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the FIA F1 Analysis Program");
        System.out.println("=============================");

        /* ======== Asking user for the csv file name ======== */
        while (!fileExists) {// Loop for user re-enter if the file not exist
            // Ask user for a file name
            fileName = DataEntryProgram.validateStringInput(
                    "> Enter the file name containing the data without the .csv: ",
                    scanner);
            fileName += ".csv"; // Add .csv to the file name

            File file = new File(fileName);

            // If the file exists, and it's not a directory
            if (file.exists() && !file.isDirectory()) {
                fileExists = true;
            } else {
                System.out.println("File " + fileName + " does not exist. Please try again.");
            }
        }

        /* ======== Read data from CSV file ======== */
        TeamAnalysis[] teamArray = readDataFromCSVFile(fileName);

        /* ======== Give user analysis options ======== */
        askForAnalysisOption(scanner, teamArray);

        /* ======== Ask if user want to exit or not ======== */
        while (!exitProgram) {
            System.out.println("=========");
            System.out.println("Would you like to exit? (Y)es/(N)o");
            System.out.print("> Enter: ");
            userOption = scanner.next().charAt(0);

            if (userOption == 'Y' || userOption == 'y') {
                System.out.println("Thank you and Good Bye!!");
                exitProgram = true;
            } else if (userOption == 'N' || userOption == 'n') {
                // Give analysis option again if user enter no
                askForAnalysisOption(scanner, teamArray);
            } else {
                System.out.println("Invalid input. Please choose yes or no.");
            }
        }

        scanner.close();
    }

    /**
     * Read the data from csv file then store into object array
     * 
     * @param pFileName
     * @return teamArray
     */
    private static TeamAnalysis[] readDataFromCSVFile(String pFileName) {
        TeamAnalysis[] teamArray = null;

        try (BufferedReader br = new BufferedReader(new FileReader(pFileName))) {
            br.readLine(); // Read header line
            String line;
            int index = 0, j = 0; // Initialized variables

            // Count for lines in csv file
            int driversCount = lineCounter(pFileName);

            /*
             * In the csv file, each 2 lines will be a team with 2 drivers
             * => number of teams will be equal to number of drivers / 2
             */
            teamArray = new TeamAnalysis[driversCount / 2];

            // Loop until the end of the file
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                // Read team data
                String teamName = data[0];
                String grandPrix = data[1];
                String carCode = data[2];

                // Read driver data
                String driverName = data[3];
                int position = Integer.parseInt(data[4]);
                double fastestLap = Double.parseDouble(data[5]);

                /*
                 * If the first team name = second team name
                 * => store the second driver of the team
                 */
                if (index > 0 && teamArray[index - 1].getTeamName().equals(teamName) && j < 1) {
                    j++;
                    teamArray[index - 1].getDrivers()[j] = new Driver(driverName, position, fastestLap);
                } else {
                    j = 0;
                    // Store data of first team and first driver
                    teamArray[index] = new TeamAnalysis(teamName, grandPrix, carCode);
                    teamArray[index].getDrivers()[j] = new Driver(driverName, position, fastestLap);

                    // Check index to make sure that array doesn't go out of bound
                    if (index < driversCount) {
                        index++;
                    }
                }
            }

            // Set the total time of each team
            for (int i = 0; i < teamArray.length; i++) {
                teamArray[i].setTotalTime();
            }

            br.close();
        } catch (IOException e) {
            System.out.println("Error in reading file");
        }

        return teamArray;
    }

    /**
     * Count for the line in the csv file
     * 
     * @param pFileName
     * @return lintCount
     */
    private static int lineCounter(String pFileName) {
        int lineCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(pFileName))) {
            br.readLine(); // Read header line
            String line = br.readLine();

            while (line != null) {
                lineCount++;
                line = br.readLine();
            }
        } catch (Exception e) {
            System.out.println("Error");
        }
        return lineCount;
    }

    /**
     * Ask user to choose which type of analysis they want
     * 
     * @param pScanner
     * @param pTeams
     */
    private static void askForAnalysisOption(Scanner pScanner, TeamAnalysis[] pTeams) {
        char userOption;

        while (true) {
            System.out.println("-----");
            System.out.println("An All Teams analysis or a Single Team analysis");
            System.out.println("(A) for All team");
            System.out.println("(S) for Single team");
            System.out.print("> Enter:");
            userOption = pScanner.next().charAt(0);
            pScanner.nextLine();

            if (userOption == 'A' || userOption == 'a') {
                /* ======== Perform ALL TEAM ANALYSIS ======== */
                System.out.println("==========");
                System.out.println("ALL TEAM ANALYSIS");
                System.out.println("\n===== TEAM WITH AT LEAST ONE DRIVER COMPLETING THE RACE =====");

                for (int i = 0; i < pTeams.length; i++) {
                    pTeams[i].completeRace();
                }

                System.out.println("\n===== TEAM WITH NO DRIVER COMPLETING THE RACE =====");

                for (int i = 0; i < pTeams.length; i++) {
                    pTeams[i].notCompleteRace();
                }

                /* ======== Sorting fastest team ======== */
                System.out.println("\n===== SORTED FASTEST TIME OF EACH TEAM IN DESCENDING ORDER =====\n");

                sortingFastestTeam(pTeams);

                // Print out to user
                for (int i = 0; i < pTeams.length; i++) {
                    System.out.println("No." + (i + 1));
                    System.out.printf("team name: %s, grand prix: %s, total time: %.3f (s)\n",
                            pTeams[i].getTeamName().toUpperCase(),
                            pTeams[i].getGrandPrix().toUpperCase(), pTeams[i].getTotalTime());
                }

                /* ======== Sorting fastest drivers in descending order ======== */
                System.out.println("\n===== SORTED FASTEST DRIVERS IN DESCENDING ORDER =====");

                for (int i = 0; i < pTeams.length; i++) {
                    System.out.printf("\n--- %s ---\n", pTeams[i].getTeamName().toUpperCase());
                    System.out.println("Grand prix: " + pTeams[i].getGrandPrix());

                    for (int j = 0; j < DRIVERS_NUM; j++) {
                        // Sorting driver fastest lap in descending order
                        Driver[] sortedDriverDescending = pTeams[i].sortFastestDriverDescending(pTeams[i].getDrivers());

                        // Create a new temp team array to sorting without affecting original team array
                        TeamAnalysis[] sortedDriverTeam = new TeamAnalysis[pTeams.length];

                        // Copy all objects from original teams to temp array for sorting
                        sortedDriverTeam[i] = pTeams[i];

                        // Copy the drivers from sorted drivers array
                        sortedDriverTeam[i].getDrivers()[j] = sortedDriverDescending[j];

                        // Print out to user
                        System.out.print((j + 1) + "/ ");
                        System.out.printf("driver name: %s, position: %d, fastest lap: %.3f (s)\n",
                                sortedDriverTeam[i].getDrivers()[j].getDriverName().toUpperCase(),
                                sortedDriverTeam[i].getDrivers()[j].getPosition(),
                                sortedDriverTeam[i].getDrivers()[j].getFastestLap());
                    }
                }

                /* ======== Sorting fastest drivers in ascending order ======== */
                System.out.println("\n===== SORTED FASTEST DRIVERS IN ASCENDING ORDER =====");

                for (int i = 0; i < pTeams.length; i++) {
                    System.out.printf("\n--- %s ---\n", pTeams[i].getTeamName().toUpperCase());
                    System.out.println("Grand prix: " + pTeams[i].getGrandPrix());

                    for (int j = 0; j < DRIVERS_NUM; j++) {
                        // Sorting driver fastest lap in descending order
                        Driver[] sortedDriverAscending = pTeams[i].sortFastestDriverAscending(pTeams[i].getDrivers());

                        // Create a new temp team array to sorting without affecting original team array
                        TeamAnalysis[] sortedDriverTeam = new TeamAnalysis[pTeams.length];

                        // Copy all objects from original teams to temp array for sorting
                        sortedDriverTeam[i] = pTeams[i];

                        // Copy the drivers from sorted drivers array
                        sortedDriverTeam[i].getDrivers()[j] = sortedDriverAscending[j];

                        // Print out to user
                        System.out.print((j + 1) + "/ ");
                        System.out.printf("driver name: %s, position: %d, fastest lap: %.3f (s)\n",
                                sortedDriverTeam[i].getDrivers()[j].getDriverName().toUpperCase(),
                                sortedDriverTeam[i].getDrivers()[j].getPosition(),
                                sortedDriverTeam[i].getDrivers()[j].getFastestLap());
                    }
                }

                break;
            } else if (userOption == 'S' || userOption == 's') {
                /* ======== Perform SINGLE TEAM ANALYSIS ======== */
                String teamRequest = "";
                boolean found = false;

                System.out.println("==========");
                System.out.println("SINGLE TEAM ANALYSIS");

                // Ask user which team they want do to do analyse and loop back until the car
                // code is found
                do {
                    teamRequest = DataEntryProgram.validateStringInput("> Enter car code of team you want to analyse: ",
                            pScanner);

                    for (int i = 0; i < pTeams.length; i++) {
                        // Find the car code that user input in the csv file
                        if (pTeams[i].getCarCode().equals(teamRequest)) {
                            found = true;
                            break;
                        }
                    }

                    // If the car code is not found in the file print out a message to user
                    if (!found) {
                        System.out.println("Car code: " + teamRequest
                                + " does not exist in the file. Please try again.");
                    }
                } while (!found);

                // Create a new temp team array to sorting without affecting original team array
                TeamAnalysis[] sortedDriverTeam = new TeamAnalysis[pTeams.length];

                for (int i = 0; i < pTeams.length; i++) {
                    for (int j = 0; j < DRIVERS_NUM; j++) {
                        // Sorting driver fastest lap in descending order
                        Driver[] sortedDriverDescending = pTeams[i].sortFastestDriverDescending(pTeams[i].getDrivers());

                        // Copy all objects from original teams to temp array for sorting
                        sortedDriverTeam[i] = pTeams[i];

                        // Copy the drivers from sorted drivers array
                        sortedDriverTeam[i].getDrivers()[j] = sortedDriverDescending[j];
                    }
                }

                // Find all the indexes that match the request
                int matchIndexes[] = findMatchIndex(sortedDriverTeam, teamRequest);

                System.out.println("-----");
                System.out.println("You have searched for: " + teamRequest);
                System.out.println("There are " + matchIndexes.length + " results");

                // Display to user
                for (int i = 0; i < matchIndexes.length; i++) {
                    if (matchIndexes[i] != -1) {
                        sortedDriverTeam[matchIndexes[i]].displayTeamDetails();
                    }
                }
                break;
            } else {
                System.out.println("Invalid input. Please re-enter.");
            }
        }
    }

    /**
     * Sorting fastest team using bubble sort
     * 
     * @param pTeams
     */
    private static void sortingFastestTeam(TeamAnalysis[] pTeams) {
        for (int i = 0; i < pTeams.length - 1; i++) {
            for (int j = 0; j < (pTeams.length - i) - 1; j++) {

                if (pTeams[j].getTotalTime() > pTeams[j + 1].getTotalTime()) {
                    TeamAnalysis temp = pTeams[j];
                    pTeams[j] = pTeams[j + 1];
                    pTeams[j + 1] = temp;
                }
            }
        }
    }

    /**
     * Find all the duplicate values in the array and return the index of it
     * 
     * @param pTeams
     * @param pSearchTarget
     * @return matchIndexes
     */
    private static int[] findMatchIndex(TeamAnalysis[] pTeams, String pSearchTarget) {
        int count = 0;
        // Loop through the team array and count for duplicate values
        for (int i = 0; i < pTeams.length; i++) {
            if (pTeams[i].getCarCode().equals(pSearchTarget)) {
                count++;
            }
        }

        // Create an array to store for duplicate team array index
        int[] matchIndexes = new int[count];

        int index = 0;
        // Loop through the team array and find the duplicate index value
        for (int i = 0; i < pTeams.length; i++) {
            if (pTeams[i].getCarCode().equals(pSearchTarget)) {
                matchIndexes[index++] = i;
            }
        }
        return matchIndexes;
    }
}
