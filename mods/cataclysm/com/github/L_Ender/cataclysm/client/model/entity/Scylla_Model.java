package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.client.animation.Scylla_Lightning_Animation;
import com.github.L_Ender.cataclysm.client.animation.Scylla_Normal_Animation;
import com.github.L_Ender.cataclysm.client.animation.Scylla_Projectile_Animation;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Scylla.Scylla_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector4f;

public class Scylla_Model extends HierarchicalModel<Scylla_Entity> {
   private final ModelPart root;
   private final ModelPart everything;
   private final ModelPart scylla;
   private final ModelPart r_leg;
   private final ModelPart r_leg_armor;
   private final ModelPart r_leg2;
   private final ModelPart body;
   private final ModelPart belt_sea_snake_mody;
   private final ModelPart belt_sea_snake;
   private final ModelPart belt_sea_snake2;
   private final ModelPart belt_sea_snake3;
   private final ModelPart belt_sea_snake_head;
   private final ModelPart belt_sea_snake_head2;
   private final ModelPart belt_sea_snake_tongue;
   private final ModelPart chest;
   private final ModelPart head;
   private final ModelPart l_eye;
   private final ModelPart r_eye;
   private final ModelPart hair;
   private final ModelPart hair2;
   private final ModelPart l_arm;
   private final ModelPart l_armor;
   private final ModelPart l_sea_snake;
   private final ModelPart l_sea_snake2;
   private final ModelPart l_sea_snake3;
   private final ModelPart l_sea_snake_head;
   private final ModelPart r_sea_snake_head3;
   private final ModelPart l_sea_snake_tongue;
   private final ModelPart l_arm2;
   private final ModelPart anchor;
   private final ModelPart trail2;
   private final ModelPart chain_main;
   private final ModelPart chain_4;
   private final ModelPart chain_3;
   private final ModelPart chain_2;
   private final ModelPart chain;
   private final ModelPart chain_anchor;
   private final ModelPart trail;
   private final ModelPart r_arm;
   private final ModelPart r_armor;
   private final ModelPart r_arm2;
   private final ModelPart r_sea_snake;
   private final ModelPart r_sea_snake2;
   private final ModelPart r_sea_snake3;
   private final ModelPart r_sea_snake_head;
   private final ModelPart r_sea_snake_tongue;
   private final ModelPart r_sea_snake_head2;
   private final ModelPart l_leg;
   private final ModelPart l_leg_armor;
   private final ModelPart l_leg2;
   private final ModelPart anchor2;

   public Scylla_Model(ModelPart root) {
      this.root = root;
      this.everything = this.root.getChild("everything");
      this.anchor2 = this.root.getChild("anchor2");
      this.scylla = this.everything.getChild("scylla");
      this.r_leg = this.scylla.getChild("r_leg");
      this.r_leg_armor = this.r_leg.getChild("r_leg_armor");
      this.r_leg2 = this.r_leg.getChild("r_leg2");
      this.body = this.scylla.getChild("body");
      this.belt_sea_snake_mody = this.body.getChild("belt_sea_snake_mody");
      this.belt_sea_snake = this.belt_sea_snake_mody.getChild("belt_sea_snake");
      this.belt_sea_snake2 = this.belt_sea_snake.getChild("belt_sea_snake2");
      this.belt_sea_snake3 = this.belt_sea_snake2.getChild("belt_sea_snake3");
      this.belt_sea_snake_head = this.belt_sea_snake3.getChild("belt_sea_snake_head");
      this.belt_sea_snake_head2 = this.belt_sea_snake_head.getChild("belt_sea_snake_head2");
      this.belt_sea_snake_tongue = this.belt_sea_snake_head.getChild("belt_sea_snake_tongue");
      this.chest = this.body.getChild("chest");
      this.head = this.chest.getChild("head");
      this.l_eye = this.head.getChild("l_eye");
      this.r_eye = this.head.getChild("r_eye");
      this.hair = this.head.getChild("hair");
      this.hair2 = this.hair.getChild("hair2");
      this.l_arm = this.chest.getChild("l_arm");
      this.l_armor = this.l_arm.getChild("l_armor");
      this.l_sea_snake = this.l_armor.getChild("l_sea_snake");
      this.l_sea_snake2 = this.l_sea_snake.getChild("l_sea_snake2");
      this.l_sea_snake3 = this.l_sea_snake2.getChild("l_sea_snake3");
      this.l_sea_snake_head = this.l_sea_snake3.getChild("l_sea_snake_head");
      this.r_sea_snake_head3 = this.l_sea_snake_head.getChild("r_sea_snake_head3");
      this.l_sea_snake_tongue = this.l_sea_snake_head.getChild("l_sea_snake_tongue");
      this.l_arm2 = this.l_arm.getChild("l_arm2");
      this.anchor = this.l_arm2.getChild("anchor");
      this.trail2 = this.anchor.getChild("trail2");
      this.chain_main = this.l_arm2.getChild("chain_main");
      this.chain_4 = this.chain_main.getChild("chain_4");
      this.chain_3 = this.chain_4.getChild("chain_3");
      this.chain_2 = this.chain_3.getChild("chain_2");
      this.chain = this.chain_2.getChild("chain");
      this.chain_anchor = this.chain.getChild("chain_anchor");
      this.trail = this.chain_anchor.getChild("trail");
      this.r_arm = this.chest.getChild("r_arm");
      this.r_armor = this.r_arm.getChild("r_armor");
      this.r_arm2 = this.r_arm.getChild("r_arm2");
      this.r_sea_snake = this.r_arm.getChild("r_sea_snake");
      this.r_sea_snake2 = this.r_sea_snake.getChild("r_sea_snake2");
      this.r_sea_snake3 = this.r_sea_snake2.getChild("r_sea_snake3");
      this.r_sea_snake_head = this.r_sea_snake3.getChild("r_sea_snake_head");
      this.r_sea_snake_tongue = this.r_sea_snake_head.getChild("r_sea_snake_tongue");
      this.r_sea_snake_head2 = this.r_sea_snake_head.getChild("r_sea_snake_head2");
      this.l_leg = this.scylla.getChild("l_leg");
      this.l_leg_armor = this.l_leg.getChild("l_leg_armor");
      this.l_leg2 = this.l_leg.getChild("l_leg2");
   }

