package com.github.L_Ender.cataclysm.client.model.item;

import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class Ceraunus_Item_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox anchor;
   private final AdvancedModelBox cube_r1;
   private final AdvancedModelBox cube_r2;
   private final AdvancedModelBox cube_r3;
   private final AdvancedModelBox cube_r4;
   private final AdvancedModelBox cube_r5;
   private final AdvancedModelBox cube_r6;
   private final AdvancedModelBox cube_r7;
   private final AdvancedModelBox cube_r8;
   private final AdvancedModelBox cube_r9;
   private final AdvancedModelBox cube_r10;
   private final AdvancedModelBox cube_r11;
   private final AdvancedModelBox cube_r12;
   private final AdvancedModelBox chain;
   private final AdvancedModelBox cube_r13;

   public Ceraunus_Item_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.anchor = new AdvancedModelBox(this);
      this.anchor.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.anchor.setTextureOffset(0, 0).addBox(0.0F, -50.3F, -9.0F, 0.0F, 11.0F, 18.0F, 0.0F, false);
      this.anchor.setTextureOffset(79, 51).addBox(-1.0F, -45.3F, -3.0F, 2.0F, 6.0F, 6.0F, 0.0F, false);
      this.anchor.setTextureOffset(37, 19).addBox(-3.0F, -48.3F, 0.0F, 6.0F, 9.0F, 0.0F, 0.0F, false);
      this.anchor.setTextureOffset(0, 30).addBox(-2.0F, -35.3F, -3.0F, 4.0F, 32.0F, 6.0F, -0.01F, false);
      this.cube_r1 = new AdvancedModelBox(this);
      this.cube_r1.setRotationPoint(0.0F, -5.3F, 0.0F);
      this.anchor.addChild(this.cube_r1);
      this.setRotationAngle(this.cube_r1, -2.3562F, 0.0F, 0.0F);
      this.cube_r1.setTextureOffset(70, 0).addBox(-2.0F, -4.0F, -4.0F, 4.0F, 8.0F, 8.0F, 0.0F, false);
      this.cube_r2 = new AdvancedModelBox(this);
      this.cube_r2.setRotationPoint(0.0F, -38.5F, 0.0F);
      this.anchor.addChild(this.cube_r2);
      this.setRotationAngle(this.cube_r2, -0.7854F, 0.0F, 0.0F);
      this.cube_r2.setTextureOffset(79, 38).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.1F, false);
      this.cube_r3 = new AdvancedModelBox(this);
      this.cube_r3.setRotationPoint(0.0F, -33.5732F, 15.6944F);
      this.anchor.addChild(this.cube_r3);
      this.setRotationAngle(this.cube_r3, -0.7418F, 0.0F, 0.0F);
      this.cube_r3.setTextureOffset(50, 38).addBox(-1.0F, -3.0F, -6.0F, 2.0F, 6.0F, 12.0F, 0.001F, false);
      this.cube_r4 = new AdvancedModelBox(this);
      this.cube_r4.setRotationPoint(0.0F, -38.3F, 2.0F);
      this.anchor.addChild(this.cube_r4);
      this.setRotationAngle(this.cube_r4, -0.1309F, 0.0F, 0.0F);
      this.cube_r4.setTextureOffset(37, 0).addBox(-2.0F, -3.0F, -1.0F, 4.0F, 6.0F, 12.0F, 0.0F, false);
      this.cube_r5 = new AdvancedModelBox(this);
      this.cube_r5.setRotationPoint(0.0F, -33.5732F, -15.6944F);
      this.anchor.addChild(this.cube_r5);
      this.setRotationAngle(this.cube_r5, 0.7418F, 0.0F, 0.0F);
      this.cube_r5.setTextureOffset(50, 57).addBox(-1.0F, -3.0F, -6.0F, 2.0F, 6.0F, 12.0F, 0.001F, false);
      this.cube_r6 = new AdvancedModelBox(this);
      this.cube_r6.setRotationPoint(0.0F, -38.3F, -2.0F);
      this.anchor.addChild(this.cube_r6);
      this.setRotationAngle(this.cube_r6, 0.1309F, 0.0F, 0.0F);
      this.cube_r6.setTextureOffset(50, 19).addBox(-2.0F, -3.0F, -11.0F, 4.0F, 6.0F, 12.0F, 0.0F, false);
      this.cube_r7 = new AdvancedModelBox(this);
      this.cube_r7.setRotationPoint(0.0F, -34.7693F, 3.0963F);
      this.anchor.addChild(this.cube_r7);
      this.setRotationAngle(this.cube_r7, 0.3491F, 0.0F, 0.0F);
      this.cube_r7.setTextureOffset(67, 76).addBox(-1.0F, -2.0F, -5.0F, 2.0F, 4.0F, 10.0F, 0.0F, false);
      this.cube_r8 = new AdvancedModelBox(this);
      this.cube_r8.setRotationPoint(0.0F, -34.7693F, -3.0963F);
      this.anchor.addChild(this.cube_r8);
      this.setRotationAngle(this.cube_r8, -0.3491F, 0.0F, 0.0F);
      this.cube_r8.setTextureOffset(42, 76).addBox(-1.0F, -2.0F, -5.0F, 2.0F, 4.0F, 10.0F, 0.0F, false);
      this.cube_r9 = new AdvancedModelBox(this);
      this.cube_r9.setRotationPoint(0.0F, -27.0353F, 19.5093F);
      this.anchor.addChild(this.cube_r9);
      this.setRotationAngle(this.cube_r9, -0.1309F, 0.0F, 0.0F);
      this.cube_r9.setTextureOffset(83, 17).addBox(-1.0F, -5.0F, -2.0F, 2.0F, 10.0F, 4.0F, 0.0F, false);
      this.cube_r9.setTextureOffset(88, 64).addBox(-1.0F, 5.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
      this.cube_r9.setTextureOffset(0, 69).addBox(0.0F, 3.0F, -5.0F, 0.0F, 10.0F, 10.0F, 0.0F, false);
      this.cube_r10 = new AdvancedModelBox(this);
      this.cube_r10.setRotationPoint(0.0F, -27.0353F, -19.5093F);
      this.anchor.addChild(this.cube_r10);
      this.setRotationAngle(this.cube_r10, 0.1309F, 0.0F, 0.0F);
      this.cube_r10.setTextureOffset(79, 64).addBox(-1.0F, 5.0F, -1.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
      this.cube_r11 = new AdvancedModelBox(this);
      this.cube_r11.setRotationPoint(0.0F, -19.1037F, -18.4651F);
      this.anchor.addChild(this.cube_r11);
      this.setRotationAngle(this.cube_r11, 0.1309F, 0.0F, 0.0F);
      this.cube_r11.setTextureOffset(21, 59).addBox(0.0F, -5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 0.0F, false);
      this.cube_r12 = new AdvancedModelBox(this);
      this.cube_r12.setRotationPoint(0.0F, -27.0353F, -19.5093F);
      this.anchor.addChild(this.cube_r12);
      this.setRotationAngle(this.cube_r12, 0.1309F, 0.0F, 0.0F);
      this.cube_r12.setTextureOffset(21, 80).addBox(-1.0F, -5.0F, -2.0F, 2.0F, 10.0F, 4.0F, 0.0F, false);
      this.chain = new AdvancedModelBox(this);
      this.chain.setRotationPoint(0.0F, 8.3F, 0.0F);
      this.anchor.addChild(this.chain);
      this.cube_r13 = new AdvancedModelBox(this);
      this.cube_r13.setRotationPoint(-1.0F, -10.6F, 0.0F);
      this.chain.addChild(this.cube_r13);
      this.setRotationAngle(this.cube_r13, -2.3562F, 0.0F, 0.0F);
      this.cube_r13.setTextureOffset(21, 30).addBox(1.0F, -10.0F, -4.0F, 0.0F, 14.0F, 14.0F, 0.0F, false);
      this.updateDefaultPose();
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }

   public void setupAnim(Entity entity, float pullAmount, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.resetToDefaultPose();
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(
         this.anchor,
         this.cube_r1,
         this.cube_r2,
         this.cube_r3,
         this.cube_r4,
         this.cube_r5,
         this.cube_r6,
         this.cube_r7,
         this.cube_r8,
         this.cube_r9,
         this.cube_r10,
         this.cube_r11,
         new AdvancedModelBox[]{this.cube_r12, this.chain, this.cube_r13}
      );
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.anchor);
   }
}
