package org.spacestation23.model.item.exceptions;

import java.util.List;

public class RemoveItemStacksException extends Exception {

    private List<NoItemStackException> errors;

    public RemoveItemStacksException(List<NoItemStackException> errors) {
        this.setErrors(errors);
    }

    public List<NoItemStackException> getErrors() {
        return errors;
    }

    private void setErrors(List<NoItemStackException> errors) {
        this.errors = errors;
    }
}
