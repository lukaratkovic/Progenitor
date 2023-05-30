package examples;

import enums.CrossoverMethod;
import enums.EndCondition;
import enums.SelectionMethod;
import model.BinaryGene;
import model.Chromosome;
import model.Progenitor;

public class BinaryExample {
    public static void main(String[] args) {
        BinaryGene templateGene = new BinaryGene();
        Chromosome c = new Chromosome(100, templateGene);

        Progenitor progenitor = new Progenitor.Builder(c)
                .populationSize(100)
                .endCondition(EndCondition.TARGET_FITNESS)
                .targetFitness(c.getLength())
                .crossoverMethod(CrossoverMethod.UNIFORM)
                .mutationProbability(0.01)
                .selectionMethod(SelectionMethod.RANK)
                .elitismCount(2)
                .fitness(BinaryExample::fitness)
                .build();

        progenitor.run();
    }

    public static Double fitness(Chromosome c){
        return (double) c.getGenes().stream()
                .filter(g -> (Boolean) g.getValue()).count();
    }
}
