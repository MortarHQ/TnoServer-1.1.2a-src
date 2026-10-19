package com.tensura_tno.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.tensura_tno.TensuraTNOMod;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.common.NeoForge;

public final class MagicOreSenseXray {
   private static final ResourceLocation MAGIC_ORE_ID = ResourceLocation.fromNamespaceAndPath("tensura", "magic_ore");
   private static final ResourceLocation DEEPSLATE_MAGIC_ORE_ID = ResourceLocation.fromNamespaceAndPath("tensura", "deepslate_magic_ore");
   private static final TagKey<Block> MAGIC_ORES_TAG = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("tensura", "magic_ores"));
   private static final int EXPANSION_TICKS = 40;
   private static final int PARTICLE_INTERVAL_TICKS = 20;
   private static final int MAX_PARTICLES_PER_TICK = 2;
   private static final float XRAY_ALPHA = 0.75F;
   private static final float BLOCK_SCALE = 1.005F;
   private static boolean initialized;
   private static Block cachedMagicOre;
   private static Block cachedDeepslateMagicOre;
   private static final List<MagicOreSenseXray.ScannedOre> scannedOres = new ArrayList<>();
   private static BlockPos scanOrigin;
   private static int scanStartTick;
   private static int scanRadius;
   private static int scanDurationTicks;
   private static int particleTickCounter;

   private MagicOreSenseXray() {
   }

   public static void init() {
      if (!initialized && ModList.get().isLoaded("tensura")) {
         initialized = true;
         cachedMagicOre = (Block)BuiltInRegistries.BLOCK.getOptional(MAGIC_ORE_ID).orElse(null);
         cachedDeepslateMagicOre = (Block)BuiltInRegistries.BLOCK.getOptional(DEEPSLATE_MAGIC_ORE_ID).orElse(null);
         NeoForge.EVENT_BUS.addListener(MagicOreSenseXray::onRenderLevelStage);
         TensuraTNOMod.LOGGER.info("[TensuraTNO] Magic Ore Sense renderer registered");
      }
   }

   public static void startScan(int radius, int durationTicks) {
      Minecraft mc = Minecraft.getInstance();
      Player player = mc.player;
      if (player != null && mc.level != null) {
         scanOrigin = player.blockPosition();
         scanStartTick = player.tickCount;
         scanRadius = Math.max(1, radius);
         scanDurationTicks = Math.max(1, durationTicks);
         particleTickCounter = 0;
         rebuildScan(player, scanOrigin, scanRadius);
      }
   }

   private static void onRenderLevelStage(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_PARTICLES) {
         if (!scannedOres.isEmpty() && scanOrigin != null) {
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            if (player != null && mc.level != null) {
               int elapsed = player.tickCount - scanStartTick;
               int holdEndTick = 40 + scanDurationTicks;
               int clearTick = holdEndTick + 40;
               if (elapsed > clearTick) {
                  clearScan();
               } else {
                  double currentRadius = visibleRadius(elapsed, holdEndTick);
                  renderRevealedOres(event, mc, currentRadius);
                  spawnOreParticles(mc, currentRadius);
               }
            }
         }
      }
   }

   private static double visibleRadius(int elapsed, int holdEndTick) {
      if (elapsed <= 40) {
         return scanRadius * Math.max(0.0, elapsed / 40.0);
      }

      if (elapsed <= holdEndTick) {
         return scanRadius;
      }

      int collapseElapsed = elapsed - holdEndTick;
      double collapseProgress = Math.min(1.0, collapseElapsed / 40.0);
      return scanRadius * (1.0 - collapseProgress);
   }

   private static void renderRevealedOres(RenderLevelStageEvent event, Minecraft mc, double currentRadius) {
      PoseStack poseStack = event.getPoseStack();
      Vec3 cam = event.getCamera().getPosition();
      BlockRenderDispatcher blockRenderer = mc.getBlockRenderer();
      BufferSource bufferSource = mc.renderBuffers().bufferSource();
      MultiBufferSource xraySource = rt -> bufferSource.getBuffer(MagicOreSenseXray.XrayRenderTypes.XRAY_BLOCK);
      scannedOres.sort(Comparator.comparingDouble(orex -> -orex.pos().distToCenterSqr(cam.x, cam.y, cam.z)));
      RenderSystem.disableDepthTest();
      RenderSystem.depthMask(false);
      RenderSystem.enableCull();
      poseStack.pushPose();

      try {
         for (MagicOreSenseXray.ScannedOre ore : scannedOres) {
            if (!(ore.distance() > currentRadius)) {
               BlockState state = mc.level.getBlockState(ore.pos());
               if (isMagicOre(state)) {
                  BlockPos pos = ore.pos();
                  RenderSystem.setShaderColor(0.3F, 0.3F, 0.3F, 0.75F);
                  poseStack.pushPose();
                  poseStack.translate(pos.getX() - cam.x, pos.getY() - cam.y, pos.getZ() - cam.z);
                  poseStack.translate(0.5, 0.5, 0.5);
                  poseStack.scale(1.005F, 1.005F, 1.005F);
                  poseStack.translate(-0.5, -0.5, -0.5);
                  blockRenderer.renderSingleBlock(state, poseStack, xraySource, 15728880, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, null);
                  poseStack.popPose();
               }
            }
         }

         bufferSource.endBatch(MagicOreSenseXray.XrayRenderTypes.XRAY_BLOCK);
      } finally {
         poseStack.popPose();
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.depthMask(true);
         RenderSystem.enableCull();
         RenderSystem.enableDepthTest();
      }
   }

   private static void spawnOreParticles(Minecraft mc, double currentRadius) {
      particleTickCounter++;
      if (particleTickCounter >= 20) {
         particleTickCounter = 0;
         List<MagicOreSenseXray.ScannedOre> visible = scannedOres.stream().filter(ore -> ore.distance() <= currentRadius).toList();
         if (!visible.isEmpty() && mc.level != null) {
            ThreadLocalRandom rng = ThreadLocalRandom.current();
            int count = Math.min(visible.size(), 2);

            for (int i = 0; i < count; i++) {
               BlockPos pos = visible.get(rng.nextInt(visible.size())).pos();
               int face = rng.nextInt(6);
               double x;
               double y;
               double z;
               switch (face) {
                  case 0:
                     x = pos.getX() + rng.nextDouble();
                     y = pos.getY() + 1.01;
                     z = pos.getZ() + rng.nextDouble();
                     break;
                  case 1:
                     x = pos.getX() + rng.nextDouble();
                     y = pos.getY() - 0.01;
                     z = pos.getZ() + rng.nextDouble();
                     break;
                  case 2:
                     x = pos.getX() - 0.01;
                     y = pos.getY() + rng.nextDouble();
                     z = pos.getZ() + rng.nextDouble();
                     break;
                  case 3:
                     x = pos.getX() + 1.01;
                     y = pos.getY() + rng.nextDouble();
                     z = pos.getZ() + rng.nextDouble();
                     break;
                  case 4:
                     x = pos.getX() + rng.nextDouble();
                     y = pos.getY() + rng.nextDouble();
                     z = pos.getZ() - 0.01;
                     break;
                  default:
                     x = pos.getX() + rng.nextDouble();
                     y = pos.getY() + rng.nextDouble();
                     z = pos.getZ() + 1.01;
               }

               mc.level.addParticle(ParticleTypes.PORTAL, x, y, z, 0.0, 0.05, 0.0);
            }
         }
      }
   }

   private static void rebuildScan(Player player, BlockPos origin, int radius) {
      scannedOres.clear();
      int radiusSqr = radius * radius;

      for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-radius, -radius, -radius), origin.offset(radius, radius, radius))) {
         double distanceSqr = pos.distSqr(origin);
         if (!(distanceSqr > radiusSqr) && isMagicOre(player.level().getBlockState(pos))) {
            scannedOres.add(new MagicOreSenseXray.ScannedOre(pos.immutable(), Math.sqrt(distanceSqr)));
         }
      }
   }

   private static void clearScan() {
      scannedOres.clear();
      scanOrigin = null;
      scanStartTick = 0;
      scanRadius = 0;
      scanDurationTicks = 0;
   }

   private static boolean isMagicOre(BlockState state) {
      Block block = state.getBlock();
      return block != cachedMagicOre && block != cachedDeepslateMagicOre ? state.is(MAGIC_ORES_TAG) : true;
   }

   private record ScannedOre(BlockPos pos, double distance) {
   }

   private static final class XrayRenderTypes extends RenderStateShard {
      static final RenderType XRAY_BLOCK = RenderType.create(
         "tensura_tno_magic_ore_sense_block",
         DefaultVertexFormat.BLOCK,
         Mode.QUADS,
         262144,
         true,
         false,
         CompositeState.builder()
            .setShaderState(RENDERTYPE_TRANSLUCENT_SHADER)
            .setTextureState(BLOCK_SHEET_MIPPED)
            .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
            .setDepthTestState(NO_DEPTH_TEST)
            .setCullState(CULL)
            .setWriteMaskState(COLOR_WRITE)
            .setLightmapState(LIGHTMAP)
            .createCompositeState(true)
      );

      private XrayRenderTypes() {
         super("dummy", () -> {}, () -> {});
      }
   }
}
