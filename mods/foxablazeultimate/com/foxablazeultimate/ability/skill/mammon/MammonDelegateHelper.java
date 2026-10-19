package com.foxablazeultimate.ability.skill.mammon;

import com.foxablazeultimate.config.MammonConfig;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.intrinsic.CharmSkill;
import io.github.manasmods.tensura.config.ability.AbilityConfig;
import io.github.manasmods.tensura.config.ability.skill.UniqueSkillConfig;
import io.github.manasmods.tensura.config.ability.skill.UniqueSkillConfig.Greed;
import io.github.manasmods.tensura.damage.TensuraDamageSource;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.template.subclass.ISubordinate;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.event.TensuraEntityEvents.ForceTameEvent;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

final class MammonDelegateHelper {
   private static final String GREED_TAG = "mammon_greed";
   private static final int HEART_CONTROL_MODE = 0;
   private static final int DEATH_WISH_MODE = 2;

   private MammonDelegateHelper() {
   }

   static MammonDelegateHelper.DelegateResult onPressed(ManasSkillInstance parent, Skill greed, LivingEntity owner, int keyNumber, int mode) {
      TensuraSkillInstance child = read(parent, greed);
      double masteryBefore = child.getMastery();
      greed.onPressed(child, owner, keyNumber, mode);
      int cooldown = child.getCoolDown(mode);
      propagateMastery(parent, owner, child, masteryBefore);
      write(parent, child);
      return new MammonDelegateHelper.DelegateResult(cooldown > 0, cooldown);
   }

   static MammonDelegateHelper.DelegateResult onHeld(ManasSkillInstance parent, Skill greed, LivingEntity owner, int heldTicks, int mode) {
      TensuraSkillInstance child = read(parent, greed);
      double masteryBefore = child.getMastery();
      boolean active = mode == 0
         ? onHeartControlHeld(child, owner, heldTicks)
         : (mode == 2 ? onDeathWishHeld(child, greed, owner, heldTicks, mode) : greed.onHeld(child, owner, heldTicks, mode));
      int cooldown = child.getCoolDown(mode);
      propagateMastery(parent, owner, child, masteryBefore);
      write(parent, child);
      return new MammonDelegateHelper.DelegateResult(active, cooldown);
   }

   static void onRelease(ManasSkillInstance parent, Skill greed, LivingEntity owner, int heldTicks, int keyNumber, int mode) {
      TensuraSkillInstance child = read(parent, greed);
      greed.onRelease(child, owner, heldTicks, keyNumber, mode);
      CompoundTag tag = child.getOrCreateTag();
      tag.remove("target");
      tag.putInt("heldSeconds", 0);
      tag.putInt("targetDesire", 0);
      write(parent, child);
   }

