package main;

import java.util.Scanner;
import java.util.Set;

import kb.BeliefBase;
import parsing.types.*;

public class main {

    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter propositions");

        String proposition = "(not a or b or d) and (b or c)"; //  = myObj.nextLine();  // Read user input
        System.out.println("Proposition is : " + proposition + "\n");  // Output user input

        Parser p = new Parser();
        BeliefBase bb = new BeliefBase();

        bb.tell(proposition);

        String question = "(a or b or c)";

        Set<Clause> clauses = p.parseNode(p.parseString(question));
        Clause c = clauses.iterator().next();

        System.out.println(Clause.emptyClause.getLiterals().size());

        System.out.println("Does " + proposition + " entails " + question + "?" + "\nResult: " + p.plResolution(bb, c));
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
