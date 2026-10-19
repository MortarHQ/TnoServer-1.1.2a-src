package com.github.wal_bos.moreskills.ability.skill.shades;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.handler.UniqueOwnerSkillHandler;
import com.github.wal_bos.moreskills.menu.NaberiusMenuProvider;
import com.github.wal_bos.moreskills.registry.effect.ModMobEffects;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.entity.human.CloneEntity;
import io.github.manasmods.tensura.entity.human.CloneEntity.CopySkill;
import io.github.manasmods.tensura.entity.template.subclass.ISubordinate;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.entity.HumanEntityTypes;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class NaberiusSkill extends AnimatedShadeSkill {
   private static final ResourceLocation[] ICON_FRAMES = new ResourceLocation[]{
      ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/ultimate/naberius_1.png")
   };
   public static final ResourceLocation MASTERY_PIERCE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_mastery_pierce");
   public static final ResourceLocation WILL_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_will");
   public static final ResourceLocation VISION_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_vision");
   public static final ResourceLocation SPEED_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_speed");
   public static final ResourceLocation DMG_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_damage");
   public static final ResourceLocation ATK_SPEED_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_attack_speed");
   public static final ResourceLocation CHANT_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_chant");
   public static final ResourceLocation DODGE_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_dodge");
   public static final ResourceLocation DOMAIN_HP_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_domain_hp");
   public static final ResourceLocation DOMAIN_DMG_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_domain_dmg");
   public static final ResourceLocation DOMAIN_SCALE_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "naberius_domain_scale");
   public static final int STANCE_SUMMON = 0;
   public static final int STANCE_COMBAT = 1;
   public static final int MODE_ARCHIVE = 1;
   public static final int MODE_MANIFEST = 2;
   public static final int MODE_CHIMERA = 3;
   public static final int MODE_DOMAIN = 4;
   public static final int MODE_LIFE_DRAIN = 5;
   public static final int MODE_ORGAN_FAILURE = 6;
   public static final int MODE_HINATA_FUSION = 7;
   public static final String TAG_STANCE = "stance";
   public static final String TAG_ROOT = "tms_naberius";
   public static final String TAG_ARCHIVE = "archive";
   public static final String TAG_ACTIVE = "active";
   public static final String TAG_TYPE = "type";
   public static final String TAG_NAME = "name";
   public static final String TAG_DATA = "data";
   public static final String TAG_HP = "hp";
   public static final String TAG_EP = "ep";
   public static final String TAG_SHP = "shp";
   public static final String TAG_COUNT = "count";
   public static final String TAG_SELECTED = "selected";
   public static final String TAG_DOMAIN = "domain";
   public static final String TAG_DOMAIN_TICKS = "domain_ticks";
   public static final String TAG_DOMAIN_POWER = "domain_power";
   public static final String TAG_DOMAIN_RADIUS = "domain_radius";
   public static final String TAG_IS_PLAYER = "is_player";
   public static final String TAG_PLAYER_NAME = "player_name";
   public static final String TAG_PLAYER_UUID = "player_uuid";
   public static final String TAG_SKILLS = "skills";
   public static final String TAG_LETHAL_CD = "lethal_cd";
   public static final String TAG_ARCHIVE_INDEX = "archive_index";
   public static final String TAG_OWNER = "owner_uuid";
   public static final String TAG_NABERIUS_ACTIVE = "naberius_active";
   public static final String TAG_DEAD = "dead";
   public static final String TAG_REVIVE_COUNT = "revive_count";
   public static final String TAG_LAST_REVIVE_TICK = "last_revive_tick";
   public static final String TAG_AGGRO_UUID = "aggro_uuid";
   public static final String TAG_HINATA_FUSION_ENTITY = "hinata_fusion_entity";
   public static final String TAG_MASTERY_TICKS = "mastery_ticks";

   public NaberiusSkill() {
      super(SkillType.ULTIMATE, ICON_FRAMES, 500L);
   }

   private static MoreSkillsConfigs.NaberiusConfig cfg() {
      return MoreSkillsConfigs.GRAND.naberius;
   }

   public int getModes(ManasSkillInstance instance) {
      return 8;
   }

   public boolean canBeLearned(LivingEntity entity) {
      return false;
   }

   public int getMaxMastery() {
      return (Integer)cfg().maxMastery.get();
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      int current = mode;
      if (current < 1 || current > 7) {
         current = 1;
      }

      return reverse ? (current <= 1 ? 7 : current - 1) : (current >= 7 ? 1 : current + 1);
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0, 1 -> "naberius.archive";
         case 2 -> "naberius.manifest";
         case 3 -> "naberius.chimera";
         case 4 -> "naberius.domain";
         case 5 -> "naberius.life_drain";
         case 6 -> "naberius.organ_failure";
         case 7 -> "naberius.hinata_fusion";
         default -> "naberius.archive";
      };
   }

   public Component getModeName(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0, 1 -> Component.translatable("tensuramoreskills.skill.mode.naberius.archive");
         case 2 -> Component.translatable("tensuramoreskills.skill.mode.naberius.manifest");
         case 3 -> Component.translatable("tensuramoreskills.skill.mode.naberius.chimera");
         case 4 -> Component.translatable("tensuramoreskills.skill.mode.naberius.domain");
         case 5 -> Component.translatable("tensuramoreskills.skill.mode.naberius.life_drain");
         case 6 -> Component.translatable("tensuramoreskills.skill.mode.naberius.organ_failure");
         case 7 -> Component.translatable("tensuramoreskills.skill.mode.naberius.hinata_fusion");
         default -> Component.translatable("tensuramoreskills.skill.mode.naberius.archive");
      };
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return true;
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 2 -> cfg().manifestCost.get();
         case 3 -> cfg().chimeraCost.get();
         case 4 -> cfg().domainCost.get();
         case 5 -> cfg().lifeDrainCost.get();
         case 6 -> cfg().organFailureCost.get();
         case 7 -> cfg().hinataFusionCost.get();
         default -> 0.0;
      };
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      UniqueOwnerSkillHandler.learnExclusiveSkill(instance, entity);
      AttributeHelper.addPermanentAttribute(entity, Attributes.KNOCKBACK_RESISTANCE, WILL_ID, (Double)cfg().learnKnockbackResistance.get(), Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, Attributes.MOVEMENT_SPEED, SPEED_ID, (Double)cfg().learnMovementSpeed.get(), Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, Attributes.ATTACK_DAMAGE, DMG_ID, (Double)cfg().learnAttackDamage.get(), Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, Attributes.ATTACK_SPEED, ATK_SPEED_ID, (Double)cfg().learnAttackSpeed.get(), Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.PRESENCE_SENSE, VISION_ID, (Double)cfg().learnPresenceSense.get(), Operation.ADD_VALUE);
      AttributeHelper.addPermanentAttribute(entity, TensuraAttributes.CHANT_SPEED, CHANT_ID, (Double)cfg().learnChantSpeed.get(), Operation.ADD_VALUE);
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      UniqueOwnerSkillHandler.releaseSkill(instance, entity);
      AttributeHelper.removeAttribute(entity, Attributes.KNOCKBACK_RESISTANCE, WILL_ID);
      AttributeHelper.removeAttribute(entity, Attributes.MOVEMENT_SPEED, SPEED_ID);
      AttributeHelper.removeAttribute(entity, Attributes.ATTACK_DAMAGE, DMG_ID);
      AttributeHelper.removeAttribute(entity, Attributes.ATTACK_SPEED, ATK_SPEED_ID);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.PRESENCE_SENSE, VISION_ID);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.CHANT_SPEED, CHANT_ID);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE, DODGE_ID);
      AttributeHelper.removeAttribute(entity, TensuraAttributes.RESISTANCE_DEGRADATION, MASTERY_PIERCE);
   }

   public void onSkillMastered(ManasSkillInstance instance, LivingEntity entity) {
      AttributeHelper.addPermanentAttribute(
         entity, TensuraAttributes.RESISTANCE_DEGRADATION, MASTERY_PIERCE, (Double)cfg().masteredResistanceDegradation.get(), Operation.ADD_VALUE
      );
      AttributeHelper.addPermanentAttribute(
         entity, TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE, DODGE_ID, (Double)cfg().masteredProjectileDodgeChance.get(), Operation.ADD_VALUE
      );
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof ServerPlayer player) {
         player.displayClientMessage(Component.literal("Naberius opens the grave archive.").withStyle(ChatFormatting.DARK_GREEN), true);
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof ServerPlayer player) {
         this.clearActiveSummons(player);
         player.displayClientMessage(Component.literal("Naberius closes its jaws.").withStyle(ChatFormatting.GRAY), true);
      }
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof ServerPlayer player) {
         if (player.level() instanceof ServerLevel level) {
            CompoundTag var8 = root(player);
            this.tickPassiveMastery(player, instance, var8);
            if (instance.isToggled() || NaberiusCombatStanceHandler.isCombatStance(player)) {
               NaberiusCombatStanceHandler.tick(this, player, instance, level, var8);
               if (instance.isToggled()) {
                  player.addEffect(
                     new MobEffectInstance(
                        ModMobEffects.getReference(ModMobEffects.NABERIUS_WILL), (Integer)cfg().passiveEffectDurationTicks.get(), 0, false, false, false
                     )
                  );
                  player.addEffect(
                     new MobEffectInstance(
                        ModMobEffects.getReference(ModMobEffects.NABERIUS_VISION), (Integer)cfg().passiveEffectDurationTicks.get(), 0, false, false, false
                     )
                  );
                  player.addEffect(
                     new MobEffectInstance(
                        TensuraMobEffects.getReference(TensuraMobEffects.PRESENCE_SENSE),
                        (Integer)cfg().passiveEffectDurationTicks.get(),
                        (Integer)cfg().passivePresenceSenseAmplifier.get(),
                        false,
                        false,
                        false
                     )
                  );
                  this.cleanseNegativePassives(player);
               }

               if (player.tickCount % (Integer)cfg().auraParticleIntervalTicks.get() == 0) {
                  this.spawnNaberiusAura(level, player);
               }

               this.markDeadActiveSummons(player, level);
               this.tryReviveActiveSummons(player, level);
               if (player.tickCount % (Integer)cfg().summonRetargetIntervalTicks.get() == 0) {
                  this.retargetSummonsNearOwner(player, level);
               }

               this.tickHinataFusionCombat(player, level);
            } else if (!var8.getList("active", 10).isEmpty()) {
               this.clearActiveSummons(player);
            }

            if (var8.getBoolean("domain")) {
               int ticks = var8.getInt("domain_ticks") + 1;
               var8.putInt("domain_ticks", ticks);
               if (ticks % (Integer)cfg().domainAuraIntervalTicks.get() == 0) {
                  this.spawnDomainAura(level, player);
               }

               if (ticks % (Integer)cfg().domainBuffIntervalTicks.get() == 0) {
                  int power = Math.max(1, var8.getInt("domain_power")) + ticks / (Integer)cfg().domainBuffIntervalTicks.get();
                  this.buffSummonsForDomain(player, level, power);
                  this.safeTickDomain(player, level);
               }

               int maxDuration = (Integer)cfg().domainBaseDurationTicks.get() + var8.getInt("domain_power") * (Integer)cfg().domainDurationPerPowerTicks.get();
               if (ticks >= maxDuration) {
                  var8.putBoolean("domain", false);
                  var8.putInt("domain_ticks", 0);
                  var8.putInt("domain_power", 0);
                  var8.putInt("domain_radius", 0);
                  this.clearDomainBuffs(player, level);
                  player.displayClientMessage(Component.literal("Garden of False Life collapses into rot.").withStyle(ChatFormatting.GRAY), true);
               }
            }

            if (instance.isMastered(player)) {
               int cd = var8.getInt("lethal_cd");
               if (cd > 0) {
                  var8.putInt("lethal_cd", cd - 1);
               }
            }
         }
      }
   }

   private void cleanseNegativePassives(ServerPlayer player) {
      player.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.FEAR));
      player.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.INSANITY));
      player.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.HYPNOSIS));
      player.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.MIND_CONTROL));
      player.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.BURDEN));
      player.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.WEBBED));
      player.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE));
      player.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE));
   }

   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (entity instanceof ServerPlayer player) {
         if (NaberiusCombatStanceHandler.isCombatStance(player)) {
            return NaberiusCombatStanceHandler.onHeld(this, player, instance, heldTicks, mode);
         }

         if (mode != 4) {
            return false;
         }

         if (heldTicks % (Integer)cfg().domainHoldCheckIntervalTicks.get() == 0) {
            int chargeSeconds = Math.max(1, heldTicks / 20);
            double holdCost = (Double)cfg().domainHoldBaseCost.get() + chargeSeconds * (Double)cfg().domainHoldCostPerSecond.get();
            if (!this.canAfford(player, holdCost)) {
               return false;
            }

            if (player.level() instanceof ServerLevel level) {
               this.spawnGardenHoldVfx(level, player, chargeSeconds);
               if (heldTicks % (Integer)cfg().domainBuffIntervalTicks.get() == 0) {
                  this.buffSummonsForDomain(player, level, chargeSeconds);
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      if (entity instanceof ServerPlayer player) {
         if (NaberiusCombatStanceHandler.isCombatStance(player)) {
            NaberiusCombatStanceHandler.onRelease(this, player, instance, heldTicks, mode);
         } else if (mode == 4) {
            if (player.level() instanceof ServerLevel level) {
               if (heldTicks >= 10) {
                  int chargeSeconds = Math.max(1, heldTicks / 20);
                  double totalCost = (Double)cfg().domainReleaseBaseCost.get()
                     + chargeSeconds * (Double)cfg().domainReleaseCostPerSecond.get()
                     + chargeSeconds * chargeSeconds * (Double)cfg().domainReleaseCostPerSecondSquared.get();
                  if (!this.spendMagicule(player, totalCost)) {
                     player.displayClientMessage(Component.literal("Naberius rejects a hollow garden.").withStyle(ChatFormatting.RED), true);
                  } else {
                     CompoundTag root = root(player);
                     root.putBoolean("domain", true);
                     root.putInt("domain_ticks", 0);
                     root.putInt("domain_power", chargeSeconds);
                     root.putInt("domain_radius", (Integer)cfg().domainBaseRadius.get() + chargeSeconds / (Integer)cfg().domainRadiusPowerDivisor.get());
                     player.swing(InteractionHand.MAIN_HAND, true);
                     level.playSound(null, player.blockPosition(), SoundEvents.WARDEN_ROAR, SoundSource.PLAYERS, 2.0F, 0.7F);
                     this.spawnGardenBloomVfx(level, player, chargeSeconds);
                     this.buffSummonsForDomain(player, level, chargeSeconds);
                     instance.setCoolDown((Integer)cfg().domainCooldownTicks.get(), mode);
                     player.displayClientMessage(Component.literal("Garden of False Life blooms.").withStyle(ChatFormatting.GREEN), true);
                  }
               }
            }
         }
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity instanceof ServerPlayer player) {
         if ((mode == 0 || mode == 1) && player.isShiftKeyDown()) {
            NaberiusCombatStanceHandler.switchStance(player);
         } else if (NaberiusCombatStanceHandler.isCombatStance(player)) {
            NaberiusCombatStanceHandler.onPressed(this, player, instance, mode);
         } else {
            switch (mode) {
               case 0:
               case 1:
                  NaberiusMenuProvider.open(player, 1);
                  break;
               case 2:
                  this.manifestSelected(player, instance, player.isShiftKeyDown());
                  break;
               case 3:
                  NaberiusMenuProvider.open(player, 3);
                  break;
               case 4:
                  player.displayClientMessage(Component.literal("Hold to bloom Garden of False Life.").withStyle(ChatFormatting.DARK_GREEN), true);
                  break;
               case 5:
                  if (player.isShiftKeyDown()) {
                     this.stealAllSummons(player, instance);
                  } else {
                     this.castLifeDrain(player, instance);
                  }
                  break;
               case 6:
                  this.castOrganFailure(player, instance);
                  break;
               case 7:
                  this.castHinataFusion(player, instance);
            }
         }
      }
   }

   public static CompoundTag root(LivingEntity entity) {
      CompoundTag root = entity.getPersistentData().getCompound("tms_naberius");
      entity.getPersistentData().put("tms_naberius", root);
      return root;
   }

   public static List<NaberiusSkill.ArchiveEntryView> getArchiveEntries(ServerPlayer player) {
      List<NaberiusSkill.ArchiveEntryView> out = new ArrayList<>();
      ListTag archive = root(player).getList("archive", 10);

      for (int i = 0; i < archive.size(); i++) {
         CompoundTag entry = archive.getCompound(i);
         out.add(
            new NaberiusSkill.ArchiveEntryView(
               i,
               entry.getString("name"),
               entry.getDouble("hp"),
               entry.getDouble("ep"),
               entry.getInt("count"),
               entry.getBoolean("selected"),
               entry.getBoolean("is_player")
            )
         );
      }

      return out;
   }

   public static int getAliveActiveSummonCount(ServerPlayer owner) {
      if (owner.level() instanceof ServerLevel level) {
         ListTag var8 = root(owner).getList("active", 10);
         int alive = 0;

         for (int i = 0; i < var8.size(); i++) {
            CompoundTag tag = var8.getCompound(i);
            if (!tag.getBoolean("dead")
               && tag.hasUUID("uuid")
               && level.getEntity(tag.getUUID("uuid")) instanceof LivingEntity living
               && living.isAlive()
               && isOwnedNaberiusSummon(owner, living)) {
               alive++;
            }
         }

         return alive;
      } else {
         return 0;
      }
   }

   public void manifestSelected(ServerPlayer player, ManasSkillInstance instance, boolean summonCounts) {
      if (player.level() instanceof ServerLevel level) {
         if (!EnergyHelper.isOutOfEnergy(player, instance, 2)) {
            this.markDeadActiveSummons(player, level);
            CompoundTag root = root(player);
            ListTag archive = root.getList("archive", 10);
            if (archive.isEmpty()) {
               player.displayClientMessage(Component.literal("The archive is barren.").withStyle(ChatFormatting.RED), true);
            } else {
               int capacity = Math.max(0, (Integer)cfg().maxActiveSummons.get() - getAliveActiveSummonCount(player));
               if (capacity <= 0) {
                  player.displayClientMessage(Component.literal("Naberius refuses to overfill the grave.").withStyle(ChatFormatting.RED), true);
               } else {
                  int spawned = 0;
                  boolean hadSelected = false;

                  for (int i = 0; i < archive.size() && spawned < capacity; i++) {
                     CompoundTag entry = archive.getCompound(i);
                     if (entry.getBoolean("selected")) {
                        hadSelected = true;
                        int count = Math.max(1, entry.getInt("count"));
                        int spawnAmount = summonCounts ? count : 1;

                        for (int c = 0; c < spawnAmount && spawned < capacity; c++) {
                           LivingEntity summon = entry.getBoolean("is_player")
                              ? this.spawnPlayerArchive(player, level, i)
                              : this.spawnArchived(player, level, i);
                           if (summon != null) {
                              this.trackActive(player, summon, i);
                              spawned++;
                           }
                        }
                     }
                  }

                  if (!hadSelected) {
                     player.displayClientMessage(Component.literal("No cadavers have been marked.").withStyle(ChatFormatting.RED), true);
                  } else if (spawned <= 0) {
                     player.displayClientMessage(Component.literal("Naberius finds no vessel worth raising.").withStyle(ChatFormatting.RED), true);
                  } else {
                     instance.setCoolDown((Integer)cfg().manifestCooldownTicks.get(), 2);
                     player.displayClientMessage(Component.literal("Naberius exhumes " + spawned + " servant(s).").withStyle(ChatFormatting.GREEN), true);
                  }
               }
            }
         }
      }
   }

   private LivingEntity spawnArchived(ServerPlayer owner, ServerLevel level, int index) {
      ListTag archive = root(owner).getList("archive", 10);
      if (index >= 0 && index < archive.size()) {
         CompoundTag entry = archive.getCompound(index);
         if (entry.getBoolean("is_player")) {
            return null;
         }

         CompoundTag data = entry.getCompound("data").copy();
         if (!data.contains("id", 8)) {
            data.putString("id", entry.getString("type"));
         }

         data.remove("UUID");
         data.remove("Pos");
         data.remove("Motion");
         data.remove("Rotation");
         data.remove("PortalCooldown");
         data.remove("Leash");
         data.remove("Passengers");

         Entity recreated;
         try {
            recreated = EntityType.loadEntityRecursive(
               data,
               level,
               e -> {
                  e.moveTo(
                     owner.getX() + owner.getLookAngle().x * (Double)cfg().summonSpawnDistance.get(),
                     owner.getY(),
                     owner.getZ() + owner.getLookAngle().z * (Double)cfg().summonSpawnDistance.get(),
                     owner.getYRot(),
                     0.0F
                  );
                  return e;
               }
            );
         } catch (Throwable ignored) {
            return null;
         }

         if (recreated instanceof LivingEntity summon) {
            try {
               this.prepareSummon(owner, summon, entry, false);
               this.applyStoredSkillsToEntity(summon, entry);
               level.addFreshEntity(summon);
               this.spawnSummonBirthVfx(level, summon);
               return summon;
            } catch (Throwable ignored) {
               try {
                  summon.discard();
               } catch (Throwable var11) {
               }

               return null;
            }
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private LivingEntity spawnPlayerArchive(ServerPlayer owner, ServerLevel level, int index) {
      ListTag archive = root(owner).getList("archive", 10);
      if (index >= 0 && index < archive.size()) {
         CompoundTag entry = archive.getCompound(index);
         if (!entry.getBoolean("is_player")) {
            return null;
         }

         CloneEntity clone;
         try {
            EntityType<?> type = (EntityType<?>)EntityType.byString("tensura:clone").orElse(null);
            if (type == null) {
               return null;
            }

            clone = new CloneEntity(type, level);
         } catch (Throwable ignored) {
            return null;
         }

         try {
            clone.tame(owner);
            clone.setPos(
               owner.getX() + owner.getLookAngle().x * (Double)cfg().summonSpawnDistance.get(),
               owner.getY(),
               owner.getZ() + owner.getLookAngle().z * (Double)cfg().summonSpawnDistance.get()
            );
            clone.copyStatsAndSkills(owner, CopySkill.ALL, true);
            CloneEntity.copyEffects(owner, clone);
            this.prepareSummon(owner, clone, entry, true);
            this.applyStoredSkillsToEntity(clone, entry);
            level.addFreshEntity(clone);
            this.spawnSummonBirthVfx(level, clone);
            return clone;
         } catch (Throwable ignored) {
            try {
               clone.discard();
            } catch (Throwable var9) {
            }

            return null;
         }
      } else {
         return null;
      }
   }

   private void prepareSummon(ServerPlayer owner, LivingEntity summon, CompoundTag entry, boolean playerClone) {
      if (summon instanceof TamableAnimal tamable) {
         tamable.tame(owner);
         tamable.setOwnerUUID(owner.getUUID());
      }

      if (summon instanceof Mob mob) {
         mob.setPersistenceRequired();
         LivingEntity target = this.getSavedAggroTarget(owner);
         if (target != null) {
            mob.setTarget(target);
            mob.setLastHurtByMob(target);
         }
      }

      IExistence existence = TensuraStorages.getExistenceFrom(summon);
      existence.setSummoner(owner.getUUID());
      existence.setSummonedSecond((Integer)cfg().summonDefaultSeconds.get());
      existence.setTemporaryOwner(owner.getUUID());
      existence.markDirty();
      int count = Math.max(1, entry.getInt("count"));
      double avgHp = entry.getDouble("hp") / count;
      double avgEp = entry.getDouble("ep") / count;
      AttributeInstance maxHp = summon.getAttribute(Attributes.MAX_HEALTH);
      if (maxHp != null) {
         double hp = playerClone
            ? Math.max(avgHp, maxHp.getBaseValue() * (Double)cfg().summonPlayerCloneHpBaseMultiplier.get())
            : Math.max(maxHp.getBaseValue(), avgHp);
         maxHp.setBaseValue(hp);
         summon.setHealth((float)Math.min(hp, Float.MAX_VALUE));
      }

      AttributeInstance damage = summon.getAttribute(Attributes.ATTACK_DAMAGE);
      if (damage != null) {
         double scaled = playerClone ? (Double)cfg().summonPlayerCloneMinimumDamage.get() : (Double)cfg().summonDamageBase.get()
            + avgEp * (Double)cfg().summonEpDamageMultiplier.get();
         double value = playerClone
            ? Math.max(damage.getBaseValue() * (Double)cfg().summonPlayerCloneDamageBaseMultiplier.get(), scaled)
            : Math.max(damage.getBaseValue(), scaled);
         damage.setBaseValue(value);
      }

      AttributeInstance armor = summon.getAttribute(Attributes.ARMOR);
      if (armor != null) {
         armor.setBaseValue(Math.max(armor.getBaseValue(), (Double)cfg().summonArmorBase.get() + avgEp * (Double)cfg().summonEpArmorMultiplier.get()));
      }

      AttributeInstance speed = summon.getAttribute(Attributes.MOVEMENT_SPEED);
      if (speed != null) {
         speed.setBaseValue(Math.max(speed.getBaseValue(), (Double)cfg().summonMovementSpeedMinimum.get()));
      }

      AttributeInstance follow = summon.getAttribute(Attributes.FOLLOW_RANGE);
      if (follow != null) {
         follow.setBaseValue(Math.max(follow.getBaseValue(), (Double)cfg().summonFollowRangeMinimum.get()));
      }

      AttributeInstance scale = summon.getAttribute(Attributes.SCALE);
      if (scale != null) {
         scale.setBaseValue(Math.max((Double)cfg().summonScaleMinimum.get(), scale.getBaseValue() + (Double)cfg().summonScaleBonus.get()));
      }

      AttributeHelper.addPermanentAttribute(
         summon, TensuraAttributes.RESISTANCE_DEGRADATION, MASTERY_PIERCE, (Double)cfg().summonResistanceDegradation.get(), Operation.ADD_VALUE
      );
      summon.getPersistentData().putUUID("owner_uuid", owner.getUUID());
      summon.getPersistentData().putBoolean("naberius_active", true);
      summon.setCustomName(Component.literal(entry.getString("name") + (playerClone ? " [Clone]" : "")));
      summon.setCustomNameVisible(true);
   }

   private void trackActive(ServerPlayer owner, LivingEntity summon, int archiveIndex) {
      CompoundTag root = root(owner);
      ListTag active = root.getList("active", 10);
      CompoundTag tag = new CompoundTag();
      tag.putUUID("uuid", summon.getUUID());
      tag.putInt("archive_index", archiveIndex);
      tag.putBoolean("dead", false);
      tag.putInt("revive_count", 0);
      tag.putLong("last_revive_tick", owner.level().getGameTime());
      LivingEntity aggro = this.getSavedAggroTarget(owner);
      if (aggro != null) {
         tag.putUUID("aggro_uuid", aggro.getUUID());
      }

      active.add(tag);
      root.put("active", active);
   }

   public void cleanupActiveSummons(ServerPlayer owner, ServerLevel level) {
      this.markDeadActiveSummons(owner, level);
   }

   private void markDeadActiveSummons(ServerPlayer owner, ServerLevel level) {
      CompoundTag root = root(owner);
      ListTag active = root.getList("active", 10);

      for (int i = 0; i < active.size(); i++) {
         CompoundTag tag = active.getCompound(i).copy();
         if (!tag.hasUUID("uuid")) {
            tag.putBoolean("dead", true);
            active.set(i, tag);
         } else {
            boolean alive = level.getEntity(tag.getUUID("uuid")) instanceof LivingEntity living && living.isAlive() && isOwnedNaberiusSummon(owner, living);
            tag.putBoolean("dead", !alive);
            active.set(i, tag);
         }
      }

      root.put("active", active);
   }

   private void tryReviveActiveSummons(ServerPlayer owner, ServerLevel level) {
      CompoundTag root = root(owner);
      ListTag active = root.getList("active", 10);
      if (!active.isEmpty()) {
         boolean changed = false;
         int alive = getAliveActiveSummonCount(owner);

         for (int i = 0; i < active.size() && alive < cfg().maxActiveSummons.get(); i++) {
            CompoundTag tag = active.getCompound(i).copy();
            if (tag.getBoolean("dead")) {
               int archiveIndex = tag.getInt("archive_index");
               if (archiveIndex >= 0) {
                  double cost = this.getReviveCost(owner, archiveIndex, tag);
                  if (this.canAfford(owner, cost)) {
                     LivingEntity aggro = this.getAggroFromTag(owner, level, tag);
                     if (this.reviveOneActiveSummon(owner, level, archiveIndex, tag, aggro)) {
                        active.set(i, tag);
                        changed = true;
                        alive++;
                     }
                  }
               }
            }
         }

         if (changed) {
            root.put("active", active);
         }
      }
   }

   public boolean reviveKilledSummon(ServerPlayer owner, UUID deadUuid, LivingEntity aggro) {
      if (!(owner.level() instanceof ServerLevel level)) {
         return false;
      } else {
         CompoundTag root = root(owner);
         ListTag active = root.getList("active", 10);
         boolean changed = false;
         if (aggro != null && aggro.isAlive()) {
            root.putUUID("aggro_uuid", aggro.getUUID());
         }

         for (int i = 0; i < active.size(); i++) {
            CompoundTag tag = active.getCompound(i).copy();
            if (tag.hasUUID("uuid") && tag.getUUID("uuid").equals(deadUuid)) {
               int archiveIndex = tag.getInt("archive_index");
               if (archiveIndex < 0) {
                  return false;
               }

               tag.putBoolean("dead", true);
               if (aggro != null && aggro.isAlive()) {
                  tag.putUUID("aggro_uuid", aggro.getUUID());
               }

               if (this.reviveOneActiveSummon(owner, level, archiveIndex, tag, aggro)) {
                  active.set(i, tag);
                  root.put("active", active);
                  return true;
               }

               active.set(i, tag);
               changed = true;
               break;
            }
         }

         if (changed) {
            root.put("active", active);
         }

         return false;
      }
   }

   private boolean reviveOneActiveSummon(ServerPlayer owner, ServerLevel level, int archiveIndex, CompoundTag activeTag, LivingEntity aggro) {
      ListTag archive = root(owner).getList("archive", 10);
      if (archiveIndex >= 0 && archiveIndex < archive.size()) {
         CompoundTag entry = archive.getCompound(archiveIndex);
         double cost = this.getReviveCost(owner, archiveIndex, activeTag);
         if (!this.spendMagicule(owner, cost)) {
            return false;
         }

         if (aggro != null && aggro.isAlive()) {
            root(owner).putUUID("aggro_uuid", aggro.getUUID());
            activeTag.putUUID("aggro_uuid", aggro.getUUID());
         }

         LivingEntity revived = entry.getBoolean("is_player")
            ? this.spawnPlayerArchive(owner, level, archiveIndex)
            : this.spawnArchived(owner, level, archiveIndex);
         if (revived == null) {
            this.giveMagicule(owner, cost);
            return false;
         }

         activeTag.putUUID("uuid", revived.getUUID());
         activeTag.putBoolean("dead", false);
         activeTag.putInt("revive_count", activeTag.getInt("revive_count") + 1);
         activeTag.putLong("last_revive_tick", owner.level().getGameTime());
         if (revived instanceof Mob mob && aggro != null && aggro.isAlive()) {
            mob.setTarget(aggro);
            mob.setLastHurtByMob(aggro);
            mob.getNavigation().moveTo(aggro, (Double)cfg().summonReviveMoveSpeed.get());
         }

         this.spawnReviveVfx(level, revived);
         owner.displayClientMessage(
            Component.literal("Naberius revives " + entry.getString("name") + ". MP -" + (long)cost).withStyle(ChatFormatting.DARK_GREEN), true
         );
         return true;
      } else {
         return false;
      }
   }

   private double getReviveCost(ServerPlayer owner, int archiveIndex, CompoundTag activeTag) {
      ListTag archive = root(owner).getList("archive", 10);
      if (archiveIndex >= 0 && archiveIndex < archive.size()) {
         CompoundTag entry = archive.getCompound(archiveIndex);
         int count = Math.max(1, entry.getInt("count"));
         double avgHp = entry.getDouble("hp") / count;
         double avgEp = entry.getDouble("ep") / count;
         int reviveCount = Math.max(0, activeTag.getInt("revive_count"));
         double exponential = Math.pow((Double)cfg().reviveExponentBase.get(), reviveCount);
         return (Double)cfg().reviveBaseCost.get() + (avgHp * (Double)cfg().reviveHpFactor.get() + avgEp * (Double)cfg().reviveEpFactor.get()) * exponential;
      } else {
         return (Double)cfg().reviveBaseCost.get();
      }
   }

   public boolean canAfford(ServerPlayer player, double amount) {
      try {
         IExistence existence = TensuraStorages.getExistenceFrom(player);
         return existence != null && existence.getMagicule() >= amount;
      } catch (Throwable ignored) {
         return false;
      }
   }

   public boolean spendMagicule(ServerPlayer player, double amount) {
      try {
         IExistence existence = TensuraStorages.getExistenceFrom(player);
         if (existence == null) {
            return false;
         }

         double current = existence.getMagicule();
         if (current < amount) {
            return false;
         }

         existence.setMagicule(current - amount);
         existence.markDirty();
         return true;
      } catch (Throwable ignored) {
         return false;
      }
   }

   private void giveMagicule(ServerPlayer player, double amount) {
      try {
         IExistence existence = TensuraStorages.getExistenceFrom(player);
         existence.setMagicule(existence.getMagicule() + amount);
         existence.markDirty();
      } catch (Throwable var5) {
      }
   }

   private double getMagicule(ServerPlayer player) {
      try {
         IExistence existence = TensuraStorages.getExistenceFrom(player);
         return existence == null ? 0.0 : existence.getMagicule();
      } catch (Throwable ignored) {
         return 0.0;
      }
   }

   public void clearActiveSummons(ServerPlayer player) {
      if (player.level() instanceof ServerLevel level) {
         ListTag var10 = root(player).getList("active", 10);

         for (int i = 0; i < var10.size(); i++) {
            CompoundTag tag = var10.getCompound(i);
            if (tag.hasUUID("uuid") && level.getEntity(tag.getUUID("uuid")) instanceof LivingEntity living && isOwnedNaberiusSummon(player, living)) {
               try {
                  living.discard();
               } catch (Throwable var9) {
               }
            }
         }

         this.clearDomainBuffs(player, level);
         root(player).put("active", new ListTag());
         player.displayClientMessage(Component.literal("Naberius scatters its army into dust.").withStyle(ChatFormatting.GRAY), true);
      }
   }

   private static boolean isOwnedNaberiusSummon(ServerPlayer owner, LivingEntity entity) {
      CompoundTag data = entity.getPersistentData();
      return data.getBoolean("naberius_active") && data.hasUUID("owner_uuid") && owner.getUUID().equals(data.getUUID("owner_uuid"));
   }

   public static boolean isNaberiusSummon(LivingEntity entity) {
      return entity.getPersistentData().getBoolean("naberius_active") && entity.getPersistentData().hasUUID("owner_uuid");
   }

   public static UUID getNaberiusOwnerUUID(LivingEntity entity) {
      CompoundTag data = entity.getPersistentData();
      return data.hasUUID("owner_uuid") ? data.getUUID("owner_uuid") : null;
   }

   public void commandActiveSummons(ServerPlayer owner, LivingEntity target) {
      if (target != null && target != owner && target.isAlive()) {
         if (owner.level() instanceof ServerLevel level) {
            if (!SubordinateHelper.isSubordinate(owner, target)) {
               if (!isOwnedNaberiusSummon(owner, target)) {
                  root(owner).putUUID("aggro_uuid", target.getUUID());
                  ListTag active = root(owner).getList("active", 10);

                  for (int i = 0; i < active.size(); i++) {
                     CompoundTag tag = active.getCompound(i).copy();
                     tag.putUUID("aggro_uuid", target.getUUID());
                     if (!tag.getBoolean("dead")
                        && tag.hasUUID("uuid")
                        && level.getEntity(tag.getUUID("uuid")) instanceof Mob mob
                        && mob.isAlive()
                        && isOwnedNaberiusSummon(owner, mob)) {
                        mob.setTarget(target);
                        mob.setLastHurtByMob(target);
                        mob.getNavigation().moveTo(target, (Double)cfg().summonCommandMoveSpeed.get());
                     }

                     active.set(i, tag);
                  }

                  root(owner).put("active", active);
               }
            }
         }
      }
   }

   private void retargetSummonsNearOwner(ServerPlayer owner, ServerLevel level) {
      LivingEntity target = this.getSavedAggroTarget(owner);
      if (target != null
         && target.isAlive()
         && !(target.distanceTo(owner) > (Double)cfg().summonRetargetRange.get())
         && !SubordinateHelper.isSubordinate(owner, target)) {
         this.commandActiveSummons(owner, target);
      }
   }

   private LivingEntity getSavedAggroTarget(ServerPlayer owner) {
      if (owner.level() instanceof ServerLevel level) {
         CompoundTag root = root(owner);
         if (!root.hasUUID("aggro_uuid")) {
            return owner.getLastHurtByMob();
         } else {
            return level.getEntity(root.getUUID("aggro_uuid")) instanceof LivingEntity living && living.isAlive() ? living : owner.getLastHurtByMob();
         }
      } else {
         return null;
      }
   }

   private LivingEntity getAggroFromTag(ServerPlayer owner, ServerLevel level, CompoundTag tag) {
      return tag.hasUUID("aggro_uuid") && level.getEntity(tag.getUUID("aggro_uuid")) instanceof LivingEntity living && living.isAlive()
         ? living
         : this.getSavedAggroTarget(owner);
   }

   private void safeTickDomain(ServerPlayer player, ServerLevel level) {
      try {
         this.tickDomain(player, level);
      } catch (Throwable ignored) {
         CompoundTag root = root(player);
         root.putBoolean("domain", false);
         root.putInt("domain_ticks", 0);
         root.putInt("domain_power", 0);
         root.putInt("domain_radius", 0);
         this.clearDomainBuffs(player, level);
      }
   }

   private void tickDomain(ServerPlayer player, ServerLevel level) {
      CompoundTag root = root(player);
      int charge = Math.max(1, root.getInt("domain_power"));
      int radius = Math.max((Integer)cfg().domainBaseRadius.get(), root.getInt("domain_radius"));
      AABB box = player.getBoundingBox().inflate(radius);

      for (LivingEntity target : level.getEntitiesOfClass(
         LivingEntity.class, box, e -> e != player && e.isAlive() && !SubordinateHelper.isSubordinate(player, e)
      )) {
         try {
            int amp = Math.min(
               (Integer)cfg().domainOrganDecayMaxAmplifier.get(),
               (Integer)cfg().domainOrganDecayBaseAmplifier.get() + charge / (Integer)cfg().domainOrganDecayPowerDivisor.get()
            );
            target.addEffect(
               new MobEffectInstance(
                  ModMobEffects.getReference(ModMobEffects.ORGAN_DECAY), (Integer)cfg().domainOrganDecayDurationTicks.get(), amp, false, true, true
               )
            );
            IExistence existence = TensuraStorages.getExistenceFrom(target);
            double spiritual = existence.getSpiritualHealth();
            if (spiritual > 0.0) {
               double flatDrain = (Double)cfg().domainSpiritualFlatDrainPerPower.get() * charge;
               double percentDrain = spiritual * ((Double)cfg().domainSpiritualPercentBase.get() + (Double)cfg().domainSpiritualPercentPerPower.get() * charge);
               existence.setSpiritualHealth(Math.max(0.0, spiritual - Math.max(flatDrain, percentDrain)));
               existence.markDirty();
            }
         } catch (Throwable var17) {
         }
      }
   }

   private void buffSummonsForDomain(ServerPlayer owner, ServerLevel level, int power) {
      ListTag active = root(owner).getList("active", 10);

      for (int i = 0; i < active.size(); i++) {
         CompoundTag tag = active.getCompound(i);
         if (tag.hasUUID("uuid")
            && level.getEntity(tag.getUUID("uuid")) instanceof LivingEntity summon
            && summon.isAlive()
            && isOwnedNaberiusSummon(owner, summon)) {
            double hpMul = Math.min((Double)cfg().domainSummonHpCap.get(), (Double)cfg().domainSummonHpPerPower.get() * power);
            double dmgMul = Math.min((Double)cfg().domainSummonDamageCap.get(), (Double)cfg().domainSummonDamagePerPower.get() * power);
            double scaleAdd = Math.min((Double)cfg().domainSummonScaleCap.get(), (Double)cfg().domainSummonScalePerPower.get() * power);
            AttributeHelper.addPermanentAttribute(summon, Attributes.MAX_HEALTH, DOMAIN_HP_ID, hpMul, Operation.ADD_MULTIPLIED_BASE);
            AttributeHelper.addPermanentAttribute(summon, Attributes.ATTACK_DAMAGE, DOMAIN_DMG_ID, dmgMul, Operation.ADD_MULTIPLIED_BASE);
            AttributeHelper.addPermanentAttribute(summon, Attributes.SCALE, DOMAIN_SCALE_ID, scaleAdd, Operation.ADD_VALUE);
            summon.setHealth(Math.min(summon.getHealth() + (float)((Double)cfg().domainSummonHealPerPower.get() * power), summon.getMaxHealth()));
            summon.addEffect(
               new MobEffectInstance(
                  TensuraMobEffects.getReference(TensuraMobEffects.HAKI_COAT),
                  (Integer)cfg().domainSummonHakiDurationTicks.get(),
                  Math.min((Integer)cfg().domainSummonHakiMaxAmplifier.get(), power / (Integer)cfg().domainSummonHakiPowerDivisor.get()),
                  false,
                  false,
                  true
               )
            );
            this.spawnNaberiusAura(level, summon);
         }
      }
   }

   private void clearDomainBuffs(ServerPlayer owner, ServerLevel level) {
      ListTag active = root(owner).getList("active", 10);

      for (int i = 0; i < active.size(); i++) {
         CompoundTag tag = active.getCompound(i);
         if (tag.hasUUID("uuid") && level.getEntity(tag.getUUID("uuid")) instanceof LivingEntity summon) {
            AttributeHelper.removeAttribute(summon, Attributes.MAX_HEALTH, DOMAIN_HP_ID);
            AttributeHelper.removeAttribute(summon, Attributes.ATTACK_DAMAGE, DOMAIN_DMG_ID);
            AttributeHelper.removeAttribute(summon, Attributes.SCALE, DOMAIN_SCALE_ID);
            if (summon.getHealth() > summon.getMaxHealth()) {
               summon.setHealth(summon.getMaxHealth());
            }
         }
      }
   }

   private void castLifeDrain(ServerPlayer player, ManasSkillInstance instance) {
      LivingEntity target = ObjectSelectionHelper.getTargetingEntity(player, (Double)cfg().lifeDrainRange.get(), false);
      if (target != null && !EnergyHelper.isOutOfEnergy(player, instance, 5)) {
         try {
            target.hurt(player.damageSources().magic(), ((Double)cfg().lifeDrainDamage.get()).floatValue());
            target.addEffect(
               new MobEffectInstance(
                  ModMobEffects.getReference(ModMobEffects.LIFE_SEIZURE),
                  (Integer)cfg().lifeDrainEffectTicks.get(),
                  (Integer)cfg().lifeDrainEffectAmplifier.get(),
                  false,
                  true,
                  true
               )
            );
            this.healSummons(player, (Double)cfg().lifeDrainSummonHealRatio.get());
            double stolen = Math.max(
               (Double)cfg().lifeDrainMinimumStolenMagicules.get(), EnergyHelper.getMaxEP(target) * (Double)cfg().lifeDrainTargetEpStealRatio.get()
            );
            IExistence ex = TensuraStorages.getExistenceFrom(player);
            ex.setMagicule(ex.getMagicule() + stolen);
            ex.markDirty();
            if (player.level() instanceof ServerLevel level) {
               this.spawnLifeDrainVfx(level, player, target);
            }

            this.commandActiveSummons(player, target);
            player.displayClientMessage(Component.literal("Naberius siphons vigor from living flesh.").withStyle(ChatFormatting.GREEN), true);
            instance.setCoolDown((Integer)cfg().lifeDrainCooldownTicks.get(), 5);
         } catch (Throwable var9) {
         }
      }
   }

   private void stealAllSummons(ServerPlayer player, ManasSkillInstance instance) {
      if (player.level() instanceof ServerLevel level) {
         if (!EnergyHelper.isOutOfEnergy(player, instance, 5)) {
            AABB box = player.getBoundingBox().inflate((Double)cfg().stealSummonsRadius.get());
            List<LivingEntity> targets = level.getEntitiesOfClass(
               LivingEntity.class, box, entity -> entity != player && entity.isAlive() && !(entity instanceof Player)
            );
            int stolen = 0;

            for (LivingEntity target : targets) {
               if (!isOwnedNaberiusSummon(player, target)) {
                  boolean steal = false;

                  try {
                     IExistence existence = TensuraStorages.getExistenceFrom(target);
                     if (existence.getSummoner() != null
                        || existence.getTemporaryOwner() != null
                        || existence.getPermanentOwner() != null
                        || existence.getSummonedSecond() > 0) {
                        steal = true;
                     }
                  } catch (Throwable var11) {
                  }

                  if (SubordinateHelper.getSubordinateOwner(target) != null) {
                     steal = true;
                  }

                  if (target instanceof ISubordinate) {
                     steal = true;
                  }

                  if (target instanceof TamableAnimal) {
                     steal = true;
                  }

                  if (steal) {
                     this.forceStealSummon(player, target);
                     this.spawnStealSummonVfx(level, player, target);
                     stolen++;
                  }
               }
            }

            if (stolen > 0) {
               instance.setCoolDown((Integer)cfg().stealSummonsCooldownTicks.get(), 5);
               level.playSound(null, player.blockPosition(), SoundEvents.EVOKER_PREPARE_SUMMON, SoundSource.PLAYERS, 1.6F, 0.65F);
               player.displayClientMessage(Component.literal("Naberius usurps " + stolen + " summon(s).").withStyle(ChatFormatting.DARK_GREEN), true);
            } else {
               player.displayClientMessage(Component.literal("No foreign summons answered Naberius.").withStyle(ChatFormatting.RED), true);
               level.playSound(null, player.blockPosition(), SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.8F, 0.55F);
            }
         }
      }
   }

   private void forceStealSummon(ServerPlayer owner, LivingEntity target) {
      try {
         IExistence existence = TensuraStorages.getExistenceFrom(target);
         existence.setSummoner(owner.getUUID());
         existence.setTemporaryOwner(owner.getUUID());
         existence.setPermanentOwner(owner.getUUID());
         existence.setSummonedSecond(Math.max((Integer)cfg().stealSummonsSeconds.get(), existence.getSummonedSecond()));
         existence.markDirty();
      } catch (Throwable var8) {
      }

      target.getPersistentData().putUUID("owner_uuid", owner.getUUID());
      target.getPersistentData().putBoolean("naberius_active", true);
      if (target instanceof TamableAnimal tamable) {
         tamable.tame(owner);
         tamable.setOwnerUUID(owner.getUUID());
      }

      if (target instanceof ISubordinate subordinate) {
         try {
            subordinate.tame(owner);
         } catch (Throwable var7) {
         }
      }

      if (target instanceof Mob mob) {
         SubordinateHelper.removeTarget(mob);
         mob.setTarget(null);
         mob.setLastHurtByMob(null);
         mob.setPersistenceRequired();
      }

      target.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.MIND_CONTROL));
      target.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.FEAR));
      target.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.INSANITY));
      ListTag active = root(owner).getList("active", 10);
      boolean already = false;

      for (int i = 0; i < active.size(); i++) {
         CompoundTag tag = active.getCompound(i);
         if (tag.hasUUID("uuid") && tag.getUUID("uuid").equals(target.getUUID())) {
            already = true;
            break;
         }
      }

      if (!already && active.size() < (Integer)cfg().maxActiveSummons.get()) {
         CompoundTag tag = new CompoundTag();
         tag.putUUID("uuid", target.getUUID());
         tag.putInt("archive_index", -1);
         tag.putBoolean("dead", false);
         tag.putInt("revive_count", 0);
         tag.putLong("last_revive_tick", owner.level().getGameTime());
         active.add(tag);
         root(owner).put("active", active);
      }
   }

   private void castOrganFailure(ServerPlayer player, ManasSkillInstance instance) {
      LivingEntity target = ObjectSelectionHelper.getTargetingEntity(player, (Double)cfg().organFailureRange.get(), false);
      if (target != null && !EnergyHelper.isOutOfEnergy(player, instance, 6)) {
         try {
            target.addEffect(
               new MobEffectInstance(
                  ModMobEffects.getReference(ModMobEffects.ORGAN_DECAY),
                  (Integer)cfg().organFailureDurationTicks.get(),
                  (Integer)cfg().organFailureOrganDecayAmplifier.get(),
                  false,
                  true,
                  true
               )
            );
            target.addEffect(
               new MobEffectInstance(
                  TensuraMobEffects.getReference(TensuraMobEffects.BURDEN),
                  (Integer)cfg().organFailureDurationTicks.get(),
                  (Integer)cfg().organFailureBurdenAmplifier.get(),
                  false,
                  true,
                  true
               )
            );
            target.addEffect(
               new MobEffectInstance(
                  TensuraMobEffects.getReference(TensuraMobEffects.FRAGILITY),
                  (Integer)cfg().organFailureDurationTicks.get(),
                  (Integer)cfg().organFailureFragilityAmplifier.get(),
                  false,
                  true,
                  true
               )
            );
            if (player.level() instanceof ServerLevel level) {
               this.spawnOrganFailureVfx(level, target);
            }

            this.commandActiveSummons(player, target);
            player.displayClientMessage(Component.literal("Naberius names a body unfit to live.").withStyle(ChatFormatting.DARK_GREEN), true);
            instance.setCoolDown((Integer)cfg().organFailureCooldownTicks.get(), 6);
         } catch (Throwable var6) {
         }
      }
   }

   private void castHinataFusion(ServerPlayer player, ManasSkillInstance instance) {
      if (player.level() instanceof ServerLevel level) {
         double var30 = this.getMagiculeCost(player, instance, 7);
         if (!this.spendMagicule(player, var30)) {
            player.displayClientMessage(Component.literal("Hinata Fusion needs " + (long)var30 + " MP.").withStyle(ChatFormatting.RED), true);
         } else {
            ListTag archive = root(player).getList("archive", 10);
            if (archive.isEmpty()) {
               this.giveMagicule(player, var30);
               player.displayClientMessage(Component.literal("The archive is barren.").withStyle(ChatFormatting.RED), true);
            } else {
               this.spawnHinataFusionStartVfx(level, player);
               LivingEntity fusion = this.createHinataOrFallback(level);
               boolean fallback = fusion.getType() == EntityType.ZOMBIE;
               double hp = 0.0;
               double ep = 0.0;
               double shp = 0.0;
               int sacrifices = 0;
               int archiveSkillCount = this.countArchivedSkillIds(archive);
               int ownerSkillCount = this.countOwnerSkillIds(player);
               int skillCount = this.countFusionInputSkillIds(player, archive);

               for (int i = 0; i < archive.size(); i++) {
                  CompoundTag entry = archive.getCompound(i);
                  hp += entry.getDouble("hp");
                  ep += entry.getDouble("ep");
                  shp += entry.getDouble("shp");
                  sacrifices += Math.max(1, entry.getInt("count"));
               }

               try {
                  fusion.moveTo(
                     player.getX() + player.getLookAngle().x * (Double)cfg().fusionSpawnDistance.get(),
                     player.getY(),
                     player.getZ() + player.getLookAngle().z * (Double)cfg().fusionSpawnDistance.get(),
                     player.getYRot(),
                     0.0F
                  );
                  AttributeInstance maxHp = fusion.getAttribute(Attributes.MAX_HEALTH);
                  if (maxHp != null) {
                     double value = Math.max(
                        fallback ? (Double)cfg().fusionFallbackMinHealth.get() : (Double)cfg().fusionHinataMinHealth.get(),
                        hp * fallback ? (Double)cfg().fusionFallbackHpMultiplier.get() : (Double)cfg().fusionHinataHpMultiplier.get()
                     );
                     maxHp.setBaseValue(value);
                     fusion.setHealth((float)Math.min(value, Float.MAX_VALUE));
                  }

                  AttributeInstance damage = fusion.getAttribute(Attributes.ATTACK_DAMAGE);
                  if (damage != null) {
                     double value = Math.max(
                        fallback ? (Double)cfg().fusionFallbackMinDamage.get() : (Double)cfg().fusionHinataMinDamage.get(),
                        (Double)cfg().fusionDamageBase.get()
                           + ep * fallback ? (Double)cfg().fusionFallbackEpDamageMultiplier.get() : (Double)cfg().fusionHinataEpDamageMultiplier.get()
                     );
                     damage.setBaseValue(value);
                  }

                  AttributeInstance armor = fusion.getAttribute(Attributes.ARMOR);
                  if (armor != null) {
                     armor.setBaseValue(
                        Math.max(armor.getBaseValue(), (Double)cfg().fusionArmorBase.get() + sacrifices * (Double)cfg().fusionArmorPerSacrifice.get())
                     );
                  }

                  AttributeInstance speed = fusion.getAttribute(Attributes.MOVEMENT_SPEED);
                  if (speed != null) {
                     speed.setBaseValue(
                        Math.max(speed.getBaseValue(), fallback ? (Double)cfg().fusionFallbackSpeed.get() : (Double)cfg().fusionHinataSpeed.get())
                     );
                  }

                  AttributeInstance follow = fusion.getAttribute(Attributes.FOLLOW_RANGE);
                  if (follow != null) {
                     follow.setBaseValue(Math.max(follow.getBaseValue(), (Double)cfg().fusionFollowRange.get()));
                  }

                  AttributeInstance scale = fusion.getAttribute(Attributes.SCALE);
                  if (scale != null) {
                     scale.setBaseValue(
                        Math.max(scale.getBaseValue(), fallback ? (Double)cfg().fusionFallbackScale.get() : (Double)cfg().fusionHinataScale.get())
                     );
                  }

                  AttributeHelper.addPermanentAttribute(
                     fusion, TensuraAttributes.RESISTANCE_DEGRADATION, MASTERY_PIERCE, (Double)cfg().fusionResistanceDegradation.get(), Operation.ADD_VALUE
                  );
                  AttributeHelper.addPermanentAttribute(
                     fusion, TensuraAttributes.PRESENCE_SENSE, VISION_ID, (Double)cfg().fusionPresenceSense.get(), Operation.ADD_VALUE
                  );
                  IExistence existence = TensuraStorages.getExistenceFrom(fusion);
                  existence.setSummoner(player.getUUID());
                  existence.setSummonedSecond((Integer)cfg().fusionSummonedSeconds.get());
                  existence.setTemporaryOwner(player.getUUID());
                  existence.setPermanentOwner(player.getUUID());
                  existence.setSpiritualHealth(Math.max((Double)cfg().fusionMinimumSpiritualHealth.get(), shp));
                  existence.markDirty();
                  if (fusion instanceof TamableAnimal tamable) {
                     tamable.tame(player);
                     tamable.setOwnerUUID(player.getUUID());
                  }

                  if (fusion instanceof Mob mob) {
                     mob.setPersistenceRequired();
                     LivingEntity aggro = this.getSavedAggroTarget(player);
                     if (aggro != null) {
                        mob.setTarget(aggro);
                        mob.setLastHurtByMob(aggro);
                     }
                  }

                  int learned = this.copyArchivedSkillsToEntity(player, fusion, archive);
                  learned += this.learnCoreResistancesAndNullifications(fusion);
                  fusion.getPersistentData().putUUID("owner_uuid", player.getUUID());
                  fusion.getPersistentData().putBoolean("naberius_active", true);
                  fusion.getPersistentData().putBoolean("hinata_fusion_entity", true);
                  fusion.setCustomName(Component.literal(fallback ? "God-Corpse of False Life" : "Hinata of False Life"));
                  fusion.setCustomNameVisible(true);
                  level.addFreshEntity(fusion);
                  this.trackActive(player, fusion, -1);
                  this.spawnHinataFusionCompleteVfx(level, player, fusion);
                  root(player).put("archive", new ListTag());
                  instance.setCoolDown((Integer)cfg().hinataFusionCooldownTicks.get(), 7);
                  player.displayClientMessage(
                     Component.literal(
                           (fallback ? "Hinata failed, so Naberius shaped a god-corpse." : "Naberius crowns the archive with Hinata of False Life.")
                              + " Skills absorbed: "
                              + learned
                              + "/"
                              + skillCount
                              + " | owner: "
                              + ownerSkillCount
                              + " | archive: "
                              + archiveSkillCount
                        )
                        .withStyle(ChatFormatting.GREEN),
                     false
                  );
               } catch (Throwable ignored) {
                  this.giveMagicule(player, var30);

                  try {
                     fusion.discard();
                  } catch (Throwable var28) {
                  }

                  player.displayClientMessage(Component.literal("The fusion rots before completion.").withStyle(ChatFormatting.RED), true);
               }
            }
         }
      }
   }

   private LivingEntity createHinataOrFallback(ServerLevel level) {
      try {
         if (((EntityType)HumanEntityTypes.HINATA_SAKAGUCHI.get()).create(level) instanceof LivingEntity living) {
            return living;
         }
      } catch (Throwable var4) {
      }

      LivingEntity fallback = (LivingEntity)EntityType.ZOMBIE.create(level);
      if (fallback == null) {
         throw new IllegalStateException("Naberius fallback failed");
      } else {
         return fallback;
      }
   }

   private int applyStoredSkillsToEntity(LivingEntity target, CompoundTag entry) {
      if (!entry.contains("skills", 9)) {
         return 0;
      }

      ListTag skills = entry.getList("skills", 8);
      List<ResourceLocation> ids = new ArrayList<>();

      for (int i = 0; i < skills.size(); i++) {
         try {
            ResourceLocation id = ResourceLocation.parse(skills.getString(i));
            if (!ids.contains(id)) {
               ids.add(id);
            }
         } catch (Throwable var7) {
         }
      }

      return this.learnSkillIds(target, ids);
   }

   private int copyArchivedSkillsToEntity(ServerPlayer owner, LivingEntity target, ListTag archive) {
      List<ResourceLocation> toLearn = new ArrayList<>();

      try {
         Skills ownerSkills = SkillAPI.getSkillsFrom(owner);
         if (ownerSkills != null) {
            for (ManasSkillInstance skillInstance : ownerSkills.getLearnedSkills()) {
               if (skillInstance != null) {
                  ManasSkill skill = skillInstance.getSkill();
                  if (skill != null && !(skill instanceof NaberiusSkill)) {
                     ResourceLocation id = this.getSkillId(skillInstance, skill);
                     if (id != null && !toLearn.contains(id)) {
                        toLearn.add(id);
                     }
                  }
               }
            }
         }
      } catch (Throwable var11) {
      }

      for (int i = 0; i < archive.size(); i++) {
         CompoundTag entry = archive.getCompound(i);
         if (entry.contains("skills", 9)) {
            ListTag skills = entry.getList("skills", 8);

            for (int s = 0; s < skills.size(); s++) {
               try {
                  ResourceLocation id = ResourceLocation.parse(skills.getString(s));
                  if (!toLearn.contains(id)) {
                     toLearn.add(id);
                  }
               } catch (Throwable var10) {
               }
            }
         }
      }

      return this.learnSkillIds(target, toLearn);
   }

   private int countOwnerSkillIds(ServerPlayer owner) {
      int count = 0;

      try {
         Skills ownerSkills = SkillAPI.getSkillsFrom(owner);
         if (ownerSkills == null) {
            return 0;
         }

         List<ResourceLocation> unique = new ArrayList<>();

         for (ManasSkillInstance skillInstance : ownerSkills.getLearnedSkills()) {
            if (skillInstance != null) {
               ManasSkill skill = skillInstance.getSkill();
               if (skill != null && !(skill instanceof NaberiusSkill)) {
                  ResourceLocation id = this.getSkillId(skillInstance, skill);
                  if (id != null && !unique.contains(id)) {
                     unique.add(id);
                  }
               }
            }
         }

         count = unique.size();
      } catch (Throwable var9) {
      }

      return count;
   }

   private int learnSkillIds(LivingEntity target, List<ResourceLocation> ids) {
      int learned = 0;

      try {
         Skills targetSkills = SkillAPI.getSkillsFrom(target);
         if (targetSkills == null) {
            return 0;
         }

         for (ResourceLocation id : ids) {
            try {
               ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(id);
               if (skill != null && !(skill instanceof NaberiusSkill)) {
                  if (targetSkills.getSkill(skill).isPresent()) {
                     ManasSkillInstance stored = (ManasSkillInstance)targetSkills.getSkill(skill).orElse(null);
                     if (stored != null) {
                        stored.setMastery(stored.getMaxMastery());
                        if (stored.canBeToggled(target)) {
                           stored.setToggled(true);

                           try {
                              stored.onToggleOn(target);
                           } catch (Throwable var15) {
                           }
                        }

                        try {
                           int modes = Math.max(1, Math.min(8, skill.getModes(stored)));

                           for (int mode = 0; mode < modes; mode++) {
                              stored.setCoolDown(0, mode);
                           }
                        } catch (Throwable var16) {
                        }

                        stored.markDirty();
                        learned++;
                     }
                  } else {
                     ManasSkillInstance newInstance = skill.createDefaultInstance();
                     newInstance.setMastery(newInstance.getMaxMastery());
                     if (newInstance.canBeToggled(target)) {
                        newInstance.setToggled(true);
                     }

                     try {
                        int modes = Math.max(1, Math.min(8, skill.getModes(newInstance)));

                        for (int mode = 0; mode < modes; mode++) {
                           newInstance.setCoolDown(0, mode);
                        }
                     } catch (Throwable var18) {
                     }

                     boolean ok = false;

                     try {
                        ok = SkillHelper.learnSkill(target, newInstance);
                     } catch (Throwable var14) {
                     }

                     if (ok) {
                        ManasSkillInstance stored = (ManasSkillInstance)targetSkills.getSkill(skill).orElse(null);
                        if (stored != null) {
                           stored.setMastery(stored.getMaxMastery());
                           if (stored.canBeToggled(target)) {
                              stored.setToggled(true);

                              try {
                                 stored.onToggleOn(target);
                              } catch (Throwable var13) {
                              }
                           }

                           try {
                              int modes = Math.max(1, Math.min(8, skill.getModes(stored)));

                              for (int mode = 0; mode < modes; mode++) {
                                 stored.setCoolDown(0, mode);
                              }
                           } catch (Throwable var17) {
                           }

                           stored.markDirty();
                        }

                        learned++;
                     }
                  }
               }
            } catch (Throwable var19) {
            }
         }
      } catch (Throwable var20) {
      }

      return learned;
   }

   private ResourceLocation getSkillId(ManasSkillInstance skillInstance, ManasSkill skill) {
      try {
         ResourceLocation id = skillInstance.getSkillId();
         if (id != null) {
            return id;
         }
      } catch (Throwable var6) {
      }

      try {
         ResourceLocation id = SkillAPI.getSkillRegistry().getId(skill);
         if (id != null) {
            return id;
         }
      } catch (Throwable var5) {
      }

      try {
         return skill.getRegistryName();
      } catch (Throwable ignored) {
         return null;
      }
   }

   private void healSummons(ServerPlayer owner, double pct) {
      if (owner.level() instanceof ServerLevel level) {
         ListTag var10 = root(owner).getList("active", 10);

         for (int i = 0; i < var10.size(); i++) {
            CompoundTag tag = var10.getCompound(i);
            if (tag.hasUUID("uuid")
               && level.getEntity(tag.getUUID("uuid")) instanceof LivingEntity summon
               && summon.isAlive()
               && isOwnedNaberiusSummon(owner, summon)) {
               summon.heal((float)(summon.getMaxHealth() * pct));
               this.spawnNaberiusAura(level, summon);
            }
         }
      }
   }

   public void captureKill(ServerPlayer owner, LivingEntity victim) {
      try {
         if (isOwnedNaberiusSummon(owner, victim)) {
            return;
         }

         CompoundTag root = root(owner);
         ListTag archive = root.getList("archive", 10);
         String typeStr = EntityType.getKey(victim.getType()).toString();
         boolean isPlayer = victim instanceof ServerPlayer;
         CompoundTag existing = null;
         int existingIndex = -1;

         for (int i = 0; i < archive.size(); i++) {
            CompoundTag entry = archive.getCompound(i);
            if (typeStr.equals(entry.getString("type")) && entry.getBoolean("is_player") == isPlayer) {
               existing = entry.copy();
               existingIndex = i;
               break;
            }
         }

         double hp = Math.max(1.0, victim.getMaxHealth());
         double ep = Math.max(1.0, EnergyHelper.getMaxEP(victim));
         double shp = Math.max(0.0, TensuraStorages.getExistenceFrom(victim).getSpiritualHealth());
         CompoundTag victimData = new CompoundTag();
         victim.saveWithoutId(victimData);
         if (!victimData.contains("id", 8)) {
            victimData.putString("id", typeStr);
         }

         victimData.remove("UUID");
         ListTag skillList = this.captureSkillList(victim);
         if (existing == null) {
            if (archive.size() >= (Integer)cfg().maxStoredSummons.get()) {
               archive.remove(0);
            }

            existing = new CompoundTag();
            existing.putString("type", typeStr);
            existing.putString("name", victim.getName().getString());
            existing.putDouble("hp", hp);
            existing.putDouble("ep", ep);
            existing.putDouble("shp", shp);
            existing.putInt("count", 1);
            existing.putBoolean("selected", false);
            existing.putBoolean("is_player", isPlayer);
            if (!isPlayer) {
               existing.put("data", victimData);
            }
         } else {
            existing.putString("name", victim.getName().getString());
            existing.putDouble("hp", existing.getDouble("hp") + hp);
            existing.putDouble("ep", existing.getDouble("ep") + ep);
            existing.putDouble("shp", existing.getDouble("shp") + shp);
            existing.putInt("count", Math.max(1, existing.getInt("count")) + 1);
            if (!isPlayer) {
               existing.put("data", victimData);
            }
         }

         if (isPlayer && victim instanceof ServerPlayer sp) {
            existing.putUUID("player_uuid", sp.getUUID());
            existing.putString("player_name", sp.getGameProfile().getName());
            existing.putString("name", sp.getGameProfile().getName());
         }

         this.mergeSkillLists(existing, skillList);
         int skillCount = existing.getList("skills", 8).size();
         if (existingIndex >= 0) {
            archive.set(existingIndex, existing);
         } else {
            archive.add(existing);
         }

         root.put("archive", archive);
         owner.displayClientMessage(
            Component.literal("Naberius records " + victim.getName().getString() + " x" + existing.getInt("count") + " | skills " + skillCount)
               .withStyle(ChatFormatting.DARK_GREEN),
            true
         );
      } catch (Throwable var18) {
      }
   }

   private ListTag captureSkillList(LivingEntity victim) {
      ListTag out = new ListTag();

      try {
         Skills skills = SkillAPI.getSkillsFrom(victim);
         if (skills == null) {
            return out;
         }

         for (ManasSkillInstance inst : skills.getLearnedSkills()) {
            if (inst != null) {
               ManasSkill skill = inst.getSkill();
               if (skill != null && !(skill instanceof NaberiusSkill)) {
                  ResourceLocation id = this.getSkillId(inst, skill);
                  if (id != null && !this.containsString(out, id.toString())) {
                     out.add(StringTag.valueOf(id.toString()));
                  }
               }
            }
         }
      } catch (Throwable var8) {
      }

      return out;
   }

   private void mergeSkillLists(CompoundTag entry, ListTag incoming) {
      ListTag merged = entry.contains("skills", 9) ? entry.getList("skills", 8).copy() : new ListTag();

      for (int i = 0; i < incoming.size(); i++) {
         String value = incoming.getString(i);
         if (!this.containsString(merged, value)) {
            merged.add(StringTag.valueOf(value));
         }
      }

      entry.put("skills", merged);
   }

   private boolean containsString(ListTag list, String value) {
      for (int i = 0; i < list.size(); i++) {
         if (value.equals(list.getString(i))) {
            return true;
         }
      }

      return false;
   }

   private int countArchivedSkillIds(ListTag archive) {
      List<String> unique = new ArrayList<>();

      for (int i = 0; i < archive.size(); i++) {
         CompoundTag entry = archive.getCompound(i);
         if (entry.contains("skills", 9)) {
            ListTag skills = entry.getList("skills", 8);

            for (int s = 0; s < skills.size(); s++) {
               String value = skills.getString(s);
               if (!unique.contains(value)) {
                  unique.add(value);
               }
            }
         }
      }

      return unique.size();
   }

   public static void toggleSelected(ServerPlayer player, int index) {
      ListTag archive = root(player).getList("archive", 10);
      if (index >= 0 && index < archive.size()) {
         CompoundTag entry = archive.getCompound(index).copy();
         boolean select = !entry.getBoolean("selected");
         if (entry.getBoolean("is_player") && select) {
            for (int i = 0; i < archive.size(); i++) {
               CompoundTag other = archive.getCompound(i).copy();
               if (other.getBoolean("is_player")) {
                  other.putBoolean("selected", false);
                  archive.set(i, other);
               }
            }
         }

         entry.putBoolean("selected", select);
         archive.set(index, entry);
         root(player).put("archive", archive);
      }
   }

   public static void reduceStoredCount(ServerPlayer player, int index) {
      ListTag archive = root(player).getList("archive", 10);
      if (index >= 0 && index < archive.size()) {
         CompoundTag entry = archive.getCompound(index).copy();
         int count = Math.max(1, entry.getInt("count"));
         if (count <= 1) {
            archive.remove(index);
         } else {
            double ratio = (double)(count - 1) / count;
            entry.putInt("count", count - 1);
            entry.putDouble("hp", entry.getDouble("hp") * ratio);
            entry.putDouble("ep", entry.getDouble("ep") * ratio);
            entry.putDouble("shp", entry.getDouble("shp") * ratio);
            archive.set(index, entry);
         }

         root(player).put("archive", archive);
      }
   }

   public static void deleteSelected(ServerPlayer player, int index) {
      ListTag archive = root(player).getList("archive", 10);
      if (index >= 0 && index < archive.size()) {
         archive.remove(index);
         root(player).put("archive", archive);
      }
   }

   public boolean tryNegateLethal(ManasSkillInstance instance, ServerPlayer player, float incomingDamage) {
      if (!instance.isMastered(player)) {
         return false;
      }

      if (!instance.isToggled()) {
         return false;
      }

      if (incomingDamage < player.getHealth()) {
         return false;
      }

      CompoundTag root = root(player);
      if (root.getInt("lethal_cd") > 0) {
         return false;
      }

      root.putInt("lethal_cd", (Integer)cfg().lethalSaveCooldownTicks.get());
      player.setHealth(Math.max(1.0F, player.getMaxHealth() * ((Double)cfg().lethalSaveHealthRatio.get()).floatValue()));
      player.invulnerableTime = (Integer)cfg().lethalSaveInvulnerabilityTicks.get();
      player.addEffect(
         new MobEffectInstance(
            TensuraMobEffects.getReference(TensuraMobEffects.HAKI_COAT),
            (Integer)cfg().lethalSaveInvulnerabilityTicks.get(),
            (Integer)cfg().lethalSaveHakiCoatAmplifier.get(),
            false,
            false,
            true
         )
      );
      player.addEffect(
         new MobEffectInstance(
            TensuraMobEffects.getReference(TensuraMobEffects.INSTANT_REGENERATION),
            (Integer)cfg().lethalSaveInvulnerabilityTicks.get(),
            (Integer)cfg().lethalSaveRegenerationAmplifier.get(),
            false,
            false,
            true
         )
      );
      if (player.level() instanceof ServerLevel level) {
         this.spawnDomainAura(level, player);
      }

      player.displayClientMessage(Component.literal("Naberius vetoes death itself.").withStyle(ChatFormatting.GREEN), true);
      return true;
   }

   private int countFusionInputSkillIds(ServerPlayer owner, ListTag archive) {
      List<ResourceLocation> unique = new ArrayList<>();

      try {
         Skills ownerSkills = SkillAPI.getSkillsFrom(owner);
         if (ownerSkills != null) {
            for (ManasSkillInstance skillInstance : ownerSkills.getLearnedSkills()) {
               if (skillInstance != null) {
                  ManasSkill skill = skillInstance.getSkill();
                  if (skill != null && !(skill instanceof NaberiusSkill)) {
                     ResourceLocation id = this.getSkillId(skillInstance, skill);
                     if (id != null && !unique.contains(id)) {
                        unique.add(id);
                     }
                  }
               }
            }
         }
      } catch (Throwable var10) {
      }

      for (int i = 0; i < archive.size(); i++) {
         CompoundTag entry = archive.getCompound(i);
         if (entry.contains("skills", 9)) {
            ListTag skills = entry.getList("skills", 8);

            for (int s = 0; s < skills.size(); s++) {
               try {
                  ResourceLocation id = ResourceLocation.parse(skills.getString(s));
                  if (!unique.contains(id)) {
                     unique.add(id);
                  }
               } catch (Throwable var9) {
               }
            }
         }
      }

      return unique.size();
   }

   private void tickPassiveMastery(ServerPlayer player, ManasSkillInstance instance, CompoundTag root) {
      if (instance.isToggled()) {
         if (!instance.isMastered(player)) {
            int ticks = root.getInt("mastery_ticks") + 1;
            if (ticks >= (Integer)cfg().passiveMasteryIntervalTicks.get()) {
               ticks = 0;
               instance.addMasteryPoint(player);
               instance.markDirty();
            }

            root.putInt("mastery_ticks", ticks);
         }
      }
   }

   private int learnCoreResistancesAndNullifications(LivingEntity target) {
      List<ResourceLocation> ids = new ArrayList<>();
      ManasSkill[] skills = new ManasSkill[]{
         (ManasSkill)ResistanceSkills.ABNORMAL_CONDITION_NULLIFICATION.get(),
         (ManasSkill)ResistanceSkills.PHYSICAL_ATTACK_NULLIFICATION.get(),
         (ManasSkill)ResistanceSkills.SPIRITUAL_ATTACK_NULLIFICATION.get(),
         (ManasSkill)ResistanceSkills.MAGIC_NULLIFICATION.get(),
         (ManasSkill)ResistanceSkills.FLAME_ATTACK_NULLIFICATION.get(),
         (ManasSkill)ResistanceSkills.WATER_ATTACK_NULLIFICATION.get(),
         (ManasSkill)ResistanceSkills.WIND_ATTACK_NULLIFICATION.get(),
         (ManasSkill)ResistanceSkills.EARTH_ATTACK_NULLIFICATION.get(),
         (ManasSkill)ResistanceSkills.SPATIAL_ATTACK_NULLIFICATION.get(),
         (ManasSkill)ResistanceSkills.GRAVITY_ATTACK_NULLIFICATION.get(),
         (ManasSkill)ResistanceSkills.HOLY_ATTACK_NULLIFICATION.get(),
         (ManasSkill)ResistanceSkills.DARKNESS_ATTACK_NULLIFICATION.get(),
         (ManasSkill)ResistanceSkills.LIGHT_ATTACK_NULLIFICATION.get(),
         (ManasSkill)ResistanceSkills.ELECTRICITY_NULLIFICATION.get(),
         (ManasSkill)ResistanceSkills.HEAT_NULLIFICATION.get(),
         (ManasSkill)ResistanceSkills.COLD_NULLIFICATION.get(),
         (ManasSkill)ResistanceSkills.CORROSION_NULLIFICATION.get(),
         (ManasSkill)ResistanceSkills.THERMAL_FLUCTUATION_NULLIFICATION.get(),
         (ManasSkill)ResistanceSkills.POISON_NULLIFICATION.get(),
         (ManasSkill)ResistanceSkills.PAIN_NULLIFICATION.get(),
         (ManasSkill)ResistanceSkills.PARALYSIS_NULLIFICATION.get(),
         (ManasSkill)ResistanceSkills.PIERCE_NULLIFICATION.get(),
         (ManasSkill)ResistanceSkills.ABNORMAL_CONDITION_RESISTANCE.get(),
         (ManasSkill)ResistanceSkills.PHYSICAL_ATTACK_RESISTANCE.get(),
         (ManasSkill)ResistanceSkills.SPIRITUAL_ATTACK_RESISTANCE.get(),
         (ManasSkill)ResistanceSkills.MAGIC_RESISTANCE.get(),
         (ManasSkill)ResistanceSkills.FLAME_ATTACK_RESISTANCE.get(),
         (ManasSkill)ResistanceSkills.WATER_ATTACK_RESISTANCE.get(),
         (ManasSkill)ResistanceSkills.WIND_ATTACK_RESISTANCE.get(),
         (ManasSkill)ResistanceSkills.EARTH_ATTACK_RESISTANCE.get(),
         (ManasSkill)ResistanceSkills.SPATIAL_ATTACK_RESISTANCE.get(),
         (ManasSkill)ResistanceSkills.GRAVITY_ATTACK_RESISTANCE.get(),
         (ManasSkill)ResistanceSkills.HOLY_ATTACK_RESISTANCE.get(),
         (ManasSkill)ResistanceSkills.DARKNESS_ATTACK_RESISTANCE.get(),
         (ManasSkill)ResistanceSkills.LIGHT_ATTACK_RESISTANCE.get(),
         (ManasSkill)ResistanceSkills.ELECTRICITY_RESISTANCE.get(),
         (ManasSkill)ResistanceSkills.HEAT_RESISTANCE.get(),
         (ManasSkill)ResistanceSkills.COLD_RESISTANCE.get(),
         (ManasSkill)ResistanceSkills.CORROSION_RESISTANCE.get(),
         (ManasSkill)ResistanceSkills.THERMAL_FLUCTUATION_RESISTANCE.get(),
         (ManasSkill)ResistanceSkills.POISON_RESISTANCE.get(),
         (ManasSkill)ResistanceSkills.PAIN_RESISTANCE.get(),
         (ManasSkill)ResistanceSkills.PARALYSIS_RESISTANCE.get(),
         (ManasSkill)ResistanceSkills.PIERCE_RESISTANCE.get()
      };

      for (ManasSkill skill : skills) {
         ResourceLocation id = SkillAPI.getSkillRegistry().getId(skill);
         if (id != null && !ids.contains(id)) {
            ids.add(id);
         }
      }

      return this.learnSkillIds(target, ids);
   }

   public void spawnNaberiusAura(ServerLevel level, LivingEntity entity) {
      float size = (float)Math.max(1.2, entity.getBbWidth() * 2.6);

      try {
         ParticleOptions acid = TensuraParticleUtils.getAcidAura(0.7F, size, -0.25F);
         ParticleOptions black = TensuraParticleUtils.getBlackAura(0.28F, size * 0.8F, -0.18F);
         ParticleOptions wave = TensuraParticleUtils.getGreenWave(0.95F, entity.getBbWidth() * 2.8F, -0.45F, true);
         ParticleOptions gust = TensuraParticleUtils.getGreenGust();
         TensuraParticleHelper.addServerAuraParticles(entity, acid, 10, 0.012);
         TensuraParticleHelper.addServerAuraParticles(entity, black, 5, 0.008);
         TensuraParticleHelper.spawnServerParticles(level, wave, entity.getX(), entity.getY() + entity.getBbHeight() * 0.33, entity.getZ());
         TensuraParticleHelper.spawnServerParticles(level, gust, entity.getX(), entity.getY() + 0.2, entity.getZ(), 8, 0.35, 0.18, 0.35, 0.012, true);
         level.sendParticles(
            ParticleTypes.COMPOSTER,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.45,
            entity.getZ(),
            8,
            entity.getBbWidth() * 0.75,
            entity.getBbHeight() * 0.35,
            entity.getBbWidth() * 0.75,
            0.035
         );
         level.sendParticles(
            ParticleTypes.HAPPY_VILLAGER,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.72,
            entity.getZ(),
            4,
            entity.getBbWidth() * 0.55,
            entity.getBbHeight() * 0.2,
            entity.getBbWidth() * 0.55,
            0.02
         );
         level.sendParticles(
            ParticleTypes.SOUL,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.55,
            entity.getZ(),
            5,
            entity.getBbWidth() * 0.5,
            entity.getBbHeight() * 0.25,
            entity.getBbWidth() * 0.5,
            0.018
         );
         level.sendParticles(
            (ParticleOptions)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(),
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.65,
            entity.getZ(),
            3,
            entity.getBbWidth() * 0.6,
            entity.getBbHeight() * 0.25,
            entity.getBbWidth() * 0.6,
            0.035
         );
      } catch (Throwable var8) {
      }
   }

   private void spawnDomainAura(ServerLevel level, LivingEntity entity) {
      float size = (float)Math.max(2.0, entity.getBbWidth() * 4.2);

      try {
         ParticleOptions acid = TensuraParticleUtils.getAcidAura(0.98F, size, -0.35F);
         ParticleOptions black = TensuraParticleUtils.getBlackAura(0.42F, size * 0.95F, -0.25F);
         ParticleOptions wave = TensuraParticleUtils.getLightGreenWave(1.0F, entity.getBbWidth() * 4.4F, -0.55F, true);
         ParticleOptions gust = TensuraParticleUtils.getGreenGust();
         TensuraParticleHelper.addServerAuraParticles(entity, acid, 22, 0.02);
         TensuraParticleHelper.addServerAuraParticles(entity, black, 10, 0.012);
         TensuraParticleHelper.spawnServerParticles(level, wave, entity.getX(), entity.getY() + entity.getBbHeight() * 0.5, entity.getZ());
         TensuraParticleHelper.spawnServerParticles(level, gust, entity.getX(), entity.getY() + 0.2, entity.getZ(), 18, 0.75, 0.24, 0.75, 0.018, true);
         level.sendParticles(
            ParticleTypes.COMPOSTER,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.45,
            entity.getZ(),
            24,
            entity.getBbWidth() * 1.6,
            entity.getBbHeight() * 0.65,
            entity.getBbWidth() * 1.6,
            0.06
         );
         level.sendParticles(
            ParticleTypes.HAPPY_VILLAGER,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.85,
            entity.getZ(),
            12,
            entity.getBbWidth() * 1.25,
            entity.getBbHeight() * 0.35,
            entity.getBbWidth() * 1.25,
            0.045
         );
         level.sendParticles(
            ParticleTypes.SOUL_FIRE_FLAME,
            entity.getX(),
            entity.getY() + 0.15,
            entity.getZ(),
            14,
            entity.getBbWidth() * 1.2,
            0.12,
            entity.getBbWidth() * 1.2,
            0.02
         );
         level.sendParticles(
            ParticleTypes.SOUL,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.6,
            entity.getZ(),
            18,
            entity.getBbWidth() * 1.15,
            entity.getBbHeight() * 0.45,
            entity.getBbWidth() * 1.15,
            0.04
         );
         level.sendParticles(
            (ParticleOptions)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(),
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.75,
            entity.getZ(),
            8,
            entity.getBbWidth() * 1.0,
            entity.getBbHeight() * 0.4,
            entity.getBbWidth() * 1.0,
            0.06
         );
      } catch (Throwable var8) {
      }
   }

   private void spawnGardenHoldVfx(ServerLevel level, ServerPlayer player, int charge) {
      try {
         this.spawnDomainAura(level, player);
         double radius = Math.min(5.0, 1.2 + charge * 0.18);
         level.sendParticles(ParticleTypes.COMPOSTER, player.getX(), player.getY() + 0.2, player.getZ(), 14 + charge, radius, 0.18, radius, 0.035);
         level.sendParticles(
            ParticleTypes.HAPPY_VILLAGER, player.getX(), player.getY() + 1.0, player.getZ(), 6 + charge / 2, radius * 0.75, 0.75, radius * 0.75, 0.025
         );
         level.sendParticles(ParticleTypes.SOUL, player.getX(), player.getY() + 1.0, player.getZ(), 8 + charge / 2, radius * 0.5, 0.75, radius * 0.5, 0.02);
         if (charge % 3 == 0) {
            level.playSound(
               null, player.blockPosition(), SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.PLAYERS, 0.9F, 0.75F + Math.min(0.5F, charge * 0.025F)
            );
         }
      } catch (Throwable var6) {
      }
   }

   private void spawnGardenBloomVfx(ServerLevel level, ServerPlayer player, int charge) {
      try {
         double radius = Math.min(9.0, 3.0 + charge * 0.35);
         this.spawnDomainAura(level, player);
         level.playSound(null, player.blockPosition(), SoundEvents.WARDEN_ROAR, SoundSource.PLAYERS, 2.0F, 0.7F);
         level.playSound(null, player.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.8F, 0.8F);
         level.sendParticles(ParticleTypes.COMPOSTER, player.getX(), player.getY() + 0.15, player.getZ(), 120, radius, 0.2, radius, 0.12);
         level.sendParticles(ParticleTypes.HAPPY_VILLAGER, player.getX(), player.getY() + 1.2, player.getZ(), 60, radius * 0.75, 1.0, radius * 0.75, 0.08);
         level.sendParticles(ParticleTypes.SOUL, player.getX(), player.getY() + 1.0, player.getZ(), 75, radius * 0.65, 1.0, radius * 0.65, 0.08);
         level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, player.getX(), player.getY() + 0.25, player.getZ(), 45, radius * 0.8, 0.25, radius * 0.8, 0.06);
         level.sendParticles(
            (ParticleOptions)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(),
            player.getX(),
            player.getY() + 1.2,
            player.getZ(),
            30,
            radius * 0.65,
            1.0,
            radius * 0.65,
            0.12
         );
      } catch (Throwable var6) {
      }
   }

   private void spawnSummonBirthVfx(ServerLevel level, LivingEntity summon) {
      try {
         this.spawnDomainAura(level, summon);
         level.playSound(null, summon.blockPosition(), SoundEvents.EVOKER_CAST_SPELL, SoundSource.PLAYERS, 1.1F, 0.65F);
         level.sendParticles(
            ParticleTypes.COMPOSTER,
            summon.getX(),
            summon.getY() + summon.getBbHeight() * 0.45,
            summon.getZ(),
            35,
            summon.getBbWidth() * 1.5,
            summon.getBbHeight() * 0.6,
            summon.getBbWidth() * 1.5,
            0.08
         );
         level.sendParticles(
            ParticleTypes.HAPPY_VILLAGER,
            summon.getX(),
            summon.getY() + summon.getBbHeight() * 0.7,
            summon.getZ(),
            18,
            summon.getBbWidth(),
            summon.getBbHeight() * 0.4,
            summon.getBbWidth(),
            0.04
         );
         level.sendParticles(
            ParticleTypes.SOUL,
            summon.getX(),
            summon.getY() + summon.getBbHeight() * 0.5,
            summon.getZ(),
            22,
            summon.getBbWidth(),
            summon.getBbHeight() * 0.45,
            summon.getBbWidth(),
            0.05
         );
      } catch (Throwable var4) {
      }
   }

   private void spawnReviveVfx(ServerLevel level, LivingEntity entity) {
      try {
         this.spawnDomainAura(level, entity);
         level.playSound(null, entity.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.2F, 0.7F);
         level.sendParticles(
            ParticleTypes.TOTEM_OF_UNDYING,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.55,
            entity.getZ(),
            45,
            entity.getBbWidth() * 1.2,
            entity.getBbHeight() * 0.65,
            entity.getBbWidth() * 1.2,
            0.12
         );
         level.sendParticles(
            ParticleTypes.COMPOSTER,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.35,
            entity.getZ(),
            32,
            entity.getBbWidth() * 1.4,
            entity.getBbHeight() * 0.5,
            entity.getBbWidth() * 1.4,
            0.08
         );
         level.sendParticles(
            ParticleTypes.SOUL,
            entity.getX(),
            entity.getY() + entity.getBbHeight() * 0.65,
            entity.getZ(),
            24,
            entity.getBbWidth(),
            entity.getBbHeight() * 0.45,
            entity.getBbWidth(),
            0.06
         );
      } catch (Throwable var4) {
      }
   }

   private void spawnStealSummonVfx(ServerLevel level, ServerPlayer owner, LivingEntity target) {
      try {
         this.spawnDomainAura(level, target);
         this.spawnNaberiusAura(level, owner);
         level.playSound(null, target.blockPosition(), SoundEvents.EVOKER_PREPARE_WOLOLO, SoundSource.PLAYERS, 1.2F, 0.55F);
         level.sendParticles(
            ParticleTypes.TOTEM_OF_UNDYING,
            target.getX(),
            target.getY() + target.getBbHeight() * 0.6,
            target.getZ(),
            35,
            target.getBbWidth() * 1.3,
            target.getBbHeight() * 0.55,
            target.getBbWidth() * 1.3,
            0.1
         );
         level.sendParticles(
            ParticleTypes.COMPOSTER,
            target.getX(),
            target.getY() + target.getBbHeight() * 0.45,
            target.getZ(),
            30,
            target.getBbWidth() * 1.4,
            target.getBbHeight() * 0.55,
            target.getBbWidth() * 1.4,
            0.08
         );
         level.sendParticles(
            (ParticleOptions)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(),
            target.getX(),
            target.getY() + target.getBbHeight() * 0.7,
            target.getZ(),
            10,
            target.getBbWidth(),
            target.getBbHeight() * 0.4,
            target.getBbWidth(),
            0.07
         );
      } catch (Throwable var5) {
      }
   }

   private void spawnLifeDrainVfx(ServerLevel level, ServerPlayer player, LivingEntity target) {
      try {
         this.spawnNaberiusAura(level, target);
         this.spawnNaberiusAura(level, player);
         Vec3 from = target.position().add(0.0, target.getBbHeight() * 0.55, 0.0);
         Vec3 to = player.position().add(0.0, player.getBbHeight() * 0.55, 0.0);
         Vec3 diff = to.subtract(from);

         for (int i = 0; i < 18; i++) {
            double t = i / 17.0;
            Vec3 p = from.add(diff.scale(t));
            level.sendParticles(ParticleTypes.COMPOSTER, p.x, p.y, p.z, 2, 0.08, 0.08, 0.08, 0.01);
            level.sendParticles(ParticleTypes.SOUL, p.x, p.y, p.z, 1, 0.05, 0.05, 0.05, 0.01);
         }

         level.playSound(null, target.blockPosition(), SoundEvents.WITHER_HURT, SoundSource.PLAYERS, 1.0F, 1.45F);
      } catch (Throwable var11) {
      }
   }

   private void spawnOrganFailureVfx(ServerLevel level, LivingEntity target) {
      try {
         this.spawnDomainAura(level, target);
         level.playSound(null, target.blockPosition(), SoundEvents.WARDEN_HEARTBEAT, SoundSource.PLAYERS, 1.4F, 0.45F);
         level.sendParticles(
            ParticleTypes.DAMAGE_INDICATOR,
            target.getX(),
            target.getY() + target.getBbHeight() * 0.65,
            target.getZ(),
            18,
            target.getBbWidth() * 0.8,
            target.getBbHeight() * 0.4,
            target.getBbWidth() * 0.8,
            0.08
         );
         level.sendParticles(
            ParticleTypes.COMPOSTER,
            target.getX(),
            target.getY() + target.getBbHeight() * 0.45,
            target.getZ(),
            20,
            target.getBbWidth(),
            target.getBbHeight() * 0.4,
            target.getBbWidth(),
            0.06
         );
         level.sendParticles(
            ParticleTypes.SOUL_FIRE_FLAME,
            target.getX(),
            target.getY() + target.getBbHeight() * 0.35,
            target.getZ(),
            16,
            target.getBbWidth(),
            target.getBbHeight() * 0.35,
            target.getBbWidth(),
            0.05
         );
      } catch (Throwable var4) {
      }
   }

   private void spawnHinataFusionStartVfx(ServerLevel level, ServerPlayer player) {
      try {
         level.playSound(null, player.blockPosition(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 2.2F, 0.55F);
         level.playSound(null, player.blockPosition(), SoundEvents.WITHER_SPAWN, SoundSource.PLAYERS, 1.4F, 0.75F);
         Vec3 look = player.getLookAngle().normalize();
         double cx = player.getX() + look.x * 2.0;
         double cy = player.getY() + player.getBbHeight() * 0.7;
         double cz = player.getZ() + look.z * 2.0;
         level.sendParticles(ParticleTypes.COMPOSTER, cx, cy, cz, 100, 2.9, 1.9, 2.9, 0.14);
         level.sendParticles(ParticleTypes.HAPPY_VILLAGER, cx, cy + 0.3, cz, 42, 2.3, 1.4, 2.3, 0.08);
         level.sendParticles(ParticleTypes.SOUL, cx, cy, cz, 80, 2.5, 1.8, 2.5, 0.12);
         level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, cx, cy, cz, 55, 1.9, 1.2, 1.9, 0.08);
         level.sendParticles(ParticleTypes.REVERSE_PORTAL, cx, cy, cz, 120, 3.2, 2.2, 3.2, 0.18);
         level.sendParticles(ParticleTypes.LARGE_SMOKE, cx, cy, cz, 35, 2.4, 1.1, 2.4, 0.05);
         level.sendParticles((ParticleOptions)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(), cx, cy, cz, 36, 2.3, 1.8, 2.3, 0.16);
         level.sendParticles((ParticleOptions)TensuraParticleTypes.DARK_PURPLE_LIGHTNING_SPARK.get(), cx, cy, cz, 28, 2.1, 1.6, 2.1, 0.13);
      } catch (Throwable var10) {
      }
   }

   private void tickHinataFusionCombat(ServerPlayer owner, ServerLevel level) {
      ListTag active = root(owner).getList("active", 10);

      for (int i = 0; i < active.size(); i++) {
         CompoundTag tag = active.getCompound(i);
         if (tag.hasUUID("uuid")
            && level.getEntity(tag.getUUID("uuid")) instanceof LivingEntity fusion
            && fusion.isAlive()
            && isOwnedNaberiusSummon(owner, fusion)
            && fusion.getPersistentData().getBoolean("hinata_fusion_entity")) {
            try {
               NaberiusFusionCombatController.tick(owner, fusion);
            } catch (Throwable ignored) {
               try {
                  fusion.discard();
               } catch (Throwable var10) {
               }
            }
         }
      }
   }

   private void spawnHinataFusionCompleteVfx(ServerLevel level, ServerPlayer player, LivingEntity fusion) {
      try {
         level.playSound(null, fusion.blockPosition(), SoundEvents.ENDER_DRAGON_GROWL, SoundSource.PLAYERS, 2.0F, 0.7F);
         level.playSound(null, fusion.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.8F, 0.55F);
         level.sendParticles(ParticleTypes.EXPLOSION, fusion.getX(), fusion.getY() + fusion.getBbHeight() * 0.5, fusion.getZ(), 4, 0.5, 0.5, 0.5, 0.0);
         level.sendParticles(ParticleTypes.COMPOSTER, fusion.getX(), fusion.getY() + fusion.getBbHeight() * 0.45, fusion.getZ(), 130, 3.0, 1.7, 3.0, 0.14);
         level.sendParticles(ParticleTypes.HAPPY_VILLAGER, fusion.getX(), fusion.getY() + fusion.getBbHeight() * 0.8, fusion.getZ(), 70, 2.4, 1.2, 2.4, 0.08);
         level.sendParticles(ParticleTypes.SOUL, fusion.getX(), fusion.getY() + fusion.getBbHeight() * 0.65, fusion.getZ(), 110, 2.6, 2.0, 2.6, 0.12);
         level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, fusion.getX(), fusion.getY() + fusion.getBbHeight() * 0.35, fusion.getZ(), 75, 2.3, 1.4, 2.3, 0.09);
         level.sendParticles(ParticleTypes.REVERSE_PORTAL, fusion.getX(), fusion.getY() + fusion.getBbHeight() * 0.6, fusion.getZ(), 160, 3.3, 2.2, 3.3, 0.2);
         level.sendParticles(
            (ParticleOptions)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(),
            fusion.getX(),
            fusion.getY() + fusion.getBbHeight() * 0.8,
            fusion.getZ(),
            48,
            2.7,
            2.0,
            2.7,
            0.18
         );
         level.sendParticles(
            (ParticleOptions)TensuraParticleTypes.DARK_PURPLE_LIGHTNING_SPARK.get(),
            fusion.getX(),
            fusion.getY() + fusion.getBbHeight() * 0.45,
            fusion.getZ(),
            36,
            2.4,
            1.7,
            2.4,
            0.14
         );
         this.spawnDomainAura(level, fusion);
         this.spawnDomainAura(level, player);
      } catch (Throwable var5) {
      }
   }

   public record ArchiveEntryView(int index, String name, double hp, double ep, int count, boolean selected, boolean player) {
   }
}
