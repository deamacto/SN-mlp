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

    public SimpleMatrix calculateBatch(SimpleMatrix input, ActivationFunction activationFunction) {
        setStimuli(input);
        return layer.calculateBatch(input, activationFunction, this.stimuli);
    }

    public SimpleMatrix softmaxBatch(SimpleMatrix input) {
        setStimuli(input);
        return layer.softmaxBatch(input, this.stimuli);
    }
}
