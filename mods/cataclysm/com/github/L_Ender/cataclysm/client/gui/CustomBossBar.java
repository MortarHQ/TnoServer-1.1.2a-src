package com.github.L_Ender.cataclysm.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.BossEvent;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent.BossEventProgress;

public class CustomBossBar {
   public static Map<Integer, CustomBossBar> customBossBars = new HashMap<>();
   private static final ResourceLocation LIFE_REMAIN_ICON = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/gui/boss_bar/life_remain.png");
   private static final ResourceLocation LIFE_REMAIN_SANS_ICON = ResourceLocation.fromNamespaceAndPath(
      "cataclysm", "textures/gui/boss_bar/life_remain_sans.png"
   );
   private static final ResourceLocation LIFE_REMAIN_SKULL_ICON = ResourceLocation.fromNamespaceAndPath(
      "cataclysm", "textures/gui/boss_bar/life_remain_skull.png"
   );
   private final ResourceLocation baseTexture;
   private final ResourceLocation overlayTexture;
   private final boolean hasOverlay;
   private final int baseHeight;
   private final int baseTextureHeight;
   private final int baseOffsetX;
   private final int baseOffsetY;
   private final int overlayOffsetX;
   private final int overlayOffsetY;
   private final int overlayWidth;
   private final int overlayHeight;
   private final int verticalIncrement;
   private final int getProgress;
   private final ChatFormatting textColor;

   public CustomBossBar(
      ResourceLocation baseTexture,
      ResourceLocation overlayTexture,
      int baseHeight,
      int baseTextureHeight,
      int baseOffsetX,
      int baseOffsetY,
      int overlayOffsetX,
      int overlayOffsetY,
      int overlayWidth,
      int overlayHeight,
      int verticalIncrement,
      int getProgress,
      ChatFormatting textColor
   ) {
      this.baseTexture = baseTexture;
      this.overlayTexture = overlayTexture;
      this.hasOverlay = overlayTexture != null;
      this.baseHeight = baseHeight;
      this.baseTextureHeight = baseTextureHeight;
      this.baseOffsetX = baseOffsetX;
      this.baseOffsetY = baseOffsetY;
      this.overlayOffsetX = overlayOffsetX;
      this.overlayOffsetY = overlayOffsetY;
      this.overlayWidth = overlayWidth;
      this.overlayHeight = overlayHeight;
      this.verticalIncrement = verticalIncrement;
      this.getProgress = getProgress;
      this.textColor = textColor;
   }

   public ResourceLocation getBaseTexture() {
      return this.baseTexture;
   }

   public ResourceLocation getOverlayTexture() {
      return this.overlayTexture;
   }

   public boolean hasOverlay() {
      return this.hasOverlay;
   }

   public int getBaseHeight() {
      return this.baseHeight;
   }

   public int getBaseTextureHeight() {
      return this.baseTextureHeight;
   }

   public int getBaseOffsetX() {
      return this.baseOffsetX;
   }

   public int getBaseOffsetY() {
      return this.baseOffsetY;
   }

   public int getOverlayOffsetX() {
      return this.overlayOffsetX;
   }

   public int getOverlayOffsetY() {
      return this.overlayOffsetY;
   }

   public int getOverlayWidth() {
      return this.overlayWidth;
   }

   public int getOverlayHeight() {
      return this.overlayHeight;
   }

   public int getProgress() {
      return this.getProgress;
   }

   public int getVerticalIncrement() {
      return this.verticalIncrement;
   }

   public ChatFormatting getTextColor() {
      return this.textColor;
   }

   public void renderBossBar(BossEventProgress event, int remainLife) {
      GuiGraphics guiGraphics = event.getGuiGraphics();
      int y = event.getY();
      int i = Minecraft.getInstance().getWindow().getGuiScaledWidth();
      int j = y - 9;
      Minecraft.getInstance().getProfiler().push("CataclysmCustomBossBarBase");
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, this.getBaseTexture());
      int barX = event.getX() + this.getBaseOffsetX();
      int barY = y + this.getBaseOffsetY();
      this.drawBar(guiGraphics, barX, barY, event.getBossEvent());
      Component component = event.getBossEvent().getName().copy().withStyle(this.getTextColor());
      Minecraft.getInstance().getProfiler().pop();
      int l = Minecraft.getInstance().font.width(component);
      int i1 = i / 2 - l / 2;
      int j1 = j;
      guiGraphics.drawString(Minecraft.getInstance().font, component, i1, j1, 16777215);
      if (this.hasOverlay()) {
         Minecraft.getInstance().getProfiler().push("CataclysmCustomBossBarOverlay");
         RenderSystem.setShaderTexture(0, this.getOverlayTexture());
         event.getGuiGraphics()
            .blit(
               this.getOverlayTexture(),
               event.getX() + this.getBaseOffsetX() + this.getOverlayOffsetX(),
               y + this.getOverlayOffsetY() + this.getBaseOffsetY(),
               0.0F,
               0.0F,
               this.getOverlayWidth(),
               this.getOverlayHeight(),
               this.getOverlayWidth(),
               this.getOverlayHeight()
            );
         Minecraft.getInstance().getProfiler().pop();
      }

