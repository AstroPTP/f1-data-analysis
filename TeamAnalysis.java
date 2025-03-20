/*
Author: Thanh Phu Phan (21664322)
Unit: Programming Desgin and Implementation
Date: 26/04/2024
Submitted date: 05/05/2024
Purpose: Analysis team performance
*/

public class TeamAnalysis extends Team {
    static final int DRIVERS_NUM = 2; // Constant variable for 2 drivers each team

    /* ============== Class fields ============== */
    private double totalTime;

    /* ============== Constructors ============== */
    // Parameters Constructor
    public TeamAnalysis(String pTeamName, String pGrandPrix, String pCarCode) {
        super(pTeamName, pCarCode, pGrandPrix);
    }

    /* ============== Mutators ============== */
    // Mutator: setTotalTime
    public void setTotalTime() {
        double totalTime = 0.0;
        for (int i = 0; i < DRIVERS_NUM; i++) {
            totalTime += getDrivers()[i].getFastestLap();
        }
        this.totalTime = totalTime;
    }

    /* ============== Accessors ============== */
    // Accessor: getTotalTime
    public double getTotalTime() {
        return this.totalTime;
    }

    /* ============== Methods ============== */
    /**
     * Display teams with at least one driver complete the race
     */
    public void completeRace() {
        if (getDrivers()[0].getPosition() >= 1 || getDrivers()[1].getPosition() >= 1) {
            System.out.println();
            System.out.println("======= " + getTeamName().toUpperCase() + " =======");
            System.out.println("Grand Prix: " + getGrandPrix() + ", Car Code: " + getCarCode());
            System.out.println("--- Driver ---");
        }

        for (int i = 0; i < DRIVERS_NUM; i++) {
            if (getDrivers()[i].getPosition() >= 1) {
                System.out
                        .println("Name: " + getDrivers()[i].getDriverName() + ", Position: "
                                + getDrivers()[i].getPosition());
            }
        }
    }

    /**
     * Display teams with no driver complete the race
     */
    public void notCompleteRace() {
        if (getDrivers()[0].getPosition() < 0 && getDrivers()[1].getPosition() < 0) {
            System.out.println();
            System.out.println("======= " + getTeamName().toUpperCase() + " =======");
            System.out.println("Grand Prix: " + getGrandPrix() + ", Car Code: " + getCarCode());
            System.out.println("--- Driver ---");

            for (int i = 0; i < DRIVERS_NUM; i++) {
                System.out
                        .println("Name: " + getDrivers()[i].getDriverName() + ", Position: "
                                + getDrivers()[i].getPosition());
            }
        }
    }
}