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

    private List<Character> getAllSymbols(Node node) {
        if (node.getChildren().size() == 0) {
            char c = ((Literal) node.getData()).literal.charAt(0);
            List<Character> shortList = new ArrayList<>();
            shortList.add(c);
            return shortList;
        } else {
            List<Character> longList = new ArrayList<>();

            for (Object childNode : node.getChildren()) {
                longList.addAll(getAllSymbols((Node) childNode));
            }

            LinkedHashSet<Character> hashSet = new LinkedHashSet<>(longList);
            ArrayList<Character> listWithoutDuplicates = new ArrayList<>(hashSet);
            return listWithoutDuplicates;
        }
    }



    public HashSet<Clause> getClauses() {
        return clauses;
    }
}
