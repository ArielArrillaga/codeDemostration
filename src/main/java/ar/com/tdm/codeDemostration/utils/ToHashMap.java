package ar.com.tdm.codeDemostration.utils;

import java.util.HashMap;

public class ToHashMap {
    public static  <T> HashMap<String, String> asHashMap(String stringRepresentation) {
    	
    	//this class convert "ToString" to hashMap
        HashMap<String, String> map = new HashMap<String, String>();
  
        if (stringRepresentation == null || stringRepresentation.trim().equals("")) {
            return map;
        }
        if (stringRepresentation.contains("[")) {
            int index = stringRepresentation.indexOf("[");
            stringRepresentation = stringRepresentation.substring(index + 1, stringRepresentation.length());
        }
        if (stringRepresentation.contains("(")) {
            int index = stringRepresentation.indexOf("(");
            stringRepresentation = stringRepresentation.substring(index + 1, stringRepresentation.length());
        }
        if (stringRepresentation.endsWith("]")) {
            stringRepresentation = stringRepresentation.substring(0, stringRepresentation.length() - 1);
        }
        if (stringRepresentation.endsWith(")")) {
            stringRepresentation = stringRepresentation.substring(0, stringRepresentation.length() - 1);
        }
        String[] commaSeprated = stringRepresentation.split(",");
        for (int i = 0; i < commaSeprated.length; i++) {
            String keyEqualsValue = commaSeprated[i];
            keyEqualsValue = keyEqualsValue.trim();
            if (keyEqualsValue.equals("") || !keyEqualsValue.contains("=")) {
                continue;
            }
            String[] keyValue = keyEqualsValue.split("=", 2);
            if (keyValue.length > 1) {
                map.put(keyValue[0].trim(), keyValue[1].trim());
            }

        }
        return map;
    }

}
