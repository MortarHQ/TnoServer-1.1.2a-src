package com.tno_tensura_extras.skill.unique;

import com.tno_tensura_extras.entity.DiabloEntity;
import com.tno_tensura_extras.registry.ExtrasSkills;
import com.tno_tensura_extras.util.SeducerManager;
import com.tno_tensura_extras.util.SeducerSoulHarvest;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.effect.template.TensuraMobEffectInstance;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.AbilitySlot;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class SeducerSkill extends Skill {
   public static final int CONTRACT = 0;
   public static final int FEAR = 1;
   public static final int SOUL_PLUNDER = 2;
   public static final int COMMAND = 3;
   private static final int MODE_COUNT = 4;
   private static final int FEAR_AMPLIFIER = 4;
   private static final double FEAR_RANGE = 12.0;
   private static final int PASSIVE_FEAR_DURATION = 100;
   private static final double SOUL_PLUNDER_RANGE = 12.0;
   private static final ResourceLocation ICON = ResourceLocation.fromNamespaceAndPath("tno_tensura_extras", "textures/skill/unique/seducer.png");

   public SeducerSkill() {
      super(SkillType.UNIQUE);
   }

   public ResourceLocation getSkillIcon() {
      return ICON;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isMastered(entity);
   }

   public int getModes(ManasSkillInstance instance) {
      return 4;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return Math.floorMod(mode + (reverse ? -1 : 1), 4);
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "seducer.contract";
         case 1 -> "seducer.fear";
         case 2 -> "seducer.soul_plunder";
         case 3 -> "seducer.command";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> 500.0;
         case 1 -> 1000.0;
         case 2 -> 2000.0;
         default -> 0.0;
      };
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity instanceof Mob mob) {
         this.tryCombatMode(instance, mob, mob.getTarget(), mode);
      } else if (entity instanceof ServerPlayer player
         && !entity.level().isClientSide()
         && player.isAlive()
         && !player.isSpectator()
         && instance != null
         && mode >= 0
         && mode < 4
         && !instance.onCoolDown(mode)) {
         switch (mode) {
            case 0:
               SeducerManager.contract(instance, player, mode);
               break;
            case 1:
               this.frighten(instance, player);
               break;
            case 2:
               this.plunderSoul(instance, player);
               break;
            case 3:
               SeducerManager.command(instance, player, mode);
         }
      }
   }

   public boolean tryCombatMode(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, int mode) {
      if (owner instanceof Mob
         && owner.level() instanceof ServerLevel level
         && instance != null
         && instance.getSkill() == this
         && instance.canInteractSkill(owner)
         && (mode == 1 || mode == 2)
         && !instance.onCoolDown(mode)
         && target != null
         && canHarmFromNpc(owner, target)
         && !(owner.distanceToSqr(target) > 144.0)
         && owner.hasLineOfSight(target)) {
         boolean mastered = instance.isMastered(owner);
         int duration = mastered ? 300 : 200;
         if (mode == 1) {
            List<LivingEntity> targets = this.getFearTargets(owner, owner.position(), 12.0, duration);
            if (targets.isEmpty() || EnergyHelper.isOutOfEnergy(owner, instance, 1)) {
               return false;
            }

            instance.setCoolDown(mastered ? 10 : 15, 1);
            this.applyFear(owner, targets, duration);
            playSoulEffect(level, owner.position());
         } else {
            if (target.getType().is(TensuraEntityTags.NO_SPIRITUAL_DAMAGE) || !SeducerManager.isSurrendered(owner, target)) {
               return false;
            }

            double spiritualHealth = TensuraStorages.getExistenceFrom(target).getSpiritualHealth();
            float damage = Math.nextUp((float)spiritualHealth);
            if (!Double.isFinite(spiritualHealth)
               || spiritualHealth <= 0.0
               || !Float.isFinite(damage)
               || damage <= 0.0F
               || EnergyHelper.isOutOfEnergy(owner, instance, 2)) {
               return false;
            }

            instance.setCoolDown(mastered ? 10 : 20, 2);
            Vec3 position = target.position();
            if (TensuraDamageHelper.directSpiritualHurt(target, owner, this.createSource(instance, owner, TensuraDamageTypes.SOUL_CONSUMED, 2), damage)) {
               playSoulEffect(level, position);
               if (!target.isAlive() && target.getHealth() <= 0.0F && target.getRemovalReason() != RemovalReason.CHANGED_DIMENSION) {
                  this.applyFear(owner, this.getFearTargets(owner, position, mastered ? 12.0 : 8.0, duration), duration);
               }
            }
         }

         owner.swing(InteractionHand.MAIN_HAND, true);
         instance.addMasteryPoint(owner);
         SkillAPI.getSkillsFrom(owner).markActiveTick();
         SkillAPI.getSkillsFrom(owner).markDirty();
         return true;
      } else {
         return false;
      }
   }

   private static boolean canHarmFromNpc(LivingEntity owner, LivingEntity target) {
      return !owner.isSpectator()
         && !target.isRemoved()
         && !target.isSpectator()
         && SeducerManager.canHarm(owner, target)
         && !(owner instanceof DiabloEntity diablo && !diablo.isAllowedCombatTarget(target))
         && !SkillUtils.isSkillToggled(target, (ManasSkill)UniqueSkills.ANTI_SKILL.get())
         && !TensuraStorages.getAbilityFrom(target).isAbilityInActivePreset((ManasSkill)UniqueSkills.ANTI_SKILL.get())
         && !SkillUtils.isSkillToggled(target, (ManasSkill)ResistanceSkills.SPIRITUAL_ATTACK_NULLIFICATION.get());
   }

   private void frighten(ManasSkillInstance instance, ServerPlayer player) {
      boolean mastered = instance.isMastered(player);
      int duration = mastered ? 300 : 200;
      List<LivingEntity> targets = this.getFearTargets(player, player.position(), 12.0, duration);
      if (targets.isEmpty()) {
         fail(player, "tensura.targeting.not_targeted");
      } else if (!EnergyHelper.isOutOfEnergy(player, instance, 1)) {
         instance.setCoolDown(mastered ? 10 : 15, 1);
         player.swing(InteractionHand.MAIN_HAND, true);
         if (this.applyFear(player, targets, duration)) {
            instance.addMasteryPoint(player);
            playSoulEffect(player.serverLevel(), player.position());
         } else {
            fail(player, "tensura.ability.activation_failed");
         }
      }
   }

   public static void onPostDamage(LivingEntity target, DamageSource source, float amount) {
      if (!target.level().isClientSide()
         && amount > 0.0F
         && target.isAlive()
         && source.getEntity() instanceof LivingEntity owner
         && (owner instanceof ServerPlayer || owner instanceof Mob)
         && owner.isAlive()
         && !owner.isSpectator()
         && !source.is(TensuraDamageTypes.FEAR)
         && !source.is(TensuraDamageTypes.SOUL_CONSUMED)
         && !source.is(DamageTypes.THORNS)
         && (source.getDirectEntity() == owner || source.getDirectEntity() instanceof Projectile)) {
         ManasSkillInstance instance = (ManasSkillInstance)SkillAPI.getSkillsFrom(owner).getSkill((ManasSkill)ExtrasSkills.SEDUCER.get()).orElse(null);
         if (instance != null
            && instance.isMastered(owner)
            && instance.isToggled()
            && instance.canInteractSkill(owner)
            && instance.getSkill() instanceof SeducerSkill skill) {
            skill.applyFear(owner, List.of(target), 100);
         }
      }
   }

   private void plunderSoul(ManasSkillInstance instance, ServerPlayer player) {
      LivingEntity target = ObjectSelectionHelper.getTargetingEntity(player, 12.0, false);
      if (target == null || !target.isAlive() || player.distanceToSqr(target) > 144.0 || !player.hasLineOfSight(target)) {
         fail(player, "tensura.targeting.not_targeted");
      } else if (!SeducerManager.canHarm(player, target) || target.getType().is(TensuraEntityTags.NO_SPIRITUAL_DAMAGE)) {
         fail(player, "tensura.ability.activation_failed");
      } else if (!SeducerManager.isSurrendered(player, target)) {
         fail(player, "tno_tensura_extras.seducer.not_surrendered");
      } else {
         double spiritualHealth = TensuraStorages.getExistenceFrom(target).getSpiritualHealth();
         if (Double.isFinite(spiritualHealth) && !(spiritualHealth <= 0.0)) {
            float amount = Math.nextUp((float)spiritualHealth);
            if (!Float.isFinite(amount) || amount <= 0.0F) {
               fail(player, "tensura.ability.activation_failed");
            } else if (!EnergyHelper.isOutOfEnergy(player, instance, 2)) {
               boolean mastered = instance.isMastered(player);
               instance.setCoolDown(mastered ? 10 : 20, 2);
               player.swing(InteractionHand.MAIN_HAND, true);
               Vec3 deathPosition = target.position();
               int unclaimedSouls = SeducerSoulHarvest.captureUnclaimedSouls(player, target);
               boolean damaged = TensuraDamageHelper.directSpiritualHurt(
                  target, player, this.createSource(instance, player, TensuraDamageTypes.SOUL_CONSUMED, 2), amount
               );
               if (!damaged) {
                  fail(player, "tensura.ability.activation_failed");
               } else {
                  if (!target.isAlive() && target.getHealth() <= 0.0F && target.getRemovalReason() != RemovalReason.CHANGED_DIMENSION) {
                     SeducerSoulHarvest.awardUnclaimedSouls(player, unclaimedSouls);
                     instance.addMasteryPoint(player);
                     int duration = mastered ? 300 : 200;
                     this.applyFear(player, this.getFearTargets(player, deathPosition, mastered ? 12.0 : 8.0, duration), duration);
                     playSoulEffect(player.serverLevel(), deathPosition);
                  }
               }
            }
         } else {
            fail(player, "tensura.ability.activation_failed");
         }
      }
   }

   private List<LivingEntity> getFearTargets(LivingEntity owner, Vec3 center, double radius, int duration) {
      ServerLevel level = (ServerLevel)owner.level();
      double radiusSquared = radius * radius;
      return level.getEntitiesOfClass(
         LivingEntity.class,
         new AABB(center, center).inflate(radius),
         target -> target.distanceToSqr(center) <= radiusSquared && this.canFrighten(owner, target, duration)
      );
   }

   private boolean canFrighten(LivingEntity owner, LivingEntity target, int duration) {
      if (target == owner
         || !owner.isAlive()
         || !target.isAlive()
         || target.isRemoved()
         || target.level() != owner.level()
         || target.isSpectator()
         || target.getType().is(TensuraEntityTags.NO_FEAR)
         || target instanceof Player player
            && (
               player.getAbilities().invulnerable
                  || owner instanceof ServerPlayer serverPlayer && (!serverPlayer.server.isPvpAllowed() || !serverPlayer.canHarmPlayer(player))
            )) {
         return false;
      }

      if (owner instanceof ServerPlayer || canHarmFromNpc(owner, target) && owner.hasLineOfSight(target)) {
         MobEffectInstance current = target.getEffect(TensuraMobEffects.getReference(TensuraMobEffects.FEAR));
         if (current == null) {
            return true;
         } else if (current.getAmplifier() <= 4 && (current.getAmplifier() != 4 || !current.isInfiniteDuration() && current.getDuration() < duration)) {
            TensuraMobEffectInstance metadata = (TensuraMobEffectInstance)current;
            AbilitySlot sourceAbility = metadata.tensura$getSourceAbility();
            return owner.getUUID().equals(metadata.tensura$getSource())
               && sourceAbility != null
               && sourceAbility.getSkill() == this
               && sourceAbility.getMode() == 1;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean applyFear(LivingEntity owner, List<LivingEntity> targets, int duration) {
      boolean applied = false;

      for (LivingEntity target : targets) {
         if (this.canFrighten(owner, target, duration)) {
            MobEffectInstance fear = new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.FEAR), duration, 4, false, true, true);
            if (TensuraMobEffect.addEffect(target, fear, owner, this, 1)) {
               applied = true;
            }
         }
      }

      return applied;
   }

   private static void playSoulEffect(ServerLevel level, Vec3 position) {
      level.sendParticles(ParticleTypes.SOUL, position.x, position.y + 0.8, position.z, 12, 0.35, 0.5, 0.35, 0.02);
      level.playSound(null, position.x, position.y, position.z, SoundEvents.SOUL_ESCAPE, SoundSource.PLAYERS, 0.8F, 1.0F);
   }

   private static void fail(ServerPlayer player, String key) {
      player.displayClientMessage(Component.translatable(key).withStyle(ChatFormatting.RED), true);
   }
}
