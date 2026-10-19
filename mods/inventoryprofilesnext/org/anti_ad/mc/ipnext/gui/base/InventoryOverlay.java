package org.anti_ad.mc.ipnext.gui.base;

import java.util.Map;
import java.util.Set;
import kotlin.jvm.JvmField;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import org.anti_ad.mc.common.gui.NativeContext;
import org.anti_ad.mc.common.math2d.Point;
import org.anti_ad.mc.common.math2d.Rectangle;
import org.anti_ad.mc.common.vanilla.render.glue.IdentifierHolder;
import org.anti_ad.mc.common.vanilla.render.glue.Sprite;
import org.jetbrains.annotations.NotNull;

public interface InventoryOverlay {
   @NotNull
   InventoryOverlay$Companion Companion = InventoryOverlay$Companion.$$INSTANCE;
   @JvmField
   @NotNull
   IdentifierHolder COMP_TEXTURE = new IdentifierHolder("inventoryprofilesnext", "textures/gui/overlay_new.png");
   @JvmField
   @NotNull
   Sprite compBackgroundSprite = new Sprite(COMP_TEXTURE, new Rectangle(40, 8, 32, 32));
   @JvmField
   @NotNull
   Point internal8x8 = new Point(8, 8);

   boolean getEnabledForeground();

   boolean getEnabledBackground();

   @NotNull
   IdentifierHolder getTEXTURE();

   @NotNull
   Sprite getBackgroundSprite();

   @NotNull
   Point getEightByEight();

   @NotNull
   Map getSlotLocations();

   void onForegroundRender(@NotNull NativeContext var1);

   void onBackgroundRender(@NotNull NativeContext var1);

   void onPostRender(@NotNull NativeContext var1);

   void processSwipe(@NotNull Set var1, @NotNull AbstractContainerScreen var2, int var3);

   void postRender(@NotNull NativeContext var1);

   void drawForeground(@NotNull NativeContext var1);

   void drawConfig(@NotNull NativeContext var1);

   void drawBackground(@NotNull NativeContext var1);
}
