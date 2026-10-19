package com.tensura_guild.client;

import com.tensura_guild.client.screen.DwarfTaskDialogueScreen;
import com.tensura_guild.network.OpenDwarfTaskDialoguePayload;
import net.minecraft.client.Minecraft;

public final class DwarfTaskClient {
   private DwarfTaskClient() {
   }

   public static void open(OpenDwarfTaskDialoguePayload payload) {
      Minecraft.getInstance().setScreen(new DwarfTaskDialogueScreen(payload));
   }
}
