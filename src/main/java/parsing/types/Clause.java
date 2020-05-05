package parsing.types;

import java.util.LinkedHashSet;

public class Clause {

    private LinkedHashSet<Literal> literals = new LinkedHashSet<Literal>();

    public LinkedHashSet<Literal> getLiterals() {
        return literals;
    }

    public void setLiterals(LinkedHashSet<Literal> literals) {
        this.literals = literals;
    }

    @Override
    public String toString() {

        StringBuilder str = new StringBuilder();

        for (Literal l : literals) {
            if(l.isNot)
                str.append("not ").append(l.literal);
            else
                str.append(" ").append(l.literal);
        }
        return str.toString();
    }
}
