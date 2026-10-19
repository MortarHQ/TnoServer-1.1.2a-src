package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.entity.projectile.Laser_Beam_Entity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class Laser_Beam_Model extends HierarchicalModel<Laser_Beam_Entity> {
   private final ModelPart root;

   public Laser_Beam_Model(ModelPart root) {
      this.root = root.getChild("root");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, -2.5F, 0.0F));
      PartDefinition cube_r1 = root.addOrReplaceChild(
         "cube_r1",
         CubeListBuilder.create().texOffs(0, -16).addBox(0.0F, -1.5F, -8.0F, 0.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F)
      );
      PartDefinition cube_r2 = root.addOrReplaceChild(
         "cube_r2",
         CubeListBuilder.create().texOffs(0, -16).addBox(0.0F, -1.5F, -8.0F, 0.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F)
      );
      return LayerDefinition.create(meshdefinition, 32, 32);
   }

   public void setupAnim(Laser_Beam_Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void setupAnim(float yRot, float xRot) {
      this.root.yRot = yRot * (float) (Math.PI / 180.0);
      this.root.xRot = xRot * (float) (Math.PI / 180.0);
   }

   public ModelPart root() {
      return this.root;
   }
}
