package com.github.wal_bos.moreskills.net.client;

import com.github.wal_bos.moreskills.client.ClientPayloadHandler;
import com.github.wal_bos.moreskills.client.fx.PainSixPathsVfxClient;
import com.github.wal_bos.moreskills.client.renderer.MorphClientState;
import com.github.wal_bos.moreskills.client.vfx.PhainonAcquirementVfxClient;
import com.github.wal_bos.moreskills.net.msg.BloodfiendDataSyncPayload;
import com.github.wal_bos.moreskills.net.msg.PainSixPathsVfxPayload;
import com.github.wal_bos.moreskills.net.msg.PhainonAcquirementVfxPayload;
import java.util.List;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;

public final class ClientPayloadExecutor {
   private ClientPayloadExecutor() {
   }

   public static void openFeldwayRewardScreen(List<ResourceLocation> skills) {
      if (FMLEnvironment.dist == Dist.CLIENT) {
         ClientPayloadHandler.openFeldwayRewardScreen(skills);
      }
   }

   public static void phainonAcquirementVfx(PhainonAcquirementVfxPayload payload) {
      if (FMLEnvironment.dist == Dist.CLIENT) {
         PhainonAcquirementVfxClient.handle(payload);
      }
   }

   public static void handleMorphSync(UUID playerId, String entityId) {
      MorphClientState.set(playerId, entityId);
   }

   public static void painSixPathsVfx(PainSixPathsVfxPayload payload) {
      if (FMLEnvironment.dist == Dist.CLIENT) {
         PainSixPathsVfxClient.spawn(payload.x(), payload.y(), payload.z(), payload.radius(), payload.kind(), payload.power(), payload.seed());
      }
   }

   public static void bloodfiendDataSync(BloodfiendDataSyncPayload payload) {
      if (FMLEnvironment.dist == Dist.CLIENT) {
         ClientPayloadHandler.bloodfiendDataSync(payload);
      }
   }
}
