package com.bobmowzie.mowziesmobs.client.gui;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent.BossEventProgress;

public class CustomBossBar {
   public static Map<ResourceLocation, CustomBossBar> customBossBars = new HashMap<>();
   private final ResourceLocation baseTexture;
   private final ResourceLocation overlayTexture;
   private final boolean hasOverlay;
   private final int baseHeight;
   private final int baseTextureHeight;
   private final int baseOffsetY;
   private final int overlayOffsetX;
   private final int overlayOffsetY;
   private final int overlayWidth;
   private final int overlayHeight;
   private final int verticalIncrement;
   private final ChatFormatting textColor;

   public CustomBossBar(
      ResourceLocation baseTexture,
      ResourceLocation overlayTexture,
      int baseHeight,
      int baseTextureHeight,
      int baseOffsetY,
      int overlayOffsetX,
      int overlayOffsetY,
      int overlayWidth,
      int overlayHeight,
      int verticalIncrement,
      ChatFormatting textColor
   ) {
      this.baseTexture = baseTexture;
      this.overlayTexture = overlayTexture;
      this.hasOverlay = overlayTexture != null;
      this.baseHeight = baseHeight;
      this.baseTextureHeight = baseTextureHeight;
      this.baseOffsetY = baseOffsetY;
      this.overlayOffsetX = overlayOffsetX;
      this.overlayOffsetY = overlayOffsetY;
      this.overlayWidth = overlayWidth;
      this.overlayHeight = overlayHeight;
      this.verticalIncrement = verticalIncrement;
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

   public int getVerticalIncrement() {
      return this.verticalIncrement;
   }

   public ChatFormatting getTextColor() {
      return this.textColor;
   }

   public void renderBossBar(BossEventProgress event) {
      int baseYOffset = this.getBaseOffsetY();
      GuiGraphics guiGraphics = event.getGuiGraphics();
      int y = event.getY();
      int i = Minecraft.getInstance().getWindow().getGuiScaledWidth();
      int j = y - 9;
      int k = i / 2 - 91;
      Minecraft.getInstance().getProfiler().push("customBossBarBase");
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, this.getBaseTexture());
      this.drawBar(guiGraphics, event.getX() + 1, y + baseYOffset, event.getBossEvent());
      Component component = event.getBossEvent().getName().copy().withStyle(this.getTextColor());
      Minecraft.getInstance().getProfiler().pop();
      int l = Minecraft.getInstance().font.width(component);
      int i1 = i / 2 - l / 2;
      int j1 = j;
      guiGraphics.drawString(Minecraft.getInstance().font, component, i1, j1, 16777215);
      if (this.hasOverlay()) {
         Minecraft.getInstance().getProfiler().push("customBossBarOverlay");
         RenderSystem.setShaderTexture(0, this.getOverlayTexture());
         event.getGuiGraphics()
            .blit(
               this.getOverlayTexture(),
               event.getX() + 1 + this.getOverlayOffsetX(),
               y + this.getOverlayOffsetY() + baseYOffset,
               0.0F,
               0.0F,
               this.getOverlayWidth(),
               this.getOverlayHeight(),
               this.getOverlayWidth(),
               this.getOverlayHeight()
            );
         Minecraft.getInstance().getProfiler().pop();
      }

      event.setIncrement(this.getVerticalIncrement());
   }

   private void drawBar(GuiGraphics guiGraphics, int x, int y, BossEvent event) {
      guiGraphics.blit(this.getBaseTexture(), x, y, 0.0F, 0.0F, 182, this.getBaseHeight(), 256, this.getBaseTextureHeight());
      int i = (int)(event.getProgress() * 183.0F);
      if (i > 0) {
         guiGraphics.blit(this.getBaseTexture(), x, y, 0.0F, this.getBaseHeight(), i, this.getBaseHeight(), 256, this.getBaseTextureHeight());
      }
   }

   static {
      customBossBars.put(
         BuiltInRegistries.ENTITY_TYPE.getKey((EntityType)EntityHandler.UMVUTHI.get()),
         new CustomBossBar(
            MMCommon.resource("textures/gui/boss_bar/umvuthi_bar_base.png"),
            MMCommon.resource("textures/gui/boss_bar/umvuthi_bar_overlay.png"),
            4,
            8,
            5,
            -5,
            -6,
            256,
            16,
            25,
            ChatFormatting.GOLD
         )
      );
      customBossBars.put(
         BuiltInRegistries.ENTITY_TYPE.getKey((EntityType)EntityHandler.FROSTMAW.get()),
         new CustomBossBar(
            MMCommon.resource("textures/gui/boss_bar/frostmaw_bar_base.png"),
            MMCommon.resource("textures/gui/boss_bar/frostmaw_bar_overlay.png"),
            10,
            32,
            2,
            -4,
            -3,
            256,
            32,
            25,
            ChatFormatting.WHITE
         )
      );
      customBossBars.put(
         BuiltInRegistries.ENTITY_TYPE.getKey((EntityType)EntityHandler.WROUGHTNAUT.get()),
         new CustomBossBar(
            MMCommon.resource("textures/gui/boss_bar/wroughtnaut_bar_base.png"),
            MMCommon.resource("textures/gui/boss_bar/wroughtnaut_bar_overlay.png"),
            4,
            8,
            5,
            -5,
            -6,
            256,
            16,
            25,
            ChatFormatting.RED
         )
      );
   }
}
