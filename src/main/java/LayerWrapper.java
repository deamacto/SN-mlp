import org.ejml.simple.SimpleMatrix;

public class LayerWrapper {
    Layer layer;
    SimpleMatrix stimuli;

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
}
