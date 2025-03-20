/*
Author: Thanh Phu Phan (21664322)
Unit: Programming Desgin and Implementation
Date: 26/04/2024
Submitted date: 05/05/2024
Purpose: Setting Teams
*/

public class Team {
    /* ============== Class field ============== */
    private String teamName, carCode, grandPrix;
    private Driver[] drivers = new Driver[2];

    /* ============== Constructors ============== */

    // Parameters Constructor
    public Team(String pTeamName, String pCarCode, String pGrandPrix) {
        this.setTeamName(pTeamName);
        this.setGrandPrix(pGrandPrix);
        this.setCarCode(pCarCode);
        this.drivers = new Driver[2];
    }

    // Copy Constructor
    public Team(Team pTeam) {
        teamName = pTeam.getTeamName();
        carCode = pTeam.getCarCode();
        drivers = pTeam.getDrivers();
        grandPrix = pTeam.getGrandPrix();
    }

    /* ============== Accessors ============== */
    // Accessor: getTeamName
    public String getTeamName() {
        return this.teamName;
    }

    // Accessor: getCarCode
    public String getCarCode() {
        return this.carCode;
    }

    // Accessor: getDrivers
    public Driver[] getDrivers() {
        return this.drivers;
    }

    // Accessor: getGrandPrix
    public String getGrandPrix() {
        return this.grandPrix;
    }

    /* ============== Mutators ============== */
    // Mutators: setTeamName
    public void setTeamName(String pTeamName) {
        this.teamName = new String(pTeamName);
    }

    // Mutators: setCarCode
    public void setCarCode(String pCarCode) {
        this.carCode = new String(pCarCode);
    }

    // Mutators: setDrivers
    public void setDrivers(Driver[] pDrivers) {
        this.drivers = pDrivers;
    }

    // Mutators: setGrandPrix
    public void setGrandPrix(String pGrandPrix) {
        this.grandPrix = new String(pGrandPrix);
    }

    /* ============== Methods ============== */
    // Convert teams details to string to display for user
    @Override
    public String toString() {
        String teamDetails = "";
        teamDetails = "Team Name: " + getTeamName() + ", Grand Prix: " + getGrandPrix() + ", Car Code: " + getCarCode();
        return teamDetails;
    }

    // Display team details to user
    public void displayTeamDetails() {
        System.out.println();
        System.out.println("======= " + getTeamName().toUpperCase() + " =======");
        System.out.println("Grand Prix: " + getGrandPrix() + ", Car Code: " + getCarCode());

        for (int i = 0; i < 2; i++) {
            // System.out.println("-------");
            System.out.println("--- Driver " + (i + 1) + " ---");

            String driverDetails = drivers[i].toString();
            System.out.println(driverDetails);
        }
    }

    @Override
    public boolean equals(Object inObject) {
        boolean isEqual = false;
        Team inTeam = null;
        boolean duplicateDrivers = getDrivers().equals(drivers);

        if (inObject instanceof Team) {
            inTeam = (Team) inObject;

            if (teamName.equals(inTeam.getTeamName())) {
                if (grandPrix.equals(inTeam.getGrandPrix())) {
                    if (carCode.equals(inTeam.getCarCode())) {
                        if (duplicateDrivers == true) {
                            isEqual = true;
                        }
                    }
                }
            }
        }
        return isEqual;
    }

    public Driver[] sortFastestDriverDescending(Driver[] pDrivers) {
        // Move all dnf drivers to second position
        if (pDrivers[0].getPosition() == -1 && drivers[1].getPosition() != -1) {
            Driver temp = pDrivers[0];
            pDrivers[0] = pDrivers[1];
            pDrivers[1] = temp;
        }

        // Sorting in descending order
        if (pDrivers[0].getPosition() > pDrivers[1].getPosition() &&
                pDrivers[1].getPosition() > 0) {
            Driver temp = pDrivers[0];
            pDrivers[0] = pDrivers[1];
            pDrivers[1] = temp;
        }

        return pDrivers;
    }

    public Driver[] sortFastestDriverAscending(Driver[] pDrivers) {
        // Move all dnf drivers to first position
        if (pDrivers[1].getPosition() == -1 && pDrivers[0].getPosition() != -1) {
            Driver temp = pDrivers[0];
            pDrivers[0] = pDrivers[1];
            pDrivers[1] = temp;
        }

        // Sorting in ascending order
        if (pDrivers[0].getPosition() < pDrivers[1].getPosition() &&
                pDrivers[0].getPosition() > 0) {
            Driver temp = pDrivers[1];
            pDrivers[1] = pDrivers[0];
            pDrivers[0] = temp;
        }
        return pDrivers;
    }
}
