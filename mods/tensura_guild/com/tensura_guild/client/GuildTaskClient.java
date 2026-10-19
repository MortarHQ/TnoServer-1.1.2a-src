package com.tensura_guild.client;

import com.tensura_guild.client.screen.GuildTaskBoardScreen;
import com.tensura_guild.network.AcceptGuildTaskResultPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public final class GuildTaskClient {
   private GuildTaskClient() {
   }

   public static void openCarriedTask(ResourceLocation taskId) {
      Minecraft.getInstance().setScreen(GuildTaskBoardScreen.openCarriedTask(taskId));
   }

   public static void handleAcceptResult(AcceptGuildTaskResultPayload payload) {
      if (Minecraft.getInstance().screen instanceof GuildTaskBoardScreen screen) {
         screen.handleAcceptTaskResult(payload.taskId(), payload.accepted());
      }
   }
}
