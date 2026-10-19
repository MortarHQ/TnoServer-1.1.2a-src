package org.anti_ad.mc.ipnext.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.math2d.Point;
import org.anti_ad.mc.common.math2d.Rectangle;
import org.anti_ad.mc.common.vanilla.Vanilla;
import org.anti_ad.mc.common.vanilla.render.ColorKt;
import org.anti_ad.mc.common.vanilla.render.glue.IdentifierHolder;
import org.anti_ad.mc.common.vanilla.render.glue.RectKt;
import org.anti_ad.mc.common.vanilla.render.glue.Sprite;
import org.anti_ad.mc.ipnext.config.ModSettings;
import org.anti_ad.mc.ipnext.gui.base.InventoryOverlay;
import org.anti_ad.mc.ipnext.gui.base.InventoryOverlay$DefaultImpls;
import org.anti_ad.mc.ipnext.ingame.InventoryKt;
import org.anti_ad.mc.ipnext.ingame.VanillaAccessorsKt$(itemType)$1;
import org.anti_ad.mc.ipnext.item.ItemStack$Companion;
import org.anti_ad.mc.ipnext.item.ItemStackExtensionsKt;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.anti_ad.mc.ipnext.item.ItemTypeExtensionsKt$EMPTY$1;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nSlotHighlightHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SlotHighlightHandler.kt\norg/anti_ad/mc/ipnext/event/SlotHighlightHandler\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 5 ItemTypeExtensions.kt\norg/anti_ad/mc/ipnext/item/ItemTypeExtensionsKt\n*L\n1#1,163:1\n78#2:164\n88#2:175\n71#2:176\n66#2,7:177\n84#2:184\n103#2:185\n99#2,6:186\n119#2,4:195\n111#2,7:199\n88#2:206\n71#2:207\n66#2,7:208\n88#2:215\n71#2:216\n66#2,7:217\n1617#3,9:165\n1869#3:174\n1870#3:193\n1626#3:194\n1#4:192\n88#5,4:224\n88#5,4:228\n*S KotlinDebug\n*F\n+ 1 SlotHighlightHandler.kt\norg/anti_ad/mc/ipnext/event/SlotHighlightHandler\n*L\n52#1:164\n55#1:175\n55#1:176\n55#1:177,7\n56#1:184\n56#1:185\n56#1:186,6\n125#1:195,4\n147#1:199,7\n148#1:206\n148#1:207\n148#1:208,7\n151#1:215\n151#1:216\n151#1:217,7\n52#1:165,9\n52#1:174\n52#1:193\n52#1:194\n52#1:192\n159#1:224,4\n45#1:228,4\n*E\n"
)
public final class SlotHighlightHandler implements InventoryOverlay {
   @NotNull
   public static final SlotHighlightHandler INSTANCE = new SlotHighlightHandler();
   @NotNull
   private static ItemType toHighlight;
   private static int ticksSinceLastFocusChange = 3;
   private static int tick;
   private static int alphaChannel = 10;
   private static int step = 10;

   private SlotHighlightHandler() {
   }

   @NotNull
   @Override
   public final Map getSlotLocations() {
      Screen var1;
      AbstractContainerScreen var10000 = (var1 = Vanilla.INSTANCE.screen()) instanceof AbstractContainerScreen ? (AbstractContainerScreen)var1 : null;
      if (var10000 == null) {
         return MapsKt.emptyMap();
      }

      AbstractContainerScreen var8 = var10000;
      NonNullList var14 = Vanilla.INSTANCE.container().slots;
      Intrinsics.checkNotNullExpressionValue(var14, "");
      var var2 = (Iterable & List)var14;
      var var3 = new ArrayList();
      Iterator var9 = var2.iterator();

      while (var9.hasNext()) {
         Slot var4;
         Slot var5;
         ItemStack var15 = (var5 = InventoryKt.vPlayerSlotOf(var4 = (Slot)var9.next(), (Screen)var8)).getItem();
         Intrinsics.checkNotNullExpressionValue(var15, "");
         ItemStack var7 = var15;
         org.anti_ad.mc.ipnext.item.ItemStack var16;
         if (var15.isEmpty()) {
            var16 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
         } else {
            ItemStack$Companion var17 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
            ItemStack var6 = var7;
            Item var10003 = var6.getItem();
            Intrinsics.checkNotNullExpressionValue(var10003, "");
            DataComponentMap var10004 = var6.getComponents();
            Intrinsics.checkNotNull(var10004);
            var16 = var17.invoke(
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
               var7.getCount()
            );
         }

         label39: {
            if (!ItemStackExtensionsKt.isEmpty(var16)) {
               ItemStack var18 = var5.getItem();
               Intrinsics.checkNotNullExpressionValue(var18, "");
               var7 = var18;
               org.anti_ad.mc.ipnext.item.ItemStack var19;
               if (var18.isEmpty()) {
                  var19 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
               } else {
                  ItemStack$Companion var20 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
                  ItemStack var11 = var7;
                  Item var23 = var11.getItem();
                  Intrinsics.checkNotNullExpressionValue(var23, "");
                  DataComponentMap var24 = var11.getComponents();
                  Intrinsics.checkNotNull(var24);
                  var19 = var20.invoke(
                     new ItemType(
                        var23,
                        (PatchedDataComponentMap)var24,
                        var11.getComponentsPatch(),
                        new VanillaAccessorsKt$(itemType)$1(var11),
                        false,
                        false,
                        null,
                        112,
                        null
                     ),
                     var7.getCount()
                  );
               }

               if (Intrinsics.areEqual(ItemType.copy$default(var19.getItemType(), null, null, null, null, true, false, null, 111, null), toHighlight)) {
                  Integer var22 = var5.index;
                  Slot var12 = var4;
                  var21 = TuplesKt.to(var22, new Point(var12.x, var12.y));
                  break label39;
               }
            }

            var21 = null;
         }

         if (var21 != null) {
            Pair var10 = var21;
            var3.add(var10);
         }
      }

      return MapsKt.toMap(var3);
   }

