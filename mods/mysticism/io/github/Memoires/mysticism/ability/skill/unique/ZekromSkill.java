package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.registry.entity.MysticismProjectileEntityTypes;
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
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.data.TensuraBlockTags;
import io.github.manasmods.tensura.entity.magic.breath.BreathEntity;
import io.github.manasmods.tensura.entity.magic.lightning.LightningBolt;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.item.TensuraMobDropItems;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ZekromSkill extends Skill {
   protected static final UniqueMysticismSkillConfig.Zekrom CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Zekrom;
   protected static final ResourceLocation ZEKROM = ResourceLocation.fromNamespaceAndPath("mysticism", "zekrom");

   public ZekromSkill() {
      super(SkillType.UNIQUE);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/zekrom.png");
   }

   public int getModes(ManasSkillInstance instance) {
      return 4;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "zekrom.bolt_strike";
         case 1 -> "zekrom.fusion_bolt";
         case 2 -> "zekrom.draconic_breath";
         case 3 -> "zekrom.thunderbolt";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> CONFIG.boltStrikeMPCost;
         case 1 -> CONFIG.fusionBoltMPCost;
         case 2 -> CONFIG.draconicBreathMPCost;
         case 3 -> CONFIG.thunderboltMPCost;
         default -> 0.0;
      };
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode == 0 ? 3 : mode - 1;
      } else {
         return mode == 3 ? 0 : mode + 1;
      }
   }

   public double getDefaultAcquiringMagiculeCost() {
      return CONFIG.mpAcquirement;
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
         boolean isKyuremMastered = SkillUtils.isSkillMastered(player, (ManasSkill)UniqueMysticismSkills.KYUREM.get());
         boolean canLearnSkill = !hasZekrom && (hasReshiram && isReshiramMastered || hasKyurem && isKyuremMastered);
         boolean hasEnoughDragonEssence = player.getStats().getValue(Stats.ITEM_USED.get((Item)TensuraMobDropItems.DRAGON_ESSENCE.get()))
            >= CONFIG.dragonEssenceAmount;
         boolean hasEnoughLightingEssence = player.getStats().getValue(Stats.ITEM_USED.get((Item)MysticismMobDropItems.LIGHTNING_ESSENCE.get()))
            >= CONFIG.lightningEssenceAmount;
         boolean hasEnoughEP = entity.getAttributeValue(TensuraAttributes.MAX_AURA) + entity.getAttributeValue(TensuraAttributes.MAX_MAGICULE)
            >= CONFIG.epRequirement;
         return canLearnSkill && hasEnoughLightingEssence && hasEnoughDragonEssence && hasEnoughEP;
      }
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.LIGHTNING_BOOST, ZEKROM, CONFIG.lightningBoostAmount, Operation.ADD_VALUE);
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.removeAttributeIfCorrect(entity, TensuraAttributes.LIGHTNING_BOOST, ZEKROM, CONFIG.lightningBoostAmount);
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      switch (mode) {
         case 0:
            if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
               LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, 30.0, false, false);
               Vec3 pos;
               if (target != null) {
                  pos = target.position();
               } else {
                  BlockHitResult result = ObjectSelectionHelper.getPlayerPOVHitResult(entity.level(), entity, Fluid.NONE, 30.0);
                  pos = result.getLocation();
               }

               LightningBolt bolt = new LightningBolt(entity.level(), entity);
               if (entity instanceof ServerPlayer serverPlayer) {
                  bolt.setCause(serverPlayer);
               }

               bolt.setTensuraDamage(CONFIG.boltStrikeDamage);
               bolt.setMpCost(CONFIG.boltStrikeMPCost);
               bolt.setSkill(entity, instance, this, mode);
               bolt.setRadius(CONFIG.boltStrikeRadius);
               bolt.setPos(pos);
               bolt.setAdditionalVisual(CONFIG.boltStrikeAdditionalVisual);
               entity.level().addFreshEntity(bolt);
               entity.swing(InteractionHand.MAIN_HAND, true);
               entity.level()
                  .playSound(
                     null,
                     entity.getX(),
                     entity.getY(),
                     entity.getZ(),
                     (SoundEvent)TensuraSoundEvents.CAST_LIGHTNING.get(),
                     TensuraSkill.ABILITY_SOUND,
                     0.5F,
                     0.5F
                  );
               instance.setCoolDown(instance.isMastered(entity) ? CONFIG.boltStrikeCooldownMastered : CONFIG.boltStrikeCooldown, mode);
               instance.addMasteryPoint(entity);
            }
         case 1:
         default:
            break;
         case 2:
            instance.getOrCreateTag().putInt("BreathEntity", 0);
            instance.markDirty();
            break;
         case 3:
            if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
               LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, CONFIG.thunderboltRange, false, false);
               Vec3 pos;
               if (target != null) {
                  pos = target.position();
               } else {
                  BlockHitResult result = ObjectSelectionHelper.getPlayerPOVHitResult(entity.level(), entity, Fluid.NONE, CONFIG.thunderboltRange);
                  pos = result.getLocation();
               }

               LightningBolt bolt = new LightningBolt(entity.level(), entity);
               if (entity instanceof ServerPlayer serverPlayer) {
                  bolt.setCause(serverPlayer);
               }

               bolt.setSkill(entity, instance, this, mode);
               bolt.setTensuraDamage(CONFIG.thunderboltDamage);
               bolt.setAdditionalVisual(2);
               bolt.setRadius(3.0F);
               bolt.setPos(pos);
               entity.level().addFreshEntity(bolt);
               entity.swing(InteractionHand.MAIN_HAND, true);
               entity.level()
                  .playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.CAST_LIGHTNING, TensuraSkill.ABILITY_SOUND, 0.5F, 0.5F);
            }
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity living, int heldTicks, int mode) {
      if (mode == 1) {
         TensuraParticleHelper.addServerAuraParticles(living, (ParticleOptions)TensuraParticleTypes.LIGHTNING_SPARK.get(), 2, 0.5, 1.5F);
         TensuraParticleHelper.addServerAuraParticles(living, (ParticleOptions)TensuraParticleTypes.YELLOW_LIGHTNING_SPARK.get(), 2, 0.5, 1.5F);
         living.level()
            .playSound(
               null, living.getX(), living.getY(), living.getZ(), (SoundEvent)TensuraSoundEvents.CAST_LIGHTNING.get(), TensuraSkill.ABILITY_SOUND, 0.5F, 0.5F
            );
         AttributeInstance zoom = living.getAttribute(TensuraAttributes.VIEW_ZOOM);
         if (zoom != null) {
            zoom.addOrUpdateTransientModifier(new AttributeModifier(ZEKROM, 1.5, Operation.ADD_VALUE));
         }

         return true;
      } else if (mode == 2) {
         if (heldTicks % 20 == 0 && EnergyHelper.isOutOfEnergy(living, instance, mode)) {
            return false;
         }

         if (heldTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0 && heldTicks > 0) {
            instance.addMasteryPoint(living);
         }

         BreathEntity.spawnBreathEntity(
            (EntityType)MysticismProjectileEntityTypes.DRACONIC_BREATH_PROJECTILE.get(), living, instance, CONFIG.draconicBreathDamage, this, mode
         );
         return true;
      } else {
         return false;
      }
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (mode == 1 && TickingSkill.isTickingSkill(entity, this, 1)) {
         AttributeInstance zoom = entity.getAttribute(TensuraAttributes.VIEW_ZOOM);
         if (zoom != null) {
            zoom.removeModifier(ZEKROM);
         }

         boolean hasReshiram = TensuraStorages.getAbilityFrom(entity).isAbilityInActivePreset((ManasSkill)UniqueMysticismSkills.RESHIRAM.get());
         if (!EnergyHelper.isOutOfEnergy(entity, instance, mode, (float)(hasReshiram ? CONFIG.fusionboltReshiramMPCost : 1.0))) {
            BlockHitResult result = ObjectSelectionHelper.getPlayerPOVHitResult(entity.level(), entity, Fluid.NONE, CONFIG.fusionBoltRange);
            BlockPos resultPos = result.getBlockPos().relative(result.getDirection());
            Vec3 vec3 = ObjectSelectionHelper.getFloorPos(resultPos);
            if (!entity.level().getBlockState(resultPos).canBeReplaced()) {
               vec3 = ObjectSelectionHelper.getFloorPos(resultPos.above());
            }

            if (entity.level().getBlockState(resultPos).is(TensuraBlockTags.SKILL_NOT_TELEPORTABLE)) {
               entity.level()
                  .playSound(
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
                  ((ServerLevel)entity.level()).sendParticles(ParticleTypes.CLOUD, particlePos.x, particlePos.y, particlePos.z, 1, 0.0, 0.0, 0.0, 0.0);
                  TensuraParticleHelper.addServerParticlesAroundPos(
                     entity.getRandom(), entity.level(), particlePos, (ParticleOptions)TensuraParticleTypes.LIGHTNING_SPARK.get(), 3.0
                  );
                  TensuraParticleHelper.addServerParticlesAroundPos(
                     entity.getRandom(), entity.level(), particlePos, (ParticleOptions)TensuraParticleTypes.YELLOW_LIGHTNING_SPARK.get(), 2.0
                  );
                  AABB aabb = new AABB(ObjectSelectionHelper.getBlockPos(particlePos))
                     .inflate(Math.max(entity.getAttributeValue(Attributes.ENTITY_INTERACTION_RANGE), 2.0));
                  List<LivingEntity> livingEntityList = entity.level()
                     .getEntitiesOfClass(LivingEntity.class, aabb, targetx -> !targetx.is(entity) && !targetx.isAlliedTo(entity));
                  if (!livingEntityList.isEmpty()) {
                     for (LivingEntity target : livingEntityList) {
                        DamageSource physical = this.createSource(instance, entity, DamageTypes.PLAYER_ATTACK, mode);
                        DamageSource electric = this.createSource(instance, entity, TensuraDamageTypes.LIGHTNING, mode);
                        TensuraDamageHelper.hurtSplit(target, physical, 0.5F, electric, CONFIG.fusionBoltDamage);
                        if (hasReshiram) {
                           target.hurt(this.createSource(instance, entity, TensuraDamageTypes.FIRE_ELEMENTAL, mode), CONFIG.fusionBoltReshiramBonus);
                           target.igniteForTicks(CONFIG.fusionBoltFireDuration * 20);
                           TensuraParticleHelper.addServerParticlesAroundSelf(target, (ParticleOptions)TensuraParticleTypes.RED_FIRE.get());
                           entity.level()
                              .playSound(
                                 null,
                                 entity.getX(),
                                 entity.getY(),
                                 entity.getZ(),
                                 (SoundEvent)TensuraSoundEvents.CAST_FIRE.get(),
                                 TensuraSkill.ABILITY_SOUND,
                                 0.5F,
                                 0.5F
                              );
                           instance.addMasteryPoint(entity);
                        }
                     }
                  }

                  entity.resetFallDistance();
                  entity.unRide();
                  entity.teleportTo(vec3.x(), vec3.y(), vec3.z());
                  TensuraParticleHelper.addServerAuraParticles(entity, (ParticleOptions)TensuraParticleTypes.LIGHTNING_SPARK.get(), 5, 1.0, 3.0F);
                  TensuraParticleHelper.addServerAuraParticles(entity, (ParticleOptions)TensuraParticleTypes.YELLOW_LIGHTNING_SPARK.get(), 5, 1.0, 3.0F);
                  entity.level()
                     .playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.LIGHTNING_BOLT_IMPACT, TensuraSkill.ABILITY_SOUND, 0.5F, 0.5F);
                  entity.swing(InteractionHand.MAIN_HAND, true);
               }
            }

            instance.setCoolDown(CONFIG.fusionBoltCooldown, mode);
         }
      } else if (mode == 2 && TickingSkill.isTickingSkill(entity, this, 2)) {
         instance.setCoolDown(CONFIG.draconicBreathCooldown, mode);
      }
   }
}
