package com.github.L_Ender.cataclysm.client.model.block;

import com.github.L_Ender.cataclysm.blockentities.AltarOfFire_Block_Entity;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class Altar_of_Fire_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox pillar;
   private final AdvancedModelBox flag_ring;
   private final AdvancedModelBox flag1;
   private final AdvancedModelBox flag2;
   private final AdvancedModelBox flag3;
   private final AdvancedModelBox flag4;
   private final AdvancedModelBox edge;
   private final AdvancedModelBox under_edge;
   private final AdvancedModelBox fire;

   public Altar_of_Fire_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.pillar = new AdvancedModelBox(this);
      this.pillar.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.root.addChild(this.pillar);
      this.pillar.setTextureOffset(0, 46).addBox(-8.0F, -12.0F, -8.0F, 16.0F, 4.0F, 16.0F, 0.0F, false);
      this.pillar.setTextureOffset(0, 25).addBox(-8.0F, -4.1F, -8.0F, 16.0F, 4.0F, 16.0F, 0.0F, false);
      this.pillar.setTextureOffset(0, 0).addBox(-8.0F, -20.75F, -8.0F, 16.0F, 8.0F, 16.0F, 0.5F, false);
      this.pillar.setTextureOffset(53, 13).addBox(-6.0F, -8.0F, -6.0F, 12.0F, 4.0F, 12.0F, 0.0F, false);
      this.flag_ring = new AdvancedModelBox(this);
      this.flag_ring.setRotationPoint(0.0F, -0.25F, 0.0F);
      this.pillar.addChild(this.flag_ring);
      this.flag1 = new AdvancedModelBox(this);
      this.flag1.setRotationPoint(0.0F, -12.0F, -8.75F);
      this.flag_ring.addChild(this.flag1);
      this.flag1.setTextureOffset(49, 0).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 11.0F, 0.0F, 0.0F, false);
      this.flag2 = new AdvancedModelBox(this);
      this.flag2.setRotationPoint(8.25F, -12.0F, 0.0F);
      this.flag_ring.addChild(this.flag2);
      this.flag2.setTextureOffset(66, 47).addBox(0.0F, 0.0F, -4.0F, 0.0F, 11.0F, 8.0F, 0.0F, false);
      this.flag3 = new AdvancedModelBox(this);
      this.flag3.setRotationPoint(0.0F, -12.0F, 8.25F);
      this.flag_ring.addChild(this.flag3);
      this.flag3.setTextureOffset(49, 0).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 11.0F, 0.0F, 0.0F, false);
      this.flag4 = new AdvancedModelBox(this);
      this.flag4.setRotationPoint(-8.25F, -12.0F, 0.0F);
      this.flag_ring.addChild(this.flag4);
      this.flag4.setTextureOffset(66, 47).addBox(0.0F, 0.0F, -4.0F, 0.0F, 11.0F, 8.0F, 0.0F, false);
      this.edge = new AdvancedModelBox(this);
      this.edge.setRotationPoint(0.0F, -12.0F, 0.0F);
      this.pillar.addChild(this.edge);
      this.edge.setTextureOffset(66, 0).addBox(6.0F, -1.0F, 6.0F, 4.0F, 3.0F, 4.0F, 0.0F, false);
      this.edge.setTextureOffset(0, 67).addBox(6.0F, -1.0F, -10.0F, 4.0F, 3.0F, 4.0F, 0.0F, false);
      this.edge.setTextureOffset(0, 67).addBox(-10.0F, -1.0F, -10.0F, 4.0F, 3.0F, 4.0F, 0.0F, true);
      this.edge.setTextureOffset(66, 0).addBox(-10.0F, -1.0F, 6.0F, 4.0F, 3.0F, 4.0F, 0.0F, true);
      this.edge.setTextureOffset(0, 7).addBox(-9.0F, 3.0F, 6.0F, 3.0F, 3.0F, 3.0F, 0.0F, true);
      this.edge.setTextureOffset(0, 7).addBox(6.0F, 3.0F, 6.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);
      this.edge.setTextureOffset(0, 0).addBox(6.0F, 3.0F, -9.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);
      this.edge.setTextureOffset(0, 0).addBox(-9.0F, 3.0F, -9.0F, 3.0F, 3.0F, 3.0F, 0.0F, true);
      this.under_edge = new AdvancedModelBox(this);
      this.under_edge.setRotationPoint(0.0F, 9.0F, 0.0F);
      this.pillar.addChild(this.under_edge);
      this.under_edge.setTextureOffset(0, 67).addBox(5.0F, -12.0F, -9.0F, 4.0F, 3.0F, 4.0F, 0.0F, false);
      this.under_edge.setTextureOffset(0, 67).addBox(-9.0F, -12.0F, -9.0F, 4.0F, 3.0F, 4.0F, 0.0F, true);
      this.under_edge.setTextureOffset(66, 0).addBox(-9.0F, -12.0F, 5.0F, 4.0F, 3.0F, 4.0F, 0.0F, true);
      this.under_edge.setTextureOffset(66, 0).addBox(5.0F, -12.0F, 5.0F, 4.0F, 3.0F, 4.0F, 0.0F, false);
      this.fire = new AdvancedModelBox(this);
      this.fire.setRotationPoint(0.0F, -12.0F, 0.0F);
      this.root.addChild(this.fire);
      this.setRotationAngle(this.fire, 0.0F, -0.7854F, 0.0F);
      this.fire.setTextureOffset(65, 30).addBox(-8.0F, -16.0F, 0.0F, 16.0F, 16.0F, 0.0F, 0.0F, false);
      this.fire.setTextureOffset(49, 51).addBox(0.0F, -16.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, false);
      this.updateDefaultPose();
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root, this.pillar, this.flag_ring, this.edge, this.under_edge, this.fire, this.flag1, this.flag2, this.flag3, this.flag4);
   }

   public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.resetToDefaultPose();
   }

   public void animate(AltarOfFire_Block_Entity beak, float partialTick) {
      this.resetToDefaultPose();
      float ageInTicks = beak.tickCount + partialTick;
      this.walk(this.flag1, 0.1F, 0.05F, false, 0.0F, -0.05F, ageInTicks, 1.0F);
      this.flap(this.flag2, 0.1F, 0.05F, false, 0.0F, -0.05F, ageInTicks, 1.0F);
      this.walk(this.flag3, 0.1F, 0.05F, true, 0.0F, -0.05F, ageInTicks, 1.0F);
      this.flap(this.flag4, 0.1F, 0.05F, true, 0.0F, -0.05F, ageInTicks, 1.0F);
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }
}
