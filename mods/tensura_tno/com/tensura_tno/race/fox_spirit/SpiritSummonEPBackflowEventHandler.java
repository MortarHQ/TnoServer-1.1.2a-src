package com.tensura_tno.race.fox_spirit;

import com.tensura_tno.ability.skill.SpiritSummonSkill;
import com.tensura_tno.entity.spirit.FoxSpiritEntity;
import com.tensura_tno.registry.TensuraTNOSkills;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.AbilitySlot;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.EnergyHelper.GainType;
import io.github.manasmods.tensura.world.TensuraGameRules;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber(modid = "tensura_tno")
public class SpiritSummonEPBackflowEventHandler {
   @SubscribeEvent(priority = EventPriority.HIGHEST)
   public static void onLivingDeath(LivingDeathEvent event) {
      LivingEntity victim = event.getEntity();
      if (!victim.level().isClientSide()) {
         if (event.getSource().getEntity() instanceof Mob killerMob) {
            if (!(killerMob instanceof FoxSpiritEntity)) {
               ServerPlayer owner = findSpiritSummonOwner(killerMob);
               if (owner != null) {
                  tryEPBackflow(killerMob, victim, owner);
               }
            }
         }
      }
   }

   private static ServerPlayer findSpiritSummonOwner(Mob mob) {
      try {
         IExistence ex = TensuraStorages.getExistenceFrom(mob);
         UUID summonerUUID = ex.getSummoner();
         if (summonerUUID == null) {
            return null;
         }

         AbilitySlot summonedAbility = ex.getSummonedAbility();
         if (summonedAbility == null) {
            return null;
         }

         if (summonedAbility.getSkill() != TensuraTNOSkills.SPIRIT_SUMMON.get()) {
            return null;
         }

         if (mob.level() instanceof ServerLevel sl && sl.getEntity(summonerUUID) instanceof ServerPlayer player) {
            return player;
         }
      } catch (Exception var7) {
      }

      return null;
   }

   private static void tryEPBackflow(Mob summon, LivingEntity victim, ServerPlayer owner) {
      if (!victim.getType().is(TensuraEntityTags.EP_DROP_EXCLUDED)) {
         try {
            if (TensuraStorages.getExistenceFrom(victim).isSkippingEPDrop()) {
               return;
            }
         } catch (Exception ignored) {
            return;
         }

         double epGain = EnergyHelper.getEPGain(victim, summon, true);
         if (!(epGain <= 0.0)) {
            int multiplier = victim.level().getGameRules().getInt(TensuraGameRules.EP_GAIN_MULTIPLIER);
            double full = epGain * multiplier;

            try {
               TensuraStorages.getExistenceFrom(victim).setSkippingEPDrop(true);
            } catch (Exception var15) {
            }

            double baseEPBefore = EnergyHelper.getBaseMaxEP(summon);
            double halfEP = full / 2.0;
            EnergyHelper.gainAura(summon, halfEP, GainType.MAX);
            EnergyHelper.gainMagicule(summon, halfEP, GainType.MAX);
            if (baseEPBefore > 0.0) {
               AttributeInstance hpAttr = summon.getAttribute(Attributes.MAX_HEALTH);
               if (hpAttr != null) {
                  double hpBoost = hpAttr.getBaseValue() * (full / baseEPBefore);
                  hpAttr.setBaseValue(hpAttr.getBaseValue() + hpBoost);
                  summon.setHealth(summon.getMaxHealth());
               }
            }

            ResourceLocation entityId = BuiltInRegistries.ENTITY_TYPE.getKey(summon.getType());
            if (entityId != null) {
               SpiritSummonSkill.SpiritSummonPockets.addBonusEP(owner, entityId.toString(), full);
               syncMaxBonusEPToRaceTag(owner, entityId.toString());
            }
         }
      }
   }

   private static void syncMaxBonusEPToRaceTag(ServerPlayer owner, String entityId) {
      double currentEP = SpiritSummonSkill.SpiritSummonPockets.getBonusEP(owner, entityId);
      Optional<ManasRaceInstance> raceOpt = RaceAPI.getRaceFrom(owner).getRace();
      raceOpt.ifPresent(instance -> SummonMaxEPRequirement.updateMaxSummonBonusEP(instance, currentEP));
   }
}
