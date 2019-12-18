import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


/**
 * Write a program to estimate the value of the percolation threshold via Monte Carlo simulation.
 *
 * @Author Jonathan
 * @Date 2019/12/17
 **/
public class Percolation {

    // number of grid (site)
    private final int size;

    // weighted union-find data structure
    private final WeightedQuickUnionUF uf;

    private final WeightedQuickUnionUF ufTop;


    // a boolean array to store status of each site
    private boolean[] status;

    /**
     * creates n-by-n grid, with all sites initially blocked
     *
     * @param n
     */
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Invalid input: the value must be greater than 0");
        }
        // row and column indices are between 1 and n, hence +1
        this.size = n;

        status = new boolean[size * size + 2];
        // all of sites are empty by default, except virtual node
        for (int i = 0; i < size; i++) {
            status[i] = false;
        }
        status[0] = status[size * size + 1] = true;

        //openStatus = new byte[this.N][this.N];
        // create two virtual nodes to indicate status of top row and bottom row, initial n * n + 2 sites
        uf = new WeightedQuickUnionUF(this.size * this.size + 2);
        ufTop = new WeightedQuickUnionUF(this.size * this.size + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        In in = new In(args[0]);
        int size = in.readInt();
        Percolation percolation = new Percolation(size);
        boolean isPercolated = false;
        int count = 0;
        while (!in.isEmpty()) {
            int row = in.readInt();
            int col = in.readInt();
            if (!percolation.isOpen(row, col)) {
                count++;
            }
            percolation.open(row, col);
            isPercolated = percolation.percolates();
            if (isPercolated) {
                break;
            }
        }
        StdOut.println(count + " opened sites");
        if (isPercolated) {
            StdOut.println("Percolated");
        } else {
            StdOut.println("Doesn't percolate");
        }
    }

    /**
     * we use 1d array presented 2d array, 2d-array[row][col] equals to 1d-array[(row - 1) * length + col]
     *
     * @param row
     * @param col
     * @return
     */
    private int getPosition(int row, int col) {
        return (row - 1) * size + col;
    }

    /**
     * check the status of site
     *
     * @param position
     * @return
     */
    private boolean isOpen(int position) {
        return status[position];
    }

    private void union(int p, int q, WeightedQuickUnionUF auf) {
        if (!(auf.find(p) == auf.find(q))) {
            auf.union(p, q);
        }
    }

    /**
     * common arguments check
     *
     * @param row
     * @param col
     */
    private void validate(int row, int col) {
        if (row < 0 || col < 0 || row > size || col > size) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    /**
     * opens the site (row, col) if it is not open already
     *
     * @param row
     * @param col
     */
    public void open(int row, int col) {
        this.validate(row, col);
        if (isOpen(row, col)) {
            return;
        }

        int curPos = getPosition(row, col);
        int prevRowPos = getPosition(row - 1, col);
        int nextRowPos = getPosition(row + 1, col);
        int prevColPos = getPosition(row, col - 1);
        int nextColPos = getPosition(row, col + 1);
        status[curPos] = true;

        /**
         * top row
         */
        if (row == 1) {
            union(0, curPos, uf);
            union(0, curPos, ufTop);
        } else if (isOpen(prevRowPos)) {
            union(curPos, prevRowPos, uf);
            union(curPos, prevRowPos, ufTop);
        }

        /**
         * bottom row
         */
        if (row == size) {
            union(size * size + 1, curPos, uf);
        } else if (isOpen(nextRowPos)) {
            union(curPos, nextRowPos, uf);
            union(curPos, nextRowPos, ufTop);
        }

        if (col != 1 && isOpen(prevColPos)) {
            union(curPos, prevColPos, uf);
            union(curPos, prevColPos, ufTop);
        }

        if (col != size && isOpen(nextColPos)) {
            union(curPos, nextColPos, uf);
            union(curPos, nextColPos, ufTop);
        }

    }

    /**
     * is the site (row, col) open?
     *
     * @param row
     * @param col
     * @return
     */
    public boolean isOpen(int row, int col) {
        this.validate(row, col);
        return isOpen(getPosition(row, col));
    }

    /**
     * is the site (row, col) full?
     *
     * @param row
     * @param col
     * @return
     */
    public boolean isFull(int row, int col) {
        this.validate(row, col);
        return ufTop.find(0) == ufTop.find(this.getPosition(row, col));
    }

    /**
     * does the system percolate?
     *
     * @return
     */
    public boolean percolates() {
        return uf.find(0) == uf.find(size * size + 1);
    }

    /**
     * returns the number of open sites
     *
     * @return
     */
    public int numberOfOpenSites() {
        int count = 0;
        int tmpSize = size * size + 1;
        for (int i = 1; i < tmpSize; i++) {
            count += (status[i] ? 1 : 0);
        }
        return count;
    }
}
