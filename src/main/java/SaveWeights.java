import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SaveWeights {
    public static void serializeData(Layer layer) {
        try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("layer.bin"))) {
            outputStream.writeObject(layer);
        } catch(Exception e) {
            return;
        }
    }

    public static Layer deserializeData() {
        try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("layer.bin"))) {
            Layer layer = (Layer) inputStream.readObject();
            return layer;
        } catch(Exception e) {
            return null;
        }
    }
}
