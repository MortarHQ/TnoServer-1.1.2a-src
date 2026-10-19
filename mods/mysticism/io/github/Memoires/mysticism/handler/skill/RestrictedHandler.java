package io.github.Memoires.mysticism.handler.skill;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent.LivingHurt;
import io.github.Memoires.mysticism.data.MysticismSkillTags;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.SkillEvents.UnlockSkillEvent;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.advancement.ExistenceGainTrigger;
import io.github.manasmods.tensura.advancement.TensuraAdvancements;
import io.github.manasmods.tensura.advancement.TensuraAdvancements.Basic;
import io.github.manasmods.tensura.config.entity.AttributeConfig;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.entity.ai.behaviour.TensuraBehaviourHelper;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraEntityEvents.AttributeBaseValueChangedEvent;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillLearningEvent;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillPlunderEvent;
import io.github.manasmods.tensura.menu.NamingMenu;
import io.github.manasmods.tensura.network.c2s.RequestNamingMenuPacket;
import io.github.manasmods.tensura.network.c2s.RequestNamingMenuPacket.NamingType;
import io.github.manasmods.tensura.race.TensuraRace;
import io.github.manasmods.tensura.registry.advancement.TensuraCriteriaTriggers;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.world.TensuraGameRules;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class RestrictedHandler {
   public static void init() {
      TensuraSkillEvents.SKILL_PLUNDER
         .register(
            (SkillPlunderEvent)(target, user, b, skill) -> {
               if (target instanceof LivingEntity) {
                  if (((ManasSkill)skill.get()).equals(UniqueMysticismSkills.RESTRICTED.get())) {
                     if (user instanceof Player p && p.getAbilities().invulnerable) {
                        return EventResult.pass();
                     } else {
                        if (user != null) {
                           if (user instanceof Player p) {
                              p.displayClientMessage(
                                 Component.translatable(
                                       "mysticism.skill.restricted.cannot_copy_restricted",
                                       new Object[]{((ManasSkill)Objects.requireNonNull((ManasSkill)skill.get())).getChatDisplayName(true)}
                                    )
                                    .withStyle(ChatFormatting.RED),
                                 false
                              );
                           }

                           user.level()
                              .playSound(null, user.getX(), user.getY(), user.getZ(), TensuraSoundEvents.GENERIC_CAST_FAIL, SoundSource.PLAYERS, 1.0F, 1.0F);
                        }

                        return EventResult.interruptFalse();
                     }
                  } else {
                     return EventResult.pass();
                  }
               } else {
                  return EventResult.pass();
               }
            }
         );
      TensuraSkillEvents.SKILL_LEARNING
         .register(
            (SkillLearningEvent)(manasSkillInstance, livingEntity, amount, a, changeable) -> {
               if (!SkillUtils.hasSkillFully(livingEntity, (ManasSkill)UniqueMysticismSkills.RESTRICTED.get())) {
                  return EventResult.pass();
               } else if (manasSkillInstance.is(MysticismSkillTags.RESTRICTED_LEARNABLE)) {
                  manasSkillInstance.getOrCreateTag().putBoolean("NoMagiculeCost", true);
                  return EventResult.pass();
               } else if (manasSkillInstance.getOrCreateTag().getBoolean("GiftedInstance")) {
                  return EventResult.pass();
               } else if (manasSkillInstance.getOrCreateTag().getBoolean("NoMagiculeCost")) {
                  return EventResult.pass();
               } else if (livingEntity instanceof Player p && p.getAbilities().instabuild) {
                  return EventResult.pass();
               } else {
                  Optional<ManasSkillInstance> skill = SkillAPI.getSkillsFrom(livingEntity).getSkill((ManasSkill)UniqueMysticismSkills.RESTRICTED.get());
                  if (skill.isPresent()) {
                     if (livingEntity instanceof Player player) {
                        player.displayClientMessage(
                           Component.translatable(
                                 "mysticism.skill.restricted.unable_to_learn_skills",
                                 new Object[]{manasSkillInstance.getChatDisplayName(true), skill.get().getChatDisplayName(true)}
                              )
                              .withStyle(ChatFormatting.RED),
                           false
                        );
                     }

                     livingEntity.level()
                        .playSound(
                           null,
                           livingEntity.getX(),
                           livingEntity.getY(),
                           livingEntity.getZ(),
                           (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(),
                           SoundSource.PLAYERS,
                           1.0F,
                           1.0F
                        );
                     return EventResult.interruptFalse();
                  } else {
                     return EventResult.pass();
                  }
               }
            }
         );
      EntityEvents.LIVING_ON_BEING_DAMAGED
         .register(
            (LivingHurt)(target, source, amount) -> {
               if (source.getEntity() instanceof LivingEntity livingAttacker) {
                  return !livingAttacker.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.IMBALANCED))
                     ? EventResult.pass()
                     : EventResult.interruptFalse();
               } else {
                  return EventResult.pass();
               }
            }
         );
      SkillEvents.UNLOCK_SKILL
         .register(
            (UnlockSkillEvent)(manasSkillInstance, livingEntity, changeable) -> {
               if (!SkillUtils.hasSkillFully(livingEntity, (ManasSkill)UniqueMysticismSkills.RESTRICTED.get())) {
                  return EventResult.pass();
               }

               if (!manasSkillInstance.is(MysticismSkillTags.RESTRICTED_LEARNABLE)) {
                  if (manasSkillInstance.getOrCreateTag().getBoolean("GiftedInstance")) {
                     return EventResult.pass();
                  } else if (manasSkillInstance.getOrCreateTag().getBoolean("NoMagiculeCost")) {
                     return EventResult.pass();
                  } else if (livingEntity instanceof Player p && p.getAbilities().instabuild) {
                     return EventResult.pass();
                  } else {
                     Optional<ManasSkillInstance> skill = SkillAPI.getSkillsFrom(livingEntity).getSkill((ManasSkill)UniqueMysticismSkills.RESTRICTED.get());
                     if (skill.isPresent() && manasSkillInstance.getMastery() >= 0.0) {
                        if (livingEntity instanceof Player player) {
                           player.displayClientMessage(
                              Component.translatable(
                                    "mysticism.skill.restricted.unable_to_learn_skills",
                                    new Object[]{manasSkillInstance.getChatDisplayName(true), skill.get().getChatDisplayName(true)}
                                 )
                                 .withStyle(ChatFormatting.RED),
                              false
                           );
                        }

                        livingEntity.level()
                           .playSound(
                              null,
                              livingEntity.getX(),
                              livingEntity.getY(),
                              livingEntity.getZ(),
                              (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(),
                              SoundSource.PLAYERS,
                              1.0F,
                              1.0F
                           );
                        return EventResult.interruptFalse();
                     } else {
                        return EventResult.pass();
                     }
                  }
               } else if (!(manasSkillInstance.getMastery() < 0.0) && !(livingEntity instanceof Player player && player.hasInfiniteMaterials())) {
                  manasSkillInstance.getOrCreateTag().putBoolean("NoMagiculeCost", true);
                  manasSkillInstance.markDirty();
                  if (manasSkillInstance.getSkill() instanceof TensuraSkill tensuraSkill) {
                     double cost = tensuraSkill.getAcquiringMagiculeCost(manasSkillInstance)
                        * livingEntity.level().getGameRules().getInt(TensuraGameRules.MP_SKILL_COST)
                        / 100.0;
                     AttributeInstance aura = livingEntity.getAttribute(TensuraAttributes.MAX_AURA);
                     if (aura == null) {
                        return EventResult.pass();
                     }

                     if (aura.getValue() <= cost) {
                        livingEntity.sendSystemMessage(
                           Component.translatable("mysticism.skill.acquire_failed.ap", new Object[]{tensuraSkill.getChatDisplayName(true)})
                              .withStyle(ChatFormatting.RED)
                        );
                        return EventResult.interruptFalse();
                     }

                     aura.setBaseValue(aura.getBaseValue() - cost);
                     IExistence existence = TensuraStorages.getExistenceFrom(livingEntity);
                     existence.setAura(existence.getAura() - cost);
                     existence.markDirty();
                  }

                  return EventResult.pass();
               } else {
                  return EventResult.pass();
               }
            }
         );
      TensuraEntityEvents.ATTRIBUTE_BASE_CHANGE_EVENT
         .register(
            (AttributeBaseValueChangedEvent)(entity, instance, oldValue, newValue) -> {
               if (!SkillUtils.hasSkillFully(entity, (ManasSkill)UniqueMysticismSkills.RESTRICTED.get())) {
                  return EventResult.pass();
               } else if (oldValue >= newValue) {
                  return EventResult.pass();
               } else if (instance.getAttribute().value() instanceof RangedAttribute attribute && oldValue == attribute.getDefaultValue()) {
                  return EventResult.pass();
               } else {
                  Level level = entity.level();
                  if (level.isClientSide()) {
                     return EventResult.pass();
                  }

                  if (!instance.getAttribute().equals(TensuraAttributes.MAX_AURA)) {
                     return EventResult.pass();
                  }

                  IExistence existence = TensuraStorages.getExistenceFrom(entity);
                  int humanKill = TensuraRace.BASE_CONFIG.massNamingHuman;
                  if (entity instanceof ServerPlayer player) {
                     if (!instance.getAttribute().equals(TensuraAttributes.MAX_AURA)) {
                        return EventResult.pass();
                     }

                     if (existence.isNameable()
                        && (
                           player.getStats().getValue(Stats.CUSTOM.get(Stats.RAID_WIN)) >= TensuraRace.BASE_CONFIG.massNamingRaid
                              || existence.getHumanKill() >= humanKill
                        )) {
                        TensuraAdvancements.grant(player, Basic.INFAMY_FAMOUS);
                        if (existence.getName() == null) {
                           RequestNamingMenuPacket.name(player, null, NamingType.LOW, player.getName().getString());
                           ((ExistenceGainTrigger)TensuraCriteriaTriggers.EXISTENCE_GAIN.get()).trigger(player);
                           return EventResult.interruptTrue();
                        }
                     }

                     applyDemonLordSeedAndSkills(player, existence, newValue);
                     ((ExistenceGainTrigger)TensuraCriteriaTriggers.EXISTENCE_GAIN.get()).trigger(player);
                     return EventResult.pass();
                  } else {
                     if (oldValue != 0.0) {
                        addStatOnEPGain(entity, existence, newValue - oldValue);
                     }

                     if (existence.getHumanKill() >= humanKill && existence.getName() == null) {
                        RequestNamingMenuPacket.name(entity, null, NamingType.LOW, NamingMenu.getRandomName(level.getRandom()));
                     }

                     return EventResult.pass();
                  }
               }
            }
         );
   }

   private static void addStatOnEPGain(LivingEntity entity, IExistence existence, double difference) {
      double gainedEP = existence.getGainedEP() + difference;
      double points = (int)gainedEP / 10000;
      if (points > 0.0 && !entity.getType().is(TensuraEntityTags.EP_INITIATE_EXCLUDED)) {
         AttributeConfig config = (AttributeConfig)ConfigRegistry.getConfig(AttributeConfig.class);
         existence.setGainedEP(gainedEP - points * 10000.0);
         AttributeInstance armor = entity.getAttribute(Attributes.ARMOR);
         if (armor != null) {
            armor.setBaseValue(armor.getBaseValue() + points * config.armorGain);
         }

         AttributeInstance attack = entity.getAttribute(Attributes.ATTACK_DAMAGE);
         if (attack != null) {
            attack.setBaseValue(attack.getBaseValue() + points * config.attackGain);
         }

         AttributeInstance HP = entity.getAttribute(Attributes.MAX_HEALTH);
         if (HP != null) {
            HP.setBaseValue(HP.getBaseValue() + points * config.HPGain);
         }

         AttributeInstance SHP = entity.getAttribute(TensuraAttributes.MAX_SPIRITUAL_HEALTH);
         if (SHP != null) {
            SHP.setBaseValue(SHP.getBaseValue() + points * config.SHPGain);
         }

         entity.heal((float)points * 10.0F);
      } else {
         existence.setGainedEP(gainedEP);
         entity.heal(TensuraBehaviourHelper.CONFIG.successKillHeal);
      }
   }

   public static void applyDemonLordSeedAndSkills(Player player, IExistence existence, double newValue) {
      double newEP = newValue;
      if (existence.getAlignment().isCanBecomeDemonLord()
         && !existence.isTrueHero()
         && !existence.isTrueDemonLord()
         && !existence.isDemonLordSeed()
         && newEP >= player.level().getGameRules().getInt(TensuraGameRules.DEMON_LORD_SEED)) {
         existence.setDemonLordSeed(true);
         existence.markDirty();
         player.sendSystemMessage(Component.translatable("tensura.evolve.demon_lord.seed").setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)));
         player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundSource.PLAYERS, 1.0F, 1.0F);
      }

      for (ManasSkill manasSkill : SkillAPI.getSkillRegistry()) {
         if (manasSkill instanceof TensuraSkill skill
            && SkillAPI.getSkillsFrom(player).getSkill(skill).isEmpty()
            && skill.checkAcquiringRequirement(player, newEP)) {
            SkillHelper.learnSkill(player, skill.createLearningInstance(player));
         }
      }
   }
}
