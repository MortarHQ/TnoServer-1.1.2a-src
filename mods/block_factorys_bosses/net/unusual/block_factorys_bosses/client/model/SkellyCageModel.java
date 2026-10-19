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
import net.minecraft.world.entity.Entity;
import net.unusual.block_factorys_bosses.BossesRise;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SkellyCageModel<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("modelskellycage"), "main");
   public final ModelPart bone;
   public final ModelPart skeleton80;
   public final ModelPart left_leg11;
   public final ModelPart right_leg10;
   public final ModelPart torso80;
   public final ModelPart head2;
   public final ModelPart right_arm38;
   public final ModelPart left_arm33;

   public SkellyCageModel(ModelPart root) {
      this.bone = root.getChild("bone");
      this.skeleton80 = root.getChild("skeleton80");
      this.left_leg11 = this.skeleton80.getChild("left_leg11");
      this.right_leg10 = this.skeleton80.getChild("right_leg10");
      this.torso80 = this.skeleton80.getChild("torso80");
      this.head2 = this.torso80.getChild("head2");
      this.right_arm38 = this.torso80.getChild("right_arm38");
      this.left_arm33 = this.torso80.getChild("left_arm33");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition bone = partdefinition.addOrReplaceChild(
         "bone",
         CubeListBuilder.create()
            .texOffs(96, 59)
            .addBox(-2.0F, -15.0F, -5.0F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
            .texOffs(96, 64)
            .addBox(-2.0F, -15.0F, 24.0F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
            .texOffs(96, 69)
            .addBox(-19.0F, -15.0F, 12.0F, 5.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(96, 64)
            .addBox(10.0F, -15.0F, 12.0F, 5.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
            .texOffs(0, 62)
            .addBox(-14.0F, 32.0F, 0.0F, 24.0F, 5.0F, 24.0F, new CubeDeformation(0.0F))
            .texOffs(0, 0)
            .addBox(-12.0F, -10.0F, 2.0F, 20.0F, 42.0F, 20.0F, new CubeDeformation(0.0F))
            .texOffs(80, 0)
            .addBox(-14.0F, -15.0F, 0.0F, 24.0F, 5.0F, 24.0F, new CubeDeformation(0.0F)),
         PartPose.offset(2.0F, -13.0F, -12.0F)
      );
      bone.addOrReplaceChild(
         "cube_r1",
         CubeListBuilder.create().texOffs(64, 91).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.0F, -19.0F, 12.0F, 0.0F, -0.7854F, 0.0F)
      );
      bone.addOrReplaceChild(
         "cube_r2",
         CubeListBuilder.create().texOffs(64, 91).addBox(-5.0F, -4.0F, 0.0F, 10.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-2.0F, -19.0F, 12.0F, 0.0F, 0.7854F, 0.0F)
      );
      PartDefinition skeleton80 = partdefinition.addOrReplaceChild("skeleton80", CubeListBuilder.create(), PartPose.offset(0.0F, 43.0F, -1.25F));
      skeleton80.addOrReplaceChild(
         "left_leg11",
         CubeListBuilder.create().texOffs(52, 91).addBox(-2.1286F, 0.0F, -1.3318F, 3.0F, 22.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(5.3728F, -30.7412F, -5.0388F, -1.0908F, -0.3054F, 0.0F)
      );
      skeleton80.addOrReplaceChild(
         "right_leg10",
         CubeListBuilder.create().texOffs(52, 91).addBox(-1.1286F, 0.0F, -1.3318F, 3.0F, 22.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-4.8714F, -37.0F, -5.4182F, -0.7069F, 0.3927F, 0.0F)
      );
      PartDefinition torso80 = skeleton80.addOrReplaceChild(
         "torso80", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.3714F, -25.8618F, -0.3966F, -0.3927F, 0.0F, 0.0F)
      );
      torso80.addOrReplaceChild(
         "cube_r3",
         CubeListBuilder.create()
            .texOffs(80, 29)
            .addBox(-6.6322F, -9.7616F, -2.9559F, 12.0F, 12.0F, 7.0F, new CubeDeformation(0.0F))
            .texOffs(80, 48)
            .addBox(-5.6322F, 2.2384F, -0.9559F, 10.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(1.0036F, -7.4717F, -1.8759F, 0.0873F, -0.3491F, 0.0F)
      );
      torso80.addOrReplaceChild(
         "cube_r4",
         CubeListBuilder.create().texOffs(38, 91).addBox(-1.6322F, -13.7616F, 1.0441F, 4.0F, 22.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0036F, -7.4717F, -1.8759F, 0.0873F, -0.3491F, 0.0F)
      );
      torso80.addOrReplaceChild(
         "head2",
         CubeListBuilder.create()
            .texOffs(0, 91)
            .addBox(-5.6286F, -8.75F, -7.0818F, 10.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
            .texOffs(84, 91)
            .addBox(-3.6286F, 0.25F, -7.0818F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.8519F, -21.9502F, -1.2068F, 0.3303F, -0.1578F, -0.5493F)
      );
      torso80.addOrReplaceChild(
         "right_arm38",
         CubeListBuilder.create().texOffs(52, 91).mirror().addBox(-0.5834F, -3.0065F, -2.3472F, 3.0F, 22.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offsetAndRotation(-8.9925F, -12.4103F, -2.4644F, 0.3024F, 0.1864F, 0.5872F)
      );
      torso80.addOrReplaceChild(
         "left_arm33",
         CubeListBuilder.create().texOffs(52, 91).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 22.0F, 3.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(9.1214F, -13.5183F, 1.3575F, 0.4771F, 0.0293F, -0.3522F)
      );
      return LayerDefinition.create(meshdefinition, 256, 256);
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
      this.bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
      this.skeleton80.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }
}
