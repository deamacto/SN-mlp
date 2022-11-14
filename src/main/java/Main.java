import org.ejml.simple.SimpleMatrix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {

        try {
            learn();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void learn() throws IOException {
        Digit[] trainDigits = MnistReader.readData("data/train-images-idx3-ubyte", "data/train-labels-idx1-ubyte");
        Digit[] testDigits = MnistReader.readData("data/t10k-images-idx3-ubyte", "data/t10k-labels-idx1-ubyte");

        ArrayList<Integer> neuronCount = new ArrayList<>();
        neuronCount.add(40);
        MLP mlp = new MLP(neuronCount);

        while (true) {
            Collections.shuffle(Arrays.asList(trainDigits));
            ArrayList<SimpleMatrix> batches = createMnistMatrix(trainDigits);
            ArrayList<SimpleMatrix> labels = createLabelMatrix(trainDigits);

            for(int i = 0; i < batches.size(); i++) {
                mlp.learn(batches.get(i), labels.get(i), ActivationFunction.SIGMOID, false);
            }

            double correct = 0.0;

            for(Digit digit : testDigits) {
                if(mlp.calculate(digit, ActivationFunction.SIGMOID, false)) {
                    correct += 1.0;
                }
            }

            System.out.println(correct/testDigits.length + "\t" + mlp.likelyhood);
            mlp.likelyhood = 0;
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
            batch.setColumn(i % Consts.BATCH_SIZE,0, digits[i].getDigit());
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
            batch.setColumn(i % Consts.BATCH_SIZE,0, digits[i].getLabelArray());
        }

        labels.add(batch.copy());

        return labels;
    }
}
