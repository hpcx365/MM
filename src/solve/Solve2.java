package solve;

import static solve.Utils.*;

public class Solve2 {
    
    public static void main(String[] args) throws Exception {
        Curve curve = new Curve(0.55);
        double left = 412.3;
        double right = 412.5;
        for (; ; ) {
            double middle = 0.5 * (left + right);
            double distance = distance(points(middle, curve));
            if (equal(distance, 0.0)) {
                System.out.printf("%.06f", middle);
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
