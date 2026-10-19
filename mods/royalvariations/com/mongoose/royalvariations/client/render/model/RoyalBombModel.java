package com.mongoose.royalvariations.client.render.model;

import com.mongoose.royalvariations.common.entities.projectile.RoyalBomb;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.AnimationDefinition.Builder;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;

public class RoyalBombModel<T extends Entity> extends HierarchicalModel<T> {
   private final ModelPart root;
   private final ModelPart royalbomb;
   private final ModelPart flicker_2;
   private final ModelPart flicker;
   public static final AnimationDefinition DETONATE = Builder.withLength(2.0833F)
      .looping()
      .addAnimation(
         "royalbomb",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.2917F, KeyframeAnimations.degreeVec(4.0672F, -1.8593F, -4.9962F), Interpolations.CATMULLROM),
               new Keyframe(0.5417F, KeyframeAnimations.degreeVec(-5.1024F, 1.9945F, 4.7321F), Interpolations.CATMULLROM),
               new Keyframe(0.7917F, KeyframeAnimations.degreeVec(11.2173F, 2.3384F, 7.4622F), Interpolations.CATMULLROM),
               new Keyframe(1.0417F, KeyframeAnimations.degreeVec(-8.1004F, 6.9134F, -6.0665F), Interpolations.CATMULLROM),
               new Keyframe(1.25F, KeyframeAnimations.degreeVec(11.7271F, 5.6169F, -5.9486F), Interpolations.CATMULLROM),
               new Keyframe(1.4167F, KeyframeAnimations.degreeVec(-13.2382F, -4.3189F, 5.9314F), Interpolations.CATMULLROM),
               new Keyframe(1.5417F, KeyframeAnimations.degreeVec(5.1704F, 0.3697F, 8.9732F), Interpolations.CATMULLROM),
               new Keyframe(1.6667F, KeyframeAnimations.degreeVec(-10.3838F, 1.0478F, -7.8327F), Interpolations.CATMULLROM),
               new Keyframe(1.75F, KeyframeAnimations.degreeVec(8.012F, 9.8883F, -9.0156F), Interpolations.CATMULLROM),
               new Keyframe(1.8333F, KeyframeAnimations.degreeVec(-8.2488F, -4.5759F, 5.9803F), Interpolations.CATMULLROM),
               new Keyframe(1.9167F, KeyframeAnimations.degreeVec(5.2249F, -5.044F, 6.1402F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "royalbomb",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5417F, KeyframeAnimations.posVec(0.0F, 0.4F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, 1.3F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0417F, KeyframeAnimations.posVec(0.0F, 0.6F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 1.9F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.4167F, KeyframeAnimations.posVec(0.0F, 1.3F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.5417F, KeyframeAnimations.posVec(0.0F, 1.7F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.6667F, KeyframeAnimations.posVec(0.0F, 1.1F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, 2.2F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.8333F, KeyframeAnimations.posVec(0.0F, 0.4F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.9167F, KeyframeAnimations.posVec(0.0F, 1.9F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 1.9F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "royalbomb",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
               new Keyframe(1.9167F, KeyframeAnimations.scaleVec(1.2F, 1.2F, 1.2F), Interpolations.CATMULLROM),
               new Keyframe(2.0F, KeyframeAnimations.scaleVec(1.5, 1.5, 1.5), Interpolations.CATMULLROM),
               new Keyframe(2.0417F, KeyframeAnimations.scaleVec(1.5, 1.5, 1.5), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "flicker",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.7917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.4167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.5417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.9167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "flicker",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5417F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0417F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.5417F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.8333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.9167F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "flicker",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.9F, 0.9F, 0.9F), Interpolations.CATMULLROM),
               new Keyframe(0.2917F, KeyframeAnimations.scaleVec(1.1F, 1.1F, 1.1F), Interpolations.CATMULLROM),
               new Keyframe(0.5417F, KeyframeAnimations.scaleVec(0.9F, 0.9F, 0.9F), Interpolations.CATMULLROM),
               new Keyframe(0.7917F, KeyframeAnimations.scaleVec(1.1F, 1.1F, 1.1F), Interpolations.CATMULLROM),
               new Keyframe(1.0417F, KeyframeAnimations.scaleVec(0.9F, 0.9F, 0.9F), Interpolations.CATMULLROM),
               new Keyframe(1.25F, KeyframeAnimations.scaleVec(1.1F, 1.1F, 1.1F), Interpolations.CATMULLROM),
               new Keyframe(1.4167F, KeyframeAnimations.scaleVec(0.9F, 0.9F, 0.9F), Interpolations.CATMULLROM),
               new Keyframe(1.5417F, KeyframeAnimations.scaleVec(1.1F, 1.1F, 1.1F), Interpolations.CATMULLROM),
               new Keyframe(1.6667F, KeyframeAnimations.scaleVec(0.9F, 0.9F, 0.9F), Interpolations.CATMULLROM),
               new Keyframe(1.75F, KeyframeAnimations.scaleVec(1.1F, 1.1F, 1.1F), Interpolations.CATMULLROM),
               new Keyframe(1.8333F, KeyframeAnimations.scaleVec(0.9F, 0.9F, 0.9F), Interpolations.CATMULLROM),
               new Keyframe(1.9167F, KeyframeAnimations.scaleVec(1.1F, 1.1F, 1.1F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.scaleVec(0.9F, 0.9F, 0.9F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "flicker_2",
         new AnimationChannel(
            Targets.ROTATION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.7917F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.4167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.5417F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.6667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.75F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.8333F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.9167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "flicker_2",
         new AnimationChannel(
            Targets.POSITION,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.2917F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.5417F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.0417F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.25F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.4167F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.5417F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.6667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.75F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.8333F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(1.9167F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM)
            }
         )
      )
      .addAnimation(
         "flicker_2",
         new AnimationChannel(
            Targets.SCALE,
            new Keyframe[]{
               new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.9F, 0.9F, 0.9F), Interpolations.CATMULLROM),
               new Keyframe(0.2917F, KeyframeAnimations.scaleVec(1.2F, 1.2F, 1.2F), Interpolations.CATMULLROM),
               new Keyframe(0.5417F, KeyframeAnimations.scaleVec(0.9F, 0.9F, 0.9F), Interpolations.CATMULLROM),
               new Keyframe(0.7917F, KeyframeAnimations.scaleVec(1.2F, 1.2F, 1.2F), Interpolations.CATMULLROM),
               new Keyframe(1.0417F, KeyframeAnimations.scaleVec(0.9F, 0.9F, 0.9F), Interpolations.CATMULLROM),
               new Keyframe(1.25F, KeyframeAnimations.scaleVec(1.2F, 1.2F, 1.2F), Interpolations.CATMULLROM),
               new Keyframe(1.4167F, KeyframeAnimations.scaleVec(0.9F, 0.9F, 0.9F), Interpolations.CATMULLROM),
               new Keyframe(1.5417F, KeyframeAnimations.scaleVec(1.2F, 1.2F, 1.2F), Interpolations.CATMULLROM),
               new Keyframe(1.6667F, KeyframeAnimations.scaleVec(0.9F, 0.9F, 0.9F), Interpolations.CATMULLROM),
               new Keyframe(1.75F, KeyframeAnimations.scaleVec(1.2F, 1.2F, 1.2F), Interpolations.CATMULLROM),
               new Keyframe(1.8333F, KeyframeAnimations.scaleVec(0.9F, 0.9F, 0.9F), Interpolations.CATMULLROM),
               new Keyframe(1.9167F, KeyframeAnimations.scaleVec(1.2F, 1.2F, 1.2F), Interpolations.CATMULLROM),
               new Keyframe(2.0833F, KeyframeAnimations.scaleVec(0.9F, 0.9F, 0.9F), Interpolations.CATMULLROM)
            }
         )
      )
      .build();

   public RoyalBombModel(ModelPart root) {
      this.root = root;
      this.royalbomb = root.getChild("royalbomb");
      this.flicker_2 = this.royalbomb.getChild("flicker_2");
      this.flicker = this.royalbomb.getChild("flicker");
   }

   public static LayerDefinition createBodyLayer() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.getRoot();
      PartDefinition royalbomb = partdefinition.addOrReplaceChild(
         "royalbomb",
         CubeListBuilder.create()
            .texOffs(0, 16)
            .addBox(-4.0F, -5.5F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
            .texOffs(0, 32)
            .addBox(-4.0F, -1.5F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.13F))
            .texOffs(32, 15)
            .addBox(-1.0F, -9.5F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(32, 29)
            .addBox(1.0F, -8.5F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(32, 21)
            .addBox(-2.0F, -8.5F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, 21.5F, 0.0F)
      );
      PartDefinition flicker_2 = royalbomb.addOrReplaceChild(
         "flicker_2",
         CubeListBuilder.create()
            .texOffs(32, 9)
            .addBox(-1.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(32, 25)
            .addBox(1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
            .texOffs(32, 33)
            .addBox(-2.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.0F, -7.5F, 0.0F)
      );
      PartDefinition flicker = royalbomb.addOrReplaceChild(
         "flicker",
         CubeListBuilder.create()
            .texOffs(32, 0)
            .addBox(-4.375F, -0.125F, -4.0F, 8.0F, 1.0F, 8.0F, new CubeDeformation(0.13F))
            .texOffs(0, 0)
            .addBox(-4.375F, -4.125F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
         PartPose.offset(0.375F, -1.375F, 0.0F)
      );
      return LayerDefinition.create(meshdefinition, 64, 64);
   }

   public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.root().getAllParts().forEach(ModelPart::resetPose);
      if (entity instanceof RoyalBomb bomb) {
         this.animate(bomb.startAnimationState, DETONATE, ageInTicks);
      }
   }

   public ModelPart root() {
      return this.root;
   }
}
