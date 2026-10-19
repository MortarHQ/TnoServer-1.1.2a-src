package com.blackgear.vanillabackport.client.api.renderer;

import com.blackgear.platform.core.BuiltInCoreRegistry;
import com.blackgear.vanillabackport.client.level.entities.model.chicken.ColdChickenModel;
import com.blackgear.vanillabackport.client.registries.ModModelLayers;
import com.blackgear.vanillabackport.common.level.entities.animal.ChickenVariant;
import com.blackgear.vanillabackport.common.level.entities.animal.ChickenVariants;
import com.blackgear.vanillabackport.core.registries.ModBuiltinRegistries;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.model.ChickenModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Chicken;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChickenVariantRenderer extends AbstractVariantRenderer<Chicken, ChickenModel<Chicken>, ChickenVariant, ChickenVariant.ModelType> {
   public ChickenVariantRenderer(Context context) {
      super(context);
   }

   @Override
   protected Map<ChickenVariant.ModelType, ChickenModel<Chicken>> bakeModels(Context context) {
      Map<ChickenVariant.ModelType, ChickenModel<Chicken>> map = Maps.newEnumMap(ChickenVariant.ModelType.class);
      map.put(ChickenVariant.ModelType.NORMAL, null);
      map.put(ChickenVariant.ModelType.COLD, new ColdChickenModel<>(context.bakeLayer(ModModelLayers.COLD_CHICKEN)));
      return map;
   }

   protected ChickenVariant.ModelType getModelType(ChickenVariant variant) {
      return variant.modelAndTexture().model();
   }

   protected ResourceLocation getTexture(ChickenVariant variant) {
      return variant.modelAndTexture().asset().path();
   }

   @Override
   protected BuiltInCoreRegistry<ChickenVariant> getRegistry() {
      return ModBuiltinRegistries.CHICKEN_VARIANTS;
   }

   @Override
   protected ResourceKey<ChickenVariant> getDefaultVariant() {
      return ChickenVariants.TEMPERATE;
   }
}
