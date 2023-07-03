package ceng.ceng351.cengvacdb;
import javax.print.attribute.SupportedValuesAttribute;
import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ceng.ceng351.cengvacdb.QueryResult.UserIDuserNameAddressResult;

public class CENGVACDB implements ICENGVACDB {

    private static String user = "###";
    private static String password = "###"; //  TODO: Your password
    private static String host = "###"; // host name
    private static String database = "###"; // TODO: Your database name
    private static int port = ###; // port

    private static Connection connection;

    @Override
    public int createTables() {
        int numberofCreatedTable = 0;
        int resultforUser, resultforVaccine, resultforVaccination, resultforAllergetic, resultforSeen;

        // User (userID:int, userName:varchar(30), age:int, address:varchar(150), password:varchar(30), status:varchar(15))
        String queryforCreatingUserTable = "create table User (" +
                "userID int not null," +
                "userName varchar(30)," +
                "age int," +
                "address varchar(150)," +
                "password varchar(30)," +
                "status varchar(15)," +
                "primary key (userID));";

        // Vaccine (code:int, vaccinename:varchar(30), type:varchar(30))
        String queryforCreatingVaccineTable = "create table Vaccine (" +
                "code int not null," +
                "vaccinename varchar(30)," +
                "type varchar(30)," +
                "primary key (code));";

        //Vaccination (code:int, userID:int, dose:int, vacdate:date) References Vaccine (code), User(userID)
        String queryforCreatingVaccinationTable = "create table Vaccination (" +
                "code int not null," +
                "userID int," +
                "dose int," +
                "vacdate date," +
                "primary key (code, userID, dose)," +
                "foreign key (code) references Vaccine(code)  on delete cascade on update cascade," +
                "foreign key (userID) references User(userID)  on delete cascade on update cascade);";

        //AllergicSideEffect (effectcode:int, effectname:varchar(50))
        String queryforCreatingAllergicSideEffectTable = "create table AllergicSideEffect (" +
                "effectcode int not null," +
                "effectname varchar(50)," +
                "primary key (effectcode));";

//      Seen (effectcode:int, code:int, userID:int, date:date, degree:varchar(30)) References AllergicSideEffect (effectcode), Vaccination (code), User (userID)

        String queryforCreatingSeenTable = "create table Seen (" +
                "effectcode int not null," +
                "code int," +
                "userID int," +
                "date date," +
                "degree varchar(30)," +
                "primary key (effectcode, code, userID)," +
                "foreign key (effectcode) references AllergicSideEffect(effectcode)  on delete cascade on update cascade, " +
                "foreign key (code) references Vaccination(code)  on delete cascade on update cascade," +
                "foreign key (userID) references User(userID) on delete cascade on update cascade);";

        try {
            Statement statement = this.connection.createStatement();

            resultforUser = statement.executeUpdate(queryforCreatingUserTable);
//            System.out.println(resultforUser);
            numberofCreatedTable++;
//            System.out.println("User Table is created");


            resultforVaccine = statement.executeUpdate(queryforCreatingVaccineTable);
//            System.out.println(resultforVaccine);
            numberofCreatedTable++;
//            System.out.println("Vaccine Table is created");

            resultforVaccination = statement.executeUpdate(queryforCreatingVaccinationTable);
//            System.out.println(resultforVaccination);
            numberofCreatedTable++;
//            System.out.println("Vaccination Table is created");

            resultforAllergetic = statement.executeUpdate(queryforCreatingAllergicSideEffectTable);
//            System.out.println(resultforAllergetic);
            numberofCreatedTable++;
//            System.out.println("Allergic Table is created");

            resultforSeen = statement.executeUpdate(queryforCreatingSeenTable);
//            System.out.println(resultforSeen);
            numberofCreatedTable++;
//            System.out.println("Seen Table is created");

            statement.close();

        } catch (SQLException e) {
            System.out.println("Error in createTables function");
            e.printStackTrace();
        }
//        System.out.println("Created " + numberofCreatedTable + " tables");
        return numberofCreatedTable;
    }

