package project;

import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class State {
    final int RADIUS = 30;
    private double x;
    private double y;
    private final Circle circle;
    private final Label stateLabel;
    private final ArrayList<Pass> arrowsArr;
    private boolean acceptingState;
    private Circle acceptingCircle;


    /**
     * Constructs the State Class
     *
     * @param name is the name of the State
     * @param x    is the X coordinates for center of the State
     * @param y    is the Y coordinates for the center of the state
     */
    public State(String name, double x, double y) {
        this.x = x;
        this.y = y;
        this.circle = createState();
        this.stateLabel = createLabel(name);
        this.arrowsArr = new ArrayList<>();
        this.acceptingState = false;
    }


    /**
     * Initializes the graphics of a State
     *
     * @return Circle for graphic representation
     */
    private Circle createState() {
        Circle circ = new Circle(x, y, RADIUS);
        this.acceptingCircle = new Circle(x, y, RADIUS - 5);
        acceptingCircle.setVisible(acceptingState);
        circ.setStroke(Color.BLACK);
        circ.setFill(Color.TRANSPARENT);
        return circ;
    }


    /**
     * Initializes the Label for the Pass
     *
     * @param name is the name of the pass
     * @return Label for graphic representation
     */
    private Label createLabel(String name) {
        Label label = new Label(name);
        label.setLayoutX(this.x - 5);
        label.setLayoutY(this.y - 10);
        return label;
    }


    /**
     * Toggles the State between being an accepting State to an non-accepting State
     */
    public void toggleAcceptableState() {
        acceptingCircle.setCenterX(circle.getCenterX());
        acceptingCircle.setCenterY(circle.getCenterY());

        if (acceptingState) {
            acceptingState = false;
            acceptingCircle.setVisible(false);
        } else {
            acceptingState = true;
            acceptingCircle.setStroke(Color.AQUA);
            acceptingCircle.setFill(Color.TRANSPARENT);
            acceptingCircle.setVisible(true);
        }
    }


    /**
     * Retrieves the name of the State
     *
     * @return Name of the State
     */
    public String getStateName() {
        return stateLabel.getText();
    }


    /**
     * Retrieves the X coordinates of the center of the State
     *
     * @return X coordinates of the center of the State
     */
    public double getX() {
        return x;
    }


    /**
     * Sets the X coordinates of the center of the State
     *
     * @param dx is the new X coordinates of the center of the State
     */
    public void setX(double dx) {
        this.x = dx;
    }


    /**
     * Retrieves Y coordinates of the center of the State
     *
     * @return Y coordinates of the center of the State
     */
    public double getY() {
        return y;
    }


    /**
     * Sets the y coordinates of the center of the State
     *
     * @param dy is the new Y coordinates of the center of the State
     */
    public void setY(double dy) {
        this.y = dy;
    }


    /**
     * Retrieves the Circle field
     *
     * @return Circle field
     */
    public Circle getCircle() {
        return circle;
    }


    /**
     * Retrieves the stateLabel field
     *
     * @return Label field
     */
    public Label getStateLabel() {
        return stateLabel;
    }


    /**
     * Checks if mouse click occurred inside the area of the State
     *
     * @param point2D is a code representation of a mouse click coordinates
     * @return {@code true} if the State contains the coordinates of the mouse click
     */
    public boolean contains(Point2D point2D) {
        return circle.contains(point2D);
    }


    /**
     * Retrieves the list of Passes associated with the State
     *
     * @return ArrayList of Passes field
     */
    public ArrayList<Pass> getArrowsArr() {
        return arrowsArr;
    }


    /**
     * Retrieves the accepting-state circle
     *
     * @return Accepting State Circle field
     */
    public Circle getAcceptingCircle() {
        return acceptingCircle;
    }


    /**
     * Drags the State over the Scene
     *
     * @param dx is the new X coordinates of the center of the State
     * @param dy Y coordinates of the center of the State
     */
    public void dragState(double dx, double dy) {
        circle.setCenterX(dx);
        circle.setCenterY(dy);
        setX(dx);
        setY(dy);
        if (acceptingState) {
            acceptingCircle.setCenterX(dx);
            acceptingCircle.setCenterY(dy);
        }
        stateLabel.setLayoutX(dx - 5);
        stateLabel.setLayoutY(dy - 10);

        for (Pass arrow : arrowsArr) {
            arrow.dragPass();
        }
    }
}
