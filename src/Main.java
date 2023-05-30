import enums.CrossoverMethod;
import enums.EndCondition;
import enums.SelectionMethod;
import model.*;

import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        IntegerValueGene intgene = new IntegerValueGene(0, 10);
        Chromosome c =
                new Chromosome(10, intgene);

        Progenitor progenitor = new Progenitor.Builder(c)
                .populationSize(100)
                .endCondition(EndCondition.MAX_GENERATIONS)
                .maxGenerations(50)
                .crossoverMethod(CrossoverMethod.TWO_POINT)
                .mutationProbability(0.01)
                .selectionMethod(SelectionMethod.RANK)
                .elitismCount(2)
                .fitness(Main::fitness)
                .build();

        progenitor.run();
    }

    public static Double fitness(Chromosome c){
        return (double) c.getGenes().stream()
                .mapToInt(g -> (Integer) g.getValue())
                .sum();
    }
}
