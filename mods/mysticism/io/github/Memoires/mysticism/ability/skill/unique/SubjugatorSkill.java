package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.bridge.SubordinateAPI;
import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.Memoires.mysticism.util.subjugator.SubjugatorDialogue;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.ability.magic.aspectual.AspectualMagic;
import io.github.manasmods.tensura.ability.magic.aspectual.AspectualMagic.AspectualType;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.common.ThoughtCommunicationSkill;
import io.github.manasmods.tensura.ability.skill.intrinsic.CharmSkill;
import io.github.manasmods.tensura.config.ability.skill.CommonSkillConfig;
import io.github.manasmods.tensura.config.ability.skill.CommonSkillConfig.ThoughtCommunication;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.entity.template.subclass.ISubordinate;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.effect.IEffect;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import io.github.manasmods.tensura.util.EnergyHelper.GainType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class SubjugatorSkill extends Skill {
   public static final UniqueMysticismSkillConfig.Subjugator CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Subjugator;
   public static final ThoughtCommunication THOUGHT_CONFIG = ((CommonSkillConfig)ConfigRegistry.getConfig(CommonSkillConfig.class)).ThoughtCommunication;
   private static final SubjugatorDialogue.ManasSkillLike SK = new SubjugatorDialogue.ManasSkillLike() {
      @Override
      public CompoundTag getOrCreateTag(Object instance) {
         return ((ManasSkillInstance)instance).getOrCreateTag();
      }

      @Override
      public void markDirty(Object instance) {
         ((ManasSkillInstance)instance).markDirty();
      }
   };

   public SubjugatorSkill() {
      super(SkillType.UNIQUE);
      SubjugatorDialogue.bootstrap();
   }

   public int getModes(ManasSkillInstance instance) {
      return 6;
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/subjugator.png");
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      int maxMode = instance.getMastery() >= instance.getMaxMastery() / 2.0 && checkAwakened(instance) ? 5 : 4;
      if (reverse) {
         return mode == 0 ? maxMode : mode - 1;
      } else {
         return mode == maxMode ? 0 : mode + 1;
      }
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return this.isInSlot(entity);
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (!entity.level().isClientSide) {
         String MAP_KEY = "LoyaltyMap";
         String TOTAL_KEY = "TotalLoyalty";
         String BONUS_KEY = "UnbreakableDefenseBonus";
         int gain = instance.isMastered(entity) ? CONFIG.loyaltyPointGainMastery : CONFIG.loyaltyPointGain;
         double radius = instance.isMastered(entity) ? CONFIG.loyaltyRangeMastery : CONFIG.loyaltyRange;
         CompoundTag tag = instance.getOrCreateTag();
         CompoundTag map = tag.getCompound("LoyaltyMap");
         List<LivingEntity> nearby = entity.level()
            .getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(radius), other -> SubordinateHelper.isSubordinate(entity, other));
         int total = 0;
         if (!nearby.isEmpty()) {
            for (LivingEntity sub : nearby) {
               String key = sub.getStringUUID();
               int curr = map.getInt(key);
               map.putInt(key, curr + gain);
               total += map.getInt(key);
               int level = instance.isMastered(entity) ? CONFIG.loyaltyResistanceMastered : CONFIG.loyaltyResistance;
               TensuraMobEffect.addEffect(sub, new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 100, level - 1, false, false, false), entity, this);
            }
         }

         if (total < 0) {
            total = 0;
         }

         if (total > CONFIG.maximumTotalLoyaltyPoints) {
            total = CONFIG.maximumTotalLoyaltyPoints;
         }

         int steps = total / CONFIG.unbreakableDefenseStep;
         float bonus = (float)Math.min(steps * CONFIG.unbreakableDefenseGain, 1.0);
         tag.put("LoyaltyMap", map);
         tag.putInt("TotalLoyalty", total);
         tag.putFloat("UnbreakableDefenseBonus", bonus);
         instance.markDirty();
         SubjugatorDialogue.tickRandom(SK, instance, entity, checkAwakened(instance));
      }
   }

   public void onSubordinateDeath(ManasSkillInstance instance, LivingEntity owner, LivingEntity subordinate, DamageSource source) {
      String MAP_KEY = "LoyaltyMap";
      String TOTAL_KEY = "TotalLoyalty";
      String BONUS_KEY = "UnbreakableDefenseBonus";
      CompoundTag tag = instance.getOrCreateTag();
      CompoundTag map = tag.getCompound("LoyaltyMap");
      String key = subordinate.getStringUUID();
      if (map.contains(key)) {
         map.remove(key);
         int total = 0;

         for (String k : map.getAllKeys()) {
            total += map.getInt(k);
         }

         if (total < 0) {
            total = 0;
         }

         if (total > CONFIG.maximumTotalLoyaltyPoints) {
            total = CONFIG.maximumTotalLoyaltyPoints;
         }

         int steps = total / CONFIG.unbreakableDefenseStep;
         float bonus = (float)Math.min(steps * CONFIG.unbreakableDefenseGain, 1.0);
         if (map.isEmpty()) {
            tag.remove("LoyaltyMap");
         } else {
            tag.put("LoyaltyMap", map);
         }

         tag.putInt("TotalLoyalty", total);
         tag.putFloat("UnbreakableDefenseBonus", bonus);
         instance.markDirty();
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "subjugator.takeover";
         case 1 -> "subjugator.communication_movement";
         case 2 -> "subjugator.communication_targeting";
         case 3 -> "subjugator.unbreakable_defense";
         case 4 -> "subjugator.return";
         case 5 -> "subjugator.the_voice";
         default -> super.getModeId(instance, mode);
      };
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (mode != 0) {
         if (mode == 1) {
            if (entity instanceof Player player) {
               if (!player.isSecondaryUseActive()) {
                  List<Mob> list = player.level()
                     .getEntitiesOfClass(
                        Mob.class, player.getBoundingBox().inflate(CONFIG.allyRadius), living -> SubordinateHelper.isSubordinate(player, living)
                     );
                  if (list.isEmpty()) {
                     player.displayClientMessage(
                        Component.translatable("tensura.telepathy.subordinate_all.not_found").setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA)), true
                     );
                     return;
                  }

                  CompoundTag tag = instance.getOrCreateTag();
                  int command = tag.getInt("command");
                  command = command == 4 ? 1 : command + 1;
                  tag.putInt("command", command);

                  for (Mob mob : list) {
                     TensuraMobEffect.addEffect(
                        mob,
                        new MobEffectInstance(
                           TensuraMobEffects.getReference(TensuraMobEffects.INSPIRATION),
                           (instance.isMastered(entity) ? CONFIG.inspirationDurationMastery : CONFIG.inspirationDuration) * 20,
                           (instance.isMastered(entity) ? CONFIG.inspirationLevelMastery : CONFIG.inspirationLevel) - 1,
                           false,
                           false,
                           false
                        ),
                        entity,
                        this,
                        mode
                     );

                     MutableComponent message = switch (command) {
                        case 2 -> {
                           SubordinateHelper.setFollow(mob);
                           yield Component.translatable("tensura.telepathy.subordinate_all.follow");
                        }
                        case 3 -> {
                           SubordinateHelper.setWander(mob);
                           yield Component.translatable("tensura.telepathy.subordinate_all.wander");
                        }
                        case 4 -> {
                           SubordinateHelper.setFollow(mob);
                           IEffect effect = TensuraStorages.getEffectFrom(mob);
                           effect.setMeatShield(true);
                           effect.markDirty();
                           yield Component.translatable("tensura.telepathy.subordinate_all.meat_shield");
                        }
                        default -> {
                           SubordinateHelper.setStay(mob);
                           IEffect effect = TensuraStorages.getEffectFrom(mob);
                           effect.setMeatShield(false);
                           effect.markDirty();
                           yield Component.translatable("tensura.telepathy.subordinate_all.stay");
                        }
                     };
                     player.swing(InteractionHand.MAIN_HAND, true);
                     player.displayClientMessage(message.setStyle(Style.EMPTY.withColor(ChatFormatting.AQUA)), true);
                     player.level()
                        .playSound(
                           null,
                           player.getX(),
                           player.getY(),
                           player.getZ(),
                           (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(),
                           TensuraSkill.ABILITY_SOUND,
                           1.0F,
                           1.0F
                        );
                  }
               } else {
                  ThoughtCommunicationSkill.movementTelepathy(instance, entity);
                  LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, THOUGHT_CONFIG.telepathyRadius, false, false);
                  if (target != null) {
                     TensuraMobEffect.addEffect(
                        target,
                        new MobEffectInstance(
                           TensuraMobEffects.getReference(TensuraMobEffects.INSPIRATION),
                           (instance.isMastered(entity) ? CONFIG.inspirationDurationMastery : CONFIG.inspirationDuration) * 20,
                           (instance.isMastered(entity) ? CONFIG.inspirationLevelMastery : CONFIG.inspirationLevel) - 1,
                           false,
                           false,
                           false
                        ),
                        entity,
                        this,
                        mode
                     );
                  }
               }
            }
         } else if (mode == 2) {
            ThoughtCommunicationSkill.targetingBehaviour(instance, entity);
         } else if (mode == 3) {
            boolean checkToggle = instance.getOrCreateTag().getBoolean("AbsoluteDefense");
            if (checkToggle) {
               entity.level()
                  .playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.DEFENCE_DEACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
            } else {
               entity.level()
                  .playSound(null, entity.getX(), entity.getY(), entity.getZ(), TensuraSoundEvents.DEFENCE_ACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);

               for (ManasSkillInstance skillInstance : SkillAPI.getSkillsFrom(entity).getLearnedSkills()) {
                  if (!skillInstance.getSkill().equals(UniqueMysticismSkills.SUBJUGATOR.get())
                     && skillInstance.canBeToggled(entity)
                     && skillInstance.canInteractSkill(entity)
                     && skillInstance.isToggled()) {
                     skillInstance.setToggled(false);
                     instance.onToggleOff(entity);
                  }
               }
            }

            instance.getOrCreateTag().putBoolean("AbsoluteDefense", !instance.getOrCreateTag().getBoolean("AbsoluteDefense"));
         } else if (mode == 4) {
            if (!(entity instanceof Player p)) {
               return;
            }

            LivingEntity target = (LivingEntity)ObjectSelectionHelper.getTargetingEntity(LivingEntity.class, entity, 5.0, 0.0, false, true);
            if (target == null) {
               p.displayClientMessage(Component.translatable("mysticism.skill.mode.subjugator.return.no_target").withStyle(ChatFormatting.RED), false);
            } else if (!SubordinateHelper.isSubordinate(entity, target)) {
               p.displayClientMessage(
                  Component.translatable("mysticism.skill.mode.subjugator.return.not_subordinate", new Object[]{target.getDisplayName()})
                     .withStyle(ChatFormatting.RED),
                  false
               );
            } else {
               CompoundTag tag = instance.getOrCreateTag();
               CompoundTag map = tag.getCompound("LoyaltyMap");
               int points = map.getInt(target.getStringUUID());
               p.displayClientMessage(
                  Component.translatable("mysticism.skill.mode.subjugator.return.points", new Object[]{target.getDisplayName(), points})
                     .withStyle(ChatFormatting.AQUA),
                  true
               );
            }
         } else if (mode == 5 && entity instanceof Player p) {
            if (checkAwakened(instance)) {
               SubjugatorDialogue.tryStartRandom(SK, instance, p, checkAwakened(instance));
            } else {
               p.playNotifySound((SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
            }
         }
      } else {
         Level level = entity.level();
         CompoundTag tag = instance.getOrCreateTag();
         int takeoverDistance = instance.isMastered(entity) ? CONFIG.takeoverDistanceMastery : CONFIG.takeoverDistance;
         LivingEntity target = (LivingEntity)ObjectSelectionHelper.getTargetingEntity(LivingEntity.class, entity, takeoverDistance, 0.5, true, true);
         if (target != null && (!tag.contains("target") || Objects.equals(tag.getUUID("target"), target.getUUID()))) {
            if (!CharmSkill.canMindControl(target, level)) {
               entity.sendSystemMessage(Component.translatable("tensura.ability.activation_failed").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
               level.playSound(
                  null,
                  entity.getX(),
                  entity.getY(),
                  entity.getZ(),
                  (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(),
                  TensuraSkill.ABILITY_SOUND,
                  0.5F,
                  1.0F
               );
               return;
            }

            IExistence existence = TensuraStorages.getExistenceFrom(target);
            double maxEPCap = CONFIG.baseMaxEPForMindControl;

            for (ManasSkillInstance skillInstance : List.copyOf(SkillAPI.getSkillsFrom(entity).getLearnedSkills())) {
               if (skillInstance.getSkill() instanceof AspectualMagic aspectualMagic && aspectualMagic.getAspectualType() == AspectualType.MENTAL) {
                  maxEPCap += 100000.0;
               }
            }

            if (existence.getEP() > maxEPCap) {
               entity.sendSystemMessage(Component.translatable("tensura.targeting.ep_not_meet").setStyle(Style.EMPTY.withColor(ChatFormatting.RED)));
               return;
            }

            instance.addMasteryPoint(entity);
            double multiplier = instance.isMastered(entity) ? CONFIG.takeoverEPMultiplierRequirementMastered : CONFIG.takeoverEPMultiplierRequirement;
            AttributeInstance mpReduction = entity.getAttribute(TensuraAttributes.MAX_MAGICULE);
            IExistence ownerExistence = TensuraStorages.getExistenceFrom(entity);
            AttributeInstance targetMP = target.getAttribute(TensuraAttributes.MAX_MAGICULE);
            AttributeInstance targetAP = target.getAttribute(TensuraAttributes.MAX_AURA);
            if (targetAP != null & targetMP != null) {
               double targetEP = targetMP.getValue() + targetAP.getValue();
               if (mpReduction == null || !(ownerExistence.getMagicule() > targetEP * multiplier)) {
                  if (entity instanceof Player player) {
                     player.displayClientMessage(
                        Component.translatable("mysticism.skill.mode.subjugator.takeover.fail.mp_too_low", new Object[]{targetEP * multiplier})
                           .withStyle(ChatFormatting.RED),
                        false
                     );
                     player.playNotifySound((SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(), TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
                  }

                  return;
               }

               EnergyHelper.gainMagicule(entity, -(existence.getEP() * multiplier), GainType.NORMAL);
               if (!Objects.equals(existence.getPermanentOwner(), entity.getUUID())) {
                  existence.setTemporaryOwner(entity.getUUID());
                  if (target instanceof Mob mob) {
                     SubordinateHelper.removeTarget(mob);
                  }

                  if (target instanceof ISubordinate subordinate && entity instanceof Player player) {
                     SubordinateAPI.tame(subordinate, player);
                  }

                  entity.swing(InteractionHand.MAIN_HAND, true);
                  TensuraParticleHelper.addServerParticlesAroundSelf(target, ParticleTypes.HEART);
                  level.playSound(
                     null,
                     entity.getX(),
                     entity.getY(),
                     entity.getZ(),
                     (SoundEvent)TensuraSoundEvents.DEBUFF_ACTIVATE.get(),
                     TensuraSkill.ABILITY_SOUND,
                     0.5F,
                     1.0F
                  );
                  instance.addMasteryPoint(entity);
                  int cooldown = instance.isMastered(entity) ? CONFIG.takeoverCooldownMastered : CONFIG.takeoverCooldown;
                  instance.setCoolDown(cooldown, 0);
                  level.playSound(
                     null,
                     entity.getX(),
                     entity.getY(),
                     entity.getZ(),
                     (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(),
                     TensuraSkill.ABILITY_SOUND,
                     1.0F,
                     1.0F
                  );
               }
            }
         }
      }
   }

   public boolean onTakenDamage(ManasSkillInstance instance, LivingEntity owner, DamageSource source, Changeable<Float> amount) {
      if (!instance.isToggled()) {
         return true;
      } else if (source.getEntity() instanceof ServerPlayer attacker && attacker.gameMode.isCreative()) {
         return true;
      } else {
         CompoundTag tag = instance.getOrCreateTag();
         int loyaltyPoints = tag.getInt("TotalLoyalty");
         float damageReduction = tag.contains("UnbreakableDefenseBonus") ? tag.getFloat("UnbreakableDefenseBonus") : 0.0F;
         if (damageReduction < 0.0F) {
            damageReduction = (float)Math.min((double)loyaltyPoints / CONFIG.unbreakableDefenseStep * CONFIG.unbreakableDefenseGain, 1.0);
         }

         if (damageReduction > 0.5F) {
            damageReduction = 0.5F;
         }

         Float dmg = (Float)amount.get();
         if (dmg != null && dmg > 0.0F) {
            amount.set(dmg * (1.0F - damageReduction));
         }

         return true;
      }
   }

   public void onScroll(ManasSkillInstance instance, LivingEntity entity, double delta, int mode) {
      SubjugatorDialogue.onScroll(SK, instance, entity, delta, mode);
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      SubjugatorDialogue.onRelease(SK, instance, entity, mode);
   }

   public static boolean checkAwakened(ManasSkillInstance instance) {
      return instance.getOrCreateTag().getBoolean("Awakened");
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (checkAwakened(instance) && target.isDeadOrDying()) {
         boolean named = instance.getOrCreateTag().getBoolean("Named");
         if (owner instanceof Player player) {
            player.displayClientMessage(getRandomKillMessage(instance, owner, named), false);
         }
      }

      return true;
   }

   public boolean onDeath(ManasSkillInstance instance, LivingEntity owner, DamageSource source) {
      if (checkAwakened(instance)) {
         boolean named = instance.getOrCreateTag().getBoolean("Named");
         if (owner instanceof Player player) {
            player.displayClientMessage(getRandomDeathMessage(instance, owner, named), false);
         }
      }

      return true;
   }

   private static Component getRandomDeathMessage(ManasSkillInstance instance, LivingEntity owner, boolean named) {
      CompoundTag tag = instance.getOrCreateTag();
      String name = named ? tag.getString("ManasName") : "???";
      if (name.isEmpty()) {
         name = "???";
      }

      List<Component> pool = new ArrayList<>();
      double mastery = instance.getMastery();
      if (mastery >= 500.0) {
         pool.add(Component.translatable("mysticism.skill.subjugator.death_1", new Object[]{name}).withStyle(ChatFormatting.GRAY));
      }

      if (mastery >= 625.0) {
         pool.add(Component.translatable("mysticism.skill.subjugator.death_2", new Object[]{name}).withStyle(ChatFormatting.GRAY));
      }

      if (mastery >= 750.0) {
         pool.add(Component.translatable("mysticism.skill.subjugator.death_3", new Object[]{name}).withStyle(ChatFormatting.GRAY));
      }

      if (mastery >= 875.0) {
         pool.add(Component.translatable("mysticism.skill.subjugator.death_4", new Object[]{name}).withStyle(ChatFormatting.GRAY));
      }

      if (instance.isMastered(owner)) {
         pool.add(Component.translatable("mysticism.skill.subjugator.death_5", new Object[]{name}).withStyle(ChatFormatting.GRAY));
      }

      if (pool.isEmpty()) {
         return Component.empty();
      }

      int idx = owner.getRandom().nextInt(pool.size());
      return pool.get(idx);
   }

   private static Component getRandomKillMessage(ManasSkillInstance instance, LivingEntity owner, boolean named) {
      CompoundTag tag = instance.getOrCreateTag();
      String name = named ? tag.getString("ManasName") : "???";
      if (name.isEmpty()) {
         name = "???";
      }

      List<Component> pool = new ArrayList<>();
      double mastery = instance.getMastery();
      if (mastery >= 500.0) {
         pool.add(Component.translatable("mysticism.skill.subjugator.kill_1", new Object[]{name}).withStyle(ChatFormatting.GRAY));
      }

      if (mastery >= 625.0) {
         pool.add(Component.translatable("mysticism.skill.subjugator.kill_2", new Object[]{name}).withStyle(ChatFormatting.GRAY));
      }

      if (mastery >= 750.0) {
         pool.add(Component.translatable("mysticism.skill.subjugator.kill_3", new Object[]{name}).withStyle(ChatFormatting.GRAY));
      }

      if (mastery >= 875.0) {
         pool.add(Component.translatable("mysticism.skill.subjugator.kill_4", new Object[]{name}).withStyle(ChatFormatting.GRAY));
      }

      if (instance.isMastered(owner)) {
         pool.add(Component.translatable("mysticism.skill.subjugator.kill_5", new Object[]{name}).withStyle(ChatFormatting.GRAY));
      }

      if (pool.isEmpty()) {
         return Component.empty();
      }

      int idx = owner.getRandom().nextInt(pool.size());
      return pool.get(idx);
   }
}
