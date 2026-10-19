package org.anti_ad.mc.ipnext.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.event.autorefill.AutoRefillHandler;
import org.anti_ad.mc.ipnext.ingame.InventoryKt;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt$(itemType)$1;
import org.anti_ad.mc.ipnext.inventory.ContainerClicker;
import org.anti_ad.mc.ipnext.inventory.GeneralInventoryActions;
import org.anti_ad.mc.ipnext.item.ItemStack$Companion;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.rule.file.RuleFileRegister;
import org.anti_ad.mc.ipnext.profiles.config.ProfileItemData;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nProfileSwitchHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ProfileSwitchHandler.kt\norg/anti_ad/mc/ipnext/event/ProfileSwitchHandler$ProfileMonitor\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 3 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,414:1\n78#2,11:415\n71#2:426\n66#2,7:427\n78#2,11:438\n71#2:449\n66#2,7:450\n78#2,11:459\n71#2:470\n66#2,7:471\n78#2,11:481\n71#2:492\n66#2,7:493\n78#2,11:508\n71#2:519\n66#2,7:520\n78#2,11:527\n71#2:538\n66#2,7:539\n131#3:434\n112#3:435\n125#3:500\n123#3:501\n106#3:502\n127#3:503\n106#3:504\n131#3:505\n112#3:506\n774#4:436\n865#4:437\n866#4:457\n1869#4:458\n1870#4:478\n774#4:479\n865#4:480\n866#4:507\n*S KotlinDebug\n*F\n+ 1 ProfileSwitchHandler.kt\norg/anti_ad/mc/ipnext/event/ProfileSwitchHandler$ProfileMonitor\n*L\n251#1:415,11\n251#1:426\n251#1:427,7\n274#1:438,11\n274#1:449\n274#1:450,7\n286#1:459,11\n286#1:470\n286#1:471,7\n314#1:481,11\n314#1:492\n314#1:493,7\n327#1:508,11\n327#1:519\n327#1:520,7\n328#1:527,11\n328#1:538\n328#1:539,7\n252#1:434\n252#1:435\n319#1:500\n319#1:501\n319#1:502\n319#1:503\n319#1:504\n323#1:505\n323#1:506\n273#1:436\n273#1:437\n273#1:457\n285#1:458\n285#1:478\n313#1:479\n313#1:480\n313#1:507\n*E\n"
)
public final class ProfileSwitchHandler$ProfileMonitor {
   private final int slot;
   @NotNull
   private final List targetValues;

   public ProfileSwitchHandler$ProfileMonitor(int var1, @NotNull List var2) {
      Intrinsics.checkNotNullParameter(var2, "");
      super();
      this.slot = var1;
      this.targetValues = var2;
   }

   public final int getSlot() {
      return this.slot;
   }

