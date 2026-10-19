package com.tensura_tno.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.tensura_tno.TensuraTNOMod;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.tensura.config.ability.AbilityConfig;
import io.github.manasmods.tensura.data.TensuraBlockTags;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.RenderStateShard.LineStateShard;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;

public final class HeatSourceCollisionXray {
   private static final ResourceLocation BLOCK_SENSE = ResourceLocation.fromNamespaceAndPath("tensura", "block_sense");
   private static final int CACHE_INTERVAL_TICKS = 20;
   private static final float RED = 0.96F;
   private static final float GREEN = 0.51F;
   private static final float BLUE = 0.12F;
   private static final float ALPHA = 1.0F;
   private static final List<HeatSourceCollisionXray.ShapeEdge> cachedEdges = new ArrayList<>();
   private static boolean initialized;
   private static int lastCacheTick = -1;
   private static int cachedRadius = -1;
   private static BlockPos cachedOrigin;
   private static Object cachedLevel;
   private static Player cachedPlayer;

   private HeatSourceCollisionXray() {
   }

   public static synchronized void init() {
      if (!initialized && ModList.get().isLoaded("tensura")) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(HeatSourceCollisionXray::onRenderLevelStage);
         TensuraTNOMod.LOGGER.info("[TensuraTNO] Heat source collision-shape renderer registered");
      }
   }

   private static void onRenderLevelStage(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_PARTICLES) {
         Minecraft mc = Minecraft.getInstance();
         Player player = mc.player;
         if (player != null && mc.level != null) {
            int radius = activeRadius(player);
            if (radius <= 0) {
               clearCache();
            } else {
               BlockPos origin = player.blockPosition();
               if (needsRebuild(player, origin, radius)) {
                  rebuildCache(player, origin, radius);
               }

               if (!cachedEdges.isEmpty()) {
                  renderEdges(event, mc);
               }
            }
         } else {
            clearCache();
         }
      }
   }

   private static boolean needsRebuild(Player player, BlockPos origin, int radius) {
      if (lastCacheTick < 0 || cachedLevel != player.level() || cachedPlayer != player) {
         return true;
      } else if (cachedOrigin != null && cachedRadius == radius) {
         long dx = (long)origin.getX() - cachedOrigin.getX();
         long dy = (long)origin.getY() - cachedOrigin.getY();
         long dz = (long)origin.getZ() - cachedOrigin.getZ();
         long shiftThreshold = Math.max(4L, cachedRadius / 2L);
         boolean movedFarEnough = dx * dx + dy * dy + dz * dz > shiftThreshold * shiftThreshold;
         int elapsed = player.tickCount - lastCacheTick;
         return movedFarEnough || elapsed >= 20;
      } else {
         return true;
      }
   }

   private static void rebuildCache(Player player, BlockPos origin, int radius) {
      cachedEdges.clear();
      cachedOrigin = origin.immutable();
      cachedRadius = radius;
      cachedLevel = player.level();
      cachedPlayer = player;
      lastCacheTick = player.tickCount;
      long radiusSquared = (long)radius * radius;

      for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-radius, -radius, -radius), origin.offset(radius, radius, radius))) {
         if (!(pos.distSqr(origin) > radiusSquared)) {
            BlockPos immutablePos = pos.immutable();
            BlockState state = player.level().getBlockState(immutablePos);
            if (isHeatSource(state)) {
               VoxelShape shape = state.getCollisionShape(player.level(), immutablePos, CollisionContext.empty());
               if (shape.isEmpty()) {
                  shape = state.getShape(player.level(), immutablePos, CollisionContext.empty());
               }

               if (!shape.isEmpty()) {
                  VoxelShape finalShape = shape;
                  finalShape.forAllEdges(
                     (x1, y1, z1, x2, y2, z2) -> cachedEdges.add(
                        new HeatSourceCollisionXray.ShapeEdge(
                           immutablePos.getX() + x1,
                           immutablePos.getY() + y1,
                           immutablePos.getZ() + z1,
                           immutablePos.getX() + x2,
                           immutablePos.getY() + y2,
                           immutablePos.getZ() + z2
                        )
                     )
                  );
               }
            }
         }
      }
   }

   private static void renderEdges(RenderLevelStageEvent event, Minecraft mc) {
      PoseStack poseStack = event.getPoseStack();
      BufferSource buffer = mc.renderBuffers().bufferSource();
      VertexConsumer consumer = buffer.getBuffer(HeatSourceCollisionXray.RenderTypes.HEAT_LINES);
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      poseStack.pushPose();

      try {
         Vec3 camera = event.getCamera().getPosition();
         poseStack.translate(-camera.x, -camera.y, -camera.z);
         Pose pose = poseStack.last();

         for (HeatSourceCollisionXray.ShapeEdge edge : cachedEdges) {
            float dx = (float)(edge.x2 - edge.x1);
            float dy = (float)(edge.y2 - edge.y1);
            float dz = (float)(edge.z2 - edge.z1);
            float length = (float)Math.sqrt(dx * dx + dy * dy + dz * dz);
            if (!(length <= 1.0E-6F)) {
               consumer.addVertex(pose, (float)edge.x1, (float)edge.y1, (float)edge.z1)
                  .setColor(0.96F, 0.51F, 0.12F, 1.0F)
                  .setNormal(pose, dx / length, dy / length, dz / length);
               consumer.addVertex(pose, (float)edge.x2, (float)edge.y2, (float)edge.z2)
                  .setColor(0.96F, 0.51F, 0.12F, 1.0F)
                  .setNormal(pose, dx / length, dy / length, dz / length);
            }
         }

         buffer.endBatch(HeatSourceCollisionXray.RenderTypes.HEAT_LINES);
      } finally {
         poseStack.popPose();
         RenderSystem.depthMask(true);
         RenderSystem.enableDepthTest();
      }
   }

   private static int activeRadius(Player player) {
      if (!((AbilityConfig)ConfigRegistry.getConfig(AbilityConfig.class)).Misc.blockXrayVision) {
         return 0;
      }

      if (player.level().dimensionType().ultraWarm()) {
         return 0;
      }

      AttributeInstance attribute = player.getAttribute(TensuraAttributes.HEAT_SENSE_RADIUS);
      return attribute != null && !(attribute.getValue() <= 0.0) && !attribute.hasModifier(BLOCK_SENSE) ? Math.max(1, (int)attribute.getValue()) : 0;
   }

   private static boolean isHeatSource(BlockState state) {
      return state.is(TensuraBlockTags.HEAT_SOURCE_BLOCKS) && state.getLightEmission() >= 3;
   }

   private static void clearCache() {
      cachedEdges.clear();
      cachedOrigin = null;
      cachedRadius = -1;
      cachedLevel = null;
      cachedPlayer = null;
      lastCacheTick = -1;
   }

   private static final class RenderTypes extends RenderStateShard {
      private static final RenderType HEAT_LINES = RenderType.create(
         "tensura_tno_heat_source_collision_lines",
         DefaultVertexFormat.POSITION_COLOR_NORMAL,
         Mode.LINES,
         256,
         false,
         false,
         CompositeState.builder()
            .setShaderState(RENDERTYPE_LINES_SHADER)
            .setLineState(new LineStateShard(OptionalDouble.of(3.0)))
            .setLayeringState(VIEW_OFFSET_Z_LAYERING)
            .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
            .setOutputState(ITEM_ENTITY_TARGET)
            .setCullState(NO_CULL)
            .setDepthTestState(NO_DEPTH_TEST)
            .setWriteMaskState(COLOR_WRITE)
            .createCompositeState(false)
      );

      private RenderTypes() {
         super("dummy", () -> {}, () -> {});
      }
   }

   private record ShapeEdge(double x1, double y1, double z1, double x2, double y2, double z2) {
   }
}
