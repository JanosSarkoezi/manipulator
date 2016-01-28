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
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.Predicate;

/**
 * @author saj
 */
public class NormalAnnotationPredicate implements Predicate<NormalAnnotationExpr> {

    private String annotation;
    private String classOrInterface;
    private final Map<String, List<String>> attributes = new HashMap<>();

    private final List<String> fieldNames = new ArrayList<>();
    private final List<String> methodNames = new ArrayList<>();
    private final List<String> parameterNames = new ArrayList<>();

    @Override
    public boolean evaluate(NormalAnnotationExpr nae) {
        boolean found = false;
        if (annotation == null) {
            throw new IllegalStateException("annotation must be not null");
        }

        if (!annotation.equals(nae.getName().getName())) {
            return found;
        }

        List<MemberValuePair> pairs = nae.getPairs();
        for (MemberValuePair pair : pairs) {
            if (attributes.keySet().contains(pair.getName())) {
                List<String> values = attributes.get(pair.getName());

                Expression expression = pair.getValue();
                if (expression instanceof StringLiteralExpr) {
                    StringLiteralExpr sle = (StringLiteralExpr) expression;
                    if (values.contains(sle.getValue())) {
                        found = true;
                        break;
                    }
                }
            }
        }

        if (found == false) {
            return found;
        }

        Node parentNode = getRightParentNode(nae.getParentNode());
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
    
    private Node getRightParentNode(Node node) {
        if(node instanceof MethodDeclaration 
                || node instanceof Parameter 
                || node instanceof FieldDeclaration 
                || node instanceof ClassOrInterfaceDeclaration) {
            return node;
        } else {
            return getRightParentNode(node.getParentNode());
        }
    }

    public NormalAnnotationPredicate forAnnotation(String annotation) {
        this.annotation = annotation;
        return this;
    }

    public NormalAnnotationPredicate onMethod(String methodName) {
        if (!methodNames.contains(methodName)) {
            methodNames.add(methodName);
        }
        return this;
    }

    public NormalAnnotationPredicate onParameter(String parameter) {
        if (!parameterNames.contains(parameter)) {
            parameterNames.add(parameter);
        }
        return this;
    }

    public NormalAnnotationPredicate onField(String fieldName) {
        if (!fieldNames.contains(fieldName)) {
            fieldNames.add(fieldName);
        }
        return this;
    }

    public NormalAnnotationPredicate onClassOrInterface(String classOrInterface) {
        this.classOrInterface = classOrInterface;
        return this;
    }

    public NormalAnnotationPredicate withAttribute(String key, String value) {
        List<String> values = attributes.get(key);

        if (values == null) {
            values = new ArrayList<>();
        }

        values.add(value);

        attributes.put(key, values);
        return this;
    }

    @Override
    public String toString() {
        return "NormalAnnotationPredicate{"
                + "annotation=" + annotation
                + ", classOrInterface=" + classOrInterface
                + ", attributes=" + attributes
                + ", fieldNames=" + fieldNames
                + ", methodNames=" + methodNames
                + ", parameterNames=" + parameterNames
                + '}';
    }
}
