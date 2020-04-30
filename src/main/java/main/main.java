package main;

import java.util.Scanner;
import parsing.types.Paser;

public class main {

    public static void main(String[] args) {

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter propositions");

        String proposition = myObj.nextLine();  // Read user input
        System.out.println("Proposition is : " + proposition);  // Output user input

        Paser p = new Paser();

        p.parseString(proposition);



    }

}
