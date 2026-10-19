package com.bobmowzie.mowziesmobs.client.model.entity;

import com.bobmowzie.mowziesmobs.client.model.tools.dynamics.DynamicChain;
import com.bobmowzie.mowziesmobs.server.capability.DataHandler;
import com.bobmowzie.mowziesmobs.server.capability.FrozenData;
import com.bobmowzie.mowziesmobs.server.entity.naga.EntityNaga;
import com.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;

public class ModelNaga<T extends EntityNaga> extends MowzieEntityModel<T> {
   public AdvancedModelRenderer root;
   public AdvancedModelRenderer body;
   public AdvancedModelRenderer neck;
   public AdvancedModelRenderer tail1;
   public AdvancedModelRenderer spike1joint;
   public AdvancedModelRenderer spike2joint;
   public AdvancedModelRenderer spike3joint;
   public AdvancedModelRenderer shoulder1_L;
   public AdvancedModelRenderer shoulder1_R;
   public AdvancedModelRenderer backFin1;
   public AdvancedModelRenderer backFin1Reversed;
   public AdvancedModelRenderer headJoint;
   public AdvancedModelRenderer head;
   public AdvancedModelRenderer jaw;
   public AdvancedModelRenderer underHead;
   public AdvancedModelRenderer teethUpper;
   public AdvancedModelRenderer eyebrowJoint_R;
   public AdvancedModelRenderer eyebrowJoint_L;
   public AdvancedModelRenderer teethLower;
   public AdvancedModelRenderer eyebrow_R;
   public AdvancedModelRenderer eyebrow_L;
   public AdvancedModelRenderer tail2;
   public AdvancedModelRenderer spike4joint;
   public AdvancedModelRenderer backFin2;
   public AdvancedModelRenderer backFin2Reversed;
   public AdvancedModelRenderer tail3;
   public AdvancedModelRenderer spike5joint;
   public AdvancedModelRenderer backWing_L;
   public AdvancedModelRenderer backWing_R;
   public AdvancedModelRenderer backWing_LReversed;
   public AdvancedModelRenderer backWing_RReversed;
   public AdvancedModelRenderer backFin3;
   public AdvancedModelRenderer backFin3Reversed;
   public AdvancedModelRenderer tail4;
   public AdvancedModelRenderer tail5;
   public AdvancedModelRenderer tail6;
   public AdvancedModelRenderer tailFin;
   public AdvancedModelRenderer tailFinReversed;
   public AdvancedModelRenderer spike5;
   public AdvancedModelRenderer spike4;
   public AdvancedModelRenderer spike1;
   public AdvancedModelRenderer spike2;
   public AdvancedModelRenderer spike3;
   public AdvancedModelRenderer spike3Bottom;
   public AdvancedModelRenderer upperArmJoint_L;
   public AdvancedModelRenderer upperArm_L;
   public AdvancedModelRenderer lowerArmJoint_L;
   public AdvancedModelRenderer wingWebbing6_L;
   public AdvancedModelRenderer wingWebbing6_LReversed;
   public AdvancedModelRenderer lowerArm_L;
   public AdvancedModelRenderer handJoint_L;
   public AdvancedModelRenderer wingWebbing5_L;
   public AdvancedModelRenderer wingWebbing5_LReversed;
   public AdvancedModelRenderer hand_L;
   public AdvancedModelRenderer wingFrame1_L;
   public AdvancedModelRenderer wingFrame2_L;
   public AdvancedModelRenderer wingFrame3_L;
   public AdvancedModelRenderer wingFrame4_L;
   public AdvancedModelRenderer wingClaw_L;
   public AdvancedModelRenderer wingWebbing1_L;
   public AdvancedModelRenderer wingWebbing2_L;
   public AdvancedModelRenderer wingWebbing3_L;
   public AdvancedModelRenderer wingWebbing4_L;
   public AdvancedModelRenderer wingWebbing1_LReversed;
   public AdvancedModelRenderer wingWebbing2_LReversed;
   public AdvancedModelRenderer wingWebbing3_LReversed;
   public AdvancedModelRenderer wingWebbing4_LReversed;
   public AdvancedModelRenderer upperArmJoint_R;
   public AdvancedModelRenderer upperArm_R;
   public AdvancedModelRenderer lowerArmJoint_R;
   public AdvancedModelRenderer wingWebbing6_R;
   public AdvancedModelRenderer wingWebbing6_RReversed;
   public AdvancedModelRenderer lowerArm_R;
   public AdvancedModelRenderer handJoint_R;
   public AdvancedModelRenderer wingWebbing5_R;
   public AdvancedModelRenderer wingWebbing5_RReversed;
   public AdvancedModelRenderer hand_R;
   public AdvancedModelRenderer wingFrame1_R;
   public AdvancedModelRenderer wingFrame2_R;
   public AdvancedModelRenderer wingFrame3_R;
   public AdvancedModelRenderer wingFrame4_R;
   public AdvancedModelRenderer wingClaw_R;
   public AdvancedModelRenderer wingWebbing1_R;
   public AdvancedModelRenderer wingWebbing2_R;
   public AdvancedModelRenderer wingWebbing3_R;
   public AdvancedModelRenderer wingWebbing4_R;
   public AdvancedModelRenderer wingWebbing1_RReversed;
   public AdvancedModelRenderer wingWebbing2_RReversed;
   public AdvancedModelRenderer wingWebbing3_RReversed;
   public AdvancedModelRenderer wingWebbing4_RReversed;
   public AdvancedModelRenderer wingFolder;
   public AdvancedModelRenderer shoulderLJoint;
   public AdvancedModelRenderer shoulderRJoint;
   public AdvancedModelRenderer mouthSocket;
   public AdvancedModelRenderer scaler;
   public AdvancedModelRenderer swooper;
   public AdvancedModelRenderer tailEnd;
   public AdvancedModelRenderer[] tailOriginal;
   public AdvancedModelRenderer[] tailDynamic;
   public AdvancedModelRenderer[] reversePlanes;
   private DynamicChain tail;

