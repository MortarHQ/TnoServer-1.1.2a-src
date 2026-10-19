package cc.sighs.oelib.neoforge.event;

import cc.sighs.oelib.event.EventBus;
import cc.sighs.oelib.event.events.ClientTickEvent;
import cc.sighs.oelib.event.events.LevelTickEvent;
import cc.sighs.oelib.event.events.ServerTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Pre;

public final class TickBridge {
   private TickBridge() {
   }

   public static void initServer() {
      NeoForge.EVENT_BUS.addListener(TickBridge::onServerTickPre);
      NeoForge.EVENT_BUS.addListener(TickBridge::onServerTickPost);
      NeoForge.EVENT_BUS.addListener(TickBridge::onLevelTickPre);
      NeoForge.EVENT_BUS.addListener(TickBridge::onLevelTickPost);
   }

   public static void initClient() {
      NeoForge.EVENT_BUS.addListener(TickBridge::onClientTickPre);
      NeoForge.EVENT_BUS.addListener(TickBridge::onClientTickPost);
   }

   private static void onServerTickPre(Pre e) {
      EventBus.post(new ServerTickEvent.Pre(e::hasTime, e.getServer()));
   }

   private static void onServerTickPost(Post e) {
      EventBus.post(new ServerTickEvent.Post(e::hasTime, e.getServer()));
   }

   private static void onLevelTickPre(net.neoforged.neoforge.event.tick.LevelTickEvent.Pre e) {
      EventBus.post(new LevelTickEvent.Pre(e::hasTime, e.getLevel()));
   }

   private static void onLevelTickPost(net.neoforged.neoforge.event.tick.LevelTickEvent.Post e) {
      EventBus.post(new LevelTickEvent.Post(e::hasTime, e.getLevel()));
   }

   private static void onClientTickPre(net.neoforged.neoforge.client.event.ClientTickEvent.Pre e) {
      EventBus.post(new ClientTickEvent.Pre());
   }

   private static void onClientTickPost(net.neoforged.neoforge.client.event.ClientTickEvent.Post e) {
      EventBus.post(new ClientTickEvent.Post());
   }
}
