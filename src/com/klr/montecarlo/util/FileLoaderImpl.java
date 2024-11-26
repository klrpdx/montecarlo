package com.klr.montecarlo.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileLoaderImpl implements DataLoader {

    private final String filePath;
    private BufferedReader reader;

    public FileLoaderImpl(String filePath) {
        this.filePath = filePath;
    }

    private String nextLine() throws IOException {
        if (reader == null) {
            loadFile();
        }
        return reader.readLine();
    }

    @Override
    public SimulationData getSimulationData() throws IOException {
        String line = null;
        int lineNumber = 0;
        SimulationData simulationData = new SimulationData();
        while ((line = this.nextLine()) != null) {
            lineNumber++;
            String[] data = line.split(",");
            if (data.length != 3) {
                throw new IOException("Malformed CSV file on line "+lineNumber);
            }
            simulationData.add(Double.parseDouble(data[1]),Double.parseDouble(data[2]));
        }
        return simulationData;
    }

    private void loadFile() throws IOException  {
        reader = new BufferedReader(new FileReader(filePath));
    }
}

