package com.mycompany.manipulator.deleter;

import com.mycompany.manipulator.helper.ResourceReader;
import com.mycompany.manipulator.deleter.predicate.SingleMemberAnnotationPredicate;
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
public class SingleMemberAnnotationTest {

    @Test
    public void notSpecified() throws ParseException, IOException {
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
    public void onMethod() throws ParseException, IOException {
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
    public void onMethodDupplicated() throws ParseException, IOException {
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
    public void onMethodWithAnnotationTypo() throws ParseException, IOException {
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
    public void onMethodWithMethodTypo() throws ParseException, IOException {
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
    public void onField() throws ParseException, IOException {
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
    public void onFieldDupplicated() throws ParseException, IOException {
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
    public void onFieldWithAnnotationTypo() throws ParseException, IOException {
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
    public void onFieldWithFieldTypo() throws ParseException, IOException {
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
    public void onParameter() throws ParseException, IOException {
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
    public void onParameterDupplicated() throws ParseException, IOException {
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
    public void onParameterWithAnnotationTypo() throws ParseException, IOException {
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
    public void onParameterWithParameterTypo() throws ParseException, IOException {
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
    public void onClassOrInterface() throws ParseException, IOException {
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
    public void onClassOrInterfaceWithClassTypo() throws ParseException, IOException {
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
    public void onClassOrInterfaceWithAnnotationTypo() throws ParseException, IOException {
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
