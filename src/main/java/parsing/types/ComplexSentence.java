package parsing.types;

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
}
