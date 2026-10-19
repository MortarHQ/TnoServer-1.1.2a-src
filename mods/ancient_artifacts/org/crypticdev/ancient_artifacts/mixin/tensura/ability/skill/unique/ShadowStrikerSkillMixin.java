package org.crypticdev.ancient_artifacts.mixin.tensura.ability.skill.unique;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.manasmods.manascore.attribute.api.ManasCoreAttributes;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.unique.ShadowStrikerSkill;
import io.github.manasmods.tensura.config.ability.skill.UniqueSkillConfig.ShadowStriker;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.crypticdev.ancient_artifacts.artifact.ArtifactItem;
import org.crypticdev.ancient_artifacts.artifact.ArtifactRefinement;
import org.crypticdev.ancient_artifacts.component.RefinementComponent;
import org.crypticdev.ancient_artifacts.platform.PlatformEquipment;
import org.crypticdev.ancient_artifacts.registry.Artifacts;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShadowStrikerSkill.class)
public abstract class ShadowStrikerSkillMixin extends Skill {
   @Shadow
   @Final
   private static ShadowStriker CONFIG;

   public ShadowStrikerSkillMixin(SkillType type) {
      super(type);
   }

   @Shadow
   public abstract int getModes(ManasSkillInstance var1);

   @ModifyReturnValue(method = "getModes", at = @At("RETURN"))
   private int addMode(int original) {
      return original + 1;
   }

   @Inject(method = "getModeId", at = @At("HEAD"), cancellable = true)
   private void addId(ManasSkillInstance instance, int mode, CallbackInfoReturnable<String> cir) {
      if (mode == this.getModes(instance) - 1) {
         cir.setReturnValue("shadow_striker.shadow_slash");
      }
   }

   @Inject(
      method = "onDamageEntity",
      at = @At(
         value = "INVOKE",
         target = "Lio/github/manasmods/tensura/ability/skill/unique/ShadowStrikerSkill;createSource(Lio/github/manasmods/manascore/skill/api/ManasSkillInstance;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/resources/ResourceKey;I)Lnet/minecraft/world/damagesource/DamageSource;"
      ),
      cancellable = true
   )
   private void ignoreResistance(
      ManasSkillInstance instance,
      LivingEntity attacker,
      LivingEntity target,
      DamageSource source,
      Changeable<Float> amount,
      CallbackInfoReturnable<Boolean> cir
   ) {
      if (attacker instanceof Player player) {
         if (!PlatformEquipment.hasArtifact(attacker, Artifacts.ShadowbindBracelet)) {
            return;
         }

         ItemStack stack = PlatformEquipment.getArtifactStack(attacker, Artifacts.ShadowbindBracelet);
         ArtifactItem artifactItem = (ArtifactItem)stack.getItem();
         if (!artifactItem.getArtifact().artifactEffects().get(2).unlockCondition().apply(stack, player)) {
            return;
         }

         DamageSource damagesource = this.createSource(instance, attacker, DamageTypes.MOB_ATTACK, 1);
         directSpiritualHurt(target, attacker, damagesource, instance.isMastered(attacker) ? CONFIG.killDamageMastered : CONFIG.killDamage);
         instance.setCoolDown(instance.isMastered(target) ? CONFIG.killCooldownMastered : CONFIG.killCooldown, 1);
         cir.setReturnValue(target.isAlive());
      }
   }

   @Unique
   private static void directSpiritualHurt(LivingEntity target, @Nullable Entity attacker, @Nullable DamageSource source, float amount) {
      if (!target.getType().is(TensuraEntityTags.NO_SPIRITUAL_DAMAGE)) {
         if (SkillUtils.isSkillToggled(target, (ManasSkill)ResistanceSkills.SPIRITUAL_ATTACK_NULLIFICATION.get())) {
            TensuraDamageHelper.directSpiritualHurt(target, attacker, source, amount, 0.5F);
         } else {
            TensuraDamageHelper.directSpiritualHurt(target, attacker, source, amount, 0.0F);
         }
      }
   }

