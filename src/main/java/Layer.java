import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
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
                weights[i][j] = random.nextGaussian() * Consts.DESIRED_STANDARD_DEVIATION + Consts.DESIRED_MEAN;
            }
        }

        return weights;
    }

    public SimpleMatrix calculate(SimpleMatrix input, ActivationFunction activationFunction) {
        SimpleMatrix sums = (weights.mult(input)).plus(biases);
        SimpleMatrix activations = new SimpleMatrix(sums.numRows(), 1);

        for(int i = 0; i < sums.numRows(); i++) {
            for(int j = 0; j < sums.numCols(); j++) {
                activations.set(i, j, activate(sums.get(i, j), activationFunction));
            }
        }
        return activations;
    }

    public SimpleMatrix softmax(SimpleMatrix input) {
        SimpleMatrix sums = (weights.mult(input)).plus(biases);
        ArrayList<Double> es = new ArrayList<>();

        for(int i = 0; i < sums.numRows(); i++) {
            es.add(Math.exp(sums.get(i, 0)));
        }

        double eSum = es.stream().mapToDouble(Double::doubleValue).sum();

        SimpleMatrix softmax = new SimpleMatrix(sums.numRows(), 1);

        for(int i = 0; i < sums.numRows(); i++) {
            softmax.set(i, 0, es.get(i)/eSum);
        }

        return softmax;
    }

    public double activate(double value, ActivationFunction activationFunction) {
        if(activationFunction == ActivationFunction.SIGMOID) {
            return sigmoid(value);
        } else if(activationFunction == ActivationFunction.TANH) {
            return tanh(value);
        } else if(activationFunction == ActivationFunction.RELU) {
            return relu(value);
        } else {
            return Double.MIN_VALUE;
        }
    }

    public static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public static double tanh(double x) {
        return (2 / (1 + Math.exp(-2 * x))) - 1;
    }

    public static double relu(double x) {
        if(x < 0) {
            return 0;
        } else {
            return x;
        }
    }
}
