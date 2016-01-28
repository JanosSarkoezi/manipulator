package com.mycompany.manipulator.deleter;

import com.mycompany.manipulator.helper.ResourceReader;
import com.mycompany.manipulator.deleter.predicate.MethodDeclarationPredicate;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author saj
 */
public class MethodDeclarationTest {
    
    @Test
    public void notSpecified() throws IOException, URISyntaxException, ParseException {

        MethodDeclarationPredicate mp = new MethodDeclarationPredicate();

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodDeleter.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new MethodDeclarationDeleter(), mp);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodDeleter.java")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void forMethod() throws IOException, URISyntaxException, ParseException {

        MethodDeclarationPredicate mp = new MethodDeclarationPredicate()
                .forMethod("method2");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodDeleter.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new MethodDeclarationDeleter(), mp);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodDeleter.forMethod")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }
    
    @Test
    public void forMethodWithMethodTypo() throws IOException, URISyntaxException, ParseException {

        MethodDeclarationPredicate mp = new MethodDeclarationPredicate()
                .forMethod("methodd2");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodDeleter.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new MethodDeclarationDeleter(), mp);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodDeleter.java")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }
    
    @Test
    public void forMethodDuplicated() throws IOException, URISyntaxException, ParseException {

        MethodDeclarationPredicate mp = new MethodDeclarationPredicate()
                .forMethod("method2")
                .forMethod("method2");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodDeleter.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new MethodDeclarationDeleter(), mp);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodDeleter.forMethod")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }
    
    @Test
    public void forMethods() throws IOException, URISyntaxException, ParseException {
        MethodDeclarationPredicate mp = new MethodDeclarationPredicate()
                .forMethod("method1")
                .forMethod("method3");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodDeleter.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new MethodDeclarationDeleter(), mp);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodDeleter.forMethods")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }
    
    @Test
    public void forMethodsAsArray() throws IOException, URISyntaxException, ParseException {
        MethodDeclarationPredicate mp = new MethodDeclarationPredicate()
                .forMethods(Arrays.asList("method1", "method3"));

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodDeleter.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new MethodDeclarationDeleter(), mp);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodDeleter.forMethods")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }
    
    @Test
    public void forMethodsAsArrayDuplicated() throws IOException, URISyntaxException, ParseException {
        MethodDeclarationPredicate mp = new MethodDeclarationPredicate()
                .forMethods(Arrays.asList("method1", "method3", "method3"));

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodDeleter.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new MethodDeclarationDeleter(), mp);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodDeleter.forMethods")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }
}
