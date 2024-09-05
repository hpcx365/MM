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
    
    public static Vector[] points(double t, Curve c) {
    
    }
    
    public static double nextTheta(double theta, double chord, Curve c) {
        double x0 = c.x(theta);
        double y0 = c.y(theta);
        double angle = c.tan(theta);
        
        double dt = DPI / 720.0; // 步长0.5度
        
    }
}
