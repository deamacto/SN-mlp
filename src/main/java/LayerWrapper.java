import org.ejml.simple.SimpleMatrix;

public class LayerWrapper {
    Layer layer;
    SimpleMatrix stimuli;
    SimpleMatrix errors;

    public LayerWrapper(Layer layer) {
        this.layer = layer;
    }

    public void setStimuli(SimpleMatrix input) {
        this.stimuli = layer.stimulateBatch(input);
    }

    public SimpleMatrix calculateBatch(ActivationFunction activationFunction) {
        return layer.calculateBatch(activationFunction, this.stimuli);
    }

    public SimpleMatrix softmaxBatch() {
        return layer.softmaxBatch(this.stimuli);
    }

    public SimpleMatrix calculateError(SimpleMatrix nextErrors, SimpleMatrix nextWeights,ActivationFunction activationFunction) {
        return layer.calculateError(nextErrors, nextWeights, activationFunction, stimuli.copy());
    }
}
