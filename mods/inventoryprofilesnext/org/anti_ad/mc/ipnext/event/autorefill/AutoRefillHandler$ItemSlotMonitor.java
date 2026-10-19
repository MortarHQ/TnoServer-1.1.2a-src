package org.anti_ad.mc.ipnext.event.autorefill;

import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.internal.ProgressionUtilKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.item.SolidBucketItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import org.anti_ad.mc.alias.text.TextExKt;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.common.vanilla.VanillaUtil;
import org.anti_ad.mc.common.vanilla.VanillaUtilKt;
import org.anti_ad.mc.common.vanilla.alias.glue.I18n;
import org.anti_ad.mc.ipnext.config.AutoRefillSettings;
import org.anti_ad.mc.ipnext.config.ToolReplaceVisualNotification;
import org.anti_ad.mc.ipnext.event.LockedSlotKeeper;
import org.anti_ad.mc.ipnext.event.Sounds;
import org.anti_ad.mc.ipnext.ingame.InventoryKt;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt$(itemType)$1;
import org.anti_ad.mc.ipnext.inventory.ContainerClicker;
import org.anti_ad.mc.ipnext.inventory.GeneralInventoryActions;
import org.anti_ad.mc.ipnext.item.ItemStack;
import org.anti_ad.mc.ipnext.item.ItemStack$Companion;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.ItemTypeExtensionsKt;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nAutoRefillHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AutoRefillHandler.kt\norg/anti_ad/mc/ipnext/event/autorefill/AutoRefillHandler$ItemSlotMonitor\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n+ 5 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,928:1\n78#2,11:929\n71#2:940\n66#2,7:941\n78#2,11:1019\n71#2:1030\n66#2,7:1031\n1761#3,3:948\n1267#3,2:969\n1270#3:972\n1267#3,2:978\n1270#3:981\n358#4:951\n356#4:952\n106#4:953\n341#4:954\n106#4:955\n358#4:956\n356#4:957\n106#4:958\n341#4:959\n106#4:960\n354#4:961\n106#4:962\n135#4:963\n106#4:964\n354#4:965\n106#4:966\n349#4:967\n106#4:968\n350#4:971\n351#4:973\n354#4:974\n106#4:975\n349#4:976\n106#4:977\n350#4:980\n351#4:982\n356#4:983\n106#4:984\n358#4:985\n356#4:986\n106#4:987\n341#4:988\n106#4:989\n358#4:990\n356#4:991\n106#4:992\n341#4:993\n106#4:994\n358#4:996\n356#4:997\n106#4:998\n341#4:999\n106#4:1000\n364#4:1001\n361#4,8:1002\n361#4:1010\n361#4:1011\n394#4:1012\n397#4:1013\n276#4:1014\n106#4:1015\n306#4:1016\n135#4:1017\n106#4:1018\n358#4:1038\n356#4:1039\n106#4:1040\n341#4:1041\n106#4:1042\n138#4:1043\n123#4:1044\n106#4:1045\n127#4:1046\n106#4:1047\n133#4:1048\n106#4:1049\n138#4:1050\n123#4:1051\n106#4:1052\n127#4:1053\n106#4:1054\n133#4:1055\n106#4:1056\n138#4:1057\n123#4:1058\n106#4:1059\n127#4:1060\n106#4:1061\n133#4:1062\n106#4:1063\n138#4:1064\n123#4:1065\n106#4:1066\n127#4:1067\n106#4:1068\n133#4:1069\n106#4:1070\n138#4:1071\n123#4:1072\n106#4:1073\n127#4:1074\n106#4:1075\n133#4:1076\n106#4:1077\n1#5:995\n*S KotlinDebug\n*F\n+ 1 AutoRefillHandler.kt\norg/anti_ad/mc/ipnext/event/autorefill/AutoRefillHandler$ItemSlotMonitor\n*L\n267#1:929,11\n267#1:940\n267#1:941,7\n394#1:1019,11\n394#1:1030\n394#1:1031,7\n297#1:948,3\n354#1:969,2\n354#1:972\n360#1:978,2\n360#1:981\n306#1:951\n306#1:952\n306#1:953\n306#1:954\n306#1:955\n307#1:956\n307#1:957\n307#1:958\n307#1:959\n307#1:960\n316#1:961\n316#1:962\n318#1:963\n318#1:964\n333#1:965\n333#1:966\n354#1:967\n354#1:968\n354#1:971\n354#1:973\n357#1:974\n357#1:975\n360#1:976\n360#1:977\n360#1:980\n360#1:982\n361#1:983\n361#1:984\n364#1:985\n364#1:986\n364#1:987\n364#1:988\n364#1:989\n365#1:990\n365#1:991\n365#1:992\n365#1:993\n365#1:994\n368#1:996\n368#1:997\n368#1:998\n368#1:999\n368#1:1000\n372#1:1001\n374#1:1002,8\n376#1:1010\n378#1:1011\n382#1:1012\n384#1:1013\n384#1:1014\n384#1:1015\n384#1:1016\n386#1:1017\n386#1:1018\n432#1:1038\n432#1:1039\n432#1:1040\n432#1:1041\n432#1:1042\n405#1:1043\n405#1:1044\n405#1:1045\n405#1:1046\n405#1:1047\n405#1:1048\n405#1:1049\n407#1:1050\n407#1:1051\n407#1:1052\n407#1:1053\n407#1:1054\n407#1:1055\n407#1:1056\n446#1:1057\n446#1:1058\n446#1:1059\n446#1:1060\n446#1:1061\n446#1:1062\n446#1:1063\n488#1:1064\n488#1:1065\n488#1:1066\n488#1:1067\n488#1:1068\n488#1:1069\n488#1:1070\n524#1:1071\n524#1:1072\n524#1:1073\n524#1:1074\n524#1:1075\n524#1:1076\n524#1:1077\n*E\n"
)
public final class AutoRefillHandler$ItemSlotMonitor {
   @NotNull
   public static final AutoRefillHandler$ItemSlotMonitor$Companion Companion = new AutoRefillHandler$ItemSlotMonitor$Companion(null);
   @NotNull
   private final AutoRefillHandler$IdAndIndex slotId;
   @NotNull
   private final List anothers;
   @NotNull
   private ItemStack storedItem;
   private int storedSlotId;
   private int tickCount;
   @NotNull
   private ItemStack lastTickItem;
   @NotNull
   private ItemStack currentItem;
   private int currentSlotId;
   private int lastNotifyDurability;
   private int lastNotifyBreakDurability;
   private boolean shouldHandle;
   @NotNull
   private ItemStack checkingItem;

