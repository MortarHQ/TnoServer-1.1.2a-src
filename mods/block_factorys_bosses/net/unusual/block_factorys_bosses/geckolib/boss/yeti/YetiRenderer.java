package net.unusual.block_factorys_bosses.geckolib.boss.yeti;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.camera.BossesRiseClientCinematicCamera;
import net.unusual.block_factorys_bosses.entity.boss.yeti.YetiEntity;
import net.unusual.block_factorys_bosses.geckolib.MatrixAnimationProcessor;
import net.unusual.block_factorys_bosses.geckolib.boss.CinematicRenderer;
import net.unusual.block_factorys_bosses.geckolib.util.BossesRiseAnimationCache;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;
import net.unusual.block_factorys_bosses.network.BlockFactorysBossesModVariables;
import net.unusual.block_factorys_bosses.util.AnimationUtil;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.joml.Vector3f;
import software.bernie.geckolib.GeckoLibConstants;
import software.bernie.geckolib.animation.Animation;
import software.bernie.geckolib.animation.AnimationProcessor;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.keyframe.BoneAnimation;
import software.bernie.geckolib.animation.state.BoneSnapshot;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.loading.object.BakedAnimations;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class YetiRenderer extends GeoEntityRenderer<YetiEntity> implements CinematicRenderer<YetiEntity> {
   public static final ResourceLocation DEFAULT = BossesRise.prefix("yeti_boss");
   public static final ResourceLocation PLUS_MODEL = BossesRise.prefix("geo/entity/yeti_boss_plus.geo.json");
   public static final ResourceLocation PLUS_TEXTURE = BossesRise.prefix("textures/entity/yeti_boss_plus.png");
   public static final ResourceLocation ANIM_PATH = BossesRise.prefix("animations/entity/yeti_boss.animation.json");
   public static final ResourceLocation DEAD_MODEL = BossesRise.prefix("geo/entity/dead_yeti.geo.json");
   public static final ResourceLocation DEAD_TEXTURE = BossesRise.prefix("textures/entity/dead_yeti.png");
   public static final String GET_HIT = "animation.get_hit";

   public YetiRenderer(Context context) {
      super(context, new YetiRenderer.YetiGeoModel());
   }

   public void defaultRender(
      PoseStack poseStack,
      YetiEntity yeti,
      MultiBufferSource bufferSource,
      @Nullable RenderType renderType,
      @Nullable VertexConsumer buffer,
      float yaw,
      float partialTick,
      int packedLight
   ) {
      super.defaultRender(poseStack, yeti, bufferSource, renderType, buffer, yaw, partialTick, packedLight);
      if (!Minecraft.getInstance().isPaused()
         && (Integer)yeti.getEntityData().get(YetiEntity.DATA_GROUNDSMASH_ANIMTIME) > 10
         && (Integer)yeti.getEntityData().get(YetiEntity.DATA_GROUNDSMASH_ANIMTIME) <= 119) {
         this.model.getBone("CTR_head").ifPresent(geoBone -> {
            Vec3 position = yeti.position().add(geoBone.getLocalPosition().x, geoBone.getLocalPosition().y, geoBone.getLocalPosition().z);
            BlockFactorysBossesModVariables.cinematicX = position.x;
            BlockFactorysBossesModVariables.cinematicY = position.y - 1.0;
            BlockFactorysBossesModVariables.cinematicZ = position.z;
         });
      }
   }

   public ResourceLocation getTextureLocation(YetiEntity animatable) {
      return super.getTextureLocation(animatable);
   }

   public Pair<Float, Float> getCinematicYawAndPitch(YetiEntity animatable, GeoBone cameraBone, LocalPlayer player) {
      Vector3d camPos = cameraBone.getWorldPosition();
      Vec3 targetPos = animatable.getEyePosition();
      double dx = camPos.x() - targetPos.x;
      double dy = camPos.y() - targetPos.y;
      double dz = camPos.z() - targetPos.z;
      float yaw = (float)(Math.atan2(dx, dz) * -180.0F / (float)Math.PI + 180.0);
      float pitch = (float)(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)) * 180.0F / (float)Math.PI);
      return new Pair(yaw, pitch);
   }

   public boolean shouldRender(YetiEntity livingEntity, Frustum camera, double camX, double camY, double camZ) {
      return BossesRiseClientCinematicCamera.getTrackedObject() == livingEntity || super.shouldRender(livingEntity, camera, camX, camY, camZ);
   }

   private static class YetiGeoModel extends CustomEntityGeoModel<YetiEntity> {
      private final MatrixAnimationProcessor<YetiEntity> yetiProcessor = new MatrixAnimationProcessor(this);
      private BakedGeoModel currentYetiModel = null;

      public YetiGeoModel() {
         super(YetiRenderer.DEFAULT);
      }

      public BakedGeoModel getBakedModel(ResourceLocation location) {
         BakedGeoModel model = (BakedGeoModel)GeckoLibCache.getBakedModels().get(location);
         if (model == null) {
            if (!location.getPath().contains("geo/")) {
               throw GeckoLibConstants.exception(location, "Invalid model resource path provided - GeckoLib models must be placed in assets/<modid>/geo/");
            } else {
               throw GeckoLibConstants.exception(location, "Unable to find model");
            }
         } else {
            if (model != this.currentYetiModel) {
               this.yetiProcessor.setActiveModel(model);
               this.currentYetiModel = model;
            }

            return this.currentYetiModel;
         }
      }

      public AnimationProcessor<YetiEntity> getAnimationProcessor() {
         return this.yetiProcessor;
      }

      public void setCustomAnimations(YetiEntity yeti, long instanceId, AnimationState<YetiEntity> animationState) {
         super.setCustomAnimations(yeti, instanceId, animationState);
         this.getBone("icespike").ifPresent(bone -> bone.setHidden(YetiEntity.YetiState.byState(yeti.getState()) != YetiEntity.YetiState.THROW_SPIKE));
         long hitTime = (Long)yeti.getEntityData().get(YetiEntity.DATA_HIT_TICK);
         if (hitTime >= 0L) {
            BakedAnimations bakedAnimations = BossesRiseAnimationCache.getBakedAnimations().get(YetiRenderer.ANIM_PATH);
            if (bakedAnimations != null) {
               Animation animation = bakedAnimations.getAnimation("animation.get_hit");
               if (animation != null) {
                  int tick = (int)(yeti.level().getGameTime() - hitTime);
                  if (tick <= 27) {
                     float completion = (tick + animationState.getPartialTick()) / 27.0F;
                     double adjustedTick = animation.length() * completion;
                     float oldFactor = Math.max(0.5F, completion * completion);
                     float newFactor = 1.0F - oldFactor;

                     for (BoneAnimation boneAnimation : animation.boneAnimations()) {
                        Vector3f rotations = AnimationUtil.getAnimationPointAtTick(boneAnimation.rotationKeyFrames(), adjustedTick, true);
                        Vector3f positions = AnimationUtil.getAnimationPointAtTick(boneAnimation.positionKeyFrames(), adjustedTick, false);
                        this.getBone(boneAnimation.boneName()).ifPresent(bone -> {
                           BoneSnapshot initialSnapshot = bone.getInitialSnapshot();
                           if (rotations != null) {
                              float newX = rotations.x + initialSnapshot.getRotX();
                              float newY = rotations.y + initialSnapshot.getRotY();
                              float newZ = rotations.z + initialSnapshot.getRotZ();
                              bone.setRotX(newX * newFactor + bone.getRotX() * oldFactor);
                              bone.setRotY(newY * newFactor + bone.getRotY() * oldFactor);
                              bone.setRotZ(newZ * newFactor + bone.getRotZ() * oldFactor);
                           }

                           if (positions != null) {
                              bone.setPosX(positions.x * newFactor + bone.getPosX() * oldFactor);
                              bone.setPosY(positions.y * newFactor + bone.getPosY() * oldFactor);
                              bone.setPosZ(positions.z * newFactor + bone.getPosZ() * oldFactor);
                           }
                        });
                     }
                  }
               }
            }
         }
      }

      public ResourceLocation getModelResource(YetiEntity yeti) {
         return yeti.isDead() ? YetiRenderer.DEAD_MODEL : (yeti.isEnraged() ? YetiRenderer.PLUS_MODEL : super.getModelResource(yeti));
      }

      public ResourceLocation getTextureResource(YetiEntity yeti) {
         return yeti.isDead() ? YetiRenderer.DEAD_TEXTURE : (yeti.isEnraged() ? YetiRenderer.PLUS_TEXTURE : super.getTextureResource(yeti));
      }
   }
}
