package org.anti_ad.mc.ipnext.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.Ref.BooleanRef;
import kotlin.ranges.IntRange;
import kotlin.text.StringsKt;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.anti_ad.a.a.a.a.a;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.common.vanilla.VanillaUtil;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.config.Debugs;
import org.anti_ad.mc.ipnext.config.LockedSlotsSettings;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.event.autorefill.AutoRefillHandler;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt$(itemType)$1;
import org.anti_ad.mc.ipnext.inventory.AreaType;
import org.anti_ad.mc.ipnext.inventory.AreaTypes;
import org.anti_ad.mc.ipnext.inventory.ContainerClicker;
import org.anti_ad.mc.ipnext.inventory.ContainerType;
import org.anti_ad.mc.ipnext.inventory.ContainerTypes;
import org.anti_ad.mc.ipnext.inventory.GeneralInventoryActions;
import org.anti_ad.mc.ipnext.inventory.ItemArea;
import org.anti_ad.mc.ipnext.item.ItemStack$Companion;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nLockedSlotKeeper.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LockedSlotKeeper.kt\norg/anti_ad/mc/ipnext/event/LockedSlotKeeper\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 kt_common.kt\norg/anti_ad/mc/common/extensions/Kt_commonKt\n+ 5 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 6 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,474:1\n78#2:475\n88#2:477\n71#2:478\n66#2,7:479\n78#2:488\n90#2:489\n66#2,4:493\n66#2,4:501\n78#2:509\n90#2:511\n90#2:514\n90#2:517\n127#2:519\n78#2:520\n90#2:521\n78#2:522\n90#2:523\n78#2:524\n90#2:525\n127#2:526\n78#2:527\n90#2:528\n78#2:529\n90#2:530\n78#2:531\n88#2:534\n71#2:535\n66#2,7:536\n78#2:544\n88#2:545\n71#2:546\n66#2,7:547\n78#2:557\n90#2:558\n66#2,4:559\n1869#3:476\n1870#3:486\n1869#3:487\n1870#3:490\n1869#3,2:507\n1869#3:510\n1870#3:512\n1869#3:513\n1870#3:515\n1869#3:516\n1870#3:518\n774#3:532\n865#3:533\n866#3:543\n1869#3:556\n1870#3:565\n130#4:491\n130#4:554\n1#5:492\n1#5:555\n115#6:497\n112#6:498\n131#6:499\n112#6:500\n131#6:505\n112#6:506\n131#6:563\n112#6:564\n*S KotlinDebug\n*F\n+ 1 LockedSlotKeeper.kt\norg/anti_ad/mc/ipnext/event/LockedSlotKeeper\n*L\n82#1:475\n84#1:477\n84#1:478\n84#1:479,7\n153#1:488\n153#1:489\n267#1:493,4\n268#1:501,4\n310#1:509\n315#1:511\n323#1:514\n332#1:517\n359#1:519\n368#1:520\n372#1:521\n377#1:522\n377#1:523\n389#1:524\n389#1:525\n412#1:526\n415#1:527\n415#1:528\n421#1:529\n421#1:530\n443#1:531\n450#1:534\n450#1:535\n450#1:536,7\n461#1:544\n467#1:545\n467#1:546\n467#1:547,7\n203#1:557\n203#1:558\n209#1:559,4\n83#1:476\n83#1:486\n152#1:487\n152#1:490\n274#1:507,2\n314#1:510\n314#1:512\n322#1:513\n322#1:515\n331#1:516\n331#1:518\n449#1:532\n449#1:533\n449#1:543\n199#1:556\n199#1:565\n234#1:491\n196#1:554\n234#1:492\n196#1:555\n267#1:497\n267#1:498\n267#1:499\n267#1:500\n268#1:505\n268#1:506\n209#1:563\n209#1:564\n*E\n"
)
public final class LockedSlotKeeper {
   @NotNull
   public static final LockedSlotKeeper INSTANCE = new LockedSlotKeeper();
   private static boolean pickingItem;
   private static boolean screenOpening;
   private static boolean worldJoined;
   private static int ticksAfterJoin;
   @NotNull
   private static final Set emptyLockedSlots = new LinkedHashSet();
   @NotNull
   private static final Set emptyNonLockedSlots = new LinkedHashSet();
   @NotNull
   private static final Set emptyNonLockedHotbarSlots = new LinkedHashSet();
   @NotNull
   private static final Set ignoredHotbarSlots = new LinkedHashSet();
   @NotNull
   private static final Set skipRemoveEmptyFor = new LinkedHashSet();
   private static boolean skipTick;
   private static boolean doEverySecondTick;
   private static boolean processingLockedPickups;
   @NotNull
   private static final Set cleanHotbarBlackList = new LinkedHashSet();

