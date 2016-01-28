package com.mycompany.manipulator;

/**
 *
 * @author saj
 */
public class Printer {

    private String value;

    public Printer(String value) {
        this.value = value;
    }

    public Printer replaceAll(String regex, String replacerent) {
        value = value.replaceAll(regex, replacerent);
        return this;
    }

    public void print() {
        System.out.println(value);
    }
}
