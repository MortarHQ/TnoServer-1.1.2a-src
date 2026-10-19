package com.mongoose.royalvariations.client.render.model;

import com.mongoose.royalvariations.common.entities.projectile.TangleEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class NetModel<T extends TangleEntity> extends HierarchicalModel<T> {
   private final ModelPart root;
   private final ModelPart main;

   public NetModel(ModelPart root) {
      this.root = root;
      this.main = root.getChild("main");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition main = partdefinition.addOrReplaceChild(
         "main",
         CubeListBuilder.create()
            .texOffs(0, 14)
            .addBox(10.8F, -1.0F, -13.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.2F))
            .texOffs(0, 14)
            .mirror()
            .addBox(-12.8F, -1.0F, -13.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.2F))
            .mirror(false)
            .texOffs(0, 14)
            .addBox(10.8F, -1.0F, 11.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.2F))
            .texOffs(0, 14)
            .mirror()
            .addBox(-12.8F, -1.0F, 11.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.2F))
            .mirror(false),
         PartPose.offset(0.0F, 23.8F, 0.0F)
      );
      PartDefinition cube_r1 = main.addOrReplaceChild(
         "cube_r1",
         CubeListBuilder.create().texOffs(0, 0).addBox(-11.5F, -7.0F, 0.0F, 23.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(-6.1F, -3.4464F, -0.3536F, 0.0F, 1.5708F, 1.021F)
      );
      PartDefinition cube_r2 = main.addOrReplaceChild(
         "cube_r2",
         CubeListBuilder.create().texOffs(0, 0).addBox(-11.5F, -7.0F, 0.0F, 23.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(6.1F, -3.4464F, -0.3536F, 0.0F, -1.5708F, -1.021F)
      );
      PartDefinition cube_r3 = main.addOrReplaceChild(
         "cube_r3",
         CubeListBuilder.create().texOffs(0, 0).addBox(-11.5F, -7.0F, 0.0F, 23.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, -3.4464F, 6.0464F, 2.1206F, 0.0F, 3.1416F)
      );
      PartDefinition cube_r4 = main.addOrReplaceChild(
         "cube_r4",
         CubeListBuilder.create().texOffs(0, 0).addBox(-11.5F, -7.0F, 0.0F, 23.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, -3.4464F, -6.7536F, -1.021F, 0.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 64, 64);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.root().getAllParts().forEach(ModelPart::resetPose);
   }

   public ModelPart root() {
      return this.root;
   }
}
