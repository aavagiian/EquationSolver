public class ResultFormatter {
    public static String formatSolution(double[] solutions){
        if (solutions.length == 1)
            return "The only real root: x = " + solutions[0];
        else
            return "The two real roots: x1 = " + solutions[0] + ", x2 = " + solutions[1];
    }
}