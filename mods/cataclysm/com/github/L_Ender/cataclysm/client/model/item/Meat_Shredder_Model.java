package com.github.L_Ender.cataclysm.client.model.item;

import com.github.L_Ender.cataclysm.client.render.CMItemstackRenderer;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class Meat_Shredder_Model extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox gear1;
   private final AdvancedModelBox core;
   private final AdvancedModelBox core2;
   private final AdvancedModelBox saw;
   private final AdvancedModelBox gear2;
   private final AdvancedModelBox coreroot;
   private final AdvancedModelBox longthing;

   public Meat_Shredder_Model() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.root.setTextureOffset(18, 0).addBox(-3.0F, -46.5646F, -1.0268F, 6.0F, 2.0F, 2.0F, 0.0F, false);
      this.root.setTextureOffset(0, 55).addBox(1.5F, -43.0F, -2.0F, 1.0F, 8.0F, 4.0F, 0.0F, false);
      this.root.setTextureOffset(6, 18).addBox(-2.5F, -35.0F, -2.0F, 5.0F, 2.0F, 4.0F, 0.0F, false);
      this.root.setTextureOffset(52, 28).addBox(-2.5F, -43.0F, -2.0F, 1.0F, 8.0F, 4.0F, 0.0F, false);
      this.root.setTextureOffset(48, 18).addBox(-2.5F, -48.0111F, -2.5268F, 1.0F, 5.0F, 5.0F, 0.0025F, false);
      this.root.setTextureOffset(16, 48).addBox(1.5F, -48.0111F, -2.5268F, 1.0F, 5.0F, 5.0F, 0.0025F, false);
      this.root.setTextureOffset(49, 0).addBox(1.5F, -48.0111F, -6.5268F, 1.0F, 3.0F, 4.0F, 0.0F, false);
      this.root.setTextureOffset(33, 0).addBox(-2.0F, -31.0F, -2.0F, 4.0F, 7.0F, 4.0F, 0.0F, false);
      this.root.setTextureOffset(0, 4).addBox(0.0F, -32.0F, 1.0F, 0.0F, 13.0F, 5.0F, 0.0F, false);
      this.root.setTextureOffset(0, 0).addBox(-3.0F, -33.5F, -3.0F, 6.0F, 3.0F, 6.0F, 0.0F, false);
      this.root.setTextureOffset(21, 6).addBox(-1.5F, -24.0F, -1.5F, 3.0F, 13.0F, 3.0F, 0.0F, false);
      this.root.setTextureOffset(0, 48).addBox(-2.0F, -17.0F, -2.0F, 4.0F, 3.0F, 4.0F, 0.0F, false);
      this.root.setTextureOffset(18, 58).addBox(0.3F, -23.0F, -2.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
      this.root.setTextureOffset(31, 21).addBox(0.3F, -23.0F, -2.0F, 2.0F, 1.0F, 2.0F, 0.2F, false);
      this.root.setTextureOffset(12, 48).addBox(-2.3F, -23.0F, -2.0F, 2.0F, 1.0F, 2.0F, 0.2F, false);
      this.root.setTextureOffset(45, 19).addBox(-2.3F, -18.0F, -2.0F, 2.0F, 1.0F, 2.0F, 0.2F, false);
      this.root.setTextureOffset(39, 21).addBox(0.3F, -18.0F, -2.0F, 2.0F, 1.0F, 2.0F, 0.2F, false);
      this.root.setTextureOffset(52, 40).addBox(-2.3F, -23.0F, -2.0F, 2.0F, 6.0F, 2.0F, 0.0F, false);
      this.root.setTextureOffset(10, 56).addBox(-1.0F, -11.0F, -1.0F, 2.0F, 11.0F, 2.0F, 0.0F, false);
      this.root.setTextureOffset(10, 68).addBox(0.0F, -10.5F, 0.5F, 0.0F, 7.0F, 1.0F, 0.0F, false);
      this.root.setTextureOffset(10, 68).addBox(0.0F, -10.5F, -1.5F, 0.0F, 7.0F, 1.0F, 0.0F, false);
      this.root.setTextureOffset(16, 69).addBox(0.5F, -10.5F, 0.0F, 1.0F, 9.0F, 0.0F, 0.0F, false);
      this.root.setTextureOffset(16, 69).addBox(-1.5F, -10.5F, 0.0F, 1.0F, 9.0F, 0.0F, 0.0F, false);
      this.root.setTextureOffset(48, 28).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, 0.1F, false);
      this.gear1 = new AdvancedModelBox(this);
      this.gear1.setRotationPoint(2.0F, -45.5F, -6.5F);
      this.root.addChild(this.gear1);
      this.setRotationAngle(this.gear1, 0.7854F, 0.0F, 0.0F);
      this.gear1.setTextureOffset(10, 9).addBox(-0.5F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, 0.0025F, false);
      this.core = new AdvancedModelBox(this);
      this.core.setRotationPoint(0.0F, -27.5F, -3.5F);
      this.root.addChild(this.core);
      this.setRotationAngle(this.core, 0.0873F, 0.0F, 0.0F);
      this.core.setTextureOffset(58, 4).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 2.0F, 3.0F, 0.0F, false);
      this.core.setTextureOffset(6, 55).addBox(-1.0F, -3.5F, -2.5F, 2.0F, 2.0F, 1.0F, 0.1F, false);
      this.core.setTextureOffset(4, 0).addBox(0.0F, -3.0F, -3.4F, 0.0F, 1.0F, 1.0F, 0.0F, false);
      this.core2 = new AdvancedModelBox(this);
      this.core2.setRotationPoint(0.0F, -27.5F, -3.5F);
      this.root.addChild(this.core2);
      this.setRotationAngle(this.core2, 0.0873F, 0.0F, 0.0F);
      this.core2.setTextureOffset(45, 0).addBox(-1.0F, -0.3F, -2.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
      this.core2.setTextureOffset(55, 0).addBox(-1.0F, -0.3F, -2.1F, 2.0F, 2.0F, 1.0F, 0.1F, false);
      this.core2.setTextureOffset(4, 1).addBox(0.0F, 0.2F, -3.0F, 0.0F, 1.0F, 1.0F, 0.0F, false);
      this.saw = new AdvancedModelBox(this);
      this.saw.setRotationPoint(0.0F, -45.5F, 0.0F);
      this.root.addChild(this.saw);
      this.saw.setTextureOffset(32, 32).addBox(-1.0F, -8.0F, -8.0F, 2.0F, 16.0F, 16.0F, 0.0F, false);
      this.saw.setTextureOffset(0, 0).addBox(0.0F, -12.0F, -12.0F, 0.0F, 24.0F, 24.0F, 0.0F, false);
      this.gear2 = new AdvancedModelBox(this);
      this.gear2.setRotationPoint(3.0F, -30.5F, 1.5F);
      this.root.addChild(this.gear2);
      this.setRotationAngle(this.gear2, -0.7854F, 0.0F, 0.0F);
      this.gear2.setTextureOffset(33, 11).addBox(-0.5F, -2.5F, -2.5F, 2.0F, 5.0F, 5.0F, 0.0F, false);
      this.gear2.setTextureOffset(56, 13).addBox(0.5F, -2.5F, -2.5F, 1.0F, 5.0F, 5.0F, 0.2F, false);
      this.coreroot = new AdvancedModelBox(this);
      this.coreroot.setRotationPoint(0.0F, -27.5F, -3.5F);
      this.root.addChild(this.coreroot);
      this.setRotationAngle(this.coreroot, 0.0873F, 0.0F, 0.0F);
      this.coreroot.setTextureOffset(47, 8).addBox(-2.0F, -4.5F, 0.0F, 4.0F, 7.0F, 3.0F, 0.1F, false);
      this.longthing = new AdvancedModelBox(this);
      this.longthing.setRotationPoint(2.0F, -33.0F, -3.0F);
      this.root.addChild(this.longthing);
      this.setRotationAngle(this.longthing, 0.3054F, 0.0F, 0.0F);
      this.longthing.setTextureOffset(0, 0).addBox(-0.5F, -4.3786F, 0.1595F, 1.0F, 5.0F, 1.0F, 0.0025F, false);
      this.longthing.setTextureOffset(28, 47).addBox(0.0F, -12.3786F, 0.1595F, 0.0F, 8.0F, 1.0F, 0.0025F, false);
      this.updateDefaultPose();
   }

   public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.resetToDefaultPose();
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.root, this.gear1, this.gear2, this.core, this.core2, this.saw, this.coreroot, this.longthing);
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public void animateStack(ItemStack itemStackIn) {
      this.resetToDefaultPose();
      float partialTick = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
      float tick = Minecraft.getInstance().player == null ? 0.0F : partialTick + Minecraft.getInstance().player.tickCount;
      if (Minecraft.getInstance().isPaused()) {
         tick = CMItemstackRenderer.ticksExisted;
      }

      this.core.rotationPointZ = this.core.rotationPointZ + (Mth.cos(tick) * 1.0F + 1.0F);
      this.core2.rotationPointZ = this.core2.rotationPointZ + (Mth.cos(tick + (float) Math.PI) * 1.0F + 1.0F);
      this.saw.rotateAngleX += tick * 0.75F;
   }
}
