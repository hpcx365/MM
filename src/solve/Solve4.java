package solve;

import static solve.Utils.*;

public class Solve4 {
    
    public static void main(String[] args) {
        double leftR = 4.2553;
        double rightR = 4.255407;
        for (; ; ) {
            double middleR = 0.5 * (leftR + rightR);
            if (check(middleR)) {
                rightR = middleR;
            } else {
                leftR = middleR;
            }
            System.out.println(middleR);
        }
        
//        System.out.println(check(4.2554455));
//        System.out.println(check(4.2554456));
//        System.out.println(check(4.2554457));
//        System.out.println(check(4.2554458));
//        System.out.println(check(4.2554459));
//        System.out.println(check(4.2554460));

//        double D = 1.7;
//        double R = 4.255243240108898;
//        Curve curve = new Curve(D, R);
//        double dT = (curve.thetaToLen(INIT_THETA) - curve.thetaToLen(curve.ThetaC)) / HEAD_VELOCITY;
//        for (int time = -100; time <= 100; time++) {
//            Moment moment = Moment.of(time + dT, curve);
//            Vector[] points = moment.velocities();
//            for (Vector p : points) {
//                System.out.printf("%.06f, ", p.module());
//            }
//            System.out.println();
//        }
    }
    
    public static boolean check(double R) {
        Curve curve = new Curve(1.7, R);
        double dT = (curve.thetaToLen(INIT_THETA) - curve.thetaToLen(curve.ThetaC)) / HEAD_VELOCITY;
        double leftT = 12.852;
        double rightT = 12.854;
        double prevTheta = nextTheta(curve.timeToTheta(leftT + dT), HEAD_LENGTH - 2.0 * EXTENT_LENGTH, curve);
        for (int n = 1000000, i = 1; i <= n; i++) {
            double time = leftT + (rightT - leftT) * i / n;
            double currentTheta = nextTheta(curve.timeToTheta(time + dT), HEAD_LENGTH - 2.0 * EXTENT_LENGTH, curve);
//            System.out.println(i + " " + time);
            if (currentTheta - prevTheta >= 1.0e-8) {
                return false;
            }
            prevTheta = currentTheta;
        }
        return true;
    }
}
