package solve;

import static solve.Utils.*;

public class Solve3 {
    
    public static void main(String[] args) {
        double leftD = 0.450;
        double rightD = 0.451;
        for (; ; ) {
            double middleD = 0.5 * (leftD + rightD);
            System.out.println(middleD);
            if (check(middleD)) {
                rightD = middleD;
            } else {
                leftD = middleD;
            }
        }
    }
    
    public static boolean check(double D) {
        Curve curve = new Curve(D);
        double leftT = 216.3;
        double rightT = 216.4;
        for (long n = 1000, i = 0; i <= n; i++) {
            double time = leftT + (rightT - leftT) * i / n;
            if (distance(points(thetas(time, curve), curve)) <= 0.0) {
                System.out.printf("%.06f\n", time);
                return false;
            }
        }
        return true;
    }
}
