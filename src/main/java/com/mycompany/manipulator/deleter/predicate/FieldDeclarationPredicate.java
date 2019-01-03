package com.mycompany.manipulator.deleter.predicate;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.apache.commons.collections4.Predicate;

/**
 * @author saj
 */
public class FieldDeclarationPredicate implements Predicate<FieldDeclaration> {

    private final List<String> fieldNames = new ArrayList<>();

    @Override
    public boolean evaluate(FieldDeclaration fieldDeclaration) {
        if (fieldNames.isEmpty()) {
            throw new IllegalStateException("fieldName must be not null");
        }

        boolean found = false;

        List<VariableDeclarator> variables = fieldDeclaration.getVariables();
        for (VariableDeclarator variable : variables) {
            if (fieldNames.contains(variable.getName().asString())) {
                found = true;
                break;
            }
        }

        return found;
    }

    public FieldDeclarationPredicate onField(@NotNull String fieldName) {
        if (!fieldNames.contains(fieldName)) {
            fieldNames.add(fieldName);
        }

        return this;
    }
    
    public FieldDeclarationPredicate onFields(@NotNull List<String> fieldNames) {
        for (String fieldName : fieldNames) {
            onField(fieldName);
        }

        return this;
    }
}
