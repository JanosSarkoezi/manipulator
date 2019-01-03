package com.mycompany.manipulator.deleter.predicate.handler;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.SimpleName;

import java.util.List;

/**
 *
 * @author saj
 */
public class ParameterHandler {

    private final List<String> parameterNames;
    private final Parameter p;

    public ParameterHandler(List<String> parameterNames, Node node) {
        this.parameterNames = parameterNames;
        this.p = (Parameter) node;
    }

    public boolean handle() {
        boolean found = false;
        if (!parameterNames.isEmpty()) {
            List<Node> childrenNodes = p.getChildNodes();
            for (Node childrenNode : childrenNodes) {
                if (childrenNode instanceof SimpleName) {
                    SimpleName sn= (SimpleName) childrenNode;
                    found = parameterNames.contains(sn.getIdentifier());
                }
            }
        }

        return found;
    }
}
