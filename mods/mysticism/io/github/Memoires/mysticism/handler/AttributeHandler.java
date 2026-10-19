package io.github.Memoires.mysticism.handler;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent.LivingHurt;
import io.github.Memoires.mysticism.ability.MysticSkill;
import io.github.Memoires.mysticism.race.elemental.LesserElementalRace;
import io.github.Memoires.mysticism.registry.attributes.MysticismAttributes;
import io.github.Memoires.mysticism.storage.MysticismStorages;
import io.github.Memoires.mysticism.storage.soulEnergy.ISoulEnergy;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.Races;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.SkillEvents.UnlockSkillEvent;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.event.TensuraEntityEvents.AwakeningEvent;
import io.github.manasmods.tensura.event.TensuraEntityEvents.NamingEvent;
import io.github.manasmods.tensura.storage.Alignment;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class AttributeHandler {
   public static void init() {
      EntityEvents.LIVING_PRE_DAMAGED
         .register(
            (LivingHurt)(entity, source, amount) -> {
               if (!(source.tensura$getResistanceBypassLevel() >= 1.0F)
                  && source.getEntity() instanceof LivingEntity attacker
                  && TensuraDamageHelper.isCold(source)
                  && attacker.getAttributeValue(MysticismAttributes.COLD_RESIST_DEGRADATION) >= 1.0) {
                  source.tensura$setResistanceBypassLevel(Math.max(source.tensura$getResistanceBypassLevel(), 1.0F));
               }

               return EventResult.pass();
            }
         );
      SkillEvents.UNLOCK_SKILL
         .register(
            (UnlockSkillEvent)(instance, entity, unlockMessage) -> {
               if (entity instanceof Player) {
                  if (instance.getMastery() < 0.0) {
                     return EventResult.pass();
                  }

                  if (instance.getSkill() instanceof MysticSkill && !instance.isTemporarySkill()) {
                     CompoundTag tag = instance.getTag();
                     if (tag != null && tag.getBoolean("NoSoulEnergyCost")) {
                        tag.remove("NoSoulEnergyCost");
                        if (tag.isEmpty()) {
                           instance.setTag(null);
                        }
                     } else if (!entity.hasInfiniteMaterials() && instance.getSkill() instanceof MysticSkill skill) {
                        Optional<ManasRaceInstance> optional = RaceAPI.getRaceFrom(entity).getRace();
                        if (optional.isEmpty() || !optional.get().isIntrinsicSkill(entity, skill)) {
                           double cost = skill.getAcquiringSoulEnergyCost(instance);
                           ISoulEnergy soulEnergy = MysticismStorages.getSoulEnergyFrom(entity);
                           if (soulEnergy == null) {
                              return EventResult.pass();
                           }

                           if (soulEnergy.getSoulEnergy() <= cost) {
                              ((Player)entity)
                                 .displayClientMessage(
                                    Component.translatable("mysticism.skill.acquire_failed.soul_energy", new Object[]{skill.getChatDisplayName(true)})
                                       .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)),
                                    true
                                 );
                              return EventResult.interruptFalse();
                           }

                           soulEnergy.setSoulEnergy(soulEnergy.getSoulEnergy() - cost);
                           soulEnergy.markDirty();
                        }
                     }
                  }
               }

               return EventResult.pass();
            }
         );
      TensuraEntityEvents.AWAKENING_EVENT
         .register(
            (AwakeningEvent)(entity, bool) -> {
               if (entity instanceof Player player) {
                  ISoulEnergy soulEnergyStorage = MysticismStorages.getSoulEnergyFrom(player);
                  double currentSoulEnergy = soulEnergyStorage.getSoulEnergy();
                  double maxSoulEnergy = player.getAttributeValue(MysticismAttributes.MAX_SOUL_ENERGY);
                  int newSoulEnergy = ThreadLocalRandom.current().nextInt(500000, 1000000);
                  double finalMaxSoulEnergy = newSoulEnergy + maxSoulEnergy;
                  double finalCurrentSoulEnergy = newSoulEnergy + currentSoulEnergy;
                  Objects.requireNonNull(player.getAttribute(MysticismAttributes.MAX_SOUL_ENERGY)).setBaseValue(finalMaxSoulEnergy);
                  soulEnergyStorage.setSoulEnergy(finalCurrentSoulEnergy);
                  soulEnergyStorage.markDirty();
                  player.sendSystemMessage(
                     Component.translatable("mysticism.soulEnergy.acquire_awakening", new Object[]{getShortenedNumber(newSoulEnergy)})
                        .setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD))
                  );
               }

               return EventResult.pass();
            }
         );
      TensuraEntityEvents.NAMING_EVENT
         .register(
            (NamingEvent)(target, namer, epGain, cost, namingType, name) -> {
               if (target instanceof Player player) {
                  ISoulEnergy soulEnergyStorage = MysticismStorages.getSoulEnergyFrom(player);
                  if (namer != null) {
                     IExistence ownerExistence = TensuraStorages.getExistenceFrom(namer);
                     if (!namer.hasInfiniteMaterials()) {
                        if (EnergyHelper.getBaseMaxEP(namer) <= (Double)cost.get()) {
                           return EventResult.pass();
                        }

                        if (ownerExistence.getAlignment().equals(Alignment.DEFAULT)) {
                           if (ownerExistence.getEP() < (Double)cost.get()) {
                              return EventResult.pass();
                           }
                        } else if (ownerExistence.getMagicule() < (Double)cost.get()) {
                           return EventResult.pass();
                        }
                     }

                     IExistence targetExistence = TensuraStorages.getExistenceFrom(target);
                     if (ownerExistence != null && ownerExistence.getAlignment() == Alignment.MAJIN) {
                        Races races = RaceAPI.getRaceFrom(target);
                        Optional<ManasRaceInstance> optional = races.getRace();
                        if (optional.isPresent()) {
                           ManasRaceInstance race = optional.get();
                           ManasRace targetRace = race.getRace();
                           if (targetRace instanceof LesserElementalRace) {
                              targetExistence.setAlignment(Alignment.MAJIN);
                           }
                        }
                     }

                     if (!soulEnergyStorage.getNamingBoost()) {
                        double targetMaxSoulEnergy = player.getAttributeValue(MysticismAttributes.MAX_SOUL_ENERGY);
                        double namerMaxSoulEnergy = namer.getAttributeValue(MysticismAttributes.MAX_SOUL_ENERGY);
                        if (targetMaxSoulEnergy < namerMaxSoulEnergy) {
                           double newTargetSoulEnergy = namerMaxSoulEnergy - targetMaxSoulEnergy;
                           if (newTargetSoulEnergy > 1000000.0) {
                              newTargetSoulEnergy = 1000000.0;
                           }

                           double currentSoulEnergy = soulEnergyStorage.getSoulEnergy();
                           double finalNewMaxSoulEnergy = targetMaxSoulEnergy + newTargetSoulEnergy;
                           double finalNewCurrentSoulEnergy = newTargetSoulEnergy + currentSoulEnergy;
                           Objects.requireNonNull(player.getAttribute(MysticismAttributes.MAX_SOUL_ENERGY)).setBaseValue(finalNewMaxSoulEnergy);
                           soulEnergyStorage.setSoulEnergy(finalNewCurrentSoulEnergy);
                           soulEnergyStorage.setNamingBoost(true);
                           soulEnergyStorage.markDirty();
                           player.sendSystemMessage(
                              Component.translatable("mysticism.soulEnergy.acquire_named_by", new Object[]{getShortenedNumber(newTargetSoulEnergy)})
                                 .setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD))
                           );
                           return EventResult.pass();
                        }
                     }
                  } else {
                     double targetMaxSoulEnergy = player.getAttributeValue(MysticismAttributes.MAX_SOUL_ENERGY);
                     double currentSoulEnergy = soulEnergyStorage.getSoulEnergy();
                     int newSoulEnergy = ThreadLocalRandom.current().nextInt(100000, 250000);
                     double finalMaxSoulEnergy = newSoulEnergy + targetMaxSoulEnergy;
                     double finalCurrentSoulEnergy = newSoulEnergy + currentSoulEnergy;
                     Objects.requireNonNull(player.getAttribute(MysticismAttributes.MAX_SOUL_ENERGY)).setBaseValue(finalMaxSoulEnergy);
                     soulEnergyStorage.setSoulEnergy(finalCurrentSoulEnergy);
                     soulEnergyStorage.markDirty();
                     player.sendSystemMessage(
                        Component.translatable("mysticism.soulEnergy.acquire_named", new Object[]{getShortenedNumber(newSoulEnergy)})
                           .setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD))
                     );
                  }
               }

               return EventResult.pass();
            }
         );
   }

   public static int getWeightedSoulEnergy() {
      double rand = ThreadLocalRandom.current().nextDouble();
      int soulEnergy;
      if (rand < 0.65) {
         soulEnergy = ThreadLocalRandom.current().nextInt(500000, 1000001);
      } else if (rand < 0.9) {
         soulEnergy = ThreadLocalRandom.current().nextInt(1000000, 2000001);
      } else if (rand < 0.96) {
         soulEnergy = ThreadLocalRandom.current().nextInt(2000000, 3000001);
      } else if (rand < 0.98) {
         soulEnergy = ThreadLocalRandom.current().nextInt(3000000, 4000001);
      } else if (rand < 0.99) {
         soulEnergy = ThreadLocalRandom.current().nextInt(4000000, 5000001);
      } else if (rand < 0.995) {
         soulEnergy = ThreadLocalRandom.current().nextInt(5000000, 6000001);
      } else if (rand < 0.998) {
         soulEnergy = ThreadLocalRandom.current().nextInt(6000000, 6500001);
      } else {
         soulEnergy = ThreadLocalRandom.current().nextInt(6500000, 7000001);
      }

      return Math.round(soulEnergy / 100.0F) * 100;
   }

   public static Component getShortenedNumber(double value) {
      if (value < 1000000.0) {
         return Component.literal(new DecimalFormat("#").format(value));
      }

      DecimalFormat decimal = new DecimalFormat("#.##");
      return value < 1.0E9
         ? Component.translatable("tensura.main_menu.million_index", new Object[]{decimal.format(value / 1000000.0)})
         : Component.translatable("tensura.main_menu.billion_index", new Object[]{decimal.format(value / 1.0E9)});
   }
}
