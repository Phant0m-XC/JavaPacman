package pacman;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Mapper {

    private List<Point> points;
    private List<Line> lines;

    public Mapper(DrawPanel panel) throws IOException {
        points = new ArrayList<Point>();
        lines = new ArrayList<Line>();
        File file = new File("K:\\MyProjects\\Java\\41\\pacman\\src\\main\\resources\\map.txt");
        FileReader mapFileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(mapFileReader);
        String line;
        int numOfLine = 0;
        while ((line = reader.readLine()) != null) {
            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '*')
                    points.add(new Point(panel, i * 50 + 25, numOfLine * 50 + 25));
                else if (line.charAt(i) == '!')
                    lines.add(new Line(panel, i * 50, numOfLine * 50));
            }
            numOfLine++;
        }
    }

    public List<Point> getPoints() {
        return points;
    }

    public List<Line> getLines() {
        return lines;
    }
}
