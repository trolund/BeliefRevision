package main;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import kb.BeliefBase;
import kb.bbController;
import parsing.types.*;

public class main {

    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        String option = "";
        String input = "";
        Node node;
        Set<Clause> clauses = new HashSet<>();
        Parser p = new Parser();
        BeliefBase bb = new BeliefBase();
        bbController kbC = new bbController();

        System.out.println("You have the following options to choose from:\n1. View the current belief base\n2. Add new belief(s) to the belief base" +
                "\n3. Remove a belief from the belief base" +
                "\n4. Check if the belief base entails a given belief\nEnter 0 to exit the program");

        do {
            option = myObj.nextLine();

            switch (option) {
                case "1":
                    System.out.println(bb.getClauses().toString());
                    break;
                case "2":
                    System.out.println("Enter new belief(s) (e.g. '(a or b)')");
                    bb.tell(myObj.nextLine());
                    System.out.println("Belief base now contains: " + bb.toString());
                    break;
                case "3":
                    System.out.println("Enter belief(s) to remove from the belief set");
                    input = myObj.nextLine();
                    node = p.parseString(input);
                    clauses.addAll(p.parseNode(node));
                    for(Clause c : clauses) {
                        kbC.contractionBB(bb, c);
                    }
                    System.out.println("Belief base now contains: " + bb.toString());
                    break;
                case "4":
                    System.out.println("Enter a belief and check whether it is entailed by the belief base");
                    input = myObj.nextLine();
                    node = p.parseString(input);
                    clauses.addAll(p.parseNode(node));
                    boolean result = true;
                    for(Clause c : clauses) {
                        if(kbC.plResolution(bb.getClauses(), c)) {
                            result = false;
                            break;
                        }
                    }
                    if(result)
                        System.out.println(input + " is entailed by the belief base");
                    else
                        System.out.println(input + " is not entailed by the belief base");
                    break;
                case "0":
                    System.exit(0);
                default:
                    System.out.println("Invalid input. Try again.");
                    break;
            }

            clauses.clear();

        } while (!input.equals("0"));

    }


}
