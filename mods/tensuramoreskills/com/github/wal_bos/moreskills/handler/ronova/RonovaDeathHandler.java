package com.github.wal_bos.moreskills.handler.ronova;

import com.github.wal_bos.moreskills.ability.skill.shades.ronova.RonovaConfigAccess;
import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.registry.skill.ShadeSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

public final class RonovaDeathHandler {
   public static final String DEATH_FIELD_DONE = "RonovaDeathFieldCompleted";
   public static final String DEATH_FIELD_ACTIVE = "RonovaDeathFieldActive";
   public static final String DEATH_FIELD_OWNER = "RonovaDeathFieldOwner";
   public static final String DEATH_FIELD_UNTIL = "RonovaDeathFieldUntil";
   public static final String SACRIFICE_PENDING = "RonovaSacrificePending";
   private static final Set<UUID> PASSIVE_FINALITY_RUNNING = new HashSet<>();

   private RonovaDeathHandler() {
   }

   public static void allySacrifice(ManasSkillInstance instance, LivingEntity entity) {
      MoreSkillsConfigs.RonovaConfig cfg = RonovaConfigAccess.cfg();
      List<LivingEntity> sacrifices = entity.level()
         .getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate((Double)cfg.allySacrificeRadius.get()), target -> canSacrifice(entity, target))
         .stream()
         .sorted(Comparator.comparingDouble(entity::distanceToSqr))
         .limit(((Integer)cfg.allySacrificeRequired.get()).intValue())
         .toList();
      int total = instance.getOrCreateTag().getInt("RonovaSacrificePending") + sacrifices.size();

      for (LivingEntity target : sacrifices) {
         target.discard();
      }

