import model.*;

public class Main {
    public static void main(String[] args) {
        IntegerValueGene gene = new IntegerValueGene(0, 10, 1);
        System.out.println(gene.value);
        gene.mutate();
        System.out.println(gene.value);
    }
}
