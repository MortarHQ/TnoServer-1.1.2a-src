package com.tno_tensura_extras.skill.unique;

import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.battlewill.Battlewill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.data.TensuraSkillTags;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillPlunderEvent;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.EnergyHelper.DrainType;
import io.github.manasmods.tensura.util.EnergyHelper.GainType;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public final class AllRounderSkill extends Skill {
   private static final double MAGICULE_COST = 1000.0;
   private static final double AURA_DRAIN = 0.01;
   private static final double COPY_MASTERY = 0.5;
   private static final double COPY_RANGE = 10.0;
   private static final float COPY_CHANCE = 0.25F;
   private static final float MASTERED_COPY_CHANCE = 0.5F;
   private static final int COOLDOWN_SECONDS = 10;
   private static final ResourceLocation ICON = ResourceLocation.fromNamespaceAndPath("tno_tensura_extras", "textures/skill/unique/all_rounder.png");

   public AllRounderSkill() {
      super(SkillType.UNIQUE);
   }

   public ResourceLocation getSkillIcon() {
      return ICON;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return "default";
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return mode == 0 ? 1000.0 : 0.0;
   }

   public boolean onTouchEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (this.isInSlot(owner, instance)
         && owner.getRandom().nextBoolean()
         && EnergyHelper.drainEnergy(target, owner, 0.01, true, DrainType.AURA, GainType.NORMAL)
         && owner instanceof Player player) {
         player.playNotifySound((SoundEvent)TensuraSoundEvents.ENERGY_DRAIN.get(), SoundSource.PLAYERS, 0.5F, 1.0F);
      }

      return true;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (mode == 0 && !entity.level().isClientSide() && !instance.onCoolDown(mode)) {
         LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, 10.0, false);
         if (target != null && target.isAlive() && !(target instanceof Player player && player.getAbilities().invulnerable)) {
            ServerLevel level = (ServerLevel)entity.level();
            if (this.cannotCopyFrom(target)) {
               entity.sendSystemMessage(Component.translatable("tensura.targeting.not_allowed").withStyle(ChatFormatting.RED));
               playFailure(level, entity);
            } else {
               List<ManasSkillInstance> candidates = SkillAPI.getSkillsFrom(target).getLearnedSkills().stream().filter(this::canCopy).toList();
               if (candidates.isEmpty()) {
                  entity.sendSystemMessage(Component.translatable("tensura.ability.activation_failed.plunder.empty").withStyle(ChatFormatting.RED));
                  playFailure(level, entity);
               } else if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
                  instance.setCoolDown(10, mode);
                  entity.swing(InteractionHand.MAIN_HAND, true);
                  float chance = instance.isMastered(entity) ? 0.5F : 0.25F;
                  if (entity.getRandom().nextFloat() >= chance) {
                     if (entity instanceof Player player) {
                        player.displayClientMessage(Component.translatable("tensura.ability.activation_failed").withStyle(ChatFormatting.RED), true);
                     }

                     playFailure(level, entity);
                  } else {
                     ManasSkillInstance selected = candidates.get(entity.getRandom().nextInt(candidates.size()));
                     if (this.copySkill(instance, entity, target, selected.getSkill())) {
                        instance.addMasteryPoint(entity);
                     }
                  }
               }
            }
         }
      }
   }

   private boolean copySkill(ManasSkillInstance sourceInstance, LivingEntity entity, LivingEntity target, ManasSkill selectedSkill) {
      Changeable<ManasSkill> changeable = Changeable.of(selectedSkill);
      if (!((SkillPlunderEvent)TensuraSkillEvents.SKILL_PLUNDER.invoker()).plunder(target, entity, false, changeable).isFalse()
         && changeable.get() instanceof Battlewill) {
         ManasSkill copiedSkill = (ManasSkill)changeable.get();
         Skills storage = SkillAPI.getSkillsFrom(entity);
         ManasSkillInstance existing = (ManasSkillInstance)storage.getSkill(copiedSkill).orElse(null);
         if (existing == null) {
            TensuraSkillInstance copy = new TensuraSkillInstance(copiedSkill);
            copy.setMastery(copy.getMaxMastery() * 0.5);
            if (!SkillHelper.learnSkill(entity, copy, sourceInstance.getRemoveTime())) {
               entity.sendSystemMessage(
                  Component.translatable("tensura.ability.activation_failed.plunder", new Object[]{copy.getChatDisplayName(true)})
                     .withStyle(ChatFormatting.RED)
               );
               entity.playSound((SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), 1.0F, 1.0F);
               return false;
            }
         } else {
            double copiedMastery = existing.getMaxMastery() * 0.5;
            if (existing.getMastery() >= copiedMastery) {
               entity.sendSystemMessage(
                  Component.translatable("tensura.ability.activation_failed.plunder", new Object[]{existing.getChatDisplayName(true)})
                     .withStyle(ChatFormatting.RED)
               );
               entity.playSound((SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), 1.0F, 1.0F);
               return false;
            }

            existing.setMastery(copiedMastery);
         }

         entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS, 1.0F, 1.0F);
         return true;
      } else {
         return false;
      }
   }

   private boolean canCopy(ManasSkillInstance instance) {
      return instance.getSkill() instanceof Battlewill
         && !instance.isTemporarySkill()
         && instance.getMastery() >= 0.0
         && !instance.is(TensuraSkillTags.NO_PLUNDERING);
   }

   private boolean cannotCopyFrom(LivingEntity target) {
      return target.getType().is(TensuraEntityTags.NO_SKILL_PLUNDER)
         || SkillUtils.isSkillToggled(target, (ManasSkill)UniqueSkills.ANTI_SKILL.get())
         || TensuraStorages.getAbilityFrom(target).isAbilityInActivePreset((ManasSkill)UniqueSkills.ANTI_SKILL.get());
   }

   private static void playFailure(ServerLevel level, LivingEntity entity) {
      level.playSound(
         null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), SoundSource.PLAYERS, 1.0F, 1.0F
      );
   }
}
