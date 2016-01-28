package com.mycompany.manipulator.deleter;

import com.mycompany.manipulator.helper.ResourceReader;
import com.mycompany.manipulator.deleter.predicate.MarkerAnnotationPredicate;
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
public class MarkerAnnotationTest {

    @Test
    public void notSpecified() throws ParseException, IOException {
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
    public void onMethod() throws ParseException, IOException {
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
    public void onMethodDuplicated() throws ParseException, IOException {
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
    public void onMethodWithAnnotationTypo() throws ParseException, IOException {
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
    public void onMethodWithMethodTypo() throws ParseException, IOException {
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
    public void onClassOrInterface() throws ParseException, IOException {
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
    public void onClassOrInterfaceWithAnnotationTypo() throws ParseException, IOException {
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
    public void onClassOrInterfaceWithClassTypo() throws ParseException, IOException {
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
    public void onField() throws ParseException, IOException {
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
    public void onFieldDuplicated() throws ParseException, IOException {
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
    public void onFieldWithAnnotationTypo() throws ParseException, IOException {
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
    public void onFieldWithFieldTypo() throws ParseException, IOException {
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
    public void onParameter() throws ParseException, IOException {
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
    public void onParameterDupplicated() throws ParseException, IOException {
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
    public void onParameterithAnnotationTypo() throws ParseException, IOException {
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
    public void onParameterithParameterTypo() throws ParseException, IOException {
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
