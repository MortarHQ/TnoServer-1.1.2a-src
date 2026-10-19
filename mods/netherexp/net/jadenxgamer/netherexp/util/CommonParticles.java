package net.jadenxgamer.netherexp.util;

import java.awt.Color;
import net.jadenxgamer.netherexp.client.particle.JNEPortalParticle;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions.ParticleSpritePicker;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.behaviors.DirectionalParticleBehavior;
import team.lodestar.lodestone.systems.particle.world.behaviors.SparkParticleBehavior;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

public class CommonParticles {
   public static final LodestoneWorldParticleType[] SMOKE_VARIANTS = new LodestoneWorldParticleType[]{
      JNEParticleTypes.REDUX_POOF.get(),
      JNEParticleTypes.REDUX_POOF.get(),
      JNEParticleTypes.REDUX_POOF_BLOB.get(),
      JNEParticleTypes.REDUX_POOF_BLOB.get(),
      JNEParticleTypes.REDUX_POOF_STAR.get()
   };

   public static void smokeParticle(Level level, RandomSource random, double x, double y, double z, Color start, Color end) {
      for (int i = 0; i < 4; i++) {
         LodestoneWorldParticleType particle = SMOKE_VARIANTS[random.nextInt(SMOKE_VARIANTS.length)];
         double parX = x + 0.5 + random.nextDouble() / 2.6 * (random.nextBoolean() ? 1 : -1);
         double parZ = z + 0.5 + random.nextDouble() / 2.6 * (random.nextBoolean() ? 1 : -1);
         WorldParticleBuilder.create(particle)
            .setNaturalLighting()
            .setScaleData(GenericParticleData.create(Mth.randomBetween(random, 0.33F, 0.53F)).build())
            .setTransparencyData(GenericParticleData.create(1.0F, 0.6F).setEasing(Easing.BOUNCE_OUT).build())
            .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
            .setColorData(ColorParticleData.create(start, end).setCoefficient(1.3F).setEasing(Easing.SINE_OUT).build())
            .setSpritePicker(ParticleSpritePicker.WITH_AGE)
            .setLifetime(Mth.randomBetweenInclusive(random, 12, 27))
            .enableNoClip()
            .addMotion(0.0 + random.nextDouble() / 64.0, 0.1, 0.0 + random.nextDouble() / 64.0)
            .spawn(level, parX, y + 0.25 + random.nextDouble() / 8.0, parZ);
      }
   }

   public static void emberParticle(Level level, RandomSource random, double x, double y, double z, Color color) {
      double parX = x + 0.5;
      double parZ = z + 0.5;
      WorldParticleBuilder.create(JNEParticleTypes.GLOWING_DOT_COIL.get())
         .setFullBrightLighting()
         .setSpinData(SpinParticleData.createRandomDirection(random, 0.0F, 1.0F).setCoefficient(0.7F).setEasing(Easing.SINE_IN).build())
         .setScaleData(GenericParticleData.create(0.18F, 0.18F, 0.0F).build())
         .setTransparencyData(GenericParticleData.create(1.0F).build())
         .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
         .setLifetime(random.nextInt(10, 50))
         .disableNoClip()
         .setGravity(0.0F)
         .setColorData(ColorParticleData.create(color).build())
         .spawn(level, parX, y, parZ);
   }

   public static void brewingStandParticle(Level level, BlockPos pos, RandomSource random, double x, double y, double z) {
      Color start = new Color(16634664);
      Color end = new Color(10042113);

      for (int i = 0; i < 3; i++) {
         LodestoneWorldParticleType particle = SMOKE_VARIANTS[random.nextInt(SMOKE_VARIANTS.length)];
         WorldParticleBuilder.create(particle)
            .setNaturalLighting()
            .setScaleData(GenericParticleData.create(Mth.randomBetween(random, 0.23F, 0.29F)).build())
            .setTransparencyData(GenericParticleData.create(0.4F, 0.2F).setEasing(Easing.BOUNCE_OUT).build())
            .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
            .setColorData(ColorParticleData.create(start, end).setCoefficient(0.6F).setEasing(Easing.SINE_OUT).build())
            .setSpritePicker(ParticleSpritePicker.WITH_AGE)
            .setLifetime(Mth.randomBetweenInclusive(random, 12, 27))
            .enableNoClip()
            .addMotion(0.0 + random.nextDouble() / 64.0, 0.025, 0.0 + random.nextDouble() / 64.0)
            .spawn(level, x, y, z);
      }

      double parX = pos.getX() + 0.5 + random.nextDouble() / 4.0 * (random.nextBoolean() ? 1 : -1);
      double parZ = pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (random.nextBoolean() ? 1 : -1);
      WorldParticleBuilder.create(JNEParticleTypes.GLOWING_DOT.get())
         .setFullBrightLighting()
         .setSpinData(SpinParticleData.createRandomDirection(random, 0.0F, 1.0F).setCoefficient(0.7F).setEasing(Easing.SINE_IN).build())
         .setScaleData(GenericParticleData.create(0.18F, 0.0F).build())
         .setTransparencyData(GenericParticleData.create(1.0F).build())
         .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
         .setLifetime(random.nextInt(5, 15))
         .disableNoClip()
         .setGravity(0.0F)
         .setColorData(ColorParticleData.create(start).build())
         .setMotion(0.0, 0.08, 0.0)
         .spawn(level, parX, y, parZ);
   }

