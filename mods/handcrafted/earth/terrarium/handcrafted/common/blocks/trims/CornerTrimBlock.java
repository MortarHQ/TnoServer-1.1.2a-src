package earth.terrarium.handcrafted.common.blocks.trims;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.handcrafted.common.blocks.base.Hammerable;
import earth.terrarium.handcrafted.common.blocks.base.SimpleBlock;
import earth.terrarium.handcrafted.common.blocks.base.properties.TrimProperty;
import earth.terrarium.handcrafted.common.constants.ConstantComponents;
import earth.terrarium.handcrafted.common.registry.ModSoundEvents;
import earth.terrarium.handcrafted.common.utils.TooltipUtils;
import java.util.List;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class CornerTrimBlock extends SimpleBlock implements Hammerable {
   public static final MapCodec<CornerTrimBlock> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(Codec.BOOL.fieldOf("wood").forGetter(CornerTrimBlock::isWood), propertiesCodec()).apply(instance, CornerTrimBlock::new)
   );
   public static final EnumProperty<TrimProperty> SHAPE = EnumProperty.create("trim", TrimProperty.class);
   public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;
   public static final VoxelShape NORMAL_TOP_NORTH = Stream.of(
         Block.box(4.0, 0.0, 4.0, 12.0, 6.0, 8.0),
         Block.box(4.0, 8.0, 10.0, 12.0, 12.0, 16.0),
         Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 4.0),
         Block.box(0.0, 12.0, 4.0, 16.0, 16.0, 16.0),
         Block.box(2.0, 6.0, 4.0, 14.0, 12.0, 10.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape NORMAL_TOP_EAST = Stream.of(
         Block.box(8.0, 0.0, 4.0, 12.0, 6.0, 12.0),
         Block.box(0.0, 8.0, 4.0, 6.0, 12.0, 12.0),
         Block.box(12.0, 0.0, 0.0, 16.0, 16.0, 16.0),
         Block.box(0.0, 12.0, 0.0, 12.0, 16.0, 16.0),
         Block.box(6.0, 6.0, 2.0, 12.0, 12.0, 14.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape NORMAL_TOP_SOUTH = Stream.of(
         Block.box(4.0, 0.0, 8.0, 12.0, 6.0, 12.0),
         Block.box(4.0, 8.0, 0.0, 12.0, 12.0, 6.0),
         Block.box(0.0, 0.0, 12.0, 16.0, 16.0, 16.0),
         Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 12.0),
         Block.box(2.0, 6.0, 6.0, 14.0, 12.0, 12.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape NORMAL_TOP_WEST = Stream.of(
         Block.box(4.0, 0.0, 4.0, 8.0, 6.0, 12.0),
         Block.box(10.0, 8.0, 4.0, 16.0, 12.0, 12.0),
         Block.box(0.0, 0.0, 0.0, 4.0, 16.0, 16.0),
         Block.box(4.0, 12.0, 0.0, 16.0, 16.0, 16.0),
         Block.box(4.0, 6.0, 2.0, 10.0, 12.0, 14.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape NORMAL_BOTTOM_NORTH = Stream.of(
         Block.box(4.0, 10.0, 4.0, 12.0, 16.0, 8.0),
         Block.box(4.0, 4.0, 10.0, 12.0, 8.0, 16.0),
         Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 4.0),
         Block.box(0.0, 0.0, 4.0, 16.0, 4.0, 16.0),
         Block.box(2.0, 4.0, 4.0, 14.0, 10.0, 10.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape NORMAL_BOTTOM_EAST = Stream.of(
         Block.box(8.0, 10.0, 4.0, 12.0, 16.0, 12.0),
         Block.box(0.0, 4.0, 4.0, 6.0, 8.0, 12.0),
         Block.box(12.0, 0.0, 0.0, 16.0, 16.0, 16.0),
         Block.box(0.0, 0.0, 0.0, 12.0, 4.0, 16.0),
         Block.box(6.0, 4.0, 2.0, 12.0, 10.0, 14.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape NORMAL_BOTTOM_SOUTH = Stream.of(
         Block.box(4.0, 10.0, 8.0, 12.0, 16.0, 12.0),
         Block.box(4.0, 4.0, 0.0, 12.0, 8.0, 6.0),
         Block.box(0.0, 0.0, 12.0, 16.0, 16.0, 16.0),
         Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 12.0),
         Block.box(2.0, 4.0, 6.0, 14.0, 10.0, 12.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape NORMAL_BOTTOM_WEST = Stream.of(
         Block.box(4.0, 10.0, 4.0, 8.0, 16.0, 12.0),
         Block.box(10.0, 4.0, 4.0, 16.0, 8.0, 12.0),
         Block.box(0.0, 0.0, 0.0, 4.0, 16.0, 16.0),
         Block.box(4.0, 0.0, 0.0, 16.0, 4.0, 16.0),
         Block.box(4.0, 4.0, 2.0, 10.0, 10.0, 14.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape THICC_TOP_NORTH = Stream.of(
         Block.box(0.0, 0.0, 4.0, 16.0, 6.0, 8.0),
         Block.box(0.0, 8.0, 10.0, 16.0, 12.0, 16.0),
         Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 4.0),
         Block.box(0.0, 12.0, 4.0, 16.0, 16.0, 16.0),
         Block.box(0.0, 6.0, 4.0, 16.0, 12.0, 10.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape THICC_TOP_EAST = Stream.of(
         Block.box(8.0, 0.0, 0.0, 12.0, 6.0, 16.0),
         Block.box(0.0, 8.0, 0.0, 6.0, 12.0, 16.0),
         Block.box(12.0, 0.0, 0.0, 16.0, 16.0, 16.0),
         Block.box(0.0, 12.0, 0.0, 12.0, 16.0, 16.0),
         Block.box(6.0, 6.0, 0.0, 12.0, 12.0, 16.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape THICC_TOP_SOUTH = Stream.of(
         Block.box(0.0, 0.0, 8.0, 16.0, 6.0, 12.0),
         Block.box(0.0, 8.0, 0.0, 16.0, 12.0, 6.0),
         Block.box(0.0, 0.0, 12.0, 16.0, 16.0, 16.0),
         Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 12.0),
         Block.box(0.0, 6.0, 6.0, 16.0, 12.0, 12.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape THICC_TOP_WEST = Stream.of(
         Block.box(4.0, 0.0, 0.0, 8.0, 6.0, 16.0),
         Block.box(10.0, 8.0, 0.0, 16.0, 12.0, 16.0),
         Block.box(0.0, 0.0, 0.0, 4.0, 16.0, 16.0),
         Block.box(4.0, 12.0, 0.0, 16.0, 16.0, 16.0),
         Block.box(4.0, 6.0, 0.0, 10.0, 12.0, 16.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape THICC_BOTTOM_NORTH = Stream.of(
         Block.box(0.0, 10.0, 4.0, 16.0, 16.0, 8.0),
         Block.box(0.0, 4.0, 10.0, 16.0, 8.0, 16.0),
         Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 4.0),
         Block.box(0.0, 0.0, 4.0, 16.0, 4.0, 16.0),
         Block.box(0.0, 4.0, 4.0, 16.0, 10.0, 10.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape THICC_BOTTOM_EAST = Stream.of(
         Block.box(8.0, 10.0, 0.0, 12.0, 16.0, 16.0),
         Block.box(0.0, 4.0, 0.0, 6.0, 8.0, 16.0),
         Block.box(12.0, 0.0, 0.0, 16.0, 16.0, 16.0),
         Block.box(0.0, 0.0, 0.0, 12.0, 4.0, 16.0),
         Block.box(6.0, 4.0, 0.0, 12.0, 10.0, 16.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape THICC_BOTTOM_SOUTH = Stream.of(
         Block.box(0.0, 10.0, 8.0, 16.0, 16.0, 12.0),
         Block.box(0.0, 4.0, 0.0, 16.0, 8.0, 6.0),
         Block.box(0.0, 0.0, 12.0, 16.0, 16.0, 16.0),
         Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 12.0),
         Block.box(0.0, 4.0, 6.0, 16.0, 10.0, 12.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape THICC_BOTTOM_WEST = Stream.of(
         Block.box(4.0, 10.0, 0.0, 8.0, 16.0, 16.0),
         Block.box(10.0, 4.0, 0.0, 16.0, 8.0, 16.0),
         Block.box(0.0, 0.0, 0.0, 4.0, 16.0, 16.0),
         Block.box(4.0, 0.0, 0.0, 16.0, 4.0, 16.0),
         Block.box(4.0, 4.0, 0.0, 10.0, 10.0, 16.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape THIN_TOP_NORTH = Stream.of(
         Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 4.0),
         Block.box(0.0, 12.0, 4.0, 16.0, 16.0, 16.0),
         Block.box(6.0, 0.0, 4.0, 10.0, 12.0, 6.0),
         Block.box(6.0, 10.0, 6.0, 10.0, 12.0, 16.0),
         Block.box(4.0, 6.0, 4.0, 12.0, 12.0, 10.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape THIN_TOP_EAST = Stream.of(
         Block.box(12.0, 0.0, 0.0, 16.0, 16.0, 16.0),
         Block.box(0.0, 12.0, 0.0, 12.0, 16.0, 16.0),
         Block.box(10.0, 0.0, 6.0, 12.0, 12.0, 10.0),
         Block.box(0.0, 10.0, 6.0, 10.0, 12.0, 10.0),
         Block.box(6.0, 6.0, 4.0, 12.0, 12.0, 12.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape THIN_TOP_SOUTH = Stream.of(
         Block.box(0.0, 0.0, 12.0, 16.0, 16.0, 16.0),
         Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 12.0),
         Block.box(6.0, 0.0, 10.0, 10.0, 12.0, 12.0),
         Block.box(6.0, 10.0, 0.0, 10.0, 12.0, 10.0),
         Block.box(4.0, 6.0, 6.0, 12.0, 12.0, 12.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape THIN_TOP_WEST = Stream.of(
         Block.box(0.0, 0.0, 0.0, 4.0, 16.0, 16.0),
         Block.box(4.0, 12.0, 0.0, 16.0, 16.0, 16.0),
         Block.box(4.0, 0.0, 6.0, 6.0, 12.0, 10.0),
         Block.box(6.0, 10.0, 6.0, 16.0, 12.0, 10.0),
         Block.box(4.0, 6.0, 4.0, 10.0, 12.0, 12.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape THIN_BOTTOM_NORTH = Stream.of(
         Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 4.0),
         Block.box(0.0, 0.0, 4.0, 16.0, 4.0, 16.0),
         Block.box(6.0, 4.0, 4.0, 10.0, 16.0, 6.0),
         Block.box(6.0, 4.0, 6.0, 10.0, 6.0, 16.0),
         Block.box(4.0, 4.0, 4.0, 12.0, 10.0, 10.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape THIN_BOTTOM_EAST = Stream.of(
         Block.box(12.0, 0.0, 0.0, 16.0, 16.0, 16.0),
         Block.box(0.0, 0.0, 0.0, 12.0, 4.0, 16.0),
         Block.box(10.0, 4.0, 6.0, 12.0, 16.0, 10.0),
         Block.box(0.0, 4.0, 6.0, 10.0, 6.0, 10.0),
         Block.box(6.0, 4.0, 4.0, 12.0, 10.0, 12.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape THIN_BOTTOM_SOUTH = Stream.of(
         Block.box(0.0, 0.0, 12.0, 16.0, 16.0, 16.0),
         Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 12.0),
         Block.box(6.0, 4.0, 10.0, 10.0, 16.0, 12.0),
         Block.box(6.0, 4.0, 0.0, 10.0, 6.0, 10.0),
         Block.box(4.0, 4.0, 6.0, 12.0, 10.0, 12.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   public static final VoxelShape THIN_BOTTOM_WEST = Stream.of(
         Block.box(0.0, 0.0, 0.0, 4.0, 16.0, 16.0),
         Block.box(4.0, 0.0, 0.0, 16.0, 4.0, 16.0),
         Block.box(4.0, 4.0, 6.0, 6.0, 16.0, 10.0),
         Block.box(6.0, 4.0, 6.0, 16.0, 6.0, 10.0),
         Block.box(4.0, 4.0, 4.0, 10.0, 10.0, 12.0)
      )
      .reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR))
      .get();
   private final boolean wood;

   public CornerTrimBlock(boolean wood, Properties properties) {
      super(properties);
      this.wood = wood;
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(SHAPE, TrimProperty.NORMAL)).setValue(HALF, Half.BOTTOM))
               .setValue(FACING, Direction.NORTH))
            .setValue(WATERLOGGED, false)
      );
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, SHAPE, HALF, WATERLOGGED});
   }

   @Override
   public BlockState updateShape(
      BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos
   ) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
      }

      return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
   }

   @Override
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      Direction direction = context.getClickedFace();
      BlockPos pos = context.getClickedPos();
      FluidState fluidState = context.getLevel().getFluidState(pos);
      return (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection()))
            .setValue(
               HALF, direction == Direction.DOWN || direction != Direction.UP && context.getClickLocation().y - pos.getY() > 0.5 ? Half.TOP : Half.BOTTOM
            ))
         .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
   }

   @Override
   public void onHammer(Level level, BlockPos pos, BlockState state, Direction side, Player user, Vec3 hitPos) {
      level.setBlockAndUpdate(pos, (BlockState)state.cycle(SHAPE));
      level.playSound(
         null, pos, this.wood ? (SoundEvent)ModSoundEvents.HAMMER_WOOD.get() : (SoundEvent)ModSoundEvents.HAMMER_STONE.get(), SoundSource.BLOCKS, 1.0F, 1.0F
      );
   }

   public boolean isWood() {
      return this.wood;
   }

   public VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
      return switch ((Half)state.getValue(HALF)) {
         case TOP -> {
            switch ((TrimProperty)state.getValue(SHAPE)) {
               case NORMAL:
                  switch ((Direction)state.getValue(FACING)) {
                     case EAST:
                        yield NORMAL_TOP_EAST;
                     case SOUTH:
                        yield NORMAL_TOP_SOUTH;
                     case WEST:
                        yield NORMAL_TOP_WEST;
                     default:
                        yield NORMAL_TOP_NORTH;
                  }
               case THIN:
                  switch ((Direction)state.getValue(FACING)) {
                     case EAST:
                        yield THIN_TOP_EAST;
                     case SOUTH:
                        yield THIN_TOP_SOUTH;
                     case WEST:
                        yield THIN_TOP_WEST;
                     default:
                        yield THIN_TOP_NORTH;
                  }
               case THICC:
                  switch ((Direction)state.getValue(FACING)) {
                     case EAST:
                        yield THICC_TOP_EAST;
                     case SOUTH:
                        yield THICC_TOP_SOUTH;
                     case WEST:
                        yield THICC_TOP_WEST;
                     default:
                        yield THICC_TOP_NORTH;
                  }
               default:
                  throw new MatchException(null, null);
            }
         }
         case BOTTOM -> {
            switch ((TrimProperty)state.getValue(SHAPE)) {
               case NORMAL:
                  switch ((Direction)state.getValue(FACING)) {
                     case EAST:
                        yield NORMAL_BOTTOM_EAST;
                     case SOUTH:
                        yield NORMAL_BOTTOM_SOUTH;
                     case WEST:
                        yield NORMAL_BOTTOM_WEST;
                     default:
                        yield NORMAL_BOTTOM_NORTH;
                  }
               case THIN:
                  switch ((Direction)state.getValue(FACING)) {
                     case EAST:
                        yield THIN_BOTTOM_EAST;
                     case SOUTH:
                        yield THIN_BOTTOM_SOUTH;
                     case WEST:
                        yield THIN_BOTTOM_WEST;
                     default:
                        yield THIN_BOTTOM_NORTH;
                  }
               case THICC:
                  switch ((Direction)state.getValue(FACING)) {
                     case EAST:
                        yield THICC_BOTTOM_EAST;
                     case SOUTH:
                        yield THICC_BOTTOM_SOUTH;
                     case WEST:
                        yield THICC_BOTTOM_WEST;
                     default:
                        yield THICC_BOTTOM_NORTH;
                  }
               default:
                  throw new MatchException(null, null);
            }
         }
         default -> throw new MatchException(null, null);
      };
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      TooltipUtils.addDescriptionComponent(tooltipComponents, ConstantComponents.HAMMER_USE_SHAPE);
   }

   protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
      return CODEC;
   }
}
