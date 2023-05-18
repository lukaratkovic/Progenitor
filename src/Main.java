import enums.CrossoverMethod;
import enums.EndCondition;
import enums.SelectionMethod;
import model.*;

import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        IntegerValueGene intgene = new IntegerValueGene(0, 10);
        Chromosome<IntegerValueGene> c =
                new Chromosome<>(10, intgene);

        Progenitor progenitor = new Progenitor.Builder(c)
                .populationSize(100)
                .endCondition(EndCondition.MAX_GENERATIONS)
                .maxGenerations(1)
                .crossoverMethod(CrossoverMethod.UNIFORM)
                .mutationProbability(0.01)
                .selectionMethod(SelectionMethod.RANK)
                .elitismCount(2)
                .fitness(Main::fitness)
                .build();

        progenitor.run();
    }

    public static Double fitness(Chromosome<IntegerValueGene> c){
        return (double) c.getGenes().stream()
                .mapToInt(g -> (Integer) g.getValue())
                .sum();
    }
}
