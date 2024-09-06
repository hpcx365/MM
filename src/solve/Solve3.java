package solve;

import java.util.Random;

import static solve.Utils.*;

public class Solve3 {
    
    public static final Random rnd = new Random();
    
    public static void main(String[] args) {
//        double left = 0.4503373930218438;
//        double right = 0.450337393021875;
//        for (; ; ) {
//            double middle = 0.5 * (left + right);
//            double distance = check(middle);
//            System.out.println(middle + "\t\t" + distance);
//            if (distance > 0.0) {
//                right = middle;
//            } else {
//                left = middle;
//            }
//        }
        System.out.println(check(0.45033739302186718));
    }
    
    public static double check(double d) {
        Curve curve = new Curve(d);
        double endTheta = MAX_TURN_RADIUS / curve.k;
//        double endTime = (curve.L(INIT_THETA) - curve.L(endTheta)) / HEAD_VELOCITY;
        double left = 216.32183;
        double right = 216.32184;
        
        double distance = Double.MAX_VALUE;
        for (long n = 10000, i = n; i >= 0; i--) {
            double time0 = left + (right - left) * i / n;
            double distance0 = distance(points(time0, curve));
            if (distance > distance0) {
                distance = distance0;
                if (distance < 0.0) {
                    return distance;
                }
            }
        }
        return distance;
    }
}
