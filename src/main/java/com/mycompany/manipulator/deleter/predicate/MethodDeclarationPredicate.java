package com.mycompany.manipulator.deleter.predicate;

import com.github.javaparser.ast.body.MethodDeclaration;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.Predicate;

/**
 * @author saj
 */
public class MethodDeclarationPredicate implements Predicate<MethodDeclaration> {

    private final List<String> methodNames = new ArrayList<>();

    @Override
    public boolean evaluate(MethodDeclaration declarator) {
        return methodNames.contains(declarator.getName());
    }

    public MethodDeclarationPredicate forMethod(@NotNull String methodName) {
        if (!methodNames.contains(methodName)) {
            methodNames.add(methodName);
        }
        return this;
    }

    public MethodDeclarationPredicate forMethods(@NotNull List<String> methodNames) {
        for (String methodName : methodNames) {
            forMethod(methodName);
        }
        return this;
    }

    @Override
    public String toString() {
        return "MethodPredicate{"
                + "methodNames=" + methodNames
                + '}';
    }
}
