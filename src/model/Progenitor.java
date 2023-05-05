package model;

import enums.*;

import java.util.ArrayList;
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
        //TODO: Implement run method

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
            
        }
    }
}
