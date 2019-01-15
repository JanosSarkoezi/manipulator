package com.mycompany.manipulator.deleter;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.mycompany.manipulator.helper.ResourceReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author saj
 */
public class MarkerAnnotationTest {

    @Test
    public void onMethod2() throws IOException {
        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        List<Node> nodes = cu.findAll(MethodDeclaration.class).stream()
                .filter(mae -> mae.getName().getId().equals("method1"))
                .map(md -> md.getAnnotations())
                .flatMap(Collection::stream)
                .filter(c -> c.getName().getId().equals("Deprecated"))
                .collect(Collectors.toList());

        nodes.forEach(Node::remove);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.onMethod")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void onClassOrInterface2() throws IOException {
        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        List<Node> nodes = cu.findAll(ClassOrInterfaceDeclaration.class).stream()
                .filter(mae -> mae.getName().getId().equals("ClassForMarkeerAnnotation"))
                .map(md -> md.getChildNodes())
                .flatMap(Collection::stream)
                .filter(c -> c instanceof MarkerAnnotationExpr)
                .map(c -> (MarkerAnnotationExpr) c)
                .filter(c -> c.getName().getId().equals("Deprecated"))
                .collect(Collectors.toList());

        nodes.forEach(Node::remove);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.onClassOrInterface")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void onField2() throws IOException {
        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        List<Node> nodes = cu.findAll(FieldDeclaration.class).stream()
                .map(fd -> fd.getVariables())
                .flatMap(Collection::stream)
                .filter(vd -> vd.getName().getId().equals("value"))
                .map(vd -> vd.getParentNode().get())
                .filter(c -> c instanceof FieldDeclaration)
                .map(c -> (FieldDeclaration) c)
                .map(c -> c.getAnnotations())
                .flatMap(Collection::stream)
                .filter(c -> c.getName().getId().equals("Deprecated"))
                .collect(Collectors.toList());

        nodes.forEach(Node::remove);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.onField")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void bla() throws IOException {
        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        List<Node> nodes = cu.findAll(MarkerAnnotationExpr.class).stream()
                .filter(c -> c.getName().getId().equals("Deprecated"))
                .map(c -> c.getParentNode().get())
                .filter(c -> c instanceof FieldDeclaration)
                .map(c -> (FieldDeclaration) c)
                .map(c -> c.getVariables())
                .flatMap(Collection::stream)
                .filter(vd -> vd.getName().getId().equals("value"))
                .map(c -> c.getParentNode().get())
                .map(c -> (FieldDeclaration) c)
                .map(c -> c.getAnnotations())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        nodes.forEach(Node::remove);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.onField")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void onParameter2() throws IOException {
        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        List<Node> nodes = cu.findAll(MarkerAnnotationExpr.class).stream()
                .filter(c -> c.getName().getId().equals("Deprecated"))
                .map(c -> c.getParentNode().get())
                .filter(c -> c instanceof Parameter)
                .map(c -> (Parameter) c)
                .filter(c -> c.getName().getId().equals("value"))
                .map(c -> c.getAnnotations())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        nodes.forEach(Node::remove);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.onParameter")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }
}
