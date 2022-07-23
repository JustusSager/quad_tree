public class QuadTree {
    Rectangle boundary;
    int max_points, points_length;
    boolean devided;
    Point[] points;
    QuadTree north_west, north_east, south_west, south_east;

    QuadTree(double x, double y, double width, double height, int max_points) {
        boundary = new Rectangle(x, y, width, height);
        this.max_points = max_points;
        this.devided = false;
        this.points = new Point[max_points];
        this.points_length = 0;
    }

    void insert(Point point) {
        /* Punkt in den QuadTree eingefügen */
        if (this.boundary.intersects(point) && point != null) {
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
        /* Koordinaten zu Punkt umwandeln und in den QuadTree eingefügen */
        Point point = new Point(x, y, user_data);
        this.insert(point);
    }
    
    void devide() {
        /* QuadTree in vier untergeordnete QuadTrees aufteilen und die Punkte passend in sie einfügen */
        this.north_west = new QuadTree(this.boundary.x, this.boundary.y, this.boundary.width / 2, this.boundary.height / 2, this.max_points);
        this.north_east = new QuadTree(this.boundary.x + this.boundary.width / 2, this.boundary.y, this.boundary.width / 2, this.boundary.height / 2, this.max_points);
        this.south_west = new QuadTree(this.boundary.x, this.boundary.y + this.boundary.height / 2, this.boundary.width / 2, this.boundary.height / 2, this.max_points);
        this.south_east = new QuadTree(this.boundary.x + this.boundary.width / 2, this.boundary.y + this.boundary.height / 2, this.boundary.width / 2, this.boundary.height / 2, this.max_points);

        for (int i = 0; i < max_points; i++) {
            if (this.points[i] != null) {
                this.north_west.insert(this.points[i]);
                this.north_east.insert(this.points[i]);
                this.south_west.insert(this.points[i]);
                this.south_east.insert(this.points[i]);
            }
        }
    }
}

class Point {
    /* Verwendete Punktklasse */
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
    /* Verwendete Recheckklasse */
    double x, y, width, height;

    Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    boolean intersects(Point point) {
        /* Kontrolle ob ein Punkt innerhalb des Rechtecks liegt */
        return (
            point.x <= this.x + this.width &&
            point.x >= this.x &&
            point.y <= this.y + this.height &&
            point.y >= this.y
        );
    }
    boolean intersects(Rectangle rect) {
        /* Kontrolle sich ein Rechteck mit diesem Rechteck schneidet */
        return !(
            rect.x >= this.x + this.width ||
            rect.x + rect.width <= this.x ||
            rect.y >= this.y + this.height ||
            rect.y + rect.height <= this.y
        );
    }
}
