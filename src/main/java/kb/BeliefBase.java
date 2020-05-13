package kb;

import parsing.types.*;
import java.util.*;

public class BeliefBase {

    private Parser parser = new Parser();
    private HashSet<Clause> clauses = new HashSet<Clause>();

    public void tell(String aSentence) {
        Node result = parser.parseString(aSentence);
        tell(parser.parseNode(result));
    }

    private void tell(Set<Clause> set) {
        clauses.addAll(set);
    }

    public void contractionBB(Clause c) {
        if(this.clauses.contains(c))
            this.clauses.remove(c);
    }

    public void expansionBB(Clause c) {
        if(!this.clauses.contains(c))
            this.clauses.add(c);
    }

    public HashSet<Clause> getClauses() {
        return clauses;
    }

    @Override
    public String toString() {
        return "Belief base: " + this.clauses.toString();
    }
}
