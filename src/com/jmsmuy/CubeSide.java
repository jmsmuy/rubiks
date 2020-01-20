package com.jmsmuy;

import java.util.ArrayList;
import java.util.List;

import static com.jmsmuy.Main.RUBIK_SIZE;

public class CubeSide {
    private final SquareColor sideColor;
    private Line[] columns;
    private Line[] rows;

    public CubeSide(SquareColor sideColor) {
        Square[][] matrix = new Square[RUBIK_SIZE][RUBIK_SIZE];
        for (int i = 0; i < RUBIK_SIZE; i++) {
            for (int p = 0; p < RUBIK_SIZE; p++) {
                matrix[i][p] = new Square();
                matrix[i][p].setColor(sideColor);
            }
        }
        columns = new Line[RUBIK_SIZE];
        rows = new Line[RUBIK_SIZE];
        for (int i = 0; i < RUBIK_SIZE; i++) {
            Square[] squaresForColumn = new Square[RUBIK_SIZE];
            for (int p = 0; p < RUBIK_SIZE; p++) {
                squaresForColumn[p] = matrix[i][p];
            }
            columns[i] = new Line(squaresForColumn);
        }

        for (int i = 0; i < RUBIK_SIZE; i++) {
            Square[] squaresForColumn = new Square[RUBIK_SIZE];
            for (int p = 0; p < RUBIK_SIZE; p++) {
                squaresForColumn[p] = matrix[p][i];
            }
            rows[i] = new Line(squaresForColumn);
        }

        this.sideColor = sideColor;
    }

    public void rotateClockwise() {
        Line[] columnsAux = new Line[RUBIK_SIZE];
        for (int i = 0; i < RUBIK_SIZE; i++) {
            columnsAux[i] = columns[i];
        }
        for (int i = 0; i < RUBIK_SIZE; i++) {
            columns[i] = rows[RUBIK_SIZE - 1 - i];
            rows[RUBIK_SIZE - 1 - i] = columnsAux[RUBIK_SIZE - 1 - i].reverseLine();
        }
    }

    public void rotateCounterClockwise() {
        Line[] columnsAux = new Line[RUBIK_SIZE];
        for (int i = 0; i < RUBIK_SIZE; i++) {
            columnsAux[i] = columns[i];
        }
        for (int i = 0; i < RUBIK_SIZE; i++) {
            columns[i] = rows[i].reverseLine();
            rows[i] = columnsAux[RUBIK_SIZE - 1 - i];
        }
    }

    public Line getColumn(int i) {
        return columns[i];
    }

    public Line getRow(int i) {
        return rows[i];
    }

    public SquareColor getSideColor() {
        return sideColor;
    }

    public void setColumn(int i, Line newColumn) {
        columns[i] = newColumn;
    }

    public void setRow(int i, Line newRow) {
        rows[i] = newRow;
    }

    public void print(String sideName) {
        for (int i = 0; i < RUBIK_SIZE; i++) {
            for (int p = 0; p < RUBIK_SIZE; p++) {
                System.out.print(String.format(" %s ", columns[p].getSquare(i).getColor().getShortName()));
            }
            if (i == RUBIK_SIZE / 2) {
                System.out.println(sideName);
            } else {
                System.out.println();
            }
        }
    }

    public boolean checkColumnsVsRows() {
        for (int x = 0; x < RUBIK_SIZE; x++) {
            for (int y = 0; y < RUBIK_SIZE; y++) {
                if (!columns[y].getSquare(x).getColor().equals(rows[x].getSquare(y).getColor())) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Square> getAllByColor(SquareColor color) {
        List<Square> squares = new ArrayList<>();
        for (int x = 0; x < RUBIK_SIZE; x++) {
            for (int y = 0; y < RUBIK_SIZE; y++) {
                Square currentSquare = columns[x].getSquare(y);
                if (currentSquare.getColor().equals(color)) {
                    squares.add(currentSquare);
                }
            }
        }
        return squares;
    }
}
