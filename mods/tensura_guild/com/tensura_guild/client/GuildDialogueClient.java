package com.tensura_guild.client;

import com.tensura_guild.client.screen.GuildTaskBoardScreen;
import com.tensura_guild.client.screen.YuukiDialogueScreen;
import com.tensura_guild.network.OpenDialoguePayload;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public class GuildDialogueClient {
   private static final ResourceLocation GUILD_MENU_DIALOGUE = ResourceLocation.fromNamespaceAndPath("tensura_guild", "yuuki_guild_menu");

   public static void open(OpenDialoguePayload payload) {
      if (GUILD_MENU_DIALOGUE.equals(payload.id())) {
         Minecraft.getInstance()
            .setScreen(YuukiDialogueScreen.guildMenu(payload.id(), payload.entityId(), payload.dialogue(), () -> new GuildTaskBoardScreen(payload.entityId())));
      } else {
         Minecraft.getInstance().setScreen(new YuukiDialogueScreen(payload.id(), payload.entityId(), payload.dialogue()));
      }
   }
}
