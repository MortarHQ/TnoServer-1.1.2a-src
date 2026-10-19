package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.render.etc.LightningBoltData;
import com.github.L_Ender.cataclysm.client.render.etc.LightningRender;
import com.github.L_Ender.cataclysm.entity.effect.Bolt_strike_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector4f;

@OnlyIn(Dist.CLIENT)
public class Boltstrike_Renderer extends EntityRenderer<Bolt_strike_Entity> {
   private Map<UUID, LightningRender> lightningRenderMap = new HashMap<>();
   private static final int MAX_HEIGHT = 15;
   private static final double START_MIN_RADIUS = 0.7;
   private static final double START_MAX_RADIUS = 1.2;
   private static final double END_MIN_RADIUS = 0.1;
   private static final double END_MAX_RADIUS = 0.5;

   public Boltstrike_Renderer(Context p_174286_) {
      super(p_174286_);
   }

   public void render(Bolt_strike_Entity entity, float p_115267_, float partialTicks, PoseStack poseStack, MultiBufferSource p_115270_, int p_115271_) {
      double x = Mth.lerp(partialTicks, entity.xOld, entity.getX());
      double y = Mth.lerp(partialTicks, entity.yOld, entity.getY());
      double z = Mth.lerp(partialTicks, entity.zOld, entity.getZ());
      int r = entity.getR();
      int g = entity.getG();
      int b = entity.getB();
      float f = entity.getAnimationProgress(partialTicks);
      float f1 = 0.0F;
      if (f != 0.0F) {
         f1 = f;
         poseStack.pushPose();
         poseStack.translate(-x, -y, -z);
         LightningBoltData.BoltRenderInfo boltData = new LightningBoltData.BoltRenderInfo(
            0.2F, 0.7F, 0.25F, 0.15F, new Vector4f(r / 255.0F, g / 255.0F, b / 255.0F, 0.7F), 0.92F
         );
         LightningBoltData bolt1 = new LightningBoltData(
               boltData, entity.getAnglePosition(partialTicks, 15.0, 1.2, 0.7), entity.getAnglePosition(partialTicks, 0.0, 0.5, 0.1), 4
            )
            .size(f1)
            .lifespan(1)
            .spawn(LightningBoltData.SpawnFunction.NO_DELAY)
            .fade(LightningBoltData.FadeFunction.NONE);
         LightningRender lightningRender = this.getLightingRender(entity.getUUID());
         if (!Minecraft.getInstance().isPaused()) {
            lightningRender.update(entity, bolt1, partialTicks);
         }

         lightningRender.render(partialTicks, poseStack, p_115270_);
         poseStack.popPose();
      }

      if (entity.isRemoved() && this.lightningRenderMap.containsKey(entity.getUUID())) {
         this.lightningRenderMap.remove(entity.getUUID());
      }
   }

   private LightningRender getLightingRender(UUID uuid) {
      if (this.lightningRenderMap.get(uuid) == null) {
         this.lightningRenderMap.put(uuid, new LightningRender());
      }

      return this.lightningRenderMap.get(uuid);
   }

   public ResourceLocation getTextureLocation(Bolt_strike_Entity p_115264_) {
      return TextureAtlas.LOCATION_BLOCKS;
   }
}
