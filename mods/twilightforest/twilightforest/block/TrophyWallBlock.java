package twilightforest.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import twilightforest.enums.BossVariant;

public class TrophyWallBlock extends AbstractTrophyBlock {
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   public static final MapCodec<TrophyWallBlock> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(BossVariant.CODEC.fieldOf("variant").forGetter(AbstractTrophyBlock::getVariant), propertiesCodec())
         .apply(instance, TrophyWallBlock::new)
   );
   private static final Map<Direction, VoxelShape> SHAPES = Maps.newEnumMap(
      ImmutableMap.of(
         Direction.NORTH,
         Block.box(4.0, 4.0, 8.0, 12.0, 12.0, 16.0),
         Direction.SOUTH,
         Block.box(4.0, 4.0, 0.0, 12.0, 12.0, 8.0),
         Direction.EAST,
         Block.box(0.0, 4.0, 4.0, 8.0, 12.0, 12.0),
         Direction.WEST,
         Block.box(8.0, 4.0, 4.0, 16.0, 12.0, 12.0)
      )
   );
   private static final Map<Direction, VoxelShape> YETI_SHAPES = Maps.newEnumMap(
      ImmutableMap.of(
         Direction.NORTH,
         Block.box(3.25, 4.0, 8.5, 12.75, 14.5, 16.0),
         Direction.SOUTH,
         Block.box(3.25, 4.0, 0.0, 12.75, 14.5, 7.5),
         Direction.EAST,
         Block.box(0.0, 4.0, 3.25, 7.5, 14.5, 12.75),
         Direction.WEST,
         Block.box(8.5, 4.0, 3.25, 16.0, 14.5, 12.75)
      )
   );

   public TrophyWallBlock(BossVariant variant, Properties properties) {
      super(variant, 0, properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(FACING, Direction.NORTH));
   }

   protected MapCodec<? extends BaseEntityBlock> codec() {
      return CODEC;
   }

   public String getDescriptionId() {
      return this.asItem().getDescriptionId();
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      if (((AbstractTrophyBlock)state.getBlock()).getVariant() == BossVariant.UR_GHAST) {
         return TrophyBlock.GHAST_SHAPE;
      } else {
         return ((AbstractTrophyBlock)state.getBlock()).getVariant() == BossVariant.ALPHA_YETI
            ? YETI_SHAPES.get(state.getValue(FACING))
            : SHAPES.get(state.getValue(FACING));
      }
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockState blockstate = this.defaultBlockState();
      BlockGetter getter = context.getLevel();
      BlockPos blockpos = context.getClickedPos();
      Direction[] adirection = context.getNearestLookingDirections();

      for (Direction direction : adirection) {
         if (direction.getAxis().isHorizontal()) {
            Direction direction1 = direction.getOpposite();
            blockstate = (BlockState)blockstate.setValue(FACING, direction1);
            if (!getter.getBlockState(blockpos.relative(direction)).canBeReplaced(context)) {
               return blockstate;
            }
         }
      }

      return null;
   }

   public BlockState rotate(BlockState state, Rotation rot) {
      return (BlockState)state.setValue(FACING, rot.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, POWERED});
   }
}
