package com.shin.tensura_kumodesu.entity.model.geo;

import com.shin.tensura_kumodesu.entity.taratect.EdeSaineTaratectEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class EdeSaineTaratectModel extends DefaultedEntityGeoModel<EdeSaineTaratectEntity> {
   public EdeSaineTaratectModel() {
      super(ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "taratect_ede_saine"), true);
   }

   public ResourceLocation getTextureResource(EdeSaineTaratectEntity entity) {
      return entity.getOwner() == Minecraft.getInstance().player
         ? ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "textures/entity/taratect_ede_saine_kumo.png")
         : ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "textures/entity/taratect_ede_saine.png");
   }

   public void setCustomAnimations(EdeSaineTaratectEntity entity, long instanceId, AnimationState<EdeSaineTaratectEntity> state) {
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