      if (remainLife > 0) {
         int iconSize = 16;
         int iconX = barX + this.getProgress() - 32;
         int iconY = barY + this.getBaseHeight() + 2;
         Calendar calendar = Calendar.getInstance();
         ResourceLocation sans = calendar.get(2) == 3 && calendar.get(5) == 1 ? LIFE_REMAIN_SANS_ICON : LIFE_REMAIN_SKULL_ICON;
         RenderSystem.setShaderTexture(0, sans);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         guiGraphics.blit(sans, iconX, iconY, 0.0F, 0.0F, iconSize, iconSize, iconSize, iconSize);
         Component lifeText = Component.literal("x " + remainLife).withStyle(ChatFormatting.WHITE);
         guiGraphics.drawString(Minecraft.getInstance().font, lifeText, iconX + iconSize + 2, iconY + 3, 16777215, true);
      }

      event.setIncrement(this.getVerticalIncrement());
   }

   private void drawBar(GuiGraphics guiGraphics, int x, int y, BossEvent event) {
      guiGraphics.blit(this.getBaseTexture(), x, y, 0.0F, 0.0F, this.getProgress(), this.getBaseHeight(), 256, this.getBaseTextureHeight());
      int i = (int)(event.getProgress() * (this.getProgress() + 1));
      if (i > 0) {
         guiGraphics.blit(this.getBaseTexture(), x, y, 0.0F, this.getBaseHeight(), i, this.getBaseHeight(), 256, this.getBaseTextureHeight());
      }
   }

   static {
      customBossBars.put(
         0,
         new CustomBossBar(
            ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/gui/boss_bar/monstrosity_bar_base.png"),
            ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/gui/boss_bar/monstrosity_bar_overlay.png"),
            5,
            16,
            1,
            1,
            -2,
            -2,
            256,
            16,
            25,
            182,
            ChatFormatting.RED
         )
      );
      customBossBars.put(
         1,
         new CustomBossBar(
            ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/gui/boss_bar/ender_guardian_bar_base.png"),
            ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/gui/boss_bar/ender_guardian_bar_overlay.png"),
            5,
            16,
            1,
            1,
            -2,
            -2,
            256,
            16,
            25,
            182,
            ChatFormatting.LIGHT_PURPLE
         )
      );
      customBossBars.put(
         2,
         new CustomBossBar(
            ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/gui/boss_bar/ignis_bar_base.png"),
            ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/gui/boss_bar/ignis_bar_overlay.png"),
            5,
            16,
            1,
            1,
            -2,
            -2,
            256,
            16,
            25,
            182,
            ChatFormatting.YELLOW
         )
      );
      customBossBars.put(
         3,
         new CustomBossBar(
            ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/gui/boss_bar/ignis_soul_bar_base.png"),
            ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/gui/boss_bar/ignis_soul_bar_overlay.png"),
            5,
            16,
            1,
            1,
            -2,
            -2,
            256,
            16,
            25,
            182,
            ChatFormatting.DARK_AQUA
         )
      );
      customBossBars.put(
         4,
         new CustomBossBar(
            ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/gui/boss_bar/harbinger_bar_base.png"),
            ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/gui/boss_bar/harbinger_bar_overlay.png"),
            5,
            16,
            1,
            7,
            -2,
            -8,
            256,
            32,
            25,
            182,
            ChatFormatting.DARK_RED
         )
      );
      customBossBars.put(
         5,
         new CustomBossBar(
            ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/gui/boss_bar/leviathan_bar_base.png"),
            ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/gui/boss_bar/leviathan_bar_overlay.png"),
            5,
            16,
            1,
            2,
            -4,
            -4,
            256,
            16,
            25,
            182,
            ChatFormatting.DARK_PURPLE
         )
      );
      customBossBars.put(
         6,
         new CustomBossBar(
            ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/gui/boss_bar/leviathan_bar_base.png"),
            ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/gui/boss_bar/leviathan_meltdown_bar_overlay.png"),
            5,
            16,
            1,
            4,
            -4,
            -6,
            256,
            16,
            25,
            182,
            ChatFormatting.DARK_PURPLE
         )
      );
      customBossBars.put(
         7,
         new CustomBossBar(
            ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/gui/boss_bar/remnant_bar_base.png"),
            ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/gui/boss_bar/remnant_bar_overlay.png"),
            5,
            16,
            1,
            7,
            -4,
            -10,
            256,
            32,
            30,
            182,
            ChatFormatting.WHITE
         )
      );
      customBossBars.put(
         8,
         new CustomBossBar(
            ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/gui/boss_bar/remnant_rage_bar_base.png"),
            ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/gui/boss_bar/remnant_rage_bar_overlay.png"),
            5,
            16,
            69,
            -8,
            -6,
            -8,
            256,
            16,
            15,
            48,
            ChatFormatting.DARK_PURPLE
         )
      );
      customBossBars.put(
         9,
         new CustomBossBar(
            ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/gui/boss_bar/maledictus_bar_base.png"),
            ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/gui/boss_bar/maledictus_bar_overlay.png"),
            5,
            16,
            0,
            7,
            -7,
            -9,
            256,
            32,
            25,
            182,
            ChatFormatting.DARK_GREEN
         )
      );
      customBossBars.put(
         10,
         new CustomBossBar(
            ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/gui/boss_bar/maledictus_rage_bar_base.png"),
            ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/gui/boss_bar/maledictus_rage_bar_overlay.png"),
            5,
            16,
            68,
            -3,
            -7,
            -8,
            256,
            16,
            15,
            48,
            ChatFormatting.DARK_PURPLE
         )
      );
      customBossBars.put(
         12,
         new CustomBossBar(
            ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/gui/boss_bar/scylla_bar_base.png"),
            ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/gui/boss_bar/scylla_bar_overlay.png"),
            5,
            16,
            0,
            7,
            -7,
            -8,
            256,
            32,
            25,
            182,
            ChatFormatting.BLUE
         )
      );
   }
}