   public static LayerDefinition createBodyLayer() {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 0000: new net/minecraft/client/model/geom/builders/MeshDefinition
      // 0003: dup
      // 0004: invokespecial net/minecraft/client/model/geom/builders/MeshDefinition.<init> ()V
      // 0007: astore 0
      // 0008: aload 0
      // 0009: invokevirtual net/minecraft/client/model/geom/builders/MeshDefinition.getRoot ()Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 000c: astore 1
      // 000d: aload 1
      // 000e: ldc "everything"
      // 0010: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0013: fconst_0
      // 0014: ldc 24.0
      // 0016: fconst_0
      // 0017: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 001a: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 001d: astore 2
      // 001e: aload 2
      // 001f: ldc "scylla"
      // 0021: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0024: fconst_0
      // 0025: fconst_0
      // 0026: fconst_0
      // 0027: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 002a: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 002d: astore 3
      // 002e: aload 3
      // 002f: ldc "r_leg"
      // 0031: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0034: bipush 120
      // 0036: bipush 66
      // 0038: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 003b: ldc -2.0
      // 003d: fconst_0
      // 003e: ldc -2.0
      // 0040: ldc 4.0
      // 0042: ldc 9.0
      // 0044: ldc 4.0
      // 0046: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0049: dup
      // 004a: fconst_0
      // 004b: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 004e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0051: ldc -2.0
      // 0053: ldc_w -20.0
      // 0056: fconst_0
      // 0057: fconst_0
      // 0058: ldc_w 0.0873
      // 005b: ldc_w 0.0436
      // 005e: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0061: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0064: astore 4
      // 0066: aload 4
      // 0068: ldc "r_leg_armor"
      // 006a: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 006d: bipush 90
      // 006f: bipush 117
      // 0071: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0074: ldc_w -0.4347
      // 0077: ldc_w 0.4957
      // 007a: ldc_w -2.5
      // 007d: ldc_w 3.0
      // 0080: ldc_w 8.0
      // 0083: ldc_w 5.0
      // 0086: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0089: dup
      // 008a: ldc_w 0.2
      // 008d: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0090: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0093: ldc_w -1.7
      // 0096: fconst_0
      // 0097: fconst_0
      // 0098: fconst_0
      // 0099: fconst_0
      // 009a: ldc_w 0.1309
      // 009d: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 00a0: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 00a3: astore 5
      // 00a5: aload 4
      // 00a7: ldc "r_leg2"
      // 00a9: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 00ac: bipush 34
      // 00ae: bipush 115
      // 00b0: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 00b3: ldc -2.0
      // 00b5: fconst_1
      // 00b6: ldc -2.0
      // 00b8: ldc 4.0
      // 00ba: ldc_w 10.0
      // 00bd: ldc 4.0
      // 00bf: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 00c2: dup
      // 00c3: ldc_w 0.3
      // 00c6: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 00c9: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 00cc: bipush 0
      // 00cd: bipush 115
      // 00cf: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 00d2: ldc -2.0
      // 00d4: fconst_0
      // 00d5: ldc -2.0
      // 00d7: ldc 4.0
      // 00d9: ldc_w 11.0
      // 00dc: ldc 4.0
      // 00de: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 00e1: dup
      // 00e2: fconst_0
      // 00e3: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 00e6: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 00e9: fconst_0
      // 00ea: ldc 9.0
      // 00ec: fconst_0
      // 00ed: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 00f0: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 00f3: astore 6
      // 00f5: aload 6
      // 00f7: ldc_w "cube_r1"
      // 00fa: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 00fd: bipush 109
      // 00ff: bipush 52
      // 0101: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0104: ldc_w -1.5
      // 0107: ldc_w -1.5
      // 010a: ldc_w -0.5
      // 010d: ldc_w 3.0
      // 0110: ldc_w 3.0
      // 0113: fconst_1
      // 0114: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0117: dup
      // 0118: fconst_0
      // 0119: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 011c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 011f: fconst_0
      // 0120: fconst_0
      // 0121: ldc_w -2.5
      // 0124: fconst_0
      // 0125: fconst_0
      // 0126: ldc_w 0.7854
      // 0129: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 012c: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 012f: astore 7
      // 0131: aload 3
      // 0132: ldc "body"
      // 0134: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0137: bipush 25
      // 0139: bipush 107
      // 013b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 013e: ldc_w -4.0
      // 0141: ldc_w -1.5
      // 0144: ldc_w -2.5
      // 0147: ldc_w 8.0
      // 014a: fconst_2
      // 014b: ldc_w 5.0
      // 014e: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0151: dup
      // 0152: fconst_0
      // 0153: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0156: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0159: bipush 104
      // 015b: bipush 23
      // 015d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0160: ldc_w -4.0
      // 0163: ldc -2.0
      // 0165: ldc_w -2.5
      // 0168: ldc_w 8.0
      // 016b: ldc_w 3.0
      // 016e: ldc_w 5.0
      // 0171: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0174: dup
      // 0175: ldc_w 0.1
      // 0178: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 017b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 017e: bipush 109
      // 0180: bipush 32
      // 0182: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0185: ldc_w -3.5
      // 0188: ldc_w -6.0
      // 018b: ldc -2.0
      // 018d: ldc_w 7.0
      // 0190: ldc_w 6.0
      // 0193: ldc 4.0
      // 0195: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0198: dup
      // 0199: fconst_0
      // 019a: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 019d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 01a0: fconst_0
      // 01a1: ldc_w -20.0
      // 01a4: fconst_0
      // 01a5: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 01a8: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 01ab: astore 8
      // 01ad: aload 8
      // 01af: ldc "belt_sea_snake_mody"
      // 01b1: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 01b4: fconst_0
      // 01b5: ldc_w -1.0
      // 01b8: fconst_0
      // 01b9: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 01bc: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 01bf: astore 9
      // 01c1: aload 9
      // 01c3: ldc_w "cube_r2"
      // 01c6: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 01c9: bipush 89
      // 01cb: bipush 62
      // 01cd: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 01d0: ldc_w -4.5
      // 01d3: ldc_w -1.0
      // 01d6: ldc_w -3.0
      // 01d9: ldc 9.0
      // 01db: fconst_2
      // 01dc: ldc_w 6.0
      // 01df: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 01e2: dup
      // 01e3: fconst_0
      // 01e4: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 01e7: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 01ea: fconst_0
      // 01eb: fconst_0
      // 01ec: fconst_0
      // 01ed: fconst_0
      // 01ee: ldc_w -0.1309
      // 01f1: ldc_w 0.1745
      // 01f4: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 01f7: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 01fa: astore 10
      // 01fc: aload 9
      // 01fe: ldc "belt_sea_snake"
      // 0200: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0203: sipush 131
      // 0206: bipush 23
      // 0208: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 020b: ldc_w -1.0
      // 020e: ldc_w -1.0
      // 0211: ldc_w -1.0
      // 0214: fconst_2
      // 0215: fconst_2
      // 0216: ldc_w 6.0
      // 0219: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 021c: dup
      // 021d: fconst_0
      // 021e: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0221: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0224: ldc_w -4.0
      // 0227: ldc_w -1.0
      // 022a: fconst_2
      // 022b: ldc_w 1.007
      // 022e: ldc_w -0.4346
      // 0231: ldc_w -1.0914
      // 0234: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0237: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 023a: astore 11
      // 023c: aload 11
      // 023e: ldc "belt_sea_snake2"
      // 0240: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0243: sipush 131
      // 0246: bipush 23
      // 0248: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 024b: ldc_w -1.0
      // 024e: ldc_w -1.0
      // 0251: fconst_0
      // 0252: fconst_2
      // 0253: fconst_2
      // 0254: ldc_w 6.0
      // 0257: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 025a: dup
      // 025b: fconst_0
      // 025c: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 025f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0262: fconst_0
      // 0263: fconst_0
      // 0264: ldc_w 5.0
      // 0267: ldc_w 1.1709
      // 026a: ldc_w -0.1796
      // 026d: ldc_w -0.3999
      // 0270: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0273: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0276: astore 12
      // 0278: aload 12
      // 027a: ldc "belt_sea_snake3"
      // 027c: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 027f: sipush 131
      // 0282: bipush 23
      // 0284: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0287: ldc_w -1.0
      // 028a: ldc_w -1.0
      // 028d: fconst_0
      // 028e: fconst_2
      // 028f: fconst_2
      // 0290: ldc_w 6.0
      // 0293: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0296: dup
      // 0297: fconst_0
      // 0298: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 029b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 029e: fconst_0
      // 029f: fconst_0
      // 02a0: ldc_w 6.0
      // 02a3: ldc_w 0.7135
      // 02a6: ldc_w 0.3775
      // 02a9: ldc_w -0.3719
      // 02ac: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 02af: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 02b2: astore 13
      // 02b4: aload 13
      // 02b6: ldc "belt_sea_snake_head"
      // 02b8: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 02bb: fconst_0
      // 02bc: fconst_0
      // 02bd: ldc_w 6.0
      // 02c0: ldc_w 0.4503
      // 02c3: ldc_w -0.1006
      // 02c6: ldc_w -0.6365
      // 02c9: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 02cc: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 02cf: astore 14
      // 02d1: aload 14
      // 02d3: ldc "belt_sea_snake_head2"
      // 02d5: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 02d8: bipush 92
      // 02da: sipush 131
      // 02dd: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 02e0: ldc_w -1.5
      // 02e3: ldc_w -1.0
      // 02e6: fconst_0
      // 02e7: ldc_w 3.0
      // 02ea: fconst_2
      // 02eb: ldc 4.0
      // 02ed: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 02f0: dup
      // 02f1: fconst_0
      // 02f2: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 02f5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 02f8: fconst_0
      // 02f9: fconst_0
      // 02fa: fconst_0
      // 02fb: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 02fe: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0301: astore 15
      // 0303: aload 14
      // 0305: ldc "belt_sea_snake_tongue"
      // 0307: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 030a: sipush 131
      // 030d: bipush 7
      // 030f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0312: ldc_w -1.5
      // 0315: fconst_0
      // 0316: fconst_0
      // 0317: ldc_w 3.0
      // 031a: fconst_0
      // 031b: ldc_w 3.0
      // 031e: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0321: dup
      // 0322: fconst_0
      // 0323: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0326: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0329: fconst_0
      // 032a: ldc_w -0.3
      // 032d: ldc 4.0
      // 032f: ldc_w 0.2618
      // 0332: fconst_0
      // 0333: fconst_0
      // 0334: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0337: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 033a: astore 16
      // 033c: aload 8
      // 033e: ldc "chest"
      // 0340: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0343: fconst_0
      // 0344: ldc_w -5.0
      // 0347: fconst_0
      // 0348: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 034b: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 034e: astore 17
      // 0350: aload 17
      // 0352: ldc_w "cube_r3"
      // 0355: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0358: bipush 78
      // 035a: bipush 39
      // 035c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 035f: ldc_w -4.0
      // 0362: ldc_w -2.0995
      // 0365: ldc_w -2.9822
      // 0368: ldc_w 8.0
      // 036b: ldc_w 5.0
      // 036e: ldc_w 7.0
      // 0371: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0374: dup
      // 0375: ldc_w 0.1
      // 0378: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 037b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 037e: fconst_0
      // 037f: ldc_w -2.7005
      // 0382: ldc_w -2.1178
      // 0385: ldc_w -0.3054
      // 0388: fconst_0
      // 0389: fconst_0
      // 038a: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 038d: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0390: astore 18
      // 0392: aload 17
      // 0394: ldc_w "cube_r4"
      // 0397: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 039a: bipush 46
      // 039c: bipush 85
      // 039e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 03a1: ldc_w -4.0
      // 03a4: ldc_w -2.0995
      // 03a7: ldc_w -2.9822
      // 03aa: ldc_w 8.0
      // 03ad: fconst_2
      // 03ae: ldc_w 7.0
      // 03b1: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 03b4: dup
      // 03b5: fconst_0
      // 03b6: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 03b9: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 03bc: fconst_0
      // 03bd: ldc_w 2.0481
      // 03c0: ldc_w -0.2347
      // 03c3: ldc_w 0.1309
      // 03c6: fconst_0
      // 03c7: fconst_0
      // 03c8: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 03cb: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 03ce: astore 19
      // 03d0: aload 17
      // 03d2: ldc_w "cube_r5"
      // 03d5: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 03d8: bipush 90
      // 03da: bipush 107
      // 03dc: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 03df: ldc_w -4.0
      // 03e2: ldc_w -2.5
      // 03e5: ldc -2.0
      // 03e7: ldc_w 8.0
      // 03ea: ldc_w 5.0
      // 03ed: ldc 4.0
      // 03ef: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 03f2: dup
      // 03f3: ldc_w 0.001
      // 03f6: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 03f9: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 03fc: fconst_0
      // 03fd: ldc_w -2.6071
      // 0400: ldc_w -3.0929
      // 0403: ldc_w -0.3054
      // 0406: fconst_0
      // 0407: fconst_0
      // 0408: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 040b: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 040e: astore 20
      // 0410: aload 17
      // 0412: ldc_w "cube_r6"
      // 0415: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0418: bipush 73
      // 041a: bipush 25
      // 041c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 041f: ldc_w -4.0
      // 0422: ldc_w -2.0995
      // 0425: ldc_w -2.9822
      // 0428: ldc_w 8.0
      // 042b: ldc_w 6.0
      // 042e: ldc_w 7.0
      // 0431: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0434: dup
      // 0435: ldc_w 0.2
      // 0438: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 043b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 043e: bipush 73
      // 0440: bipush 11
      // 0442: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0445: ldc_w -4.0
      // 0448: ldc_w -2.0995
      // 044b: ldc_w -2.9822
      // 044e: ldc_w 8.0
      // 0451: ldc_w 6.0
      // 0454: ldc_w 7.0
      // 0457: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 045a: dup
      // 045b: fconst_0
      // 045c: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 045f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0462: fconst_0
      // 0463: ldc_w -3.9005
      // 0466: ldc_w -1.0178
      // 0469: ldc_w 0.1309
      // 046c: fconst_0
      // 046d: fconst_0
      // 046e: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0471: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0474: astore 21
      // 0476: aload 17
      // 0478: ldc "head"
      // 047a: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 047d: bipush 56
      // 047f: bipush 68
      // 0481: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0484: ldc_w -4.0
      // 0487: ldc_w -8.0
      // 048a: ldc_w -4.0
      // 048d: ldc_w 8.0
      // 0490: ldc_w 8.0
      // 0493: ldc_w 8.0
      // 0496: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0499: dup
      // 049a: ldc_w 0.3
      // 049d: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 04a0: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 04a3: bipush 23
      // 04a5: bipush 68
      // 04a7: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 04aa: ldc_w -4.0
      // 04ad: ldc_w -8.0
      // 04b0: ldc_w -4.0
      // 04b3: ldc_w 8.0
      // 04b6: ldc_w 8.0
      // 04b9: ldc_w 8.0
      // 04bc: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 04bf: dup
      // 04c0: fconst_0
      // 04c1: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 04c4: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 04c7: bipush 66
      // 04c9: bipush 123
      // 04cb: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 04ce: ldc_w -2.5
      // 04d1: ldc_w -1.0
      // 04d4: ldc_w -2.5
      // 04d7: ldc_w 5.0
      // 04da: fconst_2
      // 04db: ldc_w 5.0
      // 04de: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 04e1: dup
      // 04e2: fconst_0
      // 04e3: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 04e6: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 04e9: fconst_0
      // 04ea: ldc_w -6.5
      // 04ed: fconst_0
      // 04ee: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 04f1: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 04f4: astore 22
      // 04f6: aload 22
      // 04f8: ldc_w "cube_r7"
      // 04fb: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 04fe: bipush 32
      // 0500: sipush 130
      // 0503: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0506: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0509: ldc_w -1.5
      // 050c: ldc_w -1.5
      // 050f: ldc_w -4.5
      // 0512: ldc_w 3.0
      // 0515: ldc_w 3.0
      // 0518: ldc_w 5.0
      // 051b: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 051e: dup
      // 051f: ldc_w 0.3
      // 0522: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0525: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0528: bipush 0
      // 0529: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 052c: ldc_w 3.1736
      // 052f: ldc_w -0.7152
      // 0532: fconst_0
      // 0533: ldc_w 0.1298
      // 0536: ldc_w -0.0011
      // 0539: ldc_w -0.1575
      // 053c: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 053f: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0542: astore 23
      // 0544: aload 22
      // 0546: ldc_w "cube_r8"
      // 0549: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 054c: bipush 32
      // 054e: sipush 130
      // 0551: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0554: ldc_w -1.5
      // 0557: ldc_w -1.5
      // 055a: ldc_w -4.5
      // 055d: ldc_w 3.0
      // 0560: ldc_w 3.0
      // 0563: ldc_w 5.0
      // 0566: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0569: dup
      // 056a: ldc_w 0.3
      // 056d: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0570: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0573: ldc_w -3.1736
      // 0576: ldc_w -0.7152
      // 0579: fconst_0
      // 057a: ldc_w 0.1298
      // 057d: ldc_w 0.0011
      // 0580: ldc_w 0.1575
      // 0583: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0586: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0589: astore 24
      // 058b: aload 22
      // 058d: ldc_w "cube_r9"
      // 0590: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0593: bipush 110
      // 0595: bipush 0
      // 0596: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0599: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 059c: fconst_0
      // 059d: ldc_w -5.0
      // 05a0: fconst_0
      // 05a1: ldc_w 10.0
      // 05a4: ldc_w 10.0
      // 05a7: fconst_0
      // 05a8: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 05ab: dup
      // 05ac: fconst_0
      // 05ad: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 05b0: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 05b3: bipush 0
      // 05b4: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 05b7: ldc 4.0
      // 05b9: ldc_w -6.0
      // 05bc: fconst_0
      // 05bd: fconst_0
      // 05be: ldc_w -0.3054
      // 05c1: fconst_0
      // 05c2: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 05c5: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 05c8: astore 25
      // 05ca: aload 22
      // 05cc: ldc_w "cube_r10"
      // 05cf: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 05d2: bipush 110
      // 05d4: bipush 0
      // 05d5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 05d8: ldc_w -10.0
      // 05db: ldc_w -5.0
      // 05de: fconst_0
      // 05df: ldc_w 10.0
      // 05e2: ldc_w 10.0
      // 05e5: fconst_0
      // 05e6: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 05e9: dup
      // 05ea: fconst_0
      // 05eb: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 05ee: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 05f1: ldc_w -4.0
      // 05f4: ldc_w -6.0
      // 05f7: fconst_0
      // 05f8: fconst_0
      // 05f9: ldc_w 0.3054
      // 05fc: fconst_0
      // 05fd: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0600: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0603: astore 26
      // 0605: aload 22
      // 0607: ldc_w "cube_r11"
      // 060a: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 060d: sipush 132
      // 0610: bipush 32
      // 0612: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0615: ldc_w -5.0
      // 0618: fconst_2
      // 0619: ldc_w -5.0
      // 061c: fconst_2
      // 061d: fconst_1
      // 061e: fconst_2
      // 061f: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0622: dup
      // 0623: fconst_0
      // 0624: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0627: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 062a: ldc_w -2.0E-4
      // 062d: ldc_w -5.5076
      // 0630: ldc_w 0.123
      // 0633: ldc_w 0.1742
      // 0636: ldc_w -0.7816
      // 0639: ldc_w -0.1231
      // 063c: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 063f: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0642: astore 27
      // 0644: aload 22
      // 0646: ldc_w "cube_r12"
      // 0649: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 064c: bipush 125
      // 064e: bipush 80
      // 0650: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0653: ldc_w -5.0
      // 0656: ldc_w -3.0
      // 0659: ldc_w -5.0
      // 065c: ldc 4.0
      // 065e: ldc_w 6.0
      // 0661: ldc 4.0
      // 0663: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0666: dup
      // 0667: fconst_0
      // 0668: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 066b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 066e: fconst_0
      // 066f: ldc_w -6.5
      // 0672: fconst_0
      // 0673: ldc_w 0.1742
      // 0676: ldc_w -0.7816
      // 0679: ldc_w -0.1231
      // 067c: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 067f: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0682: astore 28
      // 0684: aload 22
      // 0686: ldc "l_eye"
      // 0688: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 068b: bipush 23
      // 068d: bipush 84
      // 068f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0692: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0695: ldc_w -1.0
      // 0698: ldc_w -0.5
      // 069b: fconst_0
      // 069c: fconst_2
      // 069d: fconst_1
      // 069e: fconst_0
      // 069f: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 06a2: dup
      // 06a3: fconst_0
      // 06a4: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 06a7: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 06aa: bipush 0
      // 06ab: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 06ae: fconst_2
      // 06af: ldc_w -2.5
      // 06b2: ldc_w -4.1
      // 06b5: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 06b8: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 06bb: astore 29
      // 06bd: aload 22
      // 06bf: ldc "r_eye"
      // 06c1: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 06c4: bipush 23
      // 06c6: bipush 84
      // 06c8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 06cb: ldc_w -1.0
      // 06ce: ldc_w -0.5
      // 06d1: fconst_0
      // 06d2: fconst_2
      // 06d3: fconst_1
      // 06d4: fconst_0
      // 06d5: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 06d8: dup
      // 06d9: fconst_0
      // 06da: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 06dd: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 06e0: ldc -2.0
      // 06e2: ldc_w -2.5
      // 06e5: ldc_w -4.1
      // 06e8: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 06eb: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 06ee: astore 30
      // 06f0: aload 22
      // 06f2: ldc "hair"
      // 06f4: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 06f7: bipush 73
      // 06f9: bipush 0
      // 06fa: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 06fd: ldc_w -9.0
      // 0700: fconst_0
      // 0701: fconst_0
      // 0702: ldc_w 18.0
      // 0705: ldc_w 10.0
      // 0708: fconst_0
      // 0709: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 070c: dup
      // 070d: fconst_0
      // 070e: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0711: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0714: fconst_0
      // 0715: ldc -2.0
      // 0717: ldc 4.0
      // 0719: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 071c: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 071f: astore 31
      // 0721: aload 31
      // 0723: ldc "hair2"
      // 0725: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0728: bipush 0
      // 0729: bipush 39
      // 072b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 072e: ldc_w -12.0
      // 0731: fconst_0
      // 0732: fconst_0
      // 0733: ldc 24.0
      // 0735: ldc_w 13.0
      // 0738: fconst_0
      // 0739: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 073c: dup
      // 073d: fconst_0
      // 073e: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0741: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0744: fconst_0
      // 0745: ldc_w 10.0
      // 0748: fconst_0
      // 0749: ldc_w 0.0436
      // 074c: fconst_0
      // 074d: fconst_0
      // 074e: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0751: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0754: astore 32
      // 0756: aload 17
      // 0758: ldc "l_arm"
      // 075a: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 075d: sipush 129
      // 0760: bipush 11
      // 0762: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0765: ldc -2.0
      // 0767: ldc -2.0
      // 0769: ldc -2.0
      // 076b: ldc_w 3.0
      // 076e: ldc_w 7.0
      // 0771: ldc 4.0
      // 0773: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0776: dup
      // 0777: fconst_0
      // 0778: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 077b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 077e: ldc_w 5.5
      // 0781: ldc_w -4.0
      // 0784: fconst_0
      // 0785: ldc_w -0.5942
      // 0788: ldc_w -0.2039
      // 078b: ldc_w -0.0423
      // 078e: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0791: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0794: astore 33
      // 0796: aload 33
      // 0798: ldc_w "cube_r13"
      // 079b: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 079e: bipush 109
      // 07a0: bipush 57
      // 07a2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 07a5: ldc_w -1.5
      // 07a8: ldc_w -1.5
      // 07ab: ldc_w -0.5
      // 07ae: ldc_w 3.0
      // 07b1: ldc_w 3.0
      // 07b4: fconst_1
      // 07b5: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 07b8: dup
      // 07b9: fconst_0
      // 07ba: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 07bd: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 07c0: ldc_w -0.5
      // 07c3: ldc_w 1.5
      // 07c6: ldc_w 3.5
      // 07c9: ldc_w -3.1416
      // 07cc: fconst_0
      // 07cd: ldc_w 2.3562
      // 07d0: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 07d3: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 07d6: astore 34
      // 07d8: aload 33
      // 07da: ldc "l_armor"
      // 07dc: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 07df: fconst_0
      // 07e0: ldc_w -0.5
      // 07e3: fconst_0
      // 07e4: fconst_0
      // 07e5: fconst_0
      // 07e6: ldc_w 0.3491
      // 07e9: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 07ec: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 07ef: astore 35
      // 07f1: aload 35
      // 07f3: ldc_w "cube_r14"
      // 07f6: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 07f9: bipush 98
      // 07fb: bipush 95
      // 07fd: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0800: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0803: ldc_w -1.5
      // 0806: ldc_w -2.5
      // 0809: ldc_w -3.0
      // 080c: ldc_w 7.0
      // 080f: ldc_w 5.0
      // 0812: ldc_w 6.0
      // 0815: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0818: dup
      // 0819: ldc_w -0.1
      // 081c: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 081f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0822: bipush 0
      // 0823: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0826: bipush 89
      // 0828: bipush 71
      // 082a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 082d: ldc_w -1.5
      // 0830: ldc_w -2.5
      // 0833: ldc_w -3.0
      // 0836: ldc_w 7.0
      // 0839: ldc_w 5.0
      // 083c: ldc_w 6.0
      // 083f: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0842: dup
      // 0843: fconst_0
      // 0844: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0847: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 084a: sipush 131
      // 084d: bipush 0
      // 084e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0851: ldc_w 2.5
      // 0854: ldc_w 1.5
      // 0857: ldc_w -3.0
      // 085a: ldc_w 3.0
      // 085d: fconst_0
      // 085e: ldc_w 6.0
      // 0861: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0864: dup
      // 0865: fconst_0
      // 0866: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0869: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 086c: bipush 98
      // 086e: bipush 83
      // 0870: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0873: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0876: ldc_w -1.5
      // 0879: ldc_w -2.5
      // 087c: ldc_w -3.0
      // 087f: ldc_w 7.0
      // 0882: ldc_w 5.0
      // 0885: ldc_w 6.0
      // 0888: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 088b: dup
      // 088c: ldc_w 0.2
      // 088f: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0892: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0895: bipush 0
      // 0896: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0899: fconst_0
      // 089a: fconst_0
      // 089b: fconst_0
      // 089c: fconst_0
      // 089d: fconst_0
      // 089e: ldc_w -0.1745
      // 08a1: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 08a4: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 08a7: astore 36
      // 08a9: aload 35
      // 08ab: ldc_w "cube_r15"
      // 08ae: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 08b1: bipush 104
      // 08b3: bipush 11
      // 08b5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 08b8: ldc_w -3.0
      // 08bb: ldc_w -2.5
      // 08be: ldc_w -3.0
      // 08c1: ldc_w 6.0
      // 08c4: ldc_w 5.0
      // 08c7: ldc_w 6.0
      // 08ca: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 08cd: dup
      // 08ce: ldc_w -0.2
      // 08d1: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 08d4: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 08d7: ldc_w 2.1203
      // 08da: ldc_w 3.1021
      // 08dd: fconst_0
      // 08de: fconst_0
      // 08df: fconst_0
      // 08e0: ldc_w -0.3054
      // 08e3: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 08e6: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 08e9: astore 37
      // 08eb: aload 35
      // 08ed: ldc_w "cube_r16"
      // 08f0: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 08f3: bipush 78
      // 08f5: bipush 62
      // 08f7: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 08fa: ldc_w -1.5
      // 08fd: ldc_w -1.5
      // 0900: ldc_w -0.5
      // 0903: ldc_w 3.0
      // 0906: ldc_w 3.0
      // 0909: fconst_1
      // 090a: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 090d: dup
      // 090e: fconst_0
      // 090f: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0912: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0915: ldc_w -0.5
      // 0918: fconst_2
      // 0919: ldc_w -3.5
      // 091c: fconst_0
      // 091d: fconst_0
      // 091e: ldc_w -0.7854
      // 0921: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0924: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0927: astore 38
      // 0929: aload 35
      // 092b: ldc "l_sea_snake"
      // 092d: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0930: sipush 131
      // 0933: bipush 23
      // 0935: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0938: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 093b: ldc_w -1.0
      // 093e: ldc_w -1.0
      // 0941: ldc_w -1.0
      // 0944: fconst_2
      // 0945: fconst_2
      // 0946: ldc_w 6.0
      // 0949: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 094c: dup
      // 094d: fconst_0
      // 094e: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0951: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0954: bipush 0
      // 0955: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0958: ldc_w 2.5
      // 095b: ldc_w -0.5
      // 095e: ldc_w 3.0
      // 0961: ldc_w 0.9878
      // 0964: ldc_w 0.0119
      // 0967: ldc_w 1.1603
      // 096a: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 096d: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0970: astore 39
      // 0972: aload 39
      // 0974: ldc "l_sea_snake2"
      // 0976: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0979: sipush 131
      // 097c: bipush 23
      // 097e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0981: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0984: ldc_w -1.0
      // 0987: ldc_w -1.0
      // 098a: fconst_0
      // 098b: fconst_2
      // 098c: fconst_2
      // 098d: ldc_w 6.0
      // 0990: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0993: dup
      // 0994: fconst_0
      // 0995: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0998: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 099b: bipush 0
      // 099c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 099f: fconst_0
      // 09a0: fconst_0
      // 09a1: ldc_w 5.0
      // 09a4: ldc_w 1.0657
      // 09a7: ldc_w -0.5086
      // 09aa: ldc_w 0.263
      // 09ad: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 09b0: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 09b3: astore 40
      // 09b5: aload 40
      // 09b7: ldc "l_sea_snake3"
      // 09b9: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 09bc: sipush 131
      // 09bf: bipush 23
      // 09c1: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 09c4: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 09c7: ldc_w -1.0
      // 09ca: ldc_w -1.0
      // 09cd: fconst_0
      // 09ce: fconst_2
      // 09cf: fconst_2
      // 09d0: ldc_w 6.0
      // 09d3: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 09d6: dup
      // 09d7: fconst_0
      // 09d8: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 09db: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 09de: bipush 0
      // 09df: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 09e2: fconst_0
      // 09e3: fconst_0
      // 09e4: ldc_w 6.0
      // 09e7: ldc_w 0.5876
      // 09ea: ldc_w -0.6222
      // 09ed: ldc_w -0.0195
      // 09f0: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 09f3: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 09f6: astore 41
      // 09f8: aload 41
      // 09fa: ldc "l_sea_snake_head"
      // 09fc: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 09ff: fconst_0
      // 0a00: fconst_0
      // 0a01: ldc_w 6.0
      // 0a04: ldc_w 1.0908
      // 0a07: fconst_0
      // 0a08: fconst_0
      // 0a09: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0a0c: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0a0f: astore 42
      // 0a11: aload 42
      // 0a13: ldc "r_sea_snake_head3"
      // 0a15: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0a18: bipush 92
      // 0a1a: sipush 131
      // 0a1d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0a20: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0a23: ldc_w -1.5
      // 0a26: ldc_w -1.0
      // 0a29: fconst_0
      // 0a2a: ldc_w 3.0
      // 0a2d: fconst_2
      // 0a2e: ldc 4.0
      // 0a30: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0a33: dup
      // 0a34: fconst_0
      // 0a35: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0a38: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0a3b: bipush 0
      // 0a3c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0a3f: fconst_0
      // 0a40: fconst_0
      // 0a41: fconst_0
      // 0a42: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0a45: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0a48: astore 43
      // 0a4a: aload 42
      // 0a4c: ldc "l_sea_snake_tongue"
      // 0a4e: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0a51: sipush 131
      // 0a54: bipush 7
      // 0a56: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0a59: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0a5c: ldc_w -1.5
      // 0a5f: fconst_0
      // 0a60: fconst_0
      // 0a61: ldc_w 3.0
      // 0a64: fconst_0
      // 0a65: ldc_w 3.0
      // 0a68: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0a6b: dup
      // 0a6c: fconst_0
      // 0a6d: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0a70: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0a73: bipush 0
      // 0a74: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0a77: fconst_0
      // 0a78: ldc_w -0.3
      // 0a7b: ldc 4.0
      // 0a7d: ldc_w 0.2618
      // 0a80: fconst_0
      // 0a81: fconst_0
      // 0a82: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0a85: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0a88: astore 44
      // 0a8a: aload 33
      // 0a8c: ldc "l_arm2"
      // 0a8e: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0a91: bipush 66
      // 0a93: sipush 131
      // 0a96: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0a99: ldc_w -0.2389
      // 0a9c: ldc_w -0.9829
      // 0a9f: ldc -2.0
      // 0aa1: fconst_2
      // 0aa2: ldc_w 8.0
      // 0aa5: ldc 4.0
      // 0aa7: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0aaa: dup
      // 0aab: ldc_w 0.3
      // 0aae: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0ab1: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0ab4: bipush 51
      // 0ab6: bipush 123
      // 0ab8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0abb: ldc_w -1.5
      // 0abe: fconst_0
      // 0abf: ldc -2.0
      // 0ac1: ldc_w 3.0
      // 0ac4: ldc 9.0
      // 0ac6: ldc 4.0
      // 0ac8: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0acb: dup
      // 0acc: fconst_0
      // 0acd: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0ad0: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0ad3: fconst_0
      // 0ad4: ldc_w 5.0
      // 0ad7: fconst_0
      // 0ad8: ldc_w -1.6849
      // 0adb: ldc_w -0.1083
      // 0ade: ldc_w 0.2301
      // 0ae1: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0ae4: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0ae7: astore 45
      // 0ae9: aload 45
      // 0aeb: ldc "anchor"
      // 0aed: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0af0: bipush 0
      // 0af1: bipush 0
      // 0af2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0af5: ldc -2.0
      // 0af7: ldc_w -3.0
      // 0afa: ldc_w -23.0
      // 0afd: ldc 4.0
      // 0aff: ldc_w 6.0
      // 0b02: ldc_w 32.0
      // 0b05: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0b08: dup
      // 0b09: ldc_w -0.01
      // 0b0c: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0b0f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0b12: ldc_w -1.5
      // 0b15: ldc_w 8.0
      // 0b18: fconst_0
      // 0b19: ldc_w -0.4363
      // 0b1c: fconst_0
      // 0b1d: fconst_0
      // 0b1e: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0b21: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0b24: astore 46
      // 0b26: aload 46
      // 0b28: ldc_w "cube_r17"
      // 0b2b: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0b2e: bipush 0
      // 0b2f: bipush 85
      // 0b31: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0b34: ldc -2.0
      // 0b36: ldc_w -4.0
      // 0b39: ldc_w -4.0
      // 0b3c: ldc 4.0
      // 0b3e: ldc_w 8.0
      // 0b41: ldc_w 8.0
      // 0b44: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0b47: dup
      // 0b48: fconst_0
      // 0b49: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0b4c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0b4f: fconst_0
      // 0b50: fconst_0
      // 0b51: ldc_w 7.0
      // 0b54: ldc_w -2.3562
      // 0b57: fconst_0
      // 0b58: fconst_0
      // 0b59: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0b5c: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0b5f: astore 47
      // 0b61: aload 46
      // 0b63: ldc_w "cube_r18"
      // 0b66: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0b69: bipush 49
      // 0b6b: bipush 39
      // 0b6d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0b70: fconst_1
      // 0b71: ldc_w -10.0
      // 0b74: ldc_w -10.0
      // 0b77: fconst_0
      // 0b78: ldc_w 14.0
      // 0b7b: ldc_w 14.0
      // 0b7e: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0b81: dup
      // 0b82: fconst_0
      // 0b83: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0b86: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0b89: ldc_w -1.0
      // 0b8c: fconst_0
      // 0b8d: ldc_w 10.0
      // 0b90: ldc_w -2.3562
      // 0b93: fconst_0
      // 0b94: fconst_0
      // 0b95: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0b98: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0b9b: astore 48
      // 0b9d: aload 46
      // 0b9f: ldc_w "cube_r19"
      // 0ba2: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0ba5: bipush 0
      // 0ba6: bipush 102
      // 0ba8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0bab: ldc_w -3.0
      // 0bae: ldc_w -3.0
      // 0bb1: ldc_w -3.0
      // 0bb4: ldc_w 6.0
      // 0bb7: ldc_w 6.0
      // 0bba: ldc_w 6.0
      // 0bbd: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0bc0: dup
      // 0bc1: ldc_w 0.1
      // 0bc4: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0bc7: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0bca: fconst_0
      // 0bcb: fconst_0
      // 0bcc: ldc_w -26.3
      // 0bcf: ldc_w -2.3562
      // 0bd2: fconst_0
      // 0bd3: fconst_0
      // 0bd4: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0bd7: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0bda: astore 49
      // 0bdc: aload 46
      // 0bde: ldc_w "cube_r20"
      // 0be1: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0be4: bipush 78
      // 0be6: bipush 52
      // 0be8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0beb: ldc_w -3.0
      // 0bee: fconst_1
      // 0bef: ldc_w -3.0
      // 0bf2: ldc_w 6.0
      // 0bf5: fconst_0
      // 0bf6: ldc 9.0
      // 0bf8: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0bfb: dup
      // 0bfc: fconst_0
      // 0bfd: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0c00: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0c03: fconst_0
      // 0c04: fconst_1
      // 0c05: ldc_w -30.0
      // 0c08: ldc_w 3.1416
      // 0c0b: fconst_0
      // 0c0c: fconst_0
      // 0c0d: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0c10: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0c13: astore 50
      // 0c15: aload 46
      // 0c17: ldc_w "cube_r21"
      // 0c1a: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0c1d: bipush 107
      // 0c1f: bipush 121
      // 0c21: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0c24: ldc_w -1.0
      // 0c27: ldc_w -3.0
      // 0c2a: ldc_w -3.0
      // 0c2d: fconst_2
      // 0c2e: ldc_w 6.0
      // 0c31: ldc_w 6.0
      // 0c34: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0c37: dup
      // 0c38: fconst_0
      // 0c39: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0c3c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0c3f: fconst_0
      // 0c40: fconst_0
      // 0c41: ldc_w -30.0
      // 0c44: ldc_w 3.1416
      // 0c47: fconst_0
      // 0c48: fconst_0
      // 0c49: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0c4c: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0c4f: astore 51
      // 0c51: aload 46
      // 0c53: ldc_w "cube_r22"
      // 0c56: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0c59: bipush 0
      // 0c5a: bipush 53
      // 0c5c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0c5f: fconst_0
      // 0c60: ldc_w -7.0
      // 0c63: ldc_w -3.0
      // 0c66: fconst_0
      // 0c67: ldc_w 18.0
      // 0c6a: ldc_w 11.0
      // 0c6d: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0c70: dup
      // 0c71: fconst_0
      // 0c72: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0c75: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0c78: fconst_0
      // 0c79: fconst_2
      // 0c7a: ldc_w -30.0
      // 0c7d: ldc_w 3.1416
      // 0c80: fconst_0
      // 0c81: fconst_0
      // 0c82: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0c85: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0c88: astore 52
      // 0c8a: aload 46
      // 0c8c: ldc_w "cube_r23"
      // 0c8f: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0c92: bipush 73
      // 0c94: bipush 104
      // 0c96: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0c99: ldc_w -1.0
      // 0c9c: ldc_w -6.0
      // 0c9f: ldc_w -3.0
      // 0ca2: fconst_2
      // 0ca3: ldc_w 12.0
      // 0ca6: ldc_w 6.0
      // 0ca9: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0cac: dup
      // 0cad: ldc_w 0.001
      // 0cb0: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0cb3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0cb6: fconst_0
      // 0cb7: ldc_w -15.6944
      // 0cba: ldc_w -21.2732
      // 0cbd: ldc_w 2.3998
      // 0cc0: fconst_0
      // 0cc1: fconst_0
      // 0cc2: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0cc5: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0cc8: astore 53
      // 0cca: aload 46
      // 0ccc: ldc_w "cube_r24"
      // 0ccf: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0cd2: bipush 77
      // 0cd4: bipush 85
      // 0cd6: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0cd9: ldc -2.0
      // 0cdb: ldc_w -1.0
      // 0cde: ldc_w -3.0
      // 0ce1: ldc 4.0
      // 0ce3: ldc_w 12.0
      // 0ce6: ldc_w 6.0
      // 0ce9: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0cec: dup
      // 0ced: fconst_0
      // 0cee: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0cf1: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0cf4: fconst_0
      // 0cf5: ldc -2.0
      // 0cf7: ldc_w -26.0
      // 0cfa: ldc_w 3.0107
      // 0cfd: fconst_0
      // 0cfe: fconst_0
      // 0cff: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0d02: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0d05: astore 54
      // 0d07: aload 46
      // 0d09: ldc_w "cube_r25"
      // 0d0c: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0d0f: bipush 73
      // 0d11: bipush 104
      // 0d13: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0d16: ldc_w -1.0
      // 0d19: ldc_w -6.0
      // 0d1c: ldc_w -3.0
      // 0d1f: fconst_2
      // 0d20: ldc_w 12.0
      // 0d23: ldc_w 6.0
      // 0d26: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0d29: dup
      // 0d2a: ldc_w 0.001
      // 0d2d: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0d30: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0d33: fconst_0
      // 0d34: ldc_w 15.6944
      // 0d37: ldc_w -21.2732
      // 0d3a: ldc_w 2.3998
      // 0d3d: fconst_0
      // 0d3e: ldc_w 3.1416
      // 0d41: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0d44: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0d47: astore 55
      // 0d49: aload 46
      // 0d4b: ldc_w "cube_r26"
      // 0d4e: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0d51: bipush 77
      // 0d53: bipush 85
      // 0d55: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0d58: ldc -2.0
      // 0d5a: ldc_w -1.0
      // 0d5d: ldc_w -3.0
      // 0d60: ldc 4.0
      // 0d62: ldc_w 12.0
      // 0d65: ldc_w 6.0
      // 0d68: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0d6b: dup
      // 0d6c: fconst_0
      // 0d6d: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0d70: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0d73: fconst_0
      // 0d74: fconst_2
      // 0d75: ldc_w -26.0
      // 0d78: ldc_w 3.0107
      // 0d7b: fconst_0
      // 0d7c: ldc_w 3.1416
      // 0d7f: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0d82: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0d85: astore 56
      // 0d87: aload 46
      // 0d89: ldc_w "cube_r27"
      // 0d8c: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0d8f: bipush 125
      // 0d91: bipush 91
      // 0d93: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0d96: ldc_w -1.0
      // 0d99: ldc_w -5.0
      // 0d9c: ldc -2.0
      // 0d9e: fconst_2
      // 0d9f: ldc_w 10.0
      // 0da2: ldc 4.0
      // 0da4: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0da7: dup
      // 0da8: fconst_0
      // 0da9: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0dac: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0daf: fconst_0
      // 0db0: ldc_w -3.0963
      // 0db3: ldc_w -22.4693
      // 0db6: ldc_w 2.7925
      // 0db9: fconst_0
      // 0dba: ldc_w -3.1416
      // 0dbd: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0dc0: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0dc3: astore 57
      // 0dc5: aload 46
      // 0dc7: ldc_w "cube_r28"
      // 0dca: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0dcd: bipush 125
      // 0dcf: bipush 91
      // 0dd1: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0dd4: ldc_w -1.0
      // 0dd7: ldc_w -5.0
      // 0dda: ldc -2.0
      // 0ddc: fconst_2
      // 0ddd: ldc_w 10.0
      // 0de0: ldc 4.0
      // 0de2: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0de5: dup
      // 0de6: fconst_0
      // 0de7: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0dea: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0ded: fconst_0
      // 0dee: ldc_w 3.0963
      // 0df1: ldc_w -22.4693
      // 0df4: ldc_w 2.7925
      // 0df7: fconst_0
      // 0df8: fconst_0
      // 0df9: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0dfc: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0dff: astore 58
      // 0e01: aload 46
      // 0e03: ldc_w "cube_r29"
      // 0e06: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0e09: bipush 23
      // 0e0b: bipush 53
      // 0e0d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0e10: ldc_w -1.0
      // 0e13: ldc -2.0
      // 0e15: ldc_w -5.0
      // 0e18: fconst_2
      // 0e19: ldc 4.0
      // 0e1b: ldc_w 10.0
      // 0e1e: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0e21: dup
      // 0e22: fconst_0
      // 0e23: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0e26: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0e29: bipush 0
      // 0e2a: sipush 131
      // 0e2d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0e30: ldc_w -1.0
      // 0e33: ldc_w -1.0
      // 0e36: ldc_w -11.0
      // 0e39: fconst_2
      // 0e3a: fconst_2
      // 0e3b: ldc_w 6.0
      // 0e3e: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0e41: dup
      // 0e42: fconst_0
      // 0e43: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0e46: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0e49: bipush 25
      // 0e4b: bipush 85
      // 0e4d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0e50: fconst_0
      // 0e51: ldc_w -5.0
      // 0e54: ldc_w -13.0
      // 0e57: fconst_0
      // 0e58: ldc_w 10.0
      // 0e5b: ldc_w 10.0
      // 0e5e: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0e61: dup
      // 0e62: fconst_0
      // 0e63: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0e66: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0e69: fconst_0
      // 0e6a: ldc_w -19.5093
      // 0e6d: ldc_w -14.7353
      // 0e70: ldc_w 3.0107
      // 0e73: fconst_0
      // 0e74: fconst_0
      // 0e75: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0e78: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0e7b: astore 59
      // 0e7d: aload 46
      // 0e7f: ldc_w "cube_r30"
      // 0e82: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0e85: bipush 0
      // 0e86: sipush 131
      // 0e89: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0e8c: ldc_w -1.0
      // 0e8f: ldc_w -1.0
      // 0e92: ldc_w -11.0
      // 0e95: fconst_2
      // 0e96: fconst_2
      // 0e97: ldc_w 6.0
      // 0e9a: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0e9d: dup
      // 0e9e: fconst_0
      // 0e9f: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0ea2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0ea5: fconst_0
      // 0ea6: ldc_w 19.5093
      // 0ea9: ldc_w -14.7353
      // 0eac: ldc_w -3.0107
      // 0eaf: fconst_0
      // 0eb0: fconst_0
      // 0eb1: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0eb4: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0eb7: astore 60
      // 0eb9: aload 46
      // 0ebb: ldc_w "cube_r31"
      // 0ebe: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0ec1: bipush 25
      // 0ec3: bipush 85
      // 0ec5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0ec8: fconst_0
      // 0ec9: ldc_w -5.0
      // 0ecc: ldc_w -5.0
      // 0ecf: fconst_0
      // 0ed0: ldc_w 10.0
      // 0ed3: ldc_w 10.0
      // 0ed6: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0ed9: dup
      // 0eda: fconst_0
      // 0edb: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0ede: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0ee1: fconst_0
      // 0ee2: ldc_w 18.4651
      // 0ee5: ldc_w -6.8037
      // 0ee8: ldc_w 3.0107
      // 0eeb: fconst_0
      // 0eec: ldc_w 3.1416
      // 0eef: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0ef2: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0ef5: astore 61
      // 0ef7: aload 46
      // 0ef9: ldc_w "cube_r32"
      // 0efc: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0eff: bipush 23
      // 0f01: bipush 53
      // 0f03: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0f06: ldc_w -1.0
      // 0f09: ldc -2.0
      // 0f0b: ldc_w -5.0
      // 0f0e: fconst_2
      // 0f0f: ldc 4.0
      // 0f11: ldc_w 10.0
      // 0f14: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0f17: dup
      // 0f18: fconst_0
      // 0f19: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0f1c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0f1f: fconst_0
      // 0f20: ldc_w 19.5093
      // 0f23: ldc_w -14.7353
      // 0f26: ldc_w 3.0107
      // 0f29: fconst_0
      // 0f2a: ldc_w 3.1416
      // 0f2d: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0f30: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0f33: astore 62
      // 0f35: aload 46
      // 0f37: ldc "trail2"
      // 0f39: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0f3c: fconst_0
      // 0f3d: fconst_0
      // 0f3e: ldc_w -29.0
      // 0f41: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0f44: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0f47: astore 63
      // 0f49: aload 45
      // 0f4b: ldc "chain_main"
      // 0f4d: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0f50: sipush 134
      // 0f53: bipush 13
      // 0f55: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0f58: ldc_w -3.0
      // 0f5b: fconst_0
      // 0f5c: ldc_w -8.0
      // 0f5f: ldc_w 6.0
      // 0f62: fconst_0
      // 0f63: ldc 9.0
      // 0f65: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0f68: dup
      // 0f69: fconst_0
      // 0f6a: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0f6d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0f70: fconst_0
      // 0f71: ldc_w 8.0
      // 0f74: fconst_0
      // 0f75: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0f78: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0f7b: astore 64
      // 0f7d: aload 64
      // 0f7f: ldc "chain_4"
      // 0f81: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0f84: sipush 149
      // 0f87: bipush 13
      // 0f89: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0f8c: fconst_0
      // 0f8d: ldc_w -3.0
      // 0f90: ldc_w -7.0
      // 0f93: fconst_0
      // 0f94: ldc_w 6.0
      // 0f97: ldc 9.0
      // 0f99: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0f9c: dup
      // 0f9d: fconst_0
      // 0f9e: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0fa1: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0fa4: fconst_0
      // 0fa5: fconst_0
      // 0fa6: ldc_w -8.0
      // 0fa9: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0fac: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0faf: astore 65
      // 0fb1: aload 65
      // 0fb3: ldc "chain_3"
      // 0fb5: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0fb8: sipush 134
      // 0fbb: bipush 13
      // 0fbd: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0fc0: ldc_w -3.0
      // 0fc3: fconst_0
      // 0fc4: ldc_w -7.8
      // 0fc7: ldc_w 6.0
      // 0fca: fconst_0
      // 0fcb: ldc 9.0
      // 0fcd: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 0fd0: dup
      // 0fd1: fconst_0
      // 0fd2: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 0fd5: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0fd8: fconst_0
      // 0fd9: fconst_0
      // 0fda: ldc_w -6.2
      // 0fdd: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 0fe0: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 0fe3: astore 66
      // 0fe5: aload 66
      // 0fe7: ldc "chain_2"
      // 0fe9: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0fec: sipush 149
      // 0fef: bipush 13
      // 0ff1: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 0ff4: fconst_0
      // 0ff5: ldc_w -3.0
      // 0ff8: ldc_w -8.0
      // 0ffb: fconst_0
      // 0ffc: ldc_w 6.0
      // 0fff: ldc 9.0
      // 1001: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1004: dup
      // 1005: fconst_0
      // 1006: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1009: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 100c: fconst_0
      // 100d: fconst_0
      // 100e: ldc_w -6.8
      // 1011: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1014: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1017: astore 67
      // 1019: aload 67
      // 101b: ldc "chain"
      // 101d: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1020: sipush 134
      // 1023: bipush 13
      // 1025: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1028: ldc_w -3.0
      // 102b: fconst_0
      // 102c: ldc_w -8.0
      // 102f: ldc_w 6.0
      // 1032: fconst_0
      // 1033: ldc 9.0
      // 1035: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1038: dup
      // 1039: fconst_0
      // 103a: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 103d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1040: fconst_0
      // 1041: fconst_0
      // 1042: ldc_w -7.0
      // 1045: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1048: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 104b: astore 68
      // 104d: aload 68
      // 104f: ldc "chain_anchor"
      // 1051: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1054: bipush 0
      // 1055: bipush 0
      // 1056: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1059: ldc -2.0
      // 105b: ldc_w -3.0
      // 105e: ldc_w -43.0
      // 1061: ldc 4.0
      // 1063: ldc_w 6.0
      // 1066: ldc_w 32.0
      // 1069: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 106c: dup
      // 106d: ldc_w -0.01
      // 1070: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1073: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1076: fconst_0
      // 1077: fconst_0
      // 1078: ldc_w -7.0
      // 107b: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 107e: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1081: astore 69
      // 1083: aload 69
      // 1085: ldc_w "cube_r33"
      // 1088: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 108b: bipush 0
      // 108c: bipush 102
      // 108e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1091: ldc_w -3.0
      // 1094: ldc_w -3.0
      // 1097: ldc_w -3.0
      // 109a: ldc_w 6.0
      // 109d: ldc_w 6.0
      // 10a0: ldc_w 6.0
      // 10a3: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 10a6: dup
      // 10a7: ldc_w 0.1
      // 10aa: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 10ad: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 10b0: fconst_0
      // 10b1: fconst_0
      // 10b2: ldc_w -46.3
      // 10b5: ldc_w -2.3562
      // 10b8: fconst_0
      // 10b9: fconst_0
      // 10ba: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 10bd: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 10c0: astore 70
      // 10c2: aload 69
      // 10c4: ldc_w "cube_r34"
      // 10c7: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 10ca: bipush 0
      // 10cb: bipush 85
      // 10cd: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 10d0: ldc -2.0
      // 10d2: ldc_w -4.0
      // 10d5: ldc_w -4.0
      // 10d8: ldc 4.0
      // 10da: ldc_w 8.0
      // 10dd: ldc_w 8.0
      // 10e0: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 10e3: dup
      // 10e4: fconst_0
      // 10e5: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 10e8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 10eb: fconst_0
      // 10ec: fconst_0
      // 10ed: ldc_w -13.0
      // 10f0: ldc_w -2.3562
      // 10f3: fconst_0
      // 10f4: fconst_0
      // 10f5: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 10f8: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 10fb: astore 71
      // 10fd: aload 69
      // 10ff: ldc_w "cube_r35"
      // 1102: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1105: bipush 49
      // 1107: bipush 39
      // 1109: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 110c: fconst_1
      // 110d: ldc_w -10.0
      // 1110: ldc_w -10.0
      // 1113: fconst_0
      // 1114: ldc_w 14.0
      // 1117: ldc_w 14.0
      // 111a: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 111d: dup
      // 111e: fconst_0
      // 111f: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1122: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1125: ldc_w -1.0
      // 1128: fconst_0
      // 1129: ldc_w -10.0
      // 112c: ldc_w -2.3562
      // 112f: fconst_0
      // 1130: fconst_0
      // 1131: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1134: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1137: astore 72
      // 1139: aload 69
      // 113b: ldc_w "cube_r36"
      // 113e: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1141: bipush 78
      // 1143: bipush 52
      // 1145: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1148: ldc_w -3.0
      // 114b: fconst_1
      // 114c: ldc_w -3.0
      // 114f: ldc_w 6.0
      // 1152: fconst_0
      // 1153: ldc 9.0
      // 1155: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1158: dup
      // 1159: fconst_0
      // 115a: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 115d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1160: fconst_0
      // 1161: fconst_1
      // 1162: ldc_w -50.0
      // 1165: ldc_w 3.1416
      // 1168: fconst_0
      // 1169: fconst_0
      // 116a: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 116d: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1170: astore 73
      // 1172: aload 69
      // 1174: ldc_w "cube_r37"
      // 1177: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 117a: bipush 107
      // 117c: bipush 121
      // 117e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1181: ldc_w -1.0
      // 1184: ldc_w -3.0
      // 1187: ldc_w -3.0
      // 118a: fconst_2
      // 118b: ldc_w 6.0
      // 118e: ldc_w 6.0
      // 1191: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1194: dup
      // 1195: fconst_0
      // 1196: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1199: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 119c: fconst_0
      // 119d: fconst_0
      // 119e: ldc_w -50.0
      // 11a1: ldc_w 3.1416
      // 11a4: fconst_0
      // 11a5: fconst_0
      // 11a6: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 11a9: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 11ac: astore 74
      // 11ae: aload 69
      // 11b0: ldc_w "cube_r38"
      // 11b3: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 11b6: bipush 0
      // 11b7: bipush 53
      // 11b9: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 11bc: fconst_0
      // 11bd: ldc_w -7.0
      // 11c0: ldc_w -3.0
      // 11c3: fconst_0
      // 11c4: ldc_w 18.0
      // 11c7: ldc_w 11.0
      // 11ca: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 11cd: dup
      // 11ce: fconst_0
      // 11cf: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 11d2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 11d5: fconst_0
      // 11d6: fconst_2
      // 11d7: ldc_w -50.0
      // 11da: ldc_w 3.1416
      // 11dd: fconst_0
      // 11de: fconst_0
      // 11df: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 11e2: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 11e5: astore 75
      // 11e7: aload 69
      // 11e9: ldc_w "cube_r39"
      // 11ec: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 11ef: bipush 73
      // 11f1: bipush 104
      // 11f3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 11f6: ldc_w -1.0
      // 11f9: ldc_w -6.0
      // 11fc: ldc_w -3.0
      // 11ff: fconst_2
      // 1200: ldc_w 12.0
      // 1203: ldc_w 6.0
      // 1206: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1209: dup
      // 120a: ldc_w 0.001
      // 120d: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1210: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1213: fconst_0
      // 1214: ldc_w -15.6944
      // 1217: ldc_w -41.2732
      // 121a: ldc_w 2.3998
      // 121d: fconst_0
      // 121e: fconst_0
      // 121f: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1222: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1225: astore 76
      // 1227: aload 69
      // 1229: ldc_w "cube_r40"
      // 122c: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 122f: bipush 77
      // 1231: bipush 85
      // 1233: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1236: ldc -2.0
      // 1238: ldc_w -1.0
      // 123b: ldc_w -3.0
      // 123e: ldc 4.0
      // 1240: ldc_w 12.0
      // 1243: ldc_w 6.0
      // 1246: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1249: dup
      // 124a: fconst_0
      // 124b: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 124e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1251: fconst_0
      // 1252: ldc -2.0
      // 1254: ldc_w -46.0
      // 1257: ldc_w 3.0107
      // 125a: fconst_0
      // 125b: fconst_0
      // 125c: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 125f: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1262: astore 77
      // 1264: aload 69
      // 1266: ldc_w "cube_r41"
      // 1269: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 126c: bipush 73
      // 126e: bipush 104
      // 1270: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1273: ldc_w -1.0
      // 1276: ldc_w -6.0
      // 1279: ldc_w -3.0
      // 127c: fconst_2
      // 127d: ldc_w 12.0
      // 1280: ldc_w 6.0
      // 1283: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1286: dup
      // 1287: ldc_w 0.001
      // 128a: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 128d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1290: fconst_0
      // 1291: ldc_w 15.6944
      // 1294: ldc_w -41.2732
      // 1297: ldc_w 2.3998
      // 129a: fconst_0
      // 129b: ldc_w 3.1416
      // 129e: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 12a1: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 12a4: astore 78
      // 12a6: aload 69
      // 12a8: ldc_w "cube_r42"
      // 12ab: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 12ae: bipush 77
      // 12b0: bipush 85
      // 12b2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 12b5: ldc -2.0
      // 12b7: ldc_w -1.0
      // 12ba: ldc_w -3.0
      // 12bd: ldc 4.0
      // 12bf: ldc_w 12.0
      // 12c2: ldc_w 6.0
      // 12c5: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 12c8: dup
      // 12c9: fconst_0
      // 12ca: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 12cd: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 12d0: fconst_0
      // 12d1: fconst_2
      // 12d2: ldc_w -46.0
      // 12d5: ldc_w 3.0107
      // 12d8: fconst_0
      // 12d9: ldc_w 3.1416
      // 12dc: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 12df: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 12e2: astore 79
      // 12e4: aload 69
      // 12e6: ldc_w "cube_r43"
      // 12e9: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 12ec: bipush 125
      // 12ee: bipush 91
      // 12f0: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 12f3: ldc_w -1.0
      // 12f6: ldc_w -5.0
      // 12f9: ldc -2.0
      // 12fb: fconst_2
      // 12fc: ldc_w 10.0
      // 12ff: ldc 4.0
      // 1301: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1304: dup
      // 1305: fconst_0
      // 1306: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1309: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 130c: fconst_0
      // 130d: ldc_w -3.0963
      // 1310: ldc_w -42.4693
      // 1313: ldc_w 2.7925
      // 1316: fconst_0
      // 1317: ldc_w -3.1416
      // 131a: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 131d: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1320: astore 80
      // 1322: aload 69
      // 1324: ldc_w "cube_r44"
      // 1327: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 132a: bipush 125
      // 132c: bipush 91
      // 132e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1331: ldc_w -1.0
      // 1334: ldc_w -5.0
      // 1337: ldc -2.0
      // 1339: fconst_2
      // 133a: ldc_w 10.0
      // 133d: ldc 4.0
      // 133f: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1342: dup
      // 1343: fconst_0
      // 1344: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1347: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 134a: fconst_0
      // 134b: ldc_w 3.0963
      // 134e: ldc_w -42.4693
      // 1351: ldc_w 2.7925
      // 1354: fconst_0
      // 1355: fconst_0
      // 1356: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1359: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 135c: astore 81
      // 135e: aload 69
      // 1360: ldc_w "cube_r45"
      // 1363: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1366: bipush 23
      // 1368: bipush 53
      // 136a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 136d: ldc_w -1.0
      // 1370: ldc -2.0
      // 1372: ldc_w -5.0
      // 1375: fconst_2
      // 1376: ldc 4.0
      // 1378: ldc_w 10.0
      // 137b: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 137e: dup
      // 137f: fconst_0
      // 1380: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1383: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1386: bipush 0
      // 1387: sipush 131
      // 138a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 138d: ldc_w -1.0
      // 1390: ldc_w -1.0
      // 1393: ldc_w -11.0
      // 1396: fconst_2
      // 1397: fconst_2
      // 1398: ldc_w 6.0
      // 139b: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 139e: dup
      // 139f: fconst_0
      // 13a0: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 13a3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 13a6: bipush 25
      // 13a8: bipush 85
      // 13aa: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 13ad: fconst_0
      // 13ae: ldc_w -5.0
      // 13b1: ldc_w -13.0
      // 13b4: fconst_0
      // 13b5: ldc_w 10.0
      // 13b8: ldc_w 10.0
      // 13bb: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 13be: dup
      // 13bf: fconst_0
      // 13c0: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 13c3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 13c6: fconst_0
      // 13c7: ldc_w -19.5093
      // 13ca: ldc_w -34.7353
      // 13cd: ldc_w 3.0107
      // 13d0: fconst_0
      // 13d1: fconst_0
      // 13d2: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 13d5: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 13d8: astore 82
      // 13da: aload 69
      // 13dc: ldc_w "cube_r46"
      // 13df: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 13e2: bipush 0
      // 13e3: sipush 131
      // 13e6: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 13e9: ldc_w -1.0
      // 13ec: ldc_w -1.0
      // 13ef: ldc_w -11.0
      // 13f2: fconst_2
      // 13f3: fconst_2
      // 13f4: ldc_w 6.0
      // 13f7: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 13fa: dup
      // 13fb: fconst_0
      // 13fc: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 13ff: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1402: fconst_0
      // 1403: ldc_w 19.5093
      // 1406: ldc_w -34.7353
      // 1409: ldc_w -3.0107
      // 140c: fconst_0
      // 140d: fconst_0
      // 140e: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1411: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1414: astore 83
      // 1416: aload 69
      // 1418: ldc_w "cube_r47"
      // 141b: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 141e: bipush 25
      // 1420: bipush 85
      // 1422: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1425: fconst_0
      // 1426: ldc_w -5.0
      // 1429: ldc_w -5.0
      // 142c: fconst_0
      // 142d: ldc_w 10.0
      // 1430: ldc_w 10.0
      // 1433: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1436: dup
      // 1437: fconst_0
      // 1438: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 143b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 143e: fconst_0
      // 143f: ldc_w 18.4651
      // 1442: ldc_w -26.8037
      // 1445: ldc_w 3.0107
      // 1448: fconst_0
      // 1449: ldc_w 3.1416
      // 144c: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 144f: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1452: astore 84
      // 1454: aload 69
      // 1456: ldc_w "cube_r48"
      // 1459: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 145c: bipush 23
      // 145e: bipush 53
      // 1460: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1463: ldc_w -1.0
      // 1466: ldc -2.0
      // 1468: ldc_w -5.0
      // 146b: fconst_2
      // 146c: ldc 4.0
      // 146e: ldc_w 10.0
      // 1471: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1474: dup
      // 1475: fconst_0
      // 1476: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1479: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 147c: fconst_0
      // 147d: ldc_w 19.5093
      // 1480: ldc_w -34.7353
      // 1483: ldc_w 3.0107
      // 1486: fconst_0
      // 1487: ldc_w 3.1416
      // 148a: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 148d: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1490: astore 85
      // 1492: aload 69
      // 1494: ldc "trail"
      // 1496: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1499: fconst_0
      // 149a: fconst_0
      // 149b: ldc_w -49.0
      // 149e: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 14a1: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 14a4: astore 86
      // 14a6: aload 17
      // 14a8: ldc "r_arm"
      // 14aa: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 14ad: bipush 17
      // 14af: sipush 130
      // 14b2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 14b5: ldc_w -1.0
      // 14b8: ldc -2.0
      // 14ba: ldc -2.0
      // 14bc: ldc_w 3.0
      // 14bf: ldc_w 7.0
      // 14c2: ldc 4.0
      // 14c4: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 14c7: dup
      // 14c8: fconst_0
      // 14c9: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 14cc: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 14cf: ldc_w -5.5
      // 14d2: ldc_w -4.0
      // 14d5: fconst_0
      // 14d6: fconst_0
      // 14d7: fconst_0
      // 14d8: ldc_w 0.1309
      // 14db: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 14de: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 14e1: astore 87
      // 14e3: aload 87
      // 14e5: ldc "r_armor"
      // 14e7: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 14ea: ldc_w 0.5
      // 14ed: ldc_w -0.5
      // 14f0: fconst_0
      // 14f1: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 14f4: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 14f7: astore 88
      // 14f9: aload 88
      // 14fb: ldc_w "cube_r49"
      // 14fe: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1501: bipush 78
      // 1503: bipush 62
      // 1505: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1508: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 150b: ldc_w -1.5
      // 150e: ldc_w -1.5
      // 1511: ldc_w -0.5
      // 1514: ldc_w 3.0
      // 1517: ldc_w 3.0
      // 151a: fconst_1
      // 151b: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 151e: dup
      // 151f: fconst_0
      // 1520: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1523: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1526: bipush 0
      // 1527: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 152a: fconst_0
      // 152b: fconst_2
      // 152c: ldc_w -3.5
      // 152f: fconst_0
      // 1530: fconst_0
      // 1531: ldc_w 0.7854
      // 1534: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1537: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 153a: astore 89
      // 153c: aload 88
      // 153e: ldc_w "cube_r50"
      // 1541: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1544: bipush 109
      // 1546: bipush 57
      // 1548: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 154b: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 154e: ldc_w -1.5
      // 1551: ldc_w -1.5
      // 1554: ldc_w -0.5
      // 1557: ldc_w 3.0
      // 155a: ldc_w 3.0
      // 155d: fconst_1
      // 155e: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1561: dup
      // 1562: fconst_0
      // 1563: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1566: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1569: bipush 0
      // 156a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 156d: fconst_0
      // 156e: fconst_2
      // 156f: ldc_w 3.5
      // 1572: ldc_w -3.1416
      // 1575: fconst_0
      // 1576: ldc_w -2.3562
      // 1579: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 157c: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 157f: astore 90
      // 1581: aload 88
      // 1583: ldc_w "cube_r51"
      // 1586: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1589: bipush 46
      // 158b: bipush 95
      // 158d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1590: ldc_w -5.5
      // 1593: ldc_w -2.5
      // 1596: ldc_w -3.0
      // 1599: ldc_w 7.0
      // 159c: ldc_w 5.0
      // 159f: ldc_w 6.0
      // 15a2: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 15a5: dup
      // 15a6: fconst_0
      // 15a7: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 15aa: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 15ad: sipush 131
      // 15b0: bipush 0
      // 15b1: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 15b4: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 15b7: ldc_w -5.5
      // 15ba: ldc_w 1.5
      // 15bd: ldc_w -3.0
      // 15c0: ldc_w 3.0
      // 15c3: fconst_0
      // 15c4: ldc_w 6.0
      // 15c7: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 15ca: dup
      // 15cb: fconst_0
      // 15cc: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 15cf: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 15d2: bipush 0
      // 15d3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 15d6: bipush 98
      // 15d8: bipush 95
      // 15da: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 15dd: ldc_w -5.5
      // 15e0: ldc_w -2.5
      // 15e3: ldc_w -3.0
      // 15e6: ldc_w 7.0
      // 15e9: ldc_w 5.0
      // 15ec: ldc_w 6.0
      // 15ef: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 15f2: dup
      // 15f3: ldc_w -0.1
      // 15f6: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 15f9: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 15fc: bipush 98
      // 15fe: bipush 83
      // 1600: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1603: ldc_w -5.5
      // 1606: ldc_w -2.5
      // 1609: ldc_w -3.0
      // 160c: ldc_w 7.0
      // 160f: ldc_w 5.0
      // 1612: ldc_w 6.0
      // 1615: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1618: dup
      // 1619: ldc_w 0.2
      // 161c: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 161f: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1622: ldc_w -0.5
      // 1625: fconst_0
      // 1626: fconst_0
      // 1627: fconst_0
      // 1628: fconst_0
      // 1629: ldc_w 0.1745
      // 162c: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 162f: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1632: astore 91
      // 1634: aload 88
      // 1636: ldc_w "cube_r52"
      // 1639: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 163c: bipush 104
      // 163e: bipush 11
      // 1640: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1643: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1646: ldc_w -3.0
      // 1649: ldc_w -2.5
      // 164c: ldc_w -3.0
      // 164f: ldc_w 6.0
      // 1652: ldc_w 5.0
      // 1655: ldc_w 6.0
      // 1658: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 165b: dup
      // 165c: ldc_w -0.2
      // 165f: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1662: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1665: bipush 0
      // 1666: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.mirror (Z)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1669: ldc_w -2.6203
      // 166c: ldc_w 3.1021
      // 166f: fconst_0
      // 1670: fconst_0
      // 1671: fconst_0
      // 1672: ldc_w 0.3054
      // 1675: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1678: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 167b: astore 92
      // 167d: aload 87
      // 167f: ldc "r_arm2"
      // 1681: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1684: bipush 79
      // 1686: sipush 131
      // 1689: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 168c: ldc_w -1.7611
      // 168f: ldc_w -0.9829
      // 1692: ldc -2.0
      // 1694: fconst_2
      // 1695: ldc_w 8.0
      // 1698: ldc 4.0
      // 169a: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 169d: dup
      // 169e: ldc_w 0.3
      // 16a1: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 16a4: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 16a7: bipush 124
      // 16a9: bipush 121
      // 16ab: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 16ae: ldc_w -1.5
      // 16b1: fconst_0
      // 16b2: ldc -2.0
      // 16b4: ldc_w 3.0
      // 16b7: ldc 9.0
      // 16b9: ldc 4.0
      // 16bb: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 16be: dup
      // 16bf: fconst_0
      // 16c0: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 16c3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 16c6: fconst_0
      // 16c7: ldc_w 5.0
      // 16ca: fconst_0
      // 16cb: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 16ce: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 16d1: astore 93
      // 16d3: aload 87
      // 16d5: ldc "r_sea_snake"
      // 16d7: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 16da: sipush 131
      // 16dd: bipush 23
      // 16df: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 16e2: ldc_w -1.0
      // 16e5: ldc_w -1.0
      // 16e8: ldc_w -1.0
      // 16eb: fconst_2
      // 16ec: fconst_2
      // 16ed: ldc_w 6.0
      // 16f0: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 16f3: dup
      // 16f4: fconst_0
      // 16f5: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 16f8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 16fb: ldc_w -2.5
      // 16fe: ldc_w -1.0
      // 1701: ldc_w 3.0
      // 1704: ldc_w 0.8745
      // 1707: ldc_w -0.039
      // 170a: ldc_w -0.5504
      // 170d: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1710: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1713: astore 94
      // 1715: aload 94
      // 1717: ldc "r_sea_snake2"
      // 1719: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 171c: sipush 131
      // 171f: bipush 23
      // 1721: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1724: ldc_w -1.0
      // 1727: ldc_w -1.0
      // 172a: fconst_0
      // 172b: fconst_2
      // 172c: fconst_2
      // 172d: ldc_w 6.0
      // 1730: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1733: dup
      // 1734: fconst_0
      // 1735: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1738: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 173b: fconst_0
      // 173c: fconst_0
      // 173d: ldc_w 5.0
      // 1740: ldc_w 1.1345
      // 1743: fconst_0
      // 1744: fconst_0
      // 1745: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1748: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 174b: astore 95
      // 174d: aload 95
      // 174f: ldc "r_sea_snake3"
      // 1751: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1754: sipush 131
      // 1757: bipush 23
      // 1759: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 175c: ldc_w -1.0
      // 175f: ldc_w -1.0
      // 1762: fconst_0
      // 1763: fconst_2
      // 1764: fconst_2
      // 1765: ldc_w 6.0
      // 1768: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 176b: dup
      // 176c: fconst_0
      // 176d: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1770: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1773: fconst_0
      // 1774: fconst_0
      // 1775: ldc_w 6.0
      // 1778: ldc_w 0.829
      // 177b: fconst_0
      // 177c: fconst_0
      // 177d: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1780: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1783: astore 96
      // 1785: aload 96
      // 1787: ldc "r_sea_snake_head"
      // 1789: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 178c: fconst_0
      // 178d: fconst_0
      // 178e: ldc_w 6.0
      // 1791: ldc_w 0.7854
      // 1794: fconst_0
      // 1795: fconst_0
      // 1796: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1799: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 179c: astore 97
      // 179e: aload 97
      // 17a0: ldc "r_sea_snake_tongue"
      // 17a2: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 17a5: sipush 131
      // 17a8: bipush 7
      // 17aa: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 17ad: ldc_w -1.5
      // 17b0: fconst_0
      // 17b1: fconst_0
      // 17b2: ldc_w 3.0
      // 17b5: fconst_0
      // 17b6: ldc_w 3.0
      // 17b9: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 17bc: dup
      // 17bd: fconst_0
      // 17be: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 17c1: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 17c4: fconst_0
      // 17c5: ldc_w -0.3
      // 17c8: ldc 4.0
      // 17ca: ldc_w 0.2618
      // 17cd: fconst_0
      // 17ce: fconst_0
      // 17cf: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 17d2: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 17d5: astore 98
      // 17d7: aload 97
      // 17d9: ldc "r_sea_snake_head2"
      // 17db: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 17de: bipush 92
      // 17e0: sipush 131
      // 17e3: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 17e6: ldc_w -1.5
      // 17e9: ldc_w -1.0
      // 17ec: fconst_0
      // 17ed: ldc_w 3.0
      // 17f0: fconst_2
      // 17f1: ldc 4.0
      // 17f3: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 17f6: dup
      // 17f7: fconst_0
      // 17f8: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 17fb: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 17fe: fconst_0
      // 17ff: fconst_0
      // 1800: fconst_0
      // 1801: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1804: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1807: astore 99
      // 1809: aload 3
      // 180a: ldc "l_leg"
      // 180c: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 180f: bipush 120
      // 1811: bipush 52
      // 1813: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1816: ldc -2.0
      // 1818: fconst_0
      // 1819: ldc -2.0
      // 181b: ldc 4.0
      // 181d: ldc 9.0
      // 181f: ldc 4.0
      // 1821: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1824: dup
      // 1825: fconst_0
      // 1826: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1829: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 182c: fconst_2
      // 182d: ldc_w -20.0
      // 1830: fconst_0
      // 1831: fconst_0
      // 1832: ldc_w -0.0436
      // 1835: ldc_w -0.0436
      // 1838: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 183b: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 183e: astore 100
      // 1840: aload 100
      // 1842: ldc_w "cube_r53"
      // 1845: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1848: bipush 109
      // 184a: bipush 43
      // 184c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 184f: ldc_w -3.0
      // 1852: ldc_w -1.0
      // 1855: ldc_w -3.0
      // 1858: ldc_w 6.0
      // 185b: fconst_2
      // 185c: ldc_w 6.0
      // 185f: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1862: dup
      // 1863: fconst_0
      // 1864: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1867: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 186a: fconst_0
      // 186b: fconst_2
      // 186c: fconst_0
      // 186d: ldc_w -0.0869
      // 1870: ldc_w 0.7816
      // 1873: ldc_w -0.3849
      // 1876: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1879: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 187c: astore 101
      // 187e: aload 100
      // 1880: ldc "l_leg_armor"
      // 1882: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1885: bipush 115
      // 1887: bipush 107
      // 1889: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 188c: ldc_w -2.5653
      // 188f: ldc_w 0.4957
      // 1892: ldc_w -2.5
      // 1895: ldc_w 3.0
      // 1898: ldc_w 8.0
      // 189b: ldc_w 5.0
      // 189e: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 18a1: dup
      // 18a2: ldc_w 0.2
      // 18a5: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 18a8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 18ab: ldc_w 1.7
      // 18ae: fconst_0
      // 18af: fconst_0
      // 18b0: fconst_0
      // 18b1: fconst_0
      // 18b2: ldc_w -0.1309
      // 18b5: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 18b8: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 18bb: astore 102
      // 18bd: aload 102
      // 18bf: ldc_w "cube_r54"
      // 18c2: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 18c5: bipush 109
      // 18c7: bipush 43
      // 18c9: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 18cc: ldc_w -3.0
      // 18cf: ldc_w -1.0
      // 18d2: ldc_w -3.0
      // 18d5: ldc_w 6.0
      // 18d8: fconst_2
      // 18d9: ldc_w 6.0
      // 18dc: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 18df: dup
      // 18e0: fconst_0
      // 18e1: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 18e4: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 18e7: ldc_w -1.7
      // 18ea: ldc_w 7.0
      // 18ed: fconst_0
      // 18ee: ldc_w -0.2618
      // 18f1: ldc_w 0.3927
      // 18f4: ldc_w -0.2182
      // 18f7: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 18fa: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 18fd: astore 103
      // 18ff: aload 100
      // 1901: ldc "l_leg2"
      // 1903: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1906: bipush 17
      // 1908: bipush 115
      // 190a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 190d: ldc -2.0
      // 190f: fconst_1
      // 1910: ldc -2.0
      // 1912: ldc 4.0
      // 1914: ldc_w 10.0
      // 1917: ldc 4.0
      // 1919: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 191c: dup
      // 191d: ldc_w 0.3
      // 1920: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1923: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1926: bipush 52
      // 1928: bipush 107
      // 192a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 192d: ldc -2.0
      // 192f: fconst_0
      // 1930: ldc -2.0
      // 1932: ldc 4.0
      // 1934: ldc_w 11.0
      // 1937: ldc 4.0
      // 1939: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 193c: dup
      // 193d: fconst_0
      // 193e: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1941: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1944: fconst_0
      // 1945: ldc 9.0
      // 1947: fconst_0
      // 1948: invokestatic net/minecraft/client/model/geom/PartPose.offset (FFF)Lnet/minecraft/client/model/geom/PartPose;
      // 194b: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 194e: astore 104
      // 1950: aload 104
      // 1952: ldc_w "cube_r55"
      // 1955: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1958: bipush 109
      // 195a: bipush 43
      // 195c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 195f: ldc_w -3.0
      // 1962: ldc_w -1.0
      // 1965: ldc_w -3.0
      // 1968: ldc_w 6.0
      // 196b: fconst_2
      // 196c: ldc_w 6.0
      // 196f: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1972: dup
      // 1973: fconst_0
      // 1974: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1977: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 197a: fconst_0
      // 197b: ldc_w 5.0
      // 197e: fconst_0
      // 197f: ldc_w 0.2109
      // 1982: ldc_w 0.1038
      // 1985: ldc_w 0.2387
      // 1988: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 198b: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 198e: astore 105
      // 1990: aload 104
      // 1992: ldc_w "cube_r56"
      // 1995: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1998: bipush 78
      // 199a: bipush 62
      // 199c: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 199f: ldc_w -1.5
      // 19a2: ldc_w -1.5
      // 19a5: ldc_w -0.5
      // 19a8: ldc_w 3.0
      // 19ab: ldc_w 3.0
      // 19ae: fconst_1
      // 19af: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 19b2: dup
      // 19b3: fconst_0
      // 19b4: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 19b7: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 19ba: fconst_0
      // 19bb: fconst_0
      // 19bc: ldc_w -2.5
      // 19bf: fconst_0
      // 19c0: fconst_0
      // 19c1: ldc_w -0.7854
      // 19c4: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 19c7: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 19ca: astore 106
      // 19cc: aload 1
      // 19cd: ldc "anchor2"
      // 19cf: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 19d2: bipush 0
      // 19d3: bipush 0
      // 19d4: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 19d7: ldc -2.0
      // 19d9: ldc_w -3.0
      // 19dc: ldc_w -23.0
      // 19df: ldc 4.0
      // 19e1: ldc_w 6.0
      // 19e4: ldc_w 32.0
      // 19e7: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 19ea: dup
      // 19eb: ldc_w -0.01
      // 19ee: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 19f1: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 19f4: ldc 4.0
      // 19f6: ldc_w 8.0
      // 19f9: fconst_0
      // 19fa: ldc_w -0.4363
      // 19fd: fconst_0
      // 19fe: fconst_0
      // 19ff: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1a02: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1a05: astore 107
      // 1a07: aload 107
      // 1a09: ldc_w "cube_r57"
      // 1a0c: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1a0f: bipush 0
      // 1a10: bipush 85
      // 1a12: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1a15: ldc -2.0
      // 1a17: ldc_w -4.0
      // 1a1a: ldc_w -4.0
      // 1a1d: ldc 4.0
      // 1a1f: ldc_w 8.0
      // 1a22: ldc_w 8.0
      // 1a25: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1a28: dup
      // 1a29: fconst_0
      // 1a2a: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1a2d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1a30: fconst_0
      // 1a31: fconst_0
      // 1a32: ldc_w 7.0
      // 1a35: ldc_w -2.3562
      // 1a38: fconst_0
      // 1a39: fconst_0
      // 1a3a: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1a3d: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1a40: astore 108
      // 1a42: aload 107
      // 1a44: ldc_w "cube_r58"
      // 1a47: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1a4a: bipush 49
      // 1a4c: bipush 39
      // 1a4e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1a51: fconst_1
      // 1a52: ldc_w -10.0
      // 1a55: ldc_w -10.0
      // 1a58: fconst_0
      // 1a59: ldc_w 14.0
      // 1a5c: ldc_w 14.0
      // 1a5f: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1a62: dup
      // 1a63: fconst_0
      // 1a64: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1a67: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1a6a: ldc_w -1.0
      // 1a6d: fconst_0
      // 1a6e: ldc_w 10.0
      // 1a71: ldc_w -2.3562
      // 1a74: fconst_0
      // 1a75: fconst_0
      // 1a76: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1a79: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1a7c: astore 109
      // 1a7e: aload 107
      // 1a80: ldc_w "cube_r59"
      // 1a83: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1a86: bipush 0
      // 1a87: bipush 102
      // 1a89: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1a8c: ldc_w -3.0
      // 1a8f: ldc_w -3.0
      // 1a92: ldc_w -3.0
      // 1a95: ldc_w 6.0
      // 1a98: ldc_w 6.0
      // 1a9b: ldc_w 6.0
      // 1a9e: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1aa1: dup
      // 1aa2: ldc_w 0.1
      // 1aa5: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1aa8: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1aab: fconst_0
      // 1aac: fconst_0
      // 1aad: ldc_w -26.3
      // 1ab0: ldc_w -2.3562
      // 1ab3: fconst_0
      // 1ab4: fconst_0
      // 1ab5: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1ab8: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1abb: astore 110
      // 1abd: aload 107
      // 1abf: ldc_w "cube_r60"
      // 1ac2: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1ac5: bipush 78
      // 1ac7: bipush 52
      // 1ac9: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1acc: ldc_w -3.0
      // 1acf: fconst_1
      // 1ad0: ldc_w -3.0
      // 1ad3: ldc_w 6.0
      // 1ad6: fconst_0
      // 1ad7: ldc 9.0
      // 1ad9: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1adc: dup
      // 1add: fconst_0
      // 1ade: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1ae1: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1ae4: fconst_0
      // 1ae5: fconst_1
      // 1ae6: ldc_w -30.0
      // 1ae9: ldc_w 3.1416
      // 1aec: fconst_0
      // 1aed: fconst_0
      // 1aee: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1af1: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1af4: astore 111
      // 1af6: aload 107
      // 1af8: ldc_w "cube_r61"
      // 1afb: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1afe: bipush 107
      // 1b00: bipush 121
      // 1b02: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1b05: ldc_w -1.0
      // 1b08: ldc_w -3.0
      // 1b0b: ldc_w -3.0
      // 1b0e: fconst_2
      // 1b0f: ldc_w 6.0
      // 1b12: ldc_w 6.0
      // 1b15: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1b18: dup
      // 1b19: fconst_0
      // 1b1a: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1b1d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1b20: fconst_0
      // 1b21: fconst_0
      // 1b22: ldc_w -30.0
      // 1b25: ldc_w 3.1416
      // 1b28: fconst_0
      // 1b29: fconst_0
      // 1b2a: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1b2d: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1b30: astore 112
      // 1b32: aload 107
      // 1b34: ldc_w "cube_r62"
      // 1b37: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1b3a: bipush 0
      // 1b3b: bipush 53
      // 1b3d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1b40: fconst_0
      // 1b41: ldc_w -7.0
      // 1b44: ldc_w -3.0
      // 1b47: fconst_0
      // 1b48: ldc_w 18.0
      // 1b4b: ldc_w 11.0
      // 1b4e: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1b51: dup
      // 1b52: fconst_0
      // 1b53: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1b56: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1b59: fconst_0
      // 1b5a: fconst_2
      // 1b5b: ldc_w -30.0
      // 1b5e: ldc_w 3.1416
      // 1b61: fconst_0
      // 1b62: fconst_0
      // 1b63: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1b66: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1b69: astore 113
      // 1b6b: aload 107
      // 1b6d: ldc_w "cube_r63"
      // 1b70: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1b73: bipush 73
      // 1b75: bipush 104
      // 1b77: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1b7a: ldc_w -1.0
      // 1b7d: ldc_w -6.0
      // 1b80: ldc_w -3.0
      // 1b83: fconst_2
      // 1b84: ldc_w 12.0
      // 1b87: ldc_w 6.0
      // 1b8a: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1b8d: dup
      // 1b8e: ldc_w 0.001
      // 1b91: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1b94: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1b97: fconst_0
      // 1b98: ldc_w -15.6944
      // 1b9b: ldc_w -21.2732
      // 1b9e: ldc_w 2.3998
      // 1ba1: fconst_0
      // 1ba2: fconst_0
      // 1ba3: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1ba6: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1ba9: astore 114
      // 1bab: aload 107
      // 1bad: ldc_w "cube_r64"
      // 1bb0: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1bb3: bipush 77
      // 1bb5: bipush 85
      // 1bb7: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1bba: ldc -2.0
      // 1bbc: ldc_w -1.0
      // 1bbf: ldc_w -3.0
      // 1bc2: ldc 4.0
      // 1bc4: ldc_w 12.0
      // 1bc7: ldc_w 6.0
      // 1bca: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1bcd: dup
      // 1bce: fconst_0
      // 1bcf: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1bd2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1bd5: fconst_0
      // 1bd6: ldc -2.0
      // 1bd8: ldc_w -26.0
      // 1bdb: ldc_w 3.0107
      // 1bde: fconst_0
      // 1bdf: fconst_0
      // 1be0: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1be3: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1be6: astore 115
      // 1be8: aload 107
      // 1bea: ldc_w "cube_r65"
      // 1bed: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1bf0: bipush 73
      // 1bf2: bipush 104
      // 1bf4: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1bf7: ldc_w -1.0
      // 1bfa: ldc_w -6.0
      // 1bfd: ldc_w -3.0
      // 1c00: fconst_2
      // 1c01: ldc_w 12.0
      // 1c04: ldc_w 6.0
      // 1c07: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1c0a: dup
      // 1c0b: ldc_w 0.001
      // 1c0e: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1c11: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1c14: fconst_0
      // 1c15: ldc_w 15.6944
      // 1c18: ldc_w -21.2732
      // 1c1b: ldc_w 2.3998
      // 1c1e: fconst_0
      // 1c1f: ldc_w 3.1416
      // 1c22: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1c25: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1c28: astore 116
      // 1c2a: aload 107
      // 1c2c: ldc_w "cube_r66"
      // 1c2f: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1c32: bipush 77
      // 1c34: bipush 85
      // 1c36: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1c39: ldc -2.0
      // 1c3b: ldc_w -1.0
      // 1c3e: ldc_w -3.0
      // 1c41: ldc 4.0
      // 1c43: ldc_w 12.0
      // 1c46: ldc_w 6.0
      // 1c49: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1c4c: dup
      // 1c4d: fconst_0
      // 1c4e: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1c51: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1c54: fconst_0
      // 1c55: fconst_2
      // 1c56: ldc_w -26.0
      // 1c59: ldc_w 3.0107
      // 1c5c: fconst_0
      // 1c5d: ldc_w 3.1416
      // 1c60: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1c63: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1c66: astore 117
      // 1c68: aload 107
      // 1c6a: ldc_w "cube_r67"
      // 1c6d: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1c70: bipush 125
      // 1c72: bipush 91
      // 1c74: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1c77: ldc_w -1.0
      // 1c7a: ldc_w -5.0
      // 1c7d: ldc -2.0
      // 1c7f: fconst_2
      // 1c80: ldc_w 10.0
      // 1c83: ldc 4.0
      // 1c85: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1c88: dup
      // 1c89: fconst_0
      // 1c8a: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1c8d: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1c90: fconst_0
      // 1c91: ldc_w -3.0963
      // 1c94: ldc_w -22.4693
      // 1c97: ldc_w 2.7925
      // 1c9a: fconst_0
      // 1c9b: ldc_w -3.1416
      // 1c9e: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1ca1: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1ca4: astore 118
      // 1ca6: aload 107
      // 1ca8: ldc_w "cube_r68"
      // 1cab: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1cae: bipush 125
      // 1cb0: bipush 91
      // 1cb2: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1cb5: ldc_w -1.0
      // 1cb8: ldc_w -5.0
      // 1cbb: ldc -2.0
      // 1cbd: fconst_2
      // 1cbe: ldc_w 10.0
      // 1cc1: ldc 4.0
      // 1cc3: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1cc6: dup
      // 1cc7: fconst_0
      // 1cc8: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1ccb: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1cce: fconst_0
      // 1ccf: ldc_w 3.0963
      // 1cd2: ldc_w -22.4693
      // 1cd5: ldc_w 2.7925
      // 1cd8: fconst_0
      // 1cd9: fconst_0
      // 1cda: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1cdd: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1ce0: astore 119
      // 1ce2: aload 107
      // 1ce4: ldc_w "cube_r69"
      // 1ce7: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1cea: bipush 23
      // 1cec: bipush 53
      // 1cee: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1cf1: ldc_w -1.0
      // 1cf4: ldc -2.0
      // 1cf6: ldc_w -5.0
      // 1cf9: fconst_2
      // 1cfa: ldc 4.0
      // 1cfc: ldc_w 10.0
      // 1cff: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1d02: dup
      // 1d03: fconst_0
      // 1d04: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1d07: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1d0a: bipush 0
      // 1d0b: sipush 131
      // 1d0e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1d11: ldc_w -1.0
      // 1d14: ldc_w -1.0
      // 1d17: ldc_w -11.0
      // 1d1a: fconst_2
      // 1d1b: fconst_2
      // 1d1c: ldc_w 6.0
      // 1d1f: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1d22: dup
      // 1d23: fconst_0
      // 1d24: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1d27: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1d2a: bipush 25
      // 1d2c: bipush 85
      // 1d2e: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1d31: fconst_0
      // 1d32: ldc_w -5.0
      // 1d35: ldc_w -13.0
      // 1d38: fconst_0
      // 1d39: ldc_w 10.0
      // 1d3c: ldc_w 10.0
      // 1d3f: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1d42: dup
      // 1d43: fconst_0
      // 1d44: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1d47: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1d4a: fconst_0
      // 1d4b: ldc_w -19.5093
      // 1d4e: ldc_w -14.7353
      // 1d51: ldc_w 3.0107
      // 1d54: fconst_0
      // 1d55: fconst_0
      // 1d56: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1d59: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1d5c: astore 120
      // 1d5e: aload 107
      // 1d60: ldc_w "cube_r70"
      // 1d63: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1d66: bipush 0
      // 1d67: sipush 131
      // 1d6a: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1d6d: ldc_w -1.0
      // 1d70: ldc_w -1.0
      // 1d73: ldc_w -11.0
      // 1d76: fconst_2
      // 1d77: fconst_2
      // 1d78: ldc_w 6.0
      // 1d7b: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1d7e: dup
      // 1d7f: fconst_0
      // 1d80: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1d83: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1d86: fconst_0
      // 1d87: ldc_w 19.5093
      // 1d8a: ldc_w -14.7353
      // 1d8d: ldc_w -3.0107
      // 1d90: fconst_0
      // 1d91: fconst_0
      // 1d92: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1d95: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1d98: astore 121
      // 1d9a: aload 107
      // 1d9c: ldc_w "cube_r71"
      // 1d9f: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1da2: bipush 25
      // 1da4: bipush 85
      // 1da6: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1da9: fconst_0
      // 1daa: ldc_w -5.0
      // 1dad: ldc_w -5.0
      // 1db0: fconst_0
      // 1db1: ldc_w 10.0
      // 1db4: ldc_w 10.0
      // 1db7: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1dba: dup
      // 1dbb: fconst_0
      // 1dbc: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1dbf: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1dc2: fconst_0
      // 1dc3: ldc_w 18.4651
      // 1dc6: ldc_w -6.8037
      // 1dc9: ldc_w 3.0107
      // 1dcc: fconst_0
      // 1dcd: ldc_w 3.1416
      // 1dd0: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1dd3: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1dd6: astore 122
      // 1dd8: aload 107
      // 1dda: ldc_w "cube_r72"
      // 1ddd: invokestatic net/minecraft/client/model/geom/builders/CubeListBuilder.create ()Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1de0: bipush 23
      // 1de2: bipush 53
      // 1de4: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.texOffs (II)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1de7: ldc_w -1.0
      // 1dea: ldc -2.0
      // 1dec: ldc_w -5.0
      // 1def: fconst_2
      // 1df0: ldc 4.0
      // 1df2: ldc_w 10.0
      // 1df5: new net/minecraft/client/model/geom/builders/CubeDeformation
      // 1df8: dup
      // 1df9: fconst_0
      // 1dfa: invokespecial net/minecraft/client/model/geom/builders/CubeDeformation.<init> (F)V
      // 1dfd: invokevirtual net/minecraft/client/model/geom/builders/CubeListBuilder.addBox (FFFFFFLnet/minecraft/client/model/geom/builders/CubeDeformation;)Lnet/minecraft/client/model/geom/builders/CubeListBuilder;
      // 1e00: fconst_0
      // 1e01: ldc_w 19.5093
      // 1e04: ldc_w -14.7353
      // 1e07: ldc_w 3.0107
      // 1e0a: fconst_0
      // 1e0b: ldc_w 3.1416
      // 1e0e: invokestatic net/minecraft/client/model/geom/PartPose.offsetAndRotation (FFFFFF)Lnet/minecraft/client/model/geom/PartPose;
      // 1e11: invokevirtual net/minecraft/client/model/geom/builders/PartDefinition.addOrReplaceChild (Ljava/lang/String;Lnet/minecraft/client/model/geom/builders/CubeListBuilder;Lnet/minecraft/client/model/geom/PartPose;)Lnet/minecraft/client/model/geom/builders/PartDefinition;
      // 1e14: astore 123
      // 1e16: aload 0
      // 1e17: sipush 256
      // 1e1a: sipush 256
      // 1e1d: invokestatic net/minecraft/client/model/geom/builders/LayerDefinition.create (Lnet/minecraft/client/model/geom/builders/MeshDefinition;II)Lnet/minecraft/client/model/geom/builders/LayerDefinition;
      // 1e20: areturn
   }

