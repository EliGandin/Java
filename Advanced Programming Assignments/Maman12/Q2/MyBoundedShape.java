public abstract class MyBoundedShape extends MyShape {
    private boolean isFilled;

    /* Constructor */
    public MyBoundedShape(double x1, double y1, double x2, double y2, boolean isFilled) {
        super(x1, y1, x2, y2);
        this.isFilled = isFilled;
    }

    /* Getter and Setter */
    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MyOval && this instanceof MyOval || obj instanceof MyRectangle && this instanceof MyOval) {
            /* Calculates and evaluates the height and width of the MyBoundedShape */
            return ((Math.abs(this.getX1() - this.getX2()) == Math.abs(((MyBoundedShape) obj).getX1() - ((MyBoundedShape) obj).getX2()))
                    && ((Math.abs(this.getY1() - this.getY2()) == Math.abs(((MyBoundedShape) obj).getY1() - ((MyBoundedShape) obj).getY2()))));
        } else {
            System.out.println("Not Instance of MyBoundedShape");
            return false;
        }
    }
}
