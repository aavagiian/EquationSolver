import javax.swing.SwingUtilities;

public class EquationSolver {
    public static final String LINEAR = "Linear";
    public static final String QUADRATIC = "Quadratic";

    public static void main(String[] args) {
        // Run GUI on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new EquationSolverGUI());
    }

    public static String detectEquationType(String equation) {
        if (EquationParser.isLinear(equation))
            return LINEAR;

        if (EquationParser.isQuadratic(equation))
            return QUADRATIC;

        else return "Unknown";
    }

}