   public void setupAnim(Scylla_Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.root().getAllParts().forEach(ModelPart::resetPose);
      this.animateHeadLookTarget(netHeadYaw, headPitch);
      if (entity.getAttackState() != 7) {
         this.animateWalk(Scylla_Normal_Animation.WALK, limbSwing, limbSwingAmount, 1.5F, 4.0F);
      }

      this.l_eye.visible = entity.getEye();
      this.r_eye.visible = entity.getEye();
      this.chain_main.visible = entity.getChainAnchor();
      this.anchor2.visible = entity.isSleep();
      this.animate(entity.getAnimationState("idle"), Scylla_Normal_Animation.IDLE, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("cross_swing"), Scylla_Normal_Animation.CROSS_SWING, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("cross_swing2"), Scylla_Normal_Animation.CROSS_SWING2, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("double_swing"), Scylla_Normal_Animation.DOUBLE_CROSS_SWING, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("swing_smash"), Scylla_Normal_Animation.SWING_SMASH, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("smash"), Scylla_Normal_Animation.SMASH, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("back_step"), Scylla_Normal_Animation.BACKSTEP, ageInTicks, 1.2F);
      this.animate(entity.getAnimationState("spin"), Scylla_Normal_Animation.ANCHOR_SPIN_ATTACK, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("wave"), Scylla_Projectile_Animation.WAVE_SHOOT, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("lightning_explosion"), Scylla_Lightning_Animation.LIGHTNING_EXPLOSION, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("lightning_spear_throw"), Scylla_Lightning_Animation.LIGHTNING_SPEAR_THROW, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("water_spear_throw"), Scylla_Projectile_Animation.WATER_SPEAR_THROW, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("anchor_shot"), Scylla_Projectile_Animation.ANCHOR_THROW, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("anchor_shot_pull"), Scylla_Projectile_Animation.ANCHOR_THROW_PULL, ageInTicks, 1.1F);
      this.animate(entity.getAnimationState("chain_jump_1"), Scylla_Projectile_Animation.CHAIN_JUMP, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("chain_jump_2"), Scylla_Projectile_Animation.LANDING, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("chain_jump_3"), Scylla_Projectile_Animation.SUPER_HERO_LANDING, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("anchor_explosion"), Scylla_Lightning_Animation.ANCHOR_EXPLOSION, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("parry"), Scylla_Projectile_Animation.PARRY, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("electric_whip"), Scylla_Lightning_Animation.ELECTRIC_WHIP, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("whip_and_spear"), Scylla_Projectile_Animation.WHIP_AND_SPEAR, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("death"), Scylla_Lightning_Animation.DEATH, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("enchant_1"), Scylla_Lightning_Animation.WEAPON_ENCHANT, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("enchant_2"), Scylla_Lightning_Animation.WEAPON_ENCHANT2, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("spawn"), Scylla_Lightning_Animation.SPAWN, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("spawn_idle"), Scylla_Lightning_Animation.SPAWN_IDLE, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("summon_snake"), Scylla_Lightning_Animation.SUMMON_SNAKE, ageInTicks, 1.0F);
      this.animate(entity.getAnimationState("grab_smash"), Scylla_Normal_Animation.GRAB_SMASH, ageInTicks, 1.0F);
   }

