package com.nidee.remoteLearn.utils;

import java.util.Arrays;
import java.util.Date;

public class CommonUtils {

    /**
     * Generate a unique error code for exceptions.
     * The error code is a combination of the current filePath + Line Number + TimeStamp.
     *
     * @return A unique error code as a String.
     */
    public static String generateErrorCode(String filePath, String lineNumber) {
        StringBuilder builder = new StringBuilder();
        builder.append(filePath.replace(".java", ""));
        builder.append("_");
        builder.append(lineNumber);
        builder.append("_");
        builder.append(getCurrentTimeStamp());
        return builder.toString();
    }

    public static String getCurrentTimeStamp() {
        Date date = new Date();
        return String.valueOf(date.toInstant().getEpochSecond());
    }

    public static String getLineNumber() {
        return Thread.currentThread().getStackTrace()[1].getLineNumber() + "";
    }

    public static String getFilePath() {
        return Thread.currentThread().getStackTrace()[2].getClassName();
    }


    public static void main(String[] args) {
        System.out.println("Current File Path: " + getFilePath());
        System.out.println("Current Line Number: " + getLineNumber());
        System.out.println("Current Time Stamp: " + getCurrentTimeStamp());
//        System.out.println("Generated Error Code: " + generateErrorCode(getFilePath(), getLineNumer()));
    }

}
