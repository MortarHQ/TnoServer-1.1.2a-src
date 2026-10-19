package org.anti_ad.mc.ipnext.gui.base;

import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.math2d.Line;
import org.anti_ad.mc.common.math2d.LineKt;
import org.anti_ad.mc.common.math2d.Point;
import org.anti_ad.mc.common.math2d.Rectangle;
import org.anti_ad.mc.common.math2d.Size;
import org.anti_ad.mc.common.vanilla.render.glue.IdentifierHolder;
import org.anti_ad.mc.common.vanilla.render.glue.Sprite;
import org.anti_ad.mc.ipnext.event.MouseTracer;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nInventoryOverlay.kt\nKotlin\n*S Kotlin\n*F\n+ 1 InventoryOverlay.kt\norg/anti_ad/mc/ipnext/gui/base/InventoryOverlay$DefaultImpls\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n*L\n1#1,102:1\n119#2,4:103\n*S KotlinDebug\n*F\n+ 1 InventoryOverlay.kt\norg/anti_ad/mc/ipnext/gui/base/InventoryOverlay$DefaultImpls\n*L\n81#1:103,4\n*E\n"
)
public final class InventoryOverlay$DefaultImpls {
   @NotNull
   public static IdentifierHolder getTEXTURE(@NotNull InventoryOverlay var0) {
      return InventoryOverlay.COMP_TEXTURE;
   }

   @NotNull
   public static Sprite getBackgroundSprite(@NotNull InventoryOverlay var0) {
      return InventoryOverlay.compBackgroundSprite;
   }

   @NotNull
   public static Point getEightByEight(@NotNull InventoryOverlay var0) {
      return InventoryOverlay.internal8x8;
   }

   public static void onForegroundRender(@NotNull InventoryOverlay var0, @NotNull NativeContext var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (var0.getEnabledForeground()) {
         var0.drawConfig(var1);
         var0.drawForeground(var1);
      }
   }

   public static void onBackgroundRender(@NotNull InventoryOverlay var0, @NotNull NativeContext var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (var0.getEnabledBackground()) {
         var0.drawBackground(var1);
      }
   }

   public static void onPostRender(@NotNull InventoryOverlay var0, @NotNull NativeContext var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      if (var0.getEnabledForeground() || var0.getEnabledBackground()) {
         var0.postRender(var1);
      }
   }

   public static void processSwipe(@NotNull InventoryOverlay var0, @NotNull Set var1, @NotNull AbstractContainerScreen var2, int var3) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      Line var4 = MouseTracer.INSTANCE.getAsLine();
      AbstractContainerScreen var5 = var2;
      Point var9 = new Rectangle(var5.getGuiLeft(), var5.getGuiTop(), var5.getXSize(), var5.getYSize()).getTopLeft().minus(new Size(1, 1));
      Iterator var10 = var0.getSlotLocations().entrySet().iterator();

      while (var10.hasNext()) {
         Entry var7;
         int var6 = ((Number)(var7 = (Entry)var10.next()).getKey()).intValue();
         Point var8 = (Point)var7.getValue();
         if (var3 == 0 == !var1.contains(var6) && LineKt.intersects(var4, new Rectangle(var9.plus(var8), new Size(18, 18)))) {
            if (var3 == 0) {
               var1.add(var6);
            } else {
               var1.remove(var6);
            }
         }
      }
   }
}
