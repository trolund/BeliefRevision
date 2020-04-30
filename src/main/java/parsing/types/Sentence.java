package parsing.types;

public class Sentence {

    public Literal[] literals;
    public Connective connective;

    public void setLiterals(Literal[] literals) {
        this.literals = literals;
    }

    public void setConnective(Connective connective) {
        this.connective = connective;
    }
}
