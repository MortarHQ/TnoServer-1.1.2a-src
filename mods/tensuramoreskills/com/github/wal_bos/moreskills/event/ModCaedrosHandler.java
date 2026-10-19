package com.github.wal_bos.moreskills.event;

import com.github.wal_bos.moreskills.ability.skill.ultimate.CaedrosGodOfConquestSkill;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;

public final class ModCaedrosHandler {
   private ModCaedrosHandler() {
   }

   public static void init() {
      NeoForge.EVENT_BUS.addListener(ModCaedrosHandler::onLivingDeath);
      NeoForge.EVENT_BUS.addListener(ModCaedrosHandler::onItemToss);
      NeoForge.EVENT_BUS.addListener(ModCaedrosHandler::onServerTick);
   }

   private static void onLivingDeath(LivingDeathEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         CaedrosGodOfConquestSkill.onPlayerDeath(player);
      }

      if (event.getEntity() instanceof LivingEntity living && living.level() instanceof ServerLevel level) {
         CaedrosGodOfConquestSkill.onLivingKilledByCaedros(living, event.getSource());
         CaedrosGodOfConquestSkill.onLivingDeathConstruct(level, living);
      }
   }

   private static void onItemToss(ItemTossEvent event) {
      if (event.getPlayer() instanceof ServerPlayer player) {
         ItemEntity var3 = event.getEntity();
         CaedrosGodOfConquestSkill.onItemToss(player, var3);
      }
   }

   private static void onServerTick(Post event) {
      MinecraftServer server = event.getServer();
      CaedrosGodOfConquestSkill.tickGlobal(server);
   }
}
