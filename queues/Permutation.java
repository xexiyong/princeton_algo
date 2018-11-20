/* *****************************************************************************
 *  Name: sunzg
 *  Date: 2018-11-20
 *  Description: client
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            if (s.length() == 0) break;
            rq.enqueue(s);
        }
        Iterator<String> it = rq.iterator();
        int count = 0;
        while (it.hasNext()) {
            if (count >= k) break;
            count++;
            System.out.println(it.next());
        }
    }
}
