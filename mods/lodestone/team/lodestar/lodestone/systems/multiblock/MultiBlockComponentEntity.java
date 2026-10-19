package team.lodestar.lodestone.systems.multiblock;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.helpers.NBTHelper;
import team.lodestar.lodestone.registry.common.LodestoneBlockEntities;
import team.lodestar.lodestone.systems.blockentity.IItemHandlerSupplier;
import team.lodestar.lodestone.systems.blockentity.LodestoneBlockEntity;

public class MultiBlockComponentEntity extends LodestoneBlockEntity implements IItemHandlerSupplier {
   public BlockPos corePos;

   public MultiBlockComponentEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
      super(type, pos, state);
   }

   public MultiBlockComponentEntity(BlockPos pos, BlockState state) {
      super(LodestoneBlockEntities.MULTIBLOCK_COMPONENT.get(), pos, state);
   }

   @Override
   public IItemHandler getInventory(Direction direction) {
      return this.corePos != null
            && this.level.getBlockEntity(this.corePos) instanceof MultiBlockCoreEntity core
            && core instanceof IItemHandlerSupplier supplier
         ? supplier.getInventory(direction)
         : null;
   }

   protected void saveAdditional(CompoundTag pTag, Provider pRegistries) {
      if (this.corePos != null) {
         NBTHelper.saveBlockPos(pTag, this.corePos);
      }

      super.saveAdditional(pTag, pRegistries);
   }

   protected void loadAdditional(CompoundTag pTag, Provider pRegistries) {
      this.corePos = NBTHelper.readBlockPos(pTag);
      super.loadAdditional(pTag, pRegistries);
   }

   @Override
   public ItemInteractionResult onUse(Player pPlayer, InteractionHand pHand) {
      return this.corePos != null && this.level.getBlockEntity(this.corePos) instanceof MultiBlockCoreEntity core
         ? core.onUse(pPlayer, pHand)
         : super.onUse(pPlayer, pHand);
   }

   @Override
   public InteractionResult onUseWithoutItem(Player pPlayer) {
      return this.corePos != null && this.level.getBlockEntity(this.corePos) instanceof MultiBlockCoreEntity core
         ? core.onUseWithoutItem(pPlayer)
         : super.onUseWithoutItem(pPlayer);
   }

   @Override
   public ItemInteractionResult onUseWithItem(Player pPlayer, ItemStack pStack, InteractionHand pHand) {
      return this.corePos != null && this.level.getBlockEntity(this.corePos) instanceof MultiBlockCoreEntity core
         ? core.onUseWithItem(pPlayer, pStack, pHand)
         : super.onUseWithItem(pPlayer, pStack, pHand);
   }

   @Override
   public void onBreak(@Nullable Player player) {
      if (this.corePos != null && this.level.getBlockEntity(this.corePos) instanceof MultiBlockCoreEntity core) {
         core.onBreak(player);
      }

      super.onBreak(player);
   }
}