   public static void potionConsumeParticle(Level level, RandomSource random, LivingEntity entity, Color color) {
      Vec3 direction = new Vec3(0.0, 1.0, 0.0);
      int delay = 0;
      double x = entity.getX();
      double y = entity.getY();
      double z = entity.getZ();

      for (int i = 0; i < 3; i++) {
         WorldParticleBuilder.create(JNEParticleTypes.WIND_TRAIL)
            .setFullBrightLighting()
            .setBehavior(DirectionalParticleBehavior.directional(direction))
            .setScaleData(GenericParticleData.create(1.39F).setEasing(Easing.SINE_IN_OUT).build())
            .setTransparencyData(GenericParticleData.create(0.25F, 0.0F).build())
            .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
            .setColorData(ColorParticleData.create(color).build())
            .setSpritePicker(ParticleSpritePicker.WITH_AGE)
            .setLifetime(10)
            .setLifeDelay(delay)
            .addTickActor(actor -> actor.setPos(entity.getX(), actor.getY(), entity.getZ()))
            .enableNoClip()
            .addMotion(0.0, 0.26, 0.0)
            .spawn(level, x, y - 0.05, z);
         delay += 3;
      }

      for (int i = 0; i < 20; i++) {
         double parX = x + random.nextDouble() * (random.nextBoolean() ? 1 : -1);
         double parZ = z + random.nextDouble() * (random.nextBoolean() ? 1 : -1);
         WorldParticleBuilder.create(JNEParticleTypes.GLOWING_DOT.get())
            .setFullBrightLighting()
            .setSpinData(SpinParticleData.createRandomDirection(random, 0.0F, 1.0F).setCoefficient(0.7F).setEasing(Easing.SINE_IN).build())
            .setScaleData(GenericParticleData.create(0.38F, 0.0F).build())
            .setTransparencyData(GenericParticleData.create(0.8F).build())
            .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
            .setLifetime(random.nextInt(20))
            .disableNoClip()
            .setLifeDelay(random.nextInt(0, 5))
            .setGravity(0.0F)
            .setColorData(ColorParticleData.create(color).build())
            .setMotion(0.0, 0.26, 0.0)
            .spawn(level, parX, y, parZ);
      }
   }