    @Override
    public void initialize() {
        String url = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, this.user, this.password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int dropTables() {


        int numberofDroppedTables = 0;
        int result;

        String dropSeenTableQuery = "DROP TABLE IF EXISTS Seen";

        try {
            Statement state = this.connection.createStatement();
            result = state.executeUpdate(dropSeenTableQuery);
            numberofDroppedTables++;
//            System.out.println(result);
            state.close();
//            System.out.println("Seen Table is dropped");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String dropVaccinationTableQuery = "DROP TABLE IF EXISTS Vaccination";

        try {
            Statement state = this.connection.createStatement();
            result = state.executeUpdate(dropVaccinationTableQuery);
            numberofDroppedTables++;
//            System.out.println(result);
            state.close();
//            System.out.println("Vaccination Table is dropped");


        } catch (SQLException e) {
            e.printStackTrace();
        }

        String dropVaccineTableQuery = "DROP TABLE IF EXISTS Vaccine";
        try {
            Statement state = this.connection.createStatement();
            result = state.executeUpdate(dropVaccineTableQuery);
            numberofDroppedTables++;
//            System.out.println(result);
            state.close();
//            System.out.println("Vaccine Table is dropped");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        String dropAllergicSideEffectTableQuery = "DROP TABLE IF EXISTS AllergicSideEffect";

        try {
            Statement state = this.connection.createStatement();
            result = state.executeUpdate(dropAllergicSideEffectTableQuery);
            numberofDroppedTables++;
//            System.out.println(result);
            state.close();
//            System.out.println("Allergic Table is dropped");

        } catch (SQLException e) {
            e.printStackTrace();
        }


        String dropUserTableQuery = "DROP TABLE IF EXISTS User";
        try {
            Statement state = this.connection.createStatement();
            result = state.executeUpdate(dropUserTableQuery);
            numberofDroppedTables++;
//            System.out.println(result);
            state.close();
//            System.out.println("User Table is dropped\n");

        } catch (SQLException e) {
            e.printStackTrace();
        }

//        System.out.println("Drop Table func is called");

//        System.out.println("Dropped " + numberofDroppedTables + " tables");
        return numberofDroppedTables;


    }

    @Override
    public int insertUser(User[] users) {
        int numberofInsertedUser = 0;
        int queryResult;
        for (int i = 0; i < users.length; i++) {
            String userQuery = "insert into User values ( \"" + users[i].getUserID() + "\", " +
                    "\"" + users[i].getUserName() + "\", " +
                    "\"" + users[i].getAge() + "\", " +
                    "\"" + users[i].getAddress() + "\", " +
                    "\"" + users[i].getPassword() +
                    "\", \"" + users[i].getStatus() + "\");";
            try {
                Statement statement = this.connection.createStatement();
                queryResult = statement.executeUpdate(userQuery);
//                System.out.println(i+". User Data");
                numberofInsertedUser++;
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
//        System.out.println(numberofInsertedUser + " users are inserted");
        return numberofInsertedUser;
    }

    @Override
    public int insertAllergicSideEffect(AllergicSideEffect[] sideEffects) {
        // AllergicSideEffect (effectcode:int, effectname:varchar(50))

        int numberofInsertedAllergicSideEffect = 0;
        int queryResult;
        for (int i = 0; i < sideEffects.length; i++) {
            String allergicQuery = "insert into AllergicSideEffect values ( \"" + sideEffects[i].getEffectCode() + "\", " +
                    "\"" + sideEffects[i].getEffectName() + "\");";
            try {
                Statement statement = this.connection.createStatement();
                queryResult = statement.executeUpdate(allergicQuery);
//                System.out.println(i + ". Allergic Data");
                numberofInsertedAllergicSideEffect++;
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
//        System.out.println(numberofInsertedAllergicSideEffect + " side effects are inserted");
        return numberofInsertedAllergicSideEffect;

    }

    @Override
    public int insertVaccine(Vaccine[] vaccines) {
        // Vaccine (code:int, vaccinename:varchar(30), type:varchar(30))
        int numberofInsertedVaccine = 0;
        int queryResult;
        for (int i = 0; i < vaccines.length; i++) {
            String VaccineQuery = "insert into Vaccine values ('" + vaccines[i].getCode() + "'," +
                    " '" + vaccines[i].getVaccineName() + "', " +
                    "'" + vaccines[i].getType() + "');";
            try {
                Statement statement = this.connection.createStatement();
                queryResult = statement.executeUpdate(VaccineQuery);
//                System.out.println(i + ". Vaccine Data");
                numberofInsertedVaccine++;
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
//        System.out.println(numberofInsertedVaccine + " vaccines are inserted");
        return numberofInsertedVaccine;
    }

    @Override
    public int insertVaccination(Vaccination[] vaccinations) {
        // Vaccination (code:int, userID:int, dose:int, vacdate:date) References Vaccine (code), User(userID)

        int numberofInsertedVaccination = 0;
        int queryResult;
        for (int i = 0; i < vaccinations.length; i++) {
            String VaccinationQuery = "insert into Vaccination values ('" + vaccinations[i].getCode() + "'," +
                    " '" + vaccinations[i].getUserID() + "', " +
                    "'" + vaccinations[i].getDose() + "', " +
                    "'" + vaccinations[i].getVacdate() + "' );";
            try {
                Statement statement = this.connection.createStatement();
                queryResult = statement.executeUpdate(VaccinationQuery);
//                System.out.println(i + ". Vaccination Data");
                numberofInsertedVaccination++;
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
//        System.out.println(numberofInsertedVaccination + " vaccinations are inserted");
        return numberofInsertedVaccination;
    }

    @Override
    public int insertSeen(Seen[] seens) {
        // Seen (effectcode:int, code:int, userID:int, date:date, degree:varchar(30)) References AllergicSideEffect (effectcode), Vaccination (code), User (userID)
        int numberofInsertedSeen = 0;
        int queryResult;

        for (int i = 0; i < seens.length; i++) {
            String SeenQuery = "insert into Seen values ('" + seens[i].getEffectcode() + "', " +
                    "'" + seens[i].getCode() + "', " +
                    "'" + seens[i].getUserID() + "', " +
                    "'" + seens[i].getDate() + "', " +
                    "'" + seens[i].getDegree() + "' );";

            try {
                Statement statement = this.connection.createStatement();
                queryResult = statement.executeUpdate(SeenQuery);
//                System.out.println(i + ". Seen Data");
                numberofInsertedSeen++;
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
//        System.out.println(numberofInsertedSeen + " seens are inserted");
        return numberofInsertedSeen;
    }

    @Override
    public Vaccine[] getVaccinesNotAppliedAnyUser() {
        ResultSet resultSet;

        List<Vaccine> vacList = new ArrayList<Vaccine>();
        String queryForgetVaccinesNotAppliedAnyUser = " select DISTINCT * from Vaccine Va where Va.code not in (select DISTINCT V.code from Vaccination V where V.code) ORDER BY Va.code;";
        try {
            Statement statement = this.connection.createStatement();
            resultSet = statement.executeQuery(queryForgetVaccinesNotAppliedAnyUser);

            while (resultSet.next()) {
                Vaccine vaccinesNotAppliedAnyUser = null;
                int code = resultSet.getInt("code");
                String vacname = resultSet.getString("vaccinename");
                String type = resultSet.getString("type");

                vaccinesNotAppliedAnyUser = new Vaccine(code, vacname, type);
                vacList.add(vaccinesNotAppliedAnyUser);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Vaccine[] vaccinesNotAppliedAnyUser = new Vaccine[vacList.size()];
        vaccinesNotAppliedAnyUser = vacList.toArray(vaccinesNotAppliedAnyUser);

//        System.out.println(Arrays.toString(vaccinesNotAppliedAnyUser));
        return vaccinesNotAppliedAnyUser;
    }

    @Override
    public QueryResult.UserIDuserNameAddressResult[] getVaccinatedUsersforTwoDosesByDate(String vacdate) {

        ResultSet resultSet;

        List<UserIDuserNameAddressResult> twoDosebyDate = new ArrayList<UserIDuserNameAddressResult>();

        String queryForgetVaccinatedUsersforTwoDosesByDate = "select U.userID, U.userName, U.address from User U where U.userID IN" +
                "(select DISTINCT V.userID from Vaccination V, Vaccination V2 where V.dose = 1 and V2.dose = 2 and V.userID = V2.userID and V.vacdate>= \"" + vacdate + "\" and V2.vacdate>= \"" + vacdate + "\" UNION " +
                "select DISTINCT V.userID from Vaccination V, Vaccination V2 where V.dose = 2 and V2.dose = 3 and V.userID = V2.userID and V.vacdate>=\"" + vacdate + "\" and V2.vacdate >= \"" + vacdate + "\" ) ORDER BY U.userID;";

        try {
            Statement statement = this.connection.createStatement();
            resultSet = statement.executeQuery(queryForgetVaccinatedUsersforTwoDosesByDate);

            while (resultSet.next()) {
                UserIDuserNameAddressResult userArr = null;

                int userID = resultSet.getInt("userID");
                String userName = resultSet.getString("userName");
                String address = resultSet.getString("address");

                userArr = new UserIDuserNameAddressResult(Integer.toString(userID), userName, address);
                twoDosebyDate.add(userArr);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        UserIDuserNameAddressResult[] userIDuserNameArr = new UserIDuserNameAddressResult[twoDosebyDate.size()];
        userIDuserNameArr = twoDosebyDate.toArray(userIDuserNameArr);
//        System.out.println(Arrays.toString(userIDuserNameArr));
        return userIDuserNameArr;
    }

    @Override
    public Vaccine[] getTwoRecentVaccinesDoNotContainVac() {

        ResultSet resultSet;
        List<Vaccine> vacListDoNotContainVac = new ArrayList<Vaccine>();
        String queryForgetTwoRecentVaccinesDoNotContainVac = "select * from(select newTable.code, newTable.vaccinename, newTable.type from (select V.code, V.vaccinename, V.type, Va.vacdate from Vaccine V, Vaccination Va where V.code = Va.code and V.vaccinename not like \"%vac%\" order by Va.vacdate desc) newTable group by newTable.code order by MAX(newTable.vacdate)  desc limit 2) neww order by neww.code asc;";

        try {
            Statement statement = this.connection.createStatement();
            resultSet = statement.executeQuery(queryForgetTwoRecentVaccinesDoNotContainVac);

            while (resultSet.next()) {
                Vaccine vaccinesNotContainVac = null;
                int code = resultSet.getInt("code");
                String vacname = resultSet.getString("vaccinename");
                String type = resultSet.getString("type");
                vaccinesNotContainVac = new Vaccine(code, vacname, type);
                vacListDoNotContainVac.add(vaccinesNotContainVac);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Vaccine[] vaccinesThatNotContainVacKeyword = new Vaccine[vacListDoNotContainVac.size()];
        vaccinesThatNotContainVacKeyword = vacListDoNotContainVac.toArray(vaccinesThatNotContainVacKeyword);
//        System.out.println(Arrays.toString(vaccinesThatNotContainVacKeyword));
        return vaccinesThatNotContainVacKeyword;
    }

    @Override
    public QueryResult.UserIDuserNameAddressResult[] getUsersAtHasLeastTwoDoseAtMostOneSideEffect() {

        ResultSet resultSet;

        List<UserIDuserNameAddressResult> userHasatLeastTwoDose = new ArrayList<UserIDuserNameAddressResult>();
        String queryForgetUsersAtHasLeastTwoDoseAtMostOneSideEffect = "select U.userID, U.userName, U.address from User U where U.userID IN (select * from(select * from (select  V.userID from Vaccination V group by V.userID having COUNT(V.userID) >= 2) newtable  where newtable.userID IN  (select S.userID from Seen S group by S.userID having COUNT(S.userID) <= 1)) tableT UNION select * from(select * from (select  V.userID from Vaccination V group by V.userID having COUNT(V.userID) >= 2) newtable  where newtable.userID NOT IN  (select S.userID from Seen S group by S.userID having COUNT(S.userID) > 1)) tableW order by userID asc);";

        try {
            Statement statement = this.connection.createStatement();
            resultSet = statement.executeQuery(queryForgetUsersAtHasLeastTwoDoseAtMostOneSideEffect);

            while (resultSet.next()) {
                UserIDuserNameAddressResult userArr = null;
                int userID = resultSet.getInt("userID");
                String userName = resultSet.getString("userName");
                String address = resultSet.getString("address");

                userArr = new UserIDuserNameAddressResult(Integer.toString(userID), userName, address);
                userHasatLeastTwoDose.add(userArr);

            }
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        UserIDuserNameAddressResult[] userIDuserNameArr = new UserIDuserNameAddressResult[userHasatLeastTwoDose.size()];
        userIDuserNameArr = userHasatLeastTwoDose.toArray(userIDuserNameArr);
//        System.out.println(Arrays.toString(userIDuserNameArr));
        return userIDuserNameArr;
    }

    @Override
    public QueryResult.UserIDuserNameAddressResult[] getVaccinatedUsersWithAllVaccinesCanCauseGivenSideEffect(String effectname) {

        ResultSet resultSet;
        List<QueryResult.UserIDuserNameAddressResult> getVaccinatedUsers = new ArrayList<QueryResult.UserIDuserNameAddressResult>();
        String queryForgetVaccinatedUsersWithAllVaccinesCanCauseGivenSideEffect = "select U.userID, U.userName, U.address from User U where U.userID IN (select V.userID from Vaccination V where V.code IN (select S.code from Seen S where S.effectcode IN (select ASE.effectcode from AllergicSideEffect ASE where ASE.effectname = \"" + effectname + "\"))) order by U.userID asc;";

        try {
            Statement statement = this.connection.createStatement();
            resultSet = statement.executeQuery(queryForgetVaccinatedUsersWithAllVaccinesCanCauseGivenSideEffect);

            while (resultSet.next()) {

                UserIDuserNameAddressResult userArr = null;
                int userID = resultSet.getInt("userID");
                String userName = resultSet.getString("userName");
                String address = resultSet.getString("address");

                userArr = new UserIDuserNameAddressResult(Integer.toString(userID), userName, address);
                getVaccinatedUsers.add(userArr);

            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        UserIDuserNameAddressResult[] userIDuserNameArr = new UserIDuserNameAddressResult[getVaccinatedUsers.size()];
        userIDuserNameArr = getVaccinatedUsers.toArray(userIDuserNameArr);

//        System.out.println(Arrays.toString(userIDuserNameArr));
        return userIDuserNameArr;
    }

    @Override
    public QueryResult.UserIDuserNameAddressResult[] getUsersWithAtLeastTwoDifferentVaccineTypeByGivenInterval(String startdate, String enddate) {

        ResultSet resultSet;
        List<QueryResult.UserIDuserNameAddressResult> usersWithAtleastTwoDiffVaccine = new ArrayList<UserIDuserNameAddressResult>();

        String queryForgetUsersWithAtLeastTwoDifferentVaccineTypeByGivenInterval = "select U.userID, U.userName, U.address from User U where U.userID IN (select DISTINCT V1.userID from Vaccination V1, Vaccination V2 where V1.userID = V2.userID and V1.code <> V2.code and V1.vacdate >= \"" + startdate + "\" and V2.vacdate <= \"" + enddate + "\") order by U.userID asc;";

        try {
            Statement statement = this.connection.createStatement();
            resultSet = statement.executeQuery(queryForgetUsersWithAtLeastTwoDifferentVaccineTypeByGivenInterval);

            while (resultSet.next()) {
                UserIDuserNameAddressResult userArr = null;
                int userID = resultSet.getInt("userID");
                String userName = resultSet.getString("userName");
                String address = resultSet.getString("address");

                userArr = new UserIDuserNameAddressResult(Integer.toString(userID), userName, address);
                usersWithAtleastTwoDiffVaccine.add(userArr);

            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        UserIDuserNameAddressResult[] userIDUserNameArr = new UserIDuserNameAddressResult[usersWithAtleastTwoDiffVaccine.size()];
        userIDUserNameArr = usersWithAtleastTwoDiffVaccine.toArray(userIDUserNameArr);
//        System.out.println(Arrays.toString(userIDUserNameArr));
        return userIDUserNameArr;
    }

    @Override
    public AllergicSideEffect[] getSideEffectsOfUserWhoHaveTwoDosesInLessThanTwentyDays() {

        ResultSet resultSet;
        List<AllergicSideEffect> sideEffects = new ArrayList<AllergicSideEffect>();
        String queryForgetSideEffectsOfUserWhoHaveTwoDosesInLessThanTwentyDays = "select ASE.effectcode, ASE.effectname from AllergicSideEffect ASE where ASE.effectcode IN (select S.effectcode from Seen S where S.userID IN (select V1.userID from Vaccination V1, Vaccination V2 where V1.userID = V2.userID and V1.dose = 1 and V2.dose = 2 and DATEDIFF(V2.vacdate, V1.vacdate) <20 order by V1.userID)) order by ASE.effectcode;";

        try {
            Statement statement = this.connection.createStatement();
            resultSet = statement.executeQuery(queryForgetSideEffectsOfUserWhoHaveTwoDosesInLessThanTwentyDays);

            while (resultSet.next()) {
                AllergicSideEffect ase = null;
                int effectcode = resultSet.getInt("effectcode");
                String effectname = resultSet.getString("effectname");

                ase = new AllergicSideEffect(effectcode, effectname);
                sideEffects.add(ase);
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        AllergicSideEffect[] sideEffects1 = new AllergicSideEffect[sideEffects.size()];
        sideEffects1 = sideEffects.toArray(sideEffects1);
//        System.out.println(Arrays.toString(sideEffects1));

        return sideEffects1;
    }

    @Override
    public double averageNumberofDosesofVaccinatedUserOverSixtyFiveYearsOld() {

        ResultSet resultSet;
        String avgQuery = "select AVG(Dosed) as AvgDose from (select V2.userID as UID, COUNT(V2.userID) AS Dosed from Vaccination V2 where V2.userID IN (select DISTINCT U.userID from User U where U.age > 65 and U.userID IN (select DISTINCT V.userID from Vaccination V)) GROUP BY V2.userID) T;";
        double result = 0.0;

        try {
            Statement statement = this.connection.createStatement();
            resultSet = statement.executeQuery(avgQuery);
            resultSet.next();

            result = resultSet.getDouble("AvgDose");
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println("Average " + result);
        return result;
    }

    @Override
    public int updateStatusToEligible(String givendate) {

        int updatedRow = 0;
        String updateStatusQuery = "update User set User.status = \"Eligible\" where User.userID IN (select newtable.newUID from (select V.userID as newUID, V.vacdate as LastVacDate from Vaccination V where V.vacdate = (select MAX(V2.vacdate) from Vaccination V2 where V2.userID = V.userID) ORDER BY V.userID) newtable where DATEDIFF(\"" + givendate + "\", newtable.LastVacDate)>=120) and User.status <> \"Eligible\";";

        try {
            Statement statement = this.connection.createStatement();
            updatedRow = statement.executeUpdate(updateStatusQuery);
//            updatedRow = statement.getUpdateCount();
//            System.out.println("Updated Row " + updatedRow);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return updatedRow;
    }

    @Override
    public Vaccine deleteVaccine(String vaccineName) {

        ResultSet resultSet;
        Vaccine deletedVaccine = null;
        String deleteVaccineQuery = "delete from Vaccine V where V.vaccinename= '" + vaccineName + "'";

        try {
            Statement statement = this.connection.createStatement();
            String getDeletedVaccineQuery = "select * from Vaccine V where V.vaccinename='" + vaccineName + "'";
            resultSet = statement.executeQuery(getDeletedVaccineQuery);
            resultSet.next();
//            Vaccine (code:int, vaccinename:varchar(30), type:varchar(30))
            int code = resultSet.getInt("code");
            String vaccinename = resultSet.getString("vaccinename");
            String type = resultSet.getString("type");

            deletedVaccine = new Vaccine(code, vaccinename, type);
//            System.out.println(deletedVaccine.toString());
            statement.executeUpdate(deleteVaccineQuery);


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deletedVaccine;


    }
}
