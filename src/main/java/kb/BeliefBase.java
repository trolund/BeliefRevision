package kb;

import parsing.types.*;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class BeliefBase {

    private Parser parser = new Parser();
    private LinkedHashSet<Clause> clauses = new LinkedHashSet<Clause>();

    public void tell(String aSentence) {
        tell((Node) parser.parseString(aSentence));
    }

    private void tell(Node node) {
        if (!(sentences.contains(node))) {
            sentences.add(node);
            List<Character> symbolesList = getAllSymbols(node);
            symbols.addAll(symbolesList);
            addLiterals(node);
        }
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

    private void addLiterals(Node node) {
        if (node.getChildren().size() == 0) {
            literals.add(((Literal) node.getData()));
        } else {
            for (Object childNode : node.getChildren()) {
                addLiterals((Node) childNode);
            }
        }
    }

    public List<Node<Connective>> getSentences() {
        return sentences;
    }


    public List<Character> getSymbols() {
        return symbols;
    }

    public List<Literal> getLiterals() {
        return literals;
    }
}
