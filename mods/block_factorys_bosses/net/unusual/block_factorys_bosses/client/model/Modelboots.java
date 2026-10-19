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

public class Modelboots<T extends Entity> extends EntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(BossesRise.prefix("modelboots"), "main");
   public final ModelPart root;
   public final ModelPart leftLeg;
   public final ModelPart leftboots;
   public final ModelPart rightLeg;
   public final ModelPart rightboots;

   public Modelboots(ModelPart root) {
      this.root = root.getChild("root");
      this.leftLeg = this.root.getChild("leftLeg");
      this.leftboots = this.leftLeg.getChild("leftboots");
      this.rightLeg = this.root.getChild("rightLeg");
      this.rightboots = this.rightLeg.getChild("rightboots");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
      PartDefinition leftLeg = root.addOrReplaceChild("leftLeg", CubeListBuilder.create(), PartPose.offset(2.938F, -12.24F, 0.0F));
      PartDefinition leftboots = leftLeg.addOrReplaceChild(
         "leftboots",
         CubeListBuilder.create().texOffs(56, 50).addBox(0.938F, 5.24F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.5F)),
         PartPose.offset(-3.876F, 0.0F, 0.0F)
      );
      PartDefinition rightLeg = root.addOrReplaceChild("rightLeg", CubeListBuilder.create(), PartPose.offset(-1.938F, -12.24F, 0.0F));
      PartDefinition rightboots = rightLeg.addOrReplaceChild(
         "rightboots",
         CubeListBuilder.create().texOffs(56, 50).mirror().addBox(-2.062F, 5.24F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 128, 128);
   }

   public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int rgb) {
      this.root.render(poseStack, vertexConsumer, packedLight, packedOverlay, rgb);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.rightLeg.xRot = Mth.cos(limbSwing * 1.0F) * 1.0F * limbSwingAmount;
      this.leftLeg.xRot = Mth.cos(limbSwing * 1.0F) * -1.0F * limbSwingAmount;
   }
}
