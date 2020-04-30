package parsing.types;

import java.util.Arrays;

public class Paser {

    public ComplexSentence complexSentence = new ComplexSentence();

    public ComplexSentence parseString(String input) {

        // (a or b) and (b or c)

        //

        if(isAtomic(input)){
            Literal lit = new Literal(isNegated(input), input.replace("not", ""));
            Literal[] lits = new Literal[1];
            lits[0] = lit;
            Sentence sentence = new Sentence();
            sentence.setLiterals(lits);

            Sentence[] sentences = new Sentence[1];
            sentences[0] = sentence;

            complexSentence.setSimplerSentences(sentences);

        }
        else if(isSimpleSentence(input)){
            //example, a and b

            int firstAnd = input.indexOf("and");
            int firstOr = input.indexOf("or");

            if(firstAnd != -1 && firstOr == -1) {
                String[] substrings = input.split("and");

                for (String str : substrings) {
                    parseString(str);
                }
            }

            else if (firstAnd == -1 && firstOr != -1) {
                String[] substrings = input.split("or");

                for (String str : substrings) {
                    parseString(str);
                }
            }

        }
        else { //(a or b) and (not b or c) and ... and ...
            String[] substrings = input.split("and");

            for (String str : substrings) {
                return parseString(str);
            }
        }

        return complexSentence;
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
