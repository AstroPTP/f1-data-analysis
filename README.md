# FIA F1 Analysis Program

## Overview

This project is a **FIA Formula 1 Analysis Program** designed to allow users to enter, store, and analyze race data, focusing on teams and drivers' performance. It consists of multiple Java files responsible for handling data entry, analysis, and reporting.

## Features

-   **Data Entry Program**: Users can input teams, drivers, and race results interactively.
-   **Data Analysis**: Perform analysis on individual teams or all teams.
-   **Sorting and Filtering**: Identify the fastest teams and drivers.
-   **CSV File Handling**: Store and read race data from CSV files.

## Files and Their Responsibilities

### 1. `DataEntryProgram.java`

-   Handles user input for entering Formula 1 team and driver data.
-   Stores data in a structured format.
-   Saves the data as a `.csv` file for further analysis.

### 2. `DataAnalysisProgram.java`

-   Reads the stored `.csv` file.
-   Provides analysis options for users.
-   Displays results such as team rankings, fastest lap times, and completion statistics.

### 3. `Team.java`

-   Represents a Formula 1 team.
-   Stores team name, car code, and Grand Prix details.
-   Manages a list of `Driver` objects.

### 4. `Driver.java`

-   Represents an F1 driver.
-   Stores driver name, position, and fastest lap time.

### 5. `TeamAnalysis.java`

-   Extends `Team.java` to perform calculations such as total time per team.
-   Identifies whether a team has completed the race.
-   Provides sorting and ranking functionalities.

## How to Run

1. **Compile all Java files**:
    ```sh
    javac *.java
    ```
2. **Run the Data Entry Program**:
    ```sh
    java DataEntryProgram
    ```
    - Enter team and driver details.
    - Save data to a CSV file.
3. **Run the Data Analysis Program**:
    ```sh
    java DataAnalysisProgram
    ```
    - Load the saved CSV file.
    - Choose analysis options.
    - View rankings and race statistics.

## Dependencies

-   Java 8 or later.

## Notes

-   Ensure that the CSV file format remains consistent.
-   Input validation is enforced to prevent incorrect data entries.

## Author

-   **Thanh Phu Phan** (21664322)

## !! WARNING !!

This project is orginally submitted to **Programming Design and Implementation** unit as an assignment at _Curtin University_. The use of this project must not be copied and re-submitted.
