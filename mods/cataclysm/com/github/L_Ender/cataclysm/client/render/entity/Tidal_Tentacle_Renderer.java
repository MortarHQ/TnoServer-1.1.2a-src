package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Tidal_Tentacle_Claws_Model;
import com.github.L_Ender.cataclysm.client.model.entity.Tidal_Tentacle_Model;
import com.github.L_Ender.cataclysm.entity.projectile.Tidal_Tentacle_Entity;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class Tidal_Tentacle_Renderer extends EntityRenderer<Tidal_Tentacle_Entity> {
   private static final ResourceLocation CLAW_TEXTURE = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/tidal_tentacle_claws.png");
   private static final ResourceLocation TENTACLE_TEXTURE = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/tidal_tentacle.png");
   private static final Tidal_Tentacle_Claws_Model CLAW_MODEL = new Tidal_Tentacle_Claws_Model();
   private static final Tidal_Tentacle_Model TONGUE_MODEL = new Tidal_Tentacle_Model();
   public static final int MAX_NECK_SEGMENTS = 128;

   public Tidal_Tentacle_Renderer(Context renderManagerIn) {
      super(renderManagerIn);
   }

   public boolean shouldRender(Tidal_Tentacle_Entity entity, Frustum frustum, double x, double y, double z) {
      Entity next = entity.getFromEntity();
      return next != null && frustum.isVisible(entity.getBoundingBox().minmax(next.getBoundingBox())) || super.shouldRender(entity, frustum, x, y, z);
   }

   public void render(Tidal_Tentacle_Entity entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
      super.render(entity, yaw, partialTicks, poseStack, buffer, light);
      poseStack.pushPose();
      Entity fromEntity = entity.getFromEntity();
      float x = (float)Mth.lerp(partialTicks, entity.xo, entity.getX());
      float y = (float)Mth.lerp(partialTicks, entity.yo, entity.getY());
      float z = (float)Mth.lerp(partialTicks, entity.zo, entity.getZ());
      if (fromEntity != null) {
         float progress = (entity.prevProgress + (entity.getProgress() - entity.prevProgress) * partialTicks) / 5.0F;
         Vec3 distVec = this.getPositionOfPriorMob(entity, fromEntity, partialTicks).subtract(x, y, z);
         Vec3 to = distVec.scale(1.0F - progress);
         Vec3 from = distVec;
         int segmentCount = 0;
         Vec3 currentNeckButt = from;
         VertexConsumer neckConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(TENTACLE_TEXTURE));

         for (double remainingDistance = to.distanceTo(from); segmentCount < 128 && remainingDistance > 0.0; segmentCount++) {
            remainingDistance = Math.min(from.distanceTo(to), 0.5);
            Vec3 linearVec = to.subtract(currentNeckButt);
            Vec3 powVec = new Vec3(this.modifyVecAngle(linearVec.x), this.modifyVecAngle(linearVec.y), this.modifyVecAngle(linearVec.z));
            Vec3 smoothedVec = powVec;
            Vec3 next = smoothedVec.normalize().scale(remainingDistance).add(currentNeckButt);
            int neckLight = this.getLightColor(entity, to.add(currentNeckButt).add(x, y, z));
            renderNeckCube(currentNeckButt, next, poseStack, neckConsumer, neckLight, OverlayTexture.NO_OVERLAY, 0.0F);
            currentNeckButt = next;
         }

         VertexConsumer clawConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(CLAW_TEXTURE));
         if (entity.hasClaw() || entity.isRetracting()) {
            poseStack.pushPose();
            poseStack.translate(to.x, to.y, to.z);
            float rotY = (float)(Mth.atan2(to.x, to.z) * 180.0F / (float)Math.PI);
            float rotX = (float)(-(Mth.atan2(to.y, to.horizontalDistance()) * 180.0F / (float)Math.PI));
            CLAW_MODEL.setAttributes(rotX, rotY);
            CLAW_MODEL.renderToBuffer(poseStack, clawConsumer, this.getLightColor(entity, to.add(x, y, z)), OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
         }
      }

      poseStack.popPose();
   }

   public static void renderNeckCube(Vec3 from, Vec3 to, PoseStack poseStack, VertexConsumer buffer, int packedLightIn, int overlayCoords, float additionalYaw) {
      Vec3 sub = from.subtract(to);
      double d = sub.horizontalDistance();
      float rotY = (float)(Mth.atan2(sub.x, sub.z) * 180.0F / (float)Math.PI);
      float rotX = (float)(-(Mth.atan2(sub.y, d) * 180.0F / (float)Math.PI)) - 90.0F;
      poseStack.pushPose();
      poseStack.translate(from.x, from.y, from.z);
      TONGUE_MODEL.setAttributes((float)sub.length(), rotX, rotY, additionalYaw);
      TONGUE_MODEL.renderToBuffer(poseStack, buffer, packedLightIn, overlayCoords);
      poseStack.popPose();
   }

   private Vec3 getPositionOfPriorMob(Tidal_Tentacle_Entity segment, Entity mob, float partialTicks) {
      double d4 = Mth.lerp(partialTicks, mob.xo, mob.getX());
      double d5 = Mth.lerp(partialTicks, mob.yo, mob.getY());
      double d6 = Mth.lerp(partialTicks, mob.zo, mob.getZ());
      float f3 = 0.0F;
      if (mob instanceof Player && segment.isCreator(mob)) {
         Player player = (Player)mob;
         float f = player.getAttackAnim(partialTicks);
         float f1 = Mth.sin(Mth.sqrt(f) * (float) Math.PI);
         float f2 = Mth.lerp(partialTicks, player.yBodyRotO, player.yBodyRot) * (float) (Math.PI / 180.0);
         int i = player.getMainArm() == HumanoidArm.RIGHT ? 1 : -1;
         ItemStack itemstack = player.getMainHandItem();
         if (!itemstack.is((Item)ModItems.TIDAL_CLAWS.get())) {
            i = -i;
         }

         double d0 = Mth.sin(f2);
         double d1 = Mth.cos(f2);
         double d2 = i * 0.35;
         if ((this.entityRenderDispatcher.options == null || this.entityRenderDispatcher.options.getCameraType().isFirstPerson())
            && player == Minecraft.getInstance().player) {
            double d7 = 960.0 / ((Integer)this.entityRenderDispatcher.options.fov().get()).intValue();
            Vec3 vec3 = this.entityRenderDispatcher.camera.getNearPlane().getPointOnPlane(i * 0.6F, -1.0F);
            vec3 = vec3.scale(d7);
            vec3 = vec3.yRot(f1 * 0.25F);
            vec3 = vec3.xRot(-f1 * 0.35F);
            d4 = Mth.lerp(partialTicks, player.xo, player.getX()) + vec3.x;
            d5 = Mth.lerp(partialTicks, player.yo, player.getY()) + vec3.y;
            d6 = Mth.lerp(partialTicks, player.zo, player.getZ()) + vec3.z;
            f3 = player.getEyeHeight() * 0.5F;
         } else {
            d4 = Mth.lerp(partialTicks, player.xo, player.getX()) - d1 * d2 - d0 * 0.2;
            d5 = player.yo + player.getEyeHeight() + (player.getY() - player.yo) * partialTicks - 1.0;
            d6 = Mth.lerp(partialTicks, player.zo, player.getZ()) - d0 * d2 + d1 * 0.2;
            f3 = (player.isCrouching() ? -0.1875F : 0.0F) - player.getEyeHeight() * 0.4F;
         }
      }

      return new Vec3(d4, d5 + f3, d6);
   }

   private double modifyVecAngle(double dimension) {
      float abs = (float)Math.abs(dimension);
      return Math.signum(dimension) * Mth.clamp(Math.pow(abs, 0.1), 0.05 * abs, abs);
   }

   private int getLightColor(Entity head, Vec3 vec3) {
      BlockPos blockpos = BlockPos.containing(vec3);
      if (head.level().hasChunkAt(blockpos)) {
         int i = LevelRenderer.getLightColor(head.level(), blockpos);
         int j = LevelRenderer.getLightColor(head.level(), blockpos.above());
         int k = i & 0xFF;
         int l = j & 0xFF;
         int i1 = i >> 16 & 0xFF;
         int j1 = j >> 16 & 0xFF;
         return (k > l ? k : l) | (i1 > j1 ? i1 : j1) << 16;
      } else {
         return 0;
      }
   }

   public ResourceLocation getTextureLocation(Tidal_Tentacle_Entity entity) {
      return CLAW_TEXTURE;
   }
}
