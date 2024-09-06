package solve;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

import static solve.Utils.*;

public class Active {
    
    public static void main(String[] args) {
        Display display = new Display();
        display.createWindow();
        
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JSlider dSlider = new JSlider(JSlider.HORIZONTAL, 0, 100000, 50000);
        JSlider rSlider = new JSlider(JSlider.HORIZONTAL, 0, 100000, 50000);
        JSlider tSlider = new JSlider(JSlider.HORIZONTAL, -1000000, 1000000, 0);
        
        ChangeListener cl = e -> {
            double D = dSlider.getValue() * 0.0001;
            double R = rSlider.getValue() * 0.0001;
            double T = tSlider.getValue() * 0.0001;
            
            Curve curve = new Curve(D, R);
            double dT = (curve.thetaToLen(INIT_THETA) - curve.thetaToLen(curve.ThetaC)) / HEAD_VELOCITY;
            Vector[] points = points(T + dT, curve);
            double distance = distance(points);
            
            System.out.printf("D = %.04f  R = %.04f  T = %.04f  Dis = %.06f\n", D, R, T, distance);
            display.show(curve, points);
        };
        dSlider.addChangeListener(cl);
        rSlider.addChangeListener(cl);
        tSlider.addChangeListener(cl);
        
        panel.setLayout(new GridLayout(3, 1));
        panel.add(dSlider);
        panel.add(rSlider);
        panel.add(tSlider);
        
        frame.setContentPane(panel);
        frame.setSize(600, 160);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
