/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 *
 * @author dominikschmidtlein
 */
public class Logging {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double[] angles = {0,Math.PI/4,Math.PI/5,Math.PI/2,Math.PI/1,Math.PI/3, Math.PI*3/2};
        
        System.out.println(isGap(0, Math.PI, angles));
        System.out.println(flip(getAngle(new Point(0,0), new Point(-1,0)))/Math.PI *180);
        System.out.println(flip(getAngle(new Point(0,0), new Point(0,-2)))/Math.PI *180);
        
        
        Scanner scanner = null;
        String output = "";
        try {
            scanner = new Scanner(new File("C:\\Users\\dominikschmidtlein\\Downloads\\C-small-practice.in"));
            //scanner = new Scanner(System.in);
        } catch (Exception e) {}
        int numberOfCases = Integer.parseInt(scanner.nextLine());
        for(int i = 1; i <= numberOfCases; i++){
            int numberOfTrees = Integer.parseInt(scanner.nextLine());
            Point[] treeCoordinates = new Point[numberOfTrees];
            for(int j = 0; j < numberOfTrees; j++){
                String[] ss = scanner.nextLine().split(" ");
                treeCoordinates[j] = new Point(Integer.parseInt(ss[0]), Integer.parseInt(ss[1]));
            }
            
            
        }
        File out = new File("M:\\C-small-practice.out");
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(out));
            bw.write(output);
            bw.close();
        } catch (Exception e) {}    
    }
    
    private static Point[] getBoundaryPoints(int index, Point[] set){
        Point p1 = set[index - 1];
        double[] angles = new double[set.length];
        for(int i = 0; i < set.length; i++){
            if(i == index)
                continue;
            Point p2 = set[i];
            angles[i] = getAngle(p1, p2);
        }
        
        return null;
    }
    
    private static double getAngle(Point originPoint, Point cornerPoint){
        Point rightAnglePoint = new Point(cornerPoint.x, originPoint.y);
        double adjacentMagnitude = rightAnglePoint.x - originPoint.x;
        double hypotenuseMagnitude = Math.sqrt(Math.pow(cornerPoint.x - originPoint.x, 2) + Math.pow(cornerPoint.y - originPoint.y, 2));
        double angle = Math.acos(adjacentMagnitude / hypotenuseMagnitude);
        if(cornerPoint.y < originPoint.y)
            angle += (Math.PI - angle) * 2;
        return angle;
        
    }
    
    private static boolean isGap(double index, double gap, double[] angles){
        if(index >= Math.PI)
            return false;
        boolean topIsBlocked = false;
        boolean botIsBlocked = false;
        for(int i = 0; i < angles.length; i ++){
            if(!topIsBlocked && angles[i] > index && angles[i] < index + gap)
                topIsBlocked = true;
            if(!botIsBlocked && flip(angles[i]) < flip(index) && flip(angles[i]) > flip(index + gap))
                botIsBlocked = true;
            if(topIsBlocked && botIsBlocked)
                return isGap(index + Math.PI/180, gap, angles);
        }
        return true;
    }
    
    private static double flip(double angle){
        angle += Math.PI;
        if(angle >= 2*Math.PI)
            angle -= 2*Math.PI;
        return angle;
    }
}
