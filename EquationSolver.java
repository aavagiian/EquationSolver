import java.util.Scanner;
public class EquationSolver {
    public static final String LINEAR = "Linear";
    public static final String QUADRATIC = "Quadratic";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an equation to solve (e.g. x^2 + 5x + 6 = 0 or 2x + 4 = 0): ");
        String input = scanner.nextLine();

        String type = detectEquationType(input);
        Equation equation = EquationParser.parseEquation(input, type);

        if (equation != null && equation.validateEquation()) {
            solveEquation(equation);
        }
        else System.out.println("Invalid equation format or an unsupported one.");
    }

    public static String detectEquationType(String equation) {
        if (EquationParser.isLinear(equation))
            return LINEAR;

        if (EquationParser.isQuadratic(equation))
            return QUADRATIC;

        else return "Unknown";
    }

    public static void solveEquation(Equation equation){
        equation.solve();
    }
}
