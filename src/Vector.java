public class Vector {
    
    public double x, y;
    
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
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
}
