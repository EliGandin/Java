import java.util.Random;

/* This class contains the logic behind the game */
public class GameLogic {
    private final int SIZE = 10;
    private boolean[][] mat;   // I work with 2 matrices in order set the next generation cycle properly
    private boolean[][] tempMat;

    /* Constructor */
    public GameLogic() {
        this.mat = new boolean[SIZE][SIZE];
        this.tempMat = new boolean[SIZE][SIZE];
        randomizeMat(mat);
        copyMat(mat,tempMat);       // copy matrices
    }

    private void randomizeMat(boolean[][] mat){
        Random rand = new Random();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                mat[i][j] = rand.nextBoolean();
            }
        }
    }

    /* Getter for mat*/
    public boolean[][] getMat() {
        return this.mat;
    }

    private void copyMat(boolean [][] srcMat, boolean[][] destMat) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                destMat[i][j] = srcMat[i][j];
            }
        }
    }

    /* Calculates alive Neighbors */
    private int calcAliveNeighbors(int i, int j) {
        int res = 0;

        if ((i > 0) && (j > 0) && ((mat[i - 1][j - 1]))) {
            res++;
        }
        if ((i > 0) && (mat[i - 1][j])) {
            res++;
        }
        if ((i > 0) && (j < SIZE - 1) && ((mat[i - 1][j + 1]))) {
            res++;
        }
        if ((j > 0) && (mat[i][j - 1])) {
            res++;
        }
        if ((j < SIZE - 1) && (mat[i][j + 1])) {
            res++;
        }
        if ((i < SIZE - 1) && (j > 0) && (mat[i + 1][j - 1])) {
            res++;
        }
        if ((i < SIZE - 1) && (mat[i + 1][j])) {
            res++;
        }
        if ((i < SIZE - 1) && (j < SIZE - 1) && (mat[i + 1][j + 1])) {
            res++;
        }
        return res;
    }

    /* Calculates the next generation */
    public void nextGeneration(boolean[][] mat) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                int aliveNeighbors = calcAliveNeighbors(i, j);
                if (mat[i][j] == false) {
                    if (aliveNeighbors == 3) {
                        tempMat[i][j] = true;
                    }
                } else {
                    if ((aliveNeighbors < 2) || (aliveNeighbors > 3)) {
                        tempMat[i][j] = false;
                    }
                }
            }
        }
        copyMat(tempMat, mat);      // copy values to the main matrix
    }
}

