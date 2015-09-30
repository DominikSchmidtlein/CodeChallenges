/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mouseoncheckerboard;


public class MouseOnCheckerBoard {
    public static final int SCREENSIZE = 75;
    public static final int BLACK = 'X';
    public static final int WHITE = ' ';
    
    public static char[][] board = new char[SCREENSIZE][SCREENSIZE];
    public static Mouse mouse;
    public static void main(String[] args) {
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j ++){
                board[i][j] = WHITE;
            }
        }
        
        mouse = new Mouse(new Position(SCREENSIZE/2, SCREENSIZE/2, Position.NORTH));
        
        while(mouse.position.x > 0 && mouse.position.y > 0 && mouse.position.x < 100 && mouse.position.y < 100){
            if(board[mouse.position.x][mouse.position.y] == WHITE){
               board[mouse.position.x][mouse.position.y] = BLACK;
               mouse.position = mouse.turnRight();
            }
            else if(board[mouse.position.x][mouse.position.y] == BLACK){
                board[mouse.position.x][mouse.position.y] = WHITE;
                mouse.position = mouse.turnLeft();
            }
            System.out.print ('\f');
            printBoard();
//            try {
//                Thread.sleep(10);
//            } catch (Exception e) {}
        }
        
    }
    
    public static void printBoard(){
        String s = "";
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j ++){
                if(i == mouse.position.x && j == mouse.position.y){
                    s += '@';
                    continue;
                }
                s += board[i][j];
            }
            s += "\n";
        }
        System.out.println(s);
    }
    
}
