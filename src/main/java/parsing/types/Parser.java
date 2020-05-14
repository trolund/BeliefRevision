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

    public Set<Clause> parseNode(Node input) {
        if(input.getData() instanceof Literal) {
            Set<Literal> tempLits = new HashSet<Literal>();

            tempLits.add((Literal) input.getData());

            Clause tempClause = new Clause();

            tempClause.setLiterals(tempLits);

            Set<Clause> tempClauses = new HashSet<Clause>();
            tempClauses.add(tempClause);
            return tempClauses;
        }
        else if(isOrSentence(input)) {
            Set<Literal> literals = new HashSet<>();
            Set<Clause> tempClauses = new HashSet<>();
            for (Object n : input.getChildren()) {
                Set<Clause> results = parseNode((Node) n);
                for (Clause c : results) {
                    literals.addAll(c.getLiterals());
                }
            }
            Clause clause = new Clause();
            clause.setLiterals(literals);
            tempClauses.add(clause);
            return tempClauses;
        }
        else {
            HashSet<Clause> tempClauses = new HashSet<Clause>();
            for (Object n : input.getChildren()) {

                Set<Clause> results = parseNode((Node) n);
                tempClauses.addAll(results);
            }
            return tempClauses;
        }
    }

    public Node parseString(String input) {
        if (isAtomic(input)) {
            Literal lit = new Literal(isNegated(input), beautify(input));
            return new Node<Literal>(lit);
        } else {
            //example, a and b
            Node newNode = null;
            // handle "And"
            if(input.indexOf("and") >= 0) {

                String[] substrings = input.split("and");
                newNode = new Node<Connective>(Connective.AND);

                for (String str : substrings) {
                    newNode.addChild(parseString(str));
                }

                // handle "Or"
            }else if(input.indexOf("or")  >= 0) {
                String[] substrings = input.split("or");

                newNode = new Node<Connective>(Connective.OR);

                for (String str : substrings) {
                    newNode.addChild(parseString(str));
                }

            }
            else if (input.indexOf("->")  >= 0 && !(input.indexOf("<->") > 0)) { // bicondesion
                String[] substrings = input.split("->");

                newNode = new Node<Connective>(Connective.IMPLICATION);

                for (String str : substrings) {
                    newNode.addChild(parseString(str));
                }

            }else {
                String[] substrings = input.split("<->");

                newNode = new Node<Connective>(Connective.BICONDITIONAL);

                for (String str : substrings) {
                    newNode.addChild(parseString(str));
                }
            }

            return newNode;
        }
        /*
        else { //(a or b) and (not b or c) and ... and ...

            Node root = null;

            if(input.indexOf("and") != -1) {
                String[] substrings = input.split("and");

                root = new Node<Connective>(Connective.AND);

                for (String str : substrings) {
                    root.addChild(parseString(str));
                }
            }else if(input.indexOf("or") != -1) {

            }
            else if (input.indexOf("->") != -1) { // bicondesion

            }else {

            }


            return root;
        }
        */

    }
        //(a or b) --> [a, b]
    private boolean isOrSentence(Node node) {
        if(node.getData() instanceof Literal) {
            return true;
        }
        else if(node.getData() instanceof Connective) {
            return node.getData() == Connective.OR;
        }
        else{
            List<Boolean> bools = new ArrayList<>();
            for(Object n : node.getChildren()) {
                bools.add(isOrSentence((Node) n));
            }
            return !bools.contains(false);
        }
    }

    private boolean isOrSentence(String input) {
        return input.indexOf("and") == -1 && input.indexOf("<->") == -1 && input.indexOf("->") == -1 && input.indexOf("or") != -1;
    }

    private boolean isSimpleSentence(String input) {
        int sum = 0;
        sum = sum + numberOfConnectives(input, "and");
        sum = sum + numberOfConnectives(input, "or");
        sum = sum + numberOfConnectives(input, "->");
        sum = sum + numberOfConnectives(input, "<->");
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
        return !(input.contains("and") || input.contains("or") || input.contains("->") || input.contains("<->"));
    }

    private boolean isNegated(String input) {
        return input.contains("not");
    }



    public Node convertToCNF (Node nonCNFnonde) {
        // replace
        if(nonCNFnonde.getData() instanceof Literal){
            return nonCNFnonde;
        }
        else if (nonCNFnonde.getData() instanceof Connective && nonCNFnonde.getData() == Connective.IMPLICATION){
            return replaceImplication(nonCNFnonde);
        }else if (nonCNFnonde.getData() instanceof Connective && nonCNFnonde.getData() == Connective.BICONDITIONAL){
            Node tempNode = nonCNFnonde.clone();
            List<Node> children = tempNode.getChildren();

            Node leftofCon = new Node(Connective.IMPLICATION);
            leftofCon.setChildren(children);

            Node rightofCon = new Node(Connective.IMPLICATION);
            List<Node> childrenForRight = new ArrayList<>(tempNode.getChildren());
            Collections.reverse(childrenForRight);
            rightofCon.setChildren(childrenForRight);


            List<Node> newChildren = new ArrayList();
            newChildren.add(leftofCon);
            newChildren.add(rightofCon);
            tempNode.setChildren(newChildren);
            tempNode.setData(Connective.AND);

            return convertToCNF(tempNode);
        } else {
            Node tempNode = nonCNFnonde.clone();
            Node root = new Node(tempNode.getData());
            for (Object n : tempNode.getChildren()) {
                root.addChild(convertToCNF((Node) n));
            }
            return root;
        }
    }

    private Node replaceImplication (Node nonCNFnonde) {
        Node tempNode = nonCNFnonde.clone();
        List<Node> children = tempNode.getChildren();
        List<Node> updatedChildren = new ArrayList();

        updatedChildren.add(negateNode(children.get(0).clone()));
        updatedChildren.add(children.get(1).clone());
        tempNode.setChildren(updatedChildren);
        tempNode.setData(Connective.OR);
        return tempNode;
    }

    private Node negateNode (Node node){
        if(node.getData() instanceof Literal){
            Literal negatedLit = (Literal) node.getData();
            negatedLit.isNot = !negatedLit.isNot;
            return new Node<Literal>(negatedLit);
        }else {
            Node root = null;
            for (Object n : node.getChildren()) {
                root = negateNode((Node) n);
            }
            return root;
        }
    }

}
