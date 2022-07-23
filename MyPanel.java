import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {

    final int PANEL_WIDTH = 600;
    final int PANEL_HEIGHT = 600;
    
    QuadTree quadtree;
    Point[] points;
    
    MyPanel() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH + 1, PANEL_HEIGHT + 1));
        this.setBackground(Color.DARK_GRAY);

        quadtree = new QuadTree(0, 0, PANEL_WIDTH, PANEL_HEIGHT, 4);

        points = new Point[300];

        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(Math.random() * PANEL_WIDTH, Math.random() * PANEL_HEIGHT, points[i]);
        }

        for(Point point : points) {
            quadtree.insert(point);
        }
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paint(g);

        this.draw_quadtree(g2d, this.quadtree);

        g2d.setColor(Color.GREEN);
        for (Point point : points) {
            g2d.fillOval((int) point.x - 2, (int) point.y - 2, 4, 4);
        }

        g2d.setColor(Color.BLUE);
        g2d.drawRect(100, 100, 100, 100);
    }

    void draw_quadtree(Graphics2D g2d, QuadTree quadtree) {
        g2d.setColor(Color.WHITE);
        g2d.drawRect((int) quadtree.boundary.x, (int) quadtree.boundary.y, (int) quadtree.boundary.width, (int) quadtree.boundary.height);
        if (quadtree.devided) {
            this.draw_quadtree(g2d, quadtree.north_east);
            this.draw_quadtree(g2d, quadtree.north_west);
            this.draw_quadtree(g2d, quadtree.south_east);
            this.draw_quadtree(g2d, quadtree.south_west);
        }
    }
}