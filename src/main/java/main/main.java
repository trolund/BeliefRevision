package main;

import java.util.Scanner;

import parsing.types.ComplexSentence;
import parsing.types.Literal;
import parsing.types.Paser;
import parsing.types.Sentence;

public class main {

    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter propositions");

        String proposition = myObj.nextLine();  // Read user input
        System.out.println("Proposition is : " + proposition);  // Output user input

        Paser p = new Paser();

        ComplexSentence result = p.parseString(proposition);

        System.out.println(result.toString());

        for (Sentence s : result.getSimplerSentences()) {
            for (Literal l : s.getLiterals()) {

            }
        }

    }

}
