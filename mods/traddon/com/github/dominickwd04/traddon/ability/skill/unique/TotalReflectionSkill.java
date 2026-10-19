package com.github.dominickwd04.traddon.ability.skill.unique;

import com.github.dominickwd04.traddon.config.ability.skill.TrAddonSkillConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.magic.aspectual.AspectualMagic;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageSource;
import io.github.manasmods.tensura.data.TensuraTags.DamageTypes;
import io.github.manasmods.tensura.entity.projectile.magic.ObsidianShotProjectile;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import org.jetbrains.annotations.Nullable;

public class TotalReflectionSkill extends Skill {
   public static final TrAddonSkillConfig.TotalReflection CONFIG = ((TrAddonSkillConfig)ConfigRegistry.getConfig(TrAddonSkillConfig.class)).TotalReflection;
   public static final ResourceLocation REFLECTION = ResourceLocation.fromNamespaceAndPath("traddon", "reflection");

   public TotalReflectionSkill() {
      super(SkillType.UNIQUE);
      this.addHeldAttributeModifier(Attributes.MOVEMENT_SPEED, REFLECTION, CONFIG.moveSpeed, Operation.ADD_MULTIPLIED_TOTAL);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensura", "textures/skill/unique/reflector.png");
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return mode == 0 ? 1 : 0;
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return mode == 0 ? CONFIG.vectorReversalMagiculeCost : CONFIG.prismaticFractureCost;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "total_reflection.full_vector_reversal";
         case 1 -> "total_reflection.prismatic_fracture";
         default -> super.getModeId(instance, mode);
      };
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public boolean canBeSlotted(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return true;
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (entity.tickCount % 20 == 0) {
         CompoundTag tag = instance.getOrCreateTag();
         if (!tag.contains("StoredMagicules")) {
            tag.putDouble("StoredMagicules", 0.0);
         }

         double storedMagicules = tag.getDouble("StoredMagicules");
         if (storedMagicules > 0.0) {
            IExistence existence = TensuraStorages.getExistenceFrom(entity);
            double maxMagicules = EnergyHelper.getMaxMagicule(entity);
            double toRestore = Math.min(storedMagicules, maxMagicules - existence.getMagicule());
            if (toRestore > 0.0) {
               double maxRestore = maxMagicules * CONFIG.maxRestoredPerSecond;
               existence.setMagicule(existence.getMagicule() + Math.min(maxRestore, toRestore));
               storedMagicules -= Math.min(maxRestore, toRestore);
               tag.putDouble("StoredMagicules", storedMagicules);
            }
         }
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

      return true;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (mode == 1) {
         CompoundTag tag = instance.getOrCreateTag();
         if (!tag.contains("storedDamage")) {
            tag.putFloat("storedDamage", 0.0F);
         }

         if (EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            return;
         }

         float storedDamage = tag.getFloat("storedDamage");
         if (storedDamage > 0.0F) {
            float damagePerProjectile = storedDamage * 0.75F;

            for (int i = 0; i < 5; i++) {
               ObsidianShotProjectile needle = new ObsidianShotProjectile(entity.level(), entity);
               needle.setDamage(damagePerProjectile);
               needle.setSkill(entity, instance, this, mode);
               needle.setPosAndShoot(entity);
               needle.setSpeed(2.5F);
               needle.setDeltaMovement(
                  needle.getDeltaMovement()
                     .add(
                        (entity.getRandom().nextFloat() - 0.5F) * 0.5F,
                        (entity.getRandom().nextFloat() - 0.5F) * 0.5F,
                        (entity.getRandom().nextFloat() - 0.5F) * 0.5F
                     )
               );
               entity.level().addFreshEntity(needle);
            }

            tag.putFloat("storedDamage", 0.0F);
            int cooldown = CONFIG.prismaticFractureCooldown;
            instance.setCoolDown(cooldown, mode);
         }
      }
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      if (!tag.contains("StoredMagicules")) {
         tag.putDouble("StoredMagicules", 0.0);
      }
   }

   public boolean onBeingDamaged(ManasSkillInstance instance, LivingEntity entity, DamageSource source, float amount) {
      if (instance.isToggled()) {
         if (TensuraDamageHelper.isTensuraMagic(source)) {
            double magiculeAbsorbed = 100.0;
            if (source instanceof TensuraDamageSource tensuraSource) {
               magiculeAbsorbed = tensuraSource.tensura$getMagiculeCost();
               if (tensuraSource.tensura$getAbilityInstance() != null) {
                  ManasSkillInstance copy = TensuraSkillInstance.fromNBT(tensuraSource.tensura$getAbilityInstance().toNBT());
                  copy.setRemoveTime(0);
                  if (copy.getParentSkill() instanceof AspectualMagic) {
                     SkillHelper.learnSkill(entity, copy, 0);
                  }
               }
            }

            double maxStore = EnergyHelper.getMaxMagicule(entity) * 2.0;
            CompoundTag tag = instance.getOrCreateTag();
            double storedMagicules = tag.getDouble("StoredMagicules");
            if (storedMagicules >= maxStore) {
               return true;
            }

            storedMagicules += magiculeAbsorbed;
            tag.putDouble("StoredMagicules", Math.min(maxStore, storedMagicules));
         } else if (TensuraDamageHelper.isPhysicalAttack(source) && hasReflection(entity) && source.getEntity() instanceof LivingEntity attacker) {
            double mpCost = amount * CONFIG.vectorReversalMagiculeCost;
            if (EnergyHelper.isOutOfEnergy(entity, 0.0, mpCost)) {
               return true;
            }

            attacker.hurt(source, amount);
            return false;
         }
      }

      return true;
   }

   public static boolean hasReflection(LivingEntity entity) {
      AttributeInstance attributeInstance = entity.getAttribute(Attributes.MOVEMENT_SPEED);
      return attributeInstance == null ? false : attributeInstance.getModifier(REFLECTION) != null;
   }

   public boolean onTakenDamage(ManasSkillInstance instance, LivingEntity entity, DamageSource source, Changeable<Float> amount) {
      if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         return true;
      }

      if (source.is(DamageTypes.BYPASS_DIMENSION_FAULT)) {
         return true;
      }

      if (!TensuraDamageHelper.isSeveranceDamage(source, entity, true) && !TensuraDamageHelper.isSpiritual(source)) {
         CompoundTag tag = instance.getOrCreateTag();
         if (!tag.contains("storedDamage")) {
            tag.putFloat("storedDamage", 0.0F);
         }

         float storedDamage = tag.getFloat("storedDamage");
         storedDamage += amount.get();
         tag.putFloat("storedDamage", Math.min(storedDamage, CONFIG.maxStoredDamage));
         return true;
      } else {
         return true;
      }
   }
}
