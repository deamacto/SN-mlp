import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> neuronCount = new ArrayList<>();
        neuronCount.add(12);
        MLP mpl = new MLP(neuronCount);
        mpl.calculate(new Input(1, 1), ActivationFunction.RELU, true);
    }
}
