package com.mycompany.manipulator.remover;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.mycompany.manipulator.helper.ResourceReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

public class NormalAnnotationRemoverTest {

    private static final String TEST_FILE_ORIGINAL = "com/mycompany/manipulator/ClassForNormalAnnotation.java";
    private static final String TEST_FILE_ON_FIELD = "com/mycompany/manipulator/ClassForNormalAnnotation.onField";
    private static final String TEST_FILE_FORMATTED = "com/mycompany/manipulator/ClassForNormalAnnotation.formatted";
    private static final String TEST_FILE_ON_METHOD1 = "com/mycompany/manipulator/ClassForNormalAnnotation.onMethod1";
    private static final String TEST_FILE_ON_METHOD2 = "com/mycompany/manipulator/ClassForNormalAnnotation.onMethod2";
    private static final String TEST_FILE_ON_PARAMETER = "com/mycompany/manipulator/ClassForNormalAnnotation.onParameter";
    private static final String TEST_FILE_COLUMN_ANNOTATION = "com/mycompany/manipulator/ClassForNormalAnnotation.columnAnnotation";
    private static final String TEST_FILE_COLUMN_ANNOTATION2 = "com/mycompany/manipulator/ClassForNormalAnnotation.columnAnnotation2";
    private static final String TEST_FILE_ON_CLASS_OR_INTERFACE = "com/mycompany/manipulator/ClassForNormalAnnotation.onClassOrInterface";

    @Test
    public void testColumnAnnotation() throws FileNotFoundException {
        File source = new ResourceReader()
                .read(TEST_FILE_ORIGINAL)
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);

        new NormalAnnotationRemover(cu)
                .forAnnotation("Column")
                .remove();

        String expectedFile = new ResourceReader()
                .read(TEST_FILE_COLUMN_ANNOTATION)
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }
    @Test
    public void testOnMethod1WithAttribute() throws FileNotFoundException {
        File source = new ResourceReader()
                .read(TEST_FILE_ORIGINAL)
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);

        new NormalAnnotationRemover(cu)
                .forAnnotation("Column")
                .withAttribute("name", "value")
                .onMethod("method1")
                .remove();

        String expectedFile = new ResourceReader()
                .read(TEST_FILE_ON_METHOD1)
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void testOnMethod1() throws FileNotFoundException {
        File source = new ResourceReader()
                .read(TEST_FILE_ORIGINAL)
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);

        new NormalAnnotationRemover(cu)
                .forAnnotation("Column")
                .onMethod("method1")
                .remove();

        String expectedFile = new ResourceReader()
                .read(TEST_FILE_ON_METHOD1)
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void testOnMethod2() throws FileNotFoundException {
        File source = new ResourceReader()
                .read(TEST_FILE_ORIGINAL)
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);

        new NormalAnnotationRemover(cu)
                .forAnnotation("Column")
                .withAttribute("nullable", "false")
                .onMethod("method2")
                .remove();

        String expectedFile = new ResourceReader()
                .read(TEST_FILE_ON_METHOD2)
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void testOnParameter() throws FileNotFoundException {
        File source = new ResourceReader()
                .read(TEST_FILE_ORIGINAL)
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);

        new NormalAnnotationRemover(cu)
                .forAnnotation("MyAnnotation")
                .withAttribute("value", "42")
                .onParameter("value")
                .remove();

        String expectedFile = new ResourceReader()
                .read(TEST_FILE_ON_PARAMETER)
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void testOnClassOrInterface() throws FileNotFoundException {
        File source = new ResourceReader()
                .read(TEST_FILE_ORIGINAL)
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);

        new NormalAnnotationRemover(cu)
                .forAnnotation("NamedQuery")
                .withAttribute("name", "asdf")
                .onClassOrInterface("ClassForNormalAnnotation")
                .remove();

        String expectedFile = new ResourceReader()
                .read(TEST_FILE_ON_CLASS_OR_INTERFACE)
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void testNamedQueryAnnotationWithAttribute() throws FileNotFoundException {
        File source = new ResourceReader()
                .read(TEST_FILE_ORIGINAL)
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);

        new NormalAnnotationRemover(cu)
                .forAnnotation("NamedQuery")
                .withAttribute("name", "asdf")
                .remove();

        String expectedFile = new ResourceReader()
                .read(TEST_FILE_ON_CLASS_OR_INTERFACE)
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void testColumnAnnotationWithAttribute() throws FileNotFoundException {
        File source = new ResourceReader()
                .read(TEST_FILE_ORIGINAL)
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);

        new NormalAnnotationRemover(cu)
                .forAnnotation("Column")
                .withAttribute("name", "value")
                .remove();

        String expectedFile = new ResourceReader()
                .read(TEST_FILE_COLUMN_ANNOTATION2)
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void testField() throws FileNotFoundException {
        File source = new ResourceReader()
                .read(TEST_FILE_ORIGINAL)
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);

        new NormalAnnotationRemover(cu)
                .forAnnotation("Column")
                .withAttribute("name", "value")
                .onField("value")
                .remove();

        String expectedFile = new ResourceReader()
                .read(TEST_FILE_ON_FIELD)
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void testNull() throws FileNotFoundException {
        File source = new ResourceReader()
                .read(TEST_FILE_ORIGINAL)
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);

        new NormalAnnotationRemover(cu)
                .forAnnotation(null)
                .withAttribute(null, null)
                .onMethod(null)
                .remove();

        String expectedFile = new ResourceReader()
                .read(TEST_FILE_FORMATTED)
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }
}
