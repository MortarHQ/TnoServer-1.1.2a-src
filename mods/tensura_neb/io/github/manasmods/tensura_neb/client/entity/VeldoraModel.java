package io.github.manasmods.tensura_neb.client.entity;

import io.github.manasmods.tensura.client.entity.TensuraEntityGeoModel;
import io.github.manasmods.tensura_neb.entity.VeldoraEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class VeldoraModel<E extends VeldoraEntity> extends TensuraEntityGeoModel<E> {
   public VeldoraModel() {
      super(ResourceLocation.fromNamespaceAndPath("tensura_neb", "veldora"));
   }

   public ResourceLocation getTextureResource(E instance) {
      return ResourceLocation.fromNamespaceAndPath("tensura_neb", "textures/entity/veldora.png");
   }

   public RenderType getRenderType(E animatable, ResourceLocation texture) {
      return RenderType.entityTranslucent(texture);
   }
}
