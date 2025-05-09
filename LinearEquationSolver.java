public class LinearEquationSolver extends Equation {
    private double a;
    private double b;

    //constructor
    public LinearEquationSolver (double a, double b){
        this.a = a;
        this.b = b;
    }

    @Override
    public boolean validateEquation(){
        //in the form ax + b = 0, a should not be zero to be a linear equation
        return a != 0;
    }

    @Override
    public void solve() {
        //find x
        double x = -b/a;
        if(x == -0.0) x=0;
        System.out.println(ResultFormatter.formatSolution(new double[] {x}));
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

}
