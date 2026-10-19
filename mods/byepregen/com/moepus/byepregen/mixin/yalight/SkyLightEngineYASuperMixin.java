package com.moepus.byepregen.mixin.yalight;

import com.moepus.byepregen.yalight.YASkyLightEngine;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.lighting.ChunkSkyLightSources;
import net.minecraft.world.level.lighting.SkyLightEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = SkyLightEngine.class, remap = false)
public abstract class SkyLightEngineYASuperMixin {
   @Unique
   private static final MutableBlockPos byepregen$dummyMutablePos = new MutableBlockPos();
   @Unique
   private static ChunkSkyLightSources byepregen$dummyEmptySources;

   @Redirect(
      method = "<init>(Lnet/minecraft/world/level/chunk/LightChunkGetter;Lnet/minecraft/world/level/lighting/SkyLightSectionStorage;)V",
      at = @At(value = "NEW", target = "()Lnet/minecraft/core/BlockPos$MutableBlockPos;")
   )
   private MutableBlockPos byepregen$skipMutablePos() {
      return this instanceof YASkyLightEngine ? byepregen$dummyMutablePos : new MutableBlockPos();
   }

   @Redirect(
      method = "<init>(Lnet/minecraft/world/level/chunk/LightChunkGetter;Lnet/minecraft/world/level/lighting/SkyLightSectionStorage;)V",
      at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/LevelHeightAccessor;)Lnet/minecraft/world/level/lighting/ChunkSkyLightSources;")
   )
   private ChunkSkyLightSources byepregen$skipEmptySources(LevelHeightAccessor level) {
      return this instanceof YASkyLightEngine ? byepregen$dummyEmptySources(level) : new ChunkSkyLightSources(level);
   }

   @Unique
   private static synchronized ChunkSkyLightSources byepregen$dummyEmptySources(LevelHeightAccessor level) {
      if (byepregen$dummyEmptySources == null) {
         byepregen$dummyEmptySources = new ChunkSkyLightSources(level);
      }

      return byepregen$dummyEmptySources;
   }
}
