package solve;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Solve {
    
    public static final double PI = Math.PI;
    public static final double DPI = 2.0 * PI;
    public static final double HPI = 0.5 * PI;
    public static final double OODPI = 1.0 / DPI;
    public static final double EPS = 1.0e-9;
    
    public static final int NUM_BENCH = 223;
    public static final double HEAD_LENGTH = 3.41;
    public static final double COMMON_LENGTH = 2.20;
    public static final double BENCH_WIDTH = 0.30;
    public static final double EXTENT_LENGTH = 0.275;
    public static final double INIT_THETA = 16 * DPI;
    public static final double HEAD_VELOCITY = 1.0;
    
    public static void main(String[] args) throws Exception {
        Curve curve = new Curve(0.55);
        Vector[] points = points(300, curve);
        BufferedImage img = Utils.draw(points, curve);
        ImageIO.write(img, "png", new File("img.png"));
        
        JFrame frame = new JFrame();
        frame.setSize(1080, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setUI(new PanelUI() {
            
            @Override public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHints(Utils.renderingHints);
                g2d.drawImage(img, 0, 0, c.getWidth(), c.getHeight(), c);
            }
        });
        
        frame.setContentPane(panel);
        frame.setVisible(true);
    }
    
    public static Vector[] points(double time, Curve curve) {
        double theta = Math.sqrt(2.0 / curve.k * (curve.L(INIT_THETA) - time * HEAD_VELOCITY));
        Vector[] res = new Vector[NUM_BENCH + 1];
        for (int i = 0; i <= NUM_BENCH; i++) {
            res[i] = curve.p(theta);
            theta = nextTheta(theta, (i == 0 ? HEAD_LENGTH : COMMON_LENGTH) - 2.0 * EXTENT_LENGTH, curve);
        }
        return res;
    }
    
    public static double nextTheta(double theta, double chord, Curve curve) {
        double x0 = curve.x(theta);
        double y0 = curve.y(theta);
        double angle = curve.tan(theta);
        
        double deltaTheta = DPI / 720.0; // 步长0.5度
        double currentTheta = theta + deltaTheta;
        while (curve.chord(theta, currentTheta) < chord) {
            currentTheta += deltaTheta;
        }
        
        double left = theta;
        double right = currentTheta;
        for (; ; ) {
            double middle = 0.5 * (left + right);
            double chord1 = curve.chord(theta, middle);
            if (equal(chord, chord1)) {
                return middle;
            }
            if (chord < chord1) {
                right = middle;
            } else {
                left = middle;
            }
        }
    }
    
    public static boolean equal(double a, double b) {
        return Math.abs(a - b) < EPS;
    }
}
