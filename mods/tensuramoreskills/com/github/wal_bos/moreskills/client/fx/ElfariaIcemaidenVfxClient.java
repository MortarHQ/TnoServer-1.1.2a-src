package com.github.wal_bos.moreskills.client.fx;

import com.github.wal_bos.moreskills.net.msg.ElfariaIcemaidenVfxPayload;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.common.NeoForge;

public final class ElfariaIcemaidenVfxClient {
   private static boolean initialized;

   private ElfariaIcemaidenVfxClient() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(ElfariaIcemaidenVfxClient::onClientTick);
         NeoForge.EVENT_BUS.addListener(ElfariaIcemaidenVfxClient::render);
      }
   }

   public static void spawn(ElfariaIcemaidenVfxPayload payload) {
      init();
      Vec3 pos = new Vec3(payload.x(), payload.y(), payload.z());
      Vec3 pos2 = new Vec3(payload.x2(), payload.y2(), payload.z2());
      switch (payload.vfxType()) {
         case 0:
            int life = Math.max(8, (int)payload.power());
            ElfariaMyrdasFridolieteVfxClient.spawnBarrier(pos, payload.radius(), life, payload.seed(), payload.main());
            if (payload.main() && payload.power() >= 30.0F && payload.power() <= 40.0F) {
               ElfariaPassiveIceStepsVfxClient.spawnReviveAura(pos, Math.max(1.8F, payload.radius() * 0.28F), payload.seed() ^ 85891601L);
            }
            break;
         case 1:
            ElfariaMyrdasFridolieteVfxClient.spawnBreak(pos, payload.radius(), payload.seed());
            ElfariaOpIceShineVfxClient.spawnStorm(pos, payload.radius() * 1.4F, payload.seed() ^ 725738L, 1100, 2, false);
            break;
         case 2:
            ElfariaStellasNateaVfxClient.spawnCharge(pos, payload.radius(), Math.max(1, (int)payload.power()), payload.seed(), payload.main());
            break;
         case 3:
            ElfariaStellasNateaVfxClient.spawnStrike(pos, pos2, payload.radius(), payload.seed(), payload.main());
            break;
         case 4:
            ElfariaFruzelCardeneiaVfxClient.spawnCathedral(pos, pos2, payload.radius(), Math.max(40, (int)payload.power()), payload.seed(), payload.main());
            break;
         case 5:
            ElfariaFruzelCardeneiaVfxClient.spawnSpike(pos, payload.radius(), payload.seed());
            break;
         case 6:
            if (payload.main()) {
               ElfariaGlaciaLastAlbisVfxClient.spawnPhoenix(pos, pos2, payload.radius(), payload.seed(), true);
               ElfariaOpIceShineVfxClient.spawnFlight(pos, pos2, payload.radius(), payload.seed() ^ 2433802522L, true);
            }
            break;
         case 7:
            ElfariaGlaciaLastAlbisVfxClient.spawnBurst(pos, payload.radius(), payload.seed());
            ElfariaOpIceShineVfxClient.spawnStorm(pos, payload.radius() * 3.2F, payload.seed() ^ 737367L, 3200, 6, true);
            break;
         case 8:
            ElfariaPassiveIceStepsVfxClient.spawnStep(pos, pos2, payload.radius(), payload.seed());
            ElfariaOpIceShineVfxClient.spawnStorm(pos, payload.radius() * 1.25F, payload.seed() ^ 30300143L, 650, 1, false);
            break;
         case 9:
            ElfariaArsWeissBloomVfxClient.spawnBloom(pos, payload.radius(), payload.seed());
            ElfariaOpIceShineVfxClient.spawnStorm(pos, payload.radius() * 1.8F, payload.seed() ^ 2597L, 1500, 3, false);
            break;
         case 10:
            ElfariaMyrdasFridolieteVfxClient.spawnFieldFreeze(pos, payload.radius(), payload.seed());
            ElfariaOpIceShineVfxClient.spawnStorm(pos, payload.radius() * 1.2F, payload.seed() ^ 990749L, 900, 2, false);
            break;
         default:
            ElfariaPassiveIceStepsVfxClient.spawnStep(pos, Vec3.ZERO, payload.radius(), payload.seed());
      }
   }

   public static void onClientTick(Post event) {
      ElfariaMyrdasFridolieteVfxClient.tick();
      ElfariaStellasNateaVfxClient.tick();
      ElfariaFruzelCardeneiaVfxClient.tick();
      ElfariaGlaciaLastAlbisVfxClient.tick();
      ElfariaPassiveIceStepsVfxClient.tick();
      ElfariaArsWeissBloomVfxClient.tick();
      ElfariaOpIceShineVfxClient.tick();
   }

   public static void render(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_TRANSLUCENT_BLOCKS) {
         PoseStack poseStack = event.getPoseStack();
         float partial = event.getPartialTick().getGameTimeDeltaPartialTick(false);
         ElfariaMyrdasFridolieteVfxClient.render(poseStack, partial);
         ElfariaFruzelCardeneiaVfxClient.render(poseStack, partial);
         ElfariaGlaciaLastAlbisVfxClient.render(poseStack, partial);
         ElfariaStellasNateaVfxClient.render(poseStack, partial);
         ElfariaPassiveIceStepsVfxClient.render(poseStack, partial);
         ElfariaArsWeissBloomVfxClient.render(poseStack, partial);
         ElfariaOpIceShineVfxClient.render(poseStack, partial);
      }
   }
}
