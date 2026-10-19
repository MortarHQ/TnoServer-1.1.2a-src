package org.anti_ad.mc.ipnext.event;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.anti_ad.mc.common.extensions.Kt_collectionKt;
import org.anti_ad.mc.common.input.GlobalInputHandler;
import org.anti_ad.mc.common.math2d.Line;
import org.anti_ad.mc.common.math2d.LineKt;
import org.anti_ad.mc.common.math2d.Point;
import org.anti_ad.mc.common.math2d.Rectangle;
import org.anti_ad.mc.common.math2d.Size;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.config.LockedSlotsSettings;
import org.anti_ad.mc.ipnext.config.Tweaks;
import org.anti_ad.mc.ipnext.ingame.InventoryKt;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt$(itemType)$1;
import org.anti_ad.mc.ipnext.integration.HintsManagerNG;
import org.anti_ad.mc.ipnext.inventory.ContainerClicker;
import org.anti_ad.mc.ipnext.inventory.ContainerType;
import org.anti_ad.mc.ipnext.inventory.ContainerTypes;
import org.anti_ad.mc.ipnext.item.ItemStack$Companion;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nMiscHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MiscHandler.kt\norg/anti_ad/mc/ipnext/event/MiscHandler\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n*L\n1#1,205:1\n119#2,4:206\n117#2:210\n78#2:211\n97#2:212\n95#2:213\n95#2:214\n95#2:215\n95#2:216\n103#2:217\n99#2,6:218\n88#2:224\n71#2:225\n66#2,7:226\n84#2:233\n84#2:234\n*S KotlinDebug\n*F\n+ 1 MiscHandler.kt\norg/anti_ad/mc/ipnext/event/MiscHandler\n*L\n90#1:206,4\n93#1:210\n98#1:211\n111#1:212\n114#1:213\n115#1:214\n117#1:215\n118#1:216\n122#1:217\n122#1:218,6\n139#1:224\n139#1:225\n139#1:226,7\n64#1:233\n77#1:234\n*E\n"
)
public final class MiscHandler {
   @NotNull
   public static final MiscHandler INSTANCE = new MiscHandler();

   private MiscHandler() {
   }

   public final void swipeMoving() {
      int[] var1;
      (var1 = new int[1])[0] = -100;
      if (GlobalInputHandler.INSTANCE.shiftAnd(var1)) {
         this.slotAction(MiscHandler::swipeMoving$lambda$0);
      } else {
         (var1 = new int[1])[0] = 81;
         if (GlobalInputHandler.INSTANCE.ctrlAnd(var1)) {
            this.slotAction(MiscHandler::swipeMoving$lambda$1);
         }
      }
   }

