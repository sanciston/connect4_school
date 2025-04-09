/**
 * CONNECT FOUR
 *     
 * Author: Brendan Laking
 * File Name: Main.java
 * Modified: 2025.04.09
 * 
 * A basic command line Connect Four-like game.
 */

import Tile.*;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {    
        
        System.out.print("\u000C"); //Clears the screnn
        
        Tiles tiles = new Tiles( //generates the board
            getIntFromIn("Enter the board width: (7 is recommended) ", 4, 20),
            getIntFromIn("Enter the board height: (6 is recommended) ", 4, 20)); 
            
        final int streak = getIntFromIn("How many tiles in a row should the player have to get to win: (4 is recommended) ", 2, tiles.width); 
            
        System.out.print("\u000C");

        
        
        TileType nextTurn = TileType.O;
        TileType turn = TileType.O;

        final int boardSize = 2; //The total size of the board, has nothing to do with tiles

        
        int dropX;
        
        //Main loop
        while(true) {
            System.out.println(tiles.generateBoardHeader(boardSize * 2, boardSize) + tiles.generateBoardString(boardSize * 2, boardSize)); //Generate the board visual and print it 
            turn = nextTurn; //Sets what tile will be placed
            
            if(turn == TileType.O) {
                System.out.println("O Turn");
                dropX = getIntFromIn("Where do you want to drop the token?", 1, tiles.width); 
                nextTurn = TileType.X; //Sets what tile will be placed next turn
            } else {
                System.out.println("X Turn");
                dropX = getIntFromIn("Where do you want to drop the token?", 1, tiles.width);
                nextTurn = TileType.O;
            }
            
            
            System.out.print("\u000C");
            
            if(!tiles.dropTile(dropX, turn)) { //Drop tile drops the tile. It returns true if the placement was valid and false if it was invalid.
                System.out.println("Invalid placement");
            } else {
                if(tiles.hasWon(dropX - 1, tiles.dropY - 1, streak, turn)) { //Has won checks if the game has been won
                    if(turn == TileType.X) { //We know that whoever placed the last tile would have been the winner 
                        System.out.println("X WON");     
                        break;
                    } else {
                        System.out.println("O WON!");
                        break;
                    }
                }
            }
            

        }


    }
    
    //Gets an int from user input or System.in
    public static int getIntFromIn(String print, int minimum, int maximum) {
        String errors; 
        
        boolean hasEnteredNumber = false; //Has entered number will only be true once the player has entered a proper valid number
        
        int n = 0; //The number taken from the console
        
        while(!hasEnteredNumber) {
            
            boolean wasSuccessful = true; //Reads wheter the string is a valid number
            
            Scanner reader = new Scanner(System.in); // Reading from System.in (The console)

            System.out.println(print); //Prints out the question
        
            try { //Try and read the string as an int
                n = reader.nextInt();  
            } catch(Exception e) { //If not, catch the error and ask again
                System.out.println("Not a number");
                hasEnteredNumber = false;
                wasSuccessful = false;
            }
            
            if(n < minimum) {
                System.out.println("Number too small");
                hasEnteredNumber = false;
                wasSuccessful = false;
            }
                       
            if(n > maximum) {
                System.out.println("Number too big");
                hasEnteredNumber = false;
                wasSuccessful = false;
            }
            
            if(wasSuccessful == true ) {
                hasEnteredNumber = true;
            }
            
            reader.close(); //CLose the reader
        }
        
        return n; //returns n
    }
}