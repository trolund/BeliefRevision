package main;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import kb.BeliefBase;
import parsing.types.*;

public class main {

    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String option = "";
        String input = "";
        Set<Clause> clauses = new HashSet<>();
        Parser p = new Parser();
        BeliefBase bb = new BeliefBase();

        System.out.println("You have the following options to choose from:\n1. View the current belief set\n2. Add new belief(s) to the belief set" +
                "\n3. Remove a belief from the belief set" +
                "\n4. Check if belief base entails a given belief\nEnter 0 to exit the program");

        do {
            option = myObj.nextLine();

            switch (option) {
                case "1":
                    System.out.println(bb.getClauses().toString());
                    break;
                case "2":
                    System.out.println("Enter a new belief (e.g. '(a or b)')");
                    input = myObj.nextLine();
                    Node newNode = p.parseString(input);
                    clauses.addAll(p.parseNode(newNode));
                    for(Clause c : clauses) {
                        bb.expansionBB(c);
                    }
                    break;
                case "3":
                    System.out.println("Enter belief(s) to remove from the belief set");
                    input = myObj.nextLine();
                    Node node = p.parseString(input);
                    clauses.addAll(p.parseNode(node));
                    for(Clause c : clauses) {
                        bb.contractionBB(c);
                    }
                    break;
                case "4":

                    break;
                default:
                    System.out.println("Invalid input. Try again.");
                    break;
            }

            clauses.clear();


        } while (!input.equals("0"));

        String proposition = "(b or c) and (c)"; //  = myObj.nextLine();  // Read user input
        System.out.println("Proposition is : " + proposition + "\n");  // Output user input


        bb.tell(proposition);

        /*
        Entailment
        String question = "(b)"; //myObj.nextLine(); //"(a or b or c)";

        Set<Clause> clauses = p.parseNode(p.parseString(question));
        Clause c = clauses.iterator().next();

        System.out.println(Clause.emptyClause.getLiterals().size());

        System.out.println("Does " + proposition + " entails " + question + "?" + "\nResult: " + p.plResolution(bb, c));

         */

        /*
        Clause c1 = new Clause();
        Clause c2 = new Clause();
        Literal l1 = new Literal(false, "c");
        Literal l2 = new Literal(true, "d");
        c1.setLiteral(l1);

        c2.setLiteral(l1);
        c2.setLiteral(l2);

        System.out.println(bb.toString());

        //bb.contractionBB(c1);
        bb.expansionBB(c1);
        bb.expansionBB(c2);




        System.out.println(bb.toString());

         */



    }


}
