package model;

import enums.CrossoverMethod;
import enums.EndCondition;
import enums.SelectionMethod;
import exceptions.RunNotCompletedException;
import helpers.Rand;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Implementation of the Genetic algorithm, containing a Builder Pattern with default values, implementations of selection methods, crossovers, and mutation
 */
public class Progenitor {
    /**
     * Builder pattern for the genetic algorithm containing default values
     */
    public static class Builder{
        private Chromosome chromosome;
        private int populationSize=10, maxGenerations=100, tournamentK=1, elitismCount=0, stagnateGenerations=100;
        private long maxTime = 60;
        private double mutationProbability=0.01, targetFitness = Double.MAX_VALUE;
        private EndCondition endCondition = EndCondition.MAX_GENERATIONS;
        private CrossoverMethod crossoverMethod = CrossoverMethod.ONE_POINT;
        private SelectionMethod selectionMethod = SelectionMethod.RANK;
        private Function<Chromosome, Double> fitness;

        /**
         * Initializes the builder and defines its template chromosome
         * @param chromosome Template chromosome used for filling the initial population. It also defines the length of all new chromosomes created by the algorithm.
         */
        public Builder(Chromosome chromosome){
            this.chromosome = chromosome;
        }

        /**
         * Sets the number of chromosomes in each generation
         * @param populationSize integer number of chromosomes, default 10
         * @return the builder instance
         */
        public Builder populationSize(int populationSize) {
            this.populationSize = populationSize;
            return this;
        }

        /**
         * Sets the end condition for the genetic algorithm
         * @param endCondition end condition, default EndCondition.MAX_GENERATIONS
         * @return the builder instance
         */
        public Builder endCondition(EndCondition endCondition){
            this.endCondition = endCondition;
            return this;
        }

        /**
         * Sets the target fitness for the TARGET_FITNESS end condition
         * @param targetFitness generation at which the algorithm stops, default Double.MAX_VALUE
         * @return the builder instance
         */
        public Builder targetFitness(double targetFitness){
            this.targetFitness = targetFitness;
            return this;
        }

        /**
         * Sets the max number of generations for the MAX_GENERATIONS end condition
         * @param maxGenerations generation at which the algorithm stops, default 100
         * @return the builder instance
         */
        public Builder maxGenerations(int maxGenerations){
            this.maxGenerations = maxGenerations;
            return this;
        }

        public Builder stagnateGenerations(int stagnateGenerations){
            if(this.stagnateGenerations <= 0)
                throw new IllegalArgumentException("Number of generations for the STAGNATE end condition must be greater than 0");
            this.stagnateGenerations = stagnateGenerations;
            return this;
        }

        public Builder maxTime(long maxTime){
            this.maxTime = maxTime;
            return this;
        }

        /**
         * Sets the crossover method for creating new chromosomes
         * @param crossoverMethod crossover method, default CrossoverMethod.ONE_POINT
         * @return the builder instance
         */
        public Builder crossoverMethod(CrossoverMethod crossoverMethod){
            this.crossoverMethod=crossoverMethod;
            return this;
        }

        /**
         * Sets the probability for a chromosome's genes to undergo mutation
         * @param mutationProbability mutation probability as a percentage between 0 and 1, default 0.01
         * @return the builder instance
         */
        public Builder mutationProbability(double mutationProbability){
            if(mutationProbability < 0 || mutationProbability > 1)
                throw new IllegalArgumentException("Mutation probability must be between 0 and 1");
            this.mutationProbability = mutationProbability;
            return this;
        }

        /**
         * Sets the selection method for choosing parent Chromosomes
         * @param selectionMethod selection method, default SelectionMethod.RANK
         * @return the builder instance
         */
        public Builder selectionMethod(SelectionMethod selectionMethod){
            this.selectionMethod = selectionMethod;
            return this;
        }

        /**
         * Sets the K for the tournament selection method
         * @param k number of chromosomes entering a tournament between 1 and populationSize, default 1
         * @return the builder instance
         */
        public Builder tournamentK(int k){
            if(k>populationSize || k < 1)
                throw new IllegalArgumentException("tournamentK must be in range [1, populationSize]");
            this.tournamentK = k;
            return this;
        }

        /**
         * Sets the number of best chromosomes to be copied to new population without applying any genetic operators
         * @param elitismCount number of elites between 0 and populationSize, default 0
         * @return the builder instance
         */
        public Builder elitismCount(int elitismCount){
            if(elitismCount < 0 || elitismCount > populationSize)
                throw new IllegalArgumentException("Elitism count must be in range [0, populationSize]");
            this.elitismCount = elitismCount;
            return this;
        }

        /**
         * Sets the fitness function for determining the best chromosome of a population
         * @param fitness reference to a function taking a single Chromosome parameter and returning a double
         * @return
         */
        public Builder fitness(Function<Chromosome, Double> fitness){
            this.fitness = fitness;
            return this;
        }

