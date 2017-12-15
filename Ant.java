/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LangtonsAntGUI;


public class Ant {
    
    
    public Position position;
    
    public Ant(Position position){
        this.position = position;
    }
    
    public Position turnRight(){
        switch(position.direction){
                case Position.NORTH:
                    return new Position(position.x + 1, position.y, Position.EAST);
                case Position.EAST:
                    return new Position(position.x, position.y + 1, Position.SOUTH);
                case Position.WEST:
                    return new Position(position.x, position.y - 1, Position.NORTH);
                case Position.SOUTH:
                    return new Position(position.x - 1, position.y, Position.WEST);
                default: return null;
        }
    }
    
    public Position turnLeft(){
        switch(position.direction){
                case Position.NORTH:
                    return new Position(position.x - 1, position.y, Position.WEST);
                case Position.EAST:
                    return new Position(position.x, position.y - 1, Position.NORTH);
                case Position.WEST:
                    return new Position(position.x, position.y + 1, Position.SOUTH);
                case Position.SOUTH:
                    return new Position(position.x + 1, position.y, Position.EAST);
                default: return null;
        }
    }    
}
