# EquationSolver
## Supported equations to solve with the help of this project
Linear Equations,
quadratic equations, 
polynomial equations (with Java).

## class description
 EquationSolver
 This is the main class that manages the overall program flow.
 It takes user input, determines the type of equation, and delegates the solving process to the appropriate solver       class.
 
 Methods
 main(String[] args): Starts the program and interacts with the user.
 detectEquationType(String equation): Identifies whether the equation is linear or quadratic.
 solveEquation(Equation eq): Calls the appropriate solver method based on the equation type.
 
 
 Equation (Abstract Class)
 A parent class for different types of equations (linear, quadratic, etc.).
 Contains common attributes like coefficients and methods for validation.
 
 Methods
 validateEquation(): Checks if the equation has a valid format and coefficients.
 solve(): An abstract method that must be implemented by subclasses to solve the equation.
 
 
 LinearEquationSolver (extends Equation)
 Handles the solving process for linear equations of the form ax + b = 0.
 Implements methods to calculate and return the solution.
 
 Methods
 solve(): Calculates the solution for a linear equation using x = -b / a.
 
 
 QuadraticEquationSolver (extends Equation)
 Solves quadratic equations of the form ax² + bx + c = 0.
 Implements methods to compute real and complex solutions using the quadratic formula.
 
 Methods
 solve(): Computes the roots using (-b ± sqrt(b² - 4ac)) / 2a.
 hasRealSolutions(): Checks if the discriminant (b² - 4ac) is non-negative.
 getSolution(): Returns the roots in real or complex number format.
 
 
 EquationParser
 Responsible for parsing and interpreting user input.
 Extracts coefficients and equation type from the given string.
 
 Methods
 parseEquation(String input): Extracts coefficients from the input string and returns an Equation object.
 isLinear(String equation): Checks if the equation is of the form ax + b = 0.
 isQuadratic(String equation): Checks if the equation is of the form ax² + bx + c = 0.
 
 
 ResultFormatter
 Formats the solution for user-friendly output.
 Converts results into a readable format, handling cases like real, complex, or no solution.
 
 Methods
 formatSolution(double[] solutions): Converts numerical solutions into a readable string.
 formatComplexSolution(double real, double imaginary): Handles cases with complex numbers.

## Team Members
Alyona Avagyan, Milena Ghazaryan, Davit Avagyan.
