package org.anti_ad.mc.ipnext.inventory.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.config.PostAction;
import org.anti_ad.mc.ipnext.ingame.InventoryKt;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt$(itemType)$1;
import org.anti_ad.mc.ipnext.inventory.data.ItemBucket;
import org.anti_ad.mc.ipnext.inventory.data.ItemStackListExtensionsKt;
import org.anti_ad.mc.ipnext.inventory.data.MutableItemTracker;
import org.anti_ad.mc.ipnext.inventory.data.MutableSubTracker;
import org.anti_ad.mc.ipnext.item.ItemStack;
import org.anti_ad.mc.ipnext.item.ItemStack$Companion;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.ItemTypeExtensionsKt;
import org.anti_ad.mc.ipnext.item.MutableItemStack;
import org.anti_ad.mc.ipnext.item.rule.Rule;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nSubTrackerActions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SubTrackerActions.kt\norg/anti_ad/mc/ipnext/inventory/action/SubTrackerActionsKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 4 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n+ 5 ItemStack.kt\norg/anti_ad/mc/ipnext/item/ItemStack\n+ 6 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 7 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,255:1\n1869#2,2:256\n1869#2,2:258\n1869#2,2:260\n1869#2,2:262\n1869#2,2:264\n1869#2,2:275\n774#2:277\n865#2,2:278\n1869#2:280\n774#2:281\n865#2,2:282\n1870#2:284\n1869#2:285\n1870#2:295\n827#2:296\n855#2,2:297\n1563#2:299\n1634#2,3:300\n785#2:303\n796#2:304\n1878#2,2:305\n797#2:307\n798#2:318\n1880#2:319\n799#2:320\n1563#2:321\n1634#2,2:322\n1636#2:333\n1878#2,2:336\n1880#2:339\n1869#2,2:340\n1869#2,2:342\n88#3:266\n71#3:267\n66#3,7:268\n98#4,9:286\n98#4,9:309\n98#4,9:324\n34#5:308\n216#6,2:334\n1#7:338\n*S KotlinDebug\n*F\n+ 1 SubTrackerActions.kt\norg/anti_ad/mc/ipnext/inventory/action/SubTrackerActionsKt\n*L\n51#1:256,2\n59#1:258,2\n67#1:260,2\n74#1:262,2\n77#1:264,2\n91#1:275,2\n97#1:277\n97#1:278,2\n105#1:280\n107#1:281\n107#1:282,2\n105#1:284\n132#1:285\n132#1:295\n144#1:296\n144#1:297,2\n144#1:299\n144#1:300,3\n169#1:303\n169#1:304\n169#1:305,2\n169#1:307\n169#1:318\n169#1:319\n169#1:320\n176#1:321\n176#1:322,2\n176#1:333\n219#1:336,2\n219#1:339\n232#1:340,2\n242#1:342,2\n83#1:266\n83#1:267\n83#1:268,7\n133#1:286,9\n170#1:309,9\n178#1:324,9\n170#1:308\n183#1:334,2\n*E\n"
)
public final class SubTrackerActionsKt {
   public static final void restockFrom(@NotNull MutableSubTracker var0, @NotNull MutableSubTracker var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      List var3 = var1.getSlots();
      Iterator var2 = var0.getSlots().iterator();

      while (var2.hasNext()) {
         restockFrom((MutableItemStack)var2.next(), var3);
      }
   }

   public static final void moveAllTo(@NotNull MutableSubTracker var0, @NotNull MutableSubTracker var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      List var4 = var1.getSlots();
      Iterator var3 = var0.getSlots().iterator();

      while (var3.hasNext()) {
         moveTo((MutableItemStack)var3.next(), var4, var2);
      }
   }

   public static final void moveAllTo(@NotNull MutableSubTracker var0, @NotNull MutableSubTracker var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      List var3 = var1.getSlots();
      Iterator var2 = var0.getSlots().iterator();

      while (var2.hasNext()) {
         moveTo((MutableItemStack)var2.next(), var3);
      }
   }

   public static final void moveMatchTo(@NotNull MutableSubTracker var0, @NotNull MutableSubTracker var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      List var6 = var1.getSlots();
      if (ModSettings.INSTANCE.getIGNORE_DURABILITY().getBooleanValue()) {
         Set var7 = ItemStackListExtensionsKt.itemTypes(var6, true);

         for (MutableItemStack var8 : var0.getSlots()) {
            if (var7.contains(ItemType.copy$default(var8.getItemType(), null, null, null, null, true, false, null, 111, null))) {
               moveTo(var8, var6);
            }
         }
      } else {
         Set var2 = ItemStackListExtensionsKt.itemTypes$default(var6, false, 1, null);

         for (MutableItemStack var3 : var0.getSlots()) {
            if (var2.contains(var3.getItemType())) {
               moveTo(var3, var6);
            }
         }
      }
   }

