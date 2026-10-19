package net.jadenxgamer.netherexp.mixin.client;

import java.awt.Color;
import javax.annotation.Nullable;
import net.jadenxgamer.netherexp.config.JNEConfigImpl;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.util.CommonParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LevelRenderer.class, priority = 650)
public abstract class LevelRendererMixin {
   @Shadow
   @Nullable
   private ClientLevel level;
   @Unique
   private long netherexp$lastMistTime = 0L;

   @Inject(method = "renderSnowAndRain", at = @At("HEAD"))
   private void netherexp$renderNetherFog(LightTexture lightTexture, float partialTick, double camX, double camY, double camZ, CallbackInfo ci) {
      if (JNEConfigImpl.COMMON.isLoaded()) {
         if ((Boolean)JNEConfigs.NETHER_MIST_PARTICLES.get() && this.level.dimension() == Level.NETHER) {
            Minecraft client = Minecraft.getInstance();
            long currentTime = client.level.getGameTime();
            if (currentTime - this.netherexp$lastMistTime >= ((Integer)JNEConfigs.NETHER_MIST_SPAWN_RATE.get()).intValue()) {
               this.netherexp$lastMistTime = currentTime;
               LocalPlayer player = client.player;
               if (player == null) {
                  return;
               }

               RandomSource random = this.level.random;
               double angle = random.nextDouble() * Math.PI * 2.0;
               double distance = Mth.nextDouble(random, (Double)JNEConfigs.NETHER_MIST_MIN_DISTANCE.get(), (Double)JNEConfigs.NETHER_MIST_MAX_DISTANCE.get());
               double x = player.getX() + Math.cos(angle) * distance;
               double y = player.getY() + Mth.nextDouble(random, -8.0, 8.0);
               double z = player.getZ() + Math.sin(angle) * distance;
               Color color = new Color(((Biome)this.level.getBiome(new BlockPos((int)x, (int)y, (int)z)).value()).getFogColor());
               CommonParticles.netherFogParticle(this.level, this.level.random, x, y, z, color);
            }
         }
      }
   }
}
