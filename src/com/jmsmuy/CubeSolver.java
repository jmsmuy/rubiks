package com.jmsmuy;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CubeSolver {

    private static final int MAX_MOVES_FOR_SOLVE = 8;
    private final Cube cube;

    public CubeSolver(Cube cube) {
        this.cube = cube;
    }

    public List<ValidMoves> solveCube() {
        try {
            CubeSolution solution = new CubeSolution();
            solution = obtainSolution(cube, MAX_MOVES_FOR_SOLVE, solution);
            if(solution == null || solution.solutionMoveList == null || solution.solutionMoveList.isEmpty()) {
                System.out.println("Error getting solution! No solution was found within " + MAX_MOVES_FOR_SOLVE + " moves");
                return null;
            }
            return solution.solutionMoveList;
        } catch (Exception e) {
            System.out.println("Error getting solution!");
            return null;
        }
    }

    private CubeSolution obtainSolution(Cube cube, int movesRemaining, CubeSolution solution) throws InvocationTargetException, IllegalAccessException {
        if (cube.checkIfSolved()) {
            solution.setFound(true);
            return solution;
        }
        if (movesRemaining <= 0) {
            return null;
        }
        List<CubeSolution> possibleSolutions = new ArrayList<>();
        for (ValidMoves move : ValidMoves.values()) {
            CubeSolution newSolution = solution.copySolution();
            newSolution.addMove(move);
            Cube newCube = cube.copyCube();
            move.getMethod().invoke(newCube);
            newSolution = obtainSolution(newCube, movesRemaining - 1, newSolution);
            if (newSolution != null) {
                possibleSolutions.add(newSolution);
            }
        }
        return selectBestSolution(possibleSolutions);
    }

    private CubeSolution selectBestSolution(List<CubeSolution> possibleSolutions) {
        CubeSolution bestSolution = null;
        for (CubeSolution solution : possibleSolutions) {
            if (bestSolution == null || solution.solutionMoveList.size() < bestSolution.solutionMoveList.size()) {
                bestSolution = solution;
            }
        }
        return bestSolution;
    }
}