   public AutoRefillHandler$ItemSlotMonitor(@NotNull AutoRefillHandler$IdAndIndex var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      super();
      this.slotId = var1;
      this.anothers = new ArrayList();
      this.storedItem = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
      this.storedSlotId = -1;
      this.lastTickItem = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
      this.currentItem = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
      this.currentSlotId = -1;
      this.lastNotifyDurability = -1;
      this.lastNotifyBreakDurability = -1;
      this.checkingItem = this.storedItem;
   }

   @NotNull
   public final AutoRefillHandler$IdAndIndex getSlotId() {
      return this.slotId;
   }

   @NotNull
   public final List getAnothers() {
      return this.anothers;
   }

   @NotNull
   public final ItemStack getStoredItem() {
      return this.storedItem;
   }

   public final void setStoredItem(@NotNull ItemStack var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.storedItem = var1;
   }

   public final int getStoredSlotId() {
      return this.storedSlotId;
   }

   public final void setStoredSlotId(int var1) {
      this.storedSlotId = var1;
   }

   public final int getTickCount() {
      return this.tickCount;
   }

   public final void setTickCount(int var1) {
      this.tickCount = var1;
   }

   @NotNull
   public final ItemStack getLastTickItem() {
      return this.lastTickItem;
   }

   public final void setLastTickItem(@NotNull ItemStack var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.lastTickItem = var1;
   }

   @NotNull
   public final ItemStack getCurrentItem() {
      return this.currentItem;
   }

   public final void setCurrentItem(@NotNull ItemStack var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.currentItem = var1;
   }

   public final int getCurrentSlotId() {
      return this.currentSlotId;
   }

   public final void setCurrentSlotId(int var1) {
      this.currentSlotId = var1;
   }

   public final void updateCurrent() {
      this.lastTickItem = this.currentItem;
      this.currentSlotId = ((Number)this.slotId.getId().invoke()).intValue();
      NonNullList var10001 = ((AbstractContainerMenu)Vanilla.INSTANCE.playerContainer()).slots;
      Intrinsics.checkNotNullExpressionValue(var10001, "");
      net.minecraft.world.item.ItemStack var3 = ((Slot)((List)var10001).get(this.currentSlotId)).getItem();
      Intrinsics.checkNotNullExpressionValue(var3, "");
      net.minecraft.world.item.ItemStack var1 = var3;
      ItemStack var4;
      if (var3.isEmpty()) {
         var4 = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
      } else {
         ItemStack$Companion var5 = ItemStack.Companion;
         net.minecraft.world.item.ItemStack var2 = var1;
         Item var10004 = var2.getItem();
         Intrinsics.checkNotNullExpressionValue(var10004, "");
         DataComponentMap var10005 = var2.getComponents();
         Intrinsics.checkNotNull(var10005);
         var4 = var5.invoke(
            new ItemType(
               var10004, (PatchedDataComponentMap)var10005, var2.getComponentsPatch(), new VanillaAccessorsKt$(itemType)$1(var2), false, false, null, 112, null
            ),
            var1.getCount()
         );
      }

      this.currentItem = var4;
   }

   public final boolean getShouldHandle() {
      return this.shouldHandle;
   }

   public final void setShouldHandle(boolean var1) {
      this.shouldHandle = var1;
   }

   public final void checkShouldHandle() {
      this.shouldHandle = this.currentSlotId == this.storedSlotId && !this.isSwapped() && this.shouldHandleItem();
   }

   public final void checkHandle() {
      if (this.shouldHandle) {
         if (this.tickCount >= AutoRefillSettings.INSTANCE.getAUTO_REFILL_WAIT_TICK().getIntegerValue()) {
            this.handle();
            this.updateCurrent();
            this.unhandled();
            LockedSlotKeeper.INSTANCE.init();
         } else {
            int var1 = this.tickCount++;
         }
      } else {
         this.unhandled();
      }
   }

   private final boolean isSwapped() {
      if (Intrinsics.areEqual(this.currentItem, this.lastTickItem)) {
         return false;
      }

      Iterable var1;
      if (!((var1 = this.anothers) instanceof Collection) || !((Collection)var1).isEmpty()) {
         for (AutoRefillHandler$ItemSlotMonitor var2 : var1) {
            if (Intrinsics.areEqual(this.currentItem, var2.lastTickItem) && Intrinsics.areEqual(this.lastTickItem, var2.currentItem)) {
               return true;
            }
         }
      }

      return false;
   }

   private final void unhandled() {
      this.storedItem = this.currentItem;
      this.storedSlotId = this.currentSlotId;
      this.tickCount = 0;
      ItemType var1;
      ItemType var2 = var1 = this.storedItem.getItemType();
      net.minecraft.world.item.ItemStack var10001 = new net.minecraft.world.item.ItemStack;
      ItemLike var10003 = (ItemLike)var2.getItem();
      PatchedDataComponentMap var10005 = var2.getTag();
      if (var10005 == null) {
         DataComponentMap var13 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10005 = (PatchedDataComponentMap)var13;
      }

      var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
      int var7 = var10001.getMaxDamage();
      var2 = var1;
      net.minecraft.world.item.ItemStack var10002 = new net.minecraft.world.item.ItemStack;
      ItemLike var10004 = (ItemLike)var2.getItem();
      PatchedDataComponentMap var10006 = var2.getTag();
      if (var10006 == null) {
         DataComponentMap var16 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10006 = (PatchedDataComponentMap)var16;
      }

      var10002./* $VF: Unable to resugar constructor */<init>(var10004, 1, var10006);
      this.lastNotifyDurability = var7 - var10002.getDamageValue();
      var2 = var1 = this.storedItem.getItemType();
      var10001 = new net.minecraft.world.item.ItemStack;
      var10003 = (ItemLike)var2.getItem();
      var10005 = var2.getTag();
      if (var10005 == null) {
         DataComponentMap var15 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10005 = (PatchedDataComponentMap)var15;
      }

      var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
      int var9 = var10001.getMaxDamage();
      var2 = var1;
      var10002 = new net.minecraft.world.item.ItemStack;
      var10004 = (ItemLike)var2.getItem();
      var10006 = var2.getTag();
      if (var10006 == null) {
         DataComponentMap var18 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10006 = (PatchedDataComponentMap)var18;
      }

      var10002./* $VF: Unable to resugar constructor */<init>(var10004, 1, var10006);
      this.lastNotifyBreakDurability = var9 - var10002.getDamageValue();
   }