   public ModelNaga() {
      super(RenderType::entityCutout);
      this.textureWidth = 256;
      this.textureHeight = 256;
      this.eyebrow_L = new AdvancedModelRenderer(this, 63, 0);
      this.eyebrow_L.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.eyebrow_L.addBox(-3.5F, 0.0F, 0.0F, 9.0F, 7.0F, 3.0F, 0.0F);
      setRotateAngle(this.eyebrow_L, 0.0F, 0.0F, 0.12217305F);
      this.spike4 = new AdvancedModelRenderer(this, 45, 52);
      this.spike4.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.spike4.addBox(0.0F, 0.0F, 0.0F, 11.0F, 6.0F, 11.0F, 0.0F);
      setRotateAngle(this.spike4, 0.0F, (float) (-Math.PI / 4), 0.0F);
      this.spike3joint = new AdvancedModelRenderer(this, 0, 0);
      this.spike3joint.setRotationPoint(0.0F, 1.0F, 4.0F);
      this.spike3joint.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      setRotateAngle(this.spike3joint, (float) (Math.PI / 6), 0.0F, 0.0F);
      this.headJoint = new AdvancedModelRenderer(this, 0, 0);
      this.headJoint.setRotationPoint(0.0F, 1.0F, -13.0F);
      this.headJoint.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      setRotateAngle(this.headJoint, 0.43633232F, 0.0F, 0.0F);
      this.body = new AdvancedModelRenderer(this, 0, 0);
      this.body.setRotationPoint(0.0F, -9.0F, -9.0F);
      this.body.addBox(-10.5F, -4.0F, -3.0F, 21.0F, 13.0F, 21.0F, 0.0F);
      this.spike2 = new AdvancedModelRenderer(this, 0, 54);
      this.spike2.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.spike2.addBox(0.0F, 0.0F, 0.0F, 15.0F, 4.0F, 15.0F, 0.0F);
      setRotateAngle(this.spike2, 0.0F, (float) (-Math.PI / 4), 0.0F);
      this.lowerArmJoint_R = new AdvancedModelRenderer(this, 0, 0);
      this.lowerArmJoint_R.setRotationPoint(-15.0F, 0.0F, -0.5F);
      this.lowerArmJoint_R.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      this.spike1 = new AdvancedModelRenderer(this, 0, 54);
      this.spike1.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.spike1.addBox(0.0F, 0.0F, 0.0F, 15.0F, 4.0F, 15.0F, 0.0F);
      setRotateAngle(this.spike1, 0.0F, (float) (-Math.PI / 4), 0.0F);
      this.wingWebbing5_L = new AdvancedModelRenderer(this, 193, 128);
      this.wingWebbing5_L.setRotationPoint(12.0F, 0.0F, 1.0F);
      this.wingWebbing5_L.addBox(-12.0F, 0.0F, 0.0F, 20.0F, 0.0F, 23.0F, 0.0F);
      this.wingWebbing5_LReversed = new AdvancedModelRenderer(this, 193, 128);
      this.wingWebbing5_LReversed.mirror = true;
      this.wingWebbing5_LReversed.setRotationPoint(0.0F, -0.004F, 0.0F);
      this.wingWebbing5_LReversed.addBox(-12.0F, 0.0F, 0.0F, 20.0F, 0.0F, 23.0F, 0.0F);
      this.tail5 = new AdvancedModelRenderer(this, 162, 30);
      this.tail5.setRotationPoint(0.0F, 0.0F, 14.0F);
      this.tail5.addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 13.0F, 0.0F);
      this.tail3 = new AdvancedModelRenderer(this, 0, 34);
      this.tail3.setRotationPoint(0.0F, 0.0F, 15.0F);
      this.tail3.addBox(-5.5F, -2.5F, 0.0F, 11.0F, 4.0F, 16.0F, 0.0F);
      this.wingWebbing2_L = new AdvancedModelRenderer(this, 20, 98);
      this.wingWebbing2_L.setRotationPoint(-1.5F, 0.0F, 0.0F);
      this.wingWebbing2_L.addBox(0.0F, 0.0F, -15.0F, 50.0F, 0.0F, 30.0F, 0.0F);
      setRotateAngle(this.wingWebbing2_L, 0.0F, -0.30543262F, 0.0F);
      this.wingWebbing2_LReversed = new AdvancedModelRenderer(this, 20, 98);
      this.wingWebbing2_LReversed.mirror = true;
      this.wingWebbing2_LReversed.setRotationPoint(0.0F, -0.004F, 0.0F);
      this.wingWebbing2_LReversed.addBox(0.0F, 0.0F, -15.0F, 50.0F, 0.0F, 30.0F, 0.0F);
      this.root = new AdvancedModelRenderer(this, 0, 0);
      this.root.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.root.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      this.wingFrame1_L = new AdvancedModelRenderer(this, 50, 91);
      this.wingFrame1_L.setRotationPoint(11.0F, 0.0F, -0.5F);
      this.wingFrame1_L.addBox(0.0F, -1.5F, -1.5F, 53.0F, 3.0F, 3.0F, 0.0F);
      this.hand_R = new AdvancedModelRenderer(this, 222, 0);
      this.hand_R.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.hand_R.addBox(-11.0F, -2.0F, -2.0F, 11.0F, 4.0F, 4.0F, 0.0F);
      this.lowerArm_R = new AdvancedModelRenderer(this, 102, 83);
      this.lowerArm_R.mirror = true;
      this.lowerArm_R.setRotationPoint(0.0F, 0.0F, -0.5F);
      this.lowerArm_R.addBox(-22.0F, -2.0F, -2.0F, 22.0F, 4.0F, 4.0F, 0.0F);
      this.head = new AdvancedModelRenderer(this, 68, 23);
      this.head.setRotationPoint(0.0F, -2.0F, -1.0F);
      this.head.addBox(-8.0F, -3.7F, -8.0F, 16.0F, 6.0F, 16.0F, 0.0F);
      setRotateAngle(this.head, 0.0F, (float) (Math.PI / 4), 0.0F);
      this.eyebrowJoint_L = new AdvancedModelRenderer(this, 0, 0);
      this.eyebrowJoint_L.setRotationPoint(4.95F, -5.0F, -7.36F);
      this.eyebrowJoint_L.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      setRotateAngle(this.eyebrowJoint_L, 1.0344566F, -0.31799898F, -0.5363397F);
      this.upperArm_L = new AdvancedModelRenderer(this, 106, 74);
      this.upperArm_L.setRotationPoint(0.0F, 2.0F, 0.0F);
      this.upperArm_L.addBox(-2.0F, -2.0F, -3.0F, 18.0F, 4.0F, 5.0F, 0.0F);
      this.wingFrame2_L = new AdvancedModelRenderer(this, 0, 79);
      this.wingFrame2_L.setRotationPoint(7.0F, 0.0F, 0.0F);
      this.wingFrame2_L.addBox(0.0F, -1.5F, -1.5F, 50.0F, 3.0F, 3.0F, 0.0F);
      setRotateAngle(this.wingFrame2_L, 0.0F, -0.61086524F, 0.0F);
      this.eyebrow_R = new AdvancedModelRenderer(this, 63, 0);
      this.eyebrow_R.mirror = true;
      this.eyebrow_R.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.eyebrow_R.addBox(-5.5F, 0.0F, 0.0F, 9.0F, 7.0F, 3.0F, 0.0F);
      setRotateAngle(this.eyebrow_R, 0.0F, 0.0F, -0.12217305F);
      this.wingWebbing3_R = new AdvancedModelRenderer(this, 144, 71);
      this.wingWebbing3_R.mirror = true;
      this.wingWebbing3_R.setRotationPoint(-1.0F, 0.0F, 0.0F);
      this.wingWebbing3_R.addBox(-43.0F, 0.0F, -13.0F, 43.0F, 0.0F, 26.0F, 0.0F);
      setRotateAngle(this.wingWebbing3_R, 0.0F, 0.30543262F, 0.0F);
      this.wingWebbing3_RReversed = new AdvancedModelRenderer(this, 144, 71);
      this.wingWebbing3_RReversed.mirror = false;
      this.wingWebbing3_RReversed.setRotationPoint(0.0F, -0.004F, 0.0F);
      this.wingWebbing3_RReversed.addBox(-43.0F, 0.0F, -13.0F, 43.0F, 0.0F, 26.0F, 0.0F);
      this.wingWebbing3_L = new AdvancedModelRenderer(this, 144, 71);
      this.wingWebbing3_L.setRotationPoint(1.0F, 0.0F, 0.0F);
      this.wingWebbing3_L.addBox(0.0F, 0.0F, -13.0F, 43.0F, 0.0F, 26.0F, 0.0F);
      setRotateAngle(this.wingWebbing3_L, 0.0F, -0.30543262F, 0.0F);
      this.wingWebbing3_LReversed = new AdvancedModelRenderer(this, 144, 71);
      this.wingWebbing3_LReversed.mirror = true;
      this.wingWebbing3_LReversed.setRotationPoint(0.0F, -0.004F, 0.0F);
      this.wingWebbing3_LReversed.addBox(0.0F, 0.0F, -13.0F, 43.0F, 0.0F, 26.0F, 0.0F);
      this.handJoint_R = new AdvancedModelRenderer(this, 0, 0);
      this.handJoint_R.setRotationPoint(-20.0F, 0.0F, 0.0F);
      this.handJoint_R.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      this.spike5 = new AdvancedModelRenderer(this, 38, 36);
      this.spike5.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.spike5.addBox(0.0F, 0.0F, 0.0F, 9.0F, 4.0F, 9.0F, 0.0F);
      setRotateAngle(this.spike5, 0.0F, (float) (-Math.PI / 4), 0.0F);
      this.shoulder1_L = new AdvancedModelRenderer(this, 189, 0);
      this.shoulder1_L.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shoulder1_L.addBox(-2.0F, -3.0F, -7.0F, 12.0F, 6.0F, 9.0F, 0.0F);
      setRotateAngle(this.shoulder1_L, 0.0F, (float) (-Math.PI / 6), 0.0F);
      this.spike5joint = new AdvancedModelRenderer(this, 0, 0);
      this.spike5joint.setRotationPoint(0.0F, 0.0F, -4.05F);
      this.spike5joint.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      setRotateAngle(this.spike5joint, (float) (Math.PI / 6), 0.0F, 0.0F);
      this.wingWebbing1_R = new AdvancedModelRenderer(this, 119, 97);
      this.wingWebbing1_R.mirror = true;
      this.wingWebbing1_R.setRotationPoint(2.0F, 0.0F, 0.0F);
      this.wingWebbing1_R.addBox(-53.0F, 0.0F, -16.0F, 53.0F, 0.0F, 31.0F, 0.0F);
      setRotateAngle(this.wingWebbing1_R, 0.0F, 0.30543262F, 0.0F);
      this.wingWebbing1_RReversed = new AdvancedModelRenderer(this, 119, 97);
      this.wingWebbing1_RReversed.mirror = false;
      this.wingWebbing1_RReversed.setRotationPoint(0.0F, -0.004F, 0.0F);
      this.wingWebbing1_RReversed.addBox(-53.0F, 0.0F, -16.0F, 53.0F, 0.0F, 31.0F, 0.0F);
      this.upperArmJoint_L = new AdvancedModelRenderer(this, 0, 0);
      this.upperArmJoint_L.setRotationPoint(9.0F, -1.0F, -1.0F);
      this.upperArmJoint_L.addBox(-2.0F, 0.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      setRotateAngle(this.upperArmJoint_L, 0.0F, (float) (Math.PI / 6), 0.0F);
      this.tail1 = new AdvancedModelRenderer(this, 140, 0);
      this.tail1.setRotationPoint(0.0F, 1.5F, 17.0F);
      this.tail1.addBox(-7.5F, -4.5F, -3.0F, 15.0F, 9.0F, 19.0F, 0.0F);
      this.wingWebbing5_R = new AdvancedModelRenderer(this, 193, 128);
      this.wingWebbing5_R.mirror = true;
      this.wingWebbing5_R.setRotationPoint(-12.0F, 0.0F, 1.0F);
      this.wingWebbing5_R.addBox(-8.0F, 0.0F, 0.0F, 20.0F, 0.0F, 23.0F, 0.0F);
      this.wingWebbing5_RReversed = new AdvancedModelRenderer(this, 193, 128);
      this.wingWebbing5_RReversed.mirror = false;
      this.wingWebbing5_RReversed.setRotationPoint(0.0F, -0.004F, 0.0F);
      this.wingWebbing5_RReversed.addBox(-8.0F, 0.0F, 0.0F, 20.0F, 0.0F, 23.0F, 0.0F);
      this.backFin3 = new AdvancedModelRenderer(this, 170, 123);
      this.backFin3.setRotationPoint(0.0F, -3.0F, 0.0F);
      this.backFin3.addBox(0.0F, -9.0F, -3.0F, 0.0F, 9.0F, 19.0F, 0.0F);
      this.backFin3Reversed = new AdvancedModelRenderer(this, 170, 123);
      this.backFin3Reversed.mirror = true;
      this.backFin3Reversed.setRotationPoint(0.001F, 0.0F, 0.0F);
      this.backFin3Reversed.addBox(0.0F, -9.0F, -3.0F, 0.0F, 9.0F, 19.0F, 0.0F);
      this.handJoint_L = new AdvancedModelRenderer(this, 0, 0);
      this.handJoint_L.setRotationPoint(20.0F, 0.0F, 0.0F);
      this.handJoint_L.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      this.wingWebbing4_L = new AdvancedModelRenderer(this, 159, 36);
      this.wingWebbing4_L.setRotationPoint(0.5F, 0.0F, 0.0F);
      this.wingWebbing4_L.addBox(0.0F, 0.0F, -22.0F, 31.0F, 0.0F, 35.0F, 0.0F);
      setRotateAngle(this.wingWebbing4_L, 0.0F, -0.65449846F, 0.0F);
      this.wingWebbing4_LReversed = new AdvancedModelRenderer(this, 159, 36);
      this.wingWebbing4_LReversed.mirror = true;
      this.wingWebbing4_LReversed.setRotationPoint(0.0F, -0.004F, 0.0F);
      this.wingWebbing4_LReversed.addBox(0.0F, 0.0F, -22.0F, 31.0F, 0.0F, 35.0F, 0.0F);
      this.wingFrame3_L = new AdvancedModelRenderer(this, 0, 73);
      this.wingFrame3_L.setRotationPoint(4.0F, 0.0F, 0.0F);
      this.wingFrame3_L.addBox(0.0F, -1.5F, -1.5F, 43.0F, 3.0F, 3.0F, 0.0F);
      setRotateAngle(this.wingFrame3_L, 0.0F, -1.2217305F, 0.0F);
      this.wingWebbing1_L = new AdvancedModelRenderer(this, 119, 97);
      this.wingWebbing1_L.setRotationPoint(-2.0F, 0.0F, 0.0F);
      this.wingWebbing1_L.addBox(0.0F, 0.0F, -16.0F, 53.0F, 0.0F, 31.0F, 0.0F);
      setRotateAngle(this.wingWebbing1_L, 0.0F, -0.30543262F, 0.0F);
      this.wingWebbing1_LReversed = new AdvancedModelRenderer(this, 119, 97);
      this.wingWebbing1_LReversed.mirror = true;
      this.wingWebbing1_LReversed.setRotationPoint(0.0F, -0.004F, 0.0F);
      this.wingWebbing1_LReversed.addBox(0.0F, 0.0F, -16.0F, 53.0F, 0.0F, 31.0F, 0.0F);
      this.wingClaw_R = new AdvancedModelRenderer(this, 231, 8);
      this.wingClaw_R.mirror = true;
      this.wingClaw_R.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.wingClaw_R.addBox(-9.0F, -2.0F, -2.0F, 9.0F, 3.0F, 3.0F, 0.0F);
      setRotateAngle(this.wingClaw_R, 0.0F, (float) (-Math.PI / 6), 0.0F);
      this.eyebrowJoint_R = new AdvancedModelRenderer(this, 0, 0);
      this.eyebrowJoint_R.setRotationPoint(-4.95F, -5.0F, -7.36F);
      this.eyebrowJoint_R.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      setRotateAngle(this.eyebrowJoint_R, 1.0344566F, 0.31799898F, 0.5363397F);
      this.wingFrame2_R = new AdvancedModelRenderer(this, 0, 79);
      this.wingFrame2_R.mirror = true;
      this.wingFrame2_R.setRotationPoint(-7.0F, 0.0F, 0.0F);
      this.wingFrame2_R.addBox(-50.0F, -1.5F, -1.5F, 50.0F, 3.0F, 3.0F, 0.0F);
      setRotateAngle(this.wingFrame2_R, 0.0F, 0.61086524F, 0.0F);
      this.wingWebbing2_R = new AdvancedModelRenderer(this, 20, 98);
      this.wingWebbing2_R.mirror = true;
      this.wingWebbing2_R.setRotationPoint(1.5F, 0.0F, 0.0F);
      this.wingWebbing2_R.addBox(-50.0F, 0.0F, -15.0F, 50.0F, 0.0F, 30.0F, 0.0F);
      setRotateAngle(this.wingWebbing2_R, 0.0F, 0.30543262F, 0.0F);
      this.wingWebbing2_RReversed = new AdvancedModelRenderer(this, 20, 98);
      this.wingWebbing2_RReversed.mirror = false;
      this.wingWebbing2_RReversed.setRotationPoint(0.0F, -0.004F, 0.0F);
      this.wingWebbing2_RReversed.addBox(-50.0F, 0.0F, -15.0F, 50.0F, 0.0F, 30.0F, 0.0F);
      this.jaw = new AdvancedModelRenderer(this, 116, 62);
      this.jaw.setRotationPoint(0.0F, -1.34F, -4.38F);
      this.jaw.addBox(-4.5F, 0.16F, -9.37F, 9.0F, 4.0F, 8.0F, 0.0F);
      setRotateAngle(this.jaw, (float) (-Math.PI / 18), 0.0F, -0.0052359877F);
      this.neck = new AdvancedModelRenderer(this, 84, 0);
      this.neck.setRotationPoint(0.0F, 1.0F, -1.0F);
      this.neck.addBox(-6.5F, -4.0F, -12.2F, 13.0F, 8.0F, 15.0F, 0.0F);
      setRotateAngle(this.neck, (float) (Math.PI / 18), 0.0F, 0.0F);
      this.spike3Bottom = new AdvancedModelRenderer(this, 78, 45);
      this.spike3Bottom.setRotationPoint(0.0F, 2.69F, 5.66F);
      this.spike3Bottom.addBox(0.0F, 0.0F, 0.0F, 11.0F, 4.0F, 11.0F, 0.0F);
      setRotateAngle(this.spike3Bottom, 0.0F, (float) (-Math.PI / 4), 0.0F);
      this.teethLower = new AdvancedModelRenderer(this, 125, 0);
      this.teethLower.setRotationPoint(0.0F, 0.14F, -9.4F);
      this.teethLower.addBox(-4.5F, -6.0F, 0.0F, 9.0F, 6.0F, 7.0F, 0.0F);
      setRotateAngle(this.teethLower, -0.5009095F, 0.0F, 0.0F);
      this.lowerArm_L = new AdvancedModelRenderer(this, 102, 83);
      this.lowerArm_L.setRotationPoint(0.0F, 0.0F, -0.5F);
      this.lowerArm_L.addBox(0.0F, -2.0F, -2.0F, 22.0F, 4.0F, 4.0F, 0.0F);
      this.shoulder1_R = new AdvancedModelRenderer(this, 189, 0);
      this.shoulder1_R.mirror = true;
      this.shoulder1_R.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.shoulder1_R.addBox(-10.0F, -3.0F, -7.0F, 12.0F, 6.0F, 9.0F, 0.0F);
      setRotateAngle(this.shoulder1_R, 0.0F, (float) (Math.PI / 6), 0.0F);
      this.wingFrame1_R = new AdvancedModelRenderer(this, 50, 91);
      this.wingFrame1_R.mirror = true;
      this.wingFrame1_R.setRotationPoint(-11.0F, 0.0F, -0.5F);
      this.wingFrame1_R.addBox(-53.0F, -1.5F, -1.5F, 53.0F, 3.0F, 3.0F, 0.0F);
      this.teethUpper = new AdvancedModelRenderer(this, 171, 56);
      this.teethUpper.setRotationPoint(0.0F, 0.3F, -6.65F);
      this.teethUpper.addBox(-4.0F, -4.0F, -3.0F, 8.0F, 8.0F, 3.0F, 0.0F);
      setRotateAngle(this.teethUpper, (float) (Math.PI / 2), (float) (Math.PI / 4), 0.0F);
      this.backWing_R = new AdvancedModelRenderer(this, 35, 128);
      this.backWing_R.mirror = true;
      this.backWing_R.setRotationPoint(-4.0F, 0.0F, 4.0F);
      this.backWing_R.addBox(-30.0F, 0.0F, 0.0F, 30.0F, 0.0F, 25.0F, 0.0F);
      setRotateAngle(this.backWing_R, 0.0F, (float) (Math.PI / 6), 0.0F);
      this.backWing_RReversed = new AdvancedModelRenderer(this, 35, 128);
      this.backWing_RReversed.mirror = false;
      this.backWing_RReversed.setRotationPoint(0.0F, -0.004F, 0.0F);
      this.backWing_RReversed.addBox(-30.0F, 0.0F, 0.0F, 30.0F, 0.0F, 25.0F, 0.0F);
      this.tailFin = new AdvancedModelRenderer(this, -30, 128);
      this.tailFin.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.tailFin.addBox(-5.0F, 0.0F, -5.0F, 30.0F, 0.0F, 30.0F, 0.0F);
      setRotateAngle(this.tailFin, 0.0F, (float) (-Math.PI / 4), 0.0F);
      this.tailFinReversed = new AdvancedModelRenderer(this, -30, 128);
      this.tailFinReversed.mirror = true;
      this.tailFinReversed.setRotationPoint(0.0F, -0.004F, 0.0F);
      this.tailFinReversed.addBox(-5.0F, 0.0F, -5.0F, 30.0F, 0.0F, 30.0F, 0.0F);
      this.lowerArmJoint_L = new AdvancedModelRenderer(this, 0, 0);
      this.lowerArmJoint_L.setRotationPoint(15.0F, 0.0F, -0.5F);
      this.lowerArmJoint_L.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      this.wingFrame4_L = new AdvancedModelRenderer(this, 0, 85);
      this.wingFrame4_L.setRotationPoint(2.0F, 0.0F, 0.0F);
      this.wingFrame4_L.addBox(0.0F, -1.5F, -1.5F, 38.0F, 3.0F, 3.0F, 0.0F);
      setRotateAngle(this.wingFrame4_L, 0.0F, (float) (-Math.PI * 7.0 / 12.0), 0.0F);
      this.underHead = new AdvancedModelRenderer(this, 122, 51);
      this.underHead.setRotationPoint(0.0F, -1.82F, -1.59F);
      this.underHead.addBox(-4.5F, 1.12F, -4.03F, 9.0F, 4.0F, 7.0F, 0.0F);
      setRotateAngle(this.underHead, (float) (-Math.PI / 18), 0.0F, 0.0F);
      this.backFin2 = new AdvancedModelRenderer(this, 170, 109);
      this.backFin2.setRotationPoint(0.0F, -4.5F, 0.0F);
      this.backFin2.addBox(0.0F, -14.0F, -3.0F, 0.0F, 14.0F, 19.0F, 0.0F);
      this.backFin2Reversed = new AdvancedModelRenderer(this, 170, 109);
      this.backFin2Reversed.mirror = true;
      this.backFin2Reversed.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.backFin2Reversed.addBox(0.0F, -14.0F, -3.0F, 0.0F, 14.0F, 19.0F, 0.0F);
      this.tail2 = new AdvancedModelRenderer(this, 115, 28);
      this.tail2.setRotationPoint(0.0F, -1.0F, 15.0F);
      this.tail2.addBox(-6.5F, -3.0F, -1.0F, 13.0F, 6.0F, 17.0F, 0.0F);
      this.hand_L = new AdvancedModelRenderer(this, 222, 0);
      this.hand_L.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.hand_L.addBox(0.0F, -2.0F, -2.0F, 11.0F, 4.0F, 4.0F, 0.0F);
      this.spike4joint = new AdvancedModelRenderer(this, 0, 0);
      this.spike4joint.setRotationPoint(0.0F, -1.0F, 0.55F);
      this.spike4joint.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      setRotateAngle(this.spike4joint, (float) (Math.PI / 6), 0.0F, 0.0F);
      this.upperArmJoint_R = new AdvancedModelRenderer(this, 0, 0);
      this.upperArmJoint_R.setRotationPoint(-9.0F, -1.0F, -1.0F);
      this.upperArmJoint_R.addBox(-2.0F, 0.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      setRotateAngle(this.upperArmJoint_R, 0.0F, (float) (-Math.PI / 6), 0.0F);
      this.spike1joint = new AdvancedModelRenderer(this, 0, 0);
      this.spike1joint.setRotationPoint(0.0F, 1.0F, -12.0F);
      this.spike1joint.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      setRotateAngle(this.spike1joint, (float) (Math.PI / 6), 0.0F, 0.0F);
      this.backFin1 = new AdvancedModelRenderer(this, 120, 103);
      this.backFin1.setRotationPoint(0.0F, -4.0F, -3.0F);
      this.backFin1.addBox(0.0F, -15.0F, -4.0F, 0.0F, 15.0F, 25.0F, 0.0F);
      this.backFin1Reversed = new AdvancedModelRenderer(this, 120, 127);
      this.backFin1Reversed.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.backFin1Reversed.addBox(0.0F, -15.0F, -4.0F, 0.0F, 15.0F, 25.0F, 0.0F);
      this.backFin1Reversed.mirror = true;
      this.wingWebbing4_R = new AdvancedModelRenderer(this, 159, 36);
      this.wingWebbing4_R.mirror = true;
      this.wingWebbing4_R.setRotationPoint(-0.5F, 0.0F, 0.0F);
      this.wingWebbing4_R.addBox(-31.0F, 0.0F, -22.0F, 31.0F, 0.0F, 35.0F, 0.0F);
      setRotateAngle(this.wingWebbing4_R, 0.0F, 0.65449846F, 0.0F);
      this.wingWebbing4_RReversed = new AdvancedModelRenderer(this, 159, 36);
      this.wingWebbing4_RReversed.mirror = false;
      this.wingWebbing4_RReversed.setRotationPoint(0.0F, -0.004F, 0.0F);
      this.wingWebbing4_RReversed.addBox(-31.0F, 0.0F, -22.0F, 31.0F, 0.0F, 35.0F, 0.0F);
      this.upperArm_R = new AdvancedModelRenderer(this, 106, 74);
      this.upperArm_R.mirror = true;
      this.upperArm_R.setRotationPoint(0.0F, 2.0F, 0.0F);
      this.upperArm_R.addBox(-16.0F, -2.0F, -3.0F, 18.0F, 4.0F, 5.0F, 0.0F);
      this.backWing_L = new AdvancedModelRenderer(this, 35, 128);
      this.backWing_L.setRotationPoint(4.0F, 0.0F, 4.0F);
      this.backWing_L.addBox(0.0F, 0.0F, 0.0F, 30.0F, 0.0F, 25.0F, 0.0F);
      setRotateAngle(this.backWing_L, 0.0F, (float) (-Math.PI / 6), 0.0F);
      this.backWing_LReversed = new AdvancedModelRenderer(this, 35, 128);
      this.backWing_LReversed.mirror = true;
      this.backWing_LReversed.setRotationPoint(0.0F, -0.004F, 0.0F);
      this.backWing_LReversed.addBox(0.0F, 0.0F, 0.0F, 30.0F, 0.0F, 25.0F, 0.0F);
      this.spike3 = new AdvancedModelRenderer(this, 0, 54);
      this.spike3.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.spike3.addBox(0.0F, 0.0F, 0.0F, 15.0F, 4.0F, 15.0F, 0.0F);
      setRotateAngle(this.spike3, 0.0F, (float) (-Math.PI / 4), 0.0F);
      this.wingWebbing6_R = new AdvancedModelRenderer(this, -24, 94);
      this.wingWebbing6_R.mirror = true;
      this.wingWebbing6_R.setRotationPoint(-0.5F, 0.0F, -2.0F);
      this.wingWebbing6_R.addBox(-14.5F, 0.0F, 0.0F, 25.0F, 0.0F, 24.0F, 0.0F);
      this.wingWebbing6_RReversed = new AdvancedModelRenderer(this, -24, 94);
      this.wingWebbing6_RReversed.mirror = false;
      this.wingWebbing6_RReversed.setRotationPoint(0.0F, -0.004F, 0.0F);
      this.wingWebbing6_RReversed.addBox(-14.5F, 0.0F, 0.0F, 25.0F, 0.0F, 24.0F, 0.0F);
      this.spike2joint = new AdvancedModelRenderer(this, 0, 0);
      this.spike2joint.setRotationPoint(0.0F, 1.0F, -4.0F);
      this.spike2joint.addBox(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      setRotateAngle(this.spike2joint, (float) (Math.PI / 6), 0.0F, 0.0F);
      this.wingWebbing6_L = new AdvancedModelRenderer(this, -24, 94);
      this.wingWebbing6_L.setRotationPoint(0.5F, 0.0F, -2.0F);
      this.wingWebbing6_L.addBox(-10.5F, 0.0F, 0.0F, 25.0F, 0.0F, 24.0F, 0.0F);
      this.wingWebbing6_LReversed = new AdvancedModelRenderer(this, -24, 94);
      this.wingWebbing6_LReversed.mirror = true;
      this.wingWebbing6_LReversed.setRotationPoint(0.0F, -0.004F, 0.0F);
      this.wingWebbing6_LReversed.addBox(-10.5F, 0.0F, 0.0F, 25.0F, 0.0F, 24.0F, 0.0F);
      this.tail6 = new AdvancedModelRenderer(this, 0, 0);
      this.tail6.setRotationPoint(0.0F, 0.0F, 13.0F);
      this.tail6.addBox(-1.5F, -1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      this.wingClaw_L = new AdvancedModelRenderer(this, 231, 8);
      this.wingClaw_L.setRotationPoint(0.0F, 0.0F, 0.0F);
      this.wingClaw_L.addBox(0.0F, -2.0F, -2.0F, 9.0F, 3.0F, 3.0F, 0.0F);
      setRotateAngle(this.wingClaw_L, 0.0F, (float) (Math.PI / 6), 0.0F);
      this.wingFrame4_R = new AdvancedModelRenderer(this, 0, 85);
      this.wingFrame4_R.mirror = true;
      this.wingFrame4_R.setRotationPoint(-2.0F, 0.0F, 0.0F);
      this.wingFrame4_R.addBox(-38.0F, -1.5F, -1.5F, 38.0F, 3.0F, 3.0F, 0.0F);
      setRotateAngle(this.wingFrame4_R, 0.0F, (float) (Math.PI * 7.0 / 12.0), 0.0F);
      this.tail4 = new AdvancedModelRenderer(this, 142, 52);
      this.tail4.setRotationPoint(0.0F, -0.5F, 15.0F);
      this.tail4.addBox(-3.5F, -1.5F, 0.0F, 7.0F, 3.0F, 15.0F, 0.0F);
      this.wingFrame3_R = new AdvancedModelRenderer(this, 0, 73);
      this.wingFrame3_R.mirror = true;
      this.wingFrame3_R.setRotationPoint(-4.0F, 0.0F, 0.0F);
      this.wingFrame3_R.addBox(-43.0F, -1.5F, -1.5F, 43.0F, 3.0F, 3.0F, 0.0F);
      setRotateAngle(this.wingFrame3_R, 0.0F, 1.2217305F, 0.0F);
      this.shoulderLJoint = new AdvancedModelRenderer(this, 0, 0);
      this.shoulderLJoint.setRotationPoint(8.0F, -2.0F, -1.0F);
      this.shoulderRJoint = new AdvancedModelRenderer(this, 0, 0);
      this.shoulder1_R.setRotationPoint(-8.0F, -2.0F, -1.0F);
      this.mouthSocket = new AdvancedModelRenderer(this, 0, 0);
      this.mouthSocket.setRotationPoint(0.0F, 5.0F, -10.0F);
      this.wingFolder = new AdvancedModelRenderer(this, 0, 0);
      this.tailEnd = new AdvancedModelRenderer(this, 0, 0);
      this.tailEnd.setRotationPoint(0.0F, 0.0F, 5.0F);
      this.scaler = new AdvancedModelRenderer(this, 0, 0);
      this.swooper = new AdvancedModelRenderer(this, 0, 0);
      this.eyebrowJoint_L.addChild(this.eyebrow_L);
      this.spike4joint.addChild(this.spike4);
      this.body.addChild(this.spike3joint);
      this.neck.addChild(this.headJoint);
      this.root.addChild(this.body);
      this.spike2joint.addChild(this.spike2);
      this.upperArm_R.addChild(this.lowerArmJoint_R);
      this.spike1joint.addChild(this.spike1);
      this.lowerArm_L.addChild(this.wingWebbing5_L);
      this.tail4.addChild(this.tail5);
      this.tail2.addChild(this.tail3);
      this.wingFrame2_L.addChild(this.wingWebbing2_L);
      this.hand_L.addChild(this.wingFrame1_L);
      this.handJoint_R.addChild(this.hand_R);
      this.lowerArmJoint_R.addChild(this.lowerArm_R);
      this.headJoint.addChild(this.head);
      this.headJoint.addChild(this.eyebrowJoint_L);
      this.upperArmJoint_L.addChild(this.upperArm_L);
      this.hand_L.addChild(this.wingFrame2_L);
      this.eyebrowJoint_R.addChild(this.eyebrow_R);
      this.wingFrame3_R.addChild(this.wingWebbing3_R);
      this.wingFrame3_L.addChild(this.wingWebbing3_L);
      this.lowerArm_R.addChild(this.handJoint_R);
      this.spike5joint.addChild(this.spike5);
      this.body.addChild(this.shoulderLJoint);
      this.tail2.addChild(this.spike5joint);
      this.wingFrame1_R.addChild(this.wingWebbing1_R);
      this.shoulder1_L.addChild(this.upperArmJoint_L);
      this.body.addChild(this.tail1);
      this.lowerArm_R.addChild(this.wingWebbing5_R);
      this.tail2.addChild(this.backFin3);
      this.lowerArm_L.addChild(this.handJoint_L);
      this.wingFrame4_L.addChild(this.wingWebbing4_L);
      this.hand_L.addChild(this.wingFrame3_L);
      this.wingFrame1_L.addChild(this.wingWebbing1_L);
      this.hand_R.addChild(this.wingClaw_R);
      this.headJoint.addChild(this.eyebrowJoint_R);
      this.hand_R.addChild(this.wingFrame2_R);
      this.wingFrame2_R.addChild(this.wingWebbing2_R);
      this.headJoint.addChild(this.jaw);
      this.body.addChild(this.neck);
      this.spike3joint.addChild(this.spike3Bottom);
      this.jaw.addChild(this.teethLower);
      this.lowerArmJoint_L.addChild(this.lowerArm_L);
      this.body.addChild(this.shoulderRJoint);
      this.hand_R.addChild(this.wingFrame1_R);
      this.headJoint.addChild(this.teethUpper);
      this.tail2.addChild(this.backWing_R);
      this.tail6.addChild(this.tailFin);
      this.upperArm_L.addChild(this.lowerArmJoint_L);
      this.hand_L.addChild(this.wingFrame4_L);
      this.headJoint.addChild(this.underHead);
      this.tail1.addChild(this.backFin2);
      this.tail1.addChild(this.tail2);
      this.handJoint_L.addChild(this.hand_L);
      this.tail1.addChild(this.spike4joint);
      this.shoulder1_R.addChild(this.upperArmJoint_R);
      this.body.addChild(this.spike1joint);
      this.body.addChild(this.backFin1);
      this.wingFrame4_R.addChild(this.wingWebbing4_R);
      this.upperArmJoint_R.addChild(this.upperArm_R);
      this.tail2.addChild(this.backWing_L);
      this.spike3joint.addChild(this.spike3);
      this.upperArm_R.addChild(this.wingWebbing6_R);
      this.body.addChild(this.spike2joint);
      this.upperArm_L.addChild(this.wingWebbing6_L);
      this.tail5.addChild(this.tail6);
      this.hand_L.addChild(this.wingClaw_L);
      this.hand_R.addChild(this.wingFrame4_R);
      this.tail3.addChild(this.tail4);
      this.hand_R.addChild(this.wingFrame3_R);
      this.tail6.addChild(this.tailEnd);
      this.shoulderRJoint.addChild(this.shoulder1_R);
      this.shoulderLJoint.addChild(this.shoulder1_L);
      this.headJoint.addChild(this.mouthSocket);
      this.backFin3.addChild(this.backFin3Reversed);
      this.backFin2.addChild(this.backFin2Reversed);
      this.backFin1.addChild(this.backFin1Reversed);
      this.backWing_L.addChild(this.backWing_LReversed);
      this.backWing_R.addChild(this.backWing_RReversed);
      this.wingWebbing1_L.addChild(this.wingWebbing1_LReversed);
      this.wingWebbing2_L.addChild(this.wingWebbing2_LReversed);
      this.wingWebbing3_L.addChild(this.wingWebbing3_LReversed);
      this.wingWebbing4_L.addChild(this.wingWebbing4_LReversed);
      this.wingWebbing5_L.addChild(this.wingWebbing5_LReversed);
      this.wingWebbing6_L.addChild(this.wingWebbing6_LReversed);
      this.wingWebbing1_R.addChild(this.wingWebbing1_RReversed);
      this.wingWebbing2_R.addChild(this.wingWebbing2_RReversed);
      this.wingWebbing3_R.addChild(this.wingWebbing3_RReversed);
      this.wingWebbing4_R.addChild(this.wingWebbing4_RReversed);
      this.wingWebbing6_R.addChild(this.wingWebbing6_RReversed);
      this.wingWebbing5_R.addChild(this.wingWebbing5_RReversed);
      this.tailFin.addChild(this.tailFinReversed);
      this.updateDefaultPose();
      this.updateDefaultPoseExtendeds();
      this.tailOriginal = new AdvancedModelRenderer[]{this.tail1, this.tail2, this.tail3, this.tail4, this.tail5, this.tailEnd};
      this.tailDynamic = new AdvancedModelRenderer[this.tailOriginal.length];
      this.reversePlanes = new AdvancedModelRenderer[]{
         this.backWing_LReversed,
         this.backWing_RReversed,
         this.tailFinReversed,
         this.wingWebbing6_LReversed,
         this.wingWebbing5_LReversed,
         this.wingWebbing1_LReversed,
         this.wingWebbing2_LReversed,
         this.wingWebbing3_LReversed,
         this.wingWebbing4_LReversed,
         this.wingWebbing6_RReversed,
         this.wingWebbing5_RReversed,
         this.wingWebbing1_RReversed,
         this.wingWebbing2_RReversed,
         this.wingWebbing3_RReversed,
         this.wingWebbing4_RReversed
      };
      this.backFin1.setDoubleSided(false);
      this.backFin1Reversed.setDoubleSided(false);
      this.backFin2.setDoubleSided(false);
      this.backFin2Reversed.setDoubleSided(false);
      this.backFin3.setDoubleSided(false);
      this.backFin3Reversed.setDoubleSided(false);
      this.backWing_L.setDoubleSided(false);
      this.backWing_R.setDoubleSided(false);
      this.backWing_LReversed.setDoubleSided(false);
      this.backWing_RReversed.setDoubleSided(false);
      this.wingWebbing1_L.setDoubleSided(false);
      this.wingWebbing1_LReversed.setDoubleSided(false);
      this.wingWebbing1_R.setDoubleSided(false);
      this.wingWebbing1_RReversed.setDoubleSided(false);
      this.wingWebbing2_L.setDoubleSided(false);
      this.wingWebbing2_LReversed.setDoubleSided(false);
      this.wingWebbing2_R.setDoubleSided(false);
      this.wingWebbing2_RReversed.setDoubleSided(false);
      this.wingWebbing3_L.setDoubleSided(false);
      this.wingWebbing3_LReversed.setDoubleSided(false);
      this.wingWebbing3_R.setDoubleSided(false);
      this.wingWebbing3_RReversed.setDoubleSided(false);
      this.wingWebbing4_L.setDoubleSided(false);
      this.wingWebbing4_LReversed.setDoubleSided(false);
      this.wingWebbing4_R.setDoubleSided(false);
      this.wingWebbing4_RReversed.setDoubleSided(false);
      this.wingWebbing5_L.setDoubleSided(false);
      this.wingWebbing5_LReversed.setDoubleSided(false);
      this.wingWebbing5_R.setDoubleSided(false);
      this.wingWebbing5_RReversed.setDoubleSided(false);
      this.wingWebbing6_L.setDoubleSided(false);
      this.wingWebbing6_LReversed.setDoubleSided(false);
      this.wingWebbing6_R.setDoubleSided(false);
      this.wingWebbing6_RReversed.setDoubleSided(false);
      this.tailFin.setDoubleSided(false);
      this.tailFinReversed.setDoubleSided(false);
   }

   public void renderToBuffer(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int color) {
      this.root.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, color);
      if (this.tail != null) {
         this.tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, color, this.tailDynamic);
      }

      for (AdvancedModelRenderer AdvancedModelRenderer : this.tailOriginal) {
         AdvancedModelRenderer.showModel = false;
      }
   }

   public void setDefaultAngle(EntityNaga entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch, float delta) {
      this.tail = entity.dc;
      this.resetToDefaultPose();
      this.resetToDefaultPoseExtendeds();
      this.modelCorrections();
      float partial = delta;
      float frame = entity.tickCount + partial;
      float hoverAnim = entity.prevHoverAnimFrac + (entity.hoverAnimFrac - entity.prevHoverAnimFrac) * partial;
      float nonHoverAnim = 1.0F - hoverAnim;
      float flapAnim = entity.prevFlapAnimFrac + (entity.flapAnimFrac - entity.prevFlapAnimFrac) * partial;
      if (!DataHandler.<FrozenData>getData(entity, DataHandler.FROZEN_DATA).getFrozen()) {
         float globalSpeed = 0.28F;
         float globalDegree = 1.0F * hoverAnim;
         float flapTimingOffset = -0.5F;
         this.wingFolder.rotationPointX = globalDegree
            * flapAnim
            * (0.9F * (float)(0.5 * Math.cos(frame * globalSpeed + 1.4 + flapTimingOffset) + 0.5) + 0.05F);
         this.wingFolder.rotationPointY = globalDegree
            * flapAnim
            * (0.9F * (float)(0.5 * Math.cos(frame * globalSpeed + 1.4 + flapTimingOffset) + 0.5) + 0.05F);
         this.flap(this.shoulder1_R, globalSpeed, 1.0F * globalDegree * flapAnim, false, 0.0F + flapTimingOffset, -0.05F, frame, 1.0F);
         this.flap(this.lowerArmJoint_R, globalSpeed, 0.7F * globalDegree * flapAnim, false, -0.6F + flapTimingOffset, 0.0F, frame, 1.0F);
         this.flap(this.handJoint_R, globalSpeed, 0.6F * globalDegree * flapAnim, false, -1.2F + flapTimingOffset, 0.0F, frame, 1.0F);
         this.flap(this.shoulder1_L, globalSpeed, 1.0F * globalDegree * flapAnim, true, 0.0F + flapTimingOffset, -0.05F, frame, 1.0F);
         this.flap(this.lowerArmJoint_L, globalSpeed, 0.7F * globalDegree * flapAnim, true, -0.6F + flapTimingOffset, 0.0F, frame, 1.0F);
         this.flap(this.handJoint_L, globalSpeed, 0.6F * globalDegree * flapAnim, true, -1.2F + flapTimingOffset, 0.0F, frame, 1.0F);
         this.backWing_R.flap(globalSpeed, 0.8F * globalDegree * flapAnim, false, -1.5F + flapTimingOffset, -0.2F, frame, 1.0F);
         this.backWing_L.flap(globalSpeed, 0.8F * globalDegree * flapAnim, true, -1.5F + flapTimingOffset, -0.2F, frame, 1.0F);
         this.bob(this.root, globalSpeed, 18.0F * globalDegree * flapAnim, false, frame - 0.5F, 1.0F);
         this.root.rotationPointY += 18.0F * globalDegree * flapAnim;
         this.body.rotateAngleX -= 0.2F * globalDegree;
         this.neck.rotateAngleX += 0.2F * globalDegree;
         this.headJoint.rotateAngleX += 0.2F * globalDegree;
         this.faceTarget(headYaw, headPitch, 2.0F, this.neck, this.headJoint);
         if (entity.movement == EntityNaga.EnumNagaMovement.FALLING && entity.getAnimation() == EntityNaga.NO_ANIMATION) {
            this.root.rotateAngleX = (float)(this.root.rotateAngleX + Math.PI);
            this.root.rotateAngleY += 0.8F;
            this.wingFolder.rotationPointX += 0.4F;
            this.wingFolder.rotationPointY += 0.2F;
            this.shoulder1_R.rotateAngleZ += -1.0F;
            this.shoulder1_L.rotateAngleZ++;
            this.lowerArm_L.rotateAngleZ += 0.3F;
            this.lowerArm_R.rotateAngleZ += -0.3F;
            this.handJoint_L.rotateAngleZ += 0.2F;
            this.handJoint_R.rotateAngleZ += -0.2F;
            this.neck.rotateAngleX += 0.3F;
            this.headJoint.rotateAngleX += 0.3F;
         } else if (entity.movement == EntityNaga.EnumNagaMovement.FALLEN && entity.getAnimation() == EntityNaga.NO_ANIMATION) {
            this.animator.move(this.root, 0.0F, -16.0F, 0.0F);
            this.animator.rotate(this.root, (float) Math.PI, 0.8F, 0.0F);
            this.animator.rotate(this.body, 0.0F, 0.0F, (float) (Math.PI / 2));
            this.animator.move(this.wingFolder, 0.6F, 0.7F, 0.0F);
            this.animator.rotate(this.shoulder1_R, 0.0F, 0.0F, -1.5F);
            this.animator.rotate(this.shoulder1_L, 0.0F, 0.0F, 1.0F);
            this.animator.rotate(this.upperArm_L, 0.2F, 0.0F, 0.4F);
            this.animator.rotate(this.lowerArmJoint_L, 0.4F, -0.5F, 0.0F);
            this.animator.rotate(this.neck, 0.4F, 0.0F, 0.0F);
            this.animator.rotate(this.headJoint, 0.4F, 0.0F, 0.0F);
            this.animator.rotate(this.tail1, -0.35F, 0.0F, 0.0F);
            this.animator.rotate(this.tail2, -0.35F, 0.0F, 0.0F);
            this.animator.rotate(this.tail3, -0.35F, 0.0F, 0.0F);
            this.animator.rotate(this.tail4, -0.35F, 0.0F, 0.0F);
            this.animator.rotate(this.tail5, -0.35F, 0.0F, 0.0F);
            this.root.rotationPointY += -16.0F;
            this.root.rotateAngleX = (float)(this.root.rotateAngleX + Math.PI);
            this.root.rotateAngleY += 0.8F;
            this.body.rotateAngleZ = (float)(this.body.rotateAngleZ + (Math.PI / 2));
            this.wingFolder.rotationPointX += 0.6F;
            this.wingFolder.rotationPointY += 0.7F;
            this.shoulder1_R.rotateAngleZ += -1.5F;
            this.shoulder1_L.rotateAngleZ++;
            this.upperArm_L.rotateAngleX += 0.2F;
            this.upperArm_L.rotateAngleZ = (float)(this.upperArm_L.rotateAngleZ + 0.4);
            this.lowerArmJoint_L.rotateAngleX += 0.4F;
            this.lowerArmJoint_L.rotateAngleY += -0.5F;
            this.neck.rotateAngleX += 0.4F;
            this.headJoint.rotateAngleX += 0.4F;
            this.tail1.rotateAngleX += -0.35F;
            this.tail2.rotateAngleX += -0.35F;
            this.tail3.rotateAngleX += -0.35F;
            this.tail4.rotateAngleX += -0.35F;
            this.tail5.rotateAngleX += -0.35F;
         }
      }
   }

   protected void animate(EntityNaga entity, float limbSwing, float limbSwingAmount, float headYaw, float headPitch, float delta) {
      this.setDefaultAngle(entity, limbSwing, limbSwingAmount, headYaw, headPitch, delta);
      float frame = entity.frame + delta;
      if (!DataHandler.<FrozenData>getData(entity, DataHandler.FROZEN_DATA).getFrozen()) {
         if (entity.getAnimation() == EntityNaga.FLAP_ANIMATION) {
            this.animator.setAnimation(EntityNaga.FLAP_ANIMATION);
            this.animator.startKeyframe(25);
            this.animator.rotate(this.wingFolder, 1.0F, 0.0F, 0.0F);
            this.animator.endKeyframe();
            this.animator.resetKeyframe(0);
         }

         if (entity.getAnimation() == EntityNaga.SPIT_ANIMATION) {
            this.animator.setAnimation(EntityNaga.SPIT_ANIMATION);
            this.animator.startKeyframe(26);
            this.animator.rotate(this.body, -1.0F, 0.0F, 0.0F);
            this.animator.rotate(this.shoulderLJoint, 0.9F, 0.0F, 0.0F);
            this.animator.rotate(this.shoulderRJoint, 0.9F, 0.0F, 0.0F);
            this.animator.move(this.body, 0.0F, -14.0F, 28.0F);
            this.animator.rotate(this.headJoint, 0.1F, 0.0F, 0.0F);
            this.animator.rotate(this.neck, 0.1F, 0.0F, 0.0F);
            this.animator.move(this.scaler, 0.2F, 0.0F, 0.0F);
            this.animator.endKeyframe();
            this.animator.setStaticKeyframe(4);
            this.animator.startKeyframe(6);
            this.animator.rotate(this.body, 0.4F, 0.0F, 0.0F);
            this.animator.move(this.body, 0.0F, 5.0F, -10.0F);
            this.animator.rotate(this.headJoint, -0.7F, 0.0F, 0.0F);
            this.animator.rotate(this.neck, -0.4F, 0.0F, 0.0F);
            this.animator.move(this.scaler, 0.0F, 1.0F, 0.0F);
            this.animator.rotate(this.jaw, 1.8F, 0.0F, 0.0F);
            this.animator.endKeyframe();
            this.animator.startKeyframe(0);
            this.animator.rotate(this.body, 0.4F, 0.0F, 0.0F);
            this.animator.move(this.body, 0.0F, 5.0F, -10.0F);
            this.animator.rotate(this.headJoint, -0.7F, 0.0F, 0.0F);
            this.animator.rotate(this.neck, -0.4F, 0.0F, 0.0F);
            this.animator.move(this.scaler, 0.0F, 0.0F, 0.0F);
            this.animator.rotate(this.jaw, 1.8F, 0.0F, 0.0F);
            this.animator.endKeyframe();
            this.animator.setStaticKeyframe(4);
            this.animator.resetKeyframe(10);
            this.body.setScale(1.0F + this.scaler.rotationPointX, 1.0F + this.scaler.rotationPointX * 2.0F, 1.0F + this.scaler.rotationPointX);
            this.body.scaleChildren = false;
            float scaleSpeed = 1.0F;
            float neckScaler = 0.6F
               * (float)Math.max(Math.pow(Math.sin((this.scaler.rotationPointY * scaleSpeed + (1.0F - scaleSpeed) / 2.0F) * Math.PI + 0.4), 3.0), 0.0);
            this.neck.setScale(1.0F + neckScaler, 1.0F + neckScaler * 1.5F, 1.0F + neckScaler);
            this.neck.rotationPointY += neckScaler * 6.0F;
            this.headJoint.rotationPointY -= neckScaler * 6.0F;
            this.neck.scaleChildren = false;
            float headScaler = 0.5F
               * (float)Math.max(Math.pow(Math.sin((this.scaler.rotationPointY * scaleSpeed + (1.0F - scaleSpeed) / 2.0F) * Math.PI - 0.4), 3.0), 0.0);
            this.headJoint.setScale(1.0F + headScaler, 1.0F + headScaler, 1.0F + headScaler);
            this.headJoint.rotationPointY += headScaler * 6.0F;
            this.headJoint.rotationPointZ -= headScaler * 6.0F;
            this.headJoint.scaleChildren = true;
         } else {
            this.neck.setScale(1.0F, 1.0F, 1.0F);
            this.body.setScale(1.0F, 1.0F, 1.0F);
            this.headJoint.setScale(1.0F, 1.0F, 1.0F);
         }

         if (entity.getAnimation() == EntityNaga.SWOOP_ANIMATION) {
            this.animator.setAnimation(EntityNaga.SWOOP_ANIMATION);
            int phase1Time = 15;
            this.animator.startKeyframe(3);
            this.animator.move(this.root, -16.0F, -16.0F, 18.0F);
            this.animator.rotate(this.root, 0.0F, -0.2F, 0.0F);
            this.animator.rotate(this.body, -0.4F, 0.0F, 0.0F);
            this.animator.rotate(this.neck, 0.2F, 0.0F, 0.0F);
            this.animator.rotate(this.headJoint, 0.2F, 0.0F, 0.0F);
            this.animator.move(this.wingFolder, 0.1F, 0.1F, 0.0F);
            this.animator.rotate(this.shoulder1_R, 0.0F, 0.0F, -1.0F);
            this.animator.rotate(this.shoulder1_L, 0.0F, 0.0F, 1.0F);
            this.animator.rotate(this.lowerArm_L, 0.0F, 0.0F, 0.6F);
            this.animator.rotate(this.lowerArm_R, 0.0F, 0.0F, -0.6F);
            this.animator.endKeyframe();
            this.animator.setStaticKeyframe(1);
            this.animator.startKeyframe(phase1Time);
            this.animator.move(this.swooper, 1.0F, 0.0F, 0.0F);
            this.animator.endKeyframe();
            this.animator.startKeyframe(0);
            this.animator.move(this.root, 0.0F, -10.0F, 10.0F);
            this.animator.move(this.wingFolder, 0.42299998F, 0.052999996F, 0.0F);
            this.animator.rotate(this.shoulder1_R, 0.0F, 0.0F, 0.327F);
            this.animator.rotate(this.shoulder1_L, 0.0F, 0.185F, -0.696F);
            this.animator.rotate(this.body, -0.543F, 0.185F, 0.027F);
            this.animator.move(this.body, 9.241F, 0.0F, 0.0F);
            this.animator.rotate(this.neck, 0.30200002F, 0.092F, 0.0F);
            this.animator.rotate(this.headJoint, 0.30200002F, 0.092F, 0.0F);
            this.animator.rotate(this.tail1, 0.0F, -0.277F, 0.0F);
            this.animator.rotate(this.tail2, 0.0F, -0.277F, 0.0F);
            this.animator.rotate(this.tail3, 0.0F, -0.277F, 0.0F);
            this.animator.rotate(this.tail4, 0.0F, -0.277F, 0.0F);
            this.animator.endKeyframe();
            this.animator.setStaticKeyframe(5);
            this.animator.startKeyframe(8);
            this.animator.move(this.wingFolder, 0.8F, 0.8F, 0.0F);
            this.animator.rotate(this.shoulder1_R, 0.0F, 0.0F, -0.6F);
            this.animator.rotate(this.shoulder1_L, 0.0F, 0.0F, -0.6F);
            this.animator.rotate(this.handJoint_R, -1.0F, 0.0F, 0.0F);
            this.animator.rotate(this.handJoint_L, 1.0F, 0.0F, 0.0F);
            this.animator.rotate(this.jaw, 2.0F, 0.0F, 0.0F);
            this.animator.rotate(this.headJoint, -1.3F, 0.0F, 0.0F);
            this.animator.move(this.swooper, 0.0F, 1.0F, 0.0F);
            this.animator.endKeyframe();
            this.animator.setStaticKeyframe(15);
            this.animator.startKeyframe(7);
            this.animator.rotate(this.body, 0.0F, 0.0F, (float) (Math.PI * 2));
            this.animator.endKeyframe();
            this.root.rotationPointX = this.root.rotationPointX + 30.0F * (float)Math.sin(this.swooper.rotationPointX * Math.PI * 2.0);
            this.root.rotationPointZ = this.root.rotationPointZ
               - 10.0F
                  * (float)(
                     this.swooper.rotationPointX * (-Math.cos(this.swooper.rotationPointX * 2.0F * Math.PI) + 1.0) - Math.pow(this.swooper.rotationPointX, 2.0)
                  );
            this.root.rotateAngleY = (float)(this.root.rotateAngleY + (Math.PI * 2) * this.swooper.rotationPointX * this.swooper.rotationPointX);
            this.root.rotationPointY = this.root.rotationPointY
               + 10.0F
                  * (float)(
                     this.swooper.rotationPointX * (-Math.cos(this.swooper.rotationPointX * 2.0F * Math.PI) + 1.0) - Math.pow(this.swooper.rotationPointX, 2.0)
                  );
            this.wingFolder.rotationPointX = (float)(
               this.wingFolder.rotationPointX
                  + (0.9 * this.smoothBlend(this.swooper.rotationPointX, 0.1F, 20.0F) - 0.4 * this.smoothBlend(this.swooper.rotationPointX, 0.95F, 50.0F))
            );
            this.wingFolder.rotationPointY = (float)(
               this.wingFolder.rotationPointY
                  + (0.9 * this.smoothBlend(this.swooper.rotationPointX, 0.1F, 20.0F) - 0.8 * this.smoothBlend(this.swooper.rotationPointX, 0.95F, 50.0F))
            );
            this.shoulder1_R.rotateAngleZ = (float)(
               this.shoulder1_R.rotateAngleZ
                  + (1.0 * this.smoothBlend(this.swooper.rotationPointX, 0.1F, 20.0F) - 0.6 * this.smoothBlend(this.swooper.rotationPointX, 0.95F, 50.0F))
            );
            this.shoulder1_L.rotateAngleZ = (float)(
               this.shoulder1_L.rotateAngleZ
                  - (1.0 * this.smoothBlend(this.swooper.rotationPointX, 0.1F, 20.0F) - 0.2 * this.smoothBlend(this.swooper.rotationPointX, 0.95F, 50.0F))
            );
            this.shoulder1_L.rotateAngleY = (float)(this.shoulder1_L.rotateAngleY + 0.2 * this.smoothBlend(this.swooper.rotationPointX, 0.95F, 50.0F));
            this.body.rotateAngleX = (float)(
               this.body.rotateAngleX
                  + (1.0 * this.smoothBlend(this.swooper.rotationPointX, 0.15F, 20.0F) - 1.5 * this.smoothBlend(this.swooper.rotationPointX, 0.7F, 20.0F))
            );
            this.body.rotateAngleZ = (float)(
               this.body.rotateAngleZ
                  - (0.6 * this.smoothBlend(this.swooper.rotationPointX, 0.15F, 20.0F) - 0.6 * this.smoothBlend(this.swooper.rotationPointX, 0.7F, 20.0F))
            );
            this.neck.rotateAngleX = (float)(
               this.neck.rotateAngleX
                  + (-0.8 * this.smoothBlend(this.swooper.rotationPointX, 0.25F, 20.0F) + 1.1 * this.smoothBlend(this.swooper.rotationPointX, 0.7F, 20.0F))
            );
            this.headJoint.rotateAngleX = (float)(
               this.headJoint.rotateAngleX
                  + (-0.8 * this.smoothBlend(this.swooper.rotationPointX, 0.25F, 20.0F) + 1.1 * this.smoothBlend(this.swooper.rotationPointX, 0.7F, 20.0F))
            );
            this.body.rotationPointX = this.body.rotationPointX + 10.0F * this.smoothBlend(this.swooper.rotationPointX, 0.95F, 50.0F);
            this.body.rotateAngleY = (float)(this.body.rotateAngleY + 0.2 * this.smoothBlend(this.swooper.rotationPointX, 0.95F, 50.0F));
            this.headJoint.rotateAngleY = (float)(this.headJoint.rotateAngleY + 0.1 * this.smoothBlend(this.swooper.rotationPointX, 0.95F, 50.0F));
            this.neck.rotateAngleY = (float)(this.neck.rotateAngleY + 0.1 * this.smoothBlend(this.swooper.rotationPointX, 0.95F, 50.0F));
            this.tail1.rotateAngleY = (float)(this.tail1.rotateAngleY - 0.3 * this.smoothBlend(this.swooper.rotationPointX, 0.95F, 50.0F));
            this.tail2.rotateAngleY = (float)(this.tail2.rotateAngleY - 0.3 * this.smoothBlend(this.swooper.rotationPointX, 0.95F, 50.0F));
            this.tail3.rotateAngleY = (float)(this.tail3.rotateAngleY - 0.3 * this.smoothBlend(this.swooper.rotationPointX, 0.95F, 50.0F));
            this.tail4.rotateAngleY = (float)(this.tail4.rotateAngleY - 0.3 * this.smoothBlend(this.swooper.rotationPointX, 0.95F, 50.0F));
            this.body.rotateAngleZ = (float)(this.body.rotateAngleZ + 1.0F * frame % (Math.PI * 2) * this.swooper.rotationPointY);
            if (entity.getAnimationTick() >= 23 && entity.getAnimationTick() < 60) {
               Vec3 prevV = new Vec3(entity.prevMotionX, entity.prevMotionY, entity.prevMotionZ);
               Vec3 dv = prevV.add(entity.getDeltaMovement().subtract(prevV).scale(delta));
               double d = Math.sqrt(dv.x * dv.x + dv.y * dv.y + dv.z * dv.z);
               if (d != 0.0) {
                  double a = dv.y / d;
                  a = Math.max(-1.0, Math.min(1.0, a));
                  float pitch = -((float)Math.asin(a));
                  this.root.rotateAngleX = this.root.rotateAngleX + pitch * this.swooper.rotationPointY;
               }
            }
         }

         if (entity.getAnimation() == EntityNaga.HURT_TO_FALL_ANIMATION) {
            this.animator.setAnimation(EntityNaga.HURT_TO_FALL_ANIMATION);
            this.animator.startKeyframe(20);
            this.animator.rotate(this.root, (float) (-Math.PI * 5), 0.8F, (float) (Math.PI * 2));
            this.animator.move(this.wingFolder, 0.4F, 0.2F, 0.0F);
            this.animator.rotate(this.shoulder1_R, 0.0F, 0.0F, -1.0F);
            this.animator.rotate(this.shoulder1_L, 0.0F, 0.0F, 1.0F);
            this.animator.rotate(this.lowerArm_L, 0.0F, 0.0F, 0.3F);
            this.animator.rotate(this.lowerArm_R, 0.0F, 0.0F, -0.3F);
            this.animator.rotate(this.handJoint_L, 0.0F, 0.0F, 0.2F);
            this.animator.rotate(this.handJoint_R, 0.0F, 0.0F, -0.2F);
            this.animator.rotate(this.neck, 0.5F, 0.0F, 0.0F);
            this.animator.rotate(this.headJoint, 0.4F, 0.0F, 0.0F);
            this.animator.endKeyframe();
            this.animator.startKeyframe(0);
            this.animator.rotate(this.root, (float) Math.PI, 0.8F, 0.0F);
            this.animator.move(this.wingFolder, 0.4F, 0.2F, 0.0F);
            this.animator.rotate(this.shoulder1_R, 0.0F, 0.0F, -1.0F);
            this.animator.rotate(this.shoulder1_L, 0.0F, 0.0F, 1.0F);
            this.animator.rotate(this.lowerArm_L, 0.0F, 0.0F, 0.3F);
            this.animator.rotate(this.lowerArm_R, 0.0F, 0.0F, -0.3F);
            this.animator.rotate(this.handJoint_L, 0.0F, 0.0F, 0.2F);
            this.animator.rotate(this.handJoint_R, 0.0F, 0.0F, -0.2F);
            this.animator.rotate(this.neck, 0.3F, 0.0F, 0.0F);
            this.animator.rotate(this.headJoint, 0.3F, 0.0F, 0.0F);
            this.animator.endKeyframe();
         }

         if (entity.getAnimation() == EntityNaga.LAND_ANIMATION) {
            this.animator.setAnimation(EntityNaga.LAND_ANIMATION);
            this.animator.startKeyframe(0);
            this.animator.rotate(this.root, (float) Math.PI, 0.8F, 0.0F);
            this.animator.move(this.root, 0.0F, -11.0F, 0.0F);
            this.animator.move(this.wingFolder, 0.4F, 0.2F, 0.0F);
            this.animator.rotate(this.shoulder1_R, 0.0F, 0.0F, -1.0F);
            this.animator.rotate(this.shoulder1_L, 0.0F, 0.0F, 1.0F);
            this.animator.rotate(this.lowerArm_L, 0.0F, 0.0F, 0.3F);
            this.animator.rotate(this.lowerArm_R, 0.0F, 0.0F, -0.3F);
            this.animator.rotate(this.handJoint_L, 0.0F, 0.0F, 0.2F);
            this.animator.rotate(this.handJoint_R, 0.0F, 0.0F, -0.2F);
            this.animator.rotate(this.neck, 0.3F, 0.0F, 0.0F);
            this.animator.rotate(this.headJoint, 0.3F, 0.0F, 0.0F);
            this.animator.endKeyframe();
            this.animator.startKeyframe(4);
            this.animator.move(this.root, 0.0F, -34.0F, 0.0F);
            this.animator.rotate(this.root, (float) Math.PI, 0.8F, 0.0F);
            this.animator.rotate(this.body, 0.0F, 0.0F, (float) (Math.PI / 4));
            this.animator.move(this.wingFolder, 0.6F, 0.6F, 0.0F);
            this.animator.rotate(this.shoulder1_R, 0.0F, 0.0F, -1.0F);
            this.animator.rotate(this.shoulder1_L, 0.0F, 0.0F, 1.0F);
            this.animator.rotate(this.lowerArm_L, 0.0F, 0.0F, 0.3F);
            this.animator.rotate(this.lowerArm_R, 0.0F, 0.0F, -0.3F);
            this.animator.rotate(this.handJoint_L, 0.0F, 0.0F, 0.2F);
            this.animator.rotate(this.handJoint_R, 0.0F, 0.0F, -0.2F);
            this.animator.rotate(this.neck, 0.3F, 0.0F, 0.0F);
            this.animator.rotate(this.headJoint, 0.3F, 0.0F, 0.0F);
            this.animator.endKeyframe();
            this.animator.startKeyframe(4);
            this.animator.move(this.root, 0.0F, -16.0F, 0.0F);
            this.animator.rotate(this.root, (float) Math.PI, 0.8F, 0.0F);
            this.animator.rotate(this.body, 0.0F, 0.0F, (float) (Math.PI / 2));
            this.animator.move(this.wingFolder, 0.6F, 0.7F, 0.0F);
            this.animator.rotate(this.shoulder1_R, 0.0F, 0.0F, -1.5F);
            this.animator.rotate(this.shoulder1_L, 0.0F, 0.0F, 1.0F);
            this.animator.rotate(this.upperArm_L, 0.2F, 0.0F, 0.4F);
            this.animator.rotate(this.lowerArmJoint_L, 0.4F, -0.5F, 0.0F);
            this.animator.rotate(this.neck, 0.4F, 0.0F, 0.0F);
            this.animator.rotate(this.headJoint, 0.4F, 0.0F, 0.0F);
            this.animator.rotate(this.tail1, -0.35F, 0.0F, 0.0F);
            this.animator.rotate(this.tail2, -0.35F, 0.0F, 0.0F);
            this.animator.rotate(this.tail3, -0.35F, 0.0F, 0.0F);
            this.animator.rotate(this.tail4, -0.35F, 0.0F, 0.0F);
            this.animator.rotate(this.tail5, -0.35F, 0.0F, 0.0F);
            this.animator.endKeyframe();
         }

         if (entity.getAnimation() == EntityNaga.GET_UP_ANIMATION) {
            this.animator.setAnimation(EntityNaga.GET_UP_ANIMATION);
            this.animator.startKeyframe(0);
            this.animator.move(this.root, 0.0F, -16.0F, 0.0F);
            this.animator.rotate(this.root, (float) -Math.PI, 0.8F, 0.0F);
            this.animator.rotate(this.body, 0.0F, 0.0F, (float) (Math.PI / 2));
            this.animator.move(this.wingFolder, 0.6F, 0.7F, 0.0F);
            this.animator.rotate(this.shoulder1_R, 0.0F, 0.0F, -1.5F);
            this.animator.rotate(this.shoulder1_L, 0.0F, 0.0F, 1.0F);
            this.animator.rotate(this.upperArm_L, 0.2F, 0.0F, 0.4F);
            this.animator.rotate(this.lowerArmJoint_L, 0.4F, -0.5F, 0.0F);
            this.animator.rotate(this.handJoint_L, 0.0F, 0.0F, 0.0F);
            this.animator.rotate(this.neck, 0.4F, 0.0F, 0.0F);
            this.animator.rotate(this.headJoint, 0.4F, 0.0F, 0.0F);
            this.animator.rotate(this.tail1, -0.35F, 0.0F, 0.0F);
            this.animator.rotate(this.tail2, -0.35F, 0.0F, 0.0F);
            this.animator.rotate(this.tail3, -0.35F, 0.0F, 0.0F);
            this.animator.rotate(this.tail4, -0.35F, 0.0F, 0.0F);
            this.animator.rotate(this.tail5, -0.35F, 0.0F, 0.0F);
            this.animator.endKeyframe();
            this.animator.startKeyframe(13);
            this.animator.move(this.wingFolder, 0.3F, 0.3F, 0.0F);
            this.animator.move(this.root, 0.0F, -35.0F, 0.0F);
            this.animator.rotate(this.neck, 0.4F, 0.0F, 0.0F);
            this.animator.rotate(this.headJoint, 0.4F, 0.0F, 0.0F);
            this.animator.rotate(this.body, -0.8F, 0.0F, 0.0F);
            this.animator.rotate(this.shoulder1_R, 0.0F, 0.0F, 1.0F);
            this.animator.rotate(this.shoulder1_L, 0.0F, 0.0F, -1.0F);
            this.animator.rotate(this.shoulderRJoint, 0.8F, 0.0F, 0.0F);
            this.animator.rotate(this.shoulderLJoint, 0.8F, 0.0F, 0.0F);
            this.animator.rotate(this.lowerArmJoint_R, 0.0F, 0.0F, 0.3F);
            this.animator.rotate(this.lowerArmJoint_L, 0.0F, 0.0F, -0.3F);
            this.animator.endKeyframe();
            this.animator.setStaticKeyframe(2);
            this.animator.startKeyframe(4);
            this.animator.move(this.wingFolder, 0.1F, 0.1F, 0.0F);
            this.animator.move(this.root, 0.0F, 0.0F, 0.0F);
            this.animator.rotate(this.neck, 0.4F, 0.0F, 0.0F);
            this.animator.rotate(this.headJoint, 0.4F, 0.0F, 0.0F);
            this.animator.rotate(this.body, -0.8F, 0.0F, 0.0F);
            this.animator.rotate(this.shoulder1_R, 0.0F, 0.0F, -1.0F);
            this.animator.rotate(this.shoulder1_L, 0.0F, 0.0F, 1.0F);
            this.animator.rotate(this.shoulderRJoint, 0.8F, 0.0F, 0.0F);
            this.animator.rotate(this.shoulderLJoint, 0.8F, 0.0F, 0.0F);
            this.animator.rotate(this.lowerArmJoint_R, 0.0F, 0.0F, -0.3F);
            this.animator.rotate(this.lowerArmJoint_L, 0.0F, 0.0F, 0.3F);
            this.animator.endKeyframe();
            this.animator.setStaticKeyframe(8);
            this.animator.resetKeyframe(6);
         }

         if (entity.getAnimation() == EntityNaga.DIE_AIR_ANIMATION) {
            this.animator.setAnimation(EntityNaga.DIE_AIR_ANIMATION);
            this.animator.startKeyframe(20);
            this.animator.rotate(this.root, (float) (-Math.PI * 5), 0.8F, (float) (Math.PI * 2));
            this.animator.move(this.wingFolder, 0.4F, 0.2F, 0.0F);
            this.animator.rotate(this.shoulder1_R, 0.0F, 0.0F, -1.0F);
            this.animator.rotate(this.shoulder1_L, 0.0F, 0.0F, 1.0F);
            this.animator.rotate(this.lowerArm_L, 0.0F, 0.0F, 0.3F);
            this.animator.rotate(this.lowerArm_R, 0.0F, 0.0F, -0.3F);
            this.animator.rotate(this.handJoint_L, 0.0F, 0.0F, 0.2F);
            this.animator.rotate(this.handJoint_R, 0.0F, 0.0F, -0.2F);
            this.animator.rotate(this.neck, 0.5F, 0.0F, 0.0F);
            this.animator.rotate(this.headJoint, 0.4F, 0.0F, 0.0F);
            this.animator.endKeyframe();
            this.animator.startKeyframe(0);
            this.animator.rotate(this.root, (float) Math.PI, 0.8F, 0.0F);
            this.animator.move(this.wingFolder, 0.4F, 0.2F, 0.0F);
            this.animator.rotate(this.shoulder1_R, 0.0F, 0.0F, -1.0F);
            this.animator.rotate(this.shoulder1_L, 0.0F, 0.0F, 1.0F);
            this.animator.rotate(this.lowerArm_L, 0.0F, 0.0F, 0.3F);
            this.animator.rotate(this.lowerArm_R, 0.0F, 0.0F, -0.3F);
            this.animator.rotate(this.handJoint_L, 0.0F, 0.0F, 0.2F);
            this.animator.rotate(this.handJoint_R, 0.0F, 0.0F, -0.2F);
            this.animator.rotate(this.neck, 0.3F, 0.0F, 0.0F);
            this.animator.rotate(this.headJoint, 0.3F, 0.0F, 0.0F);
            this.animator.endKeyframe();
            this.animator.setStaticKeyframe(30);
         }

         if (entity.getAnimation() == EntityNaga.DIE_GROUND_ANIMATION) {
            this.animator.setAnimation(EntityNaga.DIE_GROUND_ANIMATION);
            this.animator.startKeyframe(0);
            this.animator.move(this.root, 0.0F, -16.0F, 0.0F);
            this.animator.rotate(this.root, (float) -Math.PI, 0.8F, 0.0F);
            this.animator.rotate(this.body, 0.0F, 0.0F, (float) (Math.PI / 2));
            this.animator.move(this.wingFolder, 0.6F, 0.7F, 0.0F);
            this.animator.rotate(this.shoulder1_R, 0.0F, 0.0F, -1.5F);
            this.animator.rotate(this.shoulder1_L, 0.0F, 0.0F, 1.0F);
            this.animator.rotate(this.upperArm_L, 0.2F, 0.0F, 0.4F);
            this.animator.rotate(this.lowerArmJoint_L, 0.4F, -0.5F, 0.0F);
            this.animator.rotate(this.handJoint_L, 0.0F, 0.0F, 0.0F);
            this.animator.rotate(this.neck, 0.4F, 0.0F, 0.0F);
            this.animator.rotate(this.headJoint, 0.4F, 0.0F, 0.0F);
            this.animator.rotate(this.tail1, -0.35F, 0.0F, 0.0F);
            this.animator.rotate(this.tail2, -0.35F, 0.0F, 0.0F);
            this.animator.rotate(this.tail3, -0.35F, 0.0F, 0.0F);
            this.animator.rotate(this.tail4, -0.35F, 0.0F, 0.0F);
            this.animator.rotate(this.tail5, -0.35F, 0.0F, 0.0F);
            this.animator.endKeyframe();
            this.animator.startKeyframe(6);
            this.animator.move(this.root, 0.0F, -16.0F, 0.0F);
            this.animator.rotate(this.root, (float) -Math.PI, 0.8F, 0.0F);
            this.animator.rotate(this.body, 0.0F, 0.0F, (float) (Math.PI / 2));
            this.animator.move(this.wingFolder, 0.6F, 0.2F, 0.0F);
            this.animator.rotate(this.shoulder1_R, 0.0F, 0.0F, -1.5F);
            this.animator.rotate(this.shoulder1_L, 0.0F, 0.0F, 0.7F);
            this.animator.rotate(this.upperArm_L, 0.2F, 0.0F, 0.3F);
            this.animator.rotate(this.lowerArmJoint_L, -0.1F, -0.5F, 0.0F);
            this.animator.rotate(this.handJoint_L, 0.0F, 0.0F, 0.0F);
            this.animator.rotate(this.neck, 0.1F, -0.5F, 0.0F);
            this.animator.rotate(this.headJoint, -0.2F, -0.5F, 0.0F);
            this.animator.rotate(this.jaw, 1.3F, 0.0F, 0.0F);
            this.animator.rotate(this.tail1, 0.0F, 0.3F, 0.0F);
            this.animator.rotate(this.tail2, 0.0F, 0.3F, 0.0F);
            this.animator.rotate(this.tail3, 0.0F, 0.3F, 0.0F);
            this.animator.rotate(this.tail4, 0.0F, 0.3F, 0.0F);
            this.animator.rotate(this.tail5, 0.0F, 0.3F, 0.0F);
            this.animator.endKeyframe();
            this.animator.setStaticKeyframe(18);
            this.animator.startKeyframe(10);
            this.animator.move(this.root, 0.0F, -16.0F, 0.0F);
            this.animator.rotate(this.root, (float) -Math.PI, 0.8F, 0.0F);
            this.animator.rotate(this.body, 0.0F, 0.0F, (float) (Math.PI / 2));
            this.animator.move(this.wingFolder, 0.6F, 0.7F, 0.0F);
            this.animator.rotate(this.shoulder1_R, 0.0F, 0.0F, -1.5F);
            this.animator.rotate(this.shoulder1_L, 0.0F, 0.0F, 1.0F);
            this.animator.rotate(this.upperArm_L, 0.2F, 0.0F, 0.4F);
            this.animator.rotate(this.lowerArmJoint_L, 0.4F, -0.5F, 0.0F);
            this.animator.rotate(this.handJoint_L, 0.0F, 0.0F, 0.0F);
            this.animator.rotate(this.neck, 0.4F, 0.0F, 0.0F);
            this.animator.rotate(this.headJoint, 0.4F, 0.0F, 0.0F);
            this.animator.rotate(this.tail1, -0.35F, 0.0F, 0.0F);
            this.animator.rotate(this.tail2, -0.35F, 0.0F, 0.0F);
            this.animator.rotate(this.tail3, -0.35F, 0.0F, 0.0F);
            this.animator.rotate(this.tail4, -0.35F, 0.0F, 0.0F);
            this.animator.rotate(this.tail5, -0.35F, 0.0F, 0.0F);
            this.animator.endKeyframe();
            this.animator.setStaticKeyframe(16);
         }

         if (entity.getAnimation() == EntityNaga.TAIL_DEMO_ANIMATION) {
            this.animator.setAnimation(EntityNaga.TAIL_DEMO_ANIMATION);
            this.animator.startKeyframe(7);
            this.animator.move(this.root, 40.0F, 0.0F, 0.0F);
            this.animator.endKeyframe();
            this.animator.setStaticKeyframe(10);
            this.animator.resetKeyframe(7);
            this.animator.setStaticKeyframe(10);
            this.animator.startKeyframe(3);
            this.animator.move(this.root, 0.0F, -20.0F, 0.0F);
            this.animator.endKeyframe();
            this.animator.resetKeyframe(3);
            this.animator.setStaticKeyframe(10);
            this.animator.startKeyframe(4);
            this.animator.rotate(this.root, 0.0F, 1.0F, 0.0F);
            this.animator.endKeyframe();
            this.animator.startKeyframe(4);
            this.animator.rotate(this.root, 0.0F, -1.0F, 0.0F);
            this.animator.endKeyframe();
            this.animator.startKeyframe(4);
            this.animator.rotate(this.root, 0.0F, 1.0F, 0.0F);
            this.animator.endKeyframe();
            this.animator.startKeyframe(4);
            this.animator.rotate(this.root, 0.0F, -1.0F, 0.0F);
            this.animator.endKeyframe();
            this.animator.resetKeyframe(4);
            this.animator.setStaticKeyframe(10);
         }

         float globalSpeed = 0.27F;
         float globalDegree = 1.1F;
         float flapFrame = (float)(this.wingFolder.rotateAngleX * Math.PI * 2.0 / globalSpeed - (Math.PI / 2) / globalSpeed);
         globalDegree = (float)(globalDegree * (1.0 - Math.pow(Math.sin(this.wingFolder.rotateAngleX * Math.PI - (Math.PI / 2)), 8.0)));
         double folder = 0.5 * Math.cos(flapFrame * globalSpeed + 1.4) + 0.5;
         this.wingFolder.rotationPointX += globalDegree * (0.9F * (float)folder + 0.05F);
         this.wingFolder.rotationPointY += globalDegree * (0.9F * (float)folder + 0.05F);
         this.flap(this.shoulder1_R, globalSpeed, 1.0F * globalDegree, false, 0.0F, 0.0F, flapFrame, 1.0F);
         this.flap(this.lowerArmJoint_R, globalSpeed, 0.7F * globalDegree, false, -0.6F, 0.0F, flapFrame, 1.0F);
         this.flap(this.handJoint_R, globalSpeed, 0.6F * globalDegree, false, -1.2F, 0.0F, flapFrame, 1.0F);
         this.flap(this.shoulder1_L, globalSpeed, 1.0F * globalDegree, true, 0.0F, 0.0F, flapFrame, 1.0F);
         this.flap(this.lowerArmJoint_L, globalSpeed, 0.7F * globalDegree, true, -0.6F, 0.0F, flapFrame, 1.0F);
         this.flap(this.handJoint_L, globalSpeed, 0.6F * globalDegree, true, -1.2F, 0.0F, flapFrame, 1.0F);
         this.backWing_R.flap(globalSpeed, 1.0F * globalDegree, false, -0.5F, -0.2F, flapFrame, 1.0F);
         this.backWing_L.flap(globalSpeed, 1.0F * globalDegree, true, -0.5F, -0.2F, flapFrame, 1.0F);
         float hoverAnim = entity.prevHoverAnimFrac + (entity.hoverAnimFrac - entity.prevHoverAnimFrac) * delta;
         float nonHoverAnim = 1.0F - hoverAnim;
         float flapAnim = entity.prevFlapAnimFrac + (entity.flapAnimFrac - entity.prevFlapAnimFrac) * delta;
         if (entity.getAnimation() != EntityNaga.DIE_AIR_ANIMATION) {
            float pitch = entity.xRotO + (entity.getXRot() - entity.xRotO) * delta;
            pitch = (float)(-pitch * Math.PI / 180.0);
            this.root.rotateAngleX += pitch * nonHoverAnim;
            this.shoulderLJoint.rotateAngleX = (float)(this.shoulderLJoint.rotateAngleX - Math.min(pitch, 0.0F) * nonHoverAnim * (1.0 - folder));
            this.shoulderRJoint.rotateAngleX = (float)(this.shoulderRJoint.rotateAngleX - Math.min(pitch, 0.0F) * nonHoverAnim * (1.0 - folder));
            double folder2 = Math.max(Math.min(pitch, 0.8), 0.1) * nonHoverAnim * (1.0F - flapAnim);
            this.wingFolder.rotationPointX = (float)(this.wingFolder.rotationPointX + folder2 * folder);
            this.wingFolder.rotationPointY = (float)(this.wingFolder.rotationPointY + folder2 * folder);
            float banking = entity.prevBanking + (entity.banking - entity.prevBanking) * delta;
            this.body.rotateAngleZ = (float)(this.body.rotateAngleZ - 10.0 * Math.toRadians(banking) * nonHoverAnim);
         }

         this.jawControls();
         this.wingFoldControls();
         entity.dc
            .updateChain(
               Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false), this.tailOriginal, this.tailDynamic, 0.5F, 0.5F, 0.5F, 0.97F, 30, true
            );
         this.computeWingWebbing();
         entity.shoulderRot = this.shoulder1_R.rotateAngleZ;
      }
   }

