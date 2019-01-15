package com.mycompany.manipulator.deleter;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.mycompany.manipulator.helper.ResourceReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 * @author saj
 */
public class MethodCallExprTest {

    @Test
    public void forMethod2() throws FileNotFoundException {
        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodCallExpr.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.findAll(MethodCallExpr.class).stream()
                .filter(m -> m.getName().getId().equals("log"))
                .forEach(Node::removeForced);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodCallExpr.forMethod")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }
}
