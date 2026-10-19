package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.entity.Pet.The_Baby_Leviathan_Entity;
import com.github.L_Ender.lionfishapi.client.model.Animations.ModelAnimator;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedEntityModel;
import com.github.L_Ender.lionfishapi.client.model.tools.AdvancedModelBox;
import com.github.L_Ender.lionfishapi.client.model.tools.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;

public class The_Baby_Leviathan_Model extends AdvancedEntityModel<The_Baby_Leviathan_Entity> {
   private final AdvancedModelBox root;
   private final AdvancedModelBox body;
   private final AdvancedModelBox main_belly;
   private final AdvancedModelBox r_tentacle;
   private final AdvancedModelBox r_tentacle2;
   private final AdvancedModelBox r_hook1;
   private final AdvancedModelBox r_hook4;
   private final AdvancedModelBox r_hook2;
   private final AdvancedModelBox r_hook3;
   private final AdvancedModelBox l_tentacle;
   private final AdvancedModelBox l_tentacle2;
   private final AdvancedModelBox l_hook1;
   private final AdvancedModelBox l_hook2;
   private final AdvancedModelBox l_hook3;
   private final AdvancedModelBox l_hook4;
   private final AdvancedModelBox belly;
   private final AdvancedModelBox r_down_fin;
   private final AdvancedModelBox l_down_fin;
   private final AdvancedModelBox tail;
   private final AdvancedModelBox tail_back;
   private final AdvancedModelBox r_spike2;
   private final AdvancedModelBox l_spike2;
   private final AdvancedModelBox head;
   private final AdvancedModelBox maw;
   private final AdvancedModelBox skul;
   private final AdvancedModelBox main_mouth;
   private final AdvancedModelBox mouth1;
   private final AdvancedModelBox mouth1_e;
   private final AdvancedModelBox mouth2;
   private final AdvancedModelBox mouth2_e;
   private final AdvancedModelBox mouth3;
   private final AdvancedModelBox mouth3_e;
   private final AdvancedModelBox mouth4;
   private final AdvancedModelBox mouth4_e;
   private final AdvancedModelBox r_fin;
   private final AdvancedModelBox l_fin;
   private final AdvancedModelBox r_spike1;
   private final AdvancedModelBox l_spike1;
   private ModelAnimator animator;

