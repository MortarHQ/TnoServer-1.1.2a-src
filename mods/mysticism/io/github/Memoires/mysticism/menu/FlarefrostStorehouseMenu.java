package io.github.Memoires.mysticism.menu;

import io.github.Memoires.mysticism.ability.subclass.IFlarefrostStorehouse;
import io.github.Memoires.mysticism.menu.slot.FixedItemSlot;
import io.github.Memoires.mysticism.registry.item.MysticismWeaponItems;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.menu.container.SpatialStorageContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FlarefrostStorehouseMenu<S extends ManasSkill & IFlarefrostStorehouse<S>> extends AbstractContainerMenu {
   private final S skill;
   private final Player player;
   public final SpatialStorageContainer weaponContainer;
   private static final int AXIOM_SLOT_X = 78;
   private static final int AXIOM_SLOT_Y = 59;
   private static final int WALTZ_SLOT_X = 121;
   private static final int WALTZ_SLOT_Y = 59;

   public FlarefrostStorehouseMenu(int id, Inventory inv, SpatialStorageContainer container, S skill) {
      super(null, id);
      this.skill = skill;
      this.player = inv.player;
      this.weaponContainer = new SpatialStorageContainer(2, 1);

      for (int i = 0; i < 2; i++) {
         this.weaponContainer.setItem(i, container.getItem(i + skill.getSpatialStorageIdOffset()));
      }

      container.startOpen(inv.player);
      this.addWeaponSlots();
      this.addPlayerInventory(inv);
   }

   private void addPlayerInventory(Inventory playerInventory) {
      for (int i = 0; i < 3; i++) {
         for (int l = 0; l < 9; l++) {
            this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 27 + l * 18, 140 + i * 18));
         }
      }

      for (int i = 0; i < 9; i++) {
         this.addSlot(new Slot(playerInventory, i, 27 + i * 18, 198));
      }
   }

   public void equipBoth(Player player) {
      if (!player.level().isClientSide()) {
         ItemStack axiom = this.weaponContainer.getItem(0).copy();
         ItemStack waltz = this.weaponContainer.getItem(1).copy();
         if (!axiom.isEmpty() && !axiom.is((Item)MysticismWeaponItems.AXIOM.get())) {
            axiom = ItemStack.EMPTY;
         }

         if (!waltz.isEmpty() && !waltz.is((Item)MysticismWeaponItems.WALTZ.get())) {
            waltz = ItemStack.EMPTY;
         }

         ItemStack oldMain = player.getMainHandItem().copy();
         ItemStack oldOff = player.getOffhandItem().copy();
         if (!oldMain.isEmpty()) {
            player.getInventory().placeItemBackInInventory(oldMain);
         }

         if (!oldOff.isEmpty()) {
            player.getInventory().placeItemBackInInventory(oldOff);
         }

         this.weaponContainer.setItem(0, ItemStack.EMPTY);
         this.weaponContainer.setItem(1, ItemStack.EMPTY);
         this.weaponContainer.setItem(0, oldMain);
         this.weaponContainer.setItem(1, oldOff);
         this.weaponContainer.setChanged();
         this.broadcastChanges();
      }
   }

   private void addWeaponSlots() {
      this.addSlot(new FixedItemSlot(this.weaponContainer, 0, 78, 59, stack -> stack.is((Item)MysticismWeaponItems.AXIOM.get())));
      this.addSlot(new FixedItemSlot(this.weaponContainer, 1, 121, 59, stack -> stack.is((Item)MysticismWeaponItems.WALTZ.get())));
   }

   public void removed(Player pPlayer) {
      ManasSkillInstance instance = this.getSkillInstance(pPlayer);
      if (instance != null && instance.getSkill() instanceof IFlarefrostStorehouse flarefrostStorehouse) {
         flarefrostStorehouse.saveContainer(instance, pPlayer, this.weaponContainer);
      }

      this.weaponContainer.stopOpen(pPlayer);
      super.removed(pPlayer);
   }

   public boolean stillValid(Player player) {
      return player.isAlive();
   }

   @Nullable
   public ManasSkillInstance getSkillInstance(Player player) {
      return (ManasSkillInstance)SkillAPI.getSkillsFrom(player).getSkill(this.skill).orElse(null);
   }

   @NotNull
   public ItemStack quickMoveStack(Player player, int index) {
      Slot from = (Slot)this.slots.get(index);
      if (!from.hasItem()) {
         return ItemStack.EMPTY;
      }

      ItemStack stack = from.getItem();
      ItemStack copy = stack.copy();
      int storageStart = 0;
      int storageEnd = 2;
      int playerStart = 2;
      int playerEnd = 38;
      if (index >= 0 && index < 2) {
         if (!this.moveItemStackTo(stack, 2, 38, true)) {
            return ItemStack.EMPTY;
         }
      } else {
         if (index < 2 || index >= 38) {
            return ItemStack.EMPTY;
         }

         if (stack.is((Item)MysticismWeaponItems.AXIOM.get())) {
            if (!this.moveItemStackTo(stack, 0, 1, false)) {
               return ItemStack.EMPTY;
            }
         } else {
            if (!stack.is((Item)MysticismWeaponItems.WALTZ.get())) {
               return ItemStack.EMPTY;
            }

            if (!this.moveItemStackTo(stack, 1, 2, false)) {
               return ItemStack.EMPTY;
            }
         }
      }

      if (stack.isEmpty()) {
         from.set(ItemStack.EMPTY);
      } else {
         from.setChanged();
      }

      from.onTake(player, stack);
      return copy;
   }

   public S getSkill() {
      return this.skill;
   }

   public Player getPlayer() {
      return this.player;
   }
}
