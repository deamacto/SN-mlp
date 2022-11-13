import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;

public class MLP {
    ArrayList<LayerWrapper> layers = new ArrayList<>();

    public MLP(ArrayList<Integer> neuronCount) {
        layers.add(new LayerWrapper(new Layer(10, Consts.NEURONS_INPUT)));

        for(int i = 0; i < neuronCount.size(); i++) {
            layers.add(new LayerWrapper(new Layer(neuronCount.get(i), layers.get(i).layer.biases.numRows())));
        }

        layers.add(new LayerWrapper(new Layer(Consts.NEURONS_OUTPUT, layers.get(layers.size() - 1).layer.biases.numRows())));
    }

    public boolean calculate(Digit input, ActivationFunction activationFunction, boolean isSoftmax) {
        SimpleMatrix result = layers.get(0).layer.calculate(new SimpleMatrix(Consts.NEURONS_INPUT, 1, true, input.getDigit()), activationFunction);
        for(int i = 1; i < layers.size() - 1; i++) {
            result = layers.get(i).layer.calculate(result, activationFunction);
        }

        if(isSoftmax) {
            result = layers.get(layers.size() - 1).layer.softmax(result);
        } else {
            result = layers.get(layers.size() - 1).layer.calculate(result, activationFunction);
        }

        int maxIndex = -1;
        double maxValue = -1;
        for(int i = 1; i < result.numRows() - 1; i++) {
            if(result.get(i, 0) > maxValue) {
                maxValue = result.get(i, 0);
                maxIndex = i;
            }
        }

        System.out.println(result);
        return maxIndex == input.getLabel();
    }

    public void learn(SimpleMatrix input, SimpleMatrix labels, ActivationFunction activationFunction, boolean isSoftmax) {
        layers.get(0).setStimuli(input);
        SimpleMatrix result = layers.get(0).calculateBatch(activationFunction);
        for(int i = 1; i < layers.size() - 1; i++) {
            layers.get(i).setStimuli(result);
            result = layers.get(i).calculateBatch(activationFunction);
        }

        layers.get((layers.size() - 1)).setStimuli(result);
        if(isSoftmax) {
            result = layers.get(layers.size() - 1).softmaxBatch();
        } else {
            result = layers.get(layers.size() - 1).calculateBatch(activationFunction);
        }

        SimpleMatrix errors = result.minus(labels);
        SimpleMatrix derivatives = layers.get(layers.size() - 1).calculateBatch(getDerivative(activationFunction, isSoftmax));

        System.out.println(errors);
    }

    public ActivationFunction getDerivative(ActivationFunction activationFunction, boolean isSoftmax) {
        if(isSoftmax) {
            return ActivationFunction.SIGMOID;
        } else {
            if(activationFunction == ActivationFunction.SIGMOID) {
                return ActivationFunction.SIGMOIDD;
            } else if(activationFunction == ActivationFunction.TANH) {
                return ActivationFunction.TANHD;
            } else if(activationFunction == ActivationFunction.RELU) {
                return ActivationFunction.SOFTPLUSD;
            }
        }

        return null;
    }
}
