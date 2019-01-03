package com.mycompany.manipulator.deleter;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import org.apache.commons.collections4.Predicate;

/**
 * @author saj
 */
public class MethodCallExprDeleter extends ModifierVisitor<Predicate<MethodCallExpr>> {

    @Override
    public Node visit(MethodCallExpr declarator, Predicate<MethodCallExpr> predicate) {
        return predicate.evaluate(declarator) ? null : declarator;
    }

    @Override
    public Node visit(ExpressionStmt declarator, Predicate<MethodCallExpr> predicate) {
        super.visit(declarator, predicate);
        return declarator.getExpression() == null ? null : declarator;
    }
}
