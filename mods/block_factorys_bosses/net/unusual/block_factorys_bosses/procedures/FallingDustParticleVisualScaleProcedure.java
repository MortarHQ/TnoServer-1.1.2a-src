package net.unusual.block_factorys_bosses.procedures;

public class FallingDustParticleVisualScaleProcedure {
   public static double execute(double age) {
      return (60.0 - age) / 60.0 * 1.2;
   }
}
