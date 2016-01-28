package com.mycompany.manipulator.deleter;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.ModifierVisitorAdapter;
import org.apache.commons.collections4.Predicate;

/**
 * @author saj
 */
public class MethodReferenceExprDeleter extends ModifierVisitorAdapter<Predicate<MethodCallExpr>> {

    @Override
    public Node visit(MethodCallExpr declarator, Predicate<MethodCallExpr> predicate) {
        return predicate.evaluate(declarator) ? null : declarator;
    }
}
