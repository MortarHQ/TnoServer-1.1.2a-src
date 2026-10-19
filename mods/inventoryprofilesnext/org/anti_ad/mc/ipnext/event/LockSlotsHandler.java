package org.anti_ad.mc.ipnext.event;

import com.mojang.blaze3d.platform.Window;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.MutablePropertyReference1;
import kotlin.jvm.internal.MutablePropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KFunction;
import kotlin.reflect.KProperty;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.ItemLike;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.math2d.Point;
import org.anti_ad.mc.common.math2d.Rectangle;
import org.anti_ad.mc.common.math2d.Size;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.common.vanilla.render.glue.IdentifierHolder;
import org.anti_ad.mc.common.vanilla.render.glue.RectKt;
import org.anti_ad.mc.common.vanilla.render.glue.Sprite;
import org.anti_ad.mc.common.vanilla.render.glue.TextureKt;
import org.anti_ad.mc.ipnext.config.LockedSlotsSettings;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.config.SwitchType;
import org.anti_ad.mc.ipnext.gui.base.InventoryOverlay;
import org.anti_ad.mc.ipnext.gui.base.InventoryOverlay$DefaultImpls;
import org.anti_ad.mc.ipnext.ingame.InventoryKt;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt$(itemType)$1;
import org.anti_ad.mc.ipnext.integration.HintsManagerNG;
import org.anti_ad.mc.ipnext.item.ItemStack$Companion;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.specific.SmallHelpersKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nLockSlotsHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LockSlotsHandler.kt\norg/anti_ad/mc/ipnext/event/LockSlotsHandler\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 5 kt_common.kt\norg/anti_ad/mc/common/extensions/Kt_commonKt\n+ 6 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n+ 7 PropertyDelegates.kt\norg/anti_ad/mc/common/extensions/PropertyDelegatesKt\n+ 8 Delegates.kt\nkotlin/properties/Delegates\n*L\n1#1,455:1\n440#1,2:502\n442#1,2:505\n440#1,2:507\n442#1,2:510\n78#2:456\n103#2:467\n99#2,6:468\n97#2:474\n86#2:475\n97#2:479\n86#2:480\n119#2,4:485\n174#2:489\n186#2:490\n174#2:491\n189#2:492\n127#2:493\n127#2:494\n127#2:495\n119#2,4:496\n78#2:512\n88#2:513\n71#2:514\n66#2,7:515\n1617#3,9:457\n1869#3:466\n1870#3:477\n1626#3:478\n295#3,2:500\n1#4:476\n1#4:482\n1#4:484\n1#4:504\n1#4:509\n1#4:531\n130#5:481\n130#5:483\n98#6,9:522\n35#7:532\n38#7:536\n33#8,3:533\n*S KotlinDebug\n*F\n+ 1 LockSlotsHandler.kt\norg/anti_ad/mc/ipnext/event/LockSlotsHandler\n*L\n419#1:502,2\n419#1:505,2\n426#1:507,2\n426#1:510,2\n83#1:456\n86#1:467\n86#1:468,6\n87#1:474\n88#1:475\n109#1:479\n110#1:480\n222#1:485,4\n287#1:489\n287#1:490\n288#1:491\n288#1:492\n292#1:493\n294#1:494\n296#1:495\n357#1:496,4\n428#1:512\n430#1:513\n430#1:514\n430#1:515,7\n83#1:457,9\n83#1:466\n83#1:477\n83#1:478\n360#1:500,2\n83#1:476\n143#1:482\n161#1:484\n419#1:504\n426#1:509\n143#1:481\n161#1:483\n432#1:522,9\n99#1:532\n99#1:536\n99#1:533,3\n*E\n"
)
public final class LockSlotsHandler implements InventoryOverlay {
   @NotNull
   public static final LockSlotsHandler INSTANCE = new LockSlotsHandler();
   @Nullable
   private static Slot lastMouseClickSlot;
   @NotNull
   private static final Set lockedInvSlotsStoredValue = new LinkedHashSet();
   @NotNull
   private static final ReadWriteProperty displayingConfig$delegate;
   private static boolean clicked;
   private static int mode;
   @NotNull
   private static final Map qMoveSlotMapping;

   private LockSlotsHandler() {
   }

   @Nullable
   public final Slot getLastMouseClickSlot() {
      return lastMouseClickSlot;
   }

   public final void setLastMouseClickSlot(@Nullable Slot var1) {
      lastMouseClickSlot = var1;
   }

