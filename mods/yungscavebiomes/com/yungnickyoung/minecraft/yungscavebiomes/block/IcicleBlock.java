package com.yungnickyoung.minecraft.yungscavebiomes.block;

import com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor.AbstractCauldronBlockAccessor;
import com.yungnickyoung.minecraft.yungscavebiomes.module.BlockModule;
import com.yungnickyoung.minecraft.yungscavebiomes.module.DamageTypeModule;
import com.yungnickyoung.minecraft.yungscavebiomes.services.Services;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.BlockStateBase;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

@ParametersAreNonnullByDefault
public class IcicleBlock extends Block implements Fallable, SimpleWaterloggedBlock {
   public static final EnumProperty<DripstoneThickness> THICKNESS = BlockStateProperties.DRIPSTONE_THICKNESS;
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   private static final VoxelShape BASE_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
   private static final VoxelShape MIDDLE_SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
   private static final VoxelShape FRUSTUM_SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
   private static final VoxelShape TIP_SHAPE = Block.box(5.0, 5.0, 5.0, 11.0, 16.0, 11.0);

   public IcicleBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(THICKNESS, DripstoneThickness.TIP)).setValue(WATERLOGGED, false)
      );
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{THICKNESS, WATERLOGGED});
   }

   public boolean canSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
      return isValidPosition(levelReader, blockPos);
   }

   public BlockState updateShape(
      BlockState currState, Direction neighborDirection, BlockState neighborBlockState, LevelAccessor levelAccessor, BlockPos currPos, BlockPos neighborPos
   ) {
      if ((Boolean)currState.getValue(WATERLOGGED)) {
         levelAccessor.scheduleTick(currPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
      }

      if (neighborDirection != Direction.UP && neighborDirection != Direction.DOWN) {
         return currState;
      } else if (levelAccessor.getBlockTicks().hasScheduledTick(currPos, this)) {
         return currState;
      } else if (neighborDirection == Direction.UP && !this.canSurvive(currState, levelAccessor, currPos)) {
         levelAccessor.scheduleTick(currPos, this, 2);
         return currState;
      } else {
         DripstoneThickness thickness = calculateIcicleThickness(levelAccessor, currPos);
         return (BlockState)currState.setValue(THICKNESS, thickness);
      }
   }

   public void onProjectileHit(Level level, @NotNull BlockState blockState, BlockHitResult blockHitResult, @NotNull Projectile projectile) {
      BlockPos blockPos = blockHitResult.getBlockPos();
      if (!level.isClientSide && projectile.mayInteract(level, blockPos) && projectile instanceof AbstractArrow && projectile.getDeltaMovement().length() > 0.4
         )
       {
         level.destroyBlock(blockPos, true);
      }
   }

   public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource random) {
      spawnFallingIcicle(blockState, serverLevel, blockPos);
   }

   public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource random) {
      maybeFillCauldron(blockState, serverLevel, blockPos, random.nextFloat());
      if (random.nextFloat() < 0.011377778F && isTop(blockState, serverLevel, blockPos)) {
         tryToGrowIcicle(blockState, serverLevel, blockPos);
      }
   }

   public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource random) {
      if (canDrip(blockState)) {
         float chance = random.nextFloat();
         if (!(chance > 0.12F)) {
            getFluidAboveIcicle(level, blockPos)
               .filter(fluid -> chance < 0.02F || canFillCauldron(fluid))
               .ifPresent(fluid -> spawnDripParticle(level, blockPos, blockState, fluid));
         }
      }
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
      Level levelAccessor = blockPlaceContext.getLevel();
      if (!isValidPosition(levelAccessor, blockPlaceContext.getClickedPos())) {
         return null;
      }

      boolean isWaterlogged = levelAccessor.getFluidState(blockPlaceContext.getClickedPos()).getType() == Fluids.WATER;
      DripstoneThickness thickness = calculateIcicleThickness(levelAccessor, blockPlaceContext.getClickedPos());
      return thickness == null ? null : (BlockState)((BlockState)this.defaultBlockState().setValue(THICKNESS, thickness)).setValue(WATERLOGGED, isWaterlogged);
   }

   @NotNull
   public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
      DripstoneThickness thickness = (DripstoneThickness)blockState.getValue(THICKNESS);

      VoxelShape voxelShape = switch (thickness) {
         case BASE -> BASE_SHAPE;
         case MIDDLE -> MIDDLE_SHAPE;
         case FRUSTUM -> FRUSTUM_SHAPE;
         default -> TIP_SHAPE;
      };
      Vec3 vec3 = blockState.getOffset(blockGetter, blockPos);
      return voxelShape.move(vec3.x, 0.0, vec3.z);
   }

   public boolean isCollisionShapeFullBlock(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
      return false;
   }

   public float getMaxHorizontalOffset() {
      return 0.125F;
   }

   public void onBrokenAfterFall(Level level, BlockPos blockPos, FallingBlockEntity fallingBlockEntity) {
      if (!fallingBlockEntity.isSilent() && level instanceof ServerLevel serverLevel) {
         Services.PLATFORM.sendIcicleProjectileShatterS2CPacket(serverLevel, Vec3.atCenterOf(blockPos));
         fallingBlockEntity.playSound(SoundEvents.GLASS_BREAK, 1.0F, 1.2F / (new Random().nextFloat() * 0.2F + 0.9F));
      }
   }

   @NotNull
   public DamageSource getFallDamageSource(@NotNull Entity entity) {
      return DamageTypeModule.of(entity.level().registryAccess(), DamageTypeModule.FALLING_ICICLE);
   }

   @NotNull
   public FluidState getFluidState(BlockState blockState) {
      return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
   }

   @Nullable
   private static BlockPos findTip(BlockState blockState, LevelAccessor levelAccessor, BlockPos blockPos, int searchDistance) {
      return isTip(blockState)
         ? blockPos
         : findBlockVertical(levelAccessor, blockPos, Direction.DOWN, state -> state.is((Block)BlockModule.ICICLE.get()), IcicleBlock::isTip, searchDistance)
            .orElse(null);
   }

   @Nullable
   private static BlockPos findRoot(LevelAccessor levelAccessor, BlockPos blockPos) {
      Predicate<BlockState> matchingPredicate = state -> state.is((Block)BlockModule.ICICLE.get());
      Predicate<BlockState> stoppingPredicate = state -> !state.is((Block)BlockModule.ICICLE.get());
      return findBlockVertical(levelAccessor, blockPos, Direction.UP, matchingPredicate, stoppingPredicate, 11).orElse(null);
   }

   private static boolean isValidPosition(LevelReader levelReader, BlockPos blockPos) {
      BlockPos posAbove = blockPos.relative(Direction.UP);
      BlockState blockStateAbove = levelReader.getBlockState(posAbove);
      return blockStateAbove.isFaceSturdy(levelReader, posAbove, Direction.DOWN) || blockStateAbove.is((Block)BlockModule.ICICLE.get());
   }

   private static boolean isTip(BlockState blockState) {
      if (!blockState.is((Block)BlockModule.ICICLE.get())) {
         return false;
      }

      DripstoneThickness thickness = (DripstoneThickness)blockState.getValue(THICKNESS);
      return thickness == DripstoneThickness.TIP;
   }

   private static boolean isTop(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
      return blockState.is((Block)BlockModule.ICICLE.get()) && !levelReader.getBlockState(blockPos.above()).is((Block)BlockModule.ICICLE.get());
   }

   private static Optional<BlockPos> findBlockVertical(
      LevelAccessor levelAccessor,
      BlockPos startPos,
      Direction searchDirection,
      Predicate<BlockState> predicate,
      Predicate<BlockState> stoppingPredicate,
      int searchDistance
   ) {
      MutableBlockPos mutable = startPos.mutable();

      for (int i = 1; i < searchDistance; i++) {
         mutable.move(searchDirection);
         BlockState blockState = levelAccessor.getBlockState(mutable);
         if (stoppingPredicate.test(blockState)) {
            return Optional.of(mutable.immutable());
         }

         if (!predicate.test(blockState) || levelAccessor.isOutsideBuildHeight(mutable.getY())) {
            return Optional.empty();
         }
      }

      return Optional.empty();
   }

   private static DripstoneThickness calculateIcicleThickness(LevelReader levelReader, BlockPos blockPos) {
      BlockState blockBelow = levelReader.getBlockState(blockPos.relative(Direction.DOWN));
      BlockState blockAbove = levelReader.getBlockState(blockPos.relative(Direction.UP));
      if (!blockBelow.is((Block)BlockModule.ICICLE.get())) {
         return DripstoneThickness.TIP;
      } else if (blockBelow.getValue(THICKNESS) == DripstoneThickness.TIP) {
         return DripstoneThickness.FRUSTUM;
      } else {
         return !blockAbove.is((Block)BlockModule.ICICLE.get()) ? DripstoneThickness.BASE : DripstoneThickness.MIDDLE;
      }
   }

   private static void spawnFallingIcicle(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos) {
      MutableBlockPos mutable = blockPos.mutable();
      BlockState blockState2 = blockState;

      while (blockState2.is((Block)BlockModule.ICICLE.get())) {
         FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(serverLevel, mutable, blockState2);
         if (isTip(blockState2)) {
            int icicleSize = Math.max(1 + blockPos.getY() - mutable.getY(), 6);
            float damagePerBlock = 0.5F * icicleSize;
            fallingBlockEntity.setHurtsEntities(damagePerBlock, 10);
            break;
         }

         mutable.move(Direction.DOWN);
         blockState2 = serverLevel.getBlockState(mutable);
      }
   }

   private static void tryToGrowIcicle(BlockState rootIcicleBlock, ServerLevel serverLevel, BlockPos rootIcicleBlockPos) {
      if (canGrow(serverLevel.getBlockState(rootIcicleBlockPos.above(1)), serverLevel.getBlockState(rootIcicleBlockPos.above(2)))) {
         BlockPos tipPos = findTip(rootIcicleBlock, serverLevel, rootIcicleBlockPos, 6);
         if (tipPos != null && isTip(serverLevel.getBlockState(tipPos))) {
            if (doesTipHaveRoomToGrow(serverLevel, tipPos)) {
               growIcicle(serverLevel, tipPos);
            }
         }
      }
   }

   private static boolean canGrow(BlockState blockState, BlockState blockStateAbove) {
      return blockState.is(BlockTags.ICE) && blockStateAbove.is(Blocks.WATER) && blockStateAbove.getFluidState().isSource();
   }

   private static boolean doesTipHaveRoomToGrow(ServerLevel serverLevel, BlockPos tipPos) {
      BlockPos belowTipPos = tipPos.relative(Direction.DOWN);
      BlockState belowTipBlock = serverLevel.getBlockState(belowTipPos);
      return !belowTipBlock.getFluidState().isEmpty() ? false : belowTipBlock.isAir();
   }

   private static void growIcicle(ServerLevel serverLevel, BlockPos tipPos) {
      BlockPos belowTipBlock = tipPos.below();
      createIcicle(serverLevel, belowTipBlock, DripstoneThickness.TIP);
   }

   private static void createIcicle(LevelAccessor levelAccessor, BlockPos blockPos, DripstoneThickness thickness) {
      BlockState blockState = (BlockState)((BlockState)((Block)BlockModule.ICICLE.get()).defaultBlockState().setValue(THICKNESS, thickness))
         .setValue(WATERLOGGED, levelAccessor.getFluidState(blockPos).getType() == Fluids.WATER);
      levelAccessor.setBlock(blockPos, blockState, 3);
   }

   @Nullable
   public static BlockPos findIcicleTipAboveCauldron(Level level, BlockPos blockPos) {
      return findBlockVertical(level, blockPos, Direction.UP, BlockStateBase::isAir, IcicleBlock::canDrip, 11).orElse(null);
   }

   private static boolean canDrip(BlockState blockState) {
      return blockState.getBlock() == BlockModule.ICICLE.get()
         && blockState.getValue(THICKNESS) == DripstoneThickness.TIP
         && !(Boolean)blockState.getValue(WATERLOGGED);
   }

   private static Optional<Fluid> getFluidAboveIcicle(Level level, BlockPos blockPos) {
      BlockPos rootPos = findRoot(level, blockPos);
      return rootPos == null ? Optional.empty() : Optional.of(level.getFluidState(rootPos.above()).getType());
   }

   private static boolean canFillCauldron(Fluid fluid) {
      return fluid == Fluids.WATER;
   }

   private static void spawnDripParticle(Level level, BlockPos blockPos, BlockState blockState, Fluid fluid) {
      Vec3 vec3 = blockState.getOffset(level, blockPos);
      double d = 0.0625;
      double x = blockPos.getX() + 0.5 + vec3.x;
      double y = blockPos.getY() + 1 - 0.6875F - d;
      double z = blockPos.getZ() + 0.5 + vec3.z;
      level.addParticle(ParticleTypes.DRIPPING_DRIPSTONE_WATER, x, y, z, 0.0, 0.0, 0.0);
   }

   public static Fluid getCauldronFillFluidType(Level level, BlockPos blockPos) {
      return getFluidAboveIcicle(level, blockPos).filter(IcicleBlock::canFillCauldron).orElse(null);
   }

   private static void maybeFillCauldron(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, float f) {
      if (!(f > 0.17578125F)) {
         if (isTop(blockState, serverLevel, blockPos)) {
            Fluid fluid = getCauldronFillFluidType(serverLevel, blockPos);
            if (fluid == Fluids.WATER) {
               float dripChance = 0.17578125F;
               if (!(f >= dripChance)) {
                  BlockPos tipPos = findTip(blockState, serverLevel, blockPos, 11);
                  if (tipPos != null) {
                     Predicate<BlockState> isValidCauldron = state -> state.getBlock() instanceof AbstractCauldronBlock
                        && ((AbstractCauldronBlockAccessor)state.getBlock()).callCanReceiveStalactiteDrip(fluid);
                     BlockPos cauldronPos = findBlockVertical(serverLevel, tipPos, Direction.DOWN, BlockStateBase::isAir, isValidCauldron, 11).orElse(null);
                     if (cauldronPos != null) {
                        int yDist = tipPos.getY() - cauldronPos.getY();
                        int timeUntilCauldronTick = 50 + yDist;
                        BlockState cauldronBlockState = serverLevel.getBlockState(cauldronPos);
                        serverLevel.scheduleTick(cauldronPos, cauldronBlockState.getBlock(), timeUntilCauldronTick);
                     }
                  }
               }
            }
         }
      }
   }
}
