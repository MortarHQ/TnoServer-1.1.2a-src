package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class ReducerSkill extends Skill {
   public static final UniqueMysticismSkillConfig.Reducer CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Reducer;
   private static final ResourceLocation REDUCER = ResourceLocation.fromNamespaceAndPath("mysticism", "reducer");

   public ReducerSkill() {
      super(SkillType.UNIQUE);
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return mode == 0 ? 1 : 0;
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/reducer.png");
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      AttributeInstance mpRegen = entity.getAttribute(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER);
      if (mpRegen != null) {
         mpRegen.addOrReplacePermanentModifier(new AttributeModifier(REDUCER, CONFIG.magiculeRegenerationMultiplierBuff, Operation.ADD_VALUE));
      }

      AttributeInstance apRegen = entity.getAttribute(TensuraAttributes.AURA_REGENERATION_MULTIPLIER);
      if (apRegen != null) {
         apRegen.addOrReplacePermanentModifier(new AttributeModifier(REDUCER, CONFIG.auraRegenerationMultiplierBuff, Operation.ADD_VALUE));
      }
   }

   public void onRespawn(ManasSkillInstance instance, ServerPlayer owner, boolean conqueredEnd) {
      if (!conqueredEnd) {
         AttributeInstance mpRegen = owner.getAttribute(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER);
         if (mpRegen != null) {
            mpRegen.addOrReplacePermanentModifier(new AttributeModifier(REDUCER, CONFIG.magiculeRegenerationMultiplierBuff, Operation.ADD_VALUE));
         }

         AttributeInstance apRegen = owner.getAttribute(TensuraAttributes.AURA_REGENERATION_MULTIPLIER);
         if (apRegen != null) {
            apRegen.addOrReplacePermanentModifier(new AttributeModifier(REDUCER, CONFIG.auraRegenerationMultiplierBuff, Operation.ADD_VALUE));
         }
      }
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.addPermanentAttribute(
         entity,
         TensuraAttributes.MAGIC_COST_MULTIPLIER,
         REDUCER,
         instance.isMastered(entity) ? -CONFIG.purificationReductionMastered : -CONFIG.purificationReduction,
         Operation.ADD_VALUE
      );
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.removeAttribute(entity, TensuraAttributes.MAGIC_COST_MULTIPLIER, REDUCER);
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      this.onToggleOff(instance, entity);
      AttributeInstance mpRegen = entity.getAttribute(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER);
      if (mpRegen != null) {
         mpRegen.removeModifier(REDUCER);
      }

      AttributeInstance apRegen = entity.getAttribute(TensuraAttributes.AURA_REGENERATION_MULTIPLIER);
      if (apRegen != null) {
         apRegen.removeModifier(REDUCER);
      }
   }

   public double getAcquiringMagiculeCost(ManasSkillInstance instance) {
      return CONFIG.mpAcquisition;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "reducer.emancipation";
         case 1 -> "reducer.purity_edge";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> CONFIG.emancipationCost;
         case 1 -> CONFIG.purityEdgeCost;
         default -> 2000.0;
      };
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (mode == 0) {
         if (!entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.REDUCER_HOLY_COAT))) {
            if (EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
               return;
            }

            MobEffectInstance holyCoat = new MobEffectInstance(
               TensuraMobEffects.getReference(MysticismMobEffects.REDUCER_HOLY_COAT), -1, 0, false, false, false
            );
            holyCoat.tensura$setSourceAbility(instance.getSkill(), mode);
            TensuraParticleHelper.spawnServerParticles(
               entity.level(),
               TensuraParticleUtils.getColorlessReversedWave(0.9F, entity.getBbWidth() * 3.0F),
               entity.getX(),
               entity.getY() + entity.getBbHeight() * 0.33,
               entity.getZ()
            );
            TensuraParticleHelper.spawnServerParticles(
               entity.level(),
               TensuraParticleUtils.getColorlessReversedWave(0.9F, entity.getBbWidth() * 3.0F),
               entity.getX(),
               entity.getY() + entity.getBbHeight() * 0.66,
               entity.getZ()
            );
            if (entity instanceof Player player) {
               player.playNotifySound((SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
            }

            entity.addEffect(holyCoat);
         } else {
            entity.removeEffect(TensuraMobEffects.getReference(MysticismMobEffects.REDUCER_HOLY_COAT));
         }
      } else if (!entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.REDUCER_PURITY_EDGE))) {
         if (EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            return;
         }

         int duration = instance.isMastered(entity) ? CONFIG.purityEdgeRecoilTimeMastered : CONFIG.purityEdgeRecoilTime;
         MobEffectInstance holyCoat = new MobEffectInstance(
            TensuraMobEffects.getReference(MysticismMobEffects.REDUCER_PURITY_EDGE), duration, 0, false, false, false
         );
         holyCoat.tensura$setSourceAbility(instance.getSkill(), mode);
         TensuraParticleHelper.spawnServerParticles(
            entity.level(),
            TensuraParticleUtils.getColorlessReversedWave(0.9F, entity.getBbWidth() * 3.0F),
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.33,
            entity.getZ()
         );
         TensuraParticleHelper.spawnServerParticles(
            entity.level(),
            TensuraParticleUtils.getColorlessReversedWave(0.9F, entity.getBbWidth() * 3.0F),
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.66,
            entity.getZ()
         );
         if (entity instanceof Player player) {
            player.playNotifySound((SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
         }

         entity.addEffect(holyCoat);
      } else {
         entity.removeEffect(TensuraMobEffects.getReference(MysticismMobEffects.REDUCER_PURITY_EDGE));
         TensuraParticleHelper.spawnServerParticles(
            entity.level(),
            TensuraParticleUtils.getColorlessReversedWave(0.9F, entity.getBbWidth() * 3.0F),
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.33,
            entity.getZ()
         );
         TensuraParticleHelper.spawnServerParticles(
            entity.level(),
            TensuraParticleUtils.getColorlessReversedWave(0.9F, entity.getBbWidth() * 3.0F),
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.66,
            entity.getZ()
         );
      }
   }
}
