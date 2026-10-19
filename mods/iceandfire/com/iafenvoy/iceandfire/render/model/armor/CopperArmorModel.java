package com.iafenvoy.iceandfire.render.model.armor;

import com.iafenvoy.uranus.client.render.armor.ArmorModelBase;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class CopperArmorModel extends ArmorModelBase {
   private static final ModelPart INNER_MODEL = getModelData(CubeDeformation.NONE.extend(0.38F), 0.0F).getRoot().bake(64, 64);
   private static final ModelPart OUTER_MODEL = getModelData(CubeDeformation.NONE.extend(0.45F), 0.0F).getRoot().bake(64, 64);

   public CopperArmorModel(boolean inner) {
      super(getBakedModel(inner));
   }

   public static MeshDefinition getModelData(CubeDeformation deformation, float offset) {
      MeshDefinition modelData = HumanoidModel.createMesh(deformation, offset);
      PartDefinition root = modelData.getRoot();
      root.getChild("head")
         .addOrReplaceChild(
            "crest", CubeListBuilder.create().texOffs(23, 31).addBox(0.0F, -7.5F, -9.0F, 0.0F, 16.0F, 14.0F), PartPose.offset(0.0F, -7.6F, 2.6F)
         );
      root.getChild("head")
         .addOrReplaceChild(
            "facePlate", CubeListBuilder.create().texOffs(34, 32).addBox(-4.5F, -8.2F, -4.01F, 9.0F, 10.0F, 1.0F), PartPose.offset(0.0F, 0.0F, 0.0F)
         );
      root.getChild("right_leg")
         .addOrReplaceChild(
            "robeLowerRight", CubeListBuilder.create().texOffs(0, 51).addBox(-2.1F, 0.0F, -2.5F, 4.0F, 8.0F, 5.0F).mirror(), PartPose.offset(0.0F, -0.2F, 0.0F)
         );
      root.getChild("left_leg")
         .addOrReplaceChild(
            "robeLowerLeft", CubeListBuilder.create().texOffs(0, 51).addBox(-1.9F, 0.0F, -2.5F, 4.0F, 8.0F, 5.0F), PartPose.offset(0.0F, -0.2F, 0.0F)
         );
      return modelData;
   }

   public static ModelPart getBakedModel(boolean inner) {
      return inner ? INNER_MODEL : OUTER_MODEL;
   }
}
