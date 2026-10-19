package net.blay09.mods.waystones.client;

import net.blay09.mods.balm.api.DeferredObject;
import net.blay09.mods.balm.api.client.rendering.BalmModels;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;

public class ModModels {
   public static DeferredObject<BakedModel> waystoneRunes;
   public static DeferredObject<BakedModel> sharestoneRunes;
   public static DeferredObject<BakedModel> portstoneRunes;

   public static void initialize(BalmModels models) {
   }

   private static ResourceLocation id(String path) {
      return ResourceLocation.fromNamespaceAndPath("waystones", path);
   }
}
