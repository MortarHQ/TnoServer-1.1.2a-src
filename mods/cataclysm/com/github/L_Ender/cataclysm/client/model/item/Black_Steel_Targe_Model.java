package com.github.L_Ender.cataclysm.client.model.item;

import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class Black_Steel_Targe_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox plate;
   private final AdvancedModelBox handle;

   public Black_Steel_Targe_Model() {
      this.texWidth = 64;
      this.texHeight = 64;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.plate = new AdvancedModelBox(this);
      this.plate.setRotationPoint(0.0F, -24.0F, -0.5F);
      this.root.addChild(this.plate);
      this.plate.setTextureOffset(0, 0).addBox(-8.0F, -8.0F, -2.0F, 16.0F, 16.0F, 2.0F, 0.0F, false);
      this.plate.setTextureOffset(17, 19).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 1.0F, 0.0F, false);
      this.handle = new AdvancedModelBox(this);
      this.handle.setRotationPoint(0.0F, -24.0F, 0.5F);
      this.root.addChild(this.handle);
      this.handle.setTextureOffset(0, 19).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 6.0F, 0.0F, false);
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }

   public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root, this.plate, this.handle);
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }
}