   private final void handle() {
      GeneralInventoryActions.INSTANCE.cleanCursor();
      ItemType var1 = this.checkingItem.getItemType();
      Integer var2;
      if ((var2 = AutoRefillHandler$ItemSlotMonitor$Companion.access$findCorrespondingSlot(Companion, this.checkingItem, this.currentItem)) != null) {
         ItemType var3 = var1;
         net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
         ItemLike var10002 = (ItemLike)var3.getItem();
         PatchedDataComponentMap var10004 = var3.getTag();
         if (var10004 == null) {
            DataComponentMap var11 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10004 = (PatchedDataComponentMap)var11;
         }

         var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
         if (var10000.isDamageableItem()) {
            this.notifySuccessfulChange(var1, var2);
         }

         var3 = this.currentItem.getItemType();
         var10000 = new net.minecraft.world.item.ItemStack;
         var10002 = (ItemLike)var3.getItem();
         var10004 = var3.getTag();
         if (var10004 == null) {
            DataComponentMap var13 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10004 = (PatchedDataComponentMap)var13;
         }

         var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
         if (var10000.isStackable()) {
            ContainerClicker.INSTANCE.shiftClick(this.storedSlotId);
         }

         int var5;
         if ((var5 = this.storedSlotId - 36) >= 0 ? var5 < 9 : false) {
            ContainerClicker.INSTANCE.swap(var2, this.storedSlotId - 36);
            return;
         }

         ContainerClicker.INSTANCE.leftClick(var2);
         ContainerClicker.INSTANCE.leftClick(this.storedSlotId);
         if (!ItemStackExtensionsKt.isEmpty(InventoryKt.vCursorStack())) {
            ContainerClicker.INSTANCE.leftClick(var2);
            return;
         }
      } else {
         ItemType var6 = var1;
         net.minecraft.world.item.ItemStack var8 = new net.minecraft.world.item.ItemStack;
         ItemLike var10 = (ItemLike)var6.getItem();
         PatchedDataComponentMap var14 = var6.getTag();
         if (var14 == null) {
            DataComponentMap var15 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var14 = (PatchedDataComponentMap)var15;
         }

         var8./* $VF: Unable to resugar constructor */<init>(var10, 1, var14);
         if (var8.isDamageableItem()) {
            this.notifyFailToChange();
         }
      }
   }

   @NotNull
   public final ItemStack getCheckingItem() {
      return this.checkingItem;
   }

   public final void setCheckingItem(@NotNull ItemStack var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.checkingItem = var1;
   }

