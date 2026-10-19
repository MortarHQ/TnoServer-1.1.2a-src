package net.pixeldreamstudios.mythika.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.pixeldreamstudios.mythika.Mythika;
import net.pixeldreamstudios.mythika.entity.CyclopsEntity;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class CyclopsModel extends GeoModel<CyclopsEntity> {
   private static final ResourceLocation MODEL = Mythika.id("geo/entity/cyclops.geo.json");
   private static final ResourceLocation TEXTURE = Mythika.id("textures/entity/cyclops.png");
   private static final ResourceLocation ANIMATION = Mythika.id("animations/entity/cyclops.animation.json");

   public ResourceLocation getModelResource(CyclopsEntity animatable) {
      return MODEL;
   }

   public ResourceLocation getTextureResource(CyclopsEntity animatable) {
      return TEXTURE;
   }

   public ResourceLocation getAnimationResource(CyclopsEntity animatable) {
      return ANIMATION;
   }

   public void setCustomAnimations(CyclopsEntity animatable, long instanceId, AnimationState<CyclopsEntity> animationState) {
      super.setCustomAnimations(animatable, instanceId, animationState);
      GeoBone head = this.getAnimationProcessor().getBone("head");
      if (head != null) {
         EntityModelData entityData = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
         head.setRotX(Mth.clamp(entityData.headPitch(), -20.0F, 20.0F) * (float) (Math.PI / 180.0));
         head.setRotY(Mth.clamp(entityData.netHeadYaw(), -60.0F, 60.0F) * (float) (Math.PI / 180.0));
      }
   }
}
