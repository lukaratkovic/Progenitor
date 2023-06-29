package examples.nnoptimization;

import enums.CrossoverMethod;
import enums.EndCondition;
import enums.SelectionMethod;
import model.Chromosome;
import model.DecimalValueGene;
import model.IntegerValueGene;
import model.Progenitor;

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
        HyperChromosome c = new HyperChromosome(Arrays.asList(batchSize, learnRate, momentum));

        fitness(c);
    }

    public static Double fitness(Chromosome c){
        HyperChromosome hc = (HyperChromosome) c;
        String[] pythonCommand = {VENV_PATH, NEURAL_NETWORK_PATH,
                hc.getBatchSize().toString(),
                hc.getLearnRate().toString(),
                hc.getMomentum().toString()
        };
        try{
            ProcessBuilder pb = new ProcessBuilder(pythonCommand);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;

            while((line = reader.readLine()) != null){
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();

            if(exitCode == 0){
                System.out.println(output);
            } else System.out.println(exitCode);

            return 0.0; //TODO: Replace this with actual fitness
        } catch (IOException | InterruptedException e) {
            return 0.0;
        }
    }
}
