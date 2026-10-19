package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.ability.AbilitySlot;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class CorroderSkill extends Skill {
   protected static final UniqueMysticismSkillConfig.Corroder CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Corroder;

   public CorroderSkill() {
      super(SkillType.UNIQUE);
   }

   public double getAcquiringMagiculeCost(ManasSkillInstance instance) {
      return CONFIG.mpAcquirement;
   }

   public boolean onEffectAdded(ManasSkillInstance instance, LivingEntity entity, @Nullable Entity source, Changeable<MobEffectInstance> effect) {
      return !this.isInSlot(entity) ? true : !((MobEffectInstance)effect.get()).getEffect().is(TensuraMobEffects.getReference(TensuraMobEffects.CORROSION));
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/corroder.png");
   }

   public static UniqueMysticismSkillConfig.Corroder getCONFIG() {
      return CONFIG;
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return mode == 0 ? 1 : 0;
   }

   public List<Integer> getModeLearningList(ManasSkillInstance instance) {
      return List.of(1);
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "corroder.dissolve";
         case 1 -> "corroder.cross_corrosion";
         default -> super.getModeId(instance, mode);
      };
   }

   public boolean onTouchEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (instance.isToggled() && TensuraDamageHelper.isPhysicalAttack(source)) {
         Holder<MobEffect> corrosion = TensuraMobEffects.getReference(TensuraMobEffects.CORROSION);
         int corrosionDuration = instance.isMastered(owner) ? CONFIG.meleeCorrosionDurationMastered : CONFIG.meleeCorrosionDuration;
         int corrosionLevel = instance.isMastered(owner) ? CONFIG.meleeCorrosionLevelMastered : CONFIG.meleeCorrosionLevel;
         target.addEffect(new MobEffectInstance(corrosion, corrosionDuration, corrosionLevel - 1, false, false, false), owner);
      }

      return true;
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> CONFIG.dissolveCost;
         case 1 -> CONFIG.crossCorrosionCost;
         default -> 0.0;
      };
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity attacker, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      ItemStack item = attacker.getMainHandItem();
      if (!this.activatedMeltingSlash(instance, attacker)) {
         return true;
      }

      if (instance.onCoolDown(1)) {
         return true;
      }

      if (source.getEntity() != attacker) {
         return true;
      }

      if (!TensuraDamageHelper.isPhysicalAttack(source)) {
         return true;
      }

      if (EnergyHelper.isOutOfEnergy(attacker, instance, 1)) {
         return true;
      }

      if (item.is(ItemTags.SWORDS)) {
         Float boxed = (Float)amount.get();
         amount.set(0.0F);
         if (boxed != null && !(boxed <= 0.0F)) {
            float damage = (float)(boxed.floatValue() * CONFIG.crossCorrosionMultiplier);
            attacker.level()
               .playSound(
                  null,
                  attacker.getX(),
                  attacker.getY(),
                  attacker.getZ(),
                  (SoundEvent)TensuraSoundEvents.ACID_SIZZLE.get(),
                  TensuraSkill.ABILITY_SOUND,
                  1.0F,
                  1.0F
               );
            source = this.createSource(instance, attacker, TensuraDamageTypes.CORROSION, 1);
            target.hurt(source, damage);
            instance.setCoolDown(instance.isMastered(target) ? CONFIG.crossCorrosionCooldownMastered : CONFIG.crossCorrosionCooldown, 1);
            return target.isAlive();
         } else {
            return true;
         }
      } else {
         return true;
      }
   }

   private boolean activatedMeltingSlash(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      return tag.getBoolean("CrossCorrosionActivated");
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (mode == 1 && !this.learnMode(instance, entity, mode)) {
         CompoundTag tag = instance.getOrCreateTag();
         boolean activated = tag.getBoolean("CrossCorrosionActivated");
         tag.putBoolean("CrossCorrosionActivated", !activated);
         entity.swing(InteractionHand.MAIN_HAND, true);
         entity.level()
            .playSound(
               null,
               entity.getX(),
               entity.getY(),
               entity.getZ(),
               activated ? (SoundEvent)TensuraSoundEvents.BUFF_DEACTIVATE.get() : (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(),
               TensuraSkill.ABILITY_SOUND,
               1.0F,
               1.0F
            );
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

      Level level = entity.level();
      level.playSound(
         null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.ACID_SIZZLE.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F
      );
      double size = entity.getAttributeValue(Attributes.SCALE) * 4.0;
      TensuraParticleHelper.addServerAuraParticles(entity, TensuraParticleUtils.getAcidAura(1.0F, (float)size, -0.3F), 3, 0.01);
      if (heldTicks % 10 == 0) {
         List<LivingEntity> list = level.getEntitiesOfClass(
            LivingEntity.class,
            entity.getBoundingBox().inflate(instance.isMastered(entity) ? CONFIG.dissolveRadiusMastered : CONFIG.dissolveRadius),
            living -> !living.is(entity) && living.isAlive() && !living.isAlliedTo(entity)
         );
         if (!list.isEmpty()) {
            for (LivingEntity target : list) {
               DamageSource source = this.createSource(instance, entity, TensuraDamageTypes.CORROSION, mode);
               if (target.hurt(source, instance.isMastered(entity) ? CONFIG.dissolveDamageMastered : CONFIG.dissolveDamage)) {
                  int corrosionDuration = instance.isMastered(entity) ? CONFIG.dissolveCorrosionDurationMastered : CONFIG.dissolveCorrosionDuration;
                  int corrosionLevel = instance.isMastered(entity) ? CONFIG.dissolveCorrosionLevelMastered : CONFIG.dissolveCorrosionLevel;
                  TensuraMobEffect.addEffect(
                     target,
                     TensuraMobEffects.getReference(TensuraMobEffects.CORROSION),
                     corrosionDuration,
                     corrosionLevel,
                     false,
                     false,
                     true,
                     entity.getUUID(),
                     AbilitySlot.fromNBT(instance.getOrCreateTag())
                  );
               }
            }
         }
      }

      return true;
   }
}