   private void jawControls() {
      this.teethUpper.setScale(1.0F, 1.0F, 0.5F + 0.5F * Math.min(this.jaw.rotateAngleX, 1.0F));
      this.underHead.setScale(1.0F, 1.0F, 1.0F - 0.2F * Math.min(this.jaw.rotateAngleX, 1.0F));
      this.underHead.rotationPointZ = (float)(this.underHead.rotationPointZ + 1.2 * Math.min(this.jaw.rotateAngleX, 1.0F));
   }

   private void wingFoldControls() {
      this.shoulder1_R.rotateAngleY = (float)(this.shoulder1_R.rotateAngleY + 0.8 * this.wingFolder.rotationPointX);
      this.upperArm_R.rotateAngleY = (float)(this.upperArm_R.rotateAngleY + 0.3 * this.wingFolder.rotationPointX);
      this.lowerArm_R.rotateAngleY = (float)(this.lowerArm_R.rotateAngleY - 2.7 * this.wingFolder.rotationPointX);
      this.hand_R.rotateAngleY = (float)(this.hand_R.rotateAngleY + 1.8 * this.wingFolder.rotationPointX);
      this.wingFrame1_R.rotateAngleY = (float)(this.wingFrame1_R.rotateAngleY + 0.8 * this.wingFolder.rotationPointX);
      this.wingFrame2_R.rotateAngleY = (float)(this.wingFrame2_R.rotateAngleY + 0.3 * this.wingFolder.rotationPointX);
      this.wingFrame3_R.rotateAngleY = (float)(this.wingFrame3_R.rotateAngleY - 0.2 * this.wingFolder.rotationPointX);
      this.wingFrame4_R.rotateAngleY = (float)(this.wingFrame4_R.rotateAngleY - 0.7 * this.wingFolder.rotationPointX);
      this.shoulder1_L.rotateAngleY = (float)(this.shoulder1_L.rotateAngleY - 0.8 * this.wingFolder.rotationPointY);
      this.upperArm_L.rotateAngleY = (float)(this.upperArm_L.rotateAngleY - 0.3 * this.wingFolder.rotationPointY);
      this.lowerArm_L.rotateAngleY = (float)(this.lowerArm_L.rotateAngleY + 2.7 * this.wingFolder.rotationPointY);
      this.hand_L.rotateAngleY = (float)(this.hand_L.rotateAngleY - 1.8 * this.wingFolder.rotationPointY);
      this.wingFrame1_L.rotateAngleY = (float)(this.wingFrame1_L.rotateAngleY - 0.8 * this.wingFolder.rotationPointY);
      this.wingFrame2_L.rotateAngleY = (float)(this.wingFrame2_L.rotateAngleY - 0.3 * this.wingFolder.rotationPointY);
      this.wingFrame3_L.rotateAngleY = (float)(this.wingFrame3_L.rotateAngleY + 0.2 * this.wingFolder.rotationPointY);
      this.wingFrame4_L.rotateAngleY = (float)(this.wingFrame4_L.rotateAngleY + 0.7 * this.wingFolder.rotationPointY);
   }

