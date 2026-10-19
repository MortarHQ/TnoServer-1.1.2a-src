package org.anti_ad.mc.ipnext.ingame;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import org.anti_ad.mc.alias.client.gui.screen.recipebook.RecipeBookExKt;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.item.ItemStack;
import org.anti_ad.mc.ipnext.item.ItemStack$Companion;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nInventory.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Inventory.kt\norg/anti_ad/mc/ipnext/ingame/InventoryKt\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,106:1\n42#1:119\n42#1:121\n42#1:123\n42#1:125\n42#1:127\n78#2:107\n71#2:108\n66#2,7:109\n97#2:116\n84#2:117\n86#2:118\n78#2:120\n78#2:122\n78#2:124\n78#2:126\n78#2:128\n111#2,7:129\n127#2:136\n1761#3,3:137\n*S KotlinDebug\n*F\n+ 1 Inventory.kt\norg/anti_ad/mc/ipnext/ingame/InventoryKt\n*L\n56#1:119\n60#1:121\n61#1:123\n62#1:125\n63#1:127\n42#1:107\n46#1:108\n46#1:109,7\n51#1:116\n53#1:117\n54#1:118\n56#1:120\n60#1:122\n61#1:124\n62#1:126\n63#1:128\n75#1:129,7\n79#1:136\n82#1:137,3\n*E\n"
)
public final class InventoryKt {
   private static final List getVPlayerSlots() {
      NonNullList var10000 = ((AbstractContainerMenu)Vanilla.INSTANCE.playerContainer()).slots;
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      return (List)var10000;
   }

   @NotNull
   public static final ItemStack vCursorStack() {
      net.minecraft.world.item.ItemStack var10000 = Vanilla.INSTANCE.playerInventory().player.containerMenu.getCarried();
      if (var10000 != null) {
         net.minecraft.world.item.ItemStack var0 = var10000;
         ItemStack var2;
         if (var10000.isEmpty()) {
            var2 = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
         } else {
            ItemStack$Companion var3 = ItemStack.Companion;
            net.minecraft.world.item.ItemStack var1 = var0;
            Item var10003 = var1.getItem();
            Intrinsics.checkNotNullExpressionValue(var10003, "");
            DataComponentMap var10004 = var1.getComponents();
            Intrinsics.checkNotNull(var10004);
            var2 = var3.invoke(
               new ItemType(
                  var10003,
                  (PatchedDataComponentMap)var10004,
                  var1.getComponentsPatch(),
                  new VanillaAccessorsKt$(itemType)$1(var1),
                  false,
                  false,
                  null,
                  112,
                  null
               ),
               var0.getCount()
            );
         }

         if (var2 != null) {
            return var2;
         }
      }

      return ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
   }

   @NotNull
   public static final Slot vPlayerSlotOf(@NotNull Slot var0, @Nullable Screen var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      if (!(var1 instanceof CreativeModeInventoryScreen)) {
         return var0;
      } else if (var0.container == null) {
         return var0;
      } else if (!(var0.container instanceof Inventory)) {
         return var0;
      } else {
         int var3 = var0.index;
         int var2;
         if (((var2 = var0.getSlotIndex()) >= 0 ? var2 < 9 : false) && var3 == var2 + 45) {
            NonNullList var7 = ((AbstractContainerMenu)Vanilla.INSTANCE.playerContainer()).slots;
            Intrinsics.checkNotNullExpressionValue(var7, "");
            return (Slot)((List)var7).get(var2 + 36);
         } else if ((9 <= var2 ? var2 < 36 : false) && var3 == 0) {
            NonNullList var6 = ((AbstractContainerMenu)Vanilla.INSTANCE.playerContainer()).slots;
            Intrinsics.checkNotNullExpressionValue(var6, "");
            return (Slot)((List)var6).get(var2);
         } else if ((var2 >= 0 ? var2 < 9 : false) && var3 == 0) {
            NonNullList var5 = ((AbstractContainerMenu)Vanilla.INSTANCE.playerContainer()).slots;
            Intrinsics.checkNotNullExpressionValue(var5, "");
            return (Slot)((List)var5).get(var2 + 36);
         } else if ((36 <= var2 ? var2 < 40 : false) && var3 == 0) {
            NonNullList var4 = ((AbstractContainerMenu)Vanilla.INSTANCE.playerContainer()).slots;
            Intrinsics.checkNotNullExpressionValue(var4, "");
            return (Slot)((List)var4).get(44 - var2);
         } else if ((40 <= var2 ? var2 < 41 : false) && var3 == 0) {
            NonNullList var10000 = ((AbstractContainerMenu)Vanilla.INSTANCE.playerContainer()).slots;
            Intrinsics.checkNotNullExpressionValue(var10000, "");
            return (Slot)((List)var10000).get(45);
         } else {
            return var0;
         }
      }
   }

   @Nullable
   public static final Slot vFocusedSlot() {
      Screen var10000 = Vanilla.INSTANCE.screen();
      if (var10000 != null) {
         Screen var0 = var10000;
         AbstractContainerScreen var1 = var10000 instanceof AbstractContainerScreen ? (AbstractContainerScreen)var0 : null;
         if ((var10000 instanceof AbstractContainerScreen ? (AbstractContainerScreen)var0 : null) != null) {
            Slot var2 = var1.getSlotUnderMouse();
            if (var2 != null) {
               return vPlayerSlotOf(var2, var0);
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public static final int vMainhandIndex() {
      return Vanilla.INSTANCE.playerInventory().selected;
   }

   public static final boolean isInputFieldActive(@NotNull AbstractContainerScreen var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      List var10000 = var0.children();
      if (var10000 == null) {
         return false;
      }

      Iterable var2;
      if (!((var2 = var10000) instanceof Collection) || !((Collection)var2).isEmpty()) {
         Iterator var3 = var2.iterator();

         while (var3.hasNext()) {
            label44: {
               label61: {
                  GuiEventListener var1;
                  if ((var1 = (GuiEventListener)var3.next()) instanceof RecipeBookComponent) {
                     EditBox var4 = RecipeBookExKt.get_searchField_/* $VF was: get(searchField) */((RecipeBookComponent)var1);
                     if (var4 != null ? var4.isFocused() : false) {
                        break label61;
                     }
                  }

                  if (!(var1 instanceof EditBox) || !((EditBox)var1).isFocused()) {
                     var5 = false;
                     break label44;
                  }
               }

               var5 = true;
            }

            if (var5) {
               return true;
            }
         }
      }

      return false;
   }
}
