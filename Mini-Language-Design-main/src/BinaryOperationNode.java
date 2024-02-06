public class BinaryOperationNode extends ASTNode {
    private final TokenType operator;
    private final ASTNode left;
    private final ASTNode right;

    public BinaryOperationNode(TokenType operator, ASTNode left, ASTNode right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "BinaryOperationNode{" +
                "operator=" + operator +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}
