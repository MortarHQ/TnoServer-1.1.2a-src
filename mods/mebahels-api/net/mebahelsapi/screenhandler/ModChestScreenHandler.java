package net.mebahelsapi.screenhandler;

import java.util.Objects;
import net.mebahelsapi.block.entity.base.BaseChestBlockEntity;
import net.mebahelsapi.network.SyncedInventoryWrapper;
import net.minecraft.class_1263;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1703;
import net.minecraft.class_1735;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2371;
import net.minecraft.class_2586;

public class ModChestScreenHandler extends class_1703 {
   private final class_1263 inventory;
   private final class_2371<class_1799> personalInventory;
   public static final int ROWS = 4;
   public static final int COLUMNS = 9;
   private final class_2338 chestPos;
   private final class_1937 world;

   public ModChestScreenHandler(int syncId, class_1661 playerInventory, class_2338 pos) {
      this(syncId, playerInventory, Objects.requireNonNull(playerInventory.field_7546.method_37908().method_8321(pos)));
   }

   public ModChestScreenHandler(int syncId, class_1661 playerInventory, class_2586 blockEntity) {
      super(ModScreenHandlers.BASE_CHEST_SCREEN_HANDLER, syncId);
      this.inventory = (class_1263)blockEntity;
      this.personalInventory = null;
      this.world = playerInventory.field_7546.method_37908();
      this.chestPos = blockEntity.method_11016();
      method_17359(this.inventory, 36);
      this.inventory.method_5435(playerInventory.field_7546);

      for (int row = 0; row < 4; row++) {
         for (int col = 0; col < 9; col++) {
            this.method_7621(new class_1735(this.inventory, col + row * 9, 8 + col * 18, 18 + row * 18));
         }
      }

      this.addPlayerInventory(playerInventory);
      this.addPlayerHotbar(playerInventory);
   }

   public ModChestScreenHandler(int syncId, class_1661 playerInventory, class_2371<class_1799> personalInventory, class_2338 pos) {
      super(ModScreenHandlers.BASE_CHEST_SCREEN_HANDLER, syncId);
      this.inventory = playerInventory;
      this.personalInventory = personalInventory;
      this.chestPos = pos;
      this.world = playerInventory.field_7546.method_37908();
      if (!this.world.field_9236 && this.world.method_8321(this.chestPos) instanceof BaseChestBlockEntity chest) {
         chest.method_5435(playerInventory.field_7546);
      }

      int slotIndex = 0;

      for (int row = 0; row < 4; row++) {
         for (int col = 0; col < 9; col++) {
            this.method_7621(new class_1735(new SyncedInventoryWrapper(personalInventory), slotIndex++, 8 + col * 18, 18 + row * 18));
         }
      }

      this.addPlayerInventory(playerInventory);
      this.addPlayerHotbar(playerInventory);
   }

   public void method_7595(class_1657 player) {
      super.method_7595(player);
      if (!this.world.field_9236) {
         if (this.world.method_8321(this.chestPos) instanceof BaseChestBlockEntity chest) {
            chest.method_5432(player);
         }
      }
   }

   public void method_7623() {
      super.method_7623();
      if (!this.world.field_9236) {
         this.inventory.method_5431();
      }
   }

   public void method_7609(class_1263 inventory) {
      super.method_7609(inventory);
      this.method_7623();
   }

   public class_1799 method_7601(class_1657 player, int invSlot) {
      class_1799 newStack = class_1799.field_8037;
      class_1735 slot = (class_1735)this.field_7761.get(invSlot);
      if (slot != null && slot.method_7681()) {
         class_1799 originalStack = slot.method_7677();
         newStack = originalStack.method_7972();
         int containerSize = 36;
         if (invSlot < containerSize) {
            if (!this.method_7616(originalStack, containerSize, this.field_7761.size(), true)) {
               return class_1799.field_8037;
            }
         } else if (!this.method_7616(originalStack, 0, containerSize, false)) {
            return class_1799.field_8037;
         }

         if (originalStack.method_7960()) {
            slot.method_53512(class_1799.field_8037);
         } else {
            slot.method_7668();
         }
      }

      return newStack;
   }

   private void addPlayerInventory(class_1661 playerInventory) {
      for (int row = 0; row < 3; row++) {
         for (int col = 0; col < 9; col++) {
            this.method_7621(new class_1735(playerInventory, col + row * 9 + 9, 8 + col * 18, 104 + row * 18));
         }
      }
   }

   private void addPlayerHotbar(class_1661 playerInventory) {
      for (int col = 0; col < 9; col++) {
         this.method_7621(new class_1735(playerInventory, col, 8 + col * 18, 162));
      }
   }

   public boolean method_7597(class_1657 player) {
      return true;
   }
}
