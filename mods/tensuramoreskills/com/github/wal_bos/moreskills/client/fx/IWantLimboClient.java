package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.limbo.IWantLimboData;
import com.github.wal_bos.moreskills.net.msg.IWantLimboOpenPayload;
import com.github.wal_bos.moreskills.net.msg.IWantLimboRoomPayload;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Axis;
import dev.architectury.networking.NetworkManager;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Matrix4f;

public final class IWantLimboClient {
   private static boolean active;
   private static final List<IWantLimboRoomPayload.CardView> CARDS = new ArrayList<>();
   private static float time;
   private static int openCooldown;

   private IWantLimboClient() {
   }

   public static void init() {
      NeoForge.EVENT_BUS.addListener(IWantLimboClient::onClientTick);
      NeoForge.EVENT_BUS.addListener(IWantLimboClient::render);
   }

   public static void setRoom(IWantLimboRoomPayload payload) {
      active = payload.active();
      CARDS.clear();
      if (payload.active()) {
         CARDS.addAll(payload.cards());
      }
   }

   private static void onClientTick(Post event) {
      time += 0.055F;
      if (openCooldown > 0) {
         openCooldown--;
      }

      if (active) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.player != null && minecraft.screen == null) {
            int nearby = nearestCard(minecraft.player, 2.45);
            if (nearby >= 0 && openCooldown <= 0) {
               openCooldown = 20;
               NetworkManager.sendToServer(new IWantLimboOpenPayload(nearby));
            } else {
               while (minecraft.options.keyAttack.consumeClick()) {
                  int card = aimedCard(minecraft.player);
                  if (card >= 0 && openCooldown <= 0) {
                     openCooldown = 20;
                     NetworkManager.sendToServer(new IWantLimboOpenPayload(card));
                     break;
                  }
               }
            }
         }
      }
   }

   private static int nearestCard(Player player, double range) {
      int best = -1;
      double bestDistance = range * range;
      Vec3 pos = player.position().add(0.0, player.getEyeHeight() * 0.55, 0.0);

      for (IWantLimboRoomPayload.CardView card : CARDS) {
         Vec3 center = new Vec3(card.x(), card.y(), card.z());
         double distance = center.distanceToSqr(pos);
         if (distance < bestDistance) {
            bestDistance = distance;
            best = card.id();
         }
      }

      return best;
   }

   private static int aimedCard(Player player) {
      Vec3 eye = player.getEyePosition();
      Vec3 look = player.getLookAngle().normalize();
      int best = -1;
      double bestScore = 999.0;

      for (IWantLimboRoomPayload.CardView card : CARDS) {
         Vec3 center = new Vec3(card.x(), card.y(), card.z());
         Vec3 to = center.subtract(eye);
         double forward = to.dot(look);
         if (!(forward < 0.35) && !(forward > 10.0)) {
            Vec3 closest = eye.add(look.scale(forward));
            double miss = closest.distanceTo(center);
            double allowed = forward < 4.0 ? 1.38 : 1.0;
            if (miss <= allowed && miss < bestScore) {
               bestScore = miss;
               best = card.id();
            }
         }
      }

      return best;
   }

   private static void render(RenderLevelStageEvent event) {
      if (active && !CARDS.isEmpty() && event.getStage() == Stage.AFTER_TRANSLUCENT_BLOCKS) {
         Camera camera = event.getCamera();
         PoseStack poseStack = event.getPoseStack();
         RenderSystem.setShader(GameRenderer::getPositionColorShader);
         RenderSystem.disableCull();
         RenderSystem.enableBlend();
         RenderSystem.blendFuncSeparate(770, 1, 1, 0);
         RenderSystem.disableDepthTest();

         for (IWantLimboRoomPayload.CardView card : CARDS) {
            renderCard(poseStack, camera, card);
         }

         RenderSystem.defaultBlendFunc();
         RenderSystem.enableDepthTest();
         RenderSystem.disableBlend();
         RenderSystem.enableCull();
      }
   }

   private static void renderCard(PoseStack poseStack, Camera camera, IWantLimboRoomPayload.CardView card) {
      Vec3 cam = camera.getPosition();
      Minecraft minecraft = Minecraft.getInstance();
      Player player = minecraft.player;
      float focus = 0.0F;
      if (player != null) {
         double distance = new Vec3(card.x(), card.y(), card.z()).distanceTo(player.getEyePosition());
         focus = Mth.clamp((float)(1.0 - (distance - 1.75) / 2.75), 0.0F, 1.0F);
      }

      float phase = time + card.id() * 0.91F;
      float drift = Mth.sin(phase * 0.62F) * 0.045F + focus * 0.035F;
      float roll = Mth.sin(phase * 0.38F) * 1.35F;
      float yawPulse = Mth.sin(phase * 0.31F) * 2.4F;
      poseStack.pushPose();
      poseStack.translate(card.x() - cam.x, card.y() - cam.y + drift, card.z() - cam.z);
      poseStack.mulPose(camera.rotation());
      poseStack.mulPose(Axis.YP.rotationDegrees(180.0F + yawPulse));
      poseStack.mulPose(Axis.ZP.rotationDegrees(roll));
      Matrix4f matrix = poseStack.last().pose();
      int color = color(card.rarity());
      float r = (color >> 16 & 0xFF) / 255.0F;
      float g = (color >> 8 & 0xFF) / 255.0F;
      float b = (color & 0xFF) / 255.0F;
      float pulse = 0.82F + 0.18F * Mth.sin(time * 1.2F + card.id() * 0.7F);
      float w = 0.82F + focus * 0.07F;
      float h = 1.22F + focus * 0.1F;
      BufferBuilder buffer = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      addAura(buffer, matrix, w, h, r, g, b, 0.18F + focus * 0.16F, pulse);
      addQuad(buffer, matrix, -w, -h, 0.0F, w, h, 0.0F, 0.01F, 0.009F, 0.017F, 0.96F);
      addQuad(buffer, matrix, -w + 0.045F, -h + 0.045F, -0.012F, w - 0.045F, h - 0.045F, -0.012F, 0.03F, 0.022F, 0.042F, 0.95F);
      addQuad(buffer, matrix, -w + 0.055F, h - 0.28F, -0.025F, w - 0.055F, h - 0.055F, -0.025F, r, g, b, 0.97F);
      addQuad(buffer, matrix, -w + 0.15F, -h + 0.23F, -0.03F, w - 0.15F, -h + 0.26F, -0.03F, r, g, b, 0.34F);
      addQuad(buffer, matrix, -w + 0.23F, -h + 0.47F, -0.03F, w - 0.23F, -h + 0.5F, -0.03F, r, g, b, 0.25F);
      addQuad(buffer, matrix, -w + 0.31F, -h + 0.71F, -0.03F, w - 0.31F, -h + 0.74F, -0.03F, r, g, b, 0.18F);

      for (int i = 0; i < 16; i++) {
         float a = phase * 0.34F + i * 0.392F;
         float radiusX = w + 0.22F + Mth.sin(a * 0.9F) * 0.035F;
         float radiusY = h + 0.2F + Mth.cos(a * 0.7F) * 0.035F;
         float x = Mth.cos(a) * radiusX;
         float y = Mth.sin(a * 1.08F) * radiusY;
         float size = 0.025F + focus * 0.018F;
         addQuad(buffer, matrix, x - size, y - size, -0.06F, x + size, y + size, -0.06F, r, g, b, 0.18F + pulse * 0.08F);
      }

      safeDraw(buffer);
      poseStack.popPose();
   }

   private static void addAura(BufferBuilder buffer, Matrix4f matrix, float w, float h, float r, float g, float b, float a, float pulse) {
      addQuad(buffer, matrix, -w - 0.3F, -h - 0.3F, 0.05F, w + 0.3F, h + 0.3F, 0.05F, r, g, b, a * pulse);
      addQuad(buffer, matrix, -w - 0.12F, -h - 0.12F, 0.035F, w + 0.12F, h + 0.12F, 0.035F, r, g, b, a * 0.52F);
   }

   private static void addQuad(
      BufferBuilder buffer, Matrix4f matrix, float x1, float y1, float z1, float x2, float y2, float z2, float r, float g, float b, float a
   ) {
      buffer.addVertex(matrix, x1, y1, z1).setColor(r, g, b, a);
      buffer.addVertex(matrix, x1, y2, z2).setColor(r, g, b, a);
      buffer.addVertex(matrix, x2, y2, z2).setColor(r, g, b, a);
      buffer.addVertex(matrix, x2, y1, z1).setColor(r, g, b, a);
   }

   private static int color(IWantLimboData.Rarity rarity) {
      return switch (rarity) {
         case BRONZE -> 12876602;
         case SILVER -> 13293544;
         case GOLD -> 16766570;
      };
   }

   private static void safeDraw(BufferBuilder buffer) {
      try {
         BufferUploader.drawWithShader(buffer.buildOrThrow());
      } catch (IllegalStateException var2) {
      }
   }
}
