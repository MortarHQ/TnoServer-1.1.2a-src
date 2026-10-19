package com.tensura_tno.event;

import com.tensura_tno.ability.skill.SpiritSummonSkill;
import com.tensura_tno.entity.spirit.FoxSpiritEntity;
import com.tensura_tno.network.ContractLittleFoxPackets;
import com.tensura_tno.registry.TensuraTNOSkills;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.SkillEvents.RemoveSkillEvent;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.handler.DeathHandler;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.world.TensuraGameRules;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = "tensura_tno")
public final class ContractLittleFoxEvents {
   private ContractLittleFoxEvents() {
   }

   public static void registerArchitecturyEvents() {
      SkillEvents.REMOVE_SKILL.register((RemoveSkillEvent)(skillInstance, owner, forgetMessage) -> {
         if (owner instanceof ServerPlayer player) {
            if (skillInstance != null && skillInstance.getSkill() != null) {
               if (skillInstance.getSkill() == TensuraTNOSkills.CONTRACT_LITTLE_FOX.get()) {
                  ContractLittleFoxPackets.onSkillReset(player);
               }

               if (skillInstance.getSkill() == TensuraTNOSkills.SPIRIT_SUMMON.get()) {
                  SpiritSummonSkill.SpiritSummonPockets.clearAll(player);
                  SpiritSummonSkill.removeAllActiveSummons(player);
               }

               return EventResult.pass();
            } else {
               return EventResult.pass();
            }
         } else {
            return EventResult.pass();
         }
      });
   }

   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onLivingDeath(LivingDeathEvent event) {
      LivingEntity victim = event.getEntity();
      if (!victim.level().isClientSide()) {
         if (victim instanceof ServerPlayer player) {
            ContractLittleFoxPackets.onPlayerDeath(player);
         } else if (victim instanceof FoxSpiritEntity fox) {
            ContractLittleFoxPackets.onFoxSpiritDeath(fox);
         } else {
            if (event.getSource().getEntity() instanceof FoxSpiritEntity killerFox) {
               tryFiftyFiftyEPSplit(killerFox, victim);
            }
         }
      }
   }

   private static void tryFiftyFiftyEPSplit(FoxSpiritEntity fox, LivingEntity victim) {
      if (!victim.getType().is(TensuraEntityTags.EP_DROP_EXCLUDED)) {
         if (!TensuraStorages.getExistenceFrom(victim).isSkippingEPDrop()) {
            Optional<UUID> summonerOpt = fox.getSummonerUUID();
            if (!summonerOpt.isEmpty()) {
               if (fox.level() instanceof ServerLevel level) {
                  ServerPlayer owner = level.getServer().getPlayerList().getPlayer(summonerOpt.get());
                  if (owner != null) {
                     double base = EnergyHelper.getEPGain(victim, fox, false);
                     if (!(base <= 0.0)) {
                        int multiplier = victim.level().getGameRules().getInt(TensuraGameRules.EP_GAIN_MULTIPLIER);
                        double full = EnergyHelper.getEPGain(victim, fox, true) * multiplier;
                        double half = full * 0.5;
                        TensuraStorages.getExistenceFrom(victim).setSkippingEPDrop(true);
                        DeathHandler.gainEPEntity(owner, victim, half);
                        DeathHandler.gainEPEntity(fox, victim, half);
                     }
                  }
               }
            }
         }
      }
   }
}
