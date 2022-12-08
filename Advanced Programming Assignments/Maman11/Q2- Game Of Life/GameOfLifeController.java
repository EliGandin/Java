import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameOfLifeController {

    @FXML
    private Canvas canv;

    private GameLogic gl;

    private GraphicsContext gc;

    @FXML
    void pressed(ActionEvent event) {
        gl.nextGeneration(gl.getMat());
        setColor(gl.getMat());
    }

    public void initialize() {
        gc = canv.getGraphicsContext2D();
        gl = new GameLogic();
        drawMatrix();
        setColor(gl.getMat());
    }

    /* Draws the matrix */
    public void drawMatrix() {
        gc.strokeRect(0, 0, canv.getWidth(), canv.getHeight());
        for (int i = 0; i < canv.getWidth(); i += (canv.getWidth() / 10)) {
            gc.strokeLine(0, i, canv.getWidth(), i);
        }
        for (int j = 0; j < canv.getHeight(); j += (canv.getWidth() / 10)) {
            gc.strokeLine(j, 0, j, canv.getHeight());
        }
    }

    /* Sets the color of the tiles */
    public void setColor(boolean[][] mat) {
        double side = canv.getWidth()/10;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (mat[i][j]) {
                    drawMatrix();
                    gc.setFill(Color.rgb(56,199,58));
                    gc.fillRect((j * side), (i * side), (j * side) + side, (i * side) + side + (j * side) + side);
                } else{
                    drawMatrix();
                    gc.setFill(Color.WHITE);
                    gc.fillRect((j * side), (i * side), (j * side) + side, (i * side) + side + (j * side) + side);
                }
            }
        }
    }
}
