package parsing.types;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Literal implements Cloneable {
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

    public void setLiteral(Boolean b) {
        this.isNot = b;
    }

    public void setExp(String exp) {
        this.literal = exp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Literal literal1 = (Literal) o;
        return isNot == literal1.isNot &&
                Objects.equals(literal, literal1.literal);
    }

    @Override
    public int hashCode() {
        char c = this.literal.charAt(0);
        return this.isNot ? -c : c;
    }

    @Override
    protected Literal clone() throws CloneNotSupportedException {
        return (Literal) super.clone();
    }
}
