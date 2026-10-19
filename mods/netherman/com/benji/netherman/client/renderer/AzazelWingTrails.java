package com.benji.netherman.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingOut;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Pre;
import org.joml.Matrix4f;

@EventBusSubscriber(modid = "netherman", value = Dist.CLIENT)
public class AzazelWingTrails {
   private static final Map<UUID, AzazelWingTrails.TrailData> TRAILS = new HashMap<>();
   private static final Map<UUID, AzazelWingTrails.SparkDrillData> ACTIVE_SPARK_DRILLS = new HashMap<>();
   private static final List<AzazelWingTrails.ShockwaveData> SHOCKWAVES = new ArrayList<>();
   private static final List<AzazelWingTrails.SparkData> SPARKS = new ArrayList<>();
   private static final int MAX_TRAIL_LENGTH = 15;

   public static void startBoost(Player player, int ticks) {
      AzazelWingTrails.TrailData data = TRAILS.computeIfAbsent(player.getUUID(), k -> new AzazelWingTrails.TrailData());
      data.ticksRemaining = ticks;
   }

   public static void spawnShockwave(Player player) {
      Vec3 look = player.getLookAngle();
      Vec3 right = look.cross(new Vec3(0.0, 1.0, 0.0)).normalize();
      Vec3 backUp = right.cross(look).normalize();
      Vec3 center = player.position().add(0.0, 0.4, 0.0).subtract(look.scale(0.01));
      SHOCKWAVES.add(new AzazelWingTrails.ShockwaveData(center, right, backUp));
   }

   public static void startDrillingSparks(Player player, int ticks, Vec3 look) {
      ACTIVE_SPARK_DRILLS.put(player.getUUID(), new AzazelWingTrails.SparkDrillData(ticks, look));
   }

   @SubscribeEvent
   public static void onClientLogout(LoggingOut event) {
      TRAILS.clear();
      SHOCKWAVES.clear();
      SPARKS.clear();
      ACTIVE_SPARK_DRILLS.clear();
   }

   @SubscribeEvent
   public static void onPlayerTick(Pre event) {
      Player player = event.getEntity();
      if (player.level().isClientSide()) {
         UUID uuid = player.getUUID();
         AzazelWingTrails.TrailData data = TRAILS.get(uuid);
         if (!player.isAlive()) {
            if (data != null) {
               data.ticksRemaining = 0;
            }

            ACTIVE_SPARK_DRILLS.remove(uuid);
         }

         if (data != null) {
            if (data.ticksRemaining > 0) {
               data.ticksRemaining--;
               Vec3 look = player.getLookAngle();
               Vec3 up = new Vec3(0.0, 1.0, 0.0);
               Vec3 right = look.cross(up).normalize();
               Vec3 backUp = right.cross(look).normalize();
               Vec3 center = player.position().add(0.0, 0.4, 0.0);
               Vec3 mainRightPos = center.add(right.scale(2.8)).subtract(look.scale(0.3)).add(backUp.scale(0.2));
               Vec3 mainLeftPos = center.subtract(right.scale(2.8)).subtract(look.scale(0.3)).add(backUp.scale(0.2));
               Vec3 addRightPos = center.add(right.scale(1.5)).subtract(look.scale(0.1)).subtract(backUp.scale(0.4));
               Vec3 addLeftPos = center.subtract(right.scale(1.5)).subtract(look.scale(0.1)).subtract(backUp.scale(0.4));
               addPoint(data.mainLeft, mainLeftPos);
               addPoint(data.mainRight, mainRightPos);
               addPoint(data.addLeft, addLeftPos);
               addPoint(data.addRight, addRightPos);
            } else {
               shrinkTrail(data.mainLeft);
               shrinkTrail(data.mainRight);
               shrinkTrail(data.addLeft);
               shrinkTrail(data.addRight);
               if (data.mainLeft.isEmpty()) {
                  TRAILS.remove(uuid);
               }
            }
         }

         AzazelWingTrails.SparkDrillData drillData = ACTIVE_SPARK_DRILLS.get(uuid);
         if (drillData != null) {
            if (drillData.ticksRemaining > 0 && player.isAlive()) {
               drillData.ticksRemaining--;
               Vec3 origin = player.position().add(0.0, 0.8, 0.0).add(drillData.direction.scale(0.5));
               RandomSource rand = player.getRandom();

               for (int i = 0; i < 8; i++) {
                  Vec3 sparkVel = drillData.direction
                     .scale(0.15)
                     .add((rand.nextDouble() - 0.5) * 0.5, (rand.nextDouble() - 0.5) * 0.5, (rand.nextDouble() - 0.5) * 0.5);
                  int maxAge = 6 + rand.nextInt(10);
                  int colorIndex = rand.nextInt(3);
                  int color = colorIndex == 0 ? 16711680 : (colorIndex == 1 ? 16737792 : 16763904);
                  SPARKS.add(new AzazelWingTrails.SparkData(origin, sparkVel, maxAge, color));
               }
            } else {
               ACTIVE_SPARK_DRILLS.remove(uuid);
            }
         }
      }
   }

