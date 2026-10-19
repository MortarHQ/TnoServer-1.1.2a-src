package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class Tidal_Tentacle_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox tonguePivot;
   private final AdvancedModelBox tongue;
   private float stretch;
   public static boolean HIDE = false;

   public Tidal_Tentacle_Model() {
      this.texWidth = 32;
      this.texHeight = 32;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 12.0F, 0.0F);
      this.tonguePivot = new AdvancedModelBox(this);
      this.tonguePivot.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.root.addChild(this.tonguePivot);
      this.tongue = new AdvancedModelBox(this);
      this.tongue.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.tonguePivot.addChild(this.tongue);
      this.tongue.setTextureOffset(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 16.0F, 2.0F, 0.0F, false);
      this.updateDefaultPose();
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root, this.tonguePivot, this.tongue);
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void setAttributes(float f, float rotX, float rotY, float additionalYaw) {
      this.resetToDefaultPose();
      this.stretch = f;
      float f1 = 1.0F;
      this.tongue.setScale(f1, this.stretch, f1);
      this.tonguePivot.rotateAngleX = (float)Math.toRadians(rotX);
      this.tonguePivot.rotateAngleY = (float)Math.toRadians(rotY);
      this.tongue.rotateAngleY = (float)Math.toRadians(-additionalYaw);
      this.tonguePivot.showModel = !HIDE;
      this.root.showModel = !HIDE;
      this.tongue.showModel = !HIDE;
   }
}
