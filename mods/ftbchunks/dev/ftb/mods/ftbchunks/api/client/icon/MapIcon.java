package dev.ftb.mods.ftbchunks.api.client.icon;

import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.math.MathUtils;
import dev.ftb.mods.ftblibrary.ui.BaseScreen;
import dev.ftb.mods.ftblibrary.ui.input.Key;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public interface MapIcon {
   Vec3 getPos(float var1);

   default boolean isVisible(MapType mapType, double distanceToPlayer, boolean outsideVisibleArea) {
      return !mapType.isWorldIcon();
   }

   default double getIconScale(MapType mapType) {
      return 1.0;
   }

   default boolean isIconOnEdge(MapType mapType, boolean outsideVisibleArea) {
      return false;
   }

   default boolean isZoomDependant(MapType mapType) {
      return false;
   }

   default int getPriority() {
      return 0;
   }

   default void addTooltip(TooltipList list) {
      Player player = Minecraft.getInstance().player;
      Vec3 pos = this.getPos(1.0F);
      list.styledString(String.format("%,d m", Mth.ceil(MathUtils.dist(player.getX(), player.getZ(), pos.x, pos.z))), ChatFormatting.GRAY);
   }

   boolean onMousePressed(BaseScreen var1, MouseButton var2);

   boolean onKeyPressed(BaseScreen var1, Key var2);

   void draw(MapType var1, GuiGraphics var2, int var3, int var4, int var5, int var6, boolean var7, int var8);

   class SimpleMapIcon implements MapIcon {
      protected final Vec3 pos;
      protected Icon icon;

      public SimpleMapIcon(Vec3 pos, Icon icon) {
         this.pos = pos;
         this.icon = icon;
      }

      public SimpleMapIcon(Vec3 pos) {
         this(pos, Color4I.empty());
      }

      @Override
      public Vec3 getPos(float partialTick) {
         return this.pos;
      }

      public Icon getIcon() {
         return this.icon;
      }

      public void setIcon(Icon icon) {
         this.icon = icon;
      }

      @Override
      public void draw(MapType mapType, GuiGraphics graphics, int x, int y, int w, int h, boolean outsideVisibleArea, int iconAlpha) {
         if (!this.icon.isEmpty()) {
            this.icon.draw(graphics, x, y, w, h);
         }
      }

      @Override
      public boolean onMousePressed(BaseScreen screen, MouseButton button) {
         return false;
      }

      @Override
      public boolean onKeyPressed(BaseScreen screen, Key key) {
         return false;
      }
   }
}
