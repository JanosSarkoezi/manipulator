package com.mycompany.manipulator;

/**
 *
 * @author saj
 */
public class ClassForMethodCallExpr {

    @Deprecated
    private String value1;

    public void method1(String name, String address) {
        log("Name: " + name);
        if (true) {
            log("logging in if statement");
            System.out.println("test");
        }
        method2(address);
    }

    public void method2(String address) {
        log("Address: " + address);
        sendMail(address);
    }

    private void sendMail(String address) {
        // TODO: Send mail to address
    }

    private void log(String value) {
        System.out.println(value);
    }
}
