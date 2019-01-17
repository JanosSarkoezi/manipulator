package com.mycompany.manipulator.remover.mediator.interfaces;

public interface LocationMediator extends Remover {
    Remover onMethod(String methodName);
    Remover onParameter(String parameterName);
    Remover onField(String fieldName);
    Remover onClassOrInterface(String className);
}