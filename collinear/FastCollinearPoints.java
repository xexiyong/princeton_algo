/* *****************************************************************************
 *  Name: Sunzg
 *  Date: 2018-11-25
 *  Description: Fast colinear points
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private Point[] pointArray;
    private ArrayList<LineSegment> lineSegments = new ArrayList<LineSegment>();

    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
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

        for (int i = 0; i < size; i++) {
            Point[] t = new Point[size];
            for (int j = 0; j < size; j++) {
                t[j] = pointArray[j];
            }
            Arrays.sort(t, t[i].slopeOrder());
            // find slope==0
            Point[] a = new Point[4];
            int cnt = 0;
            for (int j = 0; j < size; j++) {
                if (pointArray[i].slopeTo(t[j]) == +0.0) {
                    if (cnt >= a.length) {
                        a = Arrays.copyOf(a, a.length + 1);
                    }
                    a[cnt++] = t[j];
                }
            }
            System.out.println(cnt);
            if (cnt >= 3) {
                Arrays.sort(a, Point.valueOrder());
                lineSegments.add(new LineSegment(a[0], a[a.length - 1]));
                // System.out.println(a[0]);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}
