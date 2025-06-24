import java.io.IOException;
import java.util.Scanner;

public class TextUI {
    public static void main(String [] args) throws IOException {
        Scanner input = new Scanner(System.in);

        String choice = "";

        System.out.println("Welcome to the Sabrina Carpenter Database!\n");

        while (!choice.equals("X")) {
            showChoices();

            if (!input.hasNext()) {
                choice = "X";
            } else {
                choice = input.next().toUpperCase();
            }


            if (choice.equals("A")) {
                //upload
            } else if(choice.equals("B")) {
                //remove
            } else if(choice.equals("C")) {
                //search songs with keyword
            } else if(choice.equals("D")) {
                //Direct search for one song with key
            } else if(choice.equals("E")) {
                //Update
            } else if(choice.equals("F")) {
                //Show statistics
            } else {
                System.out.println("\nInvalid menu option. Please enter A-F or X to exit.\n");
            }
        }
    }

    public static void showChoices() {
        System.out.println("Select from one of the following choices:");
        System.out.println("A: Upload a new song to database");
        System.out.println("B: Remove a song from database");
        System.out.println("C: Search songs with a keyword");
        System.out.println("D: Direct search for one song with key");
        System.out.println("E: Update or modify a song entry");
        System.out.println("F: Show statistics");
        System.out.println("X: Quit program\n");
        System.out.print("Enter your choice: ");
    }

}