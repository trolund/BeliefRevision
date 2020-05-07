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

    //Resolution
    //På hvilken form skal spørgsmålet stilles?
    //Er det i orden kun at tage imod propositions på CNF.
    //Hvad fuck er spørgsmål 3?
    public boolean plResolution(BeliefBase bb, Clause question) {
        //Ini clauses
        Set<Clause> clauses = new HashSet<>();
        clauses.addAll(bb.getClauses());
        Clause negatedQuestion = negate(question);
        clauses.add(negatedQuestion);

        //Ini new clauses
        Set<Clause> newClauses = new HashSet<>();

        while (true) {
            //for each pair of clauses in set 'Clauses'
            for (Clause c1 : clauses) {
                for (Clause c2 : clauses) {
                    if(!c1.equals(c2)) {
                        Set<Clause> resolvedClause = plResolve(c1, c2);
                        if (resolvedClause.contains(Clause.emptyClause)) //TODO check if resolvedClause contains an empty clause
                            return true;
                        newClauses.addAll(resolvedClause);
                    }
                }
            }
            if (clauses.containsAll(newClauses))
                return false;
            clauses.addAll(newClauses);
        }

    }

    public Set<Clause> plResolve(Clause c1, Clause c2) {

        Set<Clause> resolvedClauses = new HashSet<>();

        resolvedClauses.add(c1);
        resolvedClauses.add(c2);

        int complementaryLiterals = 0;

        Set<Literal> complLiterals = new HashSet<>();

        for (Literal l1 : c1.getLiterals()) {
            for (Literal l2 : c2.getLiterals()) {
                if(l1.literal.equals(l2.literal) && l1.isNot != l2.isNot) {
                    complementaryLiterals += 1;
                    complLiterals.add(l1);
                    complLiterals.add(l2);
                    //c1.removeLiteral(l1);
                    //c2.removeLiteral(l2);
                }
            }
        }

        if(complementaryLiterals == 1) {
            for(Clause c : resolvedClauses) {
                Set<Literal> tempLits = c.getLiterals();

                for(Literal l : complLiterals) {
                    if(tempLits.contains(l))
                        tempLits.remove(l);
                }
                c.setLiterals(tempLits);

                //if(c.getLiterals().size() == 0)
                //    resolvedClauses.remove(c);
            }

            //resolvedClauses.removeIf(c -> c.getLiterals().size() == 0);

            return resolvedClauses;
        }
        else {
            return resolvedClauses;
        }
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

    private Clause negate(Clause c) {
        for (Literal l : c.getLiterals()) {
            l.setLiteral(!l.isNot);
        }
        return c;
    }

}
