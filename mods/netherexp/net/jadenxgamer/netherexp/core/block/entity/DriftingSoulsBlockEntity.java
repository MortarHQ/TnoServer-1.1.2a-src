package net.jadenxgamer.netherexp.core.block.entity;

import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.JNEBlockEntityType;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions.ParticleSpritePicker;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.behaviors.DirectionalParticleBehavior;

public class DriftingSoulsBlockEntity extends BlockEntity {
   public DriftingSoulsBlockEntity(BlockPos pos, BlockState blockState) {
      super(JNEBlockEntityType.DRIFTING_SOULS.get(), pos, blockState);
   }

   public BlockEntityType<?> getType() {
      return JNEBlockEntityType.DRIFTING_SOULS.get();
   }

   public void tick(Level level, BlockPos pos, BlockState state) {
      if (level.isClientSide()) {
         RandomSource random = level.random;
         int radius = (Integer)JNEConfigs.DRIFTING_SOULS_SPAWN_RADIUS.get();

         for (int i = 0; i < JNEConfigs.DRIFTING_SOULS_SPAWN_QUANTITY.getAsInt(); i++) {
            int x = pos.getX() + Mth.nextInt(random, -radius, radius);
            int y = pos.getY() + Mth.nextInt(random, -radius, radius);
            int z = pos.getZ() + Mth.nextInt(random, -radius, radius);
            BlockPos particlePos = new BlockPos(x, y, z);
            BlockState particleState = level.getBlockState(particlePos);
            if (!particleState.isSolidRender(level, particlePos)) {
               DriftingSoulsBlockEntity.Client.driftingSoulParticle(level, random, x + random.nextDouble(), y + random.nextDouble(), z + random.nextDouble());
            }
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Client {
      public static void driftingSoulParticle(Level level, RandomSource random, double x, double y, double z) {
         Vec3 direction = new Vec3(-1.0, 0.0, 1.0);
         WorldParticleBuilder.create(JNEParticleTypes.DRIFTING_SOUL.get())
            .setFullBrightLighting()
            .setSpinData(SpinParticleData.create(0.05F, -0.05F, 0.05F).build())
            .setScaleData(GenericParticleData.create(0.695F).build())
            .setTransparencyData(GenericParticleData.create(0.1F, 0.25F, 0.0F).build())
            .setRenderType(LodestoneWorldParticleRenderType.ADDITIVE)
            .setSpritePicker(ParticleSpritePicker.RANDOM_SPRITE)
            .setBehavior(DirectionalParticleBehavior.directional(direction))
            .setLifetime(random.nextInt(40, 50))
            .enableForcedSpawn()
            .enableNoClip()
            .setMotion(
               (Double)JNEConfigs.SOUL_SAND_VALLEY_WIND_SPEED.get() + random.nextDouble() * 0.2,
               Mth.randomBetween(level.random, 0.1F, 0.5F) * 0.1,
               (Double)JNEConfigs.SOUL_SAND_VALLEY_WIND_SPEED.get() + random.nextDouble() * 0.2
            )
            .spawn(level, x, y, z);
      }
   }
}
