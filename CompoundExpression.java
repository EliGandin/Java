public abstract class CompoundExpression extends Expression {
    protected Expression firstOperand; /* Attributes are PROTECTED so they could be accessed by sub-class */
    protected Expression secondOperand;

    /* Constructor */
    public CompoundExpression(Expression firstOperand, Expression secondOperand) {
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
    }
}
