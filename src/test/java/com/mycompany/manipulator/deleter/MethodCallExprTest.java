package com.mycompany.manipulator.deleter;

import com.mycompany.manipulator.Printer;
import com.mycompany.manipulator.deleter.predicate.MethodCallExprPredicate;
import com.mycompany.manipulator.helper.ResourceReader;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import java.io.File;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author saj
 */
public class MethodCallExprTest {

    @Test
    public void notSpecified() throws ParseException, IOException {
        MethodCallExprPredicate fdp = new MethodCallExprPredicate();

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodCallExpr.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        try {
            cu.accept(new MethodReferenceExprDeleter(), fdp);
            Assert.assertTrue("No IllegalStateException was thrown", false);
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void forMethod() throws ParseException, IOException {
        MethodCallExprPredicate mrep = new MethodCallExprPredicate().
                forMethod("log");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodCallExpr.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new MethodReferenceExprDeleter(), mrep);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodCallExpr.forMethod")
                .asString();

        new Printer(expectedFile).replaceAll(" ", ".").print();
        new Printer(cu.toString()).replaceAll(" ", ".").print();

//        Assert.assertEquals(expectedFile, cu.toString());
    }
}
