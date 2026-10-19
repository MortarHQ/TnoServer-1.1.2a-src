package org.anti_ad.mc.ipn.features.scrolling;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.IntIterator;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.functions.Function7;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.Ref.IntRef;
import kotlin.ranges.RangesKt;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.anti_ad.mc.common.extensions.Kt_collectionKt;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.config.LockedSlotsSettings;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.config.ScrollSettings;
import org.anti_ad.mc.ipnext.ingame.InventoryKt;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt$(itemType)$1;
import org.anti_ad.mc.ipnext.inventory.AreaType;
import org.anti_ad.mc.ipnext.inventory.AreaTypes;
import org.anti_ad.mc.ipnext.inventory.ContainerClicker;
import org.anti_ad.mc.ipnext.inventory.ContainerType;
import org.anti_ad.mc.ipnext.inventory.ContainerTypes;
import org.anti_ad.mc.ipnext.inventory.ItemArea;
import org.anti_ad.mc.ipnext.item.ItemStack$Companion;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.ItemTypeExtensionsKt;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nScrollingUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ScrollingUtils.kt\norg/anti_ad/mc/ipn/features/scrolling/ScrollingUtils\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 kt_common.kt\norg/anti_ad/mc/common/extensions/Kt_commonKt\n+ 5 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 6 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,713:1\n58#1,4:724\n58#1,4:728\n58#1,4:732\n58#1,4:736\n58#1,4:740\n58#1,4:744\n58#1,4:748\n58#1,4:752\n78#2:714\n88#2:715\n71#2:716\n66#2,7:717\n88#2:757\n71#2:758\n66#2,7:759\n84#2:766\n84#2:767\n88#2:769\n71#2:770\n66#2,7:771\n88#2:779\n71#2:780\n66#2,7:781\n84#2:788\n84#2:789\n88#2:791\n71#2:792\n66#2,7:793\n84#2:800\n88#2:802\n71#2:803\n66#2,7:804\n84#2:811\n84#2:812\n88#2:815\n71#2:816\n66#2,7:817\n88#2:824\n71#2:825\n66#2,7:826\n84#2:833\n88#2:834\n71#2:835\n66#2,7:836\n84#2:843\n88#2:846\n71#2:847\n66#2,7:848\n88#2:855\n71#2:856\n66#2,7:857\n84#2:864\n88#2:867\n71#2:868\n66#2,7:869\n88#2:876\n71#2:877\n66#2,7:878\n84#2:885\n88#2:891\n71#2:892\n66#2,7:893\n88#2:900\n71#2:901\n66#2,7:902\n88#2:909\n71#2:910\n66#2,7:911\n88#2:918\n71#2:919\n66#2,7:920\n88#2:936\n71#2:937\n66#2,7:938\n88#2:945\n71#2:946\n66#2,7:947\n1869#3:756\n1870#3:768\n1869#3:778\n1870#3:790\n1869#3:801\n1870#3:813\n1869#3:814\n1870#3:844\n1869#3:845\n1870#3:865\n1869#3:866\n1870#3:886\n1869#3,2:889\n130#4:887\n1#5:888\n98#6,9:927\n98#6,9:954\n*S KotlinDebug\n*F\n+ 1 ScrollingUtils.kt\norg/anti_ad/mc/ipn/features/scrolling/ScrollingUtils\n*L\n191#1:724,4\n227#1:728,4\n257#1:732,4\n316#1:736,4\n338#1:740,4\n363#1:744,4\n397#1:748,4\n433#1:752,4\n85#1:714\n119#1:715\n119#1:716\n119#1:717,7\n571#1:757\n571#1:758\n571#1:759,7\n573#1:766\n576#1:767\n593#1:769\n593#1:770\n593#1:771,7\n599#1:779\n599#1:780\n599#1:781,7\n601#1:788\n604#1:789\n610#1:791\n610#1:792\n610#1:793,7\n612#1:800\n628#1:802\n628#1:803\n628#1:804,7\n630#1:811\n633#1:812\n654#1:815\n654#1:816\n654#1:817,7\n655#1:824\n655#1:825\n655#1:826,7\n657#1:833\n658#1:834\n658#1:835\n658#1:836,7\n659#1:843\n681#1:846\n681#1:847\n681#1:848,7\n682#1:855\n682#1:856\n682#1:857,7\n683#1:864\n701#1:867\n701#1:868\n701#1:869,7\n702#1:876\n702#1:877\n702#1:878,7\n703#1:885\n262#1:891\n262#1:892\n262#1:893,7\n344#1:900\n344#1:901\n344#1:902,7\n370#1:909\n370#1:910\n370#1:911,7\n368#1:918\n368#1:919\n368#1:920,7\n404#1:936\n404#1:937\n404#1:938,7\n402#1:945\n402#1:946\n402#1:947,7\n570#1:756\n570#1:768\n597#1:778\n597#1:790\n626#1:801\n626#1:813\n652#1:814\n652#1:844\n679#1:845\n679#1:865\n699#1:866\n699#1:886\n290#1:889,2\n278#1:887\n278#1:888\n368#1:927,9\n402#1:954,9\n*E\n"
)
public final class ScrollingUtils {
   @NotNull
   public static final ScrollingUtils INSTANCE = new ScrollingUtils();

   private ScrollingUtils() {
   }

   private final boolean getDoIncludeHotbar() {
      return ModSettings.INSTANCE.getALWAYS_INCLUDE_HOTBAR().getBooleanValue()
         ? !ModSettings.INSTANCE.getINCLUDE_HOTBAR_MODIFIER().isPressing()
         : ModSettings.INSTANCE.getINCLUDE_HOTBAR_MODIFIER().isPressing();
   }

   private final void withEnvironmentDo(ScrollDirection var1, boolean var2, Function7 var3) {
      AbstractContainerMenu var4 = Vanilla.INSTANCE.container();
      Set var5;
      if (!(var5 = ContainerTypes.getTypes$default(ContainerTypes.INSTANCE, var4, null, 2, null)).contains(ContainerType.CREATIVE)) {
         List var10000 = var5;
         ContainerType[] var12;
         (var12 = new ContainerType[3])[0] = ContainerType.SORTABLE_STORAGE;
         var12[1] = ContainerType.NO_SORTING_STORAGE;
         var12[2] = ContainerType.CRAFTING;
         if (Kt_collectionKt.containsAny(var10000, SetsKt.setOf(var12))) {
            ItemType var13 = InventoryKt.vCursorStack().getItemType();
            AreaTypes var6 = AreaTypes.INSTANCE;
            Intrinsics.checkNotNullExpressionValue(var4.slots, "");
            List var9 = var10000;
            AreaType var7 = var6.getItemStorage();
            AreaType var8 = var6.getPlayerStorage().plus(var6.getPlayerHotbar()).plus(var6.getPlayerOffhand());
            AreaType var11 = (var2 ? var8 : var6.getPlayerStorage()).minus(var6.getLockedSlots());

            AreaType var10 = switch (var1) {
               case TO_PLAYER -> var11.minus(var6.getDisabled(var4));
               case TO_CHEST -> var7.minus(var6.getDisabled(var4));
               case BOTH -> var7.minus(var6.getDisabled(var4));
               default -> throw new NoWhenBranchMatchedException();
            };
            var3.invoke(var6, var13, var10, var9, var11, var7, var8);
         }
      }
   }

