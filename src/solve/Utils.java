package solve;

import static solve.Solve.*;

public class Utils {
    
    public static OBB toOBB(Vector p1, Vector p2, boolean isHead) {
        return new OBB(p1.midpoint(p2), isHead ? HEAD_LENGTH : COMMON_LENGTH, BENCH_WIDTH, p1.angle(p2));
    }
    
    public static boolean collide(OBB obb1, OBB obb2) {
        if (detached(obb1, obb2, new Vector(Math.cos(obb1.angle), Math.sin(obb1.angle)))) {
            return false;
        }
        if (detached(obb1, obb2, new Vector(-Math.sin(obb1.angle), Math.cos(obb1.angle)))) {
            return false;
        }
        if (detached(obb1, obb2, new Vector(Math.cos(obb2.angle), Math.sin(obb2.angle)))) {
            return false;
        }
        if (detached(obb1, obb2, new Vector(-Math.sin(obb2.angle), Math.cos(obb2.angle)))) {
            return false;
        }
        return true;
    }
    
    private static boolean detached(OBB obb1, OBB obb2, Vector axis) {
        Range range1 = projection(obb1, axis);
        Range range2 = projection(obb2, axis);
        return range1.min > range2.max || range2.min > range1.max;
    }
    
    private static Range projection(OBB obb, Vector axis) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (Vector point : obb.points) {
            double proj = point.dot(axis);
            min = Math.min(min, proj);
            max = Math.max(max, proj);
        }
        return new Range(min, max);
    }
    
    private static class Range {
        
        public double min, max;
        
        public Range(double min, double max) {
            this.min = min;
            this.max = max;
        }
    }
}
