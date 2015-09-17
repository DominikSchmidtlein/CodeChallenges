//Dominik Schmidtlein May 21, 2015

/*
 * Generate the pairs of cartesian coordinates within a square bounded by (1,1)
 * and (n,n) ordered by their product in ascending order. For instance, when n
 * is 3, the coordinates are (1,1), (1,2), (2,1), (1,3), (3,1), (2,2), (2,3), 
 * (3,2) and (3,3). Can you find a solution with time complexity better than O(n2)?
 */

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace OrderedCoordinates
{
    public class OrderedCoordinates
    {
        static void Main(string[] args)
        {
        Start:
            List<Coordinate> coordinates = new List<Coordinate>();
            Console.Write("Max coordinate: ");
            var max = int.Parse(Console.ReadLine());
            for (int i = 1; i <= Math.Pow(max, 2); i++)
            {
                var c = OrderedCoordinates.GetCoordinates(max, i);
                coordinates.AddRange(c);
            }
            OrderedCoordinates.Display(coordinates);
            Console.ReadLine();
            goto Start;

        }

        public static List<Coordinate> GetCoordinates(int max, int product)
        {
            var coordinates = new List<Coordinate>();
            /*
            int j = product / max;
            if (product % max != 0)
                j += 1;
            */
            int j = (product % max != 0) ? product / max + 1 : product / max;

            for (int i = j; i <= max; i++)
                if (product % i == 0)
                    coordinates.Add(new Coordinate { x = i, y = product / i });
            return coordinates;
        }

        public static void Display(List<Coordinate> c)
        {
            int product = 1;
            foreach (var item in c)
            {
                if (item.x * item.y != product)
                    Console.WriteLine();
                product = item.x * item.y;
                Console.Write("({0},{1}), ", item.x, item.y);
                
            }
        }
    }

    public struct Coordinate
    {
        public int x;
        public int y;
    }
}
