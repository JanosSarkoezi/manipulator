package com.mycompany.manipulator.deleter;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.mycompany.manipulator.helper.ResourceReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;

/**
 * @author saj
 */
public class FieldDeclarationTest {

    public static final String TEST_FILE = "com/mycompany/manipulator/ClassForFieldDeclaration.java";

    @Test
    public void onField2() throws IOException {
        File source = new ResourceReader()
                .read(TEST_FILE)
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
    public void onFieldWithTypo2() throws IOException {
        File source = new ResourceReader()
                .read(TEST_FILE)
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.findAll(VariableDeclarator.class).stream()
                .filter(vd -> vd.getName().getId().equals("value22"))
                .forEach(vd -> vd.getParentNode().ifPresent(Node::remove));

        String expectedFile = new ResourceReader()
                .read(TEST_FILE)
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void onFields2() throws IOException {
        File source = new ResourceReader()
                .read(TEST_FILE)
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
    public void onFieldsDuplicated2() throws IOException {
        File source = new ResourceReader()
                .read(TEST_FILE)
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
}
