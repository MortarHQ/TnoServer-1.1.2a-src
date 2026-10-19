package com.faboslav.variantsandventures.common.util;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

public final class LayerDefinitionFactory {
   public static LayerDefinition createInnerArmorLayer() {
      MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0.5F), 0.0F);
      return LayerDefinition.create(mesh, 64, 32);
   }

   public static LayerDefinition createOuterArmorLayer() {
      MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(1.0F), 0.0F);
      return LayerDefinition.create(mesh, 64, 32);
   }
}
