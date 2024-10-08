package solve;

import static solve.Utils.*;

public class Solve2 {
    
    public static void main(String[] args) {
        Curve curve = new Curve(0.55);
        double left = 411;
        double right = 413;
        for (; ; ) {
            double middle = 0.5 * (left + right);
            double distance = distance(points(thetas(middle, curve), curve));
            if (Math.abs(distance) < EPS) {
                System.out.println(middle);
                break;
            }
            if (distance > 0.0) {
                left = middle;
            } else {
                right = middle;
            }
        }
    }
}
