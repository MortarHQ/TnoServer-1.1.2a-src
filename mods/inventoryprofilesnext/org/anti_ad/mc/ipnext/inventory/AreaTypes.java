package org.anti_ad.mc.ipnext.inventory;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntRange;
import kotlin.reflect.jvm.KClassesJvm;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.MerchantResultSlot;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.inventory.Slot;
import org.anti_ad.mc.alias.screen.slot.SlotExKt;
import org.anti_ad.mc.ipnext.event.LockSlotsHandler;
import org.anti_ad.mc.ipnext.ingame.InventoryKt;
import org.anti_ad.mc.ipnext.integration.HintClassData;
import org.anti_ad.mc.ipnext.integration.HintsManagerNG;
import org.anti_ad.mc.ipnext.integration.SlotIntegrationHints;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nAreaTypes.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AreaTypes.kt\norg/anti_ad/mc/ipnext/inventory/AreaTypes\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n*L\n1#1,384:1\n97#2:385\n86#2:386\n95#2:387\n95#2:388\n97#2:389\n97#2:390\n95#2:391\n*S KotlinDebug\n*F\n+ 1 AreaTypes.kt\norg/anti_ad/mc/ipnext/inventory/AreaTypes\n*L\n118#1:385\n120#1:386\n138#1:387\n139#1:388\n156#1:389\n157#1:390\n157#1:391\n*E\n"
)
public final class AreaTypes {
   @NotNull
   public static final AreaTypes INSTANCE = new AreaTypes();
   @NotNull
   private static PlayerSlotIds playerSlotIds = new PlayerSlotIds(null, null, 0, null, 15, null);
   @NotNull
   private static final AreaType focusedSlot = AreaType$Companion.inSlots$default(AreaType.Companion, false, AreaTypes::focusedSlot$lambda$0, 1, null);
   @NotNull
   private static final AreaType playerStorage = AreaType$Companion.playerInvSlots$default(
      AreaType.Companion, false, AreaTypes::playerStorage$lambda$1, 1, null
   );
   @NotNull
   private static final AreaType playerHotbar = AreaType$Companion.playerInvSlots$default(AreaType.Companion, false, AreaTypes::playerHotbar$lambda$2, 1, null);
   @NotNull
   private static final AreaType playerMainhand = AreaType.Companion.playerInvSlots(true, AreaTypes::playerMainhand$lambda$3);
   @NotNull
   private static final AreaType playerOffhand;
   @NotNull
   private static final AreaType playerHands;
   @NotNull
   private static final AreaType itemStorage = AreaTypes$itemStorage$1.INSTANCE;
   @NotNull
   private static final AreaType sortableItemStorage = AreaTypes$sortableItemStorage$1.INSTANCE;
   @NotNull
   private static final AreaType lockedSlots = AreaType$Companion.playerInvSlots$default(AreaType.Companion, false, AreaTypes::lockedSlots$lambda$4, 1, null);
   @NotNull
   private static final AreaType craftingIngredient = AreaType$Companion.match$default(
      AreaType.Companion, false, AreaTypes::craftingIngredient$lambda$7, 1, null
   );

   private AreaTypes() {
   }

   @NotNull
   public final PlayerSlotIds getPlayerSlotIds() {
      return playerSlotIds;
   }

   public final void setPlayerSlotIds(@NotNull PlayerSlotIds var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      playerSlotIds = var1;
   }

   private final IntRange getHotbarInvSlots() {
      return playerSlotIds.getHotbarInvSlots();
   }

   private final IntRange getStorageInvSlots() {
      return playerSlotIds.getStorageInvSlots();
   }

   private final int getOffhandInvSlot() {
      return playerSlotIds.getOffhandInvSlot();
   }

   private final int getMainhandInvSlot() {
      return ((Number)playerSlotIds.getMainhandInvSlot().invoke()).intValue();
   }

   @NotNull
   public final AreaType getFocusedSlot() {
      return focusedSlot;
   }

   @NotNull
   public final AreaType getPlayerStorage() {
      return playerStorage;
   }

   @NotNull
   public final AreaType getPlayerHotbar() {
      return playerHotbar;
   }

   @NotNull
   public final AreaType getPlayerMainhand() {
      return playerMainhand;
   }

