package solve;

public record Moment(double time, Curve curve, double[] thetas, Vector[] points, Vector[] velocities) {
    
    public static Moment of(double time, Curve curve) {
        double[] thetas = Utils.thetas(time, curve);
        Vector[] points = Utils.points(thetas, curve);
        Vector[] velocities = Utils.velocities(thetas, points, curve);
        return new Moment(time, curve, thetas, points, velocities);
    }
}
