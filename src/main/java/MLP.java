import org.ejml.simple.SimpleMatrix;

import java.util.ArrayList;

public class MLP {
    ArrayList<Layer> layers = new ArrayList<>();

    public MLP(ArrayList<Integer> neuronCount) {
        layers.add(new Layer(Consts.NEURONS_INPUT, 1));

        for(int i = 0; i < neuronCount.size(); i++) {
            layers.add(new Layer(neuronCount.get(i), layers.get(i).biases.numRows()));
        }

        layers.add(new Layer(Consts.NEURONS_OUTPUT, layers.get(layers.size() - 1).biases.numRows()));
    }

    public boolean calculate(Input input, ActivationFunction activationFunction, boolean isSoftmax) {
        SimpleMatrix result = layers.get(0).calculate(input.toSimpleMatrix(), activationFunction);
        for(int i = 1; i < layers.size() - 2; i++) {
            result = layers.get(i).calculate(result, activationFunction);
        }

        if(isSoftmax) {
            result = layers.get(layers.size() - 1).softmax(result);
        } else {
            result = layers.get(layers.size() - 1).calculate(result, activationFunction);
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
        return maxIndex == input.d;
    }
}
