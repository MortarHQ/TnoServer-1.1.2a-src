package org.anti_ad.mc.ipnext.inventory;

import java.util.List;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.BeaconMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.config.PostAction;
import org.anti_ad.mc.ipnext.ingame.InventoryKt;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt$(itemType)$1;
import org.anti_ad.mc.ipnext.integration.HintsManagerNG;
import org.anti_ad.mc.ipnext.inventory.action.SubTrackerActionsKt;
import org.anti_ad.mc.ipnext.item.ItemStack$Companion;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.rule.Rule;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nGeneralInventoryActions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GeneralInventoryActions.kt\norg/anti_ad/mc/ipnext/inventory/InnerActions\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 3 common.kt\norg/anti_ad/mc/common/extensions/CommonKt\n*L\n1#1,502:1\n97#2:503\n78#2,11:518\n71#2:529\n66#2,7:530\n70#3,14:504\n*S KotlinDebug\n*F\n+ 1 GeneralInventoryActions.kt\norg/anti_ad/mc/ipnext/inventory/InnerActions\n*L\n448#1:503\n495#1:518,11\n495#1:529\n495#1:530,7\n455#1:504,14\n*E\n"
)
final class InnerActions {
   @NotNull
   public static final InnerActions INSTANCE = new InnerActions();

   private InnerActions() {
   }

   private final boolean forcePlayerSide() {
      if (ModSettings.INSTANCE.getSORT_AT_CURSOR().getBooleanValue()) {
         Slot var10000 = InventoryKt.vFocusedSlot();
         if ((var10000 != null ? var10000.container : null) instanceof Inventory) {
            return true;
         }
      }

      HintsManagerNG var1 = HintsManagerNG.INSTANCE;
      Screen var10001 = Vanilla.INSTANCE.screen();
      return var1.isPlayerSideOnly(var10001 != null ? var10001.getClass() : null);
   }

   @Nullable
   public final Unit doSort(@NotNull Rule var1, @NotNull PostAction var2, boolean var3, @NotNull AbstractContainerMenu var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var4, "");

      try {
         INSTANCE.innerDoSort(var1, var2, var3, var4);
         var6 = Unit.INSTANCE;
      } catch (Throwable var5) {
         var5.printStackTrace();
         var6 = null;
      }

      return var6;
   }

   public final void innerDoSort(@NotNull Rule var1, @NotNull PostAction var2, boolean var3, @NotNull AbstractContainerMenu var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var4, "");
      AdvancedContainer$Companion.tracker$default(AdvancedContainer.Companion, false, false, InnerActions::innerDoSort$lambda$2, 3, null);
   }

   public final void cleanTempSlotsForClosing() {
      if (Vanilla.INSTANCE.container() instanceof BeaconMenu) {
         NonNullList var10000 = Vanilla.INSTANCE.container().slots;
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         ItemStack var3 = ((Slot)((List)var10000).get(0)).getItem();
         Intrinsics.checkNotNullExpressionValue(var3, "");
         ItemStack var1 = var3;
         org.anti_ad.mc.ipnext.item.ItemStack var4;
         if (var3.isEmpty()) {
            var4 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
         } else {
            ItemStack$Companion var5 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
            ItemStack var2 = var1;
            Item var10003 = var2.getItem();
            Intrinsics.checkNotNullExpressionValue(var10003, "");
            DataComponentMap var10004 = var2.getComponents();
            Intrinsics.checkNotNull(var10004);
            var4 = var5.invoke(
               new ItemType(
                  var10003,
                  (PatchedDataComponentMap)var10004,
                  var2.getComponentsPatch(),
                  new VanillaAccessorsKt$(itemType)$1(var2),
                  false,
                  false,
                  null,
                  112,
                  null
               ),
               var1.getCount()
            );
         }

         if (!ItemStackExtensionsKt.isEmpty(var4)) {
            ContainerClicker.INSTANCE.shiftClick(0);
         }
      }
   }

   private static final Unit innerDoSort$lambda$2(boolean var0, AbstractContainerMenu var1, Rule var2, PostAction var3, AdvancedContainer$TrackerDsl var4) {
      Intrinsics.checkNotNullParameter(var4, "");
      AreaTypes var5 = AreaTypes.INSTANCE;
      ItemArea var7;
      if (!var0 && !INSTANCE.forcePlayerSide() && !var4.get(var5.getSortableItemStorage()).isEmpty()) {
         AreaType var8 = var5.getSortableItemStorage().minus(var5.getDisabled(var1));
         var7 = var4.get(var8);
      } else {
         AreaType var6 = var5.getPlayerStorage().minus(var5.getLockedSlots()).minus(var5.getDisabled(var1));
         var7 = var4.get(var6);
         if (ModSettings.INSTANCE.getRESTOCK_HOTBAR().getBooleanValue()) {
            SubTrackerActionsKt.restockFrom(var4.getAsSubTracker(var4.get(var5.getPlayerHands().plus(var5.getPlayerHotbar()))), var4.getAsSubTracker(var7));
         }
      }

      SubTrackerActionsKt.sort(var4.getAsSubTracker(var7), var2, var3, var7.isRectangular(), var7.getWidth(), var7.getHeight());
      return Unit.INSTANCE;
   }
}
