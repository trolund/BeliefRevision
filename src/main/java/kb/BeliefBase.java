package kb;

import parsing.types.*;
import java.util.*;

public class BeliefBase {

    private Parser parser = new Parser();
    private HashSet<Clause> clauses = new HashSet<Clause>();

    public void tell(String aSentence) {
        Node res = parser.parseString(aSentence);
        Node result = parser.convertToCNF(res);
        tell(parser.parseNode(result));
    }

    private void tell(Set<Clause> set) {
        clauses.addAll(set);
    }

    public HashSet<Clause> getClauses() {
        return clauses;
    }

    @Override
    public String toString() {
        return "Belief base: " + this.clauses.toString();
    }
}
