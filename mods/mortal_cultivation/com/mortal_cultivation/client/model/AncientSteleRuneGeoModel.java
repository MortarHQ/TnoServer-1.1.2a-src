package com.mortal_cultivation.client.model;

import com.mortal_cultivation.block.entity.AncientSteleBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public final class AncientSteleRuneGeoModel extends DefaultedBlockGeoModel<AncientSteleBlockEntity> {
   private static final ResourceLocation STELE_ANIMATION = ResourceLocation.fromNamespaceAndPath(
      "mortal_cultivation", "animations/block/ancient_stele.animation.json"
   );
   private static final ResourceLocation[] ANIMATION_FALLBACKS = new ResourceLocation[]{STELE_ANIMATION};

   public AncientSteleRuneGeoModel() {
      super(ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "ancient_stele_runes"));
   }

   public ResourceLocation[] getAnimationResourceFallbacks(AncientSteleBlockEntity animatable) {
      return ANIMATION_FALLBACKS;
   }
}