   @Override
   public final boolean getEnabledForeground() {
      return ModSettings.INSTANCE.getENABLE_LOCK_SLOTS().getBooleanValue() && !LockedSlotsSettings.INSTANCE.getLOCK_SLOTS_QUICK_DISABLE().isPressing();
   }

   @Override
   public final boolean getEnabledBackground() {
      return ModSettings.INSTANCE.getENABLE_LOCK_SLOTS().getBooleanValue() && LockedSlotsSettings.INSTANCE.getSHOW_LOCKED_SLOTS_BACKGROUND().getBooleanValue();
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
         Slot var4;
         Slot var5 = InventoryKt.vPlayerSlotOf(var4 = (Slot)var7.next(), (Screen)var1);
         Point var8 = new Point(var4.x, var4.y);
         Pair var12 = var5.container != null && var5.container instanceof Inventory ? TuplesKt.to(var5.getSlotIndex(), var8) : null;
         if (var12 != null) {
            Pair var9 = var12;
            var3.add(var9);
         }
      }

      return MapsKt.toMap(var3);
   }

   @NotNull
   public final Set getLockedInvSlotsStoredValue() {
      return lockedInvSlotsStoredValue;
   }

   @NotNull
   public final Iterable getLockedInvSlots() {
      return this.getEnabledForeground() ? lockedInvSlotsStoredValue : CollectionsKt.emptyList();
   }

   private final boolean getDisplayingConfig() {
      return (Boolean)displayingConfig$delegate.getValue(this, $$delegatedProperties[0]);
   }

   private final void setDisplayingConfig(boolean var1) {
      displayingConfig$delegate.setValue(this, $$delegatedProperties[0], var1);
   }

   public final boolean isSlotLocked(int var1) {
      return CollectionsKt.contains(this.getLockedInvSlots(), var1);
   }

   public final boolean isMappedSlotLocked(@NotNull Slot var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      Screen var2;
      AbstractContainerScreen var10000 = (var2 = Vanilla.INSTANCE.screen()) instanceof AbstractContainerScreen ? (AbstractContainerScreen)var2 : null;
      if (var10000 == null) {
         return false;
      }

      AbstractContainerScreen var4 = var10000;
      Slot var3;
      return (var3 = InventoryKt.vPlayerSlotOf(var1, (Screen)var4)).container instanceof Inventory
         ? CollectionsKt.contains(this.getLockedInvSlots(), var3.getSlotIndex())
         : false;
   }

   @NotNull
   public final Sprite getHotOutline() {
      return Sprite.down$default(this.getBackgroundSprite(), 0, 1, null);
   }

   @NotNull
   public final Sprite getHotOutlineLeft() {
      return this.getBackgroundSprite().right(2);
   }

   @NotNull
   public final Sprite getHotOutlineRight() {
      return this.getBackgroundSprite().right(3);
   }

   @NotNull
   public final Sprite getHotOutlineActive() {
      return Sprite.down$default(this.getBackgroundSprite(), 0, 1, null).right(2);
   }

   @NotNull
   public final Sprite getForegroundSprite() {
      return Sprite.right$default(this.getBackgroundSprite(), 0, 1, null)
         .down(LockedSlotsSettings.INSTANCE.getLOCKED_SLOTS_FOREGROUND_STYLE().getIntegerValue() - 1);
   }

   @NotNull
   public final Sprite getConfigSprite() {
      return Sprite.left$default(this.getBackgroundSprite(), 0, 1, null);
   }

   @NotNull
   public final Sprite getConfigSpriteLocked() {
      return Sprite.down$default(this.getConfigSprite(), 0, 1, null);
   }

   @Override
   public final void drawBackground(@NotNull NativeContext var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      Screen var10000 = Vanilla.INSTANCE.screen();
      if (var10000 != null) {
         Screen var2 = var10000;
         if (HintsManagerNG.INSTANCE.getHints(var2.getClass()).getIgnore()) {
            return;
         }
      }

      if (!this.getDisplayingConfig()) {
         if (LockedSlotsSettings.INSTANCE.getSHOW_LOCKED_SLOTS_BACKGROUND().getBooleanValue()) {
            this.drawSprite(var1, new LockSlotsHandler$drawBackground$2(this), null);
         }
      }
   }

   @Override
   public final void postRender(@NotNull NativeContext var1) {
      Intrinsics.checkNotNullParameter(var1, "");
   }

   @Override
   public final void drawForeground(@NotNull NativeContext var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      Screen var10000 = Vanilla.INSTANCE.screen();
      if (var10000 != null) {
         Screen var2 = var10000;
         if (HintsManagerNG.INSTANCE.getHints(var2.getClass()).getIgnore()) {
            return;
         }
      }

      if (LockedSlotsSettings.INSTANCE.getSHOW_LOCKED_SLOTS_FOREGROUND().getBooleanValue()) {
         this.drawSprite(var1, new LockSlotsHandler$drawForeground$2(this), null);
      }
   }

   @Override
   public final void drawConfig(@NotNull NativeContext var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (this.getDisplayingConfig()) {
         this.drawSprite(var1, new LockSlotsHandler$drawConfig$1(this), new LockSlotsHandler$drawConfig$2(this));
      }
   }

   private final void drawBackgroundSprite(NativeContext var1, Point var2, Point var3) {
      var2 = var2.plus(var3);
      RectKt.rFillRect(
         var1, var2.getX(), var2.getY(), var2.getX() + 16, var2.getY() + 16, LockedSlotsSettings.INSTANCE.getSHOW_LOCKED_SLOTS_BG_COLOR().getValue()
      );
   }

   private final void drawForegroundSprite(NativeContext var1, Point var2, Point var3) {
      var2 = var2.plus(var3).plus(this.getEightByEight());
      TextureKt.rDrawCenteredSprite(SmallHelpersKt.getAsOverlayContext(var1), this.getForegroundSprite(), var2);
   }

   private final void drawConfigLocked(NativeContext var1, Point var2, Point var3) {
      this.drawBackgroundSprite(var1, var2, var3);
   }

   private final void drawConfigOpen(NativeContext var1, Point var2, Point var3) {
      var2 = var2.plus(var3).plus(this.getEightByEight());
      TextureKt.rDrawCenteredSprite(var1, this.getConfigSprite(), var2);
   }

   private final void drawSprite(NativeContext var1, Function3 var2, Function3 var3) {
      Screen var5;
      AbstractContainerScreen var10000 = (var5 = Vanilla.INSTANCE.screen()) instanceof AbstractContainerScreen ? (AbstractContainerScreen)var5 : null;
      if (var10000 != null) {
         AbstractContainerScreen var8 = var10000;
         Point var4 = new Rectangle(var8.getGuiLeft(), var8.getGuiTop(), var8.getXSize(), var8.getYSize()).getTopLeft();
         Iterator var9 = this.getSlotLocations().entrySet().iterator();

         while (var9.hasNext()) {
            Entry var6;
            int var7 = ((Number)(var6 = (Entry)var9.next()).getKey()).intValue();
            Point var10 = (Point)var6.getValue();
            if (lockedInvSlotsStoredValue.contains(var7)) {
               if (var2 != null) {
                  var2.invoke(var1, var4, var10);
               }
            } else if (var3 != null) {
               var3.invoke(var1, var4, var10);
            }
         }
      }
   }

   private final void drawHotOutlineLeft(NativeContext var1, Point var2, int var3) {
      RectKt.rFillRect(var1, var2.getX() - 2, var2.getY() - 2, var2.getX() + 16, var2.getY(), var3);
      RectKt.rFillRect(var1, var2.getX() - 2, var2.getY() + 16, var2.getX() + 16, var2.getY() + 18, var3);
      RectKt.rFillRect(var1, var2.getX() - 2, var2.getY(), var2.getX(), var2.getY() + 16, var3);
   }

   private final void drawHotOutlineRight(NativeContext var1, Point var2, int var3) {
      RectKt.rFillRect(var1, var2.getX(), var2.getY() - 2, var2.getX() + 16, var2.getY(), var3);
      RectKt.rFillRect(var1, var2.getX(), var2.getY() + 16, var2.getX() + 16, var2.getY() + 18, var3);
      RectKt.rFillRect(var1, var2.getX() + 16, var2.getY() - 2, var2.getX() + 18, var2.getY() + 18, var3);
   }

   private final void drawHotOutlineActive(NativeContext var1, Point var2, int var3) {
      RectKt.rFillRect(var1, var2.getX() - 4, var2.getY() - 4, var2.getX() + 20, var2.getY(), var3);
      RectKt.rFillRect(var1, var2.getX() - 4, var2.getY(), var2.getX(), var2.getY() + 16, var3);
      RectKt.rFillRect(var1, var2.getX() - 4, var2.getY() + 16, var2.getX() + 20, var2.getY() + 20, var3);
      RectKt.rFillRect(var1, var2.getX() + 16, var2.getY(), var2.getX() + 20, var2.getY() + 16, var3);
   }

   private final void drawHotOutline(NativeContext var1, Point var2, int var3) {
      RectKt.rFillRect(var1, var2.getX() - 2, var2.getY() - 2, var2.getX() + 16, var2.getY(), var3);
      RectKt.rFillRect(var1, var2.getX() - 2, var2.getY() + 16, var2.getX() + 16, var2.getY() + 18, var3);
      RectKt.rFillRect(var1, var2.getX() - 2, var2.getY(), var2.getX(), var2.getY() + 16, var3);
      RectKt.rFillRect(var1, var2.getX() + 16, var2.getY() - 2, var2.getX() + 18, var2.getY() + 18, var3);
   }

   private final void drawHotSprite(NativeContext var1) {
      if (this.getEnabledForeground()) {
         Window var10000 = Vanilla.INSTANCE.mc().getWindow();
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         int var2 = var10000.getGuiScaledWidth();
         var10000 = Vanilla.INSTANCE.mc().getWindow();
         Intrinsics.checkNotNullExpressionValue(var10000, "");
         int var3 = var10000.getGuiScaledHeight();
         int var4 = var2 / 2;

         for (int var8 = 0; var8 < 9; var8++) {
            if (lockedInvSlotsStoredValue.contains(var8)) {
               KFunction var5 = var8 - Vanilla.INSTANCE.playerInventory().selected == -1
                  ? (KFunction)(new LockSlotsHandler$drawHotSprite$drawLockedSprite$1(this))
                  : (
                     var8 - Vanilla.INSTANCE.playerInventory().selected == 1
                        ? (KFunction)(new LockSlotsHandler$drawHotSprite$drawLockedSprite$2(this))
                        : (
                           var8 == Vanilla.INSTANCE.playerInventory().selected
                              ? (KFunction)(new LockSlotsHandler$drawHotSprite$drawLockedSprite$3(this))
                              : (KFunction)(new LockSlotsHandler$drawHotSprite$drawLockedSprite$4(this))
                        )
                  );
               int var6 = var4 - 90 + var8 * 20 + 2;
               int var7 = var3 - 16 - 3;
               Point var9;
               Point var10 = (var9 = new Point(var6, var7)).plus(new Point(8, 8));
               ((Function3)var5).invoke(var1, var9, LockedSlotsSettings.INSTANCE.getSHOW_LOCKED_SLOTS_HOTBAR_COLOR().getValue());
               if (LockedSlotsSettings.INSTANCE.getSHOW_LOCKED_SLOTS_FOREGROUND().getBooleanValue()) {
                  TextureKt.rDrawCenteredSprite(var1, this.getForegroundSprite(), 0, var10);
               }
            }
         }
      }
   }

   public final boolean getClicked() {
      return clicked;
   }

   public final void setClicked(boolean var1) {
      clicked = var1;
   }

   public final int getMode() {
      return mode;
   }

   public final void setMode(int var1) {
      mode = var1;
   }

   public final void onTickInGame() {
      if (this.getEnabledForeground()) {
         Screen var1;
         AbstractContainerScreen var10000 = (var1 = Vanilla.INSTANCE.screen()) instanceof AbstractContainerScreen ? (AbstractContainerScreen)var1 : null;
         if (var10000 == null) {
            this.setDisplayingConfig(false);
            clicked = false;
         } else {
            AbstractContainerScreen var2 = var10000;
            if (clicked) {
               this.processSwipe(lockedInvSlotsStoredValue, var2, mode);
            }
         }
      }
   }

   public final boolean onCancellableInput() {
      if (!this.getEnabledForeground()) {
         return false;
      }

      Screen var1;
      AbstractContainerScreen var10000 = (var1 = Vanilla.INSTANCE.screen()) instanceof AbstractContainerScreen ? (AbstractContainerScreen)var1 : null;
      if (var10000 == null) {
         return false;
      }

      AbstractContainerScreen var5 = var10000;
      switch ((SwitchType)LockedSlotsSettings.INSTANCE.getLOCK_SLOTS_CONFIG_SWITCH_TYPE().getValue()) {
         case TOGGLE:
            if (LockedSlotsSettings.INSTANCE.getLOCK_SLOTS_SWITCH_CONFIG_MODIFIER().isActivated()) {
               this.setDisplayingConfig(!this.getDisplayingConfig());
            }
            break;
         case HOLD:
            this.setDisplayingConfig(
               LockedSlotsSettings.INSTANCE.getLOCK_SLOTS_SWITCH_CONFIG_MODIFIER().isPressing()
                  || LockedSlotsSettings.INSTANCE.getLOCK_SLOTS_QUICK_CONFIG_KEY().isPressing()
            );
            break;
         default:
            throw new NoWhenBranchMatchedException();
      }

      boolean var2;
      if ((
            var2 = this.getDisplayingConfig() && LockedSlotsSettings.INSTANCE.getLOCK_SLOTS_CONFIG_KEY().isPressing()
               || LockedSlotsSettings.INSTANCE.getLOCK_SLOTS_QUICK_CONFIG_KEY().isPressing()
         )
         != clicked) {
         if (!var2) {
            clicked = false;
            return true;
         }

         Point var6 = new Rectangle(var5.getGuiLeft(), var5.getGuiTop(), var5.getXSize(), var5.getYSize()).getTopLeft().minus(new Size(1, 1));
         Iterator var8 = this.getSlotLocations().entrySet().iterator();

         while (true) {
            if (!var8.hasNext()) {
               var10 = null;
               break;
            }

            Object var3;
            Point var4 = (Point)((Entry)(var3 = var8.next())).getValue();
            if (new Rectangle(var6.plus(var4), new Size(18, 18)).contains(MouseTracer.INSTANCE.getLocation())) {
               var10 = var3;
               break;
            }
         }

         Entry var7;
         if ((var7 = (Entry)var10) != null) {
            int var9 = ((Number)var7.getKey()).intValue();
            clicked = true;
            mode = lockedInvSlotsStoredValue.contains(var9) ? 1 : 0;
            return true;
         }
      }

      return false;
   }

   public final boolean isQMoveActionAllowed(int var1) {
      LockSlotsHandler var2 = this;
      switch (var1) {
         case -999:
         case -1:
            return true;
         default:
            boolean var10000;
            if (lastMouseClickSlot != null) {
               Slot var3 = lastMouseClickSlot;
               var10000 = INSTANCE.isMappedSlotLocked(var3);
            } else {
               var10000 = CollectionsKt.contains(var2.getLockedInvSlots(), qMoveSlotMapping.get(var1));
            }

            return !var10000
               ? true
               : !LockedSlotsSettings.INSTANCE.getLOCK_SLOTS_DISABLE_USER_INTERACTION().getValue()
                  && !LockedSlotsSettings.INSTANCE.getLOCKED_SLOTS_DISABLE_QUICK_MOVE_THROW().getValue();
      }
   }

   public final boolean isHotbarQMoveActionAllowed(int var1, boolean var2) {
      LockSlotsHandler var3 = this;
      switch (var1) {
         case -999:
         case -1:
            return true;
         default:
            boolean var10000;
            if (lastMouseClickSlot != null) {
               Slot var8 = lastMouseClickSlot;
               var10000 = INSTANCE.isMappedSlotLocked(var8);
            } else {
               var10000 = CollectionsKt.contains(var3.getLockedInvSlots(), qMoveSlotMapping.get(var1));
            }

            if (!var10000) {
               return true;
            } else {
               if (var2 && LockedSlotsSettings.INSTANCE.getLOCKED_SLOTS_DISABLE_THROW_FOR_NON_STACKABLE().getValue()) {
                  NonNullList var9 = ((AbstractContainerMenu)Vanilla.INSTANCE.playerContainer()).slots;
                  Intrinsics.checkNotNullExpressionValue(var9, "");
                  List var6 = (List)var9;
                  if (var1 <= var6.size()) {
                     ItemStack var10 = ((Slot)var6.get(var1)).getItem();
                     Intrinsics.checkNotNullExpressionValue(var10, "");
                     ItemStack var4 = var10;
                     org.anti_ad.mc.ipnext.item.ItemStack var11;
                     if (var10.isEmpty()) {
                        var11 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
                     } else {
                        ItemStack$Companion var12 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
                        ItemStack var7 = var4;
                        Item var10003 = var7.getItem();
                        Intrinsics.checkNotNullExpressionValue(var10003, "");
                        DataComponentMap var10004 = var7.getComponents();
                        Intrinsics.checkNotNull(var10004);
                        var11 = var12.invoke(
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
                           var4.getCount()
                        );
                     }

                     ItemType var5 = var11.getItemType();
                     ItemStack var13 = new ItemStack;
                     ItemLike var10002 = (ItemLike)var5.getItem();
                     PatchedDataComponentMap var14 = var5.getTag();
                     if (var14 == null) {
                        DataComponentMap var15 = DataComponentMap.EMPTY;
                        Intrinsics.checkNotNull(DataComponentMap.EMPTY);
                        var14 = (PatchedDataComponentMap)var15;
                     }

                     var13./* $VF: Unable to resugar constructor */<init>(var10002, 1, var14);
                     if (var13.getMaxStackSize() > 1) {
                        return true;
                     }

                     return false;
                  }
               }

               return true;
            }
      }
   }

   private final boolean isQMoveActionAllowedInt(int var1, Function0 var2) {
      switch (var1) {
         case -999:
         case -1:
            return true;
         default:
            boolean var10000;
            if (lastMouseClickSlot != null) {
               Slot var3 = lastMouseClickSlot;
               var10000 = INSTANCE.isMappedSlotLocked(var3);
            } else {
               var10000 = CollectionsKt.contains(this.getLockedInvSlots(), qMoveSlotMapping.get(var1));
            }

            return !var10000 ? true : (Boolean)var2.invoke();
      }
   }

   public final void postRenderHud(@NotNull NativeContext var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (LockedSlotsSettings.INSTANCE.getALSO_SHOW_LOCKED_SLOTS_IN_HOTBAR().getValue() && GameType.SPECTATOR != Vanilla.INSTANCE.gameMode()) {
         this.drawHotSprite(var1);
      }
   }

   public final void preRenderHud(@NotNull NativeContext var1) {
      Intrinsics.checkNotNullParameter(var1, "");
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

   static {
      KProperty[] var0;
      (var0 = new KProperty[1])[0] = Reflection.mutableProperty1(
         (MutablePropertyReference1)(new MutablePropertyReference1Impl(LockSlotsHandler.class, "displayingConfig", "getDisplayingConfig()Z", 0))
      );
      $$delegatedProperties = var0;
      Boolean var1 = Boolean.FALSE;
      displayingConfig$delegate = (ReadWriteProperty)(new LockSlotsHandler$special$$inlined$detectable$1(var1));
      Pair[] var2;
      (var2 = new Pair[42])[0] = TuplesKt.to(36, 0);
      var2[1] = TuplesKt.to(37, 1);
      var2[2] = TuplesKt.to(38, 2);
      var2[3] = TuplesKt.to(39, 3);
      var2[4] = TuplesKt.to(40, 4);
      var2[5] = TuplesKt.to(41, 5);
      var2[6] = TuplesKt.to(42, 6);
      var2[7] = TuplesKt.to(43, 7);
      var2[8] = TuplesKt.to(44, 8);
      var2[9] = TuplesKt.to(27, 27);
      var2[10] = TuplesKt.to(28, 28);
      var2[11] = TuplesKt.to(29, 29);
      var2[12] = TuplesKt.to(30, 30);
      var2[13] = TuplesKt.to(31, 31);
      var2[14] = TuplesKt.to(32, 32);
      var2[15] = TuplesKt.to(33, 33);
      var2[16] = TuplesKt.to(34, 34);
      var2[17] = TuplesKt.to(35, 35);
      var2[18] = TuplesKt.to(18, 18);
      var2[19] = TuplesKt.to(19, 19);
      var2[20] = TuplesKt.to(20, 20);
      var2[21] = TuplesKt.to(21, 21);
      var2[22] = TuplesKt.to(22, 22);
      var2[23] = TuplesKt.to(23, 23);
      var2[24] = TuplesKt.to(24, 24);
      var2[25] = TuplesKt.to(25, 25);
      var2[26] = TuplesKt.to(26, 26);
      var2[27] = TuplesKt.to(9, 9);
      var2[28] = TuplesKt.to(10, 10);
      var2[29] = TuplesKt.to(10, 10);
      var2[30] = TuplesKt.to(11, 11);
      var2[31] = TuplesKt.to(12, 12);
      var2[32] = TuplesKt.to(13, 13);
      var2[33] = TuplesKt.to(14, 14);
      var2[34] = TuplesKt.to(15, 15);
      var2[35] = TuplesKt.to(16, 16);
      var2[36] = TuplesKt.to(17, 17);
      var2[37] = TuplesKt.to(8, 36);
      var2[38] = TuplesKt.to(45, 40);
      var2[39] = TuplesKt.to(7, 37);
      var2[40] = TuplesKt.to(6, 38);
      var2[41] = TuplesKt.to(5, 39);
      qMoveSlotMapping = MapsKt.mapOf(var2);
   }
}
