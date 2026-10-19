package io.github.Memoires.mysticism.menu.container;

import io.github.manasmods.tensura.item.tool.SimpleAxeItem;
import io.github.manasmods.tensura.item.weapon.SimpleSickleItem;
import io.github.manasmods.tensura.item.weapon.SimpleSpearItem;
import io.github.manasmods.tensura.item.weapon.SimpleSwordItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import org.jetbrains.annotations.NotNull;

public class WeaponStorageContainer extends SimpleContainer {
   private final int maxStackSize;

   public WeaponStorageContainer(int size, int maxStackSize) {
      super(size);
      this.maxStackSize = maxStackSize;
   }

   public boolean stillValid(Player pPlayer) {
      return pPlayer.isAlive();
   }

   public int getMaxStackSize() {
      return this.maxStackSize;
   }

   public int getMaxStackSize(ItemStack itemStack) {
      return itemStack.getMaxStackSize() == 1 ? 1 : this.getMaxStackSize();
   }

   public void setItem(int i, ItemStack itemStack) {
      this.items.set(i, itemStack);
      this.setChanged();
   }

   public void fromTag(ListTag nbt, Provider provide) {
      for (int i = 0; i < nbt.size(); i++) {
         CompoundTag tag = nbt.getCompound(i);
         int slot = tag.getInt("Slot");
         if (slot < this.getContainerSize()) {
            Optional<ItemStack> stack = ItemStack.parse(provide, tag.get("Stack"));
            this.setItem(slot, stack.orElse(ItemStack.EMPTY));
         }
      }
   }

   @NotNull
   public ListTag createTag(Provider provider) {
      ListTag listtag = new ListTag();

      for (int i = 0; i < this.getContainerSize(); i++) {
         ItemStack itemstack = this.getItem(i);
         if (!itemstack.isEmpty()) {
            CompoundTag tag = new CompoundTag();
            tag.putInt("Slot", i);
            tag.put("Stack", itemstack.save(provider, tag));
            listtag.add(tag);
         }
      }

      return listtag;
   }

   public boolean canAddItem(ItemStack pStack) {
      boolean flag = false;

      for (ItemStack itemstack : this.items) {
         if (itemstack.isEmpty()) {
            flag = true;
            break;
         }

         if (ItemStack.isSameItemSameComponents(itemstack, pStack)
            && (itemstack.getMaxStackSize() != 1 || itemstack.getCount() < 1)
            && itemstack.getCount() < this.getMaxStackSize()) {
            flag = true;
            break;
         }
      }

      return flag;
   }

   public void moveItemsBetweenStacks(ItemStack pStack, ItemStack pOther) {
      int i = pOther.getMaxStackSize() == 1 ? 1 : this.getMaxStackSize();
      int j = Math.min(pStack.getCount(), i - pOther.getCount());
      if (j > 0) {
         pOther.grow(j);
         pStack.shrink(j);
         this.setChanged();
      }
   }

   public ItemStack getRandomWeapon(RandomSource random) {
      List<ItemStack> validWeapons = new ArrayList<>();

      for (int i = 0; i < this.getContainerSize(); i++) {
         ItemStack stack = this.getItem(i);
         if (!stack.isEmpty() && isWeapon(stack)) {
            validWeapons.add(stack);
         }
      }

      return validWeapons.isEmpty() ? ItemStack.EMPTY : validWeapons.get(random.nextInt(validWeapons.size()));
   }

   public List<ItemStack> getAllWeapons() {
      List<ItemStack> validWeapons = new ArrayList<>();

      for (int i = 0; i < this.getContainerSize(); i++) {
         ItemStack stack = this.getItem(i);
         if (!stack.isEmpty() && isWeapon(stack)) {
            validWeapons.add(stack);
         }
      }

      return validWeapons;
   }

   public static boolean isWeapon(ItemStack stack) {
      Item item = stack.getItem();
      return item instanceof SwordItem
         || item instanceof AxeItem
         || item instanceof SimpleSpearItem
         || item instanceof SimpleSwordItem
         || item instanceof SimpleAxeItem
         || item instanceof SimpleSpearItem
         || item instanceof SimpleSickleItem;
   }
}
