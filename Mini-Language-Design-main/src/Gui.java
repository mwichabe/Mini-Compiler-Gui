import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Gui extends JFrame {
    private JButton lexicalButton;
    private JButton syntaxButton;
    private JButton clearButton;
    private JTextArea inputTextArea;
    private JTextArea outputTextArea;

    public Gui() {
        setTitle("Mini Language Analysis");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Create components
        lexicalButton = new JButton("Lexical Analysis");
        syntaxButton = new JButton("Syntax Analysis");
        clearButton = new JButton("Clear");
        inputTextArea = new JTextArea();
        outputTextArea = new JTextArea();
        outputTextArea.setEditable(false);

        // Add action listeners to buttons
        lexicalButton.addActionListener(new LexicalButtonListener());
        syntaxButton.addActionListener(new SyntaxButtonListener());
        clearButton.addActionListener(new ClearButtonListener());

        // Add components to content pane
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(inputTextArea, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.add(lexicalButton);
        buttonPanel.add(syntaxButton);
        buttonPanel.add(clearButton);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        contentPane.add(new JScrollPane(outputTextArea), BorderLayout.CENTER);

        // Initialize input text area with test cases
        inputTextArea.setText("2 + 3 * (4 - 1);" +
                "x = 10; \n" +
                "y = x * 5; \n" +
                "if (y > 20) { \n" +
                "   z = y / 2; \n" +
                "} else { \n" +
                "   z = y + 5; \n" +
                "}");
    }

    // Action listener for lexical analysis button
    private class LexicalButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String inputCode = inputTextArea.getText();
            try {
                Lexer lexer = new Lexer(inputCode);
                ArrayList<Token> tokens = lexer.tokenize();

                // Display Lexical Analysis Results
                StringBuilder resultBuilder = new StringBuilder("Lexical Analysis Results:\n");
                for (Token token : tokens) {
                    resultBuilder.append(token).append("\n");
                }
                outputTextArea.setText(resultBuilder.toString());
            } catch (LexicalErrorException ex) {
                outputTextArea.setText("Lexical Error: " + ex.getMessage());
            }
        }
    }

    // Action listener for syntax analysis button
    private class SyntaxButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String inputCode = inputTextArea.getText();
            try {
                Lexer lexer = new Lexer(inputCode);
                ArrayList<Token> tokens = lexer.tokenize();

                Parser parser = new Parser(tokens);
                ASTNode ast = parser.parse();

                // Display Abstract Syntax Tree (AST)
                outputTextArea.setText("Abstract Syntax Tree (AST):\n");
                outputTextArea.append(ast.toString());
            } catch (LexicalErrorException | SyntaxErrorException ex) {
                outputTextArea.setText("Syntax Error: " + ex.getMessage());
            }
        }
    }

    // Action listener for clear button
    private class ClearButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            inputTextArea.setText("");
            outputTextArea.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Gui gui = new Gui();
                gui.setVisible(true);
            }
        });
    }
}