   @SubscribeEvent
   public static void onClientTick(net.neoforged.neoforge.client.event.ClientTickEvent.Pre event) {
      Iterator<AzazelWingTrails.ShockwaveData> iterator = SHOCKWAVES.iterator();

      while (iterator.hasNext()) {
         AzazelWingTrails.ShockwaveData wave = iterator.next();
         wave.age++;
         if (wave.age >= 8) {
            iterator.remove();
         }
      }

      Iterator<AzazelWingTrails.SparkData> sparkIterator = SPARKS.iterator();

      while (sparkIterator.hasNext()) {
         AzazelWingTrails.SparkData spark = sparkIterator.next();
         spark.pos = spark.pos.add(spark.velocity);
         spark.age++;
         if (spark.age >= spark.maxAge) {
            sparkIterator.remove();
         }
      }
   }

   private static void addPoint(LinkedList<Vec3> list, Vec3 point) {
      list.addFirst(point);
      if (list.size() > 15) {
         list.removeLast();
      }
   }

   private static void shrinkTrail(LinkedList<Vec3> list) {
      if (!list.isEmpty()) {
         list.removeLast();
      }
   }

   @SubscribeEvent
   public static void onRenderLevel(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_TRANSLUCENT_BLOCKS) {
         if (!TRAILS.isEmpty() || !SHOCKWAVES.isEmpty() || !SPARKS.isEmpty()) {
            Vec3 camPos = event.getCamera().getPosition();
            PoseStack poseStack = event.getPoseStack();
            float partialTick = event.getPartialTick().getGameTimeDeltaTicks();
            RenderSystem.disableCull();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.depthMask(false);
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder buffer = tesselator.begin(Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
            poseStack.pushPose();
            poseStack.translate(-camPos.x, -camPos.y, -camPos.z);
            Matrix4f matrix = poseStack.last().pose();
            if (!TRAILS.isEmpty()) {
               for (Entry<UUID, AzazelWingTrails.TrailData> entry : TRAILS.entrySet()) {
                  AzazelWingTrails.TrailData data = entry.getValue();
                  drawRibbon(buffer, matrix, data.mainLeft, camPos, 0.02F);
                  drawRibbon(buffer, matrix, data.mainRight, camPos, 0.02F);
                  drawRibbon(buffer, matrix, data.addLeft, camPos, 0.02F);
                  drawRibbon(buffer, matrix, data.addRight, camPos, 0.02F);
                  if (data.ticksRemaining > 0 && Minecraft.getInstance().level != null) {
                     Player player = Minecraft.getInstance().level.getPlayerByUUID(entry.getKey());
                     if (player != null && player.isAlive()) {
                        double x = Mth.lerp(partialTick, player.xo, player.getX());
                        double y = Mth.lerp(partialTick, player.yo, player.getY());
                        double z = Mth.lerp(partialTick, player.zo, player.getZ());
                        Vec3 playerPos = new Vec3(x, y, z).add(0.0, 0.4, 0.0);
                        Vec3 look = player.getViewVector(partialTick);
                        Vec3 upVec = new Vec3(0.0, 1.0, 0.0);
                        if (Math.abs(look.y) > 0.99) {
                           upVec = new Vec3(1.0, 0.0, 0.0);
                        }

                        Vec3 right = look.cross(upVec).normalize();
                        Vec3 backUp = right.cross(look).normalize();
                        float var22 = Math.min(1.0F, data.ticksRemaining / 15.0F);
                     }
                  }
               }
            }

            if (!SHOCKWAVES.isEmpty()) {
               for (AzazelWingTrails.ShockwaveData wave : SHOCKWAVES) {
                  drawShockwaveRing(buffer, matrix, wave, partialTick);
               }
            }

            if (!SPARKS.isEmpty()) {
               for (AzazelWingTrails.SparkData spark : SPARKS) {
                  drawSparkLine(buffer, matrix, spark, camPos);
               }
            }

            MeshData meshData = buffer.build();
            if (meshData != null) {
               BufferUploader.drawWithShader(meshData);
            }

            poseStack.popPose();
            RenderSystem.depthMask(true);
            RenderSystem.enableBlend();
            RenderSystem.enableCull();
         }
      }
   }

