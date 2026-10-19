package net.blay09.mods.waystones.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneTypes;
import net.blay09.mods.waystones.api.WaystoneVisibility;
import net.blay09.mods.waystones.api.requirement.WarpRequirement;
import net.blay09.mods.waystones.client.requirement.RequirementClientRegistry;
import net.blay09.mods.waystones.client.requirement.RequirementRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;

public class WaystoneButton extends Button {
   private final WarpRequirement warpRequirement;
   private final Waystone waystone;

   public WaystoneButton(int x, int y, Waystone waystone, WarpRequirement warpRequirement, OnPress pressable) {
      super(x, y, 200, 20, getWaystoneNameComponent(waystone), pressable, Button.DEFAULT_NARRATION);
      Player player = Minecraft.getInstance().player;
      this.warpRequirement = warpRequirement;
      this.waystone = waystone;
      if (player == null) {
         this.active = false;
      } else if (!warpRequirement.canAfford(player) && !player.getAbilities().instabuild) {
         this.active = false;
      }
   }

   private static Component getWaystoneNameComponent(Waystone waystone) {
      MutableComponent effectiveName = waystone.getName().copy();
      if (effectiveName.getString().isEmpty()) {
         effectiveName = Component.translatable("gui.waystones.waystone_selection.unnamed_waystone");
      }

      if (waystone.getVisibility() == WaystoneVisibility.GLOBAL && waystone.getWaystoneType().equals(WaystoneTypes.WAYSTONE)) {
         effectiveName.withStyle(ChatFormatting.YELLOW);
      }

      return effectiveName;
   }

   public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      super.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      Font font = Minecraft.getInstance().font;
      LocalPlayer player = Minecraft.getInstance().player;
      if (this.waystone.getDimension() == player.level().dimension() && this.isActive()) {
         int distance = (int)player.position().distanceTo(this.waystone.getPos().getCenter());
         String distanceStr;
         if (distance >= 10000 || font.width(this.getMessage()) >= 120 && distance >= 1000) {
            distanceStr = String.format("%.1f", distance / 1000.0F).replace(",0", "").replace(".0", "") + "km";
         } else {
            distanceStr = distance + "m";
         }

         int xOffset = this.getWidth() - font.width(distanceStr);
         guiGraphics.drawString(font, distanceStr, this.getX() + xOffset - 4, this.getY() + 6, 16777215);
      }

      this.renderRequirements(this.warpRequirement, guiGraphics, mouseX, mouseY, partialTicks);
   }

   private <T extends WarpRequirement> void renderRequirements(T requirement, GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      Font font = Minecraft.getInstance().font;
      LocalPlayer player = Minecraft.getInstance().player;
      RequirementRenderer<T> renderer = RequirementClientRegistry.getRenderer((Class<T>)requirement.getClass());
      if (renderer != null) {
         renderer.renderWidget(player, requirement, guiGraphics, mouseX, mouseY, partialTicks, this.getX() + 2, this.getY() + 2);
         if (this.isHovered && mouseX < this.getX() + 2 + renderer.getWidth(player, requirement)) {
            List<Component> tooltip = new ArrayList<>();
            this.warpRequirement.appendHoverText(player, tooltip);
            guiGraphics.renderTooltip(font, tooltip, Optional.empty(), mouseX, mouseY + 9);
         }
      }
   }
}
