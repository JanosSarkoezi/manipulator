package com.mycompany.manipulator.deleter;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.mycompany.manipulator.helper.ResourceReader;
import com.mycompany.manipulator.deleter.predicate.SingleMemberAnnotationPredicate;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author saj
 */
public class SingleMemberAnnotationTest {

    @Test
    @Deprecated
    public void notSpecified() throws IOException {
        SingleMemberAnnotationPredicate smap = new SingleMemberAnnotationPredicate();

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        try {
            cu.accept(new SingleMemberAnnotationDeleter(), smap);
            Assert.assertTrue("No IllegalStateException was thrown", false);
        } catch (IllegalStateException e) {
        }
    }

    @Test
    @Deprecated
    public void onMethod() throws IOException {
        SingleMemberAnnotationPredicate smap = new SingleMemberAnnotationPredicate()
                .forAnnotation("SuppressWarnings")
                .onMethod("method1");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new SingleMemberAnnotationDeleter(), smap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.onMethod")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

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
    @Deprecated
    public void onMethodDupplicated() throws IOException {
        SingleMemberAnnotationPredicate smap = new SingleMemberAnnotationPredicate()
                .forAnnotation("SuppressWarnings")
                .onMethod("method1")
                .onMethod("method1");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new SingleMemberAnnotationDeleter(), smap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.onMethod")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    @Deprecated
    public void onMethodWithAnnotationTypo() throws IOException {
        SingleMemberAnnotationPredicate smap = new SingleMemberAnnotationPredicate()
                .forAnnotation("SuppressWarningss")
                .onMethod("method1");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new SingleMemberAnnotationDeleter(), smap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    @Deprecated
    public void onMethodWithMethodTypo() throws IOException {
        SingleMemberAnnotationPredicate smap = new SingleMemberAnnotationPredicate()
                .forAnnotation("SuppressWarnings")
                .onMethod("methodd1");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new SingleMemberAnnotationDeleter(), smap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    @Deprecated
    public void onField() throws IOException {
        SingleMemberAnnotationPredicate smap = new SingleMemberAnnotationPredicate()
                .forAnnotation("SuppressWarnings")
                .onField("value");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new SingleMemberAnnotationDeleter(), smap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.onField")
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
    @Deprecated
    public void onFieldDupplicated() throws IOException {
        SingleMemberAnnotationPredicate smap = new SingleMemberAnnotationPredicate()
                .forAnnotation("SuppressWarnings")
                .onField("value")
                .onField("value");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new SingleMemberAnnotationDeleter(), smap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.onField")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    @Deprecated
    public void onFieldWithAnnotationTypo() throws IOException {
        SingleMemberAnnotationPredicate smap = new SingleMemberAnnotationPredicate()
                .forAnnotation("SuppressWarningss")
                .onField("value");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new SingleMemberAnnotationDeleter(), smap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    @Deprecated
    public void onFieldWithFieldTypo() throws IOException {
        SingleMemberAnnotationPredicate smap = new SingleMemberAnnotationPredicate()
                .forAnnotation("SuppressWarnings")
                .onField("valuee");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new SingleMemberAnnotationDeleter(), smap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    @Deprecated
    public void onParameter() throws IOException {
        SingleMemberAnnotationPredicate smap = new SingleMemberAnnotationPredicate()
                .forAnnotation("SuppressWarnings")
                .onParameter("value");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new SingleMemberAnnotationDeleter(), smap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.onParameter")
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
    @Deprecated
    public void onParameterDupplicated() throws IOException {
        SingleMemberAnnotationPredicate smap = new SingleMemberAnnotationPredicate()
                .forAnnotation("SuppressWarnings")
                .onParameter("value")
                .onParameter("value");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new SingleMemberAnnotationDeleter(), smap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.onParameter")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    @Deprecated
    public void onParameterWithAnnotationTypo() throws IOException {
        SingleMemberAnnotationPredicate smap = new SingleMemberAnnotationPredicate()
                .forAnnotation("SuppressWarningss")
                .onParameter("value");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new SingleMemberAnnotationDeleter(), smap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    @Deprecated
    public void onParameterWithParameterTypo() throws IOException {
        SingleMemberAnnotationPredicate smap = new SingleMemberAnnotationPredicate()
                .forAnnotation("SuppressWarningss")
                .onParameter("valuee");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new SingleMemberAnnotationDeleter(), smap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    @Deprecated
    public void onClassOrInterface() throws IOException {
        SingleMemberAnnotationPredicate smap = new SingleMemberAnnotationPredicate()
                .forAnnotation("SuppressWarnings")
                .onClassOrInterface("ClassForSingleMemberAnnotation");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new SingleMemberAnnotationDeleter(), smap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.onClassOrInterface")
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

    @Test
    @Deprecated
    public void onClassOrInterfaceWithClassTypo() throws IOException {
        SingleMemberAnnotationPredicate smap = new SingleMemberAnnotationPredicate()
                .forAnnotation("SuppressWarnings")
                .onClassOrInterface("ClassForSingleMemberAnnotationn");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new SingleMemberAnnotationDeleter(), smap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    @Deprecated
    public void onClassOrInterfaceWithAnnotationTypo() throws IOException {
        SingleMemberAnnotationPredicate smap = new SingleMemberAnnotationPredicate()
                .forAnnotation("SuppressWarningss")
                .onClassOrInterface("ClassForSingleMemberAnnotation");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new SingleMemberAnnotationDeleter(), smap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForSingleMemberAnnotation.java")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }
}
