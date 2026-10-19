package net.jadenxgamer.netherexp.client.rendering.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.client.rendering.JNERenderType;
import net.jadenxgamer.netherexp.core.entity.EctoSlab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.animation.AnimationChannel.Interpolations;
import net.minecraft.client.animation.AnimationChannel.Targets;
import net.minecraft.client.animation.AnimationDefinition.Builder;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class EctoSlabRenderer extends MobRenderer<EctoSlab, EctoSlabRenderer.EctoSlabModel<EctoSlab>> {
   public EctoSlabRenderer(Context context) {
      super(context, new EctoSlabRenderer.EctoSlabModel(context.bakeLayer(EctoSlabRenderer.EctoSlabModel.LAYER)), 0.8F);
      this.addLayer(new EctoSlabRenderer.EctoSlabGlowLayer(this));
      this.addLayer(new EctoSlabRenderer.EctoSlabLightLayer(this));
   }

   @NotNull
   public ResourceLocation getTextureLocation(EctoSlab entity) {
      if (entity.isPetrified()) {
         return NetherExp.netherexpPath("textures/entity/ecto_slab/petrified.png");
      } else {
         return entity.getStackCooldown() > 20
            ? NetherExp.netherexpPath("textures/entity/ecto_slab/cracked.png")
            : NetherExp.netherexpPath("textures/entity/ecto_slab/active.png");
      }
   }

   protected float getShadowRadius(EctoSlab entity) {
      return entity.currentShadowRadius;
   }

   public void render(EctoSlab entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
      float targetShadowRadius = entity.isBurrowed() ? 0.0F : 0.8F;
      float delta = Minecraft.getInstance().getTimer().getGameTimeDeltaTicks();
      entity.currentShadowRadius = Mth.lerp(delta * 0.5F, entity.currentShadowRadius, targetShadowRadius);
      super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
   }

   private static class Animation {
      private static final float BURROW_DEPTH = 13.0F;
      public static final AnimationDefinition IDLE = createIdle(false);
      public static final AnimationDefinition IDLE_MIRRORED = createIdle(true);
      public static final AnimationDefinition IDLE_BURROWED = createIdleBurrowed();
      public static final AnimationDefinition IDLE_PETRIFIED = createIdlePetrified();
      public static final AnimationDefinition PETRIFIED_HIT = createPetrifiedHit();
      public static final AnimationDefinition BURROW = createBurrow();
      public static final AnimationDefinition EMERGE = createEmerge();

      private static AnimationDefinition createIdle(boolean mirrored) {
         Builder builder = Builder.withLength(2.0F).looping();

         for (int i = 0; i < 16; i++) {
            float yDegrees = i % 2 == 0 ? -1.0F : 1.0F;
            if (mirrored) {
               yDegrees *= -1.0F;
            }

            builder.addAnimation(
               "body" + i,
               new AnimationChannel(
                  Targets.ROTATION,
                  new Keyframe[]{
                     new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
                     new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 90.0F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                     new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, 104.17F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                     new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 90.0F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                     new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 90.0F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                     new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 180.0F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                     new Keyframe(0.7917F, KeyframeAnimations.degreeVec(0.0F, 194.17F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                     new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 180.0F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                     new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 180.0F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                     new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 270.0F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                     new Keyframe(1.2917F, KeyframeAnimations.degreeVec(0.0F, 284.17F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                     new Keyframe(1.3333F, KeyframeAnimations.degreeVec(0.0F, 270.0F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                     new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 270.0F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                     new Keyframe(1.75F, KeyframeAnimations.degreeVec(0.0F, 360.0F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                     new Keyframe(1.7917F, KeyframeAnimations.degreeVec(0.0F, 375.21F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                     new Keyframe(1.8333F, KeyframeAnimations.degreeVec(0.0F, 360.0F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                     new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 360.0F * yDegrees, 0.0F), Interpolations.LINEAR),
                     new Keyframe(2.001F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
                  }
               )
            );
         }

         return builder.build();
      }

      private static AnimationDefinition createIdlePetrified() {
         Builder builder = Builder.withLength(0.01F).looping();

         for (int i = 0; i < 16; i++) {
            float posY = i == 0 ? 0.0F : 0.0F - i;
            builder.addAnimation(
               "body" + i,
               new AnimationChannel(Targets.ROTATION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)})
            );
            builder.addAnimation(
               "body" + i,
               new AnimationChannel(Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, posY, 0.0F), Interpolations.LINEAR)})
            );
         }

         return builder.build();
      }

      private static AnimationDefinition createPetrifiedHit() {
         Builder builder = Builder.withLength(2.0F);

         for (int i = 0; i < 16; i++) {
            float yDegrees = i % 2 == 0 ? -1.0F : 1.0F;
            builder.addAnimation(
               "body" + i,
               new AnimationChannel(
                  Targets.ROTATION,
                  new Keyframe[]{
                     new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
                     new Keyframe(0.0762F, KeyframeAnimations.degreeVec(0.0F, 12.14F * yDegrees, 0.0F), Interpolations.LINEAR),
                     new Keyframe(0.127F, KeyframeAnimations.degreeVec(0.0F, -8.93F * yDegrees, 0.0F), Interpolations.LINEAR),
                     new Keyframe(0.1778F, KeyframeAnimations.degreeVec(0.0F, 5.0F * yDegrees, 0.0F), Interpolations.LINEAR),
                     new Keyframe(0.2541F, KeyframeAnimations.degreeVec(0.0F, -3.75F * yDegrees, 0.0F), Interpolations.LINEAR),
                     new Keyframe(0.3303F, KeyframeAnimations.degreeVec(0.0F, 2.5F * yDegrees, 0.0F), Interpolations.LINEAR),
                     new Keyframe(0.4065F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
                  }
               )
            );
         }

         return builder.build();
      }

      private static AnimationDefinition createIdleBurrowed() {
         Builder builder = Builder.withLength(2.0F).looping();

         for (int i = 0; i < 16; i++) {
            float depth = -13.0F * (i + 1);
            float yDegrees = i % 2 == 0 ? -1.0F : 1.0F;
            builder.addAnimation(
                  "body" + i,
                  new AnimationChannel(
                     Targets.ROTATION,
                     new Keyframe[]{
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.degreeVec(0.0F, 90.0F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(0.2917F, KeyframeAnimations.degreeVec(0.0F, 104.17F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0.0F, 90.0F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 90.0F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(0.75F, KeyframeAnimations.degreeVec(0.0F, 180.0F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(0.7917F, KeyframeAnimations.degreeVec(0.0F, 194.17F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0.0F, 180.0F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 180.0F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(1.25F, KeyframeAnimations.degreeVec(0.0F, 270.0F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(1.2917F, KeyframeAnimations.degreeVec(0.0F, 284.17F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(1.3333F, KeyframeAnimations.degreeVec(0.0F, 270.0F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(1.5F, KeyframeAnimations.degreeVec(0.0F, 270.0F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(1.75F, KeyframeAnimations.degreeVec(0.0F, 360.0F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(1.7917F, KeyframeAnimations.degreeVec(0.0F, 375.21F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(1.8333F, KeyframeAnimations.degreeVec(0.0F, 360.0F * yDegrees, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 360.0F * yDegrees, 0.0F), Interpolations.LINEAR),
                        new Keyframe(2.001F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR)
                     }
                  )
               )
               .addAnimation(
                  "body" + i,
                  new AnimationChannel(
                     Targets.POSITION, new Keyframe[]{new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, depth, 0.0F), Interpolations.CATMULLROM)}
                  )
               );
         }

         builder.addAnimation(
               "light",
               new AnimationChannel(
                  Targets.ROTATION,
                  new Keyframe[]{
                     new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
                     new Keyframe(2.0F, KeyframeAnimations.degreeVec(0.0F, 360.0F, 0.0F), Interpolations.LINEAR)
                  }
               )
            )
            .addAnimation(
               "light",
               new AnimationChannel(
                  Targets.SCALE,
                  new Keyframe[]{
                     new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.8F, 1.0, 0.8F), Interpolations.CATMULLROM),
                     new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 0.9F, 1.0), Interpolations.CATMULLROM),
                     new Keyframe(2.0F, KeyframeAnimations.scaleVec(0.8F, 1.0, 0.8F), Interpolations.CATMULLROM)
                  }
               )
            );
         return builder.build();
      }

      private static AnimationDefinition createBurrow() {
         Builder builder = Builder.withLength(1.0F);

         for (int i = 0; i < 16; i++) {
            float depth = -13.0F * (i + 1);
            float scale = i + 1;
            builder.addAnimation(
                  "body" + i,
                  new AnimationChannel(
                     Targets.ROTATION,
                     new Keyframe[]{
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(0.0833F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(0.2083F, KeyframeAnimations.degreeVec(16.9388F, 45.8965F, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(0.375F, KeyframeAnimations.degreeVec(0.0F, 17.5F, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, -720.0F, 0.0F), Interpolations.CATMULLROM)
                     }
                  )
               )
               .addAnimation(
                  "body" + i,
                  new AnimationChannel(
                     Targets.POSITION,
                     new Keyframe[]{
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(0.125F, KeyframeAnimations.posVec(0.0F, 11.0F * scale, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(0.2083F, KeyframeAnimations.posVec(0.0F, 4.63F * scale, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(0.375F, KeyframeAnimations.posVec(0.0F, 0.24F * scale, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, depth, 0.0F), Interpolations.CATMULLROM)
                     }
                  )
               );
         }

         builder.addAnimation(
            "light",
            new AnimationChannel(
               Targets.SCALE,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 0.0, 1.0), Interpolations.LINEAR),
                  new Keyframe(0.25F, KeyframeAnimations.scaleVec(1.0, 0.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(0.6667F, KeyframeAnimations.scaleVec(1.0, 0.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(0.8333F, KeyframeAnimations.scaleVec(0.5, 2.0, 0.5), Interpolations.CATMULLROM),
                  new Keyframe(1.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM)
               }
            )
         );
         return builder.build();
      }

      private static AnimationDefinition createEmerge() {
         Builder builder = Builder.withLength(0.7917F);

         for (int i = 0; i < 16; i++) {
            float depth = -13.0F * (i + 1);
            builder.addAnimation(
                  "body" + i,
                  new AnimationChannel(
                     Targets.ROTATION,
                     new Keyframe[]{
                        new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), Interpolations.LINEAR),
                        new Keyframe(0.375F, KeyframeAnimations.degreeVec(-25.0F, -720.0F, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(0.5F, KeyframeAnimations.degreeVec(28.3216F, -733.2721F, -7.0531F), Interpolations.CATMULLROM),
                        new Keyframe(0.625F, KeyframeAnimations.degreeVec(28.627F, -704.5303F, 8.2834F), Interpolations.CATMULLROM),
                        new Keyframe(0.7917F, KeyframeAnimations.degreeVec(-2.5F, -720.0F, 0.0F), Interpolations.CATMULLROM)
                     }
                  )
               )
               .addAnimation(
                  "body" + i,
                  new AnimationChannel(
                     Targets.POSITION,
                     new Keyframe[]{
                        new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, depth, 0.0F), Interpolations.LINEAR),
                        new Keyframe(0.25F, KeyframeAnimations.posVec(0.0F, 18.0F, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(0.5F, KeyframeAnimations.posVec(0.0F, 18.0F, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(0.6667F, KeyframeAnimations.posVec(0.0F, 4.0F, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(0.7083F, KeyframeAnimations.posVec(0.0F, 4.0F, 0.0F), Interpolations.CATMULLROM),
                        new Keyframe(0.7917F, KeyframeAnimations.posVec(0.0F, 1.0F, 0.0F), Interpolations.CATMULLROM)
                     }
                  )
               );
         }

         builder.addAnimation(
            "light",
            new AnimationChannel(
               Targets.SCALE,
               new Keyframe[]{
                  new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0, 1.0, 1.0), Interpolations.CATMULLROM),
                  new Keyframe(0.1667F, KeyframeAnimations.scaleVec(1.0, 0.0, 1.0), Interpolations.CATMULLROM)
               }
            )
         );
         return builder.build();
      }
   }

   public static class EctoSlabGlowLayer extends RenderLayer<EctoSlab, EctoSlabRenderer.EctoSlabModel<EctoSlab>> {
      public EctoSlabGlowLayer(RenderLayerParent<EctoSlab, EctoSlabRenderer.EctoSlabModel<EctoSlab>> renderer) {
         super(renderer);
      }

      protected ResourceLocation getTextureLocation(EctoSlab entity) {
         return entity.getStackCooldown() > 20
            ? NetherExp.netherexpPath("textures/entity/ecto_slab/cracked_glow.png")
            : NetherExp.netherexpPath("textures/entity/ecto_slab/active_glow.png");
      }

      public void render(
         PoseStack poseStack,
         MultiBufferSource buffer,
         int packedLight,
         EctoSlab entity,
         float limbSwing,
         float limbSwingAmount,
         float partialTicks,
         float ageInTicks,
         float netHeadYaw,
         float headPitch
      ) {
         if (!entity.isPetrified()) {
            VertexConsumer vertexConsumer = buffer.getBuffer(this.renderType(entity));
            ((EctoSlabRenderer.EctoSlabModel)this.getParentModel()).renderToBuffer(poseStack, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY);
         }
      }

      @NotNull
      public RenderType renderType(EctoSlab entity) {
         return RenderType.eyes(this.getTextureLocation(entity));
      }
   }

   public static class EctoSlabLightLayer extends RenderLayer<EctoSlab, EctoSlabRenderer.EctoSlabModel<EctoSlab>> {
      public EctoSlabLightLayer(RenderLayerParent<EctoSlab, EctoSlabRenderer.EctoSlabModel<EctoSlab>> renderer) {
         super(renderer);
      }

      public void render(
         PoseStack poseStack,
         MultiBufferSource buffer,
         int packedLight,
         EctoSlab entity,
         float limbSwing,
         float limbSwingAmount,
         float partialTick,
         float ageInTicks,
         float netHeadYaw,
         float headPitch
      ) {
         if (entity.showLight) {
            VertexConsumer vertexConsumer = buffer.getBuffer(this.renderType(entity));
            ((EctoSlabRenderer.EctoSlabModel)this.getParentModel()).renderToBuffer(poseStack, vertexConsumer, 15728880, OverlayTexture.NO_OVERLAY);
         }
      }

      @NotNull
      public RenderType renderType(EctoSlab entity) {
         return JNERenderType.entityAdditive(NetherExp.netherexpPath("textures/entity/ecto_slab/light.png"));
      }
   }

   public static class EctoSlabModel<T extends EctoSlab> extends HierarchicalModel<T> {
      public static final ModelLayerLocation LAYER = new ModelLayerLocation(NetherExp.netherexpPath("ecto_slab"), "main");
      private final ModelPart ecto_slab;
      private final ModelPart[] segments = new ModelPart[16];
      private final ModelPart light;

      public EctoSlabModel(ModelPart root) {
         this.ecto_slab = root.getChild("ecto_slab");

         for (int i = 0; i < 16; i++) {
            this.segments[i] = this.ecto_slab.getChild("segment" + i);
         }

         this.light = this.ecto_slab.getChild("light");
      }

      public static LayerDefinition createBodyLayer() {
         MeshDefinition meshdefinition = new MeshDefinition();
         PartDefinition partdefinition = meshdefinition.getRoot();
         PartDefinition ecto_slab = partdefinition.addOrReplaceChild("ecto_slab", CubeListBuilder.create(), PartPose.offset(0.0F, 18.0F, 0.0F));

         for (int i = 0; i < 16; i++) {
            float yRot = (float)Math.toRadians(90 * i);
            PartDefinition segment = ecto_slab.addOrReplaceChild(
               "segment" + i, CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -13.0F * i, 0.0F, 0.0F, yRot, 0.0F)
            );
            segment.addOrReplaceChild(
               "body" + i,
               CubeListBuilder.create().texOffs(0, 0).addBox(-11.0F, -6.0F, -11.0F, 22.0F, 12.0F, 22.0F, new CubeDeformation(0.0F)),
               PartPose.offset(0.0F, 0.0F, 0.0F)
            );
         }

         ecto_slab.addOrReplaceChild(
            "light",
            CubeListBuilder.create().texOffs(0, 34).addBox(-9.0F, -36.0F, -9.0F, 18.0F, 36.0F, 18.0F, new CubeDeformation(0.0F)),
            PartPose.offset(0.0F, 6.0F, 0.0F)
         );
         return LayerDefinition.create(meshdefinition, 128, 128);
      }

      public ModelPart root() {
         return this.ecto_slab;
      }

      public void setupAnim(EctoSlab entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
         this.root().getAllParts().forEach(ModelPart::resetPose);
         int stackSize = entity.getStackSize();
         int maxStack = EctoSlab.absoluteMaximumStackSize();

         for (int i = 0; i < maxStack && i < this.segments.length; i++) {
            this.segments[i].visible = i < stackSize;
         }

         this.animate(entity.idleAnimation, EctoSlabRenderer.Animation.IDLE, ageInTicks);
         this.animate(entity.idleMirroredAnimation, EctoSlabRenderer.Animation.IDLE_MIRRORED, ageInTicks);
         this.animate(entity.idleBurrowedAnimation, EctoSlabRenderer.Animation.IDLE_BURROWED, ageInTicks);
         this.animate(entity.idlePetrifiedAnimation, EctoSlabRenderer.Animation.IDLE_PETRIFIED, ageInTicks);
         this.animate(entity.petrifiedHitAnimation, EctoSlabRenderer.Animation.PETRIFIED_HIT, ageInTicks);
         this.animate(entity.burrowAnimation, EctoSlabRenderer.Animation.BURROW, ageInTicks);
         this.animate(entity.emergeAnimation, EctoSlabRenderer.Animation.EMERGE, ageInTicks);
      }
   }
}
