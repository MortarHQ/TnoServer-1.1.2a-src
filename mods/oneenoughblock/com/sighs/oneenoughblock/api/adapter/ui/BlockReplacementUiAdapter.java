package com.sighs.oneenoughblock.api.adapter.ui;

import com.mafuyu404.oneenoughitem.api.DomainRuntimeCache;
import com.mafuyu404.oneenoughitem.api.ReplacementUiAdapter;
import com.mafuyu404.oneenoughitem.client.gui.cache.AbstractGlobalReplacementCache;
import com.mafuyu404.oneenoughitem.client.gui.util.UiTooltipRenderHelper;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class BlockReplacementUiAdapter implements ReplacementUiAdapter {
   private final DomainRuntimeCache runtime;
   private final AbstractGlobalReplacementCache global;

   public BlockReplacementUiAdapter(DomainRuntimeCache runtime, AbstractGlobalReplacementCache global) {
      this.runtime = runtime;
      this.global = global;
   }

   public void addDataTooltip(List<Component> tooltip, String dataId) {
      UiTooltipRenderHelper.addDataTooltip(tooltip, dataId, this.runtime, this.global, "oneenoughblock", "block", rep -> {
         ResourceLocation rl = ResourceLocation.tryParse(rep);
         Block block = rl != null ? (Block)BuiltInRegistries.BLOCK.get(rl) : null;
         return block != null ? new ItemStack(block.asItem()).getHoverName().getString() : rep;
      });
   }

   public void addTagTooltip(List<Component> tooltip, ResourceLocation tagId) {
      UiTooltipRenderHelper.addTagTooltip(tooltip, tagId, this.runtime, this.global, "oneenoughblock", "block", rep -> {
         ResourceLocation rl = ResourceLocation.tryParse(rep);
         Block block = rl != null ? (Block)BuiltInRegistries.BLOCK.get(rl) : null;
         return block != null ? new ItemStack(block.asItem()).getHoverName().getString() : rep;
      });
   }

   public void renderDataIndicators(GuiGraphics g, String dataId, int x, int y) {
      UiTooltipRenderHelper.renderDataIndicators(g, dataId, this.runtime, this.global, x, y);
   }

   public void renderTagIndicators(GuiGraphics g, ResourceLocation tagId, int x, int y) {
      UiTooltipRenderHelper.renderTagIndicators(g, tagId, this.runtime, this.global, x, y);
   }
}
