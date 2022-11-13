import org.ejml.data.DMatrixRMaj;
import org.ejml.simple.SimpleMatrix;

public class Util {
    public static double[] toArray(SimpleMatrix matrix) {
        return ((DMatrixRMaj)matrix.getMatrix()).getData();
    }
}
