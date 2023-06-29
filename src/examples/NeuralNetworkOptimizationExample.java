package examples;

import model.Chromosome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NeuralNetworkOptimizationExample {
    private static final String NEURAL_NETWORK_PATH = "example_files/NeuralNetworkOptimizationExample/main.py";
    public static final String VENV_PATH = "example_files/NeuralNetworkOptimizationExample/venv/Scripts/python.exe";
    public static void main(String[] args) {
        fitness(null);
    }

    public static Double fitness(Chromosome c){
        String[] pythonCommand = {VENV_PATH, NEURAL_NETWORK_PATH};
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
            }

            return 0.0; //TODO: Replace this with actual fitness
        } catch (IOException | InterruptedException e) {
            return 0.0;
        }
    }
}
