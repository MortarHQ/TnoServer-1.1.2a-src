package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.registry.block.ModBlocks;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public final class PathsPortalStrandRenderer {
   private static final List<PathsPortalStrandRenderer.PortalStrand> STRANDS = new ArrayList<>();
   private static final int SCAN_RADIUS = 32;
   private static final int KEEP_RADIUS = 48;
   private static final int MAX_STRANDS = 16;
   private static int scanCooldown = 0;

   private PathsPortalStrandRenderer() {
   }

   public static void render(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_PARTICLES) {
         Minecraft mc = Minecraft.getInstance();
         ClientLevel level = mc.level;
         if (level != null && mc.player != null) {
            Camera camera = event.getCamera();
            Vec3 camPos = camera.getPosition();
            float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(false);
            float time = (float)level.getGameTime() + partialTick;
            tickStrands(level, camPos);
            if (!STRANDS.isEmpty()) {
               PoseStack poseStack = event.getPoseStack();
               RenderSystem.enableBlend();
               RenderSystem.disableCull();
               RenderSystem.depthMask(false);
               RenderSystem.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
               RenderSystem.setShader(GameRenderer::getPositionColorShader);

               for (PathsPortalStrandRenderer.PortalStrand strand : STRANDS) {
                  float alpha = strand.alpha;
                  if (!(alpha <= 0.01F)) {
                     double x = strand.pos.getX() + 0.5;
                     double y = strand.pos.getY() + 0.05;
                     double z = strand.pos.getZ() + 0.5;
                     poseStack.pushPose();
                     poseStack.translate(x - camPos.x, y - camPos.y, z - camPos.z);
                     float yaw = time * 1.15F + strand.seed * 360.0F;
                     poseStack.mulPose(new Quaternionf().rotateY((float)Math.toRadians(yaw)));
                     renderSingleStrand(poseStack, time, strand.seed, alpha);
                     poseStack.mulPose(new Quaternionf().rotateY((float)Math.toRadians(90.0)));
                     renderSingleStrand(poseStack, time + 11.0F, strand.seed + 0.37F, alpha * 0.72F);
                     poseStack.popPose();
                  }
               }

               RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
               RenderSystem.depthMask(true);
               RenderSystem.enableCull();
               RenderSystem.disableBlend();
            }
         } else {
            STRANDS.clear();
         }
      }
   }

   private static void tickStrands(ClientLevel level, Vec3 camPos) {
      Iterator<PathsPortalStrandRenderer.PortalStrand> iterator = STRANDS.iterator();

      while (iterator.hasNext()) {
         PathsPortalStrandRenderer.PortalStrand strand = iterator.next();
         double dx = strand.pos.getX() + 0.5 - camPos.x;
         double dy = strand.pos.getY() + 0.5 - camPos.y;
         double dz = strand.pos.getZ() + 0.5 - camPos.z;
         double distSq = dx * dx + dy * dy + dz * dz;
         boolean stillPortal = level.getBlockState(strand.pos).is((Block)ModBlocks.PATHS_PORTAL.get());
         boolean closeEnough = distSq <= 2304.0;
         float target = stillPortal && closeEnough ? 1.0F : 0.0F;
         strand.alpha = Mth.lerp(0.12F, strand.alpha, target);
         if (strand.alpha < 0.02F && (!stillPortal || !closeEnough)) {
            iterator.remove();
         }
      }

      if (scanCooldown-- <= 0) {
         scanCooldown = 20;
         if (STRANDS.size() < 16) {
            BlockPos center = BlockPos.containing(camPos);

            for (BlockPos scanPos : BlockPos.betweenClosed(center.offset(-32, -16, -32), center.offset(32, 16, 32))) {
               if (STRANDS.size() >= 16) {
                  return;
               }

               BlockState state = level.getBlockState(scanPos);
               if (state.is((Block)ModBlocks.PATHS_PORTAL.get()) && !hasStrand(scanPos)) {
                  STRANDS.add(new PathsPortalStrandRenderer.PortalStrand(scanPos.immutable(), hash(scanPos)));
               }
            }
         }
      }
   }

   private static boolean hasStrand(BlockPos pos) {
      for (PathsPortalStrandRenderer.PortalStrand strand : STRANDS) {
         if (strand.pos.equals(pos)) {
            return true;
         }
      }

      return false;
   }

   private static void renderSingleStrand(PoseStack poseStack, float time, float seed, float alpha) {
      Matrix4f mat = poseStack.last().pose();
      BufferBuilder bb = Tesselator.getInstance().begin(Mode.TRIANGLE_STRIP, DefaultVertexFormat.POSITION_COLOR);
      int segments = 42;
      float height = 7.5F;
      float width = 0.34F;
      float pulse = 0.65F + Mth.sin(time * 0.12F + seed * 6.28F) * 0.2F;

      for (int i = 0; i <= segments; i++) {
         float t = (float)i / segments;
         float y = t * height;
         float middle = Mth.sin(t * (float) Math.PI);
         float waveA = Mth.sin(time * 0.08F + seed * 12.0F + t * 11.0F) * 0.28F;
         float waveB = Mth.sin(time * 0.045F + seed * 7.0F + t * 23.0F) * 0.11F;
         float sway = (waveA + waveB) * middle;
         float strandWidth = width * (0.45F + middle * 1.15F) * pulse;
         int aOuter = Mth.clamp((int)(alpha * middle * 80.0F), 0, 255);
         int aInner = Mth.clamp((int)(alpha * middle * 185.0F), 0, 255);
         bb.addVertex(mat, sway - strandWidth, y, 0.0F).setColor(105, 42, 255, aOuter);
         bb.addVertex(mat, sway, y, 0.0F).setColor(210, 185, 255, aInner);
         bb.addVertex(mat, sway + strandWidth, y, 0.0F).setColor(105, 42, 255, aOuter);
      }

      BufferUploader.drawWithShader(bb.buildOrThrow());
   }

   private static float hash(BlockPos pos) {
      long value = pos.asLong();
      value ^= value >>> 33;
      value *= -49064778989728563L;
      value ^= value >>> 33;
      value *= -4265267296055464877L;
      value ^= value >>> 33;
      return (float)(value & 16777215L) / 1.6777215E7F;
   }

   private static final class PortalStrand {
      private final BlockPos pos;
      private final float seed;
      private float alpha;

      private PortalStrand(BlockPos pos, float seed) {
         this.pos = pos;
         this.seed = seed;
         this.alpha = 0.0F;
      }
   }
}
