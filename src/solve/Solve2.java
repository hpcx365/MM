package solve;

import java.io.FileWriter;
import java.io.PrintWriter;

import static solve.Utils.check;
import static solve.Utils.points;

public class Solve2 {
    
    public static void main(String[] args) throws Exception {
        Curve curve = new Curve(0.55);
        double deltaTime = 0.00002;
        long step = 0;
        
        PrintWriter writer = new PrintWriter(new FileWriter("collide.txt"));
        for (; ; step++) {
            double time = step * deltaTime;
            if (curve.toTheta(time) < 0.0) {
                break;
            }
            boolean check = check(points(time, curve));
            System.out.printf("%.06f %b\n", time, check);
            if (!check) {
                writer.println(time);
                break;
            }
        }
    }
}
