package com.mycompany.manipulator.deleter;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.mycompany.manipulator.Printer;
import com.mycompany.manipulator.deleter.predicate.MethodCallExprPredicate;
import com.mycompany.manipulator.helper.ResourceReader;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author saj
 */
public class MethodCallExprTest {

    @Test
    @Deprecated
    public void notSpecified() throws ParseException, IOException {
        MethodCallExprPredicate fdp = new MethodCallExprPredicate();

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodCallExpr.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        try {
            cu.accept(new MethodCallExprDeleter(), fdp);
            Assert.assertTrue("No IllegalStateException was thrown", false);
        } catch (IllegalStateException e) {
        }
    }

    @Test
    @Deprecated
    public void forMethod() throws ParseException, IOException {
        MethodCallExprPredicate mrep = new MethodCallExprPredicate().
                forMethod("log");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodCallExpr.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new MethodCallExprDeleter(), mrep);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodCallExpr.forMethod")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

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
