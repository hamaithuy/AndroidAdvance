package com.example.demoapp1.Common;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Common {

    public static String FormatCurrentcy(Double value){
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String str1 = currencyVN.format(value);
        return str1;
    }

    public static String getDateFormat(Date date){
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = df.format(date);
        return  dateString;
    }
}
