package cn.leolezury.eternalstarlight.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class IcicleBlock extends Block implements SimpleWaterloggedBlock {
   public static final MapCodec<IcicleBlock> CODEC = simpleCodec(IcicleBlock::new);
   public static final DirectionProperty TIP_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
   public static final EnumProperty<IcicleBlock.IcicleThickness> THICKNESS = EnumProperty.create("icicle_thickness", IcicleBlock.IcicleThickness.class);
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   private static final VoxelShape TIP_SHAPE_UP = Block.box(5.0, 0.0, 5.0, 11.0, 11.0, 11.0);
   private static final VoxelShape TIP_SHAPE_DOWN = Block.box(5.0, 5.0, 5.0, 11.0, 16.0, 11.0);
   private static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);

   public IcicleBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(TIP_DIRECTION, Direction.UP))
               .setValue(THICKNESS, IcicleBlock.IcicleThickness.TIP))
            .setValue(WATERLOGGED, false)
      );
   }

   protected MapCodec<IcicleBlock> codec() {
      return CODEC;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return this.getSuitableState(
         (BlockState)this.defaultBlockState().setValue(TIP_DIRECTION, context.getNearestLookingVerticalDirection().getOpposite()),
         context.getLevel(),
         context.getClickedPos()
      );
   }

   protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
      }

      BlockState newState = direction.getAxis() == Axis.Y ? this.getSuitableState(state, level, pos) : state;
      if (newState.isAir() && state.getValue(TIP_DIRECTION) == Direction.DOWN) {
         level.scheduleTick(pos, this, 2);
         newState = state;
      }

      return newState;
   }

   protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (this.getSuitableState(state, level, pos).isAir()) {
         this.spawnFallingStalactite(state, level, pos);
      }
   }

   protected void onProjectileHit(Level level, BlockState state, BlockHitResult hitResult, Projectile projectile) {
      if (!level.isClientSide) {
         BlockPos pos = hitResult.getBlockPos();
         if (projectile.mayInteract(level, pos) && projectile.mayBreak(level) && projectile.getDeltaMovement().length() > 0.6) {
            level.destroyBlock(pos, true);
         }
      }
   }

   public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
      if (state.getValue(TIP_DIRECTION) == Direction.UP && state.getValue(THICKNESS) == IcicleBlock.IcicleThickness.TIP) {
         entity.causeFallDamage(fallDistance + 2.0F, 2.0F, level.damageSources().stalagmite());
      } else {
         super.fallOn(level, state, pos, entity, fallDistance);
      }
   }

   private BlockState getSuitableState(BlockState state, LevelReader level, BlockPos pos) {
      Direction tipDir = (Direction)state.getValue(TIP_DIRECTION);
      BlockPos tipPos = this.findTip(state, level, pos);
      if (tipPos == null) {
         return Blocks.AIR.defaultBlockState();
      }

      BlockPos basePos = this.findBase(state, level, pos);
      if (basePos == null) {
         return Blocks.AIR.defaultBlockState();
      }

      if (!this.canSurvive(state, level, pos)) {
         return Blocks.AIR.defaultBlockState();
      }

      switch (tipPos.distManhattan(basePos)) {
         case 0:
            return (BlockState)state.setValue(THICKNESS, IcicleBlock.IcicleThickness.TIP);
         case 1:
            return (BlockState)state.setValue(THICKNESS, pos.equals(tipPos) ? IcicleBlock.IcicleThickness.TIP : IcicleBlock.IcicleThickness.TOP);
         case 2:
            BlockPos topPos = basePos.relative(tipDir, 1);
            IcicleBlock.IcicleThickness thickness;
            if (pos.equals(tipPos)) {
               thickness = IcicleBlock.IcicleThickness.TIP;
            } else if (pos.equals(topPos)) {
               thickness = IcicleBlock.IcicleThickness.TOP;
            } else {
               thickness = IcicleBlock.IcicleThickness.BASE;
            }

            return (BlockState)state.setValue(THICKNESS, thickness);
         case 3:
            BlockPos topPos = basePos.relative(tipDir, 2);
            BlockPos middlePos = basePos.relative(tipDir, 1);
            IcicleBlock.IcicleThickness thickness;
            if (pos.equals(tipPos)) {
               thickness = IcicleBlock.IcicleThickness.TIP;
            } else if (pos.equals(topPos)) {
               thickness = IcicleBlock.IcicleThickness.TOP;
            } else if (pos.equals(middlePos)) {
               thickness = IcicleBlock.IcicleThickness.MIDDLE;
            } else {
               thickness = IcicleBlock.IcicleThickness.BASE;
            }

            return (BlockState)state.setValue(THICKNESS, thickness);
         default:
            return state;
      }
   }

   protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      Direction tipDir = (Direction)state.getValue(TIP_DIRECTION);
      BlockPos tipPos = this.findTip(state, level, pos);
      if (tipPos == null) {
         return false;
      }

      BlockPos basePos = this.findBase(state, level, pos);
      if (basePos == null) {
         return false;
      }

      BlockState support = level.getBlockState(pos.relative(tipDir.getOpposite()));
      return support.isFaceSturdy(level, pos.relative(tipDir.getOpposite()), tipDir)
         || support.is(this) && (!support.hasProperty(TIP_DIRECTION) || support.getValue(TIP_DIRECTION) == state.getValue(TIP_DIRECTION));
   }

   private BlockPos findTip(BlockState state, LevelReader level, BlockPos pos) {
      Direction tipDir = (Direction)state.getValue(TIP_DIRECTION);
      BlockPos tipPos = null;

      for (int i = 0; i < 4 && (i == 0 || level.getBlockState(pos.relative(tipDir, i)).is(this)); i++) {
         if (i == 0) {
            if (!level.getBlockState(pos.relative(tipDir, i + 1)).is(this)
               || level.getBlockState(pos.relative(tipDir, i + 1)).hasProperty(TIP_DIRECTION)
                  && level.getBlockState(pos.relative(tipDir, i + 1)).getValue(TIP_DIRECTION) != tipDir) {
               tipPos = pos.relative(tipDir, i);
            }
         } else if (level.getBlockState(pos.relative(tipDir, i)).is(this)
            && level.getBlockState(pos.relative(tipDir, i)).getValue(TIP_DIRECTION) == tipDir
            && (
               !level.getBlockState(pos.relative(tipDir, i + 1)).is(this)
                  || level.getBlockState(pos.relative(tipDir, i + 1)).hasProperty(TIP_DIRECTION)
                     && level.getBlockState(pos.relative(tipDir, i + 1)).getValue(TIP_DIRECTION)
                        != level.getBlockState(pos.relative(tipDir, i)).getValue(TIP_DIRECTION)
            )) {
            tipPos = pos.relative(tipDir, i);
         }
      }

      return tipPos;
   }

   private BlockPos findBase(BlockState state, LevelReader level, BlockPos pos) {
      Direction tipDir = (Direction)state.getValue(TIP_DIRECTION);
      BlockPos basePos = null;

      for (int i = 0; i < 4 && (i == 0 || level.getBlockState(pos.relative(tipDir.getOpposite(), i)).is(this)); i++) {
         if (i == 0) {
            if (!level.getBlockState(pos.relative(tipDir.getOpposite(), i + 1)).is(this)
               || level.getBlockState(pos.relative(tipDir.getOpposite(), i + 1)).hasProperty(TIP_DIRECTION)
                  && level.getBlockState(pos.relative(tipDir.getOpposite(), i + 1)).getValue(TIP_DIRECTION) != tipDir) {
               basePos = pos.relative(tipDir.getOpposite(), i);
            }
         } else if (level.getBlockState(pos.relative(tipDir.getOpposite(), i)).is(this)
            && level.getBlockState(pos.relative(tipDir.getOpposite(), i)).getValue(TIP_DIRECTION) == tipDir
            && (
               !level.getBlockState(pos.relative(tipDir.getOpposite(), i + 1)).is(this)
                  || level.getBlockState(pos.relative(tipDir.getOpposite(), i + 1)).hasProperty(TIP_DIRECTION)
                     && level.getBlockState(pos.relative(tipDir.getOpposite(), i + 1)).getValue(TIP_DIRECTION)
                        != level.getBlockState(pos.relative(tipDir.getOpposite(), i)).getValue(TIP_DIRECTION)
            )) {
            basePos = pos.relative(tipDir.getOpposite(), i);
         }
      }

      return basePos;
   }

   private void spawnFallingStalactite(BlockState state, Level level, BlockPos pos) {
      MutableBlockPos mutable = pos.mutable();
      BlockState blockState = state;

      while (blockState.is(this) && blockState.getValue(TIP_DIRECTION) == Direction.DOWN) {
         FallingBlockEntity fallingblockentity = FallingBlockEntity.fall(level, mutable, blockState);
         if (blockState.getValue(THICKNESS) == IcicleBlock.IcicleThickness.TIP) {
            fallingblockentity.setHurtsEntities(5.0F, 40);
            break;
         }

         level.destroyBlock(mutable, false);
         mutable.move(Direction.DOWN);
         blockState = level.getBlockState(mutable);
      }
   }

   protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
      return blockState.getValue(THICKNESS) == IcicleBlock.IcicleThickness.TIP
         ? (blockState.getValue(TIP_DIRECTION) == Direction.UP ? TIP_SHAPE_UP : TIP_SHAPE_DOWN)
         : SHAPE;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{TIP_DIRECTION, THICKNESS, WATERLOGGED});
   }

   public enum IcicleThickness implements StringRepresentable {
      TIP("tip"),
      TOP("top"),
      MIDDLE("middle"),
      BASE("base");

      private final String name;

      IcicleThickness(final String string) {
         this.name = string;
      }

      @Override
      public String toString() {
         return this.name;
      }

      public String getSerializedName() {
         return this.name;
      }
   }
}
