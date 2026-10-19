package net.pixeldreamstudios.mythika.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.pixeldreamstudios.mythika.Mythika;
import net.pixeldreamstudios.mythika.entity.SatyrEntity;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class SatyrModel extends GeoModel<SatyrEntity> {
   private static final ResourceLocation MODEL = Mythika.id("geo/entity/satyr.geo.json");
   private static final ResourceLocation ANIMATION = Mythika.id("animations/entity/satyr.animation.json");
   private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
      Mythika.id("textures/entity/satyr_1.png"), Mythika.id("textures/entity/satyr_2.png"), Mythika.id("textures/entity/satyr_3.png")
   };

   public ResourceLocation getModelResource(SatyrEntity animatable) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(SatyrEntity animatable) {
      return TEXTURES[Mth.clamp(animatable.getVariant(), 0, TEXTURES.length - 1)];
   }

   public ResourceLocation getAnimationResource(SatyrEntity animatable) {
      return ANIMATION;
   }

   public void setCustomAnimations(SatyrEntity animatable, long instanceId, AnimationState<SatyrEntity> animationState) {
      super.setCustomAnimations(animatable, instanceId, animationState);
      GeoBone head = this.getAnimationProcessor().getBone("head");
      if (head != null) {
         EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
         float pitch = Mth.clamp(entityData.headPitch(), -30.0F, 30.0F) * (float) (Math.PI / 180.0);
         float yaw = Mth.clamp(entityData.netHeadYaw(), -65.0F, 65.0F) * (float) (Math.PI / 180.0);
         float weight = animatable.headLookWeight();
         head.setRotX(Mth.lerp(weight, head.getRotX(), pitch));
         head.setRotY(Mth.lerp(weight, head.getRotY(), yaw));
      }
   }
}
