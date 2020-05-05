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
}
