package ceng.ceng351.cengvacdb;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Vaccine {
    
    private int code;
    private String vaccinename;
    private String type;

    public Vaccine(int code, String name, String type) {
        this.code = code;
        this.vaccinename = name;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getVaccineName() {
        return vaccinename;
    }

    public void setVaccineName(String name) {
        this.vaccinename = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return code + "\t" + vaccinename + "\t" + type;
    }
}


//    ResultSet resultSet;
//
//    List<QueryResult.UserIDuserNameAddressResult> twoDosebyDate = new ArrayList<QueryResult.UserIDuserNameAddressResult>();
//
//    String queryForgetVaccinatedUsersforTwoDosesByDate = "select U.userID, U.userName, U.address from User U where U.userID IN" +
//            "(select DISTINCT V.userID from Vaccination V, Vaccination V2 where V.dose = 1 and V2.dose = 2 and V.userID = V2.userID and V.vacdate>= \"" + vacdate + "\" and V2.vacdate>= \"" + vacdate + "\" UNION " +
//            "select DISTINCT V.userID from Vaccination V, Vaccination V2 where V.dose = 2 and V2.dose = 3 and V.userID = V2.userID and V.vacdate>=\"" + vacdate + "\" and V2.vacdate >= \"" + vacdate + "\" ) ORDER BY U.userID;";
//
//        try {
//                Statement statement = this.connection.createStatement();
//                resultSet = statement.executeQuery(queryForgetVaccinatedUsersforTwoDosesByDate);
//
//                while (resultSet.next()) {
//                UserIDuserNameAddressResult userArr = null;
//
//                int userID = resultSet.getInt("userID");
//                String userName = resultSet.getString("userName");
//                String address = resultSet.getString("address");
//
//                userArr = new UserIDuserNameAddressResult(Integer.toString(userID), userName, address);
//                twoDosebyDate.add(userArr);
//                }
//                statement.close();
//                } catch (SQLException e) {
//                e.printStackTrace();
//                }
//                UserIDuserNameAddressResult[] userIDuserNameArr = new UserIDuserNameAddressResult[twoDosebyDate.size()];
//                userIDuserNameArr = twoDosebyDate.toArray(userIDuserNameArr);
//                System.out.println(Arrays.toString(userIDuserNameArr));
//                return userIDuserNameArr;


//    ResultSet resultSet;
//
//    List<Vaccine> vacList = new ArrayList<Vaccine>();
//    String queryForgetVaccinesNotAppliedAnyUser = " select DISTINCT * from Vaccine Va where Va.code not in (select DISTINCT V.code from Vaccination V where V.code) ORDER BY Va.code;";
//        try {
//                Statement statement = this.connection.createStatement();
//                resultSet = statement.executeQuery(queryForgetVaccinesNotAppliedAnyUser);
//
//                while (resultSet.next()) {
//                Vaccine vaccinesNotAppliedAnyUser = null;
//                int code = resultSet.getInt("code");
//                String vacname = resultSet.getString("vaccinename");
//                String type = resultSet.getString("type");
//
//                vaccinesNotAppliedAnyUser = new Vaccine(code, vacname, type);
//                vacList.add(vaccinesNotAppliedAnyUser);
//                }
//                statement.close();
//                } catch (SQLException e) {
//                e.printStackTrace();
//                }
//
//                Vaccine[] vaccinesNotAppliedAnyUser = new Vaccine[vacList.size()];
//                vaccinesNotAppliedAnyUser = vacList.toArray(vaccinesNotAppliedAnyUser);
//
//                System.out.println(Arrays.toString(vaccinesNotAppliedAnyUser));
//                return vaccinesNotAppliedAnyUser;
//                }