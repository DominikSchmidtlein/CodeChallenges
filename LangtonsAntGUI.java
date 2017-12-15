/*
 * Dominik Schmidtlein 1/7/2016
 * 
 * The purpose of the Langton's Ant simulation is to observe how Langton's Ant moves
 * given different starting setups.
 * Langton's Ant follows a few simple steps when moving:
 * -Ant will move every round
 * -if position is on a black square, turn 90 degrees left
 * -if position is on a white square, turn 90 degrees right
 *
 * The interesting observation is that Langton's Ant always eventually moves in a repetitive column.
 */
package LangtonsAntGUI;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.*;

public class LangtonsAntGUI {

    public static final int SCREENSIZE = 600;
    public static final int NUMBEROFPOSITIONS = 100;
    public static final int BLACK = 'X';
    public static final int WHITE = ' ';

    public static char[][] board = new char[NUMBEROFPOSITIONS][NUMBEROFPOSITIONS];
    public static Ant ant;

    public static void main(String[] args) {

        JFrame GUIBoard = new JFrame();
        GUIBoard.setSize(SCREENSIZE, SCREENSIZE);
        GUIBoard.setTitle("Langton's Ant GUI");
        GUIBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GUIBoard.setLocationRelativeTo(null);

        Container pane = GUIBoard.getContentPane();
        pane.setLayout(new GridLayout(NUMBEROFPOSITIONS, NUMBEROFPOSITIONS));

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = WHITE;
                JPanel panel = new JPanel();
                panel.setPreferredSize(new Dimension(SCREENSIZE / NUMBEROFPOSITIONS, SCREENSIZE / NUMBEROFPOSITIONS));
                panel.setBackground(Color.white);
                pane.add(panel);
            }
        }
        GUIBoard.setVisible(true);

        ant = new Ant(new Position(NUMBEROFPOSITIONS / 2, NUMBEROFPOSITIONS / 2, Position.SOUTH));
        while (ant.position.x > 0 && ant.position.y > 0 && ant.position.x < NUMBEROFPOSITIONS && ant.position.y < NUMBEROFPOSITIONS) {
            if (board[ant.position.x][ant.position.y] == WHITE) {
                board[ant.position.x][ant.position.y] = BLACK;
                pane.getComponent(ant.position.y * NUMBEROFPOSITIONS + ant.position.x).setBackground(Color.black);
                ant.position = ant.turnRight();
            } else if (board[ant.position.x][ant.position.y] == BLACK) {
                board[ant.position.x][ant.position.y] = WHITE;
                pane.getComponent(ant.position.y * NUMBEROFPOSITIONS + ant.position.x).setBackground(Color.white);
                ant.position = ant.turnLeft();
            }
            pane.getComponent(ant.position.y * NUMBEROFPOSITIONS + ant.position.x).setBackground(Color.red);
            try {
                Thread.sleep(1);
            } catch (Exception e) {}
        }

    }

}
