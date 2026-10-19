package io.github.Memoires.mysticism.ability.skill.extra;

import io.github.Memoires.mysticism.config.ability.skill.ExtraMysticismSkillConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.entity.magic.breath.BreathEntity;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillGriefEvent;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.entity.MiscEntityTypes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.world.TensuraGameRules;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ProfanedProminenceSkill extends Skill {
   public static final ExtraMysticismSkillConfig.ProfanedProminence CONFIG = ((ExtraMysticismSkillConfig)ConfigRegistry.getConfig(
         ExtraMysticismSkillConfig.class
      ))
      .ProfanedProminence;

   public ProfanedProminenceSkill() {
      super(SkillType.EXTRA);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/extra/profaned_prominence.png");
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> CONFIG.fireBreathMPCost;
         case 1 -> CONFIG.providenceMPCost;
         default -> 0.0;
      };
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "profaned_prominence.fire_breath";
         case 1 -> "profaned_prominence.providence";
         default -> super.getModeId(instance, mode);
      };
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return instance.getMastery() >= 0.0;
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.multiplyElementalBoost(entity, TensuraAttributes.FLAME_BOOST, CONFIG.profanedProminenceBoost);
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.removeElementalMultiplier(entity, TensuraAttributes.FLAME_BOOST, CONFIG.profanedProminenceBoost);
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode == 0 ? 1 : mode - 1;
      } else {
         return (mode + 1) % this.getModes(instance);
      }
   }

   public boolean onTouchEntity(ManasSkillInstance instance, LivingEntity attacker, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (!instance.isToggled()) {
         return true;
      }

      if (source.getDirectEntity() != attacker) {
         return true;
      }

      if (!TensuraDamageHelper.isPhysicalAttack(source) && !(source.getEntity() instanceof AbstractArrow)) {
         return true;
      }

      target.setRemainingFireTicks(instance.isMastered(attacker) ? CONFIG.prominenceBurnTick * 2 : CONFIG.prominenceBurnTick);
      attacker.level()
         .playSound(null, target.getX(), target.getY(), target.getZ(), (SoundEvent)TensuraSoundEvents.CAST_FIRE.get(), TensuraSkill.ABILITY_SOUND, 0.5F, 1.0F);
      TensuraParticleHelper.addServerParticlesAroundSelf(target, ParticleTypes.FLAME, 1.0);
      return true;
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
         BreathEntity.spawnBreathEntity((EntityType)MiscEntityTypes.FLAME_BREATH.get(), entity, instance, damage, this, mode);
         if (heldTicks % 2 == 0) {
            entity.level()
               .playSound(
                  null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.BREATH_FIRE.get(), TensuraSkill.ABILITY_SOUND, 0.75F, 1.0F
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
         boolean waterDetected = false;

         for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
               for (int y = -radius * 2; y <= 0; y++) {
                  BlockPos newPos = pos.offset(x, y, z);
                  BlockState currentState = level.getBlockState(newPos);
                  if (currentState.is(Blocks.WATER)) {
                     waterDetected = true;
                     break;
                  }
               }

               if (waterDetected) {
                  break;
               }
            }

            if (waterDetected) {
               break;
            }
         }

         if (!waterDetected) {
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
                           level.setBlock(newPos, Blocks.LAVA.defaultBlockState(), 3);
                           success = true;
                        }
                     }
                  }
               }
            }

            if (success) {
               this.addMasteryPoint(instance, entity);
               entity.swing(InteractionHand.MAIN_HAND, true);
               instance.setCoolDown(instance.isMastered(entity) ? CONFIG.providenceCooldownMastered : CONFIG.providenceCooldown, mode);
            }
         }
      }
   }
}