   public The_Baby_Leviathan_Model() {
      this.texWidth = 64;
      this.texHeight = 64;
      this.root = new AdvancedModelBox(this);
      this.root.setRotationPoint(0.0F, 24.0F, 0.0F);
      this.head = new AdvancedModelBox(this);
      this.head.setRotationPoint(0.0F, -2.4F, -5.9F);
      this.root.addChild(this.head);
      this.maw = new AdvancedModelBox(this);
      this.maw.setRotationPoint(0.0F, 0.75F, 0.0F);
      this.head.addChild(this.maw);
      this.maw.setTextureOffset(34, 20).addBox(-1.5F, -1.15F, -3.1F, 3.0F, 2.0F, 3.0F, 0.0F, false);
      this.maw.setTextureOffset(10, 0).addBox(-1.5F, -0.15F, -3.1F, 3.0F, 0.0F, 3.0F, 0.0F, false);
      this.maw.setTextureOffset(10, 4).addBox(-1.0F, -1.05F, -3.9F, 2.0F, 2.0F, 1.0F, 0.0F, false);
      this.maw.setTextureOffset(0, 7).addBox(-1.0F, -0.05F, -3.9F, 2.0F, 0.0F, 1.0F, 0.0F, false);
      this.skul = new AdvancedModelBox(this);
      this.skul.setRotationPoint(0.0F, -1.1F, -0.1F);
      this.head.addChild(this.skul);
      this.skul.setTextureOffset(6, 28).addBox(-1.0F, -1.0F, -3.8F, 2.0F, 2.0F, 1.0F, -0.1F, false);
      this.skul.setTextureOffset(34, 26).addBox(-1.5F, -1.0F, -3.0F, 3.0F, 2.0F, 3.0F, -0.1F, false);
      this.main_mouth = new AdvancedModelBox(this);
      this.main_mouth.setRotationPoint(0.0F, -0.1F, -0.1F);
      this.head.addChild(this.main_mouth);
      this.mouth1 = new AdvancedModelBox(this);
      this.mouth1.setRotationPoint(-1.5F, 0.9064F, 0.0717F);
      this.main_mouth.addChild(this.mouth1);
      this.setRotationAngle(this.mouth1, 0.0F, 0.0F, 0.0F);
      this.mouth1.setTextureOffset(11, 31).addBox(-1.5F, -0.9064F, -3.0717F, 3.0F, 2.0F, 3.0F, 0.0F, false);
      this.mouth1_e = new AdvancedModelBox(this);
      this.mouth1_e.setRotationPoint(0.0F, 1.0936F, -3.0717F);
      this.mouth1.addChild(this.mouth1_e);
      this.setRotationAngle(this.mouth1_e, -0.0873F, 0.0F, 0.0F);
      this.mouth1_e.setTextureOffset(0, 17).addBox(-1.51F, -2.01F, -3.01F, 3.0F, 2.0F, 3.0F, 0.01F, false);
      this.mouth2 = new AdvancedModelBox(this);
      this.mouth2.setRotationPoint(1.5F, 0.9064F, 0.0717F);
      this.main_mouth.addChild(this.mouth2);
      this.setRotationAngle(this.mouth2, 0.0F, 0.0F, 0.0F);
      this.mouth2.setTextureOffset(11, 31).addBox(-1.5F, -0.9064F, -3.0717F, 3.0F, 2.0F, 3.0F, 0.0F, true);
      this.mouth2_e = new AdvancedModelBox(this);
      this.mouth2_e.setRotationPoint(0.0F, 1.0936F, -3.0717F);
      this.mouth2.addChild(this.mouth2_e);
      this.setRotationAngle(this.mouth2_e, -0.0873F, 0.0F, 0.0F);
      this.mouth2_e.setTextureOffset(0, 17).addBox(-1.49F, -2.01F, -3.01F, 3.0F, 2.0F, 3.0F, 0.01F, true);
      this.mouth3 = new AdvancedModelBox(this);
      this.mouth3.setRotationPoint(-1.5F, -0.9064F, 0.0717F);
      this.main_mouth.addChild(this.mouth3);
      this.mouth3.setTextureOffset(32, 10).addBox(-1.5F, -1.0936F, -3.0717F, 3.0F, 2.0F, 3.0F, 0.0F, false);
      this.mouth3_e = new AdvancedModelBox(this);
      this.mouth3_e.setRotationPoint(0.0F, -1.0936F, -3.0717F);
      this.mouth3.addChild(this.mouth3_e);
      this.setRotationAngle(this.mouth3_e, 0.0873F, 0.0F, 0.0F);
      this.mouth3_e.setTextureOffset(24, 31).addBox(-1.515F, 0.015F, -3.015F, 3.0F, 2.0F, 3.0F, 0.015F, false);
      this.mouth4 = new AdvancedModelBox(this);
      this.mouth4.setRotationPoint(1.5F, -0.9064F, 0.0717F);
      this.main_mouth.addChild(this.mouth4);
      this.mouth4.setTextureOffset(32, 10).addBox(-1.5F, -1.0936F, -3.0717F, 3.0F, 2.0F, 3.0F, 0.0F, true);
      this.mouth4_e = new AdvancedModelBox(this);
      this.mouth4_e.setRotationPoint(0.0F, -1.0936F, -3.0717F);
      this.mouth4.addChild(this.mouth4_e);
      this.setRotationAngle(this.mouth4_e, 0.0873F, 0.0F, 0.0F);
      this.mouth4_e.setTextureOffset(24, 31).addBox(-1.485F, 0.015F, -3.015F, 3.0F, 2.0F, 3.0F, 0.015F, true);
      this.body = new AdvancedModelBox(this);
      this.body.setRotationPoint(0.0F, 0.4F, 8.8F);
      this.head.addChild(this.body);
      this.body.setTextureOffset(14, 12).addBox(-3.0F, -3.0F, -8.9F, 6.0F, 5.0F, 5.0F, 0.0F, false);
      this.body.setTextureOffset(0, 28).addBox(0.0F, -6.0F, -8.9F, 0.0F, 3.0F, 5.0F, 0.0F, false);
      this.main_belly = new AdvancedModelBox(this);
      this.main_belly.setRotationPoint(-0.9512F, 0.0F, -3.9F);
      this.body.addChild(this.main_belly);
      this.main_belly.setTextureOffset(19, 0).addBox(-1.5488F, -3.0F, 0.0F, 5.0F, 5.0F, 4.0F, 0.0F, false);
      this.main_belly.setTextureOffset(0, 0).addBox(0.9512F, -5.0F, 0.0F, 0.0F, 2.0F, 4.0F, 0.0F, false);
      this.r_tentacle = new AdvancedModelBox(this);
      this.r_tentacle.setRotationPoint(-1.5488F, -1.5F, 2.5F);
      this.main_belly.addChild(this.r_tentacle);
      this.setRotationAngle(this.r_tentacle, 0.0F, -0.6109F, 0.0F);
      this.r_tentacle.setTextureOffset(36, 36).addBox(-6.0F, -0.5F, -0.5F, 6.0F, 1.0F, 1.0F, 0.0F, false);
      this.r_tentacle2 = new AdvancedModelBox(this);
      this.r_tentacle2.setRotationPoint(-6.0F, 0.0F, 0.0F);
      this.r_tentacle.addChild(this.r_tentacle2);
      this.setRotationAngle(this.r_tentacle2, 0.0F, -0.9599F, 0.0F);
      this.r_tentacle2.setTextureOffset(34, 0).addBox(-6.0F, -0.5F, -0.5F, 6.0F, 1.0F, 1.0F, 0.0F, false);
      this.r_hook1 = new AdvancedModelBox(this);
      this.r_hook1.setRotationPoint(-6.0F, 0.0F, 0.5F);
      this.r_tentacle2.addChild(this.r_hook1);
      this.setRotationAngle(this.r_hook1, 0.0F, 0.7854F, 0.0F);
      this.r_hook1.setTextureOffset(0, 0).addBox(-1.0F, -0.5F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, false);
      this.r_hook4 = new AdvancedModelBox(this);
      this.r_hook4.setRotationPoint(-6.0F, 0.0F, -0.5F);
      this.r_tentacle2.addChild(this.r_hook4);
      this.setRotationAngle(this.r_hook4, 0.0F, -0.7854F, 0.0F);
      this.r_hook4.setTextureOffset(0, 0).addBox(-1.0F, -0.5F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, false);
      this.r_hook2 = new AdvancedModelBox(this);
      this.r_hook2.setRotationPoint(-6.0F, -0.5F, 0.0F);
      this.r_tentacle2.addChild(this.r_hook2);
      this.setRotationAngle(this.r_hook2, 0.0F, 0.0F, 0.7854F);
      this.r_hook2.setTextureOffset(19, 10).addBox(-1.0F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, 0.0F, false);
      this.r_hook3 = new AdvancedModelBox(this);
      this.r_hook3.setRotationPoint(-6.0F, 0.5F, 0.0F);
      this.r_tentacle2.addChild(this.r_hook3);
      this.setRotationAngle(this.r_hook3, 0.0F, 0.0F, -0.7854F);
      this.r_hook3.setTextureOffset(19, 10).addBox(-1.0F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, 0.0F, false);
      this.l_tentacle = new AdvancedModelBox(this);
      this.l_tentacle.setRotationPoint(3.4512F, -1.5F, 2.5F);
      this.main_belly.addChild(this.l_tentacle);
      this.setRotationAngle(this.l_tentacle, 0.0F, 0.6109F, 0.0F);
      this.l_tentacle.setTextureOffset(36, 36).addBox(0.0F, -0.5F, -0.5F, 6.0F, 1.0F, 1.0F, 0.0F, true);
      this.l_tentacle2 = new AdvancedModelBox(this);
      this.l_tentacle2.setRotationPoint(6.0F, 0.0F, 0.0F);
      this.l_tentacle.addChild(this.l_tentacle2);
      this.setRotationAngle(this.l_tentacle2, 0.0F, 0.9599F, 0.0F);
      this.l_tentacle2.setTextureOffset(34, 0).addBox(0.0F, -0.5F, -0.5F, 6.0F, 1.0F, 1.0F, 0.0F, true);
      this.l_hook1 = new AdvancedModelBox(this);
      this.l_hook1.setRotationPoint(6.0F, 0.0F, 0.5F);
      this.l_tentacle2.addChild(this.l_hook1);
      this.setRotationAngle(this.l_hook1, 0.0F, -0.7854F, 0.0F);
      this.l_hook1.setTextureOffset(0, 0).addBox(0.0F, -0.5F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, true);
      this.l_hook2 = new AdvancedModelBox(this);
      this.l_hook2.setRotationPoint(6.0F, 0.0F, -0.5F);
      this.l_tentacle2.addChild(this.l_hook2);
      this.setRotationAngle(this.l_hook2, 0.0F, 0.7854F, 0.0F);
      this.l_hook2.setTextureOffset(0, 0).addBox(0.0F, -0.5F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, true);
      this.l_hook3 = new AdvancedModelBox(this);
      this.l_hook3.setRotationPoint(6.0F, -0.5F, 0.0F);
      this.l_tentacle2.addChild(this.l_hook3);
      this.setRotationAngle(this.l_hook3, 0.0F, 0.0F, -0.7854F);
      this.l_hook3.setTextureOffset(19, 10).addBox(0.0F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, 0.0F, true);
      this.l_hook4 = new AdvancedModelBox(this);
      this.l_hook4.setRotationPoint(6.0F, 0.5F, 0.0F);
      this.l_tentacle2.addChild(this.l_hook4);
      this.setRotationAngle(this.l_hook4, 0.0F, 0.0F, 0.7854F);
      this.l_hook4.setTextureOffset(19, 10).addBox(0.0F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, 0.0F, true);
      this.belly = new AdvancedModelBox(this);
      this.belly.setRotationPoint(0.9512F, -1.0F, 4.0F);
      this.main_belly.addChild(this.belly);
      this.belly.setTextureOffset(19, 23).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 3.0F, 0.0F, false);
      this.belly.setTextureOffset(42, 42).addBox(0.0F, -4.0F, 0.0F, 0.0F, 2.0F, 3.0F, 0.0F, false);
      this.r_down_fin = new AdvancedModelBox(this);
      this.r_down_fin.setRotationPoint(-2.0F, 2.0F, 1.0F);
      this.belly.addChild(this.r_down_fin);
      this.setRotationAngle(this.r_down_fin, 0.0F, 0.0F, -0.5236F);
      this.r_down_fin.setTextureOffset(37, 16).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 0.0F, 3.0F, 0.0F, false);
      this.l_down_fin = new AdvancedModelBox(this);
      this.l_down_fin.setRotationPoint(2.0F, 2.0F, 1.0F);
      this.belly.addChild(this.l_down_fin);
      this.setRotationAngle(this.l_down_fin, 0.0F, 0.0F, 0.5236F);
      this.l_down_fin.setTextureOffset(37, 16).addBox(0.0F, 0.0F, -1.0F, 2.0F, 0.0F, 3.0F, 0.0F, true);
      this.tail = new AdvancedModelBox(this);
      this.tail.setRotationPoint(0.0F, -0.5F, 3.0F);
      this.belly.addChild(this.tail);
      this.tail.setTextureOffset(11, 37).addBox(-1.0F, -1.5F, 0.0F, 2.0F, 3.0F, 3.0F, 0.0F, false);
      this.tail.setTextureOffset(38, 39).addBox(0.0F, -3.5F, 0.0F, 0.0F, 2.0F, 3.0F, 0.0F, false);
      this.tail.setTextureOffset(31, 37).addBox(0.0F, 1.5F, 0.0F, 0.0F, 2.0F, 3.0F, 0.0F, false);
      this.tail_back = new AdvancedModelBox(this);
      this.tail_back.setRotationPoint(0.0F, -0.25F, 3.0F);
      this.tail.addChild(this.tail_back);
      this.tail_back.setTextureOffset(38, 3).addBox(-0.5F, -1.25F, 0.0F, 1.0F, 2.0F, 3.0F, 0.0F, false);
      this.tail_back.setTextureOffset(0, 0).addBox(0.0F, -3.25F, 0.0F, 0.0F, 7.0F, 9.0F, 0.0F, false);
      this.r_spike2 = new AdvancedModelBox(this);
      this.r_spike2.setRotationPoint(-1.5488F, -3.0F, 2.0F);
      this.main_belly.addChild(this.r_spike2);
      this.setRotationAngle(this.r_spike2, 0.0F, 0.0F, -0.7854F);
      this.r_spike2.setTextureOffset(22, 37).addBox(0.0F, -1.0F, -2.0F, 0.0F, 1.0F, 4.0F, 0.0F, false);
      this.l_spike2 = new AdvancedModelBox(this);
      this.l_spike2.setRotationPoint(3.4512F, -3.0F, 2.0F);
      this.main_belly.addChild(this.l_spike2);
      this.setRotationAngle(this.l_spike2, 0.0F, 0.0F, 0.7854F);
      this.l_spike2.setTextureOffset(22, 37).addBox(0.0F, -1.0F, -2.0F, 0.0F, 1.0F, 4.0F, 0.0F, true);
      this.r_fin = new AdvancedModelBox(this);
      this.r_fin.setRotationPoint(-3.0F, 1.75F, -6.9F);
      this.body.addChild(this.r_fin);
      this.setRotationAngle(this.r_fin, 0.0F, 0.0F, -0.0436F);
      this.r_fin.setTextureOffset(0, 23).addBox(-5.0F, 0.0F, -2.0F, 5.0F, 0.0F, 4.0F, 0.0F, false);
      this.l_fin = new AdvancedModelBox(this);
      this.l_fin.setRotationPoint(3.0F, 1.75F, -6.9F);
      this.body.addChild(this.l_fin);
      this.setRotationAngle(this.l_fin, 0.0F, 0.0F, 0.0436F);
      this.l_fin.setTextureOffset(0, 23).addBox(0.0F, 0.0F, -2.0F, 5.0F, 0.0F, 4.0F, 0.0F, true);
      this.r_spike1 = new AdvancedModelBox(this);
      this.r_spike1.setRotationPoint(-3.0F, -3.0F, -6.4F);
      this.body.addChild(this.r_spike1);
      this.setRotationAngle(this.r_spike1, 0.0F, 0.0F, -0.7854F);
      this.r_spike1.setTextureOffset(0, 37).addBox(0.0F, -1.0F, -2.5F, 0.0F, 1.0F, 5.0F, 0.0F, false);
      this.l_spike1 = new AdvancedModelBox(this);
      this.l_spike1.setRotationPoint(3.0F, -3.0F, -6.4F);
      this.body.addChild(this.l_spike1);
      this.setRotationAngle(this.l_spike1, 0.0F, 0.0F, 0.7854F);
      this.l_spike1.setTextureOffset(0, 37).addBox(0.0F, -1.0F, -2.5F, 0.0F, 1.0F, 5.0F, 0.0F, true);
      this.animator = ModelAnimator.create();
      this.updateDefaultPose();
   }

   public void animate(The_Baby_Leviathan_Entity entity, float f, float f1, float f2, float f3, float f4) {
      this.resetToDefaultPose();
      this.animator.update(entity);
      this.animator.setAnimation(The_Baby_Leviathan_Entity.BABY_LEVIATHAN_BITE);
      this.animator.startKeyframe(5);
      this.animator.rotate(this.root, (float)Math.toRadians(-10.0), 0.0F, 0.0F);
      this.animator.rotate(this.belly, (float)Math.toRadians(-15.0), 0.0F, 0.0F);
      this.animator.rotate(this.tail, (float)Math.toRadians(-17.5), 0.0F, 0.0F);
      this.animator.rotate(this.maw, (float)Math.toRadians(20.0), 0.0F, 0.0F);
      this.animator.rotate(this.skul, (float)Math.toRadians(-30.0), 0.0F, 0.0F);
      this.animator.rotate(this.mouth1, (float)Math.toRadians(22.5), (float)Math.toRadians(15.0), (float)Math.toRadians(10.0));
      this.animator.rotate(this.mouth2, (float)Math.toRadians(22.5), (float)Math.toRadians(-20.0), (float)Math.toRadians(-12.5));
      this.animator.rotate(this.mouth3, (float)Math.toRadians(-22.5), (float)Math.toRadians(22.5), (float)Math.toRadians(-2.5));
      this.animator.rotate(this.mouth4, (float)Math.toRadians(-25.0), (float)Math.toRadians(-22.5), (float)Math.toRadians(2.5));
      this.animator.endKeyframe();
      this.animator.startKeyframe(2);
      this.animator.rotate(this.root, (float)Math.toRadians(-1.25), 0.0F, 0.0F);
      this.animator.move(this.root, 0.0F, 0.0F, -2.0F);
      this.animator.rotate(this.belly, (float)Math.toRadians(37.5), 0.0F, 0.0F);
      this.animator.rotate(this.tail, (float)Math.toRadians(-1.25), 0.0F, 0.0F);
      this.animator.rotate(this.maw, (float)Math.toRadians(30.0), 0.0F, 0.0F);
      this.animator.rotate(this.skul, (float)Math.toRadians(-47.5), 0.0F, 0.0F);
      this.animator.rotate(this.mouth1, (float)Math.toRadians(32.5), (float)Math.toRadians(22.5), (float)Math.toRadians(15.0));
      this.animator.rotate(this.mouth2, (float)Math.toRadians(35.0), (float)Math.toRadians(-30.0), (float)Math.toRadians(-17.5));
      this.animator.rotate(this.mouth3, (float)Math.toRadians(-35.0), (float)Math.toRadians(35.0), (float)Math.toRadians(-5.0));
      this.animator.rotate(this.mouth4, (float)Math.toRadians(-37.5), (float)Math.toRadians(-35.0), (float)Math.toRadians(5.0));
      this.animator.endKeyframe();
      this.animator.startKeyframe(2);
      this.animator.rotate(this.root, (float)Math.toRadians(7.5), 0.0F, 0.0F);
      this.animator.move(this.root, 0.0F, 0.0F, -4.0F);
      this.animator.rotate(this.belly, (float)Math.toRadians(22.5), 0.0F, 0.0F);
      this.animator.rotate(this.tail, (float)Math.toRadians(15.0), 0.0F, 0.0F);
      this.animator.endKeyframe();
      this.animator.resetKeyframe(5);
      this.animator.setAnimation(The_Baby_Leviathan_Entity.BABY_LEVIATHAN_ABYSS_BLAST);
      this.animator.startKeyframe(20);
      this.animator.rotate(this.mouth1, (float)Math.toRadians(2.5), (float)Math.toRadians(5.0), 0.0F);
      this.animator.rotate(this.mouth2, (float)Math.toRadians(2.5), (float)Math.toRadians(-5.0), 0.0F);
      this.animator.rotate(this.mouth3, (float)Math.toRadians(-15.0), (float)Math.toRadians(7.5), 0.0F);
      this.animator.rotate(this.mouth4, (float)Math.toRadians(-15.0), (float)Math.toRadians(-7.5), 0.0F);
      this.animator.rotate(this.maw, (float)Math.toRadians(10.0), 0.0F, 0.0F);
      this.animator.rotate(this.skul, (float)Math.toRadians(-10.0), 0.0F, 0.0F);
      this.animator.rotate(this.tail, (float)Math.toRadians(15.0), 0.0F, 0.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(35);
      this.animator.startKeyframe(2);
      this.animator.rotate(this.mouth1, (float)Math.toRadians(37.5), (float)Math.toRadians(40.0), 0.0F);
      this.animator.rotate(this.mouth2, (float)Math.toRadians(37.5), (float)Math.toRadians(-40.0), 0.0F);
      this.animator.rotate(this.mouth3, (float)Math.toRadians(-37.5), (float)Math.toRadians(40.0), 0.0F);
      this.animator.rotate(this.mouth4, (float)Math.toRadians(-37.5), (float)Math.toRadians(-40.0), 0.0F);
      this.animator.rotate(this.maw, (float)Math.toRadians(17.5), 0.0F, 0.0F);
      this.animator.rotate(this.skul, (float)Math.toRadians(-20.0), 0.0F, 0.0F);
      this.animator.rotate(this.tail, (float)Math.toRadians(25.0), 0.0F, 0.0F);
      this.animator.endKeyframe();
      this.animator.setStaticKeyframe(80);
      this.animator.resetKeyframe(20);
   }

   public void setupAnim(The_Baby_Leviathan_Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.animate(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      float swimSpeed = 0.1F;
      float swimDegree = 0.4F;
      float finspeed = 0.1F;
      float finDegree = 0.2F;
      AdvancedModelBox[] tailBoxes = new AdvancedModelBox[]{this.tail, this.tail_back};
      AdvancedModelBox[] rt = new AdvancedModelBox[]{this.r_tentacle, this.r_tentacle};
      AdvancedModelBox[] lt = new AdvancedModelBox[]{this.l_tentacle, this.l_tentacle2};
      float partialTick = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
      float sitProgress = entityIn.prevSitProgress + (entityIn.sitProgress - entityIn.prevSitProgress) * partialTick;
      float swimProgress = entityIn.prevSwimProgress + (entityIn.SwimProgress - entityIn.prevSwimProgress) * partialTick;
      this.progressRotationPrev(this.r_fin, swimProgress, 0.0F, 0.0F, (float)Math.toRadians(2.5), 5.0F);
      this.progressRotationPrev(this.l_fin, swimProgress, 0.0F, 0.0F, (float)Math.toRadians(-2.5), 5.0F);
      this.progressRotationPrev(this.r_down_fin, swimProgress, 0.0F, 0.0F, (float)Math.toRadians(30.0), 5.0F);
      this.progressRotationPrev(this.l_down_fin, swimProgress, 0.0F, 0.0F, (float)Math.toRadians(-30.0), 5.0F);
      this.progressRotationPrev(this.main_belly, sitProgress, 0.0F, (float)Math.toRadians(20.0), 0.0F, 5.0F);
      this.progressRotationPrev(this.r_tentacle, sitProgress, 0.0F, 0.0F, (float)Math.toRadians(-35.0), 5.0F);
      this.progressRotationPrev(this.l_tentacle, sitProgress, (float)Math.toRadians(-2.5), (float)Math.toRadians(-22.5), (float)Math.toRadians(25.0), 5.0F);
      this.progressRotationPrev(this.belly, sitProgress, 0.0F, (float)Math.toRadians(30.0), 0.0F, 5.0F);
      this.progressRotationPrev(this.tail, sitProgress, 0.0F, (float)Math.toRadians(32.5), 0.0F, 5.0F);
      this.progressRotationPrev(this.tail_back, sitProgress, 0.0F, (float)Math.toRadians(35.0), 0.0F, 5.0F);
      this.progressRotationPrev(this.skul, sitProgress, (float)Math.toRadians(7.5), (float)Math.toRadians(-22.5), 0.0F, 5.0F);
      this.progressRotationPrev(this.maw, sitProgress, (float)Math.toRadians(7.5), (float)Math.toRadians(-22.5), 0.0F, 5.0F);
      this.progressRotationPrev(this.main_mouth, sitProgress, (float)Math.toRadians(7.5), (float)Math.toRadians(-22.5), 0.0F, 5.0F);
      this.flap(this.l_hook3, swimSpeed * 0.2F, 0.35F, true, 0.0F, -0.35F, ageInTicks, 1.0F);
      this.swing(this.l_hook1, swimSpeed * 0.2F, 0.35F, true, 0.0F, -0.35F, ageInTicks, 1.0F);
      this.swing(this.l_hook1, swimSpeed * 0.2F, 0.35F, false, 0.0F, -0.35F, ageInTicks, 1.0F);
      this.flap(this.l_hook4, swimSpeed * 0.2F, 0.35F, false, 0.0F, -0.35F, ageInTicks, 1.0F);
      this.flap(this.l_hook3, swimSpeed * 1.3F, 0.35F, true, 0.0F, -0.35F, limbSwing, limbSwingAmount);
      this.swing(this.l_hook2, swimSpeed * 1.3F, 0.35F, true, 0.0F, -0.35F, limbSwing, limbSwingAmount);
      this.swing(this.l_hook2, swimSpeed * 1.3F, 0.35F, false, 0.0F, -0.35F, limbSwing, limbSwingAmount);
      this.flap(this.l_hook4, swimSpeed * 1.3F, 0.35F, false, 0.0F, -0.35F, limbSwing, limbSwingAmount);
      float walkSwingAmount = limbSwingAmount * (1.0F - 0.2F * swimProgress);
      float swimSwingAmount = limbSwingAmount * 0.2F * swimProgress;
      this.flap(this.r_fin, finspeed * 4.0F, finDegree, false, 0.0F, -0.2F, limbSwing, swimSwingAmount);
      this.flap(this.l_fin, finspeed * 4.0F, finDegree, true, 0.0F, -0.2F, limbSwing, swimSwingAmount);
      this.flap(this.r_down_fin, finspeed * 4.0F, finDegree * 1.5F, false, 0.0F, -0.3F, limbSwing, swimSwingAmount);
      this.flap(this.l_down_fin, finspeed * 4.0F, finDegree * 1.5F, true, 0.0F, -0.3F, limbSwing, swimSwingAmount);
      float walkSpeed = 1.0F;
      float walkDegree = 0.7F;
      float f1 = walkDegree * 0.15F;
      float headUp = 1.6F * Math.min(0.0F, (float)(Math.sin(limbSwing * walkSpeed) * walkSwingAmount * f1 * 9.0 - walkSwingAmount * f1 * 9.0));
      this.head.rotationPointY += headUp;
      this.head.rotationPointZ = this.head.rotationPointZ
         + (float)(Math.sin(limbSwing * walkSpeed - 1.5F) * walkSwingAmount * f1 * 9.0 - walkSwingAmount * f1 * 9.0);
      this.r_fin.rotationPointY += headUp;
      this.l_fin.rotationPointY += headUp;
      this.r_down_fin.rotationPointY += headUp;
      this.l_down_fin.rotationPointY += headUp;
      this.walk(this.tail, walkSpeed, walkDegree * 0.5F, true, 1.0F, 0.04F, limbSwing, walkSwingAmount);
      this.walk(this.tail_back, walkSpeed, walkDegree * 0.65F, false, 2.0F, -0.04F, limbSwing, walkSwingAmount);
      this.walk(this.head, walkSpeed, walkDegree * 0.5F, false, 0.0F, 0.04F, limbSwing, walkSwingAmount);
      this.flap(this.r_fin, walkSpeed, walkDegree, true, 3.0F, -0.3F, limbSwing, walkSwingAmount);
      this.flap(this.l_fin, walkSpeed, walkDegree, false, 3.0F, -0.3F, limbSwing, walkSwingAmount);
      this.swing(this.r_fin, walkSpeed, walkDegree, false, 2.0F, -0.3F, limbSwing, walkSwingAmount);
      this.swing(this.l_fin, walkSpeed, walkDegree, true, 2.0F, -0.3F, limbSwing, walkSwingAmount);
      this.flap(this.r_down_fin, walkSpeed, walkDegree * 0.5F, true, 3.0F, -0.15F, limbSwing, walkSwingAmount);
      this.flap(this.l_down_fin, walkSpeed, walkDegree * 0.5F, false, 3.0F, -0.15F, limbSwing, walkSwingAmount);
      this.swing(this.r_down_fin, walkSpeed, walkDegree, false, 2.0F, -0.3F, limbSwing, walkSwingAmount);
      this.swing(this.l_down_fin, walkSpeed, walkDegree, true, 2.0F, -0.3F, limbSwing, walkSwingAmount);
      this.chainSwing(tailBoxes, swimSpeed * 0.4F, swimDegree * 0.45F, -1.0, ageInTicks, 1.0F);
      this.chainSwing(tailBoxes, swimSpeed * 4.0F, swimDegree * 0.6F, -1.0, limbSwing, limbSwingAmount);
      this.chainWave(rt, 0.03F, -0.1F, -3.0, ageInTicks, 1.0F);
      this.chainSwing(rt, 0.03F, -0.1F, -3.0, ageInTicks, 1.0F);
      this.chainWave(lt, 0.03F, 0.1F, -3.0, ageInTicks, 1.0F);
      this.chainSwing(lt, 0.03F, 0.1F, -3.0, ageInTicks, 1.0F);
      this.root.rotateAngleX += headPitch * (float) (Math.PI / 180.0);
      this.root.rotateAngleY += netHeadYaw * (float) (Math.PI / 180.0);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(
         this.root,
         this.body,
         this.main_belly,
         this.r_tentacle,
         this.r_tentacle2,
         this.r_hook1,
         this.r_hook2,
         this.r_hook3,
         this.r_hook4,
         this.l_tentacle,
         this.l_tentacle2,
         this.l_hook1,
         new AdvancedModelBox[]{
            this.l_hook2,
            this.l_hook3,
            this.l_hook4,
            this.belly,
            this.r_down_fin,
            this.l_down_fin,
            this.tail,
            this.tail_back,
            this.r_spike1,
            this.r_spike2,
            this.head,
            this.maw,
            this.skul,
            this.main_mouth,
            this.mouth1,
            this.mouth1_e,
            this.mouth2,
            this.mouth2_e,
            this.mouth3,
            this.mouth3_e,
            this.mouth4,
            this.mouth4_e,
            this.r_fin,
            this.l_fin,
            this.l_spike1,
            this.l_spike2
         }
      );
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.root);
   }

   public void setRotationAngle(AdvancedModelBox AdvancedModelBox, float x, float y, float z) {
      AdvancedModelBox.rotateAngleX = x;
      AdvancedModelBox.rotateAngleY = y;
      AdvancedModelBox.rotateAngleZ = z;
   }
}
