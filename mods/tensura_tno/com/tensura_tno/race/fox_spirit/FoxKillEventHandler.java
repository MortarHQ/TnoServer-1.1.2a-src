package com.tensura_tno.race.fox_spirit;

import com.tensura_tno.compat.stextras.STExtrasKillCredit;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = "tensura_tno")
public class FoxKillEventHandler {
   @SubscribeEvent
   public static void onLivingDeath(LivingDeathEvent event) {
      LivingEntity victim = event.getEntity();
      if (!victim.level().isClientSide()) {
         if (!(victim instanceof Player)) {
            if (event.getSource().getEntity() instanceof LivingEntity killer) {
               if (victim instanceof Mob) {
                  if (isHostileOrNeutral((Mob)victim)) {
                     IExistence killerExistence = TensuraStorages.getExistenceFrom(killer);
                     UUID ownerUUID = killerExistence.getSummoner();
                     if (ownerUUID != null) {
                        Player owner = victim.level().getPlayerByUUID(ownerUUID);
                        if (owner != null) {
                           Optional<ManasRaceInstance> raceOpt = RaceAPI.getRaceFrom(owner).getRace();
                           raceOpt.ifPresent(FoxKillRequirement::incrementSummonKills);
                           if (owner instanceof ServerPlayer sp && victim.getMaxHealth() >= 100.0F) {
                              ResourceLocation entityId = BuiltInRegistries.ENTITY_TYPE.getKey(victim.getType());
                              STExtrasKillCredit.creditKill(sp, entityId, victim);
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static boolean isHostileOrNeutral(Mob mob) {
      return mob instanceof Enemy ? true : !isPassiveAnimal(mob);
   }

   private static boolean isPassiveAnimal(Mob mob) {
      return !(mob instanceof Enemy) && mob.getTarget() == null;
   }
}
