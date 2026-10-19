package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.entity.projectile.StasisShotProjectile;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.manasmods.manascore.attribute.api.ManasCoreAttributes;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.data.TensuraTags.DamageTypes;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.option.SimpleAuraParticleOptions;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class StagnatorSkill extends Skill {
   protected static final UniqueMysticismSkillConfig.Stagnator CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class))
      .Stagnator;
   protected static final ResourceLocation STAGNATOR = ResourceLocation.fromNamespaceAndPath("mysticism", "stagnator");

   public StagnatorSkill() {
      super(SkillType.UNIQUE);
      this.addHeldAttributeModifier(Attributes.MOVEMENT_SPEED, STAGNATOR, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addHeldAttributeModifier(Attributes.FLYING_SPEED, STAGNATOR, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addHeldAttributeModifier(Attributes.ATTACK_DAMAGE, STAGNATOR, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addHeldAttributeModifier(Attributes.ATTACK_SPEED, STAGNATOR, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addHeldAttributeModifier(Attributes.JUMP_STRENGTH, STAGNATOR, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addHeldAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE, STAGNATOR, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addHeldAttributeModifier(Attributes.JUMP_STRENGTH, STAGNATOR, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addHeldAttributeModifier(Attributes.FOLLOW_RANGE, STAGNATOR, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addHeldAttributeModifier(ManasCoreAttributes.SWIM_SPEED_MULTIPLIER, STAGNATOR, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/stagnator.png");
   }

   public int getModes(ManasSkillInstance instance) {
      return 4;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode - 1 < 0 ? this.getModes(instance) - 1 : mode - 1;
      } else {
         return (mode + 1) % this.getModes(instance);
      }
   }

   public double getAcquiringMagiculeCost(ManasSkillInstance instance) {
      return CONFIG.mpAcquirement;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "stagnator.stagnate_aura";
         case 1 -> "stagnator.inaction";
         case 2 -> "stagnator.stasis_coat";
         case 3 -> "stagnator.stasis_shot";
         default -> super.getModeId(instance, mode);
      };
   }

   public List<Integer> getModeLearningList(ManasSkillInstance instance) {
      return List.of(3);
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> CONFIG.stagnateAuraCost;
         case 1 -> CONFIG.inactionCost;
         default -> 0.0;
         case 3 -> CONFIG.stasisShotCost;
      };
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity living, int heldTicks, int mode) {
      if (heldTicks % 20 == 0 && EnergyHelper.isOutOfEnergy(living, instance, mode)) {
         return false;
      }

      return switch (mode) {
         case 0 -> this.stagnateAura(instance, living, heldTicks, mode);
         case 1 -> this.inaction(instance, living, heldTicks);
         default -> super.onHeld(instance, living, heldTicks, mode);
      };
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      switch (mode) {
         case 2:
            this.stasisCoat(entity, mode);
            break;
         case 3:
            this.stasisShot(instance, entity, mode);
            break;
         default:
            super.onPressed(instance, entity, keyNumber, mode);
      }
   }

   private boolean stagnateAura(ManasSkillInstance instance, LivingEntity living, int heldTicks, int mode) {
      living.level()
         .playSound(
            null, living.getX(), living.getY(), living.getZ(), (SoundEvent)TensuraSoundEvents.ENERGY_DRAIN.get(), TensuraSkill.ABILITY_SOUND, 0.5F, 0.5F
         );
      double size = living.getAttributeValue(Attributes.SCALE) * 4.0;
      TensuraParticleHelper.addServerAuraParticles(living, new SimpleAuraParticleOptions(0.75F, 0.75F, 0.75F, 0.5F, (float)size, -0.3F), 5, 0.01, 3.0F);
      List<LivingEntity> targets = living.level()
         .getEntitiesOfClass(
            LivingEntity.class,
            living.getBoundingBox().inflate(CONFIG.stagnateAuraRadius),
            entity -> !entity.is(living) && entity.isAlive() && !entity.isAlliedTo(living)
         );
      if (!targets.isEmpty()) {
         if (heldTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0 && heldTicks > 0) {
            instance.addMasteryPoint(living);
         }

         for (LivingEntity target : targets) {
            if (!(target instanceof Player player && player.getAbilities().invulnerable)) {
               TensuraMobEffect.addEffect(
                  target, new MobEffectInstance(TensuraMobEffects.getReference(MysticismMobEffects.STAGNATE), 21, 0, false, true, true), living, this, mode
               );
               TensuraMobEffect.addEffect(
                  target, new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.SILENCE), 21, 0, false, false, false), living, this, mode
               );
            }
         }
      }

      return true;
   }

   private boolean inaction(ManasSkillInstance instance, LivingEntity living, int heldTicks) {
      if (heldTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0 && heldTicks > 0) {
         instance.addMasteryPoint(living);
      }

      return true;
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      switch (mode) {
         case 0:
            instance.setCoolDown(instance.isMastered(entity) ? CONFIG.stagnateAuraCooldownMastered : CONFIG.stagnateAuraCooldown, mode);
            instance.addMasteryPoint(entity);
            break;
         case 1:
            instance.setCoolDown(instance.isMastered(entity) ? CONFIG.inactionCooldownMastered : CONFIG.inactionCooldown, mode);
            instance.addMasteryPoint(entity);
      }
   }

   public boolean onTakenDamage(ManasSkillInstance instance, LivingEntity owner, DamageSource source, Changeable<Float> amount) {
      return !hasInaction(owner) ? true : source.is(DamageTypes.IS_SPIRITUAL);
   }

   public static boolean hasInaction(LivingEntity entity) {
      AttributeInstance attributeInstance = entity.getAttribute(Attributes.MOVEMENT_SPEED);
      return attributeInstance == null ? false : attributeInstance.getModifier(STAGNATOR) != null;
   }

   public void addHeldAttributeModifiers(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (mode == 1) {
         super.addHeldAttributeModifiers(instance, entity, mode);
      }
   }

   private void stasisCoat(LivingEntity entity, int mode) {
      if (entity.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.SEVERANCE_BLADE))) {
         entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.SEVERANCE_BLADE));
         entity.level()
            .playSound(
               null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_DEACTIVATE.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F
            );
      } else {
         entity.swing(InteractionHand.MAIN_HAND, true);
         entity.level()
            .playSound(
               null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F
            );
         TensuraMobEffect.addEffect(
            entity, new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.SEVERANCE_BLADE), 100, 0, false, false, false), entity, this, mode
         );
      }
   }

   private void stasisShot(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (!this.learnMode(instance, entity, mode)) {
         StasisShotProjectile stasisShot = new StasisShotProjectile(entity.level(), entity);
         stasisShot.setSkill(entity, instance, this, mode);
         stasisShot.setSpeed(4.5F);
         stasisShot.setNoGravity(true);
         stasisShot.setDamage(instance.isMastered(entity) ? CONFIG.stasisShotMasteredDamage : CONFIG.stasisShotDamage);
         stasisShot.setPosAndShoot(entity);
         stasisShot.setElement(Element.UNIDENTIFIED);
         entity.level().addFreshEntity(stasisShot);
         entity.swing(InteractionHand.MAIN_HAND, true);
         instance.setCoolDown(CONFIG.stasisShotCooldown, mode);
      }
   }
}
