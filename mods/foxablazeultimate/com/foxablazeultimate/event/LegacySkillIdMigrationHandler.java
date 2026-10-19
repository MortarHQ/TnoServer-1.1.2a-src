package com.foxablazeultimate.event;

import com.foxablazeultimate.compat.stextras.STExtrasCompat;
import com.foxablazeultimate.registry.LegacySkillIds;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.player.ITensuraPlayer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@EventBusSubscriber(modid = "foxablazeultimate")
public final class LegacySkillIdMigrationHandler {
   private LegacySkillIdMigrationHandler() {
   }

   @SubscribeEvent
   public static void onServerStarting(ServerStartingEvent event) {
      LegacySkillIds.migrateUniqueOwnership(TensuraStorages.getUniqueStorageFrom(event.getServer().overworld()));
   }

   public static void migratePlayerLocks(ServerPlayer player) {
      ITensuraPlayer data = TensuraStorages.getPlayerDataFrom(player);
      if (data.getLockedSkills().contains(LegacySkillIds.BEELZEBUB)) {
         if (!data.getLockedSkills().contains(LegacySkillIds.BEELZEBUTH)) {
            data.addLockedSkill(LegacySkillIds.BEELZEBUTH);
         }

         data.removeLockedSkill(LegacySkillIds.BEELZEBUB);
         data.markDirty();
      }

      STExtrasCompat.migrateSkillId(player, LegacySkillIds.BEELZEBUB, LegacySkillIds.BEELZEBUTH);
   }
}
