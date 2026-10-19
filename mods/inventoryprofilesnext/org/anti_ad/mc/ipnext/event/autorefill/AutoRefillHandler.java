package org.anti_ad.mc.ipnext.event.autorefill;

import com.mojang.blaze3d.platform.Window;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntRange;
import kotlin.text.StringsKt;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.GameType;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.math2d.Point;
import org.anti_ad.mc.common.math2d.Rectangle;
import org.anti_ad.mc.common.math2d.Size;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.common.vanilla.VanillaUtil;
import org.anti_ad.mc.common.vanilla.render.glue.IdentifierHolder;
import org.anti_ad.mc.common.vanilla.render.glue.Sprite;
import org.anti_ad.mc.common.vanilla.render.glue.TextureKt;
import org.anti_ad.mc.ipnext.config.AutoRefillSettings;
import org.anti_ad.mc.ipnext.config.Hotkeys;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.event.MouseTracer;
import org.anti_ad.mc.ipnext.gui.base.InventoryOverlay;
import org.anti_ad.mc.ipnext.gui.base.InventoryOverlay$DefaultImpls;
import org.anti_ad.mc.ipnext.ingame.InventoryKt;
import org.anti_ad.mc.ipnext.item.ItemStack;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.parser.RefillSlotsLoader;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nAutoRefillHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AutoRefillHandler.kt\norg/anti_ad/mc/ipnext/event/autorefill/AutoRefillHandler\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n+ 5 common.kt\norg/anti_ad/mc/common/extensions/CommonKt\n+ 6 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,928:1\n161#1:949\n78#2:929\n97#2:932\n86#2:933\n84#2:938\n177#2,7:940\n177#2,7:950\n78#2:979\n84#2:990\n103#2:991\n99#2,6:992\n97#2:998\n86#2:999\n119#2,4:1003\n174#2:1007\n186#2:1008\n174#2:1009\n189#2:1010\n119#2,4:1011\n774#3:930\n865#3:931\n866#3:934\n1563#3:935\n1634#3,2:936\n1636#3:939\n1869#3,2:968\n1869#3,2:970\n1869#3,2:972\n1869#3,2:977\n1617#3,9:980\n1869#3:989\n1870#3:1001\n1626#3:1002\n295#3,2:1015\n131#4:947\n112#4:948\n70#5,11:957\n81#5,3:974\n1#6:1000\n*S KotlinDebug\n*F\n+ 1 AutoRefillHandler.kt\norg/anti_ad/mc/ipnext/event/autorefill/AutoRefillHandler\n*L\n187#1:949\n147#1:929\n149#1:932\n150#1:933\n151#1:938\n161#1:940,7\n187#1:950,7\n817#1:979\n820#1:990\n821#1:991\n821#1:992,6\n822#1:998\n823#1:999\n832#1:1003,4\n858#1:1007\n858#1:1008\n859#1:1009\n859#1:1010\n911#1:1011,4\n147#1:930\n147#1:931\n147#1:934\n151#1:935\n151#1:936,2\n151#1:939\n243#1:968,2\n244#1:970,2\n245#1:972,2\n801#1:977,2\n817#1:980,9\n817#1:989\n817#1:1001\n817#1:1002\n913#1:1015,2\n171#1:947\n171#1:948\n242#1:957,11\n242#1:974,3\n817#1:1000\n*E\n"
)
public final class AutoRefillHandler implements InventoryOverlay {
   @NotNull
   public static final AutoRefillHandler INSTANCE = new AutoRefillHandler();
   private static int mode;
   private static boolean clicked;
   @NotNull
   private static final Set disabledSlots = new LinkedHashSet();
   private static boolean screenOpening;
   @NotNull
   private static final Set profilesSwappedItems = new LinkedHashSet();
   private static boolean skipTick;
   @NotNull
   private static final Set blacklist = new LinkedHashSet();
   private static int ticksAfterUp = 16;
   @NotNull
   private static final List monitors = new ArrayList();
   private static final boolean enabledBackground;

