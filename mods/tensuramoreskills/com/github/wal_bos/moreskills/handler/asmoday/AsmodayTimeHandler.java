package com.github.wal_bos.moreskills.handler.asmoday;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent.Post;

public final class AsmodayTimeHandler {
   private static boolean initialized;

   private AsmodayTimeHandler() {
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.register(AsmodayTimeHandler.class);
         AsmodaySpatialAbsorption.init();
      }
   }

   @SubscribeEvent
   public static void onLevelTick(Post event) {
      if (event.getLevel() instanceof ServerLevel level) {
         AsmodaySpatialCubeShot.tick(level);
         AsmodayFallingCubeJudgement.tick(level);
         AsmodaySpatialAbsorption.tickLevel(level);
         AsmodayPrisonRealmManager.tick(level);
         AsmodayAvatarBeam.tick(level);
      }
   }

   @SubscribeEvent
   public static void onLivingDeath(LivingDeathEvent event) {
      if (event.getEntity() instanceof ServerPlayer player && AsmodayPrisonRealmManager.isPrisoned(player.getUUID())) {
         AsmodayPrisonRealmManager.protectPrisoner(player);
         event.setCanceled(true);
      } else {
         AsmodayPrisonRealmManager.releaseOwnedBy(event.getEntity(), true, "The prison lost its master.");
      }
   }

   @SubscribeEvent
   public static void onItemToss(ItemTossEvent event) {
      AsmodayPrisonRealmManager.trackDroppedCube(event.getEntity());
   }

   @SubscribeEvent
   public static void onPlayerLogin(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         AsmodayPrisonRealmManager.onPlayerLogin(player);
      }
   }
}
