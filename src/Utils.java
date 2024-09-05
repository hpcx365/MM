public class Utils {
    
    public static final int NUM_BENCH = 223;
    public static final double HEAD_LENGTH = 3.41;
    public static final double COMMON_LENGTH = 2.20;
    public static final double BENCH_WIDTH = 0.30;
    public static final double EXTENT_LENGTH = 0.275;
    
    public static OBB toOBB(Vector p1, Vector p2, boolean isHead) {
        return new OBB(p1.midpoint(p2), isHead ? HEAD_LENGTH : COMMON_LENGTH, BENCH_WIDTH, p1.angle(p2));
    }
}
