package com.mycompany.manipulator.deleter.predicate;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.IfStmt;
import javax.validation.constraints.NotNull;
import org.apache.commons.collections4.Predicate;

/**
 * @author saj
 */
public class IfStmtPredicate implements Predicate<IfStmt> {
    private String condition;

    @Override
    public boolean evaluate(IfStmt ifStmt) {
        boolean found = condition.equals(ifStmt.getCondition().toStringWithoutComments());
        Node parentNode = ifStmt.getParentNode();
        return found;
    }

    public IfStmtPredicate forCondition(@NotNull String condition) {
        this.condition = condition;
        return this;
    }
}