   private static void drawSparkLine(VertexConsumer buffer, Matrix4f matrix, AzazelWingTrails.SparkData spark, Vec3 camPos) {
      float progress = (float)spark.age / spark.maxAge;
      int alpha = (int)(255.0F * (1.0F - progress));
      int r = spark.color >> 16 & 0xFF;
      int g = spark.color >> 8 & 0xFF;
      int b = spark.color & 0xFF;
      Vec3 p1 = spark.pos;
      Vec3 p2 = spark.pos.subtract(spark.velocity.scale(0.75));
      float width = 0.025F * (1.0F - progress);
      Vec3 toCam = camPos.subtract(p1).normalize();
      Vec3 dir = p1.subtract(p2).normalize();
      Vec3 right = dir.cross(toCam).normalize().scale(width);
      addVertex(buffer, matrix, p1.add(right), r, g, b, alpha);
      addVertex(buffer, matrix, p1.subtract(right), r, g, b, alpha);
      addVertex(buffer, matrix, p2.subtract(right), r, g, b, alpha);
      addVertex(buffer, matrix, p2.add(right), r, g, b, alpha);
   }

   private static void drawRibbon(VertexConsumer buffer, Matrix4f matrix, LinkedList<Vec3> points, Vec3 camPos, float startWidth) {
      if (points.size() >= 2) {
         for (int i = 0; i < points.size() - 1; i++) {
            Vec3 p1 = points.get(i);
            Vec3 p2 = points.get(i + 1);
            float p1Prog = (float)i / points.size();
            float p2Prog = (float)(i + 1) / points.size();
            float w1 = startWidth * (1.0F - p1Prog);
            float w2 = startWidth * (1.0F - p2Prog);
            int a1 = (int)(200.0F * (1.0F - p1Prog));
            int a2 = (int)(200.0F * (1.0F - p2Prog));
            int r1 = (int)lerp(255.0F, 100.0F, p1Prog);
            int g1 = (int)lerp(255.0F, 100.0F, p1Prog);
            int b1 = (int)lerp(255.0F, 120.0F, p1Prog);
            int r2 = (int)lerp(255.0F, 100.0F, p2Prog);
            int g2 = (int)lerp(255.0F, 100.0F, p2Prog);
            int b2 = (int)lerp(255.0F, 120.0F, p2Prog);
            Vec3 right1 = p2.subtract(p1).normalize().cross(camPos.subtract(p1).normalize()).normalize().scale(w1);
            Vec3 right2 = p2.subtract(p1).normalize().cross(camPos.subtract(p2).normalize()).normalize().scale(w2);
            addVertex(buffer, matrix, p1.add(right1), r1, g1, b1, a1);
            addVertex(buffer, matrix, p1.subtract(right1), r1, g1, b1, a1);
            addVertex(buffer, matrix, p2.subtract(right2), r2, g2, b2, a2);
            addVertex(buffer, matrix, p2.add(right2), r2, g2, b2, a2);
         }
      }
   }

