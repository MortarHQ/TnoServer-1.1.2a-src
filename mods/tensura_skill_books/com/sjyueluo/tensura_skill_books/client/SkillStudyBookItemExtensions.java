package com.sjyueluo.tensura_skill_books.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public final class SkillStudyBookItemExtensions implements IClientItemExtensions {
   private final BlockEntityWithoutLevelRenderer renderer = new SkillStudyBookItemRenderer(
      Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()
   );

   public BlockEntityWithoutLevelRenderer getCustomRenderer() {
      return this.renderer;
   }
}
