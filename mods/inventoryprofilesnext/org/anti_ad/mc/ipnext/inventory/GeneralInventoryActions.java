package org.anti_ad.mc.ipnext.inventory;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.sequences.SequencesKt;
import kotlin.text.StringsKt;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import org.anti_ad.mc.alias.component.ComponentExKt;
import org.anti_ad.mc.alias.nbt.NbtExKt;
import org.anti_ad.mc.common.LogBase;
import org.anti_ad.mc.common.TellPlayer;
import org.anti_ad.mc.common.LogBase.LogLevel;
import org.anti_ad.mc.common.config.options.ConfigEnum;
import org.anti_ad.mc.common.config.options.ConfigString;
import org.anti_ad.mc.common.extensions.Kt_collectionKt;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.common.vanilla.VanillaUtil;
import org.anti_ad.mc.ipn.features.scrolling.ScrollDirection;
import org.anti_ad.mc.ipn.features.scrolling.ScrollingUtils;
import org.anti_ad.mc.ipnext.config.ConfigEnumsExtKt;
import org.anti_ad.mc.ipnext.config.GuiSettings;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.config.PostAction;
import org.anti_ad.mc.ipnext.config.ScrollSettings;
import org.anti_ad.mc.ipnext.config.SortingMethodIndividual;
import org.anti_ad.mc.ipnext.ingame.InventoryKt;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt$(itemType)$1;
import org.anti_ad.mc.ipnext.inventory.action.SubTrackerActionsKt;
import org.anti_ad.mc.ipnext.inventory.data.MutableSubTracker;
import org.anti_ad.mc.ipnext.item.ComponentUtils;
import org.anti_ad.mc.ipnext.item.ItemStack;
import org.anti_ad.mc.ipnext.item.ItemStack$Companion;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.ItemTypeExtensionsKt;
import org.anti_ad.mc.libipn.Log;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nGeneralInventoryActions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 GeneralInventoryActions.kt\norg/anti_ad/mc/ipnext/inventory/GeneralInventoryActions\n+ 2 TellPlayer.kt\norg/anti_ad/mc/common/TellPlayer\n+ 3 Log.kt\norg/anti_ad/mc/common/LogBase\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 5 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 6 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 7 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n+ 8 AdvancedContainer.kt\norg/anti_ad/mc/ipnext/inventory/AdvancedContainer$Companion\n*L\n1#1,502:1\n40#2:503\n43#2:510\n247#3:504\n239#3,4:505\n253#3:509\n1#4:511\n97#5:512\n95#5:513\n97#5:514\n95#5:515\n78#5:516\n88#5:518\n71#5:519\n66#5,7:520\n88#5:528\n71#5:529\n66#5,7:530\n88#5:538\n71#5:539\n66#5,7:540\n88#5:548\n71#5:549\n66#5,7:550\n86#5:557\n84#5:558\n88#5:559\n71#5:560\n66#5,7:561\n88#5:572\n71#5:573\n66#5,7:574\n88#5:589\n71#5:590\n66#5,7:591\n97#5:598\n88#5:599\n71#5:600\n66#5,7:601\n97#5:608\n1869#6:517\n1870#6:527\n1869#6:537\n1870#6:547\n1869#6,2:570\n131#7:568\n112#7:569\n131#7:581\n112#7:582\n154#8,6:583\n*S KotlinDebug\n*F\n+ 1 GeneralInventoryActions.kt\norg/anti_ad/mc/ipnext/inventory/GeneralInventoryActions\n*L\n116#1:503\n116#1:510\n116#1:504\n116#1:505,4\n116#1:509\n133#1:512\n133#1:513\n173#1:514\n173#1:515\n191#1:516\n198#1:518\n198#1:519\n198#1:520,7\n204#1:528\n204#1:529\n204#1:530,7\n209#1:538\n209#1:539\n209#1:540,7\n283#1:548\n283#1:549\n283#1:550,7\n287#1:557\n287#1:558\n291#1:559\n291#1:560\n291#1:561,7\n325#1:572\n325#1:573\n325#1:574,7\n412#1:589\n412#1:590\n412#1:591,7\n416#1:598\n426#1:599\n426#1:600\n426#1:601,7\n430#1:608\n196#1:517\n196#1:527\n208#1:537\n208#1:547\n296#1:570,2\n293#1:568\n293#1:569\n327#1:581\n327#1:582\n334#1:583,6\n*E\n"
)
public final class GeneralInventoryActions {
   @NotNull
   public static final GeneralInventoryActions INSTANCE = new GeneralInventoryActions();

