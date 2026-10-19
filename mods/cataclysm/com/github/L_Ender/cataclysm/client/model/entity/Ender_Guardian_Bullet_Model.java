package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class Ender_Guardian_Bullet_Model extends AdvancedEntityModel<Entity> {
   public AdvancedModelBox renderer;

   public Ender_Guardian_Bullet_Model() {
      this.texWidth = 64;
      this.texHeight = 32;
      this.renderer = new AdvancedModelBox(this);
      this.renderer.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -1.0F, 8.0F, 8.0F, 2.0F, 0.0F);
      this.renderer.setTextureOffset(0, 10).addBox(-1.0F, -4.0F, -4.0F, 2.0F, 8.0F, 8.0F, 0.0F);
      this.renderer.setTextureOffset(20, 0).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 2.0F, 8.0F, 0.0F);
      this.renderer.setRotationPoint(0.0F, 0.0F, 0.0F);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.renderer);
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.renderer);
   }

   public void setupAnim(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.renderer.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
      this.renderer.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
   }
}
