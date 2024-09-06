package solve;

import static solve.Utils.*;

public class Solve4 {
    
    public static void main(String[] args) {
//        double leftR = 4.217;
//        double rightR = 4.218;
//        for (; ; ) {
//            double middleR = 0.5 * (leftR + rightR);
//            double deltaTheta = check(middleR);
//            if (deltaTheta >= 0.0) {
//                rightR = middleR;
//            } else {
//                leftR = middleR;
//            }
//            System.out.println(middleR + "  " + deltaTheta);
//        }
        
        double D = 1.7;
        double R = 4.217401573082793;
        Curve curve = new Curve(D, R);
        double dT = (curve.thetaToLen(INIT_THETA) - curve.thetaToLen(curve.ThetaC)) / HEAD_VELOCITY;
        for (int time = -100; time <= 100; time++) {
            Moment moment = Moment.of(time + dT, curve);
            Vector[] points = moment.velocities();
            for (Vector p : points) {
                System.out.printf("%.06f, ", p.length());
            }
            System.out.println();
        }
    }
    
    public static double check(double R) {
        Curve curve = new Curve(1.7, R);
        double dT = (curve.thetaToLen(INIT_THETA) - curve.thetaToLen(curve.ThetaC)) / HEAD_VELOCITY;
        double leftT = 12.3;
        double rightT = 13.4;
        double res = Double.MAX_VALUE;
        double prevTheta = nextTheta(curve.timeToTheta(leftT + dT), HEAD_LENGTH - 2.0 * EXTENT_LENGTH, curve);
        for (int n = 100000, i = 1; i <= n; i++) {
            double time = leftT + (rightT - leftT) * i / n;
            double currentTheta = nextTheta(curve.timeToTheta(time + dT), HEAD_LENGTH - 2.0 * EXTENT_LENGTH, curve);
            res = Math.min(res, prevTheta - currentTheta);
            if (res <= 0.0) {
                return res;
            }
        }
        return res;
    }
}
