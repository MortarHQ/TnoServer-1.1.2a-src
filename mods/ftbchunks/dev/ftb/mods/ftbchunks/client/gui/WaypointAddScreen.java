package dev.ftb.mods.ftbchunks.client.gui;

import dev.ftb.mods.ftbchunks.api.FTBChunksAPI;
import dev.ftb.mods.ftbchunks.api.client.waypoint.Waypoint;
import dev.ftb.mods.ftbchunks.client.FTBChunksClient;
import dev.ftb.mods.ftblibrary.config.ColorConfig;
import dev.ftb.mods.ftblibrary.config.ConfigFromString;
import dev.ftb.mods.ftblibrary.config.StringConfig;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.math.MathUtils;
import dev.ftb.mods.ftblibrary.ui.BaseScreen;
import dev.ftb.mods.ftblibrary.ui.Theme;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class WaypointAddScreen extends BaseScreen {
   private final StringConfig name;
   private final GlobalPos waypointLocation;
   private final ColorConfig color;
   private final boolean override;

   public WaypointAddScreen(StringConfig name, GlobalPos waypointLocation, Color4I color, boolean override) {
      this.name = name;
      this.waypointLocation = waypointLocation;
      this.setHeight(35);
      this.color = new ColorConfig();
      this.color.setValue(color);
      this.override = override;
   }

   public WaypointAddScreen(StringConfig name, GlobalPos waypointLocation) {
      this(name, waypointLocation, Color4I.hsb(MathUtils.RAND.nextFloat(), 1.0F, 1.0F), false);
   }

   public WaypointAddScreen(StringConfig name, Player player) {
      this(name, new GlobalPos(player.level().dimension(), player.blockPosition()));
   }

   public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
   }

   public void addWidgets() {
      AddWaypointOverlay.GlobalPosConfig globalPosConfig = new AddWaypointOverlay.GlobalPosConfig();
      globalPosConfig.setValue(this.waypointLocation);
      Component title = Component.translatable("ftbchunks.gui." + (this.override ? "edit_waypoint" : "add_waypoint"));
      AddWaypointOverlay overlay = new AddWaypointOverlay(
         this,
         title,
         globalPosConfig,
         this.name,
         this.color,
         set -> {
            if (set && !((String)this.name.getValue()).isEmpty()) {
               if (this.override) {
                  FTBChunksAPI.clientApi()
                     .getWaypointManager(this.waypointLocation.dimension())
                     .ifPresent(mgr -> mgr.removeWaypointAt(this.waypointLocation.pos()));
               }

               Waypoint wp = FTBChunksClient.addWaypoint(
                  (String)this.name.getValue(), (GlobalPos)globalPosConfig.getValue(), ((Color4I)this.color.getValue()).rgba()
               );
               Minecraft.getInstance()
                  .player
                  .displayClientMessage(
                     Component.translatable("ftbchunks.waypoint_added", new Object[]{wp.getDisplayName().copy().withStyle(ChatFormatting.YELLOW)}), true
                  );
            }
         }
      ) {
         public void onClosed() {
            this.closeGui();
         }
      };
      overlay.setWidth(this.width);
      this.pushModalPanel(overlay);
   }
}
