package io.github.Memoires.mysticism.ability.skill.intrinsic;

import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.impl.TickingSkill;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.config.ability.skill.IntrinsicSkillConfig;
import io.github.manasmods.tensura.config.ability.skill.IntrinsicSkillConfig.ElementalTransform;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.effect.template.ITransformation;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.particle.option.SimpleAuraParticleOptions;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import org.jetbrains.annotations.Nullable;

public class PoisonTransformSkill extends Skill implements ITransformation {
   protected static final ElementalTransform CONFIG = ((IntrinsicSkillConfig)ConfigRegistry.getConfig(IntrinsicSkillConfig.class)).ElementalTransform;

   public PoisonTransformSkill() {
      super(SkillType.INTRINSIC);
      this.addHeldAttributeModifier(
         Attributes.MOVEMENT_SPEED,
         ResourceLocation.fromNamespaceAndPath("mysticism", "poison_transform"),
         CONFIG.speedMultiplier - 1.0,
         Operation.ADD_MULTIPLIED_TOTAL
      );
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/intrinsic/poison_transform.png");
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return CONFIG.magiculeCost;
   }

   protected void applyVisualEffect(LivingEntity entity) {
      entity.level()
         .playSound(
            null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BREATH_WATER.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F
         );
      TensuraParticleHelper.addServerParticlesAroundSelf(entity, TensuraParticleUtils.getPoisonBubble(), 1.0);
      double size = entity.getAttributeValue(Attributes.SCALE) * 4.0;
      TensuraParticleHelper.addServerAuraParticles(entity, new SimpleAuraParticleOptions(0.52F, 0.0F, 0.84F, 1.0F, (float)size, -0.3F), 5, 0.01);
   }

   protected void onDamageEntity(ManasSkillInstance instance, LivingEntity user, LivingEntity target, DamageSource source) {
      Holder<MobEffect> poison = TensuraMobEffects.getReference(TensuraMobEffects.FATAL_POISON);
      TensuraMobEffect.addEffect(target, new MobEffectInstance(poison, CONFIG.effectDuration, 0, false, false, false), user, this);
      target.hurt(source, CONFIG.damage);
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (TickingSkill.isTickingSkill(owner, this) && TensuraDamageHelper.isPhysicalAttack(source)) {
         Holder<MobEffect> poison = TensuraMobEffects.getReference(TensuraMobEffects.FATAL_POISON);
         TensuraMobEffect.addEffect(target, new MobEffectInstance(poison, CONFIG.effectDuration, 1, false, false, false), owner, this);
      }

      return true;
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (this.failedToActivate(entity, null)) {
         return false;
      }

      if (heldTicks % 20 == 0 && EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         return false;
      }

      if (heldTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0 && heldTicks > 0) {
         instance.addMasteryPoint(entity);
      }

      this.applyVisualEffect(entity);
      List<LivingEntity> list = entity.level()
         .getEntitiesOfClass(
            LivingEntity.class,
            entity.getBoundingBox().inflate(CONFIG.radius),
            targetx -> !targetx.is(entity) && targetx.isAlive() && !targetx.isAlliedTo(entity)
         );
      if (!list.isEmpty()) {
         DamageSource damageSource = this.createSource(instance, entity, TensuraDamageTypes.POISONOUS_BREATH, mode);

         for (LivingEntity target : list) {
            this.onDamageEntity(instance, entity, target, damageSource);
         }
      }

      return true;
   }
}
