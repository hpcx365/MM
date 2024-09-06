package solve;

public class Utils {
    
    public static final double PI = Math.PI;
    public static final double HPI = 0.5 * PI;
    public static final double DPI = 2.0 * PI;
    public static final double EPS = 1.0e-12;
    
    public static final int NUM_BENCH = 223;
    public static final double INIT_THETA = 16 * DPI;
    public static final double HEAD_LENGTH = 3.41;
    public static final double COMMON_LENGTH = 2.20;
    public static final double BENCH_WIDTH = 0.30;
    public static final double EXTENT_LENGTH = 0.275;
    public static final double HEAD_VELOCITY = 1.0;
    public static final double MAX_TURN_RADIUS = 4.5;
    
    public static double[] thetas(double time, Curve curve) {
        double[] res = new double[NUM_BENCH + 1];
        res[0] = curve.timeToTheta(time);
        for (int i = 1; i <= NUM_BENCH; i++) {
            res[i] = nextTheta(res[i - 1], (i == 1 ? HEAD_LENGTH : COMMON_LENGTH) - 2.0 * EXTENT_LENGTH, curve);
        }
        return res;
    }
    
    public static Vector[] points(double[] thetas, Curve curve) {
        Vector[] res = new Vector[NUM_BENCH + 1];
        for (int i = 0; i <= NUM_BENCH; i++) {
            res[i] = curve.pointAt(thetas[i]);
        }
        return res;
    }
    
    public static Vector[] velocities(double[] thetas, Vector[] points, Curve curve) {
        Vector[] res = new Vector[NUM_BENCH + 1];
        res[0] = Vector.unit(curve.dir(thetas[0])).mul(HEAD_VELOCITY);
        for (int i = 1; i <= NUM_BENCH; i++) {
            double dir = curve.dir(thetas[i]);
            double m = res[i - 1].dot(points[i - 1].sub(points[i]).norm()) / Math.cos(dir - points[i].angle(points[i - 1]));
            res[i] = Vector.unit(dir).mul(m);
        }
        return res;
    }
    
    public static double distance(Vector[] points) {
        OBB[] obb = new OBB[NUM_BENCH];
        for (int i = 0; i < NUM_BENCH; i++) {
            obb[i] = OBB.of(points[i], points[i + 1], i == 0);
        }
        double res = Double.MAX_VALUE;
        for (int i = 0; i < NUM_BENCH; i++) {
            for (int j = i + 2; j < NUM_BENCH; j++) {
                res = Math.min(res, obb[i].distance(obb[j]));
            }
        }
        return res;
    }
    
    public static double nextTheta(double theta, double chord, Curve curve) {
        double deltaTheta = DPI / 720.0; // 步长0.5度
        double currentTheta = theta + deltaTheta;
        while (curve.chord(theta, currentTheta) < chord) {
            currentTheta += deltaTheta;
        }
        
        double left = theta;
        double right = currentTheta;
        for (; ; ) {
            double middle = 0.5 * (left + right);
            double chord1 = curve.chord(theta, middle);
            if (equal(chord, chord1)) {
                return middle;
            }
            if (chord < chord1) {
                right = middle;
            } else {
                left = middle;
            }
        }
    }
    
    public static boolean equal(double a, double b) {
        return Math.abs(a - b) < EPS;
    }
}
