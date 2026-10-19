package io.github.Memoires.mysticism.handler;

import dev.architectury.event.EventResult;
import io.github.Memoires.mysticism.ability.skill.extra.DarknessManipulationSkill;
import io.github.Memoires.mysticism.ability.skill.extra.IceManipulationSkill;
import io.github.Memoires.mysticism.ability.skill.extra.LightManipulationSkill;
import io.github.Memoires.mysticism.ability.skill.intrinsic.MagisteelBodySkill;
import io.github.Memoires.mysticism.ability.skill.unique.MalleableSkill;
import io.github.Memoires.mysticism.ability.subclass.IBookStorage;
import io.github.Memoires.mysticism.command.MysticismCommands;
import io.github.Memoires.mysticism.config.MysticismGeneralConfig;
import io.github.Memoires.mysticism.config.race.DragonoidConfig;
import io.github.Memoires.mysticism.race.dragonoid.DragonoidRace;
import io.github.Memoires.mysticism.race.dragonoid.TrueDragonoidRace;
import io.github.Memoires.mysticism.registry.attributes.MysticismAttributes;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.Memoires.mysticism.storage.MysticismStorages;
import io.github.Memoires.mysticism.storage.soulEnergy.ISoulEnergy;
import io.github.Memoires.mysticism.world.MysticismGameRules;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.RaceEvents;
import io.github.manasmods.manascore.race.api.Races;
import io.github.manasmods.manascore.race.api.RaceEvents.SetRaceEvent;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.SkillEvents.RemoveSkillEvent;
import io.github.manasmods.manascore.skill.api.SkillEvents.SkillActivationEvent;
import io.github.manasmods.manascore.skill.api.SkillEvents.SkillMasteryEvent;
import io.github.manasmods.manascore.skill.api.SkillEvents.SkillUpdateCooldownEvent;
import io.github.manasmods.manascore.skill.api.SkillEvents.UnlockSkillEvent;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.resist.ResistSkill;
import io.github.manasmods.tensura.data.TensuraSkillTags;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraEntityEvents.AttributeBaseValueChangedEvent;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillLearningEvent;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillPlunderEvent;
import io.github.manasmods.tensura.menu.ReincarnationMenu;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class AbilityHandler {
   public static void init() {
      SkillEvents.SKILL_MASTERY.register((SkillMasteryEvent)(instance, entity, newMastery) -> {
         Double mastery = (Double)newMastery.get();
         if (mastery != null) {
            if (instance.is(TensuraSkillTags.EARTH_SKILLS) && SkillUtils.isSkillToggled(entity, (ManasSkill)UniqueMysticismSkills.MALLEABLE.get())) {
               newMastery.set(mastery + MalleableSkill.CONFIG.masteryPoint);
            }

            if (instance.getSkill() instanceof ResistSkill && SkillUtils.isSkillToggled(entity, (ManasSkill)UniqueMysticismSkills.MALLEABLE.get())) {
               newMastery.set(mastery + MalleableSkill.CONFIG.masteryPoint);
            }

            LightManipulationSkill.learnLightManipulation(instance, entity);
            DarknessManipulationSkill.learnDarknessManipulation(instance, entity);
            IceManipulationSkill.learnIceManipulation(instance, entity);
         }

         return EventResult.pass();
      });
      SkillEvents.REMOVE_SKILL
         .register(
            (RemoveSkillEvent)(instance, entity, message) -> {
               ManasSkill skill = instance.getSkill();
               if (skill instanceof IBookStorage storage && entity instanceof Player player) {
                  storage.dropAllItems(instance, player);
               }

               if (instance.getOrCreateTag().getBoolean("costSE")) {
                  ISoulEnergy soulEnergy = MysticismStorages.getSoulEnergyFrom(entity);
                  if (soulEnergy.getSoulEnergy() == entity.getAttributeValue(MysticismAttributes.MAX_SOUL_ENERGY)) {
                     return EventResult.pass();
                  }

                  if (!(entity instanceof Player player && player.hasInfiniteMaterials())
                     && instance.getSkill() instanceof TensuraSkill tensuraSkill
                     && entity instanceof Player player) {
                     double seCost = tensuraSkill.getAcquiringMagiculeCost(instance)
                        * ((MysticismGeneralConfig)ConfigRegistry.getConfig(MysticismGeneralConfig.class)).General.seCostMultiplier;
                     player.displayClientMessage(
                        Component.translatable(
                              "mysticism.soulEnergy.skill_gain_energy", new Object[]{skill.getName(), MysticismCommands.getShortenedNumber(seCost)}
                           )
                           .withStyle(ChatFormatting.RED),
                        false
                     );
                     soulEnergy.increaseSoulEnergy(seCost);
                     soulEnergy.markDirty();
                  }
               }

               return EventResult.pass();
            }
         );
      SkillEvents.UNLOCK_SKILL
         .register(
            (UnlockSkillEvent)(instance, entity, unlockMessage) -> {
               if (instance.isSubInstance()) {
                  return EventResult.pass();
               } else if (!(!instance.isTemporarySkill() && !instance.getOrCreateTag().getBoolean("vicSkill") && instance.getSkill() instanceof Skill skill)) {
                  return EventResult.pass();
               } else if (!(entity instanceof Player player)) {
                  return EventResult.pass();
               } else if (instance.getOrCreateTag().getBoolean("NoSoulEnergyCost")) {
                  instance.getOrCreateTag().remove("NoSoulEnergyCost");
                  return EventResult.pass();
               } else if (player.hasInfiniteMaterials()) {
                  return EventResult.pass();
               } else if (!player.level().getGameRules().getBoolean(MysticismGameRules.UNIQUE_SE_COST)) {
                  return EventResult.pass();
               } else if (!skill.getType().equals(SkillType.UNIQUE)) {
                  return EventResult.pass();
               } else if (instance.getMastery() < 0.0) {
                  return EventResult.pass();
               } else {
                  ISoulEnergy soulEnergy = MysticismStorages.getSoulEnergyFrom(entity);
                  double seCost = skill.getAcquiringMagiculeCost(instance)
                     * ((MysticismGeneralConfig)ConfigRegistry.getConfig(MysticismGeneralConfig.class)).General.seCostMultiplier;
                  if (soulEnergy.getSoulEnergy() < seCost) {
                     player.displayClientMessage(
                        Component.translatable("mysticism.soulEnergy.no_skill", new Object[]{MysticismCommands.getShortenedNumber(seCost), skill.getName()})
                           .withStyle(ChatFormatting.RED),
                        false
                     );
                     AttributeInstance magicule = entity.getAttribute(TensuraAttributes.MAX_MAGICULE);
                     double magiculeAmount = magicule.getBaseValue();
                     Objects.requireNonNull(player.getAttribute(TensuraAttributes.MAX_MAGICULE))
                        .setBaseValue(magiculeAmount + skill.getAcquiringMagiculeCost(instance));
                     IExistence existence = TensuraStorages.getExistenceFrom(entity);
                     double mpAmount = existence.getMagicule();
                     existence.setMagicule(mpAmount + skill.getAcquiringMagiculeCost(instance));
                     existence.markDirty();
                     return EventResult.interruptFalse();
                  } else {
                     instance.getOrCreateTag().putBoolean("costSE", true);
                     player.displayClientMessage(
                        Component.translatable("mysticism.soulEnergy.lost_energy", new Object[]{skill.getName(), MysticismCommands.getShortenedNumber(seCost)})
                           .withStyle(ChatFormatting.RED),
                        false
                     );
                     soulEnergy.reduceSoulEnergy(seCost);
                     soulEnergy.markDirty();
                     return EventResult.pass();
                  }
               }
            }
         );
      TensuraEntityEvents.ATTRIBUTE_BASE_CHANGE_EVENT.register((AttributeBaseValueChangedEvent)(entity, instance, oldValue, newValue) -> {
         DragonoidConfig.Dragonoid CONFIG = ((DragonoidConfig)ConfigRegistry.getConfig(DragonoidConfig.class)).Dragonoid;
         if (!instance.getAttribute().equals(TensuraAttributes.MAX_MAGICULE)) {
            return EventResult.pass();
         }

         if (entity instanceof LivingEntity living) {
            if (entity.level().isClientSide()) {
               return EventResult.pass();
            }

            if (living instanceof Player player) {
               Races playerRace = RaceAPI.getRaceFrom(player);
               Optional<ManasRaceInstance> raceInstanceOpt = playerRace.getRace();
               if (raceInstanceOpt.isEmpty()) {
                  return EventResult.pass();
               }

               ManasRaceInstance raceInstance = raceInstanceOpt.get();
               if (!(raceInstance.getRace() instanceof DragonoidRace)) {
                  return EventResult.pass();
               }

               double MP = newValue;
               double minMP = CONFIG.minMP;
               double maxMP = CONFIG.maxMP;
               int minHP = CONFIG.dragonoidMinHP;
               int maxHP = CONFIG.dragonoidMaxHP;
               double minAttackDamage = CONFIG.dragonoidMinAttackDamage;
               double maxAttackDamage = CONFIG.dragonoidMaxAttackDamage;
               double minAttackSpeed = CONFIG.dragonoidMinAttackSpeed;
               double maxAttackSpeed = CONFIG.dragonoidMaxAttackSpeed;
               double minMovementSpeed = CONFIG.dragonoidMinMovementSpeed;
               double maxMovementSpeed = CONFIG.dragonoidMaxMovementSpeed;
               MP = Math.max(minMP, Math.min(MP, maxMP));
               double progress = (MP - minMP) / (maxMP - minMP);
               double newHealth = minHP + (maxHP - minHP) * progress;
               double newAttackDamage = minAttackDamage + (maxAttackDamage - minAttackDamage) * progress;
               double newAttackSpeed = minAttackSpeed + (maxAttackSpeed - minAttackSpeed) * progress;
               double newMovementSpeed = minMovementSpeed + (maxMovementSpeed - minMovementSpeed) * progress;
               Objects.requireNonNull(living.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(newHealth);
               Objects.requireNonNull(living.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(newAttackDamage);
               Objects.requireNonNull(living.getAttribute(Attributes.ATTACK_SPEED)).setBaseValue(newAttackSpeed);
               Objects.requireNonNull(living.getAttribute(TensuraAttributes.MAX_SPIRITUAL_HEALTH)).setBaseValue(newHealth * 6.48);
               Objects.requireNonNull(living.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(newMovementSpeed);
               return EventResult.pass();
            } else {
               return EventResult.pass();
            }
         } else {
            return EventResult.pass();
         }
      });
      SkillEvents.SKILL_UPDATE_COOLDOWN.register((SkillUpdateCooldownEvent)(manasSkillInstance, livingEntity, time, mode, changeable) -> {
         if (!livingEntity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.SEVERED_ARM))) {
            return EventResult.pass();
         }

         changeable.set(time * 2);
         return EventResult.pass();
      });
      RaceEvents.SET_RACE.register((SetRaceEvent)(instance, living, newInstance, evolution, teleportToSpawn, raceMessage) -> {
         if (instance != null && instance.getRace() instanceof DragonoidRace && !(newInstance.getRace() instanceof TrueDragonoidRace)) {
            Objects.requireNonNull(living.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(20.0);
            Objects.requireNonNull(living.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(1.0);
            Objects.requireNonNull(living.getAttribute(Attributes.ATTACK_SPEED)).setBaseValue(4.0);
            Objects.requireNonNull(living.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(0.1);
            if (living.getAttribute(TensuraAttributes.MAX_SPIRITUAL_HEALTH) != null) {
               Objects.requireNonNull(living.getAttribute(TensuraAttributes.MAX_SPIRITUAL_HEALTH)).setBaseValue(60.0);
            }

            living.setHealth((float)Math.min(living.getHealth(), living.getAttributeValue(Attributes.MAX_HEALTH)));
         }

         if (newInstance.getRace() == MysticismRaces.INSECT.get()) {
            ManasRace race = randomInsectRace();
            if (!(living instanceof Player player)) {
               return EventResult.pass();
            }

            if (race != null) {
               ReincarnationMenu.setRace(player, race, true, false);
               return EventResult.interruptFalse();
            }
         }

         return EventResult.pass();
      });
      TensuraSkillEvents.SKILL_LEARNING
         .register(
            (SkillLearningEvent)(instance, entity, mode, requirementPoint, newPoint) -> {
               Double point = (Double)newPoint.get();
               if (point != null) {
                  if (instance.is(TensuraSkillTags.EARTH_SKILLS) && SkillUtils.hasSkill(entity, (ManasSkill)UniqueMysticismSkills.MALLEABLE.get())) {
                     newPoint.set(point + MalleableSkill.CONFIG.learningPoint);
                  }

                  if (instance.getSkill() instanceof ResistSkill && SkillUtils.hasSkill(entity, (ManasSkill)UniqueMysticismSkills.MALLEABLE.get())) {
                     newPoint.set(point + MalleableSkill.CONFIG.learningPoint);
                  }

                  if (instance.is(TensuraSkillTags.BATTLEWILL)
                     && TensuraStorages.getAbilityFrom(entity).isAbilityInActivePreset((ManasSkill)UniqueMysticismSkills.CULTIVATOR.get())) {
                     newPoint.set(requirementPoint);
                  }
               }

               return EventResult.pass();
            }
         );
      SkillEvents.ACTIVATE_SKILL
         .register(
            (SkillActivationEvent)(changeable, livingEntity, keyNumber, mode) -> {
               if (livingEntity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.ETERNAL_PERMAFROST))
                  && (
                     ((ManasSkillInstance)Objects.requireNonNull((ManasSkillInstance)changeable.get())).is(TensuraSkillTags.BATTLEWILL)
                        || ((ManasSkillInstance)Objects.requireNonNull((ManasSkillInstance)changeable.get())).is(TensuraSkillTags.MAGIC)
                  )
                  && livingEntity.getRandom().nextFloat() < 0.5F) {
                  if (livingEntity instanceof Player player) {
                     player.displayClientMessage(Component.translatable("mysticism.effect.eternal_permafrost.hindered").withStyle(ChatFormatting.RED), false);
                     player.playNotifySound((SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
                     if (changeable.get() != null) {
                        ((ManasSkillInstance)Objects.requireNonNull((ManasSkillInstance)changeable.get())).setCoolDowns(5);
                     }
                  }

                  return EventResult.interruptFalse();
               } else {
                  return EventResult.pass();
               }
            }
         );
      TensuraSkillEvents.SKILL_PLUNDER
         .register(
            (SkillPlunderEvent)(target, entity, bool, changeable) -> ((ManasSkill)changeable.get()).getClass() == MagisteelBodySkill.class
               ? EventResult.interruptFalse()
               : EventResult.pass()
         );
   }

   private static ManasRace randomInsectRace() {
      List<ManasRace> insectRaces = new ArrayList<>();
      insectRaces.add((ManasRace)MysticismRaces.ANT.get());
      insectRaces.add((ManasRace)MysticismRaces.BEETLE.get());
      insectRaces.add((ManasRace)MysticismRaces.CENTIPEDE.get());
      insectRaces.add((ManasRace)MysticismRaces.MANTIS.get());
      insectRaces.add((ManasRace)MysticismRaces.SCORPION.get());
      insectRaces.add((ManasRace)MysticismRaces.WASP.get());
      return insectRaces.get(new Random().nextInt(insectRaces.size()));
   }
}
