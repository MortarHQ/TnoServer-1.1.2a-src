package com.sjyueluo.tensura_skill_books.client;

import com.sjyueluo.tensura_skill_books.client.icon.IconData;
import com.sjyueluo.tensura_skill_books.client.icon.TensuraSkillIconResolver;
import com.sjyueluo.tensura_skill_books.util.SkillItemNbt;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.IItemDecorator;

public final class SkillStudyBookItemDecorator implements IItemDecorator {
   public boolean render(GuiGraphics guiGraphics, Font font, ItemStack stack, int xOffset, int yOffset) {
      renderIcon(guiGraphics, stack, xOffset, yOffset);
      return false;
   }

   public static void renderIcon(GuiGraphics guiGraphics, ItemStack stack, int xOffset, int yOffset) {
      ResourceLocation iconId = SkillItemNbt.getRenderIconId(stack);
      if (iconId != null) {
         IconData icon = TensuraSkillIconResolver.resolve(iconId);
         int width = Math.max(1, icon.pixelWidth());
         int height = Math.max(1, icon.pixelHeight());
         int textureWidth = Math.max(width, Math.round(width / Math.max(1.0E-4F, icon.u1() - icon.u0())));
         int textureHeight = Math.max(height, Math.round(height / Math.max(1.0E-4F, icon.v1() - icon.v0())));
         int sourceWidth = Math.max(1, Math.round(textureWidth * (icon.u1() - icon.u0())));
         int sourceHeight = Math.max(1, Math.round(textureHeight * (icon.v1() - icon.v0())));
         int max = Math.max(width, height);
         float scale = 16.0F / max;
         int renderWidth = Math.max(1, Math.round(width * scale));
         int renderHeight = Math.max(1, Math.round(height * scale));
         int x = xOffset + (16 - renderWidth) / 2;
         int y = yOffset + (16 - renderHeight) / 2;
         float u = icon.u0() * textureWidth;
         float v = icon.v0() * textureHeight;
         guiGraphics.blit(icon.texture(), x, y, renderWidth, renderHeight, u, v, sourceWidth, sourceHeight, textureWidth, textureHeight);
      }
   }
}
