package com.github.L_Ender.cataclysm.client.model.block;

import com.github.L_Ender.cataclysm.blockentities.Abyssal_Egg_Block_Entity;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class Abyssal_Egg_Model extends AdvancedEntityModel<Entity> {
   public final AdvancedModelBox root;
   public final AdvancedModelBox fetus;

   public Abyssal_Egg_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.root.setTextureOffset(0, 0).addBox(-7.0F, -17.25F, -7.0F, 14.0F, 17.0F, 14.0F, 0.0F, false);
      this.root.setTextureOffset(0, 32).addBox(-7.0F, -7.25F, -7.0F, 14.0F, 7.0F, 14.0F, 0.3F, false);
      this.fetus = new AdvancedModelBox(this);
      this.fetus.setRotationPoint(0.0F, -8.0F, 0.0F);
      this.root.addChild(this.fetus);
      this.fetus.setTextureOffset(43, 0).addBox(-6.0F, -6.0F, 0.0F, 12.0F, 12.0F, 0.0F, 0.0F, false);
      this.updateDefaultPose();
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root, this.fetus);
   }

   public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.resetToDefaultPose();
   }

   public void animate(Abyssal_Egg_Block_Entity beak, float partialTick) {
      this.resetToDefaultPose();
      float ageInTicks = beak.tickCount + partialTick;
      float spin = 0.01F;
      this.fetus.rotateAngleY -= ageInTicks * spin;
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }
}
