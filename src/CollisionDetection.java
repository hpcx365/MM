public class CollisionDetection {
    
    public static boolean intersect(OBB2D obb1, OBB2D obb2) {
        Vector2[] axes = getAxes(obb1, obb2);
        for (Vector2 axis : axes) {
            if (!overlap(obb1, obb2, axis)) {
                return false;
            }
        }
        return true;
    }
    
    private static Vector2[] getAxes(OBB2D obb1, OBB2D obb2) {
        Vector2[] axes = new Vector2[4];
        axes[0] = new Vector2(Math.cos(obb1.angle), Math.sin(obb1.angle));
        axes[1] = new Vector2(-Math.sin(obb1.angle), Math.cos(obb1.angle));
        axes[2] = new Vector2(Math.cos(obb2.angle), Math.sin(obb2.angle));
        axes[3] = new Vector2(-Math.sin(obb2.angle), Math.cos(obb2.angle));
        return axes;
    }
    
    private static boolean overlap(OBB2D obb1, OBB2D obb2, Vector2 axis) {
        double min1 = projection(obb1, axis).min;
        double max1 = projection(obb1, axis).max;
        double min2 = projection(obb2, axis).min;
        double max2 = projection(obb2, axis).max;
        
        return max1 < min2 || max2 < min1;
    }
    
    private static Range projection(OBB2D obb, Vector2 axis) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (Vector2 point : obb.points) {
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
