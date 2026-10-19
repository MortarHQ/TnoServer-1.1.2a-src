package com.github.b4ndithelps.tennogamenolife.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class XiubiArmorModel extends EntityModel<LivingEntity> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
      ResourceLocation.fromNamespaceAndPath("tennogamenolife", "xiubi_armor"), "main"
   );
   private final ModelPart bone4;
   private final ModelPart bone3;
   private final ModelPart bone2;
   private final ModelPart bone5;
   private final ModelPart bone7;
   private final ModelPart bone8;
   private final ModelPart youyi;
   private final ModelPart bone10;
   private final ModelPart zuoyi;
   private final ModelPart bone6;

   public XiubiArmorModel(ModelPart root) {
      this.bone4 = root.getChild("bone4");
      this.bone3 = this.bone4.getChild("bone3");
      this.bone2 = this.bone4.getChild("bone2");
      this.bone5 = this.bone4.getChild("bone5");
      this.bone7 = root.getChild("bone7");
      this.bone8 = root.getChild("bone8");
      this.youyi = root.getChild("youyi");
      this.bone10 = this.youyi.getChild("bone10");
      this.zuoyi = root.getChild("zuoyi");
      this.bone6 = this.zuoyi.getChild("bone6");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition bone4 = partdefinition.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
      PartDefinition bone3 = bone4.addOrReplaceChild(
         "bone3",
         CubeListBuilder.create()
            .texOffs(40, 19)
            .addBox(-5.0F, -31.0F, -2.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(48, 9)
            .addBox(-5.3F, -30.6F, -1.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      bone3.addOrReplaceChild(
         "cube_r1",
         CubeListBuilder.create().texOffs(10, 53).addBox(0.0F, -3.0F, 0.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-5.0F, -33.1F, -1.7F, -1.9722F, 0.0F, 0.0F)
      );
      bone3.addOrReplaceChild(
         "cube_r2",
         CubeListBuilder.create().texOffs(42, 44).addBox(0.0F, -3.0F, -1.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-5.0F, -27.1F, -2.4F, -1.117F, 0.0F, 0.0F)
      );
      bone3.addOrReplaceChild(
         "cube_r3",
         CubeListBuilder.create().texOffs(28, 49).addBox(0.0F, -4.0F, -2.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-5.0F, -35.6F, 1.2F, 2.6878F, 0.0F, 0.0F)
      );
      bone3.addOrReplaceChild(
         "cube_r4",
         CubeListBuilder.create().texOffs(54, 25).addBox(0.0F, -5.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-5.0F, -39.4F, 3.1F, 2.6878F, 0.0F, 0.0F)
      );
      bone3.addOrReplaceChild(
         "cube_r5",
         CubeListBuilder.create().texOffs(54, 22).addBox(0.0F, -4.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-5.0F, -36.7F, 5.8F, 2.6878F, 0.0F, 0.0F)
      );
      bone3.addOrReplaceChild(
         "cube_r6",
         CubeListBuilder.create().texOffs(48, 35).addBox(0.0F, -4.0F, -2.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-5.0F, -33.9F, 4.4F, 2.6878F, 0.0F, 0.0F)
      );
      bone3.addOrReplaceChild(
         "cube_r7",
         CubeListBuilder.create().texOffs(52, 44).addBox(0.0F, -3.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-5.0F, -29.0F, 5.8F, 0.6458F, 0.0F, 0.0F)
      );
      bone3.addOrReplaceChild(
         "cube_r8",
         CubeListBuilder.create().texOffs(4, 51).addBox(0.0F, -3.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-5.0F, -27.6F, 3.6F, 0.192F, 0.0F, 0.0F)
      );
      bone3.addOrReplaceChild(
         "cube_r9",
         CubeListBuilder.create().texOffs(32, 44).addBox(0.0F, -3.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-5.0F, -26.3F, -0.3F, -0.6109F, 0.0F, 0.0F)
      );
      bone3.addOrReplaceChild(
         "cube_r10",
         CubeListBuilder.create().texOffs(48, 4).addBox(0.0F, -2.0F, -1.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-5.0F, -27.4F, -0.6F, 0.8901F, 0.0F, 0.0F)
      );
      PartDefinition bone2 = bone4.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, -6.0F));
      bone2.addOrReplaceChild(
         "cube_r11",
         CubeListBuilder.create().texOffs(4, 54).addBox(-2.0F, 0.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-4.6F, 0.6F, 11.9F, 0.9849F, 0.1616F, 0.0167F)
      );
      bone2.addOrReplaceChild(
         "cube_r12",
         CubeListBuilder.create().texOffs(50, 49).addBox(1.0F, -3.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-7.1F, -1.7F, 11.8F, 0.2057F, -0.0397F, 0.1879F)
      );
      bone2.addOrReplaceChild(
         "cube_r13",
         CubeListBuilder.create().texOffs(50, 19).addBox(1.0F, -3.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-5.7F, -6.2F, 10.9F, 0.1888F, -0.0913F, 0.4451F)
      );
      bone2.addOrReplaceChild(
         "cube_r14",
         CubeListBuilder.create().texOffs(46, 49).addBox(1.0F, -3.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-3.2F, -9.8F, 9.1F, 0.553F, -0.3524F, 0.5099F)
      );
      bone2.addOrReplaceChild(
         "cube_r15",
         CubeListBuilder.create().texOffs(22, 53).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-1.0F, -12.3F, 9.0F, 0.0F, 0.0F, -0.829F)
      );
      bone2.addOrReplaceChild(
         "cube_r16",
         CubeListBuilder.create().texOffs(38, 49).addBox(-2.0F, -3.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(5.7F, -6.2F, 10.9F, 0.1888F, 0.0913F, -0.4451F)
      );
      bone2.addOrReplaceChild(
         "cube_r17",
         CubeListBuilder.create().texOffs(34, 49).addBox(-2.0F, -3.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(3.2F, -9.8F, 9.1F, 0.553F, 0.3524F, -0.5099F)
      );
      bone2.addOrReplaceChild(
         "cube_r18",
         CubeListBuilder.create().texOffs(42, 49).addBox(-2.0F, -3.0F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(7.1F, -1.7F, 11.8F, 0.2057F, 0.0397F, -0.1879F)
      );
      bone2.addOrReplaceChild(
         "cube_r19",
         CubeListBuilder.create().texOffs(0, 51).addBox(1.0F, 0.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(4.6F, 0.6F, 11.9F, 0.9849F, -0.1616F, -0.0167F)
      );
      bone2.addOrReplaceChild(
         "cube_r20",
         CubeListBuilder.create().texOffs(16, 53).addBox(0.0F, 0.0F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(1.0F, -12.3F, 9.0F, 0.0F, 0.0F, 0.829F)
      );
      bone2.addOrReplaceChild(
         "cube_r21",
         CubeListBuilder.create().texOffs(50, 32).addBox(0.0F, 0.0F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(1.0F, -13.3F, 9.0F, 0.0F, 0.0F, 1.5708F)
      );
      PartDefinition bone5 = bone4.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
      bone5.addOrReplaceChild(
         "cube_r22",
         CubeListBuilder.create().texOffs(20, 48).addBox(-1.0F, -2.0F, -2.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(4.7F, -25.3F, -1.3F, 1.0976F, 0.3175F, -0.9523F)
      );
      bone5.addOrReplaceChild(
         "cube_r23",
         CubeListBuilder.create().texOffs(12, 48).addBox(0.0F, -2.0F, -2.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-4.7F, -25.3F, -1.3F, 1.0976F, -0.3175F, 0.9523F)
      );
      partdefinition.addOrReplaceChild(
         "bone7",
         CubeListBuilder.create().texOffs(20, 16).addBox(1.6F, -21.9F, 1.4F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-0.6F, 24.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "bone8",
         CubeListBuilder.create().texOffs(20, 16).mirror().addBox(-4.6F, -21.9F, 1.4F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offset(0.6F, 24.0F, 0.0F)
      );
      PartDefinition youyi = partdefinition.addOrReplaceChild(
         "youyi",
         CubeListBuilder.create().texOffs(40, 29).mirror().addBox(-7.3163F, 0.1432F, 3.0197F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(-2.4F, 3.9F, 2.0F, 0.0171F, 0.121F, 0.3326F)
      );
      youyi.addOrReplaceChild(
         "cube_r24",
         CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-5.0F, -4.0F, 0.0F, 7.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(-11.8163F, 2.8432F, 3.0197F, 0.0F, 0.0F, -0.733F)
      );
      youyi.addOrReplaceChild(
         "cube_r25",
         CubeListBuilder.create().texOffs(32, 32).mirror().addBox(-5.0F, -4.0F, 0.0F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(-10.4163F, 4.2432F, 3.0197F, 0.0F, 0.0F, -0.733F)
      );
      youyi.addOrReplaceChild(
         "cube_r26",
         CubeListBuilder.create().texOffs(48, 2).mirror().addBox(-3.0F, -3.0F, 0.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(-13.0163F, 7.8432F, 3.0197F, 0.0F, 0.0F, -0.733F)
      );
      youyi.addOrReplaceChild(
         "cube_r27",
         CubeListBuilder.create().texOffs(0, 48).mirror().addBox(-4.0F, -4.0F, 0.0F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(-16.2163F, 6.8432F, 3.0197F, 0.0F, 0.0F, -0.733F)
      );
      youyi.addOrReplaceChild(
         "cube_r28",
         CubeListBuilder.create().texOffs(54, 19).mirror().addBox(-5.0F, -2.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(-7.5163F, 0.9432F, 3.0197F, 0.0F, 0.0F, 0.4363F)
      );
      youyi.addOrReplaceChild(
         "cube_r29",
         CubeListBuilder.create().texOffs(54, 28).mirror().addBox(-5.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(-8.6163F, 1.4432F, 3.0197F, 0.0F, 0.0F, 0.4363F)
      );
      youyi.addOrReplaceChild(
         "cube_r30",
         CubeListBuilder.create().texOffs(32, 41).mirror().addBox(-5.0F, -2.0F, 0.0F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(-8.0163F, 1.7432F, 3.0197F, 0.0F, 0.0F, 0.4363F)
      );
      youyi.addOrReplaceChild(
         "cube_r31",
         CubeListBuilder.create().texOffs(40, 26).mirror().addBox(-5.0F, -2.0F, 0.0F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(-0.0163F, 0.5432F, -0.9803F, -0.4644F, 1.0351F, -0.5862F)
      );
      PartDefinition bone10 = youyi.addOrReplaceChild("bone10", CubeListBuilder.create(), PartPose.offset(-11.9163F, -1.5568F, 3.0197F));
      bone10.addOrReplaceChild(
         "cube_r32",
         CubeListBuilder.create().texOffs(46, 41).mirror().addBox(-4.0F, -4.0F, 0.0F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(-7.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.0349F)
      );
      bone10.addOrReplaceChild(
         "cube_r33",
         CubeListBuilder.create().texOffs(32, 38).mirror().addBox(-5.0F, -4.0F, 0.0F, 7.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(-1.0F, -0.8F, 0.0F, 0.0F, 0.0F, 0.0349F)
      );
      bone10.addOrReplaceChild(
         "cube_r34",
         CubeListBuilder.create().texOffs(32, 35).mirror().addBox(-5.0F, -4.0F, 0.0F, 7.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(-1.1F, 1.2F, 0.0F, 0.0F, 0.0F, 0.0349F)
      );
      bone10.addOrReplaceChild(
         "cube_r35",
         CubeListBuilder.create().texOffs(48, 0).mirror().addBox(-3.0F, -3.0F, 0.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(-1.1F, 2.2F, 0.0F, 0.0F, 0.0F, 0.0349F)
      );
      PartDefinition zuoyi = partdefinition.addOrReplaceChild(
         "zuoyi",
         CubeListBuilder.create().texOffs(40, 29).addBox(1.3849F, 0.0537F, 3.3152F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(2.4F, 4.0F, 1.7F, 0.0171F, -0.121F, -0.3326F)
      );
      zuoyi.addOrReplaceChild(
         "cube_r36",
         CubeListBuilder.create().texOffs(40, 16).addBox(-2.0F, -4.0F, 0.0F, 7.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(11.8849F, 2.7537F, 3.3152F, 0.0F, 0.0F, 0.733F)
      );
      zuoyi.addOrReplaceChild(
         "cube_r37",
         CubeListBuilder.create().texOffs(32, 32).addBox(-3.0F, -4.0F, 0.0F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(10.4849F, 4.1537F, 3.3152F, 0.0F, 0.0F, 0.733F)
      );
      zuoyi.addOrReplaceChild(
         "cube_r38",
         CubeListBuilder.create().texOffs(48, 2).addBox(-2.0F, -3.0F, 0.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(13.0849F, 7.7537F, 3.3152F, 0.0F, 0.0F, 0.733F)
      );
      zuoyi.addOrReplaceChild(
         "cube_r39",
         CubeListBuilder.create().texOffs(0, 48).addBox(-1.0F, -4.0F, 0.0F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(16.2849F, 6.7537F, 3.3152F, 0.0F, 0.0F, 0.733F)
      );
      zuoyi.addOrReplaceChild(
         "cube_r40",
         CubeListBuilder.create().texOffs(54, 19).addBox(3.0F, -2.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(7.5849F, 0.8537F, 3.3152F, 0.0F, 0.0F, -0.4363F)
      );
      zuoyi.addOrReplaceChild(
         "cube_r41",
         CubeListBuilder.create().texOffs(54, 28).addBox(4.0F, -2.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(8.6849F, 1.3537F, 3.3152F, 0.0F, 0.0F, -0.4363F)
      );
      zuoyi.addOrReplaceChild(
         "cube_r42",
         CubeListBuilder.create().texOffs(32, 41).addBox(-1.0F, -2.0F, 0.0F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(8.0849F, 1.6537F, 3.3152F, 0.0F, 0.0F, -0.4363F)
      );
      zuoyi.addOrReplaceChild(
         "cube_r43",
         CubeListBuilder.create().texOffs(40, 26).addBox(-1.0F, -2.0F, 0.0F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0849F, 0.4537F, -0.6848F, -0.4644F, -1.0351F, 0.5862F)
      );
      PartDefinition bone6 = zuoyi.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offset(11.0849F, -1.4463F, 3.3152F));
      bone6.addOrReplaceChild(
         "cube_r44",
         CubeListBuilder.create().texOffs(48, 0).addBox(-2.0F, -3.0F, 0.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(2.0F, 2.0F, 0.0F, 0.0F, 0.0F, -0.0349F)
      );
      bone6.addOrReplaceChild(
         "cube_r45",
         CubeListBuilder.create().texOffs(32, 38).addBox(-2.0F, -4.0F, 0.0F, 7.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(1.9F, -1.0F, 0.0F, 0.0F, 0.0F, -0.0349F)
      );
      bone6.addOrReplaceChild(
         "cube_r46",
         CubeListBuilder.create().texOffs(32, 35).addBox(-2.0F, -4.0F, 0.0F, 7.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(2.0F, 1.0F, 0.0F, 0.0F, 0.0F, -0.0349F)
      );
      bone6.addOrReplaceChild(
         "cube_r47",
         CubeListBuilder.create().texOffs(46, 41).addBox(-1.0F, -4.0F, 0.0F, 5.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(7.9F, -1.2F, 0.0F, 0.0F, 0.0F, -0.0349F)
      );
      return LayerDefinition.create(meshdefinition, 64, 64);
   }

   public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.resetTransforms();
   }

   public void setupAnimForPlayer() {
      this.setupAnimForPlayer(0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
   }

   public void setupAnimForPlayer(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.resetTransforms();
   }

   public void resetTransforms() {
      this.zuoyi.xRot = 0.0F;
      this.zuoyi.yRot = 0.0F;
      this.zuoyi.zRot = 0.0F;
      this.youyi.xRot = 0.0F;
      this.youyi.yRot = 0.0F;
      this.youyi.zRot = 0.0F;
      this.bone10.xRot = 0.0F;
      this.bone10.yRot = 0.0F;
      this.bone10.zRot = 0.0F;
      this.bone6.xRot = 0.0F;
      this.bone6.yRot = 0.0F;
      this.bone6.zRot = 0.0F;
      this.zuoyi.xScale = 1.0F;
      this.zuoyi.yScale = 1.0F;
      this.zuoyi.zScale = 1.0F;
      this.youyi.xScale = 1.0F;
      this.youyi.yScale = 1.0F;
      this.youyi.zScale = 1.0F;
   }

   public void applyIdleAnimation(int timer) {
      float time = timer * 0.05F;
      float cycle = time % 8.0F;
      if (cycle <= 2.0F) {
         this.zuoyi.zRot = (float)Math.toRadians(21.0F * (cycle / 2.0F));
      } else if (cycle <= 6.0F) {
         float progress = (cycle - 2.0F) / 4.0F;
         this.zuoyi.zRot = (float)Math.toRadians(21.0F - 31.0F * progress);
      } else {
         float progress = (cycle - 6.0F) / 2.0F;
         this.zuoyi.zRot = (float)Math.toRadians(-10.0F + 10.0F * progress);
      }

      if (cycle <= 2.0F) {
         this.youyi.zRot = (float)Math.toRadians(-21.0F * (cycle / 2.0F));
      } else if (cycle <= 6.0F) {
         float progress = (cycle - 2.0F) / 4.0F;
         this.youyi.zRot = (float)Math.toRadians(-21.0F + 31.0F * progress);
      } else {
         float progress = (cycle - 6.0F) / 2.0F;
         this.youyi.zRot = (float)Math.toRadians(10.0F - 10.0F * progress);
      }

      if (cycle <= 2.0F) {
         this.bone10.zRot = (float)Math.toRadians(-27.5F * (cycle / 2.0F));
      } else if (cycle <= 5.0F) {
         float progress = (cycle - 2.0F) / 3.0F;
         this.bone10.zRot = (float)Math.toRadians(-27.5F + 27.5F * progress);
      } else if (cycle <= 6.5F) {
         float progress = (cycle - 5.0F) / 1.5F;
         this.bone10.zRot = (float)Math.toRadians(-12.5F * progress);
      } else {
         float progress = (cycle - 6.5F) / 1.5F;
         this.bone10.zRot = (float)Math.toRadians(-12.5F + 12.5F * progress);
      }

      if (cycle <= 2.0F) {
         this.bone6.zRot = (float)Math.toRadians(27.5F * (cycle / 2.0F));
      } else if (cycle <= 5.0F) {
         float progress = (cycle - 2.0F) / 3.0F;
         this.bone6.zRot = (float)Math.toRadians(27.5F - 27.5F * progress);
      } else if (cycle <= 6.5F) {
         float progress = (cycle - 5.0F) / 1.5F;
         this.bone6.zRot = (float)Math.toRadians(12.5F * progress);
      } else {
         float progress = (cycle - 6.5F) / 1.5F;
         this.bone6.zRot = (float)Math.toRadians(12.5F - 12.5F * progress);
      }
   }

   public void applyWeaponAnimation(int timer) {
      float time = timer * 0.05F;
      if (time <= 0.75F) {
         float progress = time / 0.75F;
         this.zuoyi.xRot = (float)Math.toRadians(2.298592F * progress);
         this.zuoyi.yRot = (float)Math.toRadians(-1.4387114F * progress);
         this.zuoyi.zRot = (float)Math.toRadians(77.302345F * progress);
         this.youyi.xRot = (float)Math.toRadians(0.8479549F * progress);
         this.youyi.yRot = (float)Math.toRadians(-2.2455933F * progress);
         this.youyi.zRot = (float)Math.toRadians(-81.96761F * progress);
         this.zuoyi.xScale = 1.0F - 0.4F * progress;
         this.zuoyi.yScale = 1.0F - 0.2F * progress;
         this.youyi.xScale = 1.0F - 0.4F * progress;
         this.youyi.yScale = 1.0F - 0.2F * progress;
         if (progress <= 0.88893336F) {
            float boneProgress = progress / 0.88893336F;
            this.bone10.zRot = (float)Math.toRadians(-65.0F * boneProgress);
            this.bone6.zRot = (float)Math.toRadians(65.0F * boneProgress);
         } else {
            this.bone10.zRot = (float)Math.toRadians(-65.0);
            this.bone10.yRot = (float)Math.toRadians(25.0);
            this.bone6.zRot = (float)Math.toRadians(65.0);
            this.bone6.yRot = (float)Math.toRadians(-20.0);
         }
      } else {
         this.zuoyi.xRot = (float)Math.toRadians(2.298592F);
         this.zuoyi.yRot = (float)Math.toRadians(-1.4387114F);
         this.zuoyi.zRot = (float)Math.toRadians(77.302345F);
         this.youyi.xRot = (float)Math.toRadians(0.8479549F);
         this.youyi.yRot = (float)Math.toRadians(-2.2455933F);
         this.youyi.zRot = (float)Math.toRadians(-81.96761F);
         this.zuoyi.xScale = 0.6F;
         this.zuoyi.yScale = 0.8F;
         this.youyi.xScale = 0.6F;
         this.youyi.yScale = 0.8F;
         this.bone10.zRot = (float)Math.toRadians(-65.0);
         this.bone10.yRot = (float)Math.toRadians(25.0);
         this.bone6.zRot = (float)Math.toRadians(65.0);
         this.bone6.yRot = (float)Math.toRadians(-20.0);
      }
   }

   public void applyCancelWeaponAnimation(int timer) {
      float time = timer * 0.05F;
      float progress = Math.min(time / 0.5833F, 1.0F);
      this.zuoyi.xRot = (float)Math.toRadians(2.298592F * (1.0F - progress));
      this.zuoyi.yRot = (float)Math.toRadians(-1.4387114F * (1.0F - progress));
      this.zuoyi.zRot = (float)Math.toRadians(77.302345F * (1.0F - progress));
      this.youyi.xRot = (float)Math.toRadians(0.8479549F * (1.0F - progress));
      this.youyi.yRot = (float)Math.toRadians(-2.2455933F * (1.0F - progress));
      this.youyi.zRot = (float)Math.toRadians(-81.96761F * (1.0F - progress));
      this.zuoyi.xScale = 0.6F + 0.4F * progress;
      this.zuoyi.yScale = 0.8F + 0.2F * progress;
      this.youyi.xScale = 0.6F + 0.4F * progress;
      this.youyi.yScale = 0.8F + 0.2F * progress;
      this.bone10.zRot = (float)Math.toRadians(-65.0F * (1.0F - progress));
      this.bone10.yRot = (float)Math.toRadians(25.0F * (1.0F - progress));
      this.bone6.zRot = (float)Math.toRadians(65.0F * (1.0F - progress));
      this.bone6.yRot = (float)Math.toRadians(-20.0F * (1.0F - progress));
   }

   public void renderBodyDecorations(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
      poseStack.pushPose();
      poseStack.translate(this.bone4.x / 16.0F, this.bone4.y / 16.0F, this.bone4.z / 16.0F);
      poseStack.scale(this.bone4.xScale, this.bone4.yScale, this.bone4.zScale);
      this.bone2.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
      poseStack.popPose();
      this.bone7.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
      this.bone8.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
      this.youyi.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
      this.zuoyi.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
   }

   public void renderHeadDecorations(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
      poseStack.pushPose();
      poseStack.translate(this.bone4.x / 16.0F, this.bone4.y / 16.0F, this.bone4.z / 16.0F);
      poseStack.scale(this.bone4.xScale, this.bone4.yScale, this.bone4.zScale);
      this.bone3.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
      this.bone5.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
      poseStack.popPose();
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
      this.renderBodyDecorations(poseStack, vertexConsumer, packedLight, packedOverlay, color);
      this.renderHeadDecorations(poseStack, vertexConsumer, packedLight, packedOverlay, color);
   }
}
