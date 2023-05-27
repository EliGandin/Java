package project;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.shape.Polygon;


/**
 * Represents an abstract Pass with a specified name and graphically presented as a Label.
 * Provides common properties and methods for Arrow and SelfArrow.
 */
public abstract class Pass {
    protected Label label;
    protected Polygon arrowHead;
    protected State startState;
    protected Bounds labelBounds;


    /**
     * Drags the Pass over the Scene
     */
    public abstract void dragPass();


    /**
     * Checks if mouse click occurred inside the area of the Pass
     *
     * @param point is a code representation of a mouse click coordinates
     * @return {@code true} if the State contains the coordinates of the mouse click
     */
    public abstract boolean contains(Point2D point);

    /**
     * Places the Label in the fitting coordinates
     */
    public abstract void placeLabel();

    /**
     * Initializes the ArrowHead field
     *
     * @return ArrowHead field
     */
    public abstract Polygon createArrowHead();


    /**
     * Constructs the Pass
     *
     * @param input      is the name of the Pass
     * @param startState is the origin of the Pass
     */
    public Pass(String input, State startState) {
        this.label = new Label(input);
        this.startState = startState;
    }


    /**
     * Retrieves the Label field
     *
     * @return Label field
     */
    public Label getLabel() {
        return label;
    }


    /**
     * Retrieves the ArrowHead field
     *
     * @return ArrowHead field
     */
    public Polygon getArrowHead() {
        return arrowHead;
    }
}
