package com.mycompany.manipulator.deleter;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.mycompany.manipulator.helper.ResourceReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author saj
 */
public class MethodDeclarationTest {
    
    @Test
    public void forMethod2() throws IOException {
        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodDeleter.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.findAll(MethodDeclaration.class).stream()
                .filter(m -> m.getName().getId().equals("method2"))
                .forEach(Node::remove);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForMethodDeleter.forMethod")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }
}
