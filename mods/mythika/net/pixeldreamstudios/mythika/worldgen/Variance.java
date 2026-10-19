package net.pixeldreamstudios.mythika.worldgen;

final class Variance {
   private Variance() {
   }

   static int at(int a, int b, int c, int salt) {
      int h = a * 522133279 ^ c * 625341585 ^ b * -1640531527 ^ salt * -2048144789;
      h ^= h >>> 15;
      h *= 739982445;
      h ^= h >>> 13;
      return h & 2147483647;
   }
}
