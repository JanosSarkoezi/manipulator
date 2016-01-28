package com.mycompany.manipulator.deleter;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.visitor.ModifierVisitorAdapter;
import org.apache.commons.collections4.Predicate;

/**
 * @author saj
 */
public class IfStmtDeleter extends ModifierVisitorAdapter<Predicate<IfStmt>> {

    @Override
    public Node visit(IfStmt declarator, Predicate<IfStmt> predicate) {
        return predicate.evaluate(declarator) ? null : declarator;
    }
}
