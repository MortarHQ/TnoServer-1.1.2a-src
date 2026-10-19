package net.unusual.block_factorys_bosses.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.entity.decoration.CageEntity;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BigSkellyCageModel<T extends CageEntity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("modelbigskellycage"), "main");
   public final ModelPart main;
   public final ModelPart skeleton2;
   public final ModelPart left_leg2;
   public final ModelPart right_leg2;
   public final ModelPart torso2;
   public final ModelPart head3;
   public final ModelPart right_arm2;
   public final ModelPart left_arm2;
   public final ModelPart skeleton4;
   public final ModelPart left_leg4;
   public final ModelPart right_leg4;
   public final ModelPart torso4;
   public final ModelPart head4;
   public final ModelPart right_arm4;
   public final ModelPart left_arm4;
   public final ModelPart head5;

   public BigSkellyCageModel(ModelPart root) {
      this.main = root.getChild("main");
      this.skeleton2 = root.getChild("skeleton2");
      this.left_leg2 = this.skeleton2.getChild("left_leg2");
      this.right_leg2 = this.skeleton2.getChild("right_leg2");
      this.torso2 = this.skeleton2.getChild("torso2");
      this.head3 = this.torso2.getChild("head3");
      this.right_arm2 = this.torso2.getChild("right_arm2");
      this.left_arm2 = this.torso2.getChild("left_arm2");
      this.skeleton4 = root.getChild("skeleton4");
      this.left_leg4 = this.skeleton4.getChild("left_leg4");
      this.right_leg4 = this.skeleton4.getChild("right_leg4");
      this.torso4 = this.skeleton4.getChild("torso4");
      this.head4 = this.torso4.getChild("head4");
      this.right_arm4 = this.torso4.getChild("right_arm4");
      this.left_arm4 = this.torso4.getChild("left_arm4");
      this.head5 = root.getChild("head5");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition root = meshdefinition.getRoot();
      PartDefinition main = root.addOrReplaceChild(
         "main",
         CubeListBuilder.create()
            .texOffs(0, 126)
            .addBox(-22.0F, -7.0F, -22.0F, 44.0F, 7.0F, 44.0F, new CubeDeformation(0.0F))
            .texOffs(0, 0)
            .addBox(-19.0F, -95.0F, -19.0F, 38.0F, 88.0F, 38.0F, new CubeDeformation(0.0F))
            .texOffs(152, 0)
            .addBox(-22.0F, -102.0F, -22.0F, 44.0F, 7.0F, 44.0F, new CubeDeformation(0.0F))
            .texOffs(176, 172)
            .addBox(22.0F, -102.0F, 0.0F, 7.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(176, 172)
            .mirror()
            .addBox(-29.0F, -102.0F, 0.0F, 7.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
            .mirror(false)
            .texOffs(152, 88)
            .addBox(-7.0F, -113.0F, -2.0F, 14.0F, 11.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(152, 114)
            .addBox(-4.0F, -110.0F, -2.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 24.0F, 0.0F)
      );
      main.addOrReplaceChild(
         "cube_r1",
         CubeListBuilder.create()
            .texOffs(176, 172)
            .mirror()
            .addBox(-29.0F, -3.5F, 0.0F, 7.0F, 7.0F, 0.0F, new CubeDeformation(0.0F))
            .mirror(false)
            .texOffs(176, 172)
            .addBox(22.0F, -3.5F, 0.0F, 7.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, -98.5F, 0.0F, 0.0F, -1.5708F, 0.0F)
      );
      PartDefinition skeleton2 = root.addOrReplaceChild(
         "skeleton2", CubeListBuilder.create(), PartPose.offsetAndRotation(1.8304F, 12.5034F, 0.5442F, -1.6581F, 0.2618F, 0.0F)
      );
      skeleton2.addOrReplaceChild(
         "left_leg2",
         CubeListBuilder.create().texOffs(176, 139).addBox(-2.1229F, 0.3299F, -0.8955F, 3.0F, 22.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(2.534F, 7.4252F, -0.7032F, 0.1981F, 0.2212F, -0.1334F)
      );
      skeleton2.addOrReplaceChild(
         "right_leg2",
         CubeListBuilder.create().texOffs(176, 139).addBox(-2.123F, 0.3299F, -0.8955F, 3.0F, 22.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.7192F, 7.1913F, 0.3024F, 0.0893F, -0.3035F, 0.0416F)
      );
      PartDefinition torso2 = skeleton2.addOrReplaceChild("torso2", CubeListBuilder.create(), PartPose.offset(0.0F, 7.15F, 1.7778F));
      torso2.addOrReplaceChild(
         "cube_r2",
         CubeListBuilder.create()
            .texOffs(152, 51)
            .addBox(-6.6266F, -10.1817F, -3.5196F, 12.0F, 12.0F, 7.0F, new CubeDeformation(0.0F))
            .texOffs(152, 103)
            .addBox(-5.6266F, 1.8183F, -1.5196F, 10.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
            .texOffs(176, 114)
            .addBox(-2.6266F, -14.1817F, 0.4803F, 4.0F, 22.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0036F, -7.4717F, -1.8758F, 0.0F, -0.2618F, 0.0F)
      );
      torso2.addOrReplaceChild(
         "head3",
         CubeListBuilder.create()
            .texOffs(152, 70)
            .addBox(-5.623F, -8.4201F, -6.6455F, 10.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
            .texOffs(176, 164)
            .addBox(-3.623F, 0.5799F, -6.6455F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-13.9491F, -14.4933F, -0.7854F, 1.1645F, 0.1734F, 0.1955F)
      );
      torso2.addOrReplaceChild(
         "right_arm2",
         CubeListBuilder.create().texOffs(176, 139).mirror().addBox(-2.0778F, -2.6766F, -0.9109F, 3.0F, 22.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(-7.5452F, -14.9768F, -0.4846F, 0.0903F, 0.2608F, 0.4597F)
      );
      torso2.addOrReplaceChild(
         "left_arm2",
         CubeListBuilder.create().texOffs(176, 139).addBox(-2.0F, -3.0F, -0.6F, 3.0F, 22.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(7.377F, -14.6534F, -0.7955F, -0.1088F, -0.057F, -0.8283F)
      );
      PartDefinition skeleton4 = root.addOrReplaceChild(
         "skeleton4", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.5694F, 2.5034F, 10.0601F, 0.0F, -0.7854F, 0.0F)
      );
      skeleton4.addOrReplaceChild(
         "left_leg4",
         CubeListBuilder.create().texOffs(176, 139).addBox(-2.123F, 0.3299F, -0.8955F, 3.0F, 22.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(3.3034F, 4.279F, 1.3028F, -1.5213F, -0.2947F, 0.2739F)
      );
      skeleton4.addOrReplaceChild(
         "right_leg4",
         CubeListBuilder.create().texOffs(176, 139).addBox(-2.123F, 0.33F, -0.8955F, 3.0F, 22.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-4.8161F, -6.3007F, -4.3138F, -0.9035F, 0.7702F, 0.0463F)
      );
      PartDefinition torso4 = skeleton4.addOrReplaceChild(
         "torso4",
         CubeListBuilder.create()
            .texOffs(176, 114)
            .addBox(-2.623F, -21.6534F, -1.3955F, 4.0F, 22.0F, 3.0F, new CubeDeformation(0.0F))
            .texOffs(152, 103)
            .addBox(-5.623F, -5.6534F, -3.3955F, 10.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
            .texOffs(152, 51)
            .addBox(-6.623F, -17.6534F, -5.3955F, 12.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 7.15F, 1.7778F, -0.2182F, 0.0F, 0.0F)
      );
      torso4.addOrReplaceChild(
         "head4",
         CubeListBuilder.create()
            .texOffs(152, 70)
            .addBox(-5.623F, -8.4201F, -6.6455F, 10.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
            .texOffs(176, 164)
            .addBox(-3.623F, 0.5799F, -6.6455F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, -22.2333F, -0.75F, 0.4038F, 0.3456F, 0.426F)
      );
      torso4.addOrReplaceChild(
         "right_arm4",
         CubeListBuilder.create().texOffs(176, 139).mirror().addBox(-2.0778F, -2.6766F, -0.9109F, 3.0F, 22.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(-7.9781F, -13.0242F, -0.4846F, 0.1309F, 0.2618F, 0.2182F)
      );
      torso4.addOrReplaceChild(
         "left_arm4",
         CubeListBuilder.create().texOffs(176, 139).addBox(-2.0F, -3.0F, -0.6F, 3.0F, 22.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(7.6324F, -12.7211F, -1.41F, -0.3052F, -0.0131F, -0.1725F)
      );
      root.addOrReplaceChild(
         "head5",
         CubeListBuilder.create()
            .texOffs(152, 70)
            .addBox(-5.6229F, -8.4201F, -6.6455F, 10.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
            .texOffs(176, 164)
            .addBox(-3.6229F, 0.5799F, -6.6455F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(10.3934F, 15.7353F, -9.0706F, -0.678F, -0.4378F, -0.0222F)
      );
      return LayerDefinition.create(meshdefinition, 512, 512);
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
      this.main.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
      this.skeleton2.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
      this.skeleton4.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
      this.head5.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.main.yRot = entity.getFlip() ? (float) (Math.PI / 2) : 0.0F;
   }
}
