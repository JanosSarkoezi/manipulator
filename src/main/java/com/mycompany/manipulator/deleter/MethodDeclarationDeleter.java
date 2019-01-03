package com.mycompany.manipulator.deleter;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import org.apache.commons.collections4.Predicate;

/**
 * @author saj
 */
public class MethodDeclarationDeleter extends ModifierVisitor<Predicate<MethodDeclaration>> {

    @Override
    public Node visit(MethodDeclaration declarator, Predicate<MethodDeclaration> predicate) {
        return predicate.evaluate(declarator) ? null : declarator;
    }
}
