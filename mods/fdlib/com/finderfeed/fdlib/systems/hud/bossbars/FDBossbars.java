package com.finderfeed.fdlib.systems.hud.bossbars;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingOut;
import net.neoforged.neoforge.client.event.ClientTickEvent.Pre;

@EventBusSubscriber(bus = Bus.GAME, modid = "fdlib", value = Dist.CLIENT)
public class FDBossbars {
   protected static final HashMap<UUID, FDBossBar> BOSS_BARS = new LinkedHashMap<>();

   @SubscribeEvent
   public static void tick(Pre event) {
      if (Minecraft.getInstance().level == null) {
         BOSS_BARS.clear();
      } else {
         float offs = FDBossBarsOverlay.calculateBossBarsOffset();

         for (FDBossBar bossBar : BOSS_BARS.values()) {
            bossBar.tick(offs);
            offs += bossBar.height();
         }
      }
   }

   @SubscribeEvent
   public static void onLogoff(LoggingOut event) {
      BOSS_BARS.clear();
   }

   public static void removeBossBar(UUID uuid) {
      BOSS_BARS.remove(uuid);
   }

   public static void addBossBar(FDBossBar bossBar) {
      BOSS_BARS.put(bossBar.getUUID(), bossBar);
   }

   public static FDBossBar getBossBar(UUID uuid) {
      return BOSS_BARS.get(uuid);
   }
}
