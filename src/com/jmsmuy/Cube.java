package com.jmsmuy;

import java.util.ArrayList;
import java.util.List;

import static com.jmsmuy.Main.*;

public class Cube {

    private CubeSide frontSide;
    private CubeSide rightSide;
    private CubeSide leftSide;
    private CubeSide backSide;
    private CubeSide upSide;
    private CubeSide downSide;

    public Cube() {
        frontSide = new CubeSide(SquareColor.GREEN, FRONT_SIDE);
        rightSide = new CubeSide(SquareColor.RED, RIGHT_SIDE);
        leftSide = new CubeSide(SquareColor.ORANGE, LEFT_SIDE);
        backSide = new CubeSide(SquareColor.BLUE, BACK_SIDE);
        upSide = new CubeSide(SquareColor.WHITE, UP_SIDE);
        downSide = new CubeSide(SquareColor.YELLOW, DOWN_SIDE);
    }

    /**
     * Copy constructor
     * @param cube
     */
    public Cube(Cube cube) {
        frontSide = new CubeSide(cube.getFrontSide());
        rightSide = new CubeSide(cube.getRightSide());
        leftSide = new CubeSide(cube.getLeftSide());
        backSide = new CubeSide(cube.getBackSide());
        upSide = new CubeSide(cube.getUpSide());
        downSide = new CubeSide(cube.getDownSide());
    }

    public void doRn() {
        rightSide.rotateClockwise();

        Line frontColumn = frontSide.getColumn(RUBIK_SIZE - 1);
        Line upColumn = upSide.getColumn(RUBIK_SIZE - 1);
        Line backColumn = backSide.getColumn(0);
        Line downColumn = downSide.getColumn(RUBIK_SIZE - 1);

        frontSide.setColumn(RUBIK_SIZE - 1, downColumn);
        upSide.setColumn(RUBIK_SIZE - 1, frontColumn);
        backSide.setColumn(0, upColumn.reverseLine());
        downSide.setColumn(RUBIK_SIZE - 1, backColumn.reverseLine());

        if (DEBUG) {
            checkCube();
        }
    }

    public void doRr() {
        rightSide.rotateCounterClockwise();

        Line frontColumn = frontSide.getColumn(RUBIK_SIZE - 1);
        Line upColumn = upSide.getColumn(RUBIK_SIZE - 1);
        Line backColumn = backSide.getColumn(0);
        Line downColumn = downSide.getColumn(RUBIK_SIZE - 1);

        frontSide.setColumn(RUBIK_SIZE - 1, upColumn);
        upSide.setColumn(RUBIK_SIZE - 1, backColumn.reverseLine());
        backSide.setColumn(0, downColumn.reverseLine());
        downSide.setColumn(RUBIK_SIZE - 1, frontColumn);

        if (DEBUG) {
            checkCube();
        }
    }

    public void doLn() {
        leftSide.rotateClockwise();

        Line frontColumn = frontSide.getColumn(0);
        Line upColumn = upSide.getColumn(0);
        Line backColumn = backSide.getColumn(RUBIK_SIZE - 1);
        Line downColumn = downSide.getColumn(0);

        frontSide.setColumn(0, upColumn);
        upSide.setColumn(0, backColumn.reverseLine());
        backSide.setColumn(RUBIK_SIZE - 1, downColumn.reverseLine());
        downSide.setColumn(0, frontColumn);

        if (DEBUG) {
            checkCube();
        }
    }

    public void doLr() {
        leftSide.rotateCounterClockwise();

        Line frontColumn = frontSide.getColumn(0);
        Line upColumn = upSide.getColumn(0);
        Line backColumn = backSide.getColumn(RUBIK_SIZE - 1);
        Line downColumn = downSide.getColumn(0);

        frontSide.setColumn(0, downColumn);
        upSide.setColumn(0, frontColumn);
        backSide.setColumn(RUBIK_SIZE - 1, upColumn.reverseLine());
        downSide.setColumn(0, backColumn.reverseLine());

        if (DEBUG) {
            checkCube();
        }
    }

    public void doUn() {
        upSide.rotateClockwise();

        Line frontRow = frontSide.getRow(0);
        Line leftRow = leftSide.getRow(0);
        Line backRow = backSide.getRow(RUBIK_SIZE - 1);
        Line rightRow = rightSide.getRow(0);

        frontSide.setRow(0, rightRow);
        leftSide.setRow(0, frontRow);
        backSide.setRow(RUBIK_SIZE - 1, leftRow.reverseLine());
        rightSide.setRow(0, backRow.reverseLine());

        if (DEBUG) {
            checkCube();
        }
    }

    public void doUr() {
        upSide.rotateCounterClockwise();

        Line frontRow = frontSide.getRow(0);
        Line leftRow = leftSide.getRow(0);
        Line backRow = backSide.getRow(RUBIK_SIZE - 1);
        Line rightRow = rightSide.getRow(0);

        frontSide.setRow(0, leftRow);
        leftSide.setRow(0, backRow.reverseLine());
        backSide.setRow(RUBIK_SIZE - 1, rightRow.reverseLine());
        rightSide.setRow(0, frontRow);

        if (DEBUG) {
            checkCube();
        }
    }

    public void doBn() {
        backSide.rotateClockwise();

        Line upRow = upSide.getRow(0);
        Line downRow = downSide.getRow(RUBIK_SIZE - 1);
        Line rightColumn = rightSide.getColumn(RUBIK_SIZE - 1);
        Line leftColumn = leftSide.getColumn(0);

        upSide.setRow(0, rightColumn);
        downSide.setRow(RUBIK_SIZE - 1, leftColumn);
        rightSide.setColumn(RUBIK_SIZE - 1, downRow.reverseLine());
        leftSide.setColumn(0, upRow.reverseLine());

        if (DEBUG) {
            checkCube();
        }
    }