   public void translateToEye(PoseStack matrixStack, boolean right) {
      this.root.translateAndRotate(matrixStack);
      this.everything.translateAndRotate(matrixStack);
      this.scylla.translateAndRotate(matrixStack);
      this.body.translateAndRotate(matrixStack);
      this.chest.translateAndRotate(matrixStack);
      this.head.translateAndRotate(matrixStack);
      if (right) {
         this.r_eye.translateAndRotate(matrixStack);
      } else {
         this.l_eye.translateAndRotate(matrixStack);
      }
   }

   public Vec3 getChainPosition(Vec3 param1) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 00: new com/mojang/blaze3d/vertex/PoseStack
      // 03: dup
      // 04: invokespecial com/mojang/blaze3d/vertex/PoseStack.<init> ()V
      // 07: astore 2
      // 08: aload 2
      // 09: invokevirtual com/mojang/blaze3d/vertex/PoseStack.pushPose ()V
      // 0c: aload 0
      // 0d: getfield com/github/L_Ender/cataclysm/client/model/entity/Scylla_Model.root Lnet/minecraft/client/model/geom/ModelPart;
      // 10: aload 2
      // 11: invokevirtual net/minecraft/client/model/geom/ModelPart.translateAndRotate (Lcom/mojang/blaze3d/vertex/PoseStack;)V
      // 14: aload 0
      // 15: getfield com/github/L_Ender/cataclysm/client/model/entity/Scylla_Model.everything Lnet/minecraft/client/model/geom/ModelPart;
      // 18: aload 2
      // 19: invokevirtual net/minecraft/client/model/geom/ModelPart.translateAndRotate (Lcom/mojang/blaze3d/vertex/PoseStack;)V
      // 1c: aload 0
      // 1d: getfield com/github/L_Ender/cataclysm/client/model/entity/Scylla_Model.scylla Lnet/minecraft/client/model/geom/ModelPart;
      // 20: aload 2
      // 21: invokevirtual net/minecraft/client/model/geom/ModelPart.translateAndRotate (Lcom/mojang/blaze3d/vertex/PoseStack;)V
      // 24: aload 0
      // 25: getfield com/github/L_Ender/cataclysm/client/model/entity/Scylla_Model.body Lnet/minecraft/client/model/geom/ModelPart;
      // 28: aload 2
      // 29: invokevirtual net/minecraft/client/model/geom/ModelPart.translateAndRotate (Lcom/mojang/blaze3d/vertex/PoseStack;)V
      // 2c: aload 0
      // 2d: getfield com/github/L_Ender/cataclysm/client/model/entity/Scylla_Model.chest Lnet/minecraft/client/model/geom/ModelPart;
      // 30: aload 2
      // 31: invokevirtual net/minecraft/client/model/geom/ModelPart.translateAndRotate (Lcom/mojang/blaze3d/vertex/PoseStack;)V
      // 34: aload 0
      // 35: getfield com/github/L_Ender/cataclysm/client/model/entity/Scylla_Model.l_arm Lnet/minecraft/client/model/geom/ModelPart;
      // 38: aload 2
      // 39: invokevirtual net/minecraft/client/model/geom/ModelPart.translateAndRotate (Lcom/mojang/blaze3d/vertex/PoseStack;)V
      // 3c: aload 0
      // 3d: getfield com/github/L_Ender/cataclysm/client/model/entity/Scylla_Model.l_arm2 Lnet/minecraft/client/model/geom/ModelPart;
      // 40: aload 2
      // 41: invokevirtual net/minecraft/client/model/geom/ModelPart.translateAndRotate (Lcom/mojang/blaze3d/vertex/PoseStack;)V
      // 44: new org/joml/Vector4f
      // 47: dup
      // 48: aload 1
      // 49: getfield net/minecraft/world/phys/Vec3.x D
      // 4c: d2f
      // 4d: aload 1
      // 4e: getfield net/minecraft/world/phys/Vec3.y D
      // 51: d2f
      // 52: aload 1
      // 53: getfield net/minecraft/world/phys/Vec3.z D
      // 56: d2f
      // 57: fconst_1
      // 58: invokespecial org/joml/Vector4f.<init> (FFFF)V
      // 5b: astore 3
      // 5c: aload 3
      // 5d: aload 2
      // 5e: invokevirtual com/mojang/blaze3d/vertex/PoseStack.last ()Lcom/mojang/blaze3d/vertex/PoseStack$Pose;
      // 61: invokevirtual com/mojang/blaze3d/vertex/PoseStack$Pose.pose ()Lorg/joml/Matrix4f;
      // 64: invokevirtual org/joml/Vector4f.mul (Lorg/joml/Matrix4fc;)Lorg/joml/Vector4f;
      // 67: pop
      // 68: new net/minecraft/world/phys/Vec3
      // 6b: dup
      // 6c: aload 3
      // 6d: invokevirtual org/joml/Vector4f.x ()F
      // 70: f2d
      // 71: aload 3
      // 72: invokevirtual org/joml/Vector4f.y ()F
      // 75: f2d
      // 76: aload 3
      // 77: invokevirtual org/joml/Vector4f.z ()F
      // 7a: f2d
      // 7b: invokespecial net/minecraft/world/phys/Vec3.<init> (DDD)V
      // 7e: astore 4
      // 80: aload 2
      // 81: invokevirtual com/mojang/blaze3d/vertex/PoseStack.popPose ()V
      // 84: aload 4
      // 86: dconst_0
      // 87: dconst_0
      // 88: dconst_0
      // 89: invokevirtual net/minecraft/world/phys/Vec3.add (DDD)Lnet/minecraft/world/phys/Vec3;
      // 8c: areturn
   }

   public void translateChainAnchor(PoseStack matrixStack) {
      this.root.translateAndRotate(matrixStack);
      this.everything.translateAndRotate(matrixStack);
      this.scylla.translateAndRotate(matrixStack);
      this.body.translateAndRotate(matrixStack);
      this.chest.translateAndRotate(matrixStack);
      this.l_arm.translateAndRotate(matrixStack);
      this.l_arm2.translateAndRotate(matrixStack);
      this.chain_main.translateAndRotate(matrixStack);
      this.chain_4.translateAndRotate(matrixStack);
      this.chain_3.translateAndRotate(matrixStack);
      this.chain_2.translateAndRotate(matrixStack);
      this.chain.translateAndRotate(matrixStack);
      this.chain_anchor.translateAndRotate(matrixStack);
      this.trail.translateAndRotate(matrixStack);
   }

   public void translateHand(PoseStack matrixStack) {
      this.root.translateAndRotate(matrixStack);
      this.everything.translateAndRotate(matrixStack);
      this.scylla.translateAndRotate(matrixStack);
      this.body.translateAndRotate(matrixStack);
      this.chest.translateAndRotate(matrixStack);
      this.l_arm.translateAndRotate(matrixStack);
      this.l_arm2.translateAndRotate(matrixStack);
      this.anchor.translateAndRotate(matrixStack);
   }

   public Vec3 getHandPosition(Vec3 offsetIn) {
      PoseStack translationStack = new PoseStack();
      translationStack.pushPose();
      this.translateHand(translationStack);
      Vector4f armOffsetVec = new Vector4f((float)offsetIn.x, (float)offsetIn.y, (float)offsetIn.z, 1.0F);
      armOffsetVec.mul(translationStack.last().pose());
      Vec3 vec3 = new Vec3(-armOffsetVec.x(), -armOffsetVec.y(), armOffsetVec.z());
      translationStack.popPose();
      return vec3.add(0.0, 1.5, 0.0);
   }

   private void animateHeadLookTarget(float yRot, float xRot) {
      this.head.xRot = xRot * (float) (Math.PI / 180.0);
      this.head.yRot = yRot * (float) (Math.PI / 180.0);
      float f = xRot * (float) (Math.PI / 180.0);
      float f1 = 0.5F;
      this.hair.xRot = Mth.cos(f) * f1;
   }

   public ModelPart root() {
      return this.root;
   }
}
