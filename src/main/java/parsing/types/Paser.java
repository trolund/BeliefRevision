package parsing.types;

public class Paser {

    public ComplexSentence cs = new ComplexSentence();

    public ComplexSentence parseString(String input) {
        if(isAtomic(input)){
            Literal lit = new Literal(isNegated(input), input.replace("not", ""));
            //Literal[] litArray = new Literal[1];
            //litArray[0] = lit;

            return cs;
        }
        else if(isSimpleSentence(input)){
            //example, a and b
            int firstAnd = input.indexOf("and");
            int firstOr = input.indexOf("or");

            if(firstAnd < firstOr || (firstAnd != -1 && firstOr == -1)) {
                String proposition1 = input.substring(0, firstAnd);
                proposition1 = proposition1.replace(" ", "");
                String proposition2 = input.substring(firstAnd + 3);
                proposition2 = proposition2.replace(" ", "");
                System.out.println("proposition 1: " + proposition1 + " and proposition 2: " + proposition2);

                Literal[] lits = new Literal[2];

                lits[0] = new Literal(isNegated(proposition1), proposition1);
                lits[1] = new Literal(isNegated(proposition2), proposition2);

                Sentence s = new Sentence();

                s.setLiterals(lits);
                s.setConnective(Connective.AND);

            }
            else if (firstOr < firstAnd) {
                String substr = input.substring(0, firstOr);
            }
            System.out.println("Sentence contains 'and' and 'or' at indexes: " + firstAnd + ", " + firstOr);
            return cs;
        }
        else {
            return cs;
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
