package com.github.wal_bos.moreskills.client;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;
import org.joml.Quaternionf;

public final class IstarothClientDoomTornado {
   private static final Map<UUID, IstarothClientDoomTornado.TornadoVisual> TORNADOES = new HashMap<>();
   private static boolean initialized = false;

   private IstarothClientDoomTornado() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(IstarothClientDoomTornado::render);
      }
   }

   public static void start(UUID owner, Vec3 center, int ticks, float radius) {
      TORNADOES.put(owner, new IstarothClientDoomTornado.TornadoVisual(owner, center, Math.max(20, ticks), Math.max(72.0F, radius)));
   }

   public static void stop(UUID owner) {
      IstarothClientDoomTornado.TornadoVisual visual = TORNADOES.get(owner);
      if (visual != null) {
         visual.forceExplode();
      }
   }

   private static void render(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_TRANSLUCENT_BLOCKS) {
         Minecraft minecraft = Minecraft.getInstance();
         if (minecraft.level != null) {
            float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(false);
            double gameTime = (float)minecraft.level.getGameTime() + partialTick;
            PoseStack poseStack = event.getPoseStack();
            BufferSource buffer = minecraft.renderBuffers().bufferSource();
            Vec3 camera = event.getCamera().getPosition();
            Iterator<Entry<UUID, IstarothClientDoomTornado.TornadoVisual>> iterator = TORNADOES.entrySet().iterator();

            while (iterator.hasNext()) {
               Entry<UUID, IstarothClientDoomTornado.TornadoVisual> entry = iterator.next();
               IstarothClientDoomTornado.TornadoVisual visual = entry.getValue();
               if (visual.isDead(gameTime)) {
                  iterator.remove();
               } else {
                  visual.render(minecraft, poseStack, buffer, camera, gameTime);
               }
            }

            buffer.endBatch();
         }
      }
   }

   private static final class TornadoVisual {
      private static final int SAND_CORE_BLOCKS = 560;
      private static final int SAND_OUTER_BLOCKS = 280;
      private static final int SAND_STREAM_BLOCKS = 600;
      private static final int EARTH_CHUNKS = 74;
      private static final int EARTH_SHARDS = 150;
      private static final int SKY_DEBRIS = 190;
      private static final int SKY_CRACKS = 420;
      private static final int EXPLOSION_BLOCKS = 780;
      private static final int BLOCKS_PER_RING = 10;
      private static final int RINGS = 56;
      private static final int EXPLOSION_TICKS = 190;
      private final UUID owner;
      private final Vec3 center;
      private final int totalTicks;
      private final float finalRadius;
      private final long startTick;
      private final BlockState[] coreStates = new BlockState[560];
      private final float[] coreSeeds = new float[560];
      private final float[] coreSizes = new float[560];
      private final float[] coreHeightOffsets = new float[560];
      private final BlockState[] streamStates = new BlockState[600];
      private final float[] streamSeeds = new float[600];
      private final float[] streamSizes = new float[600];
      private final float[] streamSpawn = new float[600];
      private final BlockState[] earthStates = new BlockState[74];
      private final float[] earthSeeds = new float[74];
      private final float[] earthSizes = new float[74];
      private final float[] earthSpawn = new float[74];
      private final BlockState[] shardStates = new BlockState[150];
      private final float[] shardSeeds = new float[150];
      private final float[] shardSizes = new float[150];
      private final BlockState[] skyDebrisStates = new BlockState[190];
      private final float[] skyDebrisSeeds = new float[190];
      private final float[] skyDebrisSizes = new float[190];
      private final float[] skyDebrisSpawn = new float[190];
      private final BlockState[] skyCrackStates = new BlockState[420];
      private final float[] skyCrackSeeds = new float[420];
      private final float[] skyCrackSizes = new float[420];
      private final BlockState[] explosionStates = new BlockState[780];
      private final float[] explosionSeeds = new float[780];
      private final float[] explosionSizes = new float[780];
      private boolean exploding = false;
      private double explosionStart = -1.0;

      private TornadoVisual(UUID owner, Vec3 center, int totalTicks, float finalRadius) {
         this.owner = owner;
         this.center = new Vec3(center.x, center.y, center.z);
         this.totalTicks = totalTicks;
         this.finalRadius = finalRadius;
         this.startTick = Minecraft.getInstance().level == null ? 0L : Minecraft.getInstance().level.getGameTime();

         for (int i = 0; i < 560; i++) {
            this.coreStates[i] = pickSandState(i);
            this.coreSeeds[i] = hash(owner, i) * (float) (Math.PI * 2);
            this.coreSizes[i] = 0.85F + hash(owner, i + 331) * 2.15F;
            this.coreHeightOffsets[i] = (hash(owner, i + 7741) - 0.5F) * 2.4F;
         }

         for (int i = 0; i < 600; i++) {
            this.streamStates[i] = pickSandState(i + 1000);
            this.streamSeeds[i] = hash(owner, i + 77100) * (float) (Math.PI * 2);
            this.streamSizes[i] = 0.55F + hash(owner, i + 77200) * 1.85F;
            this.streamSpawn[i] = hash(owner, i + 77300);
         }

         for (int i = 0; i < 74; i++) {
            this.earthStates[i] = pickEarthChunkState(i);
            this.earthSeeds[i] = hash(owner, i + 88100) * (float) (Math.PI * 2);
            this.earthSizes[i] = 3.5F + hash(owner, i + 88200) * 7.5F;
            this.earthSpawn[i] = hash(owner, i + 88300);
         }

         for (int i = 0; i < 150; i++) {
            this.shardStates[i] = pickEarthShardState(i);
            this.shardSeeds[i] = hash(owner, i + 99100) * (float) (Math.PI * 2);
            this.shardSizes[i] = 0.85F + hash(owner, i + 99200) * 2.4F;
         }

         for (int i = 0; i < 190; i++) {
            this.skyDebrisStates[i] = pickSkyDebrisState(i);
            this.skyDebrisSeeds[i] = hash(owner, i + 121000) * (float) (Math.PI * 2);
            this.skyDebrisSizes[i] = 1.25F + hash(owner, i + 122000) * 5.0F;
            this.skyDebrisSpawn[i] = hash(owner, i + 123000);
         }

         for (int i = 0; i < 420; i++) {
            this.skyCrackStates[i] = pickSkyCrackState(i);
            this.skyCrackSeeds[i] = hash(owner, i + 131000) * (float) (Math.PI * 2);
            this.skyCrackSizes[i] = 0.45F + hash(owner, i + 132000) * 2.0F;
         }

         for (int i = 0; i < 780; i++) {
            this.explosionStates[i] = i % 3 == 0 ? pickEarthShardState(i) : pickSandState(i);
            this.explosionSeeds[i] = hash(owner, i + 92111) * (float) (Math.PI * 2);
            this.explosionSizes[i] = 1.1F + hash(owner, i + 92444) * 5.8F;
         }
      }

      private void forceExplode() {
         Minecraft minecraft = Minecraft.getInstance();
         double now = minecraft.level == null ? this.totalTicks : minecraft.level.getGameTime();
         if (!this.exploding) {
            this.exploding = true;
            this.explosionStart = now;
         }
      }

      private boolean isDead(double gameTime) {
         if (!this.exploding && gameTime - this.startTick > this.totalTicks + 120) {
            this.exploding = true;
            this.explosionStart = gameTime;
         }

         return this.exploding && gameTime - this.explosionStart > 190.0;
      }

      private void render(Minecraft minecraft, PoseStack poseStack, BufferSource buffer, Vec3 camera, double gameTime) {
         if (this.exploding) {
            this.renderExplosion(minecraft, poseStack, buffer, camera, gameTime);
         } else {
            double age = Math.max(0.0, gameTime - this.startTick);
            float progress = Mth.clamp((float)(age / this.totalTicks), 0.0F, 1.0F);
            float eased = progress * progress * (3.0F - 2.0F * progress);
            float remaining = this.totalTicks - (float)age;
            double doomPulse = remaining < 130.0F ? (130.0 - remaining) / 130.0 : 0.0;
            double deathPulse = remaining < 45.0F ? (45.0 - remaining) / 45.0 : 0.0;
            double zeroPulse = remaining < 40.0F ? (40.0 - remaining) / 40.0 : 0.0;
            double maxHeight = Mth.lerp(eased, 58.0, 285.0);
            double coreHeight = Mth.lerp(eased, 36.0, 190.0);
            double baseGrow = Mth.lerp(eased, 0.16, 1.0);
            double acceleration = 1.0 + eased * 3.7 + doomPulse * 4.8 + deathPulse * 8.0;
            this.renderSkyFractures(minecraft, poseStack, buffer, camera, age, eased, remaining, doomPulse, zeroPulse);
            this.renderFallingSkyDebris(minecraft, poseStack, buffer, camera, age, eased, remaining, doomPulse, zeroPulse);
            this.renderSlowRisingEarthChunks(minecraft, poseStack, buffer, camera, age, eased, remaining, doomPulse, deathPulse);
            this.renderEarthShardsAroundChunks(minecraft, poseStack, buffer, camera, age, eased, remaining, doomPulse);
            this.renderSandCore(minecraft, poseStack, buffer, camera, age, eased, remaining, maxHeight, coreHeight, baseGrow, doomPulse, acceleration);
            this.renderSandStreams(minecraft, poseStack, buffer, camera, age, eased, remaining, maxHeight, baseGrow, doomPulse, acceleration);
            this.renderOuterSandDebris(minecraft, poseStack, buffer, camera, age, eased, remaining, maxHeight, baseGrow, doomPulse, acceleration);
            this.renderSandEyeWall(minecraft, poseStack, buffer, camera, age, eased, doomPulse, acceleration);
         }
      }

      private void renderSkyFractures(
         Minecraft minecraft,
         PoseStack poseStack,
         BufferSource buffer,
         Vec3 camera,
         double age,
         float eased,
         float remaining,
         double doomPulse,
         double zeroPulse
      ) {
         if (!(remaining > this.totalTicks - 20) || !(eased < 0.05F)) {
            double skyY = this.center.y + 185.0 + eased * 115.0;
            double baseRadius = this.finalRadius * (2.4 + eased * 3.6);
            int branches = 18;

            for (int i = 0; i < 420; i++) {
               int branch = i % branches;
               int depth = i / branches;
               double branchProgress = (double)depth / Math.max(1, 420 / branches);
               double jag = Math.sin(depth * 1.91 + branch * 0.57) * 0.22 + Math.sin(depth * 0.47 + branch) * 0.1;
               double angle = (Math.PI * 2) * branch / branches + jag + Math.sin(age * 0.004 + branch) * 0.04;
               double fork = branchProgress > 0.42 ? Math.sin(depth * 0.78 + branch * 2.4) * 0.35 : 0.0;
               double radius = baseRadius * (0.16 + branchProgress * (0.85 + hash(this.owner, branch + 133000) * 0.55));
               double x = this.center.x + Math.cos(angle + fork) * radius;
               double z = this.center.z + Math.sin(angle + fork) * radius;
               double y = skyY + Math.sin(branchProgress * Math.PI * 2.0 + branch) * (18.0 + doomPulse * 26.0);
               double appear = Mth.clamp((eased - branchProgress * 0.42) * 2.3, 0.0, 1.0);
               if (!(appear <= 0.02)) {
                  float scale = this.skyCrackSizes[i] * (float)(0.45 + appear * 1.15 + doomPulse * 0.75 + zeroPulse * 1.25);
                  float yaw = (float)(angle + (Math.PI / 2));
                  float pitch = (float)(Math.sin(branchProgress * Math.PI + age * 0.01) * 0.35);
                  float roll = (float)(fork + Math.sin(age * 0.012 + i) * 0.18);
                  this.renderBlock(minecraft, poseStack, buffer, camera, this.skyCrackStates[i], x, y, z, yaw, pitch, roll, scale);
                  if (remaining <= 60.0F && i % 11 == 0) {
                     this.renderBlock(minecraft, poseStack, buffer, camera, Blocks.END_ROD.defaultBlockState(), x, y + 0.7, z, yaw, pitch, roll, scale * 0.55F);
                  }

                  if (remaining <= 12.0F && i % 17 == 0) {
                     this.renderBlock(minecraft, poseStack, buffer, camera, Blocks.GLOWSTONE.defaultBlockState(), x, y, z, yaw, pitch, roll, scale * 0.75F);
                  }
               }
            }
         }
      }

      private void renderFallingSkyDebris(
         Minecraft minecraft,
         PoseStack poseStack,
         BufferSource buffer,
         Vec3 camera,
         double age,
         float eased,
         float remaining,
         double doomPulse,
         double zeroPulse
      ) {
         for (int i = 0; i < 190; i++) {
            double seed = this.skyDebrisSeeds[i];
            double spawnDelay = this.skyDebrisSpawn[i] * this.totalTicks * 0.4;
            double localAge = age - spawnDelay;
            if (!(localAge < 0.0)) {
               double fallT = Mth.clamp(localAge / (360.0 + hash(this.owner, i + 124000) * 180.0), 0.0, 1.0);
               double slowFall = fallT * fallT * (3.0 - 2.0 * fallT);
               double startRadius = this.finalRadius * (2.0 + hash(this.owner, i + 125000) * 4.4);
               double angle = seed + i * 0.38 + Math.sin(age * 0.003 + seed) * 0.18;
               double startY = this.center.y + 310.0 + hash(this.owner, i + 126000) * 170.0;
               double endY = this.center.y + 100.0 + hash(this.owner, i + 127000) * 150.0;
               double radius = startRadius;
               double y = Mth.lerp(slowFall, startY, endY);
               if (remaining < 130.0F) {
                  double pull = (130.0 - remaining) / 130.0;
                  double toward = 1.0 - pull * pull * 0.62;
                  radius *= toward;
                  y += pull * pull * 60.0;
               }

               if (remaining < 45.0F) {
                  double suck = (45.0 - remaining) / 45.0;
                  radius *= 1.0 - suck * 0.58;
                  y -= suck * 42.0;
                  angle += suck * suck * 2.8;
               }

               if (remaining < 12.0F) {
                  double stop = (12.0 - remaining) / 12.0;
                  y += Math.sin(i * 0.91) * stop * 10.0;
               }

               double x = this.center.x + Math.cos(angle) * radius;
               double z = this.center.z + Math.sin(angle) * radius;
               float scale = this.skyDebrisSizes[i] * (0.75F + eased * 0.45F + (float)zeroPulse * 0.4F);
               float yaw = (float)(angle + age * 0.009);
               float pitch = (float)(seed + age * 0.01);
               float roll = (float)(seed * 0.7 + age * 0.012);
               this.renderBlock(minecraft, poseStack, buffer, camera, this.skyDebrisStates[i], x, y, z, yaw, pitch, roll, scale);
            }
         }
      }

      private void renderSlowRisingEarthChunks(
         Minecraft minecraft,
         PoseStack poseStack,
         BufferSource buffer,
         Vec3 camera,
         double age,
         float eased,
         float remaining,
         double doomPulse,
         double deathPulse
      ) {
         for (int i = 0; i < 74; i++) {
            double seed = this.earthSeeds[i];
            double spawnDelay;
            if (i < 24) {
               spawnDelay = this.totalTicks - 600.0 + this.earthSpawn[i] * 80.0;
            } else if (i < 49) {
               spawnDelay = this.totalTicks - 400.0 + this.earthSpawn[i] * 80.0;
            } else {
               spawnDelay = this.totalTicks - 200.0 + this.earthSpawn[i] * 70.0;
            }

            spawnDelay = Math.max(0.0, spawnDelay);
            double localAge = age - spawnDelay;
            if (!(localAge < 0.0)) {
               double riseDuration = 260.0 + hash(this.owner, i + 88400) * 160.0;
               double rise = Mth.clamp(localAge / riseDuration, 0.0, 1.0);
               double heavyRise = rise * rise * (3.0 - 2.0 * rise);
               double ring = 0.42 + hash(this.owner, i + 88500) * 2.35;
               double radius = this.finalRadius * ring;
               double angle = seed + i * 0.41;
               double drift = Math.sin(age * 0.006 + seed) * (1.1 + heavyRise * 2.7);
               double x = this.center.x + Math.cos(angle) * (radius + drift);
               double z = this.center.z + Math.sin(angle) * (radius + drift);
               double maxRise = 16.0 + hash(this.owner, i + 88600) * 56.0 + eased * 44.0;
               double y = this.center.y - 7.0 + heavyRise * maxRise;
               if (remaining < 120.0F) {
                  double pull = (120.0 - remaining) / 120.0;
                  y += pull * pull * (18.0 + hash(this.owner, i + 88700) * 42.0);
                  radius *= 1.0 + pull * 0.16;
                  x = this.center.x + Math.cos(angle) * radius;
                  z = this.center.z + Math.sin(angle) * radius;
               }

               if (remaining < 45.0F) {
                  y += deathPulse * deathPulse * (34.0 + hash(this.owner, i + 88800) * 45.0);
               }

               float scale = this.earthSizes[i] * (0.55F + (float)heavyRise * 0.5F);
               if (remaining <= 60.0F) {
                  scale *= 0.88F;
               }

               if (remaining <= 20.0F) {
                  scale *= 0.72F;
               }

               float yaw = (float)(seed + age * 0.003 * (i % 2 == 0 ? 1.0 : -1.0));
               float pitch = (float)Math.sin(age * 0.005 + seed) * (0.04F + (float)doomPulse * 0.18F);
               float roll = (float)Math.cos(age * 0.004 + seed) * (0.04F + (float)doomPulse * 0.18F);
               if (remaining <= 60.0F) {
                  Vec3 toEye = new Vec3(this.center.x - x, this.center.y + 80.0 - y, this.center.z - z).normalize();
                  pitch += (float)Mth.clamp(toEye.y * 0.8, -0.65, 0.65);
                  roll += (float)Math.sin(angle) * 0.25F;
               }

               if (remaining <= 40.0F) {
                  double zero = (40.0 - remaining) / 40.0;
                  pitch += (float)(zero * Math.sin(seed) * 0.35);
                  roll += (float)(zero * Math.cos(seed) * 0.35);
               }

               this.renderChunkStack(minecraft, poseStack, buffer, camera, x, y, z, yaw, pitch, roll, scale, i);
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
         this.renderBlock(minecraft, poseStack, buffer, camera, Blocks.GRASS_BLOCK.defaultBlockState(), x, y + scale * 0.42, z, yaw, pitch, roll, scale);
         this.renderBlock(minecraft, poseStack, buffer, camera, Blocks.DIRT.defaultBlockState(), x, y - scale * 0.55, z, yaw + 0.1F, pitch, roll, scale * 0.92F);

         BlockState bottom = switch (index % 6) {
            case 0 -> Blocks.STONE.defaultBlockState();
            case 1 -> Blocks.COBBLESTONE.defaultBlockState();
            case 2 -> Blocks.ANDESITE.defaultBlockState();
            case 3 -> Blocks.GRANITE.defaultBlockState();
            case 4 -> Blocks.DIORITE.defaultBlockState();
            default -> Blocks.DEEPSLATE.defaultBlockState();
         };
         this.renderBlock(minecraft, poseStack, buffer, camera, bottom, x, y - scale * 1.35, z, yaw - 0.08F, pitch, roll, scale * 0.82F);
      }

      private void renderEarthShardsAroundChunks(
         Minecraft minecraft, PoseStack poseStack, BufferSource buffer, Vec3 camera, double age, float eased, float remaining, double doomPulse
      ) {
         for (int i = 0; i < 150; i++) {
            double seed = this.shardSeeds[i];
            double spawnDelay = hash(this.owner, i + 99300) * this.totalTicks * 0.65;
            double localAge = age - spawnDelay;
            if (!(localAge < 0.0)) {
               double rise = Mth.clamp(localAge / (220.0 + hash(this.owner, i + 99400) * 190.0), 0.0, 1.0);
               double easedRise = rise * rise * (3.0 - 2.0 * rise);
               double angle = seed + i * 0.77 + age * 0.004 * (i % 2 == 0 ? 1.0 : -1.0);
               double radius = this.finalRadius * (0.65 + hash(this.owner, i + 99500) * 3.0);
               double x = this.center.x + Math.cos(angle) * radius;
               double z = this.center.z + Math.sin(angle) * radius;
               double y = this.center.y - 3.0 + easedRise * (12.0 + hash(this.owner, i + 99600) * 92.0);
               if (remaining < 90.0F) {
                  double pull = (90.0 - remaining) / 90.0;
                  y += pull * pull * 44.0;
                  radius *= 1.0 + pull * 0.24;
                  x = this.center.x + Math.cos(angle) * radius;
                  z = this.center.z + Math.sin(angle) * radius;
               }

               float scale = this.shardSizes[i] * (0.45F + (float)easedRise * 0.75F);
               float yaw = (float)(angle + age * 0.016);
               float pitch = (float)(seed + age * 0.014);
               float roll = (float)(seed * 0.7 + age * 0.016);
               this.renderBlock(minecraft, poseStack, buffer, camera, this.shardStates[i], x, y, z, yaw, pitch, roll, scale);
            }
         }
      }

      private void renderSandCore(
         Minecraft minecraft,
         PoseStack poseStack,
         BufferSource buffer,
         Vec3 camera,
         double age,
         float eased,
         float remaining,
         double maxHeight,
         double coreHeight,
         double baseGrow,
         double doomPulse,
         double acceleration
      ) {
         for (int i = 0; i < 560; i++) {
            int ring = i / 10;
            int index = i % 10;
            double layer = Mth.clamp(ring / 55.0, 0.0, 1.0);
            double layerCurve = Math.pow(layer, 1.45);
            double direction = ring % 2 == 0 ? 1.0 : -1.0;
            double baseAngle = (Math.PI * 2) * index / 10.0;
            double seed = this.coreSeeds[i];
            double y = layer * maxHeight;
            y += Math.sin(age * 0.023 + index * 1.21 + seed) * (0.65 + eased * 2.2);
            y += this.coreHeightOffsets[i] * (0.18 + layer * 0.55);
            if (y < 2.0) {
               y = 2.0;
            }

            double spinSpeed = (0.018 + eased * 0.03 + layer * 0.024) * acceleration;
            double swirlPulse = Math.sin(age * 0.018 * acceleration + seed + layer * 9.0) * (0.9 + eased * 3.0);
            double breathing = Math.sin(age * 0.012 * acceleration + layer * 15.0) * (0.9 + eased * 3.7);
            double eyeRadius = Mth.lerp(eased, 11.0, 21.0);
            double bottomWall = Mth.clamp(layer * 4.0, 0.0, 1.0);
            double baseRadius = eyeRadius + bottomWall * 12.0;
            double midBulge = Math.sin(layer * Math.PI) * this.finalRadius * 0.27 * baseGrow;
            double upperSpread = layerCurve * this.finalRadius * 1.35 * baseGrow;
            double radius = baseRadius + midBulge + upperSpread + swirlPulse + breathing;
            if (layer < 0.18) {
               radius = Math.max(radius, eyeRadius + 8.5 + layer * 48.0);
            }

            if (y > coreHeight) {
               double high = Mth.clamp((y - coreHeight) / Math.max(1.0, maxHeight - coreHeight), 0.0, 1.0);
               radius += high * high * this.finalRadius * 0.55;
            }

            if (remaining < 120.0F) {
               double collapse = (120.0 - remaining) / 120.0;
               radius *= 1.0 + collapse * (0.24 + layer * 0.5);
               y += layer * collapse * 32.0;
            }

            if (remaining < 45.0F) {
               double finalDoom = (45.0 - remaining) / 45.0;
               radius *= 1.0 + finalDoom * (0.36 + layer * 0.72);
               y += layer * Math.sin(age * 0.21 + index + seed) * finalDoom * 13.0;
            }

            radius += doomPulse * Math.sin(age * 0.22 + seed) * (0.9 + layer * 8.0);
            double angle = baseAngle + seed + ring * 0.4 + age * spinSpeed * direction;
            double x = this.center.x + Math.cos(angle) * radius;
            double z = this.center.z + Math.sin(angle) * radius;
            float scale = this.coreSizes[i] * (0.5F + (float)layer * 1.02F);
            scale *= 0.72F + eased * 0.32F;
            if (layer < 0.18) {
               scale *= 0.34F + (float)layer * 3.65F;
            }

            if (layer > 0.8) {
               scale *= 1.0F + (float)((layer - 0.8) * 1.3);
            }

            float yaw = (float)(angle + age * 0.026 * direction * acceleration);
            float pitch = (float)Math.sin(age * 0.03 * acceleration + seed) * 0.34F;
            float roll = (float)Math.cos(age * 0.029 * acceleration + seed) * 0.3F;
            this.renderBlock(minecraft, poseStack, buffer, camera, this.coreStates[i], x, this.center.y + y, z, yaw, pitch, roll, scale);
         }
      }

      private void renderSandStreams(
         Minecraft minecraft,
         PoseStack poseStack,
         BufferSource buffer,
         Vec3 camera,
         double age,
         float eased,
         float remaining,
         double maxHeight,
         double baseGrow,
         double doomPulse,
         double acceleration
      ) {
         for (int i = 0; i < 600; i++) {
            double seed = this.streamSeeds[i];
            double spawnDelay = this.streamSpawn[i] * this.totalTicks * 0.74;
            double localAge = age - spawnDelay;
            if (!(localAge < 0.0)) {
               double life = 120.0 + hash(this.owner, i + 77400) * 190.0;
               double t = localAge % life / life;
               double liftCurve = 1.0 - Math.pow(1.0 - t, 2.3);
               double layer = Mth.clamp(liftCurve, 0.0, 1.0);
               double direction = i % 2 == 0 ? 1.0 : -1.0;
               double groundRadius = this.finalRadius * (1.05 + hash(this.owner, i + 77500) * 2.25) * (0.35 + baseGrow * 0.8);
               double endRadius = this.finalRadius * (0.16 + layer * 1.25);
               double radius = Mth.lerp(layer, groundRadius, endRadius);
               radius += Math.sin(age * 0.025 * acceleration + seed) * (1.5 + layer * 6.0);
               radius += doomPulse * (5.0 + layer * 20.0);
               double y = 0.5 + liftCurve * maxHeight * (0.72 + hash(this.owner, i + 77600) * 0.35);
               y += Math.sin(age * 0.032 + seed) * (0.8 + layer * 4.5);
               if (remaining < 90.0F) {
                  double finalPull = (90.0 - remaining) / 90.0;
                  y += finalPull * finalPull * (32.0 + layer * 72.0);
                  radius *= 1.0 + finalPull * (0.25 + layer * 0.55);
               }

               double angle = seed + i * 0.43 + age * (0.03 + layer * 0.044) * direction * acceleration;
               double x = this.center.x + Math.cos(angle) * radius;
               double z = this.center.z + Math.sin(angle) * radius;
               float scale = this.streamSizes[i] * (0.48F + (float)layer * 1.05F);
               float yaw = (float)(angle + age * 0.05 * direction * acceleration);
               float pitch = (float)Math.sin(age * 0.054 * acceleration + seed) * 0.7F;
               float roll = (float)Math.cos(age * 0.05 * acceleration + seed) * 0.65F;
               this.renderBlock(minecraft, poseStack, buffer, camera, this.streamStates[i], x, this.center.y + y, z, yaw, pitch, roll, scale);
            }
         }
      }

      private void renderOuterSandDebris(
         Minecraft minecraft,
         PoseStack poseStack,
         BufferSource buffer,
         Vec3 camera,
         double age,
         float eased,
         float remaining,
         double maxHeight,
         double baseGrow,
         double doomPulse,
         double acceleration
      ) {
         for (int i = 0; i < 280; i++) {
            double layer = Mth.clamp(Math.pow(hash(this.owner, i + 20000), 0.62), 0.1, 1.0);
            double seed = hash(this.owner, i + 40000) * (float) (Math.PI * 2);
            double direction = i % 2 == 0 ? 1.0 : -1.0;
            double y = layer * maxHeight;
            if (y < 10.0) {
               y = 10.0;
            }

            double angle = seed + i * 0.74 + age * (0.014 + layer * 0.027) * direction * acceleration;
            double layerCurve = Math.pow(layer, 1.32);
            double radius = 38.0 + this.finalRadius * (0.58 + layerCurve * 1.65) * baseGrow;
            radius += Math.sin(age * 0.018 * acceleration + seed) * (3.0 + layer * 8.5);
            radius += Math.cos(age * 0.014 * acceleration + i * 0.7) * (1.5 + layer * 5.2);
            if (remaining < 120.0F) {
               double collapse = (120.0 - remaining) / 120.0;
               radius *= 1.0 + collapse * 0.34;
               y += layer * collapse * 25.0;
            }

            radius += doomPulse * (6.0 + layer * 22.0);
            double x = this.center.x + Math.cos(angle) * radius;
            double z = this.center.z + Math.sin(angle) * radius;
            float scale = 0.72F + hash(this.owner, i + 30000) * 2.25F + (float)layer * 1.0F;
            float yaw = (float)(angle + age * 0.024 * direction * acceleration);
            float pitch = (float)Math.sin(age * 0.026 * acceleration + seed) * 0.44F;
            float roll = (float)Math.cos(age * 0.024 * acceleration + seed) * 0.4F;
            this.renderBlock(minecraft, poseStack, buffer, camera, pickSandState(i + 3000), x, this.center.y + y, z, yaw, pitch, roll, scale);
         }
      }

      private void renderSandEyeWall(
         Minecraft minecraft, PoseStack poseStack, BufferSource buffer, Vec3 camera, double age, float eased, double doomPulse, double acceleration
      ) {
         int count = 38;
         double radius = Mth.lerp(eased, 12.0, 24.0) + doomPulse * 4.0;

         for (int i = 0; i < count; i++) {
            double angle = (Math.PI * 2) * i / count - age * 0.032 * acceleration;
            double y = this.center.y + 0.5 + Math.sin(age * 0.035 + i) * 0.55;
            double x = this.center.x + Math.cos(angle) * radius;
            double z = this.center.z + Math.sin(angle) * radius;
            float scale = 0.7F + eased * 0.4F + (float)doomPulse * 0.18F;
            this.renderBlock(minecraft, poseStack, buffer, camera, pickSandState(i + 5000), x, y, z, (float)angle, 0.0F, (float)(-angle), scale);
         }
      }

      private void renderExplosion(Minecraft minecraft, PoseStack poseStack, BufferSource buffer, Vec3 camera, double gameTime) {
         double explosionAge = Math.max(0.0, gameTime - this.explosionStart);
         float progress = Mth.clamp((float)(explosionAge / 190.0), 0.0F, 1.0F);
         float fade = 1.0F - progress;
         double blast = 1.0 - Math.pow(1.0 - progress, 3.0);

         for (int i = 0; i < 780; i++) {
            double seed = this.explosionSeeds[i];
            double verticalSeed = hash(this.owner, i + 111000);
            double speedSeed = hash(this.owner, i + 112000);
            double angle = seed + i * 0.31 + explosionAge * 0.055;
            double startLayer = hash(this.owner, i + 113000);
            double startY = 2.0 + startLayer * 210.0;
            double startRadius = 8.0 + hash(this.owner, i + 114000) * this.finalRadius * 2.2;
            double outwardSpeed = 70.0 + speedSeed * 230.0;
            double upwardKick = 48.0 + verticalSeed * 150.0;
            double gravity = explosionAge * explosionAge * 0.135;
            double shock = Math.sin(progress * Math.PI) * 52.0;
            double radius = startRadius + outwardSpeed * blast + shock;
            double x = this.center.x + Math.cos(angle) * radius;
            double z = this.center.z + Math.sin(angle) * radius;
            double y = this.center.y + startY + upwardKick * progress - gravity;
            if (y < this.center.y + 0.5) {
               y = this.center.y + 0.5;
            }

            float scale = this.explosionSizes[i] * (0.5F + fade * 1.05F);
            float yaw = (float)(angle + explosionAge * 0.18);
            float pitch = (float)(seed + explosionAge * 0.16);
            float roll = (float)(seed * 0.7 + explosionAge * 0.2);
            this.renderBlock(minecraft, poseStack, buffer, camera, this.explosionStates[i], x, y, z, yaw, pitch, roll, scale);
         }

         this.renderBlastRing(minecraft, poseStack, buffer, camera, progress, explosionAge, 0.0, 1.2F);
         this.renderBlastRing(minecraft, poseStack, buffer, camera, progress, explosionAge, 40.0, 0.88F);
         this.renderBlastRing(minecraft, poseStack, buffer, camera, progress, explosionAge, 92.0, 0.62F);
      }

      private void renderBlastRing(
         Minecraft minecraft, PoseStack poseStack, BufferSource buffer, Vec3 camera, float progress, double explosionAge, double yOffset, float sizeMul
      ) {
         double ringRadius = this.finalRadius * (0.45 + progress * 5.2);
         int count = 66;

         for (int i = 0; i < count; i++) {
            double angle = (Math.PI * 2) * i / count + explosionAge * 0.022;
            double wave = Math.sin(i * 0.8 + explosionAge * 0.15) * 11.0;
            double x = this.center.x + Math.cos(angle) * (ringRadius + wave);
            double z = this.center.z + Math.sin(angle) * (ringRadius + wave);
            double y = this.center.y + yOffset + Math.sin(angle * 3.0 + explosionAge * 0.1) * 5.0;
            BlockState state = i % 4 == 0 ? pickEarthShardState(i) : pickSandState(i);
            float scale = (4.5F + progress * 9.0F) * sizeMul;
            this.renderBlock(minecraft, poseStack, buffer, camera, state, x, y, z, (float)angle, (float)(explosionAge * 0.05), (float)(angle * 0.7), scale);
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

      private static BlockState pickSandState(int i) {
         return switch (i % 14) {
            case 0, 5 -> Blocks.SAND.defaultBlockState();
            case 1, 8 -> Blocks.RED_SAND.defaultBlockState();
            case 2 -> Blocks.SANDSTONE.defaultBlockState();
            case 3 -> Blocks.SMOOTH_SANDSTONE.defaultBlockState();
            case 4 -> Blocks.CUT_SANDSTONE.defaultBlockState();
            case 6 -> Blocks.CHISELED_SANDSTONE.defaultBlockState();
            case 7 -> Blocks.RED_SANDSTONE.defaultBlockState();
            case 9 -> Blocks.SMOOTH_RED_SANDSTONE.defaultBlockState();
            case 10 -> Blocks.CUT_RED_SANDSTONE.defaultBlockState();
            case 11 -> Blocks.CHISELED_RED_SANDSTONE.defaultBlockState();
            case 12 -> Blocks.SANDSTONE_SLAB.defaultBlockState();
            default -> Blocks.RED_SANDSTONE_SLAB.defaultBlockState();
         };
      }

      private static BlockState pickEarthChunkState(int i) {
         return switch (i % 12) {
            case 0 -> Blocks.GRASS_BLOCK.defaultBlockState();
            case 1 -> Blocks.DIRT.defaultBlockState();
            case 2 -> Blocks.COARSE_DIRT.defaultBlockState();
            case 3 -> Blocks.ROOTED_DIRT.defaultBlockState();
            case 4 -> Blocks.STONE.defaultBlockState();
            case 5 -> Blocks.COBBLESTONE.defaultBlockState();
            case 6 -> Blocks.ANDESITE.defaultBlockState();
            case 7 -> Blocks.DIORITE.defaultBlockState();
            case 8 -> Blocks.GRANITE.defaultBlockState();
            case 9 -> Blocks.DEEPSLATE.defaultBlockState();
            case 10 -> Blocks.TUFF.defaultBlockState();
            default -> Blocks.CALCITE.defaultBlockState();
         };
      }

      private static BlockState pickEarthShardState(int i) {
         return switch (i % 16) {
            case 0 -> Blocks.GRASS_BLOCK.defaultBlockState();
            case 1 -> Blocks.DIRT.defaultBlockState();
            case 2 -> Blocks.COARSE_DIRT.defaultBlockState();
            case 3 -> Blocks.STONE.defaultBlockState();
            case 4 -> Blocks.COBBLESTONE.defaultBlockState();
            case 5 -> Blocks.MOSSY_COBBLESTONE.defaultBlockState();
            case 6 -> Blocks.ANDESITE.defaultBlockState();
            case 7 -> Blocks.DIORITE.defaultBlockState();
            case 8 -> Blocks.GRANITE.defaultBlockState();
            case 9 -> Blocks.DEEPSLATE.defaultBlockState();
            case 10 -> Blocks.COBBLED_DEEPSLATE.defaultBlockState();
            case 11 -> Blocks.TUFF.defaultBlockState();
            case 12 -> Blocks.SMOOTH_BASALT.defaultBlockState();
            case 13 -> Blocks.BLACKSTONE.defaultBlockState();
            case 14 -> Blocks.OAK_LOG.defaultBlockState();
            default -> Blocks.OAK_LEAVES.defaultBlockState();
         };
      }

      private static BlockState pickSkyDebrisState(int i) {
         return switch (i % 12) {
            case 0 -> Blocks.TINTED_GLASS.defaultBlockState();
            case 1 -> Blocks.AMETHYST_BLOCK.defaultBlockState();
            case 2 -> Blocks.OBSIDIAN.defaultBlockState();
            case 3 -> Blocks.CRYING_OBSIDIAN.defaultBlockState();
            case 4 -> Blocks.END_STONE.defaultBlockState();
            case 5 -> Blocks.GLOWSTONE.defaultBlockState();
            case 6 -> Blocks.SMOOTH_BASALT.defaultBlockState();
            case 7 -> Blocks.BLACKSTONE.defaultBlockState();
            case 8 -> Blocks.WHITE_STAINED_GLASS.defaultBlockState();
            case 9 -> Blocks.LIGHT_GRAY_STAINED_GLASS.defaultBlockState();
            case 10 -> Blocks.PURPLE_STAINED_GLASS.defaultBlockState();
            default -> Blocks.END_ROD.defaultBlockState();
         };
      }

      private static BlockState pickSkyCrackState(int i) {
         return switch (i % 8) {
            case 0 -> Blocks.WHITE_STAINED_GLASS.defaultBlockState();
            case 1 -> Blocks.LIGHT_GRAY_STAINED_GLASS.defaultBlockState();
            case 2 -> Blocks.TINTED_GLASS.defaultBlockState();
            case 3 -> Blocks.AMETHYST_BLOCK.defaultBlockState();
            case 4 -> Blocks.END_ROD.defaultBlockState();
            case 5 -> Blocks.GLOWSTONE.defaultBlockState();
            case 6 -> Blocks.CRYING_OBSIDIAN.defaultBlockState();
            default -> Blocks.OBSIDIAN.defaultBlockState();
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
