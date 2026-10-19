package com.blackgear.vanillabackport.client.api.renderer;

import com.blackgear.platform.core.BuiltInCoreRegistry;
import com.blackgear.vanillabackport.client.level.entities.model.pig.ColdPigModel;
import com.blackgear.vanillabackport.client.registries.ModModelLayers;
import com.blackgear.vanillabackport.common.level.entities.animal.PigVariant;
import com.blackgear.vanillabackport.common.level.entities.animal.PigVariants;
import com.blackgear.vanillabackport.core.registries.ModBuiltinRegistries;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Pig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PigVariantRenderer extends AbstractVariantRenderer<Pig, PigModel<Pig>, PigVariant, PigVariant.ModelType> {
   public PigVariantRenderer(Context context) {
      super(context);
   }

   @Override
   public Map<PigVariant.ModelType, PigModel<Pig>> bakeModels(Context context) {
      Map<PigVariant.ModelType, PigModel<Pig>> map = Maps.newEnumMap(PigVariant.ModelType.class);
      map.put(PigVariant.ModelType.NORMAL, null);
      map.put(PigVariant.ModelType.COLD, new ColdPigModel<>(context.bakeLayer(ModModelLayers.COLD_PIG)));
      return map;
   }

   protected PigVariant.ModelType getModelType(PigVariant variant) {
      return variant.modelAndTexture().model();
   }

   protected ResourceLocation getTexture(PigVariant variant) {
      return variant.modelAndTexture().asset().path();
   }

   @Override
   protected BuiltInCoreRegistry<PigVariant> getRegistry() {
      return ModBuiltinRegistries.PIG_VARIANTS;
   }

   @Override
   protected ResourceKey<PigVariant> getDefaultVariant() {
      return PigVariants.TEMPERATE;
   }
}
