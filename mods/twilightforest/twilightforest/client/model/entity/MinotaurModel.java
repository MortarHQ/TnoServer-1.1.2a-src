package twilightforest.client.model.entity;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import twilightforest.client.JappaPackReloadListener;
import twilightforest.entity.monster.Minotaur;

public class MinotaurModel extends HumanoidModel<Minotaur> {
   public MinotaurModel(ModelPart root) {
      super(root);
   }

   public static LayerDefinition checkForPack() {
      return JappaPackReloadListener.INSTANCE.isJappaPackLoaded() ? createJappaModel() : create();
   }

   private static LayerDefinition create() {
      MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition head = partdefinition.addOrReplaceChild(
         "head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
      head.addOrReplaceChild(
         "snout", CubeListBuilder.create().texOffs(9, 12).addBox(-2.0F, -1.0F, -1.0F, 4.0F, 3.0F, 1.0F), PartPose.offset(0.0F, -2.0F, -4.0F)
      );
      PartDefinition rightHorn = head.addOrReplaceChild(
         "right_horn_1",
         CubeListBuilder.create().mirror().texOffs(24, 0).addBox(-5.5F, -1.5F, -1.5F, 5.0F, 3.0F, 3.0F),
         PartPose.offsetAndRotation(-2.5F, -6.5F, 0.0F, 0.0F, -0.43633232F, (float) (Math.PI / 18))
      );
      rightHorn.addOrReplaceChild(
         "right_horn_2",
         CubeListBuilder.create().mirror().texOffs(40, 0).addBox(-3.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F),
         PartPose.offsetAndRotation(-4.5F, 0.0F, 0.0F, 0.0F, (float) (-Math.PI / 12), (float) (Math.PI / 4))
      );
      PartDefinition leftHorn = head.addOrReplaceChild(
         "left_horn_1",
         CubeListBuilder.create().texOffs(24, 0).addBox(0.5F, -1.5F, -1.5F, 5.0F, 3.0F, 3.0F),
         PartPose.offsetAndRotation(2.5F, -6.5F, 0.0F, 0.0F, 0.43633232F, (float) (-Math.PI / 18))
      );
      leftHorn.addOrReplaceChild(
         "left_horn_2",
         CubeListBuilder.create().texOffs(40, 0).addBox(0.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F),
         PartPose.offsetAndRotation(4.5F, 0.0F, 0.0F, 0.0F, (float) (Math.PI / 12), (float) (-Math.PI / 4))
      );
      return LayerDefinition.create(meshdefinition, 64, 32);
   }

   private static LayerDefinition createJappaModel() {
      MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
      PartDefinition partdefinition = meshdefinition.getRoot();
      partdefinition.addOrReplaceChild(
         "head",
         CubeListBuilder.create()
            .texOffs(0, 0)
            .addBox(-4.0F, -10.0F, -4.0F, 8.0F, 8.0F, 8.0F)
            .texOffs(25, 1)
            .addBox(-3.0F, -5.0F, -5.0F, 6.0F, 3.0F, 1.0F)
            .texOffs(0, 16)
            .addBox(-8.0F, -9.0F, -1.0F, 4.0F, 2.0F, 2.0F)
            .texOffs(0, 20)
            .addBox(-8.0F, -11.0F, -1.0F, 2.0F, 2.0F, 2.0F)
            .texOffs(12, 16)
            .addBox(4.0F, -9.0F, -1.0F, 4.0F, 2.0F, 2.0F)
            .texOffs(12, 20)
            .addBox(6.0F, -11.0F, -1.0F, 2.0F, 2.0F, 2.0F),
         PartPose.offset(0.0F, -2.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
      partdefinition.addOrReplaceChild(
         "body", CubeListBuilder.create().texOffs(34, 0).addBox(-5.0F, -2.0F, -2.5F, 10.0F, 14.0F, 5.0F), PartPose.offset(0.0F, -2.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "right_arm", CubeListBuilder.create().texOffs(20, 26).addBox(-4.0F, -4.0F, -2.5F, 4.0F, 14.0F, 5.0F), PartPose.offset(-7.5F, -4.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "left_arm", CubeListBuilder.create().texOffs(20, 45).addBox(0.0F, -4.0F, -2.5F, 4.0F, 14.0F, 5.0F), PartPose.offset(7.5F, -4.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "right_leg", CubeListBuilder.create().texOffs(0, 26).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 12.0F, 5.0F), PartPose.offset(-2.5F, 12.0F, 0.0F)
      );
      partdefinition.addOrReplaceChild(
         "left_leg", CubeListBuilder.create().texOffs(0, 43).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 12.0F, 5.0F), PartPose.offset(2.5F, 12.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 64, 64);
   }
}