   public final boolean findAndSwap(@NotNull List var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      NonNullList var10000 = ((AbstractContainerMenu)Vanilla.INSTANCE.playerContainer()).slots;
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      ItemStack var20 = ((Slot)((List)var10000).get(this.slot)).getItem();
      Intrinsics.checkNotNullExpressionValue(var20, "");
      ItemStack var2 = var20;
      org.anti_ad.mc.ipnext.item.ItemStack var21;
      if (var20.isEmpty()) {
         var21 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
      } else {
         ItemStack$Companion var22 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
         ItemStack var3 = var2;
         Item var10003 = var3.getItem();
         Intrinsics.checkNotNullExpressionValue(var10003, "");
         DataComponentMap var10004 = var3.getComponents();
         Intrinsics.checkNotNull(var10004);
         var21 = var22.invoke(
            new ItemType(
               var10003, (PatchedDataComponentMap)var10004, var3.getComponentsPatch(), new VanillaAccessorsKt$(itemType)$1(var3), false, false, null, 112, null
            ),
            var2.getCount()
         );
      }

      org.anti_ad.mc.ipnext.item.ItemStack var10 = var21;
      Log var23 = Log.INSTANCE;
      ItemType var11 = var10.getItemType();
      ResourceLocation var10001 = BuiltInRegistries.ITEM.getKey(var11.getItem());
      Intrinsics.checkNotNullExpressionValue(var10001, "");
      String var33 = var10001.toString();
      Intrinsics.checkNotNullExpressionValue(var33, "");
      var23.trace("found " + var33 + " in slot " + this.slot);
      if (!this.targetValues.isEmpty()) {
         Integer var12;
         if ((var12 = ProfileSwitchHandlerKt.access$findIn(this.targetValues, var1, new ProfileSwitchHandler$ProfileMonitor$findAndSwap$swapWith$1(this)))
            != null) {
            if (this.slot != var12) {
               Log.INSTANCE.trace("swapping " + var12 + " to " + this.slot);
               this.swapSlots(this.slot, var12);
            }

            return true;
         }
      } else {
         List var28;
         if (ModSettings.INSTANCE.getENABLE_LOCK_SLOTS().getBooleanValue() && LockedSlotKeeper.INSTANCE.isMultiPlayer()) {
            var28 = !LockedSlotKeeper.INSTANCE.getEmptyLockedSlots().isEmpty()
               ? CollectionsKt.toList(LockedSlotKeeper.INSTANCE.getEmptyLockedSlots())
               : CollectionsKt.toList(LockedSlotKeeper.INSTANCE.getEmptyNonLockedSlots());
         } else {
            Iterable var6 = ProfileSwitchHandler.access$getAllSlots$p();
            var var13 = new ArrayList();
            Iterator var7 = var6.iterator();

            while (var7.hasNext()) {
               Object var16;
               int var4 = ((Number)(var16 = var7.next())).intValue();
               var10000 = ((AbstractContainerMenu)Vanilla.INSTANCE.playerContainer()).slots;
               Intrinsics.checkNotNullExpressionValue(var10000, "");
               ItemStack var25 = ((Slot)((List)var10000).get(var4)).getItem();
               Intrinsics.checkNotNullExpressionValue(var25, "");
               ItemStack var18 = var25;
               org.anti_ad.mc.ipnext.item.ItemStack var26;
               if (var25.isEmpty()) {
                  var26 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
               } else {
                  ItemStack$Companion var27 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
                  ItemStack var5 = var18;
                  Item var34 = var5.getItem();
                  Intrinsics.checkNotNullExpressionValue(var34, "");
                  DataComponentMap var36 = var5.getComponents();
                  Intrinsics.checkNotNull(var36);
                  var26 = var27.invoke(
                     new ItemType(
                        var34,
                        (PatchedDataComponentMap)var36,
                        var5.getComponentsPatch(),
                        new VanillaAccessorsKt$(itemType)$1(var5),
                        false,
                        false,
                        null,
                        112,
                        null
                     ),
                     var18.getCount()
                  );
               }

               if (ItemStackExtensionsKt.isEmpty(var26)) {
                  var13.add(var16);
               }
            }

            var28 = CollectionsKt.toList(var13);
         }

         List var14 = var28;
         if (!(var1 = CollectionsKt.minus(var28, ProfileSwitchHandler.access$getHotBarSlots$p())).isEmpty()) {
            var14 = var1;
         }

         Iterator var9 = var14.iterator();

         while (var9.hasNext()) {
            int var15 = ((Number)var9.next()).intValue();
            var10000 = ((AbstractContainerMenu)Vanilla.INSTANCE.playerContainer()).slots;
            Intrinsics.checkNotNullExpressionValue(var10000, "");
            ItemStack var30 = ((Slot)((List)var10000).get(var15)).getItem();
            Intrinsics.checkNotNullExpressionValue(var30, "");
            ItemStack var17 = var30;
            org.anti_ad.mc.ipnext.item.ItemStack var31;
            if (var30.isEmpty()) {
               var31 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
            } else {
               ItemStack$Companion var32 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
               ItemStack var19 = var17;
               Item var35 = var19.getItem();
               Intrinsics.checkNotNullExpressionValue(var35, "");
               DataComponentMap var37 = var19.getComponents();
               Intrinsics.checkNotNull(var37);
               var31 = var32.invoke(
                  new ItemType(
                     var35,
                     (PatchedDataComponentMap)var37,
                     var19.getComponentsPatch(),
                     new VanillaAccessorsKt$(itemType)$1(var19),
                     false,
                     false,
                     null,
                     112,
                     null
                  ),
                  var17.getCount()
               );
            }

            if (ItemStackExtensionsKt.isEmpty(var31)) {
               this.swapSlots(this.slot, var15);
               return true;
            }
         }
      }

      return false;
   }

