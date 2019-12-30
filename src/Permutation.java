import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * @Author Jonathan
 * @Date 2019/12/30
 **/
public class Permutation {

    public static void main(String[] args) {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            randomizedQueue.enqueue(StdIn.readString());
        }
        int number = Integer.parseInt(args[0]);
        while ((number--) > 0) {
            StdOut.println(randomizedQueue.dequeue());
        }
    }
}
