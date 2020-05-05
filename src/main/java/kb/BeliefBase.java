package kb;

import parsing.types.Connective;
import parsing.types.Literal;
import parsing.types.Node;
import parsing.types.Parser;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class BeliefBase {

    private List<Node<Connective>> sentences = new ArrayList<>();
    private List<Character> symbols = new ArrayList<>();
    private List<Literal> literals = new ArrayList<>();
    private Parser parser = new Parser();

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
        if(node.getChildren().size() == 0){
            char c = ((Literal) node.getData()).literal.charAt(0);
            List<Character> shortList = new ArrayList<>();
            shortList.add(c);
            return shortList;
        }else {
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
        if(node.getChildren().size() == 0) {
            literals.add(((Literal) node.getData()));
        }
        else {
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
