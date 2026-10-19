package com.chronodawn.client.renderer;

import com.chronodawn.client.model.EntropyKeeperModel;
import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.entities.bosses.EntropyKeeperEntity;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class EntropyKeeperRenderer extends MobRenderer<EntropyKeeperEntity, EntropyKeeperModel<EntropyKeeperEntity>> {
   private static final ResourceLocation TEXTURE = CompatResourceLocation.create("chronodawn", "textures/entity/entropy_keeper.png");
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(CompatResourceLocation.create("chronodawn", "entropy_keeper"), "main");

   public EntropyKeeperRenderer(Context context) {
      super(context, new EntropyKeeperModel(context.bakeLayer(LAYER_LOCATION)), 0.5F);
   }

   public ResourceLocation getTextureLocation(EntropyKeeperEntity entity) {
      return TEXTURE;
   }
}
