public class QuadraticEquationSolver extends Equation {
    private double a;
    private double b;
    private double c;

    //constructor
    public QuadraticEquationSolver (double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public boolean validateEquation() {
        //in the form ax^2 + bx + c = 0, a should not be zero to be a quadratic equation
        return a != 0;
    }

    @Override
    public void solve() {
        //discriminant calculation
        double discriminant = (b * b) - (4 * a * c);

        if (discriminant > 0) {
            //find the two real roots
            double firstRoot = (-b + Math.sqrt(discriminant)) / (2 * a);
            double secondRoot = (-b - Math.sqrt(discriminant)) / (2 * a);
            if(firstRoot==-0.0)firstRoot=0;
            if(secondRoot==-0.0)secondRoot=0;
            System.out.println(ResultFormatter.formatSolution(new double[] {firstRoot, secondRoot}));
        }
        else if (discriminant == 0) {
            //find the one real root
            double root = -b/(2 * a);
            if(root==-0.0)root=0;
            System.out.println(ResultFormatter.formatSolution(new double[] {root}));
        }
        else System.out.println("The equation has no real roots (the discriminant is negative).");
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

}
