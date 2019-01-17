package com.mycompany.manipulator.remover.mediator.interfaces;

public interface AttributeMediator extends LocationMediator {
    LocationMediator withAttribute(String key, String value);
}