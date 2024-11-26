package com.klr.montecarlo.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FileLoaderTest {

    @Test
    void loadFile() {
        String testFilePath = "resources/test/testfile.csv";
        DataLoader fileLoader = new FileLoaderImpl(testFilePath);
        try {
            SimulationData data = fileLoader.getSimulationData();
            assertAll("Income and Expense",
                    () -> assertEquals(-31052.20, data.getExpense(0))
            );
        }
        catch (Exception e) {
            fail();
        }

    }

    @Test
    void nextLine() {
        String testFilePath = "resources/test/testfile.csv";
        DataLoader fileLoader = new FileLoaderImpl(testFilePath);
        try {
            SimulationData data = fileLoader.getSimulationData();
            assertAll("Income and Expense",
                    () -> assertEquals(-31168.37, data.getExpense(1))
            );
        }
        catch (Exception e) {
            fail();
        }
    }

}
