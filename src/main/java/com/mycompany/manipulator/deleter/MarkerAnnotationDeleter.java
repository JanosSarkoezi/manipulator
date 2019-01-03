package com.mycompany.manipulator.deleter;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import org.apache.commons.collections4.Predicate;

/**
 * @author saj
 */
public class MarkerAnnotationDeleter extends ModifierVisitor<Predicate<MarkerAnnotationExpr>> {

    @Override
    public Node visit(MarkerAnnotationExpr declarator, Predicate<MarkerAnnotationExpr> predicate) {
        return predicate.evaluate(declarator) ? null : declarator;
    }
}
