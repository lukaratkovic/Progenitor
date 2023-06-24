package examples;

import enums.CrossoverMethod;
import enums.EndCondition;
import enums.SelectionMethod;
import model.CharacterValueGene;
import model.Chromosome;
import model.Progenitor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Example of the Progenitor genetic algorithm using CharacterValueGene whose goal it is to create a predefined string.
 */
public class CharacterExample {
    public static void main(String[] args) {
        // Allowed characters
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ !";
        List<Character> charList = characters.chars().mapToObj(ch->(char)ch).toList();

        // Declaring template gene
        CharacterValueGene templateGene = new CharacterValueGene(charList);
        // Declaring template chromosome, with a defined length and template gene
        Chromosome c = new Chromosome(52, templateGene);

        // Using Builder Pattern to create a Progenitor object with custom parameters
        Progenitor progenitor = new Progenitor.Builder(c)
                .populationSize(100)
                .fitness(CharacterExample::fitness)
                .elitismCount(2)
                .selectionMethod(SelectionMethod.RANK)
                .crossoverMethod(CrossoverMethod.ONE_POINT)
                .mutationProbability(0.01)
                .endCondition(EndCondition.TARGET_FITNESS)
                .targetFitness(52)
                .build();

        // Running the genetic algorithm
        progenitor.run();

        // Print run results
        progenitor.getRunResult().print();

        // Fetching and outputting the best chromosome of the final generation
        Chromosome best = progenitor.getRunResult().getBestChromosome();
        String result = best.getGenes().stream()
                .map(g -> g.getValue().toString())
                .collect(Collectors.joining());
        System.out.println(result);
    }

    /**
     * Custom fitness function
     * @param c Chromosome whose fitness is being calculated
     * @return The fitness of the Chromosome, in this case number of letters that match the target string
     */
    public static Double fitness(Chromosome c){
        double fitness = 0;
        String target = "Progenitor is a Java library for genetic algorithms!";
        List<Character> chars = target.chars().mapToObj(ch -> (char) ch).toList();
        for (int i = 0; i < c.getLength(); i++) {
            if(c.getGenes().get(i).getValue() == chars.get(i)) fitness++;
        }
        return fitness;
    }
}
