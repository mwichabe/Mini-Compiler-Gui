import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    private final String input;
    private int currentPosition;
    private ArrayList<Token> tokens;

    public Lexer(String input) {
        this.input = input;
        this.currentPosition = 0;
        this.tokens = new ArrayList<>();
    }

    public ArrayList<Token> tokenize() {
        while (currentPosition < input.length()) {
            char currentChar = input.charAt(currentPosition);

            // Skip whitespaces
            if (Character.isWhitespace(currentChar)) {
                currentPosition++;
                continue;
            }

            // Try to match each token pattern
            boolean matched = false;
            for (TokenType tokenType : TokenType.values()) {
                Pattern pattern = Pattern.compile(tokenType.getPattern());
                Matcher matcher = pattern.matcher(input.substring(currentPosition));

                if (matcher.find() && matcher.start() == 0) {
                    String lexeme = matcher.group();
                    tokens.add(new Token(tokenType, lexeme, currentPosition, currentPosition + lexeme.length() - 1));
                    currentPosition += lexeme.length();
                    matched = true;
                    break;
                }
            }

            // If no token pattern is matched, throw an error
            if (!matched) {
                throw new LexicalErrorException("Unexpected character at position " + currentPosition);
            }
        }

        return tokens;
    }

    public static void main(String[] args) {
        String inputCode = "2 + 3 * (4 - 1);";
        Lexer lexer = new Lexer(inputCode);
        ArrayList<Token> tokens = lexer.tokenize();

        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}

class Token {
    private final TokenType type;
    private final String lexeme;
    private final int startPos;
    private final int endPos;

    public Token(TokenType type, String lexeme, int startPos, int endPos) {
        this.type = type;
        this.lexeme = lexeme;
        this.startPos = startPos;
        this.endPos = endPos;
    }

    @Override
    public String toString() {
        return "Token{" +
                "type=" + type +
                ", lexeme='" + lexeme + '\'' +
                ", startPos=" + startPos +
                ", endPos=" + endPos +
                '}';
    }

    public TokenType getType() {
        return type;
    }

    public String getLexeme() {
        return lexeme;
    }

    public int getStartPos() {
        return startPos;
    }

    public int getEndPos() {
        return endPos;
    }
}

enum TokenType {
    NUMBER("\\d+"),
    ADDITION("\\+"),
    SUBTRACTION("-"),
    MULTIPLICATION("\\*"),
    DIVISION("/"),
    LEFT_PARENTHESIS("\\("),
    RIGHT_PARENTHESIS("\\)"),
    SEMICOLON(";");

    private final String pattern;

    TokenType(String pattern) {
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}

class LexicalErrorException extends RuntimeException {
    public LexicalErrorException(String message) {
        super(message);
    }
}
