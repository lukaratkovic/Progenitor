import model.*;

public class Main {
    public static void main(String[] args) {
        Gene gene = new BinaryGene(1);
        System.out.println("Initial value: "+gene.value);
        gene.mutate();
        System.out.println("Value after mutation: "+gene.value);
    }
}
