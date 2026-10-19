package dev.ftb.mods.ftbchunks.client.mapicon;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftbchunks.api.client.icon.MapType;
import dev.ftb.mods.ftbchunks.api.client.icon.WaypointIcon;
import dev.ftb.mods.ftbchunks.client.gui.LargeMapScreen;
import dev.ftb.mods.ftbchunks.client.gui.WaypointAddScreen;
import dev.ftb.mods.ftbchunks.client.gui.WaypointShareMenu;
import dev.ftb.mods.ftbchunks.client.map.WaypointImpl;
import dev.ftb.mods.ftbchunks.net.TeleportFromMapPacket;
import dev.ftb.mods.ftblibrary.config.ColorConfig;
import dev.ftb.mods.ftblibrary.config.StringConfig;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.icon.ImageIcon;
import dev.ftb.mods.ftblibrary.icon.ItemIcon;
import dev.ftb.mods.ftblibrary.math.MathUtils;
import dev.ftb.mods.ftblibrary.ui.BaseScreen;
import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftblibrary.ui.ColorSelectorPanel;
import dev.ftb.mods.ftblibrary.ui.ContextMenuItem;
import dev.ftb.mods.ftblibrary.ui.input.Key;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Items;

public class WaypointMapIcon extends StaticMapIcon implements WaypointIcon {
   private final WaypointImpl waypoint;
   private Icon outsideIcon;
   private int alpha;

   public WaypointMapIcon(WaypointImpl waypoint) {
      super(waypoint.getPos());
      this.waypoint = waypoint;
      this.outsideIcon = Color4I.empty();
   }

   @Override
   public int getAlpha() {
      return this.alpha;
   }

   @Override
   public void setAlpha(int alpha) {
      this.alpha = alpha;
   }

   @Override
   public Color4I getColor() {
      return Color4I.rgb(this.waypoint.getColor());
   }

   @Override
   public boolean isVisible(MapType mapType, double distanceToPlayer, boolean outsideVisibleArea) {
      return !outsideVisibleArea && !(distanceToPlayer > this.waypoint.getDrawDistance(mapType.isMinimap()))
         ? !mapType.isWorldIcon() || distanceToPlayer >= 0.5
         : false;
   }

   @Override
   public boolean isIconOnEdge(MapType mapType, boolean outsideVisibleArea) {
      return !outsideVisibleArea;
   }

   @Override
   public int getPriority() {
      return 1000;
   }

   @Override
   public void addTooltip(TooltipList list) {
      list.add(this.waypoint.getDisplayName());
      super.addTooltip(list);
   }

   @Override
   public boolean onMousePressed(BaseScreen screen, MouseButton button) {
      if (super.onMousePressed(screen, button)) {
         return true;
      } else if (button.isRight() && screen instanceof LargeMapScreen lms) {
         this.openWPContextMenu(lms);
         return true;
      } else {
         return false;
      }
   }

