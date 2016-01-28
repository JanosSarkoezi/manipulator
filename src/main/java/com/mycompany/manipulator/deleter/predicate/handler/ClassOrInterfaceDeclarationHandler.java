package com.mycompany.manipulator.deleter.predicate.handler;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

/**
 *
 * @author saj
 */
public class ClassOrInterfaceDeclarationHandler {

    private final String classOrInterface;
    private final ClassOrInterfaceDeclaration coid;

    public ClassOrInterfaceDeclarationHandler(String classOrInterface, Node node) {
        this.classOrInterface = classOrInterface;
        this.coid = (ClassOrInterfaceDeclaration) node;
    }

    public boolean handle() {
        return classOrInterface != null && classOrInterface.equals(coid.getName());
    }
}