   private final void withFocusedItemFullStackDo(
      AreaTypes var1, ScrollDirection var2, Slot var3, AreaType var4, List var5, AreaType var6, AreaType var7, Function5 var8
   ) {
      int var9 = var5.indexOf(var3);
      ItemStack var10000 = var3.getItem();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      ItemStack var10 = var10000;
      org.anti_ad.mc.ipnext.item.ItemStack var16;
      if (var10000.isEmpty()) {
         var16 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
      } else {
         ItemStack$Companion var17 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
         ItemStack var12 = var10;
         Item var10003 = var12.getItem();
         Intrinsics.checkNotNullExpressionValue(var10003, "");
         DataComponentMap var10004 = var12.getComponents();
         Intrinsics.checkNotNull(var10004);
         var16 = var17.invoke(
            new ItemType(
               var10003,
               (PatchedDataComponentMap)var10004,
               var12.getComponentsPatch(),
               new VanillaAccessorsKt$(itemType)$1(var12),
               false,
               false,
               null,
               112,
               null
            ),
            var10.getCount()
         );
      }

      ItemType var13 = ItemType.copy$default(
         var16.getItemType(), null, null, null, null, ModSettings.INSTANCE.getIGNORE_DURABILITY().getBooleanValue(), false, null, 111, null
      );
      AbstractContainerMenu var14 = Vanilla.INSTANCE.container();

      ItemArea var11 = switch (var2) {
         case TO_PLAYER -> var7.getItemArea(var14, var5);
         case TO_CHEST -> var6.getItemArea(var14, var5);
         case BOTH -> var6.plus(var7).getItemArea(var14, var5);
         default -> throw new NoWhenBranchMatchedException();
      };
      ItemArea var15 = var4.getItemArea(var14, var5);
      var8.invoke(var1, var15, var11, var13, var9);
   }

   private final void withFocusedStackSingleItemDo(
      AreaTypes var1, ScrollDirection var2, Slot var3, List var4, AreaType var5, AreaType var6, AreaType var7, AreaType var8, Function4 var9
   ) {
      AbstractContainerMenu var10 = Vanilla.INSTANCE.container();
      int var11 = var4.indexOf(var3);

      Pair var10000 = switch (var2) {
         case TO_PLAYER -> !LockedSlotsSettings.INSTANCE.getLOCK_SLOTS_DISABLE_USER_INTERACTION().getBooleanValue()
               && var5.getItemArea(var10, var4).getSlotIndices().contains(var11)
            ? new Pair(var8.getItemArea(var10, var4), var6.plus(var1.getFocusedSlot()).getItemArea(var10, var4))
            : new Pair(var8.getItemArea(var10, var4), var6.getItemArea(var10, var4));
         case TO_CHEST -> !LockedSlotsSettings.INSTANCE.getLOCK_SLOTS_DISABLE_USER_INTERACTION().getBooleanValue()
               && var5.getItemArea(var10, var4).getSlotIndices().contains(var11)
            ? new Pair(var7.plus(var1.getFocusedSlot()).getItemArea(var10, var4), var6.getItemArea(var10, var4))
            : new Pair(var7.getItemArea(var10, var4), var6.getItemArea(var10, var4));
         case BOTH -> !LockedSlotsSettings.INSTANCE.getLOCK_SLOTS_DISABLE_USER_INTERACTION().getBooleanValue()
               && var5.getItemArea(var10, var4).getSlotIndices().contains(var11)
            ? new Pair(var7.plus(var1.getFocusedSlot()).plus(var8).getItemArea(var10, var4), var6.getItemArea(var10, var4))
            : new Pair(var7.plus(var8).getItemArea(var10, var4), var6.getItemArea(var10, var4));
         default -> throw new NoWhenBranchMatchedException();
      };
      Pair var12 = var10000;
      ItemArea var14 = (ItemArea)var10000.component1();
      ItemArea var13 = (ItemArea)var12.component2();
      var9.invoke(var13, var14, var3, var11);
   }

   public final void scrollFullStack(@NotNull ScrollDirection var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.withEnvironmentDo(var1, var2, ScrollingUtils::scrollFullStack$lambda$3);
   }

   private final void doDefaultAction(AreaTypes var1, ScrollDirection var2, Slot var3, AreaType var4, List var5, AreaType var6, AreaType var7) {
      this.withFocusedItemFullStackDo(var1, var2, var3, var4, var5, var6, var7, ScrollingUtils::doDefaultAction$lambda$5);
   }

   public final void scrollFullStackLeaveLast(@NotNull ScrollDirection var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.withEnvironmentDo(var1, var2, ScrollingUtils::scrollFullStackLeaveLast$lambda$11);
   }

   public final void scrollFullStackSpread(@NotNull ScrollDirection var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      IntRef var3;
      (var3 = new IntRef()).element = 65;
      this.withEnvironmentDo(var1, var2, ScrollingUtils::scrollFullStackSpread$lambda$21);
   }

   public final void scrollFullStackThrow(@NotNull ScrollDirection var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.withEnvironmentDo(var1, var2, ScrollingUtils::scrollFullStackThrow$lambda$25);
   }

   public final void scrollSingleItemThrow(@NotNull ScrollDirection var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.withEnvironmentDo(var1, var2, ScrollingUtils::scrollSingleItemThrow$lambda$29);
   }

   public final void scrollSingleItem(@NotNull ScrollDirection var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.withEnvironmentDo(var1, var2, ScrollingUtils::scrollSingleItem$lambda$34);
   }

   public final void scrollSingleItemLeaveLast(@NotNull ScrollDirection var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.withEnvironmentDo(var1, var2, ScrollingUtils::scrollSingleItemLeaveLast$lambda$40);
   }

   public final void scrollSingleSpread(@NotNull ScrollDirection var1, boolean var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      IntRef var3;
      (var3 = new IntRef()).element = 65;
      this.withEnvironmentDo(var1, var2, ScrollingUtils::scrollSingleSpread$lambda$46);
   }

   private final void pickUpNewStackIfEmpty(ItemType var1, ScrollDirection var2, AreaType var3, AreaType var4, List var5) {
      ItemType var6 = ItemType.copy$default(var1, null, null, null, null, false, false, null, 127, null);
      AbstractContainerMenu var7 = Vanilla.INSTANCE.container();
      if (ItemTypeExtensionsKt.isEmpty(InventoryKt.vCursorStack().getItemType())) {
         ItemArea var8 = (switch (var2) {
            case TO_PLAYER -> var4;
            case TO_CHEST -> var3;
            case BOTH -> var3.plus(var4);
            default -> throw new NoWhenBranchMatchedException();
         }).getItemArea(var7, var5);
         this.findSourceAndDo(var8, var5, var6, ScrollingUtils::pickUpNewStackIfEmpty$lambda$47);
      }
   }

   private final void pickUpNewStackLeaveLastIfEmpty(ItemType var1, ScrollDirection var2, AreaType var3, AreaType var4, List var5) {
      ItemType var6 = ItemType.copy$default(var1, null, null, null, null, false, false, null, 127, null);
      AbstractContainerMenu var7 = Vanilla.INSTANCE.container();
      if (ItemTypeExtensionsKt.isEmpty(InventoryKt.vCursorStack().getItemType())) {
         ItemArea var8 = var2 == ScrollDirection.TO_CHEST ? var3.getItemArea(var7, var5) : var4.getItemArea(var7, var5);
         this.findSourceLeaveLastAndDo(var8, var5, var6, ScrollingUtils::pickUpNewStackLeaveLastIfEmpty$lambda$48);
      }
   }

   private final void findSourceAndTargetAndDo(
      ItemArea var1,
      ItemArea var2,
      List var3,
      ItemType var4,
      int var5,
      int var6,
      Function1 var7,
      boolean var8,
      boolean var9,
      boolean var10,
      Function1 var11,
      boolean var12,
      Function2 var13
   ) {
      int var14 = var10 ? -1 : findSlotOrEmpty$default(this, var1, var3, true, 0, var6, var8, var9, var7, 8, null);
      int var15 = findSlotOrEmpty$default(this, var2, var3, false, var5, var6, var12, false, var11, 68, null);
      if (!var10) {
         if (var15 != -1 && var14 != -1) {
            var13.invoke(var15, var14);
            return;
         }
      } else if (var15 != -1) {
         var13.invoke(var15, var14);
      }
   }

