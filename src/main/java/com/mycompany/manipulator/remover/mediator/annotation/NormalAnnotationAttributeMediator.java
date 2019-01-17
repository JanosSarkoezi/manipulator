package com.mycompany.manipulator.remover.mediator.annotation;


import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.mycompany.manipulator.remover.mediator.interfaces.AttributeMediator;
import com.mycompany.manipulator.remover.mediator.interfaces.LocationMediator;
import com.mycompany.manipulator.remover.mediator.interfaces.Remover;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NormalAnnotationAttributeMediator implements AttributeMediator {
    List<NormalAnnotationExpr> annotations;

    public NormalAnnotationAttributeMediator(List<NormalAnnotationExpr> annotations) {
        this.annotations = annotations;
    }

    @Override
    public Remover onMethod(String methodName) {
        List<Node> nodes = annotations.stream()
                .filter(c -> c.getParentNode().get() instanceof MethodDeclaration)
                .map(c -> (MethodDeclaration) c.getParentNode().get())
                .filter(c -> c.getName().getId().equals(methodName))
                .map(MethodDeclaration::getChildNodes)
                .flatMap(Collection::stream)
                .filter(c -> c instanceof NormalAnnotationExpr)
                .collect(Collectors.toList());

        return new AnnotationRemover(nodes);
    }

    @Override
    public Remover onParameter(String parameterName) {
        List<Node> nodes = annotations.stream()
                .filter(c -> c.getParentNode().get() instanceof Parameter)
                .map(c -> (Parameter) c.getParentNode().get())
                .filter(c -> c.getName().getId().equals(parameterName))
                .map(Parameter::getChildNodes)
                .flatMap(Collection::stream)
                .filter(c -> c instanceof NormalAnnotationExpr)
                .collect(Collectors.toList());

        return new AnnotationRemover(nodes);
    }

    @Override
    public Remover onField(String fieldName) {
        List<Node> nodes = annotations.stream()
                .filter(c -> c.getParentNode().get() instanceof FieldDeclaration)
                .map(c -> (FieldDeclaration) c.getParentNode().get())
                .map(FieldDeclaration::getChildNodes)
                .flatMap(Collection::stream)
                .filter(c -> c instanceof VariableDeclarator)
                .filter(c -> ((VariableDeclarator) c).getName().getId().equals(fieldName))
                .filter(c -> c.getParentNode().get() instanceof FieldDeclaration)
                .map(c -> (FieldDeclaration) c.getParentNode().get())
                .map(FieldDeclaration::getChildNodes)
                .flatMap(Collection::stream)
                .filter(c -> c instanceof NormalAnnotationExpr)
                .collect(Collectors.toList());

        return new AnnotationRemover(nodes);
    }

    @Override
    public Remover onClassOrInterface(String className) {
        List<Node> nodes = annotations.stream()
                .filter(c -> new ClassOrInterfacePredicate(className).test(c))
                .collect(Collectors.toList());

        return new AnnotationRemover(nodes);
    }

    @Override
    public LocationMediator withAttribute(String key, String value) {
        Predicate<MemberValuePair> keyPredicate = c -> c.getName().getId().equals(key);
        Predicate<MemberValuePair> valuePredicate = new ValuePredicate(value);

        annotations = annotations.stream()
                .map(NormalAnnotationExpr::getPairs)
                .flatMap(Collection::stream)
                .filter(keyPredicate.and(valuePredicate))
                .map(c -> (NormalAnnotationExpr) c.getParentNode().get())
                .collect(Collectors.toList());

        return this;
    }

    @Override
    public void remove() {
        annotations.forEach(Node::remove);
    }

    private class ValuePredicate implements Predicate<MemberValuePair> {
        private String value;

        ValuePredicate(String value) {
            this.value = value;
        }

        @Override
        public boolean test(MemberValuePair memberValuePair) {
            boolean result;

            if (memberValuePair.getValue().isStringLiteralExpr()) {
                result = memberValuePair.getValue().asStringLiteralExpr().getValue().equals(value);
            } else {
                result = memberValuePair.getValue().asBooleanLiteralExpr().getValue() == Boolean.parseBoolean(value);
            }

            return result;
        }
    }

    private class ClassOrInterfacePredicate implements Predicate<Node> {
        private String classOrInterfaceName;

        public ClassOrInterfacePredicate(String classOrInterfaceName) {
            this.classOrInterfaceName = classOrInterfaceName;
        }

        @Override
        public boolean test(Node node) {
            if (node instanceof ClassOrInterfaceDeclaration) {
                return ((ClassOrInterfaceDeclaration) node).getName().getId().equals(classOrInterfaceName);
            } else {
                Node parent = node.getParentNode().orElse(null);
                return test(parent);
            }
        }
    }
}