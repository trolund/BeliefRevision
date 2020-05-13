package parsing.types;

public class Sentence {
    public Connective getConnective() {
        return connective;
    }

    public void setConnective(Connective connective) {
        this.connective = connective;
    }

    public Literal[] getSimplerSentences() {
        return simplerSentences;
    }

    public void setSimplerSentences(Literal[] simplerSentences) {
        this.simplerSentences = simplerSentences;
    }

    private Connective connective;
    private Literal[] simplerSentences;
}