   public static void netherFogParticle(Level level, RandomSource random, double x, double y, double z, Color color) {
      double motion = (Double)JNEConfigs.NETHER_MIST_MOTION_MULTIPLIER.get();
      float scale = (float)JNEConfigs.NETHER_MIST_SCALE.getAsDouble();
      float opacity = ((Double)JNEConfigs.NETHER_MIST_OPACITY.get()).floatValue();
      float startSize = Mth.randomBetween(random, scale - 2.0F, scale);
      float endSize = Mth.randomBetween(random, scale + 1.0F, scale + 3.0F);
      float transparency = Mth.randomBetween(random, opacity - 0.2F, opacity + 1.0F);
      Vec3 direction = new Vec3(0.0, 1.0, 0.0);
      WorldParticleBuilder.create(JNEParticleTypes.NETHER_FOG.get())
         .setFullBrightLighting()
         .enableNoClip()
         .setBehavior(SparkParticleBehavior.sparkBehavior().setForcedDirection(direction))
         .setForceSpawn(true)
         .setScaleData(GenericParticleData.create(startSize, endSize).build())
         .setTransparencyData(GenericParticleData.create(0.02F, 3.0F, 0.0F).build())
         .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE.withDepthFade())
         .setLifetime(random.nextInt(120, 180))
         .setMotion(random.nextDouble() * motion, random.nextDouble() * motion, random.nextDouble() * motion)
         .setColorData(ColorParticleData.create(color.brighter().brighter()).build())
         .spawn(level, x, y, z);
   }

   public static void netherPortalParticle(BlockState state, Level level, BlockPos pos, RandomSource random) {
      Color base = new Color(10104831);
      Color dot = new Color(16777215);
      Color dim = new Color(6887350);
      Color dark = new Color(2687818);
      if (random.nextInt(4) == 0) {
         double x = pos.getX() + 0.5 + random.nextDouble() * (random.nextBoolean() ? 1 : -1);
         double y = pos.getY() + 0.5 + random.nextDouble() * (random.nextBoolean() ? 1 : -1);
         double z = pos.getZ() + 0.5 + random.nextDouble() * (random.nextBoolean() ? 1 : -1);
         WorldParticleBuilder.create(JNEParticleTypes.PORTAL_MIST.get())
            .setFullBrightLighting()
            .setNoClip(false)
            .enableForcedSpawn()
            .setSpinData(
               SpinParticleData.createRandomDirection(random, 0.0F, random.nextBoolean() ? -1.0F : 1.0F).setCoefficient(0.2F).setEasing(Easing.SINE_IN).build()
            )
            .setScaleData(GenericParticleData.create(2.93F).build())
            .setTransparencyData(GenericParticleData.create(0.0F, 0.25F, 0.0F).setEasing(Easing.SINE_IN_OUT).build())
            .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE.withDepthFade())
            .setColorData(ColorParticleData.create(random.nextBoolean() ? dim : dark).setEasing(Easing.SINE_OUT).build())
            .setSpritePicker(ParticleSpritePicker.WITH_AGE)
            .setLifetime(Mth.randomBetweenInclusive(random, 100, 120))
            .enableNoClip()
            .addMotion(random.nextDouble() / 50.0 * (random.nextBoolean() ? 1 : -1), 0.0, random.nextDouble() / 50.0 * (random.nextBoolean() ? 1 : -1))
            .spawn(level, x, y, z);
      }

      double dotX = pos.getX() + 0.5 + random.nextDouble() / 2.0 * (random.nextBoolean() ? 1 : -1);
      double dotY = pos.getY() + 0.5 + random.nextDouble() / 2.0 * (random.nextBoolean() ? 1 : -1);
      double dotZ = pos.getZ() + 0.5 + random.nextDouble() / 2.0 * (random.nextBoolean() ? 1 : -1);
      WorldParticleBuilder.create(JNEParticleTypes.GLOWING_DOT.get())
         .setFullBrightLighting()
         .setSpinData(
            SpinParticleData.createRandomDirection(random, 0.0F, random.nextBoolean() ? -1.0F : 1.0F).setCoefficient(0.2F).setEasing(Easing.SINE_IN).build()
         )
         .setScaleData(GenericParticleData.create(0.0F, 0.38F, 0.0F).build())
         .setTransparencyData(GenericParticleData.create(1.0F).build())
         .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE.withDepthFade())
         .setLifetime(random.nextInt(20, 40))
         .disableNoClip()
         .setGravity(0.0F)
         .setColorData(ColorParticleData.create(dot).build())
         .setMotion(0.0, 0.02, 0.0)
         .spawn(level, dotX, dotY, dotZ);

      for (int i = 0; i < 4; i++) {
         double x = pos.getX() + 0.5 + random.nextDouble() / 2.0 * (random.nextBoolean() ? 1 : -1);
         double y = pos.getY() + 0.5 + random.nextDouble() * (random.nextBoolean() ? 1 : -1);
         double z = pos.getZ() + 0.5 + random.nextDouble() / 2.0 * (random.nextBoolean() ? 1 : -1);
         switch ((Axis)state.getValue(NetherPortalBlock.AXIS)) {
            case X:
               z = pos.getZ() + 0.5 + random.nextDouble() / 0.3 * (random.nextBoolean() ? 1 : -1);
               break;
            case Z:
               x = pos.getX() + 0.5 + random.nextDouble() / 0.3 * (random.nextBoolean() ? 1 : -1);
         }

         WorldParticleBuilder.create(JNEParticleTypes.JNE_PORTAL.get())
            .setFullBrightLighting()
            .disableNoClip()
            .setScaleData(GenericParticleData.create(0.0F, 0.13F, 0.0F).setEasing(Easing.SINE_IN_OUT).build())
            .setTransparencyData(GenericParticleData.create(0.0F, 0.7F, 0.0F).build())
            .setColorData(ColorParticleData.create(random.nextBoolean() ? dim : base).build())
            .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE.withDepthFade())
            .setSpritePicker(ParticleSpritePicker.RANDOM_SPRITE)
            .setLifetime(random.nextInt(100))
            .addSpawnActor(
               actor -> {
                  if (actor instanceof JNEPortalParticle portalParticle) {
                     portalParticle.setTargetPos(
                        pos.getX() + 0.5 + random.nextDouble() / 2.0,
                        pos.getY() + 0.5 + random.nextDouble() / 2.0,
                        pos.getZ() + 0.5 + random.nextDouble() / 2.0
                     );
                  }
               }
            )
            .setMotion(0.0, 0.0, 0.0)
            .spawn(level, x, y, z);
      }
   }
}
