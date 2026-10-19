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
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
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
import org.anti_ad.mc.ipnext.inventory.ContainerClicker;
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
   "SMAP\nContinuousCraftingHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ContinuousCraftingHandler.kt\norg/anti_ad/mc/ipnext/event/ContinuousCraftingHandler$Monitor\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 5 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n+ 6 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,367:1\n78#2:368\n97#2:371\n84#2:380\n88#2:381\n71#2:382\n66#2,7:383\n88#2:390\n71#2:391\n66#2,7:392\n84#2:406\n88#2:407\n71#2:408\n66#2,7:409\n774#3:369\n865#3:370\n866#3:372\n1563#3:373\n1634#3,3:374\n1869#3,2:416\n1252#3,2:420\n1563#3:431\n1634#3,3:432\n1255#3:435\n1869#3,2:436\n1#4:377\n131#5:378\n112#5:379\n98#5,9:422\n384#6,7:399\n465#6:418\n415#6:419\n*S KotlinDebug\n*F\n+ 1 ContinuousCraftingHandler.kt\norg/anti_ad/mc/ipnext/event/ContinuousCraftingHandler$Monitor\n*L\n189#1:368\n190#1:371\n206#1:380\n217#1:381\n217#1:382\n217#1:383,7\n219#1:390\n219#1:391\n219#1:392,7\n223#1:406\n261#1:407\n261#1:408\n261#1:409,7\n190#1:369\n190#1:370\n190#1:372\n193#1:373\n193#1:374,3\n272#1:416,2\n240#1:420,2\n242#1:431\n242#1:432,3\n240#1:435\n246#1:436,2\n205#1:378\n205#1:379\n242#1:422,9\n221#1:399,7\n240#1:418\n240#1:419\n*E\n"
)
final class ContinuousCraftingHandler$Monitor implements ContinuousCraftingHandler$IMonitor {
   @NotNull
   private final List containerSlots;
   @NotNull
   private final List ingredientSlots;
   @NotNull
   private final List slotMonitors;
   @NotNull
   private final List playerSlotIndices;

   public ContinuousCraftingHandler$Monitor(@NotNull AbstractContainerMenu var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super();
      NonNullList var10001 = var1.slots;
      Intrinsics.checkNotNullExpressionValue(var1.slots, "");
      this.containerSlots = (List)var10001;
      ArrayList var2 = this.getContainerSlots();
      ContinuousCraftingHandler$Monitor var6 = this;
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
         var15.add(new ContinuousCraftingHandler$ItemSlotMonitor(var5));
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
      ItemType var4 = var2.getItemType();
      ResourceLocation var10000 = BuiltInRegistries.ITEM.getKey(var4.getItem());
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      String var6 = var10000.toString();
      Intrinsics.checkNotNullExpressionValue(var6, "");
      if (Intrinsics.areEqual(var6, "minecraft:glass_bottle")) {
         var4 = var1.getItemType();
         var10000 = BuiltInRegistries.ITEM.getKey(var4.getItem());
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         String var8 = var10000.toString();
         Intrinsics.checkNotNullExpressionValue(var8, "");
         if (Intrinsics.areEqual(var8, "minecraft:honey_bottle")) {
            ContainerClicker.INSTANCE.shiftClick(var3.index);
         }
      }

      return ItemStackExtensionsKt.isEmpty(var2);
   }

