package dev.foxboss.entity;

import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.manascore.skill.impl.TickingSkill;
import io.github.manasmods.tensura.ability.skill.unique.WrathSkill;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.IAbility;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

final class MilimWrathController {
   private static final int BREEDER = 0;
   private static final int ENRAGE = 1;
   private static final int BREEDER_TICKS = 100;
   private static final int ENRAGE_TICKS = 60;

   static void tick(MilimEntity milim) {
      SkillStorage skills = SkillAPI.getSkillsFrom(milim);
      ManasSkillInstance wrath = (ManasSkillInstance)skills.getSkill((ManasSkill)UniqueSkills.WRATH.get()).orElse(null);
      ManasSkillInstance satanael = MilimSatanaelCompat.findOrUpgrade(milim, skills, wrath);
      if (satanael != null) {
         if (wrath != null) {
            release(skills, wrath, held(skills, wrath));
            toggle(milim, skills, wrath, false);
         }

         tickSatanael(milim, skills, satanael);
      } else if (wrath != null) {
         TickingSkill held = held(skills, wrath);
         LivingEntity target = milim.getTarget();
         boolean fighting = fighting(milim, target);
         if (fighting && wrath.canInteractSkill(milim)) {
            boolean inRange = milim.getBoundingBox().inflate(WrathSkill.CONFIG.enrageRadius).intersects(target.getBoundingBox());
            boolean passiveBreeder = wrath.isMastered(milim) && wrath.isToggled();
            if (held == null) {
               if (!passiveBreeder || inRange) {
                  start(milim, skills, wrath, passiveBreeder ? 1 : 0);
               }
            } else if (held.getMode() == 0 && inRange && held.getDuration() > 100) {
               release(skills, wrath, held);
               if (!start(milim, skills, wrath, 1)) {
                  start(milim, skills, wrath, 0);
               }
            } else if (held.getMode() == 1 && (!inRange || held.getDuration() >= 60)) {
               release(skills, wrath, held);
               if (!passiveBreeder) {
                  start(milim, skills, wrath, 0);
               }
            }
         } else {
            release(skills, wrath, held);
         }
      }
   }

   private static void tickSatanael(MilimEntity milim, SkillStorage skills, ManasSkillInstance skill) {
      LivingEntity target = milim.getTarget();
      boolean fighting = fighting(milim, target) && skill.canInteractSkill(milim);
      toggle(milim, skills, skill, fighting);
      milim.tickSatanaelCombat(skills, skill, fighting);
      TickingSkill channel = held(skills, skill);
      if (!fighting) {
         release(skills, skill, channel);
         MilimSatanaelCompat.tickBetweenNativePulses(milim, skills, skill);
      } else {
         if (milim.tickCount % 20 == 0
            && MilimSatanaelCompat.stampedeReady(skill, milim)
            && milim.distanceToSqr(target) <= 1024.0
            && (
               milim.getHealth() <= milim.getMaxHealth() * 0.6F
                  || target.getHealth() >= Math.max(200.0, milim.getAttributeBaseValue(Attributes.ATTACK_DAMAGE) * 4.0)
            )) {
            release(skills, skill, channel);
            channel = null;
            if (start(milim, skills, skill, 1)) {
               release(skills, skill, held(skills, skill));
            }
         }

         if (channel == null || channel.getMode() == 0 && channel.getDuration() < 60 && MilimSatanaelCompat.safeDominationGroup(milim)) {
            if (channel == null
               && milim.tickCount % 20 == 0
               && milim.level().getGameTime() >= milim.getPersistentData().getLong("FoxBossSatanaelNextDomination")
               && MilimSatanaelCompat.safeDominationGroup(milim)
               && start(milim, skills, skill, 0)) {
               MilimSatanaelCompat.guardDominationChannel(skills, skill);
            }
         } else {
            release(skills, skill, channel);
            milim.getPersistentData().putLong("FoxBossSatanaelNextDomination", milim.level().getGameTime() + 80L);
         }

         MilimSatanaelCompat.tickBetweenNativePulses(milim, skills, skill);
      }
   }

   private static boolean fighting(MilimEntity milim, LivingEntity target) {
      return milim.isAlive()
         && !milim.isDemo()
         && !milim.isNoAi()
         && !milim.isDebugAnimation()
         && !milim.isOrderedToSit()
         && target != null
         && target.level() == milim.level()
         && target.isAlive()
         && !target.isSpectator()
         && milim.canAttack(target)
         && !(target instanceof Player player && player.getAbilities().invulnerable);
   }

   private static TickingSkill held(SkillStorage skills, ManasSkillInstance skill) {
      return skills.getHeldSkills().stream().filter(held -> held.getSkill() == skill.getSkill()).findFirst().orElse(null);
   }

   private static void toggle(MilimEntity milim, SkillStorage skills, ManasSkillInstance skill, boolean enabled) {
      if (skill.isToggled() != enabled) {
         if (!enabled || skill.canBeToggled(milim) && skill.canInteractSkill(milim)) {
            skill.setToggled(enabled);
            if (enabled) {
               skill.onToggleOn(milim);
            } else {
               skill.onToggleOff(milim);
            }

            skills.checkAndMarkDirty(skill);
         }
      }
   }

   private static boolean start(MilimEntity milim, SkillStorage skills, ManasSkillInstance wrath, int mode) {
      IAbility abilities = TensuraStorages.getAbilityFrom(milim);
      if (!abilities.isAbilityInActivePreset(mode, wrath.getSkill(), mode)) {
         abilities.setAbilitySlot(mode, wrath.getSkill(), mode);
         abilities.markDirty();
      }

      return skills.startHoldSkill(wrath, mode, mode);
   }

   private static void release(SkillStorage skills, ManasSkillInstance wrath, TickingSkill held) {
      if (held != null) {
         skills.handleSkillRelease(wrath, held.getKeyNumber(), held.getMode(), false);
      }
   }

   private MilimWrathController() {
   }
}
