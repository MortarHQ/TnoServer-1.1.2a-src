package io.github.Memoires.mysticism.ability.skill.intrinsic;

import io.github.Memoires.mysticism.config.ability.skill.IntrinsicMysticismSkillConfig;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.effect.template.ITransformation;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class LightningModeSkill extends Skill implements ITransformation {
   private static final IntrinsicMysticismSkillConfig.LightningMode CONFIG = ((IntrinsicMysticismSkillConfig)ConfigRegistry.getConfig(
         IntrinsicMysticismSkillConfig.class
      ))
      .LightningMode;

   public LightningModeSkill() {
      super(SkillType.INTRINSIC);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/intrinsic/lightning_mode.png");
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return CONFIG.magiculeCost;
   }

   public boolean canIgnoreCoolDown(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return this.canTick(instance, entity);
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.LIGHTNING_MODE));
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      int time = tag.getInt("activatedTimes");
      if (time % BASE_CONFIG.Mastery.masteryActivateTime == 0) {
         instance.addMasteryPoint(entity);
      }

      tag.putInt("activatedTimes", time + 1);
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      if (this.canTick(instance, entity)) {
         entity.removeEffect(TensuraMobEffects.getReference(MysticismMobEffects.LIGHTNING_MODE));
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!this.failedToActivate(entity, TensuraMobEffects.getReference(MysticismMobEffects.LIGHTNING_MODE))) {
         if (!entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.LIGHTNING_MODE))) {
            if (EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
               return;
            }

            instance.addMasteryPoint(entity);
            instance.setCoolDown(CONFIG.cooldown, mode);
            IExistence existence = TensuraStorages.getExistenceFrom(entity);
            existence.setMagicule(existence.getMagicule() * 2.0);
            existence.setAura(existence.getAura() * 2.0);
            existence.markDirty();
            entity.level()
               .playSound(
                  null,
                  entity.getX(),
                  entity.getY(),
                  entity.getZ(),
                  (SoundEvent)TensuraSoundEvents.TRANSFORM_DRAGON.get(),
                  TensuraSkill.ABILITY_SOUND,
                  1.0F,
                  1.0F
               );
            TensuraMobEffect.addEffect(
               entity,
               new MobEffectInstance(
                  TensuraMobEffects.getReference(MysticismMobEffects.LIGHTNING_MODE),
                  instance.isMastered(entity) ? CONFIG.transformationDurationMastered : CONFIG.transformationDuration,
                  0,
                  false,
                  false,
                  false
               ),
               entity,
               this,
               mode
            );
            TensuraParticleHelper.addServerParticlesAroundSelf(entity, (ParticleOptions)TensuraParticleTypes.YELLOW_LIGHTNING_SPARK.get(), 3.0);
            TensuraParticleHelper.spawnServerParticles(
               entity.level(),
               TensuraParticleUtils.getGoldWave(1.0F, entity.getBbWidth() * 7.0F, -0.5F, false),
               entity.getX(),
               entity.getY() + entity.getBbHeight() / 2.0F,
               entity.getZ()
            );
            TensuraParticleHelper.spawnServerParticles(
               entity.level(),
               (ParticleOptions)TensuraParticleTypes.YELLOW_LIGHTNING_SPARK.get(),
               entity.getX(),
               entity.getY(),
               entity.getZ(),
               25,
               0.08,
               0.08,
               0.08,
               0.5,
               true
            );
         } else {
            entity.removeEffect(TensuraMobEffects.getReference(MysticismMobEffects.LIGHTNING_MODE));
            entity.level()
               .playSound(
                  null,
                  entity.getX(),
                  entity.getY(),
                  entity.getZ(),
                  (SoundEvent)TensuraSoundEvents.BUFF_DEACTIVATE.get(),
                  TensuraSkill.ABILITY_SOUND,
                  1.0F,
                  1.0F
               );
         }
      }
   }
}
