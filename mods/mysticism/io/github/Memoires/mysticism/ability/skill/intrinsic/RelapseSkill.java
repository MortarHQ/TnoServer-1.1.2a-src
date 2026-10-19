package io.github.Memoires.mysticism.ability.skill.intrinsic;

import io.github.Memoires.mysticism.config.ability.skill.IntrinsicMysticismSkillConfig;
import io.github.Memoires.mysticism.entity.skill.RelapseCloneEntity;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.entity.MysticismSkillEntityTypes;
import io.github.Memoires.mysticism.registry.race.MysticismRaces;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.subclass.ICloning;
import io.github.manasmods.tensura.entity.human.CloneEntity;
import io.github.manasmods.tensura.entity.human.CloneEntity.CopySkill;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.entity.HumanEntityTypes;
import io.github.manasmods.tensura.registry.skill.IntrinsicSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.Set;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class RelapseSkill extends Skill implements ICloning {
   public static final IntrinsicMysticismSkillConfig.Relapse CONFIG = ((IntrinsicMysticismSkillConfig)ConfigRegistry.getConfig(
         IntrinsicMysticismSkillConfig.class
      ))
      .Relapse;
   private static final ResourceLocation CLONE_RELAPSE = ResourceLocation.fromNamespaceAndPath("mysticism", "relapse_bonus");
   private static final Set<ManasRace> VALID_RACES = Set.of(
      (ManasRace)MysticismRaces.FORGOTTEN.get(),
      (ManasRace)MysticismRaces.REMNANT.get(),
      (ManasRace)MysticismRaces.EMPTY.get(),
      (ManasRace)MysticismRaces.REVENANT.get(),
      (ManasRace)MysticismRaces.DIVINE_INFERIUS.get(),
      (ManasRace)MysticismRaces.WHOLE.get(),
      (ManasRace)MysticismRaces.ASCENDED.get(),
      (ManasRace)MysticismRaces.DIVINE_EXCELSIUS.get()
   );

   public RelapseSkill() {
      super(SkillType.INTRINSIC);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/intrinsic/relapse.png");
   }

   public boolean canIgnoreCoolDown(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return mode == 0 && entity.isShiftKeyDown();
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "relapse.aleph";
         case 1 -> "relapse.bet";
         case 2 -> "relapse.gimel";
         case 3 -> "relapse.daleth";
         case 4 -> "relapse.aleph_null";
         default -> super.getModeId(instance, mode);
      };
   }

   private int getMaxModeForRace(LivingEntity entity) {
      return RaceAPI.getRaceFrom(entity).getRace().map(instance -> {
         ManasRace race = instance.getRace();
         if (race.equals(MysticismRaces.FORGOTTEN.get())) {
            return 0;
         } else if (race.equals(MysticismRaces.REMNANT.get())) {
            return 1;
         } else if (race.equals(MysticismRaces.EMPTY.get()) || race.equals(MysticismRaces.WHOLE.get())) {
            return 2;
         } else if (race.equals(MysticismRaces.REVENANT.get()) || race.equals(MysticismRaces.ASCENDED.get())) {
            return 3;
         } else {
            return !race.equals(MysticismRaces.DIVINE_INFERIUS.get()) && !race.equals(MysticismRaces.DIVINE_EXCELSIUS.get()) ? 0 : 4;
         }
      }).orElse(0);
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      int maxMode = this.getMaxModeForRace(entity);
      if (!reverse) {
         return mode >= maxMode ? 0 : mode + 1;
      } else {
         return mode <= 0 ? maxMode : mode - 1;
      }
   }

   private boolean checkRace(LivingEntity entity) {
      return RaceAPI.getRaceFrom(entity).getRace().map(instance -> VALID_RACES.contains(instance.getRace())).orElse(false);
   }

   private boolean checkLine(LivingEntity entity) {
      return RaceAPI.getRaceFrom(entity).getRace().map(instance -> {
         ManasRace race = instance.getRace();
         return race.equals(MysticismRaces.EMPTY.get()) || race.equals(MysticismRaces.REVENANT.get()) || race.equals(MysticismRaces.DIVINE_INFERIUS.get());
      }).orElse(false);
   }

   public int getModes(ManasSkillInstance instance) {
      return 5;
   }

   public boolean canBeSlotted(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return this.checkRace(entity);
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      switch (mode) {
         case 0:
            this.forgottenMode(instance, entity);
            break;
         case 1:
            this.remnantMode(instance, entity);
            break;
         case 2:
            if (this.checkLine(entity)) {
               this.emptyMode(instance, entity);
            } else {
               this.wholeMode(instance, entity);
            }
            break;
         case 3:
            if (this.checkLine(entity)) {
               this.revenantMode(instance, entity);
            } else {
               this.ascendedMode(instance, entity);
            }
            break;
         case 4:
            if (this.checkLine(entity)) {
               this.divineInferiusMode(instance, entity);
            } else {
               this.divineExcelsiusMode(instance, entity);
            }
      }
   }

   private void forgottenMode(ManasSkillInstance instance, LivingEntity entity) {
      Level level = entity.level();
      if (entity.isShiftKeyDown()) {
         if (instance.getOrCreateTag().hasUUID("ForgottenCloneUUID")
            && level instanceof ServerLevel serverLevel
            && serverLevel.getEntity(instance.getOrCreateTag().getUUID("ForgottenCloneUUID")) instanceof CloneEntity clone
            && clone.isAlive()) {
            clone.remove();
            entity.playSound((SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), 1.0F, 1.0F);
         }
      } else {
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         if (!(instance.getOrCreateTag().hasUUID("ForgottenCloneUUID") && level instanceof ServerLevel serverLevel)
            || !(serverLevel.getEntity(instance.getOrCreateTag().getUUID("ForgottenCloneUUID")) instanceof CloneEntity clone && clone.isAlive())) {
            double MP = EnergyHelper.getMaxMagicule(entity);
            CloneEntity newClone = summonClone(instance, entity, level, MP, existence.getMagicule(), entity.position());
            CloneEntity.copyEffects(entity, newClone);
            instance.getOrCreateTag().putUUID("ForgottenCloneUUID", newClone.getUUID());
            instance.setCoolDown(this.getModeCooldown(entity, 0), 0);
         }
      }
   }

   private void remnantMode(ManasSkillInstance instance, LivingEntity entity) {
      Level level = entity.level();
      int count = this.getBetCloneCount(entity);
      RelapseCloneEntity.RelapseBehavior behavior = this.getBetCloneBehavior(entity);
      IExistence existence = TensuraStorages.getExistenceFrom(entity);

      for (int i = 0; i < count; i++) {
         Vec3 pos = this.getRandomClonePosition(entity, 5.0);
         double MP = EnergyHelper.getMaxMagicule(entity);
         RelapseCloneEntity clone = summonRelapseClone(instance, entity, level, MP, existence.getMagicule(), pos);
         clone.setRelapseBehavior(behavior);
         clone.setWandering(true);
         clone.setLife(600);
      }

      instance.setCoolDown(this.getModeCooldown(entity, 1), 1);
   }

   private int getBetCloneCount(LivingEntity entity) {
      return RaceAPI.getRaceFrom(entity)
         .getRace()
         .map(
            instance -> {
               ManasRace race = instance.getRace();
               if (race.equals(MysticismRaces.REMNANT.get())) {
                  return CONFIG.betClonesSummonedRemnant;
               } else if (race.equals(MysticismRaces.EMPTY.get()) || race.equals(MysticismRaces.WHOLE.get())) {
                  return CONFIG.betClonesSummonedEmptyWhole;
               } else if (race.equals(MysticismRaces.REVENANT.get()) || race.equals(MysticismRaces.ASCENDED.get())) {
                  return CONFIG.betClonesSummonedRevenantAscended;
               } else {
                  return !race.equals(MysticismRaces.DIVINE_INFERIUS.get()) && !race.equals(MysticismRaces.DIVINE_EXCELSIUS.get())
                     ? 0
                     : CONFIG.betClonesSummonedInferiusExcelsius;
               }
            }
         )
         .orElse(0);
   }

   private RelapseCloneEntity.RelapseBehavior getBetCloneBehavior(LivingEntity entity) {
      return RaceAPI.getRaceFrom(entity)
         .getRace()
         .map(
            instance -> {
               ManasRace race = instance.getRace();
               if (race.equals(MysticismRaces.REVENANT.get()) || race.equals(MysticismRaces.ASCENDED.get())) {
                  return RelapseCloneEntity.RelapseBehavior.STARE;
               } else {
                  return !race.equals(MysticismRaces.DIVINE_INFERIUS.get()) && !race.equals(MysticismRaces.DIVINE_EXCELSIUS.get())
                     ? RelapseCloneEntity.RelapseBehavior.STATIC
                     : RelapseCloneEntity.RelapseBehavior.CHASE_NO_DAMAGE;
               }
            }
         )
         .orElse(RelapseCloneEntity.RelapseBehavior.STATIC);
   }

   private Vec3 getRandomClonePosition(LivingEntity entity, double radius) {
      double angle = entity.getRandom().nextDouble() * Math.PI * 2.0;
      double distance = 1.5 + entity.getRandom().nextDouble() * radius;
      return entity.position().add(Math.cos(angle) * distance, 0.0, Math.sin(angle) * distance);
   }

   private void emptyMode(ManasSkillInstance instance, LivingEntity entity) {
      IExistence existence = TensuraStorages.getExistenceFrom(entity);
      double MP = EnergyHelper.getMaxMagicule(entity);
      int requiredKills = RaceAPI.getRaceFrom(entity).getRace().map(raceInstance -> {
         ManasRace race = raceInstance.getRace();
         if (race.equals(MysticismRaces.EMPTY.get())) {
            return CONFIG.gimelEmptyKills;
         } else if (race.equals(MysticismRaces.REVENANT.get())) {
            return CONFIG.gimelRevenantKills;
         } else {
            return race.equals(MysticismRaces.DIVINE_INFERIUS.get()) ? CONFIG.gimelInferiusKills : -1;
         }
      }).orElse(-1);
      if (requiredKills >= 0) {
         if (instance.getOrCreateTag().getInt("Kills") < requiredKills) {
            if (entity instanceof Player player) {
               player.playNotifySound((SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), TensuraSkill.ABILITY_SOUND, 0.5F, 0.5F);
               player.displayClientMessage(
                  Component.translatable(
                     "mysticism.skill.mode.relapse.gimel.not_enough_kills", new Object[]{requiredKills, instance.getOrCreateTag().getInt("Kills")}
                  ),
                  false
               );
            }
         } else {
            if (entity instanceof Player player) {
               player.playNotifySound((SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), TensuraSkill.ABILITY_SOUND, 0.5F, 0.5F);
            }

            instance.getOrCreateTag().putInt("Kills", 0);
            SkillAPI.getSkillsFrom(entity).getSkill((ManasSkill)IntrinsicSkills.POSSESSION.get()).ifPresent(possessionInstance -> {
               CloneEntity clone = summonClone(instance, entity, entity.level(), MP, existence.getMagicule(), entity.position());
               clone.setSkill(possessionInstance);
               clone.setNoAi(true);
               this.returnStatsFromRace(entity, clone);
               instance.setCoolDown(this.getGimelCooldown(entity), 2);
            });
         }
      }
   }

   private void returnStatsFromRace(LivingEntity user, CloneEntity clone) {
      RaceAPI.getRaceFrom(user).getRace().ifPresent(instance -> {
         ManasRace race = instance.getRace();
         if (race.equals(MysticismRaces.EMPTY.get())) {
            setCloneArmor(clone, 10.0);
         } else if (race.equals(MysticismRaces.REVENANT.get())) {
            setCloneArmor(clone, 20.0);
         } else if (race.equals(MysticismRaces.DIVINE_INFERIUS.get())) {
            setCloneArmor(clone, 30.0);
         }
      });
   }

   private static void setCloneArmor(CloneEntity clone, double armor) {
      AttributeInstance armorAttribute = clone.getAttribute(Attributes.ARMOR);
      if (armorAttribute != null) {
         armorAttribute.setBaseValue(armor);
      }
   }

   private void revenantMode(ManasSkillInstance instance, LivingEntity entity) {
      IExistence existence = TensuraStorages.getExistenceFrom(entity);
      if (entity instanceof Player player) {
         if (!player.isCreative() && !player.isSpectator()) {
            player.getAbilities().mayfly = true;
            player.getAbilities().flying = true;
            player.onUpdateAbilities();
         }

         existence.setSpiritualForm(true);
         existence.markDirty();
         player.level()
            .playSound(
               null, player.getX(), player.getY(), player.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_SPLIT.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F
            );
      }

      if (entity instanceof Player player) {
         player.playNotifySound((SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), TensuraSkill.ABILITY_SOUND, 0.5F, 0.5F);
      }

      RaceAPI.getRaceFrom(entity).getRace().ifPresent(manasRaceInstance -> {
         int duration = manasRaceInstance.getRace().equals(MysticismRaces.REVENANT.get()) ? CONFIG.dalethDurationRevenant : CONFIG.dalethDurationInferius;
         entity.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(MysticismMobEffects.REVENANT_HORROR), duration * 20, 0, false, false, false));
         instance.setCoolDown(this.getModeCooldown(entity, 3), 3);
      });
   }

   private void divineInferiusMode(ManasSkillInstance instance, LivingEntity entity) {
      LivingEntity target = (LivingEntity)ObjectSelectionHelper.getTargetingEntity(CloneEntity.class, entity, 3.0, 0.0, false, false, false);
      if (target != null
         && instance.getOrCreateTag().hasUUID("ForgottenCloneUUID")
         && target.getUUID().equals(instance.getOrCreateTag().getUUID("ForgottenCloneUUID"))) {
         target.remove(RemovalReason.DISCARDED);
         if (entity instanceof Player player) {
            player.playNotifySound((SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(), TensuraSkill.ABILITY_SOUND, 0.5F, 0.5F);
         }

         entity.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(MysticismMobEffects.COLLECTIVE), CONFIG.alephNullInferiusCollective * 20, 0, false, false, false
            )
         );
         instance.setCoolDown(this.getAlephNullCooldown(entity), 0);
         instance.setCoolDown(this.getAlephNullCooldown(entity), 4);
         if (entity instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("mysticism.skill.mode.relapse.aleph_null.cooldown_incurred", new Object[]{this.getAlephNullCooldown(entity)})
                  .withStyle(ChatFormatting.RED),
               false
            );
         }
      }
   }

   private void wholeMode(ManasSkillInstance instance, LivingEntity entity) {
      instance.getOrCreateTag().putInt("WholeHits", this.getGimelWholeHitsFromRace(entity));
      entity.addEffect(
         new MobEffectInstance(TensuraMobEffects.getReference(MysticismMobEffects.SANCTIFYING_LIGHT), CONFIG.gimelDurationHoly * 20, 0, false, false, false)
      );
      instance.setCoolDown(this.getModeCooldown(entity, 2), 2);
   }

   private int getGimelWholeHitsFromRace(LivingEntity entity) {
      return RaceAPI.getRaceFrom(entity).getRace().map(manasRaceInstance -> {
         ManasRace race = manasRaceInstance.getRace();
         if (race.equals(MysticismRaces.WHOLE.get())) {
            return CONFIG.gimelBeamsWhole;
         } else if (race.equals(MysticismRaces.ASCENDED.get())) {
            return CONFIG.gimelBeamsAscended;
         } else {
            return race.equals(MysticismRaces.DIVINE_EXCELSIUS.get()) ? CONFIG.gimelBeamsExcelsius : 0;
         }
      }).orElse(0);
   }

   private void ascendedMode(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof Player player) {
         player.playNotifySound((SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), TensuraSkill.ABILITY_SOUND, 0.5F, 0.5F);
      }

      RaceAPI.getRaceFrom(entity).getRace().ifPresent(manasRaceInstance -> {
         int duration = manasRaceInstance.getRace().equals(MysticismRaces.ASCENDED) ? CONFIG.dalethDurationAscended : CONFIG.dalethDurationExcelsius;
         entity.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(MysticismMobEffects.AWAKENED_FORESIGHT), duration * 20, 0, false, false, false));
         instance.setCoolDown(this.getModeCooldown(entity, 3), 3);
      });
      if (entity instanceof Player player && !player.isCreative() && !player.isSpectator()) {
         player.getAbilities().mayfly = true;
         player.getAbilities().flying = true;
         player.onUpdateAbilities();
      }
   }

   private void divineExcelsiusMode(ManasSkillInstance instance, LivingEntity entity) {
      Level level = entity.level();
      RelapseCloneEntity nearbyClone = null;
      if (instance.getOrCreateTag().hasUUID("ForgottenCloneUUID")
         && level instanceof ServerLevel serverLevel
         && serverLevel.getEntity(instance.getOrCreateTag().getUUID("ForgottenCloneUUID")) instanceof RelapseCloneEntity clone
         && clone.isAlive()
         && clone.distanceToSqr(entity) <= CONFIG.alephNullExcelsiusRange * CONFIG.alephNullExcelsiusRange) {
         nearbyClone = clone;
         if (entity instanceof Player player) {
            player.playNotifySound((SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), TensuraSkill.ABILITY_SOUND, 0.5F, 0.5F);
         }

         nearbyClone.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(MysticismMobEffects.UNSTABLE_REQUIEM), CONFIG.alephNullExcelsiusInvincibility * 20, 0, false, false, false
            )
         );
         instance.setCoolDown(this.getModeCooldown(entity, 4), 0);
         instance.setCoolDown(this.getModeCooldown(entity, 4), 4);
         if (entity instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("mysticism.skill.mode.relapse.aleph_null.cooldown_incurred", new Object[]{this.getAlephNullCooldown(entity)})
                  .withStyle(ChatFormatting.RED),
               false
            );
         }
      }
   }

   private int getAlephCloneFragility(LivingEntity entity) {
      return RaceAPI.getRaceFrom(entity)
         .getRace()
         .map(
            instance -> {
               ManasRace race = instance.getRace();
               if (race.equals(MysticismRaces.FORGOTTEN.get())) {
                  return CONFIG.alephCloneFragilityForgotten;
               } else if (race.equals(MysticismRaces.REMNANT.get())) {
                  return CONFIG.alephCloneFragilityRemnant;
               } else if (race.equals(MysticismRaces.EMPTY.get()) || race.equals(MysticismRaces.WHOLE.get())) {
                  return CONFIG.alephCloneFragilityEmptyWhole;
               } else if (race.equals(MysticismRaces.REVENANT.get()) || race.equals(MysticismRaces.ASCENDED.get())) {
                  return CONFIG.alephCloneFragilityRevenantAscended;
               } else {
                  return !race.equals(MysticismRaces.DIVINE_INFERIUS.get()) && !race.equals(MysticismRaces.DIVINE_EXCELSIUS.get())
                     ? 0
                     : CONFIG.alephCloneFragilityInferiusExcelsius;
               }
            }
         )
         .orElse(0);
   }

   private int getAlephCloneSlowness(LivingEntity entity) {
      return RaceAPI.getRaceFrom(entity)
         .getRace()
         .map(
            instance -> {
               ManasRace race = instance.getRace();
               if (race.equals(MysticismRaces.FORGOTTEN.get())) {
                  return CONFIG.alephCloneSlownessForgotten;
               } else if (race.equals(MysticismRaces.REMNANT.get())) {
                  return CONFIG.alephCloneSlownessRemnant;
               } else if (race.equals(MysticismRaces.EMPTY.get()) || race.equals(MysticismRaces.WHOLE.get())) {
                  return CONFIG.alephCloneSlownessEmptyWhole;
               } else if (race.equals(MysticismRaces.REVENANT.get()) || race.equals(MysticismRaces.ASCENDED.get())) {
                  return CONFIG.alephCloneSlownessRevenantAscended;
               } else {
                  return !race.equals(MysticismRaces.DIVINE_INFERIUS.get()) && !race.equals(MysticismRaces.DIVINE_EXCELSIUS.get())
                     ? 0
                     : CONFIG.alephCloneSlownessInferiusExcelsius;
               }
            }
         )
         .orElse(0);
   }

   public void onCloneTick(CloneEntity clone, LivingEntity livingEntity) {
      if (TensuraStorages.getExistenceFrom(clone).getSleepModeTime() > 0) {
         clone.remove();
      } else {
         int fragility = this.getAlephCloneFragility(livingEntity);
         int slowness = this.getAlephCloneSlowness(livingEntity);
         if (fragility > 0) {
            clone.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.FRAGILITY), 200, fragility - 1, false, false, false));
         }

         if (slowness > 0) {
            clone.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, slowness - 1, false, false, false));
         }
      }
   }

   private int getModeCooldown(LivingEntity entity, int mode) {
      return switch (mode) {
         case 0 -> this.getAlephCooldown(entity);
         case 1 -> this.getBetCooldown(entity);
         case 2 -> this.getGimelCooldown(entity);
         case 3 -> this.getDalethCooldown(entity);
         case 4 -> this.getAlephNullCooldown(entity);
         default -> 60;
      };
   }

   private int getAlephCooldown(LivingEntity entity) {
      return RaceAPI.getRaceFrom(entity)
         .getRace()
         .map(
            instance -> {
               ManasRace race = instance.getRace();
               if (race.equals(MysticismRaces.FORGOTTEN.get())) {
                  return CONFIG.alephCooldownForgotten;
               } else if (race.equals(MysticismRaces.REMNANT.get())) {
                  return CONFIG.alephCooldownRemnant;
               } else if (race.equals(MysticismRaces.EMPTY.get()) || race.equals(MysticismRaces.WHOLE.get())) {
                  return CONFIG.alephCooldownEmptyWhole;
               } else if (race.equals(MysticismRaces.REVENANT.get()) || race.equals(MysticismRaces.ASCENDED.get())) {
                  return CONFIG.alephCooldownRevenantAscended;
               } else {
                  return !race.equals(MysticismRaces.DIVINE_INFERIUS.get()) && !race.equals(MysticismRaces.DIVINE_EXCELSIUS.get())
                     ? 300
                     : CONFIG.alephCooldownInferiusExcelsius;
               }
            }
         )
         .orElse(300);
   }

   private int getBetCooldown(LivingEntity entity) {
      return RaceAPI.getRaceFrom(entity)
         .getRace()
         .map(
            instance -> {
               ManasRace race = instance.getRace();
               if (race.equals(MysticismRaces.REMNANT.get())) {
                  return CONFIG.betCooldownRemnant;
               } else if (race.equals(MysticismRaces.EMPTY.get()) || race.equals(MysticismRaces.WHOLE.get())) {
                  return CONFIG.betCooldownEmptyWhole;
               } else if (race.equals(MysticismRaces.REVENANT.get()) || race.equals(MysticismRaces.ASCENDED.get())) {
                  return CONFIG.betCooldownRevenantAscended;
               } else {
                  return !race.equals(MysticismRaces.DIVINE_INFERIUS.get()) && !race.equals(MysticismRaces.DIVINE_EXCELSIUS.get())
                     ? 300
                     : CONFIG.betCooldownInferiusExcelsius;
               }
            }
         )
         .orElse(300);
   }

   private int getGimelCooldown(LivingEntity entity) {
      return RaceAPI.getRaceFrom(entity).getRace().map(instance -> {
         ManasRace race = instance.getRace();
         if (race.equals(MysticismRaces.EMPTY.get())) {
            return CONFIG.gimelCooldownEmpty;
         } else if (race.equals(MysticismRaces.WHOLE.get())) {
            return CONFIG.gimelCooldownWhole;
         } else if (race.equals(MysticismRaces.REVENANT.get())) {
            return CONFIG.gimelCooldownRevenant;
         } else if (race.equals(MysticismRaces.ASCENDED.get())) {
            return CONFIG.gimelCooldownAscended;
         } else if (race.equals(MysticismRaces.DIVINE_INFERIUS.get())) {
            return CONFIG.gimelCooldownInferius;
         } else {
            return race.equals(MysticismRaces.DIVINE_EXCELSIUS.get()) ? CONFIG.gimelCooldownExcelsius : 300;
         }
      }).orElse(300);
   }

   private int getDalethCooldown(LivingEntity entity) {
      return RaceAPI.getRaceFrom(entity).getRace().map(instance -> {
         ManasRace race = instance.getRace();
         if (race.equals(MysticismRaces.REVENANT.get())) {
            return CONFIG.dalethCooldownRevenant;
         } else if (race.equals(MysticismRaces.ASCENDED.get())) {
            return CONFIG.dalethCooldownAscended;
         } else if (race.equals(MysticismRaces.DIVINE_INFERIUS.get())) {
            return CONFIG.dalethCooldownInferius;
         } else {
            return race.equals(MysticismRaces.DIVINE_EXCELSIUS.get()) ? CONFIG.dalethCooldownExcelsius : 600;
         }
      }).orElse(300);
   }

   private int getAlephNullCooldown(LivingEntity entity) {
      return RaceAPI.getRaceFrom(entity).getRace().map(instance -> {
         ManasRace race = instance.getRace();
         if (race.equals(MysticismRaces.DIVINE_INFERIUS.get())) {
            return CONFIG.alephNullCooldownInferius;
         } else {
            return race.equals(MysticismRaces.DIVINE_EXCELSIUS.get()) ? CONFIG.alephNullCooldownExcelsius : 300;
         }
      }).orElse(300);
   }

   private static CloneEntity summonClone(ManasSkillInstance instance, LivingEntity entity, Level level, double EP, double currentEP, Vec3 position) {
      EntityType<CloneEntity> type = (EntityType<CloneEntity>)HumanEntityTypes.CLONE.get();
      CloneEntity clone = new CloneEntity(type, level);
      if (entity instanceof Player player) {
         clone.tame(player);
      }

      clone.setSkill(instance);
      clone.copyStatsAndSkills(entity, CopySkill.ALL, true);
      multiplyCloneDamage(clone, 1.5);
      clone.setHealth(clone.getMaxHealth());
      EnergyHelper.setMaxMagicule(clone, EP);
      EnergyHelper.setMaxAura(clone, 50.0);
      IExistence existence = TensuraStorages.getExistenceFrom(clone);
      existence.setMagicule(Math.min(currentEP, EP));
      existence.markDirty();
      clone.setPos(position);
      CloneEntity.copyRotation(entity, clone);
      level.addFreshEntity(clone);
      level.playSound(
         null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_SPLIT.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F
      );
      double size = clone.getAttributeValue(Attributes.SCALE) * 4.0;
      TensuraParticleHelper.addServerAuraParticles(clone, TensuraParticleUtils.getBlackAura(0.5F, (float)size, -0.3F), 10, 0.01);
      return clone;
   }

   private static RelapseCloneEntity summonRelapseClone(
      ManasSkillInstance instance, LivingEntity entity, Level level, double EP, double currentEP, Vec3 position
   ) {
      EntityType<RelapseCloneEntity> type = (EntityType<RelapseCloneEntity>)MysticismSkillEntityTypes.RELAPSE_CLONE.get();
      RelapseCloneEntity clone = new RelapseCloneEntity(type, level);
      if (entity instanceof Player player) {
         clone.tame(player);
      }

      clone.setSkill(instance);
      clone.copyStatsAndSkills(entity, CopySkill.ALL, true);
      clone.setHealth(1.0F);
      EnergyHelper.setMaxMagicule(clone, EP);
      EnergyHelper.setMaxAura(clone, 50.0);
      IExistence existence = TensuraStorages.getExistenceFrom(clone);
      existence.setMagicule(Math.min(currentEP, EP));
      existence.markDirty();
      clone.setPos(position);
      clone.copyEquipments(entity);
      CloneEntity.copyRotation(entity, clone);
      level.addFreshEntity(clone);
      level.playSound(
         null, entity.getX(), entity.getY(), entity.getZ(), (SoundEvent)TensuraSoundEvents.GENERIC_SPLIT.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F
      );
      double size = clone.getAttributeValue(Attributes.SCALE) * 4.0;
      TensuraParticleHelper.addServerAuraParticles(clone, TensuraParticleUtils.getBlackAura(0.5F, (float)size, -0.3F), 10, 0.01);
      return clone;
   }

   private static void multiplyCloneDamage(CloneEntity clone, double multiplier) {
      AttributeInstance attackDamage = clone.getAttribute(Attributes.ATTACK_DAMAGE);
      if (attackDamage != null) {
         attackDamage.addOrReplacePermanentModifier(new AttributeModifier(CLONE_RELAPSE, multiplier - 1.0, Operation.ADD_MULTIPLIED_TOTAL));
      }
   }
}
