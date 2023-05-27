import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class CloneShapesController {

    @FXML
    private Canvas canv;

    private final int MAX_SIZE = 201;
    private GraphicsContext gc;

    public void initialize() throws CloneNotSupportedException {
        gc = canv.getGraphicsContext2D();
        drawShapes();
    }

    public void drawShapes() throws CloneNotSupportedException {
        Random rand = new Random();
        /* Initialize Objects*/
        MyLine line1 = new MyLine(rand.nextInt(MAX_SIZE), rand.nextInt(MAX_SIZE), rand.nextInt(MAX_SIZE), rand.nextInt(MAX_SIZE));
        MyLine line2 = new MyLine(rand.nextInt(MAX_SIZE), rand.nextInt(MAX_SIZE), rand.nextInt(MAX_SIZE), rand.nextInt(MAX_SIZE));
        MyOval oval1 = new MyOval(rand.nextInt(MAX_SIZE), rand.nextInt(MAX_SIZE), rand.nextInt(MAX_SIZE), rand.nextInt(MAX_SIZE),
                true);
        MyOval oval2 = new MyOval(rand.nextInt(MAX_SIZE), rand.nextInt(MAX_SIZE), rand.nextInt(MAX_SIZE), rand.nextInt(MAX_SIZE),
                true);
        MyRectangle rect2 = new MyRectangle(rand.nextInt(MAX_SIZE), rand.nextInt(MAX_SIZE), rand.nextInt(MAX_SIZE), rand.nextInt(MAX_SIZE),
                true);
        MyRectangle rect1 = new MyRectangle(rand.nextInt(MAX_SIZE), rand.nextInt(MAX_SIZE), rand.nextInt(MAX_SIZE), rand.nextInt(MAX_SIZE),
                true);

        /* Drawing Objects*/
        line1.draw(gc, Color.RED);
        line2.draw(gc, Color.RED);
        oval1.draw(gc, Color.RED);
        oval2.draw(gc, Color.RED);
        rect1.draw(gc, Color.RED);
        rect2.draw(gc, Color.RED);

        /* Creating ArrayLists */
        ArrayList<MyShape> originalShapes = new ArrayList<MyShape>();
        ArrayList<MyShape> clonedShapes = new ArrayList<MyShape>();
        originalShapes.add(line1);
        originalShapes.add(line2);
        originalShapes.add(oval1);
        originalShapes.add(oval2);
        originalShapes.add(rect1);
        originalShapes.add(rect2);

        /* Cloning */
        for (int i = 0; i < originalShapes.size(); i++) {
            clonedShapes.add((MyShape) originalShapes.get(i).clone());
            clonedShapes.get(i).setX1(clonedShapes.get(i).getX1() + 10);
            clonedShapes.get(i).setY1(clonedShapes.get(i).getY1() + 10);
            if (clonedShapes.get(i) instanceof MyBoundedShape) {
                ((MyBoundedShape) clonedShapes.get(i)).setFilled(false);
            }
            clonedShapes.get(i).draw(gc, Color.GREEN);
        }
    }
}
