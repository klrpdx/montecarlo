package com.klr.montecarlo;

import com.klr.montecarlo.util.FileLoaderImpl;
import com.klr.montecarlo.util.SimulationData;
import com.klr.montecarlo.util.SimulationResults;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MonteCarloTest {

    @Mock
    private FileLoaderImpl fileLoader;

    private SimulationData simulationData;
    private MonteCarlo mc;


    @BeforeEach
    void setUp() {
        mc = new MonteCarlo(fileLoader);
        mc.setSeed(.5);
    }

    @Test
    void testOutput() throws Exception {
        final double mean  = .07;
        final double std  = .01;
        final int iterations = 1;
        simulationData = new SimulationData();
        simulationData.add(0,0);

        when(fileLoader.getSimulationData()).thenReturn(simulationData);
        SimulationResults simResults = mc.simulate(100.00, 1, mean, std, iterations);
        assertEquals(107.00, simResults.getMeanEndingBalance());
    }

    @Test
    void testOutputMultiYear() throws Exception {
        final double mean  = .07;
        final double std  = .01;
        final double startingBalance = 100.0;
        final int iterations = 1;
        final int years = 2;
        simulationData = new SimulationData();
        simulationData.add(0,0);
        simulationData.add(0,0);

        when(fileLoader.getSimulationData()).thenReturn(simulationData);

        SimulationResults simResults = mc.simulate(startingBalance, years, mean, std, iterations);
        assertEquals(114.49, simResults.getMeanEndingBalance());
    }

    @Test
    void testOutputMultiLineWithdrawal() throws Exception {
        final double mean  = .07;
        final double std  = .01;
        final double startingBalance = 100.0;
        final int iterations = 1;
        final int years = 3;
        simulationData = new SimulationData();
        simulationData.add(20,0);
        simulationData.add(20,0);
        simulationData.add(20,0);

        when(fileLoader.getSimulationData()).thenReturn(simulationData);
        SimulationResults simResults = mc.simulate(startingBalance, years, mean, std, iterations);
        assertEquals(53.71, simResults.getMeanEndingBalance());
    }

    @Test
    void testOutputMultiLineWithdrawalAndIncome() throws Exception {
        final double mean  = .07;
        final double std  = .01;
        final double startingBalance = 100.0;
        final int iterations = 1;
        final int years = 3;
        simulationData = new SimulationData();
        simulationData.add(20,50);
        simulationData.add(20,50);
        simulationData.add(20,50);

        when(fileLoader.getSimulationData()).thenReturn(simulationData);

        SimulationResults simResults = mc.simulate(startingBalance, years, mean, std, iterations);
        assertEquals(53.71, simResults.getMeanEndingBalance());
    }



    @Test
    void testTwoIterations() throws Exception {
        final double mean  = .07;
        final double std  = .01;
        final double startingBalance = 100.0;
        final int years = 1;
        final int iterations = 2;
        simulationData = new SimulationData();
        simulationData.add(20,50);

        when(fileLoader.getSimulationData()).thenReturn(simulationData);

        SimulationResults simResults = mc.simulate(startingBalance, years, mean, std, iterations);
        assertEquals(85.6, simResults.getMeanEndingBalance());
    }

    @Test
    void testMultiYearMultiIterations() throws Exception {
        final double mean  = .07;
        final double std  = .01;
        final double startingBalance = 100.0;
        final int years = 3;
        final int iterations = 2;
        simulationData = new SimulationData();
        simulationData.add(20,50);
        simulationData.add(20,50);
        simulationData.add(20,50);

        when(fileLoader.getSimulationData()).thenReturn(simulationData);

        SimulationResults simResults = mc.simulate(startingBalance, years, mean, std, iterations);
        assertEquals(53.71, simResults.getMeanEndingBalance());
    }

    @Test
    void yearsCheck() throws Exception {
        final double mean  = .07;
        final double std  = .01;
        final double startingBalance = 100.0;
        final int years = 3;
        final int iterations = 2;
        simulationData = new SimulationData();
        simulationData.add(20,50);
        simulationData.add(20,50);

        when(fileLoader.getSimulationData()).thenReturn(simulationData);

        assertThrows(Exception.class, () -> {
            mc.simulate(startingBalance, years, mean, std, iterations);
        });
    }

    @Test
    void testMedian() throws Exception {
        final double mean  = .07;
        final double std  = .01;
        final double startingBalance = 100.0;
        final int years = 3;
        final int iterations = 3;
        simulationData = new SimulationData();
        simulationData.add(20,50);
        simulationData.add(20,50);
        simulationData.add(20,50);

        when(fileLoader.getSimulationData()).thenReturn(simulationData);

        SimulationResults simResults = mc.simulate(startingBalance, years, mean, std, iterations);
        assertEquals(0.0, simResults.getMedianEndingBalance());
    }

    @Test
    void testStandardDev() throws Exception {
        final double mean  = .07;
        final double std  = .01;
        final double startingBalance = 100.0;
        final int years = 3;
        final int iterations = 3;
        simulationData = new SimulationData();
        simulationData.add(20,50);
        simulationData.add(20,50);
        simulationData.add(20,50);

        when(fileLoader.getSimulationData()).thenReturn(simulationData);

        SimulationResults simResults = mc.simulate(startingBalance, years, mean, std, iterations);
        assertEquals(0, simResults.getStandardDeviation());
    }



}