   private void openWPContextMenu(LargeMapScreen screen) {
      List<ContextMenuItem> contextMenu = new ArrayList<>();
      contextMenu.add(this.makeTitleMenuItem());
      contextMenu.add(ContextMenuItem.SEPARATOR);
      LocalPlayer player = Minecraft.getInstance().player;
      WaypointShareMenu.makeShareMenu(player, this.waypoint).ifPresent(contextMenu::add);
      contextMenu.add(new ContextMenuItem(Component.translatable("gui.rename"), Icons.CHAT, b -> {
         StringConfig config = new StringConfig();
         config.setValue(this.waypoint.getName());
         config.onClicked(b, MouseButton.LEFT, accepted -> {
            if (accepted) {
               this.waypoint.setName((String)config.getValue());
            }

            screen.openGui();
         });
      }));
      if (this.waypoint.getType().canChangeColor()) {
         contextMenu.add(new ContextMenuItem(Component.translatable("ftbchunks.gui.change_color"), Icons.COLOR_RGB, btn -> {
            ColorConfig col = new ColorConfig();
            col.setValue(Color4I.rgb(this.waypoint.getColor()));
            ColorSelectorPanel.popupAtMouse(btn.getGui(), col, accepted -> {
               if (accepted) {
                  this.waypoint.setColor(((Color4I)col.getValue()).rgba());
                  this.icon = Color4I.empty();
                  this.outsideIcon = Color4I.empty();
                  this.checkIcon();
               }
            });
         }));
      }

      contextMenu.add(new ContextMenuItem(Component.translatable("ftbchunks.label." + (this.waypoint.isHidden() ? "show" : "hide")), Icons.BEACON, b -> {
         this.waypoint.setHidden(!this.waypoint.isHidden());
         screen.refreshWidgets();
      }));
      if (player.hasPermissions(2)) {
         contextMenu.add(new ContextMenuItem(Component.translatable("ftbchunks.gui.teleport"), ItemIcon.getItemIcon(Items.ENDER_PEARL), b -> {
            NetworkManager.sendToServer(new TeleportFromMapPacket(this.waypoint.getPos().above(), false, this.waypoint.getDimension()));
            screen.closeGui(false);
         }));
      }

      contextMenu.add(
         new ContextMenuItem(
            Component.translatable("ftbchunks.gui.edit"),
            Icons.SETTINGS,
            b -> {
               StringConfig configName = new StringConfig();
               configName.setValue(this.waypoint.getName());
               new WaypointAddScreen(
                     configName, GlobalPos.of(this.waypoint.getDimension(), this.waypoint.getPos()), Color4I.rgb(this.waypoint.getColor()), true
                  )
                  .openGui();
            }
         )
      );
      contextMenu.add(new ContextMenuItem(Component.translatable("gui.remove"), Icons.REMOVE, b -> {
         this.waypoint.removeFromManager();
         screen.refreshIcons();
      }));
      screen.openContextMenu(contextMenu);
   }

   private ContextMenuItem makeTitleMenuItem() {
      return new ContextMenuItem(this.waypoint.getDisplayName(), this.icon, null) {
         public Icon getIcon() {
            return WaypointMapIcon.this.icon;
         }
      };
   }

   @Override
   public boolean onKeyPressed(BaseScreen screen, Key key) {
      if (super.onKeyPressed(screen, key)) {
         return true;
      } else if (key.is(261) && screen instanceof LargeMapScreen lms) {
         this.waypoint.removeFromManager();
         lms.refreshIcons();
         return true;
      } else {
         return false;
      }
   }

   public void checkIcon() {
      if (this.icon.isEmpty() || this.outsideIcon.isEmpty()) {
         Color4I tint = Color4I.rgb(this.waypoint.getColor()).withAlpha(this.waypoint.isHidden() ? 130 : 255);
         this.icon = this.waypoint.getType().getIcon().withTint(tint);
         this.outsideIcon = this.waypoint.getType().getOutsideIcon().withTint(tint);
      }
   }

   @Override
   public void draw(MapType mapType, GuiGraphics graphics, int x, int y, int w, int h, boolean outsideVisibleArea, int iconAlpha) {
      this.checkIcon();
      Icon toDraw = !outsideVisibleArea && !mapType.isWorldIcon() ? this.icon : this.outsideIcon;
      if (iconAlpha < 255 && toDraw instanceof ImageIcon img) {
         img.withColor(img.color.withAlpha(iconAlpha)).draw(graphics, x, y, w, h);
      } else {
         toDraw.draw(graphics, x, y, w, h);
      }

      if (!outsideVisibleArea && mapType.isWorldIcon()) {
         Minecraft mc = Minecraft.getInstance();
         String ds = Mth.ceil(MathUtils.dist(this.pos.x, this.pos.y, this.pos.z, mc.player.getX(), mc.player.getY(), mc.player.getZ())) + " m";
         int nw = mc.font.width(this.waypoint.getDisplayName());
         int dw = mc.font.width(ds);
         Color4I.DARK_GRAY.withAlpha(200).draw(graphics, x + (w - nw) / 2 - 2, y - 14, nw + 4, 12);
         Color4I.DARK_GRAY.withAlpha(200).draw(graphics, x + (w - dw) / 2 - 2, y + 18, dw + 4, 12);
         graphics.drawString(mc.font, this.waypoint.getDisplayName(), x + (w - nw) / 2, y - 12, -1, true);
         graphics.drawString(mc.font, ds, x + (w - dw) / 2, y + 20, -1, true);
         RenderSystem.enableBlend();
         RenderSystem.enableDepthTest();
      }
   }
}
