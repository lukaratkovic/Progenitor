package model;

import java.util.List;

public class RunResult {
    private long executionTime;
    private Chromosome bestChromosome;
    private int generationCount;
    private double finalFitness;

    RunResult() {}

    public long getExecutionTime() {
        return executionTime;
    }

    void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public Chromosome getBestChromosome() {
        return bestChromosome;
    }

    void setBestChromosome(Chromosome bestChromosome) {
        this.bestChromosome = bestChromosome;
    }

    public int getGenerationCount() {
        return generationCount;
    }

    void setGenerationCount(int generationCount) {
        this.generationCount = generationCount;
    }

    public double getFinalFitness() {
        return finalFitness;
    }

    void setFinalFitness(double finalFitness) {
        this.finalFitness = finalFitness;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("RUN RESULTS\n")
                .append("Total generations: ").append(generationCount).append("\n")
                .append("Total execution time: ").append(executionTime).append("ms\n")
                .append("Final fitness: ").append(finalFitness).append("\n");
        return sb.toString();
    }
}