   private final int findMinSlotOrEmpty(ItemArea var1, List var2, boolean var3, Function1 var4) {
      int var5 = -1;
      int var6 = -1;
      Iterator var10 = var1.getSlotIndices().iterator();

      while (var10.hasNext()) {
         int var7 = ((Number)var10.next()).intValue();
         ItemStack var10000 = ((Slot)var2.get(var7)).getItem();
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         ItemStack var8 = var10000;
         org.anti_ad.mc.ipnext.item.ItemStack var12;
         if (var10000.isEmpty()) {
            var12 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
         } else {
            ItemStack$Companion var13 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
            ItemStack var9 = var8;
            Item var10003 = var9.getItem();
            Intrinsics.checkNotNullExpressionValue(var10003, "");
            DataComponentMap var10004 = var9.getComponents();
            Intrinsics.checkNotNull(var10004);
            var12 = var13.invoke(
               new ItemType(
                  var10003,
                  (PatchedDataComponentMap)var10004,
                  var9.getComponentsPatch(),
                  new VanillaAccessorsKt$(itemType)$1(var9),
                  false,
                  false,
                  null,
                  112,
                  null
               ),
               var8.getCount()
            );
         }

         org.anti_ad.mc.ipnext.item.ItemStack var11 = var12;
         if ((Boolean)var4.invoke(var11)) {
            var5 = ((Slot)var2.get(var7)).index;
            break;
         }

         if (var6 == -1 && ItemStackExtensionsKt.isEmpty(var11)) {
            var6 = ((Slot)var2.get(var7)).index;
         }
      }

      if (var5 != -1) {
         return var5;
      } else {
         return var3 ? var6 : -1;
      }
   }

   private final int findSlotOrEmpty(ItemArea var1, List var2, boolean var3, int var4, int var5, boolean var6, boolean var7, Function1 var8) {
      int var9;
      int var10;
      label77: {
         var9 = -1;
         var10 = -1;
         if (var5 != -1) {
            ItemStack var10001 = ((Slot)var2.get(var5)).getItem();
            Intrinsics.checkNotNullExpressionValue(var10001, "");
            ItemStack var11 = var10001;
            org.anti_ad.mc.ipnext.item.ItemStack var26;
            if (var10001.isEmpty()) {
               var26 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
            } else {
               ItemStack$Companion var27 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
               ItemStack var12 = var11;
               Item var10004 = var12.getItem();
               Intrinsics.checkNotNullExpressionValue(var10004, "");
               DataComponentMap var10005 = var12.getComponents();
               Intrinsics.checkNotNull(var10005);
               var26 = var27.invoke(
                  new ItemType(
                     var10004,
                     (PatchedDataComponentMap)var10005,
                     var12.getComponentsPatch(),
                     new VanillaAccessorsKt$(itemType)$1(var12),
                     false,
                     false,
                     null,
                     112,
                     null
                  ),
                  var11.getCount()
               );
            }

            if ((Boolean)var8.invoke(var26) && var1.getSlotIndices().contains(var5)) {
               var9 = var5;
               break label77;
            }
         }

         Iterator var16 = var1.getSlotIndices().iterator();

         while (var16.hasNext()) {
            if ((var5 = ((Number)var16.next()).intValue()) != var4) {
               ItemStack var10000 = ((Slot)var2.get(var5)).getItem();
               Intrinsics.checkNotNullExpressionValue(var10000, "");
               ItemStack var19 = var10000;
               org.anti_ad.mc.ipnext.item.ItemStack var21;
               if (var10000.isEmpty()) {
                  var21 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
               } else {
                  ItemStack$Companion var22 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
                  ItemStack var13 = var19;
                  Item var10003 = var13.getItem();
                  Intrinsics.checkNotNullExpressionValue(var10003, "");
                  DataComponentMap var29 = var13.getComponents();
                  Intrinsics.checkNotNull(var29);
                  var21 = var22.invoke(
                     new ItemType(
                        var10003,
                        (PatchedDataComponentMap)var29,
                        var13.getComponentsPatch(),
                        new VanillaAccessorsKt$(itemType)$1(var13),
                        false,
                        false,
                        null,
                        112,
                        null
                     ),
                     var19.getCount()
                  );
               }

               org.anti_ad.mc.ipnext.item.ItemStack var20 = var21;
               if ((Boolean)var8.invoke(var20)) {
                  var9 = ((Slot)var2.get(var5)).index;
                  if (var6) {
                     break;
                  }
               } else if (var10 == -1 && ItemStackExtensionsKt.isEmpty(var20)) {
                  var10 = ((Slot)var2.get(var5)).index;
               }
            }
         }

         if (var4 != -1 && var9 == -1 && !var3 && var1.getSlotIndices().contains(var4)) {
            ItemStack var23 = ((Slot)var2.get(var4)).getItem();
            Intrinsics.checkNotNullExpressionValue(var23, "");
            ItemStack var15 = var23;
            org.anti_ad.mc.ipnext.item.ItemStack var24;
            if (var23.isEmpty()) {
               var24 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
            } else {
               ItemStack$Companion var25 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
               ItemStack var17 = var15;
               Item var28 = var17.getItem();
               Intrinsics.checkNotNullExpressionValue(var28, "");
               DataComponentMap var30 = var17.getComponents();
               Intrinsics.checkNotNull(var30);
               var24 = var25.invoke(
                  new ItemType(
                     var28,
                     (PatchedDataComponentMap)var30,
                     var17.getComponentsPatch(),
                     new VanillaAccessorsKt$(itemType)$1(var17),
                     false,
                     false,
                     null,
                     112,
                     null
                  ),
                  var15.getCount()
               );
            }

            org.anti_ad.mc.ipnext.item.ItemStack var18 = var24;
            if ((Boolean)var8.invoke(var18)) {
               var9 = ((Slot)var2.get(var4)).index;
            }
         }
      }

      if (var7 && var10 != -1) {
         return var10;
      } else if (var9 != -1) {
         return var9;
      } else {
         return var3 ? var10 : -1;
      }
   }

   private final void findTargetAndDo(ItemArea var1, List var2, ItemType var3, Function1 var4) {
      int var5 = -1;
      int var6 = -1;
      Iterator var10 = var1.getSlotIndices().iterator();

      while (var10.hasNext()) {
         int var7 = ((Number)var10.next()).intValue();
         Slot var11;
         ItemStack var10000 = (var11 = (Slot)var2.get(var7)).getItem();
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         ItemStack var8 = var10000;
         org.anti_ad.mc.ipnext.item.ItemStack var13;
         if (var10000.isEmpty()) {
            var13 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
         } else {
            ItemStack$Companion var14 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
            ItemStack var9 = var8;
            Item var10003 = var9.getItem();
            Intrinsics.checkNotNullExpressionValue(var10003, "");
            DataComponentMap var10004 = var9.getComponents();
            Intrinsics.checkNotNull(var10004);
            var13 = var14.invoke(
               new ItemType(
                  var10003,
                  (PatchedDataComponentMap)var10004,
                  var9.getComponentsPatch(),
                  new VanillaAccessorsKt$(itemType)$1(var9),
                  false,
                  false,
                  null,
                  112,
                  null
               ),
               var8.getCount()
            );
         }

         org.anti_ad.mc.ipnext.item.ItemStack var12 = var13;
         if (!ItemStackExtensionsKt.isFull(var13) && Intrinsics.areEqual(var12.getItemType(), var3)) {
            var5 = var11.index;
            break;
         }

         if (var6 == -1 && ItemStackExtensionsKt.isEmpty(var12)) {
            var6 = var11.index;
         }
      }

      if (var5 != -1) {
         var4.invoke(var5);
      } else {
         if (var6 != -1) {
            var4.invoke(var6);
         }
      }
   }

