/* *****************************************************************************
 *  Name: Sunzg
 *  Date: 2018-11-25
 *  Description: Colinear app
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {

    private Point p;
    private Point q;
    private Point r;
    private Point s;
    private Point[] pointArray;
    private LineSegment[] lineSegments;
    private int number = 0;

    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        if (points == null) {
            throw new IllegalArgumentException("argument is null");
        }
        p = points[0];
        q = points[1];
        r = points[2];
        s = points[3];
        if (p == null || q == null || r == null || s == null) {
            throw new IllegalArgumentException("argument is null");
        }
        if (p.compareTo(q) == 0 ||
                p.compareTo(r) == 0 ||
                p.compareTo(s) == 0 ||
                q.compareTo(r) == 0 ||
                q.compareTo(s) == 0 ||
                r.compareTo(s) == 0) {
            throw new IllegalArgumentException("argument is dupliacates");
        }
        pointArray = new Point[4];
        pointArray[0] = p;
        pointArray[1] = q;
        pointArray[2] = r;
        pointArray[3] = s;

        Arrays.sort(pointArray, p.slopeOrder());
        double slope1 = pointArray[0].slopeTo(pointArray[1]);
        double slope2 = pointArray[1].slopeTo(pointArray[2]);
        double slope3 = pointArray[2].slopeTo(pointArray[3]);
        if (slope1 == slope2 && slope2 == slope3) {
            number = 1;
            lineSegments = new LineSegment[1];
            lineSegments[0] = new LineSegment(pointArray[0], pointArray[3]);
            return;
        }
        if (slope1 == slope2) {
            number = 2;
            lineSegments = new LineSegment[2];
            lineSegments[0] = new LineSegment(pointArray[0], pointArray[2]);
            lineSegments[1] = new LineSegment(pointArray[2], pointArray[3]);
            return;
        }
        if (slope2 == slope3) {
            number = 2;
            lineSegments = new LineSegment[2];
            lineSegments[0] = new LineSegment(pointArray[0], pointArray[1]);
            lineSegments[1] = new LineSegment(pointArray[1], pointArray[3]);
            return;
        }

        number = 3;
        lineSegments = new LineSegment[3];
        lineSegments[0] = new LineSegment(pointArray[0], pointArray[1]);
        lineSegments[1] = new LineSegment(pointArray[1], pointArray[2]);
        lineSegments[2] = new LineSegment(pointArray[2], pointArray[3]);
        return;
    }


    public int numberOfSegments() {
        // the number of line segments
        return number;
    }

    public LineSegment[] segments() {
        // the line segments
        return lineSegments;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
