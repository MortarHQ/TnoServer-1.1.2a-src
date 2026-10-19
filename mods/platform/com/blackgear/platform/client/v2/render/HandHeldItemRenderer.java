package com.blackgear.platform.client.v2.render;

import com.blackgear.platform.core.util.event.ResultHolder;
import java.util.Set;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public record HandHeldItemRenderer(ModelResourceLocation original, ModelResourceLocation handheld) implements ItemRendererRegistry.Renderer {
   private static final Set<ItemDisplayContext> CONTEXTS = Set.of(ItemDisplayContext.GUI, ItemDisplayContext.GROUND, ItemDisplayContext.FIXED);

   @Override
   public ResultHolder<BakedModel> renderFirstPerson(ItemStack stack, ItemDisplayContext context, ItemModelShaper shaper) {
      return CONTEXTS.contains(context) ? ResultHolder.submit(shaper.getModelManager().getModel(this.original())) : ResultHolder.pass();
   }

   @Override
   public ResultHolder<BakedModel> renderThirdPerson(ItemStack stack, ItemModelShaper shaper) {
      return ResultHolder.submit(shaper.getModelManager().getModel(this.handheld()));
   }

   @Override
   public Set<ModelResourceLocation> registerModels() {
      return Set.of(this.handheld());
   }
}
