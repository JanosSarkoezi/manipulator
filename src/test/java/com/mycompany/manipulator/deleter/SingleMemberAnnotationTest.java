package com.mycompany.manipulator.deleter;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.*;
import com.mycompany.manipulator.helper.ResourceReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author saj
 */
public class SingleMemberAnnotationTest {

    @Test
    public void onMethod2() throws IOException {
        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        List<Node> nodes = cu.findAll(MethodDeclaration.class).stream()
                .filter(c -> c.getName().getId().equals("method1"))
                .map(c -> c.getAnnotations())
                .flatMap(Collection::stream)
//                .filter(c -> c instanceof SingleMemberAnnotationExpr)
                .filter(c -> c.getName().getId().equals("SuppressWarnings"))
                .collect(Collectors.toList());

        nodes.forEach(Node::remove);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.onMethod")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void onField2() throws IOException {
        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        List<Node> nodes = cu.findAll(VariableDeclarator.class).stream()
                .filter(c -> c.getName().getId().equals("value"))
                .map(c -> c.getParentNode().get())
                .filter(c -> c instanceof FieldDeclaration)
                .map(c -> (FieldDeclaration) c)
                .map(c -> c.getAnnotations())
                .flatMap(Collection::stream)
//                .filter(c -> c instanceof SingleMemberAnnotationExpr)
                .filter(c -> c.getName().getId().equals("SuppressWarnings"))
                .collect(Collectors.toList());

        nodes.forEach(Node::remove);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.onField")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void onParameter2() throws IOException {
        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        List<Node> nodes = cu.findAll(Parameter.class).stream()
                .filter(c -> c.getName().getId().equals("value"))
                .map(c -> c.getAnnotations())
                .flatMap(Collection::stream)
//                .filter(c -> c instanceof SingleMemberAnnotationExpr)
                .filter(c -> c.getName().getId().equals("SuppressWarnings"))
                .collect(Collectors.toList());

        nodes.forEach(Node::remove);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.onParameter")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void onClassOrInterface2() throws IOException {
        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        List<Node> nodes = cu.findAll(ClassOrInterfaceDeclaration.class).stream()
                .filter(c -> c.getName().getId().equals("ClassForSingleMemberAnnotation"))
                .map(c ->c.getAnnotations())
                .flatMap(Collection::stream)
                .filter(c -> c.getName().getId().equals("SuppressWarnings"))
                .collect(Collectors.toList());

        nodes.forEach(Node::remove);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.onClassOrInterface")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }
}
