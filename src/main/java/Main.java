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

            for(LayerWrapper layer : mpl.layers) {
                System.out.println(layer.layer.dimensions());
            }


            ArrayList<SimpleMatrix> batches = createMnistMatrix(digits);
            ArrayList<SimpleMatrix> labels = createLabelMatrix(digits);
            mpl.learn(batches.get(0), labels.get(0), ActivationFunction.TANH, true);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<SimpleMatrix> createMnistMatrix(Digit[] digits) {
        ArrayList<SimpleMatrix> batches = new ArrayList<>();

        SimpleMatrix batch = new SimpleMatrix(Consts.NEURONS_INPUT, Consts.BATCH_SIZE);
        for(int i = 0; i < digits.length; i++) {

            if(i % Consts.BATCH_SIZE == 0 && i != 0) {
                batches.add(batch.copy());
                batch.zero();
            }
            batch.setColumn(i % 60,0, digits[i].getDigit());
        }
        batches.add(batch.copy());

        return batches;
    }

    public static ArrayList<SimpleMatrix> createLabelMatrix(Digit[] digits) {
        ArrayList<SimpleMatrix> labels = new ArrayList<>();

        SimpleMatrix batch = new SimpleMatrix(Consts.NEURONS_OUTPUT, Consts.BATCH_SIZE);
        for(int i = 0; i < digits.length; i++) {

            if(i % Consts.BATCH_SIZE == 0 && i != 0) {
                labels.add(batch.copy());
                batch.zero();
            }
            batch.setColumn(i % 60,0, digits[i].getLabelArray());
        }

        labels.add(batch.copy());

        return labels;
    }
}
