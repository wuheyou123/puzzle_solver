package hw3.puzzle;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;

public class Board implements  WorldState{

    private int[][] btiles;
    private int size;
    private int estdistance;
    private final int[][] goal;

    public Board(int[][] tiles) {
        size = tiles[0].length;
        btiles = new int[size][size];
        goal = new int[size][size];
        for(int i = 0; i < size; i++) {
            System.arraycopy(tiles[i], 0, btiles[i], 0, size);
            for(int j = 0 ; j < size; j++) {
                goal[i][j] = 1 + i * size + j;
            }
        }
        goal[size - 1][size - 1] = 0;
        estdistance = manhattan();
    }

    public int tileAt(int i, int j) {
        return btiles[i][j];
    }
    public int size() {
        return size;

    }
    public Iterable<WorldState> neighbors() {

        Queue<WorldState> neighbs = new ArrayDeque<>(4);

        int[][] tiles = new int[size][size];
        int i0 = -999;
        int j0 = -999;
        for(int i = 0; i < size; i++) {

            System.arraycopy(btiles[i], 0, tiles[i], 0, size);

            if( i0 < 0 && j0 < 0) {
                for(int j = 0; j < size; j++) {
                    if(btiles[i][j] == 0) {
                        i0 = i;
                        j0 = j;
                        break;
                    }
                }
            }
        }
        for(int i=i0-1;i<i0+2;i++){
            for(int j=j0-1;j<j0+2;j++){
                if(i<0||i>=size||j<0||j>=size||Math.abs(i-i0)+Math.abs(j-j0)!=1) continue;

                tiles[i0][j0]=tiles[i][j];
                tiles[i][j] = 0;
                neighbs.add(new Board(tiles));

                tiles[i][j]=tiles[i0][j0];
                tiles[i0][j0] = 0;
            }
        }


        return neighbs;
    }
    public int hamming() {
        int count = 0;
        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(btiles[i][j] != 0 && btiles[i][j] != goal[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }
    public int manhattan() {
        int count = 0;
        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(btiles[i][j] != 0 && btiles[i][j] != goal[i][j]) {
                    count = count + tileDistance(i,j);
                }
            }
        }
        return count;

    }
    public int estimatedDistanceToGoal() {
        return estdistance;
    }

    public boolean equals(Object y) {
        if(this == y) {
            return true;
        }

        if( y == null || getClass() != y.getClass()) {
            return false;
        }

        Board b1 = (Board) y;

        if(this.btiles == null && b1.btiles == null) return true;
        if((this.btiles == null && b1.btiles != null) || (this.btiles != null && b1.btiles == null)) return false;
        if(b1.size != this.size) return false;
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if (this.btiles[i][j] != b1.btiles[i][j]) return false;
            }
        }

        return true;
    }


    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    private int tileDistance(int i, int j) {
        int tile = btiles[i][j] - 1;
        int distancei = Math.abs(tile / size - i);
        int distancej = Math.abs(tile % size - j);
        return distancei + distancej;
    }
}
