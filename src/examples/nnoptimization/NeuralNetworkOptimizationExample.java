package examples.nnoptimization;

import enums.CrossoverMethod;
import enums.EndCondition;
import enums.SelectionMethod;
import model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class NeuralNetworkOptimizationExample {
    private static final String NEURAL_NETWORK_PATH = "example_files/NeuralNetworkOptimizationExample/main.py";
    public static final String VENV_PATH = "example_files/NeuralNetworkOptimizationExample/venv/Scripts/python.exe";
    public static void main(String[] args) {
        IntegerValueGene batchSize = new IntegerValueGene(8, 1025);
        DecimalValueGene learnRate = new DecimalValueGene(0.0001, 0.1);
        DecimalValueGene momentum = new DecimalValueGene(0.0, 1.0);
        Chromosome c = new Chromosome(Arrays.asList(batchSize, learnRate, momentum));

        Progenitor progenitor = new Progenitor.Builder(c)
                .populationSize(10)
                .fitness(NeuralNetworkOptimizationExample::fitness)
                .elitismCount(1)
                .selectionMethod(SelectionMethod.TOURNAMENT)
                .tournamentK(5)
                .crossoverMethod(CrossoverMethod.UNIFORM)
                .mutationProbability(0.01)
                .endCondition(EndCondition.MAX_GENERATIONS)
                .maxGenerations(10)
                .build();

        progenitor.run();

        RunResult res = progenitor.getRunResult();
        System.out.println(res);
        Chromosome best = res.getBestChromosome();
        System.out.println("Batch size: "+best.getGenes().get(0).getValue().toString());
        System.out.println("learn_rate: "+best.getGenes().get(1).getValue().toString());
        System.out.println("momentum: "+best.getGenes().get(2).getValue().toString());
    }

    public static Double fitness(Chromosome c){
        String[] pythonCommand = {VENV_PATH, NEURAL_NETWORK_PATH,
                c.getGenes().get(0).getValue().toString(),
                c.getGenes().get(1).getValue().toString(),
                c.getGenes().get(2).getValue().toString()
        };
        try{
            ProcessBuilder pb = new ProcessBuilder(pythonCommand);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = null, line;

            while((line = reader.readLine()) != null){
                output = line;
            }

            int exitCode = process.waitFor();

            if(exitCode == 0){
                return Double.parseDouble(output);
            }
            return 0.0;

            //TODO: Replace this with actual fitness
        } catch (IOException | InterruptedException e) {
            return 0.0;
        }
    }
}
