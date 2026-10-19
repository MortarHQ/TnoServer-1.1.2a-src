package twilightforest.world.components;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.biome.Biome;
import twilightforest.beanification.Component;
import twilightforest.util.ColorUtil;
import twilightforest.util.landmarks.LegacyLandmarkPlacements;

@Component
public class BiomeColorAlgorithms {
   public int enchanted(int originalColor, int x, int z) {
      BlockPos center = LegacyLandmarkPlacements.getNearestCenterXZ(x / 16, z / 16);
      int cx = center.getX();
      int cz = center.getZ();
      int dist = (int)Mth.sqrt((cx - x) * (cx - x) + (cz - z) * (cz - z));
      int color = dist * 16;
      color %= 512;
      if (color > 255) {
         color = 511 - color;
      }

      color = 255 - color;
      return (originalColor & 16776960) + color;
   }

   public int swamp(BiomeColorAlgorithms.Type modifierType) {
      int modifiedColor = switch (modifierType) {
         case Grass -> GrassColor.get(0.8F, 0.9F);
         case Foliage -> FoliageColor.get(0.8F, 0.9F);
      };
      return ((modifiedColor & 16711422) + 5115470) / 2;
   }

   public int darkForest(BiomeColorAlgorithms.Type modifierType) {
      int modifiedColor = switch (modifierType) {
         case Grass -> GrassColor.get(0.7F, 0.8F);
         case Foliage -> FoliageColor.get(0.7F, 0.8F);
      };
      return ((modifiedColor & 16711422) + 1969742) / 2;
   }

   public int darkForestCenterGrass(double x, double z) {
      double noise = Biome.TEMPERATURE_NOISE.getValue(x * 0.0225, z * 0.0225, false);
      return noise < -0.2 ? 6714688 : 5587220;
   }

   public int darkForestCenterFoliage(double x, double z) {
      double noise = (Biome.TEMPERATURE_NOISE.getValue(x * 0.0225, z * 0.0225, false) + 1.0) / 2.0;
      return noise < -0.1 ? 16351774 : 15289876;
   }

   public double spookyNoise(double x, double z) {
      return (Biome.TEMPERATURE_NOISE.getValue(x * 0.0225, z * 0.0225, false) + 1.0) / 2.0;
   }

   public int spookyGrass(double x, double z) {
      double noise = this.spookyNoise(x, z);
      return ColorUtil.blendColors(12858147, 6013987, noise > 0.6 ? noise * 0.1 : noise);
   }

   public int spookyFoliage(double x, double z) {
      double noise = this.spookyNoise(x, z);
      return ColorUtil.blendColors(16711937, 4849409, noise > 0.6 ? noise * 0.2 : noise);
   }

   public enum Type {
      Grass,
      Foliage;
   }
}
