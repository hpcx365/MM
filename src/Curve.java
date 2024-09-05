public class Curve {
    
    public static final double ONE_OVER_2PI = 1.0 / (2.0 * Math.PI);
    
    /*螺距*/
    public double d;
    /*圈数*/
    public int n;
    
    public double x(double theta) {
        return ONE_OVER_2PI * d * theta * Math.cos(theta);
    }
    
    public double y(double theta) {
        return ONE_OVER_2PI * d * theta * Math.sin(theta);
    }
    
    public Vector p(double theta) {
        return new Vector(x(theta), y(theta));
    }
    
    public double L(double theta) {
        return ONE_OVER_2PI * 0.5 * d * theta * theta;
    }
}
