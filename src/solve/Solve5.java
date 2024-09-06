package solve;

import static solve.Utils.HEAD_VELOCITY;
import static solve.Utils.INIT_THETA;

public class Solve5 {
    
    public static final int NUM_THREAD = 20;
    public static final double[] MAX_V = new double[NUM_THREAD];
    
    public static void main(String[] args) throws InterruptedException {
        double D = 1.7;
        double R = 4.217401573082793;
        Curve curve = new Curve(D, R);
        double dT = (curve.thetaToLen(INIT_THETA) - curve.thetaToLen(curve.ThetaC)) / HEAD_VELOCITY;
        
        Thread[] threads = new Thread[NUM_THREAD];
        
        long n = 200000000;
        for (int i = 0; i < NUM_THREAD; i++) {
            int j = i;
            threads[j] = new Thread(() -> {
                double leftT = -200.0;
                double rightT = 200.0;
                long s = n / NUM_THREAD * j;
                long e = n / NUM_THREAD * (j + 1);
                for (long step = s; step < e; step++) {
                    double time = leftT + (rightT - leftT) * step / n;
                    Moment moment = Moment.of(time + dT, curve);
                    Vector[] points = moment.velocities();
                    for (Vector p : points) {
                        double v = p.length();
                        MAX_V[j] = Math.max(MAX_V[j], v);
                    }
                    if (j == 0 && step % 1000 == 0) {
                        double maxV = 0.0;
                        for (int k = 0; k < NUM_THREAD; k++) {
                            maxV = Math.max(maxV, MAX_V[k]);
                        }
                        System.out.println(maxV);
                    }
                }
            });
            threads[j].start();
        }
        
        Thread.sleep(1000);
        
        for (int i = 0; i < NUM_THREAD; i++) {
            threads[i].join();
        }
    }
}
