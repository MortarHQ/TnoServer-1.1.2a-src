package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.effect.ability.FixationEffect;
import io.github.Memoires.mysticism.entity.skill.EternalDomainEntity;
import io.github.Memoires.mysticism.entity.skill.SolidificationShieldEntity;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class CoalescenceSkill extends Skill {
   protected static final UniqueMysticismSkillConfig.Coalescence CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(
         UniqueMysticismSkillConfig.class
      ))
      .Coalescence;

   public CoalescenceSkill() {
      super(SkillType.UNIQUE);
   }

   public double getAcquiringMagiculeCost(ManasSkillInstance instance) {
      return CONFIG.mpAcquirement;
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> CONFIG.fixationCost;
         case 1 -> CONFIG.solidificationCost;
         case 2 -> CONFIG.eternalDomainCost;
         default -> 500.0;
      };
   }

   public List<Integer> getModeLearningList(ManasSkillInstance instance) {
      return List.of(2);
   }

   public boolean canIgnoreCoolDown(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return mode == 1 && entity.isShiftKeyDown();
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return (mode + (reverse ? 2 : 1)) % 3;
   }

   public int getModes(ManasSkillInstance instance) {
      return 3;
   }

   public boolean onEffectAdded(ManasSkillInstance instance, LivingEntity entity, @Nullable Entity source, Changeable<MobEffectInstance> effect) {
      if (!instance.isToggled()) {
         return true;
      }

      MobEffectInstance effectInstance = (MobEffectInstance)effect.get();
      MobEffect mobEffect = effectInstance == null ? null : (MobEffect)effectInstance.getEffect().value();
      return mobEffect == MobEffects.MOVEMENT_SLOWDOWN.value() ? false : mobEffect != TensuraMobEffects.getReference(TensuraMobEffects.BURDEN).value();
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/coalescence.png");
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "coalescence.fixation";
         case 1 -> "coalescence.solidification";
         case 2 -> "coalescence.eternal_domain";
         default -> super.getModeId(instance, mode);
      };
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      entity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
      entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.BURDEN));
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      Level level = entity.level();
      switch (mode) {
         case 0:
            if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
               int range = instance.isMastered(entity) ? CONFIG.fixationRangeMastery : CONFIG.fixationRange;
               LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, range, false, true);
               if (target == null) {
                  if (entity instanceof Player player) {
                     player.displayClientMessage(
                        Component.translatable("mysticism.skill.mode.coalescence.fixation.no_target").withStyle(ChatFormatting.RED), false
                     );
                     player.playNotifySound((SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
                  }

                  instance.setCoolDown(instance.isMastered(entity) ? CONFIG.fixationMissCooldownMastered : CONFIG.fixationMissCooldown, mode);
               } else if (!target.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.FIXATION))) {
                  int duration = instance.isMastered(entity) ? CONFIG.fixationDurationMastered : CONFIG.fixationDuration;
                  MobEffectInstance fixation = new MobEffectInstance(
                     TensuraMobEffects.getReference(MysticismMobEffects.FIXATION), duration, 0, false, false, false
                  );
                  if (FixationEffect.tryApply(target, fixation)) {
                     instance.addMasteryPoint(entity);
                     instance.setCoolDown(instance.isMastered(entity) ? CONFIG.fixationCooldownMastered : CONFIG.fixationCooldown, mode);
                     level.playSound(
                        null,
                        target.getX(),
                        target.getY(),
                        target.getZ(),
                        (SoundEvent)TensuraSoundEvents.DEBUFF_ACTIVATE.get(),
                        TensuraSkill.ABILITY_SOUND,
                        1.0F,
                        1.0F
                     );
                     if (target instanceof Player targetPlayer) {
                        targetPlayer.displayClientMessage(
                           Component.translatable("mysticism.skill.mode.coalescence.fixation.being_fixated").withStyle(ChatFormatting.RED), false
                        );
                     }
                  }
               }
            }
            break;
         case 1:
            if (entity.isShiftKeyDown()) {
               SolidificationShieldEntity target = (SolidificationShieldEntity)ObjectSelectionHelper.getTargetingEntity(
                  SolidificationShieldEntity.class, entity, 5.0, 0.1, false, true
               );
               if (target != null && target.getOwner() == entity) {
                  target.discard();
                  entity.swing(InteractionHand.MAIN_HAND, true);
                  level.playSound(null, target.getX(), target.getY(), target.getZ(), TensuraSoundEvents.GENERIC_UNCAST, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               }
            } else if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
               BlockHitResult result = ObjectSelectionHelper.getPlayerPOVHitResult(level, entity, Fluid.NONE, Block.OUTLINE, 3.0);
               Vec3 pos = result.getLocation();
               Direction direction = entity.getNearestViewDirection();
               SolidificationShieldEntity barrier = new SolidificationShieldEntity(level, entity);
               if (!direction.getAxis().isVertical()) {
                  pos = pos.add(0.0, barrier.getSize() / 2.0F, 0.0);
               }

               barrier.setPos(pos);
               barrier.setSkill(instance);
               barrier.setFacingDirection(direction);
               barrier.setApCost(this.getAuraCost(entity, instance, mode));
               barrier.setMpCost(this.getMagiculeCost(entity, instance, mode));
               barrier.setLife(instance.isMastered(entity) ? CONFIG.solidificationShieldDurationMastered : CONFIG.solidificationShieldDuration);
               level.addFreshEntity(barrier);
               instance.addMasteryPoint(entity);
               int cooldown = instance.isMastered(entity) ? CONFIG.solidificationCooldownMastered : CONFIG.solidificationCooldown;
               instance.setCoolDown(cooldown, mode);
               entity.swing(InteractionHand.MAIN_HAND, true);
               level.playSound(
                  null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), TensuraSkill.ABILITY_SOUND, 0.5F, 0.5F
               );
            }
            break;
         case 2:
            if (!this.learnMode(instance, entity, mode)) {
               EternalDomainEntity field = new EternalDomainEntity(level, entity);
               int life = instance.isMastered(entity) ? CONFIG.eternalDomainLifeMastered : CONFIG.eternalDomainLife;
               int size = instance.isMastered(entity) ? CONFIG.eternalDomainRangeMastered : CONFIG.eternalDomainRange;
               field.setLife(life);
               field.setSize(size / 2.0F);
               field.setSkill(entity, instance, this, mode);
               field.setPos(entity.position().add(0.0, -1 * size / 2.0, 0.0));
               level.addFreshEntity(field);
               level.playSound(
                  null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), TensuraSkill.ABILITY_SOUND, 0.5F, 0.5F
               );
               instance.addMasteryPoint(entity);
               instance.setCoolDown(instance.isMastered(entity) ? CONFIG.eternalDomainCooldownMastered : CONFIG.eternalDomainCooldown, mode);
            }
      }
   }
}