   private AutoRefillHandler() {
   }

   private final List getAllIds() {
      Screen var1 = Vanilla.INSTANCE.screen();
      AbstractContainerMenu var2 = Vanilla.INSTANCE.container();
      List var3 = !(var1 instanceof InventoryScreen)
         ? CollectionsKt.toList((Iterable)(new IntRange(0, 8)))
         : CollectionsKt.plus(CollectionsKt.toList((Iterable)(new IntRange(0, 8))), CollectionsKt.toList((Iterable)(new IntRange(36, 40))));
      NonNullList var10000 = var2.slots;
      Intrinsics.checkNotNullExpressionValue(var2.slots, "");
      var var4 = (Iterable & List)var10000;
      var var9 = new ArrayList();
      Iterator var12 = var4.iterator();

      while (var12.hasNext()) {
         Object var5;
         Slot var6;
         Container var7;
         if ((var7 = InventoryKt.vPlayerSlotOf(var6 = (Slot)(var5 = var12.next()), var1).container) != null
            && var7 instanceof Inventory
            && var3.contains(var6.getSlotIndex())) {
            var9.add(var5);
         }
      }

      Iterable var10;
      var4 = var10 = var9;
      var var11 = new ArrayList(CollectionsKt.collectionSizeOrDefault(var10, 10));

      for (Object var15 : var4) {
         Slot var16 = (Slot)var15;
         ArrayList var8 = var11;
         var8.add(var16.index);
      }

      return var11;
   }

   @NotNull
   public final Sprite getForegroundSprite() {
      return new Sprite(this.getTEXTURE(), new Rectangle(8, 8, 32, 32)).right(1).down(2);
   }

   @NotNull
   public final Set getDisabledSlots() {
      return disabledSlots;
   }

   private final boolean getPressingDropKey() {
      Options var10000 = Vanilla.INSTANCE.mc().options;
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      KeyMapping var1 = var10000.keyDrop;
      Intrinsics.checkNotNullExpressionValue(var10000.keyDrop, "");
      return var1.isDown();
   }

   public final boolean getScreenOpening() {
      return screenOpening;
   }

   public final void setScreenOpening(boolean var1) {
      screenOpening = var1;
   }

   @NotNull
   public final Set getProfilesSwappedItems() {
      return profilesSwappedItems;
   }

   public final boolean getSkipTick() {
      return skipTick;
   }

   public final void setSkipTick(boolean var1) {
      skipTick = var1;
   }

   private final boolean isBlackListed(ItemStack var1) {
      Set var10000 = blacklist;
      ItemType var2 = var1.getItemType();
      ResourceLocation var10001 = BuiltInRegistries.ITEM.getKey(var2.getItem());
      Intrinsics.checkNotNullExpressionValue(var10001, "");
      String var3 = var10001.toString();
      Intrinsics.checkNotNullExpressionValue(var3, "");
      return var10000.contains(var3);
   }

   private final boolean getTempDisabledForDamageable() {
      return ticksAfterUp < 15;
   }

   public final void onTickInGame() {
      label50:
      if (!skipTick) {
         if (AutoRefillSettings.INSTANCE.getAUTO_REFILL_TEMP_DISABLE_REFILL_FOR_TOOLS().isPressing()) {
            ticksAfterUp = 0;
         } else if (ticksAfterUp < 16) {
            ticksAfterUp++;
         }

         label46:
         if (Vanilla.INSTANCE.screen() == null) {
            if (AutoRefillSettings.INSTANCE.getDISABLE_FOR_DROP_ITEM().getBooleanValue()) {
               Options var10000 = Vanilla.INSTANCE.mc().options;
               Intrinsics.checkNotNullExpressionValue(var10000, "");
               KeyMapping var3 = var10000.keyDrop;
               Intrinsics.checkNotNullExpressionValue(var10000.keyDrop, "");
               if (var3.isDown()) {
                  break label46;
               }
            }

            if (VanillaUtil.INSTANCE.inGame()) {
               if (screenOpening) {
                  screenOpening = false;
                  this.init();
               }

               this.handleAutoRefill();
            }
            break label50;
         }

         screenOpening = true;
      } else {
         skipTick = false;
      }

      Screen var1;
      AbstractContainerScreen var4 = (var1 = Vanilla.INSTANCE.screen()) instanceof AbstractContainerScreen ? (AbstractContainerScreen)var1 : null;
      if (var4 == null) {
         clicked = false;
      } else {
         AbstractContainerScreen var2 = var4;
         if (!Hotkeys.INSTANCE.getAUTO_REFILL_GUI_TOGGLE_FOR_SLOT().isPressing()) {
            clicked = false;
            RefillSlotsLoader.INSTANCE.save();
         } else {
            if (clicked) {
               this.processSwipe(disabledSlots, var2, mode);
            }
         }
      }
   }

