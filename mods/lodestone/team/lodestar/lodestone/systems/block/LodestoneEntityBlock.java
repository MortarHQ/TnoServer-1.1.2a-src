package team.lodestar.lodestone.systems.block;

import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

public class LodestoneEntityBlock<T extends LodestoneBlockEntity> extends Block implements EntityBlock {
   protected Supplier<BlockEntityType<T>> blockEntityType = null;
   protected BlockEntityTicker<T> ticker = null;

   public LodestoneEntityBlock(Properties properties) {
      super(properties);
   }

   public LodestoneEntityBlock<T> setBlockEntity(Supplier<BlockEntityType<T>> type) {
      this.blockEntityType = type;
      this.ticker = (l, p, s, t) -> {
         if (l instanceof ServerLevel level) {
            t.serverTick(level);
         } else {
            t.clientTick(l);
         }

         t.tick();
         t.commonTick(l);
      };
      return this;
   }

   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return this.hasTileEntity(state) ? this.blockEntityType.get().create(pos, state) : null;
   }

   public boolean hasTileEntity(BlockState state) {
      return this.blockEntityType != null;
   }

   public <Y extends BlockEntity> BlockEntityTicker<Y> getTicker(Level level, BlockState state, BlockEntityType<Y> type) {
      return this.ticker;
   }

   public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
      if (this.hasTileEntity(pState) && pLevel.getBlockEntity(pPos) instanceof LodestoneBlockEntity blockEntity) {
         blockEntity.onPlace(pPlacer, pStack);
      }

      super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
   }

   @NotNull
   public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
      if (this.hasTileEntity(state) && level.getBlockEntity(pos) instanceof LodestoneBlockEntity blockEntity) {
         ItemStack stack = blockEntity.onClone(state, target, level, pos, player);
         if (!stack.isEmpty()) {
            return stack;
         }
      }

      return super.getCloneItemStack(state, target, level, pos, player);
   }

   @NotNull
   public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
      this.onBlockBroken(state, level, pos, player);
      return super.playerWillDestroy(level, pos, state, player);
   }

   public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
      this.onBlockBroken(state, level, pos, null);
      super.onBlockExploded(state, level, pos, explosion);
   }

   public void onBlockBroken(BlockState state, BlockGetter level, BlockPos pos, @Nullable Player player) {
      if (this.hasTileEntity(state) && level.getBlockEntity(pos) instanceof LodestoneBlockEntity blockEntity) {
         blockEntity.onBreak(player);
      }
   }

   public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
      if (this.hasTileEntity(pState) && pLevel.getBlockEntity(pPos) instanceof LodestoneBlockEntity blockEntity) {
         blockEntity.onEntityInside(pState, pLevel, pPos, pEntity);
      }

      super.entityInside(pState, pLevel, pPos, pEntity);
   }

   public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
      if (this.hasTileEntity(pState) && pLevel.getBlockEntity(pPos) instanceof LodestoneBlockEntity blockEntity) {
         blockEntity.onNeighborUpdate(pState, pPos, pFromPos);
      }

      super.neighborChanged(pState, pLevel, pPos, pBlock, pFromPos, pIsMoving);
   }

   protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
      if (this.hasTileEntity(pState) && pLevel.getBlockEntity(pPos) instanceof LodestoneBlockEntity blockEntity) {
         InteractionResult earlyResult = blockEntity.onUseWithoutItem(pPlayer);
         return earlyResult.consumesAction() ? earlyResult : blockEntity.onUse(pPlayer, InteractionHand.MAIN_HAND).result();
      } else {
         return super.useWithoutItem(pState, pLevel, pPos, pPlayer, pHitResult);
      }
   }

   protected ItemInteractionResult useItemOn(
      ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult
   ) {
      if (this.hasTileEntity(pState) && pLevel.getBlockEntity(pPos) instanceof LodestoneBlockEntity blockEntity) {
         ItemInteractionResult earlyResult = blockEntity.onUseWithItem(pPlayer, pStack, pHand);
         return earlyResult.consumesAction() ? earlyResult : blockEntity.onUse(pPlayer, pHand);
      } else {
         return super.useItemOn(pStack, pState, pLevel, pPos, pPlayer, pHand, pHitResult);
      }
   }
}
