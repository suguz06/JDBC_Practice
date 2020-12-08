package utility;


import java.sql.*;
import java.util.*;


public class DB_Utility {






    static Connection conn; // make it static field so we can reuse in every methods we write
    static Statement stmnt;
    static ResultSet rs;

    public static void createConnection() {

        //hard-coding part
       // String connectionStr = "jdbc:oracle:thin:@52.201.187.226:1521:XE"; Akbar"s
//        String connectionStr = "jdbc:oracle:thin:@54.196.213.41:1521:XE"; Mine IP-url
//        String username = "hr";
//        String password = "hr";



        String connectionStr = ConfigurationReader.getProperty("hr.database.url");
        String username = ConfigurationReader.getProperty("hr.database.username");
        String password = ConfigurationReader.getProperty("hr.database.password");

        try {
            conn = DriverManager.getConnection(connectionStr, username, password);
            System.out.println("CONNECTION SUCCESSFUL !! ");
        } catch (SQLException e) {
            System.out.println("CONNECTION HAS FAILED !!! " + e.getMessage());
        }

    }




    public static void createConnection(String env){
        // add validation to avoid invalid input
        // because we currently only have 2 env : test , dev
        // or add some condition for invalid env
        //  to directly get the information as database.url , database.username, database.password
        // without any env
        System.out.println("You are in "+env+" environment");
        String connectionStr = ConfigurationReader.getProperty(env+".database.url");
        String username = ConfigurationReader.getProperty(env+".database.username");
        String password = ConfigurationReader.getProperty(env+".database.password");

        createConnection(connectionStr,username,password);

    }





    /**
     *  Overload createConnection method to accept url, username, password
     *     * so we can provide those information for different database
     * @param url  The connection String that used to connect to the database
     * @param username the username of database
     * @param password the password of database
     */
    public static void createConnection(String url, String username, String password){

        try{
            conn = DriverManager.getConnection(url,username,password) ;
            System.out.println("CONNECTION SUCCESSFUL");
        }catch(SQLException e){
            System.out.println("ERROR WHILE CONNECTING WITH PARAMETERS " + e.getMessage());
        }


    }



