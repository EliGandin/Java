import java.util.Random;

public class GameLogic {
    private final int SIZE = 10;
    private boolean[][] mat;

    /* Constructor */
    public GameLogic() {
        this.mat = new boolean[SIZE][SIZE];
        Random rand = new Random();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                mat[i][j] = rand.nextBoolean();
            }
        }
    }

    /* Getter */
    public boolean[][] getMat(){
        return this.mat;
    }

    /* Calculates alive Neighbors */
    public int calcAliveNeighbors(int i, int j) {
        int res = 0;

        if ((i > 0) && (j > 0) && ((mat[i - 1][j - 1] == true))) {
            res++;
        }
        if ((i > 0) && ((mat[i - 1][j] == true))) {
            res++;
        }
        if ((i > 0) && (j < SIZE - 1) && ((mat[i - 1][j + 1] == true))) {
            res++;
        }
        if ((i > 0) && ((mat[i - 1][j] == true))) {
            res++;
        }
        if ((j < SIZE - 1) && ((mat[i][j + 1] == true))) {
            res++;
        }
        if ((i > 0) && (j < SIZE - 1) && ((mat[i - 1][j + 1] == true))) {
            res++;
        }
        if ((i < SIZE - 1) && ((mat[i + 1][j] == true))) {
            res++;
        }
        if ((i < SIZE - 1) && (j < SIZE - 1) && ((mat[i + 1][j + 1] == true))) {
            res++;
        }

        return res;
    }

    /* Calculates the next generation */
    public GameLogic nextGeneration() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int aliveNeighbors = calcAliveNeighbors(i, j);
                if (mat[i][j] == false) {
                    if (aliveNeighbors == 3) {
                        this.mat[i][j] = true;
                    }
                } else {
                    if (aliveNeighbors < 2 || aliveNeighbors > 4) {
                        this.mat[i][j] = false;
                    }
                }
            }
        }
        return this;
    }

}
