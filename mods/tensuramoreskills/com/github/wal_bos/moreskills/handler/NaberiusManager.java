package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.ability.skill.shades.NaberiusSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Pre;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent.Finish;

public final class NaberiusManager {
   public static final String TAG_LIFE_BYPASS_OWNER = "tms_naberius_life_bypass_owner";
   public static final String TAG_LIFE_BYPASS_UNTIL = "tms_naberius_life_bypass_until";
   public static final String TAG_BESTOWED_LIFE = "tms_naberius_bestowed_life";
   public static final String TAG_BESTOWED_LIFE_OWNER = "tms_naberius_bestowed_life_owner";
   public static final String TAG_FRUIT_OF_LIFE = "tms_fruit_of_life";
   public static final String TAG_FRUIT_INVULN_UNTIL = "tms_fruit_invuln_until";

   private NaberiusManager() {
   }

   public static void init() {
      NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, NaberiusManager::onDeath);
      NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, NaberiusManager::onDamagePre);
      NeoForge.EVENT_BUS.addListener(NaberiusManager::onItemFinished);
   }

   private static void onDeath(LivingDeathEvent event) {
      LivingEntity victim = event.getEntity();
      if (!tryConsumeBestowedLife(event, victim)) {
         if (NaberiusSkill.isNaberiusSummon(victim)) {
            UUID ownerId = NaberiusSkill.getNaberiusOwnerUUID(victim);
            if (ownerId != null && victim.level().getServer() != null) {
               ServerPlayer owner = victim.level().getServer().getPlayerList().getPlayer(ownerId);
               if (owner != null) {
                  LivingEntity aggro = null;
                  if (event.getSource().getEntity() instanceof LivingEntity living && living != owner) {
                     aggro = living;
                  } else if (victim.getLastHurtByMob() != null) {
                     aggro = victim.getLastHurtByMob();
                  }

                  Skills ownerSkills = SkillAPI.getSkillsFrom(owner);
                  if (ownerSkills != null) {
                     for (ManasSkillInstance instance : ownerSkills.getLearnedSkills()) {
                        if (instance.getSkill() instanceof NaberiusSkill naberius && instance.isToggled()) {
                           naberius.reviveKilledSummon(owner, victim.getUUID(), aggro);
                           break;
                        }
                     }
                  }
               }
            }
         } else {
            LivingEntity killer = null;
            if (event.getSource().getEntity() instanceof LivingEntity living) {
               killer = living;
            }

            if (!(killer instanceof ServerPlayer) && victim.getKillCredit() instanceof ServerPlayer creditPlayer) {
               killer = creditPlayer;
            }

            if (!(killer instanceof ServerPlayer) && victim.getPersistentData().hasUUID("tms_naberius_life_bypass_owner")) {
               UUID id = victim.getPersistentData().getUUID("tms_naberius_life_bypass_owner");
               if (victim.level().getServer() != null) {
                  ServerPlayer marked = victim.level().getServer().getPlayerList().getPlayer(id);
                  if (marked != null) {
                     killer = marked;
                  }
               }
            }

            clearLifeBypass(victim);
            if (killer instanceof ServerPlayer player) {
               Skills skills = SkillAPI.getSkillsFrom(player);
               if (skills != null) {
                  for (ManasSkillInstance instance : skills.getLearnedSkills()) {
                     if (instance.getSkill() instanceof NaberiusSkill naberius && instance.isToggled()) {
                        naberius.captureKill(player, victim);
                        break;
                     }
                  }
               }
            }
         }
      }
   }

   private static void onDamagePre(Pre event) {
      LivingEntity damaged = event.getEntity();
      Entity sourceEntity = event.getSource().getEntity();
      if (isFruitInvulnerable(damaged, event)) {
         event.setNewDamage(0.0F);
      } else {
         boolean naberiusBypass = isLifeBypass(damaged) || event.getSource().is(DamageTypes.GENERIC_KILL);
         if (damaged instanceof ServerPlayer player) {
            Skills skills = SkillAPI.getSkillsFrom(player);
            if (skills != null) {
               for (ManasSkillInstance instance : skills.getLearnedSkills()) {
                  if (instance.getSkill() instanceof NaberiusSkill naberius) {
                     if (!naberiusBypass && naberius.tryNegateLethal(instance, player, event.getNewDamage())) {
                        event.setNewDamage(0.0F);
                     }

                     if (instance.isToggled() && sourceEntity instanceof LivingEntity attacker && attacker != player) {
                        naberius.commandActiveSummons(player, attacker);
                     }
                     break;
                  }
               }
            }
         }

         if (sourceEntity instanceof ServerPlayer attacker && damaged != attacker && damaged.isAlive()) {
            Skills skills = SkillAPI.getSkillsFrom(attacker);
            if (skills == null) {
               return;
            }

            for (ManasSkillInstance instance : skills.getLearnedSkills()) {
               if (instance.getSkill() instanceof NaberiusSkill naberius && instance.isToggled()) {
                  naberius.commandActiveSummons(attacker, damaged);
                  break;
               }
            }
         }
      }
   }

   private static void onItemFinished(Finish event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         ItemStack stack = event.getItem();
         if (isFruitOfLife(stack)) {
            player.getPersistentData().putLong("tms_fruit_invuln_until", player.level().getGameTime() + 600L);
            player.displayClientMessage(Component.literal("Fruit of Life rejects all harm for 30 seconds.").withStyle(ChatFormatting.RED), true);
         }
      }
   }

   private static boolean tryConsumeBestowedLife(LivingDeathEvent event, LivingEntity victim) {
      if (!victim.getPersistentData().getBoolean("tms_naberius_bestowed_life")) {
         return false;
      }

      if (event.getSource().is(DamageTypes.GENERIC_KILL)) {
         return false;
      }

      if (event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         return false;
      }

      victim.getPersistentData().remove("tms_naberius_bestowed_life");
      victim.getPersistentData().remove("tms_naberius_bestowed_life_owner");
      victim.setHealth(Math.max(1.0F, victim.getMaxHealth() * 0.35F));
      victim.invulnerableTime = Math.max(victim.invulnerableTime, 100);
      victim.removeAllEffects();
      victim.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.INSTANT_REGENERATION), 160, 2, false, false, true));
      victim.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.HAKI_COAT), 160, 2, false, false, true));
      event.setCanceled(true);
      if (victim instanceof ServerPlayer player) {
         player.displayClientMessage(Component.literal("Naberius spends your bestowed life.").withStyle(ChatFormatting.RED), true);
      }

      return true;
   }

   public static void markLifeBypass(LivingEntity target, ServerPlayer owner, int ticks) {
      target.getPersistentData().putUUID("tms_naberius_life_bypass_owner", owner.getUUID());
      target.getPersistentData().putLong("tms_naberius_life_bypass_until", target.level().getGameTime() + ticks);
      target.setLastHurtByMob(owner);
   }

   public static boolean isLifeBypass(LivingEntity target) {
      CompoundTag tag = target.getPersistentData();
      return tag.hasUUID("tms_naberius_life_bypass_owner") && tag.getLong("tms_naberius_life_bypass_until") >= target.level().getGameTime();
   }

   public static void clearLifeBypass(LivingEntity target) {
      target.getPersistentData().remove("tms_naberius_life_bypass_owner");
      target.getPersistentData().remove("tms_naberius_life_bypass_until");
   }

   public static boolean isFruitOfLife(ItemStack stack) {
      CustomData data = (CustomData)stack.get(DataComponents.CUSTOM_DATA);
      return data == null ? false : data.copyTag().getBoolean("tms_fruit_of_life");
   }

   private static boolean isFruitInvulnerable(LivingEntity entity, Pre event) {
      if (entity.level().isClientSide()) {
         return false;
      } else if (event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         return false;
      } else {
         long until = entity.getPersistentData().getLong("tms_fruit_invuln_until");
         if (until <= entity.level().getGameTime()) {
            entity.getPersistentData().remove("tms_fruit_invuln_until");
            return false;
         } else {
            return true;
         }
      }
   }
}