   @Override
   public final boolean getEnabledBackground() {
      return ModSettings.INSTANCE.getHIGHLIGHT_FOUSED_ITEMS().getValue() && !ModSettings.INSTANCE.getHIGHLIGHT_FOUSED_ITEMS_FOREGROUND().getValue();
   }

   @Override
   public final void drawBackground(@NotNull NativeContext var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.drawSprite(var1);
   }

   @Override
   public final boolean getEnabledForeground() {
      return ModSettings.INSTANCE.getHIGHLIGHT_FOUSED_ITEMS().getValue() && ModSettings.INSTANCE.getHIGHLIGHT_FOUSED_ITEMS_FOREGROUND().getValue();
   }

   @Override
   public final void drawForeground(@NotNull NativeContext var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      this.drawSprite(var1);
   }

   @Override
   public final void drawConfig(@NotNull NativeContext var1) {
      Intrinsics.checkNotNullParameter(var1, "");
   }

   @Override
   public final void postRender(@NotNull NativeContext var1) {
      Intrinsics.checkNotNullParameter(var1, "");
   }

   public final int getDefaultAlpha() {
      return ModSettings.INSTANCE.getHIGHLIGHT_FOUSED_ITEMS_FOREGROUND().getValue()
         ? ColorKt.getAlpha(ModSettings.INSTANCE.getHIGHLIGHT_FOUSED_ITEMS_COLOR().getValue())
         : ColorKt.getAlpha(ModSettings.INSTANCE.getHIGHLIGHT_FOUSED_ITEMS_BG_COLOR().getValue());
   }

   public final int getColor() {
      return ModSettings.INSTANCE.getHIGHLIGHT_FOUSED_ITEMS_FOREGROUND().getBooleanValue()
         ? ModSettings.INSTANCE.getHIGHLIGHT_FOUSED_ITEMS_COLOR().getValue()
         : ModSettings.INSTANCE.getHIGHLIGHT_FOUSED_ITEMS_BG_COLOR().getValue();
   }

   public final int getTick() {
      return tick;
   }

   public final void setTick(int var1) {
      tick = var1;
   }

   public final int getAlphaChannel() {
      return alphaChannel;
   }

   public final void setAlphaChannel(int var1) {
      alphaChannel = var1;
   }

   public final int getStep() {
      return step;
   }

   public final void setStep(int var1) {
      step = var1;
   }

   private final void drawSprite(NativeContext var1) {
      Screen var4;
      AbstractContainerScreen var10000 = (var4 = Vanilla.INSTANCE.screen()) instanceof AbstractContainerScreen ? (AbstractContainerScreen)var4 : null;
      if (var10000 != null) {
         AbstractContainerScreen var2 = var10000;
         Map var3;
         (var3 = new LinkedHashMap()).putAll(this.getSlotLocations());
         if (!var3.isEmpty()) {
            if (ModSettings.INSTANCE.getHIGHLIGHT_FOUSED_ITEMS_ANIMATED().getBooleanValue()) {
               if (++tick > 0) {
                  tick = 0;
                  step = (alphaChannel = alphaChannel + step) > this.getDefaultAlpha() ? -10 : (alphaChannel < 10 ? 10 : step);
               }
            } else {
               alphaChannel = this.getDefaultAlpha();
            }

            Point var8 = new Rectangle(var2.getGuiLeft(), var2.getGuiTop(), var2.getXSize(), var2.getYSize()).getTopLeft();
            Iterator var5 = var3.entrySet().iterator();

            while (var5.hasNext()) {
               Point var6 = (Point)((Entry)var5.next()).getValue();
               Point var7 = var8.plus(var6);
               RectKt.rFillRect(var1, new Rectangle(var7.getX(), var7.getY(), 16, 16), ColorKt.alpha(this.getColor(), alphaChannel));
            }
         } else {
            tick = 0;
            alphaChannel = 10;
            step = 10;
         }
      }
   }

