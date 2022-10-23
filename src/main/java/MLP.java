import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;

public class MLP {
    ArrayList<Layer> layers = new ArrayList<>();

    public MLP(int hiddenLayers, int neuronCount) {
        layers.add(new Layer(neuronCount, 1));

        for(int i = 0; i < hiddenLayers; i++) {
            layers.add(new Layer(neuronCount, neuronCount));
        }

        layers.add(new Layer(10, neuronCount));
    }

    public SimpleMatrix calculate(Input input, ActivationFunction activationFunction, boolean isSoftmax) {
        SimpleMatrix result = layers.get(0).calculate(input.toSimpleMatrix(), activationFunction);
        for(int i = 1; i < layers.size() - 2; i++) {
            result = layers.get(i).calculate(result, activationFunction);
        }

        if(isSoftmax) {
            result = layers.get(layers.size() - 1).softmax(result);
        } else {
            result = layers.get(layers.size() - 1).calculate(result, activationFunction);
        }

        return result;
    }
}
