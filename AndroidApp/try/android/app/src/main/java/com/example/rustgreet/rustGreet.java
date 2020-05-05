package com.example.rustgreet;

public class rustGreet {
    private static native String greeting(final String pattern);

    public String sayHello(String to) {
        return greeting(to);
    }
}
