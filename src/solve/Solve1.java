package solve;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import static solve.Draw.show;

public class Solve1 {
    
    public static void main(String[] args) throws Exception {
        Curve curve = new Curve(0.55);
        Vector[] points = Utils.points(300, curve);
        BufferedImage img = Draw.drawLines(points, curve);
        ImageIO.write(img, "png", new File("img.png"));
        
        show(img);
    }
}
