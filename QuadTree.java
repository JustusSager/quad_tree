import java.awt.*;

public class QuadTree {
    int x, y, width, height, max_points, points_length;
    boolean devided;
    Point[] points;
    QuadTree north_west, north_east, south_west, south_east;

    QuadTree(int x, int y, int width, int height, int max_points) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.max_points = max_points;
        this.devided = false;
        this.points = new Point[max_points];
        this.points_length = 0;
    }

    void insert(Point point) {
        if (this.intersects(point) && point != null) {
            if (this.points_length < this.max_points) {
                points[points_length] = point;
                points_length++;
            } else {
                if (!this.devided) {
                    this.devide();
                    this.devided = true;
                }
                this.north_west.insert(point);
                this.north_east.insert(point);
                this.south_west.insert(point);
                this.south_east.insert(point);
            }
        }
    }
    void insert(double x, double y, Object user_data) {
        Point point = new Point(x, y, user_data);
        this.insert(point);
    }
    
    void devide() {
        this.north_west = new QuadTree(this.x, this.y, this.width / 2, this.height / 2, this.max_points);
        this.north_east = new QuadTree(this.x + this.width / 2, this.y, this.width / 2, this.height / 2, this.max_points);
        this.south_west = new QuadTree(this.x, this.y + this.height / 2, this.width / 2, this.height / 2, this.max_points);
        this.south_east = new QuadTree(this.x + this.width / 2, this.y + this.height / 2, this.width / 2, this.height / 2, this.max_points);

        for (int i = 0; i < max_points; i++) {
            if (this.points[i] != null) {
                this.north_west.insert(this.points[i]);
                this.north_east.insert(this.points[i]);
                this.south_west.insert(this.points[i]);
                this.south_east.insert(this.points[i]);
            }
        }
    }

    boolean intersects(Point point) {
        return (
            point.x <= this.x + this.width &&
            point.x >= this.x &&
            point.y <= this.y + this.height &&
            point.y >= this.y
        );
    }
    boolean intersects(int x, int y, int width, int height) {
        return !(
            x > this.x + this.width ||
            x + width < this.x ||
            y > this.y + this.height ||
            y + height < this.y
        );
    }

    void draw(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.drawRect(this.x, this.y, this.width, this.height);
        if (devided) {
            this.north_west.draw(g2d);
            this.north_east.draw(g2d);
            this.south_west.draw(g2d);
            this.south_east.draw(g2d);
        }
    }
}

class Point {
    double x, y;
    Object user_data;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
        this.user_data = null;
    }

    Point(double x, double y, Object user_data) {
        this.x = x;
        this.y = y;
        this.user_data = user_data;
    }
}
