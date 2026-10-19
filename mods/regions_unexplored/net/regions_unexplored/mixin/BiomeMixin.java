package net.regions_unexplored.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.BiomeSpecialEffects.GrassColorModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Biome.class)
public class BiomeMixin {
   @ModifyReturnValue(method = "shouldSnow(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z", at = @At("RETURN"))
   private boolean removeTundraSnow(boolean shouldSnow, LevelReader level, BlockPos pos) {
      return shouldSnow && ((Biome)this).hasPrecipitation();
   }

   @ModifyReturnValue(method = "getGrassColor(DD)I", at = @At("RETURN"))
   private int removeTundraSnow(int color, double x, double z) {
      Biome $this = (Biome)this;
      BiomeSpecialEffects effects = $this.getSpecialEffects();
      if (effects.getFoliageColorOverride().orElse(0) == 8438124 && effects.getGrassColorModifier().equals(GrassColorModifier.SWAMP)) {
         double groundValue = Biome.BIOME_INFO_NOISE.getValue(x * 0.01, z * 0.01, false);
         return groundValue < -0.7 ? 6518332 : (groundValue < 0.3 ? 7898949 : 10659922);
      } else {
         return color;
      }
   }
}
