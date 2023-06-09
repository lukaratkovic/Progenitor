package examples;

import enums.CrossoverMethod;
import enums.EndCondition;
import enums.SelectionMethod;
import model.Chromosome;
import model.DecimalValueGene;
import model.Progenitor;

/**
 * Example of the Progenitor genetic algorithm using DecimalValueGene whose goal is finding numerical coefficients of a quadratic equation f(x)=ax^2+bx+c whose value is 0 for x=10
 */
public class DecimalExample {
    public static void main(String[] args) {
        // Declaring template gene
        DecimalValueGene templateGene = new DecimalValueGene(-50.0, 50.0);
        // Declaring template chromosome, with a defined length and template gene
        Chromosome c = new Chromosome(3, templateGene);

        // Using Builder Pattern to create a Progenitor object with custom parameters
        Progenitor progenitor = new Progenitor.Builder(c)
                .populationSize(10)
                .endCondition(EndCondition.MAX_GENERATIONS)
                .maxGenerations(100000)
                .crossoverMethod(CrossoverMethod.UNIFORM)
                .mutationProbability(0.01)
                .selectionMethod(SelectionMethod.RANK)
                .elitismCount(1)
                .fitness(DecimalExample::fitness)
                .build();

        //Running the genetic algorithm
        progenitor.run();

        // Outputting the best chromosome of the final generation
        Chromosome best = progenitor.getBest();
        System.out.println(best);
    }

    /**
     * Custom fitness function
     * @param c Chromosome whose fitness is being calculated
     * @return The fitness of the Chromosome, in this case the negative absolute difference of the quadratic equasion value for x=-10 from f(x)=0
     */
    public static Double fitness(Chromosome c){
        Double _a = (Double) c.getGenes().get(0).value;
        Double _b = (Double) c.getGenes().get(1).value;
        Double _c = (Double) c.getGenes().get(2).value;
        return -1 * Math.abs(_a*_a*10 + _b*10 + _c);
    }
}
