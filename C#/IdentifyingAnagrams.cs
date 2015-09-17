//Dominik Schmidtlein May 22, 2015

/*
 * Two words are anagrams if they consist of the same letters, with the same number of occurrences,
 * in a different order. For instance, DEPOSIT and DOPIEST are anagrams (aren’t you glad you know that),
 * and OPTS, POTS, TOPS and STOP form an anagram class.
 * 
 * Your task is to write a program that takes two strings as input and determines whether or not they
 * are anagrams; you may assume that the strings consist of only the letters A through Z in upper case. 
 * You must provide at least two different algorithms that work in fundamentally different ways. When
 * you are finished, you are welcome to read or run a suggested solution, or to post your own solution 
 * or discuss the exercise in the comments below.
 */

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace IdentifyingAnagrams
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.Write("First word: ");
            var s1 = Console.ReadLine();
            Console.Write("Second word: ");
            var s2 = Console.ReadLine();
            s1 = s1.ToUpper();
            s2 = s2.ToUpper();
            Console.WriteLine("Sort: " + (Anagrams_Sort(s1, s2) ? "Anagrams" : "Not anagrams"));
            Console.WriteLine("Recursive: " + (Anagrams_Recursive(s1, s2) ? "Anagrams" : "Not anagrams"));
            Console.ReadLine();
        }

        public static bool Anagrams_Recursive(string s1, string s2)
        {
            if (s1.Length == 0 || s2.Length == 0)
            {
                if (s1.Equals(s2))
                    return true;
                return false;
            }
            if (s2.Contains(s1[0]))
            {
                s2 = s2.RemoveChar(s1[0]);
                s1 = s1.RemoveChar(s1[0]);
                return Anagrams_Recursive(s1, s2);
            }
            return false;
        }

        public static bool Anagrams_Sort(string s1, string s2)
        {
            s1 = s1.Sort();
            s2 = s2.Sort();
            return s1.Equals(s2);
        }
    }

    public static class MyExtensions
    {
        public static string CreateString(this char[] a)
        {
            var s = "";
            for (int i = 0; i < a.Length; i++)
                s += a[i];
            return s;
        }

        public static string Sort(this String str)
        {
            var a = str.ToCharArray();
            Array.Sort<char>(a);
            return a.CreateString();
        }

        public static string RemoveChar(this string s, char c)
        {
            int i = s.IndexOf(c);
            return s.Substring(0, i) + s.Substring(i + 1, s.Length - i - 1);
        }
    }
}
