package com.jmsmuy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static final boolean DEBUG = true;
    public static final int RUBIK_SIZE = 3;
    private static final long BENCH_TIME = 60000;
    private static final long RANDOM_TIME = 2000;

    public static final String FRONT_SIDE = "F";
    public static final String RIGHT_SIDE = "R";
    public static final String LEFT_SIDE = "L";
    public static final String BACK_SIDE = "B";
    public static final String UP_SIDE = "U";
    public static final String DOWN_SIDE = "D";
    private final List<Method> usefulMethods;

    private Cube cube;
    private boolean continueRunning = true;

    public static void main(String[] args) {
        new Main();

    }

    public Main() {
        cube = new Cube();
        cube.printCube();

        usefulMethods = new ArrayList<>();
        Method[] allMethods = Cube.class.getDeclaredMethods();
        for (Method method : allMethods) {
            if (method.getName().contains("do")) {
                usefulMethods.add(method);
            }
        }

        Scanner sc = new Scanner(System.in);

        while (continueRunning) {
            Commander.processCommand(sc.nextLine(), this);
        }
    }

    public void rotateCube(String instruction) {
        Method calledMethod = null;
        for (Method method : usefulMethods) {
            if (method.getName().contains(instruction)) {
                calledMethod = method;
            }
        }
        if (calledMethod == null) {
            showErrorMessage();
            return;
        }
        try {
            calledMethod.invoke(cube);
        } catch (Exception e) {
            showErrorMessage();
            return;
        }
        print();
        printMovementMade(calledMethod);
    }

    private void printMovementMade(Method calledMethod) {
        System.out.println(String.format(" The movement %s has been made ", calledMethod.getName()));
    }

    public void print() {
        cube.printCube();
    }

    public void finish() {
        continueRunning = false;
    }

    public void showErrorMessage() {
        System.out.println("An error has occured!");
    }

    public void benchmark() {
        long t = System.currentTimeMillis();
        long startTime = t;
        long end = t + BENCH_TIME;
        int opCounter = 0;
        try {
            long progress = 0;
            long lastProgress = 0;
            while ((t = System.currentTimeMillis()) < end) {
                int random = new Random().nextInt(usefulMethods.size());
                usefulMethods.get(random).invoke(cube);
                opCounter++;
                progress = ((t-startTime) *100) / BENCH_TIME;
                if(t % 4000 == 0 && lastProgress != progress){
                    lastProgress = progress;
                    System.out.println(String.format("%d percent completed", progress));
                }
            }
        } catch (Exception e) {
            showErrorMessage();
            return;
        }
        System.out.println(String.format("The amount of %d operations on the cube have been made on a minutes", opCounter));
    }

    public void randomizeCube() {
        long t = System.currentTimeMillis();
        long end = t + RANDOM_TIME;
        try {
            while (System.currentTimeMillis() < end) {
                int random = new Random().nextInt(usefulMethods.size());
                usefulMethods.get(random).invoke(cube);
            }
        } catch (Exception e) {
            showErrorMessage();
            return;
        }
        System.out.println("Cube has been randomized");
        print();
    }

    public void resetCube() {
        cube = new Cube();
        print();
    }

    public void solveCube() {
        long start = System.currentTimeMillis();
        CubeSolver solver = new CubeSolver(cube);
        List<ValidMoves> solution = solver.solveCube();
        for(ValidMoves move : solution) {
            System.out.print(String.format(" %s ", move.toString()));
        }
        long amountTime = (System.currentTimeMillis() - start);
        System.out.println(String.format("It took %d.%d s to find the solution!", amountTime / 1000, amountTime % 1000));
    }

    public void check() {
        boolean error = cube.checkCube();
        if(!error) {
            System.out.println("Cube checks alright!");
        }
    }
}
