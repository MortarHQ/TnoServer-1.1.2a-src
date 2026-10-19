package org.crypticdev.stextras.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import io.github.manasmods.tensura.util.client.XrayHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.crypticdev.stextras.support.WispRadarHelper;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;

public final class WispRadarRenderer {
   private static final int HORIZONTAL_RADIUS = 24;
   private static final int VERTICAL_RADIUS = 16;
   private static final int MAX_OUTLINES = 3500;
   private static final int MAX_ENTITY_OUTLINES = 96;
   private static final int RESCAN_INTERVAL_TICKS = 8;
   private static final int RESCAN_MOVE_THRESHOLD = 2;
   private static final double OUTLINE_EXPANSION = 0.0025;
   private static final double ENTITY_OUTLINE_EXPANSION = 0.05;
   private static final List<WispRadarRenderer.CachedOutline> BLOCK_CACHE = new ArrayList<>(2048);
   private static final List<WispRadarRenderer.CachedOutline> ENTITY_CACHE = new ArrayList<>(128);
   private static BlockPos lastScanOrigin = BlockPos.ZERO;
   private static ResourceKey<Level> lastScanDimension;
   private static int lastScanTick = Integer.MIN_VALUE;
   private static final ByteBufferBuilder RADAR_BUFFER = new ByteBufferBuilder(2048);
   private static final BufferSource RADAR_SOURCE = MultiBufferSource.immediate(RADAR_BUFFER);

   private WispRadarRenderer() {
   }

   public static void renderRadar(Camera camera) {
      Minecraft mc = Minecraft.getInstance();
      Player player = mc.player;
      Level level = mc.level;
      if (player != null && level != null && WispRadarHelper.isRadarActive(player)) {
         Vec3 scanOrigin = player.getEyePosition();
         BlockPos eyePos = BlockPos.containing(scanOrigin);
         updateCacheIfNeeded(player, level, eyePos);
         if (!BLOCK_CACHE.isEmpty() || !ENTITY_CACHE.isEmpty()) {
            PoseStack poseStack = new PoseStack();
            if (camera.isDetached()) {
               RenderSystem.setShader(GameRenderer::getPositionColorShader);
               RenderSystem.disableDepthTest();
               RenderSystem.depthMask(false);
               RenderSystem.enableBlend();
               RenderSystem.defaultBlendFunc();
               Matrix4fStack modelViewStack = RenderSystem.getModelViewStack();
               modelViewStack.pushMatrix();
               modelViewStack.identity();
               RenderSystem.applyModelViewMatrix();
               PoseStack blackoutStack = new PoseStack();
               Matrix4f matrix4f = blackoutStack.last().pose();
               BufferBuilder bufferBuilder = Tesselator.getInstance().begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
               bufferBuilder.addVertex(matrix4f, -10.0F, -10.0F, -1.0F).setColor(0.0F, 0.0F, 0.0F, 1.0F);
               bufferBuilder.addVertex(matrix4f, 10.0F, -10.0F, -1.0F).setColor(0.0F, 0.0F, 0.0F, 1.0F);
               bufferBuilder.addVertex(matrix4f, 10.0F, 10.0F, -1.0F).setColor(0.0F, 0.0F, 0.0F, 1.0F);
               bufferBuilder.addVertex(matrix4f, -10.0F, 10.0F, -1.0F).setColor(0.0F, 0.0F, 0.0F, 1.0F);
               BufferUploader.drawWithShader(bufferBuilder.buildOrThrow());
               modelViewStack.popMatrix();
               RenderSystem.applyModelViewMatrix();
            }

            VertexConsumer buffer = RADAR_SOURCE.getBuffer(XrayHelper.XRAY_LINES);
            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            poseStack.pushPose();
            poseStack.translate(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z);
            renderCachedOutlines(poseStack, buffer, BLOCK_CACHE);
            renderCachedOutlines(poseStack, buffer, ENTITY_CACHE);
            poseStack.popPose();
            RADAR_SOURCE.endBatch(XrayHelper.XRAY_LINES);
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
         }
      } else {
         BLOCK_CACHE.clear();
         ENTITY_CACHE.clear();
      }
   }

   private static void renderCachedOutlines(PoseStack poseStack, VertexConsumer buffer, List<WispRadarRenderer.CachedOutline> cache) {
      for (WispRadarRenderer.CachedOutline outline : cache) {
         LevelRenderer.renderLineBox(
            poseStack,
            buffer,
            outline.bounds.minX,
            outline.bounds.minY,
            outline.bounds.minZ,
            outline.bounds.maxX,
            outline.bounds.maxY,
            outline.bounds.maxZ,
            WispRadarHelper.red(outline.color),
            WispRadarHelper.green(outline.color),
            WispRadarHelper.blue(outline.color),
            1.0F
         );
      }
   }

   private static void updateCacheIfNeeded(Player player, Level level, BlockPos eyePos) {
      boolean tickDue = player.tickCount - lastScanTick >= 8;
      boolean moved = eyePos.distManhattan(lastScanOrigin) >= 2;
      boolean dimChanged = !Objects.equals(lastScanDimension, level.dimension());
      if (tickDue || moved || dimChanged) {
         rescan(level, player, eyePos);
         lastScanOrigin = eyePos.immutable();
         lastScanDimension = level.dimension();
         lastScanTick = player.tickCount;
      }
   }