   private final void swapSlots(int var1, int var2) {
      AutoRefillHandler.INSTANCE.getProfilesSwappedItems().add(var1);
      GeneralInventoryActions.INSTANCE.cleanCursor();
      int var3;
      if ((var3 = var1 - 36) >= 0 ? var3 < 9 : false) {
         ContainerClicker.INSTANCE.swap(var2, var1 - 36);
      } else {
         ContainerClicker.INSTANCE.leftClick(var2);
         ContainerClicker.INSTANCE.leftClick(var1);
         if (!ItemStackExtensionsKt.isEmpty(InventoryKt.vCursorStack())) {
            ContainerClicker.INSTANCE.leftClick(var2);
         }
      }
   }

   private final Integer bestMatch(ProfileItemData var1, List var2) {
      Iterable var9 = var2;
      var var3 = new ArrayList();
      Iterator var10 = var9.iterator();

      while (var10.hasNext()) {
         Object var4;
         int var5 = ((Number)(var4 = var10.next())).intValue();
         NonNullList var10000 = ((AbstractContainerMenu)Vanilla.INSTANCE.playerContainer()).slots;
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         ItemStack var18 = ((Slot)((List)var10000).get(var5)).getItem();
         Intrinsics.checkNotNullExpressionValue(var18, "");
         ItemStack var6 = var18;
         org.anti_ad.mc.ipnext.item.ItemStack var19;
         if (var18.isEmpty()) {
            var19 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
         } else {
            ItemStack$Companion var20 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
            ItemStack var11 = var6;
            Item var10003 = var11.getItem();
            Intrinsics.checkNotNullExpressionValue(var10003, "");
            DataComponentMap var10004 = var11.getComponents();
            Intrinsics.checkNotNull(var10004);
            var19 = var20.invoke(
               new ItemType(
                  var10003,
                  (PatchedDataComponentMap)var10004,
                  var11.getComponentsPatch(),
                  new VanillaAccessorsKt$(itemType)$1(var11),
                  false,
                  false,
                  null,
                  112,
                  null
               ),
               var6.getCount()
            );
         }

         org.anti_ad.mc.ipnext.item.ItemStack var8 = var19;
         boolean var13 = false;
         if (!ItemStackExtensionsKt.isEmpty(var8)) {
            boolean var12 = ProfileSwitchHandlerKt.access$match(var1, var8);
            boolean var24;
            if (!StringsKt.isBlank(var1.getCustomName())) {
               ItemType var7;
               ItemType var14 = var7 = var8.getItemType();
               ItemStack var21 = new ItemStack;
               ItemLike var10002 = (ItemLike)var14.getItem();
               PatchedDataComponentMap var28 = var14.getTag();
               if (var28 == null) {
                  DataComponentMap var29 = DataComponentMap.EMPTY;
                  Intrinsics.checkNotNull(DataComponentMap.EMPTY);
                  var28 = (PatchedDataComponentMap)var29;
               }

               var21./* $VF: Unable to resugar constructor */<init>(var10002, 1, var28);
               String var23;
               if (var21.getComponents().get(DataComponents.CUSTOM_NAME) != null) {
                  ItemType var15 = var7;
                  ItemStack var22 = new ItemStack;
                  var10002 = (ItemLike)var15.getItem();
                  var28 = var15.getTag();
                  if (var28 == null) {
                     DataComponentMap var31 = DataComponentMap.EMPTY;
                     Intrinsics.checkNotNull(DataComponentMap.EMPTY);
                     var28 = (PatchedDataComponentMap)var31;
                  }

                  var22./* $VF: Unable to resugar constructor */<init>(var10002, 1, var28);
                  var23 = var22.getDisplayName().getString();
                  Intrinsics.checkNotNullExpressionValue(var23, "");
               } else {
                  var23 = "";
               }

               var24 = Intrinsics.areEqual(var23, var1.getCustomName());
            } else {
               var24 = true;
            }

            boolean var16 = var24;
            ItemType var17 = var8.getItemType();
            ResourceLocation var25 = BuiltInRegistries.ITEM.getKey(var17.getItem());
            Intrinsics.checkNotNullExpressionValue(var25, "");
            String var26 = var25.toString();
            Intrinsics.checkNotNullExpressionValue(var26, "");
            var13 = Intrinsics.areEqual(var26, var1.getItemId()) && var16 && var12;
         }

         if (var13) {
            var3.add(var4);
         }
      }

      return (Integer)CollectionsKt.lastOrNull(CollectionsKt.sortedWith(var3, ProfileSwitchHandler$ProfileMonitor::bestMatch$lambda$4));
   }

