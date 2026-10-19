package org.anti_ad.mc.ipnext.inventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import org.anti_ad.mc.common.TellPlayer;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt$(itemType)$1;
import org.anti_ad.mc.ipnext.inventory.data.ItemTracker;
import org.anti_ad.mc.ipnext.inventory.data.MutableItemTracker;
import org.anti_ad.mc.ipnext.inventory.data.SubTracker;
import org.anti_ad.mc.ipnext.inventory.sandbox.ContainerSandbox;
import org.anti_ad.mc.ipnext.inventory.sandbox.ItemPlanner;
import org.anti_ad.mc.ipnext.inventory.sandbox.SandboxClick;
import org.anti_ad.mc.ipnext.inventory.sandbox.diffcalculator.NoRoomException;
import org.anti_ad.mc.ipnext.item.ItemStack;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.MutableItemStack;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nAdvancedContainer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AdvancedContainer.kt\norg/anti_ad/mc/ipnext/inventory/AdvancedContainer\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 4 common.kt\norg/anti_ad/mc/common/extensions/CommonKt\n*L\n1#1,245:1\n1563#2:246\n1634#2,2:247\n1636#2:261\n1563#2:265\n1634#2,2:266\n1636#2:269\n93#3:249\n74#3:250\n66#3,10:251\n78#3:262\n84#3:268\n89#4,2:263\n91#4,2:270\n89#4,4:272\n89#4,4:276\n*S KotlinDebug\n*F\n+ 1 AdvancedContainer.kt\norg/anti_ad/mc/ipnext/inventory/AdvancedContainer\n*L\n61#1:246\n61#1:247,2\n61#1:261\n67#1:265\n67#1:266,2\n67#1:269\n61#1:249\n61#1:250\n61#1:251,10\n58#1:262\n67#1:268\n66#1:263,2\n66#1:270,2\n87#1:272,4\n96#1:276,4\n*E\n"
)
public final class AdvancedContainer {
   @NotNull
   public static final AdvancedContainer$Companion Companion = new AdvancedContainer$Companion(null);
   @NotNull
   private final AbstractContainerMenu vanillaContainer;
   @NotNull
   private final ItemPlanner planner;

