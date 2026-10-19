package zank.mods.efficient_hashing;

public class PhiMix {
   public static final int PHI = -1640531527;

   public static int mix(int x) {
      int y = x * -1640531527;
      return y ^ y >>> 16;
   }
}
