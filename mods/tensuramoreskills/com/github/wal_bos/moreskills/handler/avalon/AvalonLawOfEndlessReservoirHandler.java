package com.github.wal_bos.moreskills.handler.avalon;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.AvalonLawEndlessReservoirVfxPayload;
import com.github.wal_bos.moreskills.support.RoyalSkillSupport;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.EnergyHelper.GainType;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

final class AvalonLawOfEndlessReservoirHandler implements AvalonLawHandler {
   private static final ResourceLocation COST_REDUCTION = RoyalSkillSupport.id("avalon_endless_reservoir_cost");
   private static final Map<UUID, Map<UUID, Double>> LAST_MAGICULE = new HashMap<>();
   private static final Map<UUID, Set<UUID>> BUFFED = new HashMap<>();

   @Override
   public String name() {
      return "Law of the Endless Reservoir";
   }

   @Override
   public void sendVfx(ServerLevel level, LivingEntity owner, float radius, int durationTicks, int variant) {
      AvalonLawEndlessReservoirVfxPayload.send(level, owner, null, owner.position(), owner.position(), radius, durationTicks, variant);
   }

   @Override
   public void onActivate(ManasSkillInstance instance, LivingEntity owner) {
      LAST_MAGICULE.put(owner.getUUID(), new HashMap<>());
      BUFFED.put(owner.getUUID(), new HashSet<>());
      this.tick(instance, owner);
   }

   @Override
   public void tick(ManasSkillInstance instance, LivingEntity owner) {
      if (owner.tickCount % 10 == 0) {
         double radius = (Double)MoreSkillsConfigs.GRAND.avalon.royalLawRadius.get();
         Set<UUID> currentBuffed = BUFFED.computeIfAbsent(owner.getUUID(), ignored -> new HashSet<>());
         Set<UUID> seenAllies = new HashSet<>();

         for (LivingEntity ally : RoyalSkillSupport.alliesInRange(owner, radius)) {
            seenAllies.add(ally.getUUID());
            currentBuffed.add(ally.getUUID());
            AttributeHelper.addPermanentAttribute(ally, TensuraAttributes.MAGIC_COST_MULTIPLIER, COST_REDUCTION, -0.3, Operation.ADD_VALUE);
            ally.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.ENERGY_BLOCKADE));
            ally.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.SOUL_DRAIN));
            IExistence existence = TensuraStorages.getExistenceFrom(ally);
            if (existence.getMagicule() < EnergyHelper.getMaxMagicule(ally) * 0.2) {
               EnergyHelper.gainMagicule(ally, EnergyHelper.getMaxMagicule(ally) * 0.3, GainType.NORMAL);
            }

            if (owner.tickCount % 100 == 0) {
               RoyalSkillSupport.restoreEnergyPercent(ally, 0.15, 0.15);
            }
         }

         removeMissingBuffs(owner, currentBuffed, seenAllies);
         Map<UUID, Double> last = LAST_MAGICULE.computeIfAbsent(owner.getUUID(), ignored -> new HashMap<>());
         Set<UUID> seenEnemies = new HashSet<>();

         for (LivingEntity enemy : RoyalSkillSupport.enemiesInRange(owner, radius)) {
            seenEnemies.add(enemy.getUUID());
            double current = RoyalSkillSupport.currentMagicule(enemy);
            Double previous = last.put(enemy.getUUID(), current);
            if (previous != null && current < previous) {
               EnergyHelper.gainMagicule(owner, (previous - current) * 0.5, GainType.NORMAL);
            }
         }

         last.keySet().removeIf(uuid -> !seenEnemies.contains(uuid));
      }
   }

   private static void removeMissingBuffs(LivingEntity owner, Set<UUID> buffed, Set<UUID> seen) {
      if (owner.level() instanceof ServerLevel level) {
         for (UUID uuid : Set.copyOf(buffed)) {
            if (!seen.contains(uuid)) {
               if (level.getEntity(uuid) instanceof LivingEntity living) {
                  AttributeHelper.removeAttribute(living, TensuraAttributes.MAGIC_COST_MULTIPLIER, COST_REDUCTION);
               }

               buffed.remove(uuid);
            }
         }
      }
   }

   @Override
   public void onDeactivate(ManasSkillInstance instance, LivingEntity owner) {
      if (owner.level() instanceof ServerLevel level) {
         for (UUID uuid : BUFFED.getOrDefault(owner.getUUID(), Set.of())) {
            if (level.getEntity(uuid) instanceof LivingEntity living) {
               AttributeHelper.removeAttribute(living, TensuraAttributes.MAGIC_COST_MULTIPLIER, COST_REDUCTION);
            }
         }
      }

      AttributeHelper.removeAttribute(owner, TensuraAttributes.MAGIC_COST_MULTIPLIER, COST_REDUCTION);
      LAST_MAGICULE.remove(owner.getUUID());
      BUFFED.remove(owner.getUUID());
   }
}
