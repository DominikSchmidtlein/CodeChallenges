//Dominik Schmidtlein May 22, 2015

/*
 * Five sailors are shipwrecked on a desert island. They quickly determine that the only other 
 * inhabitant of the island is a monkey and that the only food is coconuts. They set about 
 * collecting as many coconuts as they can and put them all in a pile. By nightfall they are 
 * too tired to divide the harvest; so they agree to go to sleep and divvy up the coconuts the 
 * next morning.
 * 
 * During the night one sailor awakens, suspicious that the others might try to cheat him, and 
 * desides to take his portion then and there and not wait until morning. He divides the coconuts 
 * into five piles and finds there is one coconut left over, which he gives to the monkey. He 
 * hides one of the five piles, then puts the rest of the nuts together and returns to sleep. 
 * About an hour later a second sailor awakens with the same suspicions and does the same thing: 
 * He divides the coconuts into five piles, leaving one extra, which he gives to the monkey. Then
 * he hides what he thinks is his share and goes back to sleep.
 * 
 * One after another the rest of the sailors do the same: they each take one fifth of the coconuts
 * in the pile (after giving the extra one to the monkey) and then return to sleep.
 * 
 * When the sailors awaken the next morning they all notice the coconut pile is much smaller than
 * it was the night before, but since each man is as guilty as the others, no one says anything. 
 * They divide the coconuts (for the sixth time), but this time there is no coconut left for the 
 * monkey.
 * 
 * How many coconuts were in the original pile?
 */

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Monkeys_and_Coconuts
{
    class MonkeysandCoconuts
    {
        static void Main(string[] args)
        {
            for (int sailors = 3; sailors < 15; sailors++)
            {
                double nuts = 1;
                while (!DivideCoconuts(sailors, sailors, nuts))
                    nuts += 1;

                Console.WriteLine(sailors + " : " + nuts);
            }
            Console.ReadLine();

        }
        //returns true if the remaining nuts can be divided evenly by the number of sailors
        public static bool DivideCoconuts(int n, int sailors, double nuts)
        {
            if (n == 0)
            {
                if (nuts == 0)
                    return false;
                return nuts % sailors == 0;
            }
            return DivideCoconuts(n - 1, sailors, (nuts - 1) * (sailors - 1) / sailors);

        }
    }
}
