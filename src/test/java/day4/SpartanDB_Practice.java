package day4;

import utility.ConfigurationReader;
import utility.DB_Utility;

public class SpartanDB_Practice {


    public static void main(String[] args) {

//        String url="jdbc:oracle:thin:@54.196.213.41:1521:XE";
//        String username="SP";
//       String password="SP";

        String url= ConfigurationReader.getProperty("spartan.database.url");
        String username=ConfigurationReader.getProperty("spartan.database.username");
        String password=ConfigurationReader.getProperty("spartan.database.password");

        DB_Utility.createConnection(url,username,password);

        DB_Utility.runQuery("select * from Spartans");
        //DB_Utility.displayAllData();
       // System.out.println("DB_Utility.getAllDataAsListOfMap() = " + DB_Utility.getAllDataAsListOfMap());

        System.out.println("DB_Utility.getColumnNames() = " + DB_Utility.getColumnNames());

        System.out.println("DB_Utility.getRowCount() = " + DB_Utility.getRowCount());

        DB_Utility.destroy();
//        System.out.printf("------------");
//
//        String url1="jdbc:oracle:thin:@54.196.213.41:1521:XE";
//        String username1="hr";
//        String password1="hr";
//
//        DB_Utility.createConnection(url1,username1,password1);
//
//        DB_Utility.runQuery("select * from Regions");
//        DB_Utility.displayAllData();
    }



}