   private final void findTargetSpreadingAndDo(ItemArea var1, List var2, ItemType var3, Function1 var4) {
      int var5 = -1;
      int var6 = -1;
      int var7 = 64;
      Iterator var12 = var1.getSlotIndices().iterator();

      while (var12.hasNext()) {
         int var8 = ((Number)var12.next()).intValue();
         Slot var13;
         ItemStack var10000 = (var13 = (Slot)var2.get(var8)).getItem();
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         ItemStack var9 = var10000;
         org.anti_ad.mc.ipnext.item.ItemStack var18;
         if (var10000.isEmpty()) {
            var18 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
         } else {
            ItemStack$Companion var19 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
            ItemStack var10 = var9;
            Item var10003 = var10.getItem();
            Intrinsics.checkNotNullExpressionValue(var10003, "");
            DataComponentMap var10004 = var10.getComponents();
            Intrinsics.checkNotNull(var10004);
            var18 = var19.invoke(
               new ItemType(
                  var10003,
                  (PatchedDataComponentMap)var10004,
                  var10.getComponentsPatch(),
                  new VanillaAccessorsKt$(itemType)$1(var10),
                  false,
                  false,
                  null,
                  112,
                  null
               ),
               var9.getCount()
            );
         }

         org.anti_ad.mc.ipnext.item.ItemStack var11 = var18;
         var10000 = var13.getItem();
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         var9 = var10000;
         org.anti_ad.mc.ipnext.item.ItemStack var21;
         if (var10000.isEmpty()) {
            var21 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
         } else {
            ItemStack$Companion var22 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
            ItemStack var16 = var9;
            Item var26 = var16.getItem();
            Intrinsics.checkNotNullExpressionValue(var26, "");
            DataComponentMap var28 = var16.getComponents();
            Intrinsics.checkNotNull(var28);
            var21 = var22.invoke(
               new ItemType(
                  var26, (PatchedDataComponentMap)var28, var16.getComponentsPatch(), new VanillaAccessorsKt$(itemType)$1(var16), false, false, null, 112, null
               ),
               var9.getCount()
            );
         }

         if (!ItemStackExtensionsKt.isFull(var21) && Intrinsics.areEqual(var11.getItemType(), var3) && var11.getCount() < var7) {
            var7 = var11.getCount();
            var5 = var13.index;
         } else {
            var10000 = var13.getItem();
            Intrinsics.checkNotNullExpressionValue(var10000, "");
            var9 = var10000;
            org.anti_ad.mc.ipnext.item.ItemStack var24;
            if (var10000.isEmpty()) {
               var24 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
            } else {
               ItemStack$Companion var25 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
               ItemStack var17 = var9;
               Item var27 = var17.getItem();
               Intrinsics.checkNotNullExpressionValue(var27, "");
               DataComponentMap var29 = var17.getComponents();
               Intrinsics.checkNotNull(var29);
               var24 = var25.invoke(
                  new ItemType(
                     var27,
                     (PatchedDataComponentMap)var29,
                     var17.getComponentsPatch(),
                     new VanillaAccessorsKt$(itemType)$1(var17),
                     false,
                     false,
                     null,
                     112,
                     null
                  ),
                  var9.getCount()
               );
            }

            if (ItemStackExtensionsKt.isEmpty(var24)) {
               var6 = var13.index;
               break;
            }
         }
      }

      if (var6 != -1) {
         var4.invoke(var6);
      } else {
         if (var5 != -1) {
            var4.invoke(var5);
         }
      }
   }

   private final void findSourceAndDo(ItemArea var1, List var2, ItemType var3, Function1 var4) {
      int var5 = -1;
      Iterator var9 = var1.getSlotIndices().iterator();

      while (var9.hasNext()) {
         int var6 = ((Number)var9.next()).intValue();
         Slot var10;
         ItemStack var10000 = (var10 = (Slot)var2.get(var6)).getItem();
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         ItemStack var7 = var10000;
         org.anti_ad.mc.ipnext.item.ItemStack var13;
         if (var10000.isEmpty()) {
            var13 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
         } else {
            ItemStack$Companion var14 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
            ItemStack var8 = var7;
            Item var10003 = var8.getItem();
            Intrinsics.checkNotNullExpressionValue(var10003, "");
            DataComponentMap var10004 = var8.getComponents();
            Intrinsics.checkNotNull(var10004);
            var13 = var14.invoke(
               new ItemType(
                  var10003,
                  (PatchedDataComponentMap)var10004,
                  var8.getComponentsPatch(),
                  new VanillaAccessorsKt$(itemType)$1(var8),
                  false,
                  false,
                  null,
                  112,
                  null
               ),
               var7.getCount()
            );
         }

         if (!ItemStackExtensionsKt.isEmpty(var13)) {
            var10000 = var10.getItem();
            Intrinsics.checkNotNullExpressionValue(var10000, "");
            var7 = var10000;
            org.anti_ad.mc.ipnext.item.ItemStack var16;
            if (var10000.isEmpty()) {
               var16 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
            } else {
               ItemStack$Companion var17 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
               ItemStack var12 = var7;
               Item var18 = var12.getItem();
               Intrinsics.checkNotNullExpressionValue(var18, "");
               DataComponentMap var19 = var12.getComponents();
               Intrinsics.checkNotNull(var19);
               var16 = var17.invoke(
                  new ItemType(
                     var18,
                     (PatchedDataComponentMap)var19,
                     var12.getComponentsPatch(),
                     new VanillaAccessorsKt$(itemType)$1(var12),
                     false,
                     false,
                     null,
                     112,
                     null
                  ),
                  var7.getCount()
               );
            }

            if (Intrinsics.areEqual(var16.getItemType(), var3)) {
               var5 = var10.index;
               break;
            }
         }
      }

      if (var5 != -1) {
         var4.invoke(var5);
      }
   }

   private final void findSourceLeaveLastAndDo(ItemArea var1, List var2, ItemType var3, Function1 var4) {
      int var5 = -1;
      Iterator var9 = var1.getSlotIndices().iterator();

      while (var9.hasNext()) {
         int var6 = ((Number)var9.next()).intValue();
         Slot var10;
         ItemStack var10000 = (var10 = (Slot)var2.get(var6)).getItem();
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         ItemStack var7 = var10000;
         org.anti_ad.mc.ipnext.item.ItemStack var13;
         if (var10000.isEmpty()) {
            var13 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
         } else {
            ItemStack$Companion var14 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
            ItemStack var8 = var7;
            Item var10003 = var8.getItem();
            Intrinsics.checkNotNullExpressionValue(var10003, "");
            DataComponentMap var10004 = var8.getComponents();
            Intrinsics.checkNotNull(var10004);
            var13 = var14.invoke(
               new ItemType(
                  var10003,
                  (PatchedDataComponentMap)var10004,
                  var8.getComponentsPatch(),
                  new VanillaAccessorsKt$(itemType)$1(var8),
                  false,
                  false,
                  null,
                  112,
                  null
               ),
               var7.getCount()
            );
         }

         if (var13.getCount() > 1) {
            var10000 = var10.getItem();
            Intrinsics.checkNotNullExpressionValue(var10000, "");
            var7 = var10000;
            org.anti_ad.mc.ipnext.item.ItemStack var16;
            if (var10000.isEmpty()) {
               var16 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
            } else {
               ItemStack$Companion var17 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
               ItemStack var12 = var7;
               Item var18 = var12.getItem();
               Intrinsics.checkNotNullExpressionValue(var18, "");
               DataComponentMap var19 = var12.getComponents();
               Intrinsics.checkNotNull(var19);
               var16 = var17.invoke(
                  new ItemType(
                     var18,
                     (PatchedDataComponentMap)var19,
                     var12.getComponentsPatch(),
                     new VanillaAccessorsKt$(itemType)$1(var12),
                     false,
                     false,
                     null,
                     112,
                     null
                  ),
                  var7.getCount()
               );
            }

            if (Intrinsics.areEqual(var16.getItemType(), var3)) {
               var5 = var10.index;
               break;
            }
         }
      }

      if (var5 != -1) {
         var4.invoke(var5);
      }
   }

