package com.mycompany.manipulator.deleter;

import com.mycompany.manipulator.helper.ResourceReader;
import com.mycompany.manipulator.deleter.predicate.NormalAnnotationPredicate;
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
public class NormalAnnotationTest {

    @Test(expected = IllegalStateException.class)
    public void notSpecified() throws ParseException, IOException {
        NormalAnnotationPredicate nap = new NormalAnnotationPredicate();

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new NormalAnnotationDeleter(), nap);
    }

    @Test
    public void onMethod() throws ParseException, IOException {
        NormalAnnotationPredicate nap = new NormalAnnotationPredicate()
                .forAnnotation("Column")
                .withAttribute("name", "value")
                .onMethod("method1");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new NormalAnnotationDeleter(), nap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.onMethod")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void onMethodDuplicated() throws ParseException, IOException {
        NormalAnnotationPredicate nap = new NormalAnnotationPredicate()
                .forAnnotation("Column")
                .withAttribute("name", "value")
                .onMethod("method1")
                .onMethod("method1");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new NormalAnnotationDeleter(), nap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.onMethod")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void onMethodWithAnnotationTypo() throws ParseException, IOException {
        NormalAnnotationPredicate nap = new NormalAnnotationPredicate()
                .forAnnotation("Columnn")
                .withAttribute("name", "value")
                .onMethod("method1");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new NormalAnnotationDeleter(), nap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.formatted")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void onMethodWithMethodTypo() throws ParseException, IOException {
        NormalAnnotationPredicate nap = new NormalAnnotationPredicate()
                .forAnnotation("Column")
                .withAttribute("name", "value")
                .onMethod("method11");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new NormalAnnotationDeleter(), nap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.formatted")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void onMethodWithAttributeKeyTypo() throws ParseException, IOException {
        NormalAnnotationPredicate nap = new NormalAnnotationPredicate()
                .forAnnotation("Column")
                .withAttribute("namee", "value")
                .onMethod("method1");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new NormalAnnotationDeleter(), nap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.formatted")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void onMethodWithAttributeValueTypo() throws ParseException, IOException {
        NormalAnnotationPredicate nap = new NormalAnnotationPredicate()
                .forAnnotation("Column")
                .withAttribute("name", "valuee")
                .onMethod("method1");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new NormalAnnotationDeleter(), nap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.formatted")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void onClassOrInterface() throws ParseException, IOException {
        NormalAnnotationPredicate nap = new NormalAnnotationPredicate()
                .forAnnotation("NamedQuery")
                .withAttribute("name", "asdf")
                .onClassOrInterface("ClassForNormalAnnotation");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new NormalAnnotationDeleter(), nap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.onClassOrInterface")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void onClassOrInterfaceWithClassTypo() throws ParseException, IOException {
        NormalAnnotationPredicate nap = new NormalAnnotationPredicate()
                .forAnnotation("NamedQuery")
                .withAttribute("name", "asdf")
                .onClassOrInterface("ClassForNormalAnnotationn");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new NormalAnnotationDeleter(), nap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.formatted")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void onClassOrInterfaceWithAnnotationTypo() throws ParseException, IOException {
        NormalAnnotationPredicate nap = new NormalAnnotationPredicate()
                .forAnnotation("NamedQueryy")
                .withAttribute("name", "asdf")
                .onClassOrInterface("ClassForNormalAnnotation");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new NormalAnnotationDeleter(), nap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.formatted")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void onClassOrInterfaceWithAttributeKeyTypo() throws ParseException, IOException {
        NormalAnnotationPredicate nap = new NormalAnnotationPredicate()
                .forAnnotation("NamedQuery")
                .withAttribute("namee", "asdf")
                .onClassOrInterface("ClassForNormalAnnotation");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new NormalAnnotationDeleter(), nap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.formatted")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void onClassOrInterfaceWithAttributeValueTypo() throws ParseException, IOException {
        NormalAnnotationPredicate nap = new NormalAnnotationPredicate()
                .forAnnotation("NamedQuery")
                .withAttribute("name", "asdff")
                .onClassOrInterface("ClassForNormalAnnotation");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new NormalAnnotationDeleter(), nap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.formatted")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void onParameter() throws ParseException, IOException {
        NormalAnnotationPredicate nap = new NormalAnnotationPredicate()
                .forAnnotation("MyAnnotation")
                .withAttribute("value", "42")
                .onParameter("value");

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new NormalAnnotationDeleter(), nap);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.onParameter")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }
}
