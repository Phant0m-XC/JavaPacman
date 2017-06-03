package pacman;

import javax.swing.*;
import java.awt.*;

public class Line {

    JPanel panel;
    private int x;
    private int y;
    private int size;

    public Line(JPanel panel, int x, int y) {
        this.panel = panel;
        this.x = x;
        this.y = y;
        size = 50;
    }

    public void draw(Graphics gr) {
        gr.setColor(Color.BLUE);
        gr.fillRect(x, y, size, size);
        panel.repaint();
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}

