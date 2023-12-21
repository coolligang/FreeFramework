package com.lg.modle.other;

public enum State {
    WORKING(1), SUSPEND(0), END(-1);

    private int number;

    State(int number) {
        this.number = number;
    }

    public int getNumber() {
        return this.number;
    }

    public static State getState(int number) {
        for (State state : State.values()) {
            if (state.getNumber() == number) {
                return state;
            }
        }
        return null;
    }
}