   public final void onJoinWorld() {
      this.init();
   }

   public final void init() {
      monitors.clear();
      List var1;
      (var1 = new ArrayList()).add(new AutoRefillHandler$ItemSlotMonitor(AutoRefillHandler$WatchIds.INSTANCE.getMainHandSelected()));
      var1.add(new AutoRefillHandler$ItemSlotMonitor(AutoRefillHandler$WatchIds.INSTANCE.getOffHand()));
      if (AutoRefillSettings.INSTANCE.getREFILL_ARMOR().getBooleanValue()) {
         var1.add(new AutoRefillHandler$ItemSlotMonitor(AutoRefillHandler$WatchIds.INSTANCE.getHead()));
         var1.add(new AutoRefillHandler$ItemSlotMonitor(AutoRefillHandler$WatchIds.INSTANCE.getChest()));
         var1.add(new AutoRefillHandler$ItemSlotMonitor(AutoRefillHandler$WatchIds.INSTANCE.getLegs()));
         var1.add(new AutoRefillHandler$ItemSlotMonitor(AutoRefillHandler$WatchIds.INSTANCE.getFeet()));
      }

      ((AutoRefillHandler$ItemSlotMonitor)var1.get(0)).getAnothers().add(var1.get(1));
      CollectionsKt.addAll(((AutoRefillHandler$ItemSlotMonitor)var1.get(0)).getAnothers(), CollectionsKt.drop(var1, 2));
      ((AutoRefillHandler$ItemSlotMonitor)var1.get(1)).getAnothers().add(var1.get(0));
      CollectionsKt.addAll(((AutoRefillHandler$ItemSlotMonitor)var1.get(1)).getAnothers(), CollectionsKt.drop(var1, 2));
      monitors.addAll(var1);
   }

   @NotNull
   public final List getMonitors() {
      return monitors;
   }

   public final void handleAutoRefill() {
      try {
         Iterator var1 = monitors.iterator();

         while (var1.hasNext()) {
            ((AutoRefillHandler$ItemSlotMonitor)var1.next()).updateCurrent();
         }

         var1 = monitors.iterator();

         while (var1.hasNext()) {
            ((AutoRefillHandler$ItemSlotMonitor)var1.next()).checkShouldHandle();
         }

         var1 = monitors.iterator();

         while (var1.hasNext()) {
            ((AutoRefillHandler$ItemSlotMonitor)var1.next()).checkHandle();
         }
      } catch (Throwable var2) {
         var2.printStackTrace();
      }
   }

   public final void blackListChanged() {
      blacklist.clear();
      CharSequence var10000 = AutoRefillSettings.INSTANCE.getAUTOREFILL_BLACKLIST().getValue();
      String[] var1;
      (var1 = new String[1])[0] = ",";

      for (String var2 : StringsKt.split$default(var10000, var1, false, 0, 6, null)) {
         blacklist.add(StringsKt.trim(var2).toString());
      }
   }

   @Override
   public final boolean getEnabledForeground() {
      return ModSettings.INSTANCE.getENABLE_AUTO_REFILL().getValue()
         && AutoRefillSettings.INSTANCE.getAUTO_REFILL_ENABLE_INDICATOR_ICONS().getValue()
         && AutoRefillSettings.INSTANCE.getAUTO_REFILL_ENABLE_PER_SLOT_CONFIG().getValue();
   }

