package parsing.types;

import java.util.Arrays;

public class Parser {

    public ComplexSentence complexSentence = new ComplexSentence();

    private String beautify(String str) {
       return str.replace("not", "")
                .replace("(", "")
                .replace(")","")
                .replace(" ","");
    }

    public Node parseString(String input) {
        if(isAtomic(input)){
            Literal lit = new Literal(isNegated(input), beautify(input));
            return new Node<Literal>(lit);
        }
        else if(isSimpleSentence(input)){
            //example, a and b

            int firstAnd = input.indexOf("and");
            int firstOr = input.indexOf("or");

            String[] substrings = firstAnd != -1 ? input.split("and") : input.split("or");

            Node newNode = new Node<Connective>(firstAnd != -1 ? Connective.AND : Connective.OR);
            newNode.addChild(parseString(substrings[0]));
            newNode.addChild(parseString(substrings[1]));

            return newNode;
        }
        else { //(a or b) and (not b or c) and ... and ...
            String[] substrings = input.split("and");

            Node root = new Node<Connective>(Connective.AND);

            for (String str : substrings) {
                root.addChild(parseString(str));
            }
            return root;
        }
    }

    private boolean isSimpleSentence(String input){
        int sum = 0;
        sum = sum + numberOfConnectives(input, "and");
        sum = sum + numberOfConnectives(input, "or");
        return sum == 1;
    }

    private int numberOfConnectives(String input, String connective){
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

    private boolean isNegated(String input){
        return input.contains("not");
    }

}
