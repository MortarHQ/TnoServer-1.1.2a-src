package org.anti_ad.mc.ipnext.gui.inject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.gui.TooltipsManager;
import org.anti_ad.mc.common.gui.screen.BaseScreen;
import org.anti_ad.mc.common.gui.widgets.Widget;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.ipn.api.IPNButton;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.config.GuiSettings;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.event.LockSlotsHandler;
import org.anti_ad.mc.ipnext.event.SlotHighlightHandler;
import org.anti_ad.mc.ipnext.event.autorefill.AutoRefillHandler;
import org.anti_ad.mc.ipnext.event.villagers.VillagerTradeManager;
import org.anti_ad.mc.ipnext.gui.inject.base.InsertableWidget;
import org.anti_ad.mc.ipnext.gui.inject.base.SettingsWidget;
import org.anti_ad.mc.ipnext.integration.HintClassData;
import org.anti_ad.mc.ipnext.integration.HintsManagerNG;
import org.anti_ad.mc.ipnext.inventory.ContainerClicker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nContainerScreenEventHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ContainerScreenEventHandler.kt\norg/anti_ad/mc/ipnext/gui/inject/ContainerScreenEventHandler\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,158:1\n1#2:159\n1869#3,2:160\n1869#3:162\n1869#3,2:163\n1870#3:165\n1869#3,2:166\n1869#3,2:168\n1869#3,2:170\n*S KotlinDebug\n*F\n+ 1 ContainerScreenEventHandler.kt\norg/anti_ad/mc/ipnext/gui/inject/ContainerScreenEventHandler\n*L\n80#1:160,2\n89#1:162\n90#1:163,2\n89#1:165\n100#1:166,2\n118#1:168,2\n131#1:170,2\n*E\n"
)
public final class ContainerScreenEventHandler {
   @NotNull
   public static final ContainerScreenEventHandler INSTANCE = new ContainerScreenEventHandler();
   @Nullable
   private static List currentWidgets;

   private ContainerScreenEventHandler() {
   }

   @Nullable
   public final List getCurrentWidgets() {
      return currentWidgets;
   }

   public final void setCurrentWidgets(@Nullable List var1) {
      currentWidgets = var1;
   }

   public final void onScreenInit(@NotNull AbstractContainerScreen var1, @NotNull Function1 var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      if (Intrinsics.areEqual(var1, Vanilla.INSTANCE.screen())) {
         if (!Intrinsics.areEqual(var1.getClass().getName(), "tictim.paraglider.client.screen.BargainScreen")) {
            Log.INSTANCE.trace("Showing screen of type " + var1.getClass().getName());
            List var10 = new ArrayList();
            HintClassData var3 = HintsManagerNG.INSTANCE.getHints(var1.getClass());
            Log.INSTANCE.trace("Hints for '" + var1.getClass().getName() + "`: " + var3);
            boolean var4 = var3.getIgnore();
            EditorWidget var6;
            EditorWidget var10000 = var6 = new EditorWidget(var1, var3);
            var10000.addHintable(var10000);
            EditorWidget var5 = var6;
            SettingsWidget var7;
            SettingsWidget var8 = var7 = new SettingsWidget(var1, var3);
            var5.addHintable(var8);
            SettingsWidget var11 = var7;
            if (GuiSettings.INSTANCE.getENABLE_INVENTORY_BUTTONS().getBooleanValue() && !var4) {
               SortingButtonCollectionWidget var12;
               SortingButtonCollectionWidget var15 = var12 = new SortingButtonCollectionWidget(var1, var3);
               List var9 = var10;
               var5.addHintable(var15);
               var9.add(var12);
            }

            if (GuiSettings.INSTANCE.getENABLE_PROFILES_UI().getBooleanValue() && !var4 && !var3.hintFor(IPNButton.PROFILE_SELECTOR).getHide()) {
               ProfilesUICollectionWidget var13;
               ProfilesUICollectionWidget var16 = var13 = new ProfilesUICollectionWidget(var1, var3);
               List var18 = var10;
               var5.addHintable(var16);
               var18.add(var13);
            }

            if (ModSettings.INSTANCE.getVILLAGER_TRADING_ENABLE().getBooleanValue() && !var4 && var1 instanceof MerchantScreen) {
               VillagerOverlayWidget var14;
               VillagerOverlayWidget var17 = var14 = new VillagerOverlayWidget((MerchantScreen)var1, var3);
               List var19 = var10;
               var5.addHintable(var17);
               var19.add(var14);
            }

            var10.add(var11);
            var10.add(var5);
            if (var10.size() > 0) {
               currentWidgets = var10;
               InsertWidgetHandler.INSTANCE.insertWidget(currentWidgets);
            }
         }
      }
   }

