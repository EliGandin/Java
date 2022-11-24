import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class MyLine extends MyShape implements Cloneable {
    /* Constructor */
    public MyLine(double x1, double x2, double y1, double y2) {
        super(x1, y1, x2, y2);
    }


    /* Calculates the length of the line */
    private double getLength(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof MyLine) {
            return this.getLength(getX1(), getY1(), getX2(), getY2()) == ((MyLine) other).getLength(getX1(), getY1(), getX2(), getY2());
        } else {
            System.out.println("Not Instance Of MyLine");
            return false;
        }
    }

    @Override
    /* Drawing Method */
    public void draw(GraphicsContext gc, Color color) {
        gc.setStroke(color);
        gc.strokeLine(getX1(), getY1(), getX2(), getY2());
        if (gc.getStroke() != Color.BLACK) { // Sets stroke back to default color
            gc.setStroke(Color.BLACK);
        }
    }
}
