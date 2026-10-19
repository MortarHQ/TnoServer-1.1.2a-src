package net.regions_unexplored.block.type.dirt;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.regions_unexplored.registry.RUBlocks;
import net.regions_unexplored.world.level.block.state.properties.RuBlockStateProperties;

public class AshenDirtBlock extends Block {
   public static final BooleanProperty SMOULDERING = RuBlockStateProperties.SMOULDERING;

   public AshenDirtBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)this.defaultBlockState().setValue(SMOULDERING, false));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> stateBuilder) {
      stateBuilder.add(new Property[]{SMOULDERING});
   }

   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (level.getBlockState(pos.above()).is(Blocks.FIRE)) {
         level.setBlockAndUpdate(pos, (BlockState)RUBlocks.ASHEN_DIRT.get().defaultBlockState().setValue(SMOULDERING, true));
      }
   }

   public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
      if (state == RUBlocks.ASHEN_DIRT.get().defaultBlockState().setValue(SMOULDERING, true)) {
         super.animateTick(state, level, pos, random);
         if (random.nextInt(20) == 0) {
            level.addParticle(ParticleTypes.FLAME, pos.getX() + random.nextDouble(), pos.getY() + 1.1, pos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
            level.addParticle(ParticleTypes.SMOKE, pos.getX() + random.nextDouble(), pos.getY() + 1.1, pos.getZ() + random.nextDouble(), 0.0, 0.0, 0.0);
         }
      }
   }

   public static boolean isSmouldering(BlockState state) {
      return (Boolean)state.getValue(SMOULDERING);
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return this.defaultBlockState();
   }

   public static BlockState getSmouldering() {
      return (BlockState)RUBlocks.ASHEN_DIRT.get().defaultBlockState().setValue(SMOULDERING, true);
   }
}
