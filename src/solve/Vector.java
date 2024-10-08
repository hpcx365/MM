package solve;

public record Vector(double x, double y) {
    
    public static Vector unit(double theta) {
        return new Vector(Math.cos(theta), Math.sin(theta));
    }
    
    public Vector mul(double m) {
        return new Vector(x * m, y * m);
    }
    
    public Vector add(Vector o) {
        return new Vector(x + o.x, y + o.y);
    }
    
    public Vector sub(Vector o) {
        return new Vector(x - o.x, y - o.y);
    }
    
    public double dot(Vector o) {
        return x * o.x + y * o.y;
    }
    
    public double cross(Vector o) {
        return x * o.y - y * o.x;
    }
    
    public Vector vertical() {
        return new Vector(-y, x);
    }
    
    public Vector rotate(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        return new Vector(x * cos - y * sin, x * sin + y * cos);
    }
    
    public Vector midpoint(Vector o) {
        return new Vector((x + o.x) * 0.5, (y + o.y) * 0.5);
    }
    
    public Vector lambda(Vector o, double l) {
        double m = 1.0 - l;
        return new Vector(x * l + o.x * m, y * l + o.y * m);
    }
    
    public double slop(Vector o) {
        return Math.atan2(o.y - y, o.x - x);
    }
    
    public double module() {
        return Math.hypot(x, y);
    }
    
    public Vector norm() {
        double f = 1.0 / module();
        return new Vector(x * f, y * f);
    }
}
