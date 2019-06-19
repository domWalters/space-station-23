package org.spacestation23.map.exceptions;

import org.spacestation23.character.Pawn;

public class FailedMovementException extends Exception {

    private Pawn pawn;
    private String direction;   // U, R, L, D

    public FailedMovementException(Pawn pawn, String direction) {
        this.setPawn(pawn);
        this.setDirection(direction);
    }

    public Pawn getPawn() {
        return pawn;
    }

    private void setPawn(Pawn pawn) {
        this.pawn = pawn;
    }

    public String getDirection() {
        return direction;
    }

    private void setDirection(String direction) {
        this.direction = direction;
    }
}
