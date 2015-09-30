/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mouseoncheckerboard;


public class Position {
    public static final int NORTH = 1;
    public static final int EAST = 2;
    public static final int SOUTH = 3;
    public static final int WEST = 4;
    
    public int x;
    public int y;
    public int direction;
    
    public Position(int x, int y, int direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
}
