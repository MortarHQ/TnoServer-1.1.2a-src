package com.yungnickyoung.minecraft.yungscavebiomes.block;

import com.yungnickyoung.minecraft.yungscavebiomes.module.CriteriaModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.EntityTypeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.ItemModule;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
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
import org.jetbrains.annotations.NotNull;

@ParametersAreNonnullByDefault
public class PricklyPeachCactusBlock extends Block implements BonemealableBlock {
   public static final BooleanProperty FRUIT = BlockStateProperties.BERRIES;
   public static final IntegerProperty AGE = BlockStateProperties.AGE_25;
   protected static final VoxelShape COLLISION_SHAPE = Block.box(4.0, 0.0, 3.0, 12.0, 8.0, 11.0);
   protected static final VoxelShape OUTLINE_SHAPE = Block.box(4.0, 0.0, 3.0, 12.0, 7.0, 11.0);
   private static final float AGE_CHANCE = 0.5F;
   private static final float BERRY_CHANCE = 0.1F;
   private static final int MAX_AGE = 25;

   public PricklyPeachCactusBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(AGE, 0)).setValue(FRUIT, false));
   }

   @NotNull
   protected ItemInteractionResult useItemOn(
      ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult hitResult
   ) {
      return !isMaxAge(blockState) && itemStack.is(Items.BONE_MEAL)
         ? ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION
         : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
   }

   @NotNull
   protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult hitResult) {
      if ((Boolean)blockState.getValue(FRUIT)) {
         popFruit(level, blockPos);
         float volume = Mth.randomBetween(level.random, 0.8F, 1.2F);
         level.playSound(null, blockPos, SoundEvents.CAVE_VINES_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, volume);
         level.setBlock(blockPos, (BlockState)((BlockState)blockState.setValue(FRUIT, false)).setValue(AGE, 0), 2);
      } else if (player.getMainHandItem().isEmpty()) {
         player.hurt(level.damageSources().cactus(), 1.0F);
         if (player instanceof ServerPlayer serverPlayer) {
            CriteriaModule.INTERACT_EMPTY_PRICKLY_CACTUS.trigger(serverPlayer);
         }
      }

      return InteractionResult.sidedSuccess(level.isClientSide);
   }

   public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource random) {
      if (!blockState.canSurvive(serverLevel, blockPos)) {
         serverLevel.destroyBlock(blockPos, true);
      }
   }

   public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource random) {
      if (!isMaxAge(blockState) && !hasFruit(blockState)) {
         if (random.nextDouble() < 0.5) {
            int newAge = (Integer)blockState.getValue(AGE) + 1;
            BlockState newBlockState = (BlockState)blockState.setValue(AGE, newAge);
            if (random.nextDouble() < 0.1F || newAge == 25) {
               newBlockState = (BlockState)newBlockState.setValue(FRUIT, true);
            }

            serverLevel.setBlockAndUpdate(blockPos, newBlockState);
         }
      }
   }

   public void spawnAfterBreak(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, ItemStack itemStack, boolean bl) {
      super.spawnAfterBreak(blockState, serverLevel, blockPos, itemStack, bl);
      if (blockState.hasProperty(FRUIT) && (Boolean)blockState.getValue(FRUIT)) {
         Block.popResource(serverLevel, blockPos, new ItemStack((ItemLike)ItemModule.PRICKLY_PEACH_ITEM.get(), 1));
      }
   }

   public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
      return blockState.hasProperty(FRUIT) && !(Boolean)blockState.getValue(FRUIT);
   }

   public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos blockPos, BlockState blockState) {
      return true;
   }

   public void performBonemeal(ServerLevel serverLevel, RandomSource random, BlockPos blockPos, BlockState blockState) {
      serverLevel.setBlock(blockPos, (BlockState)((BlockState)blockState.setValue(FRUIT, true)).setValue(AGE, 25), 2);
   }

   @NotNull
   public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
      return COLLISION_SHAPE;
   }

   @NotNull
   public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
      return OUTLINE_SHAPE;
   }

   public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
      BlockState blockStateBelow = levelReader.getBlockState(blockPos.below());
      return blockStateBelow.is(BlockTags.SAND);
   }

   @NotNull
   public BlockState updateShape(
      BlockState currState, Direction neighborDirection, BlockState neighborBlockState, LevelAccessor levelAccessor, BlockPos currPos, BlockPos neighborPos
   ) {
      if (!currState.canSurvive(levelAccessor, currPos)) {
         levelAccessor.scheduleTick(currPos, this, 1);
      }

      return super.updateShape(currState, neighborDirection, neighborBlockState, levelAccessor, currPos, neighborPos);
   }

   public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
      if (entity.getType() != EntityTypeModule.SAND_SNAPPER.get()) {
         entity.hurt(level.damageSources().cactus(), 1.0F);
      }
   }

   public boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
      return false;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{AGE, FRUIT});
   }

   private static boolean isMaxAge(BlockState blockState) {
      return blockState.hasProperty(AGE) && (Integer)blockState.getValue(AGE) == 25;
   }

   private static boolean hasFruit(BlockState blockState) {
      return blockState.hasProperty(FRUIT) && (Boolean)blockState.getValue(FRUIT);
   }

   private static void popFruit(Level level, BlockPos $$1) {
      if (!level.isClientSide && level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
         double x = $$1.getX() + 0.5;
         double y = $$1.getY() + 0.5 - EntityType.ITEM.getHeight() / 2.0F;
         double z = $$1.getZ() + 0.5;
         double xOffset = Mth.nextDouble(level.random, 0.2, 0.25);
         double yOffset = 0.25;
         double zOffset = Mth.nextDouble(level.random, 0.2, 0.25);
         double dx = Mth.nextDouble(level.random, 0.075, 0.1);
         double dy = 0.2;
         double dz = Mth.nextDouble(level.random, 0.075, 0.1);
         if (level.random.nextBoolean()) {
            xOffset *= -1.0;
            dx *= -1.0;
         }

         if (level.random.nextBoolean()) {
            zOffset *= -1.0;
            dz *= -1.0;
         }

         x += xOffset;
         y += yOffset;
         z += zOffset;
         ItemStack fruitItemStack = new ItemStack((ItemLike)ItemModule.PRICKLY_PEACH_ITEM.get(), 1);
         ItemEntity itemEntity = new ItemEntity(level, x, y, z, fruitItemStack, dx, dy, dz);
         itemEntity.setDefaultPickUpDelay();
         level.addFreshEntity(itemEntity);
      }
   }
}
