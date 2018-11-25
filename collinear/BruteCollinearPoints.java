/* *****************************************************************************
 *  Name: Sunzg
 *  Date: 2018-11-25
 *  Description: Colinear app
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private Point[] pointArray;
    private ArrayList<LineSegment> lineSegments = new ArrayList<LineSegment>();
    // private int number = 0;

    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        if (points == null) {
            throw new IllegalArgumentException("argument is null");
        }
        int size = points.length;
        pointArray = new Point[size];
        for (int i = 0; i < size; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("argument is null");
            }
            for (int j = 0; j < i; j++) {
                if (pointArray[j].compareTo(points[i]) == 0)
                    throw new IllegalArgumentException("argument is dupliacates");
            }
            pointArray[i] = points[i];
        }

        for (int i = 0; i < size; i++)
            for (int j = i + 1; j < size; j++)
                for (int k = j + 1; k < size; k++)
                    for (int m = k + 1; m < size; m++) {
                        double slope1 = pointArray[i].slopeTo(pointArray[j]);
                        double slope2 = pointArray[j].slopeTo(pointArray[k]);
                        double slope3 = pointArray[k].slopeTo(pointArray[m]);
                        if (slope1 == slope2 && slope3 == slope2) {
                            Point[] a = new Point[4];
                            a[0] = pointArray[i];
                            a[1] = pointArray[j];
                            a[2] = pointArray[k];
                            a[3] = pointArray[m];
                            Arrays.sort(a, Point.valueOrder());
                            lineSegments.add(new LineSegment(a[0], a[3]));
                            // lineSegments.add(new LineSegment(pointArray[i], pointArray[m]));
                        }
                    }
    }


    public int numberOfSegments() {
        // the number of line segments
        return lineSegments.size();
    }

    public LineSegment[] segments() {
        // the line segments
        LineSegment[] integer1 = new LineSegment[lineSegments.size()];
        lineSegments.toArray(integer1);
        return integer1;
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
