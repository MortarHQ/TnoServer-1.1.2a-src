package cn.leolezury.eternalstarlight.common.block;

import cn.leolezury.eternalstarlight.common.block.entity.LootChestBlockEntity;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
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
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class LootChestBlock extends BaseEntityBlock {
   public static final MapCodec<LootChestBlock> CODEC = simpleCodec(LootChestBlock::new);
   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
   private static final VoxelShape SHAPE = Shapes.or(
      Block.box(1.0, 1.0, 1.0, 15.0, 9.0, 15.0),
      new VoxelShape[]{
         Block.box(0.75, 7.75, 0.75, 15.25, 15.25, 15.25),
         Block.box(13.0, 0.0, 0.0, 16.0, 10.0, 3.0),
         Block.box(0.0, 0.0, 0.0, 3.0, 10.0, 3.0),
         Block.box(0.0, 0.0, 13.0, 3.0, 10.0, 16.0),
         Block.box(13.0, 0.0, 13.0, 16.0, 10.0, 16.0)
      }
   );
   private static final VoxelShape EJECTING_SHAPE = Shapes.or(
      Block.box(1.0, 1.0, 1.0, 15.0, 9.0, 15.0),
      new VoxelShape[]{
         Block.box(13.0, 0.0, 0.0, 16.0, 10.0, 3.0),
         Block.box(0.0, 0.0, 0.0, 3.0, 10.0, 3.0),
         Block.box(0.0, 0.0, 13.0, 3.0, 10.0, 16.0),
         Block.box(13.0, 0.0, 13.0, 16.0, 10.0, 16.0)
      }
   );

   public LootChestBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.getStateDefinition().any()).setValue(FACING, Direction.NORTH));
   }

   protected MapCodec<LootChestBlock> codec() {
      return CODEC;
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
   }

   protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
      if (level.getBlockEntity(pos) instanceof LootChestBlockEntity blockEntity
         && player instanceof ServerPlayer serverPlayer
         && blockEntity.getRewardTargets().contains(serverPlayer.getUUID())
         && blockEntity.isFree()) {
         blockEntity.rewardPlayer(serverPlayer, pos, this, player.isCrouching());
         level.playSound(null, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, SoundEvents.CHEST_OPEN, SoundSource.BLOCKS, 1.0F, 1.0F);
         return InteractionResult.CONSUME;
      } else {
         return level.isClientSide ? InteractionResult.SUCCESS : super.useWithoutItem(state, level, pos, player, hitResult);
      }
   }

   public RenderShape getRenderShape(BlockState state) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   protected BlockState rotate(BlockState state, Rotation rotation) {
      return (BlockState)state.setValue(FACING, rotation.rotate((Direction)state.getValue(FACING)));
   }

   protected BlockState mirror(BlockState state, Mirror mirror) {
      return state.rotate(mirror.getRotation((Direction)state.getValue(FACING)));
   }

   protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return level.getBlockEntity(pos) instanceof LootChestBlockEntity blockEntity && blockEntity.isEjecting() ? EJECTING_SHAPE : SHAPE;
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{FACING});
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return new LootChestBlockEntity(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
      return createTickerHelper(type, ESBlockEntities.LOOT_CHEST.get(), LootChestBlockEntity::tick);
   }
}
