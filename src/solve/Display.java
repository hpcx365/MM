package solve;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import static solve.Utils.*;

public class Display {
    
    public static final int INIT_WIDTH = 720;
    
    private Curve curve;
    private Vector[] points;
    
    private double pixelsPerMeter = 30.0;
    private double positionX = 0.0;
    private double positionY = 0.0;
    
    private JFrame frame;
    
    public void show(Curve curve, Vector[] points) {
        this.curve = curve;
        this.points = points;
        createWindow();
        frame.getContentPane().repaint();
    }
    
    public void createWindow() {
        if (frame != null) {
            return;
        }
        
        MouseAdapter mouse = new MouseAdapter() {
            
            Point pre;
            
            @Override
            public void mouseMoved(MouseEvent e) {
                pre = e.getPoint();
            }
            
            @Override
            public void mouseDragged(MouseEvent e) {
                Point cur = e.getPoint();
                JComponent c = (JComponent) e.getSource();
                
                positionX -= (cur.x - pre.x) / pixelsPerMeter;
                positionY += (cur.y - pre.y) / pixelsPerMeter;
                
                pre = cur;
                c.repaint();
            }
            
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                JComponent c = (JComponent) e.getSource();
                
                pixelsPerMeter *= Math.pow(0.9, e.getPreciseWheelRotation());
                
                c.repaint();
            }
        };
        
        PanelUI ui = new PanelUI() {
            
            @Override
            public void paint(Graphics g, JComponent c) {
                if (curve == null || points == null) {
                    return;
                }
                drawLines(g, curve, points, c.getWidth(), c.getHeight());
            }
        };
        
        JPanel panel = new JPanel();
        panel.setUI(ui);
        panel.addMouseListener(mouse);
        panel.addMouseWheelListener(mouse);
        panel.addMouseMotionListener(mouse);
        panel.setPreferredSize(new Dimension(INIT_WIDTH, INIT_WIDTH));
        
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            
            @Override public void windowClosing(WindowEvent e) {
                frame.dispose();
                frame = null;
            }
        });
        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public void drawLines(Graphics g, Curve curve, Vector[] points, int w, int h) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHints(RENDERING_HINTS);
        
        g2d.setColor(new Color(193, 218, 239));
        g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        curve(g2d, curve, 0, 0, w, h);
        
        g2d.setColor(new Color(157, 144, 144));
        g2d.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        plot(g2d, points, 0, 0, w, h);
        
        g2d.setStroke(new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        rectangle(g2d, points, 0, 0, w, h);
        
        g.setColor(new Color(0, 0, 0));
        scatter(g2d, points, 4, 0, 0, w, h);
    }
    
    public void curve(Graphics2D g, Curve curve, int x, int y, int w, int h) {
        for (long n = 2000, i = -n; i < n; i++) {
            double t1 = i * INIT_THETA / n;
            double t2 = (i + 1) * INIT_THETA / n;
            Vector p = curve.pointAt(t1);
            Vector q = curve.pointAt(t2);
            g.drawLine(toX(p.x(), x, w), toY(p.y(), y, h), toX(q.x(), x, w), toY(q.y(), y, h));
        }
    }
    
    public void plot(Graphics2D g, Vector[] points, int x, int y, int w, int h) {
        for (int i = 0; i + 1 < points.length; i++) {
            Vector p = points[i];
            Vector q = points[i + 1];
            g.drawLine(toX(p.x(), x, w), toY(p.y(), y, h), toX(q.x(), x, w), toY(q.y(), y, h));
        }
    }
    
    public void scatter(Graphics2D g, Vector[] points, int r, int x, int y, int w, int h) {
        for (Vector p : points) {
            g.fillOval(toX(p.x(), x, w) - r, toY(p.y(), y, h) - r, 2 * r, 2 * r);
        }
    }
    
    public void rectangle(Graphics2D g, Vector[] points, int x, int y, int w, int h) {
        boolean[] collided = collided(points);
        Color red = new Color(250, 46, 46);
        Color green = new Color(12, 175, 12);
        for (int i = 1; i < points.length; i++) {
            Vector p = points[i - 1];
            Vector q = points[i];
            double slop = p.slop(q);
            Vector a = p.add(new Vector(-EXTENT_LENGTH, 0.5 * BENCH_WIDTH).rotate(slop));
            Vector b = p.add(new Vector(-EXTENT_LENGTH, -0.5 * BENCH_WIDTH).rotate(slop));
            Vector c = q.add(new Vector(EXTENT_LENGTH, -0.5 * BENCH_WIDTH).rotate(slop));
            Vector d = q.add(new Vector(EXTENT_LENGTH, 0.5 * BENCH_WIDTH).rotate(slop));
            
            g.setColor(collided[i - 1] ? red : green);
            g.drawLine(toX(a.x(), x, w), toY(a.y(), y, h), toX(b.x(), x, w), toY(b.y(), y, h));
            g.drawLine(toX(b.x(), x, w), toY(b.y(), y, h), toX(c.x(), x, w), toY(c.y(), y, h));
            g.drawLine(toX(c.x(), x, w), toY(c.y(), y, h), toX(d.x(), x, w), toY(d.y(), y, h));
            g.drawLine(toX(d.x(), x, w), toY(d.y(), y, h), toX(a.x(), x, w), toY(a.y(), y, h));
        }
    }
    
    public int toX(double x0, int x, int w) {
        return (int) Math.round(x + 0.5 * w + (x0 - positionX) * pixelsPerMeter);
    }
    
    public int toY(double y0, int y, int h) {
        return (int) Math.round(y + 0.5 * h - (y0 - positionY) * pixelsPerMeter);
    }
    
    public static final BufferedImage GRID_IMG;
    public static final Map<?, ?> RENDERING_HINTS = Map.of(
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
}