   @Override
   public final boolean getEnabledBackground() {
      return enabledBackground;
   }

   @NotNull
   @Override
   public final Map getSlotLocations() {
      Screen var2;
      AbstractContainerScreen var10000 = (var2 = Vanilla.INSTANCE.screen()) instanceof AbstractContainerScreen ? (AbstractContainerScreen)var2 : null;
      if (var10000 == null) {
         return MapsKt.emptyMap();
      }

      AbstractContainerScreen var1 = var10000;
      AbstractContainerMenu var10 = Vanilla.INSTANCE.container();
      if (var10 == null) {
         return MapsKt.emptyMap();
      }

      NonNullList var11 = var10.slots;
      Intrinsics.checkNotNullExpressionValue(var10.slots, "");
      var var6 = (Iterable & List)var11;
      var var3 = new ArrayList();
      Iterator var7 = var6.iterator();

      while (var7.hasNext()) {
         label38: {
            Slot var4;
            Slot var5 = InventoryKt.vPlayerSlotOf(var4 = (Slot)var7.next(), (Screen)var1);
            if (INSTANCE.getAllIds().contains(var5.index)) {
               Point var8 = new Point(var4.x, var4.y);
               if (var5.container != null) {
                  var12 = var5.container instanceof Inventory ? TuplesKt.to(var5.getSlotIndex(), var8) : null;
                  break label38;
               }
            }

            var12 = null;
         }

         if (var12 != null) {
            Pair var9 = var12;
            var3.add(var9);
         }
      }

      return MapsKt.toMap(var3);
   }

   @Override
   public final void drawForeground(@NotNull NativeContext var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (this.getEnabledForeground()) {
         Screen var3;
         AbstractContainerScreen var10000 = (var3 = Vanilla.INSTANCE.screen()) instanceof AbstractContainerScreen ? (AbstractContainerScreen)var3 : null;
         if (var10000 != null) {
            AbstractContainerScreen var6 = var10000;
            Point var2 = new Rectangle(var6.getGuiLeft(), var6.getGuiTop(), var6.getXSize(), var6.getYSize()).getTopLeft();
            Iterator var7 = this.getSlotLocations().entrySet().iterator();

            while (var7.hasNext()) {
               Entry var4;
               int var5 = ((Number)(var4 = (Entry)var7.next()).getKey()).intValue();
               Point var8 = (Point)var4.getValue();
               if (!disabledSlots.contains(var5)) {
                  Point var9 = var2.plus(var8).plus(this.getEightByEight());
                  TextureKt.rDrawCenteredSprite(var1, this.getForegroundSprite(), var9);
               }
            }
         }
      }
   }

   @Override
   public final void drawConfig(@NotNull NativeContext var1) {
      Intrinsics.checkNotNullParameter(var1, "");
   }

   @Override
   public final void postRender(@NotNull NativeContext var1) {
      Intrinsics.checkNotNullParameter(var1, "");
   }

   @Override
   public final void drawBackground(@NotNull NativeContext var1) {
      Intrinsics.checkNotNullParameter(var1, "");
   }

   private final void drawHotSprite(NativeContext var1) {
      if (AutoRefillSettings.INSTANCE.getAUTO_REFILL_ENABLE_PER_SLOT_CONFIG().getValue()
         && AutoRefillSettings.INSTANCE.getAUTO_REFILL_ENABLE_HORBAR_INDICATOR_ICONS().getValue()) {
         Window var10000 = Vanilla.INSTANCE.mc().getWindow();
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         int var2 = var10000.getGuiScaledWidth();
         var10000 = Vanilla.INSTANCE.mc().getWindow();
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         int var3 = var10000.getGuiScaledHeight();
         int var4 = var2 / 2;

         for (int var7 = 0; var7 < 9; var7++) {
            if (!disabledSlots.contains(var7)) {
               int var5 = var4 - 90 + var7 * 20 + 2;
               int var6 = var3 - 16 - 3;
               Point var8 = new Point(var5, var6).plus(this.getEightByEight());
               TextureKt.rDrawCenteredSprite(var1, this.getForegroundSprite(), var8);
            }
         }
      }
   }

