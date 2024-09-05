package solve;

public class Test {
    
    public static void main(String[] args) {
        Curve curve = new Curve(0.55);
        System.out.println(curve.L(curve.toTheta(0.0)));
    }
}
