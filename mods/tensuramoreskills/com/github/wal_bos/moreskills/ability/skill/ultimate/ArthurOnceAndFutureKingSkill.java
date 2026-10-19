package com.github.wal_bos.moreskills.ability.skill.ultimate;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.handler.arthur.ArthurActions;
import com.github.wal_bos.moreskills.handler.arthur.ArthurRuntimeHandler;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import com.github.wal_bos.moreskills.support.RoyalSkillSupport;
import com.github.wal_bos.moreskills.util.SkillObtainmentHelper;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.EntityEvents.ProjectileHitEvent;
import io.github.manasmods.manascore.skill.api.EntityEvents.ProjectileHitResult;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraEntityEvents.LivingPostDamageEvent;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillPlunderEvent;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

public class ArthurOnceAndFutureKingSkill extends Skill {
   private static final ThreadLocal<Boolean> ADDITIONAL_DAMAGE = ThreadLocal.withInitial(() -> false);
   private static final ThreadLocal<Boolean> SUPPRESS_AUTHORITY_CONVERSION = ThreadLocal.withInitial(() -> false);
   private static boolean initialized;

   public ArthurOnceAndFutureKingSkill() {
      super(SkillType.ULTIMATE);
      init();
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         ArthurActions.init();
         ArthurRuntimeHandler.init();
         TensuraSkillEvents.SKILL_PLUNDER
            .register(
               (SkillPlunderEvent)(target, owner, steal, skill) -> skill.get() instanceof ArthurOnceAndFutureKingSkill
                  ? EventResult.interruptFalse()
                  : EventResult.pass()
            );
         EntityEvents.PROJECTILE_HIT
            .register(
               (ProjectileHitEvent)(result, projectile, deflection, hitResult) -> {
                  if (result instanceof EntityHitResult entityHit && entityHit.getEntity() instanceof LivingEntity target) {
                     if (projectile.getOwner() instanceof LivingEntity owner && RoyalSkillSupport.validEnemy(owner, target)) {
                        boolean authority = SkillAPI.getSkillsFrom(owner).getSkill((ManasSkill)AllSkills.ARTHUR_ONCE_AND_FUTURE_KING.get()).isPresent()
                           || SkillAPI.getSkillsFrom(owner).getSkill((ManasSkill)AllSkills.AVALON_GOD_OF_ETERNAL_KINGDOM.get()).isPresent();
                        if (authority) {
                           deflection.set(ProjectileDeflection.NONE);
                           hitResult.set(ProjectileHitResult.HIT);
                        }
                     }
                  }
               }
            );
         TensuraEntityEvents.LIVING_POST_DAMAGE.register((LivingPostDamageEvent)(target, source, amount) -> {
            if (source.getEntity() instanceof LivingEntity attacker) {
               RoyalSkillSupport.markCombat(attacker, target);
               if (!ADDITIONAL_DAMAGE.get() && !SUPPRESS_AUTHORITY_CONVERSION.get()) {
                  Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(attacker).getSkill((ManasSkill)AllSkills.ARTHUR_ONCE_AND_FUTURE_KING.get());
                  if (!optional.isEmpty()) {
                     ManasSkillInstance instance = optional.get();

                     try {
                        ADDITIONAL_DAMAGE.set(true);
                        if (TensuraDamageHelper.isBattlewill(source, attacker)) {
                           RoyalSkillSupport.unavoidableDamage(target, RoyalSkillSupport.source(attacker, instance, 0, TensuraDamageTypes.HOLY_DAMAGE), amount);
                        } else if (source.is(TensuraDamageTypes.HOLY_DAMAGE)) {
                           RoyalSkillSupport.unavoidableDamage(target, RoyalSkillSupport.source(attacker, instance, 0, TensuraDamageTypes.AURA_SLASH), amount);
                        }
                     } finally {
                        ADDITIONAL_DAMAGE.set(false);
                     }
                  }
               }
            }
         });
      }
   }

   public static void runWithoutAuthorityConversion(Runnable action) {
      boolean previous = SUPPRESS_AUTHORITY_CONVERSION.get();
      SUPPRESS_AUTHORITY_CONVERSION.set(true);

      try {
         action.run();
      } finally {
         SUPPRESS_AUTHORITY_CONVERSION.set(previous);
      }
   }

   private static boolean qualifiesForCrown(DamageSource source, LivingEntity attacker) {
      if (source.is(TensuraDamageTypes.HOLY_DAMAGE)) {
         return true;
      } else if (TensuraDamageHelper.isLightDamage(source)) {
         return true;
      } else {
         return TensuraDamageHelper.isPhysicalAttack(source) ? true : TensuraDamageHelper.isBattlewill(source, attacker);
      }
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/ultimate/arthur_once_and_future_king.png");
   }

   public double getDefaultAcquiringMagiculeCost() {
      return (Double)MoreSkillsConfigs.GRAND.arthur.acquirementMagiculeCost.get();
   }

   public int getAcquirementMastery(LivingEntity entity) {
      return 0;
   }

   public int getMaxMastery() {
      return (Integer)MoreSkillsConfigs.GRAND.arthur.maxMastery.get();
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return SkillObtainmentHelper.canAcquire(
         entity,
         (Double)MoreSkillsConfigs.GRAND.arthur.acquirementMagiculeCost.get(),
         (String)MoreSkillsConfigs.GRAND.arthur.requiredSkillId.get(),
         (Boolean)MoreSkillsConfigs.GRAND.arthur.requiredSkillMastered.get()
      );
   }

   public int getModes(ManasSkillInstance instance) {
      return ArthurActions.getModes(instance);
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return ArthurActions.nextMode(entity, instance, mode, reverse);
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return ArthurActions.getModeId(instance, mode, super.getModeId(instance, mode));
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return ArthurActions.getMagiculeCost(entity, instance, mode);
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      ArthurActions.onPressed(instance, entity, keyNumber, mode);
   }

   public int getMaxHeldTime(ManasSkillInstance instance, LivingEntity entity) {
      return 72000;
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      return ArthurActions.onHeld(instance, entity, heldTicks, mode);
   }

   public boolean shouldTriggerReleaseOnHeldInterrupt(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      return ArthurActions.shouldTriggerReleaseOnHeldInterrupt(instance, entity, keyNumber, mode);
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      ArthurActions.onRelease(instance, entity, heldTicks, keyNumber, mode);
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity entity, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      RoyalSkillSupport.markCombat(entity, target);
      if (ADDITIONAL_DAMAGE.get()) {
         return true;
      }

      int stacks = ArthurRuntimeHandler.crownStacks(instance);
      if (stacks > 0 && qualifiesForCrown(source, entity)) {
         amount.set((Float)amount.get() * (1.0F + stacks * 0.05F));
      }

      return true;
   }

   public boolean onEffectAdded(ManasSkillInstance instance, LivingEntity entity, @Nullable Entity source, Changeable<MobEffectInstance> effect) {
      MobEffectInstance effectInstance = (MobEffectInstance)effect.get();
      if (effectInstance != null && instance.isToggled()) {
         Holder<MobEffect> holder = effectInstance.getEffect();
         if (holder.equals(TensuraMobEffects.getReference(TensuraMobEffects.FEAR))) {
            return false;
         } else if (holder.equals(TensuraMobEffects.getReference(TensuraMobEffects.HYPNOSIS))) {
            return false;
         } else {
            return holder.equals(TensuraMobEffects.getReference(TensuraMobEffects.MIND_CONTROL))
               ? false
               : !holder.equals(TensuraMobEffects.getReference(TensuraMobEffects.LUST_EMBRACEMENT));
         }
      } else {
         return true;
      }
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      super.onToggleOn(instance, entity);
      ArthurRuntimeHandler.onArthurToggleOn(instance, entity);
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      super.onToggleOff(instance, entity);
      ArthurRuntimeHandler.onArthurToggleOff(entity);
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onLearnSkill(instance, entity);
      ArthurRuntimeHandler.applyArthurImmediately(instance, entity);
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      super.onTick(instance, entity);
      if (!(entity instanceof ServerPlayer)) {
         ArthurRuntimeHandler.tickArthur(instance, entity);
      }
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      ArthurActions.cleanup(entity);
      ArthurRuntimeHandler.cleanupArthur(entity);
   }
}
