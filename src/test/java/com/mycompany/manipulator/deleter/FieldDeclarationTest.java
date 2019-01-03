package com.mycompany.manipulator.deleter;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.mycompany.manipulator.deleter.predicate.FieldDeclarationPredicate;
import com.mycompany.manipulator.helper.ResourceReader;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author saj
 */
public class FieldDeclarationTest {

    @Test
    @Deprecated
    public void notSpecified() throws IOException {
        FieldDeclarationPredicate fdp = new FieldDeclarationPredicate();

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        try {
            cu.accept(new FieldDeclarationDeleter(), fdp);
            Assert.assertTrue("No IllegalStateException was thrown", false);
        } catch (IllegalStateException e) {
        }
    }

    @Test
    @Deprecated
    public void onField() throws IOException {
        FieldDeclarationPredicate fdp = new FieldDeclarationPredicate().
                onField("value2");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new FieldDeclarationDeleter(), fdp);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.onField")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void onField2() throws IOException {
        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.findAll(VariableDeclarator.class).stream()
                .filter(vd -> vd.getName().getId().equals("value2"))
                .forEach(vd -> vd.getParentNode().ifPresent(Node::remove));

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.onField")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    @Deprecated
    public void onFieldWithTypo() throws IOException {
        FieldDeclarationPredicate fdp = new FieldDeclarationPredicate().
                onField("value22");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new FieldDeclarationDeleter(), fdp);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.java")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void onFieldWithTypo2() throws IOException {
        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.findAll(VariableDeclarator.class).stream()
                .filter(vd -> vd.getName().getId().equals("value22"))
                .forEach(vd -> vd.getParentNode().ifPresent(Node::remove));

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.java")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    @Deprecated
    public void onFields() throws IOException {
        FieldDeclarationPredicate fdp = new FieldDeclarationPredicate()
                .onField("value2")
                .onField("value3");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new FieldDeclarationDeleter(), fdp);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.onFields")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void onFields2() throws IOException {
        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.java")
                .asFile();

        Predicate<VariableDeclarator> value1 = vd -> vd.getName().getId().equals("value2");
        Predicate<VariableDeclarator> value2 = vd -> vd.getName().getId().equals("value3");

        CompilationUnit cu = JavaParser.parse(source);
        cu.findAll(VariableDeclarator.class).stream()
                .filter(value1.or(value2))
                .forEach(vd -> vd.getParentNode().ifPresent(Node::remove));

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.onFields")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    @Deprecated
    public void onFieldsDuplicated() throws IOException {
        FieldDeclarationPredicate fdp = new FieldDeclarationPredicate()
                .onField("value2")
                .onField("value3")
                .onField("value3");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new FieldDeclarationDeleter(), fdp);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.onFields")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void onFieldsDuplicated2() throws IOException {
        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.java")
                .asFile();

        Predicate<VariableDeclarator> value1 = vd -> vd.getName().getId().equals("value2");
        Predicate<VariableDeclarator> value2 = vd -> vd.getName().getId().equals("value3");
        Predicate<VariableDeclarator> value3 = vd -> vd.getName().getId().equals("value3");

        CompilationUnit cu = JavaParser.parse(source);
        cu.findAll(VariableDeclarator.class).stream()
                .filter(value1.or(value2).or(value3))
                .forEach(vd -> vd.getParentNode().ifPresent(Node::remove));

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.onFields")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    @Deprecated
    public void onFieldsAsArray() throws IOException {
        FieldDeclarationPredicate fdp = new FieldDeclarationPredicate()
                .onFields(Arrays.asList("value2", "value3"));

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new FieldDeclarationDeleter(), fdp);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.onFields")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    @Deprecated
    public void onFieldsAsArrayDuplicated() throws IOException {
        FieldDeclarationPredicate fdp = new FieldDeclarationPredicate()
                .onFields(Arrays.asList("value2", "value3", "value2"));

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new FieldDeclarationDeleter(), fdp);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForFieldDeclaration.onFields")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }
}
