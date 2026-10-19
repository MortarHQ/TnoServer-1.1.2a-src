package cn.leolezury.eternalstarlight.common.client.renderer.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.model.ESModelUtil;
import cn.leolezury.eternalstarlight.common.client.model.entity.TheGatekeeperModel;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.TheGatekeeperClothingLayer;
import cn.leolezury.eternalstarlight.common.entity.living.boss.gatekeeper.TheGatekeeper;
import cn.leolezury.eternalstarlight.common.util.ModelPartPose;
import cn.leolezury.eternalstarlight.common.util.ModelSnapshot;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.client.resources.PlayerSkin.Model;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class TheGatekeeperRenderer<T extends TheGatekeeper> extends MobRenderer<T, TheGatekeeperModel<T>> {
   private static final ResourceLocation ENTITY_TEXTURE = EternalStarlight.id("textures/entity/the_gatekeeper.png");
   private static final ResourceLocation SLIM_ENTITY_TEXTURE = EternalStarlight.id("textures/entity/the_gatekeeper_slim.png");
   private static final int PHANTOM_AMOUNT = 5;
   private static final int SNAPSHOT_LIFESPAN = 15;
   private static final Map<String, GameProfile> PROFILES = new HashMap<>();
   private final TheGatekeeperModel<T> normalModel;
   private final TheGatekeeperModel<T> slimModel;
   private boolean renderingPhantom = false;

   public TheGatekeeperRenderer(Context context) {
      super(context, new TheGatekeeperModel(context.bakeLayer(TheGatekeeperModel.LAYER_LOCATION), false), 0.5F);
      this.normalModel = (TheGatekeeperModel<T>)this.getModel();
      this.slimModel = new TheGatekeeperModel<>(context.bakeLayer(TheGatekeeperModel.SLIM_LAYER_LOCATION), true);
      this.addLayer(
         new ItemInHandLayer<T, TheGatekeeperModel<T>>(this, context.getItemInHandRenderer()) {
            public void render(
               PoseStack poseStack,
               MultiBufferSource buffer,
               int packedLight,
               T livingEntity,
               float limbSwing,
               float limbSwingAmount,
               float partialTicks,
               float ageInTicks,
               float netHeadYaw,
               float headPitch
            ) {
               if (!TheGatekeeperRenderer.this.renderingPhantom) {
                  super.render(poseStack, buffer, packedLight, livingEntity, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
               }
            }
         }
      );
      this.addLayer(new TheGatekeeperClothingLayer(this, context.getModelSet()));
   }

   public void render(T entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource bufferSource, int packedLight) {
      SkinManager skinManager = Minecraft.getInstance().getSkinManager();
      getGameProfile(entity).ifPresent(p -> this.model = skinManager.getInsecureSkin(p).model() == Model.SLIM ? this.slimModel : this.normalModel);
      if (entity.getBehaviorState() == 12) {
         entity.teleportAnimationState.updateTime(entity.tickCount + partialTicks, 1.0F);
         float progress = Math.min(
            (1.0F - Math.abs(Mth.clamp((float)entity.teleportAnimationState.getAccumulatedTime() / 1000.0F * 20.0F, 0.0F, 37.0F) / 37.0F - 0.5F) * 2.0F) * 1.5F,
            1.0F
         );
         float radius = progress * 0.5F;
         this.renderingPhantom = true;
         ((TheGatekeeperModel)this.getModel()).alphaFactor = 1.0F - progress;
         super.render(entity, entityYaw, partialTicks, stack, bufferSource, packedLight);
         ((TheGatekeeperModel)this.getModel()).alphaFactor /= 3.0F;

         for (int i = 0; i < 5; i++) {
            stack.pushPose();
            float spin = progress * (float) (Math.PI / 2) + i * (float) (Math.PI * 2) / 5.0F;
            Vector3f offset = new Vector3f(radius * Mth.sin(spin), radius * Mth.cos(spin), 0.0F);
            offset.rotate(this.entityRenderDispatcher.cameraOrientation());
            stack.translate(offset.x, offset.y, offset.z);
            super.render(entity, entityYaw, partialTicks, stack, bufferSource, packedLight);
            stack.popPose();
         }

         this.renderingPhantom = false;
         ((TheGatekeeperModel)this.getModel()).alphaFactor = 1.0F;
      } else {
         super.render(entity, entityYaw, partialTicks, stack, bufferSource, packedLight);
      }

      if (entity.isAlive()) {
         double currentX = Mth.lerp(partialTicks, entity.xo, entity.getX());
         double currentY = Mth.lerp(partialTicks, entity.yo, entity.getY());
         double currentZ = Mth.lerp(partialTicks, entity.zo, entity.getZ());
         float currentTick = this.getBob(entity, partialTicks);
         if (entity.trailSnapshots.isEmpty() || currentTick - entity.lastTrailTick > 3.0F) {
            if (entity.shouldAddTrailSnapshot()) {
               Map<String, ModelPartPose> snapshot = ESModelUtil.saveModelSnapshot(
                  ((TheGatekeeperModel)this.getModel()).allPartNames, ((TheGatekeeperModel)this.getModel())::getAnyDescendantWithName
               );
               entity.trailSnapshots
                  .addFirst(
                     Pair.of(
                        new Vec3(currentX, currentY, currentZ),
                        new ModelSnapshot(0.0F, Mth.rotLerp(partialTicks, entity.yBodyRotO, entity.yBodyRot), currentTick, snapshot)
                     )
                  );
               entity.lastTrailTick = currentTick;
            }

            entity.trailSnapshots.removeIf(p -> currentTick - ((ModelSnapshot)p.getSecond()).timestamp() > 15.0F);

            while (entity.trailSnapshots.size() > 32) {
               entity.trailSnapshots.removeLast();
            }
         }

         for (int i = 0; i < entity.trailSnapshots.size(); i++) {
            stack.pushPose();
            Vec3 trailPos = (Vec3)entity.trailSnapshots.get(i).getFirst();
            ModelSnapshot snapshot = (ModelSnapshot)entity.trailSnapshots.get(i).getSecond();
            ESModelUtil.loadPoseFromSnapshot(snapshot.poses(), ((TheGatekeeperModel)this.getModel())::getAnyDescendantWithName);
            stack.translate(trailPos.x - currentX, trailPos.y - currentY, trailPos.z - currentZ);
            ((TheGatekeeperModel)this.getModel()).alphaFactor = (1.0F - Mth.clamp(currentTick - snapshot.timestamp(), 0.0F, 15.0F) / 15.0F) * 0.3F;
            stack.mulPose(Axis.YP.rotationDegrees(180.0F - snapshot.yRot()));
            stack.scale(-1.0F, -1.0F, 1.0F);
            this.scale(entity, stack, partialTicks);
            stack.translate(0.0F, -1.5F, 0.0F);
            RenderType renderType = ESRenderType.entityTranslucentNoDepth(this.getTextureLocation(entity));
            VertexConsumer vertexConsumer = bufferSource.getBuffer(renderType);
            ((TheGatekeeperModel)this.getModel()).renderToBuffer(stack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
            stack.popPose();
         }

         ((TheGatekeeperModel)this.getModel()).alphaFactor = 1.0F;
      }
   }

   protected void renderNameTag(T entity, Component displayName, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, float partialTick) {
      if (!this.renderingPhantom) {
         super.renderNameTag(entity, displayName, poseStack, bufferSource, packedLight, partialTick);
      }
   }

   protected float getShadowRadius(T mob) {
      return mob.getBehaviorState() == 12 ? 0.0F : super.getShadowRadius(mob);
   }

   public ResourceLocation getTextureLocation(T entity) {
      ResourceLocation texture = this.model == this.slimModel ? SLIM_ENTITY_TEXTURE : ENTITY_TEXTURE;
      SkinManager skinManager = Minecraft.getInstance().getSkinManager();
      Optional<GameProfile> profile = getGameProfile(entity);
      if (profile.isPresent()) {
         PlayerSkin playerSkin = (PlayerSkin)skinManager.getOrLoad(profile.get()).getNow(null);
         if (playerSkin != null && !playerSkin.texture().getPath().startsWith("textures/entity/player/")) {
            texture = playerSkin.texture();
         }
      }

      return texture;
   }

   public static Optional<GameProfile> getGameProfile(TheGatekeeper entity) {
      if (entity.getCustomName() != null) {
         String customName = entity.getCustomName().getString();
         if (!PROFILES.containsKey(customName)) {
            SkullBlockEntity.fetchGameProfile(customName).thenAccept(optional -> optional.ifPresent(p -> PROFILES.put(customName, p)));
         }

         if (PROFILES.containsKey(customName)) {
            return Optional.ofNullable(PROFILES.get(customName));
         }
      }

      return Optional.empty();
   }
}