   public static final void moveFocusMatchTo(@NotNull MutableSubTracker var0, @NotNull MutableSubTracker var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      List var5 = var1.getSlots();
      Slot var10000 = InventoryKt.vFocusedSlot();
      ItemStack var11;
      if (var10000 != null) {
         net.minecraft.world.item.ItemStack var10 = var10000.getItem();
         Intrinsics.checkNotNullExpressionValue(var10, "");
         net.minecraft.world.item.ItemStack var2 = var10;
         if (var10.isEmpty()) {
            var11 = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
         } else {
            ItemStack$Companion var12 = ItemStack.Companion;
            net.minecraft.world.item.ItemStack var3 = var2;
            Item var10003 = var3.getItem();
            Intrinsics.checkNotNullExpressionValue(var10003, "");
            DataComponentMap var10004 = var3.getComponents();
            Intrinsics.checkNotNull(var10004);
            var11 = var12.invoke(
               new ItemType(
                  var10003,
                  (PatchedDataComponentMap)var10004,
                  var3.getComponentsPatch(),
                  new VanillaAccessorsKt$(itemType)$1(var3),
                  false,
                  false,
                  null,
                  112,
                  null
               ),
               var2.getCount()
            );
         }
      } else {
         var11 = null;
      }

      ItemStack var6 = var11;
      var6 = var11 != null && !ItemTypeExtensionsKt.isEmpty(var6.getItemType()) ? var6 : InventoryKt.vCursorStack();
      ItemType var8 = ModSettings.INSTANCE.getIGNORE_DURABILITY().getBooleanValue()
         ? ItemType.copy$default(var6.getItemType(), null, null, null, null, true, false, null, 111, null)
         : var6.getItemType();

      for (MutableItemStack var9 : var0.getSlots()) {
         if (Intrinsics.areEqual(var8, var9.getItemType())) {
            moveTo(var9, var5);
         }
      }
   }

   public static final void refillStacksTo(@NotNull MutableSubTracker var0, @NotNull MutableSubTracker var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      List var10 = var1.getSlots();
      Set var2 = new LinkedHashSet();
      Iterable var3 = var10;
      var var4 = new ArrayList();
      Iterator var11 = var3.iterator();

      while (var11.hasNext()) {
         Object var5;
         MutableItemStack var6;
         boolean var10000;
         if (ItemStackExtensionsKt.isEmpty(var6 = (MutableItemStack)(var5 = var11.next())) && ItemStackExtensionsKt.isFull(var6)) {
            var10000 = false;
         } else {
            var2.add(var6.getItemType());
            var10000 = true;
         }

         if (var10000) {
            var4.add(var5);
         }
      }

      List var12 = var4;

      for (MutableItemStack var9 : var0.getSlots()) {
         if (var2.contains(var9.getItemType())) {
            ArrayList var16 = var12;
            MutableItemStack var18 = var9;
            Iterable var14 = var16;
            var16 = new ArrayList();
            Iterator var15 = var14.iterator();

            while (var15.hasNext()) {
               Object var7;
               MutableItemStack var8;
               if (Intrinsics.areEqual((var8 = (MutableItemStack)(var7 = var15.next())).getItemType(), var9.getItemType())
                  && !ItemStackExtensionsKt.isEmpty(var8)
                  && !ItemStackExtensionsKt.isFull(var8)) {
                  var16.add(var7);
               }
            }

            refillIfNeeded(var18, var16);
         }
      }
   }

