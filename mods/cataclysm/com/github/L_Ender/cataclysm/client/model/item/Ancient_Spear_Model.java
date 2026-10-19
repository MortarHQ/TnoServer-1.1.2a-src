package com.github.L_Ender.cataclysm.client.model.item;

import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class Ancient_Spear_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox root;

   public Ancient_Spear_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.root.setTextureOffset(33, 0).addBox(-1.0F, -60.0F, -1.0F, 2.0F, 60.0F, 2.0F, 0.0F, false);
      this.root.setTextureOffset(0, 0).addBox(0.0F, -80.0F, -8.0F, 0.0F, 37.0F, 16.0F, 0.0F, false);
      this.root.setTextureOffset(42, 0).addBox(-8.0F, -80.0F, 0.0F, 16.0F, 37.0F, 0.0F, 0.0F, false);
   }

   public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root);
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }
}
