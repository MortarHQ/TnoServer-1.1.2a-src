package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.entity.projectile.TailedBeastBombProjectile;
import io.github.Memoires.mysticism.entity.skill.UncontrolledJinchurikiEntity;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.entity.MysticismSkillEntityTypes;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.entity.human.CloneEntity;
import io.github.manasmods.tensura.entity.human.CloneEntity.CopySkill;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class JinchurikiSkill extends Skill {
   public static final UniqueMysticismSkillConfig.Jinchuriki CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Jinchuriki;
   private static final ResourceLocation JINCHURIKI = ResourceLocation.fromNamespaceAndPath("mysticism", "jinchuriki");

   public JinchurikiSkill() {
      super(SkillType.UNIQUE);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/jinchuriki.png");
   }

   public double getAcquiringMagiculeCost(ManasSkillInstance instance) {
      return CONFIG.mpAcquirement;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return mode == 0 ? 1 : 0;
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "jinchuriki.tailed_beast_bomb";
         case 1 -> "jinchuriki.tailed_beast_cloak";
         default -> super.getModeId(instance, mode);
      };
   }

   public boolean canIgnoreCoolDown(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return mode == 1 && entity.isShiftKeyDown();
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      super.onToggleOn(instance, entity);
      int amount = (int)Math.round((instance.isMastered(entity) ? CONFIG.epGainIncreaseMastered : CONFIG.epGainIncrease) / 2.0);
      AttributeInstance mpGain = entity.getAttribute(TensuraAttributes.MAGICULE_GAIN);
      if (mpGain != null) {
         mpGain.addOrReplacePermanentModifier(new AttributeModifier(JINCHURIKI, amount, Operation.ADD_VALUE));
      }

      AttributeInstance apGain = entity.getAttribute(TensuraAttributes.AURA_GAIN);
      if (apGain != null) {
         apGain.addOrReplacePermanentModifier(new AttributeModifier(JINCHURIKI, amount, Operation.ADD_VALUE));
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      super.onToggleOff(instance, entity);
      int amount = (int)Math.round((instance.isMastered(entity) ? CONFIG.epGainIncreaseMastered : CONFIG.epGainIncrease) / 2.0);
      AttributeInstance mpGain = entity.getAttribute(TensuraAttributes.MAGICULE_GAIN);
      if (mpGain != null) {
         mpGain.addOrReplacePermanentModifier(new AttributeModifier(JINCHURIKI, amount, Operation.ADD_VALUE));
      }

      AttributeInstance apGain = entity.getAttribute(TensuraAttributes.AURA_GAIN);
      if (apGain != null) {
         apGain.addOrReplacePermanentModifier(new AttributeModifier(JINCHURIKI, amount, Operation.ADD_VALUE));
      }
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0 && !instance.isSubInstance() && !instance.isTemporarySkill();
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      Level level = entity.level();
      if (mode == 1) {
         if (entity.isShiftKeyDown()) {
            if (entity instanceof Player player) {
               player.playNotifySound((SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               player.displayClientMessage(
                  Component.translatable(
                        "mysticism.skill.mode.jinchuriki.tailed_beast_cloak.check_uses", new Object[]{instance.getOrCreateTag().getInt("Uses")}
                     )
                     .withStyle(ChatFormatting.AQUA),
                  false
               );
               player.displayClientMessage(
                  Component.translatable("mysticism.skill.mode.jinchuriki.tailed_beast_cloak.current_cloak", new Object[]{getCloakState(instance)})
                     .withStyle(ChatFormatting.AQUA),
                  false
               );
            }
         } else if (instance.getOrCreateTag().getInt("Uses") <= CONFIG.controlledState) {
            IExistence existence = TensuraStorages.getExistenceFrom(entity);
            double MP = EnergyHelper.getMaxMagicule(entity);
            double AP = EnergyHelper.getMaxAura(entity);
            UncontrolledJinchurikiEntity clone = summonJinchurikiClone(
               instance, entity, entity.level(), MP, existence.getMagicule(), AP, existence.getAura(), entity.position()
            );
            SubordinateHelper.setAggressive(clone);
            if (entity instanceof ServerPlayer player) {
               GameType previousGameMode = player.gameMode.getGameModeForPlayer();
               CompoundTag tag = instance.getOrCreateTag();
               tag.putInt("PriorGamemode", previousGameMode.getId());
               tag.putBoolean("CloakActive", true);
               tag.putUUID("UncontrolledClone", clone.getUUID());
               instance.addMasteryPoint(entity);
               instance.getOrCreateTag().putInt("Uses", instance.getOrCreateTag().getInt("Uses") + 1);
               instance.markDirty();
               player.setGameMode(GameType.SPECTATOR);
               player.setCamera(clone);
            }
         } else if (entity.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.TAILED_BEAST_CLOAK))) {
            entity.removeEffect(TensuraMobEffects.getReference(MysticismMobEffects.TAILED_BEAST_CLOAK));
         } else {
            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENDER_DRAGON_GROWL, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
            applyTailedBeastCloak(instance, entity);
         }
      } else {
         this.discardBomb(instance, entity);
         TailedBeastBombProjectile bomb = new TailedBeastBombProjectile(entity.level(), entity);
         bomb.noPhysics = true;
         bomb.setSpeed(1.0F);
         bomb.setSize(0.4F);
         bomb.setSkill(entity, instance, this, mode);
         bomb.setNoGravity(true);
         bomb.setPos(this.getBombPosition(entity, bomb));
         entity.level().addFreshEntity(bomb);
         CompoundTag tag = instance.getOrCreateTag();
         tag.putInt("BombID", bomb.getId());
         tag.putInt("PowerScale", 0);
         instance.markDirty();
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (mode == 0) {
         Level level = entity.level();
         CompoundTag tag = instance.getOrCreateTag();
         int id = tag.getInt("BombID");
         if (level.getEntity(id) instanceof TailedBeastBombProjectile bomb) {
            bomb.setAge(0);
            bomb.setPos(this.getBombPosition(entity, bomb));
            double max = instance.isMastered(entity) ? CONFIG.maxMultiplierMastered : CONFIG.maxMultiplier;
            int holdTime = Math.max(1, (instance.isMastered(entity) ? CONFIG.holdTimeMastered : CONFIG.holdTime) / 10);
            if (heldTicks > 0 && heldTicks % holdTime == 0 && tag.getInt("PowerScale") < (int)(max * 10.0)) {
               tag.putInt("PowerScale", tag.getInt("PowerScale") + 1);
               instance.markDirty();
               float power = tag.getInt("PowerScale") / 10.0F;
               if (power >= 1.0F) {
                  bomb.setSkill(entity, instance, this, mode, power);
                  bomb.setSpeed(Math.min(0.4F * power, 2.0F));
                  bomb.setDamage(CONFIG.baseDamage * power);
                  if (power > CONFIG.explosionThreshold) {
                     bomb.setBlastRadius(CONFIG.explosionBaseRadius + CONFIG.explosionRadiusPer10Charge * (int)(power / 10.0F));
                     bomb.setExplosionDamage(CONFIG.explosionDamage);
                  }
               }

               bomb.setSize(0.4F + 0.1F * power);
            }

            TensuraParticleHelper.addServerParticlesAroundSelf(entity, (ParticleOptions)TensuraParticleTypes.PLASMA_FIRE.get(), 1.0);
            if (entity instanceof Player player) {
               player.displayClientMessage(
                  Component.translatable("tensura.skill.power_scale", new Object[]{tag.getInt("PowerScale") / 10.0})
                     .setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_GREEN)),
                  true
               );
            }

            return true;
         } else {
            tag.putInt("BombID", 0);
            instance.markDirty();
            return false;
         }
      } else {
         return false;
      }
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (mode == 0) {
         this.shootBomb(instance, entity, mode);
      }
   }

   private Vec3 getBombPosition(LivingEntity entity, TailedBeastBombProjectile bomb) {
      Vec3 look = entity.getLookAngle().scale(entity.getBbWidth() / 2.0F + bomb.getBbWidth());
      return entity.getEyePosition().add(look);
   }

   private void discardBomb(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      if (entity.level().getEntity(tag.getInt("BombID")) instanceof TailedBeastBombProjectile leftover) {
         leftover.discard();
      }

      tag.putInt("BombID", 0);
      tag.putInt("PowerScale", 0);
      instance.markDirty();
   }

   private void shootBomb(ManasSkillInstance instance, LivingEntity entity, int mode) {
      Level level = entity.level();
      CompoundTag tag = instance.getOrCreateTag();
      int id = tag.getInt("BombID");
      if (level.getEntity(id) instanceof TailedBeastBombProjectile bomb) {
         float power = tag.getInt("PowerScale") / 10.0F;
         tag.putInt("PowerScale", 0);
         instance.markDirty();
         if (power < 1.0F) {
            bomb.discard();
            tag.putInt("BombID", 0);
            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.EVOKER_CAST_SPELL, TensuraSkill.ABILITY_SOUND, 3.0F, 1.0F);
         } else if (EnergyHelper.isOutOfEnergy(entity, CONFIG.apCostPerCharge * power, CONFIG.mpCostPerCharge * power)) {
            bomb.discard();
            tag.putInt("BombID", 0);
            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.EVOKER_CAST_SPELL, TensuraSkill.ABILITY_SOUND, 3.0F, 1.0F);
         } else {
            entity.swing(InteractionHand.MAIN_HAND, true);
            if (power >= 2.0F) {
               instance.addMasteryPoint(entity);
            }

            bomb.setSkill(entity, instance, this, mode, power);
            bomb.noPhysics = false;
            tag.putInt("BombID", 0);
            bomb.shootFromRot(entity.getLookAngle());
            level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BLAZE_SHOOT, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
         }
      } else {
         tag.putInt("BombID", 0);
         instance.markDirty();
      }
   }

   private static String getCloakState(ManasSkillInstance instance) {
      int uses = instance.getOrCreateTag().getInt("Uses");
      if (uses <= CONFIG.v1Cloak) {
         return "Initial";
      } else if (uses <= CONFIG.v2Cloak) {
         return "v1 Cloak";
      } else if (uses <= CONFIG.controlledState) {
         return "v2 Cloak";
      } else {
         return uses <= CONFIG.perfectControlledState ? "Controlled State" : "Perfect Controlled State";
      }
   }

   private static UncontrolledJinchurikiEntity summonJinchurikiClone(
      ManasSkillInstance instance, LivingEntity entity, Level level, double MP, double currentMP, double AP, double currentAP, Vec3 position
   ) {
      EntityType<UncontrolledJinchurikiEntity> type = (EntityType<UncontrolledJinchurikiEntity>)MysticismSkillEntityTypes.UNCONTROLLED_JINCHURIKI.get();
      UncontrolledJinchurikiEntity clone = new UncontrolledJinchurikiEntity(type, level);
      if (entity instanceof Player player) {
         clone.tame(player);
      }

      clone.setSkill(instance);
      clone.copyStatsAndSkills(entity, CopySkill.ALL, true);
      clone.setHealth(entity.getMaxHealth());
      EnergyHelper.setMaxMagicule(clone, MP);
      EnergyHelper.setMaxAura(clone, AP);
      IExistence existence = TensuraStorages.getExistenceFrom(clone);
      existence.setMagicule(Math.min(currentMP, MP));
      existence.setAura(Math.min(currentAP, AP));
      existence.markDirty();
      clone.copyEquipments(entity);
      clone.setPos(position);
      CloneEntity.copyRotation(entity, clone);
      level.addFreshEntity(clone);
      applyTailedBeastCloak(instance, clone);
      int duration = CONFIG.cloakPreControlLifespan * 20;
      if (entity instanceof Player player) {
         player.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(MysticismMobEffects.TAILED_BEAST_CLOAK), duration, 0, false, false, false));
      }

      level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENDER_DRAGON_GROWL, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
      double size = clone.getAttributeValue(Attributes.SCALE) * 4.0;
      TensuraParticleHelper.addServerAuraParticles(clone, TensuraParticleUtils.getBlackAura(0.5F, (float)size, -0.3F), 10, 0.01);
      return clone;
   }

   public static void applyTailedBeastCloak(ManasSkillInstance instance, LivingEntity entity) {
      int uses = instance.getOrCreateTag().getInt("Uses");
      int amplifier;
      if (uses <= CONFIG.v1Cloak) {
         amplifier = 0;
      } else if (uses <= CONFIG.v2Cloak) {
         amplifier = 1;
      } else if (uses <= CONFIG.controlledState) {
         amplifier = 2;
      } else if (uses <= CONFIG.perfectControlledState) {
         amplifier = 3;
      } else {
         amplifier = 4;
      }

      int duration = CONFIG.cloakPreControlLifespan * 20;
      entity.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(MysticismMobEffects.TAILED_BEAST_CLOAK), duration, amplifier, false, false, false));
   }
}
