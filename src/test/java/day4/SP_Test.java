package day4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utility.DB_Utility;

public class SP_Test {


//    public static void main(String[] args) {
//
//
//        DB_Utility.createConnection("dev");
//        DB_Utility.runQuery("select * from spartans");
//        DB_Utility.displayAllData();
//
//
//    }

    @Test
    public void test(){
        DB_Utility.createConnection("dev");
        DB_Utility.runQuery("select * from spartans");
        Assertions.assertEquals(DB_Utility.getColumnCount(),6);
    }

    @Test
    public void test1(){
        DB_Utility.createConnection("spartan");
        DB_Utility.runQuery("select * from spartans");
        Assertions.assertEquals(DB_Utility.getColumnCount(),6);
    }
}