   private GeneralInventoryActions() {
   }

   public final void doSort(@NotNull AbstractContainerMenu var1, boolean var2, boolean var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      GuiSettings var4 = GuiSettings.INSTANCE;
      INSTANCE.doSort(var4.getREGULAR_SORT_ORDER(), var4.getREGULAR_CUSTOM_RULE(), var4.getREGULAR_POST_ACTION(), var3, var1);
   }

   public final void doSortInColumns(@NotNull AbstractContainerMenu var1, boolean var2, boolean var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      GuiSettings var4 = GuiSettings.INSTANCE;
      INSTANCE.doSort(var4.getIN_COLUMNS_SORT_ORDER(), var4.getIN_COLUMNS_CUSTOM_RULE(), var4.getIN_COLUMNS_POST_ACTION(), var3, var1);
   }

   public final void doSortInRows(@NotNull AbstractContainerMenu var1, boolean var2, boolean var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      GuiSettings var4 = GuiSettings.INSTANCE;
      INSTANCE.doSort(var4.getIN_ROWS_SORT_ORDER(), var4.getIN_ROWS_CUSTOM_RULE(), var4.getIN_ROWS_POST_ACTION(), var3, var1);
   }

   private final void doSort(ConfigEnum var1, ConfigString var2, ConfigEnum var3, boolean var4, AbstractContainerMenu var5) {
      LogLevel var6 = LogLevel.WARN;
      LogBase var7 = (LogBase)Log.INSTANCE;
      Function1 var8 = GeneralInventoryActions$doSort$$inlined$listenLog$1.INSTANCE;
      Function1 var9 = new GeneralInventoryActions$doSort$$inlined$listenLog$2(var6, var8);
      var7.addLogListener(var9);
      InnerActions.INSTANCE.doSort(ConfigEnumsExtKt.rule((SortingMethodIndividual)var1.getValue(), var2.getValue()), (PostAction)var3.getValue(), var4, var5);
      var7.removeLogListener(var9);
   }

   public final void doMoveMatch() {
      Set var1;
      if (!(var1 = ContainerTypes.getTypes$default(ContainerTypes.INSTANCE, Vanilla.INSTANCE.container(), null, 2, null)).contains(ContainerType.CREATIVE)) {
         Iterable var10000 = var1;
         ContainerType[] var2;
         (var2 = new ContainerType[3])[0] = ContainerType.SORTABLE_STORAGE;
         var2[1] = ContainerType.NO_SORTING_STORAGE;
         var2[2] = ContainerType.CRAFTING;
         if (Kt_collectionKt.containsAny(var10000, SetsKt.setOf(var2))) {
            label34: {
               if (ModSettings.INSTANCE.getMOVE_ALL_AT_CURSOR().getBooleanValue()) {
                  Slot var4 = InventoryKt.vFocusedSlot();
                  label30:
                  if (var4 != null) {
                     Slot var3 = var4;
                     if (var4.container != null) {
                        Container var6 = var3.container;
                        Intrinsics.checkNotNullExpressionValue(var3.container, "");
                        if (var6 instanceof Inventory) {
                           var5 = false;
                           break label30;
                        }
                     }

                     var5 = true;
                  } else {
                     var5 = false;
                  }

                  if (var5) {
                     var7 = true;
                     break label34;
                  }
               }

               var7 = false;
            }

            if (var7) {
               doMoveMatch$default(this, true, false, 2, null);
            } else {
               doMoveMatch$default(this, false, false, 2, null);
            }
         }
      }
   }

   public final void doThrowOfType(@NotNull ItemStack var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      AbstractContainerMenu var2 = Vanilla.INSTANCE.container();
      Set var3;
      if (!(var3 = ContainerTypes.getTypes$default(ContainerTypes.INSTANCE, var2, null, 2, null)).contains(ContainerType.CREATIVE)) {
         Iterable var10000 = var3;
         ContainerType[] var4;
         (var4 = new ContainerType[3])[0] = ContainerType.SORTABLE_STORAGE;
         var4[1] = ContainerType.NO_SORTING_STORAGE;
         var4[2] = ContainerType.CRAFTING;
         if (Kt_collectionKt.containsAny(var10000, SetsKt.setOf(var4))) {
            this.executeThrow(true, var2, false, false, var1);
         }
      }
   }

