package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.client.render.ITextureProvider;
import net.minecraft.resources.ResourceLocation;

public class GauntletTextureProvider implements ITextureProvider<GauntletEntity> {
   public ResourceLocation getTexture(GauntletEntity entity) {
      return entity.hurtTime > 0
         ? ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "textures/entity/gauntlet_hurt.png")
         : ResourceLocation.fromNamespaceAndPath("bosses_of_mass_destruction", "textures/entity/gauntlet.png");
   }
}
