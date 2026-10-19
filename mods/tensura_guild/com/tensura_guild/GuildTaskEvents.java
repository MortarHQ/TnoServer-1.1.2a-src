package com.tensura_guild;

import com.tensura_guild.config.GuildConfig;
import com.tensura_guild.data.DwarfTaskService;
import com.tensura_guild.data.GuildLevelData;
import com.tensura_guild.data.GuildTaskService;
import io.github.manasmods.tensura.entity.human.DwarfEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

public final class GuildTaskEvents {
   private GuildTaskEvents() {
   }

   public static void onLivingDeath(LivingDeathEvent event) {
      if (event.getEntity() instanceof DwarfEntity dwarf && event.getEntity().level().getServer() != null) {
         DwarfTaskService.handleDwarfDeath(dwarf, event.getEntity().level().getServer());
      }

      Entity killer = event.getSource().getEntity();
      if (!GuildTaskService.handleTrackedTaskEntityDeath(event.getEntity(), killer)) {
         if (killer instanceof ServerPlayer player) {
            ResourceLocation entityType = BuiltInRegistries.ENTITY_TYPE.getKey(event.getEntity().getType());
            GuildTaskService.addKill(player, entityType, event.getEntity().blockPosition());
         }
      }
   }

   public static void onPlayerTick(Post event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         DwarfTaskService.tickDialogueLock(player);
         if (player.tickCount % 20 == 0) {
            GuildTaskService.tickActiveTasks(player);
            DwarfTaskService.tick(player);
         }
      }
   }

   public static void onServerTick(net.neoforged.neoforge.event.tick.ServerTickEvent.Post event) {
      int tickCount = event.getServer().getTickCount();
      if (tickCount > 0) {
         if (tickCount % 20 == 0) {
            GuildTaskService.expireAvailableTasks(event.getServer());
            DwarfTaskService.tickTaskOffers(event.getServer());
         }

         int baseInterval = GuildConfig.commissionAutoUpdateTicks();
         if (baseInterval > 0) {
            for (String rank : GuildLevelData.ranksUpToLevel(GuildLevelData.MAX_LEVEL)) {
               int interval = GuildConfig.commissionAutoUpdateTicksForRank(rank);
               if (interval > 0 && tickCount % interval == 0) {
                  GuildTaskService.autoUpdateTasks(event.getServer(), rank);
               }
            }
         }
      }
   }
}
