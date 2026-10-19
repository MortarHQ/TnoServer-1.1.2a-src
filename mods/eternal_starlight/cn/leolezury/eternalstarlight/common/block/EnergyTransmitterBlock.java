package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.EnergyTransmitterBlockEntity;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.util.ESBlockUtil;
import com.mojang.math.OctahedralGroup;
import com.mojang.math.SymmetricGroup3;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class EnergyTransmitterBlock extends BaseEntityBlock {
   public static final MapCodec<EnergyTransmitterBlock> CODEC = simpleCodec(EnergyTransmitterBlock::new);
   public static final DirectionProperty FACING = BlockStateProperties.FACING;
   public static final EnumProperty<OctahedralGroup> OFFSET_TRANSFORMATION = EnumProperty.create(
      "offset_transformation",
      OctahedralGroup.class,
      group -> !group.inverts(Axis.Y) && (group.permutation == SymmetricGroup3.P123 || group.permutation == SymmetricGroup3.P321)
   );
   public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
   public static final IntegerProperty POWER = BlockStateProperties.POWER;
   public static final IntegerProperty DIRECT_POWER = IntegerProperty.create("direct_power", 0, 15);
   public static final int MAX_CONNECTION_DISTANCE = 64;
   private static final VoxelShape UP_SHAPE = Shapes.or(Block.box(3.0, 0.0, 3.0, 13.0, 2.0, 13.0), Block.box(6.0, 2.0, 6.0, 10.0, 3.0, 10.0));
   private static final VoxelShape DOWN_SHAPE = ESBlockUtil.rotateVoxelShape(UP_SHAPE, Direction.DOWN);
   private static final VoxelShape SOUTH_SHAPE = ESBlockUtil.rotateVoxelShape(UP_SHAPE, Direction.SOUTH);
   private static final VoxelShape NORTH_SHAPE = ESBlockUtil.rotateVoxelShape(UP_SHAPE, Direction.NORTH);
   private static final VoxelShape EAST_SHAPE = ESBlockUtil.rotateVoxelShape(UP_SHAPE, Direction.EAST);
   private static final VoxelShape WEST_SHAPE = ESBlockUtil.rotateVoxelShape(UP_SHAPE, Direction.WEST);

   public EnergyTransmitterBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.UP))
                     .setValue(OFFSET_TRANSFORMATION, OctahedralGroup.IDENTITY))
                  .setValue(POWERED, false))
               .setValue(POWER, 0))
            .setValue(DIRECT_POWER, 0)
      );
   }

   protected MapCodec<? extends EnergyTransmitterBlock> codec() {
      return CODEC;
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
      if (level.isClientSide) {
         return InteractionResult.SUCCESS;
      } else if (!(level.getBlockEntity(pos) instanceof EnergyTransmitterBlockEntity entity)) {
         return super.useWithoutItem(state, level, pos, player, hitResult);
      } else {
         GlobalPos sourcePos = ESDataAttachments.ENERGY_TRANSMITTER_SOURCE.getData(player);
         if (sourcePos != null
            && sourcePos.dimension() == level.dimension()
            && sourcePos.pos().distManhattan(pos) <= 64
            && level.getBlockEntity(sourcePos.pos()) instanceof EnergyTransmitterBlockEntity sourceEntity) {
            entity.setInputOffset(sourcePos.pos().subtract(pos));
            entity.setOutputOffset(Vec3i.ZERO);
            sourceEntity.setOutputOffset(pos.subtract(sourcePos.pos()));
            sourceEntity.setInputOffset(Vec3i.ZERO);
            ESDataAttachments.ENERGY_TRANSMITTER_SOURCE.removeData(player);
         } else {
            ESDataAttachments.ENERGY_TRANSMITTER_SOURCE.setData(player, GlobalPos.of(level.dimension(), pos));
         }

         return InteractionResult.CONSUME;
      }
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(FACING, context.getClickedFace());
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      Direction direction = (Direction)state.getValue(FACING);
      BlockPos attachPos = pos.relative(direction.getOpposite());
      BlockState attachState = level.getBlockState(attachPos);
      return attachState.isFaceSturdy(level, attachPos, direction);
   }

   public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
      return direction == ((Direction)state.getValue(FACING)).getOpposite() && !state.canSurvive(level, pos)
         ? Blocks.AIR.defaultBlockState()
         : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
   }

   protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
      if (!level.isClientSide) {
         int signal = level.getSignal(pos.relative(((Direction)state.getValue(FACING)).getOpposite()), ((Direction)state.getValue(FACING)).getOpposite());
         if ((Integer)state.getValue(DIRECT_POWER) != signal) {
            level.setBlockAndUpdate(pos, (BlockState)state.setValue(DIRECT_POWER, signal));
         }
      }
   }

   protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
      if (!level.isClientSide) {
         int signal = level.getSignal(pos.relative(((Direction)state.getValue(FACING)).getOpposite()), ((Direction)state.getValue(FACING)).getOpposite());
         if ((Integer)state.getValue(DIRECT_POWER) != signal) {
            level.setBlockAndUpdate(pos, (BlockState)state.setValue(DIRECT_POWER, signal));
         }
      }
   }

   protected boolean isSignalSource(BlockState state) {
      return (Integer)state.getValue(POWER) > 0;
   }

   protected int getSignal(BlockState blockState, BlockGetter blockAccess, BlockPos pos, Direction side) {
      return side == blockState.getValue(FACING) ? (Integer)blockState.getValue(POWER) : 0;
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.MODEL;
   }

   protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return switch ((Direction)state.getValue(FACING)) {
         case DOWN -> DOWN_SHAPE;
         case UP -> UP_SHAPE;
         case NORTH -> NORTH_SHAPE;
         case SOUTH -> SOUTH_SHAPE;
         case WEST -> WEST_SHAPE;
         case EAST -> EAST_SHAPE;
         default -> throw new MatchException(null, null);
      };
   }

   protected BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)((BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING))))
         .setValue(OFFSET_TRANSFORMATION, rotation.rotation().compose((OctahedralGroup)state.getValue(OFFSET_TRANSFORMATION)));
   }

   protected BlockState mirror(BlockState state, Mirror mirror) {
      return (BlockState)((BlockState)state.setValue(FACING, mirror.getRotation((Direction)state.getValue(FACING)).rotate((Direction)state.getValue(FACING))))
         .setValue(OFFSET_TRANSFORMATION, mirror.rotation().compose((OctahedralGroup)state.getValue(OFFSET_TRANSFORMATION)));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING, OFFSET_TRANSFORMATION, POWERED, POWER, DIRECT_POWER});
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new EnergyTransmitterBlockEntity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return createTickerHelper(type, ESBlockEntities.ENERGY_TRANSMITTER.get(), EnergyTransmitterBlockEntity::tick);
   }
}