      if (total < (Integer)cfg.allySacrificeRequired.get()) {
         instance.getOrCreateTag().putInt("RonovaSacrificePending", total);
         entity.sendSystemMessage(
            Component.translatable("skill.tensuramoreskills.ronova.sacrifice_pending", new Object[]{total, cfg.allySacrificeRequired.get()})
         );
      } else {
         instance.getOrCreateTag().putInt("RonovaSacrificePending", 0);
         entity.hurt(entity.damageSources().magic(), Math.max(1.0F, entity.getHealth() * ((Double)cfg.allySacrificeHealthDrainRatio.get()).floatValue()));
         int duration = (Integer)cfg.allySacrificeBuffTicks.get();
         int amp = (Integer)cfg.allySacrificeAmplifier.get();
         entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, duration, amp, false, false, true));
         entity.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, duration, amp, false, false, true));
         entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, duration, amp, false, false, true));
         entity.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, duration, amp, false, false, true));
         entity.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.HAKI_COAT), duration, amp, false, false, true));
         instance.getOrCreateTag()
            .putInt("RonovaExtraLives", Math.min((Integer)cfg.allySacrificeExtraLives.get(), (Integer)cfg.allySacrificeMaxExtraLives.get()));
         instance.setCoolDown((Integer)cfg.allySacrificeCooldown.get(), 2);
         instance.addMasteryPoint(entity);
         entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.WITHER_SPAWN, SoundSource.PLAYERS, 1.0F, 0.6F);
      }
   }

   public static boolean deathFieldHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks) {
      if (entity.level().isClientSide()) {
         return true;
      }

      MoreSkillsConfigs.RonovaConfig cfg = RonovaConfigAccess.cfg();
      if (heldTicks <= 1 || !instance.getOrCreateTag().getBoolean("RonovaDeathFieldActive")) {
         instance.getOrCreateTag().putBoolean("RonovaDeathFieldActive", true);
         instance.getOrCreateTag().putBoolean("RonovaDeathFieldCompleted", false);
         instance.setCoolDown((Integer)cfg.deathFieldCooldown.get(), 3);
         entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.WITHER_AMBIENT, SoundSource.PLAYERS, 1.0F, 0.7F);
         if (entity instanceof Player player) {
            player.displayClientMessage(Component.translatable("skill.tensuramoreskills.ronova.death_field_start").withStyle(ChatFormatting.DARK_PURPLE), true);
         }
      }

      int amp = Math.min(
         (Integer)cfg.deathFieldMaxAmplifier.get(),
         (Integer)cfg.deathFieldBaseAmplifier.get() + heldTicks / Math.max(1, (Integer)cfg.deathFieldAmplifierStepTicks.get())
      );
      if (heldTicks > 0 && heldTicks % Math.max(1, (Integer)cfg.deathFieldSelfDamageTicks.get()) == 0) {
         float ratio = (float)((Double)cfg.deathFieldSelfDamageBaseRatio.get() + amp * (Double)cfg.deathFieldSelfDamageAmpRatio.get());
         entity.hurt(entity.damageSources().magic(), Math.max(1.0F, entity.getMaxHealth() * ratio));
      }

      if (entity.level() instanceof ServerLevel level && heldTicks % 5 == 0) {
         RonovaDeadLand.convert(
            level, entity.blockPosition(), (Integer)cfg.deathFieldDeadLandRadius.get(), Math.max(96, (Integer)cfg.deathFieldDeadLandBlocksPerTick.get())
         );
      }

      if (heldTicks % Math.max(1, (Integer)cfg.deathFieldPulseTicks.get()) == 0) {
         AABB area = entity.getBoundingBox().inflate((Double)cfg.deathFieldRadius.get());

         for (LivingEntity target : entity.level()
            .getEntitiesOfClass(LivingEntity.class, area, targetx -> targetx != entity && targetx.isAlive() && !targetx.isAlliedTo(entity))) {
            target.getPersistentData().putUUID("RonovaDeathFieldOwner", entity.getUUID());
            target.getPersistentData().putLong("RonovaDeathFieldUntil", entity.level().getGameTime() + 300L);
            target.hurt(entity.damageSources().magic(), ((Double)cfg.deathFieldPulseDamage.get()).floatValue());
            applyTorment(target, (Integer)cfg.deathFieldEffectTicks.get(), amp);
            if (!target.isAlive() || target.isDeadOrDying()) {
               RonovaSkillStealHandler.steal(entity, target);
            }
         }

         instance.addMasteryPoint(entity);
      }

      if (heldTicks >= (Integer)cfg.deathFieldDurationTicks.get() && !instance.getOrCreateTag().getBoolean("RonovaDeathFieldCompleted")) {
         instance.getOrCreateTag().putBoolean("RonovaDeathFieldCompleted", true);
         if (entity instanceof Player player) {
            player.displayClientMessage(Component.translatable("skill.tensuramoreskills.ronova.death_field_done").withStyle(ChatFormatting.DARK_PURPLE), true);
         }
      }

      return true;
   }

   public static void deathFieldRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks) {
      if (instance.getOrCreateTag().getBoolean("RonovaDeathFieldActive")) {
         instance.addMasteryPoint(entity);
      }

      instance.getOrCreateTag().putBoolean("RonovaDeathFieldActive", false);
      instance.getOrCreateTag().putBoolean("RonovaDeathFieldCompleted", false);
   }

   public static void onDeath(LivingDeathEvent event) {
      LivingEntity dead = event.getEntity();
      if (dead.level() instanceof ServerLevel level) {
         handleRonovaKillCredit(event, dead);
         handleDeathFieldSteal(level, dead);
         handleAutomaticSubordinateSoul(level, dead);
         handleRonovaOwnerDeathPassive(event, dead);
      }
   }

   public static void onDrops(LivingDropsEvent event) {
      if ((Boolean)RonovaConfigAccess.cfg().finalityBalanceDeleteDrops.get()) {
         if (RonovaPlayerHandler.hasRonova(event.getEntity())) {
            event.getDrops().clear();
         }
      }
   }

   private static void handleRonovaKillCredit(LivingDeathEvent event, LivingEntity dead) {
      if (event.getSource().getEntity() instanceof LivingEntity killer) {
         SkillAPI.getSkillsFrom(killer).getSkill((ManasSkill)ShadeSkills.RONOVA.get()).ifPresent(instance -> {
            if (SkillUtils.isSkillToggled(instance)) {
               RonovaPlayerHandler.addKill(instance, killer);
               if ((Boolean)RonovaConfigAccess.cfg().deathFieldStealOnAnyRonovaKill.get()) {
                  RonovaSkillStealHandler.steal(killer, dead);
               }
            }
         });
      }
   }

   private static void handleDeathFieldSteal(ServerLevel level, LivingEntity dead) {
      if (dead.getPersistentData().hasUUID("RonovaDeathFieldOwner")) {
         long until = dead.getPersistentData().getLong("RonovaDeathFieldUntil");
         if (until >= level.getGameTime()) {
            UUID uuid = dead.getPersistentData().getUUID("RonovaDeathFieldOwner");
            if (level.getEntity(uuid) instanceof LivingEntity owner) {
               SkillAPI.getSkillsFrom(owner).getSkill((ManasSkill)ShadeSkills.RONOVA.get()).ifPresent(instance -> {
                  if (SkillUtils.isSkillToggled(instance)) {
                     RonovaSkillStealHandler.steal(owner, dead);
                  }
               });
            }
         }
      }
   }

   private static void handleAutomaticSubordinateSoul(ServerLevel level, LivingEntity dead) {
      LivingEntity owner = findSubordinateOwner(dead);
      if (owner != null) {
         if (!(owner.distanceTo(dead) > (Double)RonovaConfigAccess.cfg().salvationRadius.get())) {
            SkillAPI.getSkillsFrom(owner).getSkill((ManasSkill)ShadeSkills.RONOVA.get()).ifPresent(instance -> {
               if (SkillUtils.isSkillToggled(instance)) {
                  RonovaSoulHandler.create(level, owner, dead);
               }
            });
         }
      }
   }

   private static void handleRonovaOwnerDeathPassive(LivingDeathEvent event, LivingEntity dead) {
      SkillAPI.getSkillsFrom(dead).getSkill((ManasSkill)ShadeSkills.RONOVA.get()).ifPresent(instance -> {
         if (SkillUtils.isSkillToggled(instance)) {
            int lives = instance.getOrCreateTag().getInt("RonovaExtraLives");
            if (lives > 0 && (Boolean)RonovaConfigAccess.cfg().allySacrificeExtraLivesEnabled.get()) {
               event.setCanceled(true);
               instance.getOrCreateTag().putInt("RonovaExtraLives", lives - 1);
               dead.setHealth(Math.max(1.0F, dead.getMaxHealth() * ((Double)RonovaConfigAccess.cfg().extraLifeHealthRatio.get()).floatValue()));
               dead.invulnerableTime = (Integer)RonovaConfigAccess.cfg().extraLifeInvulnerabilityTicks.get();
            } else {
               triggerTrueDeathPassive(dead);
            }
         }
      });
   }

   private static void triggerTrueDeathPassive(LivingEntity dead) {
      MoreSkillsConfigs.RonovaConfig cfg = RonovaConfigAccess.cfg();
      if ((Boolean)cfg.finalityBalanceTrueDeathEnabled.get()) {
         if (PASSIVE_FINALITY_RUNNING.add(dead.getUUID())) {
            try {
               RonovaWorldEraseHandler.trueDeathPassive(
                  dead,
                  (Double)cfg.finalityBalanceRadius.get(),
                  (Boolean)cfg.finalityBalanceEraseBlocks.get(),
                  (Boolean)cfg.finalityBalanceInstantKill.get(),
                  (Double)cfg.finalityBalanceDamage.get()
               );
            } finally {
               PASSIVE_FINALITY_RUNNING.remove(dead.getUUID());
            }
         }
      }
   }

   private static void applyTorment(LivingEntity target, int ticks, int amp) {
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.INSANITY), ticks, amp, false, false, true));
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.BURDEN), ticks, amp, false, false, true));
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.FRAGILITY), ticks, amp, false, false, true));
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.FEAR), ticks, amp, false, false, true));
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE), ticks, amp, false, false, true));
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.CORROSION), ticks, amp, false, false, true));
   }

   private static boolean canSacrifice(LivingEntity owner, LivingEntity target) {
      if (target == owner || target instanceof Player || !target.isAlive()) {
         return false;
      } else if (target instanceof TamableAnimal tamable) {
         return tamable.isTame() && owner.getUUID().equals(tamable.getOwnerUUID());
      } else {
         return target instanceof AbstractHorse horse ? horse.isTamed() : target.hasCustomName();
      }
   }

   private static LivingEntity findSubordinateOwner(LivingEntity dead) {
      LivingEntity owner = SubordinateHelper.getSubordinateOwner(dead);
      if (owner != null) {
         return owner;
      }

      if (dead instanceof TamableAnimal tamable) {
         LivingEntity var4 = tamable.getOwner();
         if (var4 instanceof LivingEntity) {
            return var4;
         }
      }

      return null;
   }
}
