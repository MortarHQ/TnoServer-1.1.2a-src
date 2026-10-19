package com.mafuyu404.oneenoughitem.api;

import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public interface ReplacementUiAdapter {
   void addDataTooltip(List<Component> var1, String var2);

   void addTagTooltip(List<Component> var1, ResourceLocation var2);

   void renderDataIndicators(GuiGraphics var1, String var2, int var3, int var4);

   void renderTagIndicators(GuiGraphics var1, ResourceLocation var2, int var3, int var4);
}
