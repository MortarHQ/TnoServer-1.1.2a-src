package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.world.entity.FeldwayAwakeningEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Quaternionf;

public final class FeldwayAwakeningBlockStorm {
   private static final Map<UUID, FeldwayAwakeningBlockStorm.StormVisual> STORMS = new HashMap<>();
   private static boolean initialized = false;

   private FeldwayAwakeningBlockStorm() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(FeldwayAwakeningBlockStorm::render);
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_TRANSLUCENT_BLOCKS) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null && minecraft.player != null) {
            float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(false);
            double gameTime = (float)minecraft.level.getGameTime() + partialTick;

            for (Entity entity : minecraft.level.entitiesForRendering()) {
               if (entity instanceof FeldwayAwakeningEntity awakening && awakening.isAlive()) {
                  UUID id = awakening.getUUID();
                  FeldwayAwakeningBlockStorm.StormVisual visual = STORMS.computeIfAbsent(
                     id, uuid -> new FeldwayAwakeningBlockStorm.StormVisual(uuid, awakening.position(), gameTime)
                  );
                  visual.updateFromEntity(awakening);
               }
            }

            PoseStack poseStack = event.getPoseStack();
            BufferSource buffer = minecraft.renderBuffers().bufferSource();
            Vec3 camera = event.getCamera().getPosition();
            Iterator<Entry<UUID, FeldwayAwakeningBlockStorm.StormVisual>> iterator = STORMS.entrySet().iterator();

            while (iterator.hasNext()) {
               FeldwayAwakeningBlockStorm.StormVisual visual = iterator.next().getValue();
               if (visual.isDead(gameTime)) {
                  iterator.remove();
               } else {
                  visual.render(minecraft, poseStack, buffer, camera, gameTime);
               }
            }

            buffer.endBatch();
         } else {
            STORMS.clear();
         }
      }
   }

   private static final class StormVisual {
      private static final int GROUND_RISING_BLOCKS = 520;
      private static final int HEAVY_CHUNKS = 92;
      private static final int ORBIT_SHARDS = 150;
      private static final int THRONE_BLOCKS = 130;
      private static final int SKY_FRAGMENTS = 120;
      private static final int FINAL_EXPLOSION_BLOCKS = 520;
      private static final int DEATH_TICKS = 120;
      private static final double DEAD_ZONE_RADIUS = 42.0;
      private static final double GROUND_MIN_RADIUS = 62.0;
      private static final double GROUND_MAX_RADIUS = 210.0;
      private static final double CHUNK_MIN_RADIUS = 78.0;
      private static final double CHUNK_MAX_RADIUS = 240.0;
      private final UUID owner;
      private final Vec3 origin;
      private final double createdGameTime;
      private double anchorGroundY;
      private boolean resolvedAnchorGround;
      private final BlockState[] groundStates = new BlockState[520];
      private final float[] groundSeeds = new float[520];
      private final float[] groundSizes = new float[520];
      private final float[] groundSpawn = new float[520];
      private final float[] groundDistance = new float[520];
      private final BlockState[] chunkStates = new BlockState[92];
      private final float[] chunkSeeds = new float[92];
      private final float[] chunkSizes = new float[92];
      private final float[] chunkSpawn = new float[92];
      private final float[] chunkDistance = new float[92];
      private final BlockState[] shardStates = new BlockState[150];
      private final float[] shardSeeds = new float[150];
      private final float[] shardSizes = new float[150];
      private final BlockState[] throneStates = new BlockState[130];
      private final float[] throneSeeds = new float[130];
      private final BlockState[] skyStates = new BlockState[120];
      private final float[] skySeeds = new float[120];
      private final float[] skySizes = new float[120];
      private final BlockState[] explosionStates = new BlockState[520];
      private final float[] explosionSeeds = new float[520];
      private final float[] explosionSizes = new float[520];
      private int age;
      private int duration = 520;
      private int unseenTicks = 0;
      private boolean ending = false;
      private double endingStart = -1.0;

      private StormVisual(UUID owner, Vec3 origin, double gameTime) {
         this.owner = owner;
         this.origin = new Vec3(origin.x, origin.y, origin.z);
         this.createdGameTime = gameTime;
         this.anchorGroundY = origin.y;

         for (int i = 0; i < 520; i++) {
            this.groundStates[i] = pickDarkState(i);
            this.groundSeeds[i] = hash(owner, i) * (float) (Math.PI * 2);
            this.groundSizes[i] = 0.35F + hash(owner, i + 1000) * 1.75F;
            this.groundSpawn[i] = hash(owner, i + 2000);
            this.groundDistance[i] = hash(owner, i + 2500);
         }

         for (int i = 0; i < 92; i++) {
            this.chunkStates[i] = pickChunkState(i);
            this.chunkSeeds[i] = hash(owner, i + 3000) * (float) (Math.PI * 2);
            this.chunkSizes[i] = 2.2F + hash(owner, i + 4000) * 5.8F;
            this.chunkSpawn[i] = hash(owner, i + 5000);
            this.chunkDistance[i] = hash(owner, i + 5500);
         }

         for (int i = 0; i < 150; i++) {
            this.shardStates[i] = pickDarkState(i + 6000);
            this.shardSeeds[i] = hash(owner, i + 7000) * (float) (Math.PI * 2);
            this.shardSizes[i] = 0.35F + hash(owner, i + 8000) * 1.55F;
         }

         for (int i = 0; i < 130; i++) {
            this.throneStates[i] = pickThroneState(i);
            this.throneSeeds[i] = hash(owner, i + 9000) * (float) (Math.PI * 2);
         }

         for (int i = 0; i < 120; i++) {
            this.skyStates[i] = pickSkyState(i);
            this.skySeeds[i] = hash(owner, i + 10000) * (float) (Math.PI * 2);
            this.skySizes[i] = 0.9F + hash(owner, i + 11000) * 3.4F;
         }

         for (int i = 0; i < 520; i++) {
            this.explosionStates[i] = i % 2 == 0 ? pickDarkState(i) : pickChunkState(i);
            this.explosionSeeds[i] = hash(owner, i + 12000) * (float) (Math.PI * 2);
            this.explosionSizes[i] = 0.7F + hash(owner, i + 13000) * 4.8F;
         }
      }

      private void updateFromEntity(FeldwayAwakeningEntity entity) {
         this.age = entity.getAwakeningAge();
         this.duration = Math.max(1, entity.getAwakeningDuration());
         this.unseenTicks = 0;
         Minecraft minecraft = Minecraft.getInstance();
         if (!this.resolvedAnchorGround && minecraft.level != null) {
            this.resolvedAnchorGround = true;
            this.anchorGroundY = this.findSurfaceY(minecraft.level, this.origin.x, this.origin.z, this.origin.y + 24.0);
         }

         if (this.age >= this.duration - 5 && !this.ending) {
            this.ending = true;
            this.endingStart = minecraft.level == null ? this.createdGameTime : minecraft.level.getGameTime();
         }
      }

      private boolean isDead(double gameTime) {
         this.unseenTicks++;
         if (!this.ending && this.unseenTicks > 80) {
            this.ending = true;
            this.endingStart = gameTime;
         }

         if (!this.ending && this.age >= this.duration) {
            this.ending = true;
            this.endingStart = gameTime;
         }

         return this.ending && gameTime - this.endingStart > 120.0;
      }

      private void render(Minecraft minecraft, PoseStack poseStack, BufferSource buffer, Vec3 camera, double gameTime) {
         float progress = Mth.clamp((float)this.age / this.duration, 0.0F, 1.0F);
         float build = smooth(Mth.clamp((progress - 0.03F) / 0.97F, 0.0F, 1.0F));
         float mid = smooth(Mth.clamp((progress - 0.28F) / 0.72F, 0.0F, 1.0F));
         float late = smooth(Mth.clamp((progress - 0.62F) / 0.38F, 0.0F, 1.0F));
         float finale = smooth(Mth.clamp((progress - 0.88F) / 0.12F, 0.0F, 1.0F));
         double time = gameTime - this.createdGameTime;
         if (this.ending) {
            this.renderFinalExplosion(minecraft, poseStack, buffer, camera, gameTime);
         } else {
            this.renderFarGroundRisingBlocks(minecraft, poseStack, buffer, camera, time, build, mid, late, finale);
            this.renderFarHeavyGroundChunks(minecraft, poseStack, buffer, camera, time, build, mid, late, finale);
            this.renderCleanOuterOrbitShards(minecraft, poseStack, buffer, camera, time, mid, late, finale);
            this.renderCleanThroneRings(minecraft, poseStack, buffer, camera, time, mid, late, finale);
            this.renderSkyCollapse(minecraft, poseStack, buffer, camera, time, progress, late, finale);
            this.renderCentralBlackPillars(minecraft, poseStack, buffer, camera, time, progress, late, finale);
         }
      }

      private void renderFarGroundRisingBlocks(
         Minecraft minecraft, PoseStack poseStack, BufferSource buffer, Vec3 camera, double time, float build, float mid, float late, float finale
      ) {
         Level level = minecraft.level;
         if (level != null) {
            if (!this.resolvedAnchorGround) {
               this.resolvedAnchorGround = true;
               this.anchorGroundY = this.findSurfaceY(level, this.origin.x, this.origin.z, this.origin.y + 24.0);
            }

            int active = 60 + (int)(build * 520.0F);

            for (int i = 0; i < active && i < 520; i++) {
               float seed = this.groundSeeds[i];
               float spawn = this.groundSpawn[i];
               float appear = smooth(Mth.clamp(build * 1.65F - spawn * 0.6F, 0.0F, 1.0F));
               if (!(appear <= 0.01F)) {
                  double layer = i % 80 / 79.0;
                  double dist = 62.0 + this.groundDistance[i] * 148.0;
                  dist += Math.sin(i * 0.73) * 12.0;
                  double angle = seed + i * 0.31 + time * (0.0018 + mid * 0.004) * (i % 2 == 0 ? 1.0 : -1.0);
                  if (dist < 42.0) {
                     dist = 42.0;
                  }

                  double x = this.origin.x + Math.cos(angle) * dist;
                  double z = this.origin.z + Math.sin(angle) * dist;
                  double surfaceY = this.findSurfaceY(level, x, z, this.anchorGroundY + 36.0);
                  if (Math.abs(surfaceY - this.anchorGroundY) > 40.0) {
                     surfaceY = this.anchorGroundY;
                  }

                  double startY = surfaceY - 1.35;
                  double lift = 8.0 + layer * 64.0 + mid * 28.0;
                  double y = startY + appear * appear * lift;
                  y += Math.sin(time * 0.018 + seed) * (0.12 + mid * 0.75);
                  if (late > 0.0F) {
                     y += late * late * (8.0 + layer * 34.0);
                     dist += late * (14.0 + layer * 24.0);
                     angle += late * 0.45;
                  }

                  if (finale > 0.0F) {
                     y += finale * (18.0 + layer * 44.0);
                     dist += finale * 18.0;
                     angle += finale * 0.65;
                  }

                  x = this.origin.x + Math.cos(angle) * dist;
                  z = this.origin.z + Math.sin(angle) * dist;
                  BlockState state = appear < 0.45F && i % 3 == 0 ? this.sampleGroundState(level, x, z, surfaceY) : this.groundStates[i];
                  float scale = this.groundSizes[i] * (0.18F + appear * 0.82F);
                  scale *= 1.0F + late * 0.22F + finale * 0.34F;
                  float yaw = (float)(angle + time * 0.01);
                  float pitch = (float)(seed + time * 0.009 * (i % 2 == 0 ? 1.0 : -1.0));
                  float roll = (float)(seed * 0.7 + time * 0.01);
                  this.renderBlock(minecraft, poseStack, buffer, camera, state, x, y, z, yaw, pitch, roll, scale);
               }
            }
         }
      }

      private void renderFarHeavyGroundChunks(
         Minecraft minecraft, PoseStack poseStack, BufferSource buffer, Vec3 camera, double time, float build, float mid, float late, float finale
      ) {
         Level level = minecraft.level;
         if (level != null) {
            if (!this.resolvedAnchorGround) {
               this.resolvedAnchorGround = true;
               this.anchorGroundY = this.findSurfaceY(level, this.origin.x, this.origin.z, this.origin.y + 24.0);
            }

            int active = 8 + (int)(build * 92.0F);

            for (int i = 0; i < active && i < 92; i++) {
               float seed = this.chunkSeeds[i];
               float spawn = this.chunkSpawn[i];
               float appear = smooth(Mth.clamp(build * 1.35F - spawn * 0.52F, 0.0F, 1.0F));
               if (!(appear <= 0.01F)) {
                  double dist = 78.0 + this.chunkDistance[i] * 162.0;
                  dist += Math.sin(i * 1.17) * 18.0;
                  double angle = seed + i * 0.43 + time * (0.0012 + mid * 0.003) * (i % 2 == 0 ? 1.0 : -1.0);
                  double x = this.origin.x + Math.cos(angle) * dist;
                  double z = this.origin.z + Math.sin(angle) * dist;
                  double surfaceY = this.findSurfaceY(level, x, z, this.anchorGroundY + 36.0);
                  if (Math.abs(surfaceY - this.anchorGroundY) > 40.0) {
                     surfaceY = this.anchorGroundY;
                  }

                  double buried = 2.4 + hash(this.owner, i + 26000) * 3.6;
                  double startY = surfaceY - buried;
                  double ripHeight = 10.0 + hash(this.owner, i + 15000) * 52.0;
                  double y = startY + appear * appear * ripHeight;
                  if (late > 0.0F) {
                     y += late * late * (18.0 + hash(this.owner, i + 16000) * 52.0);
                     dist += late * 22.0;
                     angle += late * 0.55;
                  }

                  if (finale > 0.0F) {
                     y += finale * (30.0 + hash(this.owner, i + 17000) * 78.0);
                     dist += finale * 28.0;
                     angle += finale * 0.75;
                  }

                  x = this.origin.x + Math.cos(angle) * dist;
                  z = this.origin.z + Math.sin(angle) * dist;
                  float scale = this.chunkSizes[i] * (0.22F + appear * 0.76F);
                  scale *= 1.0F + late * 0.22F;
                  float yaw = (float)(seed + time * 0.003);
                  float pitch = (float)Math.sin(time * 0.005 + seed) * (0.05F + late * 0.2F);
                  float roll = (float)Math.cos(time * 0.004 + seed) * (0.05F + late * 0.2F);
                  this.renderChunkStack(minecraft, poseStack, buffer, camera, x, y, z, yaw, pitch, roll, scale, i);
               }
            }
         }
      }

      private void renderChunkStack(
         Minecraft minecraft,
         PoseStack poseStack,
         BufferSource buffer,
         Vec3 camera,
         double x,
         double y,
         double z,
         float yaw,
         float pitch,
         float roll,
         float scale,
         int index
      ) {
         BlockState top = index % 4 == 0
            ? Blocks.CRYING_OBSIDIAN.defaultBlockState()
            : (
               index % 4 == 1
                  ? Blocks.OBSIDIAN.defaultBlockState()
                  : (index % 4 == 2 ? Blocks.BLACKSTONE.defaultBlockState() : Blocks.TINTED_GLASS.defaultBlockState())
            );
         BlockState mid = index % 3 == 0
            ? Blocks.SMOOTH_BASALT.defaultBlockState()
            : (index % 3 == 1 ? Blocks.DEEPSLATE.defaultBlockState() : Blocks.AMETHYST_BLOCK.defaultBlockState());
         BlockState bottom = index % 2 == 0 ? Blocks.CRYING_OBSIDIAN.defaultBlockState() : Blocks.BLACKSTONE.defaultBlockState();
         this.renderBlock(minecraft, poseStack, buffer, camera, top, x, y + scale * 0.45, z, yaw, pitch, roll, scale);
         this.renderBlock(minecraft, poseStack, buffer, camera, mid, x, y - scale * 0.55, z, yaw + 0.12F, pitch, roll, scale * 0.92F);
         this.renderBlock(minecraft, poseStack, buffer, camera, bottom, x, y - scale * 1.4, z, yaw - 0.1F, pitch, roll, scale * 0.82F);
      }

      private void renderCleanOuterOrbitShards(
         Minecraft minecraft, PoseStack poseStack, BufferSource buffer, Vec3 camera, double time, float mid, float late, float finale
      ) {
         if (!(mid <= 0.01F)) {
            Level level = minecraft.level;
            if (level != null) {
               int active = 25 + (int)(mid * 150.0F);

               for (int i = 0; i < active && i < 150; i++) {
                  float seed = this.shardSeeds[i];
                  double layer = i % 72 / 71.0;
                  double lane = i % 9 / 8.0;
                  float spawn = hash(this.owner, 30000 + i);
                  float rise = smooth(Mth.clamp(mid * 1.8F - spawn * 0.72F, 0.0F, 1.0F));
                  if (!(rise <= 0.01F)) {
                     double radius = 18.0 + lane * (18.0 + mid * 12.0);
                     double angle = seed + i * 0.39 + time * (0.01 + mid * 0.014 + late * 0.03) * (i % 2 == 0 ? 1.0 : -1.0);
                     double targetY = this.anchorGroundY + 7.0 + layer * (22.0 + mid * 44.0);
                     if (late > 0.0F) {
                        targetY += late * late * (6.0 + layer * 22.0);
                        radius += late * 8.0;
                     }

                     if (finale > 0.0F) {
                        radius += finale * 12.0;
                        targetY += finale * 18.0;
                        angle += finale * 0.75;
                     }

                     double x = this.origin.x + Math.cos(angle) * radius;
                     double z = this.origin.z + Math.sin(angle) * radius;
                     double surfaceY = this.findSurfaceY(level, x, z, this.anchorGroundY + 34.0);
                     if (Math.abs(surfaceY - this.anchorGroundY) > 42.0) {
                        surfaceY = this.anchorGroundY;
                     }

                     double rip = Math.sin(rise * Math.PI) * (1.2 + layer * 3.5);
                     double y = Mth.lerp(rise * rise, surfaceY - 1.25, targetY) + rip;
                     float scale = this.shardSizes[i] * (0.1F + rise * 0.9F) * (0.32F + mid * 0.62F + late * 0.14F);
                     float yaw = (float)(angle + time * (0.018 + rise * 0.016));
                     float pitch = (float)(seed + time * (0.014 + rise * 0.012));
                     float roll = (float)(seed * 0.6 + time * (0.014 + rise * 0.01));
                     BlockState state = rise < 0.42F && i % 3 == 0 ? this.sampleGroundState(level, x, z, surfaceY) : this.shardStates[i];
                     this.renderBlock(minecraft, poseStack, buffer, camera, state, x, y, z, yaw, pitch, roll, scale);
                  }
               }
            }
         }
      }

      private void renderCleanThroneRings(
         Minecraft minecraft, PoseStack poseStack, BufferSource buffer, Vec3 camera, double time, float mid, float late, float finale
      ) {
         if (!(mid <= 0.01F)) {
            Level level = minecraft.level;
            if (level != null) {
               int rings = 3;

               for (int ring = 0; ring < rings; ring++) {
                  int count = 18 + ring * 10;
                  double radius = 5.0 + ring * 5.5 + mid * 5.0 + late * 2.0;
                  double ringY = this.anchorGroundY + 2.0 + ring * 3.0 + mid * 9.0 + late * 10.0;
                  double dir = ring % 2 == 0 ? 1.0 : -1.0;

                  for (int i = 0; i < count; i++) {
                     int index = (ring * 41 + i) % 130;
                     float seed = this.throneSeeds[index];
                     float rise = smooth(Mth.clamp(mid * 1.65F - hash(this.owner, 34000 + ring * 100 + i) * 0.62F - ring * 0.08F, 0.0F, 1.0F));
                     if (!(rise <= 0.01F)) {
                        double angle = (float) (Math.PI * 2) * i / count + seed * 0.12 + time * (0.01 + ring * 0.003 + late * 0.02) * dir;
                        double bob = Math.sin(time * 0.032 + i * 0.7 + ring) * (0.12 + mid * 0.4 + late * 0.45);
                        double localRadius = radius;
                        double localY = ringY;
                        if (finale > 0.0F) {
                           localRadius += finale * 4.0;
                           localY += finale * 9.0;
                        }

                        double x = this.origin.x + Math.cos(angle) * localRadius;
                        double z = this.origin.z + Math.sin(angle) * localRadius;
                        double surfaceY = this.findSurfaceY(level, x, z, this.anchorGroundY + 24.0);
                        if (Math.abs(surfaceY - this.anchorGroundY) > 36.0) {
                           surfaceY = this.anchorGroundY;
                        }

                        double y = Mth.lerp(rise * rise, surfaceY - 1.15, localY + bob);
                        y += Math.sin(rise * Math.PI) * (0.6 + ring * 0.35);
                        float scale = (0.48F + mid * 0.76F + ring * 0.08F + finale * 0.42F) * (0.18F + rise * 0.82F);
                        BlockState state = rise < 0.4F && i % 2 == 0 ? this.sampleGroundState(level, x, z, surfaceY) : this.throneStates[index];
                        this.renderBlock(
                           minecraft, poseStack, buffer, camera, state, x, y, z, (float)angle, (float)(time * 0.008 + seed), (float)(-angle), scale
                        );
                     }
                  }
               }
            }
         }
      }

      private void renderSkyCollapse(
         Minecraft minecraft, PoseStack poseStack, BufferSource buffer, Vec3 camera, double time, float progress, float late, float finale
      ) {
         if (!(progress < 0.4F)) {
            for (int i = 0; i < 120; i++) {
               float seed = this.skySeeds[i];
               float appear = smooth(Mth.clamp((progress - 0.4F) / 0.46F - hash(this.owner, i + 18000) * 0.35F, 0.0F, 1.0F));
               if (!(appear <= 0.01F)) {
                  double radius = 70.0 + hash(this.owner, i + 19000) * 150.0;
                  double angle = seed + i * 0.28 + time * 0.002;
                  double y = this.anchorGroundY + 80.0 + hash(this.owner, i + 20000) * 120.0;
                  if (late > 0.0F) {
                     radius *= 1.0 - late * 0.16;
                     y -= late * late * 24.0;
                     angle += late * 0.55;
                  }

                  if (finale > 0.0F) {
                     radius *= 1.0 - finale * 0.1;
                     y += finale * 26.0;
                     angle += finale * 0.7;
                  }

                  double x = this.origin.x + Math.cos(angle) * radius;
                  double z = this.origin.z + Math.sin(angle) * radius;
                  float scale = this.skySizes[i] * (0.26F + appear * 0.72F + finale * 0.3F);
                  float yaw = (float)(angle + time * 0.008);
                  float pitch = (float)(seed + time * 0.01);
                  float roll = (float)(seed * 0.6 + time * 0.008);
                  this.renderBlock(minecraft, poseStack, buffer, camera, this.skyStates[i], x, y, z, yaw, pitch, roll, scale);
               }
            }
         }
      }

      private void renderCentralBlackPillars(
         Minecraft minecraft, PoseStack poseStack, BufferSource buffer, Vec3 camera, double time, float progress, float late, float finale
      ) {
         if (!(progress < 0.5F)) {
            Level level = minecraft.level;
            if (level != null) {
               float power = smooth(Mth.clamp((progress - 0.5F) / 0.5F, 0.0F, 1.0F));
               int pillars = 6;

               for (int p = 0; p < pillars; p++) {
                  double angle = (float) (Math.PI * 2) * p / pillars + time * (0.006 + late * 0.03);
                  double radius = 9.0 + power * 7.0;
                  double x = this.origin.x + Math.cos(angle) * radius;
                  double z = this.origin.z + Math.sin(angle) * radius;
                  double surfaceY = this.findSurfaceY(level, x, z, this.anchorGroundY + 20.0);
                  if (Math.abs(surfaceY - this.anchorGroundY) > 30.0) {
                     surfaceY = this.anchorGroundY;
                  }

                  int blocks = 5 + (int)(power * 8.0F);

                  for (int j = 0; j < blocks; j++) {
                     float rise = smooth(Mth.clamp(power * 1.72F - j * 0.075F - p * 0.035F, 0.0F, 1.0F));
                     if (!(rise <= 0.01F)) {
                        double targetY = this.anchorGroundY + j * (1.55 + power * 0.45) + power * 4.0;
                        double twist = angle + j * 0.36 + time * 0.014;
                        double bx = x + Math.cos(twist) * 0.55;
                        double bz = z + Math.sin(twist) * 0.55;
                        double by = Mth.lerp(rise * rise, surfaceY - 1.4, targetY);
                        by += Math.sin(rise * Math.PI) * (0.5 + j * 0.08);
                        BlockState state = j % 4 == 0
                           ? Blocks.CRYING_OBSIDIAN.defaultBlockState()
                           : (
                              j % 4 == 1
                                 ? Blocks.OBSIDIAN.defaultBlockState()
                                 : (j % 4 == 2 ? Blocks.AMETHYST_BLOCK.defaultBlockState() : Blocks.TINTED_GLASS.defaultBlockState())
                           );
                        if (rise < 0.38F) {
                           state = this.sampleGroundState(level, bx, bz, surfaceY);
                        }

                        float scale = (0.62F + power * 0.58F + finale * 0.4F) * (0.22F + rise * 0.78F);
                        this.renderBlock(minecraft, poseStack, buffer, camera, state, bx, by, bz, (float)twist, (float)(time * 0.01), (float)(-twist), scale);
                     }
                  }
               }
            }
         }
      }

      private void renderFinalExplosion(Minecraft minecraft, PoseStack poseStack, BufferSource buffer, Vec3 camera, double gameTime) {
         double explosionAge = Math.max(0.0, gameTime - this.endingStart);
         float progress = Mth.clamp((float)(explosionAge / 120.0), 0.0F, 1.0F);
         float fade = 1.0F - progress;
         double blast = 1.0 - Math.pow(1.0 - progress, 3.0);

         for (int i = 0; i < 520; i++) {
            float seed = this.explosionSeeds[i];
            double angle = seed + i * 0.31 + explosionAge * 0.06;
            double startRadius = 52.0 + hash(this.owner, i + 21000) * 160.0;
            double speed = 42.0 + hash(this.owner, i + 22000) * 110.0;
            double radius = startRadius + speed * blast;
            double y = this.anchorGroundY + 2.0 + hash(this.owner, i + 23000) * 72.0;
            y += blast * (20.0 + hash(this.owner, i + 24000) * 80.0);
            y -= progress * progress * 36.0;
            double x = this.origin.x + Math.cos(angle) * radius;
            double z = this.origin.z + Math.sin(angle) * radius;
            float scale = this.explosionSizes[i] * (0.25F + fade * 0.82F);
            float yaw = (float)(angle + explosionAge * 0.13);
            float pitch = (float)(seed + explosionAge * 0.11);
            float roll = (float)(seed * 0.7 + explosionAge * 0.12);
            this.renderBlock(minecraft, poseStack, buffer, camera, this.explosionStates[i], x, y, z, yaw, pitch, roll, scale);
         }

         this.renderShockRing(minecraft, poseStack, buffer, camera, progress, explosionAge, 0.0, 1.0F);
         this.renderShockRing(minecraft, poseStack, buffer, camera, progress, explosionAge, 30.0, 0.65F);
      }

      private void renderShockRing(
         Minecraft minecraft, PoseStack poseStack, BufferSource buffer, Vec3 camera, float progress, double age, double yOffset, float sizeMul
      ) {
         int count = 54;
         double radius = 36.0 + progress * 130.0;

         for (int i = 0; i < count; i++) {
            double angle = (float) (Math.PI * 2) * i / count + age * 0.018;
            double wave = Math.sin(i * 0.9 + age * 0.16) * 8.0;
            double x = this.origin.x + Math.cos(angle) * (radius + wave);
            double z = this.origin.z + Math.sin(angle) * (radius + wave);
            double y = this.anchorGroundY + yOffset + Math.sin(angle * 3.0 + age * 0.1) * 3.0;
            BlockState state = i % 2 == 0 ? Blocks.CRYING_OBSIDIAN.defaultBlockState() : Blocks.OBSIDIAN.defaultBlockState();
            float scale = (1.6F + progress * 5.4F) * sizeMul;
            this.renderBlock(minecraft, poseStack, buffer, camera, state, x, y, z, (float)angle, (float)(age * 0.04), (float)(-angle), scale);
         }
      }

      private double findSurfaceY(Level level, double x, double z, double preferredTop) {
         if (level == null) {
            return this.origin.y;
         }

         int blockX = Mth.floor(x);
         int blockZ = Mth.floor(z);
         int heightmapY = level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, blockX, blockZ);
         int scanTop = Mth.floor(Math.max(preferredTop, Math.max(this.origin.y + 40.0, heightmapY + 8.0)));
         int minY = level.getMinBuildHeight();
         MutableBlockPos pos = new MutableBlockPos();

         for (int y = scanTop; y >= minY; y--) {
            pos.set(blockX, y, blockZ);
            BlockState state = level.getBlockState(pos);
            if (!state.isAir() && !state.is(Blocks.WATER) && !state.is(Blocks.LAVA)) {
               return y + 1.02;
            }
         }

         return heightmapY > minY ? heightmapY + 0.05 : this.origin.y;
      }

      private BlockState sampleGroundState(Level level, double x, double z, double surfaceY) {
         if (level == null) {
            return Blocks.BLACKSTONE.defaultBlockState();
         } else {
            int blockX = Mth.floor(x);
            int blockZ = Mth.floor(z);
            int blockY = Mth.floor(surfaceY) - 1;
            BlockState state = level.getBlockState(new BlockPos(blockX, blockY, blockZ));
            if (state.isAir() || state.is(Blocks.WATER) || state.is(Blocks.LAVA)) {
               return Blocks.BLACKSTONE.defaultBlockState();
            } else if (state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT) || state.is(Blocks.COARSE_DIRT) || state.is(Blocks.ROOTED_DIRT)) {
               return Blocks.DEEPSLATE.defaultBlockState();
            } else {
               return !state.is(Blocks.SAND) && !state.is(Blocks.RED_SAND) && !state.is(Blocks.SANDSTONE) ? state : Blocks.SMOOTH_BASALT.defaultBlockState();
            }
         }
      }

      private void renderBlock(
         Minecraft minecraft,
         PoseStack poseStack,
         BufferSource buffer,
         Vec3 camera,
         BlockState state,
         double x,
         double y,
         double z,
         float yaw,
         float pitch,
         float roll,
         float scale
      ) {
         poseStack.pushPose();
         poseStack.translate(x - camera.x, y - camera.y, z - camera.z);
         poseStack.mulPose(new Quaternionf().rotateY(yaw).rotateX(pitch).rotateZ(roll));
         poseStack.scale(scale, scale, scale);
         poseStack.translate(-0.5, -0.5, -0.5);
         minecraft.getBlockRenderer().renderSingleBlock(state, poseStack, buffer, 15728880, OverlayTexture.NO_OVERLAY);
         poseStack.popPose();
      }

      private static float smooth(float value) {
         return value * value * (3.0F - 2.0F * value);
      }

      private static BlockState pickDarkState(int i) {
         return switch (i % 14) {
            case 0, 1, 2 -> Blocks.OBSIDIAN.defaultBlockState();
            case 3, 4, 5 -> Blocks.CRYING_OBSIDIAN.defaultBlockState();
            case 6, 7 -> Blocks.BLACKSTONE.defaultBlockState();
            case 8 -> Blocks.SMOOTH_BASALT.defaultBlockState();
            case 9 -> Blocks.DEEPSLATE.defaultBlockState();
            case 10 -> Blocks.TUFF.defaultBlockState();
            case 11 -> Blocks.TINTED_GLASS.defaultBlockState();
            case 12 -> Blocks.AMETHYST_BLOCK.defaultBlockState();
            default -> Blocks.PURPLE_STAINED_GLASS.defaultBlockState();
         };
      }

      private static BlockState pickChunkState(int i) {
         return switch (i % 12) {
            case 0, 1 -> Blocks.OBSIDIAN.defaultBlockState();
            case 2, 3 -> Blocks.CRYING_OBSIDIAN.defaultBlockState();
            case 4 -> Blocks.BLACKSTONE.defaultBlockState();
            case 5 -> Blocks.SMOOTH_BASALT.defaultBlockState();
            case 6 -> Blocks.DEEPSLATE.defaultBlockState();
            case 7 -> Blocks.COBBLED_DEEPSLATE.defaultBlockState();
            case 8 -> Blocks.AMETHYST_BLOCK.defaultBlockState();
            case 9 -> Blocks.END_STONE.defaultBlockState();
            case 10 -> Blocks.TINTED_GLASS.defaultBlockState();
            default -> Blocks.PURPLE_STAINED_GLASS.defaultBlockState();
         };
      }

      private static BlockState pickThroneState(int i) {
         return switch (i % 10) {
            case 0, 1, 2 -> Blocks.CRYING_OBSIDIAN.defaultBlockState();
            case 3, 4 -> Blocks.OBSIDIAN.defaultBlockState();
            case 5 -> Blocks.AMETHYST_BLOCK.defaultBlockState();
            case 6 -> Blocks.TINTED_GLASS.defaultBlockState();
            case 7 -> Blocks.BLACKSTONE.defaultBlockState();
            case 8 -> Blocks.SMOOTH_BASALT.defaultBlockState();
            default -> Blocks.END_STONE.defaultBlockState();
         };
      }

      private static BlockState pickSkyState(int i) {
         return switch (i % 10) {
            case 0 -> Blocks.TINTED_GLASS.defaultBlockState();
            case 1 -> Blocks.PURPLE_STAINED_GLASS.defaultBlockState();
            case 2 -> Blocks.AMETHYST_BLOCK.defaultBlockState();
            case 3 -> Blocks.CRYING_OBSIDIAN.defaultBlockState();
            case 4 -> Blocks.OBSIDIAN.defaultBlockState();
            case 5 -> Blocks.END_STONE.defaultBlockState();
            case 6 -> Blocks.GLOWSTONE.defaultBlockState();
            case 7 -> Blocks.END_ROD.defaultBlockState();
            case 8 -> Blocks.SMOOTH_BASALT.defaultBlockState();
            default -> Blocks.BLACKSTONE.defaultBlockState();
         };
      }

      private static float hash(UUID owner, int index) {
         long value = owner.getMostSignificantBits() ^ owner.getLeastSignificantBits() ^ index * 341873128712L;
         value ^= value >>> 33;
         value *= -49064778989728563L;
         value ^= value >>> 33;
         value *= -4265267296055464877L;
         value ^= value >>> 33;
         return (float)(value & 16777215L) / 1.6777215E7F;
      }
   }
}
