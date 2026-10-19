package com.shin.tensura_kumodesu.entity.model.geo;

import com.shin.tensura_kumodesu.entity.taratect.QueenTaratectEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class QueenTaratectModel extends DefaultedEntityGeoModel<QueenTaratectEntity> {
   public QueenTaratectModel() {
      super(ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "taratect_queen"), true);
   }

   public ResourceLocation getTextureResource(QueenTaratectEntity entity) {
      return entity.getOwner() == Minecraft.getInstance().player
         ? ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "textures/entity/taratect_queen_kumo.png")
         : ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "textures/entity/taratect_queen.png");
   }

   public void setCustomAnimations(QueenTaratectEntity entity, long instanceId, AnimationState<QueenTaratectEntity> state) {
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
