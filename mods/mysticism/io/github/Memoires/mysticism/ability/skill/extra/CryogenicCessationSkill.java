package io.github.Memoires.mysticism.ability.skill.extra;

import io.github.Memoires.mysticism.config.ability.skill.ExtraMysticismSkillConfig;
import io.github.Memoires.mysticism.registry.attributes.MysticismAttributes;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.entity.magic.breath.BreathEntity;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillGriefEvent;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.entity.MiscEntityTypes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.world.TensuraGameRules;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CryogenicCessationSkill extends Skill {
   public static final ExtraMysticismSkillConfig.CryogenicCessation CONFIG = ((ExtraMysticismSkillConfig)ConfigRegistry.getConfig(
         ExtraMysticismSkillConfig.class
      ))
      .CryogenicCessation;

   public CryogenicCessationSkill() {
      super(SkillType.EXTRA);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/extra/cryogenic_cessation.png");
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> CONFIG.iceBreathMPCost;
         case 1 -> CONFIG.freezingPointMPCost;
         default -> 0.0;
      };
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "cryogenic_cessation.ice_breath";
         case 1 -> "cryogenic_cessation.freezing_point";
         default -> super.getModeId(instance, mode);
      };
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return true;
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.multiplyElementalBoost(entity, MysticismAttributes.COLD_BOOST, CONFIG.cryogenicCessationBoost);
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.removeElementalMultiplier(entity, MysticismAttributes.COLD_BOOST, CONFIG.cryogenicCessationBoost);
   }

   public boolean onTouchEntity(ManasSkillInstance instance, LivingEntity attacker, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (!instance.isToggled()) {
         return true;
      }

      if (source.getDirectEntity() != attacker) {
         return true;
      }

      if (!TensuraDamageHelper.isPhysicalAttack(source)) {
         return true;
      }

      Level level = attacker.level();
      Holder<MobEffect> chill = TensuraMobEffects.getReference(TensuraMobEffects.CHILL);
      TensuraMobEffect.addEffect(
         target,
         new MobEffectInstance(chill, CONFIG.duration, (instance.isMastered(attacker) ? CONFIG.levelMastered : CONFIG.level) - 1, true, true, true),
         attacker,
         this
      );
      level.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.PLAYER_HURT_FREEZE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
      ((ServerLevel)level)
         .sendParticles(
            ParticleTypes.SNOWFLAKE, target.position().x, target.position().y + target.getBbHeight() / 2.0, target.position().z, 20, 0.08, 0.08, 0.08, 0.15
         );
      CompoundTag tag = instance.getOrCreateTag();
      int time = tag.getInt("activatedTimes");
      if (time % BASE_CONFIG.Mastery.masteryActivateTime == 0) {
         instance.addMasteryPoint(attacker);
      }

      tag.putInt("activatedTimes", time + 1);
      return true;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode == 0 ? 1 : mode - 1;
      } else {
         return (mode + 1) % this.getModes(instance);
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (mode == 0) {
         if (heldTicks % 20 == 0 && EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            return false;
         }

         if (heldTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0 && heldTicks > 0) {
            instance.addMasteryPoint(entity);
         }

         float damage = instance.isMastered(entity) ? CONFIG.damageMastered : CONFIG.damage;
         BreathEntity.spawnBreathEntity((EntityType)MiscEntityTypes.ICE_BREATH.get(), entity, instance, damage, this, mode);
         if (heldTicks % 2 == 0) {
            entity.level()
               .playSound(
                  null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.CAST_ICE.get(), TensuraSkill.ABILITY_SOUND, 0.75F, 1.0F
               );
         }
      }

      return true;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      Level level = entity.level();
      if (mode == 1 && !EnergyHelper.isOutOfEnergy(entity, instance, mode) && TensuraGameRules.canSkillGrief(level)) {
         BlockPos pos = entity.getOnPos();
         int radius = CONFIG.radius;
         boolean success = false;

         for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
               for (int y = -radius * 2; y <= 0; y++) {
                  BlockPos newPos = pos.offset(x, y, z);
                  BlockState currentState = level.getBlockState(newPos);
                  if (!currentState.isAir() && currentState.getDestroySpeed(level, newPos) >= 0.0F) {
                     int griefX = newPos.getX();
                     int griefY = newPos.getY();
                     int griefZ = newPos.getZ();
                     if (!((SkillGriefEvent)TensuraSkillEvents.SKILL_GRIEF_PRE.invoker()).grief(instance, level, entity, griefX, griefY, griefZ).isFalse()) {
                        ((SkillGriefEvent)TensuraSkillEvents.SKILL_GRIEF_POS.invoker()).grief(instance, level, entity, griefX, griefY, griefZ);
                        level.setBlock(newPos, Blocks.ICE.defaultBlockState(), 3);
                        success = true;
                     }
                  }
               }
            }
         }

         if (success) {
            this.addMasteryPoint(instance, entity);
            entity.swing(InteractionHand.MAIN_HAND);
            instance.setCoolDown(instance.isMastered(entity) ? CONFIG.freezingPointCooldownMastered : CONFIG.freezingPointCooldown, mode);
         }
      }
   }
}
