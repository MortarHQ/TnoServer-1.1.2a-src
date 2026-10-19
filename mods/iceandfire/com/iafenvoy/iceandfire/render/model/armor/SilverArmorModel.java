package com.iafenvoy.iceandfire.render.model.armor;

import com.iafenvoy.uranus.client.render.armor.ArmorModelBase;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class SilverArmorModel extends ArmorModelBase {
   private static final ModelPart INNER_MODEL = getModelData(CubeDeformation.NONE.extend(0.38F), 0.0F).getRoot().bake(64, 64);
   private static final ModelPart OUTER_MODEL = getModelData(CubeDeformation.NONE.extend(0.45F), 0.0F).getRoot().bake(64, 64);

   public SilverArmorModel(boolean inner) {
      super(getBakedModel(inner));
   }

   public static MeshDefinition getModelData(CubeDeformation deformation, float offset) {
      MeshDefinition modelData = HumanoidModel.createMesh(deformation, offset);
      PartDefinition root = modelData.getRoot();
      root.getChild("head")
         .addOrReplaceChild(
            "faceGuard",
            CubeListBuilder.create().texOffs(30, 47).addBox(-4.5F, -3.0F, -6.1F, 9.0F, 9.0F, 8.0F),
            PartPose.offsetAndRotation(0.0F, -6.6F, 1.9F, -0.7285004F, 0.0F, 0.0F)
         );
      root.getChild("head")
         .addOrReplaceChild(
            "helmWingR",
            CubeListBuilder.create().texOffs(2, 37).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 4.0F, 6.0F),
            PartPose.offsetAndRotation(-3.0F, -6.3F, 1.3F, (float) (Math.PI / 6), -0.43633232F, -0.05235988F)
         );
      root.getChild("head")
         .addOrReplaceChild(
            "helmWingL",
            CubeListBuilder.create().texOffs(2, 37).mirror().addBox(-0.5F, -1.0F, 0.0F, 1.0F, 4.0F, 6.0F),
            PartPose.offsetAndRotation(3.0F, -6.3F, 1.3F, (float) (Math.PI / 6), 0.43633232F, 0.05235988F)
         );
      root.getChild("hat")
         .addOrReplaceChild(
            "crest",
            CubeListBuilder.create().texOffs(18, 32).addBox(0.0F, -0.5F, 0.0F, 1.0F, 9.0F, 9.0F),
            PartPose.offsetAndRotation(0.0F, -7.9F, -0.1F, 1.2292354F, 0.0F, 0.0F)
         );
      root.getChild("body")
         .addOrReplaceChild(
            "robeLowerBack",
            CubeListBuilder.create().texOffs(4, 55).mirror().addBox(-4.0F, 0.0F, -2.5F, 8.0F, 8.0F, 1.0F),
            PartPose.offsetAndRotation(0.0F, 12.0F, 0.0F, 0.0F, (float) Math.PI, 0.0F)
         );
      root.getChild("body")
         .addOrReplaceChild(
            "robeLower", CubeListBuilder.create().texOffs(4, 55).addBox(-4.0F, 0.0F, -2.5F, 8.0F, 8.0F, 1.0F), PartPose.offset(0.0F, 12.0F, 0.0F)
         );
      return modelData;
   }

   public static ModelPart getBakedModel(boolean inner) {
      return inner ? INNER_MODEL : OUTER_MODEL;
   }
}