   private static void drawShockwaveRing(VertexConsumer buffer, Matrix4f matrix, AzazelWingTrails.ShockwaveData wave, float partialTick) {
      float progress = (wave.age + partialTick) / 8.0F;
      if (!(progress > 1.0F)) {
         float maxRadius = 4.5F;
         float currentOuterRadius = 0.2F + (maxRadius - 0.2F) * progress;
         float thickness = 0.4F * (1.0F - progress);
         float currentInnerRadius = Math.max(0.0F, currentOuterRadius - thickness);
         int alpha = (int)(180.0F * (1.0F - progress));
         int segments = 32;
         Vec3 prevInner = null;
         Vec3 prevOuter = null;
         Vec3 firstInner = null;
         Vec3 firstOuter = null;

         for (int i = 0; i <= segments; i++) {
            double angle = i * 2.0 * Math.PI / segments;
            Vec3 dirVec = wave.right.scale(Math.cos(angle)).add(wave.backUp.scale(Math.sin(angle)));
            Vec3 innerPos = wave.center.add(dirVec.scale(currentInnerRadius));
            Vec3 outerPos = wave.center.add(dirVec.scale(currentOuterRadius));
            if (i == 0) {
               firstInner = innerPos;
               firstOuter = outerPos;
            } else {
               addVertex(buffer, matrix, prevInner, 255, 255, 255, alpha);
               addVertex(buffer, matrix, prevOuter, 120, 120, 140, alpha);
               addVertex(buffer, matrix, outerPos, 120, 120, 140, alpha);
               addVertex(buffer, matrix, innerPos, 255, 255, 255, alpha);
            }

            prevInner = innerPos;
            prevOuter = outerPos;
         }

         if (prevInner != null && firstInner != null) {
            addVertex(buffer, matrix, prevInner, 255, 255, 255, alpha);
            addVertex(buffer, matrix, prevOuter, 120, 120, 140, alpha);
            addVertex(buffer, matrix, firstOuter, 120, 120, 140, alpha);
            addVertex(buffer, matrix, firstInner, 255, 255, 255, alpha);
         }
      }
   }

   private static void addVertex(VertexConsumer buffer, Matrix4f matrix, Vec3 pos, int r, int g, int b, int a) {
      buffer.addVertex(matrix, (float)pos.x, (float)pos.y, (float)pos.z).setColor(r, g, b, a);
   }

   private static float lerp(float start, float end, float delta) {
      return start + delta * (end - start);
   }

   public static class ShockwaveData {
      public final Vec3 center;
      public final Vec3 right;
      public final Vec3 backUp;
      public int age = 0;
      public final int maxAge = 8;

      public ShockwaveData(Vec3 center, Vec3 right, Vec3 backUp) {
         this.center = center;
         this.right = right;
         this.backUp = backUp;
      }
   }

   public static class SparkData {
      public Vec3 pos;
      public final Vec3 velocity;
      public int age = 0;
      public final int maxAge;
      public final int color;

      public SparkData(Vec3 pos, Vec3 velocity, int maxAge, int color) {
         this.pos = pos;
         this.velocity = velocity;
         this.maxAge = maxAge;
         this.color = color;
      }
   }

   public static class SparkDrillData {
      public int ticksRemaining;
      public Vec3 direction;

      public SparkDrillData(int ticks, Vec3 dir) {
         this.ticksRemaining = ticks;
         this.direction = dir;
      }
   }

   public static class TrailData {
      public int ticksRemaining = 0;
      public LinkedList<Vec3> mainLeft = new LinkedList<>();
      public LinkedList<Vec3> mainRight = new LinkedList<>();
      public LinkedList<Vec3> addLeft = new LinkedList<>();
      public LinkedList<Vec3> addRight = new LinkedList<>();
   }
}