   public final void doThrowMatch() {
      AbstractContainerMenu var1 = Vanilla.INSTANCE.container();
      Set var2;
      if (!(var2 = ContainerTypes.getTypes$default(ContainerTypes.INSTANCE, var1, null, 2, null)).contains(ContainerType.CREATIVE)) {
         Iterable var10000 = var2;
         ContainerType[] var5;
         (var5 = new ContainerType[3])[0] = ContainerType.SORTABLE_STORAGE;
         var5[1] = ContainerType.NO_SORTING_STORAGE;
         var5[2] = ContainerType.CRAFTING;
         if (Kt_collectionKt.containsAny(var10000, SetsKt.setOf(var5))) {
            label40: {
               if (ModSettings.INSTANCE.getMOVE_ALL_AT_CURSOR().getBooleanValue()) {
                  Slot var8 = InventoryKt.vFocusedSlot();
                  label36:
                  if (var8 != null) {
                     Slot var6 = var8;
                     if (var8.container != null) {
                        Container var10 = var6.container;
                        Intrinsics.checkNotNullExpressionValue(var6.container, "");
                        if (!(var10 instanceof Inventory)) {
                           var9 = true;
                           break label36;
                        }
                     }

                     var9 = false;
                  } else {
                     var9 = false;
                  }

                  if (var9) {
                     var11 = true;
                     break label40;
                  }
               }

               var11 = false;
            }

            boolean var7 = var11;
            boolean var3 = ModSettings.INSTANCE.getINCLUDE_HOTBAR_MODIFIER().isPressing() != ModSettings.INSTANCE.getALWAYS_INCLUDE_HOTBAR().getBooleanValue();
            boolean var4 = ModSettings.INSTANCE.getMOVE_ALL_MODIFIER().isPressing() != ModSettings.INSTANCE.getALWAYS_THROW_ALL().getBooleanValue();
            executeThrow$default(this, var3, var1, var7, var4, null, 16, null);
         }
      }
   }

