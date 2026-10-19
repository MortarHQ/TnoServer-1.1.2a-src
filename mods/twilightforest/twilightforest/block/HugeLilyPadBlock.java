package twilightforest.block;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import twilightforest.enums.HugeLilypadPiece;

public class HugeLilyPadBlock extends WaterlilyBlock {
   public static final DirectionProperty FACING = TFHorizontalBlock.FACING;
   public static final EnumProperty<HugeLilypadPiece> PIECE = EnumProperty.create("piece", HugeLilypadPiece.class);
   private static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
   private boolean isSelfDestructing = false;

   public HugeLilyPadBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.getStateDefinition().any()).setValue(FACING, Direction.NORTH)).setValue(PIECE, HugeLilypadPiece.NW)
      );
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, PIECE});
   }

   public VoxelShape getShape(BlockState state, BlockGetter getter, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public VoxelShape getOcclusionShape(BlockState state, BlockGetter getter, BlockPos pos) {
      return Shapes.empty();
   }

   public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
      if (!this.isSelfDestructing) {
         this.setGiantBlockToAir(level, pos, state);
      }
   }

   private void setGiantBlockToAir(Level level, BlockPos pos, BlockState state) {
      this.isSelfDestructing = true;

      for (BlockPos check : this.getAllMyBlocks(pos, state)) {
         BlockState stateThere = level.getBlockState(check);
         if (stateThere.getBlock() == this) {
            level.destroyBlock(check, false);
         }
      }

      this.isSelfDestructing = false;
   }

   public List<BlockPos> getAllMyBlocks(BlockPos pos, BlockState state) {
      List<BlockPos> pieces = Lists.newArrayListWithCapacity(4);
      if (state.getBlock() == this) {
         BlockPos nwPos = switch ((HugeLilypadPiece)state.getValue(PIECE)) {
            case NE -> pos.west();
            case SE -> pos.north().west();
            case SW -> pos.north();
            default -> pos;
         };
         pieces.add(nwPos);
         pieces.add(nwPos.south());
         pieces.add(nwPos.east());
         pieces.add(nwPos.south().east());
      }

      return pieces;
   }
}
