package com.mycompany.manipulator.deleter;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.visitor.ModifierVisitorAdapter;
import org.apache.commons.collections4.Predicate;

/**
 * @author saj
 */
public class SingleMemberAnnotationDeleter extends ModifierVisitorAdapter<Predicate<SingleMemberAnnotationExpr>> {

    @Override
    public Node visit(SingleMemberAnnotationExpr declarator, Predicate<SingleMemberAnnotationExpr> predicate) {
        return predicate.evaluate(declarator) ? null : declarator;
    }
}
