import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

public class GameOfLifeController {

    @FXML
    private Canvas canv;

    @FXML
    private Button updateBtn;

    private GameLogic gl;

    private GraphicsContext gc;

    @FXML
    void pressed(ActionEvent event) {
        setColor(gl.nextGeneration());
    }

    public void initialize() {
        gc = canv.getGraphicsContext2D();
        gl = new GameLogic();
        drawMatrix();
        setColor(gl);
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
    public void setColor(GameLogic gl) {
        double side = canv.getWidth()/10;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (gl.getMat()[i][j] == true) {
                    drawMatrix();
                    gc.setFill(Color.WHITE);
                    gc.fillRect((j * side), (i * side), (j * side) + side, (i * side) + side + (j * side) + side);
                } else{
                    drawMatrix();
                    gc.setFill(Color.BLACK);
                    gc.fillRect((j * side), (i * side), (j * side) + side, (i * side) + side + (j * side) + side);
                }
            }
        }
    }
}