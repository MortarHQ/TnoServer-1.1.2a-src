package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LunarisCactusBlock extends Block {
   public static final MapCodec<LunarisCactusBlock> CODEC = simpleCodec(LunarisCactusBlock::new);
   public static final IntegerProperty AGE = BlockStateProperties.AGE_15;
   public static final BooleanProperty FRUIT = BooleanProperty.create("fruit");
   protected static final VoxelShape COLLISION_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 15.0, 15.0);
   protected static final VoxelShape OUTLINE_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

   public MapCodec<LunarisCactusBlock> codec() {
      return CODEC;
   }

   public LunarisCactusBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(AGE, 0)).setValue(FRUIT, false));
   }

   protected ItemInteractionResult useItemOn(
      ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult
   ) {
      if (ESPlatform.INSTANCE.isShears(itemStack) && (Boolean)blockState.getValue(FRUIT)) {
         level.setBlockAndUpdate(blockPos, ((EquipableCarvedLunarisCactusFruitBlock)ESBlocks.CARVED_LUNARIS_CACTUS_FRUIT.get()).defaultBlockState());
         itemStack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(itemStack));
         level.playSound(null, blockPos, SoundEvents.PUMPKIN_CARVE, SoundSource.BLOCKS, 1.0F, 1.0F);
         return ItemInteractionResult.sidedSuccess(level.isClientSide);
      } else {
         return super.useItemOn(itemStack, blockState, level, blockPos, player, interactionHand, blockHitResult);
      }
   }

   protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
      if (!blockState.canSurvive(serverLevel, blockPos)) {
         serverLevel.destroyBlock(blockPos, true);
      }
   }

   protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
      BlockPos blockPos2 = blockPos.above();
      if (serverLevel.isEmptyBlock(blockPos2) && !(Boolean)blockState.getValue(FRUIT)) {
         int i = 1;

         while (serverLevel.getBlockState(blockPos.below(i)).is(this)) {
            i++;
         }

         if (i <= 3) {
            int j = (Integer)blockState.getValue(AGE);
            if (j == 15) {
               serverLevel.setBlockAndUpdate(blockPos2, (BlockState)this.defaultBlockState().setValue(FRUIT, i == 3));
               BlockState blockState2 = (BlockState)blockState.setValue(AGE, 0);
               serverLevel.setBlock(blockPos, blockState2, 4);
               serverLevel.neighborChanged(blockState2, blockPos2, this, blockPos, false);
            } else {
               serverLevel.setBlock(blockPos, (BlockState)blockState.setValue(AGE, j + 1), 4);
            }
         }
      }
   }

   protected VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
      return COLLISION_SHAPE;
   }

   protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
      return OUTLINE_SHAPE;
   }

   protected BlockState updateShape(
      BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2
   ) {
      if (!blockState.canSurvive(levelAccessor, blockPos)) {
         levelAccessor.scheduleTick(blockPos, this, 1);
      }

      return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
   }

   protected boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
      for (Direction direction : Plane.HORIZONTAL) {
         BlockState blockState2 = levelReader.getBlockState(blockPos.relative(direction));
         if (blockState2.isSolid() || levelReader.getFluidState(blockPos.relative(direction)).is(FluidTags.LAVA)) {
            return false;
         }
      }

      BlockState blockState3 = levelReader.getBlockState(blockPos.below());
      boolean validCactus = blockState3.is(this) && !(Boolean)blockState3.getValue(FRUIT);
      return (validCactus || blockState3.is(BlockTags.SAND)) && !levelReader.getBlockState(blockPos.above()).liquid();
   }

   protected void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
      if (!(Boolean)blockState.getValue(FRUIT)) {
         entity.hurt(level.damageSources().cactus(), 1.0F);
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{AGE, FRUIT});
   }

   protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
      return false;
   }
}
