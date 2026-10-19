package dev.ftb.mods.ftbchunks.client.mapicon;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.ftb.mods.ftbchunks.api.client.icon.MapIcon;
import dev.ftb.mods.ftbchunks.api.client.icon.MapType;
import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.EntityIconLoader;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.EntityIconLoader.EntityIconSettings;
import dev.ftb.mods.ftblibrary.icon.EntityIconLoader.WidthHeight;
import dev.ftb.mods.ftblibrary.ui.BaseScreen;
import dev.ftb.mods.ftblibrary.ui.input.Key;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import java.util.Map;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class EntityMapIcon implements MapIcon {
   private final Entity entity;
   private final Icon icon;
   private final WidthHeight widthHeight;

   public EntityMapIcon(Entity entity, Icon icon) {
      this.entity = entity;
      this.icon = icon;
      this.widthHeight = new WidthHeight(16, 16);
   }

   public EntityMapIcon(Entity entity, Icon icon, WidthHeight widthHeight) {
      this.entity = entity;
      this.icon = icon;
      this.widthHeight = widthHeight;
   }

   @Override
   public Vec3 getPos(float partialTick) {
      return partialTick >= 1.0F ? this.entity.position() : this.entity.getPosition(partialTick);
   }

   @Override
   public boolean isVisible(MapType mapType, double distanceToPlayer, boolean outsideVisibleArea) {
      return !mapType.isWorldIcon()
         && (this.entity instanceof AbstractClientPlayer || !outsideVisibleArea)
         && ((Map)FTBChunksClientConfig.ENTITY_ICON.get()).getOrDefault(this.entity.getType().arch$registryName().toString(), true);
   }

   @Override
   public double getIconScale(MapType mapType) {
      return (
            !(this.entity instanceof AbstractClientPlayer) && mapType.isMinimap() && !FTBChunksClientConfig.MINIMAP_LARGE_ENTITIES.get()
               ? 0.6666666666666666
               : 1.0
         )
         * EntityIconLoader.getSettings(this.entity.getType()).<Double>map(EntityIconSettings::scale).orElse(1.0);
   }

   @Override
   public boolean isZoomDependant(MapType mapType) {
      return !(this.entity instanceof AbstractClientPlayer);
   }

   @Override
   public int getPriority() {
      return this.entity instanceof LocalPlayer ? 150 : (this.entity instanceof AbstractClientPlayer ? 100 : 0);
   }

   @Override
   public void addTooltip(TooltipList list) {
      list.add(this.entity.getName());
   }

   @Override
   public boolean onMousePressed(BaseScreen screen, MouseButton button) {
      return false;
   }

   @Override
   public boolean onKeyPressed(BaseScreen screen, Key key) {
      return !(this.entity instanceof LocalPlayer) && StaticMapIcon.handleKeypress(this, screen, key);
   }

   @Override
   public void draw(MapType mapType, GuiGraphics graphics, int x, int y, int w, int h, boolean outsideVisibleArea, int iconAlpha) {
      if (this.entity instanceof AbstractClientPlayer
         && !mapType.isMinimap()
         && w >= 4
         && this.icon != EntityIconLoader.NORMAL
         && this.icon != EntityIconLoader.HOSTILE) {
         PoseStack poseStack = graphics.pose();
         poseStack.pushPose();
         poseStack.translate(x, y, 0.0F);
         poseStack.scale(w / 18.0F, h / 18.0F, 1.0F);
         Color4I.BLACK.draw(graphics, 0, 0, 18, 18);
         this.icon.draw(graphics, 1, 1, this.widthHeight.width(), this.widthHeight.height());
         poseStack.popPose();
      } else {
         this.icon.draw(graphics, x, y, w, h);
      }
   }
}