   public AdvancedContainer(@NotNull AbstractContainerMenu var1, @NotNull ItemStack var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      super();
      this.vanillaContainer = var1;
      MutableItemStack var10001 = var2.copyAsMutable();
      Iterable var8 = this.getVanillaSlots();
      MutableItemStack var6 = var10001;
      AdvancedContainer var5 = this;
      Iterable var12 = var8;
      var var9 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var8, 10));

      for (Slot var3 : var12) {
         var3 = var3;
         ArrayList var7 = var9;
         net.minecraft.world.item.ItemStack var10000 = var3.getItem();
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         net.minecraft.world.item.ItemStack var16 = var10000;
         MutableItemStack var18;
         if (var10000.isEmpty()) {
            var18 = ItemStackExtensionsKt.empty(MutableItemStack.Companion);
         } else {
            net.minecraft.world.item.ItemStack var4 = var16;
            Item var10004 = var4.getItem();
            Intrinsics.checkNotNullExpressionValue(var10004, "");
            DataComponentMap var10005 = var4.getComponents();
            Intrinsics.checkNotNull(var10005);
            var18 = new MutableItemStack(
               new ItemType(
                  var10004,
                  (PatchedDataComponentMap)var10005,
                  var4.getComponentsPatch(),
                  new VanillaAccessorsKt$(itemType)$1(var4),
                  false,
                  false,
                  null,
                  112,
                  null
               ),
               var16.getCount(),
               null,
               4,
               null
            );
         }

         var7.add(var18);
      }

      List var17 = var9;
      List var10 = var17;
      var2 = var6;
      MutableItemTracker var11 = new MutableItemTracker(var2, var10, null, 4, null);
      var5.planner = new ItemPlanner(var11);
   }

   @NotNull
   public final AbstractContainerMenu getVanillaContainer() {
      return this.vanillaContainer;
   }

   private final List getVanillaSlots() {
      NonNullList var10000 = this.vanillaContainer.slots;
      Intrinsics.checkNotNullExpressionValue(this.vanillaContainer.slots, "");
      return (List)var10000;
   }

   private final List getSlotIdClicks() {
      List var1 = this.getVanillaSlots();

      try {
         Iterable var2;
         Iterable var3 = var2 = this.planner.getClicks();
         var2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var2, 10));

         for (SandboxClick var4 : var3) {
            var4 = var4;
            ArrayList var5 = var2;
            var5.add(TuplesKt.to(((Slot)var1.get(var4.getSlotIndex())).index, var4.getButton()));
         }

         var1 = var2;
      } catch (Throwable var6) {
         var1 = this.handleException(var6);
      }

      List var10000 = var1;
      if (var1 == null) {
         var10000 = CollectionsKt.emptyList();
      }

      return var10000;
   }

   private final Void handleException(Throwable var1) {
      if (var1 instanceof NoRoomException) {
         TellPlayer var10000 = TellPlayer.INSTANCE;
         String var10001 = var1.getMessage();
         if (var10001 == null) {
            var10001 = var1.toString();
         }

         var10000.chat(var10001);
      } else {
         var1.printStackTrace();
         TellPlayer.INSTANCE.chat(var1.toString());
      }

      return null;
   }

   public final void sandbox(@NotNull Function1 var1) {
      Intrinsics.checkNotNullParameter(var1, "");

      try {
         this.planner.sandbox(AdvancedContainer::sandbox$lambda$6$lambda$5);
      } catch (Throwable var2) {
         this.handleException(var2);
      }
   }

   public final void tracker(@NotNull Function1 var1) {
      Intrinsics.checkNotNullParameter(var1, "");

      try {
         this.planner.tracker(AdvancedContainer::tracker$lambda$9$lambda$8);
      } catch (Throwable var2) {
         this.handleException(var2);
      }
   }

   public final void arrange(boolean var1) {
      var1 = !var1 && ModSettings.INSTANCE.getADD_INTERVAL_BETWEEN_CLICKS().getBooleanValue()
         ? ModSettings.INSTANCE.getINTERVAL_BETWEEN_CLICKS_MS().getIntegerValue()
         : 0;
      ContainerClicker.INSTANCE.executeClicks(this.getSlotIdClicks(), var1);
   }

   public final void cleanCursor() {
      this.sandbox(AdvancedContainer::cleanCursor$lambda$16);
   }

   private final void cursorPutTo(ContainerSandbox var1, SubTracker var2, boolean var3) {
      ItemTracker var4;
      if (!ItemStackExtensionsKt.isEmpty((var4 = var1.getItems()).getCursor())) {
         Iterator var7 = var2.getIndexedSlots().iterator();

         while (var7.hasNext()) {
            IndexedValue var5;
            int var6 = (var5 = (IndexedValue)var7.next()).component1();
            ItemStack var8 = (ItemStack)var5.component2();
            if ((!var3 || !ItemStackExtensionsKt.isEmpty(var8))
               && VanillaAccessorsKt._canInsert_/* $VF was: (canInsert) */((Slot)this.getVanillaSlots().get(var6), var8)) {
               if (ItemStackExtensionsKt.stackableWith(var4.getCursor(), var8) && !ItemStackExtensionsKt.isFull(var8)) {
                  var1.leftClick(var6);
               }

               if (ItemStackExtensionsKt.isEmpty(var4.getCursor())) {
                  return;
               }
            }
         }
      }
   }

   private static final Unit sandbox$lambda$6$lambda$5(Function1 var0, AdvancedContainer var1, ContainerSandbox var2) {
      Intrinsics.checkNotNullParameter(var2, "");
      var0.invoke(new AdvancedContainer$SandboxDsl(var1, var2));
      return Unit.INSTANCE;
   }

   private static final Unit tracker$lambda$9$lambda$8(Function1 var0, AdvancedContainer var1, MutableItemTracker var2) {
      Intrinsics.checkNotNullParameter(var2, "");
      var0.invoke(new AdvancedContainer$TrackerDsl(var1, var2));
      return Unit.INSTANCE;
   }

   private static final Unit cleanCursor$lambda$16(AdvancedContainer var0, AdvancedContainer$SandboxDsl var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      AreaTypes var2 = AreaTypes.INSTANCE;
      if (ItemStackExtensionsKt.isEmpty(var1.getSandboxTracker().getCursor())) {
         return Unit.INSTANCE;
      }

      SubTracker var3 = var1.getAsSubTracker(var1.get(var2.getFocusedSlot().minus(var2.getLockedSlots())));
      var0.cursorPutTo(var1.getSandbox(), var3, false);
      if (ItemStackExtensionsKt.isEmpty(var1.getSandboxTracker().getCursor())) {
         return Unit.INSTANCE;
      }

      var3 = var1.getAsSubTracker(var1.get(var2.getPlayerHands().plus(var2.getPlayerHotbar()).plus(var2.getPlayerStorage()).minus(var2.getLockedSlots())));
      var0.cursorPutTo(var1.getSandbox(), var3, true);
      if (ItemStackExtensionsKt.isEmpty(var1.getSandboxTracker().getCursor())) {
         return Unit.INSTANCE;
      }

      var3 = var1.getAsSubTracker(var1.get(var2.getPlayerStorage().plus(var2.getPlayerHotbar()).plus(var2.getPlayerOffhand()).minus(var2.getLockedSlots())));
      var0.cursorPutTo(var1.getSandbox(), var3, false);
      if (ItemStackExtensionsKt.isEmpty(var1.getSandboxTracker().getCursor())) {
         return Unit.INSTANCE;
      }

      var3 = var1.getAsSubTracker(var1.get(var2.getItemStorage()));
      var0.cursorPutTo(var1.getSandbox(), var3, true);
      if (ItemStackExtensionsKt.isEmpty(var1.getSandboxTracker().getCursor())) {
         return Unit.INSTANCE;
      }

      var3 = var1.getAsSubTracker(var1.get(var2.getItemStorage()));
      var0.cursorPutTo(var1.getSandbox(), var3, false);
      return ItemStackExtensionsKt.isEmpty(var1.getSandboxTracker().getCursor()) ? Unit.INSTANCE : Unit.INSTANCE;
   }
}