   private final boolean shouldHandleItem() {
      if (AutoRefillSettings.INSTANCE.getAUTO_REFILL_ENABLE_PER_SLOT_CONFIG().getValue()
         && AutoRefillHandler.INSTANCE.getDisabledSlots().contains(this.slotId.getIndex().invoke())) {
         return false;
      }

      if (AutoRefillHandler.INSTANCE.getProfilesSwappedItems().contains(this.slotId.getId().invoke())) {
         AutoRefillHandler.INSTANCE.getProfilesSwappedItems().remove(this.slotId.getId().invoke());
         return false;
      }

      this.checkingItem = this.storedItem;
      if (AutoRefillHandler.access$isBlackListed(AutoRefillHandler.INSTANCE, this.storedItem)) {
         return false;
      }

      if (ItemStackExtensionsKt.isEmpty(this.storedItem)) {
         return false;
      }

      if (ItemStackExtensionsKt.isEmpty(this.currentItem)) {
         if (AutoRefillSettings.INSTANCE.getDISABLE_FOR_LOYALTY_ITEMS().getValue()) {
            ItemType var24 = this.storedItem.getItemType();
            net.minecraft.world.item.ItemStack var49 = new net.minecraft.world.item.ItemStack;
            ItemLike var60 = (ItemLike)var24.getItem();
            PatchedDataComponentMap var76 = var24.getTag();
            if (var76 == null) {
               DataComponentMap var77 = DataComponentMap.EMPTY;
               Intrinsics.checkNotNull(DataComponentMap.EMPTY);
               var76 = (PatchedDataComponentMap)var77;
            }

            var49./* $VF: Unable to resugar constructor */<init>(var60, 1, var76);
            Set var50 = EnchantmentHelper.getEnchantmentsForCrafting(var49).entrySet();
            Intrinsics.checkNotNullExpressionValue(var50, "");
            Iterable var30 = var50;
            Map var25 = new LinkedHashMap();

            for (Object var36 : var30) {
               Map var17 = var25;
               Entry var37;
               Pair var38 = TuplesKt.to(((Holder)(var37 = (Entry)var36).getKey()).unwrapKey().get(), var37.getIntValue());
               var17.put(var38.getFirst(), var38.getSecond());
            }

            if (var25.get(Enchantments.LOYALTY) != null) {
               return false;
            }
         }

         return true;
      } else {
         ItemType var1;
         ItemType var3 = var1 = this.currentItem.getItemType();
         net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
         ItemLike var10002 = (ItemLike)var3.getItem();
         PatchedDataComponentMap var10004 = var3.getTag();
         if (var10004 == null) {
            DataComponentMap var63 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10004 = (PatchedDataComponentMap)var63;
         }

         var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
         if (var10000.isDamageableItem()) {
            label249:
            if (AutoRefillSettings.INSTANCE.getREFILL_BEFORE_TOOL_BREAK().getBooleanValue()
               && !AutoRefillHandler.access$getTempDisabledForDamageable(AutoRefillHandler.INSTANCE)) {
               if (AutoRefillSettings.INSTANCE.getALLOW_BREAK_FOR_NON_ENCHANTED().getValue()) {
                  var3 = var1;
                  var10000 = new net.minecraft.world.item.ItemStack;
                  var10002 = (ItemLike)var3.getItem();
                  var10004 = var3.getTag();
                  if (var10004 == null) {
                     DataComponentMap var66 = DataComponentMap.EMPTY;
                     Intrinsics.checkNotNull(DataComponentMap.EMPTY);
                     var10004 = (PatchedDataComponentMap)var66;
                  }

                  var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
                  Set var40 = EnchantmentHelper.getEnchantmentsForCrafting(var10000).entrySet();
                  Intrinsics.checkNotNullExpressionValue(var40, "");
                  Iterable var27 = var40;
                  Map var19 = new LinkedHashMap();

                  for (Object var7 : var27) {
                     Map var6 = var19;
                     Entry var8;
                     Pair var9 = TuplesKt.to(((Holder)(var8 = (Entry)var7).getKey()).unwrapKey().get(), var8.getIntValue());
                     var6.put(var9.getFirst(), var9.getSecond());
                  }

                  if (var19.isEmpty()) {
                     var3 = var1;
                     var10000 = new net.minecraft.world.item.ItemStack;
                     var10002 = (ItemLike)var3.getItem();
                     var10004 = var3.getTag();
                     if (var10004 == null) {
                        DataComponentMap var68 = DataComponentMap.EMPTY;
                        Intrinsics.checkNotNull(DataComponentMap.EMPTY);
                        var10004 = (PatchedDataComponentMap)var68;
                     }

                     var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
                     if (var10000.getMaxDamage() < AutoRefillSettings.INSTANCE.getTOOL_MAX_DURABILITY_THRESHOLD().getValue().intValue()) {
                        break label249;
                     }
                  }
               }

               int var10 = AutoRefillHandler$ItemSlotMonitor$Companion.access$getThreshold(Companion, var1);
               var3 = var1;
               ItemType var32 = var1;
               net.minecraft.world.item.ItemStack var55 = new net.minecraft.world.item.ItemStack;
               ItemLike var69 = (ItemLike)var32.getItem();
               PatchedDataComponentMap var82 = var32.getTag();
               if (var82 == null) {
                  DataComponentMap var83 = DataComponentMap.EMPTY;
                  Intrinsics.checkNotNull(DataComponentMap.EMPTY);
                  var82 = (PatchedDataComponentMap)var83;
               }

               var55./* $VF: Unable to resugar constructor */<init>(var69, 1, var82);
               int var56 = var55.getMaxDamage();
               var32 = var3;
               net.minecraft.world.item.ItemStack var61 = new net.minecraft.world.item.ItemStack;
               ItemLike var78 = (ItemLike)var32.getItem();
               PatchedDataComponentMap var85 = var32.getTag();
               if (var85 == null) {
                  DataComponentMap var86 = DataComponentMap.EMPTY;
                  Intrinsics.checkNotNull(DataComponentMap.EMPTY);
                  var85 = (PatchedDataComponentMap)var86;
               }

               var61./* $VF: Unable to resugar constructor */<init>(var78, 1, var85);
               this.notifyDurabilityChange(var1, var56 - var61.getDamageValue(), var10);
               var3 = var1;
               var32 = var1;
               var10000 = new net.minecraft.world.item.ItemStack;
               var10002 = (ItemLike)var32.getItem();
               var10004 = var32.getTag();
               if (var10004 == null) {
                  DataComponentMap var71 = DataComponentMap.EMPTY;
                  Intrinsics.checkNotNull(DataComponentMap.EMPTY);
                  var10004 = (PatchedDataComponentMap)var71;
               }

               var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
               int var43 = var10000.getMaxDamage();
               var32 = var3;
               net.minecraft.world.item.ItemStack var10001 = new net.minecraft.world.item.ItemStack;
               ItemLike var62 = (ItemLike)var32.getItem();
               PatchedDataComponentMap var79 = var32.getTag();
               if (var79 == null) {
                  DataComponentMap var80 = DataComponentMap.EMPTY;
                  Intrinsics.checkNotNull(DataComponentMap.EMPTY);
                  var79 = (PatchedDataComponentMap)var80;
               }

               var10001./* $VF: Unable to resugar constructor */<init>(var62, 1, var79);
               if (var43 - var10001.getDamageValue() <= var10) {
                  this.checkingItem = this.currentItem;
                  return true;
               }
            } else if (AutoRefillSettings.INSTANCE.getALLOW_ALERTS_WITHOUT_TOOL_PROTECTION().getValue()) {
               ItemType var2 = var1;
               ItemType var4 = var1;
               net.minecraft.world.item.ItemStack var51 = new net.minecraft.world.item.ItemStack;
               ItemLike var64 = (ItemLike)var4.getItem();
               PatchedDataComponentMap var10006 = var4.getTag();
               if (var10006 == null) {
                  DataComponentMap var81 = DataComponentMap.EMPTY;
                  Intrinsics.checkNotNull(DataComponentMap.EMPTY);
                  var10006 = (PatchedDataComponentMap)var81;
               }

               var51./* $VF: Unable to resugar constructor */<init>(var64, 1, var10006);
               int var52 = var51.getMaxDamage();
               var4 = var2;
               net.minecraft.world.item.ItemStack var10003 = new net.minecraft.world.item.ItemStack;
               ItemLike var10005 = (ItemLike)var4.getItem();
               PatchedDataComponentMap var10007 = var4.getTag();
               if (var10007 == null) {
                  DataComponentMap var84 = DataComponentMap.EMPTY;
                  Intrinsics.checkNotNull(DataComponentMap.EMPTY);
                  var10007 = (PatchedDataComponentMap)var84;
               }

               var10003./* $VF: Unable to resugar constructor */<init>(var10005, 1, var10007);
               this.notifyDurabilityNoChange(var1, var52 - var10003.getDamageValue(), 0);
            }
         }

         ItemType var11;
         if ((
               (var11 = this.storedItem.getItemType()).getItem() instanceof BucketItem && !Intrinsics.areEqual(var11.getItem(), Items.BUCKET)
                  || var11.getItem() instanceof MilkBucketItem
                  || var11.getItem() instanceof SolidBucketItem
            )
            && Intrinsics.areEqual(this.currentItem.getItemType().getItem(), Items.BUCKET)) {
            return true;
         }

         if ((
               (var11 = this.storedItem.getItemType()).getItem() instanceof BucketItem
                  || var11.getItem() instanceof MilkBucketItem
                  || var11.getItem() instanceof SolidBucketItem
            )
            && Intrinsics.areEqual(this.storedItem.getItemType().getItem(), Items.BUCKET)
            && (
               (var11 = this.currentItem.getItemType()).getItem() instanceof BucketItem && !Intrinsics.areEqual(var11.getItem(), Items.BUCKET)
                  || var11.getItem() instanceof MilkBucketItem
                  || var11.getItem() instanceof SolidBucketItem
            )) {
            return true;
         }

         if ((
               (var11 = this.storedItem.getItemType()).getItem() instanceof BucketItem
                  || var11.getItem() instanceof MilkBucketItem
                  || var11.getItem() instanceof SolidBucketItem
            )
            && ItemTypeExtensionsKt.isEmptyComparedTo(this.storedItem.getItemType(), this.currentItem.getItemType())) {
            return true;
         }

         if ((
               (var11 = this.storedItem.getItemType()).getItem() instanceof BucketItem
                  || var11.getItem() instanceof MilkBucketItem
                  || var11.getItem() instanceof SolidBucketItem
            )
            && ItemTypeExtensionsKt.isFullComparedTo(this.storedItem.getItemType(), this.currentItem.getItemType())) {
            return true;
         }

         if (Intrinsics.areEqual(this.storedItem.getItemType().getItem(), Items.POTION)
            && Intrinsics.areEqual(this.currentItem.getItemType().getItem(), Items.GLASS_BOTTLE)) {
            return true;
         }

         if (Intrinsics.areEqual(this.storedItem.getItemType().getItem(), Items.HONEY_BOTTLE)
            && Intrinsics.areEqual(this.currentItem.getItemType().getItem(), Items.GLASS_BOTTLE)) {
            return true;
         }

         label186: {
            if ((var11 = this.storedItem.getItemType()).getItem().components().get(DataComponents.FOOD) != null) {
               ItemType var28 = var11;
               var10000 = new net.minecraft.world.item.ItemStack;
               var10002 = (ItemLike)var28.getItem();
               var10004 = var28.getTag();
               if (var10004 == null) {
                  DataComponentMap var73 = DataComponentMap.EMPTY;
                  Intrinsics.checkNotNull(DataComponentMap.EMPTY);
                  var10004 = (PatchedDataComponentMap)var73;
               }

               var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
               FoodProperties var45 = (FoodProperties)var10000.getComponents().get(DataComponents.FOOD);
               boolean var46;
               if (var45 != null) {
                  FoodProperties var29 = var45;
                  var46 = var45.usingConvertsTo().isPresent()
                     && Intrinsics.areEqual(((net.minecraft.world.item.ItemStack)var29.usingConvertsTo().get()).getItem(), Items.BOWL);
               } else {
                  var46 = false;
               }

               if (var46) {
                  var47 = true;
                  break label186;
               }
            }

            var47 = false;
         }

         if (var47 && Intrinsics.areEqual(this.currentItem.getItemType().getItem(), Items.BOWL)) {
            return true;
         }

         var3 = this.currentItem.getItemType();
         var10000 = new net.minecraft.world.item.ItemStack;
         var10002 = (ItemLike)var3.getItem();
         var10004 = var3.getTag();
         if (var10004 == null) {
            DataComponentMap var75 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10004 = (PatchedDataComponentMap)var75;
         }

         var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
         return var10000.isStackable() && this.currentItem.getCount() <= AutoRefillSettings.INSTANCE.getSTACKABLE_THRESHOLD().getIntegerValue();
      }
   }

