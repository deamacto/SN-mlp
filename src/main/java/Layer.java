import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;
import java.util.Random;

public class Layer {
    SimpleMatrix weights;
    SimpleMatrix biases;

    public Layer(int neuronCount, int previousNeuronsCount) {
        this.weights = new SimpleMatrix(generateWeights(neuronCount, previousNeuronsCount));
        System.out.println("Weights");
        this.biases = new SimpleMatrix(generateWeights(neuronCount, 1));
        System.out.println("Biases");
    }

    private static double[][] generateWeights(int neuronCount, int previousNeuronsCount) {
        double[][] weights = new double[neuronCount][previousNeuronsCount];
        Random random = new Random();

        System.out.print(weights.length + " ");
        System.out.print(weights[0].length + " ");

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

    // Macierz inputow jest teraz 2d
    public SimpleMatrix calculateBatch(SimpleMatrix input, ActivationFunction activationFunction) {
        SimpleMatrix sums = new SimpleMatrix(weights.numRows(), input.numCols());

        for(int i = 0; i < input.numCols(); i++) {
            sums.setColumn(i, 0, Util.toArray((weights.mult(input.cols(i, i+1)).plus(biases))));
        }

        for(int i = 0; i < sums.numRows(); i++) {
            for(int j = 0; j < sums.numCols(); j++) {
                sums.set(i, j, activate(sums.get(i, j), activationFunction));
            }
        }
        return sums;
    }

    public SimpleMatrix softmaxBatch(SimpleMatrix input) {
        SimpleMatrix sums = new SimpleMatrix(weights.numRows(), input.numCols());

        for(int i = 0; i < input.numCols(); i++) {
            sums.setColumn(i, 0, Util.toArray((weights.mult(input.cols(i, i+1)).plus(biases))));
        }

        SimpleMatrix es = new SimpleMatrix(weights.numRows(), input.numCols());

        for(int i = 0; i < sums.numRows(); i++) {
            for(int j = 0; j < sums.numCols(); j++) {
                es.set(i, j, Math.exp(sums.get(i, j)));
            }
        }

        ArrayList<Double> eSums = new ArrayList<>();

        for(int i = 0; i < es.numCols(); i++) {
            eSums.add(es.cols(i, i+1).elementSum());
        }

        SimpleMatrix softmax = new SimpleMatrix(sums.numRows(), sums.numCols());

        for(int i = 0; i < sums.numRows(); i++) {
            for(int j = 0; j < sums.numCols(); j++) {
                softmax.set(i, j, es.get(i,j)/eSums.get(j));
            }
        }
        return softmax;
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

    public String dimensions() {
        return "Wymiary wag: " + "rzędy " + weights.numRows() + " kolumny " + weights.numCols();
    }
}
