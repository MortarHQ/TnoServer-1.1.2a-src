package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DuskLockboxBlockEntity extends RandomizableContainerBlockEntity implements DuskLightReceptor {
   private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);

   public DuskLockboxBlockEntity(BlockPos pos, BlockState blockState) {
      super(ESBlockEntities.DUSK_LOCKBOX.get(), pos, blockState);
   }

   @Override
   public void lightUp(Level level, BlockPos pos, Direction sourceDir) {
      RandomSource random = level.getRandom();

      for (int i = 0; i < this.getContainerSize(); i++) {
         ItemStack stack = this.getItem(i);

         while (!stack.isEmpty()) {
            ItemEntity entity = new ItemEntity(
               level, pos.getX() + random.nextFloat(), pos.getY() + random.nextFloat(), pos.getZ() + random.nextFloat(), stack.split(random.nextInt(21) + 10)
            );
            entity.setGlowingTag(true);
            level.addFreshEntity(entity);
         }
      }

      this.clearContent();
      level.destroyBlock(pos, false);
   }

   protected void saveAdditional(CompoundTag tag, Provider registries) {
      super.saveAdditional(tag, registries);
      if (!this.trySaveLootTable(tag)) {
         ContainerHelper.saveAllItems(tag, this.items, registries);
      }
   }

   protected void loadAdditional(CompoundTag tag, Provider registries) {
      super.loadAdditional(tag, registries);
      this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
      if (!this.tryLoadLootTable(tag)) {
         ContainerHelper.loadAllItems(tag, this.items, registries);
      }
   }

   public int getContainerSize() {
      return 27;
   }

   protected NonNullList<ItemStack> getItems() {
      return this.items;
   }

   protected void setItems(NonNullList<ItemStack> items) {
      this.items = items;
   }

   protected Component getDefaultName() {
      return Component.translatable("container.eternal_starlight.dusk_lockbox");
   }

   protected AbstractContainerMenu createMenu(int id, Inventory player) {
      return null;
   }
}
