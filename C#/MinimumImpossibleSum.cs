//Dominik Schmidtlein May 21, 2015

/*
 * Given a list of positive integers, find the smallest number that cannot be calculated
 * as the sum of the integers in the list. For instance, given the integers 4, 13, 2, 3 
 * and 1, the smallest number that cannot be calculated as the sum of the integers in the
 * list is 11.
 */

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MinimumImpossibleSum
{
    class MinimumImpossibleSum
    {
        static void Main(string[] args)
        {
            List<int> list = new List<int>() { 4, 13, 2, 3, 1 };
            int sum = 1;
            while (true)
            {
                var v = MinimumImpossibleSum.MinImpossibleSum(sum, new List<int>(), list.ToList());
                if (v == null)
                {
                    break;
                }
                sum++;
            }
            //done
            Console.WriteLine(sum);
            Console.ReadLine();
        }
        // returns the numbers that make the sum, else null
        public static List<int> MinImpossibleSum(int sum, List<int> comp, List<int> rem)
        {
            if (sum < 0)
                return null;
            else if (sum == 0)
                return comp;
            else
            {
                for (int i = 0; i < rem.Count; i++)
                {
                    int item = rem[i];
                    comp.Add(item);
                    rem.Remove(item);
                    List<int> a = MinImpossibleSum(sum - item, comp, rem);
                    if (a != null) //sum could be made
                        return a;
                    comp.Remove(item);
                    rem.Insert(i, item);
                }
                return null;
            }
        }
    }
}
