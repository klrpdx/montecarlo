package com.klr.montecarlo;

import com.klr.montecarlo.util.DataLoader;
import com.klr.montecarlo.util.FileLoaderImpl;
import com.klr.montecarlo.util.SimulationData;
import com.klr.montecarlo.util.SimulationResults;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.random.RandomDataGenerator;

public class MonteCarlo {

    private final DataLoader fileLoader;
    private double seed = -1;

    public MonteCarlo(DataLoader fileLoader) {
        this.fileLoader = fileLoader;
    }

    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Missing data file path");
            System.exit(1);
        }
        MonteCarlo monte = new MonteCarlo(new FileLoaderImpl(args[0]));
        SimulationResults results = null;
        try {
            results = monte.simulate(1315000.00, 39, .1084, .1735, 100000);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(results);

    }

    public void setSeed(double seed) {
        this.seed = seed;
    }

    public SimulationResults simulate(double startingBalance, int years, double mean, double std, int iterations) throws Exception {

        SimulationData simulationData = fileLoader.getSimulationData();
        if (years > simulationData.getNumberOfYears()) {
            throw new Exception(String.format("Simulation data file does not cover %d years ", years));
        }

        double[]  iterationResults = new double[iterations];
        for (int i = 0; i < iterations; i++) {
            iterationResults[i] = runIteration(mean, std, years, startingBalance, simulationData);
        }
        SimulationResults results = new SimulationResults(iterationResults);
        return results;
    }



    private double runIteration(double mean, double std, int years, double startingBalance, SimulationData simulationData) throws Exception {

        NormalDistribution nd = new NormalDistribution(mean, std);
        RandomDataGenerator rdg = new RandomDataGenerator();
        double runningBalance = startingBalance;

        for (int year = 0; year < years; year++) {

            double rando = seed < 0 ? rdg.getRandomGenerator().nextDouble() : seed;

            runningBalance -= simulationData.getExpense(year);
            double annualReturn =  nd.inverseCumulativeProbability(rando);
            runningBalance = runningBalance * (1+(annualReturn));
            if (runningBalance < 0) {
                runningBalance = 0;
            }
        }

        return runningBalance;
    }
}