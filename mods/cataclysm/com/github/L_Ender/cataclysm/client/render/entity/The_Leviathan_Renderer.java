package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.The_Leviathan_Model;
import com.github.L_Ender.cataclysm.client.model.entity.The_Leviathan_Tongue_End_Model;
import com.github.L_Ender.cataclysm.client.model.entity.The_Leviathan_Tongue_Model;
import com.github.L_Ender.cataclysm.client.render.RenderUtils;
import com.github.L_Ender.cataclysm.client.render.layer.LayerBasicGlow;
import com.github.L_Ender.cataclysm.client.render.layer.The_Leviathan_Layer;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.The_Leviathan_Entity;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.The_Leviathan_Part;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class The_Leviathan_Renderer extends MobRenderer<The_Leviathan_Entity, The_Leviathan_Model> {
   private static final ResourceLocation LEVIATHAN_TEXTURES = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/leviathan/the_leviathan.png");
   private static final ResourceLocation BURNING_LEVIATHAN_TEXTURES = ResourceLocation.fromNamespaceAndPath(
      "cataclysm", "textures/entity/leviathan/the_burning_leviathan.png"
   );
   private static final ResourceLocation LEVIATHAN_TEXTURE_EYES = ResourceLocation.fromNamespaceAndPath(
      "cataclysm", "textures/entity/leviathan/the_leviathan_eye.png"
   );
   private final RandomSource rnd = RandomSource.create();
   private static final The_Leviathan_Tongue_Model TONGUE_MODEL = new The_Leviathan_Tongue_Model();
   private static final The_Leviathan_Tongue_End_Model TONGUE_END_MODEL = new The_Leviathan_Tongue_End_Model();

   public The_Leviathan_Renderer(Context renderManagerIn) {
      super(renderManagerIn, new The_Leviathan_Model(), 1.5F);
      this.addLayer(new The_Leviathan_Layer(this));
      this.addLayer(new LayerBasicGlow(this, LEVIATHAN_TEXTURE_EYES));
   }

   public ResourceLocation getTextureLocation(The_Leviathan_Entity entity) {
      return entity.getMeltDown() ? BURNING_LEVIATHAN_TEXTURES : LEVIATHAN_TEXTURES;
   }

   public boolean shouldRender(The_Leviathan_Entity livingentity, Frustum camera, double camX, double camY, double camZ) {
      if (super.shouldRender(livingentity, camera, camX, camY, camZ)) {
         return true;
      }

      for (The_Leviathan_Part part : livingentity.leviathanParts) {
         if (camera.isVisible(part.getBoundingBox())) {
            return true;
         }
      }

      Entity weapon = livingentity.getTongue();
      if (weapon != null) {
         Vec3 vec3 = livingentity.position();
         Vec3 vec31 = weapon.position();
         return camera.isVisible(new AABB(vec31.x, vec31.y, vec31.z, vec3.x, vec3.y, vec3.z));
      } else {
         return false;
      }
   }

   public Vec3 getRenderOffset(The_Leviathan_Entity entity, float partialTicks) {
      if (entity.getAnimation() == The_Leviathan_Entity.LEVIATHAN_ABYSS_BLAST && entity.getAnimationTick() <= 66) {
         double d0 = 0.01;
         return new Vec3(this.rnd.nextGaussian() * d0, 0.0, this.rnd.nextGaussian() * d0);
      } else {
         return super.getRenderOffset(entity, partialTicks);
      }
   }

   public void render(The_Leviathan_Entity entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
      if (entity.getAnimation() == The_Leviathan_Entity.LEVIATHAN_TAIL_WHIPS) {
         Vec3 bladePos = RenderUtils.matrixStackFromCitadelModel(entity, entityYaw, ((The_Leviathan_Model)this.model).Tail_Particle);
         entity.setSocketPosArray(0, bladePos);
      }

      double x = Mth.lerp(partialTicks, entity.xOld, entity.getX());
      double y = Mth.lerp(partialTicks, entity.yOld, entity.getY());
      double z = Mth.lerp(partialTicks, entity.zOld, entity.getZ());
      float yaw = entity.yBodyRotO + (entity.yBodyRot - entity.yBodyRotO) * partialTicks;
      Entity weapon = entity.getTongue();
      if (weapon != null && entity.isAlive() && weapon.isAlive()) {
         matrixStackIn.pushPose();
         matrixStackIn.translate(-x, -y, -z);
         Vec3 headModelPos = ((The_Leviathan_Model)this.getModel()).translateToTongue(new Vec3(0.0, 0.0, 0.0), yaw).scale(0.2);
         Vec3 fromVec = entity.getTonguePosition().add(headModelPos);
         Vec3 toVec = weapon.getPosition(partialTicks);
         int segmentCount = 0;
         Vec3 currentNeckButt = fromVec;
         VertexConsumer neckConsumer = bufferIn.getBuffer(RenderType.entityCutoutNoCull(LEVIATHAN_TEXTURES));

         for (double remainingDistance = toVec.distanceTo(fromVec); segmentCount < 128 && remainingDistance > 0.0; segmentCount++) {
            remainingDistance = Math.min(fromVec.distanceTo(toVec), 0.5);
            Vec3 linearVec = toVec.subtract(currentNeckButt);
            Vec3 powVec = new Vec3(this.modifyVecAngle(linearVec.x), this.modifyVecAngle(linearVec.y), this.modifyVecAngle(linearVec.z));
            Vec3 smoothedVec = powVec;
            Vec3 next = smoothedVec.normalize().scale(remainingDistance).add(currentNeckButt);
            int neckLight = this.getLightColor(entity, toVec.add(currentNeckButt).add(x, y, z));
            renderNeckCube(currentNeckButt, next, matrixStackIn, neckConsumer, neckLight, OverlayTexture.NO_OVERLAY, 0.0F);
            currentNeckButt = next;
         }

         VertexConsumer clawConsumer = bufferIn.getBuffer(RenderType.entityCutoutNoCull(LEVIATHAN_TEXTURES));
         matrixStackIn.pushPose();
         matrixStackIn.translate(toVec.x, toVec.y, toVec.z);
         matrixStackIn.translate(0.0F, -0.5F, 0.0F);
         float rotY = (float)(Mth.atan2(toVec.x, toVec.z) * 180.0F / (float)Math.PI);
         float rotX = (float)(-(Mth.atan2(toVec.y, toVec.horizontalDistance()) * 180.0F / (float)Math.PI));
         TONGUE_END_MODEL.setAttributes(rotX, rotY);
         TONGUE_END_MODEL.renderToBuffer(matrixStackIn, clawConsumer, this.getLightColor(entity, toVec.add(x, y, z)), OverlayTexture.NO_OVERLAY);
         matrixStackIn.popPose();
         matrixStackIn.popPose();
      }
   }

   private double modifyVecAngle(double dimension) {
      float abs = (float)Math.abs(dimension);
      return Math.signum(dimension) * Mth.clamp(Math.pow(abs, 0.7), 0.005 * abs, abs);
   }

   public static void renderNeckCube(
      Vec3 from, Vec3 to, PoseStack matrixStackIn, VertexConsumer buffer, int packedLightIn, int overlayCoords, float additionalYaw
   ) {
      Vec3 sub = from.subtract(to);
      double d = sub.horizontalDistance();
      float rotY = (float)(Mth.atan2(sub.x, sub.z) * 180.0F / (float)Math.PI);
      float rotX = (float)(-(Mth.atan2(sub.y, d) * 180.0F / (float)Math.PI)) - 90.0F;
      matrixStackIn.pushPose();
      matrixStackIn.translate(from.x, from.y, from.z);
      matrixStackIn.translate(0.0F, -0.5F, 0.0F);
      TONGUE_MODEL.setAttributes((float)sub.length(), rotX, rotY, additionalYaw);
      TONGUE_MODEL.renderToBuffer(matrixStackIn, buffer, packedLightIn, overlayCoords);
      matrixStackIn.popPose();
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

   protected void scale(The_Leviathan_Entity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
      matrixStackIn.scale(1.75F, 1.75F, 1.75F);
   }

   protected float getFlipDegrees(The_Leviathan_Entity entity) {
      return 0.0F;
   }
}
