package org.anti_ad.mc.ipnext.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt$(itemType)$1;
import org.anti_ad.mc.ipnext.inventory.AdvancedContainer;
import org.anti_ad.mc.ipnext.inventory.AdvancedContainer$Companion;
import org.anti_ad.mc.ipnext.inventory.AdvancedContainer$TrackerDsl;
import org.anti_ad.mc.ipnext.inventory.AreaTypes;
import org.anti_ad.mc.ipnext.inventory.data.ItemBucket;
import org.anti_ad.mc.ipnext.inventory.data.ItemStackListExtensionsKt;
import org.anti_ad.mc.ipnext.inventory.data.MutableSubTracker;
import org.anti_ad.mc.ipnext.item.ItemStack;
import org.anti_ad.mc.ipnext.item.ItemStack$Companion;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.MutableItemStack;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nContinuousCraftingHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ContinuousCraftingHandler.kt\norg/anti_ad/mc/ipnext/event/ContinuousCraftingHandler$MonitorP\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 5 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 6 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,367:1\n78#2:368\n97#2:371\n88#2:378\n71#2:379\n66#2,7:380\n84#2:394\n774#3:369\n865#3:370\n866#3:372\n1563#3:373\n1634#3,3:374\n1869#3,2:395\n1252#3,2:399\n1563#3:410\n1634#3,3:411\n1255#3:414\n1869#3,2:415\n1#4:377\n384#5,7:387\n465#5:397\n415#5:398\n98#6,9:401\n*S KotlinDebug\n*F\n+ 1 ContinuousCraftingHandler.kt\norg/anti_ad/mc/ipnext/event/ContinuousCraftingHandler$MonitorP\n*L\n293#1:368\n294#1:371\n318#1:378\n318#1:379\n318#1:380,7\n322#1:394\n294#1:369\n294#1:370\n294#1:372\n297#1:373\n297#1:374,3\n355#1:395,2\n336#1:399,2\n337#1:410\n337#1:411,3\n336#1:414\n339#1:415,2\n320#1:387,7\n336#1:397\n336#1:398\n337#1:401,9\n*E\n"
)
final class ContinuousCraftingHandler$MonitorP implements ContinuousCraftingHandler$IMonitor {
   @NotNull
   private final List containerSlots;
   @NotNull
   private final List ingredientSlots;
   @NotNull
   private final List slotMonitors;
   @NotNull
   private final List playerSlotIndices;

