package com.blackgear.platform.client.animator;

import java.util.Optional;
import net.minecraft.client.model.geom.ModelPart;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface AnimatedModel {
   ModelPart root();

   default Optional<ModelPart> getAnyDescendantWithName(String name) {
      return this.root().getAllParts().filter(part -> part.hasChild(name)).findFirst().map(part -> part.getChild(name));
   }
}
