# CENGVAC Database Management System
 
This repository contains the implementation of a vaccination tracking service called CENGVAC, which is a database management system built using Java and MySQL. The system allows for the creation and manipulation of tables to track users, vaccines, side effects, and vaccination records. This project was implemented as an assignment for the CENG351 Data Management & File Structure course.

## Introduction

The CENGVAC database management system provides several tasks and functionalities, which are implemented through a well-defined interface. The main objectives of this project include connecting and querying a MySQL server using Java Database Connectivity (JDBC) and designing queries to accomplish specific tasks.

## Features
The implemented CENGVACDB class provides the following functionalities:

1. Creating the database tables: This task involves creating all the necessary tables based on the provided schema.

2. Inserting data into tables: Data from the provided files in `cengvacdb/data` folder can be inserted into the appropriate tables.

3. Finding vaccines that have not been applied to any user: Retrieves vaccines that have not been administered to any user.

4. Listing users who have been vaccinated for two doses since a given date: Displays users who have received two doses of a vaccine since a specified date.

5. Listing the two most recent vaccines applied to any user that do not contain `'vac'` in their name: Retrieves the two most recent vaccines administered to users that do not contain the keyword `'vac'` in their names.

6. Listing users who have had at least two doses of vaccine and have experienced at most one side effect: Displays users who have received at least two doses of a vaccine and have experienced at most one side effect.

7. Listing users who have been vaccinated with all vaccines that can cause a given side effect: Retrieves users who have been vaccinated with all vaccines that can potentially cause a specific side effect.

8. Listing users who have been vaccinated with at least two different types of vaccines within a given time interval: Displays users who have been vaccinated with at least two different types of vaccines within a specified time interval.

9. Listing side effects experienced by users who have received two doses of vaccine with less than 20 days between doses: Retrieves the side effects experienced by users who have received two doses of a vaccine with an interval of less than 20 days.

10. Calculating the average number of doses of vaccinated users over the age of 65: Calculates the average number of doses received by users over the age of 65.

11. Updating the status of users to `"eligible"` after 120 days have passed since their last vaccination: Updates the status of users to "eligible" if 120 days have passed since their last vaccination.

12. Deleting vaccine(s) from the database based on the given vaccine name: Deletes the specified vaccine(s) from the database, including related allergic side effects.

13. Dropping the database tables: Drops all the tables if they exist.

## Getting Started
To get started with the CENGVAC database management system, follow these steps:

- Clone the repository: 
```
git clone https://github.com/ramazantokay/CENGVAC-Database-Management-System.git
```
- Ensure you have Java (version 13) installed.
- Set up a MySQL server and create a database.
- Update the database connection details in the code. (in CENGVACDB.java file)
- Compile and run the program.

## Disclaimer
Please note that this implementation may contain limitations, potential bugs, and dependencies on external libraries and tools. While efforts have been made to ensure correctness, there is no guarantee of flawless execution. Exercise caution, conduct thorough testing, and adapt the code to your requirements. Report issues on GitHub and contribute to improvements. Use responsibly, validate results, and the authors disclaim liability for any damages or issues arising from the use of this code.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Contributing

Contributions are welcome! If you have any suggestions or improvements, feel free to submit a pull request or open an issue in the GitHub repository.
