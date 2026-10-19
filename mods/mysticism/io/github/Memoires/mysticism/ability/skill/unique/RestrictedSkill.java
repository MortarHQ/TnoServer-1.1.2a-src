package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.sound.MysticismSoundEvents;
import io.github.manasmods.manascore.attribute.api.ManasCoreAttributes;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.battlewill.Battlewill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.data.TensuraBlockTags;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class RestrictedSkill extends Skill {
   protected static final UniqueMysticismSkillConfig.Restricted CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class))
      .Restricted;
   private static final ResourceLocation RESTRICTED = ResourceLocation.fromNamespaceAndPath("mysticism", "restricted");

   public RestrictedSkill() {
      super(SkillType.UNIQUE);
   }

   public double getAcquiringMagiculeCost(ManasSkillInstance instance) {
      return CONFIG.mpAcquirement;
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/restricted.png");
   }

   public double getAuraCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return mode == 0 ? 500.0 : 1500.0;
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return mode == 0 ? 1 : 0;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "restricted.dash";
         case 1 -> "restricted.counter";
         default -> super.getModeId(instance, mode);
      };
   }

   public void onRespawn(ManasSkillInstance instance, ServerPlayer owner, boolean conqueredEnd) {
      if (!conqueredEnd) {
         AttributeInstance mpGain = owner.getAttribute(TensuraAttributes.MAGICULE_GAIN);
         if (mpGain != null) {
            mpGain.addOrReplacePermanentModifier(new AttributeModifier(RESTRICTED, -10000.0, Operation.ADD_VALUE));
         }

         AttributeInstance apGain = owner.getAttribute(TensuraAttributes.AURA_GAIN);
         if (apGain != null) {
            apGain.addOrReplacePermanentModifier(new AttributeModifier(RESTRICTED, 2.0, Operation.ADD_MULTIPLIED_TOTAL));
         }

         AttributeInstance apRegeneration = owner.getAttribute(TensuraAttributes.AURA_REGENERATION_MULTIPLIER);
         if (apRegeneration != null) {
            apRegeneration.addOrReplacePermanentModifier(new AttributeModifier(RESTRICTED, 2.0, Operation.ADD_MULTIPLIED_TOTAL));
         }

         if (instance.isMastered(owner)) {
            AttributeInstance degrade = owner.getAttribute(TensuraAttributes.RESISTANCE_DEGRADATION);
            if (degrade != null) {
               degrade.addOrReplacePermanentModifier(new AttributeModifier(RESTRICTED, 1.0, Operation.ADD_VALUE));
            }
         }
      }
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      AttributeInstance mpGain = entity.getAttribute(TensuraAttributes.MAGICULE_GAIN);
      if (mpGain != null) {
         mpGain.addOrReplacePermanentModifier(new AttributeModifier(RESTRICTED, -10000.0, Operation.ADD_VALUE));
      }

      AttributeInstance apGain = entity.getAttribute(TensuraAttributes.AURA_GAIN);
      if (apGain != null) {
         apGain.addOrReplacePermanentModifier(new AttributeModifier(RESTRICTED, 2.0, Operation.ADD_MULTIPLIED_TOTAL));
      }

      AttributeInstance apRegeneration = entity.getAttribute(TensuraAttributes.AURA_REGENERATION_MULTIPLIER);
      if (apRegeneration != null) {
         apRegeneration.addOrReplacePermanentModifier(new AttributeModifier(RESTRICTED, 2.0, Operation.ADD_MULTIPLIED_TOTAL));
      }
   }

   public void onSkillMastered(ManasSkillInstance instance, LivingEntity entity) {
      AttributeInstance degrade = entity.getAttribute(TensuraAttributes.RESISTANCE_DEGRADATION);
      if (degrade != null) {
         degrade.addOrReplacePermanentModifier(new AttributeModifier(RESTRICTED, 1.0, Operation.ADD_VALUE));
      }
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      double boost = this.getBoost(instance);
      int flatBoost = this.getFlatBoost(instance);
      AttributeInstance speed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
      if (speed != null) {
         speed.addOrReplacePermanentModifier(new AttributeModifier(RESTRICTED, boost, Operation.ADD_MULTIPLIED_TOTAL));
      }

      AttributeInstance sneakingSpeed = entity.getAttribute(Attributes.SNEAKING_SPEED);
      if (sneakingSpeed != null) {
         sneakingSpeed.addOrReplacePermanentModifier(new AttributeModifier(RESTRICTED, boost, Operation.ADD_MULTIPLIED_TOTAL));
      }

      AttributeInstance safeFallDistance = entity.getAttribute(Attributes.SAFE_FALL_DISTANCE);
      if (safeFallDistance != null) {
         safeFallDistance.addOrReplacePermanentModifier(new AttributeModifier(RESTRICTED, boost, Operation.ADD_MULTIPLIED_TOTAL));
      }

      AttributeInstance armor = entity.getAttribute(Attributes.ARMOR);
      if (armor != null) {
         armor.addOrReplacePermanentModifier(new AttributeModifier(RESTRICTED, flatBoost, Operation.ADD_VALUE));
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      AttributeInstance speed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
      if (speed != null) {
         speed.removeModifier(RESTRICTED);
      }

      AttributeInstance sneakingSpeed = entity.getAttribute(Attributes.SNEAKING_SPEED);
      if (sneakingSpeed != null) {
         sneakingSpeed.removeModifier(RESTRICTED);
      }

      AttributeInstance safeFallDistance = entity.getAttribute(Attributes.SAFE_FALL_DISTANCE);
      if (safeFallDistance != null) {
         safeFallDistance.removeModifier(RESTRICTED);
      }

      AttributeInstance armor = entity.getAttribute(Attributes.ARMOR);
      if (armor != null) {
         armor.removeModifier(RESTRICTED);
      }
   }

   private double getBoost(ManasSkillInstance instance) {
      double m = instance.getMastery();
      int bucket = (int)Math.floor(m / 111.0) + 1;
      double boost = 0.2 * bucket;
      return Math.min(boost, CONFIG.maximumStatBoost);
   }

   private int getFlatBoost(ManasSkillInstance instance) {
      double m = instance.getMastery();
      int bucket = (int)Math.floor(m / 111.0) + 1;
      int boost = 2 * bucket;
      return (int)Math.min(boost, CONFIG.maximumFlatStatBoost);
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (!instance.isToggled()) {
         return true;
      }

      if (source.tensura$getAbilityInstance() != null && source.tensura$getAbilityInstance().getSkill() instanceof Battlewill) {
         amount.set((float)(((Float)amount.get()).floatValue() * CONFIG.battlewillMultiplier));
      } else if (TensuraDamageHelper.isPhysicalAttack(source) && source.getDirectEntity() == source.getEntity()) {
         amount.set((float)(((Float)amount.get()).floatValue() + owner.getAttributeValue(Attributes.ATTACK_DAMAGE) * (CONFIG.physicalAttackMultiplier - 1.0)));
      }

      return true;
   }

   public boolean onEffectAdded(ManasSkillInstance instance, LivingEntity entity, @Nullable Entity source, Changeable<MobEffectInstance> effect) {
      if (!this.isInSlot(entity)) {
         return true;
      } else if (((MobEffectInstance)effect.get()).is(MobEffects.MOVEMENT_SLOWDOWN)) {
         return false;
      } else {
         return ((MobEffectInstance)effect.get()).is(TensuraMobEffects.getReference(TensuraMobEffects.FEAR))
            ? false
            : !((MobEffectInstance)effect.get()).is(TensuraMobEffects.getReference(TensuraMobEffects.BURDEN));
      }
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      AttributeInstance mpGain = entity.getAttribute(TensuraAttributes.MAGICULE_GAIN);
      if (mpGain != null) {
         mpGain.removeModifier(RESTRICTED);
      }

      AttributeInstance apGain = entity.getAttribute(TensuraAttributes.AURA_GAIN);
      if (apGain != null) {
         apGain.removeModifier(RESTRICTED);
      }

      if (instance.isMastered(entity)) {
         AttributeInstance degrade = entity.getAttribute(TensuraAttributes.RESISTANCE_DEGRADATION);
         if (degrade != null) {
            degrade.removeModifier(RESTRICTED);
         }
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (mode == 0) {
         if ((entity.onGround() || entity.isInWaterOrBubble()) && !EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            instance.addMasteryPoint(entity);
            Level level = entity.level();
            double range = instance.isMastered(entity) ? CONFIG.decimateDistanceMastered : CONFIG.decimateDistance;
            BlockHitResult result = ObjectSelectionHelper.getPlayerPOVHitResult(level, entity, Fluid.NONE, range);
            BlockPos resultPos = result.getBlockPos().relative(result.getDirection());
            Vec3 vec3 = ObjectSelectionHelper.getFloorPos(resultPos);
            if (!level.getBlockState(resultPos).canBeReplaced()) {
               vec3 = ObjectSelectionHelper.getFloorPos(resultPos.above());
            }

            if (level.getBlockState(resultPos).is(TensuraBlockTags.SKILL_NOT_TELEPORTABLE)) {
               level.playSound(
                  null,
                  entity.getX(),
                  entity.getY(),
                  entity.getZ(),
                  (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(),
                  TensuraSkill.ABILITY_SOUND,
                  1.0F,
                  1.0F
               );
            } else if (!entity.level().getWorldBorder().isWithinBounds(ObjectSelectionHelper.getBlockPos(vec3))) {
               entity.sendSystemMessage(Component.translatable("tensura.skill.teleport.out_border").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
            } else {
               Vec3 source = entity.position().add(0.0, entity.getBbHeight() / 2.0F, 0.0);
               Vec3 offSetToTarget = vec3.subtract(source);

               for (int particleIndex = 1; particleIndex < Mth.floor(offSetToTarget.length()); particleIndex++) {
                  Vec3 particlePos = source.add(offSetToTarget.normalize().scale(particleIndex));
                  ((ServerLevel)level).sendParticles(ParticleTypes.CLOUD, particlePos.x, particlePos.y, particlePos.z, 1, 0.0, 0.0, 0.0, 0.0);
                  TensuraParticleHelper.addServerParticlesAroundPos(entity.getRandom(), level, particlePos, ParticleTypes.SWEEP_ATTACK, 3.0);
                  TensuraParticleHelper.addServerParticlesAroundPos(entity.getRandom(), level, particlePos, ParticleTypes.SWEEP_ATTACK, 2.0);
                  AABB aabb = new AABB(ObjectSelectionHelper.getBlockPos(particlePos))
                     .inflate(Math.max(entity.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE), 2.0));
                  List<LivingEntity> livingEntityList = level.getEntitiesOfClass(
                     LivingEntity.class, aabb, targetx -> !targetx.is(entity) && !targetx.isAlliedTo(entity)
                  );
                  if (!livingEntityList.isEmpty()) {
                     float bonus = instance.isMastered(entity) ? CONFIG.decimateDamageMastered : CONFIG.decimateDamage;
                     float amount = (float)(
                        entity.getAttributeValue(Attributes.ATTACK_DAMAGE) * entity.getAttributeValue(ManasCoreAttributes.CRITICAL_DAMAGE_MULTIPLIER)
                     );
                     DamageSource damageSource = this.createSource(instance, entity, DamageTypes.MOB_ATTACK, mode);

                     for (LivingEntity target : livingEntityList) {
                        if (target.hurt(damageSource, amount + bonus)) {
                           ItemStack stack = entity.getMainHandItem();
                           stack.getItem().hurtEnemy(stack, target, entity);
                           entity.level()
                              .playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, entity.getSoundSource(), 1.0F, 1.0F);
                           if (level instanceof ServerLevel serverLevel) {
                              serverLevel.getChunkSource().broadcastAndSend(entity, new ClientboundAnimatePacket(entity, 4));
                           }
                        }
                     }
                  }
               }

               entity.resetFallDistance();
               entity.unRide();
               entity.teleportTo(vec3.x(), vec3.y(), vec3.z());
               entity.swing(InteractionHand.MAIN_HAND, true);
               level.playSound(
                  null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.INSTANT_MOVE.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F
               );
            }
         }
      } else if (entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.COUNTERING))) {
         instance.getOrCreateTag().putBoolean("shouldRemoveWithoutCooldown", true);
         instance.markDirty();
         entity.removeEffect(TensuraMobEffects.getReference(MysticismMobEffects.COUNTERING));
         instance.getOrCreateTag().putBoolean("shouldRemoveWithoutCooldown", false);
         instance.markDirty();
         entity.playSound((SoundEvent)MysticismSoundEvents.COUNTER.get(), 1.0F, 0.8F);
      } else {
         if (EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            return;
         }

         entity.playSound((SoundEvent)MysticismSoundEvents.COUNTER.get(), 1.0F, 1.0F);
         int duration = instance.isMastered(entity) ? CONFIG.counterStateDurationMastered : CONFIG.counterStateDuration;
         entity.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(MysticismMobEffects.COUNTERING), duration, 0, false, false, false), entity);
      }
   }
}
