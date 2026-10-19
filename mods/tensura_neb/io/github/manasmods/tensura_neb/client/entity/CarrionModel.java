package io.github.manasmods.tensura_neb.client.entity;

import io.github.manasmods.tensura.client.entity.TensuraEntityGeoModel;
import io.github.manasmods.tensura_neb.entity.CarrionEntity;
import io.github.manasmods.tensura_neb.registry.NebItems;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;

public class CarrionModel<E extends CarrionEntity> extends TensuraEntityGeoModel<E> {
   public CarrionModel() {
      super(ResourceLocation.fromNamespaceAndPath("tensura_neb", "carrion"));
   }

   public ResourceLocation getTextureResource(E instance) {
      return instance.getPhase() == 2
         ? ResourceLocation.fromNamespaceAndPath("tensura_neb", "textures/entity/carrion_royal_beast.png")
         : ResourceLocation.fromNamespaceAndPath("tensura_neb", "textures/entity/carrion.png");
   }

   public RenderType getRenderType(E animatable, ResourceLocation texture) {
      return RenderType.entityTranslucent(texture);
   }

   public void setCustomAnimations(E carrion, long instanceId, AnimationState<E> animationState) {
      GeoBone halberd = this.getAnimationProcessor().getBone("halberd");
      halberd.setHidden(!carrion.getMainHandItem().is((Item)NebItems.WHITE_TIGER_AZURE_DRAGON_HALBERD.get()));
      if (!carrion.isInFlyingPose() && !carrion.isAngry()) {
         super.setCustomAnimations(carrion, instanceId, animationState);
      }
   }
}
