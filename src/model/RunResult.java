package model;

public class RunResult {
    private long executionTime;
    private Chromosome bestChromosome;
    private int generationCount;
    private double finalFitness;

    RunResult() {}

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }

    public Chromosome getBestChromosome() {
        return bestChromosome;
    }

    public void setBestChromosome(Chromosome bestChromosome) {
        this.bestChromosome = bestChromosome;
    }

    public int getGenerationCount() {
        return generationCount;
    }

    public void setGenerationCount(int generationCount) {
        this.generationCount = generationCount;
    }

    public double getFinalFitness() {
        return finalFitness;
    }

    public void setFinalFitness(double finalFitness) {
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
