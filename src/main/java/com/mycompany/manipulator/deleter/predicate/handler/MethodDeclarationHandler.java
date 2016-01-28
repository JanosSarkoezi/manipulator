package com.mycompany.manipulator.deleter.predicate.handler;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import java.util.List;

/**
 *
 * @author saj
 */
public class MethodDeclarationHandler {

    private final List<String> methodNames;
    private final MethodDeclaration md;

    public MethodDeclarationHandler(List<String> methodNames, Node node) {
        this.methodNames = methodNames;
        this.md = (MethodDeclaration) node;
    }

    public boolean handle() {
        boolean found = false;

        if (!methodNames.isEmpty()) {
            found = methodNames.contains(md.getName());
        }

        return found;
    }
}
