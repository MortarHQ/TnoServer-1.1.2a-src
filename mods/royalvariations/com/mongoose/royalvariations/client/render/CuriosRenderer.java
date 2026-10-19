package com.mongoose.royalvariations.client.render;

import com.mongoose.royalvariations.RoyalVariations;
import com.mongoose.royalvariations.client.render.model.GloveModel;
import com.mongoose.royalvariations.common.items.RVItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class CuriosRenderer {
   public static String folderPath = "textures/models/curios/";

   public static ResourceLocation render(String textureName) {
      return RoyalVariations.location(folderPath + textureName);
   }

   public static void register() {
      CuriosRendererRegistry.register(
         (Item)RVItems.SPECTRAL_GAUNTLET.get(), () -> new WearRenderer(render("spectral_gauntlet.png"), new GloveModel(bakeLayer(RVModelLayer.GLOVE)))
      );
   }

   public static ModelPart bakeLayer(ModelLayerLocation layerLocation) {
      return Minecraft.getInstance().getEntityModels().bakeLayer(layerLocation);
   }
}
