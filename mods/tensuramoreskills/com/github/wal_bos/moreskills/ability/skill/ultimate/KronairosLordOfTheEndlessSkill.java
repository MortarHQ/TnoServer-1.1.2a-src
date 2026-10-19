package com.github.wal_bos.moreskills.ability.skill.ultimate;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.util.SkillObtainmentHelper;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class KronairosLordOfTheEndlessSkill extends Skill {
   public static final int MODE_TAKERS_WILL = 0;
   public static final int MODE_POWER_HUNGER = 1;
   public static final int MODE_ROOM = 2;
   public static final int MODE_ISOLATION = 3;
   public static final int MODE_DIVINE_LIFESTEAL = 4;
   private static final String TAG_ROOT = "tensuramoreskills_kronairos";
   private static final String TAG_LAST_EP = "LastEP";
   private static final String TAG_TW_UUID = "TakersWillTarget";
   private static final String TAG_TW_END = "TakersWillEnd";
   private static final String TAG_TW_NEXT = "TakersWillNext";
   private static final String TAG_ISO_UUID = "IsolationTarget";
   private static final String TAG_ISO_SECONDS = "IsolationSeconds";
   private static final int CD_TAKERS_WILL = 200;
   private static final int CD_POWER_HUNGER = 100;
   private static final int CD_ROOM = 100;
   private static final int CD_ISOLATION = 200;
   private static final int CD_DIVINE_LIFESTEAL = 50;
   private static final int TOGGLE_AURA_RADIUS = 3;
   private static final int TAKERS_WILL_DURATION = 1200;
   private static final int TAKERS_WILL_TICK = 200;
   private static final int ROOM_DURATION = 600;
   private static final int ISOLATION_HOLD_SECONDS = 10;
   private static final int ISOLATION_LOCK_TICKS = 100;

   public KronairosLordOfTheEndlessSkill() {
      super(SkillType.ULTIMATE);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/ultimate/kronairos_lord_of_the_endless.png");
   }

   public double getDefaultAcquiringMagiculeCost() {
      return (Double)MoreSkillsConfigs.GRAND.kronairosLordOfTheEndless.acquirementMagiculeCost.get();
   }

   public int getAcquirementMastery(LivingEntity entity) {
      return (Integer)MoreSkillsConfigs.GRAND.kronairosLordOfTheEndless.acquirementMastery.get();
   }

   public float getLearningCostMultiplier(int mode) {
      return ((Double)MoreSkillsConfigs.GRAND.kronairosLordOfTheEndless.learningCostMultiplier.get()).floatValue();
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return SkillObtainmentHelper.canAcquire(
         entity,
         (Double)MoreSkillsConfigs.GRAND.kronairosLordOfTheEndless.acquirementMagiculeCost.get(),
         (String)MoreSkillsConfigs.GRAND.kronairosLordOfTheEndless.requiredSkillId.get(),
         (Boolean)MoreSkillsConfigs.GRAND.kronairosLordOfTheEndless.requiredSkillMustBeMastered.get()
      );
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> 25000.0;
         case 1 -> 35000.0;
         case 2 -> 45000.0;
         case 3 -> 15000.0;
         case 4 -> 60000.0;
         default -> 0.0;
      };
   }

   public int getModes(ManasSkillInstance instance) {
      return 5;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      if (reverse) {
         return mode <= 0 ? this.getModes(instance) - 1 : mode - 1;
      } else {
         return mode >= this.getModes(instance) - 1 ? 0 : mode + 1;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "kronairos_lord_of_the_endless.mode.1";
         case 1 -> "kronairos_lord_of_the_endless.mode.2";
         case 2 -> "kronairos_lord_of_the_endless.mode.3";
         case 3 -> "kronairos_lord_of_the_endless.mode.4";
         case 4 -> "kronairos_lord_of_the_endless.mode.5";
         default -> super.getModeId(instance, mode);
      };
   }

   public boolean canBeToggled(@NotNull ManasSkillInstance instance, @NotNull LivingEntity living) {
      return true;
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag root = instance.getOrCreateTag().getCompound("tensuramoreskills_kronairos");
      return instance.isToggled() || root.hasUUID("TakersWillTarget");
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof ServerPlayer player) {
         if (!instance.isTemporarySkill()) {
            IExistence existence = TensuraStorages.getExistenceFrom(player);
            double current = existence.getMagicule();
            if (current >= 2000000.0) {
               existence.setMagicule(Math.max(0.0, current - 2000000.0));
               existence.markDirty();
            }
         }
      }
   }

   public void onTick(@NotNull ManasSkillInstance instance, @NotNull LivingEntity entity) {
      if (entity.level() instanceof ServerLevel level) {
         CompoundTag tag = instance.getOrCreateTag();
         CompoundTag root = tag.getCompound("tensuramoreskills_kronairos");
         if (entity instanceof ServerPlayer player) {
            IExistence existence = TensuraStorages.getExistenceFrom(player);
            double currentEP = existence.getEP();
            double lastEP = root.getDouble("LastEP");
            if (lastEP > 0.0 && currentEP > lastEP) {
               double gain = currentEP - lastEP;
               existence.setEP(currentEP + gain);
               existence.markDirty();
            }

            root.putDouble("LastEP", existence.getEP());
         }

         if (instance.isToggled()) {
            applyHpBuffs(entity);
            applyAuraDebuffs(level, entity);
         }

         tickTakersWill(level, entity, root);
         tag.put("tensuramoreskills_kronairos", root);
         instance.markDirty();
      }
   }

   private static void applyHpBuffs(LivingEntity entity) {
      float max = entity.getMaxHealth();
      if (!(max <= 0.0F)) {
         float pct = entity.getHealth() / max;
         int amp;
         if (pct < 0.2F) {
            amp = 4;
         } else if (pct < 0.4F) {
            amp = 3;
         } else if (pct < 0.6F) {
            amp = 2;
         } else {
            if (!(pct < 0.8F)) {
               return;
            }

            amp = 1;
         }

         entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 60, amp, false, false, false));
      }
   }

   private static void applyAuraDebuffs(ServerLevel level, LivingEntity self) {
      AABB box = self.getBoundingBox().inflate(3.0);

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, e -> e.isAlive() && e != self && !e.isAlliedTo(self))) {
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE), 60, 0, false, false, false), self);
         target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 0, false, false, false), self);
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.TRUE_BLINDNESS), 60, 0, false, false, false), self);
      }
   }

   private static void tickTakersWill(ServerLevel level, LivingEntity owner, CompoundTag root) {
      if (root.hasUUID("TakersWillTarget")) {
         long end = root.getLong("TakersWillEnd");
         long next = root.getLong("TakersWillNext");
         long now = level.getGameTime();
         if (now > end) {
            root.remove("TakersWillTarget");
            root.remove("TakersWillEnd");
            root.remove("TakersWillNext");
         } else if (now >= next) {
            LivingEntity target = getEntityByUuid(level, root.getUUID("TakersWillTarget"));
            if (target != null && target.isAlive() && !target.isAlliedTo(owner)) {
               double pct = 0.1;
               if (SkillUtils.isSkillToggled(target, (ManasSkill)ResistanceSkills.MAGIC_NULLIFICATION.get())) {
                  pct = 0.01;
               } else if (SkillUtils.isSkillToggled(target, (ManasSkill)ResistanceSkills.MAGIC_RESISTANCE.get())) {
                  pct = 0.05;
               }

               float reduce = (float)(target.getMaxHealth() * pct);
               target.setHealth(Math.max(1.0F, target.getHealth() - reduce));
               root.putLong("TakersWillNext", now + 200L);
            } else {
               root.remove("TakersWillTarget");
               root.remove("TakersWillEnd");
               root.remove("TakersWillNext");
            }
         }
      }
   }

   public boolean onTakenDamage(ManasSkillInstance instance, LivingEntity entity, DamageSource source, Changeable<Float> amount) {
      if (!instance.isToggled()) {
         return true;
      }

      float max = entity.getMaxHealth();
      if (max <= 0.0F) {
         return true;
      }

      float pct = entity.getHealth() / max;
      if (pct < 0.2F && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
         amount.set((Float)amount.get() * 0.7F);
      }

      return true;
   }

   public boolean onDamageEntity(ManasSkillInstance instance, LivingEntity attacker, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (instance.isToggled()) {
         IExistence attackerData = TensuraStorages.getExistenceFrom(attacker);
         IExistence targetData = TensuraStorages.getExistenceFrom(target);
         double stolen = targetData.getMagicule() * 0.01;
         if (stolen > 0.0 && attacker.getRandom().nextInt(10) == 0) {
            targetData.setMagicule(Math.max(0.0, targetData.getMagicule() - stolen));
            attackerData.setMagicule(attackerData.getMagicule() + stolen);
            targetData.markDirty();
            attackerData.markDirty();
         }
      }

      if (this.isInSlot(attacker, instance)) {
         disableInfinityPrisonBlockers(target);
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.INFINITE_IMPRISONMENT), 60, 0, false, false, true), attacker);
      }

      return true;
   }

   private static void disableInfinityPrisonBlockers(LivingEntity target) {
      Skills skills = SkillAPI.getSkillsFrom(target);

      for (ManasSkillInstance inst : List.copyOf(skills.getLearnedSkills())) {
         if (inst != null && inst.getSkill() != null) {
            ManasSkill skill = inst.getSkill();
            if (skill == ResistanceSkills.ABNORMAL_CONDITION_NULLIFICATION.get()
               || skill == ResistanceSkills.ABNORMAL_CONDITION_RESISTANCE.get()
               || skill == ResistanceSkills.MAGIC_NULLIFICATION.get()
               || skill == ResistanceSkills.MAGIC_RESISTANCE.get()) {
               try {
                  inst.setToggled(false);
                  inst.onToggleOff(target);
                  inst.markDirty();
               } catch (Throwable var7) {
               }
            }
         }
      }
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (mode != 3) {
         return false;
      } else if (!(entity.level() instanceof ServerLevel level)) {
         return false;
      } else {
         CompoundTag tag = instance.getOrCreateTag();
         CompoundTag root = tag.getCompound("tensuramoreskills_kronairos");
         LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, 20.0, false);
         if (target != null && target.isAlive() && target != entity && !target.isAlliedTo(entity)) {
            UUID uuid = target.getUUID();
            if (!root.hasUUID("IsolationTarget") || !root.getUUID("IsolationTarget").equals(uuid)) {
               root.putUUID("IsolationTarget", uuid);
               root.putInt("IsolationSeconds", 0);
            }

            if (heldTicks % 20 == 0) {
               root.putInt("IsolationSeconds", root.getInt("IsolationSeconds") + 1);
            }

            if (entity instanceof ServerPlayer sp) {
               sp.displayClientMessage(Component.literal("Isolation: " + root.getInt("IsolationSeconds") + "/10").withStyle(ChatFormatting.GOLD), true);
            }

            if (root.getInt("IsolationSeconds") >= 10) {
               root.putInt("IsolationSeconds", 0);
               root.remove("IsolationTarget");
               tag.put("tensuramoreskills_kronairos", root);
               instance.markDirty();
               if (EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
                  return false;
               }

               applyFullSkillLock(entity, target, 100);
               instance.setCoolDown(200, mode);
               entity.swing(InteractionHand.MAIN_HAND, true);
               level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 2.0F, 1.0F);
               instance.addMasteryPoint(entity);
               return false;
            } else {
               tag.put("tensuramoreskills_kronairos", root);
               instance.markDirty();
               return true;
            }
         } else {
            root.remove("IsolationTarget");
            root.putInt("IsolationSeconds", 0);
            tag.put("tensuramoreskills_kronairos", root);
            instance.markDirty();
            return false;
         }
      }
   }

   private static void applyFullSkillLock(LivingEntity caster, LivingEntity victim, int durationTicks) {
      hardDisableAllSkills(victim, durationTicks);
      victim.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL), durationTicks, 1, false, false, true), caster);
      victim.addEffect(
         new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.INFINITE_IMPRISONMENT), durationTicks, 1, false, false, true), caster
      );
   }

   private static void hardDisableAllSkills(LivingEntity victim, int cdTicks) {
      Skills skills = SkillAPI.getSkillsFrom(victim);

      for (ManasSkillInstance s : List.copyOf(skills.getLearnedSkills())) {
         if (s != null) {
            try {
               s.setToggled(false);
               s.onToggleOff(victim);
               s.setCoolDown(cdTicks, 0);
               s.markDirty();
            } catch (Throwable var6) {
            }
         }
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity.level() instanceof ServerLevel level) {
         if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            switch (mode) {
               case 0:
                  this.takersWill(instance, entity, level, mode);
                  break;
               case 1:
                  this.powerHunger(instance, entity, mode);
                  break;
               case 2:
                  this.room(instance, entity, level, mode);
               case 3:
               default:
                  break;
               case 4:
                  this.divineLifesteal(instance, entity, level, mode);
            }
         }
      }
   }

   private void takersWill(ManasSkillInstance instance, LivingEntity entity, ServerLevel level, int mode) {
      LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, 30.0, false);
      if (target != null && target.isAlive() && target != entity && !target.isAlliedTo(entity)) {
         CompoundTag tag = instance.getOrCreateTag();
         CompoundTag root = tag.getCompound("tensuramoreskills_kronairos");
         long now = level.getGameTime();
         root.putUUID("TakersWillTarget", target.getUUID());
         root.putLong("TakersWillEnd", now + 1200L);
         root.putLong("TakersWillNext", now + 200L);
         tag.put("tensuramoreskills_kronairos", root);
         instance.markDirty();
         entity.swing(InteractionHand.MAIN_HAND, true);
         level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
         instance.setCoolDown(200, mode);
         instance.addMasteryPoint(entity);
      } else {
         if (entity instanceof Player p) {
            p.displayClientMessage(Component.translatable("tensura.targeting.not_targeted").withStyle(ChatFormatting.RED), false);
         }

         instance.setCoolDown(20, mode);
      }
   }

   private void powerHunger(ManasSkillInstance instance, LivingEntity entity, int mode) {
      LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, 20.0, false);
      if (target != null && target.isAlive() && target != entity) {
         double userEP = TensuraStorages.getExistenceFrom(entity).getEP();
         double targetEP = TensuraStorages.getExistenceFrom(target).getEP();
         if (targetEP > userEP * 5.0) {
            if (entity instanceof Player p) {
               p.displayClientMessage(Component.literal("Target EP too high.").withStyle(ChatFormatting.RED), false);
            }

            instance.setCoolDown(40, mode);
         } else {
            Skills userSkills = SkillAPI.getSkillsFrom(entity);
            Skills targetSkills = SkillAPI.getSkillsFrom(target);
            List<ManasSkill> pool = new ArrayList<>();

            for (ManasSkillInstance inst : targetSkills.getLearnedSkills()) {
               if (inst != null && inst.getSkill() instanceof Skill skill) {
                  SkillType type = skill.getType();
                  if (type == SkillType.INTRINSIC || type == SkillType.RESISTANCE || type == SkillType.COMMON || type == SkillType.EXTRA) {
                     ManasSkill ms = inst.getSkill();
                     if (userSkills.getSkill(ms).isEmpty()) {
                        pool.add(ms);
                     }
                  }
               }
            }

            if (pool.isEmpty()) {
               if (entity instanceof Player p) {
                  p.displayClientMessage(Component.literal("No eligible skills to copy.").withStyle(ChatFormatting.GRAY), false);
               }

               instance.setCoolDown(40, mode);
            } else {
               int copies = 1;
               if (entity.getRandom().nextInt(100) < 5) {
                  copies = 3;
               } else if (entity.getRandom().nextInt(100) < 10) {
                  copies = 2;
               }

               List<String> copied = new ArrayList<>();
               int done = 0;

               while (done < copies && !pool.isEmpty()) {
                  ManasSkill pick = pool.remove(entity.getRandom().nextInt(pool.size()));
                  Optional<ManasSkillInstance> existing = SkillAPI.getSkillsFrom(entity).getSkill(pick);
                  if (existing.isEmpty()) {
                     TensuraSkillInstance copy = new TensuraSkillInstance(pick);
                     copy.setMastery(0.0);
                     if (SkillHelper.learnSkill(entity, copy, instance.getRemoveTime())) {
                        copied.add(pick.getRegistryName().toString());
                        done++;
                     }
                  } else {
                     ManasSkillInstance learned = existing.get();
                     double newMastery = Math.max(learned.getMastery(), learned.getMaxMastery() * 0.25);
                     learned.setMastery(newMastery);
                     copied.add(pick.getRegistryName().toString());
                     done++;
                  }
               }

               if (entity instanceof ServerPlayer sp) {
                  sp.displayClientMessage(Component.literal("Power Hunger copied: " + String.join(", ", copied)).withStyle(ChatFormatting.GOLD), false);
               }

               entity.swing(InteractionHand.MAIN_HAND, true);
               entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 0.8F);
               instance.setCoolDown(100, mode);
               instance.addMasteryPoint(entity);
            }
         }
      } else {
         if (entity instanceof Player p) {
            p.displayClientMessage(Component.translatable("tensura.targeting.not_targeted").withStyle(ChatFormatting.RED), false);
         }

         instance.setCoolDown(20, mode);
      }
   }

   private void room(ManasSkillInstance instance, LivingEntity entity, ServerLevel level, int mode) {
      AABB box = entity.getBoundingBox().inflate(1.5);

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, e -> e.isAlive() && e != entity && !e.isAlliedTo(entity))) {
         disableInfinityPrisonBlockers(target);
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.INFINITE_IMPRISONMENT), 600, 1, false, false, true), entity);
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.ANTI_SKILL), 600, 0, false, false, true), entity);
      }

      level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.0F, 1.0F);
      entity.swing(InteractionHand.MAIN_HAND, true);
      instance.setCoolDown(100, mode);
      instance.addMasteryPoint(entity);
   }

   private void divineLifesteal(ManasSkillInstance instance, LivingEntity entity, ServerLevel level, int mode) {
      AABB box = entity.getBoundingBox().inflate(2.5);
      List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, box, e -> e.isAlive() && e != entity && !e.isAlliedTo(entity));
      DamageSource space = this.createSource(instance, entity, TensuraDamageTypes.SPACE_ELEMENTAL, mode);
      DamageSource holy = this.createSource(instance, entity, TensuraDamageTypes.HOLY_DAMAGE, mode);

      for (LivingEntity target : list) {
         boolean spatialNull = SkillUtils.isSkillToggled(target, (ManasSkill)ResistanceSkills.SPATIAL_ATTACK_NULLIFICATION.get());
         boolean spatialRes = SkillUtils.isSkillToggled(target, (ManasSkill)ResistanceSkills.SPATIAL_ATTACK_RESISTANCE.get());
         boolean holyNull = SkillUtils.isSkillToggled(target, (ManasSkill)ResistanceSkills.HOLY_ATTACK_NULLIFICATION.get());
         boolean holyRes = SkillUtils.isSkillToggled(target, (ManasSkill)ResistanceSkills.HOLY_ATTACK_RESISTANCE.get());
         float spatial = spatialNull ? 0.0F : (spatialRes ? 2000.0F : 5000.0F);
         float holyDmg = holyNull ? 0.0F : (holyRes ? 2000.0F : 5000.0F);
         if (spatial > 0.0F) {
            target.hurt(space, spatial);
         }

         if (holyDmg > 0.0F) {
            target.hurt(holy, holyDmg);
         }

         if (spatial > 0.0F || holyDmg > 0.0F) {
            entity.heal((spatial + holyDmg) * 0.15F);
         }
      }

      level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 1.0F, 1.0F);
      entity.swing(InteractionHand.MAIN_HAND, true);
      instance.setCoolDown(50, mode);
      instance.addMasteryPoint(entity);
   }

   private static LivingEntity getEntityByUuid(ServerLevel level, UUID uuid) {
      return level.getEntity(uuid) instanceof LivingEntity living ? living : null;
   }
}
