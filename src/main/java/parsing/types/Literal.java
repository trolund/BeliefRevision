package parsing.types;

public class Literal {
    public boolean isNot;
    public String literal;

    public Literal(boolean isNot, String literal) {
        this.isNot = isNot;
        this.literal = literal;
    }
}
