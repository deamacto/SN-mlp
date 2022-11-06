import java.util.Arrays;

public class Digit {
    private double[] digit;
    private int label;

    public Digit(int size, int label) {
        digit = new double[size];
        this.label = label;
    }

    public double[] getDigit() {
        return digit;
    }

    public double getValue(int index) {
        return digit[index];
    }

    public void setValue(int index, int value) {
        digit[index] = value;
    }

    public int getLabel() {
        return label;
    }

    @Override
    public String toString() {
        StringBuilder digitString =new StringBuilder().append("Label: " + label + "\n");
        for(int i = 0; i < digit.length; i++) {
            if(i % 28 == 0) {
                digitString.append("\n" + digit[i] + " ");
            } else {
                digitString.append(digit[i] + " ");
            }
        }
        return String.valueOf(digitString);
    }
}
