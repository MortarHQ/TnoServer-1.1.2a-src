package net.jadenxgamer.netherexp.core.block;

import javax.annotation.Nullable;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.block.entity.SuspiciousSoulSandBlockEntity;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class SuspiciousSoulSandBlock extends BrushableBlock {
   public static final BooleanProperty PERSISTENT = BooleanProperty.create("persistent");

   public SuspiciousSoulSandBlock(Properties properties) {
      super(Blocks.SOUL_SAND, SoundEvents.BRUSH_SAND, SoundEvents.BRUSH_SAND_COMPLETED, properties);
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new SuspiciousSoulSandBlockEntity(pos, state);
   }

   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      super.randomTick(state, level, pos, random);
      if (!(Boolean)state.getValue(PERSISTENT) || !(random.nextDouble() > (Double)JNEConfigs.SUSPICIOUS_SOUL_SAND_DECAY_CHANCE.get())) {
         SuspiciousSoulSandBlockEntity blockEntity = (SuspiciousSoulSandBlockEntity)level.getBlockEntity(pos);
         if (blockEntity.getDecayCounter() >= (Integer)JNEConfigs.SUSPICIOUS_SOUL_SAND_MAX_DECAY.get()) {
            level.setBlock(pos, Blocks.SOUL_SAND.defaultBlockState(), 3);
            level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), JNESoundEvents.SUSPICIOUS_SOUL_SAND_BREAK.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
         } else {
            blockEntity.setDecayCounter(blockEntity.getDecayCounter() + 1);
         }
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{PERSISTENT});
   }
}
