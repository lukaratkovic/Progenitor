package examples;

import enums.CrossoverMethod;
import enums.EndCondition;
import enums.SelectionMethod;
import model.Chromosome;
import model.IntegerValueGene;
import model.Progenitor;

public class IntegerExample {
    public static void main(String[] args) {
        IntegerValueGene templateGene = new IntegerValueGene(1, 11);
        Chromosome c = new Chromosome(10, templateGene);

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

        progenitor.run();
    }

    private static Double fitness(Chromosome c) {
        return (double) c.getGenes().stream()
                .mapToInt(g -> (Integer) g.getValue())
                .sum();
    }
}
