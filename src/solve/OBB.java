package solve;

import static solve.Utils.*;

public class OBB {
    
    public Vector center; // 中心点
    public double width, height; // 宽度和高度
    public double angle; // 旋转角度
    public Vector[] points; // 顶点，按逆时针方向给出
    
    public OBB(Vector center, double width, double height, double angle) {
        this.center = center;
        this.width = width;
        this.height = height;
        this.angle = angle;
        double hw = 0.5 * width;
        double hh = 0.5 * height;
        this.points = new Vector[] {
                center.add(new Vector(-hw, -hh).rotate(angle)),
                center.add(new Vector(hw, -hh).rotate(angle)),
                center.add(new Vector(hw, hh).rotate(angle)),
                center.add(new Vector(-hw, hh).rotate(angle))
        };
    }
    
    public static OBB of(Vector p1, Vector p2, boolean isHead) {
        return new OBB(p1.midpoint(p2), isHead ? HEAD_LENGTH : COMMON_LENGTH, BENCH_WIDTH, p1.angle(p2));
    }
    
    public double distance(OBB o) {
        double res = -Double.MAX_VALUE;
        res = Math.max(res, distance(o, new Vector(Math.cos(angle), Math.sin(angle))));
        res = Math.max(res, distance(o, new Vector(-Math.sin(angle), Math.cos(angle))));
        res = Math.max(res, distance(o, new Vector(Math.cos(o.angle), Math.sin(o.angle))));
        res = Math.max(res, distance(o, new Vector(-Math.sin(o.angle), Math.cos(o.angle))));
        return res;
    }
    
    private double distance(OBB o, Vector axis) {
        Range range1 = projection(axis);
        Range range2 = o.projection(axis);
        return Math.max(range1.min() - range2.max(), range2.min() - range1.max());
    }
    
    private Range projection(Vector axis) {
        double min = Double.MAX_VALUE;
        double max = -Double.MAX_VALUE;
        for (Vector point : points) {
            double proj = point.dot(axis);
            min = Math.min(min, proj);
            max = Math.max(max, proj);
        }
        return new Range(min, max);
    }
}
