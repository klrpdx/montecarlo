package com.klr.montecarlo.util;

import java.io.IOException;

public interface DataLoader {
    SimulationData getSimulationData() throws IOException;
}
