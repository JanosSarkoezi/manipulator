package com.mycompany.manipulator;

import com.mycompany.manipulator.deleter.MyAnnotation;

/**
 * @author saj
 */
public class ClassForFieldDeclaration {

    @Deprecated
    private String value1;

    @Deprecated
    @MyAnnotation(value = "42")
    private String value2;

    @Deprecated
    @MyAnnotation(value = "43")
    private String value3;

    public void method1() {
        int value2;
        int value3;
    }
}
