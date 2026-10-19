package earth.terrarium.handcrafted.common.blocks.trims;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.handcrafted.common.blocks.base.Hammerable;
import earth.terrarium.handcrafted.common.blocks.base.properties.TrimProperty;
import earth.terrarium.handcrafted.common.constants.ConstantComponents;
import earth.terrarium.handcrafted.common.registry.ModSoundEvents;
import earth.terrarium.handcrafted.common.utils.TooltipUtils;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class PillarTrimBlock extends FaceAttachedHorizontalDirectionalBlock implements Hammerable, SimpleWaterloggedBlock {
   public static final MapCodec<PillarTrimBlock> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(Codec.BOOL.fieldOf("wood").forGetter(PillarTrimBlock::isWood), propertiesCodec()).apply(instance, PillarTrimBlock::new)
   );
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   public static final IntegerProperty TYPE = IntegerProperty.create("type", 1, 2);
   public static final EnumProperty<TrimProperty> SHAPE = EnumProperty.create("trim", TrimProperty.class);
   public static final VoxelShape NORMAL_NORTH_SHAPE = Shapes.join(
      Block.box(4.0, 0.0, 8.0, 12.0, 16.0, 12.0), Block.box(0.0, 0.0, 12.0, 16.0, 16.0, 16.0), BooleanOp.OR
   );
   public static final VoxelShape NORMAL_EAST_SHAPE = Shapes.join(
      Block.box(0.0, 0.0, 0.0, 4.0, 16.0, 16.0), Block.box(4.0, 0.0, 4.0, 8.0, 16.0, 12.0), BooleanOp.OR
   );
   public static final VoxelShape NORMAL_SOUTH_SHAPE = Shapes.join(
      Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 4.0), Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 8.0), BooleanOp.OR
   );
   public static final VoxelShape NORMAL_WEST_SHAPE = Shapes.join(
      Block.box(12.0, 0.0, 0.0, 16.0, 16.0, 16.0), Block.box(8.0, 0.0, 4.0, 12.0, 16.0, 12.0), BooleanOp.OR
   );
   public static final VoxelShape NORMAL_UP_X_AXIS_SHAPE = Shapes.join(
      Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0), Block.box(0.0, 8.0, 4.0, 16.0, 12.0, 12.0), BooleanOp.OR
   );
   public static final VoxelShape NORMAL_UP_Z_AXIS_SHAPE = Shapes.join(
      Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0), Block.box(4.0, 8.0, 0.0, 12.0, 12.0, 16.0), BooleanOp.OR
   );
   public static final VoxelShape NORMAL_DOWN_X_AXIS_SHAPE = Shapes.join(
      Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0), Block.box(0.0, 4.0, 4.0, 16.0, 8.0, 12.0), BooleanOp.OR
   );
   public static final VoxelShape NORMAL_DOWN_Z_AXIS_SHAPE = Shapes.join(
      Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0), Block.box(4.0, 4.0, 0.0, 12.0, 8.0, 16.0), BooleanOp.OR
   );
   public static final VoxelShape THICC_NORTH_SHAPE = Block.box(0.0, 0.0, 8.0, 16.0, 16.0, 16.0);
   public static final VoxelShape THICC_EAST_SHAPE = Block.box(0.0, 0.0, 0.0, 8.0, 16.0, 16.0);
   public static final VoxelShape THICC_SOUTH_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 8.0);
   public static final VoxelShape THICC_WEST_SHAPE = Block.box(8.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   public static final VoxelShape THICC_UP_SHAPE = Block.box(0.0, 8.0, 0.0, 16.0, 16.0, 16.0);
   public static final VoxelShape THICC_DOWN_SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
   public static final VoxelShape THIN_NORTH_SHAPE = Shapes.join(
      Block.box(6.0, 0.0, 10.0, 10.0, 16.0, 12.0), Block.box(0.0, 0.0, 12.0, 16.0, 16.0, 16.0), BooleanOp.OR
   );
   public static final VoxelShape THIN_EAST_SHAPE = Shapes.join(
      Block.box(0.0, 0.0, 0.0, 4.0, 16.0, 16.0), Block.box(4.0, 0.0, 6.0, 6.0, 16.0, 10.0), BooleanOp.OR
   );
   public static final VoxelShape THIN_SOUTH_SHAPE = Shapes.join(
      Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 4.0), Block.box(6.0, 0.0, 4.0, 10.0, 16.0, 6.0), BooleanOp.OR
   );
   public static final VoxelShape THIN_WEST_SHAPE = Shapes.join(
      Block.box(12.0, 0.0, 0.0, 16.0, 16.0, 16.0), Block.box(10.0, 0.0, 6.0, 12.0, 16.0, 10.0), BooleanOp.OR
   );
   public static final VoxelShape THIN_UP_X_AXIS_SHAPE = Shapes.join(
      Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0), Block.box(0.0, 10.0, 6.0, 16.0, 12.0, 10.0), BooleanOp.OR
   );
   public static final VoxelShape THIN_UP_Z_AXIS_SHAPE = Shapes.join(
      Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0), Block.box(6.0, 10.0, 0.0, 10.0, 12.0, 16.0), BooleanOp.OR
   );
   public static final VoxelShape THIN_DOWN_X_AXIS_SHAPE = Shapes.join(
      Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0), Block.box(0.0, 4.0, 6.0, 16.0, 6.0, 10.0), BooleanOp.OR
   );
   public static final VoxelShape THIN_DOWN_Z_AXIS_SHAPE = Shapes.join(
      Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0), Block.box(6.0, 4.0, 0.0, 10.0, 6.0, 16.0), BooleanOp.OR
   );
   private final boolean wood;

   public PillarTrimBlock(boolean wood, Properties properties) {
      super(properties);
      this.wood = wood;
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(TYPE, 1)).setValue(SHAPE, TrimProperty.NORMAL))
                  .setValue(FACING, Direction.NORTH))
               .setValue(FACE, AttachFace.WALL))
            .setValue(WATERLOGGED, false)
      );
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, FACE, TYPE, SHAPE, WATERLOGGED});
   }

   public VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
      return switch ((TrimProperty)state.getValue(SHAPE)) {
         case NORMAL -> {
            switch ((AttachFace)state.getValue(FACE)) {
               case FLOOR:
                  yield ((Direction)state.getValue(FACING)).getAxis() == Axis.X ? NORMAL_DOWN_X_AXIS_SHAPE : NORMAL_DOWN_Z_AXIS_SHAPE;
               case CEILING:
                  yield ((Direction)state.getValue(FACING)).getAxis() == Axis.X ? NORMAL_UP_X_AXIS_SHAPE : NORMAL_UP_Z_AXIS_SHAPE;
               default:
                  switch ((Direction)state.getValue(FACING)) {
                     case EAST:
                        yield NORMAL_EAST_SHAPE;
                     case SOUTH:
                        yield NORMAL_SOUTH_SHAPE;
                     case WEST:
                        yield NORMAL_WEST_SHAPE;
                     default:
                        yield NORMAL_NORTH_SHAPE;
                  }
            }
         }
         case THICC -> {
            switch ((AttachFace)state.getValue(FACE)) {
               case FLOOR:
                  yield THICC_DOWN_SHAPE;
               case CEILING:
                  yield THICC_UP_SHAPE;
               default:
                  switch ((Direction)state.getValue(FACING)) {
                     case EAST:
                        yield THICC_EAST_SHAPE;
                     case SOUTH:
                        yield THICC_SOUTH_SHAPE;
                     case WEST:
                        yield THICC_WEST_SHAPE;
                     default:
                        yield THICC_NORTH_SHAPE;
                  }
            }
         }
         case THIN -> {
            switch ((AttachFace)state.getValue(FACE)) {
               case FLOOR:
                  yield ((Direction)state.getValue(FACING)).getAxis() == Axis.X ? THIN_DOWN_X_AXIS_SHAPE : THIN_DOWN_Z_AXIS_SHAPE;
               case CEILING:
                  yield ((Direction)state.getValue(FACING)).getAxis() == Axis.X ? THIN_UP_X_AXIS_SHAPE : THIN_UP_Z_AXIS_SHAPE;
               default:
                  switch ((Direction)state.getValue(FACING)) {
                     case EAST:
                        yield THIN_EAST_SHAPE;
                     case SOUTH:
                        yield THIN_SOUTH_SHAPE;
                     case WEST:
                        yield THIN_WEST_SHAPE;
                     default:
                        yield THIN_NORTH_SHAPE;
                  }
            }
         }
      };
   }

   @Override
   public void onHammer(Level level, BlockPos pos, BlockState state, Direction side, Player user, Vec3 hitPos) {
      if (user.isShiftKeyDown()) {
         level.setBlockAndUpdate(pos, (BlockState)state.cycle(TYPE));
      } else {
         level.setBlockAndUpdate(pos, (BlockState)state.cycle(SHAPE));
      }

      level.playSound(
         null, pos, this.wood ? (SoundEvent)ModSoundEvents.HAMMER_WOOD.get() : (SoundEvent)ModSoundEvents.HAMMER_STONE.get(), SoundSource.BLOCKS, 1.0F, 1.0F
      );
   }

   public boolean isWood() {
      return this.wood;
   }

   @NotNull
   public FluidState getFluidState(BlockState state) {
      return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   @NotNull
   public BlockState updateShape(
      BlockState state,
      @NotNull Direction direction,
      @NotNull BlockState neighborState,
      @NotNull LevelAccessor level,
      @NotNull BlockPos pos,
      @NotNull BlockPos neighborPos
   ) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
      }

      return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
   }

   public BlockState getStateForPlacement(BlockPlaceContext ctx) {
      FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
      BlockState placementState = super.getStateForPlacement(ctx);
      return placementState == null ? null : (BlockState)placementState.setValue(WATERLOGGED, fluidState.getType().equals(Fluids.WATER));
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      TooltipUtils.addDescriptionComponent(tooltipComponents, ConstantComponents.HAMMER_USE_SHAPE, ConstantComponents.HAMMER_USE_LOOK_SHIFT);
   }

   protected MapCodec<? extends FaceAttachedHorizontalDirectionalBlock> codec() {
      return CODEC;
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      return true;
   }
}
