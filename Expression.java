public abstract class Expression {
    public abstract double calculate(); // As required in the assignment

    @Override
    /* Equals method that will be used for sub-classes*/
    public boolean equals(Object obj) {
        if (obj instanceof Expression) {
            return this.calculate() == ((Expression) obj).calculate();
        } else {
            System.out.println("Not Instance of Expression");
            return false;
        }
    }
}
