package com.faboslav.variantsandventures.common.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.ZombieModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.world.entity.monster.Zombie;

public class ThicketEntityModel<T extends Zombie> extends ZombieModel<T> {
   public ThicketEntityModel(ModelPart root) {
      super(root);
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition modelData = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
      return LayerDefinition.create(modelData, 64, 64);
   }

   public static LayerDefinition createOuterLayer() {
      MeshDefinition modelData = HumanoidModel.createMesh(new CubeDeformation(0.25F), 0.0F);
      return LayerDefinition.create(modelData, 64, 64);
   }

   public static LayerDefinition createInnerArmorLayer() {
      MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0.5F), 0.0F);
      return LayerDefinition.create(mesh, 64, 32);
   }

   public static LayerDefinition createOuterArmorLayer() {
      MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(1.0F), 0.0F);
      return LayerDefinition.create(mesh, 64, 32);
   }
}
