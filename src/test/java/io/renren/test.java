package io.renren;

import org.junit.Test;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class test {
    @Test
    public void testtime(){
        Date date = new Date();
        /*SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        int time = Integer.parseInt(formatter.format(date));
        System.out.println(formatter);
        System.out.println(time);*/
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        System.out.println(sdf.format(date));
    }

    @Test
    public void testchange() throws ParseException {
        String time= "2020-5-27 13:57:45";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
        Date date = simpleDateFormat.parse(time);
        System.out.println(date);
        long longtime = date.getTime();
        System.out.println(longtime);

        Date date1=new Date();
        System.out.println(date1);
    }

    @Test
    public void testchuo(){
        System.out.println(10535485/60000);
        System.out.println((10535485/1000)%60);
    }
}
