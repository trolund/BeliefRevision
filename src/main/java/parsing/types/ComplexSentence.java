package parsing.types;

import java.util.Arrays;

public class ComplexSentence {

    public Connective getConnective() {
        return connective;
    }

    public void setConnective(Connective connective) {
        this.connective = connective;
    }

    public Sentence[] getSimplerSentences() {
        return simplerSentences;
    }

    public void setSimplerSentences(Sentence[] simplerSentences) {
        this.simplerSentences = simplerSentences;
    }

    private Connective connective;
    private Sentence[] simplerSentences;

    @Override
    public String toString() {
        return "ComplexSentence{" +
                "connective=" + connective +
                ", simplerSentences=" + Arrays.toString(simplerSentences) +
                '}';
    }
}
