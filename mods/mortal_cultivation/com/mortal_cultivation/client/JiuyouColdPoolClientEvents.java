package com.mortal_cultivation.client;

import com.mortal_cultivation.client.particle.JiuyouMistParticle;
import com.mortal_cultivation.client.particle.JiuyouWispParticle;
import com.mortal_cultivation.registry.ParticleRegistry;
import com.mortal_cultivation.registry.WorldgenRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.material.FogType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeFogColor;
import net.neoforged.neoforge.client.event.ViewportEvent.RenderFog;

@EventBusSubscriber(modid = "mortal_cultivation", value = Dist.CLIENT)
public final class JiuyouColdPoolClientEvents {
   private static ClientLevel lastLevel;
   private static float underwaterBlend;
   private static float depthBlend;

   private JiuyouColdPoolClientEvents() {
   }

   @SubscribeEvent
   public static void tick(Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      ClientLevel level = minecraft.level;
      if (level != lastLevel) {
         lastLevel = level;
         underwaterBlend = 0.0F;
         depthBlend = 0.0F;
      }

      if (level != null && minecraft.player != null && !minecraft.isPaused()) {
         BlockPos camera = minecraft.gameRenderer.getMainCamera().getBlockPosition();
         boolean inside = level.getBiome(camera).is(WorldgenRegistry.JIUYOU_COLD_POOL_BIOME) && level.getBrightness(LightLayer.SKY, camera) == 0;
         boolean underwater = inside && minecraft.gameRenderer.getMainCamera().getFluidInCamera() == FogType.WATER;
         underwaterBlend = Mth.lerp(0.12F, underwaterBlend, underwater ? 1.0F : 0.0F);
         if (underwater) {
            int depth = 0;

            while (depth < 14 && level.getFluidState(camera.above(depth + 1)).is(FluidTags.WATER)) {
               depth++;
            }

            depthBlend = Mth.lerp(0.1F, depthBlend, Mth.clamp(depth / 7.0F, 0.0F, 1.0F));
         }

         if (inside && minecraft.options.particles().get() != ParticleStatus.MINIMAL && level.getGameTime() % 4L == 0L) {
            RandomSource random = level.random;
            int samples = minecraft.options.particles().get() == ParticleStatus.DECREASED ? 2 : 5;
            MutableBlockPos pos = new MutableBlockPos();

            for (int i = 0; i < samples; i++) {
               int x = camera.getX() + random.nextInt(41) - 20;
               int z = camera.getZ() + random.nextInt(41) - 20;
               if (level.hasChunkAt(pos.set(x, camera.getY(), z))) {
                  for (int dy = 12; dy >= -18; dy--) {
                     pos.set(x, camera.getY() + dy, z);
                     if (level.getFluidState(pos).is(FluidTags.WATER) && level.isEmptyBlock(pos.above())) {
                        if (!level.getBiome(pos).is(WorldgenRegistry.JIUYOU_COLD_POOL_BIOME) || level.getBrightness(LightLayer.SKY, pos.above()) != 0) {
                           break;
                        }

                        double px = x + random.nextDouble();
                        double pz = z + random.nextDouble();
                        level.addParticle(
                           (ParticleOptions)ParticleRegistry.JIUYOU_MIST.get(), px, pos.getY() + 1.15, pz, 0.004, 0.0, (random.nextDouble() - 0.5) * 0.003
                        );
                        int depth = 1;

                        while (depth < 14 && level.getFluidState(pos.below(depth)).is(FluidTags.WATER)) {
                           depth++;
                        }

                        if (random.nextInt(5) == 0 && depth > 3) {
                           level.addParticle((ParticleOptions)ParticleRegistry.JIUYOU_MIST.get(), px, pos.getY() - depth + 1.4, pz, 0.002, 8.0E-4, 0.001);
                        }

                        if (random.nextInt(28) == 0 && depth > 2) {
                           level.addParticle(
                              (ParticleOptions)ParticleRegistry.JIUYOU_WISP.get(),
                              px,
                              pos.getY() - Math.min(depth - 1.4, 1.3 + random.nextDouble() * 2.1),
                              pz,
                              0.0,
                              0.0,
                              0.0
                           );
                        }
                        break;
                     }
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void fogColor(ComputeFogColor event) {
      if (!(underwaterBlend < 0.01F) && event.getCamera().getFluidInCamera() == FogType.WATER) {
         float blend = underwaterBlend * (0.35F + depthBlend * 0.35F);
         event.setRed(Mth.lerp(blend, event.getRed(), 0.018F));
         event.setGreen(Mth.lerp(blend, event.getGreen(), 0.035F));
         event.setBlue(Mth.lerp(blend, event.getBlue(), 0.095F));
      }
   }

   @SubscribeEvent
   public static void fogDistance(RenderFog event) {
      if (!(underwaterBlend < 0.01F) && event.getCamera().getFluidInCamera() == FogType.WATER) {
         float distance = Mth.lerp(underwaterBlend, event.getFarPlaneDistance(), 13.0F - depthBlend * 7.0F);
         event.setFarPlaneDistance(Math.min(event.getFarPlaneDistance(), distance));
         event.setNearPlaneDistance(Math.min(event.getNearPlaneDistance(), 0.0F));
         event.setCanceled(true);
      }
   }

   @EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.MOD, value = Dist.CLIENT)
   public static final class Providers {
      private Providers() {
      }

      @SubscribeEvent
      public static void register(RegisterParticleProvidersEvent event) {
         event.registerSpriteSet((ParticleType)ParticleRegistry.JIUYOU_MIST.get(), JiuyouMistParticle.Provider::new);
         event.registerSpriteSet((ParticleType)ParticleRegistry.JIUYOU_WISP.get(), JiuyouWispParticle.Provider::new);
      }
   }
}
