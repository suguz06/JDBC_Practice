package day4;


import utility.DB_Utility;
import java.sql.*;
import java.util.*;


public class TestQuery {


    public static void main(String[] args) throws SQLException {

        // test out all the methods you have created so far for practice
        DB_Utility.createConnection();

        ResultSet jobRS = DB_Utility.runQuery("SELECT * FROM JOBS");

        /**
         * Get the row count of the ResultSet
         * move the pointer to the last row and get the row number
         */

        int rowCount =  DB_Utility.getRowCount() ;
        System.out.println("rowCount = " + rowCount);

        int colCount = DB_Utility.getColumnCount() ;
        System.out.println("colCount = " + colCount);

        System.out.println("All Column names " +  DB_Utility.getColumnNames()  );

        System.out.println("Row data at row 3 " + DB_Utility.getRowDataAsList(3)  );

        System.out.println("Get Cell value at row 2 col 4 "
                + DB_Utility.getColumnDataAtRow(2, 4) );

        System.out.println("Get Cell value at row 2 col MIN_SALARY "
                + DB_Utility.getColumnDataAtRow(2, "MIN_SALARY") );

        System.out.println("Get Cell value at row 5 col JOB_TITLE "
                + DB_Utility.getColumnDataAtRow(2, "JOB_TITLE") );

        System.out.println("3rd column value " + DB_Utility.getColumnDataAsList(3) );
        System.out.println("JOB_TITLE column value " + DB_Utility.getColumnDataAsList("JOB_TITLE") );

        System.out.println("--------\n");
//        DB_Utility.displayAllData();

        Map<String,String>  row1Map = new LinkedHashMap<>() ; //new HashMap<>() ;
        //JOB_ID, JOB_TITLE, MIN_SALARY, MAX_SALARY
        //AC_ACCOUNT	Public Accountant	4200	9000
        row1Map.put("JOB_ID", "AC_ACCOUNT");
        row1Map.put("JOB_TITLE", "Public Accountant");
        row1Map.put("MIN_SALARY", "4200");
        row1Map.put("MAX_SALARY", "9000");

        // now do above programmatically
        // create row 1 map like above programmatically
//        System.out.println("row1Map = " + row1Map);

        System.out.println("first row rowMap = " + DB_Utility.getRowMap(1)  );

        // if one row can be represented as one map object
        // what data structure is good to store 19 rows of data
        // List of Map

        // Get 2nd row and 4th row and save it into list of map as practice
        Map<String,String> row2Map = DB_Utility.getRowMap(2);
        Map<String,String> row4Map = DB_Utility.getRowMap(4);

        List<Map<String,String> >  rowMapList = new ArrayList<>();
        rowMapList.add(row2Map);
        rowMapList.add(row4Map);

        System.out.println( rowMapList );





        DB_Utility.destroy();

    }
}
