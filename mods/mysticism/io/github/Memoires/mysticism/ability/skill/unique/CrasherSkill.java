package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.damage.MysticismDamageTypes;
import io.github.Memoires.mysticism.registry.dimension.MysticismDimensions;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.SpawnPointHelper;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.impl.TickingSkill;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.extra.ThoughtAccelerationSkill;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.dimension.TensuraDimensions;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CrasherSkill extends Skill {
   protected static final UniqueMysticismSkillConfig.Crasher CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Crasher;
   protected static final ResourceLocation CRASHER = ResourceLocation.fromNamespaceAndPath("mysticism", "crasher");

   public CrasherSkill() {
      super(SkillType.UNIQUE);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/crasher.png");
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public double getAcquiringMagiculeCost(ManasSkillInstance instance) {
      return CONFIG.mpAcquirement;
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      ThoughtAccelerationSkill.onToggle(instance, entity, CRASHER, true);
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      ThoughtAccelerationSkill.onToggle(instance, entity, CRASHER, false);
   }

   public boolean canScroll(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return instance.getMastery() >= 0.0 && mode == 1;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return mode == 0 ? 1 : 0;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "crasher.destroyer_haki";
         case 1 -> "crasher.dimension_hopper";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> CONFIG.destroyerHakiCost;
         case 1 -> CONFIG.dimensionHopperCost;
         default -> 0.0;
      };
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity living, int heldTicks, int mode) {
      return switch (mode) {
         case 0 -> {
            living.level()
               .playSound(
                  null, living.getX(), living.getY(), living.getZ(), (SoundEvent)TensuraSoundEvents.ENERGY_DRAIN.get(), TensuraSkill.ABILITY_SOUND, 0.5F, 0.5F
               );
            double size = living.getAttributeValue(Attributes.SCALE) * 4.0;
            TensuraParticleHelper.addServerAuraParticles(living, TensuraParticleUtils.getBlackAura(0.5F, (float)size, -0.3F), 5, 0.01, 3.0F);
            if (heldTicks % 20 == 0 && !EnergyHelper.isOutOfEnergy(living, instance, mode)) {
               if (heldTicks > 0) {
                  this.addMasteryPoint(instance, living);
               }

               if (heldTicks % 200 == 0 && living.getRandom().nextFloat() <= CONFIG.insanityChance / 100.0F) {
                  int level = CONFIG.insanityLevel;
                  MobEffectInstance insanity = living.getEffect(TensuraMobEffects.getReference(TensuraMobEffects.INSANITY));
                  if (insanity != null) {
                     level = insanity.getAmplifier() + CONFIG.insanityLevel;
                  }

                  TensuraMobEffect.addEffect(
                     living,
                     new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.INSANITY), CONFIG.insanityDuration, level, false, false, false),
                     living,
                     this,
                     mode
                  );
               }

               List<LivingEntity> list = living.level()
                  .getEntitiesOfClass(
                     LivingEntity.class,
                     living.getBoundingBox().inflate(CONFIG.destroyerHakiRadius),
                     entity -> !entity.is(living) && entity.isAlive() && !entity.isAlliedTo(living)
                  );
               if (!list.isEmpty()) {
                  double ownerEP = EnergyHelper.getMaxEP(living);

                  for (LivingEntity target : list) {
                     if (!(target instanceof Player player && player.getAbilities().invulnerable)) {
                        double targetEP = EnergyHelper.getMaxEP(target);
                        double difference = (instance.isMastered(living) ? CONFIG.destroyerHakiEPMastered : CONFIG.destroyerHakiEP) - targetEP / ownerEP;
                        if (!(difference < 0.0)) {
                           int level = 1 + (int)(difference / CONFIG.epDifferenceMultiplier);
                           int slowDuration = instance.isMastered(living) ? CONFIG.slowDurationMastered : CONFIG.slowDuration;
                           TensuraMobEffect.addEffect(target, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, slowDuration, level), living, this, mode);
                        }

                        DamageSource damageSource = this.createSource(instance, living, MysticismDamageTypes.VOID, mode);
                        damageSource.tensura$setResistanceBypassLevel(1.0F);
                        double damage = instance.isMastered(living) ? CONFIG.destroyerHakiDamageMastered : CONFIG.destroyerHakiDamage;
                        target.hurt(damageSource, (float)damage);
                     }
                  }
               }
            }

            yield true;
         }
         case 1 -> {
            CompoundTag tag = instance.getOrCreateTag();
            String currentDimension = tag != null ? tag.getString("dimension") : "Overworld";
            if (living instanceof Player player) {
               player.displayClientMessage(
                  Component.translatable("mysticism.skill.mode.crasher.dimension_hopper.current_dimension", new Object[]{currentDimension})
                     .setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_AQUA)),
                  true
               );
            }

            yield true;
         }
         default -> false;
      };
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (mode == 1 && TickingSkill.isTickingSkill(entity, this, 1)) {
         CompoundTag tag = instance.getOrCreateTag();
         String currentDimension = tag != null ? tag.getString("dimension") : "Overworld";
         ResourceKey<Level> targetDimension = this.getTargetDimension(currentDimension);
         if (targetDimension != null && entity.getServer() != null) {
            if (entity instanceof Player player && entity.level().dimension() == targetDimension) {
               player.displayClientMessage(
                  Component.translatable("mysticism.skill.mode.crasher.dimension_hopper.received_same").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)),
                  false
               );
               return;
            }

            double targetX = entity.getX();
            double targetY = entity.getY();
            double targetZ = entity.getZ();
            ServerLevel targetLevel = entity.getServer().getLevel(targetDimension);
            if (entity.level().dimension() == Level.OVERWORLD && targetDimension == Level.NETHER) {
               targetX /= 8.0;
               targetZ /= 8.0;
            } else if (entity.level().dimension() == Level.NETHER && targetDimension == Level.OVERWORLD) {
               targetX *= 8.0;
               targetZ *= 8.0;
            }

            if (targetLevel != null) {
               BlockPos safePos = this.findNearestSafeBlock(targetX, targetY, targetZ, targetLevel);
               SpawnPointHelper.teleportToAcrossDimensions(
                  entity, targetLevel, safePos.getX(), safePos.getY(), safePos.getZ(), entity.getXRot(), entity.getYRot()
               );
               if (entity instanceof Player player && player.getAbilities().mayfly) {
                  player.getAbilities().flying = true;
                  player.onUpdateAbilities();
               }
            }

            this.addMasteryPoint(instance, entity);
            int cooldown = instance.isMastered(entity) ? CONFIG.dimensionHopperCooldownMastered : CONFIG.dimensionHopperCooldown;
            instance.setCoolDown(cooldown, mode);
         }
      }
   }

   public void onScroll(ManasSkillInstance instance, LivingEntity living, double delta, int mode) {
      if (mode == 1) {
         CompoundTag tag = instance.getOrCreateTag();
         List<String> dimensions = List.of("Overworld", "Nether", "The End", "Hell", "Elemental Realm", "Kamui Dimension");
         int newScale = tag.getInt("dimensionIndex") + (int)delta;
         if (newScale >= dimensions.size()) {
            newScale = 0;
         } else if (newScale < 0) {
            newScale = dimensions.size() - 1;
         }

         if (tag.getInt("dimensionIndex") != newScale) {
            tag.putInt("dimensionIndex", newScale);
            tag.putString("dimension", dimensions.get(newScale));
            instance.markDirty();
         }
      }
   }

   private BlockPos findNearestSafeBlock(double x, double y, double z, ServerLevel level) {
      MutableBlockPos mutablePos = new MutableBlockPos();
      int radius = 100;

      for (int r = 1; r <= radius; r++) {
         for (int dx = -r; dx <= r; dx++) {
            for (int dz = -r; dz <= r; dz++) {
               for (int dy = -r; dy <= r; dy++) {
                  mutablePos.set(x + dx, y + dy, z + dz);
                  BlockState blockState = level.getBlockState(mutablePos);
                  BlockState blockBelow = level.getBlockState(mutablePos.below());
                  if (blockState.isAir() && blockBelow.canOcclude()) {
                     return mutablePos.immutable();
                  }
               }
            }
         }
      }

      return new BlockPos((int)x, (int)y, (int)z);
   }

   private ResourceKey<Level> getTargetDimension(String dimensionName) {
      return switch (dimensionName) {
         case "Overworld" -> Level.OVERWORLD;
         case "Nether" -> Level.NETHER;
         case "The End" -> Level.END;
         case "Hell" -> TensuraDimensions.HELL;
         case "Elemental Realm" -> MysticismDimensions.ELEMENTAL_REALM;
         case "Kamui Dimension" -> MysticismDimensions.KAMUI_DIMENSION;
         default -> null;
      };
   }
}
