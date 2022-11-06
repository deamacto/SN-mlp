import org.ejml.simple.SimpleMatrix;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {


        try {
            Digit[] digits = MnistReader.readData("data/train-images-idx3-ubyte", "data/train-labels-idx1-ubyte");
//            System.out.println(digits[11]);
            ArrayList<Integer> neuronCount = new ArrayList<>();
            neuronCount.add(200);
            MLP mpl = new MLP(neuronCount);
            mpl.calculate(digits[2], ActivationFunction.TANH, true);

            for(Layer layer : mpl.layers) {
                System.out.println(layer.dimensions());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
