package com.github.L_Ender.cataclysm.client.model.item;

import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class Bulwark_of_the_flame_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox shield;
   private final AdvancedModelBox left_side;
   private final AdvancedModelBox right_side;
   private final AdvancedModelBox handle;

   public Bulwark_of_the_flame_Model() {
      this.texWidth = 64;
      this.texHeight = 64;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, -0.5F);
      this.shield = new AdvancedModelBox(this);
      this.shield.setRotationPoint(0.0F, -14.0F, -2.0F);
      this.root.addChild(this.shield);
      this.shield.setTextureOffset(0, 0).addBox(-3.0F, -11.0F, -1.5F, 6.0F, 22.0F, 1.0F, 0.0F, false);
      this.shield.setTextureOffset(0, 28).addBox(-3.5F, 9.0F, -2.0F, 7.0F, 5.0F, 2.0F, 0.0F, false);
      this.shield.setTextureOffset(15, 19).addBox(-3.5F, -14.0F, -2.0F, 7.0F, 6.0F, 2.0F, 0.0F, false);
      this.shield.setTextureOffset(34, 18).addBox(-2.0F, -2.0F, -2.25F, 4.0F, 4.0F, 1.0F, 0.0F, false);
      this.left_side = new AdvancedModelBox(this);
      this.left_side.setRotationPoint(5.33F, -12.8316F, -2.1F);
      this.root.addChild(this.left_side);
      this.setRotationAngle(this.left_side, 0.0F, -0.2182F, 0.0436F);
      this.left_side.setTextureOffset(15, 0).addBox(-3.0F, -9.1667F, -0.5F, 5.0F, 17.0F, 1.0F, 0.0F, false);
      this.left_side.setTextureOffset(28, 11).addBox(-3.5F, 7.8333F, -1.0F, 6.0F, 4.0F, 2.0F, 0.0F, false);
      this.left_side.setTextureOffset(19, 28).addBox(-3.5F, -13.1667F, -1.0F, 6.0F, 4.0F, 2.0F, 0.0F, false);
      this.right_side = new AdvancedModelBox(this);
      this.right_side.setRotationPoint(-5.33F, -12.8316F, -2.1F);
      this.root.addChild(this.right_side);
      this.setRotationAngle(this.right_side, 0.0F, 0.2182F, -0.0436F);
      this.right_side.setTextureOffset(15, 0).addBox(-2.0F, -9.1667F, -0.5F, 5.0F, 17.0F, 1.0F, 0.0F, true);
      this.right_side.setTextureOffset(28, 11).addBox(-2.5F, 7.8333F, -1.0F, 6.0F, 4.0F, 2.0F, 0.0F, true);
      this.right_side.setTextureOffset(19, 28).addBox(-2.5F, -13.1667F, -1.0F, 6.0F, 4.0F, 2.0F, 0.0F, true);
      this.handle = new AdvancedModelBox(this);
      this.handle.setRotationPoint(6.0F, -8.0F, -8.0F);
      this.root.addChild(this.handle);
      this.handle.setTextureOffset(28, 0).addBox(-7.0F, -8.5F, 5.5F, 2.0F, 6.0F, 6.0F, 0.0F, false);
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }

   public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.shield, this.root, this.left_side, this.right_side, this.handle);
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }
}
