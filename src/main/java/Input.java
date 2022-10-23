import org.ejml.simple.SimpleMatrix;

public class Input {
    public int x;
    public int d;

    public Input(int x, int d) {
        this.x = x;
        this.d = d;
    }

    public SimpleMatrix toSimpleMatrix() {
        return new SimpleMatrix(1, 1);
    }
}
