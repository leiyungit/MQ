package com.msb.mq;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class testData {
    public static void main(String[] args) throws InterruptedException {

        //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        SimpleDateFormat sdf = new SimpleDateFormat("ssSSS");
        for (int i = 0; i < 5; i++) {
            Date date = new Date();
            String format = sdf.format(date);
            System.out.println(format);
            Thread.sleep(1000);
        }


    }
}
