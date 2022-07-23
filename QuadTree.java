import java.awt.*;

public class QuadTree {
    Rectangle body;
    int max_points, points_length;
    boolean devided;
    Point[] points;
    QuadTree north_west, north_east, south_west, south_east;

    QuadTree(double x, double y, double width, double height, int max_points) {
        body = new Rectangle(x, y, width, height);
        this.max_points = max_points;
        this.devided = false;
        this.points = new Point[max_points];
        this.points_length = 0;
    }

    void insert(Point point) {
        if (this.body.intersects(point) && point != null) {
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
        this.north_west = new QuadTree(this.body.x, this.body.y, this.body.width / 2, this.body.height / 2, this.max_points);
        this.north_east = new QuadTree(this.body.x + this.body.width / 2, this.body.y, this.body.width / 2, this.body.height / 2, this.max_points);
        this.south_west = new QuadTree(this.body.x, this.body.y + this.body.height / 2, this.body.width / 2, this.body.height / 2, this.max_points);
        this.south_east = new QuadTree(this.body.x + this.body.width / 2, this.body.y + this.body.height / 2, this.body.width / 2, this.body.height / 2, this.max_points);

        for (int i = 0; i < max_points; i++) {
            if (this.points[i] != null) {
                this.north_west.insert(this.points[i]);
                this.north_east.insert(this.points[i]);
                this.south_west.insert(this.points[i]);
                this.south_east.insert(this.points[i]);
            }
        }
    }

    void draw(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.drawRect((int) this.body.x, (int) this.body.y, (int) this.body.width, (int) this.body.height);
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

class Rectangle {
    double x, y, width, height;

    Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    boolean intersects(Point point) {
        return (
            point.x <= this.x + this.width &&
            point.x >= this.x &&
            point.y <= this.y + this.height &&
            point.y >= this.y
        );
    }
    boolean intersects(Rectangle rect) {
        return !(
            rect.x >= this.x + this.width ||
            rect.x + rect.width <= this.x ||
            rect.y >= this.y + this.height ||
            rect.y + rect.height <= this.y
        );
    }
}
