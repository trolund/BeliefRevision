package parsing.types;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class Clause implements Cloneable {

    public static final Clause emptyClause = new Clause(); // Fra https://github.com/aimacode/aima-java/blob/54ddca0f2f79cb3f2cbf9d0f3293ebd95d785d13/aima-core/src/main/java/aima/core/logic/propositional/kb/data/Clause.java#L25

    public Clause(Set<Literal> literals) {
        this.literals = literals;
    }

    public Clause() {}

    private Set<Literal> literals = new HashSet<Literal>();

    public Set<Literal> getLiterals() {
        return literals;
    }

    public void setLiterals(Set<Literal> literals) {
        this.literals = literals;
    }

    public void setLiteral(Literal literal) {this.literals.add(literal); }

    @Override
    public String toString() {

        StringBuilder str = new StringBuilder();
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

    //Source for overriding equals + hashCode: https://dzone.com/articles/working-with-hashcode-and-equals-in-java

/*
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Clause))
            return false;
        if (obj == this)
            return true;

        if(this.getLiterals().size() == ((Clause) obj).getLiterals().size()) {
            return this.getLiterals().contains(((Clause) obj).getLiterals());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(literals.hashCode());
    }

*/

    @Override
    public int hashCode() {
        int hashCode = 1;
        for (Literal e : literals)
            hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Clause other = (Clause) obj;
        if (this.hashCode() != other.hashCode())
            return false;
        return true;
    }

    @Override
    public Clause clone() {
        try {
            return (Clause) super.clone();
        } catch (Exception e) {
            return new Clause(this.getLiterals());
        }
        /*
        try {
            return new Clause(this.getLiterals());
        } catch (Exception e) {
            return new Clause(this.getLiterals());
        }

         */
    }

}