   @Inject(method = "onPressed", at = @At("HEAD"))
   private void shadowSlashUse(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode, CallbackInfo ci) {
      Level level = entity.level();
      if (entity instanceof Player player && mode == this.getModes(instance) - 1) {
         if (!PlatformEquipment.hasArtifact(entity, Artifacts.ShadowbindBracelet)) {
            entity.sendSystemMessage(Component.translatable("tensura.skill.mode.shadow_striker.shadow_slash.fail"));
            return;
         }

         ItemStack stack = PlatformEquipment.getArtifactStack(entity, Artifacts.ShadowbindBracelet);
         ArtifactItem artifactItem = (ArtifactItem)stack.getItem();
         if (!artifactItem.getArtifact().artifactEffects().get(2).unlockCondition().apply(stack, player)) {
            entity.sendSystemMessage(Component.translatable("tensura.skill.mode.shadow_striker.shadow_slash.fail.unlock"));
            return;
         }

         if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, 30.0, false);
            if (target != null && target.isAlive()) {
               float radius = -1.5F;
               float angle = (float) (Math.PI / 180.0) * target.yHeadRot;
               double extraX = radius * Mth.sin((float)(Math.PI + angle));
               double extraZ = radius * Mth.cos(angle);
               BlockPos behindPos = new BlockPos((int)(target.getX() + extraX), (int)target.getY(), (int)(target.getZ() + extraZ));
               if (level.getBlockState(behindPos).isSolidRender(level, behindPos)
                  && level.getBlockState(behindPos.above()).isSolidRender(level, behindPos.above())) {
                  behindPos = target.blockPosition();
               }

               SubordinateHelper.removeSpecificTargetInRadius(entity, 40.0, mob -> true);
               TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.SQUID_INK, 1.0, 10);
               entity.unRide();
               if (entity instanceof ServerPlayer serverPlayer) {
                  double d0 = target.getX() - behindPos.getX();
                  double d2 = target.getZ() - behindPos.getZ();
                  float yRot = Mth.wrapDegrees((float)(Mth.atan2(d2, d0) * 180.0F / (float)Math.PI) - 90.0F);
                  serverPlayer.teleportTo((ServerLevel)level, behindPos.getX(), behindPos.getY(), behindPos.getZ(), yRot, entity.getXRot());
               } else {
                  entity.teleportTo(behindPos.getX(), behindPos.getY(), behindPos.getZ());
               }

               entity.hasImpulse = true;
               TensuraParticleHelper.addServerParticlesAroundSelf(entity, ParticleTypes.SQUID_INK, 1.0);
               level.playSound((Player)null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ILLUSIONER_MIRROR_MOVE, SoundSource.PLAYERS, 1.0F, 1.0F);
            } else {
               entity.sendSystemMessage(Component.translatable("tensura.targeting.not_targeted").withStyle(ChatFormatting.RED));
            }

            AttributeInstance degrade = entity.getAttribute(TensuraAttributes.RESISTANCE_DEGRADATION);
            if (degrade != null) {
               degrade.addOrReplacePermanentModifier(
                  new AttributeModifier(ResourceLocation.fromNamespaceAndPath("ancient_artifacts", "degrade"), 1.0, Operation.ADD_VALUE)
               );
            }

            ItemStack artifactStack = PlatformEquipment.getArtifactStack(entity, Artifacts.ShadowbindBracelet);
            RefinementComponent refinement = ArtifactRefinement.get(artifactStack);
            int refinementSteps = Math.max(0, refinement.refinement() - 1);
            int maxRefinementSteps = RefinementComponent.GRADES.getOrDefault(refinement.grade(), 0);
            refinementSteps = Math.min(refinementSteps, maxRefinementSteps);
            float bonus = (instance.isMastered(entity) ? 70 : 35) + refinementSteps * 5;
            float amount = (float)(
               entity.getAttributeValue(Attributes.ATTACK_DAMAGE) * entity.getAttributeValue(ManasCoreAttributes.CRITICAL_DAMAGE_MULTIPLIER)
            );
            DamageSource damageSource = this.createSource(instance, entity, DamageTypes.MOB_ATTACK, mode);
            if (target.hurt(damageSource, amount + bonus)) {
               ItemStack mainHandItem = entity.getMainHandItem();
               mainHandItem.getItem().hurtEnemy(mainHandItem, target, entity);
               entity.level()
                  .playSound((Player)null, target.getX(), target.getY(), target.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, entity.getSoundSource(), 1.0F, 1.0F);
               if (level instanceof ServerLevel serverLevel) {
                  serverLevel.getChunkSource().broadcastAndSend(entity, new ClientboundAnimatePacket(entity, 4));
               }
            }

            degrade.removeModifier(ResourceLocation.fromNamespaceAndPath("ancient_artifacts", "degrade"));
            instance.setCoolDown(3, mode);
         }
      }
   }

   @Inject(method = "nextMode", at = @At("HEAD"), cancellable = true)
   private void addModeSwitch(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse, CallbackInfoReturnable<Integer> cir) {
      if (PlatformEquipment.hasArtifact(entity, Artifacts.ShadowbindBracelet) && entity instanceof Player player) {
         ItemStack stack = PlatformEquipment.getArtifactStack(entity, Artifacts.ShadowbindBracelet);
         ArtifactItem artifactItem = (ArtifactItem)stack.getItem();
         if (artifactItem.getArtifact().artifactEffects().get(2).unlockCondition().apply(stack, player)) {
            if (reverse) {
               cir.setReturnValue(mode == 0 ? 3 : mode - 1);
            } else {
               cir.setReturnValue(mode == 3 ? 0 : mode + 1);
            }
         }
      }

      if (mode == 3) {
         if (reverse) {
            cir.setReturnValue(2);
         } else {
            cir.setReturnValue(0);
         }
      }
   }
}
