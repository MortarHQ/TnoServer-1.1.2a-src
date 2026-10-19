package com.blackgear.vanillabackport.client.api.renderer;

import com.blackgear.platform.core.BuiltInCoreRegistry;
import com.blackgear.vanillabackport.client.level.entities.model.cow.ColdCowModel;
import com.blackgear.vanillabackport.client.level.entities.model.cow.WarmCowModel;
import com.blackgear.vanillabackport.client.registries.ModModelLayers;
import com.blackgear.vanillabackport.common.level.entities.animal.CowVariant;
import com.blackgear.vanillabackport.common.level.entities.animal.CowVariants;
import com.blackgear.vanillabackport.core.registries.ModBuiltinRegistries;
import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Cow;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CowVariantRenderer extends AbstractVariantRenderer<Cow, CowModel<Cow>, CowVariant, CowVariant.ModelType> {
   public CowVariantRenderer(Context context) {
      super(context);
   }

   @Override
   public Map<CowVariant.ModelType, CowModel<Cow>> bakeModels(Context context) {
      Map<CowVariant.ModelType, CowModel<Cow>> map = Maps.newEnumMap(CowVariant.ModelType.class);
      map.put(CowVariant.ModelType.NORMAL, null);
      map.put(CowVariant.ModelType.WARM, new WarmCowModel<>(context.bakeLayer(ModModelLayers.WARM_COW)));
      map.put(CowVariant.ModelType.COLD, new ColdCowModel<>(context.bakeLayer(ModModelLayers.COLD_COW)));
      return map;
   }

   protected CowVariant.ModelType getModelType(CowVariant variant) {
      return variant.modelAndTexture().model();
   }

   protected ResourceLocation getTexture(CowVariant variant) {
      return variant.modelAndTexture().asset().path();
   }

   @Override
   protected BuiltInCoreRegistry<CowVariant> getRegistry() {
      return ModBuiltinRegistries.COW_VARIANTS;
   }

   @Override
   protected ResourceKey<CowVariant> getDefaultVariant() {
      return CowVariants.TEMPERATE;
   }
}
