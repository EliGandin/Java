import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MyRectangle extends MyBoundedShape {
    public MyRectangle(double x1, double y1, double x2, double y2, boolean isFilled) {
        super(x1, y1, x2, y2, isFilled);
    }

    @Override
    /* Drawing Method */
    public void draw(GraphicsContext gc, Color color) {
        gc.setStroke(color);
        gc.setFill(color);
        if (isFilled()) {
            gc.fillRect(getX1(), getY1(), getX2(), getY2());
        } else {
            gc.strokeRect(getX1(), getY1(), getX2(), getY2());
        }

        if (gc.getStroke() != Color.BLACK) { // Sets stroke back to default color
            gc.setFill(Color.BLACK);
        }
    }
}
