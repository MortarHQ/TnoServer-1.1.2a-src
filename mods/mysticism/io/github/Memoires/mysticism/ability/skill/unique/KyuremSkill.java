package io.github.Memoires.mysticism.ability.skill.unique;

import com.mojang.datafixers.util.Pair;
import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.entity.projectile.DraconicPulseProjectile;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.item.MysticismMobDropItems;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.impl.TickingSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.entity.magic.barrier.BarrierEntity;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.particle.option.SimpleAuraParticleOptions;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.entity.MiscEntityTypes;
import io.github.manasmods.tensura.registry.item.TensuraMobDropItems;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.List;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public class KyuremSkill extends Skill {
   public static final UniqueMysticismSkillConfig.Kyurem CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Kyurem;

   public KyuremSkill() {
      super(SkillType.UNIQUE);
   }

   public double getDefaultAcquiringMagiculeCost() {
      return CONFIG.mpAcquirement;
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/kyurem.png");
   }

   public int getModes(ManasSkillInstance instance) {
      return 4;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "kyurem.glaciate";
         case 1 -> "kyurem.draconic_pulse";
         case 2 -> "kyurem.freeze_dry";
         case 3 -> "kyurem.blizzard";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> CONFIG.glaciateMPCost;
         case 1 -> CONFIG.draconicPulseMPCost;
         case 2 -> CONFIG.freezeDryMPCost;
         case 3 -> CONFIG.blizzardMPCost;
         default -> 0.0;
      };
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode == 0 ? 3 : mode - 1;
      } else {
         return (mode + 1) % this.getModes(instance);
      }
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      if (!CONFIG.canAcquireWithOtherSkills) {
         return false;
      } else if (!(entity instanceof ServerPlayer player)) {
         return false;
      } else {
         boolean hasZekrom = SkillUtils.hasSkillFully(player, (ManasSkill)UniqueMysticismSkills.ZEKROM.get());
         boolean hasKyurem = SkillUtils.hasSkillFully(player, (ManasSkill)UniqueMysticismSkills.KYUREM.get());
         boolean hasReshiram = SkillUtils.hasSkillFully(player, (ManasSkill)UniqueMysticismSkills.RESHIRAM.get());
         boolean isReshiramMastered = SkillUtils.isSkillMastered(player, (ManasSkill)UniqueMysticismSkills.RESHIRAM.get());
         boolean isZekromMastered = SkillUtils.isSkillMastered(player, (ManasSkill)UniqueMysticismSkills.ZEKROM.get());
         boolean canLearnSkill = !hasKyurem && (hasReshiram && isReshiramMastered || hasZekrom && isZekromMastered);
         boolean hasEnoughIceEssence = player.getStats().getValue(Stats.ITEM_USED.get((Item)MysticismMobDropItems.ICE_ESSENCE.get()))
            >= CONFIG.iceEssenceAmount;
         boolean hasEnoughDragonEssence = player.getStats().getValue(Stats.ITEM_USED.get((Item)TensuraMobDropItems.DRAGON_ESSENCE.get()))
            >= CONFIG.dragonEssenceAmount;
         boolean hasEnoughEP = entity.getAttributeValue(TensuraAttributes.MAX_AURA) + entity.getAttributeValue(TensuraAttributes.MAX_MAGICULE)
            >= CONFIG.epRequirement;
         return canLearnSkill && hasEnoughIceEssence && hasEnoughDragonEssence && hasEnoughEP;
      }
   }

   public boolean onBeingDamaged(ManasSkillInstance instance, LivingEntity entity, DamageSource source, float amount) {
      if (this.isInSlot(entity) && source.getEntity() instanceof LivingEntity attacker) {
         TensuraMobEffect.addEffect(
            attacker,
            new MobEffectInstance(TensuraMobEffects.getReference(MysticismMobEffects.PRESSURE), CONFIG.pressureEffectDuration * 20, 0, false, true, true),
            entity,
            this
         );
      }

      return true;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (mode == 1) {
         if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            entity.swing(InteractionHand.MAIN_HAND, true);
            DraconicPulseProjectile draconicPulse = new DraconicPulseProjectile(entity.level(), entity);
            draconicPulse.setSkill(entity, instance, this, mode);
            draconicPulse.setSpeed(1.5F);
            draconicPulse.setExplosionRadius(CONFIG.draconicPulseExplosionRadius);
            draconicPulse.setMpCost(CONFIG.draconicPulseMPCost);
            draconicPulse.setDamage(CONFIG.draconicPulseDamage);
            draconicPulse.setPosAndShoot(entity);
            entity.level().addFreshEntity(draconicPulse);
            entity.level()
               .playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.DRAGON_FIREBALL_EXPLODE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
            instance.setCoolDown(instance.isMastered(entity) ? CONFIG.draconicPulseCooldownMastered : CONFIG.draconicPulseCooldown, mode);
         }
      } else if (mode == 3) {
         instance.getOrCreateTag().putInt("BarrierID", 0);
         instance.markDirty();
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity living, int heldTicks, int mode) {
      return switch (mode) {
         case 0 -> {
            if (!EnergyHelper.isOutOfEnergy(living, instance, mode)) {
               living.level()
                  .playSound(null, living.getX(), living.getY(), living.getZ(), SoundEvents.EVOKER_CAST_SPELL, TensuraSkill.ABILITY_SOUND, 0.6F, 1.0F);
               double size = living.getAttributeValue(Attributes.SCALE) * 4.0;
               TensuraParticleHelper.addServerAuraParticles(living, new SimpleAuraParticleOptions(0.8F, 0.9F, 1.0F, 1.0F, (float)size, -0.3F), 5, 0.01, 3.0F);
               List<LivingEntity> list = living.level()
                  .getEntitiesOfClass(
                     LivingEntity.class,
                     living.getBoundingBox().inflate(CONFIG.glaciateRange),
                     entityData -> !entityData.is(living) && entityData.isAlive() && !living.isAlliedTo(entityData)
                  );
               if (!list.isEmpty()) {
                  for (LivingEntity target : list) {
                     int damage = CONFIG.glaciateDamageIncrement + heldTicks / 200 * 5;
                     int maxDamage = instance.isMastered(living) ? CONFIG.glaciateMaxDamageMastered : CONFIG.glaciateMaxDamage;
                     if (damage > maxDamage) {
                        damage = maxDamage;
                     }

                     if (!(target instanceof Player player && player.getAbilities().invulnerable)) {
                        int amp = Math.min(heldTicks / 200, 2);
                        TensuraMobEffect.addEffect(target, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, amp), living, this, mode);
                        target.hurt(this.createSource(instance, living, DamageTypes.FREEZE, mode), damage);
                     }
                  }
               }

               if (heldTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0 && heldTicks > 0) {
                  instance.addMasteryPoint(living);
               }

               yield true;
            } else {
               yield false;
            }
         }
         default -> false;
         case 2 -> {
            if (!EnergyHelper.isOutOfEnergy(living, instance, mode)) {
               LivingEntity target = ObjectSelectionHelper.getTargetingEntity(living, 5.0, false, true);
               if (target instanceof Player player && player.getAbilities().invulnerable) {
                  yield false;
               }

               if (heldTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0 && heldTicks > 0) {
                  instance.addMasteryPoint(living);
               }

               if (target != null) {
                  if (heldTicks % 20 == 0) {
                     TensuraParticleHelper.spawnServerParticles(
                        target.level(),
                        TensuraParticleUtils.getColorlessReversedWave(0.9F, target.getBbWidth() * 3.0F),
                        target.getX(),
                        target.getY() + target.getBbHeight() * 0.33,
                        target.getZ()
                     );
                     TensuraParticleHelper.spawnServerParticles(
                        target.level(),
                        TensuraParticleUtils.getColorlessReversedWave(0.9F, target.getBbWidth() * 3.0F),
                        target.getX(),
                        target.getY() + target.getBbHeight() * 0.33,
                        target.getZ()
                     );
                     living.level()
                        .playSound(null, target.getX(), target.getY(), target.getZ(), TensuraSoundEvents.CAST_ICE, TensuraSkill.ABILITY_SOUND, 0.6F, 1.0F);
                  }

                  boolean hasResist = hasResist(target);
                  boolean hasNull = !hasResist && hasNull(target);
                  target.hurt(this.createSource(instance, living, DamageTypes.FREEZE, mode), CONFIG.freezeDryDamage);
                  TensuraMobEffect.addEffect(target, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 0), living, this, mode);
                  double chance = hasResist ? 0.1 : 0.05;
                  if (Math.random() < chance && !hasNull) {
                     TensuraMobEffect.addEffect(target, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, hasResist ? 15 : 30, 5), living, this, mode);
                  }

                  yield true;
               }
            }

            yield false;
         }
         case 3 -> {
            int holdDuration = 100;
            float radius = CONFIG.blizzardRadius;
            Pair<Double, Double> cost = Pair.of(this.getAuraCost(living, instance, mode), this.getMagiculeCost(living, instance, mode));
            BarrierEntity blizzard = BarrierEntity.getLastingBarrier(
               (EntityType)MiscEntityTypes.BLIZZARD.get(),
               0.0F,
               radius,
               0.0F,
               60,
               20.0F,
               living.position(),
               living,
               instance,
               mode,
               cost,
               Pair.of(0.0, 0.0),
               heldTicks
            );
            label115:
            if (blizzard != null) {
               if (heldTicks == 1) {
                  blizzard.setAge(-20);
                  blizzard.setSecondaryDamage(CONFIG.blizzardDamage);
                  MobEffectInstance chill = new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.CHILL), 2, 600, true, false, true);
                  blizzard.setMobEffect(chill);
                  TensuraParticleHelper.addServerParticlesAroundSelf(living, (ParticleOptions)TensuraParticleTypes.SNOWFLAKE.get(), radius / 3.0F);
               }

               if (heldTicks > 1 && heldTicks % holdDuration == 0) {
                  if (EnergyHelper.isOutOfEnergy(living, 0.0, CONFIG.blizzardMPCost)) {
                     yield false;
                     break label115;
                  }

                  if (heldTicks == holdDuration) {
                     blizzard.setEffectStack(true);
                     blizzard.setSecondaryDamage(CONFIG.blizzardDamage);
                     MobEffectInstance chillHold = new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.CHILL), 1, 300, true, false, true);
                     blizzard.setMobEffect(chillHold);
                     TensuraParticleHelper.addServerParticlesAroundSelf(living, (ParticleOptions)TensuraParticleTypes.SNOWFLAKE.get(), radius);
                  }
               }

               if (heldTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0 && heldTicks > 0) {
                  instance.addMasteryPoint(living);
               }

               yield heldTicks / holdDuration <= instance.getMaxHeldTime(living) - 1;
            } else {
               yield false;
            }
         }
      };
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      switch (mode) {
         case 0:
            if (TickingSkill.isTickingSkill(entity, this, 0)) {
               instance.setCoolDown(CONFIG.glaciateCooldown, mode);
            }
         case 1:
         default:
            break;
         case 2:
            if (TickingSkill.isTickingSkill(entity, this, 2)) {
               instance.setCoolDown(instance.isMastered(entity) ? CONFIG.freezeDryCooldownMastered : CONFIG.freezeDryCooldown, mode);
            }
            break;
         case 3:
            if (TickingSkill.isTickingSkill(entity, this, 3)) {
               instance.setCoolDown(CONFIG.blizzardCooldown, mode);
            }
      }
   }

   private static boolean hasResist(LivingEntity entity) {
      return SkillUtils.isSkillToggled(entity, (ManasSkill)ResistanceSkills.COLD_RESISTANCE.get())
         || SkillUtils.isSkillToggled(entity, (ManasSkill)ResistanceSkills.THERMAL_FLUCTUATION_RESISTANCE.get());
   }

   private static boolean hasNull(LivingEntity entity) {
      return SkillUtils.isSkillToggled(entity, (ManasSkill)ResistanceSkills.COLD_NULLIFICATION.get())
         || SkillUtils.isSkillToggled(entity, (ManasSkill)ResistanceSkills.THERMAL_FLUCTUATION_NULLIFICATION.get());
   }
}
