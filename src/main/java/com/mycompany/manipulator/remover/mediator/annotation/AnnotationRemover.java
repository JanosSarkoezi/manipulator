package com.mycompany.manipulator.remover.mediator.annotation;

import java.util.List;

import com.github.javaparser.ast.Node;
import com.mycompany.manipulator.remover.mediator.interfaces.Remover;

public class AnnotationRemover implements Remover {
    private List<Node> nodes;

    public AnnotationRemover(List<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public void remove() {
        nodes.forEach(Node::remove);
    }

}