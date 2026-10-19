package com.tensura_tno.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "com.github.wal_bos.moreskills.client.fx.SatellaVfxClient$BlackMiasma", remap = false)
public abstract class MoreSkillsSatellaMiasmaSurfaceOptimizationMixin {
   @Unique
   private static final int TENSURA_TNO$CACHE_SIZE = 4096;
   @Unique
   private final long[] tensuraTno$surfaceKeys = new long[4096];
   @Unique
   private final double[] tensuraTno$surfaceValues = new double[4096];
   @Unique
   private final long[] tensuraTno$surfaceTimes = new long[4096];
   @Unique
   private final boolean[] tensuraTno$surfaceValid = new boolean[4096];
   @Unique
   private final MutableBlockPos tensuraTno$surfaceCursor = new MutableBlockPos();

   @Inject(method = "sampleSurfaceY(DDD)D", at = @At("HEAD"), cancellable = true, require = 0, remap = false)
   private void tensuraTno$sampleSurfaceOnce(double x, double z, double yHint, CallbackInfoReturnable<Double> cir) {
      int ix = Mth.floor(x);
      int iz = Mth.floor(z);
      ClientLevel level = Minecraft.getInstance().level;
      if (level != null) {
         long key = (long)ix << 32 ^ iz & 4294967295L;
         int slot = this.tensuraTno$surfaceSlot(key);
         long gameTime = level.getGameTime();
         long cachedTime = this.tensuraTno$surfaceTimes[slot];
         if (this.tensuraTno$surfaceValid[slot] && this.tensuraTno$surfaceKeys[slot] == key && gameTime >= cachedTime && gameTime - cachedTime <= 4L) {
            cir.setReturnValue(this.tensuraTno$surfaceValues[slot]);
         } else {
            int startY = Mth.floor(yHint) + 14;
            int endY = Mth.floor(yHint) - 18;
            MutableBlockPos cursor = this.tensuraTno$surfaceCursor;
            cursor.set(ix, startY + 1, iz);
            boolean aboveAir = level.getBlockState(cursor).isAir();
            double result = yHint + 0.005;

            for (int y = startY; y >= endY; y--) {
               cursor.set(ix, y, iz);
               boolean currentAir = level.getBlockState(cursor).isAir();
               if (!currentAir && aboveAir) {
                  result = y + 1.005;
                  break;
               }

               aboveAir = currentAir;
            }

            this.tensuraTno$surfaceKeys[slot] = key;
            this.tensuraTno$surfaceValues[slot] = result;
            this.tensuraTno$surfaceTimes[slot] = gameTime;
            this.tensuraTno$surfaceValid[slot] = true;
            cir.setReturnValue(result);
         }
      }
   }

   @Unique
   private int tensuraTno$surfaceSlot(long key) {
      long hash = key;
      hash ^= hash >>> 33;
      hash *= -49064778989728563L;
      hash ^= hash >>> 33;
      hash *= -4265267296055464877L;
      hash ^= hash >>> 33;
      int first = (int)hash & 4095;
      int slot = first;

      while (this.tensuraTno$surfaceValid[slot] && this.tensuraTno$surfaceKeys[slot] != key) {
         slot = slot + 1 & 4095;
         if (slot == first) {
            return first;
         }
      }

      return slot;
   }
}
