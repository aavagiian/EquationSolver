import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;

public class EquationSolverGUI extends JFrame {
    private JTextField equationField;
    private JButton solveButton;
    private JButton clearButton;
    private JTextArea resultArea;
    private ByteArrayOutputStream outputStream;
    private PrintStream originalOut;
    private Timer animationTimer;

    public EquationSolverGUI() {
        // Set up the frame to maximize to full screen
        setTitle("Equation Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize to full screen
        setLocationRelativeTo(null); // Center the window

        // Get screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Set up layered pane for background image
        JLayeredPane layeredPane = new JLayeredPane();
        setContentPane(layeredPane);

        // Load and set background image with error handling
        try {
            ImageIcon background = new ImageIcon("/Users/aavagiian/Desktop/math-curriculum.jpg.jpg");
            if (background.getImageLoadStatus() != MediaTracker.COMPLETE) {
                System.err.println("Error: Image failed to load properly.");
            }
            // Scale the image to fit the full screen
            Image scaledImage = background.getImage().getScaledInstance(screenWidth, screenHeight, Image.SCALE_SMOOTH);
            background = new ImageIcon(scaledImage);
            JLabel backgroundLabel = new JLabel(background);
            backgroundLabel.setBounds(0, 0, screenWidth, screenHeight);
            layeredPane.add(backgroundLabel, Integer.valueOf(0));
        } catch (Exception e) {
            System.err.println("Error loading background image: " + e.getMessage());
        }

        // Initialize output stream for capturing System.out
        outputStream = new ByteArrayOutputStream();
        originalOut = System.out;

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        inputPanel.setOpaque(true); // Ensure background color is visible
        inputPanel.setBackground(Color.WHITE); // Set background to white
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel equationLabel = new JLabel("Equation:");
        equationLabel.setForeground(new Color(255, 165, 0)); // Orange text
        inputPanel.add(equationLabel);
        equationField = new JTextField(20);
        equationField.addActionListener(new SolveActionListener());
        equationField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                equationField.setBackground(new Color(255, 255, 102)); // Yellow highlight
            }
            @Override
            public void focusLost(FocusEvent e) {
                equationField.setBackground(Color.WHITE);
            }
        });
        inputPanel.add(equationField);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Make panel transparent to show background
        solveButton = new JButton("Solve");
        solveButton.setVisible(true);
        solveButton.setBackground(new Color(144, 238, 144)); // Green background
        solveButton.addActionListener(new SolveActionListener());
        clearButton = new JButton("Clear");
        clearButton.setBackground(new Color(255, 182, 193)); // Light pink background
        clearButton.addActionListener(e -> resultArea.setText(""));
        buttonPanel.add(solveButton);
        buttonPanel.add(clearButton);

        // Output area (result bar)
        resultArea = new JTextArea(5, 40); // Reduced rows to 5 to keep it smaller
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setBackground(new Color(211, 211, 211)); // Light gray background
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Add components to layered pane with adjusted positions
        int inputPanelWidth = 300; // Fixed width for the input panel
        int inputPanelX = (screenWidth - inputPanelWidth) / 2; // Center horizontally
        inputPanel.setBounds(inputPanelX, 0, inputPanelWidth, 50); // Center at the top
        buttonPanel.setBounds(0, 50, screenWidth, 50);
        scrollPane.setBounds(0, 100, screenWidth, 100); // Reduced height to 100 pixels
        layeredPane.add(inputPanel, Integer.valueOf(1));
        layeredPane.add(buttonPanel, Integer.valueOf(1));
        layeredPane.add(scrollPane, Integer.valueOf(1));

        // Animation for blinking cursor effect
        animationTimer = new Timer(500, new ActionListener() {
            private boolean showCursor = true;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (resultArea.getText().endsWith("|")) {
                    resultArea.setText(resultArea.getText().substring(0, resultArea.getText().length() - 1));
                } else if (showCursor && !resultArea.getText().isEmpty()) {
                    resultArea.setText(resultArea.getText() + "|");
                }
                showCursor = !showCursor;
            }
        });
        animationTimer.start();

        // Make the frame visible
        setVisible(true);
    }

    private class SolveActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resultArea.setText(""); // Clear previous results
            String equation = equationField.getText().trim();

            if (equation.isEmpty()) {
                resultArea.append("Error: Please enter an equation.\n");
                return;
            }

            // Detect equation type automatically
            String type = EquationSolver.detectEquationType(equation);
            if (type.equals("Unknown")) {
                resultArea.append("Error: Unable to determine equation type.\n");
                return;
            }

            // Redirect System.out to capture solve() output
            PrintStream printStream = new PrintStream(outputStream);
            System.setOut(printStream);

            try {
                // Parse equation
                Equation solver = EquationParser.parseEquation(equation, type);
                if (solver == null) {
                    resultArea.append("Error: Invalid equation format.\n");
                    return;
                }

                // Display parsed equation
                if (solver instanceof LinearEquationSolver) {
                    LinearEquationSolver linear = (LinearEquationSolver) solver;
                    resultArea.append(String.format("Parsed: Linear Equation Solver (a = %.2f, b = %.2f)\n",
                            linear.getA(), linear.getB()));
                } else if (solver instanceof QuadraticEquationSolver) {
                    QuadraticEquationSolver quadratic = (QuadraticEquationSolver) solver;
                    resultArea.append(String.format("Parsed: Quadratic Equation Solver (a = %.2f, b = %.2f, c = %.2f)\n",
                            quadratic.getA(), quadratic.getB(), quadratic.getC()));
                }

                // Validate and solve
                if (!solver.validateEquation()) {
                    resultArea.append("Error: Invalid equation (a = 0).\n");
                    return;
                }

                // Clear output stream and solve
                outputStream.reset();
                solver.solve();
                String solutionOutput = outputStream.toString().trim();
                resultArea.append("Result: " + solutionOutput + "\n");
            } catch (Exception ex) {
                resultArea.append("Error: " + ex.getMessage() + "\n");
            } finally {
                // Restore System.out
                System.setOut(originalOut);
            }
        }
    }

    public static void main(String[] args) {
        // Run GUI on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new EquationSolverGUI());
    }
}
