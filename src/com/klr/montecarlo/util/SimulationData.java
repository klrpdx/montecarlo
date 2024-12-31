package com.klr.montecarlo.util;

import java.util.ArrayList;

public class SimulationData {

    private final ArrayList<Double> expenses = new ArrayList<>();

    public void add(double expense) {
        expenses.add(expense);
    }

    public double getExpense(int index) {
        return expenses.get(index);
    }

    public int getNumberOfYears() {
        return expenses.size();
    }

}
