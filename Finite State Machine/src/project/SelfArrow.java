package project;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Polygon;

public class SelfArrow extends Pass {
    private final Arc arc;
    private final double RADIUS = 20;
    private final double OFFSET = 30;


    /**
     * Constructs the SelfArrow
     *
     * @param input is the name of the Arrow
     * @param state is the origin of the Arrow
     */
    public SelfArrow(String input, State state) {
        super(input, state);
        this.arc = createArc();
        this.arrowHead = createArrowHead();
        placeLabel();
    }


    /**
     * Initializes the arc field
     *
     * @return Arc field
     */
    private Arc createArc() {
        Arc arc = new Arc(startState.getX() - OFFSET, startState.getY() - OFFSET, RADIUS, RADIUS, 0, 270);

        arc.setFill(Color.TRANSPARENT);
        arc.setType(ArcType.OPEN);
        arc.setStroke(Color.BLACK);

        return arc;
    }


    /**
     * Initializes the ArrowHead field
     *
     * @return the ArrowHead field
     */
    public Polygon createArrowHead() {
        Polygon arrowHead = new Polygon();
        arrowHead.getPoints().addAll(arc.getCenterX() + arc.getRadiusX(), arc.getCenterY(),
                arc.getCenterX() + arc.getRadiusX() + 7, arc.getCenterY() - 7,
                arc.getCenterX() + arc.getRadiusX() - 7, arc.getCenterY() - 7);

        arrowHead.setFill(Color.BLACK);

        return arrowHead;
    }


    /**
     * Drags of SelfArrow following changes in the {@code state}
     */
    @Override
    public void dragPass() {
        arc.setCenterX(startState.getX() - OFFSET);
        arc.setCenterY(startState.getY() - OFFSET);
        arc.setRadiusX(RADIUS);
        arc.setRadiusY(RADIUS);

        arrowHead.getPoints().clear();
        arrowHead.getPoints().addAll(arc.getCenterX() + arc.getRadiusX(), arc.getCenterY(),
                arc.getCenterX() + arc.getRadiusX() + 7, arc.getCenterY() - 7,
                arc.getCenterX() + arc.getRadiusX() - 7, arc.getCenterY() - 7);

        placeLabel();
    }


    /**
     * Checks if mouse click occurred inside the area of the SelfArrow
     *
     * @param point is a code representation of a mouse click coordinates
     * @return {code true} if the Arrow contains the coordinates of the mouse click
     */
    @Override
    public boolean contains(Point2D point) {
        return (arc.contains(point) || arrowHead.contains(point) || labelBounds.contains(point));
    }


    /**
     * Places the Label in the fitting coordinates
     */
    @Override
    public void placeLabel() {
        label.setLayoutX((arc.getCenterX()));
        label.setLayoutY(arc.getCenterY() - RADIUS - 15);
        label.setAlignment(javafx.geometry.Pos.CENTER);
    }


    /**
     * Retrieves the Arc field
     *
     * @return Arc field
     */
    public Arc getArc() {
        return arc;
    }
}
