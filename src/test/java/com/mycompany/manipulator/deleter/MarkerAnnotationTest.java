package com.mycompany.manipulator.deleter;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.mycompany.manipulator.deleter.predicate.MarkerAnnotationPredicate;
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
    @Deprecated
    public void notSpecified() throws IOException {
        MarkerAnnotationPredicate map = new MarkerAnnotationPredicate();

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        try {
            cu.accept(new MarkerAnnotationDeleter(), map);
            Assert.assertTrue("No IllegalStateException was thrown", false);
        } catch (IllegalStateException e) {
        }
    }

    @Test
    @Deprecated
    public void onMethod() throws IOException {
        MarkerAnnotationPredicate map = new MarkerAnnotationPredicate()
                .forAnnotation("Deprecated")
                .onMethod("method1");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new MarkerAnnotationDeleter(), map);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.onMethod")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

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
    @Deprecated
    public void onMethodDuplicated() throws IOException {
        MarkerAnnotationPredicate map = new MarkerAnnotationPredicate()
                .forAnnotation("Deprecated")
                .onMethod("method1")
                .onMethod("method1");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new MarkerAnnotationDeleter(), map);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.onMethod")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    @Deprecated
    public void onMethodWithAnnotationTypo() throws IOException {
        MarkerAnnotationPredicate map = new MarkerAnnotationPredicate()
                .forAnnotation("Deprecatedd")
                .onMethod("method1");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new MarkerAnnotationDeleter(), map);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    @Deprecated
    public void onMethodWithMethodTypo() throws IOException {
        MarkerAnnotationPredicate map = new MarkerAnnotationPredicate()
                .forAnnotation("Deprecated")
                .onMethod("methodd1");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new MarkerAnnotationDeleter(), map);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    @Deprecated
    public void onClassOrInterface() throws IOException {
        MarkerAnnotationPredicate map = new MarkerAnnotationPredicate()
                .forAnnotation("Deprecated")
                .onClassOrInterface("ClassForMarkeerAnnotation");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new MarkerAnnotationDeleter(), map);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.onClassOrInterface")
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
    @Deprecated
    public void onClassOrInterfaceWithAnnotationTypo() throws IOException {
        MarkerAnnotationPredicate map = new MarkerAnnotationPredicate()
                .forAnnotation("Deprecatedd")
                .onClassOrInterface("ClassForMarkeerAnnotation");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new MarkerAnnotationDeleter(), map);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    @Deprecated
    public void onClassOrInterfaceWithClassTypo() throws IOException {
        MarkerAnnotationPredicate map = new MarkerAnnotationPredicate()
                .forAnnotation("Deprecated")
                .onClassOrInterface("ClassForMarkeerAnnotationn");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new MarkerAnnotationDeleter(), map);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    @Deprecated
    public void onField() throws IOException {
        MarkerAnnotationPredicate map = new MarkerAnnotationPredicate()
                .forAnnotation("Deprecated")
                .onField("value");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new MarkerAnnotationDeleter(), map);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.onField")
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
    @Deprecated
    public void onFieldDuplicated() throws IOException {
        MarkerAnnotationPredicate map = new MarkerAnnotationPredicate()
                .forAnnotation("Deprecated")
                .onField("value")
                .onField("value");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new MarkerAnnotationDeleter(), map);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.onField")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    @Deprecated
    public void onFieldWithAnnotationTypo() throws IOException {
        MarkerAnnotationPredicate map = new MarkerAnnotationPredicate()
                .forAnnotation("Deprecatedd")
                .onField("value");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new MarkerAnnotationDeleter(), map);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    @Deprecated
    public void onFieldWithFieldTypo() throws IOException {
        MarkerAnnotationPredicate map = new MarkerAnnotationPredicate()
                .forAnnotation("Deprecated")
                .onField("valuee");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new MarkerAnnotationDeleter(), map);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    @Deprecated
    public void onParameter() throws IOException {
        MarkerAnnotationPredicate map = new MarkerAnnotationPredicate()
                .forAnnotation("Deprecated")
                .onParameter("value");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new MarkerAnnotationDeleter(), map);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.onParameter")
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

    @Test
    @Deprecated
    public void onParameterDupplicated() throws IOException {
        MarkerAnnotationPredicate map = new MarkerAnnotationPredicate()
                .forAnnotation("Deprecated")
                .onParameter("value")
                .onParameter("value");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new MarkerAnnotationDeleter(), map);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.onParameter")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    @Deprecated
    public void onParameterithAnnotationTypo() throws IOException {
        MarkerAnnotationPredicate map = new MarkerAnnotationPredicate()
                .forAnnotation("Deprecatedd")
                .onParameter("value");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new MarkerAnnotationDeleter(), map);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    @Deprecated
    public void onParameterithParameterTypo() throws IOException {
        MarkerAnnotationPredicate map = new MarkerAnnotationPredicate()
                .forAnnotation("Deprecatedd")
                .onParameter("valuee");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new MarkerAnnotationDeleter(), map);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMarkeerAnnotation.java")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }
}
