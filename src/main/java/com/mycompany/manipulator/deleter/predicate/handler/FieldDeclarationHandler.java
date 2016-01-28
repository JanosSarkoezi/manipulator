package com.mycompany.manipulator.deleter.predicate.handler;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import java.util.List;

/**
 *
 * @author saj
 */
public class FieldDeclarationHandler {

    private final List<String> fieldNames;
    private final FieldDeclaration fd;

    public FieldDeclarationHandler(List<String> fieldNames, Node node) {
        this.fieldNames = fieldNames;
        this.fd = (FieldDeclaration) node;
    }

    public boolean handle() {
        boolean found = false;
        if (!fieldNames.isEmpty()) {
//                FieldDeclaration fd = (FieldDeclaration) parentNode;

            List<VariableDeclarator> variables = fd.getVariables();
            for (VariableDeclarator variable : variables) {
                if (fieldNames.contains(variable.getId().getName())) {
                    found = true;
                    break;
                }
            }
        }

        return found;
    }
}
