package parsing.types;

public class Paser {

    public Sentence[] parseString(String input) {
        if(isAtomic(input)){
            Literal lit = new Literal(isNegated(input), input.replace("not", ""));
            Sentence sen = new Sentence();
            Literal[] litArray = new Literal[1];
            litArray[0] = lit;
            sen.setSimplerSentences(litArray);



            return sen;
        }

        if(isSimpleSentence(input)){

        }



    }

    private Literal[] parseLit (Literal[] array, string input){


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
