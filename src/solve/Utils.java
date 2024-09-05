package solve;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import static solve.Solve.*;

public class Utils {
    
    public static OBB toOBB(Vector p1, Vector p2, boolean isHead) {
        return new OBB(p1.midpoint(p2), isHead ? HEAD_LENGTH : COMMON_LENGTH, BENCH_WIDTH, p1.angle(p2));
    }
    
    public static boolean collide(OBB obb1, OBB obb2) {
        if (detached(obb1, obb2, new Vector(Math.cos(obb1.angle), Math.sin(obb1.angle)))) {
            return false;
        }
        if (detached(obb1, obb2, new Vector(-Math.sin(obb1.angle), Math.cos(obb1.angle)))) {
            return false;
        }
        if (detached(obb1, obb2, new Vector(Math.cos(obb2.angle), Math.sin(obb2.angle)))) {
            return false;
        }
        if (detached(obb1, obb2, new Vector(-Math.sin(obb2.angle), Math.cos(obb2.angle)))) {
            return false;
        }
        return true;
    }
    
    private static boolean detached(OBB obb1, OBB obb2, Vector axis) {
        Range range1 = projection(obb1, axis);
        Range range2 = projection(obb2, axis);
        return range1.min > range2.max || range2.min > range1.max;
    }
    
    private static Range projection(OBB obb, Vector axis) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (Vector point : obb.points) {
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
    
    public static final int IMG_WIDTH = 2560;
    public static final double IMG_EXTENT = 20.4;
    public static final BufferedImage GRID_IMG;
    public static final Map<?, ?> renderingHints = Map.of(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON,
            RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE,
            RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY,
            RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY,
            RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC
    );
    
    static {
        try {
            GRID_IMG = ImageIO.read(new File("grid.png"));
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    
    public static BufferedImage draw(Vector[] points, Curve curve) {
        BufferedImage res = new BufferedImage(IMG_WIDTH, IMG_WIDTH, BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g = res.createGraphics();
        g.setRenderingHints(renderingHints);
        
        g.drawImage(GRID_IMG, 0, 0, IMG_WIDTH, IMG_WIDTH, null);
        
        g.translate(0, IMG_WIDTH);
        g.scale(1.0, -1.0);
        
//        g.setColor(new Color(243, 243, 243));
//        g.fillRect(0, 0, IMG_WIDTH, IMG_WIDTH);
        
        g.setColor(new Color(150, 210, 243));
        g.setStroke(new BasicStroke(12.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (int i = 0; i < 1000; i++) {
            double t1 = i * INIT_THETA / 1000;
            double t2 = (i + 1) * INIT_THETA / 1000;
            g.drawLine(map(curve.x(t1)), map(curve.y(t1)), map(curve.x(t2)), map(curve.y(t2)));
        }
        
        g.setColor(new Color(250, 46, 46));
        g.setStroke(new BasicStroke(8.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (int i = 0; i + 1 < points.length; i++) {
            Vector p1 = points[i];
            Vector p2 = points[i + 1];
            g.drawLine(map(p1.x), map(p1.y), map(p2.x), map(p2.y));
        }
        
        g.setColor(new Color(0, 0, 0));
        for (int i = 0; i < points.length; i++) {
            int r = 6;
            Vector p = points[i];
            g.fillOval(map(p.x) - r, map(p.y) - r, r * 2, r * 2);
        }
        
        g.dispose();
        
        return res;
    }
    
    public static int map(double x) {
        return (int) Math.round((x / IMG_EXTENT + 0.5) * IMG_WIDTH);
    }
}
