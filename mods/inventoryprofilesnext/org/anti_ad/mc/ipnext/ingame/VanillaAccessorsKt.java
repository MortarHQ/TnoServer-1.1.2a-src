package org.anti_ad.mc.ipnext.ingame;

import com.mojang.blaze3d.platform.Window;
import java.util.List;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.StonecutterMenu;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.anti_ad.mc.common.math2d.Point;
import org.anti_ad.mc.common.math2d.Rectangle;
import org.anti_ad.mc.ipnext.item.ItemStack$Companion;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.MutableItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nVanillaAccessors.kt\nKotlin\n*S Kotlin\n*F\n+ 1 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n*L\n1#1,209:1\n66#1,4:210\n66#1,4:214\n71#1:218\n66#1,7:219\n74#1:226\n66#1,10:227\n99#1:237\n101#1:238\n117#1:239\n*S KotlinDebug\n*F\n+ 1 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n*L\n71#1:210,4\n74#1:214,4\n88#1:218\n88#1:219,7\n93#1:226\n93#1:227,10\n103#1:237\n104#1:238\n111#1:239\n*E\n"
)
public final class VanillaAccessorsKt {
   @NotNull
   public static final ItemType get_itemType_/* $VF was: get(itemType) */(@NotNull ItemStack var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      Item var10002 = var0.getItem();
      Intrinsics.checkNotNullExpressionValue(var10002, "");
      DataComponentMap var10003 = var0.getComponents();
      Intrinsics.checkNotNull(var10003);
      return new ItemType(
         var10002, (PatchedDataComponentMap)var10003, var0.getComponentsPatch(), new VanillaAccessorsKt$(itemType)$1(var0), false, false, null, 112, null
      );
   }

   @NotNull
   public static final org.anti_ad.mc.ipnext.item.ItemStack get_itemStack_/* $VF was: get(itemStack) */(@NotNull ItemStack var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      if (var0.isEmpty()) {
         return ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
      }

      ItemStack$Companion var10000 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
      ItemStack var1 = var0;
      Item var10003 = var1.getItem();
      Intrinsics.checkNotNullExpressionValue(var10003, "");
      DataComponentMap var10004 = var1.getComponents();
      Intrinsics.checkNotNull(var10004);
      return var10000.invoke(
         new ItemType(
            var10003, (PatchedDataComponentMap)var10004, var1.getComponentsPatch(), new VanillaAccessorsKt$(itemType)$1(var1), false, false, null, 112, null
         ),
         var0.getCount()
      );
   }

   @NotNull
   public static final MutableItemStack get_mutableItemStack_/* $VF was: get(mutableItemStack) */(@NotNull ItemStack var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      if (var0.isEmpty()) {
         return ItemStackExtensionsKt.empty(MutableItemStack.Companion);
      }

      ItemStack var1 = var0;
      Item var10004 = var1.getItem();
      Intrinsics.checkNotNullExpressionValue(var10004, "");
      DataComponentMap var10005 = var1.getComponents();
      Intrinsics.checkNotNull(var10005);
      return new MutableItemStack(
         new ItemType(
            var10004, (PatchedDataComponentMap)var10005, var1.getComponentsPatch(), new VanillaAccessorsKt$(itemType)$1(var1), false, false, null, 112, null
         ),
         var0.getCount(),
         null,
         4,
         null
      );
   }

   @NotNull
   public static final List get_slots_/* $VF was: get(slots) */(@NotNull AbstractContainerMenu var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      NonNullList var10000 = var0.slots;
      Intrinsics.checkNotNullExpressionValue(var0.slots, "");
      return (List)var10000;
   }

   public static final int get_syncId_/* $VF was: get(syncId) */(@NotNull AbstractContainerMenu var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.containerId;
   }

   public static final int get_id_/* $VF was: get(id) */(@NotNull Slot var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.index;
   }

   public static final int get_invSlot_/* $VF was: get(invSlot) */(@NotNull Slot var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.getSlotIndex();
   }

