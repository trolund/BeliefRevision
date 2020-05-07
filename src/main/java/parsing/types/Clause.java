package parsing.types;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class Clause {

    public static final Clause emptyClause = new Clause(); // Fra https://github.com/aimacode/aima-java/blob/54ddca0f2f79cb3f2cbf9d0f3293ebd95d785d13/aima-core/src/main/java/aima/core/logic/propositional/kb/data/Clause.java#L25

    private Set<Literal> literals = new HashSet<Literal>();

    public Set<Literal> getLiterals() {
        return literals;
    }

    public void setLiterals(Set<Literal> literals) {
        this.literals = literals;
    }

    public void removeLiteral(Literal literal) {
        this.literals.remove(literal);
    }

    @Override
    public String toString() {

        StringBuilder str = new StringBuilder();

        /*
        for (Literal l : literals) {
            if(l.isNot)
                str.append("not ").append(l.literal);
            else
                str.append(" ").append(l.literal);
            str.append(" or ");
        }
         */

        Iterator<Literal> iterator = literals.iterator();

        while(iterator.hasNext()) {
            Literal l = iterator.next();

            if(l.isNot)
                str.append("not ");
            str.append(l.literal);
            if(iterator.hasNext())
                str.append(" or ");
        }

        return str.toString();
    }
}
