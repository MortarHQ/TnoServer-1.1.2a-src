package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.entity.Ignis_Model;
import com.github.L_Ender.cataclysm.client.render.CMRenderTypes;
import com.github.L_Ender.cataclysm.client.render.RenderUtils;
import com.github.L_Ender.cataclysm.client.render.layer.Ignis_Armor_Crack_Layer;
import com.github.L_Ender.cataclysm.client.render.layer.Ignis_Shield_Layer;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ignis_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Ignis_Renderer extends MobRenderer<Ignis_Entity, Ignis_Model> {
   private static final ResourceLocation[] TEXTURE_PROGRESS = new ResourceLocation[8];
   private static final ResourceLocation[] TEXTURE_SOUL_PROGRESS = new ResourceLocation[8];

   public Ignis_Renderer(Context renderManagerIn) {
      super(renderManagerIn, new Ignis_Model(), 1.0F);
      this.addLayer(new Ignis_Armor_Crack_Layer(this));
      this.addLayer(new Ignis_Shield_Layer(this));

      for (int i = 0; i < 8; i++) {
         TEXTURE_PROGRESS[i] = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/ignis/ignis_idle_" + i + ".png");
         TEXTURE_SOUL_PROGRESS[i] = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/ignis/ignis_soul_idle_" + i + ".png");
      }
   }

   public ResourceLocation getTextureLocation(Ignis_Entity entity) {
      return this.getGrowingTexture(entity, (int)(entity.tickCount * 0.5F % 8.0F));
   }

   public ResourceLocation getGrowingTexture(Ignis_Entity entity, int age) {
      return entity.getBossPhase() > 0 ? TEXTURE_SOUL_PROGRESS[Mth.clamp(age, 0, 7)] : TEXTURE_PROGRESS[Mth.clamp(age, 0, 7)];
   }

   public void render(Ignis_Entity entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
      if (entity.getAnimation() == Ignis_Entity.HORIZONTAL_SWING_ATTACK
         || entity.getAnimation() == Ignis_Entity.SWING_ATTACK
         || entity.getAnimation() == Ignis_Entity.HORIZONTAL_SWING_ATTACK_SOUL
         || entity.getAnimation() == Ignis_Entity.SWING_ATTACK_SOUL
         || entity.getAnimation() == Ignis_Entity.SWING_ATTACK_BERSERK
         || entity.getAnimation() == Ignis_Entity.REINFORCED_SMASH_IN_AIR
         || entity.getAnimation() == Ignis_Entity.REINFORCED_SMASH_IN_AIR_SOUL
         || entity.getAnimation() == Ignis_Entity.PHASE_3
         || entity.getAnimation() == Ignis_Entity.SPIN_ATTACK
         || entity.getAnimation() == Ignis_Entity.ULTIMATE_ATTACK
         || entity.getAnimation() == Ignis_Entity.STRIKE
         || entity.getAnimation() == Ignis_Entity.COMBO1
         || entity.getAnimation() == Ignis_Entity.COMBO2
         || entity.getAnimation() == Ignis_Entity.SHIELD_BREAK_STRIKE
         || entity.getAnimation() == Ignis_Entity.HORIZONTAL_SMALL_SWING_ATTACK
         || entity.getAnimation() == Ignis_Entity.HORIZONTAL_SMALL_SWING_ALT_ATTACK2
         || entity.getAnimation() == Ignis_Entity.SWING_UPPERSLASH) {
         Vec3 bladePos = RenderUtils.matrixStackFromCitadelModel(entity, entityYaw, ((Ignis_Model)this.model).blade2);
         entity.setSocketPosArray(0, bladePos);
      }

      matrixStackIn.pushPose();
      Vec3 endBeam = entity.ClientTargetPosition(partialTicks);
      Vec3 startBeam = this.getPosition(entity, 0.03, partialTicks);
      if (endBeam != null) {
         float beamVecX = (float)(endBeam.x - startBeam.x);
         float beamVecZ = (float)(endBeam.z - startBeam.z);
         this.renderBeams(entity, beamVecX, 0.0F, beamVecZ, partialTicks, matrixStackIn, bufferIn, packedLightIn);
      }

      matrixStackIn.popPose();
   }

   private Vec3 getPosition(LivingEntity livingEntity, double yOffset, float partialTick) {
      double d0 = Mth.lerp(partialTick, livingEntity.xOld, livingEntity.getX());
      double d1 = Mth.lerp(partialTick, livingEntity.yOld, livingEntity.getY()) + yOffset;
      double d2 = Mth.lerp(partialTick, livingEntity.zOld, livingEntity.getZ());
      return new Vec3(d0, d1, d2);
   }

   private void renderBeams(
      Ignis_Entity entity, float x, float y, float z, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight
   ) {
      float f = Mth.sqrt(x * x + z * z);
      float f1 = Mth.sqrt(x * x + y * y + z * z);
      if (!(f1 < 0.01F)) {
         poseStack.pushPose();
         poseStack.translate(0.0F, 0.15F, 0.0F);
         poseStack.mulPose(Axis.YP.rotation((float)(-Math.atan2(z, x)) - (float) (Math.PI / 2)));
         poseStack.mulPose(Axis.XP.rotation((float)(-Math.atan2(f, y)) - (float) (Math.PI / 2)));
         VertexConsumer vertexconsumer = bufferSource.getBuffer(CMRenderTypes.CMLightning());
         Pose posestack$pose = poseStack.last();
         int alpha = 255;
         float beamLength = f1;
         float beamWidth = 0.7F;
         float widthHalf = beamWidth / 2.0F;
         float ax = -widthHalf;
         float ay = 0.0F;
         float az = 0.0F;
         float bx = widthHalf;
         float by = 0.0F;
         float bz = 0.0F;
         float cx = -widthHalf;
         float cy = 0.0F;
         float cz = beamLength;
         float dx = widthHalf;
         float dy = 0.0F;
         float dz = beamLength;
         int r = entity.getBossPhase() > 0 ? 2 : 255;
         int g = entity.getBossPhase() > 0 ? 199 : 215;
         int b = entity.getBossPhase() > 0 ? 203 : 63;
         vertexconsumer.addVertex(posestack$pose, ax, ay, az).setColor(r, g, b, alpha).setLight(packedLight);
         vertexconsumer.addVertex(posestack$pose, bx, by, bz).setColor(r, g, b, alpha).setLight(packedLight);
         vertexconsumer.addVertex(posestack$pose, dx, dy, dz).setColor(0, 0, 0, alpha).setLight(packedLight);
         vertexconsumer.addVertex(posestack$pose, cx, cy, cz).setColor(0, 0, 0, alpha).setLight(packedLight);
         poseStack.popPose();
      }
   }

   protected int getBlockLightLevel(Ignis_Entity entityIn, BlockPos pos) {
      return 15;
   }

   protected float getFlipDegrees(Ignis_Entity entity) {
      return 0.0F;
   }
}
