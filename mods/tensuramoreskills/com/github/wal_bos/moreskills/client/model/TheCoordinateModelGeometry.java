package com.github.wal_bos.moreskills.client.model;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public final class TheCoordinateModelGeometry {
   private TheCoordinateModelGeometry() {
   }

   public static LayerDefinition create() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition bone5 = partdefinition.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offset(13.15F, 5.525F, 2.775F));
      PartDefinition cube_r1 = bone5.addOrReplaceChild(
         "cube_r1",
         CubeListBuilder.create().texOffs(112, 131).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-14.0F, 0.0F, 0.0F, -0.9015F, 0.3085F, -0.6931F)
      );
      PartDefinition cube_r2 = bone5.addOrReplaceChild(
         "cube_r2",
         CubeListBuilder.create().texOffs(72, 127).addBox(-1.5F, -5.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-4.3F, -2.575F, 35.4F, -2.1881F, -0.0384F, 0.7445F)
      );
      PartDefinition cube_r3 = bone5.addOrReplaceChild(
         "cube_r3",
         CubeListBuilder.create().texOffs(124, 71).addBox(-1.0F, -9.0F, -1.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-6.3F, -3.075F, 24.15F, -1.1693F, 0.2944F, 0.6568F)
      );
      PartDefinition cube_r4 = bone5.addOrReplaceChild(
         "cube_r4",
         CubeListBuilder.create().texOffs(124, 0).addBox(-1.0F, -9.0F, -1.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-7.3F, -4.825F, 11.65F, -1.4047F, -0.2297F, -1.6624F)
      );
      PartDefinition cube_r5 = bone5.addOrReplaceChild(
         "cube_r5",
         CubeListBuilder.create().texOffs(120, 27).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-11.4F, -0.325F, 0.975F, -1.902F, 0.8089F, -1.2813F)
      );
      PartDefinition bone6 = partdefinition.addOrReplaceChild(
         "bone6", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.85F, 2.525F, 1.775F, 0.0F, -1.3526F, 0.0F)
      );
      PartDefinition bone = partdefinition.addOrReplaceChild(
         "bone", CubeListBuilder.create(), PartPose.offsetAndRotation(25.6F, -7.55F, 22.675F, 0.0F, 0.9599F, 0.0F)
      );
      PartDefinition bone2 = partdefinition.addOrReplaceChild(
         "bone2", CubeListBuilder.create(), PartPose.offsetAndRotation(-21.4F, -7.55F, -25.325F, 0.0F, -2.2689F, 0.0F)
      );
      PartDefinition bone3 = partdefinition.addOrReplaceChild(
         "bone3", CubeListBuilder.create(), PartPose.offsetAndRotation(19.6F, -7.55F, -26.325F, 0.0F, 2.6616F, 0.0F)
      );
      PartDefinition bone4 = partdefinition.addOrReplaceChild(
         "bone4", CubeListBuilder.create(), PartPose.offsetAndRotation(-22.4F, -7.55F, 24.675F, 0.0F, -0.6109F, 0.0F)
      );
      PartDefinition cube_r6 = bone5.addOrReplaceChild(
         "cube_r6",
         CubeListBuilder.create().texOffs(60, 117).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-16.9F, -2.275F, 4.175F, 0.1391F, 0.3369F, -2.2516F)
      );
      PartDefinition cube_r7 = bone5.addOrReplaceChild(
         "cube_r7",
         CubeListBuilder.create().texOffs(96, 14).addBox(-1.5F, 1.0F, -1.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-27.0F, -2.275F, 18.25F, 0.7897F, 0.0924F, -1.1725F)
      );
      PartDefinition cube_r8 = bone5.addOrReplaceChild(
         "cube_r8",
         CubeListBuilder.create().texOffs(66, 33).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-19.0F, -4.025F, 10.0F, 0.7897F, 0.0924F, -2.0888F)
      );
      PartDefinition cube_r9 = bone5.addOrReplaceChild(
         "cube_r9",
         CubeListBuilder.create().texOffs(128, 43).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-10.4F, 0.05F, -8.3F, -1.2455F, -0.5446F, -0.0287F)
      );
      PartDefinition cube_r10 = bone5.addOrReplaceChild(
         "cube_r10",
         CubeListBuilder.create().texOffs(88, 117).addBox(-1.5F, -10.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(1.825F, -3.9F, -32.625F, -1.0308F, -0.1843F, -1.2008F)
      );
      PartDefinition cube_r11 = bone5.addOrReplaceChild(
         "cube_r11",
         CubeListBuilder.create().texOffs(80, 117).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-5.425F, -2.65F, -19.375F, -1.0952F, 0.1827F, -2.5957F)
      );
      PartDefinition cube_r12 = bone5.addOrReplaceChild(
         "cube_r12",
         CubeListBuilder.create().texOffs(100, 0).addBox(-1.5F, -2.0F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-52.0F, -1.175F, -0.725F, 1.0471F, 0.3816F, 0.703F)
      );
      PartDefinition cube_r13 = bone5.addOrReplaceChild(
         "cube_r13",
         CubeListBuilder.create().texOffs(66, 18).addBox(-1.0F, -2.0F, -8.0F, 2.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-41.75F, -6.425F, -1.725F, 2.2112F, -0.9466F, 0.8312F)
      );
      PartDefinition cube_r14 = bone5.addOrReplaceChild(
         "cube_r14",
         CubeListBuilder.create().texOffs(60, 63).addBox(-1.0F, -2.0F, -8.0F, 2.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-29.75F, -4.675F, -3.225F, -1.4123F, 0.5691F, -1.3454F)
      );
      PartDefinition cube_r15 = bone5.addOrReplaceChild(
         "cube_r15",
         CubeListBuilder.create().texOffs(0, 54).addBox(-1.0F, -2.0F, -8.0F, 2.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-18.0F, -1.175F, -2.975F, -1.6307F, 0.2081F, -1.142F)
      );
      PartDefinition cube_r16 = bone5.addOrReplaceChild(
         "cube_r16",
         CubeListBuilder.create().texOffs(118, 105).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-13.65F, 1.475F, -5.775F, -0.1806F, 0.4106F, 0.6604F)
      );
      PartDefinition cube_r17 = bone5.addOrReplaceChild(
         "cube_r17",
         CubeListBuilder.create().texOffs(94, 33).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-18.5F, 2.5F, -5.75F, 0.1119F, 1.1238F, -0.4349F)
      );
      PartDefinition cube_r18 = bone5.addOrReplaceChild(
         "cube_r18",
         CubeListBuilder.create().texOffs(96, 105).addBox(-1.5F, -2.0F, -4.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-40.25F, -2.625F, -26.05F, -0.3629F, -0.607F, -1.7802F)
      );
      PartDefinition cube_r19 = bone5.addOrReplaceChild(
         "cube_r19",
         CubeListBuilder.create().texOffs(0, 106).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-35.75F, 0.875F, -20.8F, -0.8969F, 0.4F, -1.061F)
      );
      PartDefinition cube_r20 = bone5.addOrReplaceChild(
         "cube_r20",
         CubeListBuilder.create().texOffs(36, 18).addBox(-1.0F, -2.0F, -8.0F, 2.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-26.25F, 2.625F, -12.55F, -0.6879F, -0.4115F, -1.7331F)
      );
      PartDefinition cube_r21 = bone5.addOrReplaceChild(
         "cube_r21",
         CubeListBuilder.create().texOffs(36, 0).addBox(-1.0F, -2.0F, -15.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-15.15F, -1.6F, -11.95F, 0.1891F, 0.2707F, 1.2747F)
      );
      PartDefinition cube_r22 = bone5.addOrReplaceChild(
         "cube_r22",
         CubeListBuilder.create().texOffs(96, 116).addBox(-1.5F, -2.0F, -8.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-12.65F, -7.6F, -40.7F, 0.4071F, 0.3369F, -1.5832F)
      );
      PartDefinition cube_r23 = bone5.addOrReplaceChild(
         "cube_r23",
         CubeListBuilder.create().texOffs(0, 36).addBox(-1.0F, -2.0F, -15.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-17.9F, -5.85F, -25.95F, 0.1891F, 0.2707F, -3.1322F)
      );
      PartDefinition cube_r24 = bone5.addOrReplaceChild(
         "cube_r24",
         CubeListBuilder.create().texOffs(0, 127).addBox(-1.5F, -5.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(24.175F, -7.625F, 18.925F, 1.9539F, 0.6386F, 1.3073F)
      );
      PartDefinition cube_r25 = bone5.addOrReplaceChild(
         "cube_r25",
         CubeListBuilder.create().texOffs(126, 114).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(15.925F, -6.875F, 11.175F, 2.5504F, -0.0945F, 1.2268F)
      );
      PartDefinition cube_r26 = bone5.addOrReplaceChild(
         "cube_r26",
         CubeListBuilder.create().texOffs(124, 87).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(4.675F, -4.375F, 5.925F, 2.292F, -1.1612F, 2.261F)
      );
      PartDefinition cube_r27 = bone5.addOrReplaceChild(
         "cube_r27",
         CubeListBuilder.create().texOffs(8, 127).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-6.575F, -1.625F, 0.925F, 0.5644F, -0.5596F, -2.213F)
      );
      PartDefinition cube_r28 = bone5.addOrReplaceChild(
         "cube_r28",
         CubeListBuilder.create().texOffs(105, 58).addBox(-4.0F, -2.0F, -1.0F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(23.35F, -7.575F, -9.05F, -2.8284F, -0.3722F, 0.1843F)
      );
      PartDefinition cube_r29 = bone5.addOrReplaceChild(
         "cube_r29",
         CubeListBuilder.create().texOffs(96, 27).addBox(-4.0F, -2.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(14.1F, -8.075F, -12.05F, -1.9411F, -0.1457F, 0.0955F)
      );
      PartDefinition cube_r30 = bone5.addOrReplaceChild(
         "cube_r30",
         CubeListBuilder.create().texOffs(72, 14).addBox(-4.0F, -2.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(5.85F, -5.825F, -8.55F, -0.2628F, 0.6272F, -0.2591F)
      );
      PartDefinition cube_r31 = bone5.addOrReplaceChild(
         "cube_r31",
         CubeListBuilder.create().texOffs(66, 51).addBox(-4.0F, -2.0F, -1.0F, 17.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-8.65F, -0.075F, -3.8F, -0.5097F, 0.2248F, -0.4179F)
      );
      PartDefinition cube_r32 = bone5.addOrReplaceChild(
         "cube_r32",
         CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -2.0F, -8.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-16.325F, 0.025F, -9.825F, -0.5821F, 0.1351F, -1.4325F)
      );
      PartDefinition cube_r33 = bone5.addOrReplaceChild(
         "cube_r33",
         CubeListBuilder.create().texOffs(40, 117).addBox(-1.5F, -2.0F, -2.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-24.425F, -2.375F, -39.225F, -0.4308F, -0.0058F, 2.1299F)
      );
      PartDefinition cube_r34 = bone5.addOrReplaceChild(
         "cube_r34",
         CubeListBuilder.create().texOffs(20, 117).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-24.175F, -3.875F, -31.975F, -0.4308F, -0.0058F, -2.0589F)
      );
      PartDefinition cube_r35 = bone5.addOrReplaceChild(
         "cube_r35",
         CubeListBuilder.create().texOffs(90, 63).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-21.925F, -1.375F, -21.225F, -0.4308F, -0.0058F, -0.2699F)
      );
      PartDefinition cube_r36 = bone5.addOrReplaceChild(
         "cube_r36",
         CubeListBuilder.create().texOffs(16, 78).addBox(-1.0F, -2.0F, -11.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-4.65F, -14.35F, -25.95F, 0.3394F, -0.069F, 1.1009F)
      );
      PartDefinition cube_r37 = bone5.addOrReplaceChild(
         "cube_r37",
         CubeListBuilder.create().texOffs(72, 0).addBox(-1.0F, -2.0F, -11.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-11.15F, -11.35F, -18.2F, -0.5469F, 0.5418F, 1.7232F)
      );
      PartDefinition cube_r38 = bone5.addOrReplaceChild(
         "cube_r38",
         CubeListBuilder.create().texOffs(72, 105).addBox(-1.0F, -2.0F, -9.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-13.65F, -4.6F, -11.2F, -0.5469F, 0.5418F, 1.0251F)
      );
      PartDefinition cube_r39 = bone5.addOrReplaceChild(
         "cube_r39",
         CubeListBuilder.create().texOffs(118, 62).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-13.65F, -1.525F, -4.775F, -0.3175F, 0.2351F, 0.6167F)
      );
      PartDefinition cube_r40 = bone5.addOrReplaceChild(
         "cube_r40",
         CubeListBuilder.create().texOffs(66, 59).addBox(-4.0F, -2.0F, -1.0F, 17.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(18.6F, -10.575F, 3.95F, -0.0675F, -0.1014F, -0.3339F)
      );
      PartDefinition cube_r41 = bone5.addOrReplaceChild(
         "cube_r41",
         CubeListBuilder.create().texOffs(66, 55).addBox(-4.0F, -2.0F, -1.0F, 17.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(3.1F, -9.575F, -1.55F, -1.2106F, -0.3451F, -0.0489F)
      );
      PartDefinition cube_r42 = bone5.addOrReplaceChild(
         "cube_r42",
         CubeListBuilder.create().texOffs(66, 47).addBox(-4.0F, -2.0F, -1.0F, 17.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-10.15F, -1.325F, -2.3F, -0.3463F, 0.0191F, -0.6597F)
      );
      PartDefinition cube_r43 = bone5.addOrReplaceChild(
         "cube_r43",
         CubeListBuilder.create().texOffs(44, 92).addBox(-1.5F, -2.0F, -6.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(16.575F, -7.375F, 25.525F, -0.8846F, -0.2637F, -2.2022F)
      );
      PartDefinition cube_r44 = bone5.addOrReplaceChild(
         "cube_r44",
         CubeListBuilder.create().texOffs(16, 92).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(10.075F, -7.625F, 17.525F, -0.5407F, -0.3949F, -1.4586F)
      );
      PartDefinition cube_r45 = bone5.addOrReplaceChild(
         "cube_r45",
         CubeListBuilder.create().texOffs(72, 78).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(2.075F, -9.875F, 9.525F, -0.8238F, -0.5705F, -2.1114F)
      );
      PartDefinition cube_r46 = bone5.addOrReplaceChild(
         "cube_r46",
         CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -8.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-7.325F, -4.225F, 1.925F, -0.8722F, 0.5403F, -1.8584F)
      );
      PartDefinition cube_r47 = bone5.addOrReplaceChild(
         "cube_r47",
         CubeListBuilder.create().texOffs(40, 127).addBox(-1.5F, -5.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(19.925F, -6.575F, -22.725F, -0.7726F, -0.6363F, -1.2116F)
      );
      PartDefinition cube_r48 = bone5.addOrReplaceChild(
         "cube_r48",
         CubeListBuilder.create().texOffs(32, 127).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(9.925F, -10.075F, -17.975F, -0.3631F, 0.5698F, -1.2284F)
      );
      PartDefinition cube_r49 = bone5.addOrReplaceChild(
         "cube_r49",
         CubeListBuilder.create().texOffs(24, 127).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-0.075F, -8.575F, -12.975F, -0.5662F, -0.5395F, -1.7792F)
      );
      PartDefinition cube_r50 = bone5.addOrReplaceChild(
         "cube_r50",
         CubeListBuilder.create().texOffs(16, 127).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-9.075F, -3.075F, -5.975F, -0.8205F, -0.5704F, -1.6446F)
      );
      PartDefinition cube_r51 = bone5.addOrReplaceChild(
         "cube_r51",
         CubeListBuilder.create().texOffs(104, 46).addBox(-1.5F, -2.0F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-46.0F, -6.175F, -18.475F, 0.8709F, -0.792F, 2.8265F)
      );
      PartDefinition cube_r52 = bone5.addOrReplaceChild(
         "cube_r52",
         CubeListBuilder.create().texOffs(100, 77).addBox(-1.0F, -2.0F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-39.25F, -5.675F, -14.725F, -0.897F, -0.2511F, -2.3316F)
      );
      PartDefinition cube_r53 = bone5.addOrReplaceChild(
         "cube_r53",
         CubeListBuilder.create().texOffs(30, 63).addBox(-1.0F, -2.0F, -8.0F, 2.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-29.0F, -6.925F, -9.225F, -1.1417F, -0.4532F, -1.6791F)
      );
      PartDefinition cube_r54 = bone5.addOrReplaceChild(
         "cube_r54",
         CubeListBuilder.create().texOffs(36, 48).addBox(-1.0F, -2.0F, -8.0F, 2.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-18.0F, -2.175F, -4.475F, -1.1973F, -0.0046F, -1.097F)
      );
      PartDefinition cube_r55 = bone5.addOrReplaceChild(
         "cube_r55",
         CubeListBuilder.create().texOffs(44, 106).addBox(-1.5F, -2.0F, -4.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-30.75F, -2.625F, -26.05F, -0.7015F, -0.1931F, -1.2088F)
      );
      PartDefinition cube_r56 = bone5.addOrReplaceChild(
         "cube_r56",
         CubeListBuilder.create().texOffs(22, 106).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-28.75F, -5.375F, -20.8F, -0.6237F, -0.7121F, 2.8138F)
      );
      PartDefinition cube_r57 = bone5.addOrReplaceChild(
         "cube_r57",
         CubeListBuilder.create().texOffs(36, 33).addBox(-1.0F, -2.0F, -8.0F, 2.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-20.75F, -6.625F, -12.3F, -0.6879F, -0.4115F, -1.7331F)
      );
      PartDefinition cube_r58 = bone5.addOrReplaceChild(
         "cube_r58",
         CubeListBuilder.create().texOffs(72, 92).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-14.75F, -2.5F, -5.0F, -0.7136F, -0.2177F, -1.0173F)
      );
      PartDefinition cube_r59 = bone5.addOrReplaceChild(
         "cube_r59",
         CubeListBuilder.create().texOffs(126, 129).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-15.0F, -1.75F, 0.0F, -0.6545F, 0.0F, -0.6545F)
      );
      PartDefinition cube_r60 = bone5.addOrReplaceChild(
         "cube_r60",
         CubeListBuilder.create().texOffs(96, 127).addBox(-1.5F, -5.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-18.55F, -13.075F, 30.9F, -1.7044F, -0.4735F, -0.2545F)
      );
      PartDefinition cube_r61 = bone5.addOrReplaceChild(
         "cube_r61",
         CubeListBuilder.create().texOffs(64, 127).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-16.05F, -15.325F, 19.9F, -1.4288F, 0.0278F, -2.8485F)
      );
      PartDefinition cube_r62 = bone5.addOrReplaceChild(
         "cube_r62",
         CubeListBuilder.create().texOffs(56, 127).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-13.05F, -10.575F, 11.15F, -1.087F, 0.8495F, -1.38F)
      );
      PartDefinition cube_r63 = bone5.addOrReplaceChild(
         "cube_r63",
         CubeListBuilder.create().texOffs(118, 114).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-13.15F, -1.825F, 0.225F, -1.3432F, 0.5833F, -0.9152F)
      );
      PartDefinition cube_r64 = bone5.addOrReplaceChild(
         "cube_r64",
         CubeListBuilder.create().texOffs(98, 92).addBox(-1.5F, 1.0F, -1.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-26.65F, -12.525F, 13.5F, 1.2853F, 0.9791F, -2.2604F)
      );
      PartDefinition cube_r65 = bone5.addOrReplaceChild(
         "cube_r65",
         CubeListBuilder.create().texOffs(44, 78).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-24.4F, -9.025F, 5.0F, 0.0301F, 0.5238F, -2.2098F)
      );
      PartDefinition cube_r66 = bone5.addOrReplaceChild(
         "cube_r66",
         CubeListBuilder.create().texOffs(0, 117).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-19.15F, -4.775F, 1.75F, 1.2787F, 0.7401F, -1.0303F)
      );
      PartDefinition cube_r67 = bone5.addOrReplaceChild(
         "cube_r67",
         CubeListBuilder.create().texOffs(104, 127).addBox(-1.5F, -7.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-43.675F, -1.225F, 11.15F, -1.7348F, 1.173F, 2.4045F)
      );
      PartDefinition cube_r68 = bone5.addOrReplaceChild(
         "cube_r68",
         CubeListBuilder.create().texOffs(128, 20).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-34.425F, -2.725F, 9.65F, -0.1032F, -0.6606F, -1.5576F)
      );
      PartDefinition cube_r69 = bone5.addOrReplaceChild(
         "cube_r69",
         CubeListBuilder.create().texOffs(48, 127).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-24.175F, -1.475F, 3.15F, -0.7408F, -0.3782F, -1.0557F)
      );
      PartDefinition cube_r70 = bone5.addOrReplaceChild(
         "cube_r70",
         CubeListBuilder.create().texOffs(128, 32).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-17.4F, -1.525F, -0.525F, -0.6545F, -0.9163F, -1.0908F)
      );
      PartDefinition cube_r71 = bone6.addOrReplaceChild(
         "cube_r71",
         CubeListBuilder.create().texOffs(112, 131).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.9015F, 0.3085F, -0.6931F)
      );
      PartDefinition cube_r72 = bone6.addOrReplaceChild(
         "cube_r72",
         CubeListBuilder.create().texOffs(72, 127).addBox(-1.5F, -5.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(9.7F, -2.575F, 35.4F, -2.1881F, -0.0384F, 0.7445F)
      );
      PartDefinition cube_r73 = bone6.addOrReplaceChild(
         "cube_r73",
         CubeListBuilder.create().texOffs(124, 71).addBox(-1.0F, -9.0F, -1.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(7.7F, -3.075F, 24.15F, -1.1693F, 0.2944F, 0.6568F)
      );
      PartDefinition cube_r74 = bone6.addOrReplaceChild(
         "cube_r74",
         CubeListBuilder.create().texOffs(124, 0).addBox(-1.0F, -9.0F, -1.0F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(6.7F, -4.825F, 11.65F, -1.4047F, -0.2297F, -1.6624F)
      );
      PartDefinition cube_r75 = bone6.addOrReplaceChild(
         "cube_r75",
         CubeListBuilder.create().texOffs(120, 27).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(2.6F, -0.325F, 0.975F, -1.902F, 0.8089F, -1.2813F)
      );
      PartDefinition cube_r76 = bone6.addOrReplaceChild(
         "cube_r76",
         CubeListBuilder.create().texOffs(60, 117).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.9F, -2.275F, 4.175F, 0.1391F, 0.3369F, -2.2516F)
      );
      PartDefinition cube_r77 = bone6.addOrReplaceChild(
         "cube_r77",
         CubeListBuilder.create().texOffs(96, 14).addBox(-1.5F, 1.0F, -1.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-13.0F, -2.275F, 18.25F, 0.7897F, 0.0924F, -1.1725F)
      );
      PartDefinition cube_r78 = bone6.addOrReplaceChild(
         "cube_r78",
         CubeListBuilder.create().texOffs(66, 33).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-5.0F, -4.025F, 10.0F, 0.7897F, 0.0924F, -2.0888F)
      );
      PartDefinition cube_r79 = bone6.addOrReplaceChild(
         "cube_r79",
         CubeListBuilder.create().texOffs(128, 43).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(3.6F, 0.05F, -8.3F, -1.2455F, -0.5446F, -0.0287F)
      );
      PartDefinition cube_r80 = bone6.addOrReplaceChild(
         "cube_r80",
         CubeListBuilder.create().texOffs(88, 117).addBox(-1.5F, -10.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(15.825F, -3.9F, -32.625F, -1.0308F, -0.1843F, -1.2008F)
      );
      PartDefinition cube_r81 = bone6.addOrReplaceChild(
         "cube_r81",
         CubeListBuilder.create().texOffs(80, 117).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(8.575F, -2.65F, -19.375F, -1.0952F, 0.1827F, -2.5957F)
      );
      PartDefinition cube_r82 = bone6.addOrReplaceChild(
         "cube_r82",
         CubeListBuilder.create().texOffs(100, 0).addBox(-1.5F, -2.0F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-38.0F, -1.175F, -0.725F, 1.0471F, 0.3816F, 0.703F)
      );
      PartDefinition cube_r83 = bone6.addOrReplaceChild(
         "cube_r83",
         CubeListBuilder.create().texOffs(66, 18).addBox(-1.0F, -2.0F, -8.0F, 2.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-27.75F, -6.425F, -1.725F, 2.2112F, -0.9466F, 0.8312F)
      );
      PartDefinition cube_r84 = bone6.addOrReplaceChild(
         "cube_r84",
         CubeListBuilder.create().texOffs(60, 63).addBox(-1.0F, -2.0F, -8.0F, 2.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-15.75F, -4.675F, -3.225F, -1.4123F, 0.5691F, -1.3454F)
      );
      PartDefinition cube_r85 = bone6.addOrReplaceChild(
         "cube_r85",
         CubeListBuilder.create().texOffs(0, 54).addBox(-1.0F, -2.0F, -8.0F, 2.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-4.0F, -1.175F, -2.975F, -1.6307F, 0.2081F, -1.142F)
      );
      PartDefinition cube_r86 = bone6.addOrReplaceChild(
         "cube_r86",
         CubeListBuilder.create().texOffs(118, 105).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.35F, 1.475F, -5.775F, -0.1806F, 0.4106F, 0.6604F)
      );
      PartDefinition cube_r87 = bone6.addOrReplaceChild(
         "cube_r87",
         CubeListBuilder.create().texOffs(94, 33).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-4.5F, 2.5F, -5.75F, 0.1119F, 1.1238F, -0.4349F)
      );
      PartDefinition cube_r88 = bone6.addOrReplaceChild(
         "cube_r88",
         CubeListBuilder.create().texOffs(96, 105).addBox(-1.5F, -2.0F, -4.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-26.25F, -2.625F, -26.05F, -0.3629F, -0.607F, -1.7802F)
      );
      PartDefinition cube_r89 = bone6.addOrReplaceChild(
         "cube_r89",
         CubeListBuilder.create().texOffs(0, 106).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-21.75F, 0.875F, -20.8F, -0.8969F, 0.4F, -1.061F)
      );
      PartDefinition cube_r90 = bone6.addOrReplaceChild(
         "cube_r90",
         CubeListBuilder.create().texOffs(36, 18).addBox(-1.0F, -2.0F, -8.0F, 2.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-12.25F, 2.625F, -12.55F, -0.6879F, -0.4115F, -1.7331F)
      );
      PartDefinition cube_r91 = bone6.addOrReplaceChild(
         "cube_r91",
         CubeListBuilder.create().texOffs(36, 0).addBox(-1.0F, -2.0F, -15.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-1.15F, -1.6F, -11.95F, 0.1891F, 0.2707F, 1.2747F)
      );
      PartDefinition cube_r92 = bone6.addOrReplaceChild(
         "cube_r92",
         CubeListBuilder.create().texOffs(96, 116).addBox(-1.5F, -2.0F, -8.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(1.35F, -7.6F, -40.7F, 0.4071F, 0.3369F, -1.5832F)
      );
      PartDefinition cube_r93 = bone6.addOrReplaceChild(
         "cube_r93",
         CubeListBuilder.create().texOffs(0, 36).addBox(-1.0F, -2.0F, -15.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-3.9F, -5.85F, -25.95F, 0.1891F, 0.2707F, -3.1322F)
      );
      PartDefinition cube_r94 = bone6.addOrReplaceChild(
         "cube_r94",
         CubeListBuilder.create().texOffs(0, 127).addBox(-1.5F, -5.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(38.175F, -7.625F, 18.925F, 1.9539F, 0.6386F, 1.3073F)
      );
      PartDefinition cube_r95 = bone6.addOrReplaceChild(
         "cube_r95",
         CubeListBuilder.create().texOffs(126, 114).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(29.925F, -6.875F, 11.175F, 2.5504F, -0.0945F, 1.2268F)
      );
      PartDefinition cube_r96 = bone6.addOrReplaceChild(
         "cube_r96",
         CubeListBuilder.create().texOffs(124, 87).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(18.675F, -4.375F, 5.925F, 2.292F, -1.1612F, 2.261F)
      );
      PartDefinition cube_r97 = bone6.addOrReplaceChild(
         "cube_r97",
         CubeListBuilder.create().texOffs(8, 127).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(7.425F, -1.625F, 0.925F, 0.5644F, -0.5596F, -2.213F)
      );
      PartDefinition cube_r98 = bone6.addOrReplaceChild(
         "cube_r98",
         CubeListBuilder.create().texOffs(105, 58).addBox(-4.0F, -2.0F, -1.0F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(37.35F, -7.575F, -9.05F, -2.8284F, -0.3722F, 0.1843F)
      );
      PartDefinition cube_r99 = bone6.addOrReplaceChild(
         "cube_r99",
         CubeListBuilder.create().texOffs(96, 27).addBox(-4.0F, -2.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(28.1F, -8.075F, -12.05F, -1.9411F, -0.1457F, 0.0955F)
      );
      PartDefinition cube_r100 = bone6.addOrReplaceChild(
         "cube_r100",
         CubeListBuilder.create().texOffs(72, 14).addBox(-4.0F, -2.0F, -1.0F, 10.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(19.85F, -5.825F, -8.55F, -0.2628F, 0.6272F, -0.2591F)
      );
      PartDefinition cube_r101 = bone6.addOrReplaceChild(
         "cube_r101",
         CubeListBuilder.create().texOffs(66, 51).addBox(-4.0F, -2.0F, -1.0F, 17.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(5.35F, -0.075F, -3.8F, -0.5097F, 0.2248F, -0.4179F)
      );
      PartDefinition cube_r102 = bone6.addOrReplaceChild(
         "cube_r102",
         CubeListBuilder.create().texOffs(0, 18).addBox(-1.0F, -2.0F, -8.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.325F, 0.025F, -9.825F, -0.5821F, 0.1351F, -1.4325F)
      );
      PartDefinition cube_r103 = bone6.addOrReplaceChild(
         "cube_r103",
         CubeListBuilder.create().texOffs(40, 117).addBox(-1.5F, -2.0F, -2.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-10.425F, -2.375F, -39.225F, -0.4308F, -0.0058F, 2.1299F)
      );
      PartDefinition cube_r104 = bone6.addOrReplaceChild(
         "cube_r104",
         CubeListBuilder.create().texOffs(20, 117).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-10.175F, -3.875F, -31.975F, -0.4308F, -0.0058F, -2.0589F)
      );
      PartDefinition cube_r105 = bone6.addOrReplaceChild(
         "cube_r105",
         CubeListBuilder.create().texOffs(90, 63).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-7.925F, -1.375F, -21.225F, -0.4308F, -0.0058F, -0.2699F)
      );
      PartDefinition cube_r106 = bone6.addOrReplaceChild(
         "cube_r106",
         CubeListBuilder.create().texOffs(16, 78).addBox(-1.0F, -2.0F, -11.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(9.35F, -14.35F, -25.95F, 0.3394F, -0.069F, 1.1009F)
      );
      PartDefinition cube_r107 = bone6.addOrReplaceChild(
         "cube_r107",
         CubeListBuilder.create().texOffs(72, 0).addBox(-1.0F, -2.0F, -11.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(2.85F, -11.35F, -18.2F, -0.5469F, 0.5418F, 1.7232F)
      );
      PartDefinition cube_r108 = bone6.addOrReplaceChild(
         "cube_r108",
         CubeListBuilder.create().texOffs(72, 105).addBox(-1.0F, -2.0F, -9.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.35F, -4.6F, -11.2F, -0.5469F, 0.5418F, 1.0251F)
      );
      PartDefinition cube_r109 = bone6.addOrReplaceChild(
         "cube_r109",
         CubeListBuilder.create().texOffs(118, 62).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.35F, -1.525F, -4.775F, -0.3175F, 0.2351F, 0.6167F)
      );
      PartDefinition cube_r110 = bone6.addOrReplaceChild(
         "cube_r110",
         CubeListBuilder.create().texOffs(66, 59).addBox(-4.0F, -2.0F, -1.0F, 17.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(32.6F, -10.575F, 3.95F, -0.0675F, -0.1014F, -0.3339F)
      );
      PartDefinition cube_r111 = bone6.addOrReplaceChild(
         "cube_r111",
         CubeListBuilder.create().texOffs(66, 55).addBox(-4.0F, -2.0F, -1.0F, 17.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(17.1F, -9.575F, -1.55F, -1.2106F, -0.3451F, -0.0489F)
      );
      PartDefinition cube_r112 = bone6.addOrReplaceChild(
         "cube_r112",
         CubeListBuilder.create().texOffs(66, 47).addBox(-4.0F, -2.0F, -1.0F, 17.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(3.85F, -1.325F, -2.3F, -0.3463F, 0.0191F, -0.6597F)
      );
      PartDefinition cube_r113 = bone6.addOrReplaceChild(
         "cube_r113",
         CubeListBuilder.create().texOffs(44, 92).addBox(-1.5F, -2.0F, -6.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(30.575F, -7.375F, 25.525F, -0.8846F, -0.2637F, -2.2022F)
      );
      PartDefinition cube_r114 = bone6.addOrReplaceChild(
         "cube_r114",
         CubeListBuilder.create().texOffs(16, 92).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(24.075F, -7.625F, 17.525F, -0.5407F, -0.3949F, -1.4586F)
      );
      PartDefinition cube_r115 = bone6.addOrReplaceChild(
         "cube_r115",
         CubeListBuilder.create().texOffs(72, 78).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(16.075F, -9.875F, 9.525F, -0.8238F, -0.5705F, -2.1114F)
      );
      PartDefinition cube_r116 = bone6.addOrReplaceChild(
         "cube_r116",
         CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -2.0F, -8.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(6.675F, -4.225F, 1.925F, -0.8722F, 0.5403F, -1.8584F)
      );
      PartDefinition cube_r117 = bone6.addOrReplaceChild(
         "cube_r117",
         CubeListBuilder.create().texOffs(40, 127).addBox(-1.5F, -5.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(33.925F, -6.575F, -22.725F, -0.7726F, -0.6363F, -1.2116F)
      );
      PartDefinition cube_r118 = bone6.addOrReplaceChild(
         "cube_r118",
         CubeListBuilder.create().texOffs(32, 127).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(23.925F, -10.075F, -17.975F, -0.3631F, 0.5698F, -1.2284F)
      );
      PartDefinition cube_r119 = bone6.addOrReplaceChild(
         "cube_r119",
         CubeListBuilder.create().texOffs(24, 127).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(13.925F, -8.575F, -12.975F, -0.5662F, -0.5395F, -1.7792F)
      );
      PartDefinition cube_r120 = bone6.addOrReplaceChild(
         "cube_r120",
         CubeListBuilder.create().texOffs(16, 127).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(4.925F, -3.075F, -5.975F, -0.8205F, -0.5704F, -1.6446F)
      );
      PartDefinition cube_r121 = bone6.addOrReplaceChild(
         "cube_r121",
         CubeListBuilder.create().texOffs(104, 46).addBox(-1.5F, -2.0F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-32.0F, -6.175F, -18.475F, 0.8709F, -0.792F, 2.8265F)
      );
      PartDefinition cube_r122 = bone6.addOrReplaceChild(
         "cube_r122",
         CubeListBuilder.create().texOffs(100, 77).addBox(-1.0F, -2.0F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-25.25F, -5.675F, -14.725F, -0.897F, -0.2511F, -2.3316F)
      );
      PartDefinition cube_r123 = bone6.addOrReplaceChild(
         "cube_r123",
         CubeListBuilder.create().texOffs(30, 63).addBox(-1.0F, -2.0F, -8.0F, 2.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-15.0F, -6.925F, -9.225F, -1.1417F, -0.4532F, -1.6791F)
      );
      PartDefinition cube_r124 = bone6.addOrReplaceChild(
         "cube_r124",
         CubeListBuilder.create().texOffs(36, 48).addBox(-1.0F, -2.0F, -8.0F, 2.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-4.0F, -2.175F, -4.475F, -1.1973F, -0.0046F, -1.097F)
      );
      PartDefinition cube_r125 = bone6.addOrReplaceChild(
         "cube_r125",
         CubeListBuilder.create().texOffs(44, 106).addBox(-1.5F, -2.0F, -4.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-16.75F, -2.625F, -26.05F, -0.7015F, -0.1931F, -1.2088F)
      );
      PartDefinition cube_r126 = bone6.addOrReplaceChild(
         "cube_r126",
         CubeListBuilder.create().texOffs(22, 106).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-14.75F, -5.375F, -20.8F, -0.6237F, -0.7121F, 2.8138F)
      );
      PartDefinition cube_r127 = bone6.addOrReplaceChild(
         "cube_r127",
         CubeListBuilder.create().texOffs(36, 33).addBox(-1.0F, -2.0F, -8.0F, 2.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-6.75F, -6.625F, -12.3F, -0.6879F, -0.4115F, -1.7331F)
      );
      PartDefinition cube_r128 = bone6.addOrReplaceChild(
         "cube_r128",
         CubeListBuilder.create().texOffs(72, 92).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-0.75F, -2.5F, -5.0F, -0.7136F, -0.2177F, -1.0173F)
      );
      PartDefinition cube_r129 = bone6.addOrReplaceChild(
         "cube_r129",
         CubeListBuilder.create().texOffs(126, 129).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-1.0F, -1.75F, 0.0F, -0.6545F, 0.0F, -0.6545F)
      );
      PartDefinition cube_r130 = bone6.addOrReplaceChild(
         "cube_r130",
         CubeListBuilder.create().texOffs(96, 127).addBox(-1.5F, -5.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-4.55F, -13.075F, 30.9F, -1.7044F, -0.4735F, -0.2545F)
      );
      PartDefinition cube_r131 = bone6.addOrReplaceChild(
         "cube_r131",
         CubeListBuilder.create().texOffs(64, 127).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.05F, -15.325F, 19.9F, -1.4288F, 0.0278F, -2.8485F)
      );
      PartDefinition cube_r132 = bone6.addOrReplaceChild(
         "cube_r132",
         CubeListBuilder.create().texOffs(56, 127).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.95F, -10.575F, 11.15F, -1.087F, 0.8495F, -1.38F)
      );
      PartDefinition cube_r133 = bone6.addOrReplaceChild(
         "cube_r133",
         CubeListBuilder.create().texOffs(118, 114).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.85F, -1.825F, 0.225F, -1.3432F, 0.5833F, -0.9152F)
      );
      PartDefinition cube_r134 = bone6.addOrReplaceChild(
         "cube_r134",
         CubeListBuilder.create().texOffs(98, 92).addBox(-1.5F, 1.0F, -1.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-12.65F, -12.525F, 13.5F, 1.2853F, 0.9791F, -2.2604F)
      );
      PartDefinition cube_r135 = bone6.addOrReplaceChild(
         "cube_r135",
         CubeListBuilder.create().texOffs(44, 78).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-10.4F, -9.025F, 5.0F, 0.0301F, 0.5238F, -2.2098F)
      );
      PartDefinition cube_r136 = bone6.addOrReplaceChild(
         "cube_r136",
         CubeListBuilder.create().texOffs(0, 117).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-5.15F, -4.775F, 1.75F, 1.2787F, 0.7401F, -1.0303F)
      );
      PartDefinition cube_r137 = bone6.addOrReplaceChild(
         "cube_r137",
         CubeListBuilder.create().texOffs(104, 127).addBox(-1.5F, -7.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-29.675F, -1.225F, 11.15F, -1.7348F, 1.173F, 2.4045F)
      );
      PartDefinition cube_r138 = bone6.addOrReplaceChild(
         "cube_r138",
         CubeListBuilder.create().texOffs(128, 20).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-20.425F, -2.725F, 9.65F, -0.1032F, -0.6606F, -1.5576F)
      );
      PartDefinition cube_r139 = bone6.addOrReplaceChild(
         "cube_r139",
         CubeListBuilder.create().texOffs(48, 127).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-10.175F, -1.475F, 3.15F, -0.7408F, -0.3782F, -1.0557F)
      );
      PartDefinition cube_r140 = bone6.addOrReplaceChild(
         "cube_r140",
         CubeListBuilder.create().texOffs(128, 32).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-3.4F, -1.525F, -0.525F, -0.6545F, -0.9163F, -1.0908F)
      );
      PartDefinition cube_r141 = bone.addOrReplaceChild(
         "cube_r141",
         CubeListBuilder.create().texOffs(96, 127).addBox(-1.5F, -5.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.7044F, -0.4735F, -0.2545F)
      );
      PartDefinition cube_r142 = bone.addOrReplaceChild(
         "cube_r142",
         CubeListBuilder.create().texOffs(64, 127).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(2.5F, -2.25F, -11.0F, -1.4288F, 0.0278F, -2.8485F)
      );
      PartDefinition cube_r143 = bone.addOrReplaceChild(
         "cube_r143",
         CubeListBuilder.create().texOffs(56, 127).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(5.5F, 2.5F, -19.75F, -1.087F, 0.8495F, -1.38F)
      );
      PartDefinition cube_r144 = bone.addOrReplaceChild(
         "cube_r144",
         CubeListBuilder.create().texOffs(118, 114).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(5.4F, 11.25F, -30.675F, -1.3432F, 0.5833F, -0.9152F)
      );
      PartDefinition cube_r145 = bone.addOrReplaceChild(
         "cube_r145",
         CubeListBuilder.create().texOffs(96, 14).addBox(-1.5F, 1.0F, -1.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-8.45F, 10.8F, -12.65F, 0.7897F, 0.0924F, -1.1725F)
      );
      PartDefinition cube_r146 = bone.addOrReplaceChild(
         "cube_r146",
         CubeListBuilder.create().texOffs(66, 33).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-0.45F, 9.05F, -20.9F, 0.7897F, 0.0924F, -2.0888F)
      );
      PartDefinition cube_r147 = bone.addOrReplaceChild(
         "cube_r147",
         CubeListBuilder.create().texOffs(60, 117).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(1.65F, 10.8F, -26.725F, 0.1391F, 0.3369F, -2.2516F)
      );
      PartDefinition cube_r148 = bone.addOrReplaceChild(
         "cube_r148",
         CubeListBuilder.create().texOffs(112, 131).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(4.55F, 13.075F, -30.9F, -0.9015F, 0.3085F, -0.6931F)
      );
      PartDefinition cube_r149 = bone.addOrReplaceChild(
         "cube_r149",
         CubeListBuilder.create().texOffs(98, 92).addBox(-1.5F, 1.0F, -1.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-8.1F, 0.55F, -17.4F, 1.2853F, 0.9791F, -2.2604F)
      );
      PartDefinition cube_r150 = bone.addOrReplaceChild(
         "cube_r150",
         CubeListBuilder.create().texOffs(44, 78).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-5.85F, 4.05F, -25.9F, 0.0301F, 0.5238F, -2.2098F)
      );
      PartDefinition cube_r151 = bone.addOrReplaceChild(
         "cube_r151",
         CubeListBuilder.create().texOffs(0, 117).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-0.6F, 8.3F, -29.15F, 1.2787F, 0.7401F, -1.0303F)
      );
      PartDefinition cube_r152 = bone.addOrReplaceChild(
         "cube_r152",
         CubeListBuilder.create().texOffs(126, 129).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(3.55F, 11.325F, -30.9F, -0.6545F, 0.0F, -0.6545F)
      );
      PartDefinition cube_r153 = bone.addOrReplaceChild(
         "cube_r153",
         CubeListBuilder.create().texOffs(118, 62).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(4.9F, 11.55F, -35.675F, -0.3175F, 0.2351F, 0.6167F)
      );
      PartDefinition cube_r154 = bone.addOrReplaceChild(
         "cube_r154",
         CubeListBuilder.create().texOffs(72, 92).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(3.8F, 10.575F, -35.9F, -0.7136F, -0.2177F, -1.0173F)
      );
      PartDefinition cube_r155 = bone.addOrReplaceChild(
         "cube_r155",
         CubeListBuilder.create().texOffs(36, 33).addBox(-1.0F, -2.0F, -8.0F, 2.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.2F, 6.45F, -43.2F, -0.6879F, -0.4115F, -1.7331F)
      );
      PartDefinition cube_r156 = bone.addOrReplaceChild(
         "cube_r156",
         CubeListBuilder.create().texOffs(22, 106).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-10.2F, 7.7F, -51.7F, -0.6237F, -0.7121F, 2.8138F)
      );
      PartDefinition cube_r157 = bone.addOrReplaceChild(
         "cube_r157",
         CubeListBuilder.create().texOffs(44, 106).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-12.2F, 10.45F, -56.95F, -0.7015F, -0.1931F, -1.2088F)
      );
      PartDefinition cube_r158 = bone.addOrReplaceChild(
         "cube_r158",
         CubeListBuilder.create().texOffs(16, 78).addBox(-1.5F, -2.0F, -11.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(13.9F, -1.275F, -56.85F, 0.3394F, -0.069F, 1.1009F)
      );
      PartDefinition cube_r159 = bone.addOrReplaceChild(
         "cube_r159",
         CubeListBuilder.create().texOffs(72, 0).addBox(-1.0F, -2.0F, -11.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(7.4F, 1.725F, -49.1F, -0.5469F, 0.5418F, 1.7232F)
      );
      PartDefinition cube_r160 = bone.addOrReplaceChild(
         "cube_r160",
         CubeListBuilder.create().texOffs(72, 105).addBox(-1.0F, -2.0F, -9.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(4.9F, 8.475F, -42.1F, -0.5469F, 0.5418F, 1.0251F)
      );
      PartDefinition cube_r161 = bone2.addOrReplaceChild(
         "cube_r161",
         CubeListBuilder.create().texOffs(96, 127).addBox(-1.5F, -5.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.7044F, -0.4735F, -0.2545F)
      );
      PartDefinition cube_r162 = bone2.addOrReplaceChild(
         "cube_r162",
         CubeListBuilder.create().texOffs(64, 127).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(2.5F, -2.25F, -11.0F, -1.4288F, 0.0278F, -2.8485F)
      );
      PartDefinition cube_r163 = bone2.addOrReplaceChild(
         "cube_r163",
         CubeListBuilder.create().texOffs(56, 127).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(5.5F, 2.5F, -19.75F, -1.087F, 0.8495F, -1.38F)
      );
      PartDefinition cube_r164 = bone2.addOrReplaceChild(
         "cube_r164",
         CubeListBuilder.create().texOffs(118, 114).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(5.4F, 11.25F, -30.675F, -1.3432F, 0.5833F, -0.9152F)
      );
      PartDefinition cube_r165 = bone2.addOrReplaceChild(
         "cube_r165",
         CubeListBuilder.create().texOffs(96, 14).addBox(-1.5F, 1.0F, -1.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-8.45F, 10.8F, -12.65F, 0.7897F, 0.0924F, -1.1725F)
      );
      PartDefinition cube_r166 = bone2.addOrReplaceChild(
         "cube_r166",
         CubeListBuilder.create().texOffs(66, 33).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-0.45F, 9.05F, -20.9F, 0.7897F, 0.0924F, -2.0888F)
      );
      PartDefinition cube_r167 = bone2.addOrReplaceChild(
         "cube_r167",
         CubeListBuilder.create().texOffs(60, 117).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(1.65F, 10.8F, -26.725F, 0.1391F, 0.3369F, -2.2516F)
      );
      PartDefinition cube_r168 = bone2.addOrReplaceChild(
         "cube_r168",
         CubeListBuilder.create().texOffs(112, 131).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(4.55F, 13.075F, -30.9F, -0.9015F, 0.3085F, -0.6931F)
      );
      PartDefinition cube_r169 = bone2.addOrReplaceChild(
         "cube_r169",
         CubeListBuilder.create().texOffs(98, 92).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-8.1F, 0.55F, -17.4F, 1.2853F, 0.9791F, -2.2604F)
      );
      PartDefinition cube_r170 = bone2.addOrReplaceChild(
         "cube_r170",
         CubeListBuilder.create().texOffs(44, 78).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-5.85F, 4.05F, -25.9F, 0.0301F, 0.5238F, -2.2098F)
      );
      PartDefinition cube_r171 = bone2.addOrReplaceChild(
         "cube_r171",
         CubeListBuilder.create().texOffs(0, 117).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-0.6F, 8.3F, -29.15F, 1.2787F, 0.7401F, -1.0303F)
      );
      PartDefinition cube_r172 = bone2.addOrReplaceChild(
         "cube_r172",
         CubeListBuilder.create().texOffs(126, 129).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(3.55F, 11.325F, -30.9F, -0.6545F, 0.0F, -0.6545F)
      );
      PartDefinition cube_r173 = bone2.addOrReplaceChild(
         "cube_r173",
         CubeListBuilder.create().texOffs(118, 62).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(4.9F, 11.55F, -35.675F, -0.3175F, 0.2351F, 0.6167F)
      );
      PartDefinition cube_r174 = bone2.addOrReplaceChild(
         "cube_r174",
         CubeListBuilder.create().texOffs(72, 92).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(3.8F, 10.575F, -35.9F, -0.7136F, -0.2177F, -1.0173F)
      );
      PartDefinition cube_r175 = bone2.addOrReplaceChild(
         "cube_r175",
         CubeListBuilder.create().texOffs(36, 33).addBox(-1.0F, -2.0F, -8.0F, 2.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.2F, 6.45F, -43.2F, -0.6879F, -0.4115F, -1.7331F)
      );
      PartDefinition cube_r176 = bone2.addOrReplaceChild(
         "cube_r176",
         CubeListBuilder.create().texOffs(22, 106).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-10.2F, 7.7F, -51.7F, -0.6237F, -0.7121F, 2.8138F)
      );
      PartDefinition cube_r177 = bone2.addOrReplaceChild(
         "cube_r177",
         CubeListBuilder.create().texOffs(44, 106).addBox(-1.5F, -2.0F, -4.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-12.2F, 10.45F, -56.95F, -0.7015F, -0.1931F, -1.2088F)
      );
      PartDefinition cube_r178 = bone2.addOrReplaceChild(
         "cube_r178",
         CubeListBuilder.create().texOffs(16, 78).addBox(-1.5F, -2.0F, -11.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(13.9F, -1.275F, -56.85F, 0.3394F, -0.069F, 1.1009F)
      );
      PartDefinition cube_r179 = bone2.addOrReplaceChild(
         "cube_r179",
         CubeListBuilder.create().texOffs(72, 0).addBox(-1.0F, -2.0F, -11.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(7.4F, 1.725F, -49.1F, -0.5469F, 0.5418F, 1.7232F)
      );
      PartDefinition cube_r180 = bone2.addOrReplaceChild(
         "cube_r180",
         CubeListBuilder.create().texOffs(72, 105).addBox(-1.0F, -2.0F, -9.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(4.9F, 8.475F, -42.1F, -0.5469F, 0.5418F, 1.0251F)
      );
      PartDefinition cube_r181 = bone3.addOrReplaceChild(
         "cube_r181",
         CubeListBuilder.create().texOffs(96, 127).addBox(-1.5F, -5.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.7044F, -0.4735F, -0.2545F)
      );
      PartDefinition cube_r182 = bone3.addOrReplaceChild(
         "cube_r182",
         CubeListBuilder.create().texOffs(64, 127).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(2.5F, -2.25F, -11.0F, -1.4288F, 0.0278F, -2.8485F)
      );
      PartDefinition cube_r183 = bone3.addOrReplaceChild(
         "cube_r183",
         CubeListBuilder.create().texOffs(56, 127).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(5.5F, 2.5F, -19.75F, -1.087F, 0.8495F, -1.38F)
      );
      PartDefinition cube_r184 = bone3.addOrReplaceChild(
         "cube_r184",
         CubeListBuilder.create().texOffs(118, 114).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(5.4F, 11.25F, -30.675F, -1.3432F, 0.5833F, -0.9152F)
      );
      PartDefinition cube_r185 = bone3.addOrReplaceChild(
         "cube_r185",
         CubeListBuilder.create().texOffs(96, 14).addBox(-1.5F, 1.0F, -1.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-8.45F, 10.8F, -12.65F, 0.7897F, 0.0924F, -1.1725F)
      );
      PartDefinition cube_r186 = bone3.addOrReplaceChild(
         "cube_r186",
         CubeListBuilder.create().texOffs(66, 33).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-0.45F, 9.05F, -20.9F, 0.7897F, 0.0924F, -2.0888F)
      );
      PartDefinition cube_r187 = bone3.addOrReplaceChild(
         "cube_r187",
         CubeListBuilder.create().texOffs(60, 117).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(1.65F, 10.8F, -26.725F, 0.1391F, 0.3369F, -2.2516F)
      );
      PartDefinition cube_r188 = bone3.addOrReplaceChild(
         "cube_r188",
         CubeListBuilder.create().texOffs(112, 131).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(4.55F, 13.075F, -30.9F, -0.9015F, 0.3085F, -0.6931F)
      );
      PartDefinition cube_r189 = bone3.addOrReplaceChild(
         "cube_r189",
         CubeListBuilder.create().texOffs(98, 92).addBox(-1.5F, 1.0F, -1.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-8.1F, 0.55F, -17.4F, 1.2853F, 0.9791F, -2.2604F)
      );
      PartDefinition cube_r190 = bone3.addOrReplaceChild(
         "cube_r190",
         CubeListBuilder.create().texOffs(44, 78).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-5.85F, 4.05F, -25.9F, 0.0301F, 0.5238F, -2.2098F)
      );
      PartDefinition cube_r191 = bone3.addOrReplaceChild(
         "cube_r191",
         CubeListBuilder.create().texOffs(0, 117).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-0.6F, 8.3F, -29.15F, 1.2787F, 0.7401F, -1.0303F)
      );
      PartDefinition cube_r192 = bone3.addOrReplaceChild(
         "cube_r192",
         CubeListBuilder.create().texOffs(126, 129).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(3.55F, 11.325F, -30.9F, -0.6545F, 0.0F, -0.6545F)
      );
      PartDefinition cube_r193 = bone3.addOrReplaceChild(
         "cube_r193",
         CubeListBuilder.create().texOffs(118, 62).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(4.9F, 11.55F, -35.675F, -0.3175F, 0.2351F, 0.6167F)
      );
      PartDefinition cube_r194 = bone3.addOrReplaceChild(
         "cube_r194",
         CubeListBuilder.create().texOffs(72, 92).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(3.8F, 10.575F, -35.9F, -0.7136F, -0.2177F, -1.0173F)
      );
      PartDefinition cube_r195 = bone3.addOrReplaceChild(
         "cube_r195",
         CubeListBuilder.create().texOffs(36, 33).addBox(-1.0F, -2.0F, -8.0F, 2.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.2F, 6.45F, -43.2F, -0.6879F, -0.4115F, -1.7331F)
      );
      PartDefinition cube_r196 = bone3.addOrReplaceChild(
         "cube_r196",
         CubeListBuilder.create().texOffs(22, 106).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-10.2F, 7.7F, -51.7F, -0.6237F, -0.7121F, 2.8138F)
      );
      PartDefinition cube_r197 = bone3.addOrReplaceChild(
         "cube_r197",
         CubeListBuilder.create().texOffs(44, 106).addBox(-1.5F, -2.0F, -4.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-12.2F, 10.45F, -56.95F, -0.7015F, -0.1931F, -1.2088F)
      );
      PartDefinition cube_r198 = bone3.addOrReplaceChild(
         "cube_r198",
         CubeListBuilder.create().texOffs(16, 78).addBox(-1.5F, -2.0F, -11.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(13.9F, -1.275F, -56.85F, 0.3394F, -0.069F, 1.1009F)
      );
      PartDefinition cube_r199 = bone3.addOrReplaceChild(
         "cube_r199",
         CubeListBuilder.create().texOffs(72, 0).addBox(-1.0F, -2.0F, -11.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(7.4F, 1.725F, -49.1F, -0.5469F, 0.5418F, 1.7232F)
      );
      PartDefinition cube_r200 = bone3.addOrReplaceChild(
         "cube_r200",
         CubeListBuilder.create().texOffs(72, 105).addBox(-1.0F, -2.0F, -9.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(4.9F, 8.475F, -42.1F, -0.5469F, 0.5418F, 1.0251F)
      );
      PartDefinition cube_r201 = bone4.addOrReplaceChild(
         "cube_r201",
         CubeListBuilder.create().texOffs(96, 127).addBox(-1.5F, -5.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.7044F, -0.4735F, -0.2545F)
      );
      PartDefinition cube_r202 = bone4.addOrReplaceChild(
         "cube_r202",
         CubeListBuilder.create().texOffs(64, 127).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(2.5F, -2.25F, -11.0F, -1.4288F, 0.0278F, -2.8485F)
      );
      PartDefinition cube_r203 = bone4.addOrReplaceChild(
         "cube_r203",
         CubeListBuilder.create().texOffs(56, 127).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(5.5F, 2.5F, -19.75F, -1.087F, 0.8495F, -1.38F)
      );
      PartDefinition cube_r204 = bone4.addOrReplaceChild(
         "cube_r204",
         CubeListBuilder.create().texOffs(118, 114).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(5.4F, 11.25F, -30.675F, -1.3432F, 0.5833F, -0.9152F)
      );
      PartDefinition cube_r205 = bone4.addOrReplaceChild(
         "cube_r205",
         CubeListBuilder.create().texOffs(96, 14).addBox(-1.5F, 1.0F, -1.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-8.45F, 10.8F, -12.65F, 0.7897F, 0.0924F, -1.1725F)
      );
      PartDefinition cube_r206 = bone4.addOrReplaceChild(
         "cube_r206",
         CubeListBuilder.create().texOffs(66, 33).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-0.45F, 9.05F, -20.9F, 0.7897F, 0.0924F, -2.0888F)
      );
      PartDefinition cube_r207 = bone4.addOrReplaceChild(
         "cube_r207",
         CubeListBuilder.create().texOffs(60, 117).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(1.65F, 10.8F, -26.725F, 0.1391F, 0.3369F, -2.2516F)
      );
      PartDefinition cube_r208 = bone4.addOrReplaceChild(
         "cube_r208",
         CubeListBuilder.create().texOffs(112, 131).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(4.55F, 13.075F, -30.9F, -0.9015F, 0.3085F, -0.6931F)
      );
      PartDefinition cube_r209 = bone4.addOrReplaceChild(
         "cube_r209",
         CubeListBuilder.create().texOffs(98, 92).addBox(-1.5F, 1.0F, -1.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-8.1F, 0.55F, -17.4F, 1.2853F, 0.9791F, -2.2604F)
      );
      PartDefinition cube_r210 = bone4.addOrReplaceChild(
         "cube_r210",
         CubeListBuilder.create().texOffs(44, 78).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-5.85F, 4.05F, -25.9F, 0.0301F, 0.5238F, -2.2098F)
      );
      PartDefinition cube_r211 = bone4.addOrReplaceChild(
         "cube_r211",
         CubeListBuilder.create().texOffs(0, 117).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-0.6F, 8.3F, -29.15F, 1.2787F, 0.7401F, -1.0303F)
      );
      PartDefinition cube_r212 = bone4.addOrReplaceChild(
         "cube_r212",
         CubeListBuilder.create().texOffs(126, 129).addBox(-1.0F, -5.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(3.55F, 11.325F, -30.9F, -0.6545F, 0.0F, -0.6545F)
      );
      PartDefinition cube_r213 = bone4.addOrReplaceChild(
         "cube_r213",
         CubeListBuilder.create().texOffs(118, 62).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(4.9F, 11.55F, -35.675F, -0.3175F, 0.2351F, 0.6167F)
      );
      PartDefinition cube_r214 = bone4.addOrReplaceChild(
         "cube_r214",
         CubeListBuilder.create().texOffs(72, 92).addBox(-1.0F, -2.0F, -6.0F, 2.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(3.8F, 10.575F, -35.9F, -0.7136F, -0.2177F, -1.0173F)
      );
      PartDefinition cube_r215 = bone4.addOrReplaceChild(
         "cube_r215",
         CubeListBuilder.create().texOffs(36, 33).addBox(-1.0F, -2.0F, -8.0F, 2.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.2F, 6.45F, -43.2F, -0.6879F, -0.4115F, -1.7331F)
      );
      PartDefinition cube_r216 = bone4.addOrReplaceChild(
         "cube_r216",
         CubeListBuilder.create().texOffs(22, 106).addBox(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-10.2F, 7.7F, -51.7F, -0.6237F, -0.7121F, 2.8138F)
      );
      PartDefinition cube_r217 = bone4.addOrReplaceChild(
         "cube_r217",
         CubeListBuilder.create().texOffs(44, 106).addBox(-1.5F, -2.0F, -4.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-12.2F, 10.45F, -56.95F, -0.7015F, -0.1931F, -1.2088F)
      );
      PartDefinition cube_r218 = bone4.addOrReplaceChild(
         "cube_r218",
         CubeListBuilder.create().texOffs(16, 78).addBox(-1.5F, -2.0F, -11.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(13.9F, -1.275F, -56.85F, 0.3394F, -0.069F, 1.1009F)
      );
      PartDefinition cube_r219 = bone4.addOrReplaceChild(
         "cube_r219",
         CubeListBuilder.create().texOffs(72, 0).addBox(-1.0F, -2.0F, -11.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(7.4F, 1.725F, -49.1F, -0.5469F, 0.5418F, 1.7232F)
      );
      PartDefinition cube_r220 = bone4.addOrReplaceChild(
         "cube_r220",
         CubeListBuilder.create().texOffs(72, 105).addBox(-1.0F, -2.0F, -9.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(4.9F, 8.475F, -42.1F, -0.5469F, 0.5418F, 1.0251F)
      );
      PartDefinition bb_main = partdefinition.addOrReplaceChild(
         "bb_main",
         CubeListBuilder.create()
            .texOffs(0, 69)
            .addBox(-2.0F, -22.0F, -2.0F, 4.0F, 22.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(0, 95)
            .addBox(-2.0F, -22.675F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 24.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 256, 256);
   }
}
