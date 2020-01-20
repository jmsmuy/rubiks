package com.jmsmuy;

import java.util.ArrayList;
import java.util.List;

public class CubeSolution {
    List<ValidMoves> solutionMoveList;
    private boolean solutionFound = false;

    public CubeSolution() {
        solutionMoveList = new ArrayList<>();
    }

    public CubeSolution(CubeSolution cubeSolution) {
        this.solutionFound = cubeSolution.solutionFound;
        this.solutionMoveList = new ArrayList<>(cubeSolution.solutionMoveList);
    }

    public List<ValidMoves> getSolutionMoveList() {
        return solutionMoveList;
    }

    public void setFound(boolean solutionFound) {
        this.solutionFound = solutionFound;
    }

    public CubeSolution copySolution() {
        return  new CubeSolution(this);
    }

    public void addMove(ValidMoves move) {
        solutionMoveList.add(move);
    }
}
