package com.foxablazeultimate.ability.skill.mammon;

import com.foxablazeultimate.config.MammonConfig;
import com.foxablazeultimate.world.FoxAblazeGameRules;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.extra.MultilayerBarrierSkill;
import io.github.manasmods.tensura.ability.skill.extra.ThoughtAccelerationSkill;
import io.github.manasmods.tensura.ability.skill.unique.GreedSkill;
import io.github.manasmods.tensura.config.ability.skill.ExtraSkillConfig;
import io.github.manasmods.tensura.config.ability.skill.ExtraSkillConfig.ThoughtAcceleration;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.data.TensuraSkillTags;
import io.github.manasmods.tensura.entity.human.CloneEntity;
import io.github.manasmods.tensura.entity.template.subclass.ILivingPartEntity;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillPlunderEvent;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.unique.ITrulyUnique;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import io.github.manasmods.tensura.util.EnergyHelper.DrainType;
import io.github.manasmods.tensura.util.EnergyHelper.GainType;
import io.github.manasmods.tensura.world.TensuraGameRules;
import java.util.List;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;

public class MammonSkill extends Skill {
   private static final int MODE_COUNT = 6;
   private static final int MODE_HEART_CONTROL = 0;
   private static final int MODE_FLARE = 1;
   private static final int MODE_DEATH_WISH = 2;
   private static final int MODE_MULTILAYER_BARRIER = 3;
   private static final int MODE_LIFE_STEAL = 4;
   private static final int MODE_SKILL_STEAL = 5;
   private static final String SKILL_STOLEN_MARKER = "FoxAblazeUltimateMammonSkillStolen";
   private static final ResourceLocation THOUGHT_ACCELERATION = id("thought_acceleration");
   private static final ResourceLocation MULTILAYER_BARRIER = id("multilayer_barrier");
   private static final ResourceLocation ALLY_MULTILAYER_BARRIER = id("ally_multilayer_barrier");
   private static final TagKey<ManasSkill> UNIQUE_SKILLS = skillTag("unique_skills");
   private static final TagKey<ManasSkill> ULTIMATE_SKILLS = skillTag("ultimate_skills");

   public MammonSkill() {
      super(SkillType.ULTIMATE);
   }

