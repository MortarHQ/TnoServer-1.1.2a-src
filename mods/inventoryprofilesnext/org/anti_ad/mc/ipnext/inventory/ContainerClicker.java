package org.anti_ad.mc.ipnext.inventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.concurrent.TimersKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.Ref.ObjectRef;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen.ItemPickerMenu;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.math2d.Point;
import org.anti_ad.mc.common.math2d.Rectangle;
import org.anti_ad.mc.common.math2d.Size;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.common.vanilla.render.ColorKt;
import org.anti_ad.mc.common.vanilla.render.glue.RectKt;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.event.LockSlotsHandler;
import org.anti_ad.mc.ipnext.ingame.InventoryKt;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nContainerClicker.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ContainerClicker.kt\norg/anti_ad/mc/ipnext/inventory/ContainerClicker\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 3 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 5 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,405:1\n200#2:406\n81#2:407\n196#2:408\n203#2:409\n78#2:430\n84#2:434\n103#2:435\n99#2,6:436\n119#2,4:443\n216#3,2:410\n216#3,2:412\n216#3,2:414\n216#3,2:416\n1788#4,4:418\n1788#4,4:422\n1869#4,2:426\n1869#4,2:428\n1563#4:431\n1634#4,2:432\n1636#4:442\n1617#4,9:447\n1869#4:456\n1870#4:458\n1626#4:459\n1869#4,2:460\n1#5:457\n*S KotlinDebug\n*F\n+ 1 ContainerClicker.kt\norg/anti_ad/mc/ipnext/inventory/ContainerClicker\n*L\n126#1:406\n134#1:407\n134#1:408\n142#1:409\n347#1:430\n350#1:434\n350#1:435\n350#1:436,6\n358#1:443,4\n150#1:410,2\n158#1:412,2\n209#1:414,2\n215#1:416,2\n271#1:418,4\n272#1:422,4\n280#1:426,2\n287#1:428,2\n347#1:431\n347#1:432,2\n347#1:442\n360#1:447,9\n360#1:456\n360#1:458\n360#1:459\n360#1:460,2\n360#1:457\n*E\n"
)
public final class ContainerClicker {
   @NotNull
   public static final ContainerClicker INSTANCE = new ContainerClicker();
   private static boolean doSendContentUpdates = true;
   @NotNull
   private static final Set highlights;

   private ContainerClicker() {
   }

   public final void leftClick(int var1) {
      this.click(var1, 0);
   }

   public final void rightClick(int var1) {
      this.click(var1, 1);
   }

   public final void shiftClick(int var1) {
      this.genericClick(var1, 0, ClickType.QUICK_MOVE);
   }

   public final void qClick(int var1) {
      this.genericClick(var1, 1, ClickType.THROW);
   }

   private final void swapClick(int var1, int var2) {
      GeneralInventoryActions.INSTANCE.cleanCursor();
      int var3;
      if ((var3 = var1 - 36) >= 0 ? var3 < 9 : false) {
         this.swap(var2, var1 - 36);
      } else {
         this.leftClick(var2);
         this.leftClick(var1);
         if (!ItemStackExtensionsKt.isEmpty(InventoryKt.vCursorStack())) {
            this.leftClick(var2);
         }
      }
   }

   public final void click(int var1, int var2) {
      this.genericClick(var1, var2, ClickType.PICKUP);
   }

   public final void swap(int var1, int var2) {
      this.genericClick(var1, var2, ClickType.SWAP);
   }

   public final boolean getDoSendContentUpdates() {
      return doSendContentUpdates;
   }

   public final void setDoSendContentUpdates(boolean var1) {
      doSendContentUpdates = var1;
   }

   public final void genericClick(int var1, int var2, @NotNull ClickType var3) {
      Intrinsics.checkNotNullParameter(var3, "");
      this.genericClick(Vanilla.INSTANCE.container(), var1, var2, var3, doSendContentUpdates);
   }

