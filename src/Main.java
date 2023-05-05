import model.*;

public class Main {
    public static void main(String[] args) {
        IntegerValueGene intgene = new IntegerValueGene(0, 10);
        Chromosome<IntegerValueGene> c =
                new Chromosome<>(10, intgene);
        System.out.println(c);
    }
}
