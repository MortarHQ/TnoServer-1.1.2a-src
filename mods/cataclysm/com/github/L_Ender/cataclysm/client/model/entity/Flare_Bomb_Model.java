package com.github.L_Ender.cataclysm.client.model.entity;

import com.github.L_Ender.cataclysm.entity.projectile.Flare_Bomb_Entity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.phys.Vec3;

public class Flare_Bomb_Model extends HierarchicalModel<Flare_Bomb_Entity> {
   private final ModelPart root;
   private final ModelPart outer;
   private final ModelPart inner;

   public Flare_Bomb_Model(ModelPart root) {
      this.root = root.getChild("root");
      this.outer = this.root.getChild("outer");
      this.inner = this.root.getChild("inner");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 0.0F));
      PartDefinition outer = root.addOrReplaceChild(
         "outer",
         CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      PartDefinition inner = root.addOrReplaceChild(
         "inner",
         CubeListBuilder.create().texOffs(0, 33).addBox(-4.5F, -4.5F, -4.5F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 0.0F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 64, 64);
   }

   public void setupAnim(Flare_Bomb_Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      float delta = ageInTicks - entity.tickCount;
      this.root().getAllParts().forEach(ModelPart::resetPose);
      Vec3 prevV = new Vec3(entity.prevDeltaMovementX, entity.prevDeltaMovementY, entity.prevDeltaMovementZ);
      Vec3 dv = prevV.add(entity.getDeltaMovement().subtract(prevV).scale(delta));
      double d = Math.sqrt(dv.x * dv.x + dv.y * dv.y + dv.z * dv.z);
      if (d != 0.0) {
         double a = dv.y / d;
         a = Math.max(-10.0, Math.min(1.0, a));
         float pitch = -((float)Math.asin(a));
         this.root.xRot = pitch + (float) (Math.PI / 2);
      }

      this.inner.yRot = ageInTicks * 20.0F * (float) (Math.PI / 180.0);
      this.inner.xRot = ageInTicks * 20.0F * (float) (Math.PI / 180.0);
      this.inner.zRot = ageInTicks * 20.0F * (float) (Math.PI / 180.0);
      this.outer.yRot = ageInTicks * -10.0F * (float) (Math.PI / 180.0);
      this.outer.xRot = ageInTicks * -10.0F * (float) (Math.PI / 180.0);
      this.outer.zRot = ageInTicks * -10.0F * (float) (Math.PI / 180.0);
   }

   public ModelPart root() {
      return this.root;
   }
}
