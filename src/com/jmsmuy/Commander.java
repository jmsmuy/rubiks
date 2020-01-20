package com.jmsmuy;

public class Commander {
    public static void processCommand(String nextLine, Main operator) {
        if(nextLine.equals("Rn")
                || (nextLine.equals("Rr"))
                || (nextLine.equals("Ln"))
                || (nextLine.equals("Lr"))
                || (nextLine.equals("Dn"))
                || (nextLine.equals("Dr"))
                || (nextLine.equals("Un"))
                || (nextLine.equals("Ur"))
                || (nextLine.equals("Fn"))
                || (nextLine.equals("Fr"))
                || (nextLine.equals("Bn"))
                || (nextLine.equals("Br"))) {
            operator.rotateCube(nextLine);
        } else if(nextLine.equals("Check")){
            operator.check();
        } else if(nextLine.equals("Randomize")){
            operator.randomizeCube();
        } else if(nextLine.equals("Solve")){
            operator.solveCube();
        } else if(nextLine.equals("Benchmark")){
            operator.benchmark();
        } else if(nextLine.equals("Reset")){
            operator.resetCube();
        } else if(nextLine.equals("Print")){
            operator.print();
        } else if(nextLine.equals("Exit")){
            operator.finish();
        } else {
            operator.showErrorMessage();
        }
    }
}