   private void modelCorrections() {
      this.backWing_R.rotationPointX -= 2.0F;
      this.backWing_L.rotationPointX += 2.0F;
      this.teethLower.setScale(1.01F, 1.01F, 1.01F);
      this.spike2joint.scaleChildren = true;
      this.spike2joint.setScale(0.99F, 1.0F, 1.0F);
      this.spike2joint.rotationPointY = (float)(this.spike2joint.rotationPointY + 0.28);
      this.spike2joint.rotationPointZ = (float)(this.spike2joint.rotationPointZ - 0.15);
      this.spike3joint.scaleChildren = true;
      this.spike3joint.setScale(0.99F, 1.0F, 1.0F);
      this.spike3joint.rotationPointY = (float)(this.spike3joint.rotationPointY + 0.28);
      this.spike3joint.rotationPointZ = (float)(this.spike3joint.rotationPointZ - 0.15);
      this.spike3Bottom.setScale(1.0F, 1.8F, 1.0F);
      this.spike4joint.scaleChildren = true;
      this.spike4joint.setScale(0.993F, 1.0F, 1.0F);
      this.spike4joint.rotationPointY = (float)(this.spike4joint.rotationPointY + 0.35);
      this.spike4joint.rotationPointZ = (float)(this.spike4joint.rotationPointZ + 0.7);
      this.spike4.setScale(1.0F, 1.0F, 1.0F);
      this.spike5joint.scaleChildren = true;
      this.spike5joint.setScale(1.02F, 1.0F, 1.0F);
      this.spike5joint.rotationPointY = (float)(this.spike5joint.rotationPointY + 0.15);
      this.spike5joint.rotationPointZ = (float)(this.spike5joint.rotationPointZ - 0.42);
      this.tail1.setScale(1.03F, 1.0F, 1.0F);
      this.root.rotationPointY += 22.0F;
      this.backFin1.rotationPointX = (float)(this.backFin1.rotationPointX + 0.001);
      this.backFin1Reversed.rotationPointX = (float)(this.backFin1Reversed.rotationPointX - 0.002);
      this.backFin2.rotationPointX = (float)(this.backFin2.rotationPointX + 5.0E-4);
      this.backFin2Reversed.rotationPointX = (float)(this.backFin2Reversed.rotationPointX - 0.001);
      this.handJoint_L.rotationPointY = (float)(this.handJoint_L.rotationPointY + 0.01);
      this.handJoint_R.rotationPointY = (float)(this.handJoint_R.rotationPointY + 0.01);

      for (AdvancedModelRenderer m : this.reversePlanes) {
         setRotateAngle(m, (float) Math.PI, (float) Math.PI, 0.0F);
      }

      this.backWing_LReversed.rotationPointX = 30.0F;
      this.backWing_RReversed.rotationPointX = -30.0F;
      this.tailFinReversed.rotationPointX = 20.0F;
      this.wingWebbing1_LReversed.rotationPointX = 53.0F;
      this.wingWebbing1_RReversed.rotationPointX = -53.0F;
      this.wingWebbing2_LReversed.rotationPointX = 50.0F;
      this.wingWebbing2_RReversed.rotationPointX = -50.0F;
      this.wingWebbing3_LReversed.rotationPointX = 43.0F;
      this.wingWebbing3_RReversed.rotationPointX = -43.0F;
      this.wingWebbing4_LReversed.rotationPointX = 31.0F;
      this.wingWebbing4_RReversed.rotationPointX = -31.0F;
      this.wingWebbing5_LReversed.rotationPointX = -4.0F;
      this.wingWebbing5_RReversed.rotationPointX = 4.0F;
      this.wingWebbing6_LReversed.rotationPointX = 4.0F;
      this.wingWebbing6_RReversed.rotationPointX = -4.0F;
      this.backFin1Reversed.rotateAngleY = (float) Math.PI;
      this.backFin1Reversed.rotationPointZ = 20.0F;
   }

