package com.github.dominickwd04.traddon.ability.skill.unique;

import com.github.dominickwd04.traddon.config.ability.skill.TrAddonSkillConfig;
import com.github.dominickwd04.traddon.registry.effect.TrAddonMobEffects;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.EntityEvents.ProjectileHitResult;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.extra.HakiSkill;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.entity.magic.field.haki.HakiField.HakiVariant;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

public class DevastatorSkill extends Skill {
   public static final TrAddonSkillConfig.Devastator CONFIG = ((TrAddonSkillConfig)ConfigRegistry.getConfig(TrAddonSkillConfig.class)).devastator;
   protected static final ResourceLocation DEVASTATOR = ResourceLocation.fromNamespaceAndPath("traddon", "devastator");

   public DevastatorSkill() {
      super(SkillType.UNIQUE);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensura", "textures/skill/unique/wrath.png");
   }

   public double getDefaultAcquiringMagiculeCost() {
      return CONFIG.mpAcquirement;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      TensuraMobEffect.addEffect(
         entity, new MobEffectInstance(TrAddonMobEffects.getReference(TrAddonMobEffects.ATOMIC_ANNIHILATION), 2400, 0, false, false, false), entity, this
      );
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      if (entity.hasEffect(TrAddonMobEffects.getReference(TrAddonMobEffects.POST_MORTEM_SURGE))) {
         entity.removeEffect(TrAddonMobEffects.getReference(TrAddonMobEffects.POST_MORTEM_SURGE));
      }

      if (entity.hasEffect(TrAddonMobEffects.getReference(TrAddonMobEffects.ATOMIC_ANNIHILATION))) {
         entity.removeEffect(TrAddonMobEffects.getReference(TrAddonMobEffects.ATOMIC_ANNIHILATION));
      }
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return mode == 0 ? CONFIG.tyrantsHakiCost : CONFIG.endOfDaysCost;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "devastator.tyrants_haki";
         case 1 -> "devastator.end_of_days";
         default -> super.getModeId(instance, mode);
      };
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      instance.onToggleOff(entity);
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return mode == 0 ? 1 : 0;
   }

   public boolean onTouchEntity(ManasSkillInstance instance, LivingEntity entity, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (!this.isInSlot(entity, instance)) {
         return true;
      }

      if (source.getDirectEntity() == entity && TensuraDamageHelper.isPhysicalAttack(source)) {
         int durabilityBreak = (int)Math.max(1.0, (double)((Float)amount.get()).floatValue() * CONFIG.armorDurability);

         for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack slotStack = target.getItemBySlot(slot);
            slotStack.hurtAndBreak(durabilityBreak, target, slot);
         }
      }

      return true;
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      boolean inSlot = this.isInSlot(owner, instance);
      if (target.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.FEAR))
         || target.hasEffect(TrAddonMobEffects.getReference(TrAddonMobEffects.DESPAIR))) {
         TensuraMobEffect.addEffect(
            target, new MobEffectInstance(TrAddonMobEffects.getReference(TrAddonMobEffects.DESPAIR), 100, 0, false, false, false), owner, this
         );
      }

      if (TensuraDamageHelper.isPhysicalAttack(source) && inSlot) {
         amount.set((Float)amount.get() * (float)CONFIG.physicalDamageMultiplier);
         float spiritualMult = instance.isMastered(owner) ? (float)CONFIG.masterDamageMultiplier : (float)CONFIG.spiritualDamageMultiplier;
         TensuraDamageHelper.directSpiritualHurt(target, owner, (Float)amount.get() * spiritualMult);
      }

      return true;
   }

   public boolean onTakenDamage(ManasSkillInstance instance, LivingEntity owner, DamageSource source, Changeable<Float> amount) {
      if (!this.isInSlot(owner, instance)) {
         return true;
      }

      if (source.getEntity() instanceof LivingEntity attacker) {
         attacker.getItemInHand(InteractionHand.MAIN_HAND).hurtAndBreak(CONFIG.weaponDurability, attacker, EquipmentSlot.MAINHAND);
      }

      return true;
   }

   public void onProjectileHit(
      ManasSkillInstance instance,
      LivingEntity entity,
      EntityHitResult hitResult,
      Projectile projectile,
      Changeable<ProjectileDeflection> deflection,
      Changeable<ProjectileHitResult> result
   ) {
      if (instance.isToggled()) {
         result.set(ProjectileHitResult.PASS);
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (mode == 0) {
         instance.getOrCreateTag().putInt("HakiID", 0);
         instance.markDirty();
      } else if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         this.castEndOfDays(instance, entity);
         return;
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (mode != 0) {
         return false;
      }

      if (heldTicks % 20 == 0 && EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         return false;
      }

      if (heldTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0 && heldTicks > 0) {
         instance.addMasteryPoint(entity);
      }

      return HakiSkill.summonHaki(instance, entity, mode, heldTicks, this, HakiVariant.DEMON_LORD, CONFIG.epDifferenceMultiplier);
   }

   private void castEndOfDays(ManasSkillInstance instance, LivingEntity user) {
      double radius = CONFIG.endOfDaysRadius;
      double userEP = EnergyHelper.getMaxEP(user);
      RandomSource rnd = user.getRandom();

      for (LivingEntity t : user.level().getEntitiesOfClass(LivingEntity.class, user.getBoundingBox().inflate(radius), e -> e != user && !e.isAlliedTo(user))) {
         double tEP = EnergyHelper.getMaxEP(t);
         if (tEP < userEP * 0.75) {
            double resistFactor = Math.min(0.9, tEP / Math.max(1.0, userEP));
            double chance = 1.0 - resistFactor;
            if (rnd.nextDouble() < chance) {
               TensuraDamageHelper.directSpiritualHurt(t, user, (float)(t.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH) * 2.0));
            } else {
               TensuraDamageHelper.directSpiritualHurt(t, user, (float)(t.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH) * 0.5));
            }
         } else {
            TensuraDamageHelper.directSpiritualHurt(t, user, (float)(t.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH) * 0.1F));
         }
      }

      user.level()
         .playSound(
            (Player)null, user.getX(), user.getY(), user.getZ(), (SoundEvent)TensuraSoundEvents.TRANSFORM_BERSERKER.get(), SoundSource.PLAYERS, 2.0F, 0.85F
         );
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return instance.isToggled();
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (entity.tickCount % 20 == 0) {
         TensuraMobEffect.addEffect(
            entity, new MobEffectInstance(TrAddonMobEffects.getReference(TrAddonMobEffects.ATOMIC_ANNIHILATION), 2400, 0, false, false, false), entity, this
         );
      }
   }
}
