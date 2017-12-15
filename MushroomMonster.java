/*
 * Practice problem from google code jam
 * https://code.google.com/codejam/contest/4224486/dashboard
 */
package mushroommonster;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 *
 * @author dominik
 */
public class MushroomMonster {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("/home/dominik/Downloads/A-large-practice.in"));
        } catch (Exception e) {}       
        String output = "";
        int numberOfCases = -1;
        numberOfCases = Integer.parseInt(scanner.nextLine());
        for(int i = 0; i < numberOfCases; i++){
            int numberOfPeriods = Integer.parseInt(scanner.nextLine());
            String numberOfMushrooms = scanner.nextLine();
            int[] mushrooms = getNumberOfMushroomsAsIntegers(numberOfMushrooms);
            double method1 = anytimeEatingMinimum(mushrooms);
            double method2 = constantConsumptionMinimum(mushrooms);
            output += "Case #" + (i+1) + ": " + (int)method1 + " " + (int)method2 + "\n";
        }
        File out = new File("/home/dominik/Downloads/A-large-practice.out");
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(out));
            bw.write(output);
            bw.close();
        } catch (Exception e) {}        
    }
    
    private static int[] getNumberOfMushroomsAsIntegers(String numberOfMushrooms){
        String[] numbers = numberOfMushrooms.split(" ");
        int[] integers = new int[numbers.length];
        for(int i = 0; i < numbers.length; i++)
            integers[i] = Integer.parseInt(numbers[i]);
        return integers;
    }
    
    private static double anytimeEatingMinimum(int[] mushrooms){
        double consumedMushrooms = 0;
        for(int i = 0; i < mushrooms.length - 1; i++){
            if(mushrooms[i+1] >= mushrooms[i])
                continue;
            consumedMushrooms += mushrooms[i] - mushrooms[i+1];
        }
        return consumedMushrooms;
    }
    
    private static double constantConsumptionMinimum(int[] mushrooms){
        double consumedMushrooms = 0;
        int maximumConsumed = 0;
        for(int i = 0; i < mushrooms.length - 1; i++)
            if(mushrooms[i] - mushrooms[i+1] > maximumConsumed)
                maximumConsumed = mushrooms[i] - mushrooms[i+1];
        for(int i = 0; i < mushrooms.length - 1; i++){
            if(mushrooms[i] < maximumConsumed)
                consumedMushrooms += mushrooms[i];
            else
                consumedMushrooms += maximumConsumed;
        }
        return consumedMushrooms;
    }
}
