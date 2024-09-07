package solve;

import static solve.Utils.*;

public class Curve {
    
    public final double D, R, K;
    public final double AlphaC;
    public final double ThetaC;
    public final double R1, R2;
    public final Vector O1, O2;
    
    public Curve(double D) {
        this(D, 1.0e-8);
    }
    
    public Curve(double D, double R) {
        this.D = D;
        this.R = R;
        this.K = D / DPI;
        this.ThetaC = R / K;
        this.AlphaC = Math.atan(1.0 / ThetaC);
        this.R1 = 2.0 / 3.0 * R * Math.sqrt(1.0 + 1.0 / (ThetaC * ThetaC));
        this.R2 = 0.5 * R1;
        Vector t = Vector.unit(ThetaC - AlphaC);
        this.O1 = f0(ThetaC).sub(t.mul(R1));
        this.O2 = f0(-ThetaC).add(t.mul(R2));
    }
    
    private Vector f0(double theta) {
        double x = K * theta * Math.cos(theta);
        double y = K * Math.abs(theta) * Math.sin(theta);
        return new Vector(x, y);
    }
    
    private Vector f1(double theta) {
        double t = ThetaC + AlphaC + theta / ThetaC * (PI - 2.0 * AlphaC);
        return O1.sub(Vector.unit(t).mul(R1));
    }
    
    private Vector f2(double theta) {
        double t = ThetaC + AlphaC - theta / ThetaC * (PI - 2.0 * AlphaC);
        return O2.add(Vector.unit(t).mul(R2));
    }
    
    public Vector pointAt(double theta) {
        if (Math.abs(theta) >= ThetaC) {
            return f0(theta);
        }
        return theta >= 0.0 ? f1(theta) : f2(theta);
    }
    
    public double thetaToLen(double theta) {
        if (theta >= ThetaC) {
            return R1 * (PI - 2.0 * AlphaC) + 0.5 * K * theta * theta - 0.5 * K * ThetaC * ThetaC;
        }
        if (theta >= 0.0) {
            return theta / ThetaC * R1 * (PI - 2.0 * AlphaC);
        }
        if (theta >= -ThetaC) {
            return theta / ThetaC * R2 * (PI - 2.0 * AlphaC);
        }
        return -R2 * (PI - 2.0 * AlphaC) + 0.5 * K * ThetaC * ThetaC - 0.5 * K * theta * theta;
    }
    
    public double lenToTheta(double len) {
        if (len >= thetaToLen(ThetaC)) {
            return Math.sqrt(2.0 / K * (len + 0.5 * K * ThetaC * ThetaC - R1 * (PI - 2.0 * AlphaC)));
        }
        if (len >= 0) {
            return len * ThetaC / (R1 * (PI - 2.0 * AlphaC));
        }
        if (len >= thetaToLen(-ThetaC)) {
            return len * ThetaC / (R2 * (PI - 2.0 * AlphaC));
        }
        return -Math.sqrt(-2.0 / K * (len + R2 * (PI - 2.0 * AlphaC) - 0.5 * K * ThetaC * ThetaC));
    }
    
    public double timeToTheta(double time) {
        return lenToTheta(thetaToLen(INIT_THETA) - time * HEAD_VELOCITY);
    }
    
    public double dir(double theta) {
        if (theta >= ThetaC) {
            return theta - HPI - Math.atan2(1.0, theta);
        }
        if (theta <= -ThetaC) {
            return -theta + HPI - Math.atan2(1.0, -theta);
        }
        if (theta >= 0.0) {
            return ThetaC + AlphaC + (theta / ThetaC * (PI - 2.0 * AlphaC) - HPI);
        } else {
            return ThetaC + AlphaC - (theta / ThetaC * (PI - 2.0 * AlphaC) - HPI);
        }
    }
    
    public double chord(double theta1, double theta2) {
        return pointAt(theta1).sub(pointAt(theta2)).module();
    }
}
