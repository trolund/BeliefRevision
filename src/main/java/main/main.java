package main;

import java.util.Scanner;

import kb.BeliefBase;
import parsing.types.*;

public class main {

    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter propositions");

        String proposition = "(not a or b) and (b or c)"; //  = myObj.nextLine();  // Read user input
        System.out.println("Proposition is : " + proposition);  // Output user input

        Parser p = new Parser();

        // Node result = p.parseString(proposition);

        BeliefBase kb = new BeliefBase();

        kb.tell(proposition);

        System.out.println(kb.getSentences());
        System.out.println(kb.getSymbols());

       // System.out.println(treeToString(result));
        // System.out.println(result.toString());



    }

    public static String treeToString(Node root)
    {
        String b = "";
        // bases case
        if (root == null)
            return "";

        // push the root data as character
        b = b + root.getData();

        // for left subtree
        b = b +"\n";
        for (Object node: root.getChildren()) {
           b = b + treeToString((Node) node);
        }


        return b;
    }

}
