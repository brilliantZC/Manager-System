package io.renren;

import org.junit.Test;

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
}
