package com.mycompany.manipulator;

/**
 *
 * @author saj
 */
@Deprecated
public class ClassForMarkeerAnnotation {

    @Deprecated
    private String value;

    @Deprecated
    public void method1() {
        System.out.println("method1");
    }

    public void method2() {
        System.out.println("method2");
    }

    @Deprecated
    public void method3() {
        System.out.println("method3");
    }

    public void method4(@Deprecated Integer value) {
        System.out.println("method4");
    }
}
