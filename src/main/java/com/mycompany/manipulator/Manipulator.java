package com.mycompany.manipulator;

import com.github.javaparser.ParseException;
import java.io.IOException;

/**
 * @author saj
 */
public interface Manipulator {
    public String manipulate(String fileName) throws ParseException, IOException;
}
