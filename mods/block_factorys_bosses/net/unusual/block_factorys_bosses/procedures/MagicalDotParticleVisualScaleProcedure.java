package net.unusual.block_factorys_bosses.procedures;

public class MagicalDotParticleVisualScaleProcedure {
   public static double execute(double age) {
      return (age <= 20.0 ? 1.0 : (60.0 - age) / 40.0) * 8.0;
   }
}
