package parsing.types;

import kb.BeliefBase;

import java.util.*;

public class Parser {

    private String beautify(String str) {
        return str.replace("not", "")
                .replace("(", "")
                .replace(")", "")
                .replace(" ", "");
    }

    public HashSet<Clause> parseNode(Node input) {
        if(input.getData().equals(Connective.AND)) {
            HashSet<Clause> tempClauses = new HashSet<Clause>();
            for (Object n : input.getChildren()) {

                Set<Clause> results = parseNode((Node) n);
                tempClauses.addAll(results);
            }
            return tempClauses;
        }
        else  {
            LinkedHashSet<Literal> tempLits = new LinkedHashSet<Literal>();

            for (Object n : input.getChildren()) {
                Node tempNode = (Node) n;
                tempLits.add((Literal) tempNode.getData());
            }

            Clause tempClause = new Clause();
            tempClause.setLiterals(tempLits);

            HashSet<Clause> tempClauses = new HashSet<Clause>();
            tempClauses.add(tempClause);
            return tempClauses;
        }
    }

    public Node parseString(String input) {
        if (isAtomic(input)) {
            Literal lit = new Literal(isNegated(input), beautify(input));
            return new Node<Literal>(lit);
        } else if (isSimpleSentence(input)) {
            //example, a and b

            int firstAnd = input.indexOf("and");
            int firstOr = input.indexOf("or");

            String[] substrings = firstAnd != -1 ? input.split("and") : input.split("or");

            Node newNode = new Node<Connective>(firstAnd != -1 ? Connective.AND : Connective.OR);

            for (String str : substrings) {
                newNode.addChild(parseString(str));
            }

            //newNode.addChild(parseString(substrings[0]));
            //newNode.addChild(parseString(substrings[1]));

            return newNode;
        } else { //(a or b) and (not b or c) and ... and ...
            String[] substrings = input.split("and");

            Node root = new Node<Connective>(Connective.AND);

            for (String str : substrings) {
                root.addChild(parseString(str));
            }
            return root;
        }
    }


    //BB entails p, clause

    public boolean entailment(BeliefBase bb, String question) {

        bb.tell(question);

        /*
        Node result = parseString(question);

        Literal l = (Literal) result.getData();

        Literal notL = new Literal(!l.isNot, l.literal);
        Node result = parseString(question);

         */

        return true;
    }

    private boolean isSimpleSentence(String input) {
        int sum = 0;
        sum = sum + numberOfConnectives(input, "and");
        sum = sum + numberOfConnectives(input, "or");
        return sum == 1;
    }

    private int numberOfConnectives(String input, String connective) {
        int index = input.indexOf(connective);
        int count = 0;
        while (index != -1) {
            count++;
            input = input.substring(index + 1);
            index = input.indexOf("is");
        }
        return count;
    }

    private boolean isAtomic(String input) {
        return !(input.contains("and") || input.contains("or"));
    }

    private boolean isNegated(String input) {
        return input.contains("not");
    }

}
