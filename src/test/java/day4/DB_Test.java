package day4;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utility.DB_Utility;

public class DB_Test {


    //create connection once and destroy it once


    @BeforeAll
    public static void setUp(){
        DB_Utility.createConnection();
    }


    @Test
    public   void testEmployeeCount(){

        //run query=> select * from employees
        //assert that employees count 107


        DB_Utility.runQuery("select * from employees");
        Assertions.assertEquals(107,DB_Utility.getRowCount(), "test failed");

//        String query = "select * from employees";
//        DB_Utility.runQuery(query);
//        int actual = DB_Utility.getRowCount();
//        int expected = 107;
//        Assertions.assertEquals(actual,expected);

    }



    @Test
    public void rowDataTest(){
        String query = "select * from regions";
        DB_Utility.runQuery(query);
       String actual = DB_Utility.getColumnDataAtRow(3,2);
        String expected = "Asia";
        Assertions.assertEquals(actual,expected);

    }





    @AfterAll
    public static void tearDown(){

        DB_Utility.destroy();
    }


}
