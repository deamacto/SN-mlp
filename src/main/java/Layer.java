import org.ejml.simple.SimpleMatrix;

import java.util.Random;

public class Layer {
    SimpleMatrix weights;
    SimpleMatrix biases;

    public Layer(int neuronCount, int previousNeuronsCount) {
        this.weights = new SimpleMatrix(generateWeights(neuronCount, previousNeuronsCount));
        this.biases = new SimpleMatrix(generateWeights(neuronCount, 1));
    }

    private static double[][] generateWeights(int neuronCount, int previousNeuronsCount) {
        double[][] weights = new double[neuronCount][previousNeuronsCount];
        Random random = new Random();

        for(int i = 0; i < weights.length; i++) {
            for(int j = 0; j < weights[0].length; j++) {
                weights[i][j] = random.nextGaussian();
            }
        }

        return weights;
    }
}
