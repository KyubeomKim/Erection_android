package com.cybil.study.erection.util;

public class Data {
    private final boolean result;
    private final String message;

    public Data(boolean result, String message) {
        this.result = result;
        this.message = message;
    }

    public boolean getResult() {return result;}
    public String getMessage() {return message;}
}