   private final void executeThrow(boolean var1, AbstractContainerMenu var2, boolean var3, boolean var4, ItemStack var5) {
      AreaTypes var6 = AreaTypes.INSTANCE;
      AreaType var9 = (var1 ? var6.getPlayerStorage().plus(var6.getPlayerHotbar()) : var6.getPlayerStorage()).minus(var6.getLockedSlots());
      AreaType var7 = var6.getItemStorage();
      NonNullList var10000 = var2.slots;
      Intrinsics.checkNotNullExpressionValue(var2.slots, "");
      List var8 = (List)var10000;
      ItemArea var26 = (var3 ? var7 : var9).minus(var6.getDisabled(var2)).getItemArea(var2, var8);
      Map var34;
      if (var4) {
         Map var13 = new LinkedHashMap();
         Iterator var10 = var26.getSlotIndices().iterator();

         while (var10.hasNext()) {
            var4 = ((Number)var10.next()).intValue();
            Slot var16;
            net.minecraft.world.item.ItemStack var31 = (var16 = (Slot)var8.get(var4)).getItem();
            Intrinsics.checkNotNullExpressionValue(var31, "");
            net.minecraft.world.item.ItemStack var22 = var31;
            ItemStack var32;
            if (var31.isEmpty()) {
               var32 = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
            } else {
               ItemStack$Companion var33 = ItemStack.Companion;
               net.minecraft.world.item.ItemStack var27 = var22;
               Item var10003 = var27.getItem();
               Intrinsics.checkNotNullExpressionValue(var10003, "");
               DataComponentMap var10004 = var27.getComponents();
               Intrinsics.checkNotNull(var10004);
               var32 = var33.invoke(
                  new ItemType(
                     var10003,
                     (PatchedDataComponentMap)var10004,
                     var27.getComponentsPatch(),
                     new VanillaAccessorsKt$(itemType)$1(var27),
                     false,
                     false,
                     null,
                     112,
                     null
                  ),
                  var22.getCount()
               );
            }

            if (!ItemStackExtensionsKt.isEmpty(var32)) {
               Integer var28 = var4;
               var13.put(var28, var16);
            }
         }

         var34 = var13;
      } else {
         ItemStack var35 = var5;
         if (var5 == null) {
            Slot var36 = InventoryKt.vFocusedSlot();
            if (var36 != null) {
               net.minecraft.world.item.ItemStack var37 = var36.getItem();
               Intrinsics.checkNotNullExpressionValue(var37, "");
               net.minecraft.world.item.ItemStack var20 = var37;
               if (var37.isEmpty()) {
                  var35 = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
               } else {
                  ItemStack$Companion var38 = ItemStack.Companion;
                  net.minecraft.world.item.ItemStack var17 = var20;
                  Item var42 = var17.getItem();
                  Intrinsics.checkNotNullExpressionValue(var42, "");
                  DataComponentMap var44 = var17.getComponents();
                  Intrinsics.checkNotNull(var44);
                  var35 = var38.invoke(
                     new ItemType(
                        var42,
                        (PatchedDataComponentMap)var44,
                        var17.getComponentsPatch(),
                        new VanillaAccessorsKt$(itemType)$1(var17),
                        false,
                        false,
                        null,
                        112,
                        null
                     ),
                     var20.getCount()
                  );
               }
            } else {
               var35 = null;
            }

            if (var35 == null) {
               var35 = InventoryKt.vCursorStack();
            }
         }

         ItemStack var14 = var35;
         var35.getItemType().setIgnoreDurability(ModSettings.INSTANCE.getIGNORE_DURABILITY().getBooleanValue());
         Map var11 = new LinkedHashMap();
         if (!ItemStackExtensionsKt.isEmpty(var14)) {
            Iterator var18 = var26.getSlotIndices().iterator();

            while (var18.hasNext()) {
               var4 = ((Number)var18.next()).intValue();
               net.minecraft.world.item.ItemStack var39 = ((Slot)var8.get(var4)).getItem();
               Intrinsics.checkNotNullExpressionValue(var39, "");
               net.minecraft.world.item.ItemStack var23 = var39;
               ItemStack var40;
               if (var39.isEmpty()) {
                  var40 = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
               } else {
                  ItemStack$Companion var41 = ItemStack.Companion;
                  net.minecraft.world.item.ItemStack var29 = var23;
                  Item var43 = var29.getItem();
                  Intrinsics.checkNotNullExpressionValue(var43, "");
                  DataComponentMap var45 = var29.getComponents();
                  Intrinsics.checkNotNull(var45);
                  var40 = var41.invoke(
                     new ItemType(
                        var43,
                        (PatchedDataComponentMap)var45,
                        var29.getComponentsPatch(),
                        new VanillaAccessorsKt$(itemType)$1(var29),
                        false,
                        false,
                        null,
                        112,
                        null
                     ),
                     var23.getCount()
                  );
               }

               var5 = var40;
               var40.getItemType().setIgnoreDurability(ModSettings.INSTANCE.getIGNORE_DURABILITY().getBooleanValue());
               ItemStack var30 = var5;
               if (!ItemStackExtensionsKt.isEmpty(var5) && Intrinsics.areEqual(var30.getItemType(), var14.getItemType())) {
                  Integer var25 = var4;
                  var11.put(var25, var8.get(var4));
               }
            }
         }

         var34 = var11;
      }

      Map var12 = var34;
      if (!var34.isEmpty()) {
         int var15 = ModSettings.INSTANCE.getADD_INTERVAL_BETWEEN_CLICKS().getBooleanValue()
            ? ModSettings.INSTANCE.getINTERVAL_BETWEEN_CLICKS_MS().getIntegerValue()
            : 0;
         ContainerClicker.INSTANCE.executeQClicks(var12, var15);
      }
   }

   public final void doMoveMatch(boolean var1, boolean var2) {
      AbstractContainerMenu var7 = Vanilla.INSTANCE.container();
      Set var3;
      if (!(var3 = ContainerTypes.getTypes$default(ContainerTypes.INSTANCE, var7, null, 2, null)).contains(ContainerType.CREATIVE)) {
         if (var3.contains(ContainerType.CRAFTING)) {
            this.doMoveMatchCrafting(var7);
         } else {
            boolean var8 = ModSettings.INSTANCE.getINCLUDE_HOTBAR_MODIFIER().isPressing() != ModSettings.INSTANCE.getALWAYS_INCLUDE_HOTBAR().getBooleanValue();
            boolean var4 = ModSettings.INSTANCE.getMOVE_ALL_MODIFIER().isPressing() != ModSettings.INSTANCE.getALWAYS_MOVE_ALL().getBooleanValue();
            boolean var5 = ModSettings.INSTANCE.getMOVE_FOCUS_MACH_MODIFIER().isPressing() || !ItemStackExtensionsKt.isEmpty(InventoryKt.vCursorStack());
            boolean var6 = ModSettings.INSTANCE.getMOVE_JUST_REFILL_MODIFIER().isPressing();
            AdvancedContainer$Companion.tracker$default(AdvancedContainer.Companion, false, false, GeneralInventoryActions::doMoveMatch$lambda$11, 3, null);
         }
      }
   }

