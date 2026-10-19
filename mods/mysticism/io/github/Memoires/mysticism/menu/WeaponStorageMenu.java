package io.github.Memoires.mysticism.menu;

import io.github.Memoires.mysticism.ability.subclass.IWeaponStorage;
import io.github.Memoires.mysticism.menu.container.WeaponStorageContainer;
import io.github.Memoires.mysticism.menu.slot.WeaponSlot;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import java.util.Optional;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class WeaponStorageMenu extends AbstractContainerMenu {
   private static final Logger log = LogManager.getLogger(WeaponStorageMenu.class);
   private static final int NORMAL_ROWS = 2;
   private static final int MASTERED_ROWS = 4;
   private static final int COLS = 5;
   private final ManasSkill skill;
   private final Player player;
   private final int page;
   private final SimpleContainer container;
   private final boolean mastered;
   private final int rows;
   private final int weaponStart;
   private static final int WEAPON_GRID_X = 73;
   private static final int WEAPON_GRID_Y = 25;
   private static final int INV_GRID_X = 37;
   private static final int INV_GRID_Y = 92;

   public WeaponStorageMenu(int id, Inventory inv, SimpleContainer container, ManasSkill skill, int page, boolean mastered) {
      super(null, id);
      this.skill = skill;
      this.player = inv.player;
      this.page = page;
      this.container = container;
      this.mastered = mastered;
      this.rows = mastered ? 4 : 2;
      this.weaponStart = this.rows * 5;
      container.startOpen(inv.player);
      this.addWeaponSlots(container);
      this.addPlayerInventory(inv);
   }

   public boolean stillValid(Player player) {
      return player.isAlive();
   }

   private void addPlayerInventory(Inventory playerInventory) {
      for (int i = 0; i < 3; i++) {
         for (int l = 0; l < 9; l++) {
            this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 37 + l * 18, 92 + i * 18));
         }
      }

      for (int i = 0; i < 9; i++) {
         this.addSlot(new Slot(playerInventory, i, 37 + i * 18, 150));
      }
   }

   private void addWeaponSlots(SimpleContainer container) {
      int perPage = this.rows * 5;
      int slotIndex = perPage * this.page;
      int size = container.getContainerSize() - perPage * this.page;

      for (int i = 0; i < this.rows && size > 0; i++) {
         for (int j = 0; j < 5 && size > 0; j++) {
            this.addSlot(new WeaponSlot(container, slotIndex, 73 + j * 18, 25 + i * 18));
            slotIndex++;
            size--;
         }
      }
   }

   public boolean clickMenuButton(Player player, int i) {
      int perPage = this.rows * 5;
      if (i == 0 && this.page > 0) {
         ManasSkillInstance instance = this.getSkillInstance(player);
         if (instance != null && instance.getSkill() instanceof IWeaponStorage weaponStorage) {
            this.removed(player);
            if (player instanceof ServerPlayer serverPlayer) {
               weaponStorage.openWeaponStorageMenu(serverPlayer, instance, this.page - 1);
            }
         }

         return true;
      } else if (i == 1 && this.page < (this.container.getContainerSize() - 1) / perPage) {
         ManasSkillInstance instance = this.getSkillInstance(player);
         if (instance != null && instance.getSkill() instanceof IWeaponStorage weaponStorage) {
            this.removed(player);
            if (player instanceof ServerPlayer serverPlayer) {
               weaponStorage.openWeaponStorageMenu(serverPlayer, instance, this.page + 1);
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Nullable
   public ManasSkillInstance getSkillInstance(Player player) {
      Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(player).getSkill(this.skill);
      return optional.orElse(null);
   }

   public void removed(Player pPlayer) {
      ManasSkillInstance instance = this.getSkillInstance(pPlayer);
      if (instance != null && this.container instanceof WeaponStorageContainer storageContainer && instance.getSkill() instanceof IWeaponStorage weaponStorage) {
         weaponStorage.saveContainer(instance, pPlayer, storageContainer);
      }

      this.container.stopOpen(pPlayer);
      super.removed(pPlayer);
   }

   public int getStorageSize() {
      int perPage = this.rows * 5;
      return Math.min(this.container.getContainerSize() - this.page * perPage, perPage);
   }

   private int getWeaponEnd() {
      return this.weaponStart + this.getStorageSize();
   }

   public ItemStack quickMoveStack(Player pPlayer, int i) {
      ItemStack copy = ItemStack.EMPTY;
      Slot slot = (Slot)this.slots.get(i);
      if (!slot.hasItem()) {
         return copy;
      }

      ItemStack stack = slot.getItem();
      copy = stack.copy();
      if (i >= this.weaponStart) {
         if (!this.moveItemStackTo(stack, 0, this.weaponStart, false)) {
            return ItemStack.EMPTY;
         }
      } else if (!this.moveItemStackTo(stack, this.weaponStart, this.slots.size(), false)) {
         return ItemStack.EMPTY;
      }

      if (stack.isEmpty()) {
         slot.setByPlayer(ItemStack.EMPTY);
      } else {
         slot.setChanged();
      }

      if (stack.getCount() == copy.getCount()) {
         return ItemStack.EMPTY;
      }

      slot.onTake(this.player, stack);
      return copy;
   }

   protected boolean moveItemStackTo(ItemStack pStack, int pStartIndex, int pEndIndex, boolean pReverseDirection) {
      boolean flag = false;
      int i = pStartIndex;
      if (pReverseDirection) {
         i = pEndIndex - 1;
      }

      if (pStack.isStackable()) {
         while (!pStack.isEmpty() && (pReverseDirection ? i >= pStartIndex : i < pEndIndex)) {
            Slot slot = (Slot)this.slots.get(i);
            ItemStack itemstack = slot.getItem();
            if (!itemstack.isEmpty() && slot.mayPlace(pStack) && ItemStack.isSameItemSameComponents(pStack, itemstack)) {
               int j = itemstack.getCount() + pStack.getCount();
               int maxSize = i < this.weaponStart ? this.container.getMaxStackSize(pStack) : Math.min(itemstack.getMaxStackSize(), slot.getMaxStackSize());
               if (j <= maxSize) {
                  pStack.setCount(0);
                  itemstack.setCount(j);
                  slot.setChanged();
                  flag = true;
               } else if (itemstack.getCount() < maxSize) {
                  pStack.shrink(maxSize - itemstack.getCount());
                  itemstack.setCount(maxSize);
                  slot.setChanged();
                  flag = true;
               }
            }

            if (pReverseDirection) {
               i--;
            } else {
               i++;
            }
         }
      }

      if (!pStack.isEmpty()) {
         i = pReverseDirection ? pEndIndex - 1 : pStartIndex;

         while (pReverseDirection ? i >= pStartIndex : i < pEndIndex) {
            Slot pSlot = (Slot)this.slots.get(i);
            ItemStack stack = pSlot.getItem();
            if (stack.isEmpty() && pSlot.mayPlace(pStack)) {
               if (pStack.getCount() > pSlot.getMaxStackSize()) {
                  pSlot.set(pStack.split(pSlot.getMaxStackSize()));
               } else {
                  pSlot.set(pStack.split(pStack.getCount()));
               }

               pSlot.setChanged();
               flag = true;
               break;
            }

            if (pReverseDirection) {
               i--;
            } else {
               i++;
            }
         }
      }

      return flag;
   }

   public ManasSkill getSkill() {
      return this.skill;
   }

   public Player getPlayer() {
      return this.player;
   }

   public int getPage() {
      return this.page;
   }

   public boolean isMastered() {
      return this.mastered;
   }

   public int getRows() {
      return this.rows;
   }
}
