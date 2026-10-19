package twilightforest.block;

import com.mojang.serialization.MapCodec;
import java.util.Map;
import java.util.Random;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PatchBlock extends TFPlantBlock {
   public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
   public static final BooleanProperty EAST = BlockStateProperties.EAST;
   public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
   public static final BooleanProperty WEST = BlockStateProperties.WEST;
   public static final MapCodec<PatchBlock> CODEC = simpleCodec(PatchBlock::new);
   private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION
      .entrySet()
      .stream()
      .filter(directionPropertyPair -> ((Direction)directionPropertyPair.getKey()).getAxis().isHorizontal())
      .collect(Util.toMap());

   public PatchBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(NORTH, false)).setValue(EAST, false))
               .setValue(SOUTH, false))
            .setValue(WEST, false)
      );
   }

   public static VoxelShape shapeFromLong(BlockState state, long seed) {
      boolean xConnect0 = (Boolean)state.getValue(EAST);
      boolean xConnect1 = (Boolean)state.getValue(WEST);
      boolean zConnect0 = (Boolean)state.getValue(SOUTH);
      boolean zConnect1 = (Boolean)state.getValue(NORTH);
      int xOff0 = (int)(seed >> 12 & 3L);
      int xOff1 = (int)(seed >> 15 & 3L);
      int zOff0 = (int)(seed >> 18 & 3L);
      int zOff1 = (int)(seed >> 21 & 3L);
      return Block.box(
         xConnect1 ? 0.0 : 1.0F + xOff1, 0.0, zConnect1 ? 0.0 : 1.0F + zOff1, xConnect0 ? 16.0 : 15.0F - xOff0, 1.0, zConnect0 ? 16.0 : 15.0F - zOff0
      );
   }

   public static VoxelShape shapeFromRandom(BlockState state, Random random) {
      return shapeFromLong(state, random.nextLong());
   }

   public static VoxelShape shapeFromPos(BlockState state, BlockPos pos) {
      return shapeFromRandom(state, new Random(state.getSeed(pos)));
   }

   public static BoundingBox AABBFromLong(long seed) {
      int xOff0 = (int)(seed >> 12 & 3L);
      int xOff1 = (int)(seed >> 15 & 3L);
      int zOff0 = (int)(seed >> 18 & 3L);
      int zOff1 = (int)(seed >> 21 & 3L);
      int x0 = 1 + xOff1;
      int z0 = 1 + zOff1;
      int x1 = 15 - xOff0;
      int z1 = 15 - zOff0;
      return new BoundingBox(x0, 0, z0, x1, 1, z1);
   }

   public static BoundingBox AABBFromRandom(RandomSource random) {
      return AABBFromLong(random.nextLong());
   }

   protected MapCodec<? extends BushBlock> codec() {
      return CODEC;
   }

   public BlockState updateShape(BlockState state, Direction dir, BlockState neighborUpdated, LevelAccessor accessor, BlockPos pos, BlockPos posNeighbor) {
      return dir.getAxis().isHorizontal()
         ? (BlockState)state.setValue((Property)PROPERTY_BY_DIRECTION.get(dir), neighborUpdated.getBlock() == this)
         : super.updateShape(state, dir, neighborUpdated, accessor, pos, posNeighbor);
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      return shapeFromPos(state, pos);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{NORTH, EAST, SOUTH, WEST});
   }

   protected BlockState rotate(BlockState state, Rotation rot) {
      return switch (rot) {
         case CLOCKWISE_180 -> (BlockState)((BlockState)((BlockState)((BlockState)state.setValue(NORTH, (Boolean)state.getValue(SOUTH)))
                  .setValue(EAST, (Boolean)state.getValue(WEST)))
               .setValue(SOUTH, (Boolean)state.getValue(NORTH)))
            .setValue(WEST, (Boolean)state.getValue(EAST));
         case COUNTERCLOCKWISE_90 -> (BlockState)((BlockState)((BlockState)((BlockState)state.setValue(NORTH, (Boolean)state.getValue(EAST)))
                  .setValue(EAST, (Boolean)state.getValue(SOUTH)))
               .setValue(SOUTH, (Boolean)state.getValue(WEST)))
            .setValue(WEST, (Boolean)state.getValue(NORTH));
         case CLOCKWISE_90 -> (BlockState)((BlockState)((BlockState)((BlockState)state.setValue(NORTH, (Boolean)state.getValue(WEST)))
                  .setValue(EAST, (Boolean)state.getValue(NORTH)))
               .setValue(SOUTH, (Boolean)state.getValue(EAST)))
            .setValue(WEST, (Boolean)state.getValue(SOUTH));
         default -> state;
      };
   }

   protected BlockState mirror(BlockState state, Mirror mirror) {
      return switch (mirror) {
         case LEFT_RIGHT -> (BlockState)((BlockState)state.setValue(NORTH, (Boolean)state.getValue(SOUTH))).setValue(SOUTH, (Boolean)state.getValue(NORTH));
         case FRONT_BACK -> (BlockState)((BlockState)state.setValue(EAST, (Boolean)state.getValue(WEST))).setValue(WEST, (Boolean)state.getValue(EAST));
         default -> super.mirror(state, mirror);
      };
   }
}
