public class Main {
    public static void main(String[] args) {
        MLP mpl = new MLP(2, 10);
        System.out.println(mpl.calculate(new Input(1, 1), ActivationFunction.RELU, true));
    }
}
