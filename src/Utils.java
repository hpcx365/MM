public class Utils {
    
    public static final int NUM_BENCH = 223;
    public static final double HEAD_LENGTH = 3.41;
    public static final double COMMON_LENGTH = 2.20;
    public static final double BENCH_WIDTH = 0.30;
    public static final double EXTENT_LENGTH = 0.275;
    
    public static boolean collide(double x11, double y11, double x12, double y12, double x21, double y21, double x22, double y22) {
        return false;
    }
    
    public static boolean contain(double x, double y, double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        return cross(x1, y1, x2, y2, x, y) > 0.0 && cross(x2, y2, x3, y3, x, y) > 0.0 && cross(x3, y3, x4, y4, x, y) > 0.0 && cross(x4, y4, x1, y1, x, y) > 0.0;
    }
    
    public static double cross(double x0, double y0, double x1, double y1, double x2, double y2) {
        return cross(x1 - x0, y1 - y0, x2 - x0, y2 - y0);
    }
    
    public static double cross(double x1, double y1, double x2, double y2) {
        return x1 * y2 - x2 * y1;
    }
}