   public final void doMoveMatchCrafting(@NotNull AbstractContainerMenu var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      boolean var2 = ModSettings.INSTANCE.getINCLUDE_HOTBAR_MODIFIER().isPressing();
      AdvancedContainer$Companion.tracker$default(AdvancedContainer.Companion, false, false, GeneralInventoryActions::doMoveMatchCrafting$lambda$13, 3, null);
   }

   public final void dumpItemNbt() {
      ItemStack var5;
      label26: {
         Slot var10000 = InventoryKt.vFocusedSlot();
         if (var10000 != null) {
            net.minecraft.world.item.ItemStack var4 = var10000.getItem();
            Intrinsics.checkNotNullExpressionValue(var4, "");
            net.minecraft.world.item.ItemStack var1 = var4;
            if (var4.isEmpty()) {
               var5 = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
            } else {
               ItemStack$Companion var6 = ItemStack.Companion;
               net.minecraft.world.item.ItemStack var2 = var1;
               Item var10003 = var2.getItem();
               Intrinsics.checkNotNullExpressionValue(var10003, "");
               DataComponentMap var10004 = var2.getComponents();
               Intrinsics.checkNotNull(var10004);
               var5 = var6.invoke(
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

            if (var5 != null) {
               break label26;
            }
         }

         var5 = InventoryKt.vCursorStack();
      }

      String var3 = ItemTypeExtensionsKt.fullItemInfoAsJson(var5.getItemType());
      org.anti_ad.mc.ipnext.Log.INSTANCE.info(var3);
      TellPlayer var7 = TellPlayer.INSTANCE;
      Slot var10001 = InventoryKt.vFocusedSlot();
      Integer var8 = var10001 != null ? var10001.getSlotIndex() : null;
      Slot var10002 = InventoryKt.vFocusedSlot();
      var7.chat("Slot: " + var8 + ", " + (var10002 != null ? var10002.index : null) + " " + var3);
   }

   public final void nbtToClipboard() {
      ItemStack var16;
      label38: {
         Slot var10000 = InventoryKt.vFocusedSlot();
         if (var10000 != null) {
            net.minecraft.world.item.ItemStack var15 = var10000.getItem();
            Intrinsics.checkNotNullExpressionValue(var15, "");
            net.minecraft.world.item.ItemStack var1 = var15;
            if (var15.isEmpty()) {
               var16 = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
            } else {
               ItemStack$Companion var17 = ItemStack.Companion;
               net.minecraft.world.item.ItemStack var4 = var1;
               Item var10003 = var4.getItem();
               Intrinsics.checkNotNullExpressionValue(var10003, "");
               DataComponentMap var10004 = var4.getComponents();
               Intrinsics.checkNotNull(var10004);
               var16 = var17.invoke(
                  new ItemType(
                     var10003,
                     (PatchedDataComponentMap)var10004,
                     var4.getComponentsPatch(),
                     new VanillaAccessorsKt$(itemType)$1(var4),
                     false,
                     false,
                     null,
                     112,
                     null
                  ),
                  var1.getCount()
               );
            }

            if (var16 != null) {
               break label38;
            }
         }

         var16 = InventoryKt.vCursorStack();
      }

      ItemStack var6 = var16;
      if (!Intrinsics.areEqual(var16, ItemStackExtensionsKt.getEMPTY(ItemStack.Companion))) {
         ItemType var2 = var6.getItemType();
         ResourceLocation var18 = BuiltInRegistries.ITEM.getKey(var2.getItem());
         Intrinsics.checkNotNullExpressionValue(var18, "");
         String var19 = var18.toString();
         Intrinsics.checkNotNullExpressionValue(var19, "");
         String var8 = var19 + " => [\n";
         PatchedDataComponentMap var3;
         if ((var3 = var6.getItemType().getTag()) != null) {
            Set var20 = ComponentExKt.get_types_/* $VF was: get(types) */(var3);
            Intrinsics.checkNotNullExpressionValue(var20, "");

            for (DataComponentType var10 : var20) {
               Object var5;
               if ((var5 = (Tag)var3.get(var10)) != null) {
                  ComponentUtils var21 = ComponentUtils.INSTANCE;
                  Optional var10002 = Optional.of(var5);
                  Intrinsics.checkNotNullExpressionValue(var10002, "");
                  Tag var22 = var21.toFullNbtOrNull(var10, var10002);
                  if (var22 != null) {
                     var5 = var22;
                     String var11 = var10 + " ->\n";
                     String var9 = var8 + "\t" + var11;
                     String var12 = "\t" + StringsKt.repeat(" ", var11.length()) + "\t";
                     String var23 = NbtExKt.NbtHelper_toFormattedString(var5, true);
                     Intrinsics.checkNotNullExpressionValue(var23, "");
                     String var13 = SequencesKt.joinToString$default(StringsKt.lineSequence(var23), "\n" + var12, var12, null, 0, null, null, 60, null);
                     var8 = var9 + var13;
                  }
               }

               var8 = var8 + "\n";
            }

            Vanilla.INSTANCE.setClipboard(var8);
            TellPlayer.INSTANCE.chat("Copy Components.");
         }
      }
   }

   public final void idToClipboard() {
      ItemStack var6;
      label20: {
         Slot var10000 = InventoryKt.vFocusedSlot();
         if (var10000 != null) {
            net.minecraft.world.item.ItemStack var5 = var10000.getItem();
            Intrinsics.checkNotNullExpressionValue(var5, "");
            net.minecraft.world.item.ItemStack var1 = var5;
            if (var5.isEmpty()) {
               var6 = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
            } else {
               ItemStack$Companion var7 = ItemStack.Companion;
               net.minecraft.world.item.ItemStack var2 = var1;
               Item var10003 = var2.getItem();
               Intrinsics.checkNotNullExpressionValue(var10003, "");
               DataComponentMap var10004 = var2.getComponents();
               Intrinsics.checkNotNull(var10004);
               var6 = var7.invoke(
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

            if (var6 != null) {
               break label20;
            }
         }

         var6 = InventoryKt.vCursorStack();
      }

      ItemStack var3 = var6;
      if (!Intrinsics.areEqual(var6, ItemStackExtensionsKt.getEMPTY(ItemStack.Companion))) {
         Vanilla var8 = Vanilla.INSTANCE;
         ItemType var4 = var3.getItemType();
         ResourceLocation var10001 = BuiltInRegistries.ITEM.getKey(var4.getItem());
         Intrinsics.checkNotNullExpressionValue(var10001, "");
         String var9 = var10001.toString();
         Intrinsics.checkNotNullExpressionValue(var9, "");
         var8.setClipboard(var9 + "\n");
         TellPlayer.INSTANCE.chat("Copy Item ID.");
      }
   }

   public final void cleanCursor() {
      if (!ItemStackExtensionsKt.isEmpty(InventoryKt.vCursorStack())) {
         AdvancedContainer$Companion var1 = AdvancedContainer.Companion;
         if (VanillaUtil.INSTANCE.inGame()) {
            AdvancedContainer var2;
            (var2 = var1.create()).cleanCursor();
            var2.arrange(true);
         }
      }
   }

   public final void handleCloseContainer() {
      this.cleanCursor();
      InnerActions.INSTANCE.cleanTempSlotsForClosing();
   }

   public final void scrollToPlayer(@NotNull ItemStack var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      var1.getItemType();
      if (this.isEnabled(var1)) {
         if (ScrollSettings.INSTANCE.getSCROLL_FULL_STACK().isPressing()) {
            if (ScrollSettings.INSTANCE.getSCROLL_THROW().isPressing()) {
               ScrollingUtils.scrollFullStackThrow$default(ScrollingUtils.INSTANCE, null, false, 3, null);
               return;
            }

            if (ScrollSettings.INSTANCE.getSCROLL_SPREAD().isPressing()) {
               ScrollingUtils.scrollFullStackSpread$default(ScrollingUtils.INSTANCE, ScrollDirection.TO_PLAYER, false, 2, null);
               return;
            }

            if (ScrollSettings.INSTANCE.getSCROLL_LEAVE_LAST().isPressing()) {
               ScrollingUtils.scrollFullStackLeaveLast$default(ScrollingUtils.INSTANCE, ScrollDirection.TO_PLAYER, false, 2, null);
               return;
            }

            ScrollingUtils.scrollFullStack$default(ScrollingUtils.INSTANCE, ScrollDirection.TO_PLAYER, false, 2, null);
            return;
         }

         if (ScrollSettings.INSTANCE.getSCROLL_THROW().isPressing()) {
            ScrollingUtils.scrollSingleItemThrow$default(ScrollingUtils.INSTANCE, null, false, 3, null);
            return;
         }

         if (ScrollSettings.INSTANCE.getSCROLL_SPREAD().isPressing()) {
            ScrollingUtils.scrollSingleSpread$default(ScrollingUtils.INSTANCE, ScrollDirection.TO_PLAYER, false, 2, null);
            return;
         }

         if (ScrollSettings.INSTANCE.getSCROLL_LEAVE_LAST().isPressing()) {
            ScrollingUtils.scrollSingleItemLeaveLast$default(ScrollingUtils.INSTANCE, ScrollDirection.TO_PLAYER, false, 2, null);
            return;
         }

         ScrollingUtils.scrollSingleItem$default(ScrollingUtils.INSTANCE, ScrollDirection.TO_PLAYER, false, 2, null);
      }
   }

   public final boolean isEnabled(@NotNull ItemStack var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      ItemType var3 = var1.getItemType();
      boolean var2 = ScrollSettings.INSTANCE.getTEMP_DISABLE().isPressing();
      boolean var4;
      return (
               var4 = ScrollSettings.INSTANCE.getDISABLE_FOR_NON_EMPTY_BUNDLES().getBooleanValue()
                  && ItemTypeExtensionsKt.get_isBundleAndHasItems_/* $VF was: get(isBundleAndHasItems) */(var3)
            )
            && var2
         || !var4 && !var2;
   }

   public final void scrollToChest(@NotNull ItemStack var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (this.isEnabled(var1)) {
         if (ScrollSettings.INSTANCE.getSCROLL_FULL_STACK().isPressing()) {
            if (ScrollSettings.INSTANCE.getSCROLL_THROW().isPressing()) {
               ScrollingUtils.scrollFullStackThrow$default(ScrollingUtils.INSTANCE, null, false, 3, null);
               return;
            }

            if (ScrollSettings.INSTANCE.getSCROLL_SPREAD().isPressing()) {
               ScrollingUtils.scrollFullStackSpread$default(ScrollingUtils.INSTANCE, ScrollDirection.TO_CHEST, false, 2, null);
               return;
            }

            if (ScrollSettings.INSTANCE.getSCROLL_LEAVE_LAST().isPressing()) {
               ScrollingUtils.scrollFullStackLeaveLast$default(ScrollingUtils.INSTANCE, ScrollDirection.TO_CHEST, false, 2, null);
               return;
            }

            ScrollingUtils.scrollFullStack$default(ScrollingUtils.INSTANCE, ScrollDirection.TO_CHEST, false, 2, null);
            return;
         }

         if (ScrollSettings.INSTANCE.getSCROLL_THROW().isPressing()) {
            ScrollingUtils.scrollSingleItemThrow$default(ScrollingUtils.INSTANCE, null, false, 3, null);
            return;
         }

         if (ScrollSettings.INSTANCE.getSCROLL_SPREAD().isPressing()) {
            ScrollingUtils.scrollSingleSpread$default(ScrollingUtils.INSTANCE, ScrollDirection.TO_CHEST, false, 2, null);
            return;
         }

         if (ScrollSettings.INSTANCE.getSCROLL_LEAVE_LAST().isPressing()) {
            ScrollingUtils.scrollSingleItemLeaveLast$default(ScrollingUtils.INSTANCE, ScrollDirection.TO_CHEST, false, 2, null);
            return;
         }

         ScrollingUtils.scrollSingleItem$default(ScrollingUtils.INSTANCE, ScrollDirection.TO_CHEST, false, 2, null);
      }
   }

   public final void scrollUp() {
      ItemStack var5;
      label29: {
         Slot var10000 = InventoryKt.vFocusedSlot();
         if (var10000 != null) {
            net.minecraft.world.item.ItemStack var4 = var10000.getItem();
            Intrinsics.checkNotNullExpressionValue(var4, "");
            net.minecraft.world.item.ItemStack var1 = var4;
            if (var4.isEmpty()) {
               var5 = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
            } else {
               ItemStack$Companion var6 = ItemStack.Companion;
               net.minecraft.world.item.ItemStack var2 = var1;
               Item var10003 = var2.getItem();
               Intrinsics.checkNotNullExpressionValue(var10003, "");
               DataComponentMap var10004 = var2.getComponents();
               Intrinsics.checkNotNull(var10004);
               var5 = var6.invoke(
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

            if (var5 != null) {
               break label29;
            }
         }

         var5 = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
      }

      ItemStack var3 = var5;
      if (ScrollSettings.INSTANCE.getDIRECTION_ABSOLUTE().getBooleanValue()) {
         this.scrollToChest(var3);
      } else {
         Slot var7 = InventoryKt.vFocusedSlot();
         if ((var7 != null ? var7.container : null) instanceof Inventory) {
            this.scrollToChest(var3);
         } else {
            this.scrollToPlayer(var3);
         }
      }
   }

   public final void scrollDown() {
      ItemStack var5;
      label29: {
         Slot var10000 = InventoryKt.vFocusedSlot();
         if (var10000 != null) {
            net.minecraft.world.item.ItemStack var4 = var10000.getItem();
            Intrinsics.checkNotNullExpressionValue(var4, "");
            net.minecraft.world.item.ItemStack var1 = var4;
            if (var4.isEmpty()) {
               var5 = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
            } else {
               ItemStack$Companion var6 = ItemStack.Companion;
               net.minecraft.world.item.ItemStack var2 = var1;
               Item var10003 = var2.getItem();
               Intrinsics.checkNotNullExpressionValue(var10003, "");
               DataComponentMap var10004 = var2.getComponents();
               Intrinsics.checkNotNull(var10004);
               var5 = var6.invoke(
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

            if (var5 != null) {
               break label29;
            }
         }

         var5 = ItemStackExtensionsKt.getEMPTY(ItemStack.Companion);
      }

      ItemStack var3 = var5;
      if (ScrollSettings.INSTANCE.getDIRECTION_ABSOLUTE().getBooleanValue()) {
         this.scrollToPlayer(var3);
      } else {
         Slot var7 = InventoryKt.vFocusedSlot();
         if ((var7 != null ? var7.container : null) instanceof Inventory) {
            this.scrollToPlayer(var3);
         } else {
            this.scrollToChest(var3);
         }
      }
   }

   private static final Unit doMoveMatch$lambda$11(
      boolean var0, boolean var1, boolean var2, AbstractContainerMenu var3, boolean var4, boolean var5, AdvancedContainer$TrackerDsl var6
   ) {
      Intrinsics.checkNotNullParameter(var6, "");
      AreaTypes var7 = AreaTypes.INSTANCE;
      AreaType var8 = (!var0 && (!var1 || !var2) ? var7.getPlayerStorage() : var7.getPlayerStorage().plus(var7.getPlayerHotbar()).plus(var7.getPlayerOffhand()))
         .minus(var7.getLockedSlots())
         .minus(var7.getDisabled(var3));
      AreaType var10 = var7.getItemStorage().minus(var7.getDisabled(var3));
      MutableSubTracker var11 = var6.getAsSubTracker(var6.get(var1 ? var10 : var8));
      MutableSubTracker var9 = var6.getAsSubTracker(var6.get(var1 ? var8 : var10));
      if (var4) {
         SubTrackerActionsKt.moveAllTo(var11, var9);
      } else if (var5) {
         SubTrackerActionsKt.moveFocusMatchTo(var11, var9);
      } else if (var2) {
         SubTrackerActionsKt.refillStacksTo(var11, var9);
      } else {
         SubTrackerActionsKt.moveMatchTo(var11, var9);
      }

      return Unit.INSTANCE;
   }

   private static final Unit doMoveMatchCrafting$lambda$13(boolean var0, AbstractContainerMenu var1, AdvancedContainer$TrackerDsl var2) {
      Intrinsics.checkNotNullParameter(var2, "");
      AreaTypes var3 = AreaTypes.INSTANCE;
      AreaType var5 = (var0 ? var3.getPlayerStorage().plus(var3.getPlayerHotbar()).plus(var3.getPlayerOffhand()) : var3.getPlayerStorage())
         .minus(var3.getLockedSlots());
      AreaType var4 = var3.getCraftingIngredient();
      MutableSubTracker var6 = var2.getAsSubTracker(var2.get(var5.minus(var3.getDisabled(var1))));
      MutableSubTracker var7 = var2.getAsSubTracker(var2.get(var4));
      SubTrackerActionsKt.moveMatchCraftingTo(var6, var7);
      return Unit.INSTANCE;
   }
}
