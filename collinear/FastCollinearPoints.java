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
import java.util.Comparator;

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
            Arrays.sort(t, pointArray[i].slopeOrder());

            // find similar slope side by side
            Point[] a = new Point[4];
            a[0] = t[0];
            int cnt = 1;
            for (int j = 0; j < size; j++) {
                if (cnt == 1) {
                    a[cnt++] = t[j];
                }
                else {
                    if (a[0].slopeTo(a[cnt - 1]) == a[0].slopeTo(t[j])) {
                        if (cnt >= a.length) {
                            // System.out.println("before resize length" + a.length + " cnt=" + cnt);
                            a = Arrays.copyOf(a, a.length + 1);
                            // System.out.println("resize a " + a.length);
                        }
                        a[cnt++] = t[j];
                    }
                    else {
                        if (cnt >= 4) {
                            // if similar stored exceed 3 or more(except itself), take it to lineSegment; reset the cnt flag;
                            // System.out.println("a length " + a.length);
                            // System.out.println("find 4 or more similar");
                            Arrays.sort(a, FastCollinearPoints.valueOrder());
                            LineSegment ls = new LineSegment(a[0], a[cnt - 1]);
                            if (!isAlready(ls)) {
                                lineSegments.add(ls);
                            }
                            cnt = 1;
                            clear(a);
                        }
                        else {
                            // if not reach 3 or more, reset this array;
                            cnt = 1;
                            clear(a);
                            a[cnt++] = t[j];
                        }
                    }
                }
            }
            // in case the last 3 point in one segment
            if (cnt >= 4) {
                // if similar stored exceed 3 or more(except itself), take it to lineSegment; reset the cnt flag;
                // System.out.println("a length at last" + a.length + " cnt =" + cnt);
                // System.out.println("find 4 or more similar at last");
                Arrays.sort(a, FastCollinearPoints.valueOrder());
                LineSegment ls = new LineSegment(a[0], a[cnt - 1]);
                if (!isAlready(ls)) {
                    lineSegments.add(ls);
                }
            }
        }

    }

    private static Comparator<Point> valueOrder() {
        Comparator<Point> c = new CustomOrder2();
        return c;
    }

    private static class CustomOrder2 implements Comparator<Point> {
        @Override
        public int compare(Point p1, Point p2) {
            if (p1 == null && p2 == null) return 0;
            if (p1 == null) return 1;
            if (p2 == null) return -1;
            return p1.compareTo(p2);
        }
    }

    private void clear(Point[] a) {
        for (int i = 1; i < a.length; i++) {
            a[i] = null;
        }
    }

    private boolean isAlready(LineSegment ls) {
        for (LineSegment segment : lineSegments) {
            if (segment.toString().equals(ls.toString()))
                return true;
        }
        return false;
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
