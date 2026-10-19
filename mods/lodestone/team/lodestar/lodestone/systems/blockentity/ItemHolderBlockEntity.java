package team.lodestar.lodestone.systems.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public abstract class ItemHolderBlockEntity extends LodestoneBlockEntity implements IItemHandlerSupplier {
   public LodestoneBlockEntityInventory inventory;

   public ItemHolderBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
      super(type, pos, state);
   }

   @Override
   public IItemHandler getInventory(Direction direction) {
      return this.inventory;
   }

   @Override
   public ItemInteractionResult onUseWithItem(Player pPlayer, ItemStack pStack, InteractionHand pHand) {
      if (this.level instanceof ServerLevel serverLevel) {
         this.inventory.interact(serverLevel, pPlayer, pHand);
      }

      return ItemInteractionResult.SUCCESS;
   }

   @Override
   public void onBreak(@Nullable Player player) {
      this.inventory.dumpItems(this.level, this.worldPosition);
   }

   protected void saveAdditional(CompoundTag pTag, Provider pRegistries) {
      this.inventory.save(pRegistries, pTag);
      super.saveAdditional(pTag, pRegistries);
   }

   protected void loadAdditional(CompoundTag pTag, Provider pRegistries) {
      this.inventory.load(pRegistries, pTag);
      super.loadAdditional(pTag, pRegistries);
   }
}
