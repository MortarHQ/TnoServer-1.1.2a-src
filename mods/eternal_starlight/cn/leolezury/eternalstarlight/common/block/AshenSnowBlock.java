package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class AshenSnowBlock extends LayeredBlock {
   public static final MapCodec<AshenSnowBlock> CODEC = simpleCodec(AshenSnowBlock::new);

   public AshenSnowBlock(Properties properties) {
      super(properties);
   }

   @Override
   public MapCodec<AshenSnowBlock> codec() {
      return CODEC;
   }

   public void stepOn(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
      if (level.isClientSide && entity.getDeltaMovement().length() > 0.001F) {
         for (int i = 0; i < 4; i++) {
            float scale = Mth.randomBetween(entity.getRandom(), 1.75F, 2.25F);
            level.addParticle(
               (ParticleOptions)ESParticles.ASHEN_SNOW.get(),
               entity.getRandomX(0.75),
               entity.getY(),
               entity.getRandomZ(0.75),
               entity.getDeltaMovement().x() / scale,
               Mth.randomBetween(entity.getRandom(), 0.15F, 0.25F),
               entity.getDeltaMovement().z() / scale
            );
         }
      }
   }
}
