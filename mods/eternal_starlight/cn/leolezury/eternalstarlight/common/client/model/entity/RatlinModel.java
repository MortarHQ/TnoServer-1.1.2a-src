package cn.leolezury.eternalstarlight.common.client.model.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.model.animation.AnimatedEntityModel;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.RatlinAnimation;
import cn.leolezury.eternalstarlight.common.entity.living.animal.Ratlin;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class RatlinModel<T extends Ratlin> extends AnimatedEntityModel<T> {
   public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(EternalStarlight.id("ratlin"), "main");
   private final ModelPart root;
   private final ModelPart head;

   public RatlinModel(ModelPart root) {
      this.root = root.getChild("root");
      this.head = root.getChild("root").getChild("body").getChild("head");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
      PartDefinition body = root.addOrReplaceChild(
         "body",
         CubeListBuilder.create().texOffs(0, 0).addBox(-6.5F, -11.0F, -8.0F, 13.0F, 11.0F, 16.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -3.0F, 0.0F)
      );
      body.addOrReplaceChild(
         "tail",
         CubeListBuilder.create().texOffs(26, 27).addBox(-2.0F, -2.0F, -0.5F, 4.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)),
         PartPose.offsetAndRotation(0.0F, -4.0F, 5.5F, -0.5236F, 0.0F, 0.0F)
      );
      PartDefinition head = body.addOrReplaceChild(
         "head",
         CubeListBuilder.create().texOffs(0, 27).addBox(-3.5F, -4.0F, -6.0F, 7.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.5F, -7.0F, -8.0F)
      );
      head.addOrReplaceChild(
         "left_ear",
         CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-0.5F, -6.0F, 0.0F, 7.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false),
         PartPose.offset(3.0F, 0.0F, -3.0F)
      );
      head.addOrReplaceChild(
         "right_ear",
         CubeListBuilder.create().texOffs(0, 0).addBox(-6.5F, -6.0F, 0.0F, 7.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-3.0F, 0.0F, -3.0F)
      );
      root.addOrReplaceChild(
         "left_front_leg",
         CubeListBuilder.create().texOffs(39, 41).addBox(-2.0F, 0.0F, -2.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.offset(4.0F, -3.0F, -4.5F)
      );
      root.addOrReplaceChild(
         "right_front_leg",
         CubeListBuilder.create().texOffs(39, 41).addBox(-2.0F, 0.0F, -2.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-4.0F, -3.0F, -4.5F)
      );
      root.addOrReplaceChild(
         "left_rear_leg",
         CubeListBuilder.create().texOffs(26, 38).addBox(-2.0F, 0.0F, -2.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.offset(4.0F, -3.0F, 4.5F)
      );
      root.addOrReplaceChild(
         "right_rear_leg",
         CubeListBuilder.create().texOffs(39, 41).addBox(-2.0F, 0.0F, -2.5F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)),
         PartPose.offset(-4.0F, -3.0F, 4.5F)
      );
      return LayerDefinition.create(meshdefinition, 64, 64);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.root().getAllParts().forEach(ModelPart::resetPose);
      this.head.yRot = netHeadYaw * (float) (Math.PI / 180.0);
      this.head.xRot = headPitch * (float) (Math.PI / 180.0);
      if (this.young) {
         this.root.xScale = 0.6F;
         this.root.yScale = 0.6F;
         this.root.zScale = 0.6F;
         this.head.xScale = 1.67F;
         this.head.yScale = 1.67F;
         this.head.zScale = 1.67F;
      }

      this.animate(entity.idleAnimationState, RatlinAnimation.IDLE, ageInTicks);
      this.animateWalk(RatlinAnimation.WALK, limbSwing, limbSwingAmount, this.young ? 3.0F : 5.0F, 1.0F);
   }

   @Override
   public ModelPart root() {
      return this.root;
   }
}
