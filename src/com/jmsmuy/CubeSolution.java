package com.jmsmuy;

import java.util.ArrayList;
import java.util.List;

public class CubeSolution {
    List<ValidMoves> solutionMoveList;
    private boolean solutionFound = false;
    List<CubeSolution> superSolutions;

    public CubeSolution() {
        superSolutions = new ArrayList<>();
        solutionMoveList = new ArrayList<>();
    }

    public CubeSolution(List<ValidMoves> solutionMoveList) {
        superSolutions = new ArrayList<>();
        this.solutionMoveList = solutionMoveList;
    }

    public CubeSolution(CubeSolution cubeSolution) {
        superSolutions = new ArrayList<>();
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

    public void addSuperSolution(CubeSolution superSolution) {
        superSolutions.add(superSolution);
    }

    public List<CubeSolution> getSuperSolutions() {
        return superSolutions;
    }

    public void printSteps() {
        for(ValidMoves move : solutionMoveList){
            System.out.print(String.format(" %s ", move.getMethodName()));
        }
        System.out.println();
    }
}
