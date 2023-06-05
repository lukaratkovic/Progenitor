package model;

import enums.CrossoverMethod;
import enums.EndCondition;
import enums.SelectionMethod;
import exceptions.RunNotCompletedException;
import helpers.Rand;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class Progenitor {
    public static class Builder{
        private Chromosome chromosome;
        private int populationSize=10, maxGenerations=100, tournamentK=1, elitismCount=0;
        private double mutationProbability=0.01, targetFitness = Double.MAX_VALUE;
        private EndCondition endCondition = EndCondition.TARGET_FITNESS;
        private CrossoverMethod crossoverMethod = CrossoverMethod.ONE_POINT;
        private SelectionMethod selectionMethod = SelectionMethod.RANK;
        private Function<Chromosome, Double> fitness;

        public Builder(Chromosome chromosome){
            this.chromosome = chromosome;
        }

        public Builder populationSize(int populationSize) {
            this.populationSize = populationSize;
            return this;
        }

        public Builder endCondition(EndCondition endCondition){
            this.endCondition = endCondition;
            return this;
        }

        public Builder targetFitness(double targetFitness){
            this.targetFitness = targetFitness;
            return this;
        }

        public Builder maxGenerations(int maxGenerations){
            this.maxGenerations = maxGenerations;
            return this;
        }

        public Builder crossoverMethod(CrossoverMethod crossoverMethod){
            this.crossoverMethod=crossoverMethod;
            return this;
        }

        public Builder mutationProbability(double mutationProbability){
            this.mutationProbability = mutationProbability;
            return this;
        }

        public Builder selectionMethod(SelectionMethod selectionMethod){
            this.selectionMethod = selectionMethod;
            return this;
        }

        public Builder tournamentK(int k){
            if(k>populationSize || k < 1)
                throw new IllegalArgumentException("tournamentK must be in range [1, populationSize]");
            this.tournamentK = k;
            return this;
        }

        public Builder elitismCount(int elitismCount){
            if(elitismCount < 0 || elitismCount > populationSize)
                throw new IllegalArgumentException("Elitism count must be in range [0, populationSize]");
            this.elitismCount = elitismCount;
            return this;
        }

        public Builder fitness(Function<Chromosome, Double> fitness){
            this.fitness = fitness;
            return this;
        }

        public Progenitor build(){
            Progenitor p = new Progenitor();
            p.chromosome = chromosome;
            p.populationSize = populationSize;
            p.maxGenerations = maxGenerations;
            p.targetFitness = targetFitness;
            p.endCondition = endCondition;
            p.crossoverMethod = crossoverMethod;
            p.mutationProbability = mutationProbability;
            p.selectionMethod = selectionMethod;
            p.tournamentK = tournamentK;
            p.elitismCount = elitismCount;
            p.fitness = fitness;
            return p;
        }
    }

    // User-defined parameters
    private Chromosome chromosome;
    private int populationSize, maxGenerations, tournamentK, elitismCount;
    private double mutationProbability, targetFitness;
    private EndCondition endCondition;
    private CrossoverMethod crossoverMethod;
    private SelectionMethod selectionMethod;
    private Function<Chromosome, Double> fitness;

    private Chromosome bestChromosome;

    private Progenitor(){}

    public void run(){
        // Create initial population with random values
        List<Chromosome> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            population.add(chromosome.getRandom());
        }

        int generation = 0;
        boolean exitCondition = false;

        // Run genetic algorithm
        while(!exitCondition){
            List<Chromosome> newPopulation = new ArrayList<>();

            // Elitism
            population.sort((c1, c2) -> fitness.apply(c2).compareTo(fitness.apply(c1)));
            newPopulation.addAll(population.subList(0, elitismCount).stream()
                    .map(Chromosome::clone).toList());

            // Fill new population with new chromosomes
            while(newPopulation.size() < populationSize){
                Chromosome parent1 = switch(selectionMethod){
                    case TOURNAMENT -> tournament(population);
                    case ROULETTE -> roulette(population);
                    case RANK -> rank(population);
                };
                Chromosome parent2 = parent1;
                while(parent2 == parent1){
                    parent2 = switch (selectionMethod) {
                        case TOURNAMENT -> tournament(population);
                        case ROULETTE -> roulette(population);
                        case RANK -> rank(population);
                    };
                }
                Chromosome child = switch(crossoverMethod){
                    case ONE_POINT -> onePointCrossover(parent1, parent2);
                    case TWO_POINT -> twoPointCrossover(parent1, parent2);
                    case UNIFORM -> uniformCrossover(parent1, parent2);
                };
                mutate(child);
                newPopulation.add(child);
            }

            Chromosome best = newPopulation.stream().max((c1, c2) -> fitness.apply(c1).compareTo(fitness.apply(c2))).get();

            population = newPopulation;
            generation++;

            if(endCondition == EndCondition.MAX_GENERATIONS && generation == maxGenerations
            || endCondition == EndCondition.TARGET_FITNESS && fitness.apply(best) >= targetFitness)
                exitCondition=true;

            // Progress output
            int currentProgress = switch(endCondition){
                case TARGET_FITNESS -> (int)(fitness.apply(best)*50 / targetFitness);
                case MAX_GENERATIONS -> generation*50 / maxGenerations;
            };
            StringBuilder progressBuilder = new StringBuilder();
            progressBuilder
                    .append('║')
                    .append("█".repeat(currentProgress))
                    .append(" ".repeat(50-currentProgress))
                    .append("║")
                    .append(" Generation ")
                    .append(generation)
                    .append(", Fitness: ")
                    .append(fitness.apply(best))
                    .append("\r");
            System.out.print(progressBuilder);
            bestChromosome = best;
        }
        System.out.println();
    }

    public Chromosome getBest(){
        if(bestChromosome == null)
            throw new RunNotCompletedException("Best chromosome is null. Ensure that you have run the Progenitor.run() method first.");
        return bestChromosome;
    }

    private Chromosome rank(List<Chromosome> population) {
        population.sort((c1,c2) -> fitness.apply(c2).compareTo(fitness.apply(c1)));
        double current = 0, randomValue = Rand.getRandDouble(0,1);
        for (int i=0; i<populationSize;i++){
            current += ((double)(populationSize-i)/(populationSize*(populationSize+1)/2));
            if(randomValue < current) {
                return population.get(i);
            }
        }
        throw new RuntimeException("Roulette method of Progenitor class exceeded its bounds." +
                "This is a library error and should not occur in implementation.");
    }

    private Chromosome roulette(List<Chromosome> population) {
        double populationFitness = population.stream().mapToDouble(c -> fitness.apply(c)).sum();
        double current = 0, rouletteResult = Rand.getRandDouble(0, populationFitness);
        for(Chromosome c: population){
            current += fitness.apply(c);
            if(rouletteResult < current){
                return c;
            }
        }
        throw new RuntimeException("Roulette method of Progenitor class exceeded its bounds." +
                "This is a library error and should not occur in implementation.");
    }

    private Chromosome tournament(List<Chromosome> population){
        List<Chromosome> candidates = new ArrayList<>();
        // Select K random distinct chromosomes
        Rand.rand.ints(0, population.size()-1)
                .distinct()
                .limit(tournamentK)
                .forEach(i -> candidates.add(population.get(i)));
        return candidates.stream()
                .max(Comparator.comparing(c -> fitness.apply(c)))
                .get();
    }

    private Chromosome onePointCrossover(Chromosome p1, Chromosome p2){
        int crossoverPoint = Rand.getRandInteger(1, chromosome.getLength());

        List<Gene> genes = new ArrayList<>();
        genes.addAll(p1.getGenes().subList(0, crossoverPoint).stream()
                .map(Gene::clone).toList());
        genes.addAll(p2.getGenes().subList(crossoverPoint, chromosome.getLength()).stream()
                .map(Gene::clone).toList());

        return new Chromosome(genes);
    }

    private Chromosome twoPointCrossover(Chromosome p1, Chromosome p2){
        int[] crossoverPoints = Rand.rand.ints(1, chromosome.getLength())
                .distinct()
                .limit(2)
                .sorted()
                .toArray();

        List<Gene> genes = new ArrayList<>();
        genes.addAll(p1.getGenes().subList(0, crossoverPoints[0]).stream()
                .map(Gene::clone).toList());
        genes.addAll(p2.getGenes().subList(crossoverPoints[0], crossoverPoints[1]).stream()
                .map(Gene::clone).toList());
        genes.addAll(p1.getGenes().subList(crossoverPoints[1], chromosome.getLength()).stream()
                .map(Gene::clone).toList());

        return new Chromosome(genes);
    }

    private Chromosome uniformCrossover(Chromosome p1, Chromosome p2){
        List<Gene> genes = new ArrayList<>();
        for (int i = 0; i < chromosome.getLength(); i++) {
            Chromosome targetParent = Rand.getRandBool() ? p1 : p2;
            Gene gene = targetParent.getGenes().get(i);
            genes.add(gene.clone());
        }
        return new Chromosome(genes);
    }

    private void mutate(Chromosome chromosome){
        for (Gene gene : chromosome.getGenes()) {
            if(Rand.getRandInteger(0, 100) < mutationProbability*100){
                gene.mutate();
            }
        }
    }
}
