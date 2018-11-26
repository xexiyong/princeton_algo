/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;
import java.util.Comparator;

public class test {

    public static Comparator<Point> valueOrder() {
        Comparator<Point> c = new CustomOrder2();
        return c;
    }

    private static class CustomOrder2 implements Comparator<Point> {
        @Override
        public int compare(Point p1, Point p2) {
            if (p1 == null && p2 == null) return 0;
            if (p1 == null) return 1;
            if (p2 == null) return -1;

            return test.;
        }
    }

    public static void main(String[] args) {
        Point[] a = new Point[5];
        a[0] = 10;
        a[1] = 23;
        a[2] = 1;
        a[3] = 0;
        System.out.println(a[0] + "," + a[1] + "," + a[2] + "," + a[3] + "," + a[4]);
        Arrays.sort(a, test.valueOrder());
        System.out.println(a[0] + "," + a[1] + "," + a[2] + "," + a[3] + "," + a[4]);
    }
}
