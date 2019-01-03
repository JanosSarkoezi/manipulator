package com.mycompany.manipulator.deleter;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.mycompany.manipulator.helper.ResourceReader;
import com.mycompany.manipulator.deleter.predicate.MethodDeclarationPredicate;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author saj
 */
public class MethodDeclarationTest {
    
    @Test
    @Deprecated
    public void notSpecified() throws IOException {

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
    @Deprecated
    public void forMethod() throws IOException {
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
    public void forMethod2() throws IOException {
        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodDeleter.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.findAll(MethodDeclaration.class).stream()
                .filter(m -> m.getName().getId().equals("method2"))
                .forEach(Node::remove);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodDeleter.forMethod")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }
    
    @Test
    @Deprecated
    public void forMethodWithMethodTypo() throws IOException {
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
    @Deprecated
    public void forMethodDuplicated() throws IOException {
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
    @Deprecated
    public void forMethods() throws IOException {
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
    @Deprecated
    public void forMethods2() throws IOException {
        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodDeleter.java")
                .asFile();

        Predicate<MethodDeclaration> method1 = m -> m.getName().getId().equals("method1");
        Predicate<MethodDeclaration> method3 = m -> m.getName().getId().equals("method3");

        CompilationUnit cu = JavaParser.parse(source);
        cu.findAll(MethodDeclaration.class).stream()
                .filter(method1.or(method3))
                .forEach(Node::remove);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodDeleter.forMethods")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    
    @Test
    @Deprecated
    public void forMethodsAsArray() throws IOException {
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
    @Deprecated
    public void forMethodsAsArrayDuplicated() throws IOException {
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