    public void doBr() {
        backSide.rotateCounterClockwise();

        Line upRow = upSide.getRow(0);
        Line downRow = downSide.getRow(RUBIK_SIZE - 1);
        Line rightColumn = rightSide.getColumn(RUBIK_SIZE - 1);
        Line leftColumn = leftSide.getColumn(0);

        upSide.setRow(0, leftColumn.reverseLine());
        downSide.setRow(RUBIK_SIZE - 1, rightColumn.reverseLine());
        rightSide.setColumn(RUBIK_SIZE - 1, upRow);
        leftSide.setColumn(0, downRow);

        if (DEBUG) {
            checkCube();
        }
    }

    public void doFn() {
        frontSide.rotateClockwise();

        Line upRow = upSide.getRow(RUBIK_SIZE - 1);
        Line downRow = downSide.getRow(0);
        Line rightColumn = rightSide.getColumn(0);
        Line leftColumn = leftSide.getColumn(RUBIK_SIZE - 1);

        upSide.setRow(RUBIK_SIZE - 1, leftColumn.reverseLine());
        downSide.setRow(0, rightColumn.reverseLine());
        rightSide.setColumn(0, upRow);
        leftSide.setColumn(RUBIK_SIZE - 1, downRow);

        if (DEBUG) {
            checkCube();
        }
    }

    public void doFr() {
        frontSide.rotateCounterClockwise();

        Line upRow = upSide.getRow(RUBIK_SIZE - 1);
        Line downRow = downSide.getRow(0);
        Line rightColumn = rightSide.getColumn(0);
        Line leftColumn = leftSide.getColumn(RUBIK_SIZE - 1);

        upSide.setRow(RUBIK_SIZE - 1, rightColumn);
        downSide.setRow(0, leftColumn);
        rightSide.setColumn(0, downRow.reverseLine());
        leftSide.setColumn(RUBIK_SIZE - 1, upRow.reverseLine());

        if (DEBUG) {
            checkCube();
        }
    }

    public void doDn() {
        downSide.rotateClockwise();

        Line frontRow = frontSide.getRow(RUBIK_SIZE - 1);
        Line rightRow = rightSide.getRow(RUBIK_SIZE - 1);
        Line backRow = backSide.getRow(0);
        Line leftRow = leftSide.getRow(RUBIK_SIZE - 1);

        frontSide.setRow(RUBIK_SIZE - 1, leftRow);
        rightSide.setRow(RUBIK_SIZE - 1, frontRow);
        backSide.setRow(0, rightRow.reverseLine());
        leftSide.setRow(RUBIK_SIZE - 1, backRow.reverseLine());

        if (DEBUG) {
            checkCube();
        }
    }

    public void doDr() {
        downSide.rotateCounterClockwise();

        Line frontRow = frontSide.getRow(RUBIK_SIZE - 1);
        Line rightRow = rightSide.getRow(RUBIK_SIZE - 1);
        Line backRow = backSide.getRow(0);
        Line leftRow = leftSide.getRow(RUBIK_SIZE - 1);

        frontSide.setRow(RUBIK_SIZE - 1, rightRow);
        rightSide.setRow(RUBIK_SIZE - 1, backRow.reverseLine());
        backSide.setRow(0, leftRow.reverseLine());
        leftSide.setRow(RUBIK_SIZE - 1, frontRow);

        if (DEBUG) {
            checkCube();
        }
    }

    public CubeSide getFrontSide() {
        return frontSide;
    }

    public CubeSide getRightSide() {
        return rightSide;
    }

    public CubeSide getLeftSide() {
        return leftSide;
    }

    public CubeSide getBackSide() {
        return backSide;
    }

    public CubeSide getUpSide() {
        return upSide;
    }

    public CubeSide getDownSide() {
        return downSide;
    }

    public void printCube() {
        upSide.print("Up");
        frontSide.print("Front");
        downSide.print("Down");
        leftSide.print("Left");
        rightSide.print("Right");
        backSide.print("Back");
    }

    /**
     * This method doesn't completely check the cube, but makes some health sanity checks
     * 1- number of colored squares is correct
     * 2- rows and columns match
     */
    public boolean checkCube() {
        boolean error = false;
        error |= frontSide.checkColumnsVsRows();
        error |= backSide.checkColumnsVsRows();
        error |= rightSide.checkColumnsVsRows();
        error |= leftSide.checkColumnsVsRows();
        error |= upSide.checkColumnsVsRows();
        error |= downSide.checkColumnsVsRows();
        error |= countColors(frontSide, backSide, rightSide, leftSide, upSide, downSide);

        if (error) {
            System.out.println("Errors have been found on the cube!");
        }
        return error;
    }

    private boolean countColors(CubeSide... sides) {
        for (SquareColor color : SquareColor.values()) {
            List<Square> squares = new ArrayList<>();

            for (CubeSide side : sides) {
                squares.addAll(side.getAllByColor(color));
            }

            if (squares.size() != RUBIK_SIZE * RUBIK_SIZE) {
                System.out.println(String.format("Error, color %s is incorrect, it has %d ocurrences", color.getShortName(), squares.size()));
                return true;
            }
        }
        return false;
    }

    /**
     * returns true if cube is solved!
     */
    public boolean checkIfSolved() {
        boolean solved = true;
        solved &= frontSide.isSolved();
        solved &= backSide.isSolved();
        solved &= leftSide.isSolved();
        solved &= rightSide.isSolved();
        solved &= upSide.isSolved();
        solved &= downSide.isSolved();
        return solved;
    }

    public Cube copyCube() {
        Cube newCube = new Cube(this);
        return newCube;
    }
}
