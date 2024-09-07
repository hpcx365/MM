package solve;

import java.io.FileWriter;
import java.io.PrintWriter;

import static solve.Utils.*;

public class Solve4 {
    
    public static void main(String[] args) throws Exception {
        Curve curve = null;
        double leftR = 4.2;
        double rightR = 4.3;
        while (rightR - leftR >= 1.0e-12) {
            double middleR = 0.5 * (leftR + rightR);
            curve = new Curve(1.7, middleR);
            double theta = getTheta(curve);
            Vector o = curve.pointAt(0.0);
            Vector h = curve.pointAt(theta);
            if (o.sub(h).module() >= HEAD_LENGTH - 2.0 * EXTENT_LENGTH) {
                rightR = middleR;
            } else {
                leftR = middleR;
            }
            System.out.println(middleR + "  " + theta);
        }
        
        PrintWriter pw = new PrintWriter(new FileWriter("4p.csv"));
        PrintWriter vw = new PrintWriter(new FileWriter("4v.csv"));
        
        double dT = (curve.thetaToLen(INIT_THETA) - curve.thetaToLen(curve.ThetaC)) / HEAD_VELOCITY;
        for (int time = -100; time <= 100; time++) {
            Moment moment = Moment.of(time + dT, curve);
            for (Vector p : moment.points()) {
                pw.printf("%.06f, %.06f, ", p.x(), p.y());
            }
            for (Vector v : moment.velocities()) {
                vw.printf("%.06f, ", v.module());
            }
            pw.println();
            vw.println();
        }
        
        pw.close();
        vw.close();
    }
    
    public static double getTheta(Curve curve) {
        double leftTheta = -curve.ThetaC;
        double rightTheta = -curve.ThetaC;
        
        while (curve.O2.sub(curve.O1).cross(curve.pointAt(leftTheta).sub(curve.O2)) <= 0.0) {
            leftTheta -= 0.01;
        }
        
        for (; ; ) {
            double middleTheta = 0.5 * (leftTheta + rightTheta);
            double c = curve.O2.sub(curve.O1).cross(curve.pointAt(middleTheta).sub(curve.O2));
            if (Math.abs(c) <= 1.0e-12) {
                return middleTheta;
            }
            if (c <= 0.0) {
                rightTheta = middleTheta;
            } else {
                leftTheta = middleTheta;
            }
        }
    }
}