   private final void notifySuccessfulChange(ItemType var1, int var2) {
      if (AutoRefillSettings.INSTANCE.getVISUAL_REPLACE_SUCCESS_NOTIFICATION().getValue()) {
         NonNullList var10000 = ((AbstractContainerMenu)Vanilla.INSTANCE.playerContainer()).slots;
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         net.minecraft.world.item.ItemStack var7 = ((Slot)((List)var10000).get(var2)).getItem();
         Intrinsics.checkNotNullExpressionValue(var7, "");
         net.minecraft.world.item.ItemStack var4 = var7;
         ItemStack var8;
         if (var7.isEmpty()) {
            var8 = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
         } else {
            ItemStack$Companion var9 = ItemStack.Companion;
            net.minecraft.world.item.ItemStack var3 = var4;
            Item var10003 = var3.getItem();
            Intrinsics.checkNotNullExpressionValue(var10003, "");
            DataComponentMap var10004 = var3.getComponents();
            Intrinsics.checkNotNull(var10004);
            var8 = var9.invoke(
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
               var4.getCount()
            );
         }

         ItemType var5 = var8.getItemType();
         Function1 var6 = AutoRefillHandler$ItemSlotMonitor::notifySuccessfulChange$lambda$2;
         switch ((ToolReplaceVisualNotification)AutoRefillSettings.INSTANCE.getTYPE_VISUAL_REPLACE_SUCCESS_NOTIFICATION().getValue()) {
            case SUBTITLE:
               VanillaUtilKt.showSubTitle((Component)TextExKt.fromSerializedJson((String)var6.invoke(Boolean.FALSE)));
               break;
            case HOTBAR:
               Vanilla.INSTANCE.inGameHud().setOverlayMessage((Component)TextExKt.fromSerializedJson((String)var6.invoke(Boolean.FALSE)), false);
               break;
            case CHAT:
               VanillaUtil var10 = VanillaUtil.INSTANCE;
               MutableComponent var10001 = TextExKt.fromSerializedJson((String)var6.invoke(Boolean.TRUE));
               Intrinsics.checkNotNull(var10001);
               var10.chat(var10001);
               break;
            default:
               throw new NoWhenBranchMatchedException();
         }
      }

      if (AutoRefillSettings.INSTANCE.getAUDIO_REPLACE_SUCCESS_NOTIFICATION().getValue()) {
         Sounds.REFILL_STEP_NOTIFY.play(0.2F);
         Sounds.REFILL_STEP_NOTIFY.play(1.5F, 5);
      }
   }