   private static void rescan(Level level, Player player, BlockPos eyePos) {
      BLOCK_CACHE.clear();
      ENTITY_CACHE.clear();
      List<WispRadarRenderer.OutlineCandidate> blockCandidates = new ArrayList<>();
      int minX = eyePos.getX() - 24;
      int maxX = eyePos.getX() + 24;
      int minY = Math.max(level.getMinBuildHeight(), eyePos.getY() - 16);
      int maxY = Math.min(level.getMaxBuildHeight() - 1, eyePos.getY() + 16);
      int minZ = eyePos.getZ() - 24;
      int maxZ = eyePos.getZ() + 24;
      long hRadSq = 576L;
      long vRadSq = 256L;
      long maxDistSq = hRadSq * vRadSq;
      MutableBlockPos mutable = new MutableBlockPos();
      int minChunkX = SectionPos.blockToSectionCoord(minX);
      int maxChunkX = SectionPos.blockToSectionCoord(maxX);
      int minChunkZ = SectionPos.blockToSectionCoord(minZ);
      int maxChunkZ = SectionPos.blockToSectionCoord(maxZ);

      for (int cX = minChunkX; cX <= maxChunkX; cX++) {
         for (int cZ = minChunkZ; cZ <= maxChunkZ; cZ++) {
            LevelChunk chunk = level.getChunk(cX, cZ);
            if (!chunk.isEmpty()) {
               int startX = Math.max(minX, cX << 4);
               int endX = Math.min(maxX, (cX << 4) + 15);
               int startZ = Math.max(minZ, cZ << 4);
               int endZ = Math.min(maxZ, (cZ << 4) + 15);

               for (int x = startX; x <= endX; x++) {
                  long dx = x - eyePos.getX();
                  long dxSq = dx * dx;

                  for (int z = startZ; z <= endZ; z++) {
                     long dz = z - eyePos.getZ();
                     long dzSq = dz * dz;
                     long hDistSq = dxSq + dzSq;
                     if (hDistSq <= hRadSq) {
                        for (int y = minY; y <= maxY; y++) {
                           long dy = y - eyePos.getY();
                           long vDistSq = dy * dy;
                           if (hDistSq * vRadSq + vDistSq * hRadSq <= maxDistSq) {
                              mutable.set(x, y, z);
                              BlockState state = chunk.getBlockState(mutable);
                              if (WispRadarHelper.shouldOutlineBlock(level, mutable, state)) {
                                 blockCandidates.add(
                                    new WispRadarRenderer.OutlineCandidate(
                                       createBlockBounds(mutable), WispRadarHelper.getBlockOutlineColor(level, mutable, state), hDistSq + vDistSq
                                    )
                                 );
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      blockCandidates.sort((l, r) -> Double.compare(l.distanceSq, r.distanceSq));
      int limit = Math.min(3500, blockCandidates.size());

      for (int i = 0; i < limit; i++) {
         BLOCK_CACHE.add(new WispRadarRenderer.CachedOutline(blockCandidates.get(i).bounds, blockCandidates.get(i).color));
      }

      if (level instanceof ClientLevel clientLevel) {
         cacheEntities(clientLevel, player);
      }
   }

   private static void cacheEntities(ClientLevel level, Player player) {
      AABB searchBounds = player.getBoundingBox().inflate(24.0, 16.0, 24.0);
      List<WispRadarRenderer.OutlineCandidate> entityCandidates = new ArrayList<>();

      for (Entity entity : level.getEntities(player, searchBounds, WispRadarRenderer::shouldOutlineEntity)) {
         int color = WispRadarHelper.getBlockOutlineColor(level, entity.blockPosition(), level.getBlockState(entity.blockPosition()));
         entityCandidates.add(
            new WispRadarRenderer.OutlineCandidate(entity.getBoundingBox().inflate(0.05), color, entity.position().distanceToSqr(player.position()))
         );
      }

      entityCandidates.sort((l, r) -> Double.compare(l.distanceSq, r.distanceSq));
      int limit = Math.min(96, entityCandidates.size());

      for (int i = 0; i < limit; i++) {
         ENTITY_CACHE.add(new WispRadarRenderer.CachedOutline(entityCandidates.get(i).bounds, entityCandidates.get(i).color));
      }
   }

   private static boolean shouldOutlineEntity(Entity e) {
      return e.isAlive() && !e.isRemoved() && !e.isSpectator();
   }

   private static AABB createBlockBounds(BlockPos pos) {
      return new AABB(
         pos.getX() - 0.0025, pos.getY() - 0.0025, pos.getZ() - 0.0025, pos.getX() + 1.0 + 0.0025, pos.getY() + 1.0 + 0.0025, pos.getZ() + 1.0 + 0.0025
      );
   }

   private static final class CachedOutline {
      private final AABB bounds;
      private final int color;

      private CachedOutline(AABB bounds, int color) {
         this.bounds = bounds;
         this.color = color;
      }
   }

   private static final class OutlineCandidate {
      private final AABB bounds;
      private final int color;
      private final double distanceSq;

      private OutlineCandidate(AABB bounds, int color, double distanceSq) {
         this.bounds = bounds;
         this.color = color;
         this.distanceSq = distanceSq;
      }
   }
}
