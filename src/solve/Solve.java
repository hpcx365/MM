package solve;

public class Solve {
    
    public static final double PI = Math.PI;
    public static final double DPI = 2.0 * PI;
    public static final double HPI = 0.5 * PI;
    public static final double OODPI = 1.0 / DPI;
    
    public static final int NUM_BENCH = 223;
    public static final double HEAD_LENGTH = 3.41;
    public static final double COMMON_LENGTH = 2.20;
    public static final double BENCH_WIDTH = 0.30;
    public static final double EXTENT_LENGTH = 0.275;
    public static final double INIT_THETA = 16 * DPI;
    public static final double HEAD_VELOCITY = 1.0;
    
    public static final double EPS = 1.0e-9;
    
    public static Vector[] points(double time, Curve curve) {
    
    }
    
    public static double nextTheta(double theta, double chord, Curve curve) {
        double x0 = curve.x(theta);
        double y0 = curve.y(theta);
        double angle = curve.tan(theta);
        
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