   private static final Unit scrollFullStack$lambda$3$lambda$2(int var0) {
      ContainerClicker.INSTANCE.leftClick(var0);
      return Unit.INSTANCE;
   }

   private static final Unit scrollFullStack$lambda$3(
      ScrollDirection var0, AreaTypes var1, ItemType var2, AreaType var3, List var4, AreaType var5, AreaType var6, AreaType var7
   ) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      Intrinsics.checkNotNullParameter(var4, "");
      Intrinsics.checkNotNullParameter(var5, "");
      Intrinsics.checkNotNullParameter(var6, "");
      Intrinsics.checkNotNullParameter(var7, "");
      if (ItemTypeExtensionsKt.isEmpty(var2)) {
         Slot var10000 = InventoryKt.vFocusedSlot();
         if (var10000 != null) {
            Slot var8 = var10000;
            INSTANCE.doDefaultAction(var1, var0, var8, var3, var4, var5, var6);
         }
      } else {
         INSTANCE.findTargetAndDo(var3.getItemArea(Vanilla.INSTANCE.container(), var4), var4, var2, ScrollingUtils::scrollFullStack$lambda$3$lambda$2);
         INSTANCE.pickUpNewStackIfEmpty(var2, var0, var5, var6, var4);
      }

      return Unit.INSTANCE;
   }

   private static final Unit doDefaultAction$lambda$5$lambda$4(int var0, int var1) {
      ContainerClicker.INSTANCE.leftClick(var0);
      ContainerClicker.INSTANCE.leftClick(var1);
      ContainerClicker.INSTANCE.leftClick(var0);
      return Unit.INSTANCE;
   }

   private static final Unit doDefaultAction$lambda$5(List var0, AreaTypes var1, ItemArea var2, ItemArea var3, ItemType var4, int var5) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      Intrinsics.checkNotNullParameter(var4, "");
      findSourceAndTargetAndDo$default(
         INSTANCE, var2, var3, var0, var4, var5, 0, null, false, false, false, null, false, ScrollingUtils::doDefaultAction$lambda$5$lambda$4, 4064, null
      );
      return Unit.INSTANCE;
   }

   private static final boolean scrollFullStackLeaveLast$lambda$11$lambda$9$lambda$8$lambda$6(ItemType var0, org.anti_ad.mc.ipnext.item.ItemStack var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return var1.getCount() > 1 && Intrinsics.areEqual(var1.getItemType(), var0);
   }

   private static final Unit scrollFullStackLeaveLast$lambda$11$lambda$9$lambda$8$lambda$7(int var0, int var1) {
      ContainerClicker.INSTANCE.leftClick(var0);
      ContainerClicker.INSTANCE.rightClick(var0);
      ContainerClicker.INSTANCE.leftClick(var1);
      if (!ItemStackExtensionsKt.isEmpty(InventoryKt.vCursorStack())) {
         ContainerClicker.INSTANCE.leftClick(var0);
      }

      return Unit.INSTANCE;
   }

   private static final Unit scrollFullStackLeaveLast$lambda$11$lambda$9$lambda$8(
      List var0, AreaTypes var1, ItemArea var2, ItemArea var3, ItemType var4, int var5
   ) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      Intrinsics.checkNotNullParameter(var4, "");
      findSourceAndTargetAndDo$default(
         INSTANCE,
         var2,
         var3,
         var0,
         var4,
         var5,
         0,
         null,
         false,
         false,
         false,
         ScrollingUtils::scrollFullStackLeaveLast$lambda$11$lambda$9$lambda$8$lambda$6,
         false,
         ScrollingUtils::scrollFullStackLeaveLast$lambda$11$lambda$9$lambda$8$lambda$7,
         3040,
         null
      );
      return Unit.INSTANCE;
   }

   private static final Unit scrollFullStackLeaveLast$lambda$11$lambda$10(int var0) {
      ContainerClicker.INSTANCE.leftClick(var0);
      return Unit.INSTANCE;
   }

   private static final Unit scrollFullStackLeaveLast$lambda$11(
      ScrollDirection var0, AreaTypes var1, ItemType var2, AreaType var3, List var4, AreaType var5, AreaType var6, AreaType var7
   ) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      Intrinsics.checkNotNullParameter(var4, "");
      Intrinsics.checkNotNullParameter(var5, "");
      Intrinsics.checkNotNullParameter(var6, "");
      Intrinsics.checkNotNullParameter(var7, "");
      if (ItemTypeExtensionsKt.isEmpty(var2)) {
         Slot var10000 = InventoryKt.vFocusedSlot();
         if (var10000 != null) {
            Slot var8 = var10000;
            INSTANCE.withFocusedItemFullStackDo(var1, var0, var8, var3, var4, var5, var6, ScrollingUtils::scrollFullStackLeaveLast$lambda$11$lambda$9$lambda$8);
         }
      } else {
         INSTANCE.findTargetAndDo(
            var3.getItemArea(Vanilla.INSTANCE.container(), var4), var4, var2, ScrollingUtils::scrollFullStackLeaveLast$lambda$11$lambda$10
         );
         INSTANCE.pickUpNewStackLeaveLastIfEmpty(var2, var0, var5, var6, var4);
      }

      return Unit.INSTANCE;
   }

   private static final boolean scrollFullStackSpread$lambda$21$lambda$19$lambda$18$lambda$12(
      IntRef var0, ItemType var1, org.anti_ad.mc.ipnext.item.ItemStack var2
   ) {
      Intrinsics.checkNotNullParameter(var2, "");
      Log.INSTANCE.trace("minCount = " + var0.element);
      if (var2.getCount() < var0.element && Intrinsics.areEqual(var2.getItemType(), var1)) {
         var0.element = var2.getCount();
         return true;
      } else {
         return false;
      }
   }

   private static final boolean scrollFullStackSpread$lambda$21$lambda$19$lambda$18$lambda$14(
      ItemType var0, IntRef var1, org.anti_ad.mc.ipnext.item.ItemStack var2
   ) {
      Intrinsics.checkNotNullParameter(var2, "");
      boolean var4;
      boolean var3 = var4 = Intrinsics.areEqual(var2.getItemType(), var0);
      if (var4) {
         var1.element = var2.getCount();
      }

      return var3;
   }

   private static final Unit scrollFullStackSpread$lambda$21$lambda$19$lambda$18$lambda$15(IntRef var0, int var1, int var2) {
      ContainerClicker.INSTANCE.leftClick(var1);
      ContainerClicker.INSTANCE.rightClick(var2);
      var0.element = var1;
      return Unit.INSTANCE;
   }

   private static final Unit scrollFullStackSpread$lambda$21$lambda$19$lambda$18$lambda$17$lambda$16(IntRef var0, int var1) {
      Log.INSTANCE.trace("spreading from " + var0.element + " to " + var1);
      ContainerClicker.INSTANCE.rightClick(var1);
      return Unit.INSTANCE;
   }

   private static final Unit scrollFullStackSpread$lambda$21$lambda$19$lambda$18(
      List var0, IntRef var1, IntRef var2, IntRef var3, AreaTypes var4, ItemArea var5, ItemArea var6, ItemType var7, int var8
   ) {
      Intrinsics.checkNotNullParameter(var4, "");
      Intrinsics.checkNotNullParameter(var5, "");
      Intrinsics.checkNotNullParameter(var6, "");
      Intrinsics.checkNotNullParameter(var7, "");
      findSourceAndTargetAndDo$default(
         INSTANCE,
         var5,
         var6,
         var0,
         var7,
         var8,
         0,
         ScrollingUtils::scrollFullStackSpread$lambda$21$lambda$19$lambda$18$lambda$12,
         false,
         true,
         false,
         ScrollingUtils::scrollFullStackSpread$lambda$21$lambda$19$lambda$18$lambda$14,
         false,
         ScrollingUtils::scrollFullStackSpread$lambda$21$lambda$19$lambda$18$lambda$15,
         2592,
         null
      );
      if (var1.element != -1) {
         Iterator var9 = ((Iterable)RangesKt.until(1, var2.element)).iterator();

         while (var9.hasNext()) {
            ((IntIterator)var9).nextInt();
            INSTANCE.findTargetSpreadingAndDo(var5, var0, var7, ScrollingUtils::scrollFullStackSpread$lambda$21$lambda$19$lambda$18$lambda$17$lambda$16);
         }

         if (!ItemStackExtensionsKt.isEmpty(InventoryKt.vCursorStack())) {
            ContainerClicker.INSTANCE.leftClick(var1.element);
         }
      }

      return Unit.INSTANCE;
   }

   private static final Unit scrollFullStackSpread$lambda$21$lambda$20(int var0) {
      ContainerClicker.INSTANCE.rightClick(var0);
      return Unit.INSTANCE;
   }

   private static final Unit scrollFullStackSpread$lambda$21(
      ScrollDirection var0, IntRef var1, AreaTypes var2, ItemType var3, AreaType var4, List var5, AreaType var6, AreaType var7, AreaType var8
   ) {
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      Intrinsics.checkNotNullParameter(var4, "");
      Intrinsics.checkNotNullParameter(var5, "");
      Intrinsics.checkNotNullParameter(var6, "");
      Intrinsics.checkNotNullParameter(var7, "");
      Intrinsics.checkNotNullParameter(var8, "");
      if (ItemTypeExtensionsKt.isEmpty(var3)) {
         Slot var10000 = InventoryKt.vFocusedSlot();
         if (var10000 != null) {
            Slot var11 = var10000;
            IntRef var12;
            IntRef var14 = var12 = new IntRef();
            ItemStack var10001 = var11.getItem();
            Intrinsics.checkNotNullExpressionValue(var10001, "");
            ItemStack var9 = var10001;
            org.anti_ad.mc.ipnext.item.ItemStack var15;
            if (var10001.isEmpty()) {
               var15 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
            } else {
               ItemStack$Companion var16 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
               ItemStack var10 = var9;
               Item var10004 = var10.getItem();
               Intrinsics.checkNotNullExpressionValue(var10004, "");
               DataComponentMap var10005 = var10.getComponents();
               Intrinsics.checkNotNull(var10005);
               var15 = var16.invoke(
                  new ItemType(
                     var10004,
                     (PatchedDataComponentMap)var10005,
                     var10.getComponentsPatch(),
                     new VanillaAccessorsKt$(itemType)$1(var10),
                     false,
                     false,
                     null,
                     112,
                     null
                  ),
                  var9.getCount()
               );
            }

            var14.element = var15.getCount() - 1;
            IntRef var13;
            (var13 = new IntRef()).element = -1;
            INSTANCE.withFocusedItemFullStackDo(var2, var0, var11, var4, var5, var6, var7, ScrollingUtils::scrollFullStackSpread$lambda$21$lambda$19$lambda$18);
         }
      } else {
         INSTANCE.findTargetSpreadingAndDo(
            var4.getItemArea(Vanilla.INSTANCE.container(), var5), var5, var3, ScrollingUtils::scrollFullStackSpread$lambda$21$lambda$20
         );
         INSTANCE.pickUpNewStackIfEmpty(var3, var0, var6, var7, var5);
      }

      return Unit.INSTANCE;
   }

   private static final Unit scrollFullStackThrow$lambda$25$lambda$24$lambda$23$lambda$22(int var0, int var1) {
      ContainerClicker.INSTANCE.qClick(var0);
      return Unit.INSTANCE;
   }

   private static final Unit scrollFullStackThrow$lambda$25$lambda$24$lambda$23(
      List var0, AreaTypes var1, ItemArea var2, ItemArea var3, ItemType var4, int var5
   ) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      Intrinsics.checkNotNullParameter(var4, "");
      findSourceAndTargetAndDo$default(
         INSTANCE,
         var2,
         var3,
         var0,
         var4,
         var5,
         0,
         null,
         false,
         false,
         true,
         null,
         false,
         ScrollingUtils::scrollFullStackThrow$lambda$25$lambda$24$lambda$23$lambda$22,
         3552,
         null
      );
      return Unit.INSTANCE;
   }

   private static final Unit scrollFullStackThrow$lambda$25(
      ScrollDirection var0, AreaTypes var1, ItemType var2, AreaType var3, List var4, AreaType var5, AreaType var6, AreaType var7
   ) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      Intrinsics.checkNotNullParameter(var4, "");
      Intrinsics.checkNotNullParameter(var5, "");
      Intrinsics.checkNotNullParameter(var6, "");
      Intrinsics.checkNotNullParameter(var7, "");
      if (ItemTypeExtensionsKt.isEmpty(var2)) {
         Slot var10000 = InventoryKt.vFocusedSlot();
         if (var10000 != null) {
            Slot var8 = var10000;
            INSTANCE.withFocusedItemFullStackDo(var1, var0, var8, var3, var4, var5, var6, ScrollingUtils::scrollFullStackThrow$lambda$25$lambda$24$lambda$23);
         }
      } else {
         ContainerClicker.INSTANCE.leftClick(-999);
         INSTANCE.pickUpNewStackIfEmpty(var2, var0, var5, var6, var4);
      }

      return Unit.INSTANCE;
   }

   private static final Unit scrollSingleItemThrow$lambda$29$lambda$28$lambda$27$lambda$26(int var0, int var1) {
      ContainerClicker.INSTANCE.leftClick(var0);
      ContainerClicker.INSTANCE.rightClick(-999);
      ContainerClicker.INSTANCE.leftClick(var0);
      return Unit.INSTANCE;
   }

   private static final Unit scrollSingleItemThrow$lambda$29$lambda$28$lambda$27(List var0, ItemArea var1, ItemArea var2, Slot var3, int var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      ItemStack var10000 = var3.getItem();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      ItemStack var6 = var10000;
      org.anti_ad.mc.ipnext.item.ItemStack var8;
      if (var10000.isEmpty()) {
         var8 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
      } else {
         ItemStack$Companion var9 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
         ItemStack var5 = var6;
         Item var10003 = var5.getItem();
         Intrinsics.checkNotNullExpressionValue(var10003, "");
         DataComponentMap var10004 = var5.getComponents();
         Intrinsics.checkNotNull(var10004);
         var8 = var9.invoke(
            new ItemType(
               var10003, (PatchedDataComponentMap)var10004, var5.getComponentsPatch(), new VanillaAccessorsKt$(itemType)$1(var5), false, false, null, 112, null
            ),
            var6.getCount()
         );
      }

      ItemType var7 = var8.getItemType();
      findSourceAndTargetAndDo$default(
         INSTANCE,
         var1,
         var2,
         var0,
         var7,
         0,
         var4,
         null,
         false,
         false,
         true,
         null,
         false,
         ScrollingUtils::scrollSingleItemThrow$lambda$29$lambda$28$lambda$27$lambda$26,
         3536,
         null
      );
      return Unit.INSTANCE;
   }

   private static final Unit scrollSingleItemThrow$lambda$29(
      ScrollDirection var0, AreaTypes var1, ItemType var2, AreaType var3, List var4, AreaType var5, AreaType var6, AreaType var7
   ) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      Intrinsics.checkNotNullParameter(var4, "");
      Intrinsics.checkNotNullParameter(var5, "");
      Intrinsics.checkNotNullParameter(var6, "");
      Intrinsics.checkNotNullParameter(var7, "");
      if (ItemTypeExtensionsKt.isEmpty(var2)) {
         Slot var10000 = InventoryKt.vFocusedSlot();
         if (var10000 != null) {
            Slot var8 = var10000;
            INSTANCE.withFocusedStackSingleItemDo(
               var1, var0, var8, var4, var7, var3, var5, var6, ScrollingUtils::scrollSingleItemThrow$lambda$29$lambda$28$lambda$27
            );
         }
      } else {
         ContainerClicker.INSTANCE.rightClick(-999);
         INSTANCE.pickUpNewStackIfEmpty(var2, var0, var5, var6, var4);
      }

      return Unit.INSTANCE;
   }

   private static final Unit scrollSingleItem$lambda$34$lambda$32$lambda$31$lambda$30(int var0, int var1) {
      ContainerClicker.INSTANCE.leftClick(var0);
      ContainerClicker.INSTANCE.rightClick(var1);
      ContainerClicker.INSTANCE.leftClick(var0);
      return Unit.INSTANCE;
   }

   private static final Unit scrollSingleItem$lambda$34$lambda$32$lambda$31(List var0, ItemArea var1, ItemArea var2, Slot var3, int var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      ItemStack var10000 = var3.getItem();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      ItemStack var6 = var10000;
      org.anti_ad.mc.ipnext.item.ItemStack var8;
      if (var10000.isEmpty()) {
         var8 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
      } else {
         ItemStack$Companion var9 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
         ItemStack var5 = var6;
         Item var10003 = var5.getItem();
         Intrinsics.checkNotNullExpressionValue(var10003, "");
         DataComponentMap var10004 = var5.getComponents();
         Intrinsics.checkNotNull(var10004);
         var8 = var9.invoke(
            new ItemType(
               var10003, (PatchedDataComponentMap)var10004, var5.getComponentsPatch(), new VanillaAccessorsKt$(itemType)$1(var5), false, false, null, 112, null
            ),
            var6.getCount()
         );
      }

      ItemType var7 = var8.getItemType();
      findSourceAndTargetAndDo$default(
         INSTANCE,
         var1,
         var2,
         var0,
         var7,
         0,
         var4,
         null,
         false,
         false,
         false,
         null,
         false,
         ScrollingUtils::scrollSingleItem$lambda$34$lambda$32$lambda$31$lambda$30,
         4048,
         null
      );
      return Unit.INSTANCE;
   }

   private static final Unit scrollSingleItem$lambda$34$lambda$33(int var0) {
      ContainerClicker.INSTANCE.rightClick(var0);
      return Unit.INSTANCE;
   }

   private static final Unit scrollSingleItem$lambda$34(
      ScrollDirection var0, AreaTypes var1, ItemType var2, AreaType var3, List var4, AreaType var5, AreaType var6, AreaType var7
   ) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      Intrinsics.checkNotNullParameter(var4, "");
      Intrinsics.checkNotNullParameter(var5, "");
      Intrinsics.checkNotNullParameter(var6, "");
      Intrinsics.checkNotNullParameter(var7, "");
      if (ItemTypeExtensionsKt.isEmpty(var2)) {
         Slot var10000 = InventoryKt.vFocusedSlot();
         if (var10000 != null) {
            Slot var11 = var10000;
            ItemStack var13 = var10000.getItem();
            Intrinsics.checkNotNullExpressionValue(var13, "");
            ItemStack var8 = var13;
            org.anti_ad.mc.ipnext.item.ItemStack var14;
            if (var13.isEmpty()) {
               var14 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
            } else {
               ItemStack$Companion var15 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
               ItemStack var9 = var8;
               Item var10003 = var9.getItem();
               Intrinsics.checkNotNullExpressionValue(var10003, "");
               DataComponentMap var10004 = var9.getComponents();
               Intrinsics.checkNotNull(var10004);
               var14 = var15.invoke(
                  new ItemType(
                     var10003,
                     (PatchedDataComponentMap)var10004,
                     var9.getComponentsPatch(),
                     new VanillaAccessorsKt$(itemType)$1(var9),
                     false,
                     false,
                     null,
                     112,
                     null
                  ),
                  var8.getCount()
               );
            }

            ItemType var12 = var14.getItemType();
            ItemStack var16 = new ItemStack;
            ItemLike var10002 = (ItemLike)var12.getItem();
            PatchedDataComponentMap var17 = var12.getTag();
            if (var17 == null) {
               DataComponentMap var18 = DataComponentMap.EMPTY;
               Intrinsics.checkNotNull(DataComponentMap.EMPTY);
               var17 = (PatchedDataComponentMap)var18;
            }

            var16./* $VF: Unable to resugar constructor */<init>(var10002, 1, var17);
            if (var16.getMaxStackSize() > 1) {
               INSTANCE.withFocusedStackSingleItemDo(
                  var1, var0, var11, var4, var7, var3, var5, var6, ScrollingUtils::scrollSingleItem$lambda$34$lambda$32$lambda$31
               );
            } else {
               INSTANCE.doDefaultAction(var1, var0, var11, var3, var4, var5, var6);
            }
         }
      } else {
         AbstractContainerMenu var10 = Vanilla.INSTANCE.container();
         INSTANCE.findTargetAndDo(var3.getItemArea(var10, var4), var4, var2, ScrollingUtils::scrollSingleItem$lambda$34$lambda$33);
         if (ScrollSettings.INSTANCE.getSCROLL_AUTO_PICKUP_NEXT_FOR_SINGLE().getBooleanValue()) {
            INSTANCE.pickUpNewStackIfEmpty(var2, var0, var5, var6, var4);
         }
      }

      return Unit.INSTANCE;
   }

   private static final boolean scrollSingleItemLeaveLast$lambda$40$lambda$38$lambda$37$lambda$35(ItemType var0, org.anti_ad.mc.ipnext.item.ItemStack var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return var1.getCount() > 1 && Intrinsics.areEqual(var1.getItemType(), var0);
   }

   private static final Unit scrollSingleItemLeaveLast$lambda$40$lambda$38$lambda$37$lambda$36(int var0, int var1) {
      ContainerClicker.INSTANCE.leftClick(var0);
      ContainerClicker.INSTANCE.rightClick(var1);
      if (!ItemStackExtensionsKt.isEmpty(InventoryKt.vCursorStack())) {
         ContainerClicker.INSTANCE.leftClick(var0);
      }

      return Unit.INSTANCE;
   }

   private static final Unit scrollSingleItemLeaveLast$lambda$40$lambda$38$lambda$37(List var0, ItemArea var1, ItemArea var2, Slot var3, int var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      ItemStack var10000 = var3.getItem();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      ItemStack var6 = var10000;
      org.anti_ad.mc.ipnext.item.ItemStack var8;
      if (var10000.isEmpty()) {
         var8 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
      } else {
         ItemStack$Companion var9 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
         ItemStack var5 = var6;
         Item var10003 = var5.getItem();
         Intrinsics.checkNotNullExpressionValue(var10003, "");
         DataComponentMap var10004 = var5.getComponents();
         Intrinsics.checkNotNull(var10004);
         var8 = var9.invoke(
            new ItemType(
               var10003, (PatchedDataComponentMap)var10004, var5.getComponentsPatch(), new VanillaAccessorsKt$(itemType)$1(var5), false, false, null, 112, null
            ),
            var6.getCount()
         );
      }

      ItemType var7 = var8.getItemType();
      findSourceAndTargetAndDo$default(
         INSTANCE,
         var1,
         var2,
         var0,
         var7,
         0,
         var4,
         null,
         false,
         false,
         false,
         ScrollingUtils::scrollSingleItemLeaveLast$lambda$40$lambda$38$lambda$37$lambda$35,
         false,
         ScrollingUtils::scrollSingleItemLeaveLast$lambda$40$lambda$38$lambda$37$lambda$36,
         3024,
         null
      );
      return Unit.INSTANCE;
   }

   private static final Unit scrollSingleItemLeaveLast$lambda$40$lambda$39(int var0) {
      ContainerClicker.INSTANCE.rightClick(var0);
      return Unit.INSTANCE;
   }

   private static final Unit scrollSingleItemLeaveLast$lambda$40(
      ScrollDirection var0, AreaTypes var1, ItemType var2, AreaType var3, List var4, AreaType var5, AreaType var6, AreaType var7
   ) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      Intrinsics.checkNotNullParameter(var4, "");
      Intrinsics.checkNotNullParameter(var5, "");
      Intrinsics.checkNotNullParameter(var6, "");
      Intrinsics.checkNotNullParameter(var7, "");
      if (ItemTypeExtensionsKt.isEmpty(var2)) {
         Slot var10000 = InventoryKt.vFocusedSlot();
         if (var10000 != null) {
            Slot var11 = var10000;
            ItemStack var13 = var10000.getItem();
            Intrinsics.checkNotNullExpressionValue(var13, "");
            ItemStack var8 = var13;
            org.anti_ad.mc.ipnext.item.ItemStack var14;
            if (var13.isEmpty()) {
               var14 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
            } else {
               ItemStack$Companion var15 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
               ItemStack var9 = var8;
               Item var10003 = var9.getItem();
               Intrinsics.checkNotNullExpressionValue(var10003, "");
               DataComponentMap var10004 = var9.getComponents();
               Intrinsics.checkNotNull(var10004);
               var14 = var15.invoke(
                  new ItemType(
                     var10003,
                     (PatchedDataComponentMap)var10004,
                     var9.getComponentsPatch(),
                     new VanillaAccessorsKt$(itemType)$1(var9),
                     false,
                     false,
                     null,
                     112,
                     null
                  ),
                  var8.getCount()
               );
            }

            ItemType var12 = var14.getItemType();
            ItemStack var16 = new ItemStack;
            ItemLike var10002 = (ItemLike)var12.getItem();
            PatchedDataComponentMap var17 = var12.getTag();
            if (var17 == null) {
               DataComponentMap var18 = DataComponentMap.EMPTY;
               Intrinsics.checkNotNull(DataComponentMap.EMPTY);
               var17 = (PatchedDataComponentMap)var18;
            }

            var16./* $VF: Unable to resugar constructor */<init>(var10002, 1, var17);
            if (var16.getMaxStackSize() > 1) {
               INSTANCE.withFocusedStackSingleItemDo(
                  var1, var0, var11, var4, var7, var3, var5, var6, ScrollingUtils::scrollSingleItemLeaveLast$lambda$40$lambda$38$lambda$37
               );
            } else {
               INSTANCE.doDefaultAction(var1, var0, var11, var3, var4, var5, var6);
            }
         }
      } else {
         AbstractContainerMenu var10 = Vanilla.INSTANCE.container();
         INSTANCE.findTargetAndDo(var3.getItemArea(var10, var4), var4, var2, ScrollingUtils::scrollSingleItemLeaveLast$lambda$40$lambda$39);
         if (ScrollSettings.INSTANCE.getSCROLL_AUTO_PICKUP_NEXT_FOR_SINGLE().getBooleanValue()) {
            INSTANCE.pickUpNewStackLeaveLastIfEmpty(var2, var0, var5, var6, var4);
         }
      }

      return Unit.INSTANCE;
   }

   private static final boolean scrollSingleSpread$lambda$46$lambda$44$lambda$43$lambda$41(
      IntRef var0, ItemType var1, org.anti_ad.mc.ipnext.item.ItemStack var2
   ) {
      Intrinsics.checkNotNullParameter(var2, "");
      Log.INSTANCE.trace("minCount = " + var0.element);
      if (var2.getCount() < var0.element && Intrinsics.areEqual(var2.getItemType(), var1)) {
         var0.element = var2.getCount();
         return true;
      } else {
         return false;
      }
   }

   private static final Unit scrollSingleSpread$lambda$46$lambda$44$lambda$43$lambda$42(int var0, int var1) {
      Log.INSTANCE.trace("Will move one from " + var0 + " to " + var1);
      ContainerClicker.INSTANCE.leftClick(var0);
      ContainerClicker.INSTANCE.rightClick(var1);
      ContainerClicker.INSTANCE.leftClick(var0);
      return Unit.INSTANCE;
   }

   private static final Unit scrollSingleSpread$lambda$46$lambda$44$lambda$43(
      List var0, IntRef var1, AreaTypes var2, ItemArea var3, ItemArea var4, ItemType var5, int var6
   ) {
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      Intrinsics.checkNotNullParameter(var4, "");
      Intrinsics.checkNotNullParameter(var5, "");
      findSourceAndTargetAndDo$default(
         INSTANCE,
         var3,
         var4,
         var0,
         var5,
         var6,
         0,
         ScrollingUtils::scrollSingleSpread$lambda$46$lambda$44$lambda$43$lambda$41,
         false,
         true,
         false,
         null,
         false,
         ScrollingUtils::scrollSingleSpread$lambda$46$lambda$44$lambda$43$lambda$42,
         3616,
         null
      );
      return Unit.INSTANCE;
   }

   private static final Unit scrollSingleSpread$lambda$46$lambda$45(int var0) {
      ContainerClicker.INSTANCE.rightClick(var0);
      return Unit.INSTANCE;
   }

   private static final Unit scrollSingleSpread$lambda$46(
      ScrollDirection var0, IntRef var1, AreaTypes var2, ItemType var3, AreaType var4, List var5, AreaType var6, AreaType var7, AreaType var8
   ) {
      Intrinsics.checkNotNullParameter(var2, "");
      Intrinsics.checkNotNullParameter(var3, "");
      Intrinsics.checkNotNullParameter(var4, "");
      Intrinsics.checkNotNullParameter(var5, "");
      Intrinsics.checkNotNullParameter(var6, "");
      Intrinsics.checkNotNullParameter(var7, "");
      Intrinsics.checkNotNullParameter(var8, "");
      if (ItemTypeExtensionsKt.isEmpty(var3)) {
         Slot var10000 = InventoryKt.vFocusedSlot();
         if (var10000 != null) {
            Slot var9 = var10000;
            INSTANCE.withFocusedItemFullStackDo(var2, var0, var9, var4, var5, var6, var7, ScrollingUtils::scrollSingleSpread$lambda$46$lambda$44$lambda$43);
         }
      } else {
         INSTANCE.findTargetSpreadingAndDo(
            var4.getItemArea(Vanilla.INSTANCE.container(), var5), var5, var3, ScrollingUtils::scrollSingleSpread$lambda$46$lambda$45
         );
         INSTANCE.pickUpNewStackIfEmpty(var3, var0, var6, var7, var5);
      }

      return Unit.INSTANCE;
   }

   private static final Unit pickUpNewStackIfEmpty$lambda$47(int var0) {
      ContainerClicker.INSTANCE.leftClick(var0);
      return Unit.INSTANCE;
   }

   private static final Unit pickUpNewStackLeaveLastIfEmpty$lambda$48(int var0) {
      ContainerClicker.INSTANCE.leftClick(var0);
      ContainerClicker.INSTANCE.rightClick(var0);
      return Unit.INSTANCE;
   }

   private static final boolean findSourceAndTargetAndDo$lambda$49(ItemType var0, org.anti_ad.mc.ipnext.item.ItemStack var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return !ItemStackExtensionsKt.isFull(var1) && Intrinsics.areEqual(var1.getItemType(), var0);
   }

   private static final boolean findSourceAndTargetAndDo$lambda$50(ItemType var0, org.anti_ad.mc.ipnext.item.ItemStack var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      return Intrinsics.areEqual(var1.getItemType(), var0);
   }
}
