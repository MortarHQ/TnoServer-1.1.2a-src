package net.unusual.block_factorys_bosses.block;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.unusual.block_factorys_bosses.block.entity.DragonBannerBlockEntity;
import org.jetbrains.annotations.Nullable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class DragonBannerBlock extends Block implements EntityBlock {
   public static final IntegerProperty ATTACHED = IntegerProperty.create("blockstate", 1, 2);
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   private static final VoxelShape NORTH_UNATTACHED = Shapes.or(
      box(-14.0, 7.0, 5.0, 30.0, 12.0, 9.0), new VoxelShape[]{box(-12.5, 6.5, 4.5, 28.5, 12.5, 9.5), box(3.0, 0.0, 4.0, 13.0, 15.0, 10.0)}
   );
   private static final VoxelShape EAST_UNATTACHED = Shapes.or(
      box(7.0, 7.0, -14.0, 11.0, 12.0, 30.0), new VoxelShape[]{box(6.5, 6.5, -12.5, 11.5, 12.5, 28.5), box(6.0, 0.0, 3.0, 12.0, 15.0, 13.0)}
   );
   private static final VoxelShape WEST_UNATTACHED = Shapes.or(
      box(5.0, 7.0, -14.0, 9.0, 12.0, 30.0), new VoxelShape[]{box(4.5, 6.5, -12.5, 9.5, 12.5, 28.5), box(4.0, 0.0, 3.0, 10.0, 15.0, 13.0)}
   );
   private static final VoxelShape SOUTH_UNATTACHED = Shapes.or(
      box(-14.0, 7.0, 7.0, 30.0, 12.0, 11.0), new VoxelShape[]{box(-12.5, 6.5, 6.5, 28.5, 12.5, 11.5), box(3.0, 0.0, 6.0, 13.0, 15.0, 12.0)}
   );
   private static final VoxelShape NORTH_ATTACHED = Shapes.or(NORTH_UNATTACHED, box(3.0, 0.0, 10.0, 13.0, 15.0, 16.0));
   private static final VoxelShape EAST_ATTACHED = Shapes.or(EAST_UNATTACHED, box(0.0, 0.0, 3.0, 6.0, 15.0, 13.0));
   private static final VoxelShape WEST_ATTACHED = Shapes.or(WEST_UNATTACHED, box(10.0, 0.0, 3.0, 16.0, 15.0, 13.0));
   private static final VoxelShape SOUTH_ATTACHED = Shapes.or(SOUTH_UNATTACHED, box(3.0, 0.0, 0.0, 13.0, 15.0, 6.0));
   public static final int UNATTACHED = 1;
   public static final int UN_UNATTACHED = 2;

   public DragonBannerBlock() {
      super(Properties.of().sound(SoundType.WOOD).strength(1.0F, 10.0F).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(ATTACHED, 1));
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
      return true;
   }

   public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
      return 0;
   }

   public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      return Shapes.empty();
   }

   public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
      if ((Integer)state.getValue(ATTACHED) == 1) {
         return switch ((Direction)state.getValue(FACING)) {
            case NORTH -> NORTH_UNATTACHED;
            case EAST -> EAST_UNATTACHED;
            case WEST -> WEST_UNATTACHED;
            default -> SOUTH_UNATTACHED;
         };
      } else {
         return switch ((Direction)state.getValue(FACING)) {
            case NORTH -> NORTH_ATTACHED;
            case EAST -> EAST_ATTACHED;
            case WEST -> WEST_ATTACHED;
            default -> SOUTH_ATTACHED;
         };
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, ATTACHED});
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return context.getClickedFace().getAxis() == Axis.Y
         ? (BlockState)((BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())).setValue(ATTACHED, 1)
         : (BlockState)((BlockState)this.defaultBlockState().setValue(FACING, context.getClickedFace())).setValue(ATTACHED, 2);
   }

   public BlockState rotate(BlockState state, Rotation rot) {
      return (BlockState)state.setValue(FACING, rot.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirrorIn) {
      return state.rotate(mirrorIn.getRotation((Direction)state.getValue(FACING)));
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
      return new DragonBannerBlockEntity(blockPos, blockState);
   }
}
