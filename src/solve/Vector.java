package solve;

public class Vector {
    
    public double x, y;
    
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector add(Vector o) {
        return new Vector(x + o.x, y + o.y);
    }
    
    public Vector rotate(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        return new Vector(x * cos - y * sin, x * sin + y * cos);
    }
    
    public Vector midpoint(Vector o) {
        return new Vector((x + o.x) * 0.5, (y + o.y) * 0.5);
    }
    
    public double angle(Vector o) {
        return Math.atan2(o.y - y, o.x - x);
    }
    
    public double dot(Vector o) {
        return x * o.x + y * o.y;
    }
    
    public double cross(Vector o) {
        return x * o.y - y * o.x;
    }
    
    public double length() {
        return Math.hypot(x, y);
    }
    
    public Vector norm() {
        double f = 1.0 / length();
        return new Vector(x * f, y * f);
    }
    
    public void print() {
        System.out.printf("%.06f, %.06f, ", x, y);
    }
}