   @NotNull
   public static final org.anti_ad.mc.ipnext.item.ItemStack get_itemStack_/* $VF was: get(itemStack) */(@NotNull Slot var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      ItemStack var10000 = var0.getItem();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      ItemStack var2 = var10000;
      if (var10000.isEmpty()) {
         return ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
      }

      ItemStack$Companion var3 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
      ItemStack var1 = var2;
      Item var10003 = var1.getItem();
      Intrinsics.checkNotNullExpressionValue(var10003, "");
      DataComponentMap var10004 = var1.getComponents();
      Intrinsics.checkNotNull(var10004);
      return var3.invoke(
         new ItemType(
            var10003, (PatchedDataComponentMap)var10004, var1.getComponentsPatch(), new VanillaAccessorsKt$(itemType)$1(var1), false, false, null, 112, null
         ),
         var2.getCount()
      );
   }

   @NotNull
   public static final ItemStack get_vanillaStack_/* $VF was: get(vanillaStack) */(@NotNull Slot var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      ItemStack var10000 = var0.getItem();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      return var10000;
   }

   @NotNull
   public static final MutableItemStack get_mutableItemStack_/* $VF was: get(mutableItemStack) */(@NotNull Slot var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      ItemStack var10000 = var0.getItem();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      ItemStack var2 = var10000;
      if (var10000.isEmpty()) {
         return ItemStackExtensionsKt.empty(MutableItemStack.Companion);
      }

      ItemStack var1 = var2;
      Item var10004 = var1.getItem();
      Intrinsics.checkNotNullExpressionValue(var10004, "");
      DataComponentMap var10005 = var1.getComponents();
      Intrinsics.checkNotNull(var10005);
      return new MutableItemStack(
         new ItemType(
            var10004, (PatchedDataComponentMap)var10005, var1.getComponentsPatch(), new VanillaAccessorsKt$(itemType)$1(var1), false, false, null, 112, null
         ),
         var2.getCount(),
         null,
         4,
         null
      );
   }

   @NotNull
   public static final Container get_inventory_/* $VF was: get(inventory) */(@NotNull Slot var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      Container var10000 = var0.container;
      Intrinsics.checkNotNullExpressionValue(var0.container, "");
      return var10000;
   }

   @Nullable
   public static final Container get_inventoryOrNull_/* $VF was: get(inventoryOrNull) */(@NotNull Slot var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.container;
   }

   public static final int get_left_/* $VF was: get(left) */(@NotNull Slot var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.x;
   }

   public static final int get_top_/* $VF was: get(top) */(@NotNull Slot var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.y;
   }

   @NotNull
   public static final Point get_topLeft_/* $VF was: get(topLeft) */(@NotNull Slot var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return new Point(var0.x, var0.y);
   }

   public static final boolean _canInsert_/* $VF was: (canInsert) */(@NotNull Slot var0, @NotNull org.anti_ad.mc.ipnext.item.ItemStack var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      return var0.mayPlace(ItemStackExtensionsKt.getVanillaStack(var1));
   }

   @Nullable
   public static final Slot get_focusedSlot_/* $VF was: get(focusedSlot) */(@NotNull Screen var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      AbstractContainerScreen var10000 = var0 instanceof AbstractContainerScreen ? (AbstractContainerScreen)var0 : null;
      if ((var0 instanceof AbstractContainerScreen ? (AbstractContainerScreen)var0 : null) != null) {
         Slot var1 = var10000.getSlotUnderMouse();
         if (var1 != null) {
            return InventoryKt.vPlayerSlotOf(var1, var0);
         }
      }

      return null;
   }

   @Nullable
   public static final Slot get_rawFocusedSlot_/* $VF was: get(rawFocusedSlot) */(@NotNull AbstractContainerScreen var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.getSlotUnderMouse();
   }

   @NotNull
   public static final Rectangle get_containerBounds_/* $VF was: get(containerBounds) */(@NotNull AbstractContainerScreen var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return new Rectangle(var0.getGuiLeft(), var0.getGuiTop(), var0.getXSize(), var0.getYSize());
   }

   @NotNull
   public static final AbstractContainerMenu get_container_/* $VF was: get(container) */(@NotNull AbstractContainerScreen var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      AbstractContainerMenu var10000 = var0.getMenu();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      return var10000;
   }

