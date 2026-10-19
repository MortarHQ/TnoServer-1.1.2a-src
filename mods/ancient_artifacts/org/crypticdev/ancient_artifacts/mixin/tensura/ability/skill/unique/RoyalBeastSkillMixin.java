package org.crypticdev.ancient_artifacts.mixin.tensura.ability.skill.unique;

import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.unique.RoyalBeastSkill;
import io.github.manasmods.tensura.data.TensuraBlockTags;
import io.github.manasmods.tensura.entity.projectile.magic.ThunderLanceProjectile;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleType;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.EnergyHelper.DrainType;
import io.github.manasmods.tensura.util.EnergyHelper.GainType;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.crypticdev.ancient_artifacts.artifact.ArtifactItem;
import org.crypticdev.ancient_artifacts.artifact.ArtifactRefinement;
import org.crypticdev.ancient_artifacts.component.RefinementComponent;
import org.crypticdev.ancient_artifacts.config.dynamic_effects.BeastAuraConfig;
import org.crypticdev.ancient_artifacts.platform.PlatformEquipment;
import org.crypticdev.ancient_artifacts.registry.Artifacts;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(RoyalBeastSkill.class)
public class RoyalBeastSkillMixin extends Skill {
   public RoyalBeastSkillMixin(SkillType type) {
      super(type);
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (entity.hasEffect(TensuraMobEffects.BEAST_TRANSFORMATION)
         && entity.getEffect(TensuraMobEffects.BEAST_TRANSFORMATION).getAmplifier() == 1
         && PlatformEquipment.hasArtifact(entity, Artifacts.VermillionBirdCrown)
         && entity instanceof Player player) {
         ItemStack stack = PlatformEquipment.getArtifactStack(entity, Artifacts.VermillionBirdCrown);
         ArtifactItem artifactItem = (ArtifactItem)stack.getItem();
         if (artifactItem.getArtifact().artifactEffects().get(2).unlockCondition().apply(stack, player)) {
            if (reverse) {
               return mode == 0 ? 2 : mode - 1;
            }

            return mode == 2 ? 0 : mode + 1;
         }
      }

      return 0;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 1 -> "royal_beast.beast_roar";
         case 2 -> "royal_beast.burst_roar";
         default -> "default";
      };
   }

   public int getModes(ManasSkillInstance instance) {
      return 3;
   }

   @Inject(method = "onPressed", at = @At("HEAD"), cancellable = true)
   private void addBurstAndBeastRoar(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode, CallbackInfo ci) {
      if (entity.hasEffect(TensuraMobEffects.BEAST_TRANSFORMATION)
         && entity.getEffect(TensuraMobEffects.BEAST_TRANSFORMATION).getAmplifier() == 1
         && PlatformEquipment.hasArtifact(entity, Artifacts.VermillionBirdCrown)
         && entity instanceof Player player) {
         ItemStack stack = PlatformEquipment.getArtifactStack(entity, Artifacts.VermillionBirdCrown);
         ArtifactItem artifactItem = (ArtifactItem)stack.getItem();
         if (artifactItem.getArtifact().artifactEffects().get(2).unlockCondition().apply(stack, player)) {
            if (mode == 1) {
               double auraCost = Math.min(EnergyHelper.getMaxAura(entity) * 0.1, instance.isMastered(entity) ? 25000.0 : 25000.0);
               if (!EnergyHelper.isOutOfEnergy(entity, auraCost, 0.0)) {
                  ThunderLanceProjectile lance = new ThunderLanceProjectile(player.level(), player);
                  lance.noPhysics = true;
                  lance.setEffectRange(-1.0F);
                  lance.setLife(141);
                  lance.setSkill(entity, instance, this, mode);
                  lance.setSize(2.0F);
                  float damage = (float)(auraCost / 10.0);
                  lance.setDamage(damage);
                  lance.setSpeed(1.75F);
                  lance.setPiercingEntity(true);
                  lance.setNoGravity(true);
                  lance.setLookDistance(20.0F);
                  lance.setPosAndShoot(entity);
                  entity.level().addFreshEntity(lance);
               }

               ci.cancel();
            }

            if (mode == 2) {
               if (entity.onGround()) {
                  double auraCost = EnergyHelper.getMaxAura(entity) * 0.33;
                  if (!EnergyHelper.isOutOfEnergy(entity, auraCost, 0.0)) {
                     instance.addMasteryPoint(entity);
                     Level level = entity.level();
                     double range = 30.0;
                     BlockHitResult result = ObjectSelectionHelper.getPlayerPOVHitResult(level, entity, Fluid.NONE, Block.COLLIDER, range);
                     BlockPos resultPos = result.getBlockPos().relative(result.getDirection());
                     Vec3 vec3 = ObjectSelectionHelper.getFloorPos(resultPos);
                     if (!level.getBlockState(resultPos).canBeReplaced()) {
                        vec3 = ObjectSelectionHelper.getFloorPos(resultPos.above());
                     }

                     if (level.getBlockState(resultPos).is(TensuraBlockTags.SKILL_NOT_TELEPORTABLE)) {
                        level.playSound(
                           (Player)null,
                           entity.getX(),
                           entity.getY(),
                           entity.getZ(),
                           (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(),
                           SoundSource.PLAYERS,
                           1.0F,
                           1.0F
                        );
                     } else if (!entity.level().getWorldBorder().isWithinBounds(ObjectSelectionHelper.getBlockPos(vec3))) {
                        entity.sendSystemMessage(Component.translatable("tensura.skill.teleport.out_border").withStyle(ChatFormatting.RED));
                     } else {
                        Vec3 source = entity.position().add(0.0, entity.getBbHeight() / 2.0F, 0.0);
                        Vec3 offSetToTarget = vec3.subtract(source);

                        for (int particleIndex = 1; particleIndex < Mth.floor(offSetToTarget.length()); particleIndex++) {
                           Vec3 particlePos = source.add(offSetToTarget.normalize().scale(particleIndex));
                           ((ServerLevel)level)
                              .sendParticles(
                                 (TensuraParticleType)TensuraParticleTypes.LIGHTNING_SPARK.get(),
                                 particlePos.x,
                                 particlePos.y,
                                 particlePos.z,
                                 1,
                                 0.0,
                                 0.0,
                                 0.0,
                                 0.0
                              );
                           TensuraParticleHelper.addServerParticlesAroundPos(
                              entity.getRandom(), level, particlePos, (ParticleOptions)TensuraParticleTypes.LIGHTNING_SPARK.get(), 3.0
                           );
                           TensuraParticleHelper.addServerParticlesAroundPos(
                              entity.getRandom(), level, particlePos, (ParticleOptions)TensuraParticleTypes.LIGHTNING_SPARK.get(), 2.0
                           );
                           AABB aabb = new AABB(ObjectSelectionHelper.getBlockPos(particlePos)).inflate(2.0);
                           List<LivingEntity> livingEntityList = level.getEntitiesOfClass(
                              LivingEntity.class, aabb, targetx -> !targetx.is(entity) && !targetx.isAlliedTo(entity)
                           );
                           if (!livingEntityList.isEmpty()) {
                              AttributeInstance degrade = entity.getAttribute(TensuraAttributes.RESISTANCE_DEGRADATION);
                              if (degrade != null) {
                                 degrade.addOrReplacePermanentModifier(
                                    new AttributeModifier(ResourceLocation.fromNamespaceAndPath("ancient_artifacts", "degrade"), 2.0, Operation.ADD_VALUE)
                                 );
                              }

                              for (LivingEntity target : livingEntityList) {
                                 EnergyHelper.drainEnergy(target, entity, auraCost, false, DrainType.AURA, GainType.NONE);
                              }

                              degrade.removeModifier(ResourceLocation.fromNamespaceAndPath("ancient_artifacts", "degrade"));
                           }
                        }

                        entity.resetFallDistance();
                        entity.unRide();
                        entity.teleportTo(vec3.x(), vec3.y(), vec3.z());
                        entity.swing(InteractionHand.MAIN_HAND, true);
                        level.playSound(
                           (Player)null,
                           entity.getX(),
                           entity.getY(),
                           entity.getZ(),
                           (SoundEvent)TensuraSoundEvents.INSTANT_MOVE.get(),
                           SoundSource.PLAYERS,
                           1.0F,
                           1.0F
                        );
                     }
                  }
               }

               ci.cancel();
            }
         }
      }
   }

   @ModifyArgs(method = "onPressed", at = @At(value = "INVOKE", target = "Lio/github/manasmods/manascore/skill/api/ManasSkillInstance;setCoolDown(II)V"))
   private void lowerCooldown(Args args, ManasSkillInstance manasSkillInstance, LivingEntity entity, int keyNumber, int mode) {
      if (entity instanceof Player player) {
         if (!PlatformEquipment.hasArtifact(player, Artifacts.VermillionBirdCrown)) {
            return;
         }

         ItemStack stack = PlatformEquipment.getArtifactStack(player, Artifacts.VermillionBirdCrown);
         if (!((ArtifactItem)stack.getItem()).getArtifact().artifactEffects().get(2).unlockCondition().apply(stack, player)) {
            return;
         }

         BeastAuraConfig config = (BeastAuraConfig)ConfigRegistry.getConfig(BeastAuraConfig.class);
         RefinementComponent refinement = ArtifactRefinement.get(stack);
         int refinementSteps = Math.max(0, refinement.upgrades().get(1));
         if (config.max_refinements != -1) {
            refinementSteps = Math.min(refinementSteps, config.max_refinements);
         }

         args.set(0, 600);
      }
   }
}
