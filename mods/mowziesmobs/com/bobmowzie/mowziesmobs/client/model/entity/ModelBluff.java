package com.bobmowzie.mowziesmobs.client.model.entity;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoBone;
import com.bobmowzie.mowziesmobs.client.model.tools.geckolib.MowzieGeoModel;
import com.bobmowzie.mowziesmobs.server.entity.bluff.EntityBluff;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.data.EntityModelData;

public class ModelBluff extends MowzieGeoModel<EntityBluff> {
   public ResourceLocation getModelResource(EntityBluff object) {
      return MMCommon.resource("geo/bluff.geo.json");
   }

   public ResourceLocation getTextureResource(EntityBluff object) {
      return MMCommon.resource("textures/entity/bluff.png");
   }

   public ResourceLocation getAnimationResource(EntityBluff object) {
      return MMCommon.resource("animations/bluff.animation.json");
   }

   public void setCustomAnimations(EntityBluff entity, long instanceId, AnimationState<EntityBluff> animationState) {
      super.setCustomAnimations(entity, instanceId, animationState);
      float frame = entity.frame + animationState.getPartialTick();
      float ticks = entity.tickCount;
      MowzieGeoBone rotation1 = this.getMowzieBone("rotation1");
      MowzieGeoBone rotation2 = this.getMowzieBone("rotation2");
      MowzieGeoBone rotation3 = this.getMowzieBone("rotation3");
      MowzieGeoBone core = this.getMowzieBone("core");
      if (entity.isAlive()) {
         rotation1.addRotY(frame % 360.0F / 4.0F);
         rotation2.addRotY(frame % 360.0F / 4.0F);
         rotation3.addRotY(frame % 360.0F / 4.0F);
         core.addRotY(frame % 360.0F / -4.0F);
         core.addPosY((float)(Math.sin(frame / 5.0F) * 0.8F));
         core.addRotX((float)(Math.sin(frame / 9.0F) * 1.0));
      }

      MowzieGeoBone head = this.getMowzieBone("head");
      MowzieGeoBone root = this.getMowzieBone("root");
      EntityModelData data = (EntityModelData)animationState.getData(DataTickets.ENTITY_MODEL_DATA);
      float headYaw = Mth.wrapDegrees(data.netHeadYaw());
      float headPitch = Mth.wrapDegrees(data.headPitch());
      head.addRotX(headPitch * (float) Math.PI / 180.0F);
      root.addRotY(headYaw * (float) Math.PI / 180.0F);
   }
}
