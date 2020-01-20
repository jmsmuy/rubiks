package com.jmsmuy;

import java.util.ArrayList;
import java.util.List;

import static com.jmsmuy.Main.RUBIK_SIZE;

public class CubeSide {
    private final String sideName;
    private final SquareColor sideColor;
    private Line[] columns;
    private Line[] rows;

    public CubeSide(SquareColor sideColor, String sideName) {
        this.sideName = sideName;

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

    /**
     * Copy constructor
     * @param originalSide
     */
    public CubeSide(CubeSide originalSide) {
        this.sideName = originalSide.sideName;
        this.sideColor = originalSide.sideColor;

        this.rows = new Line[originalSide.rows.length];
        this.columns = new Line[originalSide.columns.length];

        for(int x = 0; x < this.rows.length; x++) {
            this.rows[x] = new Line(originalSide.rows[x].getSquares());
            this.columns[x] = new Line(originalSide.columns[x].getSquares());
        }

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
        updateRows(i);
    }

    public void setRow(int i, Line newRow) {
        rows[i] = newRow;
        updateColumns(i);
    }

    private void updateRows(int position) {
        for(int i = 0; i < RUBIK_SIZE; i++) {
            rows[i].setSquare(position, columns[position].getSquare(i));
        }
    }

    private void updateColumns(int position) {
        for(int i = 0; i < RUBIK_SIZE; i++) {
            columns[i].setSquare(position, rows[position].getSquare(i));
        }
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
        boolean errorFound = false;
        for (int x = 0; x < RUBIK_SIZE; x++) {
            for (int y = 0; y < RUBIK_SIZE; y++) {
                if (!columns[y].getSquare(x).getColor().equals(rows[x].getSquare(y).getColor())) {
                    System.out.println(String.format("Error found on column vs row on %s on %d,%d - row says %s and column says %s",
                            sideName, x, y, rows[x].getSquare(y).getColor().getShortName(),
                            columns[y].getSquare(x).getColor().getShortName()));
                    errorFound = true;
                }
            }
        }
        return errorFound;
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

    public boolean isSolved() {
        for(int x = 0; x < RUBIK_SIZE; x++) {
            for(int y = 0; y < RUBIK_SIZE; y++) {
                if(!rows[y].getSquare(x).getColor().equals(columns[x].getSquare(y).getColor())){
                    System.out.println(String.format("Error on discrepancies on cube! should do a check!!!  %d, %d, row: %s and col: %s", x, y,
                            rows[y].getSquare(x).getColor().getShortName(), columns[x].getSquare(y).getColor().getShortName()));
                }
                if(!rows[y].getSquare(x).getColor().equals(sideColor)){
                    return false;
                }
            }
        }
        return true;
    }
}
