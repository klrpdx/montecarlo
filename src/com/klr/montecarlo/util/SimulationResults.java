package com.klr.montecarlo.util;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.util.Precision;

import java.text.DecimalFormat;

public class SimulationResults {

   private DescriptiveStatistics stats;

   public SimulationResults(double[] results) {
       stats = new DescriptiveStatistics(results);
   }

    public double getMeanEndingBalance() {
        return Precision.round(stats.getMean(),2);
    }

    public double getMedianEndingBalance() {
        double median = Math.sqrt(stats.getVariance());
        return Precision.round(median,2);
    }

    public double getStandardDeviation() {
        return Precision.round(stats.getStandardDeviation(),2);
    }


    public double getPercentile(int percentile) {
        return Precision.round(stats.getPercentile(percentile),2);
    }

    public int getFirstZero() {
       for (int i = 99; i > 1; i--) {
           if (stats.getPercentile(i) <= 0.0) {
               return i;
           }
       }
       return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Mean: %.2f", getMeanEndingBalance())).append("\n");
        sb.append(String.format("Standard Deviation: %.2f", getStandardDeviation())).append("\n");
        sb.append(String.format("5 Percentile: %.2f",  getPercentile(5))).append("\n");
        sb.append(String.format("25 Percentile: %.2f", getPercentile(25))).append("\n");
        sb.append(String.format("50 Percentile: %.2f", getPercentile(50))).append("\n");
        sb.append(String.format("75 Percentile: %.2f", getPercentile(75))).append("\n");
        sb.append(String.format("95 Percentile: %.2f", getPercentile(95))).append("\n");
        sb.append(String.format("Likelihood of lifestyle adjustment: %d%%", getFirstZero())).append("\n");
        return sb.toString();
    }
}
