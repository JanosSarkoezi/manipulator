package com.mycompany.manipulator.remover;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.mycompany.manipulator.helper.ResourceReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

public class NormalAnnotaionRemoverTest {

    @Test
    public void test() throws FileNotFoundException {
        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.java")
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
}
