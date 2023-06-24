package examples;

import enums.CrossoverMethod;
import enums.EndCondition;
import enums.SelectionMethod;
import model.BinaryGene;
import model.Chromosome;
import model.Progenitor;
import model.RunResult;

/**
 * Example of the Progenitor genetic algorithm using BinaryGene whose goal it is to create a Chromosome of 100 binary genes with all values being "1".
 */
public class BinaryExample {
    public static void main(String[] args) {
        // Declaring template gene
        BinaryGene templateGene = new BinaryGene();
        // Declaring template chromosome, with a defined length and template gene
        Chromosome c = new Chromosome(100, templateGene);

        // Using Builder Pattern to create a Progenitor object with custom parameters
        Progenitor progenitor = new Progenitor.Builder(c)
                .populationSize(10)
                .endCondition(EndCondition.TARGET_FITNESS)
                .targetFitness(c.getLength())
                .crossoverMethod(CrossoverMethod.UNIFORM)
                .mutationProbability(0.01)
                .selectionMethod(SelectionMethod.TOURNAMENT)
                .tournamentK(5)
                .elitismCount(2)
                .fitness(BinaryExample::fitness)
                .build();

        // Running the genetic algorithm
        progenitor.run();
        // Print run results
        System.out.println(progenitor.getRunResult());
    }

    /**
     * Custom fitness function
     * @param c Chromosome whose fitness is being calculated
     * @return The fitness of the Chromosome, in this case the number of binary genes with value of 1
     */
    public static Double fitness(Chromosome c){
        return (double) c.getGenes().stream()
                .filter(g -> (Boolean) g.getValue()).count();
    }
}
