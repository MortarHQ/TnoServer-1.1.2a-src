package com.iafenvoy.iceandfire.render.model.armor;

import com.iafenvoy.uranus.client.render.armor.ArmorModelBase;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class DeathWormArmorModel extends ArmorModelBase {
   private static final ModelPart INNER_MODEL = getModelData(CubeDeformation.NONE.extend(0.38F), 0.0F).getRoot().bake(64, 64);
   private static final ModelPart OUTER_MODEL = getModelData(CubeDeformation.NONE.extend(0.45F), 0.0F).getRoot().bake(64, 64);

   public DeathWormArmorModel(boolean inner) {
      super(inner ? INNER_MODEL : OUTER_MODEL);
   }

   public static MeshDefinition getModelData(CubeDeformation deformation, float offset) {
      MeshDefinition modelData = HumanoidModel.createMesh(deformation, offset);
      PartDefinition root = modelData.getRoot();
      root.addOrReplaceChild(
         "right_leg",
         CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation),
         PartPose.offset(-1.9F, 12.0F + offset, 0.0F)
      );
      root.addOrReplaceChild(
         "left_leg",
         CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, deformation),
         PartPose.offset(1.9F, 12.0F + offset, 0.0F)
      );
      root.getChild("right_arm")
         .addOrReplaceChild(
            "spineR1",
            CubeListBuilder.create().texOffs(32, 40).addBox(-0.5F, -1.7F, -0.5F, 1.0F, 3.0F, 1.0F),
            PartPose.offsetAndRotation(-1.0F, -2.7F, 0.0F, -0.49148473F, 0.0F, 0.0F)
         );
      root.getChild("left_arm")
         .addOrReplaceChild(
            "spineL1",
            CubeListBuilder.create().texOffs(32, 40).addBox(-0.5F, -1.7F, -0.5F, 1.0F, 3.0F, 1.0F),
            PartPose.offsetAndRotation(1.0F, -2.7F, 0.0F, -0.49148473F, 0.0F, 0.0F)
         );
      root.getChild("right_arm")
         .addOrReplaceChild(
            "spineR2",
            CubeListBuilder.create().texOffs(32, 40).addBox(-0.6F, -1.7F, -0.5F, 1.0F, 2.0F, 1.0F),
            PartPose.offsetAndRotation(-2.5F, -1.6F, 0.0F, -0.49148473F, 0.0F, 0.0F)
         );
      root.getChild("left_arm")
         .addOrReplaceChild(
            "spineL2",
            CubeListBuilder.create().texOffs(32, 40).addBox(-0.4F, -1.7F, -0.5F, 1.0F, 2.0F, 1.0F),
            PartPose.offsetAndRotation(2.5F, -1.6F, 0.0F, -0.49148473F, 0.0F, 0.0F)
         );
      root.getChild("hat")
         .addOrReplaceChild(
            "spineH1",
            CubeListBuilder.create().texOffs(32, 40).addBox(-0.5F, -1.7F, -0.5F, 1.0F, 3.0F, 1.0F),
            PartPose.offsetAndRotation(0.0F, -9.0F, -3.0F, -0.49148473F, 0.0F, 0.0F)
         );
      root.getChild("hat")
         .addOrReplaceChild(
            "spineH2",
            CubeListBuilder.create().texOffs(32, 40).addBox(-0.5F, -2.7F, -0.5F, 1.0F, 4.0F, 1.0F),
            PartPose.offsetAndRotation(0.0F, -9.0F, 0.0F, -0.49148473F, 0.0F, 0.0F)
         );
      root.getChild("hat")
         .addOrReplaceChild(
            "spineH3",
            CubeListBuilder.create().texOffs(32, 40).addBox(-0.5F, -1.7F, -0.5F, 1.0F, 3.0F, 1.0F),
            PartPose.offsetAndRotation(0.0F, -9.0F, 3.0F, -0.8651597F, 0.0F, 0.0F)
         );
      root.getChild("hat")
         .addOrReplaceChild(
            "spineH4",
            CubeListBuilder.create().texOffs(32, 40).addBox(-0.5F, -2.7F, -0.5F, 1.0F, 4.0F, 1.0F),
            PartPose.offsetAndRotation(0.0F, -8.0F, 5.0F, -1.548107F, 0.0F, 0.0F)
         );
      root.getChild("hat")
         .addOrReplaceChild(
            "spineH5",
            CubeListBuilder.create().texOffs(32, 40).addBox(-0.5F, -1.7F, -0.5F, 1.0F, 3.0F, 1.0F),
            PartPose.offsetAndRotation(0.0F, -6.0F, 5.0F, -1.821251F, 0.0F, 0.0F)
         );
      root.getChild("hat")
         .addOrReplaceChild(
            "spineH6",
            CubeListBuilder.create().texOffs(32, 40).addBox(-0.5F, -2.7F, -0.5F, 1.0F, 5.0F, 1.0F),
            PartPose.offsetAndRotation(0.0F, -3.5F, 5.0F, -2.003289F, 0.0F, 0.0F)
         );
      root.getChild("hat")
         .addOrReplaceChild(
            "spineH7",
            CubeListBuilder.create().texOffs(32, 40).addBox(-0.5F, -1.7F, -0.5F, 1.0F, 3.0F, 1.0F),
            PartPose.offsetAndRotation(0.0F, -1.3F, 4.5F, -2.003289F, 0.0F, 0.0F)
         );
      return modelData;
   }

   public static ModelPart getBakedModel(boolean inner) {
      return inner ? INNER_MODEL : OUTER_MODEL;
   }
}