   @Override
   public final boolean autoRefill() {
      boolean var1 = false;
      Map var2 = new LinkedHashMap();
      Iterator var3 = this.getSlotMonitors().iterator();

      while (var3.hasNext()) {
         ContinuousCraftingHandler$ItemSlotMonitor var4;
         net.minecraft.world.item.ItemStack var10000 = (var4 = (ContinuousCraftingHandler$ItemSlotMonitor)var3.next()).getSlot().getItem();
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         net.minecraft.world.item.ItemStack var5 = var10000;
         ItemStack var18;
         if (var10000.isEmpty()) {
            var18 = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
         } else {
            ItemStack$Companion var19 = ItemStack.Companion;
            net.minecraft.world.item.ItemStack var6 = var5;
            Item var10003 = var6.getItem();
            Intrinsics.checkNotNullExpressionValue(var10003, "");
            DataComponentMap var10004 = var6.getComponents();
            Intrinsics.checkNotNull(var10004);
            var18 = var19.invoke(
               new ItemType(
                  var10003,
                  (PatchedDataComponentMap)var10004,
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

         var18.getItemType().setIgnoreDurability(true);
         ItemStack var10001 = var4.getStoredItem();
         net.minecraft.world.item.ItemStack var10002 = var4.getSlot().getItem();
         Intrinsics.checkNotNullExpressionValue(var10002, "");
         var5 = var10002;
         ItemStack var23;
         if (var10002.isEmpty()) {
            var23 = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
         } else {
            ItemStack$Companion var24 = ItemStack.Companion;
            net.minecraft.world.item.ItemStack var15 = var5;
            Item var10005 = var15.getItem();
            Intrinsics.checkNotNullExpressionValue(var10005, "");
            DataComponentMap var10006 = var15.getComponents();
            Intrinsics.checkNotNull(var10006);
            var23 = var24.invoke(
               new ItemType(
                  var10005,
                  (PatchedDataComponentMap)var10006,
                  var15.getComponentsPatch(),
                  new VanillaAccessorsKt$(itemType)$1(var15),
                  false,
                  false,
                  null,
                  112,
                  null
               ),
               var5.getCount()
            );
         }

         if (this.shouldHandle(var10001, var23, var4.getSlot())) {
            Map var12 = var2;
            ItemType var7 = ItemType.copy$default(var4.getStoredItem().getItemType(), null, null, null, null, true, false, null, 111, null);
            Object var16;
            if ((var16 = var12.get(var7)) == null) {
               var16 = new ArrayList();
               var12.put(var7, var16);
            }

            ((List)var16).add(var4.getSlot().index);
            var1 = true;
         }
      }

      if (var2.isEmpty()) {
         return var1;
      }

      var1 = true;
      AdvancedContainer$Companion.tracker$default(AdvancedContainer.Companion, true, false, ContinuousCraftingHandler$Monitor::autoRefill$lambda$11, 2, null);
      var3 = this.getSlotMonitors().iterator();

      while (var3.hasNext()) {
         ContinuousCraftingHandler$ItemSlotMonitor var10;
         net.minecraft.world.item.ItemStack var20 = (var10 = (ContinuousCraftingHandler$ItemSlotMonitor)var3.next()).getSlot().getItem();
         Intrinsics.checkNotNullExpressionValue(var20, "");
         net.minecraft.world.item.ItemStack var13 = var20;
         ItemStack var21;
         if (var20.isEmpty()) {
            var21 = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
         } else {
            ItemStack$Companion var22 = ItemStack.Companion;
            net.minecraft.world.item.ItemStack var17 = var13;
            Item var25 = var17.getItem();
            Intrinsics.checkNotNullExpressionValue(var25, "");
            DataComponentMap var26 = var17.getComponents();
            Intrinsics.checkNotNull(var26);
            var21 = var22.invoke(
               new ItemType(
                  var25, (PatchedDataComponentMap)var26, var17.getComponentsPatch(), new VanillaAccessorsKt$(itemType)$1(var17), false, false, null, 112, null
               ),
               var13.getCount()
            );
         }

         ItemStack var14 = var21;
         if (!ItemStackExtensionsKt.isEmpty(var10.getStoredItem())) {
            var1 = !ItemStackExtensionsKt.isEmpty(var14);
         }

         if (!var1) {
            break;
         }
      }

      return var1;
   }

   @Override
   public final void save() {
      Iterator var1 = this.getSlotMonitors().iterator();

      while (var1.hasNext()) {
         ((ContinuousCraftingHandler$ItemSlotMonitor)var1.next()).save();
      }
   }

   private static final ItemStack autoRefill$lambda$11$lambda$6(ItemStack var0) {
      Intrinsics.checkNotNullParameter(var0, "");
      MutableItemStack var1;
      (var1 = var0.copyAsMutable()).getItemType().setIgnoreDurability(true);
      return var1;
   }

   private static final Unit autoRefill$lambda$11(ContinuousCraftingHandler$Monitor var0, Map var1, AdvancedContainer$TrackerDsl var2) {
      Intrinsics.checkNotNullParameter(var2, "");
      MutableSubTracker var12;
      ItemBucket var3 = ItemStackListExtensionsKt.processAndCollect(
         (var12 = var2.getTracker().subTracker(var0.getPlayerSlotIndices())).getSlots(), ContinuousCraftingHandler$Monitor::autoRefill$lambda$11$lambda$6
      );
      Map var4 = var1;
      Map var5 = var1;
      var4 = new LinkedHashMap(MapsKt.mapCapacity(var4.size()));

      for (Object var14 : var5.entrySet()) {
         Entry var7 = (Entry)var14;
         Map var8 = var4;
         net.minecraft.world.item.ItemStack var10001 = (net.minecraft.world.item.ItemStack)var7.getKey();
         Entry var33 = (Entry)var14;
         Object var11 = var10001;
         Map var10 = var8;
         ItemType var15 = (ItemType)var33.getKey();
         List var24 = (List)var33.getValue();
         ItemType var28 = ItemType.copy$default(var15, null, null, null, null, true, false, null, 111, null);
         int var37 = var3.count(var28) / var24.size();
         ItemType var29 = var15;
         var10001 = new net.minecraft.world.item.ItemStack;
         ItemLike var10003 = (ItemLike)var29.getItem();
         PatchedDataComponentMap var10005 = var29.getTag();
         if (var10005 == null) {
            DataComponentMap var41 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10005 = (PatchedDataComponentMap)var41;
         }

         var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
         Integer var38 = RangesKt.coerceAtMost(var37, var10001.getMaxStackSize());
         Iterable var16 = var24;
         Integer var25 = var38;
         Iterable var30 = var16;
         var var17 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var16, 10));

         for (Object var34 : var30) {
            int var35 = ((Number)var34).intValue();
            ArrayList var9 = var17;
            var9.add((MutableItemStack)var2.getTracker().getSlots().get(var35));
         }

         Pair var18 = TuplesKt.to(var25, var17);
         var10.put(var11, var18);
      }

      var1 = var4;
      Iterator var26 = var12.getSlots().iterator();

      while (var26.hasNext()) {
         MutableItemStack var13;
         ItemType var20 = ItemType.copy$default((var13 = (MutableItemStack)var26.next()).getItemType(), null, null, null, null, true, false, null, 111, null);
         Pair var39 = (Pair)var1.get(var20);
         if (var39 != null) {
            Pair var27 = var39;
            int var32 = ((Number)var39.component1()).intValue();
            Iterator var21 = ((List)var27.component2()).iterator();

            while (var21.hasNext()) {
               MutableItemStack var36;
               if ((var36 = (MutableItemStack)var21.next()).getCount() < var32) {
                  if (ItemStackExtensionsKt.isEmpty(var13)) {
                     break;
                  }

                  int var22 = var32 - var36.getCount();
                  ItemStackExtensionsKt.transferNTo(var13, var36, var22);
               }
            }
         }
      }

      return Unit.INSTANCE;
   }
}
