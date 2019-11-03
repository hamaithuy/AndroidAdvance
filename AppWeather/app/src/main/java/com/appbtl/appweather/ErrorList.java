package com.appbtl.appweather;

public class ErrorList {
    public static String MainErr = "{\"cod\":\"400\",\"message\":\"undefined is not a float\"}";
    public static String HourErr = "{\"cod\": 500,\"message\": \"Internal server error\"}";
    public static String DailyErr = "{\"cod\":\"404\",\"message\":\"city not found\"}";
    public static String NonError = "{\"cod\":\"400\",\"message\":\"Nothing to geocode\"}";
}
