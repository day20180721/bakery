package com.littlejenny.bakery.utils;

import java.util.Base64;
import java.util.TimeZone;
import java.util.UUID;

public class FileNameUtil {

    public static String getRandomImageName(String extension){
        return UUID.randomUUID() + extension;
    }
}
