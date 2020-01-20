package com.jmsmuy;

import static com.jmsmuy.Main.RUBIK_SIZE;

public class Line {
    private Square[] squares;

    public Line(Square... squares) {
        this.squares = new Square[squares.length];
        for (int i = 0; i < squares.length; i++) {
            this.squares[i] = squares[i];
        }
    }

    public Square getSquare(int i) {
        return squares[i];
    }

    public Line reverseLine() {
        for (int i = 0; i < RUBIK_SIZE / 2; i++) {
            Square aux = squares[i];
            squares[i] = squares[RUBIK_SIZE -1 - i];
            squares[RUBIK_SIZE - 1 - i] = aux;
        }
        return this;
    }
}
