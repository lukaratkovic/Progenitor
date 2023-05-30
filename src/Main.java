import enums.CrossoverMethod;
import enums.EndCondition;
import enums.SelectionMethod;
import model.*;

public class Main {
    public static void main(String[] args) {
        IntegerValueGene intgene = new IntegerValueGene(0, 10);
        Chromosome c =
                new Chromosome(10, intgene);

        Progenitor progenitor = new Progenitor.Builder(c)
                .populationSize(100)
                .endCondition(EndCondition.TARGET_FITNESS)
                .targetFitness(90)
                .crossoverMethod(CrossoverMethod.UNIFORM)
                .mutationProbability(0.01)
                .selectionMethod(SelectionMethod.TOURNAMENT)
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