   private final void notifyFailToChange() {
      if (!ItemStackExtensionsKt.isEmpty(this.currentItem)
         && (
            AutoRefillSettings.INSTANCE.getVISUAL_REPLACE_FAILED_NOTIFICATION().getValue()
               || AutoRefillSettings.INSTANCE.getAUDIO_REPLACE_FAILED_NOTIFICATION().getValue()
         )) {
         ItemType var1 = this.currentItem.getItemType();
         int var2 = AutoRefillHandler$ItemSlotMonitor$Companion.access$getThreshold(Companion, var1);
         ItemType var3 = var1;
         ItemType var4 = var1;
         net.minecraft.world.item.ItemStack var10000 = new net.minecraft.world.item.ItemStack;
         ItemLike var10002 = (ItemLike)var4.getItem();
         PatchedDataComponentMap var10004 = var4.getTag();
         if (var10004 == null) {
            DataComponentMap var11 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10004 = (PatchedDataComponentMap)var11;
         }

         var10000./* $VF: Unable to resugar constructor */<init>(var10002, 1, var10004);
         int var8 = var10000.getMaxDamage();
         var4 = var3;
         net.minecraft.world.item.ItemStack var10001 = new net.minecraft.world.item.ItemStack;
         ItemLike var10003 = (ItemLike)var4.getItem();
         PatchedDataComponentMap var10005 = var4.getTag();
         if (var10005 == null) {
            DataComponentMap var12 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10005 = (PatchedDataComponentMap)var12;
         }

         var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
         int var5;
         if ((var5 = var8 - var10001.getDamageValue()) <= var2 && var5 != this.lastNotifyBreakDurability) {
            if (AutoRefillSettings.INSTANCE.getVISUAL_REPLACE_FAILED_NOTIFICATION().getValue()) {
               Function1 var6 = AutoRefillHandler$ItemSlotMonitor::notifyFailToChange$lambda$3;
               switch ((ToolReplaceVisualNotification)AutoRefillSettings.INSTANCE.getTYPE_VISUAL_REPLACE_FAILED_NOTIFICATION().getValue()) {
                  case SUBTITLE:
                     VanillaUtilKt.showSubTitle((Component)TextExKt.fromSerializedJson((String)var6.invoke(Boolean.FALSE)));
                     break;
                  case HOTBAR:
                     Vanilla.INSTANCE.inGameHud().setOverlayMessage((Component)TextExKt.fromSerializedJson((String)var6.invoke(Boolean.FALSE)), false);
                     break;
                  case CHAT:
                     VanillaUtil var9 = VanillaUtil.INSTANCE;
                     MutableComponent var10 = TextExKt.fromSerializedJson((String)var6.invoke(Boolean.TRUE));
                     Intrinsics.checkNotNull(var10);
                     var9.chat(var10);
                     break;
                  default:
                     throw new NoWhenBranchMatchedException();
               }
            }

            if (AutoRefillSettings.INSTANCE.getAUDIO_REPLACE_FAILED_NOTIFICATION().getValue()) {
               Sounds.REFILL_STEP_NOTIFY.play(1.2F);
               Sounds.REFILL_STEP_NOTIFY.play(1.5F, 5);
            }
         }
      }
   }

   private final void notifyDurabilityChange(ItemType var1, int var2, int var3) {
      if ((
            AutoRefillSettings.INSTANCE.getVISUAL_DURABILITY_NOTIFICATION().getValue()
               || AutoRefillSettings.INSTANCE.getAUDIO_DURABILITY_NOTIFICATION().getValue()
         )
         && this.isItNotifyStep(var2, var3)) {
         if (AutoRefillSettings.INSTANCE.getVISUAL_DURABILITY_NOTIFICATION().getValue()) {
            Function1 var4 = AutoRefillHandler$ItemSlotMonitor::notifyDurabilityChange$lambda$4;
            switch ((ToolReplaceVisualNotification)AutoRefillSettings.INSTANCE.getTYPE_VISUAL_DURABILITY_NOTIFICATION().getValue()) {
               case SUBTITLE:
                  VanillaUtilKt.showSubTitle((Component)TextExKt.fromSerializedJson((String)var4.invoke(Boolean.FALSE)));
                  break;
               case HOTBAR:
                  Vanilla.INSTANCE.inGameHud().setOverlayMessage((Component)TextExKt.fromSerializedJson((String)var4.invoke(Boolean.FALSE)), false);
                  break;
               case CHAT:
                  VanillaUtil var10000 = VanillaUtil.INSTANCE;
                  MutableComponent var10001 = TextExKt.fromSerializedJson((String)var4.invoke(Boolean.TRUE));
                  Intrinsics.checkNotNull(var10001);
                  var10000.chat(var10001);
                  break;
               default:
                  throw new NoWhenBranchMatchedException();
            }
         }

         if (AutoRefillSettings.INSTANCE.getAUDIO_DURABILITY_NOTIFICATION().getValue()) {
            Sounds.REFILL_STEP_NOTIFY.play();
         }
      }
   }

   private final void notifyDurabilityNoChange(ItemType var1, int var2, int var3) {
      if ((
            AutoRefillSettings.INSTANCE.getVISUAL_DURABILITY_NOTIFICATION().getValue()
               || AutoRefillSettings.INSTANCE.getAUDIO_DURABILITY_NOTIFICATION().getValue()
         )
         && this.isItNotifyStep(var2, var3)) {
         if (AutoRefillSettings.INSTANCE.getVISUAL_DURABILITY_NOTIFICATION().getValue()) {
            Function1 var4 = AutoRefillHandler$ItemSlotMonitor::notifyDurabilityNoChange$lambda$5;
            switch ((ToolReplaceVisualNotification)AutoRefillSettings.INSTANCE.getTYPE_VISUAL_DURABILITY_NOTIFICATION().getValue()) {
               case SUBTITLE:
                  VanillaUtilKt.showSubTitle((Component)TextExKt.fromSerializedJson((String)var4.invoke(Boolean.FALSE)));
                  break;
               case HOTBAR:
                  Vanilla.INSTANCE.inGameHud().setOverlayMessage((Component)TextExKt.fromSerializedJson((String)var4.invoke(Boolean.FALSE)), false);
                  break;
               case CHAT:
                  VanillaUtil var10000 = VanillaUtil.INSTANCE;
                  MutableComponent var10001 = TextExKt.fromSerializedJson((String)var4.invoke(Boolean.TRUE));
                  Intrinsics.checkNotNull(var10001);
                  var10000.chat(var10001);
                  break;
               default:
                  throw new NoWhenBranchMatchedException();
            }
         }

         if (AutoRefillSettings.INSTANCE.getAUDIO_DURABILITY_NOTIFICATION().getValue()) {
            Sounds.REFILL_STEP_NOTIFY.play();
         }
      }
   }

