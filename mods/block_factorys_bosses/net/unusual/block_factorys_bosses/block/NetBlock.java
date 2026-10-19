package net.unusual.block_factorys_bosses.block;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.ItemAbilities;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class NetBlock extends Block {
   public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
   public static final EnumProperty<NetBlock.NetType> NET_TYPE = EnumProperty.create("net_type", NetBlock.NetType.class);
   private static final VoxelShape FLOOR = box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
   private static final VoxelShape WEST_HANGING = Block.box(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
   private static final VoxelShape EAST_HANGING = Block.box(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
   private static final VoxelShape NORTH_HANGING = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
   private static final VoxelShape SOUTH_HANGING = Block.box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);

   public NetBlock() {
      super(Properties.of().sound(SoundType.WOOL).strength(0.5F, 2.0F).noOcclusion().noCollission());
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH)).setValue(NET_TYPE, NetBlock.NetType.FLOOR)
      );
   }

   protected float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
      return player.getMainHandItem().canPerformAction(ItemAbilities.SHEARS_DIG) ? 1.0F : super.getDestroyProgress(state, player, level, pos);
   }

   public boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
      return state.getValue(NET_TYPE) != NetBlock.NetType.FLOOR;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, NET_TYPE});
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      Level level = context.getLevel();
      Direction face = context.getClickedFace();
      Direction horizontal = context.getHorizontalDirection();
      BlockPos below = context.getClickedPos().below();
      BlockState belowState = level.getBlockState(below);
      if (belowState.isFaceSturdy(level, below, Direction.UP)) {
         return face == Direction.UP
            ? (BlockState)((BlockState)this.defaultBlockState().setValue(FACING, horizontal)).setValue(NET_TYPE, NetBlock.NetType.FLOOR)
            : (BlockState)((BlockState)this.defaultBlockState().setValue(FACING, horizontal)).setValue(NET_TYPE, NetBlock.NetType.LEANING);
      } else {
         return (BlockState)((BlockState)this.defaultBlockState().setValue(FACING, horizontal)).setValue(NET_TYPE, NetBlock.NetType.HANGING);
      }
   }

   public BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
   }

   public BlockState mirror(BlockState state, Mirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      if (state.getValue(NET_TYPE) == NetBlock.NetType.FLOOR) {
         return FLOOR;
      }

      return switch ((Direction)state.getValue(FACING)) {
         case EAST -> EAST_HANGING;
         case WEST -> WEST_HANGING;
         case SOUTH -> SOUTH_HANGING;
         default -> NORTH_HANGING;
      };
   }

   public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
      return true;
   }

   public int getLightBlock(BlockState state, BlockGetter world, BlockPos pos) {
      return 0;
   }

   public enum NetType implements StringRepresentable {
      FLOOR("floor"),
      LEANING("leaning"),
      HANGING("hanging");

      private final String name;

      NetType(String name) {
         this.name = name;
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
