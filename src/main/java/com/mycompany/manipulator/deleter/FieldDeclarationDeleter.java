package com.mycompany.manipulator.deleter;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.ModifierVisitorAdapter;
import org.apache.commons.collections4.Predicate;

/**
 *
 * @author saj
 */
public class FieldDeclarationDeleter extends ModifierVisitorAdapter<Predicate<FieldDeclaration>> {

    @Override
    public Node visit(FieldDeclaration declarator, Predicate<FieldDeclaration> predicate) {
        return predicate.evaluate(declarator) ? null : declarator;
    }
}
