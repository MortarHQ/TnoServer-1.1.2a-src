package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class Wither_Missile_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox head;
   private final AdvancedModelBox jaw;

   public Wither_Missile_Model() {
      this.texWidth = 64;
      this.texHeight = 64;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, -5.0F, 0.0F);
      this.root.setTextureOffset(22, 24).addBox(-3.0F, -2.0F, 0.0F, 6.0F, 6.0F, 3.0F, 0.0F, false);
      this.root.setTextureOffset(24, 0).addBox(-3.0F, -2.0F, 2.0F, 6.0F, 6.0F, 2.0F, 0.3F, false);
      this.root.setTextureOffset(0, 24).addBox(-4.0F, -3.0F, 4.0F, 8.0F, 8.0F, 3.0F, 0.0F, false);
      this.head = new AdvancedModelBox(this);
      this.head.setRotationPoint(0.0F, -0.25F, -6.25F);
      this.root.addChild(this.head);
      this.setRotationAngle(this.head, -0.2182F, 0.0F, 0.0F);
      this.head.setTextureOffset(36, 30).addBox(0.0F, -10.75F, -7.0F, 0.0F, 12.0F, 14.0F, 0.0F, false);
      this.head.setTextureOffset(0, 49).addBox(-4.0F, -4.25F, -1.0F, 8.0F, 7.0F, 8.0F, 0.25F, false);
      this.head.setTextureOffset(0, 0).addBox(-4.0F, -4.75F, -1.0F, 8.0F, 6.0F, 8.0F, 0.0F, false);
      this.jaw = new AdvancedModelBox(this);
      this.jaw.setRotationPoint(0.0F, 4.0F, 1.0F);
      this.head.addChild(this.jaw);
      this.setRotationAngle(this.jaw, 0.5236F, 0.0F, 0.0F);
      this.jaw.setTextureOffset(0, 14).addBox(-4.0F, 0.25F, -1.0F, 8.0F, 2.0F, 8.0F, 0.0F, false);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root, this.jaw, this.head);
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }

   public void setupAnim(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.root.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
      this.root.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
   }
}