   private final boolean isItNotifyStep(int var1, int var2) {
      if (!Intrinsics.areEqual(this.storedItem.getItemType(), this.lastTickItem.getItemType())) {
         this.lastNotifyDurability = -1;
      }

      if (this.lastNotifyDurability != var1 && var1 != var2) {
         int var3 = AutoRefillSettings.INSTANCE.getNUMBER_OF_NOTIFICATIONS().getValue().intValue();
         int var4 = AutoRefillSettings.INSTANCE.getNOTIFICATION_STEP().getValue().intValue();
         var3 = var2 + var3 * var4;
         if (var4 <= 0) {
            throw new IllegalArgumentException("Step must be positive, was: " + var4 + ".");
         }

         int var5 = var2;
         var2 = ProgressionUtilKt.getProgressionLastElement(var2, var3, var4);
         if (var5 <= var2) {
            while (true) {
               if (var1 == var5) {
                  this.lastNotifyDurability = var5;
                  return true;
               }

               if (var5 == var2) {
                  break;
               }

               var5 += var4;
            }
         }
      }

      return false;
   }

   private static final String notifySuccessfulChange$lambda$2(ItemType var0, ItemType var1, boolean var2) {
      String var10000 = var2 ? "{\"text\": \"\\n\"}," : "{\"text\": \" - \", \"color\": \"#FFFFFF\"},";
      ItemType var4 = var0;
      net.minecraft.world.item.ItemStack var10001 = new net.minecraft.world.item.ItemStack;
      ItemLike var10003 = (ItemLike)var4.getItem();
      PatchedDataComponentMap var10005 = var4.getTag();
      if (var10005 == null) {
         DataComponentMap var23 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10005 = (PatchedDataComponentMap)var23;
      }

      var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
      String var11;
      if (var10001.getComponents().get(DataComponents.CUSTOM_NAME) != null) {
         ItemType var5 = var0;
         var10001 = new net.minecraft.world.item.ItemStack;
         var10003 = (ItemLike)var5.getItem();
         var10005 = var5.getTag();
         if (var10005 == null) {
            DataComponentMap var25 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10005 = (PatchedDataComponentMap)var25;
         }

         var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
         var11 = var10001.getDisplayName().getString();
         Intrinsics.checkNotNullExpressionValue(var11, "");
      } else {
         I18n var12 = I18n.INSTANCE;
         ItemType var6 = var0;
         net.minecraft.world.item.ItemStack var10002 = new net.minecraft.world.item.ItemStack;
         ItemLike var10004 = (ItemLike)var6.getItem();
         PatchedDataComponentMap var10006 = var6.getTag();
         if (var10006 == null) {
            DataComponentMap var27 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10006 = (PatchedDataComponentMap)var27;
         }

         var10002./* $VF: Unable to resugar constructor */<init>(var10004, 1, var10006);
         String var13 = var10002.getDescriptionId();
         Intrinsics.checkNotNullExpressionValue(var13, "");
         var11 = var12.translate(var13, new Object[0]);
      }

      var0 = var1;
      ItemType var7 = var1;
      net.minecraft.world.item.ItemStack var14 = new net.minecraft.world.item.ItemStack;
      ItemLike var21 = (ItemLike)var7.getItem();
      PatchedDataComponentMap var28 = var7.getTag();
      if (var28 == null) {
         DataComponentMap var29 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var28 = (PatchedDataComponentMap)var29;
      }

      var14./* $VF: Unable to resugar constructor */<init>(var21, 1, var28);
      String var16;
      if (var14.getComponents().get(DataComponents.CUSTOM_NAME) != null) {
         ItemType var8 = var0;
         var14 = new net.minecraft.world.item.ItemStack;
         var21 = (ItemLike)var8.getItem();
         var28 = var8.getTag();
         if (var28 == null) {
            DataComponentMap var31 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var28 = (PatchedDataComponentMap)var31;
         }

         var14./* $VF: Unable to resugar constructor */<init>(var21, 1, var28);
         var16 = var14.getDisplayName().getString();
         Intrinsics.checkNotNullExpressionValue(var16, "");
      } else {
         I18n var17 = I18n.INSTANCE;
         ItemType var9 = var0;
         net.minecraft.world.item.ItemStack var19 = new net.minecraft.world.item.ItemStack;
         ItemLike var26 = (ItemLike)var9.getItem();
         PatchedDataComponentMap var10007 = var9.getTag();
         if (var10007 == null) {
            DataComponentMap var32 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10007 = (PatchedDataComponentMap)var32;
         }

         var19./* $VF: Unable to resugar constructor */<init>(var26, 1, var10007);
         String var20 = var19.getDescriptionId();
         Intrinsics.checkNotNullExpressionValue(var20, "");
         var16 = var17.translate(var20, new Object[0]);
      }

      return "[\n                           {\"translate\" : \"inventoryprofiles.config.notification.tool_replace_ping.ipn\", \"color\" : \"#3584E4\" },\n                           "
         + var10000
         + "\n                           {\"text\" : \"\\\""
         + var11
         + "\\\" \", \"color\": \"#FF8484\"},\n                           {\"translate\" : \"inventoryprofiles.config.notification.tool_replace_success\", \"color\": \"#FFFFFF\"},\n                           {\"text\" : \" \\\""
         + var16
         + "\\\"\", \"color\": \"#8484FF\"}\n                    ]";
   }

   private static final String notifyFailToChange$lambda$3(ItemType var0, boolean var1) {
      String var10000 = var1 ? "{\"text\": \"\\n\"}," : "{\"text\": \" - \", \"color\": \"#FFFFFF\"},";
      ItemType var2 = var0;
      net.minecraft.world.item.ItemStack var10001 = new net.minecraft.world.item.ItemStack;
      ItemLike var10003 = (ItemLike)var2.getItem();
      PatchedDataComponentMap var10005 = var2.getTag();
      if (var10005 == null) {
         DataComponentMap var10 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10005 = (PatchedDataComponentMap)var10;
      }

      var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
      String var6;
      if (var10001.getComponents().get(DataComponents.CUSTOM_NAME) != null) {
         ItemType var3 = var0;
         var10001 = new net.minecraft.world.item.ItemStack;
         var10003 = (ItemLike)var3.getItem();
         var10005 = var3.getTag();
         if (var10005 == null) {
            DataComponentMap var12 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10005 = (PatchedDataComponentMap)var12;
         }

         var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
         var6 = var10001.getDisplayName().getString();
         Intrinsics.checkNotNullExpressionValue(var6, "");
      } else {
         I18n var7 = I18n.INSTANCE;
         ItemType var4 = var0;
         net.minecraft.world.item.ItemStack var10002 = new net.minecraft.world.item.ItemStack;
         ItemLike var10004 = (ItemLike)var4.getItem();
         PatchedDataComponentMap var10006 = var4.getTag();
         if (var10006 == null) {
            DataComponentMap var13 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10006 = (PatchedDataComponentMap)var13;
         }

         var10002./* $VF: Unable to resugar constructor */<init>(var10004, 1, var10006);
         String var8 = var10002.getDescriptionId();
         Intrinsics.checkNotNullExpressionValue(var8, "");
         var6 = var7.translate(var8, new Object[0]);
      }

      return "[\n                                   {\"translate\" : \"inventoryprofiles.config.notification.tool_replace_ping.ipn\", \"color\" : \"#3584E4\" },\n                                   {\"translate\": \"inventoryprofiles.config.notification.tool_replace_ping.warning\", \"color\" : \"#FF8484\"},\n                                   "
         + var10000
         + "\n                                   {\"translate\": \"inventoryprofiles.config.notification.tool_replace_failed.replacing\", \"color\" : \"#E5A50A\", \"with\": [\""
         + var6
         + "\"]}\n                                   ]";
   }

