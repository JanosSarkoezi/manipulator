package com.mycompany.manipulator;

/**
 *
 * @author saj
 */
@SuppressWarnings("unchecked")
public class ClassForSingleMemberAnnotation {

    @SuppressWarnings("unchecked")
    private String value;

    @SuppressWarnings("unchecked")
    public void method1() {
        System.out.println("method1");
    }

    public void method2(@SuppressWarnings("unchecked") Integer value) {
        System.out.println("method1");
    }
}
