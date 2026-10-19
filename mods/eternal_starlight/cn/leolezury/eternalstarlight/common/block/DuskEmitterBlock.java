package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.AbstractDuskLightBlockEntity;
import cn.leolezury.eternalstarlight.common.block.entity.DuskEmitterBlockEntity;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import com.mojang.serialization.MapCodec;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class DuskEmitterBlock extends BaseEntityBlock {
   public static final MapCodec<DuskEmitterBlock> CODEC = simpleCodec(DuskEmitterBlock::new);
   public static final BooleanProperty NORTH = PipeBlock.NORTH;
   public static final BooleanProperty EAST = PipeBlock.EAST;
   public static final BooleanProperty SOUTH = PipeBlock.SOUTH;
   public static final BooleanProperty WEST = PipeBlock.WEST;
   public static final BooleanProperty UP = PipeBlock.UP;
   public static final BooleanProperty DOWN = PipeBlock.DOWN;
   private static final Map<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = PipeBlock.PROPERTY_BY_DIRECTION;

   public DuskEmitterBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(NORTH, false))
                        .setValue(EAST, false))
                     .setValue(SOUTH, false))
                  .setValue(WEST, false))
               .setValue(UP, false))
            .setValue(DOWN, false)
      );
   }

   protected MapCodec<DuskEmitterBlock> codec() {
      return CODEC;
   }

   protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
      if (level.getBlockEntity(blockPos) instanceof DuskEmitterBlockEntity entity && entity.isLit()) {
         if (!level.isClientSide) {
            BooleanProperty property = PROPERTY_BY_DIRECTION.get(blockHitResult.getDirection());
            level.setBlockAndUpdate(blockPos, (BlockState)blockState.setValue(property, !(Boolean)blockState.getValue(property)));
         }

         return InteractionResult.sidedSuccess(level.isClientSide);
      } else {
         return InteractionResult.PASS;
      }
   }

   protected BlockState rotate(BlockState state, Rotation rot) {
      return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)state.setValue(
                        (Property)PROPERTY_BY_DIRECTION.get(rot.rotate(Direction.NORTH)), (Boolean)state.getValue(NORTH)
                     ))
                     .setValue((Property)PROPERTY_BY_DIRECTION.get(rot.rotate(Direction.SOUTH)), (Boolean)state.getValue(SOUTH)))
                  .setValue((Property)PROPERTY_BY_DIRECTION.get(rot.rotate(Direction.EAST)), (Boolean)state.getValue(EAST)))
               .setValue((Property)PROPERTY_BY_DIRECTION.get(rot.rotate(Direction.WEST)), (Boolean)state.getValue(WEST)))
            .setValue((Property)PROPERTY_BY_DIRECTION.get(rot.rotate(Direction.UP)), (Boolean)state.getValue(UP)))
         .setValue((Property)PROPERTY_BY_DIRECTION.get(rot.rotate(Direction.DOWN)), (Boolean)state.getValue(DOWN));
   }

   protected BlockState mirror(BlockState state, Mirror mirror) {
      return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)state.setValue(
                        (Property)PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.NORTH)), (Boolean)state.getValue(NORTH)
                     ))
                     .setValue((Property)PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.SOUTH)), (Boolean)state.getValue(SOUTH)))
                  .setValue((Property)PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.EAST)), (Boolean)state.getValue(EAST)))
               .setValue((Property)PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.WEST)), (Boolean)state.getValue(WEST)))
            .setValue((Property)PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.UP)), (Boolean)state.getValue(UP)))
         .setValue((Property)PROPERTY_BY_DIRECTION.get(mirror.mirror(Direction.DOWN)), (Boolean)state.getValue(DOWN));
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{UP, DOWN, NORTH, EAST, SOUTH, WEST});
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
      return new DuskEmitterBlockEntity(blockPos, blockState);
   }

   protected RenderShape getRenderShape(BlockState blockState) {
      return RenderShape.MODEL;
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
      return createTickerHelper(blockEntityType, ESBlockEntities.DUSK_EMITTER.get(), AbstractDuskLightBlockEntity::tick);
   }
}
