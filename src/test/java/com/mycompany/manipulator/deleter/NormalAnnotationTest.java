package com.mycompany.manipulator.deleter;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.mycompany.manipulator.helper.ResourceReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author saj
 */
public class NormalAnnotationTest {

    @Test
    public void onMethod2() throws IOException {
        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        List<Node> nodes = cu.findAll(NormalAnnotationExpr.class).stream()
                .map(c -> c.getPairs())
                .flatMap(Collection::stream)
                .filter(c -> c.getName().getId().equals("name") && c.getValue().asStringLiteralExpr().getValue().equals("value"))
                .filter(c -> c.getParentNode().get().getParentNode().get() instanceof MethodDeclaration)
                .map(c -> (MethodDeclaration) c.getParentNode().get().getParentNode().get())
                .filter(c -> c.getName().getId().equals("method1"))
                .map(c -> c.getAnnotations())
                .flatMap(Collection::stream)
//                .filter(c -> c instanceof NormalAnnotationExpr)
                .filter(c -> c.getName().getId().equals("Column"))
                .collect(Collectors.toList());

        nodes.forEach(Node::remove);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.onMethod")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }

    @Test
    public void onMethod3() throws IOException {
        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        List<Node> nodes = cu.findAll(MethodDeclaration.class).stream()
                .filter(c -> c.getName().getId().equals("method1"))
                .map(c -> c.getChildNodes())
                .flatMap(Collection::stream)
                .filter(c -> c instanceof NormalAnnotationExpr)
                .map(c -> (NormalAnnotationExpr) c)
                .map(c -> c.getPairs())
                .flatMap(Collection::stream)
                .filter(c -> c.getName().getId().equals("name") && c.getValue().asStringLiteralExpr().getValue().equals("value"))
                .map(c -> c.getParentNode().get())
                .collect(Collectors.toList());

        nodes.forEach(Node::remove);

        String expectedFile = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.onMethod")
                .asString();

        Assert.assertEquals(expectedFile, cu.toString());
    }
}
