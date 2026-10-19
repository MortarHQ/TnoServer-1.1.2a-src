package com.mortal_cultivation.client;

import com.mortal_cultivation.client.particle.LightningWoodArcParticle;
import com.mortal_cultivation.client.particle.SpiritMistParticle;
import com.mortal_cultivation.registry.BlockRegistry;
import com.mortal_cultivation.registry.ParticleRegistry;
import com.mortal_cultivation.registry.WorldgenRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;

@EventBusSubscriber(modid = "mortal_cultivation", value = Dist.CLIENT)
public final class SpiritForestAtmosphereEvents {
   private SpiritForestAtmosphereEvents() {
   }

   @SubscribeEvent
   public static void tick(Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      ClientLevel level = minecraft.level;
      if (level != null
         && minecraft.player != null
         && !minecraft.isPaused()
         && minecraft.options.particles().get() != ParticleStatus.MINIMAL
         && level.getGameTime() % 4L == 0L) {
         BlockPos camera = minecraft.gameRenderer.getMainCamera().getBlockPosition();
         if (level.getBiome(camera).is(WorldgenRegistry.SPIRIT_FORESTS)) {
            RandomSource random = level.random;
            boolean sky = level.getBiome(camera).is(WorldgenRegistry.SKY_SPIRIT_FOREST) || camera.getY() > 180 && !level.canSeeSky(camera);
            int samples = minecraft.options.particles().get() == ParticleStatus.DECREASED ? 1 : 2;

            for (int i = 0; i < samples; i++) {
               BlockPos sample = camera.offset(random.nextInt(37) - 18, 0, random.nextInt(37) - 18);
               if (level.hasChunkAt(sample)) {
                  boolean emitted = groundMist(level, sample, random);
                  if (sky && !emitted) {
                     islandMist(level, sample, random);
                  }
               }
            }

            if (level.isNight()) {
               fruitGlow(level, camera, random);
            }
         }
      }
   }

   private static boolean groundMist(ClientLevel level, BlockPos sample, RandomSource random) {
      for (int dy = 6; dy >= -10; dy--) {
         BlockPos ground = sample.above(dy);
         BlockState state = level.getBlockState(ground);
         if (soil(state) || state.getFluidState().is(FluidTags.WATER)) {
            BlockPos air = ground.above();
            if (level.isEmptyBlock(air) || level.getBlockState(air).canBeReplaced()) {
               boolean water = state.getFluidState().is(FluidTags.WATER);

               for (Direction direction : Plane.HORIZONTAL) {
                  water |= level.getFluidState(ground.relative(direction)).is(FluidTags.WATER);
               }

               if (water || state.is(BlockTags.DIRT) && random.nextInt(8) == 0) {
                  mist(level, air.getX() + random.nextDouble(), air.getY() + 0.3, air.getZ() + random.nextDouble(), random);
                  if (water && level.isNight() && random.nextBoolean()) {
                     level.addParticle(
                        ParticleTypes.GLOW,
                        air.getX() + 0.5,
                        air.getY() + 0.8,
                        air.getZ() + 0.5,
                        (random.nextDouble() - 0.5) * 0.015,
                        0.005,
                        (random.nextDouble() - 0.5) * 0.015
                     );
                  }

                  return true;
               } else {
                  return false;
               }
            }
         }
      }

      return false;
   }

   private static void islandMist(ClientLevel level, BlockPos sample, RandomSource random) {
      for (int dy = -14; dy <= 18; dy++) {
         BlockPos underside = sample.above(dy);
         if (soil(level.getBlockState(underside)) && level.isEmptyBlock(underside.below()) && level.isEmptyBlock(underside.below(2))) {
            mist(level, underside.getX() + 0.5, underside.getY() - 1.8, underside.getZ() + 0.5, random);
            return;
         }
      }

      if (level.isEmptyBlock(sample.below(8))) {
         for (Direction direction : Plane.HORIZONTAL) {
            if (soil(level.getBlockState(sample.relative(direction, 5).below(2)))) {
               mist(level, sample.getX() + 0.5, sample.getY() - 7.0, sample.getZ() + 0.5, random);
               return;
            }
         }
      }
   }

   private static void fruitGlow(ClientLevel level, BlockPos camera, RandomSource random) {
      for (int i = 0; i < 4; i++) {
         BlockPos pos = camera.offset(random.nextInt(21) - 10, random.nextInt(17) - 2, random.nextInt(21) - 10);
         if (level.hasChunkAt(pos) && level.getBlockState(pos).is((Block)BlockRegistry.SPIRIT_FRUIT_LEAVES.get())) {
            Direction face = Direction.getRandom(random);
            if (level.isEmptyBlock(pos.relative(face))) {
               level.addParticle(
                  ParticleTypes.GLOW,
                  pos.getX() + 0.5 + face.getStepX() * 0.6,
                  pos.getY() + 0.5 + face.getStepY() * 0.6,
                  pos.getZ() + 0.5 + face.getStepZ() * 0.6,
                  0.0,
                  0.003,
                  0.0
               );
            }
         }
      }
   }

   private static boolean soil(BlockState state) {
      return state.is(BlockTags.DIRT) || state.is(BlockTags.BASE_STONE_OVERWORLD);
   }

   private static void mist(ClientLevel level, double x, double y, double z, RandomSource random) {
      level.addParticle(
         (ParticleOptions)ParticleRegistry.SPIRIT_MIST.get(), x, y, z, 0.005 + random.nextDouble() * 0.005, 8.0E-4, (random.nextDouble() - 0.5) * 0.003
      );
   }

   @EventBusSubscriber(modid = "mortal_cultivation", bus = Bus.MOD, value = Dist.CLIENT)
   public static final class Providers {
      private Providers() {
      }

      @SubscribeEvent
      public static void register(RegisterParticleProvidersEvent event) {
         event.registerSpriteSet((ParticleType)ParticleRegistry.SPIRIT_MIST.get(), SpiritMistParticle.Provider::new);
         event.registerSpriteSet((ParticleType)ParticleRegistry.LIGHTNING_WOOD_ARC.get(), LightningWoodArcParticle.Provider::new);
      }
   }
}
