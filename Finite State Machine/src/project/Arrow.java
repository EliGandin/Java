package project;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class Arrow extends Pass {
    private final Line line;
    private final State endState;
    private double startDx;
    private double startDy;
    private double endDx;
    private double endDy;


    /**
     * Constructs the Arrow
     *
     * @param input      is the name of the Arrow
     * @param startState is the origin of the Arrow
     * @param endState   is the destination of the Arrow
     */
    public Arrow(String input, State startState, State endState) {
        super(input, startState);
        this.endState = endState;
        this.line = createLine();
        this.arrowHead = createArrowHead();
        placeLabel();
    }


    /**
     * Initializes the graphic representation of the Arrow
     *
     * @return Line field
     */
    private Line createLine() {
        setArrowPlacement();
        return new Line(startDx, startDy, endDx, endDy);
    }


    /**
     * Sets the arrow to connect the closest points on the circumferences of the {@code startState} and {@code endState}
     */
    private void setArrowPlacement() {
        double dx = endState.getCircle().getCenterX() - startState.getCircle().getCenterX();
        double dy = endState.getCircle().getCenterY() - startState.getCircle().getCenterY();

        double angleOfArrow;
        if (dx == 0) {
            angleOfArrow = dy > 0 ? Math.PI / 2 : -Math.PI / 2;
        } else if (dx > 0) {
            angleOfArrow = Math.atan(dy / dx);
        } else {
            angleOfArrow = Math.atan(dy / dx) + Math.PI;
        }

        startDx = startState.getCircle().getCenterX() + Math.cos(angleOfArrow) * startState.getCircle().getRadius();
        startDy = startState.getCircle().getCenterY() + Math.sin(angleOfArrow) * startState.getCircle().getRadius();
        endDx = endState.getCircle().getCenterX() - Math.cos(angleOfArrow) * endState.getCircle().getRadius();
        endDy = endState.getCircle().getCenterY() - Math.sin(angleOfArrow) * endState.getCircle().getRadius();
    }


    /**
     * Initializes the ArrowHead field
     *
     * @return ArrowHead field
     */
    public Polygon createArrowHead() {
        double arrowHeadAngle = Math.atan2(getEndY() - getStartY(), getEndX() - getStartX());

        Polygon arrowhead = new Polygon();
        arrowhead.getPoints().addAll(getEndX() - 10 * Math.cos(arrowHeadAngle - Math.toRadians(45)),
                getEndY() - 10 * Math.sin(arrowHeadAngle - Math.toRadians(45)),
                getEndX(), line.getEndY(),
                getEndX() - 10 * Math.cos(arrowHeadAngle + Math.toRadians(45)),
                getEndY() - 10 * Math.sin(arrowHeadAngle + Math.toRadians(45)));

        return arrowhead;
    }


    /**
     * Drags of Arrow following changes in the {@code startState} and {@code endState} position
     */
    @Override
    public void dragPass() {
        setArrowPlacement();
        line.setStartX(startDx);
        line.setStartY(startDy);
        line.setEndX(endDx);
        line.setEndY(endDy);

        placeLabel();
        setArrowheadAngle();
    }


    /**
     * sets the angle the ArrowHead points following changes in the {@code startState} and {@code endState} position
     */
    private void setArrowheadAngle() {
        double arrowHeadAngle = Math.atan2(getEndY() - getStartY(), getEndX() - getStartX());
        arrowHead.getPoints().clear();
        arrowHead.getPoints().addAll(
                line.getEndX() - 10 * Math.cos(arrowHeadAngle - Math.toRadians(45)),
                line.getEndY() - 10 * Math.sin(arrowHeadAngle - Math.toRadians(45)),
                line.getEndX(),
                line.getEndY(),
                line.getEndX() - 10 * Math.cos(arrowHeadAngle + Math.toRadians(45)),
                line.getEndY() - 10 * Math.sin(arrowHeadAngle + Math.toRadians(45)));
    }


    /**
     * Checks if mouse click occurred inside the area of the Arrow
     *
     * @param point is a code representation of a mouse click coordinates
     * @return {code true} if the Arrow contains the coordinates of the mouse click
     */
    @Override
    public boolean contains(Point2D point) {
        return (line.contains(point) || arrowHead.contains(point) || labelBounds.contains(point));
    }


    /**
     * Places the Label in the fitting coordinates
     */
    @Override
    public void placeLabel() {
        label.setLayoutX(Math.abs(line.getEndX() + line.getStartX()) / 2);
        label.setLayoutY(Math.abs((line.getEndY() + line.getStartY()) / 2) - 15);

        label.setAlignment(javafx.geometry.Pos.CENTER);
    }


    /**
     * Retrieves the X coordinates of the start of the Arrow
     *
     * @return X coordinates of the start of the Arrow
     */
    public double getStartX() {
        return this.line.getStartX();
    }


    /**
     * Retrieves the Y coordinates of the start of the Arrow
     *
     * @return Y coordinates of the start of the Arrow
     */
    public double getStartY() {
        return line.getStartY();
    }


    /**
     * Retrieves the X coordinates of the end of the Arrow
     *
     * @return X coordinates of the end of the Arrow
     */
    public double getEndX() {
        return this.line.getEndX();
    }


    /**
     * Retrieves the Y coordinates of the end of the Arrow
     *
     * @return Y coordinates of the end of the Arrow
     */
    public double getEndY() {
        return this.line.getEndY();
    }


    /**
     * Retrieves the Line field
     *
     * @return Line field
     */
    public Line getLine() {
        return line;
    }
}
