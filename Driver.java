/*
Author: Thanh Phu Phan (21664322)
Unit: Programming Desgin and Implementation
Date: 26/04/2024
Submitted date: 05/05/2024
Purpose: Get F1 Drivers details
*/

public class Driver {
    /* ============== Class field ============== */
    private String driverName;
    private int position;
    private double fastestLap;

    /* ============== Constructors ============== */

    // Parameters Constructor
    public Driver(String pDriverName, int pPosition, double pFastestLap) {
        this.setDriverName(pDriverName);
        this.setPosition(pPosition);
        this.setFastestLap(pFastestLap);
    }

    /* ============== Accessors ============== */
    // Accessor: getName
    public String getDriverName() {
        return this.driverName;
    }

    // Accessor: getPosition
    public int getPosition() {
        return this.position;
    }

    // Accessor: getFastestLap
    public double getFastestLap() {
        return this.fastestLap;
    }

    /* ============== Mutators ============== */
    // Mutator: setDriverName
    public void setDriverName(String pDriverName) {
        this.driverName = new String(pDriverName);
    }

    // Mutator: setPosition
    public void setPosition(int pPosition) {
        this.position = pPosition;
    }

    // Mutator: setFastestLap
    public void setFastestLap(double pFastestLap) {
        this.fastestLap = pFastestLap;
    }

    /* ============== Methods ============== */
    // Convert drivers details to string to display for user
    @Override
    public String toString() {
        String driverDetails = "";
        driverDetails = "Name: " + getDriverName() + ", Position: " + getPosition() + ", Fastest Lap: "
                + getFastestLap();
        return driverDetails;
    }

    // Check for duplicate drivers
    @Override
    public boolean equals(Object inObject) {
        boolean isEqual = false;
        Driver inDriver = null;

        if (inObject instanceof Driver) {
            inDriver = (Driver) inObject;

            if (driverName.equals(inDriver.getDriverName())) {
                if (position == inDriver.getPosition()) {
                    if (fastestLap == inDriver.getFastestLap()) {
                        isEqual = true;
                    }
                }
            }
        }
        return isEqual;
    }
}