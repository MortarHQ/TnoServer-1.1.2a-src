package io.github.Memoires.mysticism.ability.skill.unique;

import dev.architectury.networking.NetworkManager;
import io.github.Memoires.mysticism.client.ability.CaptivatorClientState;
import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.network.s2c.SyncLieModePacket;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.impl.TickingSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.intrinsic.CharmSkill;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.List;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class CaptivatorSkill extends Skill {
   protected static final UniqueMysticismSkillConfig.Captivator CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class))
      .Captivator;

   public CaptivatorSkill() {
      super(SkillType.UNIQUE);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath(
         "mysticism",
         CaptivatorClientState.isLieMode() ? "textures/skill/unique/captivator/captivator_lie.png" : "textures/skill/unique/captivator/captivator_truth.png"
      );
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0 && this.isInSlot(entity);
   }

   public void onTick(ManasSkillInstance instance, LivingEntity living) {
      TensuraMobEffect.addEffect(living, new MobEffectInstance(MobEffects.HERO_OF_THE_VILLAGE, 240, 4, false, false, false), living, this);
   }

   public int getModes(ManasSkillInstance instance) {
      return 4;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "captivator.performance";
         case 1 -> "captivator.masquerade";
         case 2 -> "captivator.unveil";
         case 3 -> "captivator.star_power";
         default -> super.getModeId(instance, mode);
      };
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> CONFIG.mpPerformanceCost;
         case 1 -> CONFIG.mpMasqueradeCost;
         case 2 -> CONFIG.mpUnveilCost;
         case 3 -> CONFIG.mpStarPowerCost;
         default -> 0.0;
      };
   }

   private boolean checkCurrentMode(ManasSkillInstance instance) {
      return instance.getOrCreateTag().getBoolean("lieMode");
   }

   private void switchSkillMode(ManasSkillInstance instance, LivingEntity entity) {
      boolean newLieMode = !instance.getOrCreateTag().getBoolean("lieMode");
      instance.getOrCreateTag().putBoolean("lieMode", newLieMode);
      if (entity instanceof ServerPlayer player) {
         NetworkManager.sendToPlayer(player, new SyncLieModePacket(newLieMode));
      }
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (!reverse) {
         if (this.checkCurrentMode(instance)) {
            return switch (mode) {
               case 0 -> 1;
               case 1 -> instance.isMastered(entity) ? 3 : 0;
               default -> 0;
            };
         } else {
            return switch (mode) {
               case 0 -> 2;
               case 2 -> instance.isMastered(entity) ? 3 : 0;
               default -> 0;
            };
         }
      } else if (this.checkCurrentMode(instance)) {
         return switch (mode) {
            case 0 -> instance.isMastered(entity) ? 3 : 1;
            case 3 -> 1;
            default -> 0;
         };
      } else {
         return switch (mode) {
            case 0 -> instance.isMastered(entity) ? 3 : 2;
            case 3 -> 2;
            default -> 0;
         };
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      switch (mode) {
         case 1:
            this.masquerade(instance, entity, mode);
            break;
         case 2:
            this.unveil(instance, entity, mode);
            break;
         case 3:
            this.starPower(instance, entity, mode);
            break;
         default:
            super.onPressed(instance, entity, keyNumber, mode);
      }
   }

   private void masquerade(ManasSkillInstance instance, LivingEntity entity, int mode) {
      List<LivingEntity> list = entity.level()
         .getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(CONFIG.masqueradeRange), living -> !living.is(entity) && living.isAlive());
      if (!list.isEmpty()) {
         int level = instance.isMastered(entity) ? 3 : 1;

         for (LivingEntity target : list) {
            if (target.isAlliedTo(entity)) {
               target.level()
                  .playSound(null, target.getX(), target.getY(), target.getZ(), TensuraSoundEvents.BUFF_ACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               MobEffectInstance speed = new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, level, false, false, false);
               MobEffectInstance strength = new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.STRENGTHEN), 200, level, false, false, false);
               TensuraMobEffect.addEffect(target, speed, entity, this, mode);
               TensuraMobEffect.addEffect(target, strength, entity, this, mode);
               target.heal(target.getMaxHealth() * 0.25F);
               IExistence existence = TensuraStorages.getExistenceFrom(target);
               existence.setSpiritualHealth(existence.getSpiritualHealth() + existence.getSpiritualHealth() * 0.25);
               existence.markDirty();
            } else {
               if (target instanceof Player targetPlayer && targetPlayer.getAbilities().invulnerable) {
                  continue;
               }

               MobEffectInstance blindness = new MobEffectInstance(MobEffects.BLINDNESS, 200, level, false, false, false);
               TensuraMobEffect.addEffect(target, blindness, entity, this, mode);
            }

            instance.addMasteryPoint(entity);
            instance.setCoolDown(CONFIG.masqueradeCooldown, mode);
         }
      }
   }

   private void unveil(ManasSkillInstance instance, LivingEntity entity, int mode) {
      List<LivingEntity> list = entity.level()
         .getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(CONFIG.unveilRange), living -> !living.is(entity) && living.isAlive());
      if (!list.isEmpty()) {
         entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.GENERIC_CAST, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);

         for (LivingEntity target : list) {
            if (!(target instanceof Player targetPlayer && targetPlayer.getAbilities().invulnerable)) {
               target.level()
                  .playSound(null, target.getX(), target.getY(), target.getZ(), TensuraSoundEvents.DEBUFF_ACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               if (!SubordinateHelper.isSubordinate(target, entity) || !target.isAlliedTo(entity)) {
                  if (instance.isMastered(entity)) {
                     MobEffectInstance paralysis = new MobEffectInstance(
                        TensuraMobEffects.getReference(TensuraMobEffects.PARALYSIS), 200, 1, false, false, false
                     );
                     MobEffectInstance marked = new MobEffectInstance(
                        TensuraMobEffects.getReference(MysticismMobEffects.MARKED_FOR_DEATH), 200, 0, false, false, false
                     );
                     TensuraMobEffect.addEffect(target, paralysis, entity, this, mode);
                     TensuraMobEffect.addEffect(target, marked, entity, this, mode);
                  } else {
                     MobEffectInstance paralysis = new MobEffectInstance(
                        TensuraMobEffects.getReference(TensuraMobEffects.PARALYSIS), 200, 1, false, false, false
                     );
                     TensuraMobEffect.addEffect(target, paralysis, entity, this, mode);
                  }
               }
            }
         }

         instance.addMasteryPoint(entity);
         instance.setCoolDown(CONFIG.unveilCooldown, mode);
      }
   }

   private void starPower(ManasSkillInstance instance, LivingEntity entity, int mode) {
      Level level = entity.level();
      Vec3 selfEyePos = entity.getEyePosition();

      for (LivingEntity other : level.getEntitiesOfClass(
         LivingEntity.class, entity.getBoundingBox().inflate(20.0), e -> e.isAlive() && !e.is(entity) && !e.isAlliedTo(entity)
      )) {
         other.level().playSound(null, other.getX(), other.getY(), other.getZ(), TensuraSoundEvents.DEBUFF_ACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
         if (!(other instanceof Player targetPlayer && targetPlayer.getAbilities().invulnerable)) {
            Vec3 otherEyePos = other.getEyePosition();
            Vec3 otherLookVec = other.getLookAngle().normalize();
            Vec3 toPlayerVec = selfEyePos.subtract(otherEyePos).normalize();
            double dot = otherLookVec.dot(toPlayerVec);
            if (dot > 0.5) {
               MobEffectInstance dazzled = new MobEffectInstance(TensuraMobEffects.getReference(MysticismMobEffects.DAZZLED), 200, 0, false, false, false);
               TensuraMobEffect.addEffect(other, dazzled, entity, this, mode);
            }
         }
      }

      instance.setCoolDown(CONFIG.starPowerCooldown, mode);
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity living, int heldTicks, int mode) {
      if (mode == 0) {
         if (heldTicks % 20 == 0 && !EnergyHelper.isOutOfEnergy(living, instance, mode)) {
            this.switchSkillMode(instance, living);
            instance.setCoolDown(CONFIG.performanceCooldown, mode);
            if (living instanceof Player player) {
               if (this.checkCurrentMode(instance)) {
                  player.displayClientMessage(
                     Component.translatable("mysticism.skill.mode.captivator.performance.truth").setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GRAY)),
                     false
                  );
               } else {
                  player.displayClientMessage(
                     Component.translatable("mysticism.skill.mode.captivator.performance.lie").setStyle(Style.EMPTY.withColor(ChatFormatting.LIGHT_PURPLE)),
                     false
                  );
               }
            }

            return false;
         }

         if (heldTicks > 600) {
            this.switchSkillMode(instance, living);
            instance.setCoolDown(CONFIG.performanceCooldown, mode);
            if (living instanceof Player player) {
               if (this.checkCurrentMode(instance)) {
                  player.displayClientMessage(
                     Component.translatable("mysticism.skill.mode.captivator.performance.truth").setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GRAY)),
                     false
                  );
               } else {
                  player.displayClientMessage(
                     Component.translatable("mysticism.skill.mode.captivator.performance.lie").setStyle(Style.EMPTY.withColor(ChatFormatting.LIGHT_PURPLE)),
                     false
                  );
               }
            }

            instance.addMasteryPoint(living);
            return false;
         }

         UUID uuid = living.getUUID();
         Level level = living.level();
         int effectLevel = instance.isMastered(living) ? 1 : 0;

         for (LivingEntity target : level.getEntitiesOfClass(
            LivingEntity.class, living.getBoundingBox().inflate(CONFIG.performanceRange), targetx -> !targetx.is(living) && targetx.isAlive()
         )) {
            if (!(target instanceof Player targetPlayer && targetPlayer.getAbilities().invulnerable)) {
               IExistence existence = TensuraStorages.getExistenceFrom(target);
               if (existence.getTemporaryOwner() == uuid || existence.getPermanentOwner() == uuid) {
                  MobEffectInstance inspiration = new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.INSPIRATION), 240, effectLevel, false, false, false
                  );
                  TensuraMobEffect.addEffect(target, inspiration, living, this, mode);
               } else if (CharmSkill.canMindControl(target, level)
                  && !target.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.RAMPAGE))
                  && !existence.isSpiritualForm()
                  && !SkillUtils.isSkillToggled(target, (ManasSkill)ResistanceSkills.SPIRITUAL_ATTACK_NULLIFICATION.get())
                  && !target.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.MIND_CONTROL))) {
                  int duration = SkillUtils.isSkillToggled(target, (ManasSkill)ResistanceSkills.SPIRITUAL_ATTACK_RESISTANCE.get()) ? 100 : 600;
                  MobEffectInstance mindControl = new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.MIND_CONTROL), duration, 0, false, false, false
                  );
                  TensuraMobEffect.addEffect(target, mindControl, living, instance.getSkill(), mode);
                  existence.setTemporaryOwner(uuid);
                  existence.addNeutralTarget(uuid);
                  if (target instanceof Mob mob) {
                     SubordinateHelper.removeTarget(mob);
                  }

                  if (living instanceof Player player && target instanceof TamableAnimal animal) {
                     animal.tame(player);
                  }

                  existence.markDirty();
                  TensuraParticleHelper.addServerParticlesAroundSelf(target, ParticleTypes.DAMAGE_INDICATOR);
                  level.playSound(
                     null,
                     living.getX(),
                     living.getY(),
                     living.getZ(),
                     (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(),
                     TensuraSkill.ABILITY_SOUND,
                     1.0F,
                     0.5F
                  );
               }
            }
         }
      }

      return true;
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (mode == 0 && TickingSkill.isTickingSkill(entity, this, 0)) {
         this.switchSkillMode(instance, entity);
         instance.setCoolDown(CONFIG.performanceCooldown, mode);
         if (entity instanceof Player player) {
            if (this.checkCurrentMode(instance)) {
               player.displayClientMessage(
                  Component.translatable("mysticism.skill.mode.captivator.performance.truth").setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GRAY)), false
               );
            } else {
               player.displayClientMessage(
                  Component.translatable("mysticism.skill.mode.captivator.performance.lie").setStyle(Style.EMPTY.withColor(ChatFormatting.LIGHT_PURPLE)), false
               );
            }
         }
      }
   }
}