   private static boolean onHeartControlHeld(TensuraSkillInstance instance, LivingEntity owner, int heldTicks) {
      if (owner.isShiftKeyDown()) {
         return false;
      }

      CompoundTag tag = instance.getOrCreateTag();
      int heldSeconds = tag.getInt("heldSeconds");
      Greed greedConfig = ((UniqueSkillConfig)ConfigRegistry.getConfig(UniqueSkillConfig.class)).Greed;
      LivingEntity target = (LivingEntity)ObjectSelectionHelper.getTargetingEntity(LivingEntity.class, owner, greedConfig.maxDistance, 0.5, true, true, false);
      if (target != null && (!tag.contains("target") || Objects.equals(tag.getUUID("target"), target.getUUID()))) {
         if (!CharmSkill.canMindControl(target, owner.level())) {
            owner.sendSystemMessage(Component.translatable("tensura.ability.activation_failed").withStyle(ChatFormatting.RED));
            owner.level()
               .playSound(
                  null, owner.getX(), owner.getY(), owner.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), SoundSource.PLAYERS, 0.5F, 1.0F
               );
            return false;
         }

         int masteryHoldTicks = Math.max(1, ((AbilityConfig)ConfigRegistry.getConfig(AbilityConfig.class)).Mastery.masteryHoldTick);
         if (heldTicks > 0 && heldTicks % masteryHoldTicks == 0) {
            instance.addMasteryPoint(owner);
         }

         int requiredSeconds = getHeartControlTime(owner, target, tag, greedConfig);
         if (heldTicks % 20 == 0) {
            tag.putInt("heldSeconds", heldSeconds + 1);
         }

         if (owner instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("tensura.skill.time_held.max", new Object[]{heldSeconds, requiredSeconds})
                  .setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)),
               true
            );
            player.playNotifySound((SoundEvent)TensuraSoundEvents.CAST_DARK.get(), SoundSource.PLAYERS, 0.2F, 1.0F);
         }

         if (heldSeconds >= requiredSeconds) {
            IExistence existence = TensuraStorages.getExistenceFrom(target);
            EventResult tame = ((ForceTameEvent)TensuraEntityEvents.FORCE_TAME_EVENT.invoker()).tame(target, owner, false);
            if (!Objects.equals(existence.getPermanentOwner(), owner.getUUID()) && !tame.isFalse()) {
               existence.setTemporaryOwner(owner.getUUID());
               existence.markDirty();
               if (target instanceof Mob mob) {
                  SubordinateHelper.removeTarget(mob);
               }

               if (target instanceof ISubordinate subordinate && owner instanceof Player player) {
                  subordinate.tame(player);
               }

               owner.swing(InteractionHand.MAIN_HAND, true);
               TensuraParticleHelper.addServerParticlesAroundSelf(target, ParticleTypes.RAID_OMEN);
               TensuraParticleHelper.addServerParticlesAroundSelf(target, ParticleTypes.RAID_OMEN, 2.0);
               owner.level()
                  .playSound(
                     null, owner.getX(), owner.getY(), owner.getZ(), (SoundEvent)TensuraSoundEvents.DEBUFF_ACTIVATE.get(), SoundSource.PLAYERS, 0.5F, 1.0F
                  );
            }

            resetHeartControlCharge(tag);
         }
      } else if (heldTicks % 20 == 0) {
         tag.putInt("heldSeconds", Math.max(0, heldSeconds - 1));
         if (tag.getInt("heldSeconds") <= 0) {
            tag.remove("target");
         }

         if (owner instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("tensura.skill.time_held", new Object[]{heldSeconds}).setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)), true
            );
         }
      }

      return true;
   }

   private static boolean onDeathWishHeld(TensuraSkillInstance instance, Skill greed, LivingEntity owner, int heldTicks, int mode) {
      CompoundTag tag = instance.getOrCreateTag();
      int heldSeconds = tag.getInt("heldSeconds");
      Greed greedConfig = ((UniqueSkillConfig)ConfigRegistry.getConfig(UniqueSkillConfig.class)).Greed;
      int requiredSeconds = Math.max(1, MammonConfig.settings().deathWishBaseChargeSeconds);
      double range = owner.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE);
      LivingEntity target = (LivingEntity)ObjectSelectionHelper.getTargetingEntity(LivingEntity.class, owner, range, 0.5, true, true, false);
      if (target != null && (!tag.contains("target") || Objects.equals(tag.getUUID("target"), target.getUUID()))) {
         if (target instanceof Player player && player.getAbilities().invulnerable) {
            return false;
         }

         if (heldTicks % 20 == 0) {
            tag.putInt("heldSeconds", heldSeconds + 1);
         }

         if (heldSeconds >= requiredSeconds) {
            tag.putInt("heldSeconds", 0);
            tag.remove("target");
            if (EnergyHelper.isOutOfEnergy(owner, instance, mode)) {
               return false;
            }

            instance.addMasteryPoint(owner);
            DamageSource source = ((TensuraDamageSource)greed.createSource(instance, owner, TensuraDamageTypes.DEATH_WISH, mode)).tensura$setDodgeBypass();
            if (target.hurt(source, target.getMaxHealth() * 10.0F)) {
               double size = target.getAttributeValue(Attributes.SCALE) * 4.0;
               TensuraParticleHelper.addServerAuraParticles(target, TensuraParticleUtils.getBlackAura(0.5F, (float)size, -0.3F), 10, 0.01);
               return false;
            }
         } else {
            target.addEffect(
               new MobEffectInstance(
                  TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE), 10, greedConfig.deathInterference - 1, false, false, false
               ),
               owner
            );
            TensuraParticleHelper.addServerParticlesAroundSelf(target, ParticleTypes.WHITE_ASH, 1.0);
            if (owner instanceof Player player) {
               player.displayClientMessage(
                  Component.translatable("tensura.skill.time_held.max", new Object[]{heldSeconds, requiredSeconds})
                     .setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)),
                  true
               );
            }
         }
      } else if (heldTicks % 20 == 0) {
         tag.putInt("heldSeconds", Math.max(0, heldSeconds - 1));
         if (tag.getInt("heldSeconds") <= 0) {
            tag.remove("target");
         }

         if (owner instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("tensura.skill.time_held", new Object[]{heldSeconds}).setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)), true
            );
         }
      }

      return true;
   }

   private static int getHeartControlTime(LivingEntity owner, LivingEntity target, CompoundTag tag, Greed greedConfig) {
      int base = Math.max(1, MammonConfig.settings().heartControlBaseChargeSeconds);
      int originalBase = Math.max(1, target instanceof Player ? greedConfig.playerControl : greedConfig.entityControl);
      int required = base;
      double distance = target.distanceTo(owner);
      if (distance < greedConfig.closeDistance) {
         required -= scaleControlTime(greedConfig.closeControl, base, originalBase);
      } else if (distance > greedConfig.farDistance) {
         required += scaleControlTime(greedConfig.farControl, base, originalBase);
      }

      required -= scaleControlTime(Math.max(0, tag.getInt("targetDesire")), base, originalBase);
      return Math.max(1, required);
   }

   private static int scaleControlTime(int seconds, int newBase, int originalBase) {
      return seconds <= 0 ? 0 : Math.max(1, (int)Math.round((double)seconds * newBase / originalBase));
   }

   private static void resetHeartControlCharge(CompoundTag tag) {
      tag.remove("target");
      tag.putInt("heldSeconds", 0);
      tag.putInt("targetDesire", 0);
   }

   static boolean onDamageEntity(ManasSkillInstance parent, Skill greed, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      TensuraSkillInstance child = read(parent, greed);
      double masteryBefore = child.getMastery();
      boolean result = greed.onDamageEntity(child, owner, target, source, amount);
      propagateMastery(parent, owner, child, masteryBefore);
      write(parent, child);
      return result;
   }

   private static TensuraSkillInstance read(ManasSkillInstance parent, ManasSkill skill) {
      CompoundTag parentTag = parent.getOrCreateTag();
      CompoundTag saved = parentTag.contains("mammon_greed", 10) ? parentTag.getCompound("mammon_greed") : new CompoundTag();
      TensuraSkillInstance child = new TensuraSkillInstance(skill);
      if (!saved.isEmpty()) {
         child.deserialize(saved);
      }

      if (child.getMastery() < parent.getMastery()) {
         child.setMastery(parent.getMastery());
      }

      return child;
   }

   private static void write(ManasSkillInstance parent, TensuraSkillInstance child) {
      parent.getOrCreateTag().put("mammon_greed", child.toNBT());
      parent.markDirty();
   }

   private static void propagateMastery(ManasSkillInstance parent, LivingEntity owner, TensuraSkillInstance child, double masteryBefore) {
      double delta = child.getMastery() - masteryBefore;
      if (delta > 0.0) {
         parent.getSkill().addMasteryPoint(parent, owner, delta);
      }
   }

   record DelegateResult(boolean active, int cooldown) {
   }
}
