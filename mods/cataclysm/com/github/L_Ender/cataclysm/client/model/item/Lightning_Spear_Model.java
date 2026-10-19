package com.github.L_Ender.cataclysm.client.model.item;

import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class Lightning_Spear_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox rot;
   private final AdvancedModelBox root_r1;

   public Lightning_Spear_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, -4.0F, 0.0F);
      this.rot = new AdvancedModelBox(this);
      this.rot.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.root.addChild(this.rot);
      this.rot.setTextureOffset(0, -64).addBox(0.0F, -7.5F, -23.0F, 0.0F, 15.0F, 64.0F, 0.0F, false);
      this.root_r1 = new AdvancedModelBox(this);
      this.root_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.rot.addChild(this.root_r1);
      this.setRotationAngle(this.root_r1, 0.0F, 0.0F, 1.5708F);
      this.root_r1.setTextureOffset(0, -64).addBox(0.0F, -7.5F, -23.0F, 0.0F, 15.0F, 64.0F, 0.0F, false);
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

   public void animateStack(ItemStack itemStackIn) {
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root, this.rot, this.root_r1);
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }
}
