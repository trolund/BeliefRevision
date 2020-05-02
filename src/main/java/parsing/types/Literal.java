package parsing.types;

public class Literal implements Iexp<String> {
    @Override
    public String toString() {
        return isNot ? "!" + literal : literal;
    }

    public boolean isNot;
    public String literal;

    public Literal(boolean isNot, String literal) {
        this.isNot = isNot;
        this.literal = literal;
    }

    public String getExp() {
        return literal;
    }

    public void setExp(String exp) {
        this.literal = exp;
    }
}
