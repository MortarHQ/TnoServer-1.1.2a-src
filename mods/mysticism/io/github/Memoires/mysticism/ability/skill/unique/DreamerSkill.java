package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.damage.MysticismDamageTypes;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.SpawnPointHelper;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class DreamerSkill extends Skill {
   protected static final UniqueMysticismSkillConfig.Dreamer CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Dreamer;

   public DreamerSkill() {
      super(SkillType.UNIQUE);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/dreamer.png");
   }

   public double getAcquiringMagiculeCost(ManasSkillInstance instance) {
      return CONFIG.mpAcquirement;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return this.isInSlot(entity);
   }

   public int getModes(ManasSkillInstance instance) {
      return 3;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "dreamer.dream";
         case 1 -> "dreamer.hypnosis";
         case 2 -> "dreamer.dream_eater";
         default -> super.getModeId(instance, mode);
      };
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode == 0 ? 2 : mode - 1;
      } else {
         return mode == 2 ? 0 : mode + 1;
      }
   }

   public void onTick(ManasSkillInstance instance, LivingEntity living) {
      IExistence existence = TensuraStorages.getExistenceFrom(living);
      double maxMP = EnergyHelper.getMaxMagicule(living);
      double currentMP = existence.getMagicule();
      double recoveryRate = instance.isMastered(living) ? CONFIG.magiculeRecoveryMastered : CONFIG.magiculeRecovery;
      if (recoveryRate > 0.0) {
         recoveryRate /= 100.0;
         existence.setMagicule(Math.min(currentMP + maxMP * recoveryRate, maxMP));
      }
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> {
            double mpCost = CONFIG.drainMode ? CONFIG.dreamSetFlatCost : EnergyHelper.getMaxMagicule(entity) * (CONFIG.dreamSetPercentageCost / 100.0);
            yield instance.getOrCreateTag().getBoolean("Dream") ? CONFIG.dreamActivateCost : mpCost;
         }
         case 1 -> CONFIG.hypnosisCost;
         case 2 -> CONFIG.dreamEaterCost;
         default -> 500.0;
      };
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (mode == 0 && !EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
         this.dream(instance, entity, mode);
      }
   }

   public boolean onDeath(ManasSkillInstance instance, LivingEntity owner, DamageSource source) {
      CompoundTag tag = instance.getOrCreateTag();
      if (source.getEntity() instanceof Player creativePlayer && creativePlayer.isCreative()) {
         return true;
      } else if (owner instanceof Player player && tag.getBoolean("Dream") && !EnergyHelper.isOutOfEnergy(owner, instance, 0)) {
         player.removeAllEffects();
         player.invulnerableTime = 60;
         ArrayList<Object> stats = this.readStatsFromTag(tag);
         this.restoreStats(player, stats);
         BlockPos position = this.readLocationFromTag(tag);
         ResourceKey<Level> dimension = this.readLevelFromTag(tag);
         if (!player.level().dimension().equals(dimension)) {
            ServerLevel targetLevel = player.getServer().getLevel(dimension);
            if (targetLevel != null) {
               SpawnPointHelper.teleportToAcrossDimensions(
                  player, targetLevel, position.getX(), position.getY(), position.getZ(), player.getXRot(), player.getYRot()
               );
            } else {
               player.displayClientMessage(
                  Component.translatable("mysticism.skill.mode.dreamer.dream.null_level").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), false
               );
               player.level()
                  .playSound(
                     null,
                     player.getX(),
                     player.getY(),
                     player.getZ(),
                     (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(),
                     TensuraSkill.ABILITY_SOUND,
                     1.0F,
                     1.0F
                  );
            }
         } else {
            player.teleportTo(position.getX(), position.getY(), position.getZ());
         }

         player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.END_PORTAL_SPAWN, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
         player.displayClientMessage(
            Component.translatable("mysticism.skill.mode.dreamer.dream.death").setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_PURPLE)), false
         );
         instance.addMasteryPoint(player);
         int cooldown = instance.isMastered(player) ? CONFIG.dreamCooldownMastered : CONFIG.dreamCooldown;
         instance.setCoolDown(cooldown, 0);
         tag.putBoolean("Dream", false);
         return false;
      } else {
         return true;
      }
   }

   public void dream(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (entity instanceof Player player) {
         CompoundTag tag = instance.getOrCreateTag();
         if (tag.getBoolean("Dream")) {
            if (player.isShiftKeyDown()) {
               player.displayClientMessage(
                  Component.translatable("mysticism.skill.mode.dreamer.dream.cancel").setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_PURPLE)), false
               );
               tag.putBoolean("Dream", false);
            } else if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
               ArrayList<Object> stats = this.readStatsFromTag(tag);
               this.restoreStats(player, stats);
               BlockPos position = this.readLocationFromTag(tag);
               ResourceKey<Level> dimension = this.readLevelFromTag(tag);
               if (!player.level().dimension().equals(dimension)) {
                  ServerLevel targetLevel = player.getServer().getLevel(dimension);
                  if (targetLevel != null) {
                     SpawnPointHelper.teleportToAcrossDimensions(
                        entity, targetLevel, position.getX(), position.getY(), position.getZ(), entity.getXRot(), entity.getYRot()
                     );
                  } else {
                     player.displayClientMessage(
                        Component.translatable("mysticism.skill.mode.dreamer.dream.null_level").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)), false
                     );
                     player.level()
                        .playSound(
                           null,
                           player.getX(),
                           player.getY(),
                           player.getZ(),
                           (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(),
                           TensuraSkill.ABILITY_SOUND,
                           1.0F,
                           1.0F
                        );
                  }
               } else {
                  player.teleportTo(position.getX(), position.getY(), position.getZ());
               }

               player.level()
                  .playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.END_PORTAL_SPAWN, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               player.displayClientMessage(
                  Component.translatable("mysticism.skill.mode.dreamer.dream.restore").setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_PURPLE)), false
               );
               instance.addMasteryPoint(player);
               int cooldown = instance.isMastered(entity) ? CONFIG.dreamCooldownMastered : CONFIG.dreamCooldown;
               instance.setCoolDown(cooldown, mode);
               tag.putBoolean("Dream", false);
            }
         } else if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            ArrayList<Object> stats = this.createDream(instance, entity);
            this.writeStatsToTag(tag, stats, player);
            tag.putBoolean("Dream", true);
            player.level()
               .playSound(null, player.getX(), player.getY(), player.getZ(), TensuraSoundEvents.GENERIC_CAST, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
            player.displayClientMessage(
               Component.translatable("mysticism.skill.mode.dreamer.dream.create").setStyle(Style.EMPTY.withColor(ChatFormatting.DARK_PURPLE)), false
            );
         }
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      Level level = entity.level();
      switch (mode) {
         case 1:
            float radius = instance.isMastered(entity) ? (float)CONFIG.hypnosisRadiusMastered : (float)CONFIG.hypnosisRadius;
            if (heldTicks % 20 == 0) {
               level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.WARDEN_SONIC_BOOM, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
               TensuraParticleHelper.spawnServerParticles(
                  level, TensuraParticleUtils.getDrowsinessWave(radius, 0.0F), entity.getX(), entity.getY() + entity.getBbHeight() * 0.5, entity.getZ()
               );
            }

            List<LivingEntity> list = level.getEntitiesOfClass(
               LivingEntity.class, entity.getBoundingBox().inflate(radius), living -> !living.is(entity) && living.isAlive() && !living.isAlliedTo(entity)
            );
            if (!list.isEmpty()) {
               if (heldTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0 && heldTicks > 0) {
                  instance.addMasteryPoint(entity);
               }

               for (LivingEntity targetx : list) {
                  if (!(targetx instanceof Player player && player.getAbilities().invulnerable)) {
                     int originalDuration = instance.isMastered(entity) ? CONFIG.hypnosisDurationMastered : CONFIG.hypnosisDuration;
                     MobEffectInstance drowsiness = targetx.getEffect(TensuraMobEffects.getReference(TensuraMobEffects.DROWSINESS));
                     int initialLevel = instance.isMastered(entity) ? CONFIG.hypnosisLevelMastered : CONFIG.hypnosisLevel;
                     int duration;
                     if (drowsiness != null && heldTicks > 0) {
                        duration = drowsiness.getDuration() + 2;
                     } else {
                        duration = originalDuration;
                     }

                     MobEffectInstance effectInstance = new MobEffectInstance(
                        TensuraMobEffects.getReference(TensuraMobEffects.DROWSINESS), duration, initialLevel - 1, false, false, false
                     );
                     TensuraMobEffect.addEffect(targetx, effectInstance, entity, this);
                  }
               }
            }
            break;
         case 2:
            CompoundTag tag = instance.getOrCreateTag();
            int second = tag.getInt("heldSeconds");
            float distance = instance.isMastered(entity) ? CONFIG.dreamEaterRangeMastered : CONFIG.dreamEaterRange;
            LivingEntity target = (LivingEntity)ObjectSelectionHelper.getTargetingEntity(LivingEntity.class, entity, distance, 0.5, true, true);
            if (target != null && target.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.DROWSINESS))) {
               if (!tag.contains("target") || Objects.equals(tag.getUUID("target"), target.getUUID())) {
                  if (heldTicks % 100 == 0 && heldTicks > 0) {
                     this.addMasteryPoint(instance, entity);
                  }

                  int controlling = 5;
                  if (heldTicks % 20 == 0) {
                     tag.putInt("heldSeconds", second + 1);
                  }

                  if (entity instanceof Player player) {
                     player.displayClientMessage(
                        Component.translatable("tensura.skill.time_held.max", new Object[]{second, controlling})
                           .setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)),
                        true
                     );
                     player.playNotifySound(SoundEvents.EVOKER_PREPARE_ATTACK, TensuraSkill.ABILITY_SOUND, 0.2F, 1.0F);
                  }

                  if (second >= controlling) {
                     DamageSource damageSource = this.createSource(instance, entity, MysticismDamageTypes.DREAM_EATER, mode);
                     float damage = instance.isMastered(entity) ? CONFIG.dreamEaterDamageMastered : CONFIG.dreamEaterDamage;
                     float spiritualDamage = instance.isMastered(entity) ? CONFIG.dreamEaterSpiritualDamageMastered : CONFIG.dreamEaterSpiritualDamage;
                     target.hurt(damageSource, damage);
                     TensuraDamageHelper.directSpiritualHurt(target, entity, spiritualDamage);
                     tag.putInt("heldSeconds", 0);
                     instance.decreaseCoolDown(60, 0);
                  }
               }
            } else {
               tag.putInt("heldSeconds", Math.max(0, second - 1));
               if (tag.getInt("heldSeconds") <= 0 || target == null) {
                  tag.remove("target");
               }

               if (entity instanceof Player player) {
                  player.displayClientMessage(
                     Component.translatable("tensura.skill.time_held", new Object[]{second}).setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD)), true
                  );
               }
            }
      }

      return true;
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (mode == 2 || mode == 3) {
         CompoundTag tag = instance.getOrCreateTag();
         tag.remove("target");
         tag.putInt("heldSeconds", 0);
      }
   }

   private ArrayList<Object> createDream(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         float currentHealth = player.getHealth();
         double currentMP = TensuraStorages.getExistenceFrom(player).getMagicule();
         double currentAP = TensuraStorages.getExistenceFrom(player).getAura();
         double currentSHP = TensuraStorages.getExistenceFrom(player).getSpiritualHealth();
         ArrayList<Object> stats = new ArrayList<>();
         stats.add(currentHealth);
         stats.add(currentMP);
         stats.add(currentAP);
         stats.add(currentSHP);
         return stats;
      } else {
         return new ArrayList<>();
      }
   }

   private void restoreStats(Player player, ArrayList<Object> stats) {
      if (stats.size() == 4) {
         float health = (Float)stats.get(0);
         double mp = (Double)stats.get(1);
         double ap = (Double)stats.get(2);
         double shp = (Double)stats.get(3);
         IExistence existence = TensuraStorages.getExistenceFrom(player);
         player.setHealth(Math.min(health, player.getMaxHealth()));
         existence.setMagicule(Math.min(mp, EnergyHelper.getMaxMagicule(player)));
         existence.setAura(Math.min(ap, EnergyHelper.getMaxAura(player)));
         existence.setSpiritualHealth(shp);
      }
   }

   private void writeStatsToTag(CompoundTag tag, ArrayList<Object> stats, Player player) {
      tag.putFloat("Health", (Float)stats.get(0));
      tag.putDouble("MP", (Double)stats.get(1));
      tag.putDouble("AP", (Double)stats.get(2));
      tag.putDouble("SHP", (Double)stats.get(3));
      tag.putDouble("PosX", player.getX());
      tag.putDouble("PosY", player.getY());
      tag.putDouble("PosZ", player.getZ());
      tag.putString("LevelNamespace", player.level().dimension().location().getNamespace());
      tag.putString("LevelPath", player.level().dimension().location().getPath());
   }

   private ArrayList<Object> readStatsFromTag(CompoundTag tag) {
      ArrayList<Object> stats = new ArrayList<>();
      stats.add(tag.getFloat("Health"));
      stats.add(tag.getDouble("MP"));
      stats.add(tag.getDouble("AP"));
      stats.add(tag.getDouble("SHP"));
      return stats;
   }

   private BlockPos readLocationFromTag(CompoundTag tag) {
      double x = tag.getDouble("PosX");
      double y = tag.getDouble("PosY");
      double z = tag.getDouble("PosZ");
      return new MutableBlockPos(x, y, z);
   }

   private ResourceKey<Level> readLevelFromTag(CompoundTag tag) {
      String levelNamespace = tag.getString("LevelNamespace");
      String levelPath = tag.getString("LevelPath");
      return ResourceKey.create(Registries.DIMENSION, ResourceLocation.fromNamespaceAndPath(levelNamespace, levelPath));
   }
}