   private static final String notifyDurabilityChange$lambda$4(ItemType var0, int var1, int var2, boolean var3) {
      String var10000 = var3 ? "{\"text\": \"\\n\"}," : "{\"text\": \" - \", \"color\": \"#FFFFFF\"},";
      ItemType var4 = var0;
      net.minecraft.world.item.ItemStack var10001 = new net.minecraft.world.item.ItemStack;
      ItemLike var10003 = (ItemLike)var4.getItem();
      PatchedDataComponentMap var10005 = var4.getTag();
      if (var10005 == null) {
         DataComponentMap var12 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10005 = (PatchedDataComponentMap)var12;
      }

      var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
      String var8;
      if (var10001.getComponents().get(DataComponents.CUSTOM_NAME) != null) {
         ItemType var5 = var0;
         var10001 = new net.minecraft.world.item.ItemStack;
         var10003 = (ItemLike)var5.getItem();
         var10005 = var5.getTag();
         if (var10005 == null) {
            DataComponentMap var14 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10005 = (PatchedDataComponentMap)var14;
         }

         var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
         var8 = var10001.getDisplayName().getString();
         Intrinsics.checkNotNullExpressionValue(var8, "");
      } else {
         I18n var9 = I18n.INSTANCE;
         ItemType var6 = var0;
         net.minecraft.world.item.ItemStack var10002 = new net.minecraft.world.item.ItemStack;
         ItemLike var10004 = (ItemLike)var6.getItem();
         PatchedDataComponentMap var10006 = var6.getTag();
         if (var10006 == null) {
            DataComponentMap var15 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10006 = (PatchedDataComponentMap)var15;
         }

         var10002./* $VF: Unable to resugar constructor */<init>(var10004, 1, var10006);
         String var10 = var10002.getDescriptionId();
         Intrinsics.checkNotNullExpressionValue(var10, "");
         var8 = var9.translate(var10, new Object[0]);
      }

      return "[\n                        {\"text\" : \"\"},\n                        {\"translate\" : \"inventoryprofiles.config.notification.tool_replace_ping.ipn\", \"color\" : \"#3584E4\" },\n                        "
         + var10000
         + "\n                        {\"translate\": \"inventoryprofiles.config.notification.tool_replace_ping.durability\", \"color\" : \"#E5A50A\", \"with\": [\""
         + var8
         + "\",\""
         + var1
         + "\"]},\n                        {\"translate\": \"inventoryprofiles.config.notification.tool_replace_ping.replacing\", \"color\" : \"#FF4545\", \"with\": [\""
         + var2
         + "\"]}\n                        ]";
   }

   private static final String notifyDurabilityNoChange$lambda$5(ItemType var0, int var1, boolean var2) {
      String var10000 = var2 ? "{\"text\": \"\\n\"}," : "{\"text\": \" - \", \"color\": \"#FFFFFF\"},";
      ItemType var3 = var0;
      net.minecraft.world.item.ItemStack var10001 = new net.minecraft.world.item.ItemStack;
      ItemLike var10003 = (ItemLike)var3.getItem();
      PatchedDataComponentMap var10005 = var3.getTag();
      if (var10005 == null) {
         DataComponentMap var11 = DataComponentMap.EMPTY;
         Intrinsics.checkNotNull(DataComponentMap.EMPTY);
         var10005 = (PatchedDataComponentMap)var11;
      }

      var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
      String var7;
      if (var10001.getComponents().get(DataComponents.CUSTOM_NAME) != null) {
         ItemType var4 = var0;
         var10001 = new net.minecraft.world.item.ItemStack;
         var10003 = (ItemLike)var4.getItem();
         var10005 = var4.getTag();
         if (var10005 == null) {
            DataComponentMap var13 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10005 = (PatchedDataComponentMap)var13;
         }

         var10001./* $VF: Unable to resugar constructor */<init>(var10003, 1, var10005);
         var7 = var10001.getDisplayName().getString();
         Intrinsics.checkNotNullExpressionValue(var7, "");
      } else {
         I18n var8 = I18n.INSTANCE;
         ItemType var5 = var0;
         net.minecraft.world.item.ItemStack var10002 = new net.minecraft.world.item.ItemStack;
         ItemLike var10004 = (ItemLike)var5.getItem();
         PatchedDataComponentMap var10006 = var5.getTag();
         if (var10006 == null) {
            DataComponentMap var14 = DataComponentMap.EMPTY;
            Intrinsics.checkNotNull(DataComponentMap.EMPTY);
            var10006 = (PatchedDataComponentMap)var14;
         }

         var10002./* $VF: Unable to resugar constructor */<init>(var10004, 1, var10006);
         String var9 = var10002.getDescriptionId();
         Intrinsics.checkNotNullExpressionValue(var9, "");
         var7 = var8.translate(var9, new Object[0]);
      }

      return "[\n                        {\"text\" : \"\"},\n                        {\"translate\" : \"inventoryprofiles.config.notification.tool_replace_ping.ipn\", \"color\" : \"#3584E4\" },\n                        "
         + var10000
         + "\n                        {\"translate\": \"inventoryprofiles.config.notification.tool_replace_ping.durability\", \"color\" : \"#E5A50A\", \"with\": [\""
         + var7
         + "\",\""
         + var1
         + "\"]}\n                        ]";
   }
}
