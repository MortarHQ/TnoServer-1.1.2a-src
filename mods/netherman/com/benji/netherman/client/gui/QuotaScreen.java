package com.benji.netherman.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public class QuotaScreen extends Screen {
   private static final ResourceLocation BG_TEXTURE = ResourceLocation.fromNamespaceAndPath("netherman", "textures/gui/quota_gui.png");
   private static final ResourceLocation ALTAR_HINT_TEXTURE = ResourceLocation.fromNamespaceAndPath("netherman", "textures/gui/altar_hint.png");
   private static final String[] TASK_TEXTURES = new String[]{
      "quota_altar_line", "quota_destroy_line", "quota_kill_line", "quota_mine_line", "quota_pillagers_line", "quota_villagers_line"
   };
   private static final int IMAGE_WIDTH = 260;
   private static final int IMAGE_HEIGHT = 160;
   private long timeOpened;
   private static final float ANIMATION_DURATION_MS = 400.0F;
   private final ItemStack quotaItem;

   public QuotaScreen(ItemStack stack) {
      super(Component.empty());
      this.quotaItem = stack;
   }

   protected void init() {
      super.init();
      this.timeOpened = System.currentTimeMillis();
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
      long timePassed = System.currentTimeMillis() - this.timeOpened;
      float progress = Math.min(1.0F, (float)timePassed / 400.0F);
      float easeOutProgress = 1.0F - (1.0F - progress) * (1.0F - progress);
      int x = (this.width - 260) / 2;
      int startY = this.height;
      int endY = (this.height - 160) / 2;
      int currentY = (int)(startY + (endY - startY) * easeOutProgress);
      guiGraphics.blit(BG_TEXTURE, x, currentY, 0.0F, 0.0F, 260, 160, 260, 160);
      CustomData customData = (CustomData)this.quotaItem.get(DataComponents.CUSTOM_DATA);
      if (customData != null && customData.contains("QuotaData")) {
         CompoundTag data = customData.copyTag().getCompound("QuotaData");
         int stage = data.getInt("QuotaStage");
         String stageTexture = stage >= 6 ? "quota_stage_last.png" : "quota_stage_" + stage + ".png";
         guiGraphics.blit(ResourceLocation.fromNamespaceAndPath("netherman", "textures/gui/" + stageTexture), x, currentY, 0.0F, 0.0F, 260, 160, 260, 160);

         for (int i = 1; i <= 3; i++) {
            if (data.contains("QuotaTask" + i)) {
               int taskType = data.getInt("QuotaTask" + i);
               int prog = data.getInt("QuotaProg" + i);
               int max = data.getInt("QuotaMax" + i);
               if (prog < max) {
                  ResourceLocation taskTex = ResourceLocation.fromNamespaceAndPath("netherman", "textures/gui/" + TASK_TEXTURES[taskType] + i + ".png");
                  guiGraphics.blit(taskTex, x, currentY, 0.0F, 0.0F, 260, 160, 260, 160);
                  int lineStartY = currentY + 30 + (i - 1) * 35;
                  int lineEndY = lineStartY + 30;
                  if (mouseX >= x + 20 && mouseX <= x + 240 && mouseY >= lineStartY && mouseY <= lineEndY) {
                     ResourceLocation frameTex = ResourceLocation.fromNamespaceAndPath("netherman", "textures/gui/quota_selected_line" + i + ".png");
                     guiGraphics.blit(frameTex, x, currentY, 0.0F, 0.0F, 260, 160, 260, 160);
                     this.renderTooltipText(guiGraphics, taskType, prog, max, mouseX, mouseY);
                  }
               }
            }
         }
      }
   }

   private void renderTooltipText(GuiGraphics guiGraphics, int taskType, int prog, int max, int mouseX, int mouseY) {
      Component text;
      switch (taskType) {
         case 0:
            text = Component.translatable("gui.netherman.quota.altar");
            int hintWidth = 64;
            int hintHeight = 64;
            int hintX = mouseX - hintWidth - 10;
            int hintY = mouseY - hintHeight / 2;
            guiGraphics.blit(ALTAR_HINT_TEXTURE, hintX, hintY, 0.0F, 0.0F, hintWidth, hintHeight, hintWidth, hintHeight);
            break;
         case 1:
            text = Component.translatable("gui.netherman.quota.destroy", new Object[]{prog, max});
            break;
         case 2:
            text = Component.translatable("gui.netherman.quota.sacrifice", new Object[]{prog, max});
            break;
         case 3:
            text = Component.translatable("gui.netherman.quota.mine", new Object[]{prog, max});
            break;
         case 4:
            text = Component.translatable("gui.netherman.quota.pillagers", new Object[]{prog, max});
            break;
         case 5:
            text = Component.translatable("gui.netherman.quota.villagers", new Object[]{prog, max});
            break;
         default:
            text = Component.empty();
      }

      guiGraphics.renderTooltip(this.font, text, mouseX + 15, mouseY - 15);
   }

   public boolean isPauseScreen() {
      return false;
   }
}
