package com.mycompany.manipulator.remover;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.mycompany.manipulator.helper.ResourceReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

public class NormalAnnotaionRemoverTest {

    public static final String TEST_FILE_ORIGINAL = "com/mycompany/manipulator/ClassForNormalAnnotation.java";
    public static final String TEST_FILE_FORMATED = "com/mycompany/manipulator/ClassForNormalAnnotation.formatted";

    @Test
    public void test() throws FileNotFoundException {
        File source = new ResourceReader()
                .read(TEST_FILE_ORIGINAL)
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);

        new NormalAnnotaionRemover(cu)
                .forAnnotation("Column")
                .withAttribute("name", "value")
                .onMethod("method1")
                .remove();

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.onMethod")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void testNull() throws FileNotFoundException {
        File source = new ResourceReader()
                .read(TEST_FILE_ORIGINAL)
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);

        new NormalAnnotaionRemover(cu)
                .forAnnotation(null)
                .withAttribute(null, null)
                .onMethod(null)
                .remove();

        String expectedFile = new ResourceReader()
                .read(TEST_FILE_FORMATED)
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }
}