   private final void slotAction(Function3 var1) {
      Screen var2;
      AbstractContainerScreen var10000 = (var2 = Vanilla.INSTANCE.screen()) instanceof AbstractContainerScreen ? (AbstractContainerScreen)var2 : null;
      if (var10000 != null) {
         AbstractContainerScreen var6 = var10000;
         Point var17 = new Rectangle(var6.getGuiLeft(), var6.getGuiTop(), var6.getXSize(), var6.getYSize()).getTopLeft();
         if (var17 != null) {
            Point var3 = var17;
            List var19;
            if (HintsManagerNG.INSTANCE.isFastSwipeDisabled(var2.getClass())) {
               List var5 = CollectionsKt.emptyList();
               Slot var18 = ((AbstractContainerScreen)var2).getSlotUnderMouse();
               if (var18 != null) {
                  var5 = CollectionsKt.listOf(var18);
               }

               var19 = var5;
            } else {
               NonNullList var20 = Vanilla.INSTANCE.container().slots;
               Intrinsics.checkNotNullExpressionValue(var20, "");
               var19 = (List)var20;
            }

            List var4 = var19;
            if (!ItemStackExtensionsKt.isEmpty(InventoryKt.vCursorStack())) {
               return;
            }

            Line var12 = MouseTracer.INSTANCE.getAsLine();
            Set var13 = ContainerTypes.getTypes$default(ContainerTypes.INSTANCE, Vanilla.INSTANCE.container(), null, 2, null);
            ContainerType[] var8;
            (var8 = new ContainerType[3])[0] = ContainerType.NO_SORTING_STORAGE;
            var8[1] = ContainerType.SORTABLE_STORAGE;
            var8[2] = ContainerType.PURE_BACKPACK;
            Set var7 = SetsKt.setOf(var8);
            Iterator var14 = var4.iterator();

            while (var14.hasNext()) {
               Slot var11;
               if ((var11 = (Slot)var14.next()).container != null) {
                  if (!Tweaks.INSTANCE.getSWIPE_MOVE_CRAFTING_RESULT_SLOT().getBooleanValue()) {
                     if (!Kt_collectionKt.containsAny(var13, var7)) {
                        Container var21 = var11.container;
                        Intrinsics.checkNotNullExpressionValue(var11.container, "");
                        if (!(var21 instanceof Inventory)) {
                           continue;
                        }
                     }

                     Container var22 = var11.container;
                     Intrinsics.checkNotNullExpressionValue(var11.container, "");
                     if (var22 instanceof CraftingContainer) {
                        continue;
                     }

                     Container var23 = var11.container;
                     Intrinsics.checkNotNullExpressionValue(var11.container, "");
                     if (var23 instanceof ResultContainer) {
                        continue;
                     }
                  } else if (var13.contains(ContainerType.STONECUTTER)) {
                     Container var24 = var11.container;
                     Intrinsics.checkNotNullExpressionValue(var11.container, "");
                     if (var24 instanceof CraftingContainer) {
                        continue;
                     }

                     Container var25 = var11.container;
                     Intrinsics.checkNotNullExpressionValue(var11.container, "");
                     if (var25 instanceof ResultContainer) {
                        continue;
                     }
                  }

                  Slot var9 = var11;
                  Rectangle var15 = new Rectangle(var3.plus(new Point(var9.x, var9.y)), new Size(16, 16));
                  if (LineKt.intersects(var12, var15)) {
                     ItemStack var26 = var11.getItem();
                     Intrinsics.checkNotNullExpressionValue(var26, "");
                     ItemStack var10 = var26;
                     org.anti_ad.mc.ipnext.item.ItemStack var27;
                     if (var26.isEmpty()) {
                        var27 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
                     } else {
                        ItemStack$Companion var28 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
                        ItemStack var16 = var10;
                        Item var10003 = var16.getItem();
                        Intrinsics.checkNotNullExpressionValue(var10003, "");
                        DataComponentMap var10004 = var16.getComponents();
                        Intrinsics.checkNotNull(var10004);
                        var27 = var28.invoke(
                           new ItemType(
                              var10003,
                              (PatchedDataComponentMap)var10004,
                              var16.getComponentsPatch(),
                              new VanillaAccessorsKt$(itemType)$1(var16),
                              false,
                              false,
                              null,
                              112,
                              null
                           ),
                           var10.getCount()
                        );
                     }

                     if (!ItemStackExtensionsKt.isEmpty(var27)) {
                        var1.invoke(var11, var2, var13);
                     }
                  }
               }
            }

            return;
         }
      }
   }

   private static final Unit swipeMoving$lambda$0(Slot var0, Screen var1, Set var2) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      if (!LockSlotsHandler.INSTANCE.isMappedSlotLocked(var0)
         || !LockedSlotsSettings.INSTANCE.getLOCKED_SLOTS_DISABLE_QUICK_MOVE_THROW().getBooleanValue()
            && !LockedSlotsSettings.INSTANCE.getLOCK_SLOTS_DISABLE_USER_INTERACTION().getBooleanValue()) {
         LockSlotsHandler.INSTANCE.setLastMouseClickSlot(var0);
         ContainerClicker.INSTANCE.shiftClick(InventoryKt.vPlayerSlotOf(var0, var1).index);
         LockSlotsHandler.INSTANCE.setLastMouseClickSlot(null);
      }

      return Unit.INSTANCE;
   }

   private static final Unit swipeMoving$lambda$1(Slot var0, Screen var1, Set var2) {
      Intrinsics.checkNotNullParameter(var0, "");
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      if (!LockSlotsHandler.INSTANCE.isMappedSlotLocked(var0)
         || !LockedSlotsSettings.INSTANCE.getLOCKED_SLOTS_DISABLE_QUICK_MOVE_THROW().getBooleanValue()
            && !LockedSlotsSettings.INSTANCE.getLOCK_SLOTS_DISABLE_USER_INTERACTION().getBooleanValue()) {
         ContainerType[] var3;
         (var3 = new ContainerType[2])[0] = ContainerType.NO_SORTING_STORAGE;
         var3[1] = ContainerType.SORTABLE_STORAGE;
         Set var4 = SetsKt.setOf(var3);
         if (Kt_collectionKt.containsAny(var2, var4) && !var2.contains(ContainerType.CREATIVE)) {
            LockSlotsHandler.INSTANCE.setLastMouseClickSlot(var0);
            ContainerClicker.INSTANCE.qClick(InventoryKt.vPlayerSlotOf(var0, var1).index);
            LockSlotsHandler.INSTANCE.setLastMouseClickSlot(null);
         }
      }

      return Unit.INSTANCE;
   }
}
