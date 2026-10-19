package com.blackgear.platform.core.mixin.client;

import com.blackgear.platform.client.v2.render.DynamicItemRenderer;
import com.blackgear.platform.client.v2.render.ItemRendererRegistry;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelBakery.class)
public abstract class ModelBakeryMixin {
   @Shadow
   @Final
   private Map<ModelResourceLocation, UnbakedModel> topLevelModels;

   @Shadow
   protected abstract void loadSpecialItemModelAndDependencies(ModelResourceLocation var1);

   @Shadow
   abstract UnbakedModel getModel(ResourceLocation var1);

   @Inject(
      method = "<init>(Lnet/minecraft/client/color/block/BlockColors;Lnet/minecraft/util/profiling/ProfilerFiller;Ljava/util/Map;Ljava/util/Map;)V",
      at = @At("RETURN")
   )
   public void addModel(CallbackInfo ci) {
      for (Entry<Item, ItemRendererRegistry.Renderer> renderer : ((ItemRendererRegistry)ItemRendererRegistry.INSTANCE.get()).getRenderers().entrySet()) {
         for (ModelResourceLocation model : renderer.getValue().registerModels()) {
            this.loadSpecialItemModelAndDependencies(model);
            UnbakedModel unbaked = this.topLevelModels.get(model);
            unbaked.resolveParents(resource -> this.getModel(resource));
         }
      }

      for (Entry<Item, DynamicItemRenderer.Renderer> renderer : ((DynamicItemRenderer)DynamicItemRenderer.INSTANCE.get()).getRenderers().entrySet()) {
         for (ModelResourceLocation model : renderer.getValue().registerModels()) {
            this.loadSpecialItemModelAndDependencies(model);
            UnbakedModel unbaked = this.topLevelModels.get(model);
            unbaked.resolveParents(resource -> this.getModel(resource));
         }
      }
   }
}
