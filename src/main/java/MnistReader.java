import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class MnistReader {

    public static Digit[] readData(String dataFilePath, String labelFilePath) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(dataFilePath)));
        int magicNumber = dataInputStream.readInt();
        int numberOfItems = dataInputStream.readInt();
        int nRows = dataInputStream.readInt();
        int nCols = dataInputStream.readInt();

        DataInputStream labelInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(labelFilePath)));
        int labelMagicNumber = labelInputStream.readInt();
        int numberOfLabels = labelInputStream.readInt();

        Digit[] digits = new Digit[numberOfItems];

        if(numberOfItems == numberOfLabels) {
            for(int i = 0; i < numberOfItems; i++) {
                Digit digit = new Digit(nCols * nRows, labelInputStream.readUnsignedByte());
                for (int j = 0; j < nCols * nRows; j++) {
                        digit.setValue(j, dataInputStream.readUnsignedByte());
                }

                digits[i] = digit;
            }
            dataInputStream.close();
            labelInputStream.close();
        }

        return digits;
    }
}