   public static final void moveSingle(@NotNull MutableSubTracker var0, @NotNull MutableSubTracker var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      if (!var0.getSlots().isEmpty()) {
         MutableItemStack var2;
         if (!ItemTypeExtensionsKt.isEmpty((var2 = (MutableItemStack)var0.getSlots().get(0)).getItemType())) {
            ItemType var6 = ((MutableItemStack)var0.getSlots().get(0)).getItemType();
            List var8;
            Iterator var3 = (var8 = var1.getSlots()).iterator();

            Object var10000;
            while (true) {
               if (!var3.hasNext()) {
                  var10000 = null;
                  break;
               }

               Object var4;
               MutableItemStack var5;
               if (Intrinsics.areEqual((var5 = (MutableItemStack)(var4 = var3.next())).getItemType(), var6)
                  && !ItemStackExtensionsKt.isEmpty(var5)
                  && !ItemStackExtensionsKt.isFull(var5)) {
                  var10000 = (MutableItemStack)var4;
                  break;
               }
            }

            var10000 = var10000;
            if (var10000 == null) {
               var3 = var8.iterator();

               while (true) {
                  if (!var3.hasNext()) {
                     var10000 = null;
                     break;
                  }

                  Object var10;
                  if (ItemStackExtensionsKt.isEmpty((MutableItemStack)(var10 = var3.next()))) {
                     var10000 = (MutableItemStack)var10;
                     break;
                  }
               }

               var10000 = var10000;
            }

            MutableItemStack var7 = var10000;
            if (var10000 != null) {
               ItemStackExtensionsKt.transferOneTo(var2, var7);
            }
         }
      }
   }

   private static final void refillIfNeeded(MutableItemStack var0, List var1) {
      if (!var1.isEmpty()) {
         Iterator var4 = var1.iterator();

         while (var4.hasNext()) {
            MutableItemStack var2;
            ItemType var3 = (var2 = (MutableItemStack)var4.next()).getItemType();
            net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
            ItemLike var10002 = (ItemLike)var3.getItem();
            PatchedDataComponentMap var10004 = var3.getTag();
            if (var10004 == null) {
               DataComponentMap var6 = DataComponentMap.EMPTY;
               Intrinsics.checkNotNull(DataComponentMap.EMPTY);
               var10004 = (PatchedDataComponentMap)var6;
            }

            var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
            int var5 = var10000.getMaxStackSize() - var2.getCount();
            ItemStackExtensionsKt.transferNTo(var0, var2, var5);
            var0.getCount();
         }
      }
   }

   public static final void moveMatchCraftingTo(@NotNull MutableSubTracker var0, @NotNull MutableSubTracker var1) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      MutableItemTracker var2 = var1.getMainTracker();
      Iterable var3 = var1.getIndexedSlots();
      var var6 = new ArrayList();
      Iterator var11 = var3.iterator();

      while (var11.hasNext()) {
         Object var4;
         if (!ItemStackExtensionsKt.isEmpty((ItemStack)((IndexedValue)(var4 = var11.next())).getValue())) {
            var6.add(var4);
         }
      }