   public static final int get_selectedSlot_/* $VF was: get(selectedSlot) */(@NotNull Inventory var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.selected;
   }

   public static final void set_selectedSlot_/* $VF was: set(selectedSlot) */(@NotNull Inventory var0, int var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      var0.selected = var1;
   }

   public static final boolean get_isInventoryTab_/* $VF was: get(isInventoryTab) */(@NotNull CreativeModeInventoryScreen var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.isInventoryOpen();
   }

   public static final int _getRawId_/* $VF was: (getRawId) */(@NotNull DefaultedRegistry var0, Object var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.getId(var1);
   }

   public static final Object _getByIdentifier_/* $VF was: (getByIdentifier) */(@NotNull DefaultedRegistry var0, @NotNull ResourceLocation var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      return var0.get(var1);
   }

   public static final int _getRawId_/* $VF was: (getRawId) */(@NotNull Registry var0, Object var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.getId(var1);
   }

   @Nullable
   public static final Object _getByIdentifier_/* $VF was: (getByIdentifier) */(@NotNull Registry var0, @NotNull ResourceLocation var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      return var0.get(var1);
   }

   public static final int get_type_/* $VF was: get(type) */(@NotNull Tag var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.getId();
   }

   @NotNull
   public static final String get_asString_/* $VF was: get(asString) */(@NotNull Tag var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      String var10000 = var0.getAsString();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      return var10000;
   }

   @NotNull
   public static final Set get_keys_/* $VF was: get(keys) */(@NotNull CompoundTag var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      Set var10000 = var0.getAllKeys();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      return var10000;
   }

   @NotNull
   public static final Window get_window_/* $VF was: get(window) */(@NotNull Minecraft var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      Window var10000 = var0.getWindow();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      return var10000;
   }

   @NotNull
   public static final Options get_options_/* $VF was: get(options) */(@NotNull Minecraft var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      Options var10000 = var0.options;
      Intrinsics.checkNotNullExpressionValue(var0.options, "");
      return var10000;
   }

   @NotNull
   public static final KeyMapping get_keyDrop_/* $VF was: get(keyDrop) */(@NotNull Options var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      KeyMapping var10000 = var0.keyDrop;
      Intrinsics.checkNotNullExpressionValue(var0.keyDrop, "");
      return var10000;
   }

   public static final boolean get_isPressed_/* $VF was: get(isPressed) */(@NotNull KeyMapping var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.isDown();
   }

   public static final int get_scaledWidth_/* $VF was: get(scaledWidth) */(@NotNull Window var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.getGuiScaledWidth();
   }

   public static final int get_scaledHeight_/* $VF was: get(scaledHeight) */(@NotNull Window var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.getGuiScaledHeight();
   }

   @NotNull
   public static final EquipmentSlot get_equipmentSlot_/* $VF was: get(equipmentSlot) */(@NotNull ArmorItem var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      EquipmentSlot var10000 = var0.getEquipmentSlot();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      return var10000;
   }

   public static final void _clickSlot_/* $VF was: (clickSlot) */(
      @NotNull MultiPlayerGameMode var0, int var1, int var2, int var3, @NotNull ClickType var4, @NotNull Player var5
   ) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var4, "");
      Intrinsics.checkNotNullParameter(var5, "");
      var0.handleInventoryMouseClick(var1, var2, var3, var4, var5);
   }

   public static final void _onSlotClick_/* $VF was: (onSlotClick) */(
      @NotNull InventoryMenu var0, int var1, int var2, @NotNull ClickType var3, @NotNull Player var4
   ) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var3, "");
      Intrinsics.checkNotNullParameter(var4, "");
      var0.clicked(var1, var2, var3, var4);
   }

   public static final void _sendContentUpdates_/* $VF was: (sendContentUpdates) */(@NotNull InventoryMenu var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      var0.broadcastChanges();
   }

   public static final int get_selectedRecipe_/* $VF was: get(selectedRecipe) */(@NotNull StonecutterMenu var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0.getSelectedRecipeIndex();
   }

   public static final void _send_/* $VF was: (send) */(@NotNull Minecraft var0, @NotNull Runnable var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      var0.tell(var1);
   }
}