   private static ResourceLocation id(String path) {
      return ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "mammon/" + path);
   }

   private static TagKey<ManasSkill> skillTag(String path) {
      return TagKey.create(SkillAPI.getSkillRegistryKey(), ResourceLocation.fromNamespaceAndPath("tensura", path));
   }

   private static MammonConfig.MammonSettings settings() {
      return MammonConfig.settings();
   }

   private static GreedSkill greed() {
      return (GreedSkill)UniqueSkills.GREED.get();
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "textures/skill/mammon.png");
   }

   public MutableComponent getColoredName() {
      MutableComponent name = super.getName();
      return name == null ? null : name.withStyle(ChatFormatting.DARK_PURPLE);
   }

   public double getDefaultAcquiringMagiculeCost() {
      return Double.POSITIVE_INFINITY;
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return false;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return instance.getMastery() >= 0.0;
   }

   public int getModes(ManasSkillInstance instance) {
      return 6;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      int safe = sanitizeMode(mode);
      if (reverse) {
         return safe == 0 ? 5 : safe - 1;
      } else {
         return safe == 5 ? 0 : safe + 1;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (sanitizeMode(mode)) {
         case 0 -> "mammon.heart_control";
         case 1 -> "mammon.flare";
         case 2 -> "mammon.death";
         case 3 -> "mammon.multilayer_barrier";
         case 4 -> "mammon.life_steal";
         case 5 -> "mammon.skill_steal";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      int safe = sanitizeMode(mode);
      if (safe <= 2) {
         return greed().getMagiculeCost(entity, instance, safe);
      }

      MammonConfig.MammonSettings config = settings();

      return switch (safe) {
         case 4 -> Math.max(0.0, config.lifeStealMagiculeCost);
         case 5 -> Math.max(0.0, config.skillStealMagiculeCost);
         default -> 0.0;
      };
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      int safe = sanitizeMode(mode);
      if (!instance.onCoolDown(safe)) {
         if (safe <= 2) {
            MammonDelegateHelper.DelegateResult result = MammonDelegateHelper.onPressed(instance, greed(), entity, keyNumber, safe);
            copyChildCooldown(instance, safe, result.cooldown());
         } else {
            switch (safe) {
               case 3:
                  this.toggleMultilayerBarrier(instance, entity, safe);
                  break;
               case 4:
                  this.lifeSteal(instance, entity, safe);
                  break;
               case 5:
                  this.stealSkill(instance, entity, safe);
            }
         }
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      int safe = sanitizeMode(mode);
      if (safe <= 2 && !instance.onCoolDown(safe)) {
         MammonDelegateHelper.DelegateResult result = MammonDelegateHelper.onHeld(instance, greed(), entity, heldTicks, safe);
         copyChildCooldown(instance, safe, result.cooldown());
         if (!result.active()) {
            MammonDelegateHelper.onRelease(instance, greed(), entity, heldTicks, 0, safe);
         }

         return result.active();
      } else {
         return false;
      }
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      int safe = sanitizeMode(mode);
      if (safe <= 2) {
         MammonDelegateHelper.onRelease(instance, greed(), entity, heldTicks, keyNumber, safe);
      }
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      return MammonDelegateHelper.onDamageEntity(instance, greed(), owner, target, source, amount);
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      super.onToggleOn(instance, entity);
      if (!instance.isTemporarySkill()) {
         applyThoughtAcceleration(instance, entity);
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      super.onToggleOff(instance, entity);
      removeThoughtAcceleration(instance, entity);
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isToggled();
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (instance.isToggled() && !instance.isTemporarySkill()) {
         int ticks = instance.getOrCreateTag().getInt("MammonThoughtAccelerationTicks") + 1;
         instance.getOrCreateTag().putInt("MammonThoughtAccelerationTicks", ticks);
         if (ticks % 20 == 0) {
            applyThoughtAcceleration(instance, entity);
         }

         if (ticks % Math.max(1, BASE_CONFIG.Mastery.masteryActivateTime) == 0) {
            instance.addMasteryPoint(entity);
         }
      }
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onLearnSkill(instance, entity);
      if (!instance.isTemporarySkill() && entity instanceof ServerPlayer player) {
         this.claimUniqueOwnership(player);
      }
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      removeThoughtAcceleration(instance, entity);
      removeBarrierModifiers(entity);
      if (entity instanceof ServerPlayer player) {
         this.releaseUniqueOwnership(player);
      }
   }

   private static void copyChildCooldown(ManasSkillInstance parent, int mode, int cooldown) {
      if (cooldown > 0) {
         parent.setCoolDown(cooldown, mode);
      }
   }

   private static int sanitizeMode(int mode) {
      return Math.floorMod(mode, 6);
   }

   private static void applyThoughtAcceleration(ManasSkillInstance instance, LivingEntity entity) {
      ThoughtAccelerationSkill.onToggle(instance, entity, THOUGHT_ACCELERATION, true);
      ThoughtAcceleration config = ((ExtraSkillConfig)ConfigRegistry.getConfig(ExtraSkillConfig.class)).ThoughtAcceleration;
      AttributeInstance chant = entity.getAttribute(TensuraAttributes.CHANT_SPEED);
      if (chant != null) {
         chant.addOrReplacePermanentModifier(new AttributeModifier(THOUGHT_ACCELERATION, config.chantSpeed, Operation.ADD_VALUE));
      }

      AttributeInstance dodge = entity.getAttribute(TensuraAttributes.DODGE_INVULNERABILITY);
      if (dodge != null) {
         if (instance.isMastered(entity)) {
            dodge.addOrReplacePermanentModifier(new AttributeModifier(THOUGHT_ACCELERATION, config.dodgeInvulnerability, Operation.ADD_VALUE));
         } else {
            dodge.removeModifier(THOUGHT_ACCELERATION);
         }
      }
   }

   private static void removeThoughtAcceleration(ManasSkillInstance instance, LivingEntity entity) {
      ThoughtAccelerationSkill.onToggle(instance, entity, THOUGHT_ACCELERATION, false);
      AttributeInstance chant = entity.getAttribute(TensuraAttributes.CHANT_SPEED);
      if (chant != null) {
         chant.removeModifier(THOUGHT_ACCELERATION);
      }

      AttributeInstance dodge = entity.getAttribute(TensuraAttributes.DODGE_INVULNERABILITY);
      if (dodge != null) {
         dodge.removeModifier(THOUGHT_ACCELERATION);
      }
   }

   private void toggleMultilayerBarrier(ManasSkillInstance instance, LivingEntity entity, int mode) {
      LivingEntity target = entity.isShiftKeyDown() ? ObjectSelectionHelper.getTargetingEntity(entity, 5.0, false) : null;
      if (target != null) {
         AttributeInstance barrier = target.getAttribute(TensuraAttributes.MULTILAYER_BARRIER);
         if (barrier == null || !SubordinateHelper.isAlly(entity, target)) {
            fail(entity, "foxablazeultimate.skill.mammon.target.invalid");
         } else if (barrier.getModifier(ALLY_MULTILAYER_BARRIER) != null) {
            barrier.removeModifier(ALLY_MULTILAYER_BARRIER);
            playBarrierBreak(target);
         } else {
            instance.addMasteryPoint(entity);
            instance.setCoolDown(MultilayerBarrierSkill.CONFIG.cooldown, mode);
            double points = target.getMaxHealth() * MultilayerBarrierSkill.CONFIG.allyPointMultiplier;
            barrier.addOrReplacePermanentModifier(new AttributeModifier(ALLY_MULTILAYER_BARRIER, points, Operation.ADD_VALUE));
            playBarrierActivation(target);
         }
      } else {
         AttributeInstance barrier = entity.getAttribute(TensuraAttributes.MULTILAYER_BARRIER);
         if (barrier == null) {
            fail(entity, "foxablazeultimate.skill.mammon.target.invalid");
         } else {
            barrier.removeModifier(ALLY_MULTILAYER_BARRIER);
            if (barrier.getModifier(MULTILAYER_BARRIER) != null) {
               barrier.removeModifier(MULTILAYER_BARRIER);
               playBarrierBreak(entity);
            } else {
               instance.addMasteryPoint(entity);
               instance.setCoolDown(MultilayerBarrierSkill.CONFIG.cooldown, mode);
               double points = entity.getMaxHealth() * MultilayerBarrierSkill.CONFIG.pointMultiplier;
               barrier.addOrReplacePermanentModifier(new AttributeModifier(MULTILAYER_BARRIER, points, Operation.ADD_VALUE));
               playBarrierActivation(entity);
            }
         }
      }
   }

   private static void playBarrierActivation(LivingEntity entity) {
      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.DEFENCE_ACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
      TensuraParticleHelper.spawnServerParticles(
         entity.level(),
         TensuraParticleUtils.getBlueWave(0.8F, entity.getBbWidth() * 2.5F, -0.2F, true),
         entity.getX(),
         entity.getY() + entity.getBbHeight() * 0.75,
         entity.getZ()
      );
      TensuraParticleHelper.spawnServerParticles(
         entity.level(),
         TensuraParticleUtils.getPurpleWave(0.8F, entity.getBbWidth() * 3.0F, -0.2F, true),
         entity.getX(),
         entity.getY() + entity.getBbHeight() * 0.5,
         entity.getZ()
      );
   }

   private static void playBarrierBreak(LivingEntity entity) {
      entity.level()
         .playSound(null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BARRIER_BREAK.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
      TensuraParticleHelper.spawnServerParticles(
         entity.level(),
         TensuraParticleUtils.getBlueWave(0.8F, entity.getBbWidth() * 2.0F, 0.0F, false),
         entity.getX(),
         entity.getY() + entity.getBbHeight() * 0.5,
         entity.getZ()
      );
   }

   private static void removeBarrierModifiers(LivingEntity entity) {
      AttributeInstance barrier = entity.getAttribute(TensuraAttributes.MULTILAYER_BARRIER);
      if (barrier != null) {
         barrier.removeModifier(MULTILAYER_BARRIER);
         barrier.removeModifier(ALLY_MULTILAYER_BARRIER);
      }
   }

   private void lifeSteal(ManasSkillInstance instance, LivingEntity owner, int mode) {
      MammonConfig.MammonSettings config = settings();
      LivingEntity target = this.selectTarget(owner);
      if (target == null
         || target != owner
            && target.isAlive()
            && !(target instanceof CloneEntity)
            && !target.getType().is(TensuraEntityTags.CLONES)
            && !target.getType().is(TensuraEntityTags.NO_EP_PLUNDER)) {
         if (target instanceof Player && !TensuraGameRules.canEpSteal(owner.level())) {
            failedAttempt(instance, mode, config.lifeStealFailureCooldownSeconds);
            fail(owner, "tensura.ability.activation_failed");
         } else if (!EnergyHelper.isOutOfEnergy(owner, instance, mode)) {
            if (target == null) {
               failedAttempt(instance, mode, config.lifeStealFailureCooldownSeconds);
               fail(owner, "foxablazeultimate.skill.mammon.target.missing");
            } else if (EnergyHelper.getMaxEP(owner) <= EnergyHelper.getMaxEP(target)) {
               failedAttempt(instance, mode, config.lifeStealFailureCooldownSeconds);
               fail(owner, "foxablazeultimate.skill.mammon.life_steal.ep_too_low");
            } else {
               double configuredFraction = instance.isMastered(owner) ? config.lifeStealEpFractionMastered : config.lifeStealEpFraction;
               double fraction = Math.max(0.0, Math.min(1.0, configuredFraction));
               boolean success = EnergyHelper.drainEnergy(target, owner, fraction, true, DrainType.MAX_EP, GainType.MAX);
               if (!success) {
                  failedAttempt(instance, mode, config.lifeStealFailureCooldownSeconds);
                  fail(owner, "foxablazeultimate.skill.mammon.life_steal.blocked");
               } else {
                  instance.addMasteryPoint(owner);
                  instance.setCoolDown(Math.max(0, config.lifeStealSuccessCooldownSeconds), mode);
                  owner.swing(InteractionHand.MAIN_HAND, true);
                  TensuraParticleHelper.addServerParticlesAroundSelf(target, ParticleTypes.SOUL, 1.5);
                  owner.level()
                     .playSound(
                        null, owner.getX(), owner.getY(), owner.getZ(), (SoundEvent)TensuraSoundEvents.DEBUFF_ACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 0.75F
                     );
               }
            }
         }
      } else {
         failedAttempt(instance, mode, config.lifeStealFailureCooldownSeconds);
         fail(owner, "foxablazeultimate.skill.mammon.life_steal.blocked");
      }
   }

   private void stealSkill(ManasSkillInstance instance, LivingEntity owner, int mode) {
      MammonConfig.MammonSettings config = settings();
      if (!TensuraGameRules.canStealSkill(owner.level())) {
         skillStealFailed(instance, owner, mode, "tensura.ability.activation_failed");
      } else if (!EnergyHelper.isOutOfEnergy(owner, instance, mode)) {
         LivingEntity target = this.selectTarget(owner);
         if (target != null && target != owner && (!(target instanceof Player) || config.skillStealAllowsPlayers)) {
            if (config.oneSuccessfulSkillStealPerTarget && target.getPersistentData().getBoolean("FoxAblazeUltimateMammonSkillStolen")) {
               skillStealFailed(instance, owner, mode, "foxablazeultimate.skill.mammon.skill_steal.already_stolen");
            } else if (config.respectNoSkillPlunderEntityTag && target.getType().is(TensuraEntityTags.NO_SKILL_PLUNDER)) {
               skillStealFailed(instance, owner, mode, "foxablazeultimate.skill.mammon.skill_steal.blocked");
            } else if (config.respectAntiSkill && hasActiveAntiSkill(target)) {
               skillStealFailed(instance, owner, mode, "foxablazeultimate.skill.mammon.skill_steal.blocked");
            } else {
               Skills ownerSkills = SkillAPI.getSkillsFrom(owner);
               Skills targetSkills = SkillAPI.getSkillsFrom(target);
               List<ManasSkillInstance> candidates = targetSkills.getLearnedSkills().stream().filter(candidate -> canSteal(candidate, ownerSkills)).toList();
               if (candidates.isEmpty()) {
                  skillStealFailed(instance, owner, mode, "foxablazeultimate.skill.mammon.skill_steal.empty");
               } else {
                  ManasSkillInstance selected = candidates.get(target.getRandom().nextInt(candidates.size()));
                  Changeable<ManasSkill> changedSkill = Changeable.of(selected.getSkill());
                  EventResult event = ((SkillPlunderEvent)TensuraSkillEvents.SKILL_PLUNDER.invoker()).plunder(target, owner, true, changedSkill);
                  ManasSkill finalSkill = (ManasSkill)changedSkill.get();
                  if (!event.isFalse() && finalSkill != null) {
                     if (finalSkill != selected.getSkill()) {
                        selected = targetSkills.getSkill(finalSkill).filter(candidate -> canSteal(candidate, ownerSkills)).orElse(null);
                     }

                     if (selected != null && canSteal(selected, ownerSkills)) {
                        ManasSkill stolenSkill = selected.getSkill();
                        ManasSkillInstance moved = stolenSkill.createDefaultInstance();
                        moved.setMastery(Math.max(0.0, selected.getMastery()));
                        moved.setRemoveTime(-1);
                        moved.setToggled(false);
                        if (!SkillHelper.learnSkill(owner, moved)) {
                           skillStealFailed(instance, owner, mode, "foxablazeultimate.skill.mammon.skill_steal.learn_failed");
                        } else {
                           targetSkills.forgetSkill(
                              stolenSkill,
                              Component.translatable("tensura.skill.forget.stolen", new Object[]{stolenSkill.getChatDisplayName(true), owner.getName()})
                           );
                           if (targetSkills.getSkill(stolenSkill).isPresent()) {
                              ownerSkills.forgetSkill(stolenSkill);
                              skillStealFailed(instance, owner, mode, "foxablazeultimate.skill.mammon.skill_steal.remove_failed");
                           } else {
                              if (config.oneSuccessfulSkillStealPerTarget) {
                                 target.getPersistentData().putBoolean("FoxAblazeUltimateMammonSkillStolen", true);
                              }

                              instance.addMasteryPoint(owner);
                              instance.setCoolDown(Math.max(0, config.skillStealSuccessCooldownSeconds), mode);
                              owner.swing(InteractionHand.MAIN_HAND, true);
                              owner.level()
                                 .playSound(null, owner.getX(), owner.getY(), owner.getZ(), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 1.0F, 1.0F);
                           }
                        }
                     } else {
                        skillStealFailed(instance, owner, mode, "foxablazeultimate.skill.mammon.skill_steal.blocked");
                     }
                  } else {
                     skillStealFailed(instance, owner, mode, "foxablazeultimate.skill.mammon.skill_steal.blocked");
                  }
               }
            }
         } else {
            skillStealFailed(instance, owner, mode, "foxablazeultimate.skill.mammon.target.missing");
         }
      }
   }

   private static boolean canSteal(ManasSkillInstance candidate, Skills ownerSkills) {
      if (candidate == null || candidate.isTemporarySkill() || candidate.isSubInstance() || candidate.getMastery() < 0.0) {
         return false;
      } else if (!candidate.is(TensuraSkillTags.NO_PLUNDERING) && !candidate.is(UNIQUE_SKILLS) && !candidate.is(ULTIMATE_SKILLS)) {
         return !(candidate.getSkill() instanceof Skill skill && (skill.getType() == SkillType.UNIQUE || skill.getType() == SkillType.ULTIMATE))
            ? ownerSkills.getSkill(candidate.getSkill()).isEmpty()
            : false;
      } else {
         return false;
      }
   }

   private static boolean hasActiveAntiSkill(LivingEntity target) {
      ManasSkill antiSkill = (ManasSkill)UniqueSkills.ANTI_SKILL.get();
      return SkillUtils.isSkillToggled(target, antiSkill) || TensuraStorages.getAbilityFrom(target).isAbilityInActivePreset(antiSkill);
   }

   private LivingEntity selectTarget(LivingEntity owner) {
      LivingEntity target = ObjectSelectionHelper.getTargetingEntity(owner, Math.max(1.0, settings().targetingRange), false);
      return target == null ? null : ILivingPartEntity.checkForHead(target);
   }

   private static void failedAttempt(ManasSkillInstance instance, int mode, int cooldownSeconds) {
      if (cooldownSeconds > 0) {
         instance.setCoolDown(cooldownSeconds, mode);
      }
   }

   private static void skillStealFailed(ManasSkillInstance instance, LivingEntity owner, int mode, String message) {
      failedAttempt(instance, mode, settings().skillStealFailureCooldownSeconds);
      fail(owner, message);
   }

   private static void fail(LivingEntity owner, String translationKey) {
      owner.sendSystemMessage(Component.translatable(translationKey).withStyle(ChatFormatting.RED));
      owner.level()
         .playSound(null, owner.getX(), owner.getY(), owner.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), SoundSource.PLAYERS, 0.75F, 1.0F);
   }

   private void claimUniqueOwnership(ServerPlayer player) {
      if (player.level().getGameRules().getBoolean(FoxAblazeGameRules.MAMMON_TRULY_UNIQUE)) {
         ServerLevel overworld = overworld(player);
         ResourceLocation skillId = this.getRegistryName();
         if (overworld != null && skillId != null) {
            ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);
            UUID owner = unique.getOwner(skillId);
            if (owner == null || owner.equals(player.getUUID())) {
               unique.addSkill(skillId, player.getUUID());
               unique.markDirty();
            }
         }
      }
   }

   private void releaseUniqueOwnership(ServerPlayer player) {
      ServerLevel overworld = overworld(player);
      ResourceLocation skillId = this.getRegistryName();
      if (overworld != null && skillId != null) {
         ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);
         if (player.getUUID().equals(unique.getOwner(skillId))) {
            unique.removeSkill(skillId);
            unique.markDirty();
         }
      }
   }

   private static ServerLevel overworld(ServerPlayer player) {
      MinecraftServer server = player.getServer();
      return server == null ? null : server.overworld();
   }
}