   @NotNull
   public final AreaType getPlayerOffhand() {
      return playerOffhand;
   }

   @NotNull
   public final AreaType getPlayerHands() {
      return playerHands;
   }

   @NotNull
   public final AreaType getItemStorage() {
      return itemStorage;
   }

   @NotNull
   public final AreaType getSortableItemStorage() {
      return sortableItemStorage;
   }

   private final void fillSlots(Set var1, List var2, List var3) {
      boolean var7 = var1.contains(ContainerType.HORSE_STORAGE);
      Iterator var8 = var2.iterator();
      int var4 = 0;

      while (var8.hasNext()) {
         int var5 = var4++;
         Slot var6;
         int var9;
         if (!((var6 = (Slot)var8.next()).container instanceof Inventory) && (!var7 || ((var9 = var6.getSlotIndex()) >= 0 ? var9 >= 2 : true))) {
            var3.add(var5);
         }
      }
   }

   @NotNull
   public final AreaType getLockedSlots() {
      return lockedSlots;
   }

   @NotNull
   public final AreaType getDisabled(@NotNull AbstractContainerMenu var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return AreaType$Companion.match$default(AreaType.Companion, false, AreaTypes::_get_disabled_$lambda$6, 1, null);
   }

   @NotNull
   public final AreaType getCraftingIngredient() {
      return craftingIngredient;
   }

   private static final Collection focusedSlot$lambda$0() {
      return CollectionsKt.listOfNotNull(InventoryKt.vFocusedSlot());
   }

   private static final Iterable playerStorage$lambda$1() {
      return (Iterable)INSTANCE.getStorageInvSlots();
   }

   private static final Iterable playerHotbar$lambda$2() {
      return (Iterable)INSTANCE.getHotbarInvSlots();
   }

   private static final Iterable playerMainhand$lambda$3() {
      return CollectionsKt.listOf(INSTANCE.getMainhandInvSlot());
   }

   private static final Iterable lockedSlots$lambda$4() {
      return LockSlotsHandler.INSTANCE.getLockedInvSlots();
   }

   private static final boolean _get_disabled_$lambda$6(AbstractContainerMenu var0, Slot var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      String var2 = KClassesJvm.getJvmName(Reflection.getOrCreateKotlinClass(var1.getClass()));
      if (!SlotExKt.get_disablesDynamicDisplay_/* $VF was: get(disablesDynamicDisplay) */(var1)
         && SlotExKt.get_isEnabled_/* $VF was: get(isEnabled) */(var1)
         && var1.x >= 0
         && var1.y >= 0
         && !SlotIntegrationHints.INSTANCE.hintFor(var2).getIgnore()) {
         boolean var6;
         label40: {
            Class var4 = var0.getClass();
            HintClassData var3 = HintsManagerNG.INSTANCE.getHints(var4);
            label28:
            if (var1.getClass() != var4 || !var3.getSlotIgnoreInventoryTypes().contains(var2)) {
               if (var3.getIgnoreCraftingGrid()) {
                  Container var10000 = var1.container;
                  Intrinsics.checkNotNullExpressionValue(var1.container, "");
                  if (var10000 instanceof CraftingContainer) {
                     break label28;
                  }

                  Container var5 = var1.container;
                  Intrinsics.checkNotNullExpressionValue(var1.container, "");
                  if (var5 instanceof ResultContainer) {
                     break label28;
                  }
               }

               var6 = false;
               break label40;
            }

            var6 = true;
         }

         if (!var6) {
            return false;
         }
      }

      return true;
   }

   private static final boolean craftingIngredient$lambda$7(Slot var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      if (var0.container instanceof CraftingContainer && var0.container != null) {
         Container var10000 = var0.container;
         Intrinsics.checkNotNullExpressionValue(var0.container, "");
         if (!(var10000 instanceof ResultContainer) && !(var0 instanceof ResultSlot) && !(var0 instanceof MerchantResultSlot)) {
            return true;
         }
      }

      return false;
   }

   static {
      AreaType$Companion var10000 = AreaType.Companion;
      int[] var0;
      (var0 = new int[1])[0] = INSTANCE.getOffhandInvSlot();
      playerOffhand = var10000.playerInvSlots(var0, true);
      playerHands = playerMainhand.plus(playerOffhand);
   }
}
