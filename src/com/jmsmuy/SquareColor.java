package com.jmsmuy;

public enum SquareColor {
    RED("R"), GREEN("G"), YELLOW("Y"), BLUE("B"), WHITE("W"), ORANGE("O");

    private final String shortName;

    SquareColor(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }
}
