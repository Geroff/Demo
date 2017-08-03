package com.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Demo {

    public static void main(String[] args) {
        String ssidAscii = convertStr2Ascii("/ssid.bssid/");
        String ssid = convertAscii2Str(ssidAscii);
    }

    private static String convertStr2Ascii(String str) {
        int len = str.length();
        if (len <= 0) {
            return "as";
        }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("as");
        for (int i = 0; i < len; i++) {
            char aChar = str.charAt(i);
            int num = aChar - 32;
            if (num < 10) {
                stringBuilder.append("0").append(num);
            } else {
                stringBuilder.append(num);
            }
        }
        return stringBuilder.toString();
    }

    private static String convertAscii2Str(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        int len = str.length();
        if (len > 0) {
            Pattern pattern = Pattern.compile("as(\\d+)");
            Matcher matcher = pattern.matcher(str);
            if (matcher.matches()) {
                String numberStr = matcher.group(1);
                int size = numberStr.length() / 2;
                for (int i = 0; i < size; i++) {
                    String number = numberStr.substring(0, 2);
                    int num = Integer.parseInt(number);
                    char c = (char) ((char) num + 32);
                    stringBuilder.append(c);
                    numberStr = numberStr.substring(2);
                }
            }
        }
        return stringBuilder.toString();
    }
}
