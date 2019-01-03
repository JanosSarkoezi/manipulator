package com.mycompany.manipulator.deleter;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.mycompany.manipulator.helper.ResourceReader;
import com.mycompany.manipulator.deleter.predicate.NormalAnnotationPredicate;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author saj
 */
public class NormalAnnotationTest {

    @Test(expected = IllegalStateException.class)
    @Deprecated
    public void notSpecified() throws IOException {
        NormalAnnotationPredicate nap = new NormalAnnotationPredicate();

        File source = new ResourceReader()
                .read("com/mycompany/manipulator/ClassForNormalAnnotation.java")
                .asFile();

        CompilationUnit cu = JavaParser.parse(source);
        cu.accept(new NormalAnnotationDeleter(), nap);
    }

    @Test
    @Deprecated
    public void onMethod() throws IOException {
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



    @Test
    @Deprecated
    public void onMethodDuplicated() throws IOException {
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
    @Deprecated
    public void onMethodWithAnnotationTypo() throws IOException {
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
    @Deprecated
    public void onMethodWithMethodTypo() throws IOException {
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
    @Deprecated
    public void onMethodWithAttributeKeyTypo() throws IOException {
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
    @Deprecated
    public void onMethodWithAttributeValueTypo() throws IOException {
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
    @Deprecated
    public void onClassOrInterface() throws IOException {
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
    @Deprecated
    public void onClassOrInterfaceWithClassTypo() throws IOException {
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
    @Deprecated
    public void onClassOrInterfaceWithAnnotationTypo() throws IOException {
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
    @Deprecated
    public void onClassOrInterfaceWithAttributeKeyTypo() throws IOException {
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
    @Deprecated
    public void onClassOrInterfaceWithAttributeValueTypo() throws IOException {
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
    @Deprecated
    public void onParameter() throws IOException {
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
