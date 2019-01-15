package com.mycompany.manipulator.remover;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NormalAnnotaionRemover {
    private CompilationUnit cu;
    private String methodName;
    private String annotation;
    private String key;
    private String value;

    public NormalAnnotaionRemover(CompilationUnit cu) {
        this.cu = cu;
    }

    public void remove() {
        Predicate<MemberValuePair> keyPredicate = c -> c.getName().getId().equals(key);
        Predicate<MemberValuePair> valuePredicate = c -> c.getValue().asStringLiteralExpr().getValue().equals(value);

        List<Node> nodes = cu.findAll(MethodDeclaration.class).stream()
                .filter(c -> c.getName().getId().equals(methodName))
                .map(MethodDeclaration::getChildNodes)
                .flatMap(Collection::stream)
                .filter(c -> c instanceof NormalAnnotationExpr)
                .map(c -> (NormalAnnotationExpr) c)
                .filter(c -> c.getName().getId().equals(annotation))
                .map(NormalAnnotationExpr::getPairs)
                .flatMap(Collection::stream)
                .filter(keyPredicate.and(valuePredicate))
                .map(c -> c.getParentNode().get())
                .collect(Collectors.toList());

        nodes.forEach(Node::remove);
    }

    public NormalAnnotaionRemover onMethod(String methodName) {
        this.methodName = methodName;
        return this;
    }

    public NormalAnnotaionRemover forAnnotation(String annotation) {
        this.annotation = annotation;
        return this;
    }

    public NormalAnnotaionRemover withAttribute(String key, String value) {
        this.key = key;
        this.value = value;

        return this;
    }
}
