package Tile;
/**
 * The 2D array for tiles and associated methods 
 *
 * Author: Brendan Laking
 * File: Tiles.java
 * Modified: 2025.04.09
 */

public class Tiles {
    public TileType[][] type;
    public int width;
    public int height;
    public int dropY;
    
    public Tiles(int width, int height) {
        this.type = new TileType[width][height];
        this.width = width;
        this.height = height;
        this.dropY = 0;
        
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                this.type[x][y] = TileType.EMPTY;
            }
        }
    }
    
    public String generateBoardString(int spacingX, int spacingY) {
        String boardString = "";
        
        for(int y = 0; y < this.height; y++) {
            for(int x = 0; x < this.width; x++) {
                switch(this.type[x][y]) {
                    case EMPTY -> {
                        boardString += ".";
        
                    } case X -> {
                        boardString += "X";
                    } case O -> {
                        boardString += "0";
                    }
                }
                for(int i = 0; i < spacingX; i++) {
                    boardString += " ";
                }
            }   
            for(int i = 0; i < spacingY; i++) {
                boardString += "\n";
            }
        }
        return boardString;
    }
    
    public String generateBoardHeader(int spacingX, int spacingY) {
        String headerString = "";
        
        for(int x = 0; x < this.width; x++) {
            String number = Integer.toString(x + 1);
            headerString += number;
            
            for(int i = 0; i < spacingX - (number.length() - 1); i++) {
                headerString += " ";
            }
        }
        
        for(int i = 0; i < spacingY; i++) {
            headerString += "\n";
        }
        return headerString;        
    }
    
    public boolean dropTile(int tileX, TileType tileType) {
        boolean tileInPlace = false;
        int tileY = 0;
        
        if(tileX > 0) {
            if(this.type[tileX - 1][0] != TileType.EMPTY) {
                return false;
            }
        } else {
            return false;
        }
        
        while(!tileInPlace) {
            if(tileY < this.height - 1) {
                if(this.type[tileX - 1][tileY + 1] != TileType.EMPTY) {
                    tileInPlace = true;            
                }
            }
            
            if(tileY == this.height - 1) {
                tileInPlace = true;
            }
            tileY++;
        }
        this.dropY = tileY;
        
        this.type[tileX - 1][tileY - 1] = tileType; 
        return true;
    }
    
    //Check wno has won by going through the tiles.
    public Boolean hasWon(int placedX, int placedY, int streakLength, TileType type) {
        int offsetX;
        int offsetY; //The distance from the placed tile to start checking from 
        int currentStreak = 0; //How many tiles in a row to check
        int checkX = 0;
        int checkY = 0;
        
        //Check the tiles horizontaly 
        
        if(placedX - streakLength - 1 >= 0) {
            offsetX = streakLength - 1; // If the placed X is not too close to the wall and we are able to check streakLength back
        } else {
            offsetX = placedX; // If the placed X is too close to wall and we aren't able to check streakLength back, check the distance from placedX to 0 (placedX - 0 or just placedX)
        }
        
        for(int i = 0; i < offsetX + streakLength; i++) {
            checkX = (placedX - offsetX) + i; //Calculate where to check from. First calculate the starting position (placedX - offset) and add i, which increments with subsequent loops.
            
            if (checkX >= this.width) { //once checkX gets out of bounds, break
                break;
            }
            
            if(this.type[checkX][placedY] == type) {
                currentStreak++; //if the type that we are checking equals type we increment the streak.
            } else {
                currentStreak = 0; //Reset currentStreak if the streak is broken
            }
            if(currentStreak >= streakLength) {
                return true; //Once the streak reaches a large enough point to where a player has won, return true, which triggers a win state
            }
        }
        
        //check the tiles veriticaly
        //The same procedure as above
        
        if(placedY - streakLength - 1 >= 0) {
            offsetY = streakLength - 1;
        } else {
            offsetY = placedY;
        }
        
        for(int i = 0; i < offsetY + streakLength; i++) {
            checkY = (placedY - (offsetY)) + i;
            
            if (checkY >= this.height) {
                break;
            }

            if(this.type[placedX][checkY] == type) {
                currentStreak++;
            } else {
                currentStreak = 0;
            }
            if(currentStreak >= streakLength) {
                return true;
            }
        }
        
        int offset;        
        if(offsetY > offsetX) {
            offset = offsetX;
        } else {
            offset = offsetY;
        }
        
        currentStreak = 0;
        
        for(int i = 0; i < offset + streakLength; i++) {
            checkY = (placedY - offset) + i;
            checkX = (placedX - offset) + i;
                                    
            if (checkY >= this.height || checkX >= this.width) {
                break;
            }

            if(this.type[checkX][checkY] == type) {
                currentStreak++;
            } else {
                currentStreak = 0;
            }
            if(currentStreak >= streakLength) {
                return true;
            }
        }
        
        if(placedX + streakLength < this.width) {
            offsetX = streakLength - 1; // If the placed X is not too close to the wall and we are able to check streakLength back
        } else {
            offsetX = this.width - placedX - 1; // If the placed X is too close to wall and we aren't able to check streakLength back, check the distance from placedX to 0 (placedX - 0 or just placedX)
        }
        
        if(offsetY > offsetX) {
            offset = offsetX;
        } else {
            offset = offsetY;
        }
        
                
        for(int i = 0; i < offset + streakLength; i++) {
            checkY = (placedY - offset) + i;
            checkX = (placedX + offset) - i;
                                    
            if (checkY >= this.height || checkX <= 0) {
                break;
            }

            if(this.type[checkX][checkY] == type) {
                currentStreak++;
            } else {
                currentStreak = 0;
            }
            if(currentStreak >= streakLength) {
                return true;
            }
        }

        
        return false;
    } 
}