package com.blackgear.vanillabackport.core.neoforge.emissive;

import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.ModelEvent.ModifyBakingResult;
import net.neoforged.neoforge.client.event.ModelEvent.RegisterAdditional;

@EventBusSubscriber(modid = "vanillabackport", bus = Bus.MOD, value = Dist.CLIENT)
public class EmissiveModelHandler {
   private static final String[] EMISSIVE_BLOCKS = new String[]{"open_eyeblossom", "potted_open_eyeblossom", "firefly_bush"};
   private static final String EMISSIVE_SUFFIX = "_emissive";

   @SubscribeEvent
   public static void onModelBake(ModifyBakingResult event) {
      Map<ModelResourceLocation, BakedModel> modelRegistry = event.getModels();

      for (String blockName : EMISSIVE_BLOCKS) {
         ModelResourceLocation emissiveModelLocation = ModelResourceLocation.standalone(
            ResourceLocation.withDefaultNamespace("block/" + blockName + "_emissive")
         );
         BakedModel emissiveModel = modelRegistry.get(emissiveModelLocation);
         if (emissiveModel != null) {
            for (Entry<ModelResourceLocation, BakedModel> entry : modelRegistry.entrySet()) {
               ModelResourceLocation location = entry.getKey();
               if (location.variant().isEmpty()) {
                  String path = location.id().getPath();
                  if (path.equals("block/" + blockName) || path.equals(blockName)) {
                     BakedModel wrappedModel = new EmissiveModelWrapper(entry.getValue(), emissiveModel);
                     modelRegistry.put(location, wrappedModel);
                  }
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onRegisterAdditionalModels(RegisterAdditional event) {
      for (String blockName : EMISSIVE_BLOCKS) {
         ModelResourceLocation emissiveModelLocation = ModelResourceLocation.standalone(
            ResourceLocation.withDefaultNamespace("block/" + blockName + "_emissive")
         );
         event.register(emissiveModelLocation);
      }
   }
}
