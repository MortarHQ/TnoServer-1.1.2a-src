package com.mongoose.royalvariations.client.render.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.LivingEntity;

public class RoyalKnightHelmetModel extends HumanoidModel<LivingEntity> {
   private final ModelPart head;

   public RoyalKnightHelmetModel(ModelPart root) {
      super(root);
      this.head = root.getChild("head");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition head = partdefinition.addOrReplaceChild(
         "head",
         CubeListBuilder.create()
            .texOffs(31, 112)
            .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.7F))
            .texOffs(28, 98)
            .addBox(-4.0F, -2.5F, -3.9F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.8F))
            .texOffs(0, 93)
            .addBox(-1.5F, -12.7F, -0.5F, 3.0F, 8.0F, 8.0F, new CubeDeformation(0.8F))
            .texOffs(0, 80)
            .addBox(-1.5F, -8.1F, 5.5F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.8F))
            .texOffs(0, 115)
            .addBox(-4.5F, -6.8F, -4.4F, 9.0F, 7.0F, 6.0F, new CubeDeformation(0.7F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 128, 128);
   }

   public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
   }

   protected Iterable<ModelPart> headParts() {
      return ImmutableList.of(this.head);
   }
}
