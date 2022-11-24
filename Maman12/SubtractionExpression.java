public class SubtractionExpression extends CompoundExpression {

    /* Constructor */
    public SubtractionExpression(Expression firstOperand, Expression secondOperand) {
        super(firstOperand, secondOperand);
    }

    @Override
    /* Implements abstract calculate() method*/
    public double calculate() {
        return firstOperand.calculate() - secondOperand.calculate();
    }

    @Override
    /* Overrides toString() method */
    public String toString() {
        return firstOperand.toString() + " - " + secondOperand.toString();
    }
}
