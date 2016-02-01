package com.mycompany.manipulator.deleter.predicate;

import com.github.javaparser.ast.expr.MethodCallExpr;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.apache.commons.collections4.Predicate;

/**
 * @author saj
 */
public class MethodCallExprPredicate implements Predicate<MethodCallExpr> {

    private final List<String> methodNames = new ArrayList<>();

    @Override
    public boolean evaluate(MethodCallExpr methodCallExpr) {
        if (methodNames.isEmpty()) {
            throw new IllegalStateException("methodName must be not null");
        }

        return methodNames.contains(methodCallExpr.getName());
    }

    public MethodCallExprPredicate forMethod(@NotNull String methodName) {
        if (!methodNames.contains(methodName)) {
            methodNames.add(methodName);
        }

        return this;
    }

    public MethodCallExprPredicate forMethods(@NotNull List<String> methodNames) {
        for (String methodName : methodNames) {
            forMethod(methodName);
        }

        return this;
    }
}
