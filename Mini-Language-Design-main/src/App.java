import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        // Test Case 1: Simple Addition
        String inputCode1 = "2 + 3;";
        performAnalysis(inputCode1);

        // Test Case 2: Complex Expression with Parentheses
        String inputCode2 = "(4 + 2) * (8 - 3);";
        performAnalysis(inputCode2);

        // Test Case 3: Mixed Operators
        String inputCode3 = "5 * 2 - (10 / 2);";
        performAnalysis(inputCode3);

        // Test Case 4: Single Statement Program
        String inputCode4 = "7;";
        performAnalysis(inputCode4);

        // Test Case 5: Unclosed Parentheses (Invalid)
        String inputCode5 = "(3 + 5 * 2;";
        performAnalysis(inputCode5);

        // Test Case 6: Unexpected Operator (Invalid)
        String inputCode6 = "6 + * 4;";
        performAnalysis(inputCode6);

        // Test Case 7: Incomplete Expression (Invalid)
        String inputCode7 = "9 - ;";
        performAnalysis(inputCode7);

        // Test Case 8: Invalid Token (Invalid)
        String inputCode8 = "12 $ 3;";
        performAnalysis(inputCode8);

        // Test Case 9: Empty Program (Edge Case)
        String inputCode9 = ";";
        performAnalysis(inputCode9);

        // Test Case 10: Single-Digit Number (Edge Case)
        String inputCode10 = "9;";
        performAnalysis(inputCode10);

        // Test Case 11: Division by Zero (Edge Case)
        String inputCode11 = "15 / 0;";
        performAnalysis(inputCode11);

        // Test Case 12: Nested Parentheses
        String inputCode12 = "((2 + 3) * 4) / (7 - 1);";
        performAnalysis(inputCode12);
    }

    private static void performAnalysis(String inputCode) {
        // Lexical Analysis
        Lexer lexer = new Lexer(inputCode);
        ArrayList<Token> tokens = lexer.tokenize();

        // Print Lexical Analysis Results
        System.out.println("\nInput Code: " + inputCode);
        System.out.println("Lexical Analysis Results:");
        for (Token token : tokens) {
            System.out.println(token);
        }

        // Syntax Analysis
        Parser parser = new Parser(tokens);
        ASTNode ast = parser.parse();

        // AST for verification
        System.out.println("\nAbstract Syntax Tree (AST):");
        System.out.println(ast);
    }
}