   public final void postRenderHud(@NotNull NativeContext var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (this.getEnabledForeground() && GameType.SPECTATOR != Vanilla.INSTANCE.gameMode()) {
         this.drawHotSprite(var1);
      }
   }

   public final void preRenderHud(@NotNull NativeContext var1) {
      Intrinsics.checkNotNullParameter(var1, "");
   }

   public final boolean onInput(int var1, int var2) {
      if (!this.getEnabledForeground()) {
         return false;
      } else {
         Screen var3;
         AbstractContainerScreen var4 = (var3 = Vanilla.INSTANCE.screen()) instanceof AbstractContainerScreen ? (AbstractContainerScreen)var3 : null;
         if (VanillaUtil.INSTANCE.inGame() && var4 == null && Hotkeys.INSTANCE.getAUTO_REFILL_GAME_TOGGLE_FOR_SLOT().isActivated()) {
            var1 = InventoryKt.vMainhandIndex();
            this.toggleDisableRefillForSlot(var1);
            this.init();
            return true;
         } else {
            return var4 != null && this.onCancellableInput(var4);
         }
      }
   }

   private final void toggleDisableRefillForSlot(int var1) {
      if (disabledSlots.contains(var1)) {
         disabledSlots.remove(var1);
      } else {
         disabledSlots.add(var1);
      }

      RefillSlotsLoader.INSTANCE.save();
   }

   public final boolean onCancellableInput(@NotNull AbstractContainerScreen var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (Hotkeys.INSTANCE.getAUTO_REFILL_GUI_TOGGLE_FOR_SLOT().isPressing() != clicked) {
         Point var5 = new Rectangle(var1.getGuiLeft(), var1.getGuiTop(), var1.getXSize(), var1.getYSize()).getTopLeft().minus(new Size(1, 1));
         Iterator var2 = this.getSlotLocations().entrySet().iterator();

         Object var10000;
         while (true) {
            if (!var2.hasNext()) {
               var10000 = null;
               break;
            }

            Object var3;
            Point var4 = (Point)((Entry)(var3 = var2.next())).getValue();
            if (new Rectangle(var5.plus(var4), new Size(18, 18)).contains(MouseTracer.INSTANCE.getLocation())) {
               var10000 = var3;
               break;
            }
         }

         Entry var6;
         if ((var6 = (Entry)var10000) != null) {
            int var7 = ((Number)var6.getKey()).intValue();
            clicked = true;
            mode = disabledSlots.contains(var7) ? 1 : 0;
            return true;
         }
      }

      return false;
   }

   @NotNull
   @Override
   public final IdentifierHolder getTEXTURE() {
      return InventoryOverlay$DefaultImpls.getTEXTURE(this);
   }

   @NotNull
   @Override
   public final Sprite getBackgroundSprite() {
      return InventoryOverlay$DefaultImpls.getBackgroundSprite(this);
   }

   @NotNull
   @Override
   public final Point getEightByEight() {
      return InventoryOverlay$DefaultImpls.getEightByEight(this);
   }

   @Override
   public final void onForegroundRender(@NotNull NativeContext var1) {
      InventoryOverlay$DefaultImpls.onForegroundRender(this, var1);
   }

   @Override
   public final void onBackgroundRender(@NotNull NativeContext var1) {
      InventoryOverlay$DefaultImpls.onBackgroundRender(this, var1);
   }

   @Override
   public final void onPostRender(@NotNull NativeContext var1) {
      InventoryOverlay$DefaultImpls.onPostRender(this, var1);
   }

   @Override
   public final void processSwipe(@NotNull Set var1, @NotNull AbstractContainerScreen var2, int var3) {
      InventoryOverlay$DefaultImpls.processSwipe(this, var1, var2, var3);
   }
}
