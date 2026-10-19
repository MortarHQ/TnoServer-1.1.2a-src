package net.unusual.block_factorys_bosses.block;

import com.mojang.datafixers.util.Pair;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PrisonDoorBlock extends Block {
   public static final IntegerProperty DOOR_PART = IntegerProperty.create("blockstate", 0, 1);
   public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
   private static final VoxelShape SOUTH = box(0.0, 0.0, 14.0, 16.0, 16.0, 16.0);
   private static final VoxelShape NORTH = box(0.0, 0.0, 0.0, 16.0, 16.0, 2.0);
   private static final VoxelShape EAST = box(14.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape WEST = box(0.0, 0.0, 0.0, 2.0, 16.0, 16.0);
   public static final int HINGE = 0;
   public static final int EDGE = 1;

   public PrisonDoorBlock() {
      super(Properties.of().sound(SoundType.CHAIN).strength(1.0F, 10.0F).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
      this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(DOOR_PART, 1)).setValue(FACING, Direction.NORTH));
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
      return switch ((Direction)state.getValue(FACING)) {
         case NORTH -> NORTH;
         case EAST -> EAST;
         case WEST -> WEST;
         default -> SOUTH;
      };
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, DOOR_PART});
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockPos pos = context.getClickedPos();
      Level level = context.getLevel();
      if (pos.getY() < level.getMaxBuildHeight() - 2
         && level.getBlockState(pos.above(1)).canBeReplaced(context)
         && level.getBlockState(pos.above(2)).canBeReplaced(context)) {
         Direction parallel = context.getHorizontalDirection().getClockWise();
         if (level.getBlockState(pos.relative(parallel)).canBeReplaced(context)
            && level.getBlockState(pos.relative(parallel).above(1)).canBeReplaced(context)
            && level.getBlockState(pos.relative(parallel).above(2)).canBeReplaced(context)) {
            BlockState state = super.getStateForPlacement(context);
            if (state == null) {
               return null;
            }

            return (BlockState)state.setValue(FACING, context.getHorizontalDirection().getOpposite());
         }
      }

      return null;
   }

   public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
      if (!level.isClientSide) {
         if (player.isCreative()) {
            this.preventCreativeDropFromBottomPart(level, pos, state, player);
         } else if ((Integer)state.getValue(DOOR_PART) == 1) {
            dropResources(state, level, pos, null, player, player.getMainHandItem());
         }
      }

      return super.playerWillDestroy(level, pos, state, player);
   }

   protected void preventCreativeDropFromBottomPart(Level level, BlockPos pos, BlockState state, Player player) {
      if ((Integer)state.getValue(DOOR_PART) == 1) {
         Direction parallel = ((Direction)state.getValue(FACING)).getCounterClockWise();
         BlockPos hingePos = (BlockPos)this.getHinge(parallel, level, pos).getSecond();
         if (hingePos == null) {
            return;
         }

         level.setBlock(hingePos, Blocks.AIR.defaultBlockState(), 35);
         level.levelEvent(player, 2001, hingePos, Block.getId(level.getBlockState(hingePos)));
      }
   }

   public BlockState rotate(BlockState state, Rotation rot) {
      return (BlockState)state.setValue(FACING, rot.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirrorIn) {
      return state.rotate(mirrorIn.getRotation((Direction)state.getValue(FACING)));
   }

   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos pos, BlockPos facingPos) {
      if ((Integer)state.getValue(DOOR_PART) == 0) {
         if (facing.getAxis() == Axis.Y && !facingState.is(this)) {
            return Blocks.AIR.defaultBlockState();
         }

         if (facing == ((Direction)state.getValue(FACING)).getCounterClockWise() && !facingState.is(this)) {
            return Blocks.AIR.defaultBlockState();
         }
      } else {
         Direction parallel = ((Direction)state.getValue(FACING)).getCounterClockWise();
         Vec3i hinge = (Vec3i)this.getHinge(parallel, level, pos).getFirst();
         if (hinge == null) {
            return Blocks.AIR.defaultBlockState();
         }

         int xz = hinge.getX() + hinge.getZ();
         if (xz == 0) {
            if (facing == ((Direction)state.getValue(FACING)).getCounterClockWise() && !facingState.is(this)) {
               return Blocks.AIR.defaultBlockState();
            }
         } else if (facing == ((Direction)state.getValue(FACING)).getClockWise() && !facingState.is(this)) {
            return Blocks.AIR.defaultBlockState();
         }
      }

      return state;
   }

   protected Pair<Vec3i, BlockPos> getHinge(Direction parallel, LevelAccessor level, BlockPos pos) {
      for (int y = -1; y <= 1; y++) {
         for (int i = -1; i <= 0; i++) {
            BlockPos newPos = pos.relative(parallel, i).above(y);
            BlockState parallelState = level.getBlockState(newPos);
            if (parallelState.is(this) && (Integer)parallelState.getValue(DOOR_PART) == 0) {
               return Pair.of(Vec3i.ZERO.relative(parallel, i).above(y), newPos);
            }
         }
      }

      return Pair.of(null, null);
   }

   public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity living, ItemStack stack) {
      level.setBlock(pos.above(1), (BlockState)state.setValue(DOOR_PART, 0), 3);
      level.setBlock(pos.above(2), state, 3);
      level.setBlock(pos.relative(((Direction)state.getValue(FACING)).getCounterClockWise()), state, 3);
      level.setBlock(pos.relative(((Direction)state.getValue(FACING)).getCounterClockWise()).above(1), state, 3);
      level.setBlock(pos.relative(((Direction)state.getValue(FACING)).getCounterClockWise()).above(2), state, 3);
   }
}
