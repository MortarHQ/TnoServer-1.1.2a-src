package net.regions_unexplored.world.level.block.plant.sapling;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.regions_unexplored.block.sapling.RuUltraFromSuperTreeGrower;

public class RuUltraFromSuperSaplingBlock extends BushBlock implements BonemealableBlock {
   protected final RuUltraFromSuperTreeGrower treeGrower;
   public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
   protected static final float AABB_OFFSET = 6.0F;
   protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);
   public static final MapCodec<RuUltraFromSuperSaplingBlock> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            RuUltraFromSuperTreeGrower.CODEC.fieldOf("tree").forGetter(ultraSaplingBlock -> ultraSaplingBlock.treeGrower), propertiesCodec()
         )
         .apply(instance, RuUltraFromSuperSaplingBlock::new)
   );

   public RuUltraFromSuperSaplingBlock(RuUltraFromSuperTreeGrower treeGrower, Properties properties) {
      super(properties);
      this.treeGrower = treeGrower;
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(STAGE, 0));
   }

   public void advanceTree(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
      if ((Integer)state.getValue(STAGE) == 0) {
         level.setBlock(pos, (BlockState)state.cycle(STAGE), 4);
      } else {
         this.treeGrower.growTree(level, level.getChunkSource().getGenerator(), pos, state, random);
      }
   }

   public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
      return SHAPE;
   }

   public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
      if (serverLevel.getMaxLocalRawBrightness(blockPos.above()) >= 9 && randomSource.nextInt(7) == 0) {
         this.advanceTree(serverLevel, blockPos, blockState, randomSource);
      }
   }

   protected MapCodec<? extends BushBlock> codec() {
      return CODEC;
   }

   public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
      return true;
   }

   public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
      return level.random.nextFloat() < 0.45;
   }

   public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
      this.advanceTree(serverLevel, blockPos, blockState, randomSource);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{STAGE});
   }
}
