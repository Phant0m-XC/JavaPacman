package pacman;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class DrawPanel extends JPanel {

    private Pacman pacman;
    private Mapper mapper;

    public DrawPanel() {
        this.setFocusable(true);
        this.requestFocus();
        this.addKeyListener(new GameController());
        try {
            mapper = new Mapper(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        pacman = new Pacman(this, mapper);
        new Thread(pacman).start();
    }

    @Override
    protected void paintComponent(Graphics gr) {
        gr.fillRect(0, 0, 1250, 900);
        for (int i = 0; i < mapper.getPoints().size(); i++) {
            Point p = mapper.getPoints().get(i);
            p.draw(gr);
        }
        for (int i = 0; i < mapper.getLines().size(); i++) {
            Line l = mapper.getLines().get(i);
            l.draw(gr);
        }
        pacman.draw(gr);
    }

    class GameController implements KeyListener {

        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            pacman.setDirection(e.getKeyCode());
        }

        public void keyReleased(KeyEvent e) {
        }
    }
}