   private LockedSlotKeeper() {
   }

   public final boolean getPickingItem() {
      return pickingItem;
   }

   public final void setPickingItem(boolean var1) {
      pickingItem = var1;
   }

   @NotNull
   public final Set getEmptyLockedSlots() {
      return emptyLockedSlots;
   }

   @NotNull
   public final Set getEmptyNonLockedSlots() {
      return emptyNonLockedSlots;
   }

   public final boolean getDoEverySecondTick() {
      return doEverySecondTick;
   }

   public final void setDoEverySecondTick(boolean var1) {
      doEverySecondTick = var1;
   }

   public final boolean isMultiPlayer() {
      return !Vanilla.INSTANCE.isSinglePlayer() || Debugs.INSTANCE.getFORCE_SERVER_METHOD_FOR_LOCKED_SLOTS().getBooleanValue();
   }

   public final boolean getProcessingLockedPickups() {
      return processingLockedPickups;
   }

   public final boolean isWholeInventoryEmpty() {
      AbstractContainerMenu var1 = Vanilla.INSTANCE.container();
      if (ContainerTypes.getTypes$default(ContainerTypes.INSTANCE, var1, null, 2, null).contains(ContainerType.CREATIVE)) {
         return true;
      }

      NonNullList var10000 = var1.slots;
      Intrinsics.checkNotNullExpressionValue(var1.slots, "");
      Iterator var4 = ((List)var10000).iterator();

      while (var4.hasNext()) {
         ItemStack var5 = ((Slot)var4.next()).getItem();
         Intrinsics.checkNotNullExpressionValue(var5, "");
         ItemStack var2 = var5;
         org.anti_ad.mc.ipnext.item.ItemStack var6;
         if (var5.isEmpty()) {
            var6 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
         } else {
            ItemStack$Companion var7 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
            ItemStack var3 = var2;
            Item var10003 = var3.getItem();
            Intrinsics.checkNotNullExpressionValue(var10003, "");
            DataComponentMap var10004 = var3.getComponents();
            Intrinsics.checkNotNull(var10004);
            var6 = var7.invoke(
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

         if (!ItemStackExtensionsKt.isEmpty(var6)) {
            return false;
         }
      }

      return true;
   }

   public final void onTickInGame() {
      if (doEverySecondTick) {
         if (skipTick) {
            skipTick = false;
            AutoRefillHandler.INSTANCE.setSkipTick(true);
            return;
         }

         skipTick = true;
      }

      if (ModSettings.INSTANCE.getENABLE_LOCK_SLOTS().getBooleanValue()
         && !LockedSlotsSettings.INSTANCE.getLOCKED_SLOTS_ALLOW_PICKUP_INTO_EMPTY().getBooleanValue()
         && this.isMultiPlayer()) {
         if (worldJoined) {
            if (ticksAfterJoin > LockedSlotsSettings.INSTANCE.getLOCKED_SLOTS_DELAY_KEEPER_REINIT_TICKS().getIntegerValue()) {
               Log.INSTANCE.trace("Initialising because of timeout!");
               this.init();
               worldJoined = false;
               ticksAfterJoin = 0;
               return;
            }

            if (!this.isWholeInventoryEmpty()) {
               Log.INSTANCE.trace("Inventory is NOT empty initialising");
               this.init();
               worldJoined = false;
               ticksAfterJoin = 0;
               return;
            }

            Log.INSTANCE.trace("Inventory is empty skipping initialization");
            ticksAfterJoin++;
         }

         if (VanillaUtil.INSTANCE.inGame() && Vanilla.INSTANCE.screen() != null) {
            screenOpening = true;
            return;
         }

         if (screenOpening) {
            screenOpening = false;
            Log.INSTANCE.trace("Inventory is NOT empty initialising - 2");
            this.init();
            return;
         }

         if (Vanilla.INSTANCE.container() instanceof InventoryMenu) {
            this.checkNewItems();
         }
      }
   }

   private final void checkNewItems() {
      List var1;
      CollectionsKt.sort(var1 = CollectionsKt.toMutableList(emptyNonLockedSlots));
      BooleanRef var2 = new BooleanRef();
      processingLockedPickups = true;
      Set var3 = new LinkedHashSet();
      Iterator var4 = emptyLockedSlots.iterator();

      while (var4.hasNext()) {
         int var5 = ((Number)var4.next()).intValue();
         NonNullList var10000 = Vanilla.INSTANCE.container().slots;
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         ItemStack var7 = ((Slot)((List)var10000).get(var5)).getItem();
         Intrinsics.checkNotNullExpressionValue(var7, "");
         if (!var7.isEmpty()) {
            var2.element = true;
            if (!ignoredHotbarSlots.contains(var5)) {
               Log.INSTANCE.trace("Items were placed int locked slot! " + var5);
               AutoRefillHandler.INSTANCE.setSkipTick(true);
               if (!var1.isEmpty()) {
                  int var6 = ((Number)var1.get(0)).intValue();
                  INSTANCE.moveItem(var5, var6);
                  emptyNonLockedHotbarSlots.remove(var6);
                  var1.remove(0);
               } else {
                  Log.INSTANCE.trace("Throwing away " + var5 + " since there no free unlocked slots");
                  ContainerClicker.INSTANCE.qClick(var5);
               }
            } else {
               Log.INSTANCE.trace("Removing ignored: " + var5 + " in locked slots.");
               ignoredHotbarSlots.remove(var5);
               var3.add(var5);
            }
         }
      }

      if (!var3.isEmpty()) {
         Log.INSTANCE.trace("WIll skip empty non locked checks");
         var3.removeAll(skipRemoveEmptyFor);
         skipRemoveEmptyFor.clear();
         emptyLockedSlots.removeAll(var3);
      } else if (LockedSlotsSettings.INSTANCE.getLOCKED_SLOTS_EMPTY_HOTBAR_AS_SEMI_LOCKED().getBooleanValue()
         && !emptyNonLockedSlots.isEmpty()
         && !emptyNonLockedHotbarSlots.isEmpty()) {
         if (!skipRemoveEmptyFor.isEmpty()) {
            emptyNonLockedHotbarSlots.addAll(skipRemoveEmptyFor);
         }

         checkNewItems$checkHotbar$default(var3, var1, var2, false, 8, null);
         ignoredHotbarSlots.addAll(skipRemoveEmptyFor);
         if (!var1.isEmpty()) {
            checkNewItems$checkHotbar(var3, var1, var2, true);
         }

         skipRemoveEmptyFor.clear();
      }

      emptyNonLockedSlots.clear();
      emptyNonLockedSlots.addAll(var1);
      if (var2.element) {
         Log.INSTANCE.trace("empty Non Locked Slots: " + emptyNonLockedSlots);
         Log.INSTANCE.trace("empty Locked Slots: " + emptyLockedSlots);
         Log.INSTANCE.trace("empty non Locked hotbar Slots: " + emptyNonLockedHotbarSlots);
         Log.INSTANCE.trace("ignored hotbat Slots: " + ignoredHotbarSlots);
      }

      processingLockedPickups = false;
   }

   private final boolean isWildCard(String var1) {
      return StringsKt.contains$default(var1, '*', false, 2, null) || StringsKt.contains$default(var1, '?', false, 2, null);
   }

   public final boolean isIgnored(@Nullable ItemStack var1) {
      if (var1 != null) {
         Iterator var2 = cleanHotbarBlackList.iterator();

         Object var12;
         while (true) {
            if (!var2.hasNext()) {
               var12 = null;
               break;
            }

            Object var3;
            label46: {
               String var4;
               var12 = var4 = (String)(var3 = var2.next());
               ItemStack var5 = var1;
               Item var10003 = var5.getItem();
               Intrinsics.checkNotNullExpressionValue(var10003, "");
               DataComponentMap var10004 = var5.getComponents();
               Intrinsics.checkNotNull(var10004);
               ItemType var6 = new ItemType(
                  var10003,
                  (PatchedDataComponentMap)var10004,
                  var5.getComponentsPatch(),
                  new VanillaAccessorsKt$(itemType)$1(var5),
                  false,
                  false,
                  null,
                  112,
                  null
               );
               ResourceLocation var10001 = BuiltInRegistries.ITEM.getKey(var6.getItem());
               Intrinsics.checkNotNullExpressionValue(var10001, "");
               String var13 = var10001.getNamespace();
               Intrinsics.checkNotNullExpressionValue(var13, "");
               if (!Intrinsics.areEqual(var12, var13)) {
                  var5 = var1;
                  var10003 = var5.getItem();
                  Intrinsics.checkNotNullExpressionValue(var10003, "");
                  var10004 = var5.getComponents();
                  Intrinsics.checkNotNull(var10004);
                  ItemType var8 = new ItemType(
                     var10003,
                     (PatchedDataComponentMap)var10004,
                     var5.getComponentsPatch(),
                     new VanillaAccessorsKt$(itemType)$1(var5),
                     false,
                     false,
                     null,
                     112,
                     null
                  );
                  var10001 = BuiltInRegistries.ITEM.getKey(var8.getItem());
                  Intrinsics.checkNotNullExpressionValue(var10001, "");
                  String var15 = var10001.toString();
                  Intrinsics.checkNotNullExpressionValue(var15, "");
                  label29:
                  if (!Intrinsics.areEqual(var4, var15)) {
                     if (INSTANCE.isWildCard(var4)) {
                        var5 = var1;
                        var10003 = var5.getItem();
                        Intrinsics.checkNotNullExpressionValue(var10003, "");
                        var10004 = var5.getComponents();
                        Intrinsics.checkNotNull(var10004);
                        ItemType var10 = new ItemType(
                           var10003,
                           (PatchedDataComponentMap)var10004,
                           var5.getComponentsPatch(),
                           new VanillaAccessorsKt$(itemType)$1(var5),
                           false,
                           false,
                           null,
                           112,
                           null
                        );
                        var10001 = BuiltInRegistries.ITEM.getKey(var10.getItem());
                        Intrinsics.checkNotNullExpressionValue(var10001, "");
                        String var17 = var10001.toString();
                        Intrinsics.checkNotNullExpressionValue(var17, "");
                        if (a.a(var4, var17)) {
                           break label29;
                        }
                     }

                     var12 = false;
                     break label46;
                  }
               }

               var12 = true;
            }

            if (var12) {
               var12 = var3;
               break;
            }
         }

         if (var12 != null) {
            return true;
         }
      }

      return false;
   }

   public final void updateIgnored() {
      cleanHotbarBlackList.clear();
      CharSequence var10000 = LockedSlotsSettings.INSTANCE.getLOCKED_SLOTS_EMPTY_HOTBAR_BLACKLIST().getValue();
      String[] var1;
      (var1 = new String[1])[0] = ",";

      for (String var2 : StringsKt.split$default(var10000, var1, false, 0, 6, null)) {
         cleanHotbarBlackList.add(StringsKt.trim(var2).toString());
      }
   }

   private final void moveItem(int var1, int var2) {
      GeneralInventoryActions.INSTANCE.cleanCursor();
      int var3;
      if ((var3 = var1 - 36) >= 0 ? var3 < 9 : false) {
         var3 = var1 - 36;
         Log.INSTANCE.trace("Swapping " + var1 + " to " + var2);
         ContainerClicker.INSTANCE.swap(var2, var3);
      } else {
         Log.INSTANCE.trace("moving stack from " + var1 + " to " + var2);
         ContainerClicker.INSTANCE.leftClick(var1);
         ContainerClicker.INSTANCE.leftClick(var2);
      }
   }

   public final void init() {
      if (!processingLockedPickups) {
         if (this.isMultiPlayer()) {
            emptyLockedSlots.clear();
            emptyNonLockedSlots.clear();
            emptyNonLockedHotbarSlots.clear();
            AbstractContainerMenu var1 = Vanilla.INSTANCE.container();
            if (!ContainerTypes.getTypes$default(ContainerTypes.INSTANCE, var1, null, 2, null).contains(ContainerType.CREATIVE)) {
               AreaTypes var2 = AreaTypes.INSTANCE;
               AreaType var3 = AreaTypes.INSTANCE.getPlayerStorage().plus(var2.getPlayerHotbar()).minus(var2.getLockedSlots());
               NonNullList var10000 = var1.slots;
               Intrinsics.checkNotNullExpressionValue(var1.slots, "");
               List var5 = (List)var10000;
               ItemArea var4 = var3.getItemArea(var1, var5);
               Iterator var9 = var2.getLockedSlots().getItemArea(var1, var5).getSlotIndices().iterator();

               while (var9.hasNext()) {
                  int var6 = ((Number)var9.next()).intValue();
                  ItemStack var12 = ((Slot)var5.get(var6)).getItem();
                  Intrinsics.checkNotNullExpressionValue(var12, "");
                  if (var12.isEmpty()) {
                     emptyLockedSlots.add(var6);
                  }
               }

               Log.INSTANCE.trace("empty locked slots " + emptyLockedSlots);
               Iterator var10 = var4.getSlotIndices().iterator();

               while (var10.hasNext()) {
                  int var11 = ((Number)var10.next()).intValue();
                  ItemStack var13 = ((Slot)var5.get(var11)).getItem();
                  Intrinsics.checkNotNullExpressionValue(var13, "");
                  if (var13.isEmpty()) {
                     emptyNonLockedSlots.add(var11);
                  }
               }

               Log.INSTANCE.trace("empty NON Locked slots " + emptyNonLockedSlots);
               if (LockedSlotsSettings.INSTANCE.getLOCKED_SLOTS_EMPTY_HOTBAR_AS_SEMI_LOCKED().getBooleanValue()) {
                  Iterator var8 = var2.getPlayerHotbar().getItemArea(var1, var5).getSlotIndices().iterator();

                  while (var8.hasNext()) {
                     int var7 = ((Number)var8.next()).intValue();
                     ItemStack var14 = ((Slot)var5.get(var7)).getItem();
                     Intrinsics.checkNotNullExpressionValue(var14, "");
                     if (var14.isEmpty() && !emptyLockedSlots.contains(var7)) {
                        emptyNonLockedHotbarSlots.add(var7);
                     }
                  }

                  Log.INSTANCE.trace("empty NON Locked hotbar slots " + emptyNonLockedHotbarSlots);
               }
            }
         }
      }
   }

   public final void onJoinWorld() {
      if (this.isMultiPlayer()) {
         ticksAfterJoin = 0;
         worldJoined = true;
      }
   }

   public final void addIgnoredHotbarSlotId(int var1) {
      if (pickingItem && this.isMultiPlayer()) {
         Log.INSTANCE.trace("Adding " + var1 + " to ignored");
         ignoredHotbarSlots.add(var1);
      }
   }

   public final void ignoreSelectedHotbarSlotForHandSwap() {
      if (pickingItem && this.isMultiPlayer()) {
         int var1 = Vanilla.INSTANCE.playerInventory().selected + 36;
         if (36 <= var1 ? var1 < 45 : false) {
            AbstractContainerMenu var2 = Vanilla.INSTANCE.container();
            if (ContainerTypes.getTypes$default(ContainerTypes.INSTANCE, var2, null, 2, null).contains(ContainerType.CREATIVE)) {
               return;
            }

            NonNullList var10000 = var2.slots;
            Intrinsics.checkNotNullExpressionValue(var2.slots, "");
            List var3 = (List)var10000;
            ItemArea var4 = AreaTypes.INSTANCE.getPlayerOffhand().getItemArea(var2, var3);
            ItemArea var5 = AreaTypes.INSTANCE.getLockedSlots().getItemArea(var2, var3);
            int var7 = ((Number)var4.getSlotIndices().get(0)).intValue();
            ItemStack var8 = ((Slot)var3.get(var7)).getItem();
            Intrinsics.checkNotNullExpressionValue(var8, "");
            ItemStack var6 = var8;
            Log.INSTANCE.trace("Offhand slot id is " + var7);
            Log.INSTANCE.trace("Offhand slot is " + var6);
            skipTick = false;
            if (!var6.isEmpty()) {
               var10000 = Vanilla.INSTANCE.container().slots;
               Intrinsics.checkNotNullExpressionValue(var10000, "");
               ItemStack var10 = ((Slot)((List)var10000).get(var1)).getItem();
               Intrinsics.checkNotNullExpressionValue(var10, "");
               if (var10.isEmpty()) {
                  ignoredHotbarSlots.add(var1);
                  Log.INSTANCE.trace("ignoring hotbar slotId: " + var1 + ". For offhand swap");
                  if (LockSlotsHandler.INSTANCE.isSlotLocked(var1)) {
                     Log.INSTANCE.trace("Adding " + var1 + " to empty locked slots");
                     emptyLockedSlots.add(var1);
                  } else if (!emptyLockedSlots.contains(var1)) {
                     emptyNonLockedHotbarSlots.add(var1);
                  }

                  skipTick = false;
                  return;
               }
            } else {
               var10000 = var2.slots;
               Intrinsics.checkNotNullExpressionValue(var2.slots, "");
               ItemStack var12 = ((Slot)((List)var10000).get(var1)).getItem();
               Intrinsics.checkNotNullExpressionValue(var12, "");
               if (!var12.isEmpty()) {
                  if (var5.getSlotIndices().contains(var1)) {
                     Log.INSTANCE.trace("(Empty offhand) Adding " + var1 + " to empty locked slots");
                     emptyLockedSlots.add(var1);
                  }

                  if (!emptyLockedSlots.contains(var1)) {
                     Log.INSTANCE.trace("(Empty offhand) Adding " + var1 + " to empty non locked hotbar slots");
                     emptyNonLockedHotbarSlots.add(var1);
                     emptyNonLockedSlots.add(var1);
                  }

                  skipRemoveEmptyFor.add(var1);
                  ignoredHotbarSlots.add(var1);
                  skipTick = false;
               }
            }
         }
      }
   }

   public final void ignoredSelectedHotbarSlot(int var1) {
      if (pickingItem && this.isMultiPlayer()) {
         AbstractContainerMenu var2 = Vanilla.INSTANCE.container();
         Log.INSTANCE.trace("picked up from slot: " + var1);
         int var3 = Vanilla.INSTANCE.playerInventory().selected + 36;
         if (36 <= var3 ? var3 < 45 : false) {
            NonNullList var10000 = var2.slots;
            Intrinsics.checkNotNullExpressionValue(var2.slots, "");
            ItemStack var7 = ((Slot)((List)var10000).get(var3)).getItem();
            Intrinsics.checkNotNullExpressionValue(var7, "");
            if (var7.isEmpty()) {
               Log.INSTANCE.trace("ignoring selected hotbar slotId: " + var3);
               ignoredHotbarSlots.add(var3);
            } else {
               int var4 = var3;
               Iterator var6 = CollectionsKt.minus((Iterable)(new IntRange(36, 44)), var3).iterator();

               while (var6.hasNext()) {
                  int var5 = ((Number)var6.next()).intValue();
                  var10000 = var2.slots;
                  Intrinsics.checkNotNullExpressionValue(var2.slots, "");
                  ItemStack var9 = ((Slot)((List)var10000).get(var5)).getItem();
                  Intrinsics.checkNotNullExpressionValue(var9, "");
                  if (var9.isEmpty()) {
                     var4 = var5;
                     break;
                  }
               }

               Log.INSTANCE.trace("ignoring selected hotbar slotId: " + var4);
               ignoredHotbarSlots.add(var4);
            }
         }

         if (var1 > 0 && (36 <= var1 ? var1 >= 45 : true)) {
            emptyNonLockedSlots.add(var1);
         }
      }
   }

   public final boolean isOnlyHotbarFree() {
      AbstractContainerMenu var1 = Vanilla.INSTANCE.container();
      if (ContainerTypes.getTypes$default(ContainerTypes.INSTANCE, var1, null, 2, null).contains(ContainerType.CREATIVE)) {
         return false;
      }

      NonNullList var10000 = var1.slots;
      Intrinsics.checkNotNullExpressionValue(var1.slots, "");
      List var2 = (List)var10000;
      Iterable var7 = (LockedSlotsSettings.INSTANCE.getLOCKED_SLOTS_ALLOW_PICKUP_INTO_EMPTY().getBooleanValue()
            ? AreaTypes.INSTANCE.getPlayerStorage().getItemArea(var1, var2)
            : AreaTypes.INSTANCE.getPlayerStorage().getItemArea(var1, var2).minus(AreaTypes.INSTANCE.getLockedSlots().getItemArea(var1, var2)))
         .getSlotIndices();
      var var3 = new ArrayList();
      Iterator var8 = var7.iterator();

      while (var8.hasNext()) {
         Object var4;
         int var5 = ((Number)(var4 = var8.next())).intValue();
         ItemStack var10 = ((Slot)var2.get(var5)).getItem();
         Intrinsics.checkNotNullExpressionValue(var10, "");
         ItemStack var9 = var10;
         org.anti_ad.mc.ipnext.item.ItemStack var11;
         if (var10.isEmpty()) {
            var11 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
         } else {
            ItemStack$Companion var12 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
            ItemStack var6 = var9;
            Item var10003 = var6.getItem();
            Intrinsics.checkNotNullExpressionValue(var10003, "");
            DataComponentMap var10004 = var6.getComponents();
            Intrinsics.checkNotNull(var10004);
            var11 = var12.invoke(
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
               var9.getCount()
            );
         }

         if (ItemStackExtensionsKt.isEmpty(var11)) {
            var3.add(var4);
         }
      }

      return var3.isEmpty();
   }

   public final boolean isHotBarSlotEmpty(int var1) {
      AbstractContainerMenu var2 = Vanilla.INSTANCE.container();
      if (ContainerTypes.getTypes$default(ContainerTypes.INSTANCE, var2, null, 2, null).contains(ContainerType.CREATIVE)) {
         return false;
      }

      NonNullList var10000 = var2.slots;
      Intrinsics.checkNotNullExpressionValue(var2.slots, "");
      List var3 = (List)var10000;
      ItemArea var6 = AreaTypes.INSTANCE.getPlayerHotbar().getItemArea(var2, var3);
      if (var1 >= 0 ? var1 < 9 : false) {
         var1 += 36;
         if (var6.getSlotIndices().contains(var1)) {
            ItemStack var8 = ((Slot)var3.get(var1)).getItem();
            Intrinsics.checkNotNullExpressionValue(var8, "");
            ItemStack var5 = var8;
            org.anti_ad.mc.ipnext.item.ItemStack var9;
            if (var8.isEmpty()) {
               var9 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
            } else {
               ItemStack$Companion var10 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
               ItemStack var7 = var5;
               Item var10003 = var7.getItem();
               Intrinsics.checkNotNullExpressionValue(var10003, "");
               DataComponentMap var10004 = var7.getComponents();
               Intrinsics.checkNotNull(var10004);
               var9 = var10.invoke(
                  new ItemType(
                     var10003,
                     (PatchedDataComponentMap)var10004,
                     var7.getComponentsPatch(),
                     new VanillaAccessorsKt$(itemType)$1(var7),
                     false,
                     false,
                     null,
                     112,
                     null
                  ),
                  var5.getCount()
               );
            }

            return ItemStackExtensionsKt.isEmpty(var9);
         }
      }

      return false;
   }

   private static final void checkNewItems$checkHotbar(Set var0, List var1, BooleanRef var2, boolean var3) {
      Set var4 = CollectionsKt.toMutableSet(skipRemoveEmptyFor);
      String var9 = var3 ? "Second run: " : "";
      var0.clear();
      if (!CollectionsKt.minus(var1, emptyNonLockedHotbarSlots).isEmpty()) {
         Iterator var5 = emptyNonLockedHotbarSlots.iterator();

         while (var5.hasNext()) {
            int var6 = ((Number)var5.next()).intValue();
            if (!(Vanilla.INSTANCE.container() instanceof InventoryMenu)) {
               Log.INSTANCE.trace("Current container is: " + Vanilla.INSTANCE.container().getClass().getName());
            }

            NonNullList var10000 = Vanilla.INSTANCE.container().slots;
            Intrinsics.checkNotNullExpressionValue(var10000, "");
            ItemStack var12 = ((Slot)((List)var10000).get(var6)).getItem();
            Intrinsics.checkNotNullExpressionValue(var12, "");
            ItemStack var7 = var12;
            if (!var12.isEmpty()) {
               var2.element = true;
               if (INSTANCE.isIgnored(var7)) {
                  var0.add(var6);
                  AutoRefillHandler.INSTANCE.setSkipTick(true);
                  Log var13 = Log.INSTANCE;
                  Item var10004 = var7.getItem();
                  Intrinsics.checkNotNullExpressionValue(var10004, "");
                  DataComponentMap var10005 = var7.getComponents();
                  Intrinsics.checkNotNull(var10005);
                  ItemType var10 = new ItemType(
                     var10004,
                     (PatchedDataComponentMap)var10005,
                     var7.getComponentsPatch(),
                     new VanillaAccessorsKt$(itemType)$1(var7),
                     false,
                     false,
                     null,
                     112,
                     null
                  );
                  ResourceLocation var10002 = BuiltInRegistries.ITEM.getKey(var10.getItem());
                  Intrinsics.checkNotNullExpressionValue(var10002, "");
                  String var14 = var10002.toString();
                  Intrinsics.checkNotNullExpressionValue(var14, "");
                  var13.trace(var9 + "Item type: " + var14 + " is allowed in slot id " + var6);
               } else if (!ignoredHotbarSlots.contains(var6)) {
                  if (var1.size() > 0) {
                     AutoRefillHandler.INSTANCE.setSkipTick(true);
                     int var11 = ((Number)var1.get(0)).intValue();
                     int var8 = var6 - 36;
                     Log.INSTANCE.trace(var9 + "Fast Swapping " + var6 + " to " + var11);
                     ContainerClicker.INSTANCE.swap(var11, var8);
                     var1.remove(0);
                  }
               } else {
                  AutoRefillHandler.INSTANCE.setSkipTick(true);
                  Log.INSTANCE.trace(var9 + "Removing " + var6 + " form ignored");
                  ignoredHotbarSlots.remove(var6);
                  var0.add(var6);
               }
            }
         }

         if (!var0.isEmpty()) {
            Log.INSTANCE.trace("Skip remove from empty is: " + var4);
            var0.removeAll(var4);
            emptyNonLockedHotbarSlots.removeAll(var0);
         }
      }
   }
}
