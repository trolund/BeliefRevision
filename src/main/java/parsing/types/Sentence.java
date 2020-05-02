package parsing.types;

import java.util.Arrays;

public class Sentence {

    private Connective connective;
    private Literal[] literals;

    public Connective getConnective() {
        return connective;
    }

    public void setConnective(Connective connective) {
        this.connective = connective;
    }

    public Literal[] getLiterals() {
        return literals;
    }

    public void setLiterals(Literal[] literals) {
        this.literals = literals;
    }

    public String getSentence() {
        StringBuilder str = new StringBuilder();

        for (Literal l : literals) {
            str.append(l.literal);
        }

        return str.toString();
    }

    @Override
    public String toString() {
        return "Sentence{" +
                "connective=" + connective +
                ", literals=" + Arrays.toString(literals) +
                '}';
    }

}