   private static final int bestMatch$lambda$3(Integer var0, Integer var1) {
      NonNullList var10000 = ((AbstractContainerMenu)Vanilla.INSTANCE.playerContainer()).slots;
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      List var8 = (List)var10000;
      Intrinsics.checkNotNull(var1);
      ItemStack var9 = ((Slot)var8.get(var1)).getItem();
      Intrinsics.checkNotNullExpressionValue(var9, "");
      ItemStack var4 = var9;
      org.anti_ad.mc.ipnext.item.ItemStack var10;
      if (var9.isEmpty()) {
         var10 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
      } else {
         ItemStack$Companion var11 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
         ItemStack var2 = var4;
         Item var10003 = var2.getItem();
         Intrinsics.checkNotNullExpressionValue(var10003, "");
         DataComponentMap var10004 = var2.getComponents();
         Intrinsics.checkNotNull(var10004);
         var10 = var11.invoke(
            new ItemType(
               var10003, (PatchedDataComponentMap)var10004, var2.getComponentsPatch(), new VanillaAccessorsKt$(itemType)$1(var2), false, false, null, 112, null
            ),
            var4.getCount()
         );
      }

      org.anti_ad.mc.ipnext.item.ItemStack var5 = var10;
      var10000 = ((AbstractContainerMenu)Vanilla.INSTANCE.playerContainer()).slots;
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      List var13 = (List)var10000;
      Intrinsics.checkNotNull(var0);
      ItemStack var14 = ((Slot)var13.get(var0)).getItem();
      Intrinsics.checkNotNullExpressionValue(var14, "");
      ItemStack var3 = var14;
      org.anti_ad.mc.ipnext.item.ItemStack var15;
      if (var14.isEmpty()) {
         var15 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
      } else {
         ItemStack$Companion var16 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
         ItemStack var6 = var3;
         Item var17 = var6.getItem();
         Intrinsics.checkNotNullExpressionValue(var17, "");
         DataComponentMap var18 = var6.getComponents();
         Intrinsics.checkNotNull(var18);
         var15 = var16.invoke(
            new ItemType(
               var17, (PatchedDataComponentMap)var18, var6.getComponentsPatch(), new VanillaAccessorsKt$(itemType)$1(var6), false, false, null, 112, null
            ),
            var3.getCount()
         );
      }

      org.anti_ad.mc.ipnext.item.ItemStack var7 = var15;
      return RuleFileRegister.INSTANCE.getCustomRuleOrEmpty("auto_refill_best").compare(var5.getItemType(), var7.getItemType());
   }

   private static final int bestMatch$lambda$4(Function2 var0, Object var1, Object var2) {
      return ((Number)var0.invoke(var1, var2)).intValue();
   }
}
