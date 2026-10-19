package org.anti_ad.mc.ipnext.event;

import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.anti_ad.mc.common.input.KeybindSettings;
import org.anti_ad.mc.common.input.MainKeybind;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.config.GuiSettings;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.inventory.ContainerClicker;
import org.anti_ad.mc.ipnext.inventory.ContainerType;
import org.anti_ad.mc.ipnext.inventory.ContainerTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ContinuousCraftingHandler {
   @NotNull
   public static final ContinuousCraftingHandler INSTANCE = new ContinuousCraftingHandler();
   private static boolean afterRefill;
   private static int autoRefillRetry;
   private static int submitNextCraft;
   private static boolean processingClick;
   @Nullable
   private static AbstractContainerScreen trackingScreen;
   private static ContinuousCraftingHandler$IMonitor monitor;
   private static boolean isCrafting;
   private static int onCraftCount;
   private static boolean crafted;
   @NotNull
   private static final MainKeybind SHIFT = new MainKeybind("LEFT_SHIFT", KeybindSettings.Companion.getGUI_EXTRA());

   private ContinuousCraftingHandler() {
   }

   public final boolean getProcessingClick() {
      return processingClick;
   }

   public final void setProcessingClick(boolean var1) {
      processingClick = var1;
   }

   private final boolean getEnabled() {
      return GuiSettings.INSTANCE.getCONTINUOUS_CRAFTING_SAVED_VALUE().getBooleanValue();
   }

   public final void onTickInGame() {
      Screen var1 = Vanilla.INSTANCE.screen();
      if (this.getEnabled() && var1 instanceof AbstractContainerScreen) {
         if (!Intrinsics.areEqual(var1, trackingScreen)) {
            trackingScreen = (AbstractContainerScreen)var1;
            this.init();
         }

         this.handle();
      }
   }

   private final void init() {
      AbstractContainerMenu var1 = Vanilla.INSTANCE.container();
      if (isCrafting = ContainerTypes.getTypes$default(ContainerTypes.INSTANCE, var1, null, 2, null).contains(ContainerType.CRAFTING)) {
         monitor = switch (ModSettings.INSTANCE.getCONTINUOUS_CRAFTING_METHOD().getIntegerValue()) {
            case 1 -> {
               Log.INSTANCE.trace("Using DEFAULT Monitor");
               yield new ContinuousCraftingHandler$Monitor(var1);
            }
            case 2 -> {
               Log.INSTANCE.trace("Using OLD Monitor");
               yield new ContinuousCraftingHandler$MonitorP(var1);
            }
            default -> {
               Log.INSTANCE.error("Unexpected value so Using DEFAULT Monitor");
               yield new ContinuousCraftingHandler$Monitor(var1);
            }
         };
         onCraftCount = 0;
         crafted = false;
         processingClick = false;
      }
   }

   private final void handle() {
      if (isCrafting) {
         if (!processingClick && onCraftCount > 0) {
            onCraftCount += -1;
         }

         if (submitNextCraft > 0 && (submitNextCraft += -1) <= 0) {
            Vanilla.INSTANCE.queueForMainThread(ContinuousCraftingHandler::handle$lambda$0);
            submitNextCraft = 0;
         }

         if (afterRefill) {
            if (SHIFT.isPressing() && ModSettings.INSTANCE.getINCLUDE_HOTBAR_MODIFIER().isPressing()) {
               submitNextCraft = ModSettings.INSTANCE.getAUTO_CRAFT_DELAY().getIntegerValue();
            }

            afterRefill = false;
         }

         if (crafted) {
            if (!processingClick && onCraftCount <= 0) {
               ContinuousCraftingHandler$IMonitor var10000 = monitor;
               if (monitor == null) {
                  Intrinsics.throwUninitializedPropertyAccessException("");
                  var10000 = null;
               }

               if (var10000.autoRefill()) {
                  crafted = false;
                  onCraftCount = 0;
                  afterRefill = true;
                  autoRefillRetry = 0;
               } else if (autoRefillRetry < 6) {
                  autoRefillRetry++;
               } else {
                  autoRefillRetry = 0;
                  crafted = false;
                  onCraftCount = 0;
                  afterRefill = false;
               }
            } else {
               Log.INSTANCE.trace("Not refilling yet");
            }
         }

         if (!crafted) {
            ContinuousCraftingHandler$IMonitor var1 = monitor;
            if (monitor == null) {
               Intrinsics.throwUninitializedPropertyAccessException("");
               var1 = null;
            }

            var1.save();
         }
      }
   }

   public final void onCrafted() {
      if (isCrafting) {
         onCraftCount = RangesKt.coerceAtMost(onCraftCount + 2, 2);
         crafted = true;
         Log.INSTANCE.trace("crafted!!!!!!");
      }
   }

   private static final void handle$lambda$0() {
      ContainerClicker.INSTANCE.shiftClick(0);
   }
}
