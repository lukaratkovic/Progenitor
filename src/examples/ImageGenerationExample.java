package examples;

import enums.CrossoverMethod;
import enums.EndCondition;
import enums.SelectionMethod;
import model.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Example of the Progenitor genetic algorithm using IntegerValueGene whose goal it is to recreate a target grayscale image
 */
public class ImageGenerationExample {
    private static final String IN_IMAGE_PATH = "example_files/tvz.png";
    private static final String OUT_IMAGE_PATH = "example_files/tvz_final.png";
    private static List<Integer> targetImage;
    private static int imageX, imageY;
    public static void main(String[] args) {
        // Loading the target image
        loadImage();

        // Declaring template gene
        IntegerValueGene templateGene =
                new IntegerValueGene(0, 256);
        // Declaring template chromosome, with a defined length equal to the number of image pixels and template gene
        Chromosome c = new Chromosome(imageX*imageY, templateGene);

        // Using Builder Pattern to create a Progenitor object with custom parameters
        Progenitor progenitor = new Progenitor.Builder(c)
                .populationSize(10)
                .fitness(ImageGenerationExample::fitness)
                .elitismCount(2)
                .selectionMethod(SelectionMethod.RANK)
//                .tournamentK(10)
                .crossoverMethod(CrossoverMethod.UNIFORM)
                .mutationProbability(0.01)
//                .endCondition(EndCondition.MAX_GENERATIONS)
//                .maxGenerations(100000)
                .endCondition(EndCondition.TIME_ELAPSED)
                .maxTime(30)
//                .endCondition(EndCondition.STAGNATE)
//                .stagnateGenerations(500)
                .build();

        // Running the genetic algorithm
        progenitor.run();

        RunResult result = progenitor.getRunResult();
        result.print();

        /*List<Integer> breakpoints = Arrays.asList(100, 500, 1000, 10000, 25000, 50000, 100000);
        for(Integer i : breakpoints){
            List<Integer> image = result.getBestForGeneration(i).getGenes()
                    .stream().map(k -> (Integer) k.getValue())
                    .toList();
            saveImage(image, "example_files/tvz_"+i+".png");
        }*/

        List<Integer> best = result.getBestChromosome().getGenes().stream().map(g->(Integer)g.getValue()).toList();
        saveImage(best, OUT_IMAGE_PATH);
    }

    /**
     * Custom fitness function
     * @param c Chromosome whose fitness is being calculated
     * @return The fitness of the chromosome, in this case the reciprocal sum of differences of pixel values of the generated image compared to the target
     */
    public static Double fitness(Chromosome c){
        return 1.0/IntStream.range(0, c.getLength())
                .mapToDouble(i -> Math.abs((Integer) c.getGenes().get(i).getValue() - targetImage.get(i)))
                .sum();
    }

    /**
     * Loads the image from IN_IMAGE_PATH and stores it in the targetImage field
     */
    public static void loadImage(){
        targetImage = new ArrayList<>();
        File file = new File(IN_IMAGE_PATH);
        try {
            BufferedImage image = ImageIO.read(file);

            imageX = image.getWidth();
            imageY = image.getHeight();

            for (int x = 0; x < imageX; x++) {
                for (int y = 0; y < imageY; y++) {
                    int pixel = image.getRGB(x, y);
                    int grayscaleValue = (pixel >> 16) & 0xFF;
                    targetImage.add(grayscaleValue);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image\n"+e);
        }
    }

    /**
     * Saves the passed image to the OUT_IMAGE_PATH path
     * @param image List of integers (0-255) representing the brightness of each pixel
     */
    public static void saveImage(List<Integer> image, String path){
        BufferedImage output = new BufferedImage(imageX, imageY, BufferedImage.TYPE_BYTE_GRAY);

        int index = 0;
        for (int x = 0; x < imageX; x++) {
            for (int y = 0; y < imageY; y++) {
                int grayscaleValue = image.get(index);
                int pixel = (grayscaleValue << 16) | (grayscaleValue << 8) | grayscaleValue;
                output.setRGB(x, y, pixel);
                index++;
            }
        }

        File file = new File(path);
        try{
            ImageIO.write(output, "png", file);
        } catch(IOException e){
            throw new RuntimeException("Failed to save Image\n"+e);
        }
    }
}
