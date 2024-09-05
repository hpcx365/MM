package solve;

import static solve.Utils.HPI;
import static solve.Utils.OODPI;

public class Curve {
    
    public final double d, k; // 螺距, 螺距/2pi
    
    public Curve(double d) {
        this.d = d;
        this.k = OODPI * d;
    }
    
    public double x(double theta) {
        return k * theta * Math.cos(theta);
    }
    
    public double y(double theta) {
        return k * theta * Math.sin(theta);
    }
    
    public Vector p(double theta) {
        return new Vector(x(theta), y(theta));
    }
    
    public double tan(double theta) {
        return theta + HPI - Math.atan2(1.0, theta);
    }
    
    public double L(double theta) {
        return 0.5 * k * theta * theta;
    }
    
    public double chord(double theta1, double theta2) {
        return Math.hypot(x(theta1) - x(theta2), y(theta1) - y(theta2));
    }
}
