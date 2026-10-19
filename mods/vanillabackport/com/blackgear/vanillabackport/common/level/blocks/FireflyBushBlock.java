package com.blackgear.vanillabackport.common.level.blocks;

import com.blackgear.vanillabackport.client.registries.ModParticles;
import com.blackgear.vanillabackport.client.registries.ModSoundEvents;
import com.blackgear.vanillabackport.common.api.block.SpreadableBonemealableBlock;
import com.blackgear.vanillabackport.core.util.LevelUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.levelgen.Heightmap.Types;

public class FireflyBushBlock extends BushBlock implements SpreadableBonemealableBlock {
   public static final MapCodec<FireflyBushBlock> CODEC = simpleCodec(FireflyBushBlock::new);
   private static final double FIREFLY_CHANCE_PER_TICK = 0.7;
   private static final double FIREFLY_HORIZONTAL_RANGE = 10.0;
   private static final double FIREFLY_VERTICAL_RANGE = 5.0;
   private static final int FIREFLY_SPAWN_MAX_BRIGHTNESS_LEVEL = 13;
   private static final int FIREFLY_AMBIENT_SOUND_CHANCE_ONE_IN = 30;

   public FireflyBushBlock(Properties properties) {
      super(properties);
   }

   protected MapCodec<? extends BushBlock> codec() {
      return CODEC;
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      if (random.nextInt(30) == 0 && LevelUtils.isMoonVisible(level) && level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, pos.getX(), pos.getZ()) <= pos.getY()) {
         level.playLocalSound(pos, ModSoundEvents.FIREFLY_BUSH_IDLE.get(), SoundSource.AMBIENT, 1.0F, 1.0F, false);
      }

      if ((LevelUtils.isMoonVisible(level) || level.getMaxLocalRawBrightness(pos) <= 13) && random.nextDouble() <= 0.7) {
         double x = pos.getX() + random.nextDouble() * 10.0 - 5.0;
         double y = pos.getY() + random.nextDouble() * 5.0;
         double z = pos.getZ() + random.nextDouble() * 10.0 - 5.0;
         level.addParticle((ParticleOptions)ModParticles.FIREFLY.get(), x, y, z, 0.0, 0.0, 0.0);
      }
   }

   public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
      return SpreadableBonemealableBlock.hasSpreadableNeighbourPos(level, pos, state);
   }

   public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
      return true;
   }

   public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
      SpreadableBonemealableBlock.findSpreadableNeighbourPos(level, pos, state).ifPresent(newPos -> level.setBlockAndUpdate(newPos, this.defaultBlockState()));
   }
}