   public final void genericClick(@NotNull AbstractContainerMenu var1, int var2, int var3, @NotNull ClickType var4, boolean var5) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var4, "");
      Log.INSTANCE.trace("Clicking: on slot: " + var2 + " with button: " + var3 + ", and action type: " + var4.name());
      if (var1 instanceof ItemPickerMenu) {
         InventoryMenu var10 = Vanilla.INSTANCE.playerContainer();
         Player var8 = (Player)Vanilla.INSTANCE.player();
         var10.clicked(var2, var3, var4, var8);
         if (var5) {
            this.sendContentUpdates();
         }
      } else {
         MultiPlayerGameMode var6 = Vanilla.INSTANCE.interactionManager();
         int var7 = var1.containerId;
         Player var9 = (Player)Vanilla.INSTANCE.player();
         var6.handleInventoryMouseClick(var7, var2, var3, var4, var9);
      }
   }

   public final void sendContentUpdates() {
      Vanilla.INSTANCE.playerContainer().broadcastChanges();
   }

   public final void executeQClicks(@NotNull Map var1, int var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (var2 != 0) {
         AbstractContainerMenu var17 = Vanilla.INSTANCE.container();
         ObjectRef var4;
         (var4 = new ObjectRef()).element = Vanilla.INSTANCE.screen();
         Iterator var10 = var1.entrySet().iterator();
         ContainerClicker$Highlight var5 = new ContainerClicker$Highlight(-1);
         highlights.add(var5);
         long var6 = var2;
         Timer var15 = TimersKt.timer(null, false);
         var15.schedule(new ContainerClicker$executeQClicks$$inlined$timer$default$1(var17, var5, var4, var10), 0L, var6);
      } else if (Vanilla.INSTANCE.container() instanceof ItemPickerMenu) {
         doSendContentUpdates = false;
         Iterator var9 = var1.entrySet().iterator();

         while (var9.hasNext()) {
            Entry var13;
            int var16 = ((Number)(var13 = (Entry)var9.next()).getKey()).intValue();
            Slot var14 = (Slot)var13.getValue();
            LockSlotsHandler.INSTANCE.setLastMouseClickSlot(var14);
            INSTANCE.qClick(var16);
            LockSlotsHandler.INSTANCE.setLastMouseClickSlot(null);
         }

         this.sendContentUpdates();
         doSendContentUpdates = true;
      } else {
         Iterator var8 = var1.entrySet().iterator();

         while (var8.hasNext()) {
            Entry var11;
            int var3 = ((Number)(var11 = (Entry)var8.next()).getKey()).intValue();
            Slot var12 = (Slot)var11.getValue();
            LockSlotsHandler.INSTANCE.setLastMouseClickSlot(var12);
            INSTANCE.qClick(var3);
            LockSlotsHandler.INSTANCE.setLastMouseClickSlot(null);
         }
      }
   }

   public final void executeSwapClicks(@NotNull Map var1, int var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (var2 != 0) {
         AbstractContainerMenu var16 = Vanilla.INSTANCE.container();
         ObjectRef var4;
         (var4 = new ObjectRef()).element = Vanilla.INSTANCE.screen();
         Iterator var11 = var1.entrySet().iterator();
         ContainerClicker$Highlight var5 = new ContainerClicker$Highlight(-1);
         ContainerClicker$Highlight var18 = new ContainerClicker$Highlight(-1);
         highlights.add(var5);
         highlights.add(var18);
         long var7 = var2;
         Timer var14 = TimersKt.timer(null, false);
         var14.schedule(new ContainerClicker$executeSwapClicks$$inlined$timer$default$1(var16, var5, var18, var4, var11), 0L, var7);
      } else if (Vanilla.INSTANCE.container() instanceof ItemPickerMenu) {
         doSendContentUpdates = false;
         Iterator var10 = var1.entrySet().iterator();

         while (var10.hasNext()) {
            Entry var17;
            var2 = ((Number)(var17 = (Entry)var10.next()).getKey()).intValue();
            int var15 = ((Number)var17.getValue()).intValue();
            INSTANCE.swapClick(var2, var15);
         }

         this.sendContentUpdates();
         doSendContentUpdates = true;
      } else {
         Iterator var9 = var1.entrySet().iterator();

         while (var9.hasNext()) {
            Entry var6;
            var2 = ((Number)(var6 = (Entry)var9.next()).getKey()).intValue();
            int var3 = ((Number)var6.getValue()).intValue();
            LockSlotsHandler.INSTANCE.setLastMouseClickSlot(Vanilla.INSTANCE.container().getSlot(var2));
            INSTANCE.swapClick(var2, var3);
         }
      }
   }

   public final void executeClicks(@NotNull List var1, int var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Iterable var4;
      int var10000;
      if ((var4 = var1) instanceof Collection && ((Collection)var4).isEmpty()) {
         var10000 = 0;
      } else {
         int var3 = 0;
         Iterator var5 = var4.iterator();

         while (var5.hasNext()) {
            if (((Number)((Pair)var5.next()).getSecond()).intValue() == 0) {
               if (++var3 < 0) {
                  CollectionsKt.throwCountOverflow();
               }
            }
         }

         var10000 = var3;
      }

      int var10 = var10000;
      if ((var4 = var1) instanceof Collection && ((Collection)var4).isEmpty()) {
         var10000 = 0;
      } else {
         int var17 = 0;
         Iterator var6 = var4.iterator();

         while (var6.hasNext()) {
            if (((Number)((Pair)var6.next()).getSecond()).intValue() == 1) {
               if (++var17 < 0) {
                  CollectionsKt.throwCountOverflow();
               }
            }
         }

         var10000 = var17;
      }

      int var13 = var10000;
      this.logClicks(var1.size(), var10, var13, var2);
      if (var2 != 0) {
         AbstractContainerMenu var16 = Vanilla.INSTANCE.container();
         ObjectRef var11;
         (var11 = new ObjectRef()).element = Vanilla.INSTANCE.screen();
         Iterator var20 = var1.iterator();
         ContainerClicker$Highlight var21 = new ContainerClicker$Highlight(-1);
         highlights.add(var21);
         long var7 = var2;
         Timer var9 = TimersKt.timer(null, false);
         var9.schedule(new ContainerClicker$executeClicks$$inlined$timer$default$1(var16, var21, var11, var20), 0L, var7);
      } else if (Vanilla.INSTANCE.container() instanceof ItemPickerMenu) {
         doSendContentUpdates = false;

         for (Pair var15 : var1) {
            INSTANCE.click(((Number)var15.getFirst()).intValue(), ((Number)var15.getSecond()).intValue());
         }

         this.sendContentUpdates();
         doSendContentUpdates = true;
      } else {
         for (Pair var14 : var1) {
            INSTANCE.click(((Number)var14.getFirst()).intValue(), ((Number)var14.getSecond()).intValue());
         }
      }
   }

   private final void logClicks(int var1, int var2, int var3, int var4) {
      Log.INSTANCE.debug("Click count total " + var1 + ". " + var2 + " left. " + var3 + " right. Time = " + var1 * var4 / 1000.0 + "s");
   }

   private final Map getSlotLocations() {
      Screen var1;
      AbstractContainerScreen var10000 = (var1 = Vanilla.INSTANCE.screen()) instanceof AbstractContainerScreen ? (AbstractContainerScreen)var1 : null;
      if (var10000 == null) {
         return MapsKt.emptyMap();
      }

      AbstractContainerScreen var6 = var10000;
      NonNullList var10 = Vanilla.INSTANCE.container().slots;
      Intrinsics.checkNotNullExpressionValue(var10, "");
      Iterable var2;
      Iterable var3 = var2 = (Iterable & List)var10;
      var2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var2, 10));

      for (Slot var4 : var3) {
         var4 = var4;
         ArrayList var5 = var2;
         var5.add(TuplesKt.to(InventoryKt.vPlayerSlotOf(var4, (Screen)var6).index, new Point(var4.x, var4.y)));
      }

      return MapsKt.toMap((Iterable & List)var2);
   }

   private final void drawHighlight(NativeContext var1) {
      Screen var3;
      AbstractContainerScreen var10000 = (var3 = Vanilla.INSTANCE.screen()) instanceof AbstractContainerScreen ? (AbstractContainerScreen)var3 : null;
      if (var10000 != null) {
         AbstractContainerScreen var7 = var10000;
         Point var2 = new Rectangle(var7.getGuiLeft(), var7.getGuiTop(), var7.getXSize(), var7.getYSize()).getTopLeft();
         Map var8 = this.getSlotLocations();
         Iterable var4 = highlights;
         var var5 = new ArrayList();

         for (ContainerClicker$Highlight var6 : var4) {
            Point var13 = (Point)var8.get(var6.getId());
            if (var13 != null) {
               Point var12 = var13;
               var5.add(var12);
            }
         }

         for (Point var9 : var5) {
            RectKt.rFillRect(var1, new Rectangle(var2.plus(var9), new Size(16, 16)), ColorKt.alpha(-1, 0.5F));
         }
      }
   }

   public final void postScreenRender(@NotNull NativeContext var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (ModSettings.INSTANCE.getHIGHLIGHT_CLICKING_SLOT().getBooleanValue() && !highlights.isEmpty()) {
         this.drawHighlight(var1);
      }
   }

   static {
      KeySetView var10000 = ConcurrentHashMap.newKeySet();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      highlights = var10000;
   }
}
