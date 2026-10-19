package com.mortal_cultivation.client.model;

import com.mortal_cultivation.block.entity.AncientSteleBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

public final class AncientSteleGeoModel extends DefaultedBlockGeoModel<AncientSteleBlockEntity> {
   private static final ResourceLocation RUNE_ANIMATION = ResourceLocation.fromNamespaceAndPath(
      "mortal_cultivation", "animations/block/ancient_stele_runes.animation.json"
   );
   private static final ResourceLocation[] ANIMATION_FALLBACKS = new ResourceLocation[]{RUNE_ANIMATION};

   public AncientSteleGeoModel() {
      super(ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "ancient_stele"));
   }

   public ResourceLocation[] getAnimationResourceFallbacks(AncientSteleBlockEntity animatable) {
      return ANIMATION_FALLBACKS;
   }
}
