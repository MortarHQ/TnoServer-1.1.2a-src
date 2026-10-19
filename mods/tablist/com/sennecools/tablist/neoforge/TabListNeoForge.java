package com.sennecools.tablist.neoforge;

import com.sennecools.tablist.TabListUpdater;
import com.sennecools.tablist.TabListVariables;
import com.sennecools.tablist.config.TabListConfig;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.ServerChatEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.TabListNameFormat;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;

@Mod("tablist")
public class TabListNeoForge {
   private final TabListUpdater updater;

   public TabListNeoForge() {
      TabListConfig.load();
      this.updater = new TabListUpdater();
      NeoForge.EVENT_BUS.register(this);
   }

   @SubscribeEvent
   public void onServerTick(Post event) {
      this.updater.onServerTick(event.getServer());
   }

   @SubscribeEvent
   public void onServerChat(ServerChatEvent event) {
      this.updater.onPlayerChat(event.getPlayer());
   }

   @SubscribeEvent
   public void onTabListNameFormat(TabListNameFormat event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         String displayName = TabListVariables.resolveDisplayName(player);
         event.setDisplayName(TabListVariables.parseColoredText(displayName));
      }
   }

   @SubscribeEvent
   public void onPlayerLogout(PlayerLoggedOutEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         this.updater.onPlayerLogout(player);
      }
   }

   @SubscribeEvent
   public void onRegisterCommands(RegisterCommandsEvent event) {
      event.getDispatcher().register(this.updater.buildReloadCommand());
   }
}
