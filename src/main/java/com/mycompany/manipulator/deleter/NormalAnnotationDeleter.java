package com.mycompany.manipulator.deleter;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import org.apache.commons.collections4.Predicate;

/**
 * @author saj
 */
public class NormalAnnotationDeleter extends ModifierVisitor<Predicate<NormalAnnotationExpr>> {

    @Override
    public Node visit(NormalAnnotationExpr declarator, Predicate<NormalAnnotationExpr> predicate) {
        return predicate.evaluate(declarator) ? null : declarator;
    }
}
