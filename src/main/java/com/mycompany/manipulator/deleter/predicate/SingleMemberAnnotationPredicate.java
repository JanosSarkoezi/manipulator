package com.mycompany.manipulator.deleter.predicate;

import com.mycompany.manipulator.deleter.predicate.handler.ClassOrInterfaceDeclarationHandler;
import com.mycompany.manipulator.deleter.predicate.handler.FieldDeclarationHandler;
import com.mycompany.manipulator.deleter.predicate.handler.MethodDeclarationHandler;
import com.mycompany.manipulator.deleter.predicate.handler.ParameterHandler;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.Predicate;

/**
 * @author saj
 */
public class SingleMemberAnnotationPredicate implements Predicate<SingleMemberAnnotationExpr> {

    private String annotation;
    private String classOrInterface;
    private final List<String> fieldNames = new ArrayList<>();
    private final List<String> methodNames = new ArrayList<>();
    private final List<String> parameterNames = new ArrayList<>();

    @Override
    public boolean evaluate(SingleMemberAnnotationExpr smae) {
        boolean found = false;
        if (annotation == null) {
            throw new IllegalStateException("Annotation must not be null");
        }

        if (!annotation.equals(smae.getName().getName())) {
            return found;
        }

        Node parentNode = smae.getParentNode();
        if (parentNode instanceof MethodDeclaration) {
            found = new MethodDeclarationHandler(methodNames, parentNode).handle();
        } else if (parentNode instanceof Parameter) {
            found = new ParameterHandler(parameterNames, parentNode).handle();
        } else if (parentNode instanceof FieldDeclaration) {
            found = new FieldDeclarationHandler(fieldNames, parentNode).handle();
        } else if (parentNode instanceof ClassOrInterfaceDeclaration) {
            found = new ClassOrInterfaceDeclarationHandler(classOrInterface, parentNode).handle();
        }

        return found;
    }

    public SingleMemberAnnotationPredicate forAnnotation(@NotNull String annotation) {
        this.annotation = annotation;
        return this;
    }

    public SingleMemberAnnotationPredicate onMethod(@NotNull String methodName) {
        if (!methodNames.contains(methodName)) {
            methodNames.add(methodName);
        }
        return this;
    }

    public SingleMemberAnnotationPredicate onParameter(@NotNull String parameter) {
        if (!parameterNames.contains(parameter)) {
            parameterNames.add(parameter);
        }
        return this;
    }

    public SingleMemberAnnotationPredicate onField(@NotNull String fieldName) {
        if (!fieldNames.contains(fieldName)) {
            fieldNames.add(fieldName);
        }
        return this;
    }

    public SingleMemberAnnotationPredicate onClassOrInterface(@NotNull String classOrInterface) {
        this.classOrInterface = classOrInterface;
        return this;
    }

    @Override
    public String toString() {
        return "SingleMemberAnnotationPredicate{"
                + "annotation=" + annotation
                + ", classOrInterface=" + classOrInterface
                + ", fieldNames=" + fieldNames
                + ", methodNames=" + methodNames
                + ", parameterNames=" + parameterNames
                + '}';
    }
}
