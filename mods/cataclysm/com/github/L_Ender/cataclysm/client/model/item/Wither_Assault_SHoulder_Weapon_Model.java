package com.github.L_Ender.cataclysm.client.model.item;

import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class Wither_Assault_SHoulder_Weapon_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox handle2;
   private final AdvancedModelBox handle;
   private final AdvancedModelBox trigger;
   private final AdvancedModelBox cube1;
   private final AdvancedModelBox cube2;
   private final AdvancedModelBox cube3;
   private final AdvancedModelBox cube4;
   private final AdvancedModelBox cap;
   private final AdvancedModelBox cap2;
   private final AdvancedModelBox cap3;

   public Wither_Assault_SHoulder_Weapon_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 23.75F, 0.0F);
      this.root.setTextureOffset(0, 0).addBox(-4.0F, -16.0F, -22.0F, 8.0F, 8.0F, 39.0F, 0.0F, false);
      this.root.setTextureOffset(0, 31).addBox(-3.0F, -7.0F, 18.0F, 6.0F, 6.0F, 0.0F, 0.0F, false);
      this.root.setTextureOffset(18, 30).addBox(-3.0F, -7.0F, -21.0F, 6.0F, 6.0F, 0.0F, 0.0F, false);
      this.root.setTextureOffset(0, 12).addBox(-4.0F, -16.0F, -16.0F, 8.0F, 8.0F, 2.0F, 0.2F, false);
      this.root.setTextureOffset(0, 22).addBox(-4.0F, -16.0F, -13.0F, 8.0F, 8.0F, 1.0F, 0.2F, false);
      this.handle2 = new AdvancedModelBox(this);
      this.handle2.setRotationPoint(0.0F, 0.0F, 13.0F);
      this.root.addChild(this.handle2);
      this.handle2.setTextureOffset(21, 9).addBox(-1.0F, -7.0F, -29.0F, 2.0F, 7.0F, 3.0F, 0.0F, false);
      this.handle2.setTextureOffset(30, 30).addBox(-1.0F, -8.0F, -29.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
      this.handle2.setTextureOffset(0, 0).addBox(-1.0F, 0.0F, -31.0F, 1.0F, 0.0F, 2.0F, 0.0F, false);
      this.handle = new AdvancedModelBox(this);
      this.handle.setRotationPoint(0.0F, 0.0F, -7.0F);
      this.root.addChild(this.handle);
      this.handle.setTextureOffset(24, 0).addBox(-1.0F, -5.75F, -1.0F, 2.0F, 6.0F, 3.0F, 0.0F, false);
      this.handle.setTextureOffset(27, 33).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 2.0F, 3.0F, 0.3F, false);
      this.trigger = new AdvancedModelBox(this);
      this.trigger.setRotationPoint(-0.5F, -6.75F, -1.25F);
      this.handle.addChild(this.trigger);
      this.setRotationAngle(this.trigger, -0.48F, 0.0F, 0.0F);
      this.trigger.setTextureOffset(31, 0).addBox(0.0F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);
      this.cube1 = new AdvancedModelBox(this);
      this.cube1.setRotationPoint(0.0F, 0.0F, -18.5F);
      this.root.addChild(this.cube1);
      this.cube1.setTextureOffset(28, 9).addBox(-1.0F, -17.0F, -3.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
      this.cube2 = new AdvancedModelBox(this);
      this.cube2.setRotationPoint(0.0F, -17.0F, -3.0F);
      this.cube1.addChild(this.cube2);
      this.setRotationAngle(this.cube2, -0.3491F, 0.0F, 0.0F);
      this.cube2.setTextureOffset(18, 0).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, 0.0F, false);
      this.cube3 = new AdvancedModelBox(this);
      this.cube3.setRotationPoint(0.0F, 0.0F, 13.5F);
      this.root.addChild(this.cube3);
      this.cube3.setTextureOffset(29, 17).addBox(-1.0F, -17.0F, 1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);
      this.cube4 = new AdvancedModelBox(this);
      this.cube4.setRotationPoint(0.0F, -17.0F, 3.0F);
      this.cube3.addChild(this.cube4);
      this.setRotationAngle(this.cube4, 0.3491F, 0.0F, 0.0F);
      this.cube4.setTextureOffset(18, 2).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F, 0.0F, false);
      this.cap = new AdvancedModelBox(this);
      this.cap.setRotationPoint(0.0F, 0.0F, 3.0F);
      this.root.addChild(this.cap);
      this.cap.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -25.0F, 8.0F, 8.0F, 4.0F, 0.0F, false);
      this.cap2 = new AdvancedModelBox(this);
      this.cap2.setRotationPoint(0.0F, -8.0F, 17.0F);
      this.root.addChild(this.cap2);
      this.setRotationAngle(this.cap2, 3.1416F, 0.0F, 0.0F);
      this.cap3 = new AdvancedModelBox(this);
      this.cap3.setRotationPoint(0.0F, -4.0F, -1.0F);
      this.cap2.addChild(this.cap3);
      this.setRotationAngle(this.cap3, 0.0F, 0.0F, -3.1416F);
      this.cap3.setTextureOffset(18, 20).addBox(-4.0F, -4.0F, -1.0F, 8.0F, 8.0F, 2.0F, 0.0F, false);
   }

   public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(
         this.root, this.handle, this.handle2, this.trigger, this.cube1, this.cube2, this.cube3, this.cube4, this.cap, this.cap2, this.cap3
      );
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }
}
