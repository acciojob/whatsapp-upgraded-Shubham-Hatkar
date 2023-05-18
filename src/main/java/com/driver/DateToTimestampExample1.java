package com.driver;

import java.sql.Timestamp;
import java.util.Date;
public class DateToTimestampExample1 {

    private Object ts;


    public static Timestamp date() {
        Date date = new Date();
        java.sql.Timestamp ts=new java.sql.Timestamp(date.getTime());
        return ts;
    }
}