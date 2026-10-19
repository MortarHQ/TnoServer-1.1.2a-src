package com.mongoose.royalvariations.client.render.model;

import com.mongoose.royalvariations.client.render.animation.SpectralAnvilAnimations;
import com.mongoose.royalvariations.common.entities.projectile.SpectralAnvil;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class SpectralAnvilModel<T extends SpectralAnvil> extends HierarchicalModel<T> {
   private final ModelPart root;
   private final ModelPart spectralanvil;

   public SpectralAnvilModel(ModelPart root) {
      this.root = root;
      this.spectralanvil = root.getChild("spectralanvil");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition spectralanvil = partdefinition.addOrReplaceChild(
         "spectralanvil",
         CubeListBuilder.create()
            .texOffs(0, 32)
            .addBox(-6.0F, 3.85F, -6.0F, 12.0F, 4.0F, 12.0F, new CubeDeformation(0.0F))
            .texOffs(48, 32)
            .addBox(-5.0F, 2.85F, -4.0F, 10.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
            .texOffs(48, 50)
            .addBox(-4.0F, -2.15F, -2.0F, 8.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
            .texOffs(0, 0)
            .addBox(-8.0F, -8.15F, -5.0F, 16.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 16.15F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 128, 128);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.root().getAllParts().forEach(ModelPart::resetPose);
      this.animate(entity.spawnAnimationState, SpectralAnvilAnimations.SPAWN, ageInTicks);
      this.animate(entity.fallAnimationState, SpectralAnvilAnimations.FALL, ageInTicks);
   }

   public ModelPart root() {
      return this.root;
   }
}
