package examples;

import enums.CrossoverMethod;
import enums.EndCondition;
import enums.SelectionMethod;
import model.Chromosome;
import model.IntegerValueGene;
import model.Progenitor;

/**
 * Example of the Progenitor genetic algorithm using IntegerValueGene whose goal it is to create a Chromosome of 10 numbers between 1 and 10 with the highest total value
 */
public class IntegerExample {
    public static void main(String[] args) {
        // Declaring template gene
        IntegerValueGene templateGene = new IntegerValueGene(1, 11);
        // Declaring template chromosome, with a defined length and template gene
        Chromosome c = new Chromosome(10, templateGene);

        // Using Builder Pattern to create a Progenitor object with custom parameters
        Progenitor progenitor = new Progenitor.Builder(c)
                .populationSize(100)
                .endCondition(EndCondition.TARGET_FITNESS)
                .targetFitness(100)
                .crossoverMethod(CrossoverMethod.UNIFORM)
                .mutationProbability(0.01)
                .selectionMethod(SelectionMethod.RANK)
                .elitismCount(2)
                .fitness(IntegerExample::fitness)
                .build();

        // Running the genetic algorithm
        progenitor.run();
    }

    /**
     * Custom fitness function
     * @param c Chromosome whose fitness is being calculated
     * @return The fitness of the Chromosome, in this case total sum of all genes' values
     */
    private static Double fitness(Chromosome c) {
        return (double) c.getGenes().stream()
                .mapToInt(g -> (Integer) g.getValue())
                .sum();
    }
}
