public class EquationParser {
    public static Equation parseEquation(String input, String type) {
        // Remove spaces and normalize input
        input = input.replaceAll("\\s+", "").toLowerCase();
        String[] sides = input.split("=");
        if (sides.length != 2) {
            System.out.println("Parsing error: Equation must contain exactly one '=' sign");
            return null;
        }

        String leftSide = sides[0];
        String rightSide = sides[1];

        // Parse right-hand side value
        double rightValue;
        try {
            rightValue = Double.parseDouble(rightSide);
        } catch (Exception e) {
            System.out.println("Parsing error: Invalid right-hand side: " + e.getMessage());
            return null;
        }

        System.out.println("Processed left side: " + leftSide);

        try {
            if (type.equals(EquationSolver.LINEAR)) {
                double a = 0, b = 0;

                // Handle cases like "7x", "-x", or "x"
                if (leftSide.endsWith("x")) {
                    String coeff = leftSide.replace("x", "");
                    a = coeff.isEmpty() ? 1.0 : coeff.equals("-") ? -1.0 : Double.parseDouble(coeff);
                } else {
                    // Split at 'x' to handle "7x+8" or "7x-8"
                    String[] parts = leftSide.split("x");
                    if (parts.length < 2) {
                        System.out.println("Parsing error: No 'x' found in linear equation");
                        return null;
                    }
                    // Parse a and b
                    String coeff = parts[0];
                    a = coeff.isEmpty() ? 1.0 : coeff.equals("-") ? -1.0 : Double.parseDouble(coeff);
                    b = Double.parseDouble(parts[1].replace("+", ""));
                }
                b -= rightValue;
                return new LinearEquationSolver(a, b);
            } else if (type.equals(EquationSolver.QUADRATIC)) {
                double a = 0, b = 0, c = 0;

                leftSide = leftSide.startsWith("-") ? leftSide : "+" + leftSide;
                leftSide = leftSide.replace("-", "+-");
                String[] terms = leftSide.split("\\+");

                for (String term : terms) {
                    if (term.isEmpty()) continue;
                    if (term.contains("x^2")) {
                        String coeff = term.replace("x^2", "");
                        a = coeff.isEmpty() ? 1.0 : coeff.equals("-") ? -1.0 : Double.parseDouble(coeff);
                    } else if (term.contains("x")) {
                        String coeff = term.replace("x", "");
                        b = coeff.isEmpty() ? 1.0 : coeff.equals("-") ? -1.0 : Double.parseDouble(coeff);
                    } else {
                        c = Double.parseDouble(term);
                    }
                }
                // Adjust c by subtracting rightValue
                c -= rightValue;
                return new QuadraticEquationSolver(a, b, c);
            }
        } catch (Exception e) {
            System.out.println("Parsing error: " + e.getMessage());
            return null;
        }
        return null;
    }

    public static boolean isLinear(String equation) {
        equation = equation.toLowerCase();
        return equation.contains("x") && !equation.contains("x^2");
    }

    public static boolean isQuadratic(String equation) {
        equation = equation.toLowerCase();
        return equation.contains("x^2");
    }
}