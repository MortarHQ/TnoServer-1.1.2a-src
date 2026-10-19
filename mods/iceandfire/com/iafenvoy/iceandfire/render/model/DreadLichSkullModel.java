package com.iafenvoy.iceandfire.render.model;

import com.google.common.collect.ImmutableList;
import com.iafenvoy.iceandfire.entity.DreadLichSkullEntity;
import com.iafenvoy.uranus.client.model.AdvancedEntityModel;
import com.iafenvoy.uranus.client.model.AdvancedModelBox;
import com.iafenvoy.uranus.client.model.basic.BasicModelPart;
import com.iafenvoy.uranus.client.model.util.HideableModelRenderer;

public class DreadLichSkullModel extends AdvancedEntityModel<DreadLichSkullEntity> {
   public final HideableModelRenderer bipedHead;
   public final HideableModelRenderer bipedHeadwear;

   public DreadLichSkullModel() {
      this(0.0F);
   }

   public DreadLichSkullModel(float modelSize) {
      this.texHeight = 32;
      this.texWidth = 64;
      this.bipedHead = new HideableModelRenderer(this, 0, 0);
      this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, modelSize - 0.5F);
      this.bipedHead.setPos(0.0F, 0.0F, 0.0F);
      this.bipedHeadwear = new HideableModelRenderer(this, 32, 0);
      this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, modelSize);
      this.bipedHeadwear.setPos(0.0F, 0.0F, 0.0F);
      this.updateDefaultPose();
   }

   public void setAngles(DreadLichSkullEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
      this.resetToDefaultPose();
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.bipedHead, this.bipedHeadwear);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.bipedHead, this.bipedHeadwear);
   }
}