        /**
         * Builds and returns the final Progenitor object based on the provided parameters
         * @return the fully constructed Progenitor object
         */
        public Progenitor build(){
            if(this.fitness == null)
                throw new IllegalStateException("Fitness function must not be null");
            Progenitor p = new Progenitor();
            p.chromosome = chromosome;
            p.populationSize = populationSize;
            p.maxGenerations = maxGenerations;
            p.targetFitness = targetFitness;
            p.stagnateGenerations = stagnateGenerations;
            p.maxTime = maxTime;
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
    private int populationSize, maxGenerations, tournamentK, elitismCount, stagnateGenerations;
    private long maxTime;
    private double mutationProbability, targetFitness;
    private EndCondition endCondition;
    private CrossoverMethod crossoverMethod;
    private SelectionMethod selectionMethod;
    private Function<Chromosome, Double> fitness;
    private RunResult runResult;
    private Map<Chromosome, Double> populationFitness;

    private Progenitor(){}

    public void run(){
        List<Chromosome> bestIndividuals = new ArrayList<>();
        List<Double> bestFitnesses = new ArrayList<>();
        Chromosome bestChromosome = null;
        int sameGenerations = 0;

        // Start measuring execution time
        long startTime = System.nanoTime();
        long totalSelectionTime=0, totalCrossoverTime=0, totalMutationTime=0;

        printProgress(0, 0, 0.0, startTime);

        // Create initial population with random values
        List<Chromosome> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            population.add(chromosome.getRandom());
        }

        populationFitness = population.stream()
                .collect(Collectors.toMap(
                        chromosome -> chromosome,
                        chromosome -> fitness.apply(chromosome)
                ));

        int generation = 0;
        boolean exitCondition = false;
        Chromosome bestInitialChromosome = population.stream().max(Comparator.comparing(c -> populationFitness.get(c))).get();
        bestIndividuals.add(bestInitialChromosome);
        double startFitness = populationFitness.values().stream().max(Double::compare).get();
        bestFitnesses.add(startFitness);

        printProgress(0, generation, startFitness, startTime);

        // Run genetic algorithm
        while(!exitCondition){
            List<Chromosome> newPopulation = new ArrayList<>();

            // Elitism
            population.sort((c1, c2) -> populationFitness.get(c2).compareTo(populationFitness.get(c1)));
            newPopulation.addAll(population.subList(0, elitismCount).stream()
                    .map(Chromosome::clone).toList());

            // Fill new population with new chromosomes
            while(newPopulation.size() < populationSize){
                long selectionStartTime = System.nanoTime();
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
                totalSelectionTime += System.nanoTime() - selectionStartTime;
                long crossoverStartTime = System.nanoTime();
                Chromosome child = switch(crossoverMethod){
                    case ONE_POINT -> onePointCrossover(parent1, parent2);
                    case TWO_POINT -> twoPointCrossover(parent1, parent2);
                    case UNIFORM -> uniformCrossover(parent1, parent2);
                };
                totalCrossoverTime += System.nanoTime()-crossoverStartTime;
                long mutationStartTime = System.nanoTime();
                mutate(child);
                totalMutationTime += System.nanoTime() - mutationStartTime;
                newPopulation.add(child);
            }

            // Calculate new population fitness
            populationFitness = newPopulation.stream()
                    .collect(Collectors.toMap(
                            chromosome -> chromosome,
                            chromosome -> fitness.apply(chromosome)
                    ));

            // Finding the best chromosome
            Chromosome best = newPopulation.stream().max(Comparator.comparing(c -> populationFitness.get(c))).get();
            Double bestFitness = populationFitness.get(best);
            bestIndividuals.add(best.clone());
            bestFitnesses.add(bestFitness);

            population = newPopulation;
            generation++;

            // Checking if the end condition is met
            if(endCondition == EndCondition.MAX_GENERATIONS && generation == maxGenerations
            || endCondition == EndCondition.TARGET_FITNESS && bestFitness >= targetFitness
            || endCondition == EndCondition.TIME_ELAPSED && (System.nanoTime()-startTime)/1_000_000_000 >= maxTime)
                exitCondition=true;
            if(endCondition == EndCondition.STAGNATE && generation >= stagnateGenerations){
                boolean hasDifferences = false;
                int counter = 1;
                for(int i=generation-1; i>generation-stagnateGenerations; i--){
                    if(!Objects.equals(bestFitness, bestFitnesses.get(i))) {
                        hasDifferences = true;
                        break;
                    }
                    else counter++;
                }
                if(!hasDifferences) exitCondition=true;
                if(sameGenerations < counter) sameGenerations = counter;
            }

            // Progress output
            int currentProgress = switch(endCondition){
                case TARGET_FITNESS -> (int) ((startFitness-bestFitness)/(startFitness-targetFitness)*50); // Negative fitness
                case STAGNATE -> (int)((double)sameGenerations/stagnateGenerations*50);
                case TIME_ELAPSED -> (int)((System.nanoTime()-startTime)/1_000_000_000/(double)maxTime*50);
                case MAX_GENERATIONS -> generation*50 / maxGenerations;
            };
            printProgress(currentProgress, generation, bestFitness, startTime);
            bestChromosome = best;
        }
        System.out.println();

        // Stop measuring execution time
        long elapsedTime = System.nanoTime() - startTime;

        // Create run results
        runResult = new RunResult();
        runResult.setExecutionTime(elapsedTime / 1_000_000);
        runResult.setSelectionTime(totalSelectionTime / 1_000_000);
        runResult.setCrossoverTime(totalCrossoverTime / 1_000_000);
        runResult.setMutationTime(totalMutationTime / 1_000_000);
        runResult.setBestChromosome(bestChromosome);
        runResult.setGenerationCount(generation);
        runResult.setFinalFitness(populationFitness.get(bestChromosome));
        runResult.setBestIndividuals(bestIndividuals);
        runResult.setBestFitnesses(bestFitnesses);
    }

