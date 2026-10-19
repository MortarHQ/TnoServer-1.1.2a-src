package com.natamus.starterkit_common_fabric.inventory;

import com.mojang.datafixers.util.Pair;
import com.natamus.starterkit_common_fabric.data.VariablesClient;
import net.minecraft.class_1263;
import net.minecraft.class_1304;
import net.minecraft.class_1657;
import net.minecraft.class_1662;
import net.minecraft.class_1729;
import net.minecraft.class_1735;
import net.minecraft.class_1799;
import net.minecraft.class_2960;
import net.minecraft.class_3955;
import net.minecraft.class_5421;
import net.minecraft.class_8566;
import net.minecraft.class_8786;
import net.minecraft.class_9694;
import org.jetbrains.annotations.NotNull;

public class StarterKitInventoryMenu extends class_1729<class_9694, class_3955> {
   public static final int CONTAINER_ID = 0;
   public static final int RESULT_SLOT = 0;
   public static final int CRAFT_SLOT_START = 0;
   public static final int CRAFT_SLOT_END = 0;
   public static final int ARMOR_SLOT_START = 5;
   public static final int ARMOR_SLOT_END = 9;
   public static final int INV_SLOT_START = 9;
   public static final int INV_SLOT_END = 36;
   public static final int USE_ROW_SLOT_START = 36;
   public static final int USE_ROW_SLOT_END = 45;
   public static final int SHIELD_SLOT = 45;
   public static final class_2960 BLOCK_ATLAS = class_2960.method_60654("textures/atlas/blocks.png");
   public static final class_2960 EMPTY_ARMOR_SLOT_HELMET = class_2960.method_60654("item/empty_armor_slot_helmet");
   public static final class_2960 EMPTY_ARMOR_SLOT_CHESTPLATE = class_2960.method_60654("item/empty_armor_slot_chestplate");
   public static final class_2960 EMPTY_ARMOR_SLOT_LEGGINGS = class_2960.method_60654("item/empty_armor_slot_leggings");
   public static final class_2960 EMPTY_ARMOR_SLOT_BOOTS = class_2960.method_60654("item/empty_armor_slot_boots");
   public static final class_2960 EMPTY_ARMOR_SLOT_SHIELD = class_2960.method_60654("item/empty_armor_slot_shield");
   static final class_2960[] TEXTURE_EMPTY_SLOTS = new class_2960[]{
      EMPTY_ARMOR_SLOT_BOOTS, EMPTY_ARMOR_SLOT_LEGGINGS, EMPTY_ARMOR_SLOT_CHESTPLATE, EMPTY_ARMOR_SLOT_HELMET
   };
   private static final class_1304[] SLOT_IDS = new class_1304[]{class_1304.field_6169, class_1304.field_6174, class_1304.field_6172, class_1304.field_6166};
   public final boolean active = true;
   private final class_1657 owner;

   public StarterKitInventoryMenu(class_1657 player) {
      super(null, 0);
      this.owner = player;
      if (VariablesClient.cachedStarterKitInventory != null) {
         for (int i = 0; i < 4; i++) {
            final class_1304 equipmentSlot = SLOT_IDS[i];
            this.method_7621(new class_1735(VariablesClient.cachedStarterKitInventory, 39 - i, 8, 8 + i * 18) {
               public void method_48931(@NotNull class_1799 newStack, @NotNull class_1799 oldStack) {
                  StarterKitInventoryMenu.onEquipItem(StarterKitInventoryMenu.this.owner, equipmentSlot, newStack, oldStack);
                  super.method_48931(newStack, oldStack);
               }

               public int method_7675() {
                  return 1;
               }

               public boolean method_7680(@NotNull class_1799 stack) {
                  return false;
               }

               public boolean method_7674(@NotNull class_1657 player) {
                  class_1799 itemStack = this.method_7677();
                  return false;
               }

               public Pair<class_2960, class_2960> method_7679() {
                  return Pair.of(StarterKitInventoryMenu.BLOCK_ATLAS, StarterKitInventoryMenu.TEXTURE_EMPTY_SLOTS[equipmentSlot.method_5927()]);
               }
            });
         }

         for (int var5 = 0; var5 < 3; var5++) {
            for (int j = 0; j < 9; j++) {
               this.method_7621(new class_1735(VariablesClient.cachedStarterKitInventory, j + (var5 + 1) * 9, 8 + j * 18, 84 + var5 * 18));
            }
         }

         for (int var6 = 0; var6 < 9; var6++) {
            this.method_7621(new class_1735(VariablesClient.cachedStarterKitInventory, var6, 8 + var6 * 18, 142));
         }

         this.method_7621(new class_1735(VariablesClient.cachedStarterKitInventory, 40, 77, 62) {
            public void method_48931(@NotNull class_1799 newStack, @NotNull class_1799 oldStack) {
               StarterKitInventoryMenu.onEquipItem(StarterKitInventoryMenu.this.owner, class_1304.field_6171, newStack, oldStack);
               super.method_48931(newStack, oldStack);
            }

            public Pair<class_2960, class_2960> method_7679() {
               return Pair.of(StarterKitInventoryMenu.BLOCK_ATLAS, StarterKitInventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
            }
         });
      }
   }

   static void onEquipItem(class_1657 player, class_1304 slot, class_1799 newItem, class_1799 oldItem) {
      player.method_6116(slot, oldItem, newItem);
   }

   public static boolean isHotbarSlot(int index) {
      return index >= 36 && index < 45 || index == 45;
   }

   public void method_7654(@NotNull class_1662 itemHelper) {
   }

   public void method_7657() {
   }

   public boolean method_7652(@NotNull class_8786 recipeHolder) {
      return false;
   }

   public void method_7609(@NotNull class_1263 container) {
   }

   public void method_7595(@NotNull class_1657 player) {
      super.method_7595(player);
   }

   public boolean method_7597(@NotNull class_1657 player) {
      return true;
   }

   @NotNull
   public class_1799 method_7601(@NotNull class_1657 player, int index) {
      return class_1799.field_8037;
   }

   public boolean method_7613(@NotNull class_1799 stack, @NotNull class_1735 slot) {
      return false;
   }

   public int method_7655() {
      return 0;
   }

   public int method_7653() {
      return 0;
   }

   public int method_7656() {
      return 0;
   }

   public int method_7658() {
      return 5;
   }

   public class_8566 getCraftSlots() {
      return null;
   }

   @NotNull
   public class_5421 method_30264() {
      return class_5421.field_25763;
   }

   public boolean method_32339(int slotIndex) {
      return slotIndex != this.method_7655();
   }
}
