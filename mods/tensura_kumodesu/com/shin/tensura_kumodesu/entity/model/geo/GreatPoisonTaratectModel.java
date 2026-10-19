package com.shin.tensura_kumodesu.entity.model.geo;

import com.shin.tensura_kumodesu.entity.taratect.GreatPoisonTaratectEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class GreatPoisonTaratectModel extends DefaultedEntityGeoModel<GreatPoisonTaratectEntity> {
   public GreatPoisonTaratectModel() {
      super(ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "taratect_great_poison"), true);
   }

   public ResourceLocation getTextureResource(GreatPoisonTaratectEntity entity) {
      return entity.getOwner() == Minecraft.getInstance().player
         ? ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "textures/entity/taratect_great_poison_kumo.png")
         : ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "textures/entity/taratect_great_poison.png");
   }

   public void setCustomAnimations(GreatPoisonTaratectEntity entity, long instanceId, AnimationState<GreatPoisonTaratectEntity> state) {
      super.setCustomAnimations(entity, instanceId, state);
      GeoBone head = this.getAnimationProcessor().getBone("HEAD");
      if (head != null) {
         float yaw = entity.getCustomHeadYaw();
         float pitch = entity.getCustomHeadPitch();
         yaw = Mth.clamp(yaw, -60.0F, 60.0F);
         pitch = Mth.clamp(pitch, -45.0F, 45.0F);
         head.setRotY((float)Math.toRadians(-yaw));
         head.setRotX((float)Math.toRadians(-pitch));
      }
   }
}
