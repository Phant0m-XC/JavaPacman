package pacman;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Pacman extends Thread {

    private int x;
    private int y;
    private int size;
    private int startAngle;
    private int arcAngle;
    private boolean openClose;
    private DrawPanel panel;
    private Direction direction;
    private Direction nextDirection;
    private boolean changeDirection;
    private Mapper mapper;
    private int score;

    public Pacman(DrawPanel panel, Mapper mapper) {
        this.x = 600;
        this.y = 500;
        this.size = 50;
        this.startAngle = 50;
        this.arcAngle = 300;
        this.openClose = false;
        this.panel = panel;
        this.direction = Direction.STOP;
        this.mapper = mapper;
        this.score = 0;
    }

    public void draw(Graphics gr) {
        gr.setColor(Color.YELLOW);
        gr.fillArc(x, y, size, size, startAngle, arcAngle);
        if (gr instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D) gr;
            g2.setColor(Color.GREEN);
            g2.drawString("Очки: " + score, 50, 50);
        }
    }

    private void wakawaka(Direction direction) {
        if (direction != Direction.STOP) {
            if (openClose)
                arcAngle -= 10;
            else
                arcAngle += 10;
            if (arcAngle > 360)
                openClose = true;
            else if (arcAngle < 300)
                openClose = false;
            switch (direction) {
                case RIGHT:
                    startAngle = (360 - arcAngle) / 2;
                    break;
                case LEFT:
                    startAngle = (0 - arcAngle) / 2;
                    break;
                case UP:
                    startAngle = (540 - arcAngle) / 2;
                    break;
                case DOWN:
                    startAngle = (180 - arcAngle) / 2;
                    break;
            }
            checkPoints();
        }
    }

    public void setDirection(int dir) {
        switch (dir) {
            case KeyEvent.VK_LEFT:
                this.nextDirection = Direction.LEFT;
                changeDirection = true;
                break;
            case KeyEvent.VK_RIGHT:
                this.nextDirection = Direction.RIGHT;
                changeDirection = true;
                break;
            case KeyEvent.VK_UP:
                this.nextDirection = Direction.UP;
                changeDirection = true;
                break;
            case KeyEvent.VK_DOWN:
                this.nextDirection = Direction.DOWN;
                changeDirection = true;
                break;
        }
    }

    private void go() {
        if (changeDirection && x % size == 0 && y % size == 0) {
            direction = nextDirection;
            changeDirection = false;
        }
        if (checkBounds())
            switch (this.direction) {
                case RIGHT:
                    goRight();
                    break;
                case LEFT:
                    goLeft();
                    break;
                case UP:
                    goUp();
                    break;
                case DOWN:
                    goDown();
                    break;
            }
        wakawaka(direction);
    }

    private void goLeft() {
        if (x > -size)
            x -= 5;
        else
            x = 1250;
    }

    private void goRight() {
        if (x < 1250)
            x += 5;
        else
            x = -size;
    }

    private void goUp() {
        if (y > -size)
            y -= 5;
        else
            y = 900;
    }

    private void goDown() {
        if (y < 900)
            y += 5;
        else
            y = -size;
    }

    private boolean checkBounds() {
        if (x % (size / 2) == 0 && y % (size / 2) == 0) {
            ArrayList<Line> lines = (ArrayList<Line>) mapper.getLines();
            int x0, y0;
            for (int i = 0; i < lines.size(); i++) {
                x0 = lines.get(i).getX();
                y0 = lines.get(i).getY();
                if ((direction == Direction.LEFT || direction == Direction.STOP) && nextDirection == Direction.LEFT && y0 == y && (x0 + size) == x) {
                    direction = Direction.STOP;
                    return false;
                }
                if ((direction == Direction.UP || direction == Direction.STOP) && nextDirection == Direction.UP && x0 == x && (y0 + size) == y) {
                    direction = Direction.STOP;
                    return false;
                }
                if ((direction == Direction.RIGHT || direction == Direction.STOP) && nextDirection == Direction.RIGHT && y0 == y && x0 == (x + size)) {
                    direction = Direction.STOP;
                    return false;
                }
                if ((direction == Direction.DOWN || direction == Direction.STOP) && nextDirection == Direction.DOWN && x0 == x && y0 == (y + size)) {
                    direction = Direction.STOP;
                    return false;
                }
            }
        }
        return true;
    }

    private void checkPoints() {
        if (x % (size / 2) == 0 && y % (size / 2) == 0) {
            ArrayList<Point> points = (ArrayList<Point>) mapper.getPoints();
            int x0, y0;
            for (int i = 0; i < points.size(); i++) {
                x0 = points.get(i).getX();
                y0 = points.get(i).getY();
                if (x + size / 2 == x0 && y + size / 2 == y0) {
                    mapper.getPoints().remove(i);
                    score += 10;
                    break;
                }
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            go();
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            panel.repaint();
        }
    }
}
