package model;

import enums.*;
import helpers.Utils;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class Progenitor {
    public static class Builder{
        private Chromosome chromosome;
        private int populationSize=10, maxGenerations=100, tournamentK=1, elitismCount=0;
        private double mutationProbability=0.01;
        private EndCondition endCondition = EndCondition.FITNESS_REACHED;
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
    private double mutationProbability;
    private EndCondition endCondition;
    private CrossoverMethod crossoverMethod;
    private SelectionMethod selectionMethod;

    private Function<Chromosome, Double> fitness;

    // Internal parameters
    private int generation = 0;

    private Progenitor(){}

    public void run(){
        // Create initial population with random values
        List<Chromosome> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            population.add(chromosome.getRandom());
        }

        int generation = 1;
        boolean exitCondition = false;

        // Run genetic algorithm
        while(!exitCondition){
            List<Chromosome> newPopulation = new ArrayList<>();

            // Elitism
            population.sort((c1, c2) -> {
                if(fitness.apply(c1) < fitness.apply(c2)) return 1;
                else return -1;
            });
            population.subList(0, elitismCount).forEach(c -> newPopulation.add(c));

            // Fill new population with new chromosomes
            while(newPopulation.size() < populationSize){
                // Select parents TODO: Implement selection methods
                Chromosome parent1 = switch(selectionMethod){
                    case TOURNAMENT -> tournament(population);
                    case ROULETTE -> roulette(population);
                    case RANK -> rank(population);
                };
                population.remove(parent1);
                Chromosome parent2 = switch(selectionMethod){
                    case TOURNAMENT -> tournament(population);
                    case ROULETTE -> roulette(population);
                    case RANK -> rank(population);
                };


                // TODO: Use crossover to create new chromosome from parents
                // TODO: Undergo mutation randomly
                // TODO: Add new chromosome to new population
            }

            population = newPopulation;
            generation++;

            //TODO: Remove this, for single iteration testing only
            exitCondition = true;
        }
    }

    private Chromosome rank(List<Chromosome> population) {
        population.sort((c1,c2) -> {
            if(fitness.apply(c1) < fitness.apply(c2)) return 1;
            else return -1;
        });
        double current = 0, randomValue = Utils.getRandDouble(0,1);
        for (int i=0; i<populationSize;i++){
            current += ((double)(populationSize-i)/(populationSize*(populationSize+1)/2));
            if(randomValue < current)
                return population.get(i);
        }
        throw new RuntimeException("Roulette method of Progenitor class exceeded its bounds." +
                "This is a library error and should not occur in implementation.");
    }

    private Chromosome roulette(List<Chromosome> population) {
        double populationFitness = population.stream().mapToDouble(c -> fitness.apply(c)).sum();
        double current = 0, rouletteResult = Utils.getRandDouble(0, populationFitness);
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
        Utils.rand.ints(0, population.size()-1)
                .distinct()
                .limit(tournamentK)
                .forEach(i -> candidates.add(population.get(i)));
        return candidates.stream()
                .max((c1,c2) -> {
                    if(fitness.apply(c1) < fitness.apply(c2)) return 1;
                    else return -1;
                })
                .get();
    }

    
}