    // Create a method called runQuery that accept a SQL Query
    // and return ResultSet Object
    public static ResultSet runQuery(String query) {

//        ResultSet rs  = null;
        // reusing the connection built from previous method
        try {
            stmnt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmnt.executeQuery(query);

        } catch (SQLException e) {
            System.out.println("Error while getting resultset " + e.getMessage());
        }

        return rs;

    }
    /**
     * cleaning up the resources
     */
    public static void destroy(){


        try {
            if(rs!=null) {
                rs.close();
            }
            if(stmnt!=null) {
                stmnt.close();
            }
            if(conn!=null) {
                conn.close();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * Count how many row we have
     *
     * @return the row count of the resultset we got
     */
    public static int getRowCount() {

        int rowCount = 0;

        try {
            rs.last();
            rowCount = rs.getRow();

            // move the cursor back to beforeFirst location to avoid accident
            rs.beforeFirst();

        } catch (SQLException e) {

            System.out.println("ERROR WHILE GETTING ROW COUNT " + e.getMessage());
        }

        return rowCount;

    }

    /**
     * Get the column count
     *
     * @return count of column the result set have
     */
    public static int getColumnCount() {

        int columnCount = 0;

        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            columnCount = rsmd.getColumnCount();

        } catch (SQLException e) {
            System.out.println("ERROR WHILE COUNTING THE COLUMNS " + e.getMessage());
        }

        return columnCount;
    }


    /**
     * a method to get the column count of the current ResultSet
     * @return column count
     * */
    public static int getColumnCNT() {

        int colCount = 0 ;

        try {
            ResultSetMetaData rsmd = rs.getMetaData() ;
            colCount = rsmd.getColumnCount();
        } catch (SQLException e) {
            System.out.println("ERROR WHILE COUNTING THE COLUMNS" + e.getMessage());
        }
        return colCount;

    }


    /**
     * a method that return all column names as List<String>
     */
    public static List<String> getColumnNames() {

        List<String> columnList = new ArrayList<>();

        try {
            ResultSetMetaData rsmd = rs.getMetaData();

            for (int colNum = 1; colNum <= getColumnCount(); colNum++) {

                String columnName = rsmd.getColumnLabel(colNum);
                columnList.add(columnName);
            }

        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING ALL COLUMN NAMES " + e.getMessage());
        }
        return columnList;

    }

    /**
     * Create a method that return all row data as a List<String>
     *
     * @param rowNum Row number you want to get the data
     * @return the row data as List object
     */
    public static List<String> getRowDataAsList(int rowNum) {

        List<String> rowDataList = new ArrayList<>();

        // first we need to move the pointer to the location the rowNum specified
        try {
            rs.absolute(rowNum);

            for (int colNum = 1; colNum <= getColumnCount(); colNum++) {

                String cellValue = rs.getString(colNum);
                rowDataList.add(cellValue);

            }
            rs.beforeFirst();

        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING ROW DATA AS LIST " + e.getMessage());
        }
        return rowDataList;

    }


    /**
     * Create a method to return the cell value at certain row certain column
     *
     * @param rowNum
     * @return Cell value as String
     * @parem colNum
     */
    public static String getColumnDataAtRow(int rowNum, int colNum) {

        String result = "";

        try {
            rs.absolute(rowNum);
            result = rs.getString(colNum);
            rs.beforeFirst(); // moving back to before first location

        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING CELL VALUE AT ROWNUM COLNUM " + e.getMessage());
        }

        return result;
    }

    /**
     * Create a method to return the cell value at certain row certain column
     *
     * @param rowNum row number
     * @return Cell value as String
     * @parem colName column name
     */
    public static String getColumnDataAtRow(int rowNum, String colName) {

        String result = "";

        try {
            rs.absolute(rowNum);
            result = rs.getString(colName);
            rs.beforeFirst(); // moving back to before first location

        } catch (SQLException e) {
            System.out.println("ERROR WHILE GETTING CELL VALUE AT ROWNUM column name " + e.getMessage());
        }

        return result;
    }

    /**
     *
     * @param columnIndex the column you want to get a list out of
     * @return List of String that contains entire column data from 1st row to last row
     */
    public static List<String> getColumnDataAsList(int columnIndex){

        List<String> columnDataLst = new ArrayList<>();
        try{

            rs.beforeFirst();

            while(rs.next() ) {

                String cellValue = rs.getString(columnIndex);
                columnDataLst.add(cellValue);

            }
            rs.beforeFirst();
        }catch(SQLException e){
            System.out.println("ERROR WHILE getColumnDataAsList "+ e.getMessage() );
        }
        return columnDataLst ;


    }

    /**
     *
     * @param columnName the column you want to get a list out of
     * @return List of String that contains entire column data from the column name specified
     */
    public static List<String> getColumnDataAsList(String columnName){
        List<String> columnDataLst = new ArrayList<>();
        try{

            rs.beforeFirst();

            while(rs.next() ) {

                String cellValue = rs.getString(columnName);
                columnDataLst.add(cellValue);

            }
            rs.beforeFirst();

        }catch(SQLException e){
            System.out.println("ERROR WHILE getColumnDataAsList "+ e.getMessage() );
        }
        return columnDataLst ;


    }

    /*
     * a method to display all the data in the result set
     *
     * */
    public static void displayAllData(){

        try {
            rs.beforeFirst();

            while (rs.next() ){

                for (int colNum = 1; colNum <= getColumnCNT() ; colNum++) {

                 //   System.out.print(  rs.getString(colNum) + "\t" );
                    System.out.printf("%-35s", rs.getString(colNum));

                }
                System.out.println();

            }

        } catch (SQLException e) {
            System.out.println("ERROR WHILE DISPLAYING ALL DATA " + e.getMessage() );
        }


    }

    /**
     * We want to store certain row data as a Map<String,String>
     * * For example :
     *  give me number 3rd row  --->> Map<String,String>   {region_id : 3 , region_name : Asia}
     * @param rowNum
     * @return Map object that contains column names as a key and cell as value
     */
    public static  Map<String,String> getRowMap (int rowNum) {

        Map<String,String> rowMap = new LinkedHashMap<>();

        try {
            rs.absolute(rowNum);
            ResultSetMetaData rsmd = rs.getMetaData();

            for (int colNum = 1; colNum <= getColumnCNT() ; colNum++) {

                String colName      = rsmd.getColumnLabel(colNum) ;
                String cellValue    = rs.getString(colNum) ;
                rowMap.put(colName, cellValue) ;

            }
            rs.beforeFirst();
        } catch (SQLException e) {
            System.out.println("ERROR AT ROW MAP FUNCTION " + e.getMessage() );
        }
        return rowMap ;

    }

    /**
     * Getting entire resultset data as List of Map object
     *
     * @return List< Map<String,String> > that represent all row data
     */
    public static List< Map<String,String> > getAllDataAsListOfMap(){

        List< Map<String,String> > rowMapList = new ArrayList<>();

        for (int rowNum = 1; rowNum <= getRowCount() ; rowNum++) {

            rowMapList.add(   getRowMap(rowNum)   ) ;

        }

        return rowMapList ;


    }

}