   public final void onTickInGame() {
      if (ModSettings.INSTANCE.getHIGHLIGHT_FOUSED_ITEMS().getBooleanValue()) {
         Screen var1;
         AbstractContainerScreen var10000 = (var1 = Vanilla.INSTANCE.screen()) instanceof AbstractContainerScreen ? (AbstractContainerScreen)var1 : null;
         if (var10000 == null) {
            return;
         }

         label48: {
            var10000 = (var1 = (Screen & AbstractContainerScreen)var10000) instanceof AbstractContainerScreen ? (AbstractContainerScreen)var1 : null;
            if (var10000 != null) {
               Slot var9 = var10000.getSlotUnderMouse();
               if (var9 != null) {
                  var10 = InventoryKt.vPlayerSlotOf(var9, var1);
                  break label48;
               }
            }

            var10 = null;
         }

         Slot var5 = var10;
         if (var10 != null) {
            ItemType var11 = toHighlight;
            ItemStack var10001 = var5.getItem();
            Intrinsics.checkNotNullExpressionValue(var10001, "");
            ItemStack var2 = var10001;
            org.anti_ad.mc.ipnext.item.ItemStack var15;
            if (var10001.isEmpty()) {
               var15 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
            } else {
               ItemStack$Companion var16 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
               ItemStack var3 = var2;
               Item var10004 = var3.getItem();
               Intrinsics.checkNotNullExpressionValue(var10004, "");
               DataComponentMap var10005 = var3.getComponents();
               Intrinsics.checkNotNull(var10005);
               var15 = var16.invoke(
                  new ItemType(
                     var10004,
                     (PatchedDataComponentMap)var10005,
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

            if (!Intrinsics.areEqual(var11, var15.getItemType())) {
               if ((ticksSinceLastFocusChange += -1) >= 0) {
                  return;
               }

               ItemStack var12 = var5.getItem();
               Intrinsics.checkNotNullExpressionValue(var12, "");
               var2 = var12;
               org.anti_ad.mc.ipnext.item.ItemStack var13;
               if (var12.isEmpty()) {
                  var13 = ItemStackExtensionsKt.getEMPTY(org.anti_ad.mc.ipnext.item.ItemStack.Companion);
               } else {
                  ItemStack$Companion var14 = org.anti_ad.mc.ipnext.item.ItemStack.Companion;
                  ItemStack var7 = var2;
                  Item var10003 = var7.getItem();
                  Intrinsics.checkNotNullExpressionValue(var10003, "");
                  DataComponentMap var17 = var7.getComponents();
                  Intrinsics.checkNotNull(var17);
                  var13 = var14.invoke(
                     new ItemType(
                        var10003,
                        (PatchedDataComponentMap)var17,
                        var7.getComponentsPatch(),
                        new VanillaAccessorsKt$(itemType)$1(var7),
                        false,
                        false,
                        null,
                        112,
                        null
                     ),
                     var2.getCount()
                  );
               }

               toHighlight = ItemType.copy$default(var13.getItemType(), null, null, null, null, true, false, null, 111, null);
            }

            ticksSinceLastFocusChange = ModSettings.INSTANCE.getHIGHLIGHT_FOUSED_WAIT_TICKS().getIntegerValue();
            return;
         }

         Item var10002 = Items.AIR;
         Intrinsics.checkNotNullExpressionValue(Items.AIR, "");
         toHighlight = new ItemType(
            var10002,
            new PatchedDataComponentMap(Items.AIR.components()),
            DataComponentPatch.EMPTY,
            ItemTypeExtensionsKt$EMPTY$1.INSTANCE,
            false,
            false,
            null,
            112,
            null
         );
      }
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
      Item var10002 = Items.AIR;
      Intrinsics.checkNotNullExpressionValue(Items.AIR, "");
      toHighlight = new ItemType(
         var10002,
         new PatchedDataComponentMap(Items.AIR.components()),
         DataComponentPatch.EMPTY,
         ItemTypeExtensionsKt$EMPTY$1.INSTANCE,
         false,
         false,
         null,
         112,
         null
      );
   }
}