    public RunResult getRunResult() {
        if(runResult == null)
            throw new RunNotCompletedException("Run result could not be found. Make sure to run Progenitor.run() before calling this method");
        return runResult;
    }

    /**
     * Performs the rank selection from a given population by ranking them, then randomly selecting a chromosome with higher rank chromosomes having a higher likelihood of being chosen
     * @param population Population to perform the rank selection on
     * @return Selected Chromosome
     */
    private Chromosome rank(List<Chromosome> population) {
        population.sort((c1, c2) -> populationFitness.get(c2).compareTo(populationFitness.get(c1)));
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

    /**
     * Performs the roulette selection from a given population by randomly selecting a chromosome with higher-fitness chromosomes having a higher likelihood of being chosen
     * @param population Population to perform the roulette selection on
     * @return Selected Chromosome
     */
    private Chromosome roulette(List<Chromosome> population) {
        double populationFitnessSum = populationFitness.values().stream().mapToDouble(Double::doubleValue).sum();
        double current = 0, rouletteResult = Rand.getRandDouble(0, Math.abs(populationFitnessSum));
        for(Chromosome c: population){
            current += Math.abs(populationFitness.get(c));
            if(rouletteResult < current){
                return c;
            }
        }
        throw new RuntimeException("Roulette method of Progenitor class exceeded its bounds." +
                "This is a library error and should not occur in implementation.");
    }

    /**
     * Performs the tournament selection by randomly selecting K chromosomes from the population, then choosing the highest-fitness one as the winner
     * @param population Population to perform the tournament selection on
     * @return Selected Chromosome
     */
    private Chromosome tournament(List<Chromosome> population){
        // Select K random distinct chromosomes
        return Rand.rand.ints(0, population.size()-1)
                .distinct()
                .limit(tournamentK)
                .mapToObj(population::get)
                .max(Comparator.comparing(c -> populationFitness.get(c)))
                .get();
    }

    /**
     * Creates a new Chromosome from two parents by picking a random point, then copying the first parent's genes up to that point, and the second parent's genes from that point onwards
     * @param p1 First parent
     * @param p2 Second parent
     * @return New Chromosome created from the combination of both parents' genes
     */
    private Chromosome onePointCrossover(Chromosome p1, Chromosome p2){
        int crossoverPoint = Rand.getRandInteger(1, chromosome.getLength());

        List<Gene> genes = new ArrayList<>();
        genes.addAll(p1.getGenes().subList(0, crossoverPoint).stream()
                .map(Gene::clone).toList());
        genes.addAll(p2.getGenes().subList(crossoverPoint, chromosome.getLength()).stream()
                .map(Gene::clone).toList());

        return new Chromosome(genes);
    }

    /**
     * Creates a new chromosome by picking two random, different points, then copying the first parent's genes up to the first point and from the second point onwards, and filling the rest with the second parent's genes
     * @param p1 First parent
     * @param p2 Second parent
     * @return New Chromosome created from the combination of both parents' genes
     */
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

    /**
     * Creates a new chromosome by randomly picking genes from both parents
     * @param p1 First parent
     * @param p2 Second parent
     * @return New Chromosome created from the combination of both parents' genes
     */
    private Chromosome uniformCrossover(Chromosome p1, Chromosome p2){
        List<Gene> genes = new ArrayList<>();
        for (int i = 0; i < chromosome.getLength(); i++) {
            Chromosome targetParent = Rand.getRandBool() ? p1 : p2;
            Gene gene = targetParent.getGenes().get(i);
            genes.add(gene.clone());
        }
        return new Chromosome(genes);
    }

    /**
     * Mutates randomly selected genes of a chromosome according to the mutationProbability paremeter
     * @param chromosome Chromosome to undergo mutation
     */
    private void mutate(Chromosome chromosome){
        for (Gene gene : chromosome.getGenes()) {
            if(Rand.getRandInteger(0, 100) < mutationProbability*100){
                gene.mutate();
            }
        }
    }

    private void printProgress(int currentProgress, int generation, double bestFitness, long startTime){
        StringBuilder progressBuilder = new StringBuilder();
        progressBuilder
                .append('\u2551')
                .append("\u2588".repeat(currentProgress))
                .append(" ".repeat(50-currentProgress))
                .append("\u2551")
                .append(" Generation ")
                .append(generation)
                .append(", Fitness: ")
                .append(bestFitness)
                .append(", Time: ")
                .append((System.nanoTime()-startTime)/1_000_000_000)
                .append("s\r");
        System.out.print(progressBuilder);
    }
}
