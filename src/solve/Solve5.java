package solve;

import static solve.Utils.*;

public class Solve5 {
    
    public static void main(String[] args) {
        double D = 1.7;
        double R = 4.255243240108898;
        Curve curve = new Curve(D, R);
        double dT = (curve.thetaToLen(INIT_THETA) - curve.thetaToLen(curve.ThetaC)) / HEAD_VELOCITY;
        Moment[] moments = new Moment[201];
        
        for (int time = -100; time <= 100; time++) {
            moments[time + 100] = Moment.of(time + dT, curve);
        }
        
        double maxV = 0.0;
        for (int i = 1; i <= 200; i++) {
            double[] t0 = moments[i - 1].thetas();
            double[] t1 = moments[i].thetas();
            for (int j = 0; j <= NUM_BENCH; j++) {
                double v = curve.thetaToLen(t0[j]) - curve.thetaToLen(t1[j]);
                maxV = Math.max(maxV, v);
            }
        }
        
        System.out.println(maxV);
    }
}
