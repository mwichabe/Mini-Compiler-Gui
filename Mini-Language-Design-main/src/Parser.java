import java.util.ArrayList;

public class Parser {
    private final ArrayList<Token> tokens;
    private int currentPosition;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
        this.currentPosition = 0;
    }

    public ASTNode parse() {
        return parseExpression();
    }

    private ASTNode parseExpression() {
        ASTNode term = parseTerm();
        while (currentPosition < tokens.size() && (isAddition() || isSubtraction())) {
            TokenType operator = tokens.get(currentPosition++).getType();
            ASTNode nextTerm = parseTerm();
            term = new BinaryOperationNode(operator, term, nextTerm);
        }
        return term;
    }

    private ASTNode parseTerm() {
        ASTNode factor = parseFactor();
        while (currentPosition < tokens.size() && (isMultiplication() || isDivision())) {
            TokenType operator = tokens.get(currentPosition++).getType();
            ASTNode nextFactor = parseFactor();
            factor = new BinaryOperationNode(operator, factor, nextFactor);
        }
        return factor;
    }

    private ASTNode parseFactor() {
        if (isNumber()) {
            return new NumberNode(Integer.parseInt(tokens.get(currentPosition++).getLexeme()));
        } else if (isLeftParenthesis()) {
            currentPosition++;
            ASTNode expression = parseExpression();
            currentPosition++; // Consume the right parenthesis
            return expression;
        } else {
            // Handle error or unsupported token
            System.err.println("Error: Unexpected token at position " + currentPosition);
            return null;
        }
    }

    private boolean isAddition() {
        return currentPosition < tokens.size() && tokens.get(currentPosition).getType() == TokenType.ADDITION;
    }

    private boolean isSubtraction() {
        return currentPosition < tokens.size() && tokens.get(currentPosition).getType() == TokenType.SUBTRACTION;
    }

    private boolean isMultiplication() {
        return currentPosition < tokens.size() && tokens.get(currentPosition).getType() == TokenType.MULTIPLICATION;
    }

    private boolean isDivision() {
        return currentPosition < tokens.size() && tokens.get(currentPosition).getType() == TokenType.DIVISION;
    }

    private boolean isNumber() {
        return currentPosition < tokens.size() && tokens.get(currentPosition).getType() == TokenType.NUMBER;
    }

    private boolean isLeftParenthesis() {
        return currentPosition < tokens.size() && tokens.get(currentPosition).getType() == TokenType.LEFT_PARENTHESIS;
    }
}
