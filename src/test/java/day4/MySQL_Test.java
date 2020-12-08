package day4;

import utility.ConfigurationReader;
import utility.DB_Utility;

public class MySQL_Test {

    public static void main(String[] args) {



//       String connectionStr="jdbc:mysql://18.233.97.71:3306/library1";
//       String username="library1_client";
//       String password="WVF4NdGXCKHeE6VQ";


        String connectionStr= ConfigurationReader.getProperty("library1.database.url");
        String username=ConfigurationReader.getProperty("library1.database.username");
        String password=ConfigurationReader.getProperty("library1.database.password");


        DB_Utility.createConnection(connectionStr,username,password);

        DB_Utility.runQuery("select * from books");
     DB_Utility.displayAllData();
        System.out.println("DB_Utility.getColumnNames() = " + DB_Utility.getColumnNames());


        DB_Utility.destroy();

    }
}


