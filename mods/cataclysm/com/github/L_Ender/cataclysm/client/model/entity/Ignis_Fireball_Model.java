package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class Ignis_Fireball_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox core;
   private final AdvancedModelBox out_line;

   public Ignis_Fireball_Model() {
      this.texWidth = 64;
      this.texHeight = 64;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.core = new AdvancedModelBox(this);
      this.core.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.root.addChild(this.core);
      this.core.setTextureOffset(20, 37).addBox(-4.0F, -4.0F, -1.0F, 8.0F, 8.0F, 2.0F, 0.0F, false);
      this.core.setTextureOffset(0, 31).addBox(-1.0F, -4.0F, -4.0F, 2.0F, 8.0F, 8.0F, 0.0F, false);
      this.core.setTextureOffset(0, 21).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 2.0F, 8.0F, 0.0F, false);
      this.out_line = new AdvancedModelBox(this);
      this.out_line.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.root.addChild(this.out_line);
      this.out_line.setTextureOffset(0, 0).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 10.0F, 0.0F, false);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root, this.core, this.out_line);
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public void setupAnim(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.root.rotateAngleY = netHeadYaw * (float) (Math.PI / 180.0);
      this.root.rotateAngleX = headPitch * (float) (Math.PI / 180.0);
   }
}