      Iterable var7;
      var3 = var7 = var6;
      var var8 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var7, 10));

      for (Object var14 : var3) {
         IndexedValue var5 = (IndexedValue)var14;
         ArrayList var15 = var8;
         var15.add(var5.getIndex());
      }

      List var9 = var8;
      var1 = var2.subTracker(var9);
      moveMatchTo(var0, var1);
      writeTo(MiscKt.distributeMonotonic(var1.getSlots()), var1.getSlots());
   }

   public static final void sort(@NotNull MutableSubTracker var0, @NotNull Rule var1, @NotNull PostAction var2, boolean var3, int var4, int var5) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      List var6;
      writeTo(postAction(sortItems(var6 = var0.getSlots(), var1), var2, var3, var4, var5), var6);
   }

   private static final List sortItems(List var0, Rule var1) {
      Map var2 = new LinkedHashMap();
      List var4 = var0;
      var var3 = new ArrayList();
      int var5 = 0;

      for (Object var7 : var4) {
         int var17;
         if ((var17 = var5++) < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         ItemStack var18 = (ItemStack)var7;
         int var8 = var17;
         ItemStack var9 = var18;
         int var30 = var18.getCount();
         ItemType var27 = var9.getItemType();
         net.minecraft.world.item.ItemStack var10001 = new net.minecraft.world.item.ItemStack;
         ItemLike var10003 = (ItemLike)var27.getItem();
         PatchedDataComponentMap var10005 = var27.getTag();
         if (var10005 == null) {
            DataComponentMap var32 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10005 = (PatchedDataComponentMap)var32;
         }

         var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
         boolean var28;
         if (var28 = var30 > var10001.getMaxStackSize()) {
            var2.put(var8, var18);
         }

         if (var28) {
            var3.add(var7);
         }
      }

      List var15 = var3;
      ItemBucket var16;
      Iterable var11 = var4 = CollectionsKt.sortedWith(
         CollectionsKt.toList((var16 = ItemStackListExtensionsKt.collect(CollectionsKt.minus(var0, var15))).getElementSet()), var1
      );
      var4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var4, 10));

      for (Object var22 : var11) {
         ItemType var25 = (ItemType)var22;
         ArrayList var23 = var4;
         int var31 = var16.count(var25);
         ItemType var12 = var25;
         net.minecraft.world.item.ItemStack var10002 = new net.minecraft.world.item.ItemStack;
         ItemLike var10004 = (ItemLike)var12.getItem();
         PatchedDataComponentMap var10006 = var12.getTag();
         if (var10006 == null) {
            DataComponentMap var33 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10006 = (PatchedDataComponentMap)var33;
         }

         var10002./* $VF: Unable to resugar constructor */<init>(var10004, 1, var10006);
         var23.add(TuplesKt.to(var25, MiscKt.pack(var31, var10002.getMaxStackSize())));
      }

      var0 = CollectionsKt.toMutableList(MiscKt.flatten(var4, var0.size()));
      Iterator var13 = var2.entrySet().iterator();

      while (var13.hasNext()) {
         Entry var14;
         int var24 = ((Number)(var14 = (Entry)var13.next()).getKey()).intValue();
         ItemStack var26 = (ItemStack)var14.getValue();
         var0.add(var24, var26);
      }

      return var0;
   }

   private static final int findEmptySlot(List var0, int var1) {
      int var2 = var0.size();

      while (var1 < var2) {
         if (ItemStackExtensionsKt.isEmpty((ItemStack)var0.get(var1))) {
            return var1;
         }

         var1++;
      }

      return -1;
   }

   private static final List postAction(List var0, PostAction var1, boolean var2, int var3, int var4) {
      switch (var1) {
         case NONE:
            return var0;
         case GROUP_IN_ROWS:
            if (var2) {
               return PostActions.INSTANCE.groupInRows(var0, var3, var4);
            }

            return var0;
         case GROUP_IN_COLUMNS:
            if (var2) {
               return PostActions.INSTANCE.groupInColumns(var0, var3, var4);
            }

            return var0;
         case DISTRIBUTE_EVENLY:
            return MiscKt.spreadSlot$default(var0, 0, 1, null);
         case SHUFFLE:
            return CollectionsKt.shuffled(MiscKt.spreadItemCount(MiscKt.spreadSlot$default(var0, 0, 1, null)));
         case FILL_ONE:
            return MiscKt.fillOne(MiscKt.spreadSlot$default(var0, 0, 1, null));
         case REVERSE:
            return CollectionsKt.reversed(var0);
         default:
            throw new NoWhenBranchMatchedException();
      }
   }

   private static final void writeTo(List var0, List var1) {
      Iterable var6 = var1;
      int var2 = 0;

      for (MutableItemStack var3 : var6) {
         int var4;
         if ((var4 = var2++) < 0) {
            CollectionsKt.throwIndexOverflow();
         }

         var3 = var3;
         var4 = var4;
         List var5 = var0;
         ItemStack var10;
         ItemType var12 = (var10 = (ItemStack)((var4 >= 0 ? var4 < var5.size() : false) ? var5.get(var4) : ItemStackExtensionsKt.getEMPTY(ItemStack.Companion)))
            .component1();
         var4 = var10.component2();
         var3.setItemType(var12);
         var3.setCount(var4);
      }
   }

   private static final void restockFrom(MutableItemStack var0, List var1) {
      if (!ItemStackExtensionsKt.isEmpty(var0)) {
         if (!ItemStackExtensionsKt.isFull(var0)) {
            Iterator var2 = var1.iterator();

            while (var2.hasNext()) {
               ItemStackExtensionsKt.transferTo((MutableItemStack)var2.next(), var0);
               if (ItemStackExtensionsKt.isFull(var0)) {
                  return;
               }
            }
         }
      }
   }

   private static final void moveTo(MutableItemStack var0, List var1, boolean var2) {
      if (!ItemStackExtensionsKt.isEmpty(var0)) {
         for (MutableItemStack var3 : var1) {
            if (!var2 || !ItemStackExtensionsKt.isEmpty(var3)) {
               ItemStackExtensionsKt.transferTo(var0, var3);
               if (ItemStackExtensionsKt.isEmpty(var0)) {
                  return;
               }
            }
         }
      }
   }

   private static final void moveTo(MutableItemStack var0, List var1) {
      moveTo(var0, var1, true);
      moveTo(var0, var1, false);
   }
}
