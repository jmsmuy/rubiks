package com.jmsmuy;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CubeSolver {

    private static final int MAX_MOVES_FOR_SOLVE = 6;
    private static final int MAX_AMOUNT_POSSIBLE_SOLUTIONS = calculatePossibleSolutions(MAX_MOVES_FOR_SOLVE);

    private static int calculatePossibleSolutions(int maxMovesForSolve) {
        int sum = 0;
        for (int i = 0; i < maxMovesForSolve; i++) {
            sum += (int) Math.pow(ValidMoves.values().length, MAX_MOVES_FOR_SOLVE - i);
        }
        return sum;
    }

    private final Cube cube;
    private AtomicInteger minimumStepsSolution;
    private CubeSolution[] possibleSolutions;
    int numTheadsForSolvePossibilities;
    int[] currentSolutionCounter;

    public CubeSolver(Cube cube, int numThreadsSolvePossibilities) {
        this.cube = cube;
        this.numTheadsForSolvePossibilities = numThreadsSolvePossibilities;
        currentSolutionCounter = new int[numThreadsSolvePossibilities];
    }

    /**
     * This method generates one thread, whose job is to generate all possible solutions (sequence of moves)
     * And then a pool of threads that just test all those solutions
     * Every time a solution is found, all solutions that have this solution as a subsolution get marked as done as well
     *
     * @return
     */
    public List<ValidMoves> solveCubeThreaded() throws Exception {

        // We first create the first batch of possible solutions
        possibleSolutions = new CubeSolution[MAX_AMOUNT_POSSIBLE_SOLUTIONS];
        int topPossibleSolutionsForIterations = calculatePossibleSolutions(MAX_MOVES_FOR_SOLVE - 1);

        for (ValidMoves move : ValidMoves.values()) {
            ArrayList<ValidMoves> list = new ArrayList<>();
            list.add(move);
            possibleSolutions[currentSolutionCounter[0]++] = new CubeSolution(list);
        }
        for(int i = 1; i < currentSolutionCounter.length; i++) {
            currentSolutionCounter[i] = currentSolutionCounter[i-1]+1;
        }

        ValidMoves[] validMoves = ValidMoves.values();
        // After we create the first batch of solutions, we create the first thread
        // Whose job is just to create all possible solutions, from the smallest (in length)
        // Towards the longest ones
        Thread[] possibilitiesGeneratorThreads = new Thread[numTheadsForSolvePossibilities];

        for(int i = 0; i < numTheadsForSolvePossibilities && i < ValidMoves.values().length; i++){
            int finalI = i;
            possibilitiesGeneratorThreads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean finish = false;
                    for (int solNumber = finalI; solNumber < topPossibleSolutionsForIterations && !finish; solNumber = solNumber + numTheadsForSolvePossibilities) {
                        CubeSolution selectedSolution = possibleSolutions[solNumber];
                        if ((selectedSolution == null || selectedSolution.solutionMoveList == null) && finalI == 1) {
                            System.out.println("ERROR HAPPENED!, null was detected on solNumber" + solNumber + " on thread " + finalI);
                            System.out.println("finalI = " + finalI);
                            System.out.println("currentSolutionCounter[i] = " + currentSolutionCounter[finalI]);
                            if(selectedSolution == null) {
                                System.out.println("selectedSolution == null");
                            } else if (selectedSolution.solutionMoveList == null) {
                                System.out.println("solutionMoveList == null");
                            } else {
                                System.out.println("possibleSolutions[solNumber] = ");
                                possibleSolutions[solNumber].printSteps();
                            }
                            finish = true;
                        }
                        if (selectedSolution.solutionMoveList.size() >= MAX_MOVES_FOR_SOLVE || currentSolutionCounter[finalI] >= MAX_AMOUNT_POSSIBLE_SOLUTIONS) {
                            finish = true;
                        }
                        if(!finish) {
                            for (ValidMoves move : validMoves) {
                                CubeSolution newSolution = new CubeSolution(selectedSolution);
                                selectedSolution.addSuperSolution(newSolution);
                                newSolution.addMove(move);
                                possibleSolutions[currentSolutionCounter[finalI]] = newSolution;
                                currentSolutionCounter[finalI] += numTheadsForSolvePossibilities;
                            }
                        }
                    }
                }
            });
            possibilitiesGeneratorThreads[i].start();
        }


//        Timer progressTimer = new Timer();
//        progressTimer.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println(String.format("Creating sequence of moves %f percent completed",
//                        (((float) currentSolutionCounter[0] * 100) / ((float) MAX_AMOUNT_POSSIBLE_SOLUTIONS))));
//            }
//        }, 0, 1000);
        for(int i = 0; i < possibilitiesGeneratorThreads.length; i++) {
            possibilitiesGeneratorThreads[i].join();
        }

//        printCubeSolutions(possibleSolutions);
//        progressTimer.cancel();
        return possibleSolutions[0].solutionMoveList;
    }

    private void printCubeSolutions(CubeSolution[] possibleSolutions) {
        for (CubeSolution solution : possibleSolutions) {
            if (solution != null) {
                solution.printSteps();
            }
        }
    }

    /**
     * @return
     * @deprecated should use threaded version
     */
    public List<ValidMoves> solveCube() {
        minimumStepsSolution = new AtomicInteger(MAX_MOVES_FOR_SOLVE + 1);
        try {
            CubeSolution solution = new CubeSolution();
            solution = obtainSolution(cube, 0, solution);
            if (solution == null || solution.solutionMoveList == null || solution.solutionMoveList.isEmpty()) {
                System.out.println("Error getting solution! No solution was found within " + MAX_MOVES_FOR_SOLVE + " moves");
                return null;
            }
            return solution.solutionMoveList;
        } catch (Exception e) {
            System.out.println("Error getting solution!");
            return null;
        }
    }

    private CubeSolution obtainSolution(Cube cube, int movesDone, CubeSolution solution) throws InvocationTargetException, IllegalAccessException {
        if (cube.checkIfSolved()) {
            solution.setFound(true);
            if (minimumStepsSolution.get() > movesDone) {
                minimumStepsSolution.set(movesDone);
            }
            return solution;
        }
        if (movesDone >= MAX_MOVES_FOR_SOLVE || movesDone >= minimumStepsSolution.get()) {
            return null;
        }
        List<CubeSolution> possibleSolutions = new ArrayList<>();
        ForkJoinPool pool = new ForkJoinPool(ValidMoves.values().length);
        for (ValidMoves move : ValidMoves.values()) {
            final CubeSolution newSolution = solution.copySolution();
            newSolution.addMove(move);
            final Cube newCube = cube.copyCube();
            move.getMethod().invoke(newCube);
            possibleSolutions.add(pool.invoke(new RecursiveTask<CubeSolution>() {
                @Override
                protected CubeSolution compute() {
                    try {
                        return obtainSolution(newCube, movesDone + 1, newSolution);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            }));
        }
        try {
            pool.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
