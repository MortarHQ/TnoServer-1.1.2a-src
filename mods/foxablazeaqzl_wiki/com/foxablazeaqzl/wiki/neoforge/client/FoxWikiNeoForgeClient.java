package com.foxablazeaqzl.wiki.neoforge.client;

import com.foxablazeaqzl.wiki.client.FoxWikiClientEvents;
import com.foxablazeaqzl.wiki.client.FoxWikiCommands;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.common.NeoForge;

public final class FoxWikiNeoForgeClient {
   private FoxWikiNeoForgeClient() {
   }

   public static void register() {
      NeoForge.EVENT_BUS.addListener(FoxWikiNeoForgeClient::onClientTick);
      NeoForge.EVENT_BUS.addListener(FoxWikiNeoForgeClient::onRegisterClientCommands);
   }

   private static void onClientTick(Post event) {
      FoxWikiClientEvents.onClientTick(Minecraft.getInstance());
   }

   private static void onRegisterClientCommands(RegisterClientCommandsEvent event) {
      FoxWikiCommands.register(event.getDispatcher(), (source, message) -> source.sendSuccess(() -> message, false), CommandSourceStack::sendFailure);
   }
}
