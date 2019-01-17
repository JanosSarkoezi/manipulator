package com.mycompany.manipulator;

import com.mycompany.manipulator.deleter.MyAnnotation;
import javax.persistence.Column;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author saj
 */
@NamedQueries({
    @NamedQuery(name = "asdf", query = "asdf"),
    @NamedQuery(name = "qwer", query = "qwer")
})
public class ClassForNormalAnnotation {

    @Column(name = "value")
    private String value;

    @Column(name = "value")
    public void method1() {
        System.out.println("method1");
    }

    @Column(nullable = false)
    public void method2(@MyAnnotation(value = "42") String value) {
        System.out.println("method2");
    }
}