   private void computeWingWebbing() {
      this.wingWebbing1_L.scaleChildren = true;
      this.wingWebbing2_L.scaleChildren = true;
      this.wingWebbing3_L.scaleChildren = true;
      this.wingWebbing4_L.scaleChildren = true;
      this.wingWebbing5_L.scaleChildren = true;
      this.wingWebbing6_L.scaleChildren = true;
      this.wingWebbing1_R.scaleChildren = true;
      this.wingWebbing2_R.scaleChildren = true;
      this.wingWebbing3_R.scaleChildren = true;
      this.wingWebbing4_R.scaleChildren = true;
      this.wingWebbing6_R.scaleChildren = true;
      this.wingWebbing5_R.scaleChildren = true;
      float webbing1LScale = (float)((this.wingFrame1_L.rotateAngleY - this.wingFrame2_L.rotateAngleY) / Math.toRadians(35.0));
      webbing1LScale = 0.8F * webbing1LScale + 0.2F;
      float webbing1LRot = (float)(-(this.wingFrame1_L.rotateAngleY - (this.wingFrame2_L.rotateAngleY + Math.toRadians(35.0))) / 2.0);
      this.wingWebbing1_L.rotateAngleY += webbing1LRot;
      this.wingWebbing1_L.setScale(1.0F - webbing1LScale * 0.07F, 1.0F, webbing1LScale);
      float webbing2LScale = (float)((this.wingFrame2_L.rotateAngleY - this.wingFrame3_L.rotateAngleY) / Math.toRadians(35.0));
      webbing2LScale = 0.8F * webbing2LScale + 0.2F;
      float webbing2LRot = (float)(-(this.wingFrame2_L.rotateAngleY - (this.wingFrame3_L.rotateAngleY + Math.toRadians(35.0))) / 2.0);
      this.wingWebbing2_L.rotateAngleY += webbing2LRot;
      this.wingWebbing2_L.setScale(1.0F - webbing2LScale * 0.07F, 1.0F, webbing2LScale);
      float webbing3LScale = (float)((this.wingFrame3_L.rotateAngleY - this.wingFrame4_L.rotateAngleY) / Math.toRadians(35.0));
      webbing3LScale = 0.8F * webbing3LScale + 0.2F;
      float webbing3LRot = (float)(-(this.wingFrame3_L.rotateAngleY - (this.wingFrame4_L.rotateAngleY + Math.toRadians(35.0))) / 2.0);
      this.wingWebbing3_L.rotateAngleY += webbing3LRot;
      this.wingWebbing3_L.setScale(1.0F - webbing3LScale * 0.07F, 1.0F, webbing3LScale);
      float webbing4LScale = 1.0F - (float)(-0.5 * this.hand_L.rotateAngleY);
      float webbing4LRot = (float)(-(this.wingFrame4_L.rotateAngleY + this.hand_L.rotateAngleY + Math.toRadians(105.0)) / 2.0);
      this.wingWebbing4_L.rotateAngleY += webbing4LRot;
      this.wingWebbing4_L.setScale(1.0F - webbing4LScale * 0.02F, 1.0F, webbing4LScale);
      this.wingWebbing5_L.rotationPointX = this.wingWebbing5_L.rotationPointX + 12.0F * (float)(this.upperArm_L.rotateAngleY / Math.toRadians(35.0));
      this.wingWebbing5_L
         .setScale(
            (float)(2.0 - (-this.shoulder1_L.rotateAngleY + this.shoulder1_L.defaultRotationY) / 1.5),
            1.0F,
            (float)(0.915 - (-this.shoulder1_L.rotateAngleY + this.shoulder1_L.defaultRotationY) / 1.2)
         );
      this.wingWebbing6_L
         .setScale(
            1.0F + (-this.shoulder1_L.rotateAngleY + this.shoulder1_L.defaultRotationY) / 4.0F,
            1.0F,
            (float)(1.0 - (-this.shoulder1_L.rotateAngleY + this.shoulder1_L.defaultRotationY) / 1.6)
         );
      this.wingWebbing6_L.rotationPointX = this.wingWebbing6_L.rotationPointX + 14.0F * (-this.shoulder1_L.rotateAngleY + this.shoulder1_L.defaultRotationY);
      float webbing1RScale = (float)(-(this.wingFrame1_R.rotateAngleY - this.wingFrame2_R.rotateAngleY) / Math.toRadians(35.0));
      webbing1RScale = 0.8F * webbing1RScale + 0.2F;
      float webbing1RRot = (float)(-(this.wingFrame1_R.rotateAngleY - (this.wingFrame2_R.rotateAngleY - Math.toRadians(35.0))) / 2.0);
      this.wingWebbing1_R.rotateAngleY += webbing1RRot;
      this.wingWebbing1_R.setScale(1.0F - webbing1RScale * 0.07F, 1.0F, webbing1RScale);
      float webbing2RScale = (float)(-(this.wingFrame2_R.rotateAngleY - this.wingFrame3_R.rotateAngleY) / Math.toRadians(35.0));
      webbing2RScale = 0.8F * webbing2RScale + 0.2F;
      float webbing2RRot = (float)(-(this.wingFrame2_R.rotateAngleY - (this.wingFrame3_R.rotateAngleY - Math.toRadians(35.0))) / 2.0);
      this.wingWebbing2_R.rotateAngleY += webbing2RRot;
      this.wingWebbing2_R.setScale(1.0F - webbing2RScale * 0.07F, 1.0F, webbing2RScale);
      float webbing3RScale = (float)(-(this.wingFrame3_R.rotateAngleY - this.wingFrame4_R.rotateAngleY) / Math.toRadians(35.0));
      webbing3RScale = 0.8F * webbing3RScale + 0.2F;
      float webbing3RRot = (float)(-(this.wingFrame3_R.rotateAngleY - (this.wingFrame4_R.rotateAngleY - Math.toRadians(35.0))) / 2.0);
      this.wingWebbing3_R.rotateAngleY += webbing3RRot;
      this.wingWebbing3_R.setScale(1.0F - webbing3RScale * 0.07F, 1.0F, webbing3RScale);
      float webbing4RScale = 1.0F - (float)(0.5 * this.hand_R.rotateAngleY);
      float webbing4RRot = (float)(-(this.wingFrame4_R.rotateAngleY + this.hand_R.rotateAngleY - Math.toRadians(105.0)) / 2.0);
      this.wingWebbing4_R.rotateAngleY += webbing4RRot;
      this.wingWebbing4_R.setScale(1.0F - webbing4RScale * 0.02F, 1.0F, webbing4RScale);
      this.wingWebbing5_R.rotationPointX = this.wingWebbing5_R.rotationPointX - 12.0F * (float)(-this.upperArm_R.rotateAngleY / Math.toRadians(35.0));
      this.wingWebbing5_R
         .setScale(
            (float)(2.0 - (this.shoulder1_R.rotateAngleY - this.shoulder1_R.defaultRotationY) / 1.5),
            1.0F,
            (float)(0.915 - (this.shoulder1_R.rotateAngleY - this.shoulder1_R.defaultRotationY) / 1.2)
         );
      this.wingWebbing6_R
         .setScale(
            1.0F + (this.shoulder1_R.rotateAngleY - this.shoulder1_R.defaultRotationY) / 4.0F,
            1.0F,
            (float)(1.0 - (this.shoulder1_R.rotateAngleY - this.shoulder1_R.defaultRotationY) / 1.6)
         );
      this.wingWebbing6_R.rotationPointX = this.wingWebbing6_R.rotationPointX + 14.0F * (this.shoulder1_L.rotateAngleY - this.shoulder1_L.defaultRotationY);
      this.wingWebbing4_R.rotationPointY -= 0.01F;
      this.wingWebbing4_L.rotationPointY -= 0.01F;
      this.wingWebbing5_R.rotationPointY += 0.01F;
      this.wingWebbing5_L.rotationPointY += 0.01F;
      this.wingWebbing3_R.rotationPointY += 0.01F;
      this.wingWebbing3_L.rotationPointY += 0.01F;
      this.tailFin.rotationPointY -= 0.1F;
      if (this.tailDynamic[0] != null) {
         this.backFin1.setScale(1.0F, 1.0F, 1.0F - 0.5F * this.tailDynamic[0].rotateAngleX);
         this.backFin1.scaleChildren = true;
         this.backFin1.rotationPointX = (float)(this.backFin1.rotationPointX + 0.002);
         this.backFin1Reversed.rotationPointX = (float)(this.backFin1Reversed.rotationPointX - 0.004);
         this.backFin2.setScale(1.0F, 1.0F, 1.0F - 0.2F * this.tailDynamic[1].rotateAngleX);
         this.backFin2.scaleChildren = true;
         this.backFin2.rotationPointX = (float)(this.backFin2.rotationPointX + 0.001);
         this.backFin2Reversed.rotationPointX = (float)(this.backFin2Reversed.rotationPointX - 0.002);
      }
   }

   public void updateDefaultPoseExtendeds() {
      this.boxList.stream().filter(modelRenderer -> modelRenderer instanceof AdvancedModelRenderer).forEach(modelRenderer -> {
         AdvancedModelRenderer extendedModelRenderer = (AdvancedModelRenderer)modelRenderer;
         extendedModelRenderer.updateDefaultPose();
      });
   }

   public void resetToDefaultPoseExtendeds() {
      this.boxList.stream().filter(modelRenderer -> modelRenderer instanceof AdvancedModelRenderer).forEach(modelRenderer -> {
         AdvancedModelRenderer extendedModelRenderer = (AdvancedModelRenderer)modelRenderer;
         extendedModelRenderer.resetToDefaultPose();
      });
   }

   private float smoothBlend(float x, float center, float speed) {
      return (float)(1.0 / (1.0 + Math.exp(-speed * (x - center))));
   }
}
