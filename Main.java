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
        
        System.out.print("\u000C");
        
        Tiles tiles = new Tiles( //generates the board
            getIntFromIn("Enter the board width: (7 is recommended) ", 4, 20),
            getIntFromIn("Enter the board height: (6 is recommended) ", 4, 20)); 
            
        System.out.print("\u000C");

        
        int boardSize = 2;    
        
        TileType nextTurn = TileType.O;
        TileType turn = TileType.O;
        
        int dropX;
        
        while(true) {
            System.out.println(tiles.generateBoardHeader(boardSize * 2, boardSize) + tiles.generateBoardString(boardSize * 2, boardSize)); //Generate the board visual and print it 
            turn = nextTurn;
            
            if(turn == TileType.O) {
                System.out.println("O Turn");
                dropX = getIntFromIn("Where do you want to drop the token?", 1, tiles.width);
                nextTurn = TileType.X;
            } else {
                System.out.println("X Turn");
                dropX = getIntFromIn("Where do you want to drop the token?", 1, tiles.width);
                nextTurn = TileType.O;
            }
            
            
            System.out.print("\u000C");
            
            if(!tiles.dropTile(dropX, turn)) {
                System.out.println("Invalid placement");
            } else {
                if(tiles.hasWon(dropX - 1, tiles.dropY - 1, 4, turn)) {
                    if(turn == TileType.X) {
                        System.out.println("X WON");         
                    } else {
                        System.out.println("O WON!");
                    }
                }
            }
            

        }


    }
    
    public static int getIntFromIn(String print, int minimum, int maximum) {
        String errors; 
        
        boolean hasEnteredNumber = false;
        
        int n = 0;
        
        while(!hasEnteredNumber) {
            
            boolean wasSuccessful = true;
            
            Scanner reader = new Scanner(System.in); // Reading from System.in

            System.out.println(print);
        
            try {
                n = reader.nextInt(); 
            } catch(Exception e) {
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
            
            reader.close();
        }
        
        return n;
    }
}