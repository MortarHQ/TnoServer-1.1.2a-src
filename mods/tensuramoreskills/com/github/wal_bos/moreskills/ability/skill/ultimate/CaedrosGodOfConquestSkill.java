package com.github.wal_bos.moreskills.ability.skill.ultimate;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.handler.UniqueOwnerSkillHandler;
import com.github.wal_bos.moreskills.menu.CaedrosAspectMenu;
import com.github.wal_bos.moreskills.menu.CaedrosReconstructMenu;
import com.github.wal_bos.moreskills.registry.TenaddexItems;
import com.mojang.datafixers.util.Pair;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.magic.MagicCircle;
import io.github.manasmods.tensura.entity.variant.MagicCircleVariant;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillGriefEvent;
import io.github.manasmods.tensura.particle.TensuraParticleType;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ability.AbilitySlot;
import io.github.manasmods.tensura.storage.ability.IAbility;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.AttributeHelper;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class CaedrosGodOfConquestSkill extends Skill {
   public static final int MODE_CONQUEST = 0;
   public static final int MODE_PLAGUE = 1;
   public static final int MODE_BANG = 2;
   public static final int MODE_CLOUD = 3;
   public static final int MODE_FANG = 4;
   public static final int MODE_CONSTRUCT = 5;
   private static final MoreSkillsConfigs.CaedrosGodOfConquest CONFIG = MoreSkillsConfigs.GRAND.caedrosGodOfConquest;
   private static final String TAG_CAEDROS_ASPECT_ACTIVE = "TenaddexCaedrosAspectActive";
   private static final String TAG_CAEDROS_TOTAL_WAR = "TenaddexCaedrosTotalWar";
   private static final String TAG_CONTROL_LOCKED_SKILL = "TenaddexControlLockedSkill";
   private static final String TAG_FAMINE_DEBUFFS = "TenaddexFamineDebuffs";
   private static final String TAG_FAMINE_UNTIL_TICK = "TenaddexFamineUntil";
   private static final String TAG_FAMINE_STACKS = "TenaddexFamineStacks";
   private static final String TAG_CONQUERED = "TenaddexConquered";
   private static final String TAG_WAR_OWNER_EP_BASE = "TenaddexWarOwnerBaseEP";
   private static final String TAG_WAR_TEMP_GAIN = "TenaddexWarTempGain";
   private static final String TAG_REPLICA_OWNER = "TenaddexReplicaOwner";
   private static final String TAG_CONTROL_BY = "TenaddexControlBy";
   private static final String TAG_CONTROL_PREVGM = "TenaddexControlPrevGM";
   private static final String TAG_FAMINE_BY = "TenaddexFamineBy";
   private static final String TAG_WAR_BY = "TenaddexWarBy";
   private static final String TAG_EATEN_SKILLS = "TenaddexEatenSkills";
   private static final String TAG_LAST_OUROBOROS_EAT = "TenaddexCaedrosLastOuroborosEat";
   private static final String TAG_LAST_HISS = "TenaddexCaedrosLastHiss";
   private static final String TAG_GUILT = "TenaddexCaedrosGuilt";
   private static final String TAG_CLOUD_RETURN_DIM = "TenaddexCloudReturnDimension";
   private static final String TAG_CLOUD_RETURN_X = "TenaddexCloudReturnX";
   private static final String TAG_CLOUD_RETURN_Y = "TenaddexCloudReturnY";
   private static final String TAG_CLOUD_RETURN_Z = "TenaddexCloudReturnZ";
   private static final String TAG_CAEDROS_LAST_KILLER = "TenaddexCaedrosLastKiller";
   private static final String TAG_CAEDROS_LAST_KILL_TIME = "TenaddexCaedrosLastKillTime";
   private static final String TAG_BANG_CASTING = "TenaddexCaedrosBangCasting";
   private static final String TAG_BANG_TICKS = "TenaddexCaedrosBangTicks";
   private static final String TAG_BANG_CIRCLE = "caedros_bang_circle";
   private static final String TAG_BANG_LAST_FIRE = "TenaddexCaedrosBangLastFire";
   private static final String TAG_CONQUEST_CIRCLE = "caedros_conquest_charge";
   private static final String TAG_LAST_GUILT_TICK = "TenaddexCaedrosLastGuiltTick";
   private static final String TAG_LAST_CONSTRUCT_TICK = "TenaddexCaedrosLastConstructTick";
   private static final ResourceLocation CAEDROS_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "caedros_god_of_conquest");
   private static final ResourceLocation PATHS_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "paths");
   private static final ResourceLocation TOGGLE_ATTACK_DAMAGE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "caedros_toggle_attack_damage");
   private static final ResourceLocation TOGGLE_ATTACK_SPEED = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "caedros_toggle_attack_speed");
   private static final ResourceLocation TOGGLE_KNOCKBACK_RESIST = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "caedros_toggle_knockback_resist");
   private static final ResourceLocation TOGGLE_PROJECTILE_DODGE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "caedros_toggle_projectile_dodge");
   private static final ResourceLocation TOGGLE_MELEE_DODGE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "caedros_toggle_melee_dodge");
   private static final ResourceLocation TOGGLE_MAGICULE = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "caedros_toggle_magicule");
   private static final ResourceLocation TOGGLE_AURA = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "caedros_toggle_aura");
   private static final Map<UUID, CaedrosGodOfConquestSkill.ConquestData> CONQUEST = new HashMap<>();
   private static final Map<UUID, List<CaedrosGodOfConquestSkill.SwordSpirit>> CONSTRUCT_SWORDS = new HashMap<>();
   private static CaedrosGodOfConquestSkill.CloudData CLOUD = null;

   public CaedrosGodOfConquestSkill() {
      super(SkillType.ULTIMATE);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/ultimate/caedros_god_of_conquest.png");
   }

   public int getAcquirementMastery(LivingEntity entity) {
      return (Integer)CONFIG.acquirementMastery.get();
   }

   public int getMaxMastery() {
      return (Integer)CONFIG.maxMastery.get();
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> ((Integer)CONFIG.conquestCost.get()).intValue();
         case 1 -> ((Integer)CONFIG.plagueCost.get()).intValue();
         case 2 -> ((Integer)CONFIG.bangCost.get()).intValue();
         case 3 -> ((Integer)CONFIG.cloudCost.get()).intValue();
         case 4 -> ((Integer)CONFIG.fangCost.get()).intValue();
         case 5 -> ((Integer)CONFIG.constructCost.get()).intValue();
         default -> 0.0;
      };
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return true;
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof ServerPlayer player && player.level() instanceof ServerLevel level) {
         level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WARDEN_HEARTBEAT, SoundSource.PLAYERS, 1.8F, 0.65F);
         level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.1F, 0.45F);
         level.sendParticles(
            (TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_EFFECT.get(), player.getX(), player.getY() + 1.0, player.getZ(), 40, 0.8, 0.9, 0.8, 0.01
         );
         level.sendParticles(
            (TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(),
            player.getX(),
            player.getY() + 1.0,
            player.getZ(),
            28,
            0.55,
            0.8,
            0.55,
            0.0
         );
         level.sendParticles(
            (TensuraParticleType)TensuraParticleTypes.BLACK_FIRE.get(), player.getX(), player.getY() + 0.85, player.getZ(), 16, 0.45, 0.35, 0.45, 0.0
         );
         shockwave(level, player.position().add(0.0, 0.25, 0.0), 5.0, 0.2, 28);
         applyToggledBuffs(player);
         player.displayClientMessage(Component.literal("Caedros enabled").withStyle(ChatFormatting.DARK_RED), true);
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof ServerPlayer player && player.level() instanceof ServerLevel level) {
         CaedrosGodOfConquestSkill.ConquestData conquest = CONQUEST.get(player.getUUID());
         if (conquest != null) {
            conquest.charging = false;
            conquest.chargeTicks = 0;
         }

         CONSTRUCT_SWORDS.remove(player.getUUID());
         player.getPersistentData().putBoolean("TenaddexCaedrosBangCasting", false);
         player.getPersistentData().putInt("TenaddexCaedrosBangTicks", 0);
         clearBangCircle(player);
         clearToggleBuffs(player);
         level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BEACON_DEACTIVATE, SoundSource.PLAYERS, 1.0F, 0.55F);
         level.sendParticles(
            (TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(), player.getX(), player.getY() + 1.0, player.getZ(), 12, 0.4, 0.6, 0.4, 0.0
         );
         player.displayClientMessage(Component.literal("Caedros disabled").withStyle(ChatFormatting.GRAY), true);
      }
   }

   public int getModes(ManasSkillInstance instance) {
      return 6;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "caedros.conquest";
         case 1 -> "caedros.plague";
         case 2 -> "caedros.bang";
         case 3 -> "caedros.cloud";
         case 4 -> "caedros.fang_serpent";
         case 5 -> "caedros.construct";
         default -> super.getModeId(instance, mode);
      };
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      int max = this.getModes(instance);
      if (mode < 0 || mode >= max) {
         mode = 0;
      }

      if (reverse) {
         return mode <= 0 ? max - 1 : mode - 1;
      } else {
         return mode >= max - 1 ? 0 : mode + 1;
      }
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return false;
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onLearnSkill(instance, entity);
      UniqueOwnerSkillHandler.learnExclusiveSkill(instance, entity);
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      UniqueOwnerSkillHandler.releaseSkill(instance, entity);
      if (entity instanceof ServerPlayer player) {
         player.getPersistentData().remove("TenaddexCaedrosGuilt");
         CONSTRUCT_SWORDS.remove(player.getUUID());
         CONQUEST.remove(player.getUUID());
         clearToggleBuffs(player);
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity instanceof ServerPlayer player) {
         if (player.level() instanceof ServerLevel level) {
            if (!player.getPersistentData().getBoolean("TenaddexConquered")) {
               if (instance.canInteractSkill(player)) {
                  if (!EnergyHelper.isOutOfEnergy(player, instance, mode)) {
                     if (player.isShiftKeyDown()) {
                        if (mode == 4) {
                           openReconstructMenu(player);
                        } else {
                           openAspectMenu(player);
                        }
                     } else {
                        switch (mode) {
                           case 0:
                              castConquest(player, level, instance, mode);
                              break;
                           case 1:
                              castPlague(player, level, instance, mode);
                              break;
                           case 2:
                              castBang(player, level, instance, mode);
                              break;
                           case 3:
                              castCloud(player, level, instance, mode);
                              break;
                           case 4:
                              castFangAndSerpent(player, level, instance, mode);
                              break;
                           case 5:
                              castConstruct(player, level, instance, mode);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static void tickGlobal(MinecraftServer server) {
      if (server != null) {
         for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            if (hasCaedros(player) && player.level() instanceof ServerLevel level) {
               ManasSkillInstance instance = getCaedrosInstance(player);
               if (instance != null) {
                  long time = level.getGameTime();
                  tickPassiveGlobal(player, instance, level, time);
                  tickConquestGlobal(player, instance, level);
                  tickBangGlobal(player, instance, level, time);
                  tickConstructGlobal(player, instance, level, time);
                  CaedrosGodOfConquestSkill.CaedrosAspect aspect = getActiveAspect(player);
                  if (aspect == CaedrosGodOfConquestSkill.CaedrosAspect.CONTROL) {
                     tickControl(player, level);
                  }

                  if (aspect == CaedrosGodOfConquestSkill.CaedrosAspect.FAMINE) {
                     tickFamine(player, level);
                  }

                  if (aspect == CaedrosGodOfConquestSkill.CaedrosAspect.WAR) {
                     tickWar(player, level);
                  }

                  if (aspect == CaedrosGodOfConquestSkill.CaedrosAspect.DEATH) {
                     tickDeath(player, level);
                  }
               }
            }
         }

         tickCloud(server);
      }
   }

   public static void openAspectMenu(ServerPlayer player) {
      List<String> entries = getLockableSkillIds(player);
      int current = getActiveAspect(player) == null ? -1 : getActiveAspect(player).ordinal();
      player.openMenu(
         new SimpleMenuProvider((containerId, inventory, p) -> new CaedrosAspectMenu(containerId, inventory, null), Component.literal("Caedros Aspects")),
         buf -> {
            buf.writeInt(current);
            buf.writeVarInt(entries.size());

            for (String entry : entries) {
               buf.writeUtf(entry);
            }
         }
      );
   }

   public static void openReconstructMenu(ServerPlayer player) {
      List<String> entries = getEatenSkills(player);
      player.openMenu(
         new SimpleMenuProvider((containerId, inventory, p) -> new CaedrosReconstructMenu(containerId, inventory, null), Component.literal("Reconstruct")),
         buf -> {
            buf.writeDouble(getGuilt(player));
            buf.writeDouble(getEP(player));
            buf.writeVarInt(entries.size());

            for (String entry : entries) {
               buf.writeUtf(entry);
            }
         }
      );
   }

   public static double getEP(LivingEntity entity) {
      return TensuraStorages.getExistenceFrom(entity).getEP();
   }

   public static void setEP(LivingEntity entity, double value) {
      IExistence existence = TensuraStorages.getExistenceFrom(entity);
      existence.setEP(Math.max(0.0, value));
      existence.markDirty();
   }

   public static CaedrosGodOfConquestSkill.CaedrosAspect getActiveAspect(ServerPlayer player) {
      if (!player.getPersistentData().contains("TenaddexCaedrosAspectActive")) {
         return null;
      }

      int v = player.getPersistentData().getInt("TenaddexCaedrosAspectActive");
      return v >= 0 && v < CaedrosGodOfConquestSkill.CaedrosAspect.values().length ? CaedrosGodOfConquestSkill.CaedrosAspect.values()[v] : null;
   }

   public static void setActiveAspect(ServerPlayer player, CaedrosGodOfConquestSkill.CaedrosAspect aspect) {
      CaedrosGodOfConquestSkill.CaedrosAspect current = getActiveAspect(player);
      if (current != null) {
         onAspectDisable(player, current);
      }

      if (aspect == null) {
         player.getPersistentData().putInt("TenaddexCaedrosAspectActive", -1);
         player.getPersistentData().putBoolean("TenaddexCaedrosTotalWar", false);
         player.getPersistentData().remove("TenaddexControlLockedSkill");
         double base = player.getPersistentData().getDouble("TenaddexWarOwnerBaseEP");
         if (base > 0.0) {
            setEP(player, base);
         }

         player.getPersistentData().remove("TenaddexWarOwnerBaseEP");
         player.getPersistentData().remove("TenaddexWarTempGain");
      } else {
         player.getPersistentData().putInt("TenaddexCaedrosAspectActive", aspect.ordinal());
         onAspectEnable(player, aspect);
         player.displayClientMessage(Component.literal("Aspect: " + aspect.name()).withStyle(ChatFormatting.DARK_RED), true);
      }
   }

   public static void setControlLockedSkill(ServerPlayer player, String id) {
      if (id != null && !id.isBlank() && !"OFF".equalsIgnoreCase(id)) {
         player.getPersistentData().putString("TenaddexControlLockedSkill", id);
         player.displayClientMessage(Component.literal("CONTROL locked -> " + id).withStyle(ChatFormatting.DARK_PURPLE), true);
      } else {
         player.getPersistentData().remove("TenaddexControlLockedSkill");
         player.displayClientMessage(Component.literal("CONTROL lock cleared").withStyle(ChatFormatting.GRAY), true);
      }
   }

   public static String getControlLockedSkill(ServerPlayer player) {
      String s = player.getPersistentData().getString("TenaddexControlLockedSkill");
      return s != null && !s.isBlank() ? s : null;
   }

   public static void rebuyEatenSkillWithGuilt(ServerPlayer player, String id) {
      if (id != null && !id.isBlank()) {
         List<String> eaten = getEatenSkills(player);
         if (eaten.contains(id)) {
            ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(ResourceLocation.parse(id));
            if (skill != null) {
               double cost = Math.max(
                  1.0,
                  (Double)CONFIG.reconstructBaseGuiltCost.get()
                     / isMasteredCaedros(player) ? (Double)CONFIG.reconstructGuiltDivisorMastered.get() : (Double)CONFIG.reconstructGuiltDivisor.get()
               );
               if (consumeGuilt(player, cost)) {
                  if (SkillHelper.learnSkill(player, skill.createDefaultInstance())) {
                     eaten.remove(id);
                     saveEatenSkills(player, eaten);
                  } else {
                     addGuilt(player, cost);
                  }
               }
            }
         }
      }
   }

   public static void rebuyEatenSkillWithEP(ServerPlayer player, String id) {
      if (id != null && !id.isBlank()) {
         List<String> eaten = getEatenSkills(player);
         if (eaten.contains(id)) {
            ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(ResourceLocation.parse(id));
            if (skill != null) {
               double cost = (Double)CONFIG.reconstructEpCost.get();
               if (!(getEP(player) < cost)) {
                  setEP(player, getEP(player) - cost);
                  if (SkillHelper.learnSkill(player, skill.createDefaultInstance())) {
                     eaten.remove(id);
                     saveEatenSkills(player, eaten);
                  } else {
                     setEP(player, getEP(player) + cost);
                  }
               }
            }
         }
      }
   }

   public static void onPlayerDeath(ServerPlayer deadPlayer) {
      if (deadPlayer != null) {
         if (!hasCaedros(deadPlayer) || !tryDeathRevive(deadPlayer)) {
            if (CLOUD != null && CLOUD.active) {
               MinecraftServer server = deadPlayer.getServer();
               if (server == null) {
                  return;
               }

               if (deadPlayer.getUUID().equals(CLOUD.ownerId)) {
                  if ((Boolean)CONFIG.cloudStripsCaedrosIfOwnerDies.get()) {
                     stripCaedros(deadPlayer);
                  }

                  endCloudSession(server, true);
                  return;
               }

               if ((Boolean)CONFIG.cloudRespawnsVictimsInsidePaths.get()
                  && deadPlayer.level() instanceof ServerLevel serverLevel
                  && serverLevel.dimension().location().equals(PATHS_ID)) {
                  deadPlayer.setHealth(deadPlayer.getMaxHealth());
                  deadPlayer.removeAllEffects();
                  teleportToPaths(server.getLevel(ResourceKey.create(Registries.DIMENSION, PATHS_ID)), deadPlayer);
               }
            }
         }
      }
   }

   public static void onLivingKilledByCaedros(LivingEntity victim, DamageSource source) {
      if (victim != null && source != null) {
         if (source.getEntity() instanceof ServerPlayer player) {
            if (hasCaedros(player)) {
               if (player.level() instanceof ServerLevel level) {
                  victim.getPersistentData().putUUID("TenaddexCaedrosLastKiller", player.getUUID());
                  victim.getPersistentData().putLong("TenaddexCaedrosLastKillTime", level.getGameTime());
               }
            }
         }
      }
   }

   public static void onLivingDeathConstruct(ServerLevel level, LivingEntity dead) {
      if (dead != null) {
         if (dead.getPersistentData().hasUUID("TenaddexCaedrosLastKiller")) {
            UUID killerId = dead.getPersistentData().getUUID("TenaddexCaedrosLastKiller");
            long markTime = dead.getPersistentData().getLong("TenaddexCaedrosLastKillTime");
            if (level.getGameTime() - markTime <= 10L) {
               ServerPlayer killer = level.getServer().getPlayerList().getPlayer(killerId);
               if (killer != null) {
                  if (hasCaedros(killer)) {
                     if (hasCaedrosToggled(killer)) {
                        queueConstructSword(killer, getCaedrosInstance(killer));
                     }
                  }
               }
            }
         }
      }
   }

   public static void onItemToss(ServerPlayer player, ItemEntity entity) {
      if (player != null && entity != null) {
         if (player.level() instanceof ServerLevel level) {
            CaedrosGodOfConquestSkill.ConquestData data = CONQUEST.get(player.getUUID());
            if (data != null && data.hasSummoned) {
               ItemStack stack = entity.getItem();
               if (isHalberdStack(stack)) {
                  entity.discard();
                  if (player.getMainHandItem().isEmpty()) {
                     player.setItemInHand(InteractionHand.MAIN_HAND, stack.copy());
                  }

                  Vec3 look = player.getLookAngle().normalize();
                  player.setDeltaMovement(look.scale(2.25).add(0.0, 0.25, 0.0));
                  player.hurtMarked = true;
                  level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 2.0F, 0.55F);
                  level.sendParticles(
                     (TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_EFFECT.get(),
                     player.getX(),
                     player.getY() + 1.0,
                     player.getZ(),
                     30,
                     0.7,
                     0.7,
                     0.7,
                     0.01
                  );
                  shockwave(level, player.position().add(0.0, 0.2, 0.0), 10.0, 0.35, 40);
                  AABB area = player.getBoundingBox().inflate(((Integer)CONFIG.conquestSweepRadius.get()).intValue());

                  for (LivingEntity ent : level.getEntitiesOfClass(LivingEntity.class, area, e -> e.isAlive() && e != player)) {
                     dealTrueDamage(
                        player,
                        ent,
                        trueCaedrosSource(player, TensuraDamageTypes.MAGIC_GENERIC),
                        applyFinalCaedrosDamage(player, ((Double)CONFIG.conquestSweepDamage.get()).floatValue())
                     );
                     knockAway(player, ent, 1.5, 0.25);
                     onAspectHit(player, level, ent);
                  }
               }
            }
         }
      }
   }

   public static void onTargetHurtByCaedros(ServerPlayer owner, LivingEntity target) {
      if (owner != null && target != null) {
         if (owner.level() instanceof ServerLevel level) {
            if (hasCaedros(owner)) {
               if (getActiveAspect(owner) == CaedrosGodOfConquestSkill.CaedrosAspect.FAMINE && target != owner) {
                  applyFamineOnHit(owner, level, target);
               }

               if (hasCaedrosToggled(owner)
                  && target.hasEffect(TensuraMobEffects.getReference(TensuraMobEffects.INSANITY))
                  && owner.getRandom().nextFloat() < 0.6F) {
                  target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE), 80, 2, false, false, true));
                  target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.BURDEN), 80, 1, false, false, true));
               }
            }
         }
      }
   }

   private static void tickPassiveGlobal(ServerPlayer player, ManasSkillInstance instance, ServerLevel level, long time) {
      handleTruePassive(player, time);
      handleFearInsanityAura(player, level, time);
      boolean toggled = instance.isToggled();
      if (time % 20L != 0L) {
         if (!toggled) {
            clearToggleBuffs(player);
         }
      } else {
         double before = getGuilt(player);
         if (toggled) {
            double drain = computeToggledGuiltDrain(player);
            boolean ok = consumeGuilt(player, drain);
            if (ok) {
               applyToggledBuffs(player);
            } else {
               player.getPersistentData().putDouble("TenaddexCaedrosGuilt", 0.0);
               clearToggleBuffs(player);
               handleOuroboros(player, level, time);
            }
         } else {
            clearToggleBuffs(player);
            double gain = computePassiveGuiltGain(player);
            addGuilt(player, gain);
         }

         if (toggled && getGuilt(player) > 0.0) {
            double tier = Math.min(((Integer)CONFIG.maxGuiltTier.get()).intValue(), getGuilt(player) / (Double)CONFIG.guiltTierDivisor.get());
            level.sendParticles(
               (TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(),
               player.getX(),
               player.getY() + 1.05,
               player.getZ(),
               6 + (int)tier,
               0.35,
               0.55,
               0.35,
               0.0
            );
            level.sendParticles(
               (TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(),
               player.getX(),
               player.getY() + 1.12,
               player.getZ(),
               4 + (int)(tier / 2.0),
               0.24,
               0.42,
               0.24,
               0.0
            );
            level.sendParticles(
               (TensuraParticleType)TensuraParticleTypes.SOUL.get(),
               player.getX(),
               player.getY() + 0.95,
               player.getZ(),
               1 + (int)(tier / 3.0),
               0.12,
               0.25,
               0.12,
               0.0
            );
         }

         if (time % 40L == 0L) {
            handleOuroboros(player, level, time);
         }

         if (time % 80L == 0L) {
            addMasteryAmbient(instance, player);
         }

         if (before != getGuilt(player)) {
            player.getPersistentData().putLong("TenaddexCaedrosLastGuiltTick", time);
         }
      }
   }

   private static void tickConquestGlobal(ServerPlayer player, ManasSkillInstance instance, ServerLevel level) {
      CaedrosGodOfConquestSkill.ConquestData data = CONQUEST.get(player.getUUID());
      if (data != null && data.charging) {
         if (!isHoldingHalberd(player)) {
            data.charging = false;
            data.chargeTicks = 0;
            instance.getOrCreateTag().putInt("caedros_conquest_charge", 0);
         } else {
            if (data.chargeTicks > 0) {
               data.chargeTicks--;
               if (data.chargeTicks % 10 == 0) {
                  level.sendParticles(
                     (TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(),
                     player.getX(),
                     player.getY() + 1.1,
                     player.getZ(),
                     14,
                     0.35,
                     0.5,
                     0.35,
                     0.0
                  );
                  level.sendParticles(
                     (TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(),
                     player.getX(),
                     player.getY() + 1.15,
                     player.getZ(),
                     7,
                     0.25,
                     0.4,
                     0.25,
                     0.0
                  );
                  level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BEACON_POWER_SELECT, SoundSource.PLAYERS, 0.8F, 0.5F);
               }

               if (data.chargeTicks == 0) {
                  doConquestRelease(player, level);
                  data.charging = false;
                  instance.getOrCreateTag().putInt("caedros_conquest_charge", 0);
               }

               instance.addMasteryPoint(player);
            }
         }
      }
   }

   private static void tickBangGlobal(ServerPlayer player, ManasSkillInstance instance, ServerLevel level, long time) {
      if (player.getPersistentData().getBoolean("TenaddexCaedrosBangCasting")) {
         int ticksLeft = player.getPersistentData().getInt("TenaddexCaedrosBangTicks");
         if (ticksLeft > 0) {
            player.getPersistentData().putInt("TenaddexCaedrosBangTicks", --ticksLeft);
            MagicCircle.castMagicCircle(
               "caedros_bang_circle",
               3.8F,
               10,
               MagicCircleVariant.DEMON,
               true,
               player,
               instance.getOrCreateTag(),
               1.6F,
               96.0F,
               new Vec3(0.0, 0.15, 0.0),
               instance,
               2,
               Pair.of(0.0, 0.0)
            );
            renderCaedrosCircleFlavor(level, player, 3.8);
            renderChargeBeam(level, player, Math.min(18, (Integer)CONFIG.bangRange.get() / 8), ticksLeft);
         } else {
            fireBangBeam(player, level, instance);
            player.getPersistentData().putBoolean("TenaddexCaedrosBangCasting", false);
            player.getPersistentData().putInt("TenaddexCaedrosBangTicks", 0);
            player.getPersistentData().putLong("TenaddexCaedrosBangLastFire", time);
            clearBangCircle(player);
            instance.addMasteryPoint(player);
         }
      }
   }

   private static void tickConstructGlobal(ServerPlayer player, ManasSkillInstance instance, ServerLevel level, long time) {
      List<CaedrosGodOfConquestSkill.SwordSpirit> current = CONSTRUCT_SWORDS.get(player.getUUID());
      if (current != null && !current.isEmpty()) {
         List<CaedrosGodOfConquestSkill.SwordSpirit> snapshot = new ArrayList<>(current);
         int count = snapshot.size();
         double radius = 2.05;

         for (int i = 0; i < count; i++) {
            CaedrosGodOfConquestSkill.SwordSpirit sword = snapshot.get(i);
            double angle = (time * 7.5 + 360.0 / Math.max(1, count) * i) * (Math.PI / 180.0);
            double x = player.getX() + Math.cos(angle) * radius;
            double y = player.getY() + 1.2 + Math.sin((time + i * 6.0) * 0.16) * 0.3;
            double z = player.getZ() + Math.sin(angle) * radius;
            Vec3 now = new Vec3(x, y, z);
            Vec3 prev = sword.lastOrbitPos == null ? now : sword.lastOrbitPos;
            int trailSteps = 4;

            for (int t = 0; t <= trailSteps; t++) {
               Vec3 p = prev.lerp(now, (double)t / trailSteps);
               level.sendParticles(new DustParticleOptions(new Vector3f(0.12F, 0.0F, 0.0F), 1.2F), p.x, p.y, p.z, 1, 0.0, 0.0, 0.0, 0.0);
               if (t % 2 == 0) {
                  level.sendParticles((TensuraParticleType)TensuraParticleTypes.SOUL.get(), p.x, p.y, p.z, 1, 0.0, 0.0, 0.0, 0.0);
               }
            }

            level.sendParticles(ParticleTypes.SWEEP_ATTACK, x, y, z, 1, 0.02, 0.02, 0.02, 0.0);
            sword.lastOrbitPos = now;
         }

         if (time % ((Integer)CONFIG.constructAttackIntervalTicks.get()).intValue() != 0L) {
            CONSTRUCT_SWORDS.put(player.getUUID(), snapshot);
         } else {
            int attackRange = instance != null && instance.isMastered(player)
               ? (Integer)CONFIG.constructMasterRange.get()
               : (Integer)CONFIG.constructBaseRange.get();
            AABB area = player.getBoundingBox().inflate(attackRange);
            List<LivingEntity> candidates = level.getEntitiesOfClass(LivingEntity.class, area, e -> e.isAlive() && e != player);
            if (candidates.isEmpty()) {
               CONSTRUCT_SWORDS.put(player.getUUID(), snapshot);
            } else {
               List<CaedrosGodOfConquestSkill.SwordSpirit> survivors = new ArrayList<>();
               int pendingAdds = 0;

               for (CaedrosGodOfConquestSkill.SwordSpirit sword : snapshot) {
                  LivingEntity target = candidates.get(level.random.nextInt(candidates.size()));
                  Vec3 from = sword.lastOrbitPos == null ? player.position().add(0.0, 1.0, 0.0) : sword.lastOrbitPos;
                  Vec3 to = target.position().add(0.0, target.getBbHeight() * 0.5, 0.0);
                  Vec3 delta = to.subtract(from);
                  int steps = Math.max(8, Math.min(18, (int)Math.ceil(delta.length() * 1.5)));

                  for (int s = 0; s <= steps; s++) {
                     double f = (double)s / steps;
                     Vec3 p = from.lerp(to, f);
                     level.sendParticles(new DustParticleOptions(new Vector3f(0.35F, 0.02F, 0.02F), 1.0F), p.x, p.y, p.z, 1, 0.0, 0.0, 0.0, 0.0);
                     if (s % 2 == 0) {
                        level.sendParticles((TensuraParticleType)TensuraParticleTypes.SOUL.get(), p.x, p.y, p.z, 1, 0.0, 0.0, 0.0, 0.0);
                     }

                     if (s % 3 == 0) {
                        level.sendParticles(ParticleTypes.SWEEP_ATTACK, p.x, p.y, p.z, 1, 0.0, 0.0, 0.0, 0.0);
                     }
                  }

                  level.sendParticles(
                     (TensuraParticleType)TensuraParticleTypes.SOUL.get(),
                     target.getX(),
                     target.getY() + target.getBbHeight() * 0.5,
                     target.getZ(),
                     5,
                     0.15,
                     0.15,
                     0.15,
                     0.0
                  );
                  level.sendParticles(
                     (TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_EFFECT.get(),
                     target.getX(),
                     target.getY() + target.getBbHeight() * 0.5,
                     target.getZ(),
                     2,
                     0.06,
                     0.06,
                     0.06,
                     0.0
                  );
                  level.sendParticles(
                     (TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(),
                     target.getX(),
                     target.getY() + target.getBbHeight() * 0.5,
                     target.getZ(),
                     4,
                     0.16,
                     0.16,
                     0.16,
                     0.0
                  );
                  level.playSound(
                     null,
                     target.getX(),
                     target.getY(),
                     target.getZ(),
                     SoundEvents.PLAYER_ATTACK_SWEEP,
                     SoundSource.PLAYERS,
                     0.8F,
                     0.8F + level.random.nextFloat() * 0.2F
                  );
                  boolean wasAlive = target.isAlive();
                  float spiritDamage = sword.masteryBonus
                     ? ((Double)CONFIG.constructSpiritMasteredDamage.get()).floatValue()
                     : ((Double)CONFIG.constructSpiritBaseDamage.get()).floatValue();
                  dealTrueDamage(player, target, trueCaedrosSource(player, TensuraDamageTypes.DEATH_WISH), applyFinalCaedrosDamage(player, spiritDamage));
                  applyConstructDebuffs(target, sword.masteryBonus);
                  knockAway(player, target, 0.75, 0.15);
                  onAspectHit(player, level, target);
                  if (wasAlive && !target.isAlive()) {
                     pendingAdds++;
                  }

                  sword.usesLeft--;
                  if (sword.usesLeft > 0) {
                     survivors.add(sword);
                  } else {
                     addGuilt(player, (Double)CONFIG.constructSwordBreakGuilt.get());
                     level.sendParticles(
                        (TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(),
                        target.getX(),
                        target.getY() + 0.9,
                        target.getZ(),
                        6,
                        0.18,
                        0.18,
                        0.18,
                        0.0
                     );
                  }
               }

               for (int i = 0; i < pendingAdds; i++) {
                  pushConstructSword(survivors, player, instance);
               }

               if (survivors.isEmpty()) {
                  CONSTRUCT_SWORDS.remove(player.getUUID());
               } else {
                  CONSTRUCT_SWORDS.put(player.getUUID(), survivors);
               }

               player.getPersistentData().putLong("TenaddexCaedrosLastConstructTick", time);
            }
         }
      }
   }

   private static void castFangAndSerpent(ServerPlayer player, ServerLevel level, ManasSkillInstance instance, int mode) {
      level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SILVERFISH_HURT, SoundSource.PLAYERS, 1.5F, 0.45F);
      level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.DRAGON_FIREBALL_EXPLODE, SoundSource.PLAYERS, 1.2F, 0.55F);
      MagicCircle.castMagicCircle(
         "caedros_hiss_circle",
         2.6F,
         18,
         player.position().add(0.0, 0.15, 0.0),
         MagicCircleVariant.DEMON,
         true,
         player,
         instance.getOrCreateTag(),
         instance,
         mode,
         Pair.of(0.0, 0.0)
      );
      renderCaedrosCircleFlavor(level, player, 2.6);
      level.sendParticles(
         (TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_EFFECT.get(), player.getX(), player.getY() + 1.0, player.getZ(), 24, 1.0, 0.9, 1.0, 0.01
      );
      level.sendParticles((TensuraParticleType)TensuraParticleTypes.BLACK_FIRE.get(), player.getX(), player.getY() + 0.8, player.getZ(), 22, 0.6, 0.4, 0.6, 0.0);
      level.sendParticles(
         (TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(), player.getX(), player.getY() + 0.95, player.getZ(), 14, 0.5, 0.45, 0.5, 0.0
      );
      swingBurst(level, player.position().add(0.0, 1.0, 0.0), 7.0, 24);
      boolean toggled = hasCaedrosToggled(player);

      for (LivingEntity target : level.getEntitiesOfClass(
         LivingEntity.class, player.getBoundingBox().inflate(((Integer)CONFIG.fangRadius.get()).intValue()), e -> e.isAlive() && e != player
      )) {
         if (toggled) {
            target.addEffect(
               new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.FEAR), (Integer)CONFIG.fangFearDurationTicks.get(), 1, false, false, true)
            );
            target.addEffect(
               new MobEffectInstance(
                  TensuraMobEffects.getReference(TensuraMobEffects.INSANITY), (Integer)CONFIG.fangInsanityDurationTicks.get(), 1, false, false, true
               )
            );
         }

         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.BURDEN), (Integer)CONFIG.fangBurdenDurationTicks.get(), 1, false, false, true
            )
         );
         target.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE),
               (Integer)CONFIG.fangMagicInterferenceDurationTicks.get(),
               1,
               false,
               false,
               true
            )
         );
         disableOrDeleteRandomSkills(
            target,
            instance.isMastered(player) ? (Integer)CONFIG.fangSkillDisableAmountMastered.get() : (Integer)CONFIG.fangSkillDisableAmount.get(),
            (Boolean)CONFIG.fangCanDisableUniqueSkills.get(),
            (Boolean)CONFIG.fangCanDisableUltimateSkills.get(),
            (Boolean)CONFIG.fangCanPermanentlyDeleteSkills.get()
         );
         level.sendParticles(
            (TensuraParticleType)TensuraParticleTypes.BLACK_FIRE.get(), target.getX(), target.getY() + 0.7, target.getZ(), 8, 0.25, 0.35, 0.25, 0.0
         );
         level.sendParticles((TensuraParticleType)TensuraParticleTypes.SOUL.get(), target.getX(), target.getY() + 0.9, target.getZ(), 2, 0.12, 0.25, 0.12, 0.0);
      }

      player.displayClientMessage(Component.literal("Guilt: " + (long)getGuilt(player)).withStyle(ChatFormatting.DARK_RED), true);
      instance.setCoolDown((Integer)CONFIG.fangCooldownTicks.get(), mode);
      instance.addMasteryPoint(player);
   }

   private static void castConquest(ServerPlayer player, ServerLevel level, ManasSkillInstance instance, int mode) {
      CaedrosGodOfConquestSkill.ConquestData data = CONQUEST.computeIfAbsent(player.getUUID(), k -> new CaedrosGodOfConquestSkill.ConquestData());
      if (!data.hasSummoned || !isHoldingHalberd(player)) {
         ItemStack halberd = new ItemStack((ItemLike)TenaddexItems.CAEDROS_ASHEN_HALBERD.get());
         player.setItemInHand(InteractionHand.MAIN_HAND, halberd);
         data.hasSummoned = true;
         data.firedMiniBang = false;
         data.charging = false;
         data.chargeTicks = 0;
         level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 2.0F, 0.45F);
         level.sendParticles(
            (TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_EFFECT.get(),
            player.getX(),
            player.getY() + 1.0,
            player.getZ(),
            65,
            0.95,
            0.95,
            0.95,
            0.01
         );
         level.sendParticles(
            (TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(),
            player.getX(),
            player.getY() + 1.0,
            player.getZ(),
            28,
            0.45,
            0.8,
            0.45,
            0.0
         );
         shockwave(level, player.position().add(0.0, 0.15, 0.0), 4.0, 0.15, 22);
         instance.setCoolDown((Integer)CONFIG.conquestSummonCooldownTicks.get(), mode);
      } else if (!data.firedMiniBang) {
         fireConquestMiniBang(player, level);
         data.firedMiniBang = true;
         instance.setCoolDown((Integer)CONFIG.conquestMiniBangCooldownTicks.get(), mode);
      } else if (!data.charging) {
         doConquestSweep(player, level);
         data.charging = true;
         data.chargeTicks = (Integer)CONFIG.conquestChargeTicks.get();
         level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BEACON_ACTIVATE, SoundSource.PLAYERS, 1.8F, 0.55F);
         MagicCircle.castMagicCircle(
            "caedros_conquest_charge",
            2.0F,
            (Integer)CONFIG.conquestChargeTicks.get() + 8,
            player.position().add(0.0, 1.15, 0.0),
            MagicCircleVariant.DEMON,
            true,
            player,
            instance.getOrCreateTag(),
            instance,
            mode,
            Pair.of(0.0, 0.0)
         );
         renderCaedrosCircleFlavor(level, player, 2.0);
         instance.setCoolDown((Integer)CONFIG.conquestSweepCooldownTicks.get(), mode);
      } else {
         doConquestRelease(player, level);
         data.charging = false;
         data.chargeTicks = 0;
         instance.getOrCreateTag().putInt("caedros_conquest_charge", 0);
         instance.setCoolDown((Integer)CONFIG.conquestReleaseCooldownTicks.get(), mode);
      }
   }

   private static void fireConquestMiniBang(ServerPlayer player, ServerLevel level) {
      Vec3 start = player.position().add(0.0, player.getBbHeight() * 0.65, 0.0);
      Vec3 dir = player.getLookAngle().normalize();
      level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 2.2F, 1.2F);
      level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.4F, 1.4F);

      for (int i = 0; i <= CONFIG.conquestMiniBangRange.get(); i++) {
         Vec3 p = start.add(dir.scale(i));
         level.sendParticles((TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(), p.x, p.y, p.z, 7, 0.12, 0.12, 0.12, 0.0);
         level.sendParticles((TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(), p.x, p.y, p.z, 4, 0.08, 0.08, 0.08, 0.0);
         level.sendParticles((TensuraParticleType)TensuraParticleTypes.SOUL.get(), p.x, p.y, p.z, 1, 0.05, 0.05, 0.05, 0.0);
         if (i % 2 == 0) {
            level.sendParticles(TensuraParticleUtils.getRedWave(0.9F, 1.3F, -0.1F, false), p.x, p.y, p.z, 1, 0.0, 0.0, 0.0, 0.0);
         }
      }

      AABB area = player.getBoundingBox().inflate((Integer)CONFIG.conquestMiniBangRange.get() + (Integer)CONFIG.conquestMiniBangRadius.get());

      for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, area, x -> x.isAlive() && x != player)) {
         Vec3 rel = e.position().subtract(start);
         double along = rel.dot(dir);
         if (!(along < 0.0) && !(along > ((Integer)CONFIG.conquestMiniBangRange.get()).intValue())) {
            Vec3 closest = start.add(dir.scale(along));
            double dist = e.position().distanceTo(closest);
            if (!(dist > ((Integer)CONFIG.conquestMiniBangRadius.get()).intValue() + Math.max(2.0, e.getBbWidth()))) {
               dealTrueDamage(
                  player,
                  e,
                  trueCaedrosSource(player, TensuraDamageTypes.DISINTEGRATION),
                  applyFinalCaedrosDamage(player, ((Double)CONFIG.conquestMiniBangDamage.get()).floatValue())
               );
               knockAway(player, e, 1.8, 0.25);
               onAspectHit(player, level, e);
            }
         }
      }
   }

   private static void doConquestSweep(ServerPlayer player, ServerLevel level) {
      level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 2.0F, 0.6F);
      level.sendParticles(
         (TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_EFFECT.get(), player.getX(), player.getY() + 1.0, player.getZ(), 16, 0.5, 0.6, 0.5, 0.01
      );
      level.sendParticles(
         (TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(), player.getX(), player.getY() + 1.0, player.getZ(), 12, 0.4, 0.45, 0.4, 0.0
      );
      swingBurst(level, player.position().add(0.0, 1.0, 0.0), ((Integer)CONFIG.conquestSweepRadius.get()).intValue(), 24);
      shockwave(level, player.position().add(0.0, 0.2, 0.0), ((Integer)CONFIG.conquestSweepRadius.get()).intValue(), 0.25, 36);
      AABB area = player.getBoundingBox().inflate(((Integer)CONFIG.conquestSweepRadius.get()).intValue());

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, area, e -> e.isAlive() && e != player)) {
         dealTrueDamage(
            player,
            target,
            trueCaedrosSource(player, TensuraDamageTypes.MAGIC_GENERIC),
            applyFinalCaedrosDamage(player, ((Double)CONFIG.conquestSweepDamage.get()).floatValue())
         );
         knockAway(player, target, 1.8, 0.3);
         onAspectHit(player, level, target);
      }
   }

   private static void doConquestRelease(ServerPlayer player, ServerLevel level) {
      Vec3 look = player.getLookAngle().normalize();
      Vec3 start = player.position().add(0.0, player.getBbHeight() * 0.65, 0.0);
      float damage = applyFinalCaedrosDamage(player, ((Double)CONFIG.conquestChargedDamage.get()).floatValue());
      level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 2.0F, 0.4F);
      level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 2.5F, 0.7F);

      for (int i = 1; i <= CONFIG.conquestChargedRange.get(); i++) {
         Vec3 p = start.add(look.scale(i));
         level.sendParticles(ParticleTypes.SWEEP_ATTACK, p.x, p.y, p.z, 2, 0.15, 0.05, 0.15, 0.0);
         level.sendParticles((TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(), p.x, p.y, p.z, 5, 0.12, 0.12, 0.12, 0.0);
         if (i % 2 == 0) {
            level.sendParticles((TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(), p.x, p.y, p.z, 3, 0.08, 0.08, 0.08, 0.0);
         }
      }

      AABB box = player.getBoundingBox().inflate(((Integer)CONFIG.conquestChargedRange.get()).intValue());

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, box, e -> e.isAlive() && e != player)) {
         Vec3 to = target.position().subtract(player.position());
         if (!(to.lengthSqr() < 1.0E-4) && !(to.normalize().dot(look) < 0.2)) {
            dealTrueDamage(player, target, trueCaedrosSource(player, TensuraDamageTypes.MAGIC_GENERIC), damage);
            knockAway(player, target, 3.0, 0.55);
            onAspectHit(player, level, target);
         }
      }

      shockwave(level, start, 12.0, 0.35, 60);
   }

   private static void castPlague(ServerPlayer player, ServerLevel level, ManasSkillInstance instance, int mode) {
      boolean mastered = instance.isMastered(player);
      int slashes = mastered ? (Integer)CONFIG.plagueSlashesMastered.get() : (Integer)CONFIG.plagueSlashes.get();
      double reach = mastered ? (Double)CONFIG.plagueReachMastered.get() : (Double)CONFIG.plagueReach.get();
      double width = mastered ? (Double)CONFIG.plagueWidthMastered.get() : (Double)CONFIG.plagueWidth.get();
      float damage = applyFinalCaedrosDamage(
         player, mastered ? ((Double)CONFIG.plagueMasteredDamage.get()).floatValue() : ((Double)CONFIG.plagueBaseDamage.get()).floatValue()
      );
      level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 2.2F, 0.55F);
      level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BEEHIVE_DRIP, SoundSource.PLAYERS, 1.4F, 0.55F);
      level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WITHER_SHOOT, SoundSource.PLAYERS, 1.2F, 0.65F);
      MagicCircle.castMagicCircle(
         "caedros_plague_circle",
         2.8F,
         16,
         MagicCircleVariant.DEMON,
         true,
         player,
         instance.getOrCreateTag(),
         1.25F,
         24.0F,
         new Vec3(0.0, 0.0, 0.0),
         instance,
         mode,
         Pair.of(0.0, 0.0)
      );
      renderCaedrosCircleFlavor(level, player, 2.8);
      Vec3 eye = player.getEyePosition();
      Vec3 look = player.getLookAngle().normalize();

      for (int i = 0; i < slashes; i++) {
         double step = 3.0 + i * (reach / slashes);
         Vec3 center = eye.add(look.scale(step));
         plagueSlashBurst(level, center, width + i * 0.18, 18 + i);

         for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(reach + 8.0), e -> e.isAlive() && e != player)) {
            Vec3 rel = target.position().subtract(eye);
            double along = rel.dot(look);
            if (!(along < step - 2.5) && !(along > step + 2.5)) {
               Vec3 closest = eye.add(look.scale(along));
               double dist = target.position().distanceTo(closest);
               if (!(dist > width + Math.max(1.8, target.getBbWidth()))) {
                  dealTrueDamage(player, target, trueCaedrosSource(player, TensuraDamageTypes.INFECTION), Math.max(1.0F, damage * (0.7F + i * 0.06F)));
                  applyRandomPlagueDebuffs(
                     target, mastered, mastered ? (Integer)CONFIG.plagueDebuffPicksMastered.get() : (Integer)CONFIG.plagueDebuffPicks.get()
                  );
                  knockAway(player, target, 0.55 + i * 0.03, 0.06);
                  onAspectHit(player, level, target);
               }
            }
         }
      }

      level.sendParticles(
         (TensuraParticleType)TensuraParticleTypes.BLACK_FIRE.get(), player.getX(), player.getY() + 0.8, player.getZ(), 28, 0.6, 0.45, 0.6, 0.0
      );
      level.sendParticles(
         (TensuraParticleType)TensuraParticleTypes.FALLING_ACID.get(), player.getX(), player.getY() + 0.9, player.getZ(), 18, 0.5, 0.4, 0.5, 0.0
      );
      level.sendParticles(
         (TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(), player.getX(), player.getY() + 0.9, player.getZ(), 12, 0.35, 0.4, 0.35, 0.0
      );
      instance.setCoolDown((Integer)CONFIG.plagueCooldownTicks.get(), mode);
      instance.addMasteryPoint(player);
   }

   private static void castBang(ServerPlayer player, ServerLevel level, ManasSkillInstance instance, int mode) {
      if (!player.getPersistentData().getBoolean("TenaddexCaedrosBangCasting")) {
         long lastFire = player.getPersistentData().getLong("TenaddexCaedrosBangLastFire");
         if (level.getGameTime() - lastFire >= ((Integer)CONFIG.bangRepeatFireLockoutTicks.get()).intValue()) {
            if (!consumeGuilt(player, (Double)CONFIG.bangGuiltCost.get())) {
               player.displayClientMessage(Component.literal("Not enough Guilt").withStyle(ChatFormatting.RED), true);
            } else {
               player.getPersistentData().putBoolean("TenaddexCaedrosBangCasting", true);
               player.getPersistentData().putInt("TenaddexCaedrosBangTicks", (Integer)CONFIG.bangWindupTicks.get());
               level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 3.5F, 0.75F);
               MagicCircle.castMagicCircle(
                  "caedros_bang_circle",
                  3.8F,
                  (Integer)CONFIG.bangWindupTicks.get() + 10,
                  MagicCircleVariant.DEMON,
                  true,
                  player,
                  instance.getOrCreateTag(),
                  1.6F,
                  96.0F,
                  new Vec3(0.0, 0.15, 0.0),
                  instance,
                  mode,
                  Pair.of(0.0, 0.0)
               );
               renderCaedrosCircleFlavor(level, player, 3.8);
               level.sendParticles(
                  (TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_EFFECT.get(),
                  player.getX(),
                  player.getY() + 1.0,
                  player.getZ(),
                  55,
                  1.15,
                  0.9,
                  1.15,
                  0.01
               );
               level.sendParticles(
                  (TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(),
                  player.getX(),
                  player.getY() + 1.0,
                  player.getZ(),
                  32,
                  0.8,
                  0.7,
                  0.8,
                  0.0
               );
               instance.setCoolDown((Integer)CONFIG.bangCooldownTicks.get(), mode);
            }
         }
      }
   }

   private static void fireBangBeam(ServerPlayer player, ServerLevel level, ManasSkillInstance instance) {
      boolean totalWar = getActiveAspect(player) == CaedrosGodOfConquestSkill.CaedrosAspect.WAR
         && player.getPersistentData().getBoolean("TenaddexCaedrosTotalWar");
      int range = totalWar ? (Integer)CONFIG.totalWarBangRange.get() : (Integer)CONFIG.bangRange.get();
      int radius = totalWar ? (Integer)CONFIG.totalWarBangRadius.get() : (Integer)CONFIG.bangRadius.get();
      Vec3 start = player.getEyePosition();
      Vec3 dir = player.getLookAngle().normalize();
      level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, totalWar ? 4.5F : 2.8F, 0.8F);
      level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, totalWar ? 8.0F : 4.8F, 0.7F);

      for (int step = 0; step <= range; step++) {
         Vec3 p = start.add(dir.scale(step));
         renderBangSlice(level, p, radius, totalWar, step);
         carveCircularTunnel(level, player, p, radius, totalWar, instance);
      }

      hitBangTargets(level, player, start, dir, range, radius, totalWar);
      Vec3 end = start.add(dir.scale(range));
      LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
      bolt.moveTo(end.x, end.y, end.z);
      bolt.setCause(player);
      level.addFreshEntity(bolt);
      shockwave(level, end, totalWar ? 24.0 : 12.0, 0.0, totalWar ? 120 : 56);
   }

   private static void renderChargeBeam(ServerLevel level, ServerPlayer player, int visualRange, int ticksLeft) {
      Vec3 start = player.getEyePosition();
      Vec3 dir = player.getLookAngle().normalize();
      double width = 0.35 + (double)((Integer)CONFIG.bangWindupTicks.get() - ticksLeft) / ((Integer)CONFIG.bangWindupTicks.get()).intValue() * 0.75;

      for (int step = 0; step <= visualRange; step++) {
         Vec3 p = start.add(dir.scale(step));
         level.sendParticles(ParticleTypes.END_ROD, p.x, p.y, p.z, 2, width * 0.12, width * 0.12, width * 0.12, 0.0);
         level.sendParticles(ParticleTypes.ELECTRIC_SPARK, p.x, p.y, p.z, 8, width * 0.1, width * 0.1, width * 0.1, 0.0);
         level.sendParticles(
            (TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(), p.x, p.y, p.z, 7, width * 0.08, width * 0.08, width * 0.08, 0.0
         );
         level.sendParticles(
            (TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(), p.x, p.y, p.z, 6, width * 0.06, width * 0.06, width * 0.06, 0.0
         );
         if (step % 2 == 0) {
            level.sendParticles(ParticleTypes.FLASH, p.x, p.y, p.z, 1, 0.0, 0.0, 0.0, 0.0);
         }
      }
   }

   private static void renderBangSlice(ServerLevel level, Vec3 p, int radius, boolean totalWar, int step) {
      int sparks = totalWar ? 42 : 18;
      double spread = totalWar ? 0.75 : 0.3;
      level.sendParticles(ParticleTypes.END_ROD, p.x, p.y, p.z, totalWar ? 5 : 2, spread * 0.25, spread * 0.25, spread * 0.25, 0.0);
      level.sendParticles(ParticleTypes.ELECTRIC_SPARK, p.x, p.y, p.z, sparks, spread, spread, spread, 0.0);
      level.sendParticles(
         (TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(),
         p.x,
         p.y,
         p.z,
         totalWar ? 30 : 14,
         spread * 0.9,
         spread * 0.9,
         spread * 0.9,
         0.0
      );
      level.sendParticles(
         (TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(),
         p.x,
         p.y,
         p.z,
         totalWar ? 26 : 12,
         spread * 0.8,
         spread * 0.8,
         spread * 0.8,
         0.0
      );
      level.sendParticles(ParticleTypes.FLASH, p.x, p.y, p.z, 1, 0.0, 0.0, 0.0, 0.0);
      if (step % 2 == 0) {
         level.sendParticles(TensuraParticleUtils.getRedWave(0.9F, totalWar ? 15.0F : 7.0F, -0.1F, false), p.x, p.y, p.z, 1, 0.0, 0.0, 0.0, 0.0);
      }

      int ringCount = totalWar ? 20 : 10;
      double ringRadius = Math.max(0.9, radius * 0.45);

      for (int i = 0; i < ringCount; i++) {
         double a = (Math.PI * 2) * i / ringCount;
         double ox = Math.cos(a) * ringRadius;
         double oz = Math.sin(a) * ringRadius;
         level.sendParticles(ParticleTypes.ELECTRIC_SPARK, p.x + ox, p.y, p.z + oz, 1, 0.02, 0.02, 0.02, 0.0);
         level.sendParticles((TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(), p.x + ox, p.y, p.z + oz, 1, 0.0, 0.0, 0.0, 0.0);
      }
   }

   private static void hitBangTargets(ServerLevel level, ServerPlayer player, Vec3 start, Vec3 dir, int range, int radius, boolean totalWar) {
      AABB area = player.getBoundingBox().inflate(range + radius + 8.0);

      for (LivingEntity e : level.getEntitiesOfClass(LivingEntity.class, area, x -> x.isAlive() && x != player)) {
         Vec3 rel = e.position().subtract(start);
         double along = rel.dot(dir);
         if (!(along < 0.0) && !(along > range)) {
            Vec3 closest = start.add(dir.scale(along));
            double dist = e.position().distanceTo(closest);
            if (!(dist > radius + Math.max(4.0, e.getBbWidth()))) {
               if (totalWar) {
                  dealTrueDamage(
                     player, e, trueCaedrosSource(player, TensuraDamageTypes.LIGHTNING_ELEMENTAL), ((Double)CONFIG.totalWarBangDamage.get()).floatValue()
                  );
                  knockAway(player, e, 8.0, 1.2);
               } else {
                  dealTrueDamage(
                     player,
                     e,
                     trueCaedrosSource(player, TensuraDamageTypes.DISINTEGRATION),
                     applyFinalCaedrosDamage(player, ((Double)CONFIG.bangDamage.get()).floatValue())
                  );
                  knockAway(player, e, 4.5, 0.9);
               }

               onAspectHit(player, level, e);
            }
         }
      }
   }

   private static void carveCircularTunnel(ServerLevel level, ServerPlayer owner, Vec3 center, int radius, boolean totalWar, ManasSkillInstance instance) {
      if ((Boolean)CONFIG.bangCanDestroyBlocks.get()) {
         int cx = Mth.floor(center.x);
         int cy = Mth.floor(center.y);
         int cz = Mth.floor(center.z);
         int radiusXZ = totalWar ? Math.max(radius, 36) : Math.max(9, radius);
         int radiusY = totalWar ? Math.max(radius, 30) : Math.max(7, radius - 1);
         int r2 = radiusXZ * radiusXZ;

         for (int dx = -radiusXZ; dx <= radiusXZ; dx++) {
            for (int dz = -radiusXZ; dz <= radiusXZ; dz++) {
               if (dx * dx + dz * dz <= r2) {
                  for (int dy = -radiusY; dy <= radiusY; dy++) {
                     BlockPos pos = new BlockPos(cx + dx, cy + dy, cz + dz);
                     if (level.isLoaded(pos)) {
                        BlockState state = level.getBlockState(pos);
                        if (!state.isAir() && !state.is(Blocks.BARRIER)) {
                           boolean canDestroyBedrock = totalWar
                              ? (Boolean)CONFIG.totalWarBangCanDestroyBedrock.get()
                              : (Boolean)CONFIG.bangCanDestroyBedrock.get();
                           if ((!state.is(Blocks.BEDROCK) || canDestroyBedrock)
                              && !((SkillGriefEvent)TensuraSkillEvents.SKILL_GRIEF_PRE.invoker())
                                 .grief(instance, level, owner, pos.getX(), pos.getY(), pos.getZ())
                                 .isFalse()) {
                              level.destroyBlock(pos, false);
                              ((SkillGriefEvent)TensuraSkillEvents.SKILL_GRIEF_POS.invoker()).grief(instance, level, owner, pos.getX(), pos.getY(), pos.getZ());
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static void castCloud(ServerPlayer player, ServerLevel level, ManasSkillInstance instance, int mode) {
      if (CLOUD == null || !CLOUD.inWindup && !CLOUD.active) {
         CLOUD = new CaedrosGodOfConquestSkill.CloudData();
         CLOUD.inWindup = true;
         CLOUD.active = false;
         CLOUD.ownerId = player.getUUID();
         CLOUD.windupTicks = (Integer)CONFIG.cloudWindupTicks.get();
         CLOUD.remainingTicks = (Integer)CONFIG.cloudDurationTicks.get();
         CLOUD.castDimension = level.dimension().location();
         player.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.GUARDED), (Integer)CONFIG.cloudWindupTicks.get() + 40, 4, false, false, false
            )
         );
         level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_DRAGON_GROWL, SoundSource.PLAYERS, 3.0F, 0.55F);
         player.sendSystemMessage(
            Component.literal("The skies darken... Eden approaches").withStyle(new ChatFormatting[]{ChatFormatting.DARK_RED, ChatFormatting.BOLD})
         );
         level.sendParticles(
            (TensuraParticleType)TensuraParticleTypes.WEATHER_MANIPULATION.get(), player.getX(), player.getY() + 2.0, player.getZ(), 180, 5.0, 2.2, 5.0, 0.0
         );
         level.sendParticles(
            (TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_EFFECT.get(), player.getX(), player.getY() + 3.0, player.getZ(), 30, 3.5, 1.2, 3.5, 0.01
         );
         level.sendParticles(
            (TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(), player.getX(), player.getY() + 2.6, player.getZ(), 24, 2.2, 1.0, 2.2, 0.0
         );
         instance.setCoolDown((Integer)CONFIG.cloudCooldownTicks.get(), mode);
      }
   }

   private static void clearBangCircle(ServerPlayer player) {
      ManasSkillInstance instance = getCaedrosInstance(player);
      if (instance != null) {
         instance.getOrCreateTag().putInt("caedros_bang_circle", 0);
         instance.markDirty();
      }
   }

   private static void tickCloud(MinecraftServer server) {
      if (server != null && CLOUD != null) {
         ServerPlayer owner = server.getPlayerList().getPlayer(CLOUD.ownerId);
         if (owner != null && owner.isAlive() && !owner.isRemoved()) {
            ServerLevel currentOwnerLevel = owner.serverLevel();
            if (CLOUD.inWindup) {
               CLOUD.windupTicks--;
               currentOwnerLevel.sendParticles(
                  (TensuraParticleType)TensuraParticleTypes.WEATHER_MANIPULATION.get(), owner.getX(), owner.getY() + 2.0, owner.getZ(), 20, 2.6, 0.8, 2.6, 0.0
               );
               currentOwnerLevel.sendParticles(
                  (TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(), owner.getX(), owner.getY() + 2.4, owner.getZ(), 12, 1.6, 0.8, 1.6, 0.0
               );
               currentOwnerLevel.sendParticles(
                  (TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(),
                  owner.getX(),
                  owner.getY() + 2.35,
                  owner.getZ(),
                  10,
                  1.25,
                  0.65,
                  1.25,
                  0.0
               );
               if (CLOUD.windupTicks <= 0) {
                  startCloudDomain(currentOwnerLevel, CLOUD);
               }
            } else {
               if (CLOUD.active) {
                  CLOUD.remainingTicks--;
                  ServerLevel paths = server.getLevel(ResourceKey.create(Registries.DIMENSION, PATHS_ID));
                  if (paths == null) {
                     endCloudSession(server, true);
                     return;
                  }

                  for (ServerPlayer sp : new ArrayList(server.getPlayerList().getPlayers())) {
                     if (sp.serverLevel().dimension().location().equals(PATHS_ID)) {
                        sp.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.GUARDED), 40, 1, false, false, true));
                        leashToOwnerInPaths(paths, owner, sp);
                        if (paths.getGameTime() % 20L == 0L) {
                           paths.sendParticles(
                              (TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(),
                              sp.getX(),
                              sp.getY() + 1.0,
                              sp.getZ(),
                              6,
                              0.35,
                              0.5,
                              0.35,
                              0.0
                           );
                           paths.sendParticles(
                              (TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(),
                              sp.getX(),
                              sp.getY() + 1.0,
                              sp.getZ(),
                              4,
                              0.25,
                              0.35,
                              0.25,
                              0.0
                           );
                        }
                     }
                  }

                  if (CLOUD.remainingTicks <= 0) {
                     endCloudSession(server, true);
                  }
               }
            }
         } else {
            endCloudSession(server, true);
         }
      }
   }

   private static void leashToOwnerInPaths(ServerLevel paths, ServerPlayer owner, ServerPlayer player) {
      if (owner != null && player != null) {
         if (player.level() == paths && owner.level() == paths) {
            if (player != owner) {
               double dist = player.distanceTo(owner);
               if (!(dist <= ((Integer)CONFIG.edenLeashRange.get()).intValue())) {
                  Vec3 dir = player.position().subtract(owner.position());
                  if (dir.lengthSqr() < 1.0E-4) {
                     dir = new Vec3(1.0, 0.0, 0.0);
                  }

                  dir = dir.normalize();
                  Vec3 target = owner.position().add(dir.scale(((Integer)CONFIG.edenLeashRange.get()).intValue() - 2.0));
                  player.teleportTo(paths, target.x, Math.max(paths.getMinBuildHeight() + 5, owner.getY()), target.z, player.getYRot(), player.getXRot());
                  player.setDeltaMovement(Vec3.ZERO);
                  player.hurtMarked = true;
                  paths.sendParticles(
                     (TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(),
                     player.getX(),
                     player.getY() + 1.0,
                     player.getZ(),
                     24,
                     0.35,
                     0.45,
                     0.35,
                     0.0
                  );
                  paths.sendParticles(
                     (TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(),
                     player.getX(),
                     player.getY() + 1.0,
                     player.getZ(),
                     16,
                     0.25,
                     0.35,
                     0.25,
                     0.0
                  );
               }
            }
         }
      }
   }

   private static void startCloudDomain(ServerLevel level, CaedrosGodOfConquestSkill.CloudData cloud) {
      cloud.inWindup = false;
      cloud.active = true;
      ServerPlayer owner = level.getServer().getPlayerList().getPlayer(cloud.ownerId);
      if (owner == null) {
         cloud.active = false;
      } else {
         AttributeInstance atk = owner.getAttribute(Attributes.ATTACK_DAMAGE);
         if (atk != null) {
            cloud.prevAttackBase = atk.getBaseValue();
            atk.setBaseValue(cloud.prevAttackBase * (Double)CONFIG.cloudOwnerAttackMultiplier.get());
         }

         AttributeInstance hp = owner.getAttribute(Attributes.MAX_HEALTH);
         if (hp != null) {
            cloud.prevHpBase = hp.getBaseValue();
            hp.setBaseValue(cloud.prevHpBase * (Double)CONFIG.cloudOwnerHealthMultiplier.get());
            owner.setHealth((float)hp.getValue());
         }

         MinecraftServer server = level.getServer();
         ServerLevel paths = server.getLevel(ResourceKey.create(Registries.DIMENSION, PATHS_ID));
         if (paths == null) {
            if (atk != null && cloud.prevAttackBase > 0.0) {
               atk.setBaseValue(cloud.prevAttackBase);
            }

            if (hp != null && cloud.prevHpBase > 0.0) {
               hp.setBaseValue(cloud.prevHpBase);
               owner.setHealth(Math.min(owner.getHealth(), (float)hp.getValue()));
            }

            cloud.active = false;
         } else {
            owner.sendSystemMessage(Component.literal("Cloud the Skies: Eden opens").withStyle(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD}));
            level.sendParticles(
               (TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_EFFECT.get(), owner.getX(), owner.getY() + 2.0, owner.getZ(), 120, 7.0, 1.5, 7.0, 0.02
            );
            level.sendParticles(
               (TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(), owner.getX(), owner.getY() + 2.0, owner.getZ(), 48, 4.0, 1.0, 4.0, 0.0
            );
            WorldBorder border = paths.getWorldBorder();
            cloud.prevBorderCenterX = border.getCenterX();
            cloud.prevBorderCenterZ = border.getCenterZ();
            cloud.prevBorderSize = border.getSize();
            cloud.prevBorderDamage = border.getDamagePerBlock();
            cloud.savedBorder = true;
            border.setCenter(0.0, 0.0);
            border.setSize((Double)CONFIG.edenWorldBorderSize.get());
            List<ServerPlayer> snapshot = new ArrayList<>(server.getPlayerList().getPlayers());

            for (ServerPlayer player : snapshot) {
               player.getPersistentData().putString("TenaddexCloudReturnDimension", player.level().dimension().location().toString());
               player.getPersistentData().putDouble("TenaddexCloudReturnX", player.getX());
               player.getPersistentData().putDouble("TenaddexCloudReturnY", player.getY());
               player.getPersistentData().putDouble("TenaddexCloudReturnZ", player.getZ());
            }

            if ((Boolean)CONFIG.cloudTeleportsAllPlayers.get()) {
               for (ServerPlayer player : snapshot) {
                  if (!player.isRemoved() && player.isAlive()) {
                     teleportToPaths(paths, player);
                  }
               }
            } else {
               teleportToPaths(paths, owner);
            }
         }
      }
   }

   private static void endCloudSession(MinecraftServer server, boolean forceRestore) {
      if (CLOUD != null) {
         ServerPlayer owner = server.getPlayerList().getPlayer(CLOUD.ownerId);
         if (owner != null) {
            AttributeInstance atk = owner.getAttribute(Attributes.ATTACK_DAMAGE);
            if (atk != null && CLOUD.prevAttackBase > 0.0) {
               atk.setBaseValue(CLOUD.prevAttackBase);
            }

            AttributeInstance hp = owner.getAttribute(Attributes.MAX_HEALTH);
            if (hp != null && CLOUD.prevHpBase > 0.0) {
               hp.setBaseValue(CLOUD.prevHpBase);
               owner.setHealth(Math.min(owner.getHealth(), (float)hp.getValue()));
            }
         }

         List<ServerPlayer> snapshot = new ArrayList<>(server.getPlayerList().getPlayers());
         if (forceRestore) {
            for (ServerPlayer player : snapshot) {
               String dimStr = player.getPersistentData().getString("TenaddexCloudReturnDimension");
               if (!dimStr.isBlank()) {
                  ResourceLocation id;
                  try {
                     id = ResourceLocation.parse(dimStr);
                  } catch (Exception ignored) {
                     continue;
                  }

                  ServerLevel target = server.getLevel(ResourceKey.create(Registries.DIMENSION, id));
                  if (target != null) {
                     double x = player.getPersistentData().getDouble("TenaddexCloudReturnX");
                     double y = player.getPersistentData().getDouble("TenaddexCloudReturnY");
                     double z = player.getPersistentData().getDouble("TenaddexCloudReturnZ");
                     if (!player.isRemoved()) {
                        player.teleportTo(target, x, y, z, player.getYRot(), player.getXRot());
                     }
                  }
               }
            }
         }

         ServerLevel paths = server.getLevel(ResourceKey.create(Registries.DIMENSION, PATHS_ID));
         if (paths != null && CLOUD.savedBorder) {
            WorldBorder border = paths.getWorldBorder();
            border.setCenter(CLOUD.prevBorderCenterX, CLOUD.prevBorderCenterZ);
            border.setSize(CLOUD.prevBorderSize);
            border.setDamagePerBlock(CLOUD.prevBorderDamage);
         }

         for (ServerPlayer player : snapshot) {
            player.getPersistentData().remove("TenaddexCloudReturnDimension");
            player.getPersistentData().remove("TenaddexCloudReturnX");
            player.getPersistentData().remove("TenaddexCloudReturnY");
            player.getPersistentData().remove("TenaddexCloudReturnZ");
         }

         CLOUD = null;
      }
   }

   private static void teleportToPaths(ServerLevel paths, ServerPlayer player) {
      if (paths != null && player != null) {
         BlockPos pos = new BlockPos(0, Math.max(paths.getMinBuildHeight() + 5, 120), 0);
         player.teleportTo(paths, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, player.getYRot(), player.getXRot());
         paths.sendParticles(
            (TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_EFFECT.get(),
            player.getX(),
            player.getY() + 1.0,
            player.getZ(),
            18,
            0.45,
            0.65,
            0.45,
            0.01
         );
         paths.sendParticles(
            (TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(),
            player.getX(),
            player.getY() + 1.0,
            player.getZ(),
            12,
            0.32,
            0.45,
            0.32,
            0.0
         );
      }
   }

   private static void castConstruct(ServerPlayer player, ServerLevel level, ManasSkillInstance instance, int mode) {
      int range = instance.isMastered(player) ? (Integer)CONFIG.constructMasterRange.get() : (Integer)CONFIG.constructBaseRange.get();
      Vec3 look = player.getLookAngle().normalize();
      swingBurst(level, player.position().add(0.0, player.getBbHeight() * 0.5, 0.0), range, 22);
      level.sendParticles((TensuraParticleType)TensuraParticleTypes.SOUL.get(), player.getX(), player.getY() + 1.0, player.getZ(), 18, 0.6, 0.45, 0.6, 0.0);
      level.sendParticles(
         (TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_EFFECT.get(), player.getX(), player.getY() + 1.0, player.getZ(), 5, 0.35, 0.35, 0.35, 0.0
      );
      level.sendParticles(
         (TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(), player.getX(), player.getY() + 1.0, player.getZ(), 8, 0.28, 0.28, 0.28, 0.0
      );
      level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 3.0F, 0.5F);
      float finalDamage = applyFinalCaedrosDamage(player, ((Double)CONFIG.constructDamage.get()).floatValue());
      AABB hitBox = player.getBoundingBox().inflate(range, 3.0, range);
      int pendingAdds = 0;

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, hitBox, e -> e.isAlive() && e != player)) {
         Vec3 to = target.position().subtract(player.position());
         Vec3 flat = new Vec3(to.x, 0.0, to.z);
         Vec3 flatLook = new Vec3(look.x, 0.0, look.z);
         if (!(flat.lengthSqr() < 1.0E-4) && !(flatLook.lengthSqr() < 1.0E-4) && !(flat.normalize().dot(flatLook.normalize()) < 0.0)) {
            boolean wasAlive = target.isAlive();
            dealTrueDamage(player, target, trueCaedrosSource(player, TensuraDamageTypes.DEATH_WISH), finalDamage);
            applyConstructDebuffs(target, instance.isMastered(player));
            onAspectHit(player, level, target);
            level.sendParticles(ParticleTypes.SWEEP_ATTACK, target.getX(), target.getY() + target.getBbHeight() * 0.5, target.getZ(), 3, 0.18, 0.18, 0.18, 0.0);
            level.sendParticles(
               (TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(),
               target.getX(),
               target.getY() + target.getBbHeight() * 0.5,
               target.getZ(),
               6,
               0.08,
               0.08,
               0.08,
               0.0
            );
            level.sendParticles(
               (TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(),
               target.getX(),
               target.getY() + target.getBbHeight() * 0.5,
               target.getZ(),
               4,
               0.08,
               0.08,
               0.08,
               0.0
            );
            if (wasAlive && !target.isAlive()) {
               pendingAdds++;
            }
         }
      }

      for (int i = 0; i < pendingAdds; i++) {
         queueConstructSword(player, instance);
      }

      instance.setCoolDown((Integer)CONFIG.constructCooldownTicks.get(), mode);
   }

   private static void handleTruePassive(ServerPlayer player, long time) {
      if (isMasteredCaedros(player)) {
         long last = player.getPersistentData().getLong("TenaddexCaedrosLastHiss");
         if (time - last >= 600L) {
            if (player.getRandom().nextInt(8) == 0) {
               player.getPersistentData().putLong("TenaddexCaedrosLastHiss", time);
               player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SILVERFISH_HURT, SoundSource.PLAYERS, 0.5F, 0.55F);
            }
         }
      }
   }

   private static void handleFearInsanityAura(ServerPlayer player, ServerLevel level, long time) {
      if (hasCaedrosToggled(player)) {
         if (time % ((Integer)CONFIG.fearInsanityIntervalTicks.get()).intValue() == 0L) {
            for (LivingEntity target : level.getEntitiesOfClass(
               LivingEntity.class, player.getBoundingBox().inflate(((Integer)CONFIG.fearInsanityRadius.get()).intValue()), e -> e.isAlive() && e != player
            )) {
               MobEffectInstance fear = target.getEffect(TensuraMobEffects.getReference(TensuraMobEffects.FEAR));
               int nextFear = fear == null ? 0 : Math.min((Integer)CONFIG.fearInsanityMaxAmplifier.get(), fear.getAmplifier() + 1);
               target.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.FEAR), (Integer)CONFIG.fearInsanityDurationTicks.get(), nextFear, false, false, true
                  )
               );
               MobEffectInstance insanity = target.getEffect(TensuraMobEffects.getReference(TensuraMobEffects.INSANITY));
               int nextInsanity = insanity == null ? 0 : Math.min((Integer)CONFIG.fearInsanityMaxAmplifier.get(), insanity.getAmplifier() + 1);
               target.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.INSANITY),
                     (Integer)CONFIG.fearInsanityDurationTicks.get(),
                     nextInsanity,
                     false,
                     false,
                     true
                  )
               );
               if (time % (((Integer)CONFIG.fearInsanityIntervalTicks.get()).intValue() * 2L) == 0L) {
                  level.sendParticles(
                     (TensuraParticleType)TensuraParticleTypes.BLACK_FIRE.get(), target.getX(), target.getY() + 0.7, target.getZ(), 4, 0.15, 0.2, 0.15, 0.0
                  );
                  level.sendParticles(
                     (TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(),
                     target.getX(),
                     target.getY() + 0.8,
                     target.getZ(),
                     3,
                     0.12,
                     0.18,
                     0.12,
                     0.0
                  );
               }
            }
         }
      }
   }

   private static void handleOuroboros(ServerPlayer player, ServerLevel level, long time) {
      if (hasCaedrosToggled(player)) {
         double guilt = getGuilt(player);
         int inspo = Math.min((Integer)CONFIG.maxGuiltTier.get(), (int)(guilt / (Double)CONFIG.guiltTierDivisor.get()));
         if (inspo > 0) {
            for (LivingEntity target : player.level().getEntitiesOfClass(LivingEntity.class, player.getBoundingBox().inflate(25.0), e -> e.isAlive())) {
               if (SubordinateHelper.isSubordinate(player, target)) {
                  target.addEffect(
                     new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.INSPIRATION), 140, Math.max(0, inspo - 1), false, false, true)
                  );
                  if (level.getGameTime() % 20L == 0L) {
                     level.sendParticles(
                        (TensuraParticleType)TensuraParticleTypes.SOUL.get(),
                        target.getX(),
                        target.getY() + target.getBbHeight() * 0.5,
                        target.getZ(),
                        2,
                        0.12,
                        0.18,
                        0.12,
                        0.0
                     );
                  }
               }
            }

            player.addEffect(
               new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.GUARDED), 60, Math.min(5, Math.max(0, inspo / 2)), false, false, true)
            );
            player.addEffect(
               new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.HAKI_COAT), 60, Math.min(4, Math.max(0, inspo / 3)), false, false, true)
            );
            if (inspo >= 3) {
               player.addEffect(
                  new MobEffectInstance(
                     TensuraMobEffects.getReference(TensuraMobEffects.STRENGTHEN), 60, Math.min(5, Math.max(0, inspo / 2)), false, false, true
                  )
               );
            }
         }

         long lastEat = player.getPersistentData().getLong("TenaddexCaedrosLastOuroborosEat");
         if (time - lastEat >= ((Integer)CONFIG.ouroborosCheckIntervalTicks.get()).intValue()) {
            player.getPersistentData().putLong("TenaddexCaedrosLastOuroborosEat", time);
            if (!consumeGuilt(player, (Double)CONFIG.ouroborosGuiltUpkeep.get())) {
               if ((Boolean)CONFIG.enableOuroborosSkillEating.get()) {
                  level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WARDEN_ROAR, SoundSource.PLAYERS, 1.2F, 0.55F);
                  level.sendParticles(
                     (TensuraParticleType)TensuraParticleTypes.BLACK_FIRE.get(), player.getX(), player.getY() + 1.0, player.getZ(), 30, 0.6, 0.8, 0.6, 0.0
                  );
                  level.sendParticles(
                     (TensuraParticleType)TensuraParticleTypes.SOUL.get(), player.getX(), player.getY() + 1.0, player.getZ(), 8, 0.35, 0.4, 0.35, 0.0
                  );
                  if (!eatRandomSkill(player) && (Boolean)CONFIG.allowOuroborosToKillUser.get()) {
                     player.setHealth(0.0F);
                     clearToggleBuffs(player);
                     ManasSkillInstance inst = getCaedrosInstance(player);
                     if (inst != null) {
                        inst.setToggled(false);
                        inst.onToggleOff(player);
                     }
                  }
               }
            }
         }
      }
   }

   private static double computePassiveGuiltGain(ServerPlayer player) {
      double amount = (Double)CONFIG.guiltOfflineGainPerSecond.get();
      MinecraftServer server = player.getServer();
      if (server == null) {
         return amount;
      }

      for (ServerPlayer other : server.getPlayerList().getPlayers()) {
         if (other != player
            && !(other.distanceTo(player) > ((Integer)CONFIG.guiltSubordinateRadius.get()).intValue())
            && SubordinateHelper.isSubordinate(player, other)) {
            amount += CONFIG.guiltSubordinateBonus.get();
            amount += countHighSkills(other) * CONFIG.guiltSkillBonus.get();
         }
      }

      return amount;
   }

   private static double computeToggledGuiltDrain(ServerPlayer player) {
      double amount = (Double)CONFIG.guiltToggleDrainPerSecond.get();
      MinecraftServer server = player.getServer();
      if (server == null) {
         return amount;
      }

      for (ServerPlayer other : server.getPlayerList().getPlayers()) {
         if (other != player
            && !(other.distanceTo(player) > ((Integer)CONFIG.guiltSubordinateRadius.get()).intValue())
            && SubordinateHelper.isSubordinate(player, other)) {
            amount += CONFIG.guiltSubordinateDrain.get();
            amount += countHighSkills(other) * CONFIG.guiltSkillDrain.get();
         }
      }

      return amount;
   }

   private static void applyToggledBuffs(ServerPlayer player) {
      double guilt = getGuilt(player);
      int guiltTier = Math.min((Integer)CONFIG.maxGuiltTier.get(), (int)(guilt / (Double)CONFIG.guiltTierDivisor.get()));
      if (guiltTier <= 0) {
         clearToggleBuffs(player);
      } else {
         double attackBoost = (Double)CONFIG.toggleAttackBase.get() + guiltTier * (Double)CONFIG.toggleAttackPerTier.get();
         double attackSpeedBoost = (Double)CONFIG.toggleAttackSpeedBase.get() + guiltTier * (Double)CONFIG.toggleAttackSpeedPerTier.get();
         double kbResistBoost = Math.min(
            (Double)CONFIG.toggleKnockbackCap.get(), (Double)CONFIG.toggleKnockbackBase.get() + guiltTier * (Double)CONFIG.toggleKnockbackPerTier.get()
         );
         double dodgeBoost = Math.min(
            (Double)CONFIG.toggleDodgeCap.get(), (Double)CONFIG.toggleDodgeBase.get() + guiltTier * (Double)CONFIG.toggleDodgePerTier.get()
         );
         double energyBoost = (Double)CONFIG.toggleEnergyGainBase.get() + guiltTier * (Double)CONFIG.toggleEnergyGainPerTier.get();
         AttributeHelper.addPermanentAttribute(player, Attributes.ATTACK_DAMAGE, TOGGLE_ATTACK_DAMAGE, attackBoost, Operation.ADD_MULTIPLIED_TOTAL);
         AttributeHelper.addPermanentAttribute(player, Attributes.ATTACK_SPEED, TOGGLE_ATTACK_SPEED, attackSpeedBoost, Operation.ADD_MULTIPLIED_TOTAL);
         AttributeHelper.addPermanentAttribute(player, Attributes.KNOCKBACK_RESISTANCE, TOGGLE_KNOCKBACK_RESIST, kbResistBoost, Operation.ADD_VALUE);
         AttributeHelper.addPermanentAttribute(player, TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE, TOGGLE_PROJECTILE_DODGE, dodgeBoost, Operation.ADD_VALUE);
         AttributeHelper.addPermanentAttribute(player, TensuraAttributes.AUTO_MELEE_DODGE_CHANCE, TOGGLE_MELEE_DODGE, dodgeBoost, Operation.ADD_VALUE);
         AttributeHelper.addPermanentAttribute(player, TensuraAttributes.MAGICULE_GAIN, TOGGLE_MAGICULE, energyBoost, Operation.ADD_VALUE);
         AttributeHelper.addPermanentAttribute(player, TensuraAttributes.AURA_GAIN, TOGGLE_AURA, energyBoost, Operation.ADD_VALUE);
         int duration = (Integer)CONFIG.toggleBuffDurationTicks.get();
         player.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.GUARDED),
               duration,
               Math.min((Integer)CONFIG.toggleGuardedMaxAmplifier.get(), 1 + guiltTier / 2),
               false,
               false,
               true
            )
         );
         player.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.HAKI_COAT),
               duration,
               Math.min((Integer)CONFIG.toggleHakiMaxAmplifier.get(), guiltTier / 2),
               false,
               false,
               true
            )
         );
         player.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.STRENGTHEN),
               duration,
               Math.min((Integer)CONFIG.toggleStrengthenMaxAmplifier.get(), 1 + guiltTier / 2),
               false,
               false,
               true
            )
         );
         player.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.INSPIRATION),
               duration,
               Math.min((Integer)CONFIG.toggleInspirationMaxAmplifier.get(), 1 + guiltTier / 2),
               false,
               false,
               true
            )
         );
         player.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_AURA),
               duration,
               Math.min((Integer)CONFIG.toggleMagicAuraMaxAmplifier.get(), guiltTier / 3),
               false,
               false,
               true
            )
         );
         player.addEffect(
            new MobEffectInstance(
               TensuraMobEffects.getReference(TensuraMobEffects.SELF_REGENERATION),
               duration,
               Math.min((Integer)CONFIG.toggleSelfRegenMaxAmplifier.get(), guiltTier / 3),
               false,
               false,
               true
            )
         );
      }
   }

   private static void clearToggleBuffs(ServerPlayer player) {
      AttributeHelper.removeAttribute(player, Attributes.ATTACK_DAMAGE, TOGGLE_ATTACK_DAMAGE);
      AttributeHelper.removeAttribute(player, Attributes.ATTACK_SPEED, TOGGLE_ATTACK_SPEED);
      AttributeHelper.removeAttribute(player, Attributes.KNOCKBACK_RESISTANCE, TOGGLE_KNOCKBACK_RESIST);
      AttributeHelper.removeAttribute(player, TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE, TOGGLE_PROJECTILE_DODGE);
      AttributeHelper.removeAttribute(player, TensuraAttributes.AUTO_MELEE_DODGE_CHANCE, TOGGLE_MELEE_DODGE);
      AttributeHelper.removeAttribute(player, TensuraAttributes.MAGICULE_GAIN, TOGGLE_MAGICULE);
      AttributeHelper.removeAttribute(player, TensuraAttributes.AURA_GAIN, TOGGLE_AURA);
   }

   private static void addMasteryAmbient(ManasSkillInstance instance, ServerPlayer player) {
      if (instance.isMastered(player)) {
         instance.addMasteryPoint(player);
      }
   }

   private static int countHighSkills(ServerPlayer player) {
      int count = 0;
      Skills skills = SkillAPI.getSkillsFrom(player);

      for (ManasSkillInstance inst : List.copyOf(skills.getLearnedSkills())) {
         if (inst != null
            && inst.getSkill() != null
            && inst.getSkill() instanceof Skill skill
            && (skill.getType() == SkillType.UNIQUE || skill.getType() == SkillType.ULTIMATE)) {
            count++;
         }
      }

      return count;
   }

   private static boolean eatRandomSkill(ServerPlayer player) {
      if (!(Boolean)CONFIG.enableOuroborosSkillEating.get()) {
         return false;
      }

      Skills skills = SkillAPI.getSkillsFrom(player);
      List<ManasSkillInstance> pool = new ArrayList<>();

      for (ManasSkillInstance inst : List.copyOf(skills.getLearnedSkills())) {
         if (inst != null
            && inst.getSkill() != null
            && inst.getSkillId() != null
            && !CAEDROS_ID.equals(inst.getSkillId())
            && inst.getSkill() instanceof Skill skill
            && canOuroborosEatSkillType(skill.getType())) {
            pool.add(inst);
         }
      }

      if (pool.isEmpty()) {
         return false;
      }

      ManasSkillInstance pick = pool.get(player.getRandom().nextInt(pool.size()));
      skills.forgetSkill(pick.getSkill());
      addEatenSkill(player, pick.getSkillId().toString());
      return true;
   }

   private static boolean canOuroborosEatSkillType(SkillType type) {
      return switch (type) {
         case UNIQUE -> CONFIG.allowEatingUniqueSkills.get();
         case ULTIMATE -> CONFIG.allowEatingUltimateSkills.get();
         case INTRINSIC -> CONFIG.allowEatingIntrinsicSkills.get();
         case RESISTANCE -> CONFIG.allowEatingResistanceSkills.get();
         case COMMON -> CONFIG.allowEatingCommonSkills.get();
         case EXTRA -> CONFIG.allowEatingExtraSkills.get();
         default -> true;
      };
   }

   private static void tickControl(ServerPlayer owner, ServerLevel level) {
      if ((Boolean)CONFIG.controlEnabled.get()) {
         double ownerEp = getEP(owner);
         double threshold = ownerEp * (Double)CONFIG.controlEpThresholdRatio.get();
         String lockedSkill = getControlLockedSkill(owner);

         for (ServerPlayer player : level.players()) {
            if (player != owner) {
               double ep = getEP(player);
               if (ep < threshold) {
                  if ((Boolean)CONFIG.controlForcesSpectator.get()) {
                     player.getPersistentData().putUUID("TenaddexControlBy", owner.getUUID());
                     if (!player.getPersistentData().contains("TenaddexControlPrevGM")) {
                        player.getPersistentData().putString("TenaddexControlPrevGM", player.gameMode.getGameModeForPlayer().getName());
                     }

                     if (player.gameMode.getGameModeForPlayer() != GameType.SPECTATOR) {
                        player.setGameMode(GameType.SPECTATOR);
                     }
                  }

                  if (lockedSkill != null && !lockedSkill.isBlank()) {
                     if ((Boolean)CONFIG.controlCanPermanentlyDeleteLockedSkill.get()) {
                        deleteSpecificSkill(player, lockedSkill);
                     } else {
                        if ((Boolean)CONFIG.controlCanDisableLockedSkill.get()) {
                           disableSpecificSkill(player, lockedSkill);
                        }

                        if ((Boolean)CONFIG.controlCanUnslotLockedSkill.get()) {
                           unslotSpecificSkill(player, lockedSkill);
                        }
                     }
                  }
               } else if (player.getPersistentData().hasUUID("TenaddexControlBy")
                  && owner.getUUID().equals(player.getPersistentData().getUUID("TenaddexControlBy"))) {
                  restoreControlVictim(player);
               }
            }
         }
      }
   }

   private static void restoreControlVictim(ServerPlayer player) {
      String prev = player.getPersistentData().getString("TenaddexControlPrevGM");
      GameType restore = GameType.byName(prev == null ? "survival" : prev.toLowerCase(), GameType.SURVIVAL);
      if (player.gameMode.getGameModeForPlayer() == GameType.SPECTATOR) {
         player.setGameMode(restore);
      }

      player.getPersistentData().remove("TenaddexControlBy");
      player.getPersistentData().remove("TenaddexControlPrevGM");
   }

   private static void disableSpecificSkill(LivingEntity target, String idText) {
      try {
         Skills skills = SkillAPI.getSkillsFrom(target);
         ResourceLocation id = ResourceLocation.parse(idText);

         for (ManasSkillInstance inst : List.copyOf(skills.getLearnedSkills())) {
            if (inst != null && inst.getSkill() != null && id.equals(inst.getSkillId())) {
               inst.setToggled(false);
               inst.onToggleOff(target);
               inst.markDirty();
            }
         }
      } catch (Throwable var6) {
      }
   }

   private static void deleteSpecificSkill(LivingEntity target, String idText) {
      try {
         Skills skills = SkillAPI.getSkillsFrom(target);
         ResourceLocation id = ResourceLocation.parse(idText);

         for (ManasSkillInstance inst : List.copyOf(skills.getLearnedSkills())) {
            if (inst != null && inst.getSkill() != null && id.equals(inst.getSkillId())) {
               skills.forgetSkill(inst.getSkill());
               skills.markDirty();
               break;
            }
         }
      } catch (Throwable var6) {
      }
   }

   private static void unslotSpecificSkill(ServerPlayer player, String idText) {
      try {
         ResourceLocation id = ResourceLocation.parse(idText);
         IAbility ability = TensuraStorages.getAbilityFrom(player);

         for (int preset = 0; preset < 9; preset++) {
            for (int slot = 0; slot < 8; slot++) {
               AbilitySlot abilitySlot = ability.getAbilitySlot(preset, slot);
               if (abilitySlot != null && abilitySlot.getSkill() != null) {
                  ResourceLocation loc = abilitySlot.getSkill().getRegistryName();
                  if (id.equals(loc)) {
                     ability.setAbilitySlot(preset, slot, (ManasSkill)null, 0);
                  }
               }
            }
         }

         ability.markDirty();
      } catch (Throwable var8) {
      }
   }

   private static void disableOrDeleteRandomSkills(LivingEntity target, int amount, boolean allowUnique, boolean allowUltimate, boolean permanentDelete) {
      try {
         Skills skills = SkillAPI.getSkillsFrom(target);
         List<ManasSkillInstance> pool = new ArrayList<>();

         for (ManasSkillInstance inst : List.copyOf(skills.getLearnedSkills())) {
            if (inst != null && inst.getSkill() != null && inst.getSkill() instanceof Skill skill) {
               if (skill.getType() == SkillType.UNIQUE && allowUnique) {
                  pool.add(inst);
               } else if (skill.getType() == SkillType.ULTIMATE && allowUltimate) {
                  pool.add(inst);
               }
            }
         }

         for (int i = 0; i < amount && !pool.isEmpty(); i++) {
            ManasSkillInstance pick = pool.remove(target.getRandom().nextInt(pool.size()));
            if (permanentDelete) {
               skills.forgetSkill(pick.getSkill());
               skills.markDirty();
            } else {
               pick.setToggled(false);
               pick.onToggleOff(target);
               pick.markDirty();
            }
         }
      } catch (Throwable var11) {
      }
   }

   private static void tickFamine(ServerPlayer owner, ServerLevel level) {
      if ((Boolean)CONFIG.famineEnabled.get()) {
         if ((Boolean)CONFIG.famineStarvesPlayers.get()) {
            for (ServerPlayer player : level.players()) {
               if (player != owner) {
                  FoodData fd = player.getFoodData();
                  fd.setFoodLevel(0);
                  fd.setSaturation(0.0F);
               }
            }
         }

         long time = level.getGameTime();

         for (LivingEntity e : level.getEntitiesOfClass(
            LivingEntity.class, owner.getBoundingBox().inflate((Double)CONFIG.famineRadius.get()), x -> x.isAlive() && x != owner
         )) {
            if (e.getPersistentData().hasUUID("TenaddexFamineBy")
               && owner.getUUID().equals(e.getPersistentData().getUUID("TenaddexFamineBy"))
               && e.getPersistentData().getLong("TenaddexFamineUntil") > time) {
               int stacks = Math.max(1, e.getPersistentData().getInt("TenaddexFamineStacks"));
               applyFamineDebuffs(e, stacks, false);
            }
         }
      }
   }

   private static void applyFamineOnHit(ServerPlayer owner, ServerLevel level, LivingEntity target) {
      target.getPersistentData().putUUID("TenaddexFamineBy", owner.getUUID());
      int debuffs = target.getPersistentData().getInt("TenaddexFamineDebuffs") + 1;
      target.getPersistentData().putInt("TenaddexFamineDebuffs", debuffs);
      int stacks = Math.max(1, debuffs / Math.max(1, (Integer)CONFIG.famineDebuffsPerStack.get()));
      target.getPersistentData().putInt("TenaddexFamineStacks", stacks);
      target.getPersistentData().putLong("TenaddexFamineUntil", level.getGameTime() + ((Integer)CONFIG.famineDurationTicks.get()).intValue());
      applyFamineDebuffs(target, stacks, true);
      disableOrDeleteRandomSkills(
         target,
         (Integer)CONFIG.famineDisableSkillsOnHit.get(),
         (Boolean)CONFIG.famineCanDisableUniqueSkills.get(),
         (Boolean)CONFIG.famineCanDisableUltimateSkills.get(),
         (Boolean)CONFIG.famineCanPermanentlyDeleteSkillsOnHit.get()
      );
   }

   private static void applyFamineDebuffs(LivingEntity target, int stacks, boolean burst) {
      int amp = Math.min((Integer)CONFIG.famineMaxAmplifier.get(), stacks);
      int secondary = Math.min((Integer)CONFIG.famineMaxSecondaryAmplifier.get(), stacks / 2);
      int duration = burst ? (Integer)CONFIG.famineBurstDurationTicks.get() : (Integer)CONFIG.famineDurationTicks.get();
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.INFECTION), duration, amp, false, false, true));
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.BURDEN), duration, amp, false, false, true));
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.CURSE), duration, amp, false, false, true));
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.FRAGILITY), duration, amp, false, false, true));
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE), duration, amp, false, false, true));
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.CORROSION), duration, amp, false, false, true));
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.CHILL), duration, amp, false, false, true));
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.FEAR), duration, amp, false, false, true));
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.INSANITY), duration, amp, false, false, true));
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.PARALYSIS), duration, secondary, false, false, true));
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.WEBBED), duration, secondary, false, false, true));
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.BLACK_BURN), duration, secondary, false, false, true));
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.DROWSINESS), duration, secondary, false, false, true));
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE), duration, secondary, false, false, true));
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.OPPRESSION), duration, secondary, false, false, true));
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.SOUL_DRAIN), duration, secondary, false, false, true));
      target.addEffect(
         new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.SPATIAL_BLOCKADE), duration, Math.min(1, stacks / 3), false, false, true)
      );
   }

   private static void tickWar(ServerPlayer owner, ServerLevel level) {
      if ((Boolean)CONFIG.warEnabled.get()) {
         double base = owner.getPersistentData().getDouble("TenaddexWarOwnerBaseEP");
         if (base <= 0.0) {
            base = getEP(owner);
            owner.getPersistentData().putDouble("TenaddexWarOwnerBaseEP", base);
         }

         double threshold = base * (Double)CONFIG.warConquerThresholdRatio.get();
         boolean everyoneConquered = true;
         int count = 0;
         double tempGain = 0.0;

         for (ServerPlayer player : level.players()) {
            if (player != owner) {
               count++;
               double ep = getEP(player);
               if (ep < threshold) {
                  player.getPersistentData().putBoolean("TenaddexConquered", true);
                  player.getPersistentData().putUUID("TenaddexWarBy", owner.getUUID());
                  player.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.BURDEN), 80, 3, false, false, true));
                  player.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.FEAR), 80, 1, false, false, true));
                  player.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE), 80, 2, false, false, true));
                  tempGain += ep * CONFIG.warTempEpGainRatio.get();
               } else {
                  everyoneConquered = false;
                  player.getPersistentData().putBoolean("TenaddexConquered", false);
                  if (player.getPersistentData().hasUUID("TenaddexWarBy") && owner.getUUID().equals(player.getPersistentData().getUUID("TenaddexWarBy"))) {
                     player.getPersistentData().remove("TenaddexWarBy");
                  }
               }
            }
         }

         double prevTemp = owner.getPersistentData().getDouble("TenaddexWarTempGain");
         if (Math.abs(prevTemp - tempGain) > 1.0) {
            owner.getPersistentData().putDouble("TenaddexWarTempGain", tempGain);
            setEP(owner, base + tempGain);
         }

         owner.getPersistentData().putBoolean("TenaddexCaedrosTotalWar", count > 0 && everyoneConquered);
      }
   }

   private static void tickDeath(ServerPlayer owner, ServerLevel level) {
      if ((Boolean)CONFIG.deathEnabled.get()) {
         for (LivingEntity e : level.getEntitiesOfClass(
            LivingEntity.class,
            owner.getBoundingBox().inflate((Double)CONFIG.deathReplicaFollowRadius.get()),
            x -> x.getPersistentData().hasUUID("TenaddexReplicaOwner")
         )) {
            if (owner.getUUID().equals(e.getPersistentData().getUUID("TenaddexReplicaOwner"))
               && e.distanceToSqr(owner) > (Double)CONFIG.deathReplicaTeleportDistanceSqr.get()) {
               Vec3 back = owner.position().subtract(owner.getLookAngle().normalize().scale(1.2));
               e.teleportTo(back.x, owner.getY(), back.z);
            }
         }
      }
   }

   private static void onAspectHit(ServerPlayer owner, ServerLevel level, LivingEntity target) {
      if (getActiveAspect(owner) == CaedrosGodOfConquestSkill.CaedrosAspect.FAMINE) {
         applyFamineOnHit(owner, level, target);
      }
   }

   private static float applyFinalCaedrosDamage(ServerPlayer owner, float baseDamage) {
      float damage = baseDamage;
      damage *= getFearDamageMultiplier(owner);
      CaedrosGodOfConquestSkill.CaedrosAspect aspect = getActiveAspect(owner);
      if (aspect == CaedrosGodOfConquestSkill.CaedrosAspect.WAR) {
         double base = owner.getPersistentData().getDouble("TenaddexWarOwnerBaseEP");
         double current = getEP(owner);
         if (base > 0.0 && current > base) {
            damage *= (float)Math.min((Double)CONFIG.warDamageEpGainCapMultiplier.get(), 1.0 + (current - base) / base);
         }

         damage += (float)(
            owner.getAttributeValue(TensuraAttributes.MAX_MAGICULE) * CONFIG.warMagiculeDamageRatio.get() / CONFIG.warMagiculeDamageDivisor.get()
         );
      }

      if (aspect == CaedrosGodOfConquestSkill.CaedrosAspect.DEATH) {
         int souls = getSoulPointsSafe(owner);
         damage *= (float)(1.0 + souls / CONFIG.deathSoulDamageDivisor.get());
      }

      if (hasCaedrosToggled(owner)) {
         int guiltTier = Math.min((Integer)CONFIG.maxGuiltTier.get(), (int)(getGuilt(owner) / (Double)CONFIG.guiltTierDivisor.get()));
         damage *= 1.0F + guiltTier * ((Double)CONFIG.finalDamageGuiltPerTier.get()).floatValue();
      }

      return damage;
   }

   private static float getFearDamageMultiplier(ServerPlayer owner) {
      if (!hasCaedrosToggled(owner)) {
         return 1.0F;
      }

      int totalFearStacks = 0;

      for (LivingEntity e : owner.level()
         .getEntitiesOfClass(
            LivingEntity.class, owner.getBoundingBox().inflate(((Integer)CONFIG.fearInsanityRadius.get()).intValue()), x -> x.isAlive() && x != owner
         )) {
         MobEffectInstance fear = e.getEffect(TensuraMobEffects.getReference(TensuraMobEffects.FEAR));
         if (fear != null) {
            totalFearStacks += fear.getAmplifier() + 1;
         }
      }

      float per = isMasteredCaedros(owner)
         ? ((Double)CONFIG.fearDamagePerStackMastered.get()).floatValue()
         : ((Double)CONFIG.fearDamagePerStack.get()).floatValue();
      return 1.0F + totalFearStacks * per;
   }

   private static DamageSource trueCaedrosSource(ServerPlayer player, ResourceKey<DamageType> type) {
      return TensuraDamageTypes.getEntityDamageSource(player.level(), type, player);
   }

   private static void dealTrueDamage(ServerPlayer sourcePlayer, LivingEntity target, DamageSource source, float damage) {
      float amount = Math.max(((Double)CONFIG.trueDamageMinimum.get()).floatValue(), damage);
      if (!(target instanceof Player p && (p.isCreative() || p.isSpectator() || p.getAbilities().invulnerable))) {
         target.invulnerableTime = 0;
         target.hurt(source, amount);
         target.invulnerableTime = 0;
         TensuraDamageHelper.directSpiritualHurt(target, sourcePlayer, source, amount);
         target.invulnerableTime = 0;
         onLivingKilledByCaedros(target, source);
      }
   }

   private static void knockAway(ServerPlayer owner, LivingEntity target, double horizontal, double vertical) {
      Vec3 push = target.position().subtract(owner.position());
      if (push.lengthSqr() < 1.0E-4) {
         push = owner.getLookAngle();
      }

      push = push.normalize().scale(horizontal);
      target.setDeltaMovement(target.getDeltaMovement().add(push.x, vertical, push.z));
      target.hurtMarked = true;
   }

   private static void swingBurst(ServerLevel level, Vec3 center, double radius, int count) {
      for (int i = 0; i < count; i++) {
         double a = (Math.PI * 2) * i / count;
         double x = center.x + Math.cos(a) * radius;
         double z = center.z + Math.sin(a) * radius;
         level.sendParticles(ParticleTypes.SWEEP_ATTACK, x, center.y, z, 1, 0.0, 0.0, 0.0, 0.0);
      }
   }

   private static void shockwave(ServerLevel level, Vec3 center, double radius, double y, int points) {
      for (int i = 0; i < points; i++) {
         double a = (Math.PI * 2) * i / points;
         double x = center.x + Math.cos(a) * radius;
         double z = center.z + Math.sin(a) * radius;
         level.sendParticles(TensuraParticleUtils.getRedWave(0.9F, 2.4F, -0.15F, false), x, center.y + y, z, 1, 0.0, 0.0, 0.0, 0.0);
      }
   }

   private static int getSoulPointsSafe(ServerPlayer player) {
      try {
         return TensuraStorages.getExistenceFrom(player).getSoulPoints();
      } catch (Throwable ignored) {
         return 0;
      }
   }

   private static void setSoulPointsSafe(ServerPlayer player, int value) {
      try {
         IExistence existence = TensuraStorages.getExistenceFrom(player);
         existence.setSoulPoints(Math.max(0, value));
         existence.markDirty();
      } catch (Throwable var3) {
      }
   }

   private static boolean tryDeathRevive(ServerPlayer owner) {
      if (!(Boolean)CONFIG.enableDeathRevive.get()) {
         return false;
      }

      if (getActiveAspect(owner) != CaedrosGodOfConquestSkill.CaedrosAspect.DEATH) {
         return false;
      }

      int souls = getSoulPointsSafe(owner);
      if (souls < (Integer)CONFIG.deathReviveSoulPointCost.get()) {
         return false;
      }

      setSoulPointsSafe(owner, souls - (Integer)CONFIG.deathReviveSoulPointCost.get());
      owner.setHealth(owner.getMaxHealth());
      owner.removeAllEffects();
      owner.clearFire();
      return true;
   }

   private static void stripCaedros(ServerPlayer player) {
      try {
         Skills skills = SkillAPI.getSkillsFrom(player);
         ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(CAEDROS_ID);
         if (skill != null) {
            skills.forgetSkill(skill);
            skills.markDirty();
         }
      } catch (Throwable var3) {
      }
   }

   private static void cleanupControl(ServerPlayer owner) {
      MinecraftServer server = owner.getServer();
      if (server != null) {
         for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            if (player != owner
               && player.getPersistentData().hasUUID("TenaddexControlBy")
               && owner.getUUID().equals(player.getPersistentData().getUUID("TenaddexControlBy"))) {
               restoreControlVictim(player);
            }
         }
      }
   }

   private static void cleanupFamine(ServerPlayer owner) {
      MinecraftServer server = owner.getServer();
      if (server != null) {
         UUID ownerId = owner.getUUID();

         for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            if (player.getPersistentData().hasUUID("TenaddexFamineBy") && ownerId.equals(player.getPersistentData().getUUID("TenaddexFamineBy"))) {
               clearFamineData(player);
            }
         }

         for (ServerLevel level : server.getAllLevels()) {
            for (ServerPlayer player : level.players()) {
               AABB area = player.getBoundingBox().inflate(128.0);

               for (LivingEntity entity : level.getEntitiesOfClass(
                  LivingEntity.class,
                  area,
                  e -> e.isAlive() && e.getPersistentData().hasUUID("TenaddexFamineBy") && ownerId.equals(e.getPersistentData().getUUID("TenaddexFamineBy"))
               )) {
                  clearFamineData(entity);
               }
            }
         }
      }
   }

   private static void clearFamineData(LivingEntity entity) {
      entity.getPersistentData().remove("TenaddexFamineDebuffs");
      entity.getPersistentData().remove("TenaddexFamineStacks");
      entity.getPersistentData().remove("TenaddexFamineUntil");
      entity.getPersistentData().remove("TenaddexFamineBy");
   }

   private static void cleanupWar(ServerPlayer owner) {
      MinecraftServer server = owner.getServer();
      if (server != null) {
         for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            if (player != owner
               && player.getPersistentData().hasUUID("TenaddexWarBy")
               && owner.getUUID().equals(player.getPersistentData().getUUID("TenaddexWarBy"))) {
               player.getPersistentData().putBoolean("TenaddexConquered", false);
               player.getPersistentData().remove("TenaddexWarBy");
            }
         }
      }
   }

   private static void cleanupDeath(ServerPlayer owner) {
      MinecraftServer server = owner.getServer();
      if (server != null) {
         UUID ownerId = owner.getUUID();

         for (ServerLevel level : server.getAllLevels()) {
            for (ServerPlayer player : level.players()) {
               AABB area = player.getBoundingBox().inflate(160.0);

               for (LivingEntity replica : level.getEntitiesOfClass(
                  LivingEntity.class,
                  area,
                  e -> e.getPersistentData().hasUUID("TenaddexReplicaOwner") && ownerId.equals(e.getPersistentData().getUUID("TenaddexReplicaOwner"))
               )) {
                  replica.discard();
               }
            }
         }
      }
   }

   private static void onAspectEnable(ServerPlayer player, CaedrosGodOfConquestSkill.CaedrosAspect aspect) {
      if (aspect == CaedrosGodOfConquestSkill.CaedrosAspect.WAR) {
         player.getPersistentData().putDouble("TenaddexWarOwnerBaseEP", getEP(player));
         player.getPersistentData().putDouble("TenaddexWarTempGain", 0.0);
         player.getPersistentData().putBoolean("TenaddexCaedrosTotalWar", false);
      }
   }

   private static void onAspectDisable(ServerPlayer player, CaedrosGodOfConquestSkill.CaedrosAspect aspect) {
      if (aspect == CaedrosGodOfConquestSkill.CaedrosAspect.CONTROL) {
         player.getPersistentData().remove("TenaddexControlLockedSkill");
         cleanupControl(player);
      }

      if (aspect == CaedrosGodOfConquestSkill.CaedrosAspect.FAMINE) {
         cleanupFamine(player);
      }

      if (aspect == CaedrosGodOfConquestSkill.CaedrosAspect.WAR) {
         player.getPersistentData().putBoolean("TenaddexCaedrosTotalWar", false);
         double base = player.getPersistentData().getDouble("TenaddexWarOwnerBaseEP");
         if (base > 0.0) {
            setEP(player, base);
         }

         player.getPersistentData().remove("TenaddexWarOwnerBaseEP");
         player.getPersistentData().remove("TenaddexWarTempGain");
         cleanupWar(player);
      }

      if (aspect == CaedrosGodOfConquestSkill.CaedrosAspect.DEATH) {
         cleanupDeath(player);
      }
   }

   private static boolean hasCaedros(ServerPlayer player) {
      Skills skills = SkillAPI.getSkillsFrom(player);
      ManasSkill caedros = (ManasSkill)SkillAPI.getSkillRegistry().get(CAEDROS_ID);
      return caedros != null && skills.getSkill(caedros).isPresent();
   }

   private static boolean hasCaedrosToggled(ServerPlayer player) {
      ManasSkillInstance instance = getCaedrosInstance(player);
      return instance != null && instance.isToggled();
   }

   private static ManasSkillInstance getCaedrosInstance(ServerPlayer player) {
      Skills skills = SkillAPI.getSkillsFrom(player);
      ManasSkill caedros = (ManasSkill)SkillAPI.getSkillRegistry().get(CAEDROS_ID);
      return caedros == null ? null : (ManasSkillInstance)skills.getSkill(caedros).orElse(null);
   }

   private static boolean isMasteredCaedros(ServerPlayer player) {
      Skills skills = SkillAPI.getSkillsFrom(player);
      ManasSkill caedros = (ManasSkill)SkillAPI.getSkillRegistry().get(CAEDROS_ID);
      if (caedros == null) {
         return false;
      }

      Optional<ManasSkillInstance> optional = skills.getSkill(caedros);
      return optional.filter(inst -> inst.isMastered(player)).isPresent();
   }

   private static List<String> getLockableSkillIds(ServerPlayer player) {
      List<String> out = new ArrayList<>();
      Skills skills = SkillAPI.getSkillsFrom(player);

      for (ManasSkillInstance inst : List.copyOf(skills.getLearnedSkills())) {
         if (inst != null
            && inst.getSkill() != null
            && inst.getSkillId() != null
            && !CAEDROS_ID.equals(inst.getSkillId())
            && inst.getSkill() instanceof Skill skill
            && (skill.getType() == SkillType.UNIQUE || skill.getType() == SkillType.ULTIMATE)) {
            out.add(inst.getSkillId().toString());
         }
      }

      return out;
   }

   private static List<String> getEatenSkills(ServerPlayer player) {
      List<String> out = new ArrayList<>();
      ListTag list = player.getPersistentData().getList("TenaddexEatenSkills", 8);

      for (int i = 0; i < list.size(); i++) {
         out.add(list.getString(i));
      }

      return out;
   }

   private static void saveEatenSkills(ServerPlayer player, List<String> ids) {
      ListTag list = new ListTag();

      for (String s : ids) {
         if (s != null && !s.isBlank()) {
            list.add(StringTag.valueOf(s));
         }
      }

      player.getPersistentData().put("TenaddexEatenSkills", list);
   }

   private static void addEatenSkill(ServerPlayer player, String id) {
      List<String> list = getEatenSkills(player);
      if (!list.contains(id)) {
         list.add(id);
         saveEatenSkills(player, list);
      }
   }

   private static double getGuilt(Player player) {
      return player.getPersistentData().getDouble("TenaddexCaedrosGuilt");
   }

   private static boolean consumeGuilt(Player player, double amount) {
      if (amount <= 0.0) {
         return true;
      }

      double current = getGuilt(player);
      if (current < amount) {
         return false;
      }

      player.getPersistentData().putDouble("TenaddexCaedrosGuilt", Math.max(0.0, current - amount));
      return true;
   }

   private static void addGuilt(Player player, double amount) {
      if (!(amount <= 0.0)) {
         player.getPersistentData().putDouble("TenaddexCaedrosGuilt", getGuilt(player) + amount);
      }
   }

   private static void queueConstructSword(ServerPlayer owner, ManasSkillInstance instance) {
      List<CaedrosGodOfConquestSkill.SwordSpirit> list = CONSTRUCT_SWORDS.computeIfAbsent(owner.getUUID(), k -> new ArrayList<>());
      pushConstructSword(list, owner, instance);
      CONSTRUCT_SWORDS.put(owner.getUUID(), list);
   }

   private static void pushConstructSword(List<CaedrosGodOfConquestSkill.SwordSpirit> list, ServerPlayer owner, ManasSkillInstance instance) {
      while (list.size() >= CONFIG.constructMaxSwords.get()) {
         list.remove(0);
         addGuilt(owner, (Double)CONFIG.constructSwordBreakGuilt.get());
      }

      CaedrosGodOfConquestSkill.SwordSpirit sword = new CaedrosGodOfConquestSkill.SwordSpirit();
      sword.usesLeft = (Integer)CONFIG.constructSwordUses.get();
      sword.masteryBonus = instance != null && instance.isMastered(owner);
      sword.lastOrbitPos = owner.position().add(0.0, 1.0, 0.0);
      list.add(sword);
      if (owner.level() instanceof ServerLevel level) {
         level.sendParticles((TensuraParticleType)TensuraParticleTypes.SOUL.get(), owner.getX(), owner.getY() + 1.0, owner.getZ(), 8, 0.45, 0.35, 0.45, 0.0);
         level.sendParticles(
            (TensuraParticleType)TensuraParticleTypes.BLACK_LIGHTNING_SPARK.get(), owner.getX(), owner.getY() + 1.0, owner.getZ(), 6, 0.25, 0.25, 0.25, 0.0
         );
         level.sendParticles(
            (TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(), owner.getX(), owner.getY() + 1.0, owner.getZ(), 5, 0.2, 0.2, 0.2, 0.0
         );
         level.playSound(null, owner.getX(), owner.getY(), owner.getZ(), SoundEvents.ALLAY_AMBIENT_WITHOUT_ITEM, SoundSource.PLAYERS, 1.0F, 0.6F);
      }
   }

   private static boolean isHoldingHalberd(ServerPlayer player) {
      return isHalberdStack(player.getMainHandItem());
   }

   private static boolean isHalberdStack(ItemStack stack) {
      return !stack.isEmpty() && stack.is((Item)TenaddexItems.CAEDROS_ASHEN_HALBERD.get());
   }

   private static void renderCaedrosCircleFlavor(ServerLevel level, LivingEntity owner, double radius) {
      double y = owner.getY() + 0.12;

      for (int i = 0; i < 22; i++) {
         double a = (Math.PI * 2) * i / 22.0;
         double x = owner.getX() + Math.cos(a) * radius;
         double z = owner.getZ() + Math.sin(a) * radius;
         level.sendParticles((TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(), x, y, z, 1, 0.03, 0.02, 0.03, 0.0);
         if (i % 2 == 0) {
            level.sendParticles((TensuraParticleType)TensuraParticleTypes.BLACK_FIRE.get(), x, y + 0.02, z, 1, 0.02, 0.01, 0.02, 0.0);
         }
      }

      level.sendParticles(
         TensuraParticleUtils.getRedWave(0.85F, (float)(radius * 2.1), -0.08F, false), owner.getX(), y + 0.02, owner.getZ(), 1, 0.0, 0.0, 0.0, 0.0
      );
   }

   private static void plagueSlashBurst(ServerLevel level, Vec3 center, double radius, int points) {
      for (int i = 0; i < points; i++) {
         double a = (Math.PI * 2) * i / points;
         double x = center.x + Math.cos(a) * radius;
         double z = center.z + Math.sin(a) * radius;
         level.sendParticles(ParticleTypes.SWEEP_ATTACK, x, center.y, z, 1, 0.0, 0.0, 0.0, 0.0);
         if (i % 2 == 0) {
            level.sendParticles((TensuraParticleType)TensuraParticleTypes.BLACK_FIRE.get(), x, center.y, z, 1, 0.04, 0.04, 0.04, 0.0);
         }

         level.sendParticles((TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(), x, center.y + 0.05, z, 1, 0.03, 0.03, 0.03, 0.0);
      }

      level.sendParticles(
         TensuraParticleUtils.getRedWave(0.85F, (float)(radius * 1.5), -0.08F, false), center.x, center.y + 0.02, center.z, 1, 0.0, 0.0, 0.0, 0.0
      );
   }

   private static void applyRandomPlagueDebuffs(LivingEntity target, boolean mastered, int picks) {
      int amp = mastered ? (Integer)CONFIG.plagueDebuffAmplifierMastered.get() : (Integer)CONFIG.plagueDebuffAmplifier.get();
      int dur = mastered ? (Integer)CONFIG.plagueDebuffDurationMasteredTicks.get() : (Integer)CONFIG.plagueDebuffDurationTicks.get();
      List<Integer> pool = new ArrayList<>();

      for (int i = 0; i < 12; i++) {
         pool.add(i);
      }

      for (int i = 0; i < picks && !pool.isEmpty(); i++) {
         int idx = pool.remove(target.getRandom().nextInt(pool.size()));
         switch (idx) {
            case 0:
               target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.INFECTION), dur, amp, false, false, true));
               break;
            case 1:
               target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.CURSE), dur, amp, false, false, true));
               break;
            case 2:
               target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.FRAGILITY), dur, amp, false, false, true));
               break;
            case 3:
               target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE), dur, amp, false, false, true));
               break;
            case 4:
               target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.CORROSION), dur, amp, false, false, true));
               break;
            case 5:
               target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.CHILL), dur, amp, false, false, true));
               break;
            case 6:
               target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.WEBBED), dur, Math.min(2, amp), false, false, true));
               break;
            case 7:
               target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.PARALYSIS), dur, Math.min(1, amp), false, false, true));
               break;
            case 8:
               target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.BLACK_BURN), dur, Math.min(1, amp), false, false, true));
               break;
            case 9:
               target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.DROWSINESS), dur, Math.min(1, amp), false, false, true));
               break;
            case 10:
               target.addEffect(
                  new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MOVEMENT_INTERFERENCE), dur, Math.min(1, amp), false, false, true)
               );
               break;
            case 11:
               target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.OPPRESSION), dur, Math.min(1, amp), false, false, true));
         }
      }

      levelParticlesForPlague(target);
   }

   private static void levelParticlesForPlague(LivingEntity target) {
      if (target.level() instanceof ServerLevel level) {
         level.sendParticles(
            (TensuraParticleType)TensuraParticleTypes.FALLING_ACID.get(), target.getX(), target.getY() + 0.8, target.getZ(), 10, 0.22, 0.3, 0.22, 0.0
         );
         level.sendParticles(
            (TensuraParticleType)TensuraParticleTypes.BLACK_FIRE.get(), target.getX(), target.getY() + 0.5, target.getZ(), 10, 0.18, 0.25, 0.18, 0.0
         );
         level.sendParticles(
            (TensuraParticleType)TensuraParticleTypes.DARK_RED_LIGHTNING_SPARK.get(),
            target.getX(),
            target.getY() + 0.9,
            target.getZ(),
            5,
            0.12,
            0.12,
            0.12,
            0.0
         );
      }
   }

   private static void applyConstructDebuffs(LivingEntity target, boolean mastered) {
      int amp = mastered ? 1 : 0;
      int dur = mastered ? 140 : 100;
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.BURDEN), dur, 1 + amp, false, false, true));
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.FRAGILITY), dur, amp, false, false, true));
      target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.MAGIC_INTERFERENCE), dur, amp, false, false, true));
      if (mastered) {
         target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.OPPRESSION), 80, 0, false, false, true));
      }
   }

   public enum CaedrosAspect {
      CONTROL,
      FAMINE,
      WAR,
      DEATH;
   }

   private static final class CloudData {
      boolean inWindup = false;
      boolean active = false;
      UUID ownerId;
      int windupTicks;
      int remainingTicks;
      double prevAttackBase = -1.0;
      double prevHpBase = -1.0;
      ResourceLocation castDimension;
      double prevBorderCenterX = 0.0;
      double prevBorderCenterZ = 0.0;
      double prevBorderSize = 6.0E7;
      double prevBorderDamage = 0.2;
      boolean savedBorder = false;
   }

   private static final class ConquestData {
      boolean hasSummoned = false;
      boolean firedMiniBang = false;
      boolean charging = false;
      int chargeTicks = 0;
   }

   private static final class SwordSpirit {
      int usesLeft = 1;
      boolean masteryBonus = false;
      Vec3 lastOrbitPos = Vec3.ZERO;
   }
}
