package dev.foxboss.entity;

import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.SkillEvents.SkillPostTickEvent;
import io.github.manasmods.manascore.skill.api.SkillEvents.SkillTickEvent;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.manascore.skill.impl.TickingSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.ModList;
import org.slf4j.LoggerFactory;

final class MilimSatanaelCompat {
   static final ResourceLocation SATANAEL = ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "satanael");
   private static final String GRANTED = "FoxBossSatanaelGranted";
   private static boolean settingsUnavailable;

   static ManasSkillInstance findOrUpgrade(MilimEntity milim, SkillStorage skills, ManasSkillInstance wrath) {
      ManasSkillInstance existing = (ManasSkillInstance)skills.getSkill(SATANAEL).orElse(null);
      if (existing != null) {
         milim.getPersistentData().putBoolean("FoxBossSatanaelGranted", true);
         return existing;
      }

      if (wrath != null && !milim.getPersistentData().getBoolean("FoxBossSatanaelGranted") && milim.isAlive() && !milim.isDemo() && !milim.isDebugAnimation()) {
         ManasSkill registered = (ManasSkill)SkillAPI.getSkillRegistry().get(SATANAEL);
         if (registered == null) {
            return null;
         }

         if (!skills.learnSkill(registered.createDefaultInstance(), null)) {
            return null;
         }

         milim.getPersistentData().putBoolean("FoxBossSatanaelGranted", true);
         return (ManasSkillInstance)skills.getSkill(SATANAEL).orElse(null);
      } else {
         return null;
      }
   }

   static boolean stampedeReady(ManasSkillInstance skill, MilimEntity milim) {
      CompoundTag tag = skill.getTag();
      if (tag != null && !tag.contains("SatanaelStampedeEnd") && !skill.onCoolDown(1) && tag.getLong("SatanaelWeaknessEnd") <= milim.level().getGameTime()) {
         MilimSatanaelCompat.Settings settings = settings();
         return settings != null && tag.getDouble("SatanaelWrathValue") + 1.0E-4 >= settings.requiredWrath;
      } else {
         return false;
      }
   }

   static boolean safeDominationGroup(MilimEntity milim) {
      MilimSatanaelCompat.Settings settings = settings();
      if (settings != null && !(settings.radius <= 0.0)) {
         int controllableEnemies = 0;

         for (LivingEntity entity : milim.level().getEntitiesOfClass(LivingEntity.class, milim.getBoundingBox().inflate(settings.radius))) {
            if (entity != milim
               && entity.isAlive()
               && !entity.isSpectator()
               && !entity.isAlliedTo(milim)
               && !milim.isAlliedTo(entity)
               && !(entity instanceof Player player && player.isCreative())) {
               if (!isEnemy(milim, entity)) {
                  return false;
               }

               if (entity instanceof Mob && !SkillUtils.isSkillToggled(entity, (ManasSkill)ResistanceSkills.SPIRITUAL_ATTACK_NULLIFICATION.get())) {
                  controllableEnemies++;
               }
            }
         }

         return controllableEnemies >= 2;
      } else {
         return false;
      }
   }

   static double combatRecoveryFraction() {
      MilimSatanaelCompat.Settings settings = settings();
      return settings == null ? 0.0 : settings.recoveryFraction;
   }

   private static boolean isEnemy(MilimEntity milim, LivingEntity entity) {
      if (entity == milim.getTarget()) {
         return true;
      }

      if (entity instanceof Mob mob) {
         if (entity instanceof Enemy) {
            return true;
         }

         LivingEntity victim = mob.getTarget();
         return victim == milim || victim != null && milim.isAlliedTo(victim);
      } else {
         return false;
      }
   }

   static void guardDominationChannel(SkillStorage skills, ManasSkillInstance instance) {
      for (int i = 0; i < skills.getHeldSkills().size(); i++) {
         TickingSkill channel = (TickingSkill)skills.getHeldSkills().get(i);
         if (channel.getSkill() == instance.getSkill() && channel.getMode() == 0 && channel.getDuration() == 0) {
            skills.getHeldSkills()
               .set(
                  i,
                  new TickingSkill(channel.getSkill(), 0, channel.getKeyNumber()) {
                     public boolean tick(SkillStorage storage, LivingEntity entity) {
                        return entity instanceof MilimEntity milim
                              && milim.hasCombatTarget()
                              && !milim.isNoAi()
                              && !milim.isDemo()
                              && !milim.isDebugAnimation()
                              && !milim.isOrderedToSit()
                              && MilimSatanaelCompat.safeDominationGroup(milim)
                           ? super.tick(storage, entity)
                           : false;
                     }
                  }
               );
         }
      }
   }

   static void tickBetweenNativePulses(MilimEntity milim, SkillStorage skills, ManasSkillInstance skill) {
      if (MilimSatanaelCompat.AuditedTiming.ENABLED
         && milim.level().getServer() != null
         && milim.level().getServer().getTickCount() % 100 != 0
         && skill.canTick(milim)
         && skill.canInteractSkill(milim)) {
         if (!((SkillTickEvent)SkillEvents.SKILL_PRE_TICK.invoker()).tick(skill, milim).isFalse()) {
            skill.onTick(milim);
            ((SkillPostTickEvent)SkillEvents.SKILL_POST_TICK.invoker()).tick(skill, milim);
            skills.checkAndMarkDirty(skill);
            if (skill.onCoolDown(1)) {
               skills.markActiveTick();
            }
         }
      }
   }

   private static MilimSatanaelCompat.Settings settings() {
      if (settingsUnavailable) {
         return null;
      }

      try {
         Class<?> configClass = Class.forName("com.foxablazeultimate.config.SatanaelConfig");
         Object config = configClass.getMethod("get").invoke(null);
         if (config == null) {
            return null;
         }

         Object settings = configClass.getField("Satanael").get(config);
         Class<?> type = settings.getClass();
         double radius = type.getField("dominationRadius").getDouble(settings);
         double wrath = type.getField("stampedeRequiredWrath").getDouble(settings);
         double recovery = type.getField("combatMagiculeRecoveryFraction").getDouble(settings);
         return new MilimSatanaelCompat.Settings(Math.max(0.0, radius), Math.max(0.0, Math.min(100.0, wrath)), Math.max(0.0, recovery));
      } catch (ReflectiveOperationException | LinkageError exception) {
         settingsUnavailable = true;
         LoggerFactory.getLogger(MilimSatanaelCompat.class)
            .warn("Could not read optional Satanael settings; Milim will use its passive reactor only", exception);
         return null;
      }
   }

   private static boolean version(String modId, String expected) {
      return ModList.get().getModContainerById(modId).map(container -> expected.equals(container.getModInfo().getVersion().toString())).orElse(false);
   }

   private MilimSatanaelCompat() {
   }

   private static final class AuditedTiming {
      private static final boolean ENABLED = MilimSatanaelCompat.version("foxablazeultimate", "1.0.6.3")
         && MilimSatanaelCompat.version("manascore_skill", "4.0.0.0");
   }

   private record Settings(double radius, double requiredWrath, double recoveryFraction) {
   }
}
