package solve;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.PanelUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Solve1 {
    
    public static void main(String[] args) throws Exception {
        Curve curve = new Curve(0.55);
        Vector[] points = Utils.points(300, curve);
        BufferedImage img = Draw.drawLines(points, curve);
        ImageIO.write(img, "png", new File("img.png"));
        
        JFrame frame = new JFrame();
        frame.setSize(1080, 1080);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        panel.setUI(new PanelUI() {
            
            @Override public void paint(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHints(Draw.renderingHints);
                g2d.drawImage(img, 0, 0, c.getWidth(), c.getHeight(), c);
            }
        });
        
        frame.setContentPane(panel);
        frame.setVisible(true);
    }
}