   public ContinuousCraftingHandler$MonitorP(@NotNull AbstractContainerMenu var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super();
      NonNullList var10001 = var1.slots;
      Intrinsics.checkNotNullExpressionValue(var1.slots, "");
      this.containerSlots = (List)var10001;
      ArrayList var2 = this.getContainerSlots();
      ContinuousCraftingHandler$MonitorP var6 = this;
      Iterable var3 = var2;
      var2 = new ArrayList();
      Iterator var11 = var3.iterator();

      while (var11.hasNext()) {
         Object var4;
         if (((Slot)(var4 = var11.next())).container instanceof CraftingContainer) {
            var2.add(var4);
         }
      }

      var6.ingredientSlots = var2;
      List var8 = this.getIngredientSlots();
      var6 = this;
      var3 = var8;
      var8 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var8, 10));

      for (Object var14 : var3) {
         Slot var5 = (Slot)var14;
         ArrayList var15 = var8;
         var15.add(new ContinuousCraftingHandler$ItemSlotMonitorP(var5));
      }

      var6.slotMonitors = var8;
      AreaTypes var10 = AreaTypes.INSTANCE;
      var6 = this;
      var6.playerSlotIndices = var10.getPlayerStorage()
         .plus(var10.getPlayerHotbar())
         .plus(var10.getPlayerOffhand())
         .minus(var10.getLockedSlots())
         .getItemArea(var1, this.getContainerSlots())
         .getSlotIndices();
   }

   @NotNull
   @Override
   public final List getContainerSlots() {
      return this.containerSlots;
   }

   @NotNull
   @Override
   public final List getIngredientSlots() {
      return this.ingredientSlots;
   }

   @NotNull
   @Override
   public final List getSlotMonitors() {
      return this.slotMonitors;
   }

   @NotNull
   @Override
   public final List getPlayerSlotIndices() {
      return this.playerSlotIndices;
   }

   @Override
   public final boolean shouldHandle(@NotNull ItemStack var1, @NotNull ItemStack var2, @NotNull Slot var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      Log.INSTANCE.trace("Checking if wee should load more items into crafting slot");
      if (ItemStackExtensionsKt.isEmpty(var1)) {
         return false;
      }

      Log.INSTANCE.trace("Should handle: " + ItemStackExtensionsKt.isEmpty(var2));
      return ItemStackExtensionsKt.isEmpty(var2);
   }

   @Override
   public final boolean autoRefill() {
      boolean var1 = false;
      Map var2 = new LinkedHashMap();

      for (ContinuousCraftingHandler$ItemSlotMonitorP var4 : this.getSlotMonitors()) {
         ItemStack var10001 = var4.getStoredItem();
         net.minecraft.world.item.ItemStack var10002 = var4.getSlot().getItem();
         Intrinsics.checkNotNullExpressionValue(var10002, "");
         net.minecraft.world.item.ItemStack var5 = var10002;
         ItemStack var10;
         if (var10002.isEmpty()) {
            var10 = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
         } else {
            ItemStack$Companion var11 = ItemStack.Companion;
            net.minecraft.world.item.ItemStack var6 = var5;
            Item var10005 = var6.getItem();
            Intrinsics.checkNotNullExpressionValue(var10005, "");
            DataComponentMap var10006 = var6.getComponents();
            Intrinsics.checkNotNull(var10006);
            var10 = var11.invoke(
               new ItemType(
                  var10005,
                  (PatchedDataComponentMap)var10006,
                  var6.getComponentsPatch(),
                  new VanillaAccessorsKt$(itemType)$1(var6),
                  false,
                  false,
                  null,
                  112,
                  null
               ),
               var5.getCount()
            );
         }

         if (this.shouldHandle(var10001, var10, var4.getSlot())) {
            Map var8 = var2;
            ItemType var7 = var4.getStoredItem().getItemType();
            Object var9;
            if ((var9 = var8.get(var7)) == null) {
               var9 = new ArrayList();
               var8.put(var7, var9);
            }

            ((List)var9).add(var4.getSlot().index);
            var1 = true;
         }
      }

      if (var2.isEmpty()) {
         return var1;
      }

      AdvancedContainer$Companion.tracker$default(AdvancedContainer.Companion, true, false, ContinuousCraftingHandler$MonitorP::autoRefill$lambda$10, 2, null);
      return true;
   }

   @Override
   public final void save() {
      Iterator var1 = this.getSlotMonitors().iterator();

      while (var1.hasNext()) {
         ((ContinuousCraftingHandler$ItemSlotMonitorP)var1.next()).save();
      }
   }

   private static final ItemStack autoRefill$lambda$10$lambda$5(ItemStack var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      return var0;
   }

   private static final Unit autoRefill$lambda$10(ContinuousCraftingHandler$MonitorP var0, Map var1, AdvancedContainer$TrackerDsl var2) {
      Intrinsics.checkNotNullParameter(var2, "");
      MutableSubTracker var12;
      ItemBucket var3 = ItemStackListExtensionsKt.processAndCollect(
         (var12 = var2.getTracker().subTracker(var0.getPlayerSlotIndices())).getSlots(), ContinuousCraftingHandler$MonitorP::autoRefill$lambda$10$lambda$5
      );
      Map var4 = var1;
      Map var5 = var1;
      var4 = new LinkedHashMap(MapsKt.mapCapacity(var4.size()));

      for (Object var6 : var5.entrySet()) {
         Entry var7 = (Entry)var6;
         Map var8 = var4;
         net.minecraft.world.item.ItemStack var10001 = (net.minecraft.world.item.ItemStack)var7.getKey();
         var7 = (Entry)var6;
         Object var11 = var10001;
         Map var10 = var8;
         ItemType var19 = (ItemType)var7.getKey();
         List var24 = (List)var7.getValue();
         int var10000 = var3.count(var19) / var24.size();
         ItemType var28 = var19;
         var10001 = new net.minecraft.world.item.ItemStack;
         ItemLike var10003 = (ItemLike)var28.getItem();
         PatchedDataComponentMap var10005 = var28.getTag();
         if (var10005 == null) {
            DataComponentMap var38 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10005 = (PatchedDataComponentMap)var38;
         }

         var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
         Integer var35 = RangesKt.coerceAtMost(var10000, var10001.getMaxStackSize());
         Iterable var20 = var24;
         Integer var25 = var35;
         Iterable var29 = var20;
         var var21 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var20, 10));

         for (Object var32 : var29) {
            int var33 = ((Number)var32).intValue();
            ArrayList var9 = var21;
            var9.add((MutableItemStack)var2.getTracker().getSlots().get(var33));
         }

         Pair var22 = TuplesKt.to(var25, var21);
         var10.put(var11, var22);
      }

      var1 = var4;

      for (MutableItemStack var13 : var12.getSlots()) {
         Pair var36 = (Pair)var1.get(var13.getItemType());
         if (var36 != null) {
            Pair var16 = var36;
            int var26 = ((Number)var36.component1()).intValue();
            Iterator var34 = ((List)var16.component2()).iterator();

            while (var34.hasNext()) {
               MutableItemStack var17;
               if ((var17 = (MutableItemStack)var34.next()).getCount() < var26) {
                  if (ItemStackExtensionsKt.isEmpty(var13)) {
                     break;
                  }

                  int var31 = var26 - var17.getCount();
                  ItemStackExtensionsKt.transferNTo(var13, var17, var31);
               }
            }
         }
      }

      return Unit.INSTANCE;
   }
}
