package solve;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Draw {
    
    public static final int IMG_WIDTH = 2560;
    public static final double IMG_EXTENT = 20.4;
    
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
    
    public static void show(BufferedImage img) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(960, 960));
        panel.setUI(new PanelUI() {
            
            @Override public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHints(RENDERING_HINTS);
                g2d.drawImage(img, 0, 0, c.getWidth(), c.getHeight(), c);
            }
        });
        
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
    }
    
    public static BufferedImage drawLines(Vector[] points, Curve curve) {
        BufferedImage res = new BufferedImage(IMG_WIDTH, IMG_WIDTH, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = createGraphics(res);
        
        g.setColor(new Color(150, 210, 243));
        g.setStroke(new BasicStroke(12.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        for (int i = 0; i < 1000; i++) {
            double t1 = i * Utils.INIT_THETA / 1000;
            double t2 = (i + 1) * Utils.INIT_THETA / 1000;
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
        for (Vector p : points) {
            int r = 6;
            g.fillOval(map(p.x) - r, map(p.y) - r, r * 2, r * 2);
        }
        
        g.dispose();
        
        return res;
    }
    
    private static Graphics2D createGraphics(BufferedImage img) {
        Graphics2D g = img.createGraphics();
        g.setRenderingHints(RENDERING_HINTS);
        g.drawImage(GRID_IMG, 0, 0, IMG_WIDTH, IMG_WIDTH, null);
        g.translate(0, IMG_WIDTH);
        g.scale(1.0, -1.0);
        return g;
    }
    
    private static int map(double x) {
        return (int) Math.round((x / IMG_EXTENT + 0.5) * IMG_WIDTH);
    }
}
