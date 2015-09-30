/*
 * google code jam 2015 round 1a problem b "Haircut"
 * https://code.google.com/codejam/contest/4224486/dashboard#s=p1&a=1
 */
package haircut;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author dominikschmidtlein
 */
public class Haircut {
    
    public static boolean NEXT = false;
    
    public static void main(String[] args) {
        Scanner scanner = null;
        String output = "";
        try {
            scanner = new Scanner(new File("C:\\Users\\dominikschmidtlein\\Downloads\\B-small-practice.in"));
            //scanner = new Scanner(System.in);
        } catch (Exception e) {}
        int numberOfCases = Integer.parseInt(scanner.nextLine());
        for(int i = 1; i <= numberOfCases; i++){
            int[] people = toIntegerArray(scanner.nextLine());
            int[] barberSpeeds = toIntegerArray(scanner.nextLine());
            int numberOfBarbers = people[0];
            int positionInLine = people[1];  
            
            
            int lcm = lcm(toList(barberSpeeds));
            int div = countOccurenceInLCM(lcm, barberSpeeds);
            positionInLine -= 1;
            positionInLine = positionInLine % div;
            positionInLine += 1;
            if(positionInLine <= numberOfBarbers){                    
                output += "Case #" + i + ": " + positionInLine + "\n";
                System.out.println("Case #" + i + ": " + positionInLine);
                continue;
            }
            positionInLine -= numberOfBarbers;
            
            for(int count = 1; ; count++){
                for(int j = 1; j <= barberSpeeds.length; j++){
                    if(count % barberSpeeds[j - 1] == 0){
                        positionInLine -=1;
                        if(positionInLine == 0){
                            output += "Case #" + i + ": " + j + "\n";
                            System.out.println("Case #" + i + ": " + j);
                            NEXT = true;
                            break;
                        }
                    }
                }
                if(NEXT){
                    NEXT = false;
                    break;
                }
            }
        }
        
        File out = new File("M:\\B-small-practice.out");
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(out));
            bw.write(output);
            bw.close();
        } catch (Exception e) {}    
    }
    
    private static int[] toIntegerArray(String s){
        String[] ss = s.split(" ");
        int[] ns = new int[ss.length];
        for(int i = 0; i < ss.length; i++)
            ns[i] = Integer.parseInt(ss[i]);        
        return ns;
    }
    
    private static int lcm(ArrayList<Integer> a){
        if(a.size() == 2)
            return (int) 1.0 * a.get(0) / gcd(a.get(0), a.get(1)) * a.get(1);
        ArrayList<Integer> b = new ArrayList<>();
        b.add(a.get(0));
        b.add(lcm(new ArrayList<Integer>(a.subList(1, a.size()))));
        return lcm(b);
    }
    
    private static int gcd(int a, int b){        
        return b == 0 ? a : gcd(b, a%b);
    }
    
    private static ArrayList<Integer> toList(int[] a){
        ArrayList<Integer> l = new ArrayList<>();
        for(int i: a)
            l.add(i);
        return l;
    }
    
    private static int countOccurenceInLCM(int lcm, int[] a){
        int count = 0;
        for(Integer i: a)
            count += lcm/i;
        return count;
    }
}
