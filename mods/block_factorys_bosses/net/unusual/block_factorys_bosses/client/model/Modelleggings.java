package net.unusual.block_factorys_bosses.client.model;

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
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.unusual.block_factorys_bosses.BossesRise;

public class Modelleggings<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("modelleggings"), "main");
   public final ModelPart root;
   public final ModelPart waist;
   public final ModelPart body;
   public final ModelPart head;
   public final ModelPart mask;
   public final ModelPart leftArm;
   public final ModelPart arm;
   public final ModelPart hand;
   public final ModelPart rightArm;
   public final ModelPart arm2;
   public final ModelPart hand2;
   public final ModelPart legs;
   public final ModelPart leftLeg;
   public final ModelPart leftboots;
   public final ModelPart rightLeg;
   public final ModelPart rightboots;

   public Modelleggings(ModelPart root) {
      this.root = root.getChild("root");
      this.waist = this.root.getChild("waist");
      this.body = this.waist.getChild("body");
      this.head = this.body.getChild("head");
      this.mask = this.head.getChild("mask");
      this.leftArm = this.body.getChild("leftArm");
      this.arm = this.leftArm.getChild("arm");
      this.hand = this.leftArm.getChild("hand");
      this.rightArm = this.body.getChild("rightArm");
      this.arm2 = this.rightArm.getChild("arm2");
      this.hand2 = this.rightArm.getChild("hand2");
      this.legs = this.waist.getChild("legs");
      this.leftLeg = this.legs.getChild("leftLeg");
      this.leftboots = this.leftLeg.getChild("leftboots");
      this.rightLeg = this.legs.getChild("rightLeg");
      this.rightboots = this.rightLeg.getChild("rightboots");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
      PartDefinition waist = root.addOrReplaceChild("waist", CubeListBuilder.create(), PartPose.offset(0.0F, -12.24F, 0.0F));
      PartDefinition body = waist.addOrReplaceChild(
         "body",
         CubeListBuilder.create().texOffs(0, 67).addBox(-4.0F, 0.48F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
         PartPose.offset(0.0F, -12.24F, 0.0F)
      );
      PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
      PartDefinition mask = head.addOrReplaceChild("mask", CubeListBuilder.create(), PartPose.offset(0.0F, 24.48F, 0.0F));
      PartDefinition leftArm = body.addOrReplaceChild("leftArm", CubeListBuilder.create(), PartPose.offset(5.1F, 2.04F, 0.0F));
      PartDefinition arm = leftArm.addOrReplaceChild("arm", CubeListBuilder.create(), PartPose.offset(-5.1F, 22.44F, 5.1F));
      PartDefinition hand = leftArm.addOrReplaceChild("hand", CubeListBuilder.create(), PartPose.offset(-5.1F, 22.44F, 5.1F));
      PartDefinition rightArm = body.addOrReplaceChild("rightArm", CubeListBuilder.create(), PartPose.offset(-5.1F, 2.04F, 0.0F));
      PartDefinition arm2 = rightArm.addOrReplaceChild("arm2", CubeListBuilder.create(), PartPose.offset(5.1F, 22.44F, 0.0F));
      PartDefinition hand2 = rightArm.addOrReplaceChild("hand2", CubeListBuilder.create(), PartPose.offset(5.1F, 22.44F, 0.0F));
      PartDefinition legs = waist.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(1.938F, 0.0F, 0.0F));
      PartDefinition leftLeg = legs.addOrReplaceChild(
         "leftLeg",
         CubeListBuilder.create().texOffs(0, 46).addBox(-1.938F, 0.24F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      PartDefinition leftboots = leftLeg.addOrReplaceChild("leftboots", CubeListBuilder.create(), PartPose.offset(-3.876F, 0.0F, 0.0F));
      PartDefinition rightLeg = legs.addOrReplaceChild(
         "rightLeg",
         CubeListBuilder.create().texOffs(0, 46).mirror().addBox(-2.062F, 0.24F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false),
         PartPose.offset(-3.876F, 0.0F, 0.0F)
      );
      PartDefinition rightboots = rightLeg.addOrReplaceChild("rightboots", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
      return LayerDefinition.create(meshdefinition, 128, 128);
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
      this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.leftLeg.xRot = Mth.cos(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
      this.rightLeg.xRot = Mth.cos(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
   }
}