   public final void showEditor() {
      if (currentWidgets != null) {
         Iterator var1 = currentWidgets.iterator();

         while (var1.hasNext()) {
            InsertableWidget var2;
            if ((var2 = (InsertableWidget)var1.next()) instanceof EditorWidget) {
               ((EditorWidget)var2).showEditorScreen();
            }
         }
      }
   }

   @NotNull
   public final Set getWidgetPoints() {
      Set var1 = new LinkedHashSet();
      if (currentWidgets != null) {
         Iterator var2 = currentWidgets.iterator();

         while (var2.hasNext()) {
            Iterator var3 = ((InsertableWidget)var2.next()).getSnapableList().iterator();

            while (var3.hasNext()) {
               Widget var4;
               if ((var4 = (Widget)var3.next()).getVisible()) {
                  var1.add(var4.getAbsoluteBounds());
               }
            }
         }
      }

      return var1;
   }

   private final void checkValid() {
      if (currentWidgets != null) {
         for (InsertableWidget var2 : currentWidgets) {
            Screen var3;
            BaseScreen var10000 = (var3 = Vanilla.INSTANCE.screen()) instanceof BaseScreen ? (BaseScreen)var3 : null;
            if (var10000 != null ? !var10000.hasParent((Screen)var2.getScreen()) : !Intrinsics.areEqual(var3, var2.getScreen())) {
               currentWidgets = null;
            }
         }
      }
   }

   public final void preRender() {
      this.checkValid();
   }

   public final void onBackgroundRender(@NotNull NativeContext var1, int var2, int var3, float var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      LockSlotsHandler.INSTANCE.onBackgroundRender(var1);
      SlotHighlightHandler.INSTANCE.onBackgroundRender(var1);
      AutoRefillHandler.INSTANCE.onBackgroundRender(var1);
      if (currentWidgets != null) {
         Iterator var5 = currentWidgets.iterator();

         while (var5.hasNext()) {
            ((InsertableWidget)var5.next()).postBackgroundRender(var1, var2, var3, var4);
         }
      }
   }

   public final void onForegroundRender(@NotNull NativeContext var1, int var2, int var3, float var4) {
      Intrinsics.checkNotNullParameter(var1, "");
      LockSlotsHandler.INSTANCE.onForegroundRender(var1);
      SlotHighlightHandler.INSTANCE.onForegroundRender(var1);
      AutoRefillHandler.INSTANCE.onForegroundRender(var1);
      if (currentWidgets != null) {
         Iterator var5 = currentWidgets.iterator();

         while (var5.hasNext()) {
            ((InsertableWidget)var5.next()).postForegroundRender(var1, var2, var3, var4);
         }
      }
   }

   public final void postRender(@NotNull NativeContext var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      LockSlotsHandler.INSTANCE.onPostRender(var1);
      SlotHighlightHandler.INSTANCE.onPostRender(var1);
      ContainerClicker.INSTANCE.postScreenRender(var1);
      if (currentWidgets != null) {
         TooltipsManager.INSTANCE.renderAll(var1);
      }
   }

   public final void onScreenRemoved(@NotNull AbstractContainerScreen var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (var1 instanceof MerchantScreen) {
         VillagerTradeManager.INSTANCE.setCurrentVillager(null);
      }

      if (currentWidgets != null) {
         currentWidgets.clear();
      }
   }
}
