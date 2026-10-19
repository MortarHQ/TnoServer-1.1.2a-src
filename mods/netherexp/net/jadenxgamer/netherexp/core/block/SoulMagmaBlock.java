package net.jadenxgamer.netherexp.core.block;

import net.jadenxgamer.netherexp.registry.JNEMobEffects;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions.ParticleSpritePicker;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;

public class SoulMagmaBlock extends Block {
   public SoulMagmaBlock(Properties properties) {
      super(properties);
   }

   public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
      if (entity instanceof LivingEntity living && this.canHurtEntity(living)) {
         living.hurt(level.damageSources().hotFloor(), 2.0F);
      }

      super.stepOn(level, pos, state, entity);
   }

   public boolean addRunningEffects(BlockState state, Level level, BlockPos pos, Entity entity) {
      if (level.isClientSide) {
         SoulMagmaBlock.Client.particle(level, level.random, entity.getRandomX(0.5), entity.blockPosition().getY(), entity.getRandomZ(0.5));
      }

      return true;
   }

   private boolean canHurtEntity(LivingEntity entity) {
      return entity.isSprinting() && !entity.hasEffect(JNEMobEffects.SOUL_SPEED);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Client {
      public static void particle(Level level, RandomSource random, double x, double y, double z) {
         WorldParticleBuilder.create(JNEParticleTypes.SOUL_MAGMA.get())
            .setFullBrightLighting()
            .setSpinData(SpinParticleData.createRandomDirection(random, 0.0F, 1.0F).setCoefficient(0.7F).setEasing(Easing.SINE_IN).build())
            .setScaleData(GenericParticleData.create(0.17F).build())
            .setTransparencyData(GenericParticleData.create(1.0F).build())
            .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
            .setSpritePicker(ParticleSpritePicker.WITH_AGE)
            .setLifetime(random.nextInt(20, 30))
            .disableNoClip()
            .setGravity(0.05F)
            .setMotion(random.nextDouble() * 0.1, 0.04, random.nextDouble() * 0.1)
            .spawn(level, x, y, z);
      }
   }
}
