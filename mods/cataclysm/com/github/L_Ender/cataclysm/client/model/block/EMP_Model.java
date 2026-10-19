package com.github.L_Ender.cataclysm.client.model.block;

import com.github.L_Ender.cataclysm.blockentities.EMP_Block_Entity;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class EMP_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox down;
   private final AdvancedModelBox inner;
   private final AdvancedModelBox up;

   public EMP_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.down = new AdvancedModelBox(this);
      this.down.setRotationPoint(0.0F, -7.0F, 0.0F);
      this.root.addChild(this.down);
      this.down.setTextureOffset(0, 23).addBox(-8.0F, 0.0F, -8.0F, 16.0F, 7.0F, 16.0F, 0.0F, false);
      this.down.setTextureOffset(48, 23).addBox(-7.0F, -1.0F, -7.0F, 14.0F, 1.0F, 14.0F, 0.0F, false);
      this.inner = new AdvancedModelBox(this);
      this.inner.setRotationPoint(0.0F, -8.0F, 0.0F);
      this.root.addChild(this.inner);
      this.inner.setTextureOffset(0, 46).addBox(-6.0F, -14.0F, -6.0F, 12.0F, 14.0F, 12.0F, 0.0F, false);
      this.inner.setTextureOffset(0, 72).addBox(-6.0F, -8.0F, -6.0F, 12.0F, 3.0F, 12.0F, 0.3F, false);
      this.up = new AdvancedModelBox(this);
      this.up.setRotationPoint(0.0F, -15.0F, 0.0F);
      this.inner.addChild(this.up);
      this.up.setTextureOffset(0, 0).addBox(-8.0F, -7.0F, -8.0F, 16.0F, 7.0F, 16.0F, 0.0F, false);
      this.up.setTextureOffset(48, 0).addBox(-7.0F, 0.0F, -7.0F, 14.0F, 1.0F, 14.0F, 0.0F, false);
      this.updateDefaultPose();
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root, this.down, this.inner, this.up);
   }

   public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void animate(EMP_Block_Entity beak, float partialTick) {
      this.resetToDefaultPose();
      float amount = beak.getChompProgress(partialTick);
      this.progressPositionPrev(this.inner, amount, 0.0F, 5.0F, 0.0F, 15.0F);
      this.progressPositionPrev(this.up, amount, 0.0F, 5.5F, 0.0F, 15.0F);
   }
}
