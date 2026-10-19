package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.registry.race.MoreskillsRaces;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.data.TensuraSkillTags;
import io.github.manasmods.tensura.race.TensuraRace;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.EnergyHelper.GainType;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.ClickEvent.Action;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Pre;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.Clone;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent.Post;

public final class ParasiteHandler {
   public static final int PLAN_MERGER = 1;
   public static final int PLAN_TAKEOVER = 2;
   private static final ResourceLocation FORM_AURA_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "parasite_form_aura");
   private static final ResourceLocation FORM_MAGICULE_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "parasite_form_magicule");
   private static final ResourceLocation FORM_ATTACK_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "parasite_form_attack");
   private static final ResourceLocation LEGACY_PARASITE_SCALE_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "parasite_short_scale");
   private static final TagKey<DamageType> PARASITE_FIRE_WEAKNESS = TagKey.create(
      Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "parasite_fire_weakness")
   );
   private static final TagKey<DamageType> PARASITE_SOUND_WEAKNESS = TagKey.create(
      Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "parasite_sound_weakness")
   );
   private static boolean initialized;

   private ParasiteHandler() {
   }

   private static MoreSkillsConfigs.ParasiteConfig cfg() {
      return MoreSkillsConfigs.GRAND.parasite;
   }

   public static void init() {
      if (!initialized) {
         initialized = true;
         NeoForge.EVENT_BUS.addListener(ParasiteHandler::onLevelTick);
         NeoForge.EVENT_BUS.addListener(ParasiteHandler::onDamage);
         NeoForge.EVENT_BUS.addListener(ParasiteHandler::onDeath);
         NeoForge.EVENT_BUS.addListener(ParasiteHandler::onPlayerClone);
         NeoForge.EVENT_BUS.addListener(ParasiteHandler::onPlayerLogin);
         NeoForge.EVENT_BUS.addListener(ParasiteHandler::onPlayerLogout);
         NeoForge.EVENT_BUS.addListener(ParasiteHandler::onEntityJoin);
      }
   }

   public static boolean isParasiteRace(LivingEntity entity) {
      return RaceAPI.getRaceFrom(entity).getRace().map(instance -> instance.getRace() == MoreskillsRaces.PARASITE.get()).orElse(false);
   }

   public static void setParasiteRace(LivingEntity entity) {
      removeModifier(entity, Attributes.SCALE, LEGACY_PARASITE_SCALE_ID);
      removeRaceModifiers(entity);
      RaceAPI.getRaceFrom(entity).setRace(((ManasRace)MoreskillsRaces.PARASITE.get()).createDefaultInstance(), false, false, Component.empty());
      AttributeInstance maxHealth = entity.getAttribute(Attributes.MAX_HEALTH);
      if (maxHealth != null) {
         maxHealth.setBaseValue(Math.max(1.0, (Double)cfg().parasiteRaceHealth.get()));
      }

      entity.refreshDimensions();
      entity.setHealth(Math.min(entity.getHealth(), ((Double)cfg().parasiteRaceHealth.get()).floatValue()));
      applyPendingParasiteEnergy(entity);
   }

   public static boolean grantIntrinsic(LivingEntity entity, ManasSkill skill) {
      Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(entity).getSkill(skill);
      if (optional.isPresent()) {
         ManasSkillInstance existing = optional.get();
         if (existing.getMastery() < 0.0) {
            existing.setMastery(0.0);
         }

         existing.setRemoveTime(-1);
         existing.markDirty();
         SkillAPI.getSkillsFrom(entity).markDirty();
         return true;
      } else {
         TensuraSkillInstance instance = new TensuraSkillInstance(skill);
         instance.setMastery(0.0);
         instance.setRemoveTime(-1);
         instance.getOrCreateTag().putBoolean("NoMagiculeCost", true);
         return SkillHelper.learnSkill(entity, instance, -1);
      }
   }

   public static void removeSkill(LivingEntity entity, ManasSkill skill) {
      SkillAPI.getSkillsFrom(entity).getSkill(skill).ifPresent(instance -> {
         SkillAPI.getSkillsFrom(entity).forgetSkill(instance);
         SkillAPI.getSkillsFrom(entity).markDirty();
      });
   }

   public static boolean bond(LivingEntity parasiteEntity, LivingEntity host, int plan) {
      if (parasiteEntity instanceof ServerPlayer parasite) {
         if (parasite == host || !host.isAlive()) {
            return false;
         }

         if (!ParasiteData.hasBond(parasite) && !ParasiteData.hasBond(host)) {
            if (SkillAPI.getSkillsFrom(host).getSkill((ManasSkill)AllSkills.ANTI_SYMBIOTE.get()).isPresent()) {
               return false;
            }

            if (ParasiteData.isForbidden(parasite, host.getUUID())) {
               return false;
            }

            if (plan == 2 && EnergyHelper.getMaxEP(host) > EnergyHelper.getMaxEP(parasite) * (Double)cfg().hostileInvalidHostEpMultiplier.get()) {
               return false;
            }

            ParasiteData.createBond(parasite, host, plan);
            initializeBondTimers(host);
            if (!grantIntrinsic(host, (ManasSkill)AllSkills.SYMBIOTE.get())) {
               ParasiteData.clearBond(parasite, host);
               return false;
            }

            parasite.displayClientMessage(
               Component.translatable("tensuramoreskills.message.parasite.bonded", new Object[]{host.getDisplayName()}).withStyle(ChatFormatting.DARK_PURPLE),
               true
            );
            if (host instanceof ServerPlayer playerHost) {
               playerHost.displayClientMessage(
                  Component.translatable("tensuramoreskills.message.parasite.symbiote_gained").withStyle(ChatFormatting.LIGHT_PURPLE), true
               );
            }

            sendParasiteControls(parasite);
            if (host instanceof ServerPlayer playerHost) {
               sendHostControls(playerHost);
            }

            if (hostileTakeoverReady(host, parasite)) {
               takeover(parasite, host);
            } else {
               enterSpectatorBody(parasite, host);
            }

            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public static boolean leave(LivingEntity parasiteEntity, boolean expelled, boolean mergerReward) {
      if (parasiteEntity instanceof ServerPlayer parasite) {
         if (!ParasiteData.isParasite(parasite)) {
            return false;
         }

         UUID hostId = ParasiteData.getHost(parasite);
         if (hostId == null) {
            return false;
         }

         LivingEntity host = resolveLiving(parasite, hostId);
         double relationship = host != null ? ParasiteData.getRelationshipDouble(host) : ParasiteData.getRelationshipDouble(parasite);
         double reward = relationship * mergerReward ? (Double)cfg().mergerExpelEpPerRelationship.get() : (Double)cfg().leaveEpPerRelationship.get();
         if (host instanceof ServerPlayer playerHost && ParasiteData.tempControlActive(host)) {
            endPlayerTemporaryControl(playerHost, parasite, false);
         } else if (host instanceof Mob mobHost && ParasiteData.tempControlActive(host)) {
            endNpcTemporaryControl(mobHost, parasite, false);
         } else if (ParasiteData.tempControlActive(parasite)) {
            restoreTemporaryBody(parasite);
         }

         ParasiteData.addForbidden(parasite, hostId);
         clearFormAttributes(parasite);
         if (host != null) {
            removeSkill(host, (ManasSkill)AllSkills.SYMBIOTE.get());
            clearFormAttributes(host);
            if (host instanceof Mob mob) {
               restoreNpcHostState(mob);
            }

            ParasiteData.setParasiteControl(host, false);
            ParasiteData.clearBond(parasite, host);
         } else {
            ParasiteData.clearBond(parasite, null);
            ParasiteServerData.get(parasite.server).queueHostCleanup(hostId, parasite.getUUID());
         }

         gainFlatEP(parasite, reward);
         exitSpectatorBody(parasite, true);
         if (host != null && host.level() instanceof ServerLevel level) {
            parasite.teleportTo(level, host.getX(), host.getY(), host.getZ(), host.getYRot(), host.getXRot());
         }

         parasite.displayClientMessage(
            Component.translatable(expelled ? "tensuramoreskills.message.parasite.expelled" : "tensuramoreskills.message.parasite.left")
               .withStyle(ChatFormatting.DARK_PURPLE),
            true
         );
         return true;
      } else {
         return false;
      }
   }

   public static boolean expelMerger(LivingEntity hostEntity) {
      if (hostEntity instanceof ServerPlayer host) {
         if (!ParasiteData.isHost(host)) {
            return false;
         }

         if (ParasiteData.getPlan(host) != 1) {
            return false;
         }

         double relationship = ParasiteData.getRelationshipDouble(host);
         if (relationship < ((Integer)cfg().mergerExpelRelationship.get()).intValue()) {
            return false;
         }

         UUID parasiteId = ParasiteData.getParasite(host);
         if (parasiteId == null) {
            return false;
         }

         ServerPlayer parasite = resolvePlayer(host, parasiteId);
         ListTag uniqueCandidates = buildUniqueCandidates(host);
         double reward = relationship * (Double)cfg().mergerExpelEpPerRelationship.get();
         if (parasite != null && ParasiteData.tempControlActive(host)) {
            endPlayerTemporaryControl(host, parasite, false);
         }

         removeSkill(host, (ManasSkill)AllSkills.SYMBIOTE.get());
         grantIntrinsic(host, (ManasSkill)AllSkills.ANTI_SYMBIOTE.get());
         ParasiteData.setRelationship(host, Math.min(((Integer)cfg().antiMaxRelationship.get()).intValue(), relationship));
         clearFormAttributes(host);
         ParasiteData.setParasiteControl(host, false);
         if (parasite != null) {
            copyFirstUniqueCandidate(parasite, uniqueCandidates);
            ParasiteData.addForbidden(parasite, host.getUUID());
            clearFormAttributes(parasite);
            ParasiteData.clearBond(parasite, host);
            exitSpectatorBody(parasite, true);
            parasite.teleportTo((ServerLevel)host.level(), host.getX(), host.getY(), host.getZ(), host.getYRot(), host.getXRot());
            gainFlatEP(parasite, reward);
            parasite.displayClientMessage(Component.translatable("tensuramoreskills.message.parasite.expelled").withStyle(ChatFormatting.DARK_PURPLE), true);
         } else {
            ParasiteData.clearBond(null, host);
            ParasiteServerData.get(host.server).queueParasiteCleanup(parasiteId, host.getUUID(), reward, uniqueCandidates);
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean symbiosisUse(LivingEntity host) {
      if (ParasiteData.isHost(host) && host.isAlive()) {
         ServerPlayer parasite = resolvePlayer(host, ParasiteData.getParasite(host));
         if (parasite == null || !ParasiteData.isParasite(parasite) || !parasite.isAlive()) {
            return false;
         }

         if (ParasiteData.tempControlActive(host)) {
            if (host instanceof ServerPlayer playerHost) {
               endPlayerTemporaryControl(playerHost, parasite, true);
            } else {
               if (!(host instanceof Mob mobHost)) {
                  return false;
               }

               endNpcTemporaryControl(mobHost, parasite, true);
            }

            return true;
         } else {
            ParasiteData.addRelationship(host, (Integer)cfg().relationshipPerSymbiosisUse.get());
            if (ParasiteData.getPlan(host) == 2) {
               ParasiteData.addTakeover(host, (Integer)cfg().takeoverPerSymbiosisUse.get());
            }

            ParasiteData.mirror(host, parasite);
            if (ParasiteData.getRelationshipDouble(host) >= ((Integer)cfg().maxRelationship.get()).intValue() && host instanceof ServerPlayer playerHost) {
               playerHost.sendSystemMessage(Component.literal("We are " + parasite.getName().getString()).withStyle(ChatFormatting.LIGHT_PURPLE));
            }

            if (hostileTakeoverReady(host, parasite)) {
               takeover(parasite, host);
               return true;
            }

            if (host instanceof ServerPlayer playerHost) {
               startPlayerTemporaryControl(playerHost, parasite);
            } else {
               if (!(host instanceof Mob mobHost)) {
                  return false;
               }

               startNpcTemporaryControl(mobHost, parasite);
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public static boolean requestHostReclaim(ServerPlayer host) {
      if (ParasiteData.isHost(host) && ParasiteData.tempControlActive(host)) {
         ServerPlayer parasite = resolvePlayer(host, ParasiteData.getParasite(host));
         if (parasite == null) {
            return false;
         }

         endPlayerTemporaryControl(host, parasite, true);
         return true;
      } else {
         return false;
      }
   }

   public static boolean requestParasiteRelease(ServerPlayer parasite) {
      if (ParasiteData.isParasite(parasite) && ParasiteData.tempControlActive(parasite)) {
         LivingEntity host = resolveLiving(parasite, ParasiteData.getHost(parasite));
         if (host instanceof ServerPlayer playerHost) {
            endPlayerTemporaryControl(playerHost, parasite, true);
         } else {
            if (!(host instanceof Mob mobHost)) {
               return false;
            }

            endNpcTemporaryControl(mobHost, parasite, true);
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean toggleNpcFromParasite(ServerPlayer parasite) {
      if (!ParasiteData.isParasite(parasite)) {
         return false;
      }

      LivingEntity host = resolveLiving(parasite, ParasiteData.getHost(parasite));
      return !(host instanceof Mob) ? false : symbiosisUse(host);
   }

   public static boolean setNpcRandomFromParasite(ServerPlayer parasite, boolean enabled) {
      if (!ParasiteData.isParasite(parasite)) {
         return false;
      }

      LivingEntity host = resolveLiving(parasite, ParasiteData.getHost(parasite));
      if (!(host instanceof Mob)) {
         return false;
      }

      ParasiteData.setNpcRandomEnabled(host, enabled);
      scheduleNextNpcToggle(host);
      return true;
   }

   public static LivingEntity getBondedHost(ServerPlayer parasite) {
      return ParasiteData.isParasite(parasite) ? resolveLiving(parasite, ParasiteData.getHost(parasite)) : null;
   }

   public static void applyFormAttributes(LivingEntity entity, double relationship, boolean anti, int durationTicks) {
      double rel = anti
         ? Math.min(((Integer)cfg().antiMaxRelationship.get()).intValue(), relationship)
         : Math.min(((Integer)cfg().maxRelationship.get()).intValue(), relationship);
      double epBoost = Math.max(0.0, rel * (Double)cfg().epMultiplierPerRelationship.get());
      double attackBoost = Math.max(0.0, rel * (Double)cfg().attackPerRelationship.get());
      addModifier(entity, TensuraAttributes.MAX_AURA, FORM_AURA_ID, epBoost, Operation.ADD_MULTIPLIED_TOTAL);
      addModifier(entity, TensuraAttributes.MAX_MAGICULE, FORM_MAGICULE_ID, epBoost, Operation.ADD_MULTIPLIED_TOTAL);
      AttributeInstance attack = entity.getAttribute(Attributes.ATTACK_DAMAGE);
      if (attack != null) {
         attack.addOrReplacePermanentModifier(new AttributeModifier(FORM_ATTACK_ID, attackBoost, Operation.ADD_VALUE));
      }

      CompoundTag root = ParasiteData.root(entity);
      root.putBoolean("FormActive", true);
      root.putBoolean("AntiForm", anti);
      if (anti) {
         root.putLong("FormEnd", entity.level().getGameTime() + Math.max(20, durationTicks));
      } else {
         root.remove("FormEnd");
      }

      ParasiteData.save(entity, root);
   }

   public static void clearFormAttributes(LivingEntity entity) {
      removeModifier(entity, TensuraAttributes.MAX_AURA, FORM_AURA_ID);
      removeModifier(entity, TensuraAttributes.MAX_MAGICULE, FORM_MAGICULE_ID);
      AttributeInstance attack = entity.getAttribute(Attributes.ATTACK_DAMAGE);
      if (attack != null) {
         attack.removeModifier(FORM_ATTACK_ID);
      }

      CompoundTag root = ParasiteData.root(entity);
      root.remove("FormEnd");
      root.remove("FormActive");
      root.remove("AntiForm");
      ParasiteData.save(entity, root);
   }

   public static void takeover(LivingEntity parasiteEntity, LivingEntity host) {
      if (parasiteEntity instanceof ServerPlayer parasite) {
         CompoundTag parasiteBaseline = captureParasiteBaselineEnergy(parasite);
         LinkedHashSet gained = new LinkedHashSet();
         if (ParasiteData.tempControlActive(host) || ParasiteData.tempControlActive(parasite)) {
            gained.addAll(promoteTempControlSkills(parasite));
         }

         copyRace(host, parasite);
         equipPermanentBodyStats(host, parasite, parasiteBaseline);
         gained.addAll(copyBodySkills(host, parasite, true));
         ParasiteData.setBodySkills(parasite, new ArrayList<>(gained));
         ParasiteData.clearTempOriginalBody(parasite);
         removeSkill(host, (ManasSkill)AllSkills.SYMBIOTE.get());
         clearFormAttributes(host);
         clearFormAttributes(parasite);
         if (host instanceof ServerPlayer playerHost && playerHost.isSpectator()) {
            restoreHostFromSpectator(playerHost);
         }

         if (host instanceof Mob mobHost) {
            restoreNpcHostState(mobHost);
         }

         ParasiteData.clearBond(parasite, host);
         ParasiteData.setParasiteControl(parasite, false);
         parasite.setCamera(parasite);
         if (parasite.isSpectator()) {
            parasite.setGameMode(GameType.SURVIVAL);
         }

         if (host.level() instanceof ServerLevel level) {
            parasite.teleportTo(level, host.getX(), host.getY(), host.getZ(), host.getYRot(), host.getXRot());
         }

         parasite.displayClientMessage(Component.translatable("tensuramoreskills.message.parasite.takeover").withStyle(ChatFormatting.DARK_PURPLE), true);
         if (host instanceof ServerPlayer playerHost) {
            playerHost.displayClientMessage(Component.translatable("tensuramoreskills.message.parasite.body_stolen").withStyle(ChatFormatting.RED), true);
            forceRespawnHost(playerHost);
         } else {
            forceKillHost(host);
         }
      }
   }

   public static void loseBodySkills(LivingEntity parasite) {
      CompoundTag bodyStats = ParasiteData.getBodyStats(parasite).copy();
      if (!bodyStats.isEmpty()) {
         queuePermanentBodyBaselineRestore(parasite, bodyStats);
      }

      for (ResourceLocation id : ParasiteData.getBodySkills(parasite)) {
         ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(id);
         if (skill != null && skill != AllSkills.PARASITE.get()) {
            removeSkill(parasite, skill);
         }
      }

      ParasiteData.clearBodySkills(parasite);
      ParasiteData.clearBodyStats(parasite);
   }

   private static void forceRespawnHost(ServerPlayer host) {
      DamageSource source = host.damageSources().generic();
      host.hurt(source, Float.MAX_VALUE);
      if (host.isAlive()) {
         host.setHealth(0.0F);
         host.die(source);
      }
   }

   private static void forceKillHost(LivingEntity host) {
      DamageSource source = host.damageSources().generic();
      host.hurt(source, Float.MAX_VALUE);
      if (host.isAlive()) {
         host.setHealth(0.0F);
         host.die(source);
      }
   }

   private static void enterSpectatorBody(ServerPlayer parasite, LivingEntity host) {
      if (parasite.isAlive() && host.isAlive()) {
         if (parasite.level() != host.level() && host.level() instanceof ServerLevel level) {
            parasite.teleportTo(level, host.getX(), host.getY(), host.getZ(), host.getYRot(), host.getXRot());
         }

         parasite.setGameMode(GameType.SPECTATOR);
         parasite.setCamera(host);
      }
   }

   private static void exitSpectatorBody(ServerPlayer parasite, boolean returnSurvival) {
      parasite.setCamera(parasite);
      if (returnSurvival && parasite.isSpectator()) {
         parasite.setGameMode(GameType.SURVIVAL);
      }
   }

   private static void startPlayerTemporaryControl(ServerPlayer host, ServerPlayer parasite) {
      snapshotTemporaryBody(parasite);
      exitSpectatorBody(parasite, true);
      parasite.teleportTo((ServerLevel)host.level(), host.getX(), host.getY(), host.getZ(), host.getYRot(), host.getXRot());
      copyRace(host, parasite);
      copyBodyStats(host, parasite, false);
      ParasiteData.setTempControlSkills(parasite, copyBodySkills(host, parasite, false));
      clearFormAttributes(host);
      applyFormAttributes(parasite, ParasiteData.getRelationshipDouble(host), false, 0);
      parasite.setHealth(Math.min(host.getHealth(), parasite.getMaxHealth()));
      host.setGameMode(GameType.SPECTATOR);
      host.setCamera(parasite);
      ParasiteData.setParasiteControl(host, true);
      ParasiteData.setParasiteControl(parasite, true);
      ParasiteData.mirror(host, parasite);
      parasite.displayClientMessage(Component.translatable("tensuramoreskills.message.symbiote.control_taken").withStyle(ChatFormatting.DARK_PURPLE), true);
      host.displayClientMessage(Component.translatable("tensuramoreskills.message.symbiote.control_lost").withStyle(ChatFormatting.LIGHT_PURPLE), true);
      sendControlRelease(parasite);
      sendControlReclaim(host);
   }

   private static void endPlayerTemporaryControl(ServerPlayer host, ServerPlayer parasite, boolean messages) {
      restoreHostFromSpectator(host);
      if (parasite.level() instanceof ServerLevel level) {
         host.teleportTo(level, parasite.getX(), parasite.getY(), parasite.getZ(), parasite.getYRot(), parasite.getXRot());
      }

      restoreTemporaryBody(parasite);
      ParasiteData.setParasiteControl(host, false);
      ParasiteData.setParasiteControl(parasite, false);
      ParasiteData.mirror(host, parasite);
      enterSpectatorBody(parasite, host);
      applyFormAttributes(host, ParasiteData.getRelationshipDouble(host), false, 0);
      if (messages) {
         host.displayClientMessage(Component.translatable("tensuramoreskills.message.symbiote.control_returned").withStyle(ChatFormatting.LIGHT_PURPLE), true);
         parasite.displayClientMessage(
            Component.translatable("tensuramoreskills.message.symbiote.control_released").withStyle(ChatFormatting.DARK_PURPLE), true
         );
      }

      sendHostControls(host);
      sendParasiteControls(parasite);
   }

   private static void startNpcTemporaryControl(Mob host, ServerPlayer parasite) {
      snapshotTemporaryBody(parasite);
      snapshotNpcHostState(host);
      exitSpectatorBody(parasite, true);
      if (host.level() instanceof ServerLevel level) {
         parasite.teleportTo(level, host.getX(), host.getY(), host.getZ(), host.getYRot(), host.getXRot());
      }

      copyRace(host, parasite);
      copyBodyStats(host, parasite, false);
      ParasiteData.setTempControlSkills(parasite, copyBodySkills(host, parasite, false));
      clearFormAttributes(host);
      applyFormAttributes(parasite, ParasiteData.getRelationshipDouble(host), false, 0);
      parasite.setHealth(Math.min(host.getHealth(), parasite.getMaxHealth()));
      ParasiteData.setNpcControl(host, true);
      ParasiteData.setNpcControl(parasite, true);
      lockNpcControlState(host, parasite);
      ParasiteData.mirror(host, parasite);
      parasite.displayClientMessage(Component.literal("You now control " + host.getName().getString() + ".").withStyle(ChatFormatting.DARK_PURPLE), true);
      sendControlRelease(parasite);
   }

   private static void endNpcTemporaryControl(Mob host, ServerPlayer parasite, boolean messages) {
      host.setPos(parasite.getX(), parasite.getY(), parasite.getZ());
      if (parasite.isAlive()) {
         host.setHealth(Math.min(Math.max(1.0F, parasite.getHealth()), host.getMaxHealth()));
      }

      restoreTemporaryBody(parasite);
      restoreNpcHostState(host);
      ParasiteData.setParasiteControl(host, false);
      ParasiteData.setParasiteControl(parasite, false);
      ParasiteData.mirror(host, parasite);
      enterSpectatorBody(parasite, host);
      applyFormAttributes(host, ParasiteData.getRelationshipDouble(host), false, 0);
      if (messages) {
         parasite.displayClientMessage(
            Component.literal("Control returned to " + host.getName().getString() + ".").withStyle(ChatFormatting.LIGHT_PURPLE), true
         );
      }

      sendParasiteControls(parasite);
   }

   private static void lockNpcControlState(Mob host, ServerPlayer parasite) {
      host.setNoAi(true);
      host.getNavigation().stop();
      host.setTarget(null);
      host.setAggressive(false);
      host.setInvisible(true);
      host.setInvulnerable(true);
      host.setSilent(true);
      host.setDeltaMovement(Vec3.ZERO);
      host.fallDistance = 0.0F;
      host.setPos(parasite.getX(), parasite.getY(), parasite.getZ());
   }

   private static void restoreHostFromSpectator(ServerPlayer host) {
      host.setCamera(host);
      if (host.isSpectator()) {
         host.setGameMode(GameType.SURVIVAL);
      }

      IExistence existence = TensuraStorages.getExistenceFrom(host);
      existence.setSpiritualForm(false);
      existence.markDirty();
      host.setInvulnerable(false);
      host.setInvisible(false);
   }

   private static void snapshotNpcHostState(Mob host) {
      CompoundTag root = ParasiteData.root(host);
      if (!root.getBoolean("NpcStateSaved")) {
         root.putBoolean("NpcStateSaved", true);
         root.putBoolean("NpcWasNoAi", host.isNoAi());
         root.putBoolean("NpcWasInvisible", host.isInvisible());
         root.putBoolean("NpcWasInvulnerable", host.isInvulnerable());
         root.putBoolean("NpcWasSilent", host.isSilent());
         ParasiteData.save(host, root);
      }
   }

   private static void restoreNpcHostState(Mob host) {
      CompoundTag root = ParasiteData.root(host);
      if (root.getBoolean("NpcStateSaved")) {
         host.setNoAi(root.getBoolean("NpcWasNoAi"));
         host.setInvisible(root.getBoolean("NpcWasInvisible"));
         host.setInvulnerable(root.getBoolean("NpcWasInvulnerable"));
         host.setSilent(root.getBoolean("NpcWasSilent"));
      } else {
         host.setNoAi(false);
         host.setInvisible(false);
         host.setInvulnerable(false);
         host.setSilent(false);
      }

      root.remove("NpcStateSaved");
      root.remove("NpcWasNoAi");
      root.remove("NpcWasInvisible");
      root.remove("NpcWasInvulnerable");
      root.remove("NpcWasSilent");
      root.putBoolean("ParasiteControl", false);
      root.putBoolean("TempControl", false);
      root.putBoolean("NpcControl", false);
      ParasiteData.save(host, root);
   }

   private static void snapshotTemporaryBody(ServerPlayer parasite) {
      ParasiteData.setTempOriginalBody(parasite, captureTemporaryBody(parasite));
   }

   private static void restoreTemporaryBody(ServerPlayer parasite) {
      restoreTemporaryBody(parasite, true);
   }

   private static void restoreTemporaryBody(ServerPlayer parasite, boolean restoreHealth) {
      removeTempControlSkills(parasite);
      clearFormAttributes(parasite);
      CompoundTag original = ParasiteData.getTempOriginalBody(parasite).copy();
      CompoundTag gains = captureTemporaryEnergyGains(parasite, original);
      setParasiteRace(parasite);
      if (!original.isEmpty()) {
         applyTemporaryBody(parasite, original, restoreHealth);
         applyTemporaryEnergyGains(parasite, original, gains);
      }

      ParasiteData.clearTempOriginalBody(parasite);
      ParasiteData.setParasiteControl(parasite, false);
   }

   private static void removeTempControlSkills(ServerPlayer parasite) {
      for (ResourceLocation id : ParasiteData.getTempControlSkills(parasite)) {
         ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(id);
         if (skill != null && skill != AllSkills.PARASITE.get()) {
            removeSkill(parasite, skill);
         }
      }

      ParasiteData.clearTempControlSkills(parasite);
   }

   private static List<ResourceLocation> promoteTempControlSkills(ServerPlayer parasite) {
      List<ResourceLocation> promoted = new ArrayList<>();

      for (ResourceLocation id : ParasiteData.getTempControlSkills(parasite)) {
         SkillAPI.getSkillsFrom(parasite).getSkill(id).ifPresent(instance -> {
            instance.setRemoveTime(-1);
            instance.markDirty();
            promoted.add(id);
         });
      }

      ParasiteData.clearTempControlSkills(parasite);
      return promoted;
   }

   private static void onLevelTick(Post event) {
      if (event.getLevel() instanceof ServerLevel level) {
         long var6 = level.getGameTime();

         for (ServerPlayer player : level.players()) {
            lockTemporaryControl(player);
            lockAttachedCamera(player, var6);
         }

         if (var6 % 20L == 0L) {
            for (ServerPlayer player : level.players()) {
               tickPlayer(player, var6);
            }
         }
      }
   }

   private static void lockTemporaryControl(ServerPlayer parasite) {
      if (ParasiteData.isParasite(parasite) && ParasiteData.tempControlActive(parasite)) {
         LivingEntity host = resolveLiving(parasite, ParasiteData.getHost(parasite));
         if (host instanceof ServerPlayer playerHost) {
            parasite.setCamera(parasite);
            if (parasite.isSpectator()) {
               parasite.setGameMode(GameType.SURVIVAL);
            }

            if (!playerHost.isSpectator()) {
               playerHost.setGameMode(GameType.SPECTATOR);
            }

            if (playerHost.getCamera() != parasite) {
               playerHost.setCamera(parasite);
            }
         } else {
            if (host instanceof Mob mobHost) {
               lockNpcControlState(mobHost, parasite);
            }
         }
      }
   }

   private static void lockAttachedCamera(ServerPlayer parasite, long time) {
      if (ParasiteData.isParasite(parasite) && !ParasiteData.tempControlActive(parasite)) {
         if (ParasiteData.getReattachAfter(parasite) <= time) {
            LivingEntity host = resolveLiving(parasite, ParasiteData.getHost(parasite));
            if (host != null && host.isAlive()) {
               if (!parasite.isSpectator()) {
                  parasite.setGameMode(GameType.SPECTATOR);
               }

               if (parasite.getCamera() != host) {
                  parasite.setCamera(host);
               }
            }
         }
      }
   }

   private static void tickPlayer(ServerPlayer player, long time) {
      CompoundTag root = ParasiteData.root(player);
      if (root.getBoolean("AntiForm") && root.getLong("FormEnd") > 0L && time >= root.getLong("FormEnd")) {
         clearFormAttributes(player);
      }

      if (root.getBoolean("FormActive") && !root.getBoolean("AntiForm") && !ParasiteData.hasBond(player)) {
         clearFormAttributes(player);
      }

      if (ParasiteData.hasBodyStats(player) && !ParasiteData.tempControlActive(player)) {
         syncPermanentBodyEnergy(player);
      }

      if (ParasiteData.isParasite(player)) {
         tickParasite(player, time);
      }

      if (ParasiteData.isHost(player)) {
         tickOfflinePlayerHost(player, time);
      }
   }

   private static void tickParasite(ServerPlayer parasite, long time) {
      long reattachAfter = ParasiteData.getReattachAfter(parasite);
      if (reattachAfter > time) {
         parasite.setCamera(parasite);
         if (parasite.isSpectator()) {
            parasite.setGameMode(GameType.SURVIVAL);
         }
      } else {
         if (reattachAfter > 0L) {
            ParasiteData.setReattachAfter(parasite, 0L);
         }

         LivingEntity host = resolveLiving(parasite, ParasiteData.getHost(parasite));
         if (host != null && host.isAlive()) {
            if (ParasiteData.tempControlActive(parasite) && !ParasiteData.tempControlActive(host)) {
               restoreTemporaryBody(parasite);
               enterSpectatorBody(parasite, host);
               applyFormAttributes(host, ParasiteData.getRelationshipDouble(host), false, 0);
            } else if (!ParasiteData.tempControlActive(parasite) && ParasiteData.tempControlActive(host)) {
               if (host instanceof ServerPlayer playerHost) {
                  restoreHostFromSpectator(playerHost);
               }

               if (host instanceof Mob mobHost) {
                  restoreNpcHostState(mobHost);
               }

               ParasiteData.setParasiteControl(host, false);
            }

            if (ParasiteData.tempControlActive(parasite)) {
               if (host instanceof Mob mob && ParasiteData.npcControlActive(host)) {
                  lockNpcControlState(mob, parasite);
               }
            } else {
               ParasiteData.mirror(host, parasite);
               enterSpectatorBody(parasite, host);
            }

            tickBond(host, parasite, time);
         } else {
            if (ParasiteData.tempControlActive(parasite)) {
               restoreTemporaryBody(parasite);
            }

            clearFormAttributes(parasite);
            parasite.setCamera(parasite);
            if (!parasite.isSpectator()) {
               parasite.setGameMode(GameType.SPECTATOR);
            }
         }
      }
   }

   private static void tickOfflinePlayerHost(ServerPlayer host, long time) {
      ServerPlayer parasite = resolvePlayer(host, ParasiteData.getParasite(host));
      if (parasite == null) {
         if (ParasiteData.tempControlActive(host)) {
            restoreHostFromSpectator(host);
            ParasiteData.setParasiteControl(host, false);
         }

         clearFormAttributes(host);
         advancePassiveProgress(host, time);
      }
   }

   private static void tickBond(LivingEntity host, ServerPlayer parasite, long time) {
      advancePassiveProgress(host, time);
      if (rootSymbiosisActive(host)) {
         advanceSymbiosisProgress(host, time);
      }

      if (host instanceof Mob && ParasiteData.npcRandomEnabled(host) && time >= ParasiteData.getNextNpcToggle(host)) {
         scheduleNextNpcToggle(host);
         symbiosisUse(host);
         if (!ParasiteData.isHost(host)) {
            return;
         }
      }

      ParasiteData.mirror(host, parasite);
      if (hostileTakeoverReady(host, parasite)) {
         takeover(parasite, host);
      }
   }

   private static void initializeBondTimers(LivingEntity host) {
      long time = host.level().getGameTime();
      CompoundTag root = ParasiteData.root(host);
      root.putLong("LastPassiveDay", time / Math.max(20, (Integer)cfg().minecraftDayTicks.get()));
      root.putLong("LastSymbiosisInterval", time / Math.max(20, (Integer)cfg().symbiosisRelationshipIntervalTicks.get()));
      ParasiteData.save(host, root);
      if (host instanceof Mob) {
         scheduleNextNpcToggle(host);
      }
   }

   private static void advancePassiveProgress(LivingEntity host, long time) {
      int dayTicks = Math.max(20, (Integer)cfg().minecraftDayTicks.get());
      long day = time / dayTicks;
      CompoundTag root = ParasiteData.root(host);
      long last = root.contains("LastPassiveDay") ? root.getLong("LastPassiveDay") : day;
      if (day > last) {
         long elapsed = Math.min(64L, day - last);
         if (ParasiteData.getPlan(host) == 1) {
            ParasiteData.addRelationship(host, (double)((Integer)cfg().passiveRelationshipPerDay.get()).intValue() * elapsed);
         }

         if (ParasiteData.getPlan(host) == 2) {
            ParasiteData.addTakeover(host, (int)Math.min(2147483647L, ((Integer)cfg().passiveTakeoverPerDay.get()).intValue() * elapsed));
         }

         root = ParasiteData.root(host);
         root.putLong("LastPassiveDay", day);
         ParasiteData.save(host, root);
      }
   }

   private static void advanceSymbiosisProgress(LivingEntity host, long time) {
      int interval = Math.max(20, (Integer)cfg().symbiosisRelationshipIntervalTicks.get());
      long current = time / interval;
      CompoundTag root = ParasiteData.root(host);
      long last = root.contains("LastSymbiosisInterval") ? root.getLong("LastSymbiosisInterval") : current;
      if (current > last) {
         long elapsed = Math.min(64L, current - last);
         ParasiteData.addRelationship(host, 0.25 * elapsed);
         root = ParasiteData.root(host);
         root.putLong("LastSymbiosisInterval", current);
         ParasiteData.save(host, root);
      }
   }

   private static void scheduleNextNpcToggle(LivingEntity host) {
      long delay = 600L + host.getRandom().nextInt(1201);
      ParasiteData.setNextNpcToggle(host, host.level().getGameTime() + delay);
   }

   private static boolean hostileTakeoverReady(LivingEntity host, ServerPlayer parasite) {
      return ParasiteData.getPlan(host) == 2
         && (
            ParasiteData.getTakeover(host) >= (Integer)cfg().takeoverRequiredPoints.get()
               || EnergyHelper.getMaxEP(host) < EnergyHelper.getMaxEP(parasite) * (Double)cfg().instantTakeoverHostEpRatio.get()
         );
   }

   private static boolean rootSymbiosisActive(LivingEntity entity) {
      CompoundTag root = ParasiteData.root(entity);
      return root.getBoolean("TempControl") || root.getBoolean("FormActive") && !root.getBoolean("AntiForm");
   }

   private static void onDamage(Pre event) {
      LivingEntity entity = event.getEntity();
      boolean weak = isParasiteRace(entity) || rootSymbiosisActive(entity);
      if (weak) {
         if (isFireOrSound(event.getSource())) {
            event.setNewDamage(event.getNewDamage() * ((Double)cfg().fireSoundDamageMultiplier.get()).floatValue());
         }
      }
   }

   private static void onDeath(LivingDeathEvent event) {
      LivingEntity dead = event.getEntity();
      if (dead instanceof ServerPlayer deadPlayer && ParasiteData.isParasite(deadPlayer)) {
         LivingEntity host = resolveLiving(deadPlayer, ParasiteData.getHost(deadPlayer));
         deadPlayer.setCamera(deadPlayer);
         if (ParasiteData.tempControlActive(deadPlayer)) {
            if (host instanceof ServerPlayer playerHost) {
               restoreHostFromSpectator(playerHost);
               ParasiteData.setParasiteControl(playerHost, false);
            } else if (host instanceof Mob mobHost) {
               mobHost.setPos(deadPlayer.getX(), deadPlayer.getY(), deadPlayer.getZ());
               restoreNpcHostState(mobHost);
               ParasiteData.setParasiteControl(mobHost, false);
            }

            restoreTemporaryBody(deadPlayer, false);
         }

         clearFormAttributes(deadPlayer);
         if (host != null) {
            clearFormAttributes(host);
         }

         ParasiteData.setReattachAfter(deadPlayer, deadPlayer.level().getGameTime() + 40L);
      } else if (ParasiteData.isHost(dead)) {
         UUID parasiteId = ParasiteData.getParasite(dead);
         ServerPlayer parasite = resolvePlayer(dead, parasiteId);
         if (dead instanceof ServerPlayer playerHost) {
            playerHost.setCamera(playerHost);
            if (parasite != null) {
               parasite.setCamera(parasite);
               if (!parasite.isSpectator()) {
                  parasite.setGameMode(GameType.SPECTATOR);
               }
            }

            if (ParasiteData.tempControlActive(playerHost) && parasite != null) {
               restoreTemporaryBody(parasite);
               parasite.setCamera(parasite);
               if (!parasite.isSpectator()) {
                  parasite.setGameMode(GameType.SPECTATOR);
               }

               ParasiteData.setParasiteControl(parasite, false);
            }

            ParasiteData.setParasiteControl(playerHost, false);
            clearFormAttributes(playerHost);
            if (parasite != null) {
               clearFormAttributes(parasite);
               ParasiteData.setReattachAfter(parasite, playerHost.level().getGameTime() + 40L);
            }
         } else {
            double relationship = ParasiteData.getRelationshipDouble(dead);
            double reward = relationship * (Double)cfg().leaveEpPerRelationship.get();
            if (parasite != null) {
               if (ParasiteData.tempControlActive(parasite)) {
                  restoreTemporaryBody(parasite);
               }

               ParasiteData.addForbidden(parasite, dead.getUUID());
               ParasiteData.clearBond(parasite, dead);
               clearFormAttributes(parasite);
               exitSpectatorBody(parasite, true);
               gainFlatEP(parasite, reward);
               parasite.displayClientMessage(Component.literal("Your NPC host died and the bond ended.").withStyle(ChatFormatting.RED), true);
            } else if (parasiteId != null) {
               MinecraftServer server = serverOf(dead);
               if (server != null) {
                  ParasiteServerData.get(server).queueParasiteCleanup(parasiteId, dead.getUUID(), reward, new ListTag());
               }

               ParasiteData.clearBond(null, dead);
            }
         }
      }
   }

   private static void onPlayerClone(Clone event) {
      if (event.getOriginal() instanceof ServerPlayer oldPlayer) {
         if (event.getEntity() instanceof ServerPlayer newPlayer) {
            if (ParasiteData.hasAnyData(oldPlayer)) {
               ParasiteData.save(newPlayer, ParasiteData.root(oldPlayer).copy());
               newPlayer.setCamera(newPlayer);
               if (newPlayer.isSpectator()) {
                  newPlayer.setGameMode(GameType.SURVIVAL);
               }

               if (ParasiteData.hasBodyStats(newPlayer)) {
                  reapplyBodyStats(newPlayer);
               }

               if (ParasiteData.isParasite(newPlayer) && !ParasiteData.tempControlActive(newPlayer)) {
                  setParasiteRace(newPlayer);
               }

               if (ParasiteData.tempControlActive(newPlayer)) {
                  if (ParasiteData.isParasite(newPlayer)) {
                     restoreTemporaryBody(newPlayer);
                  } else {
                     ParasiteData.setParasiteControl(newPlayer, false);
                  }
               }

               if (ParasiteData.isHost(newPlayer)) {
                  grantIntrinsic(newPlayer, (ManasSkill)AllSkills.SYMBIOTE.get());
               }

               if (ParasiteData.isParasite(newPlayer)) {
                  ParasiteData.setReattachAfter(newPlayer, newPlayer.level().getGameTime() + 40L);
               }
            }
         }
      }
   }

   private static void onPlayerLogin(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         processPending(player);
         if (SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)AllSkills.PARASITE.get()).isPresent()
            && !ParasiteData.isParasite(player)
            && !ParasiteData.hasBodySkills(player)
            && !ParasiteData.hasBodyStats(player)) {
            setParasiteRace(player);
         }

         if (ParasiteData.hasBodyStats(player)) {
            reapplyBodyStats(player);
         }

         normalizeControlOnLogin(player);
         if (!ParasiteData.tempControlActive(player) && ParasiteData.hasTempOriginalBody(player)) {
            ParasiteData.clearTempOriginalBody(player);
            ParasiteData.clearTempControlSkills(player);
         }

         if (ParasiteData.isParasite(player) && !ParasiteData.tempControlActive(player)) {
            setParasiteRace(player);
         }

         if (ParasiteData.isHost(player)) {
            grantIntrinsic(player, (ManasSkill)AllSkills.SYMBIOTE.get());
            if (ParasiteData.root(player).getBoolean("FormActive") && !ParasiteData.root(player).getBoolean("AntiForm")) {
               applyFormAttributes(player, ParasiteData.getRelationshipDouble(player), false, 0);
            }

            sendHostControls(player);
         }

         if (ParasiteData.isParasite(player)) {
            ParasiteData.setReattachAfter(player, player.level().getGameTime() + 20L);
            player.setCamera(player);
            if (player.isSpectator()) {
               player.setGameMode(GameType.SURVIVAL);
            }

            sendParasiteControls(player);
         }

         CompoundTag root = ParasiteData.root(player);
         if (root.getBoolean("AntiForm") && root.getLong("FormEnd") > player.level().getGameTime()) {
            applyFormAttributes(
               player, ParasiteData.getRelationshipDouble(player), true, (int)Math.min(2147483647L, root.getLong("FormEnd") - player.level().getGameTime())
            );
         }
      }
   }

   private static void normalizeControlOnLogin(ServerPlayer player) {
      if (ParasiteData.tempControlActive(player)) {
         if (ParasiteData.isParasite(player)) {
            LivingEntity host = resolveLiving(player, ParasiteData.getHost(player));
            if (host instanceof ServerPlayer playerHost) {
               restoreHostFromSpectator(playerHost);
               ParasiteData.setParasiteControl(playerHost, false);
            } else if (host instanceof Mob mobHost) {
               restoreNpcHostState(mobHost);
               ParasiteData.setParasiteControl(mobHost, false);
            }

            restoreTemporaryBody(player);
         } else if (ParasiteData.isHost(player)) {
            restoreHostFromSpectator(player);
            ParasiteData.setParasiteControl(player, false);
            clearFormAttributes(player);
            ServerPlayer parasite = resolvePlayer(player, ParasiteData.getParasite(player));
            if (parasite != null && ParasiteData.tempControlActive(parasite)) {
               restoreTemporaryBody(parasite);
            }
         } else {
            ParasiteData.setParasiteControl(player, false);
            clearFormAttributes(player);
         }
      }
   }

   private static void onPlayerLogout(PlayerLoggedOutEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         if (ParasiteData.isHost(player)) {
            ServerPlayer parasite = resolvePlayer(player, ParasiteData.getParasite(player));
            if (ParasiteData.tempControlActive(player)) {
               if (parasite != null) {
                  endPlayerTemporaryControl(player, parasite, false);
               } else {
                  restoreHostFromSpectator(player);
                  ParasiteData.setParasiteControl(player, false);
               }
            } else if (parasite != null) {
               parasite.setCamera(parasite);
               if (!parasite.isSpectator()) {
                  parasite.setGameMode(GameType.SPECTATOR);
               }
            }

            clearFormAttributes(player);
            if (parasite != null) {
               clearFormAttributes(parasite);
            }
         }

         if (ParasiteData.isParasite(player)) {
            LivingEntity host = resolveLiving(player, ParasiteData.getHost(player));
            if (ParasiteData.tempControlActive(player)) {
               if (host instanceof ServerPlayer playerHost) {
                  restoreHostFromSpectator(playerHost);
                  ParasiteData.setParasiteControl(playerHost, false);
               } else if (host instanceof Mob mobHost) {
                  mobHost.setPos(player.getX(), player.getY(), player.getZ());
                  restoreNpcHostState(mobHost);
                  ParasiteData.setParasiteControl(mobHost, false);
               }

               restoreTemporaryBody(player);
            }

            player.setCamera(player);
            if (!player.isSpectator()) {
               player.setGameMode(GameType.SPECTATOR);
            }

            clearFormAttributes(player);
            if (host != null) {
               clearFormAttributes(host);
            }
         }
      }
   }

   private static void onEntityJoin(EntityJoinLevelEvent event) {
      if (!event.getLevel().isClientSide()) {
         if (event.getEntity() instanceof LivingEntity living) {
            if (!(living instanceof Player)) {
               processPendingHost(living);
               if (ParasiteData.isHost(living)) {
                  grantIntrinsic(living, (ManasSkill)AllSkills.SYMBIOTE.get());
                  if (living instanceof Mob mob && ParasiteData.npcControlActive(living)) {
                     restoreNpcHostState(mob);
                  }
               }
            }
         }
      }
   }

   private static void processPending(ServerPlayer player) {
      CompoundTag action = ParasiteServerData.get(player.server).take(player.getUUID());
      if (action != null) {
         int type = action.getInt("Type");
         if (type == 1) {
            cleanPendingHost(player);
         } else {
            if (type == 2) {
               if (ParasiteData.tempControlActive(player)) {
                  restoreTemporaryBody(player);
               }

               clearFormAttributes(player);
               UUID host = action.hasUUID("Other") ? action.getUUID("Other") : null;
               ParasiteData.addForbidden(player, host);
               ParasiteData.clearBond(player, null);
               exitSpectatorBody(player, true);
               gainFlatEP(player, action.getDouble("Reward"));
               copyFirstUniqueCandidate(player, action.getList("UniqueCandidates", 10));
               player.displayClientMessage(Component.translatable("tensuramoreskills.message.parasite.expelled").withStyle(ChatFormatting.DARK_PURPLE), true);
            }
         }
      }
   }

   private static void processPendingHost(LivingEntity living) {
      MinecraftServer server = serverOf(living);
      if (server != null) {
         CompoundTag action = ParasiteServerData.get(server).take(living.getUUID());
         if (action != null) {
            if (action.getInt("Type") == 1) {
               cleanPendingHost(living);
            }
         }
      }
   }

   private static void cleanPendingHost(LivingEntity host) {
      removeSkill(host, (ManasSkill)AllSkills.SYMBIOTE.get());
      clearFormAttributes(host);
      ParasiteData.setParasiteControl(host, false);
      ParasiteData.clearBond(null, host);
      if (host instanceof ServerPlayer player) {
         player.setCamera(player);
         if (player.isSpectator()) {
            player.setGameMode(GameType.SURVIVAL);
         }
      }

      if (host instanceof Mob mob) {
         restoreNpcHostState(mob);
      }
   }

   private static boolean isFireOrSound(DamageSource source) {
      if (source.is(DamageTypeTags.IS_FIRE)) {
         return true;
      } else if (source.is(PARASITE_FIRE_WEAKNESS) || source.is(PARASITE_SOUND_WEAKNESS)) {
         return true;
      } else if (matches(source, TensuraDamageTypes.FIRE_ELEMENTAL)) {
         return true;
      } else if (matches(source, TensuraDamageTypes.BLACK_FLAME)) {
         return true;
      } else if (matches(source, TensuraDamageTypes.FLAME_BREATH)) {
         return true;
      } else if (matches(source, TensuraDamageTypes.BURN)) {
         return true;
      } else {
         return matches(source, TensuraDamageTypes.HEAT_WAVE) ? true : matches(source, TensuraDamageTypes.SOUND_BLAST);
      }
   }

   private static boolean matches(DamageSource source, ResourceKey<DamageType> type) {
      return source.is(type);
   }

   private static void gainFlatEP(LivingEntity entity, double amount) {
      if (!(amount <= 0.0) && Double.isFinite(amount)) {
         double half = amount / 2.0;
         EnergyHelper.gainAura(entity, half, GainType.MAX);
         EnergyHelper.gainMagicule(entity, half, GainType.MAX);
      }
   }

   private static ServerPlayer resolvePlayer(LivingEntity source, UUID id) {
      MinecraftServer server = serverOf(source);
      return id != null && server != null ? server.getPlayerList().getPlayer(id) : null;
   }

   private static LivingEntity resolveLiving(LivingEntity source, UUID id) {
      MinecraftServer server = serverOf(source);
      if (id != null && server != null) {
         ServerPlayer player = server.getPlayerList().getPlayer(id);
         if (player != null) {
            return player;
         }

         for (ServerLevel level : server.getAllLevels()) {
            if (level.getEntity(id) instanceof LivingEntity living) {
               return living;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private static MinecraftServer serverOf(LivingEntity entity) {
      if (entity instanceof ServerPlayer player) {
         return player.server;
      } else {
         return entity.level() instanceof ServerLevel level ? level.getServer() : null;
      }
   }

   private static List<ResourceLocation> copyBodySkills(LivingEntity from, LivingEntity to, boolean permanent) {
      List<ResourceLocation> gained = new ArrayList<>();
      int removeTime = permanent ? -1 : -2;

      for (ManasSkillInstance instance : List.copyOf(SkillAPI.getSkillsFrom(from).getLearnedSkills())) {
         if (instance != null && !instance.isTemporarySkill()) {
            ManasSkill skill = instance.getSkill();
            if (skill != AllSkills.SYMBIOTE.get()
               && skill != AllSkills.ANTI_SYMBIOTE.get()
               && skill != AllSkills.PARASITE.get()
               && !SkillAPI.getSkillsFrom(to).getSkill(skill).isPresent()) {
               ManasSkillInstance copy = ManasSkillInstance.fromNBT(instance.toNBT());
               copy.setRemoveTime(removeTime);
               copy.getOrCreateTag().putBoolean("NoMagiculeCost", true);
               if (SkillHelper.learnSkill(to, copy, removeTime)) {
                  gained.add(skill.getRegistryName());
               }
            }
         }
      }

      return gained;
   }

   private static void copyRace(LivingEntity from, LivingEntity to) {
      removeModifier(to, Attributes.SCALE, LEGACY_PARASITE_SCALE_ID);
      removeRaceModifiers(to);
      Optional<ManasRaceInstance> race = RaceAPI.getRaceFrom(from).getRace();
      race.ifPresent(instance -> RaceAPI.getRaceFrom(to).setRace(instance.copy(), false, false, Component.empty()));
      to.refreshDimensions();
   }

   private static CompoundTag captureBody(LivingEntity entity) {
      CompoundTag stored = new CompoundTag();

      for (Holder<Attribute> attribute : bodyStatList()) {
         AttributeInstance source = entity.getAttribute(attribute);
         ResourceLocation id = BuiltInRegistries.ATTRIBUTE.getKey((Attribute)attribute.value());
         if (source != null && id != null) {
            stored.putDouble(id.toString(), source.getBaseValue());
         }
      }

      stored.putDouble("BaseAura", EnergyHelper.getBaseMaxAura(entity));
      stored.putDouble("BaseMagicule", EnergyHelper.getBaseMaxMagicule(entity));
      IExistence existence = TensuraStorages.getExistenceFrom(entity);
      stored.putDouble("Aura", existence.getAura());
      stored.putDouble("Magicule", existence.getMagicule());
      stored.putFloat("Health", entity.getHealth());
      return stored;
   }

   private static CompoundTag captureTemporaryBody(LivingEntity entity) {
      CompoundTag stored = captureBody(entity);
      CompoundTag baseAttributes = new CompoundTag();

      for (Holder<Attribute> attribute : BuiltInRegistries.ATTRIBUTE.holders().toList()) {
         AttributeInstance instance = entity.getAttribute(attribute);
         ResourceLocation id = BuiltInRegistries.ATTRIBUTE.getKey((Attribute)attribute.value());
         if (instance != null && id != null) {
            baseAttributes.putDouble(id.toString(), instance.getBaseValue());
         }
      }

      stored.put("AllBaseAttributes", baseAttributes);
      return stored;
   }

   private static void applyTemporaryBody(LivingEntity entity, CompoundTag stored, boolean restoreHealth) {
      CompoundTag baseAttributes = stored.getCompound("AllBaseAttributes");

      for (Holder<Attribute> attribute : BuiltInRegistries.ATTRIBUTE.holders().toList()) {
         AttributeInstance instance = entity.getAttribute(attribute);
         ResourceLocation id = BuiltInRegistries.ATTRIBUTE.getKey((Attribute)attribute.value());
         if (instance != null && id != null && baseAttributes.contains(id.toString(), 6)) {
            instance.setBaseValue(baseAttributes.getDouble(id.toString()));
         }
      }

      applyCapturedBody(entity, stored, restoreHealth);
   }

   private static void applyCapturedBody(LivingEntity entity, CompoundTag stored) {
      applyCapturedBody(entity, stored, true);
   }

   private static void applyCapturedBody(LivingEntity entity, CompoundTag stored, boolean restoreHealth) {
      for (Holder<Attribute> attribute : bodyStatList()) {
         ResourceLocation id = BuiltInRegistries.ATTRIBUTE.getKey((Attribute)attribute.value());
         AttributeInstance target = entity.getAttribute(attribute);
         if (id != null && target != null && stored.contains(id.toString())) {
            target.setBaseValue(stored.getDouble(id.toString()));
         }
      }

      if (stored.contains("BaseAura")) {
         EnergyHelper.setMaxAura(entity, Math.max(1.0, stored.getDouble("BaseAura")));
      }

      if (stored.contains("BaseMagicule")) {
         EnergyHelper.setMaxMagicule(entity, Math.max(1.0, stored.getDouble("BaseMagicule")));
      }

      IExistence existence = TensuraStorages.getExistenceFrom(entity);
      if (stored.contains("Aura")) {
         existence.setAura(Math.min(stored.getDouble("Aura"), EnergyHelper.getMaxAura(entity)));
      }

      if (stored.contains("Magicule")) {
         existence.setMagicule(Math.min(stored.getDouble("Magicule"), EnergyHelper.getMaxMagicule(entity)));
      }

      existence.markDirty();
      if (restoreHealth && stored.contains("Health")) {
         entity.setHealth(Math.min(stored.getFloat("Health"), entity.getMaxHealth()));
      }
   }

   private static void copyBodyStats(LivingEntity from, LivingEntity to, boolean permanent) {
      CompoundTag stored = captureBody(from);
      applyCapturedBody(to, stored);
      if (permanent) {
         ParasiteData.setBodyStats(to, stored);
      } else if (to instanceof ServerPlayer parasite && ParasiteData.hasTempOriginalBody(parasite)) {
         CompoundTag original = ParasiteData.getTempOriginalBody(parasite).copy();
         original.putDouble("BorrowedBaseAura", stored.getDouble("BaseAura"));
         original.putDouble("BorrowedBaseMagicule", stored.getDouble("BaseMagicule"));
         original.putDouble("BorrowedAura", stored.getDouble("Aura"));
         original.putDouble("BorrowedMagicule", stored.getDouble("Magicule"));
         ParasiteData.setTempOriginalBody(parasite, original);
      }
   }

   private static CompoundTag captureParasiteBaselineEnergy(ServerPlayer parasite) {
      if (ParasiteData.hasTempOriginalBody(parasite)) {
         CompoundTag original = ParasiteData.getTempOriginalBody(parasite).copy();
         CompoundTag gains = captureTemporaryEnergyGains(parasite, original);
         CompoundTag baseline = new CompoundTag();
         baseline.putDouble("BaseAura", safePositiveAdd(original.getDouble("BaseAura"), gains.getDouble("BaseAura")));
         baseline.putDouble("BaseMagicule", safePositiveAdd(original.getDouble("BaseMagicule"), gains.getDouble("BaseMagicule")));
         baseline.putDouble("Aura", safePositiveAdd(original.getDouble("Aura"), gains.getDouble("Aura")));
         baseline.putDouble("Magicule", safePositiveAdd(original.getDouble("Magicule"), gains.getDouble("Magicule")));
         return baseline;
      }

      CompoundTag bodyStats = ParasiteData.getBodyStats(parasite);
      if (!bodyStats.isEmpty() && bodyStats.contains("ParasiteBaselineBaseAura")) {
         return calculatePermanentBodyBaseline(parasite, bodyStats);
      }

      CompoundTag baseline = new CompoundTag();
      IExistence existence = TensuraStorages.getExistenceFrom(parasite);
      baseline.putDouble("BaseAura", Math.max(0.0, EnergyHelper.getBaseMaxAura(parasite)));
      baseline.putDouble("BaseMagicule", Math.max(0.0, EnergyHelper.getBaseMaxMagicule(parasite)));
      baseline.putDouble("Aura", Math.max(0.0, existence.getAura()));
      baseline.putDouble("Magicule", Math.max(0.0, existence.getMagicule()));
      return baseline;
   }

   private static CompoundTag captureTemporaryEnergyGains(ServerPlayer parasite, CompoundTag original) {
      CompoundTag gains = new CompoundTag();
      if (original.isEmpty()) {
         return gains;
      }

      double borrowedBaseAura = original.contains("BorrowedBaseAura") ? original.getDouble("BorrowedBaseAura") : EnergyHelper.getBaseMaxAura(parasite);
      double borrowedBaseMagicule = original.contains("BorrowedBaseMagicule")
         ? original.getDouble("BorrowedBaseMagicule")
         : EnergyHelper.getBaseMaxMagicule(parasite);
      IExistence existence = TensuraStorages.getExistenceFrom(parasite);
      double borrowedAura = original.contains("BorrowedAura") ? original.getDouble("BorrowedAura") : existence.getAura();
      double borrowedMagicule = original.contains("BorrowedMagicule") ? original.getDouble("BorrowedMagicule") : existence.getMagicule();
      gains.putDouble("BaseAura", Math.max(0.0, EnergyHelper.getBaseMaxAura(parasite) - borrowedBaseAura));
      gains.putDouble("BaseMagicule", Math.max(0.0, EnergyHelper.getBaseMaxMagicule(parasite) - borrowedBaseMagicule));
      gains.putDouble("Aura", Math.max(0.0, existence.getAura() - borrowedAura));
      gains.putDouble("Magicule", Math.max(0.0, existence.getMagicule() - borrowedMagicule));
      return gains;
   }

   private static void applyTemporaryEnergyGains(ServerPlayer parasite, CompoundTag original, CompoundTag gains) {
      double baseAura = safePositiveAdd(original.getDouble("BaseAura"), gains.getDouble("BaseAura"));
      double baseMagicule = safePositiveAdd(original.getDouble("BaseMagicule"), gains.getDouble("BaseMagicule"));
      EnergyHelper.setMaxAura(parasite, Math.max(1.0, baseAura));
      EnergyHelper.setMaxMagicule(parasite, Math.max(1.0, baseMagicule));
      IExistence existence = TensuraStorages.getExistenceFrom(parasite);
      double aura = safePositiveAdd(original.getDouble("Aura"), gains.getDouble("Aura"));
      double magicule = safePositiveAdd(original.getDouble("Magicule"), gains.getDouble("Magicule"));
      existence.setAura(Math.min(aura, EnergyHelper.getMaxAura(parasite)));
      existence.setMagicule(Math.min(magicule, EnergyHelper.getMaxMagicule(parasite)));
      existence.markDirty();
   }

   private static void equipPermanentBodyStats(LivingEntity host, ServerPlayer parasite, CompoundTag baseline) {
      CompoundTag body = captureBody(host);
      double bodyBaseAura = Math.max(0.0, body.getDouble("BaseAura"));
      double bodyBaseMagicule = Math.max(0.0, body.getDouble("BaseMagicule"));
      double fusedBaseAura = safePositiveAdd(baseline.getDouble("BaseAura"), bodyBaseAura);
      double fusedBaseMagicule = safePositiveAdd(baseline.getDouble("BaseMagicule"), bodyBaseMagicule);
      double fusedAura = safePositiveAdd(baseline.getDouble("Aura"), body.getDouble("Aura"));
      double fusedMagicule = safePositiveAdd(baseline.getDouble("Magicule"), body.getDouble("Magicule"));
      body.putDouble("ParasiteBaselineBaseAura", Math.max(0.0, baseline.getDouble("BaseAura")));
      body.putDouble("ParasiteBaselineBaseMagicule", Math.max(0.0, baseline.getDouble("BaseMagicule")));
      body.putDouble("ParasiteBaselineAura", Math.max(0.0, baseline.getDouble("Aura")));
      body.putDouble("ParasiteBaselineMagicule", Math.max(0.0, baseline.getDouble("Magicule")));
      body.putDouble("PermanentBodyBaseAura", bodyBaseAura);
      body.putDouble("PermanentBodyBaseMagicule", bodyBaseMagicule);
      body.putDouble("PermanentInitialBaseAura", fusedBaseAura);
      body.putDouble("PermanentInitialBaseMagicule", fusedBaseMagicule);
      body.putDouble("BaseAura", fusedBaseAura);
      body.putDouble("BaseMagicule", fusedBaseMagicule);
      body.putDouble("Aura", Math.min(fusedAura, fusedBaseAura));
      body.putDouble("Magicule", Math.min(fusedMagicule, fusedBaseMagicule));
      applyCapturedBody(parasite, body);
      ParasiteData.setBodyStats(parasite, body);
   }

   private static CompoundTag calculatePermanentBodyBaseline(LivingEntity parasite, CompoundTag bodyStats) {
      CompoundTag baseline = new CompoundTag();
      double initialBaseAura = bodyStats.getDouble("PermanentInitialBaseAura");
      double initialBaseMagicule = bodyStats.getDouble("PermanentInitialBaseMagicule");
      double gainedBaseAura = Math.max(0.0, EnergyHelper.getBaseMaxAura(parasite) - initialBaseAura);
      double gainedBaseMagicule = Math.max(0.0, EnergyHelper.getBaseMaxMagicule(parasite) - initialBaseMagicule);
      double restoredBaseAura = safePositiveAdd(bodyStats.getDouble("ParasiteBaselineBaseAura"), gainedBaseAura);
      double restoredBaseMagicule = safePositiveAdd(bodyStats.getDouble("ParasiteBaselineBaseMagicule"), gainedBaseMagicule);
      IExistence existence = TensuraStorages.getExistenceFrom(parasite);
      baseline.putDouble("BaseAura", restoredBaseAura);
      baseline.putDouble("BaseMagicule", restoredBaseMagicule);
      baseline.putDouble("Aura", Math.min(Math.max(0.0, existence.getAura()), restoredBaseAura));
      baseline.putDouble("Magicule", Math.min(Math.max(0.0, existence.getMagicule()), restoredBaseMagicule));
      return baseline;
   }

   private static void queuePermanentBodyBaselineRestore(LivingEntity parasite, CompoundTag bodyStats) {
      CompoundTag baseline;
      if (bodyStats.contains("ParasiteBaselineBaseAura")) {
         baseline = calculatePermanentBodyBaseline(parasite, bodyStats);
      } else {
         baseline = new CompoundTag();
         IExistence existence = TensuraStorages.getExistenceFrom(parasite);
         baseline.putDouble("BaseAura", Math.max(0.0, EnergyHelper.getBaseMaxAura(parasite)));
         baseline.putDouble("BaseMagicule", Math.max(0.0, EnergyHelper.getBaseMaxMagicule(parasite)));
         baseline.putDouble("Aura", Math.max(0.0, existence.getAura()));
         baseline.putDouble("Magicule", Math.max(0.0, existence.getMagicule()));
      }

      CompoundTag root = ParasiteData.root(parasite);
      root.put("PendingParasiteEnergy", baseline);
      ParasiteData.save(parasite, root);
   }

   private static void applyPendingParasiteEnergy(LivingEntity entity) {
      CompoundTag root = ParasiteData.root(entity);
      if (root.contains("PendingParasiteEnergy", 10)) {
         CompoundTag baseline = root.getCompound("PendingParasiteEnergy").copy();
         root.remove("PendingParasiteEnergy");
         ParasiteData.save(entity, root);
         EnergyHelper.setMaxAura(entity, Math.max(1.0, baseline.getDouble("BaseAura")));
         EnergyHelper.setMaxMagicule(entity, Math.max(1.0, baseline.getDouble("BaseMagicule")));
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         existence.setAura(Math.min(Math.max(0.0, baseline.getDouble("Aura")), EnergyHelper.getMaxAura(entity)));
         existence.setMagicule(Math.min(Math.max(0.0, baseline.getDouble("Magicule")), EnergyHelper.getMaxMagicule(entity)));
         existence.markDirty();
      }
   }

   private static void syncPermanentBodyEnergy(LivingEntity entity) {
      CompoundTag body = ParasiteData.getBodyStats(entity).copy();
      if (!body.isEmpty()) {
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         body.putDouble("BaseAura", Math.max(0.0, EnergyHelper.getBaseMaxAura(entity)));
         body.putDouble("BaseMagicule", Math.max(0.0, EnergyHelper.getBaseMaxMagicule(entity)));
         body.putDouble("Aura", Math.max(0.0, existence.getAura()));
         body.putDouble("Magicule", Math.max(0.0, existence.getMagicule()));
         ParasiteData.setBodyStats(entity, body);
      }
   }

   private static double safePositiveAdd(double first, double second) {
      double result = Math.max(0.0, first) + Math.max(0.0, second);
      return Double.isFinite(result) ? result : Double.MAX_VALUE;
   }

   private static void reapplyBodyStats(LivingEntity entity) {
      CompoundTag stats = ParasiteData.getBodyStats(entity);
      if (!stats.isEmpty()) {
         applyCapturedBody(entity, stats);
      }
   }

   private static ListTag buildUniqueCandidates(LivingEntity from) {
      List<ManasSkillInstance> candidates = new ArrayList<>();

      for (ManasSkillInstance instance : List.copyOf(SkillAPI.getSkillsFrom(from).getLearnedSkills())) {
         if (instance != null && !instance.isTemporarySkill()) {
            ManasSkill skill = instance.getSkill();
            if (skill != AllSkills.PARASITE.get()
               && skill != AllSkills.SYMBIOTE.get()
               && skill != AllSkills.ANTI_SYMBIOTE.get()
               && skill instanceof Skill tensuraSkill
               && tensuraSkill.getType() == SkillType.UNIQUE
               && !SkillAPI.getSkillRegistry().delegate(skill.getRegistryName()).is(TensuraSkillTags.NO_PLUNDERING)) {
               candidates.add(instance);
            }
         }
      }

      candidates.sort(Comparator.comparingDouble(ManasSkillInstance::getMastery).reversed().thenComparing(instancex -> instancex.getSkillId().toString()));
      ListTag result = new ListTag();

      for (ManasSkillInstance candidate : candidates) {
         ManasSkillInstance copy = ManasSkillInstance.fromNBT(candidate.toNBT());
         copy.setRemoveTime(-1);
         copy.getOrCreateTag().putBoolean("NoMagiculeCost", true);
         result.add(copy.toNBT());
      }

      return result;
   }

   private static boolean copyFirstUniqueCandidate(LivingEntity to, ListTag candidates) {
      for (Tag raw : candidates) {
         if (raw instanceof CompoundTag tag) {
            ManasSkillInstance copy = ManasSkillInstance.fromNBT(tag.copy());
            ManasSkill skill = copy.getSkill();
            if (skill != null && !SkillAPI.getSkillsFrom(to).getSkill(skill).isPresent()) {
               copy.setRemoveTime(-1);
               copy.getOrCreateTag().putBoolean("NoMagiculeCost", true);
               if (SkillHelper.learnSkill(to, copy, -1)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   private static List<Holder<Attribute>> bodyStatList() {
      return List.of(
         Attributes.MAX_HEALTH,
         Attributes.ATTACK_DAMAGE,
         Attributes.ATTACK_SPEED,
         Attributes.MOVEMENT_SPEED,
         Attributes.KNOCKBACK_RESISTANCE,
         TensuraAttributes.MAX_SPIRITUAL_HEALTH
      );
   }

   private static void removeRaceModifiers(LivingEntity entity) {
      for (Holder<Attribute> attribute : BuiltInRegistries.ATTRIBUTE.holders().toList()) {
         AttributeInstance instance = entity.getAttribute(attribute);
         if (instance != null) {
            instance.removeModifier(TensuraRace.DEFAULT_RACE_ID);
         }
      }
   }

   private static void addModifier(LivingEntity entity, Holder<Attribute> attribute, ResourceLocation id, double amount, Operation operation) {
      AttributeInstance instance = entity.getAttribute(attribute);
      if (instance != null) {
         instance.addOrReplacePermanentModifier(new AttributeModifier(id, amount, operation));
      }
   }

   private static void removeModifier(LivingEntity entity, Holder<Attribute> attribute, ResourceLocation id) {
      AttributeInstance instance = entity.getAttribute(attribute);
      if (instance != null) {
         instance.removeModifier(id);
      }
   }

   private static void sendParasiteControls(ServerPlayer parasite) {
      MutableComponent message = Component.literal("Parasite controls: ")
         .withStyle(ChatFormatting.DARK_PURPLE)
         .append(commandButton("[LEAVE HOST]", "/parasite leave", ChatFormatting.RED));
      LivingEntity host = getBondedHost(parasite);
      if (host instanceof Mob) {
         message.append(Component.literal(" ")).append(commandButton("[TOGGLE NPC]", "/parasite npc toggle", ChatFormatting.LIGHT_PURPLE));
      }

      parasite.sendSystemMessage(message);
   }

   private static void sendHostControls(ServerPlayer host) {
      host.sendSystemMessage(
         Component.literal("Symbiote controls: ")
            .withStyle(ChatFormatting.LIGHT_PURPLE)
            .append(commandButton("[SYMBIOSIS]", "/parasite symbiosis", ChatFormatting.DARK_PURPLE))
            .append(Component.literal(" "))
            .append(commandButton("[EXPEL]", "/parasite expel", ChatFormatting.RED))
      );
   }

   private static void sendControlReclaim(ServerPlayer host) {
      host.sendSystemMessage(
         Component.literal("You are not in control. ")
            .withStyle(ChatFormatting.RED)
            .append(commandButton("[RECLAIM CONTROL]", "/parasite reclaim", ChatFormatting.GREEN))
      );
   }

   private static void sendControlRelease(ServerPlayer parasite) {
      parasite.sendSystemMessage(
         Component.literal("You control the host. ")
            .withStyle(ChatFormatting.DARK_PURPLE)
            .append(commandButton("[RELEASE CONTROL]", "/parasite release", ChatFormatting.GREEN))
            .append(Component.literal(" "))
            .append(commandButton("[LEAVE HOST]", "/parasite leave", ChatFormatting.RED))
      );
   }

   private static MutableComponent commandButton(String text, String command, ChatFormatting color) {
      return Component.literal(text).setStyle(Style.EMPTY.withColor(color).withClickEvent(new ClickEvent(Action.RUN_COMMAND, command)));
   }
}
