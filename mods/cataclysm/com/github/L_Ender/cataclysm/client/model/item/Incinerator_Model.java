package com.github.L_Ender.cataclysm.client.model.item;

import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class Incinerator_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox blade;
   private final AdvancedModelBox blade2;
   private final AdvancedModelBox blade_mid;
   private final AdvancedModelBox handle_core;
   private final AdvancedModelBox core;
   private final AdvancedModelBox upper_guard;
   private final AdvancedModelBox lower_guard;

   public Incinerator_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.root.setTextureOffset(0, 54).addBox(-1.5F, -23.0F, -1.5F, 3.0F, 12.0F, 3.0F, 0.0F, false);
      this.root.setTextureOffset(49, 53).addBox(-2.5F, -11.0F, -2.5F, 5.0F, 2.0F, 5.0F, 0.0F, false);
      this.root.setTextureOffset(0, 0).addBox(0.0F, -66.0F, -6.5F, 0.0F, 40.0F, 13.0F, 0.0F, false);
      this.root.setTextureOffset(27, 0).addBox(-0.5F, -66.0F, -3.5F, 1.0F, 40.0F, 7.0F, 0.0F, false);
      this.blade = new AdvancedModelBox(this);
      this.blade.setRotationPoint(0.0F, -66.0F, 0.0F);
      this.root.addChild(this.blade);
      this.setRotationAngle(this.blade, -0.7854F, 0.0F, 0.0F);
      this.blade.setTextureOffset(0, 0).addBox(-0.5F, -2.5F, -2.5F, 1.0F, 5.0F, 5.0F, -0.01F, false);
      this.blade2 = new AdvancedModelBox(this);
      this.blade2.setRotationPoint(0.0F, -66.0F, 0.0F);
      this.root.addChild(this.blade2);
      this.setRotationAngle(this.blade2, -0.7854F, 0.0F, 0.0F);
      this.blade2.setTextureOffset(35, 39).addBox(0.0F, -4.5F, -4.5F, 0.0F, 9.0F, 9.0F, 0.0F, false);
      this.blade_mid = new AdvancedModelBox(this);
      this.blade_mid.setRotationPoint(0.0F, -25.8F, 0.0F);
      this.root.addChild(this.blade_mid);
      this.setRotationAngle(this.blade_mid, 0.7854F, 0.0F, 0.0F);
      this.blade_mid.setTextureOffset(22, 53).addBox(-1.5F, -2.5F, -2.5F, 3.0F, 5.0F, 5.0F, 0.5F, false);
      this.blade_mid.setTextureOffset(54, 48).addBox(-1.0F, -0.5F, -5.5F, 2.0F, 1.0F, 3.0F, 0.0F, false);
      this.blade_mid.setTextureOffset(27, 48).addBox(-1.0F, 2.5F, -0.5F, 2.0F, 3.0F, 1.0F, 0.0F, false);
      this.handle_core = new AdvancedModelBox(this);
      this.handle_core.setRotationPoint(0.0F, -4.5F, 0.0F);
      this.root.addChild(this.handle_core);
      this.setRotationAngle(this.handle_core, -0.7854F, 0.0F, 0.0F);
      this.handle_core.setTextureOffset(45, 35).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, 0.7F, false);
      this.core = new AdvancedModelBox(this);
      this.core.setRotationPoint(0.0F, -4.5F, 0.0F);
      this.root.addChild(this.core);
      this.core.setTextureOffset(44, 24).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, 0.0F, false);
      this.upper_guard = new AdvancedModelBox(this);
      this.upper_guard.setRotationPoint(0.0F, -25.5F, 0.9F);
      this.root.addChild(this.upper_guard);
      this.setRotationAngle(this.upper_guard, -0.1745F, 0.0F, 0.0F);
      this.upper_guard.setTextureOffset(44, 12).addBox(-1.0F, -3.0F, 4.0F, 2.0F, 4.0F, 1.0F, 0.0F, false);
      this.upper_guard.setTextureOffset(37, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
      this.upper_guard.setTextureOffset(42, 58).addBox(-0.5F, -1.0F, 4.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
      this.upper_guard.setTextureOffset(60, 40).addBox(-0.5F, -2.0F, 3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
      this.upper_guard.setTextureOffset(56, 12).addBox(-0.5F, -3.0F, 3.0F, 1.0F, 1.0F, 7.0F, 0.0F, false);
      this.upper_guard.setTextureOffset(60, 21).addBox(-0.5F, -6.0F, 6.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
      this.upper_guard.setTextureOffset(13, 54).addBox(-0.5F, -7.0F, 10.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
      this.upper_guard.setTextureOffset(44, 12).addBox(-0.5F, -5.0F, 3.0F, 1.0F, 2.0F, 9.0F, 0.0F, false);
      this.lower_guard = new AdvancedModelBox(this);
      this.lower_guard.setRotationPoint(0.0F, -25.5F, -1.9F);
      this.root.addChild(this.lower_guard);
      this.setRotationAngle(this.lower_guard, 0.1745F, 0.0F, 0.0F);
      this.lower_guard.setTextureOffset(27, 0).addBox(-1.0F, -3.0F, -4.0F, 2.0F, 4.0F, 1.0F, 0.0F, false);
      this.lower_guard.setTextureOffset(14, 0).addBox(-1.0F, -1.0F, -3.0F, 2.0F, 2.0F, 4.0F, 0.0F, false);
      this.lower_guard.setTextureOffset(14, 7).addBox(-0.5F, -1.0F, -6.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
      this.lower_guard.setTextureOffset(33, 58).addBox(-0.5F, -2.0F, -8.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
      this.lower_guard.setTextureOffset(56, 0).addBox(-0.5F, -3.0F, -9.0F, 1.0F, 1.0F, 7.0F, 0.0F, false);
      this.lower_guard.setTextureOffset(13, 58).addBox(-0.5F, -6.0F, -11.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
      this.lower_guard.setTextureOffset(46, 0).addBox(-0.5F, -7.0F, -11.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
      this.lower_guard.setTextureOffset(44, 0).addBox(-0.5F, -5.0F, -11.0F, 1.0F, 2.0F, 9.0F, 0.0F, false);
   }

   public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root, this.blade, this.blade2, this.blade_mid, this.handle_core, this.core, this.upper_guard, this.lower_guard);
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }
}
