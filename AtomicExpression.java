public class AtomicExpression extends Expression {
    private double value;

    /* Constructor*/
    public AtomicExpression(double value) {
        this.value = value;
    }

    @Override
    /* Implements abstract calculate() method*/
    public double calculate() {
        return value;
    }

    @Override
    /* Overrides toString() method */
    public String toString(){
        return String.valueOf(this.value);
    }
}
