package pacman;

import javax.swing.*;
import java.awt.*;

public class Point extends Thread {
    private int x;
    private int y;
    private int size;
    private JPanel panel;

    public Point(JPanel panel, int x, int y) {
        this.panel = panel;
        this.x = x;
        this.y = y;
        size = 10;
    }

    public void draw(Graphics gr) {
        gr.setColor(Color.WHITE);
        gr.fillArc(x, y, size, size, 0, 360);
        panel.repaint();
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
