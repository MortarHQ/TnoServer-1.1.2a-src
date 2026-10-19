package com.github.wal_bos.moreskills.client;

import com.github.wal_bos.moreskills.client.screen.FeldwayRewardScreen;
import com.github.wal_bos.moreskills.handler.BloodfiendData;
import com.github.wal_bos.moreskills.net.msg.BloodfiendDataSyncPayload;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public final class ClientPayloadHandler {
   private ClientPayloadHandler() {
   }

   public static void openFeldwayRewardScreen(List<ResourceLocation> skills) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft != null) {
         minecraft.setScreen(new FeldwayRewardScreen(skills));
      }
   }

   public static void bloodfiendDataSync(BloodfiendDataSyncPayload payload) {
      BloodfiendClientData.update(
         payload.bloodfiend(), payload.blood(), payload.maxBlood(), payload.generation(), payload.totalBloodDrained(), payload.totalThrallsTurned()
      );
      Minecraft minecraft = Minecraft.getInstance();
      Player player = minecraft.player;
      if (player != null) {
         BloodfiendData.setClientSyncedProgress(player, payload.totalBloodDrained(), payload.totalThrallsTurned());
         BloodfiendData.setClientSyncedGeneration(player, payload.generation());
         BloodfiendData.setClientSyncedBlood(player, payload.blood());
      }
   }
}
