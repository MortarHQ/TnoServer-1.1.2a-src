package com.tno_tensura_extras.util;

import com.mojang.datafixers.util.Pair;
import com.tno_tensura_extras.TensuraExtras;
import com.tno_tensura_extras.entity.LooterBlackHoleEntity;
import com.tno_tensura_extras.network.LooterPackets;
import com.tno_tensura_extras.registry.ExtrasSkills;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.storage.api.StorageHolder;
import io.github.manasmods.tensura.entity.magic.MagicCircle;
import io.github.manasmods.tensura.entity.template.subclass.ISubordinate;
import io.github.manasmods.tensura.entity.variant.MagicCircleVariant;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.Clone;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;

public final class LooterManager {
   public static final String SHADOW_MARKER = "tno_tensura_extras_looter_shadow";
   public static final String SHADOW_OWNER = "tno_tensura_extras_looter_owner";
   private static final String ROOT = "tno_tensura_extras_looter";
   private static final String ENTRIES = "entries";
   private static final String ACTIVE_SHADOWS = "active_shadows";
   private static final String ACTIVE_ID = "id";
   private static final String ENTRY_ID = "id";
   private static final String ENTRY_SOURCE_UUID = "source_uuid";
   private static final String ENTRY_TYPE = "type";
   private static final String ENTRY_ENTITY = "entity";
   private static final String ENTRY_SKILLS = "skills";
   private static final String ENTRY_AURA = "aura";
   private static final String ENTRY_EP = "ep";
   private static final String ENTRY_MAGICULE = "magicule";
   private static final String ENTRY_SHP = "shp";
   private static final String ENTRY_BASE_AURA = "base_aura";
   private static final String ENTRY_BASE_MAGICULE = "base_magicule";
   private static final String ENTRY_BASE_SHP = "base_shp";
   private static final String ANIMATING = "tno_tensura_extras_looter_summoning";
   private static final String RISE_TICK = "looter_rise_tick";
   private static final String TARGET_X = "looter_target_x";
   private static final String TARGET_Y = "looter_target_y";
   private static final String TARGET_Z = "looter_target_z";
   private static final String START_Y = "looter_start_y";
   private static final int MAX_ACTIVE_SHADOWS = 5;
   private static final int MASTERED_MAX_ACTIVE_SHADOWS = 8;
   private static final int MAX_STORED_ENTRIES = 12;
   private static final int MASTERED_MAX_STORED_ENTRIES = 20;
   private static final ResourceLocation LOOTER_SKILL_ID = ResourceLocation.fromNamespaceAndPath("tno_tensura_extras", "looter");
   private static final int RISE_TICKS = 40;
   private static final Map<UUID, LooterManager.AnimationState> ANIMATIONS = new HashMap<>();
   private static final Map<UUID, Set<UUID>> PENDING_ACTIVE_REMOVALS = new HashMap<>();
   private static final Set<UUID> CAPTURE_LOCKS = new HashSet<>();
   private static final Set<MinecraftServer> INITIALIZED_SERVERS = Collections.newSetFromMap(new IdentityHashMap<>());

   private LooterManager() {
   }

   public static void openSelection(ServerPlayer player) {
      if (!getEntries(player).isEmpty()) {
         NetworkManager.sendToPlayer(player, new LooterPackets.OpenScreenPayload(encodeEntries(player)));
      }
   }

   public static void onLivingDeath(LivingDeathEvent event) {
      LivingEntity victim = event.getEntity();
      if (!victim.level().isClientSide() && !event.isCanceled()) {
         if (isShadow(victim)) {
            broadcastShadowState(victim.level().getServer(), victim.getUUID(), false);
            removeAnimation(victim.getUUID());
            removeActiveShadow(victim.level().getServer(), getShadowOwnerUUID(victim), victim.getUUID());
            markNoDrops(victim);
         } else if (victim instanceof Mob && !(victim instanceof Player)) {
            ServerPlayer owner = resolvePlayer(event.getSource().getEntity());
            if (owner == null) {
               owner = resolvePlayer(event.getSource().getDirectEntity());
            }

            if (owner == null && victim.getKillCredit() instanceof ServerPlayer creditedPlayer) {
               owner = creditedPlayer;
            }

            if (owner != null && hasLooterSkill(owner) && !isShadow(owner)) {
               if (CAPTURE_LOCKS.add(victim.getUUID())) {
                  if (!capture(owner, victim)) {
                     CAPTURE_LOCKS.remove(victim.getUUID());
                  }
               }
            }
         }
      }
   }

   public static void onLivingDrops(LivingDropsEvent event) {
      if (isShadow(event.getEntity())) {
         event.setCanceled(true);
         event.getDrops().clear();
      }
   }

   public static void onLivingExperienceDrop(LivingExperienceDropEvent event) {
      if (isShadow(event.getEntity())) {
         event.setDroppedExperience(0);
         event.setCanceled(true);
      }
   }

   public static void onEntityLeaveLevel(EntityLeaveLevelEvent event) {
      if (!event.getLevel().isClientSide() && event.getEntity() instanceof LivingEntity living) {
         if (!isShadow(living)) {
            CAPTURE_LOCKS.remove(living.getUUID());
         } else {
            RemovalReason reason = event.getEntity().getRemovalReason();
            boolean temporary = reason == RemovalReason.UNLOADED_TO_CHUNK
               || reason == RemovalReason.UNLOADED_WITH_PLAYER
               || reason == RemovalReason.CHANGED_DIMENSION;
            if (!temporary) {
               broadcastShadowState(event.getLevel().getServer(), living.getUUID(), false);
               removeActiveShadow(event.getLevel().getServer(), getShadowOwnerUUID(living), living.getUUID());
               removeAnimation(living.getUUID());
            }
         }
      }
   }

   public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
      if (!event.getLevel().isClientSide() && event.getEntity() instanceof Mob shadow && isShadow(shadow)) {
         broadcastShadowState(event.getLevel().getServer(), shadow.getUUID(), true);
         restoreAnimation(shadow);
      }
   }

   public static void onLivingChangeTarget(LivingChangeTargetEvent event) {
      LivingEntity shadow = event.getEntity();
      if (isShadow(shadow)) {
         LivingEntity target = event.getNewAboutToBeSetTarget();
         UUID owner = getShadowOwnerUUID(shadow);
         if (target != null) {
            if (owner != null && owner.equals(target.getUUID())) {
               event.setCanceled(true);
               return;
            }

            try {
               if (SubordinateHelper.isAlly(shadow, target)) {
                  event.setCanceled(true);
               }
            } catch (RuntimeException var5) {
            }
         }
      }
   }

   public static void onServerTick(Post event) {
      MinecraftServer server = event.getServer();
      if (INITIALIZED_SERVERS.add(server)) {
         discoverAnimations(server);
      }

      if (server.getTickCount() % 10 == 0) {
         clearAllyTargets(server);
      }

      if (!ANIMATIONS.isEmpty()) {
         Iterator<Entry<UUID, LooterManager.AnimationState>> iterator = ANIMATIONS.entrySet().iterator();

         while (iterator.hasNext()) {
            Entry<UUID, LooterManager.AnimationState> mapEntry = iterator.next();
            LooterManager.AnimationState state = mapEntry.getValue();
            Entity loaded = findEntity(server, state.entityId);
            if (loaded != null) {
               if (!(loaded instanceof Mob shadow && shadow.isAlive() && isShadow(shadow))) {
                  iterator.remove();
               } else if (state.tick >= 40) {
                  finishAnimation(shadow, state);
                  iterator.remove();
               } else {
                  double progress = (state.tick + 1) / 40.0;
                  double y = state.startY + (state.targetY - state.startY) * progress;
                  shadow.setPos(state.x, y, state.z);
                  shadow.getPersistentData().putInt("looter_rise_tick", state.tick + 1);
                  spawnRiseParticles(serverLevelOf(shadow), state, state.tick);
                  state.tick++;
               }
            }
         }
      }
   }

   public static void onServerStopped(ServerStoppedEvent event) {
      ANIMATIONS.clear();
      PENDING_ACTIVE_REMOVALS.clear();
      CAPTURE_LOCKS.clear();
      INITIALIZED_SERVERS.remove(event.getServer());
   }

   public static void onPlayerClone(Clone event) {
      CompoundTag originalData = event.getOriginal().getPersistentData();
      if (originalData.contains("tno_tensura_extras_looter", 10)) {
         event.getEntity().getPersistentData().put("tno_tensura_extras_looter", originalData.getCompound("tno_tensura_extras_looter").copy());
      }
   }

   public static void clearActiveShadowsForWorldReset(ServerPlayer player) {
      if (player != null) {
         CompoundTag root = player.getPersistentData().getCompound("tno_tensura_extras_looter");
         root.remove("active_shadows");
         player.getPersistentData().put("tno_tensura_extras_looter", root);
         PENDING_ACTIVE_REMOVALS.remove(player.getUUID());
      }
   }

   public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         Set<UUID> pending = PENDING_ACTIVE_REMOVALS.remove(player.getUUID());
         if (pending != null) {
            for (UUID entityId : pending) {
               removeActiveShadow(player, entityId);
            }
         }

         for (ServerLevel level : player.server.getAllLevels()) {
            for (Entity entity : level.getAllEntities()) {
               if (entity instanceof LivingEntity living && living.isAlive() && isShadow(living)) {
                  LooterPackets.sendShadowState(List.of(player), living.getUUID(), true);
               }
            }
         }

         try {
            if (player instanceof StorageHolder holder) {
               holder.manasCore$sync();
            }
         } catch (RuntimeException var8) {
         }
      }
   }

   public static boolean hasLooterSkill(LivingEntity entity) {
      try {
         return SkillAPI.getSkillsFrom(entity).getSkill(ResourceLocation.fromNamespaceAndPath("tno_tensura_extras", "looter")).isPresent();
      } catch (RuntimeException ignored) {
         return false;
      }
   }

   public static boolean isShadow(LivingEntity entity) {
      if (entity == null) {
         return false;
      }

      if (!entity.getPersistentData().getBoolean("tno_tensura_extras_looter_shadow") && !entity.getTags().contains("tno_tensura_extras_looter_shadow")) {
         try {
            IExistence existence = TensuraStorages.getExistenceFrom(entity);
            if (existence != null && existence.getSummonedAbility() != null) {
               ResourceLocation id = SkillAPI.getSkillRegistry().getId(existence.getSummonedAbility().getSkill());
               return id != null && "tno_tensura_extras:looter".equals(id.toString()) && getShadowOwnerUUID(entity) != null;
            } else {
               return false;
            }
         } catch (RuntimeException ignored) {
            return false;
         }
      } else {
         return true;
      }
   }

   public static UUID getShadowOwnerUUID(LivingEntity entity) {
      if (entity == null) {
         return null;
      }

      if (entity.getPersistentData().hasUUID("tno_tensura_extras_looter_owner")) {
         return entity.getPersistentData().getUUID("tno_tensura_extras_looter_owner");
      }

      try {
         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         if (existence != null) {
            if (existence.getSummoner() != null) {
               return existence.getSummoner();
            }

            if (existence.getPermanentOwner() != null) {
               return existence.getPermanentOwner();
            }
         }
      } catch (RuntimeException var2) {
      }

      return null;
   }

   public static void beginSummon(ServerPlayer player, UUID entryId) {
      if (hasLooterSkill(player) && countActiveShadows(player) < getMaxActiveShadows(player)) {
         CompoundTag entry = findEntry(player, entryId);
         if (entry != null) {
            ResourceLocation typeId = ResourceLocation.tryParse(entry.getString("type"));
            if (typeId != null && BuiltInRegistries.ENTITY_TYPE.containsKey(typeId)) {
               ServerLevel level = player.serverLevel();
               BlockHitResult hit = ObjectSelectionHelper.getPlayerPOVHitResult(level, player, Fluid.NONE, 10.0);
               Vec3 target = hit.getLocation();
               if (hit.getType() == Type.MISS) {
                  target = player.position().add(player.getLookAngle().scale(3.0));
               }

               if (((EntityType)BuiltInRegistries.ENTITY_TYPE.get(typeId)).create(level) instanceof Mob shadow) {
                  try {
                     shadow.finalizeSpawn(level, level.getCurrentDifficultyAt(BlockPos.containing(target)), MobSpawnType.MOB_SUMMONED, null);
                     CompoundTag snapshot = entry.getCompound("entity").copy();
                     shadow.load(snapshot);
                     shadow.setUUID(UUID.randomUUID());
                     shadow.setPos(target.x, target.y - shadow.getBbHeight() * 1.5, target.z);
                     shadow.setYRot(player.getYRot());
                     shadow.setXRot(0.0F);
                     shadow.setYHeadRot(player.getYRot());
                     shadow.setYBodyRot(player.getYRot());
                     shadow.setNoAi(true);
                     shadow.noPhysics = true;
                     shadow.setInvulnerable(false);
                     shadow.setPersistenceRequired();
                     if (shadow.getHealth() <= 0.0F) {
                        shadow.setHealth(shadow.getMaxHealth());
                     }

                     applyStatsAndSkills(shadow, player, entry);
                     markShadow(shadow, player);
                     if (!level.addFreshEntity(shadow)) {
                        shadow.discard();
                        return;
                     }

                     broadcastShadowState(player.server, shadow.getUUID(), true);
                     addActiveShadow(player, shadow.getUUID());
                     removeEntry(player, entryId);
                     CompoundTag data = shadow.getPersistentData();
                     data.putBoolean("tno_tensura_extras_looter_summoning", true);
                     data.putDouble("looter_start_y", shadow.getY());
                     data.putDouble("looter_target_x", target.x);
                     data.putDouble("looter_target_y", target.y);
                     data.putDouble("looter_target_z", target.z);
                     data.putInt("looter_rise_tick", 0);
                     LooterManager.AnimationState state = new LooterManager.AnimationState(
                        shadow.getUUID(), player.getUUID(), target.x, target.y, target.z, shadow.getY(), 0
                     );
                     ANIMATIONS.put(shadow.getUUID(), state);
                     spawnSummonVisuals(level, target, player);
                     addLooterMasteryPoint(player);
                  } catch (RuntimeException exception) {
                     shadow.discard();
                     removeActiveShadow(player.server, player.getUUID(), shadow.getUUID());
                     removeEntry(player, entryId);
                     TensuraExtras.LOGGER.debug("Unable to summon Looter shadow", exception);
                  }
               } else {
                  removeEntry(player, entryId);
               }
            } else {
               removeEntry(player, entryId);
            }
         }
      }
   }

   private static void spawnSummonVisuals(ServerLevel level, Vec3 target, ServerPlayer player) {
      try {
         CompoundTag circleTag = new CompoundTag();
         MagicCircle.castMagicCircle(
            "tno_looter_circle", 3.5F, 48, target, MagicCircleVariant.DARK, true, player, circleTag, (ManasSkill)null, 0, Pair.of(0.0, 0.0)
         );
      } catch (RuntimeException | LinkageError exception) {
         TensuraExtras.LOGGER.debug("Unable to render Looter magic circle", exception);
      }

      try {
         LooterBlackHoleEntity.spawn(level, target.add(0.0, 0.025, 0.0), player);
      } catch (RuntimeException | LinkageError exception) {
         TensuraExtras.LOGGER.debug("Unable to render Looter black hole", exception);
      }
   }

   public static String encodeEntries(ServerPlayer player) {
      StringBuilder encoded = new StringBuilder();

      for (CompoundTag entry : getEntries(player)) {
         UUID id = readUUID(entry, "id");
         String type = entry.getString("type");
         if (id != null && !type.isBlank()) {
            if (encoded.length() > 0) {
               encoded.append(';');
            }

            encoded.append(id)
               .append(',')
               .append(type)
               .append(',')
               .append(formatNetworkNumber(entry.getDouble("ep")))
               .append(',')
               .append(formatNetworkNumber(entry.getDouble("magicule")))
               .append(',')
               .append(formatNetworkNumber(entry.getDouble("shp")));
         }
      }

      return encoded.toString();
   }

   private static String formatNetworkNumber(double value) {
      return Double.isFinite(value) && value >= 0.0 ? Double.toString(value) : "0";
   }

   private static boolean capture(ServerPlayer owner, LivingEntity victim) {
      List<CompoundTag> existing = getEntries(owner);
      UUID sourceUuid = victim.getUUID();

      for (CompoundTag entry : existing) {
         if (sourceUuid.equals(readUUID(entry, "source_uuid"))) {
            return true;
         }
      }

      if (existing.size() >= getMaxStoredEntries(owner)) {
         return false;
      }

      ResourceLocation typeId = BuiltInRegistries.ENTITY_TYPE.getKey(victim.getType());
      if (typeId == null) {
         return false;
      }

      CompoundTag skills = new CompoundTag();

      CompoundTag snapshot;
      try {
         snapshot = victim.saveWithoutId(new CompoundTag());
         sanitizeSnapshot(snapshot);
         SkillAPI.getSkillsFrom(victim).save(skills);
         removeLooterSkill(skills);
      } catch (RuntimeException exception) {
         TensuraExtras.LOGGER.debug("Unable to capture Looter target", exception);
         return false;
      }

      CompoundTag entry = new CompoundTag();
      entry.putUUID("id", UUID.randomUUID());
      entry.putUUID("source_uuid", sourceUuid);
      entry.putString("type", typeId.toString());
      entry.put("entity", snapshot);
      entry.put("skills", skills);

      try {
         IExistence existence = TensuraStorages.getExistenceFrom(victim);
         entry.putDouble("aura", existence.getAura());
         entry.putDouble("ep", existence.getEP());
         entry.putDouble("magicule", existence.getMagicule());
         entry.putDouble("shp", existence.getSpiritualHealth());
      } catch (RuntimeException ignored) {
         entry.putDouble("aura", 0.0);
         entry.putDouble("ep", 0.0);
         entry.putDouble("magicule", 0.0);
         entry.putDouble("shp", 0.0);
      }

      entry.putDouble("base_aura", EnergyHelper.getBaseMaxAura(victim));
      entry.putDouble("base_magicule", EnergyHelper.getBaseMaxMagicule(victim));
      AttributeInstance shp = victim.getAttribute(TensuraAttributes.MAX_SPIRITUAL_HEALTH);
      entry.putDouble("base_shp", shp == null ? 0.0 : shp.getBaseValue());
      ListTag entries = readEntryList(owner);
      entries.add(entry);
      writeEntryList(owner, entries);
      return true;
   }

   private static void releaseCaptureLock(CompoundTag entry) {
      UUID sourceUuid = readUUID(entry, "source_uuid");
      if (sourceUuid != null) {
         CAPTURE_LOCKS.remove(sourceUuid);
      }
   }

   private static void applyStatsAndSkills(Mob shadow, ServerPlayer owner, CompoundTag entry) {
      LooterManager.AttributeSetter.set(shadow, TensuraAttributes.MAX_AURA, entry.getDouble("base_aura"));
      LooterManager.AttributeSetter.set(shadow, TensuraAttributes.MAX_MAGICULE, entry.getDouble("base_magicule"));
      LooterManager.AttributeSetter.set(shadow, TensuraAttributes.MAX_SPIRITUAL_HEALTH, entry.getDouble("base_shp"));

      try {
         CompoundTag skills = entry.getCompound("skills").copy();
         skills.putBoolean("resetExistingData", true);
         SkillAPI.getSkillsFrom(shadow).load(skills);
      } catch (RuntimeException var5) {
      }

      try {
         IExistence existence = TensuraStorages.getExistenceFrom(shadow);
         existence.setEP(entry.getDouble("ep"));
         existence.setAura(entry.getDouble("aura"));
         existence.setMagicule(entry.getDouble("magicule"));
         existence.setSpiritualHealth(entry.getDouble("shp"));
         existence.setSkippingEPDrop(true);
         existence.setSummonedSecond(0);
         existence.setPermanentOwner(owner.getUUID());
         existence.setTemporaryOwner(null);
         existence.setSummoner(owner.getUUID());
         existence.setSummonedAbility((ManasSkill)ExtrasSkills.LOOTER.get(), 0);
         existence.markDirty();
      } catch (RuntimeException var4) {
      }

      if (shadow instanceof ISubordinate subordinate) {
         subordinate.tame(owner);
      }

      if (shadow instanceof TamableAnimal tamable) {
         tamable.setOwnerUUID(owner.getUUID());
         tamable.setTame(true, true);
      }

      shadow.skipDropExperience();
      shadow.setTarget(null);
   }

   private static void markShadow(Mob shadow, ServerPlayer owner) {
      shadow.getPersistentData().putBoolean("tno_tensura_extras_looter_shadow", true);
      shadow.getPersistentData().putUUID("tno_tensura_extras_looter_owner", owner.getUUID());
      shadow.addTag("tno_tensura_extras_looter_shadow");
   }

   private static void markNoDrops(LivingEntity shadow) {
      shadow.getPersistentData().putBoolean("tno_tensura_extras_looter_shadow", true);
      if (shadow instanceof Mob mob) {
         mob.skipDropExperience();
      }

      try {
         IExistence existence = TensuraStorages.getExistenceFrom(shadow);
         existence.setSkippingEPDrop(true);
         existence.markDirty();
      } catch (RuntimeException var2) {
      }
   }

   private static void finishAnimation(Mob shadow, LooterManager.AnimationState state) {
      shadow.setPos(state.x, state.targetY, state.z);
      shadow.noPhysics = false;
      shadow.setNoAi(false);
      shadow.getPersistentData().remove("tno_tensura_extras_looter_summoning");
      shadow.setPersistenceRequired();
      shadow.setTarget(null);
   }

   private static void spawnRiseParticles(ServerLevel level, LooterManager.AnimationState state, int tick) {
      if (level != null) {
         if ((tick & 3) == 0) {
            level.sendParticles(ParticleTypes.REVERSE_PORTAL, state.x, state.targetY + 0.15, state.z, 1, 0.16, 0.05, 0.16, 0.008);
         }
      }
   }

   private static void discoverAnimations(MinecraftServer server) {
      for (ServerLevel level : server.getAllLevels()) {
         for (Entity entity : level.getAllEntities()) {
            if (entity instanceof Mob mob && isShadow(mob)) {
               CompoundTag data = mob.getPersistentData();
               if (data.getBoolean("tno_tensura_extras_looter_summoning")) {
                  restoreAnimation(mob);
               }
            }
         }
      }
   }

   private static void restoreAnimation(Mob shadow) {
      CompoundTag data = shadow.getPersistentData();
      if (data.getBoolean("tno_tensura_extras_looter_summoning")) {
         if (data.contains("looter_target_y", 6) && data.contains("looter_start_y", 6)) {
            double targetY = data.getDouble("looter_target_y");
            double startY = data.getDouble("looter_start_y");
            double targetX = data.contains("looter_target_x", 6) ? data.getDouble("looter_target_x") : shadow.getX();
            double targetZ = data.contains("looter_target_z", 6) ? data.getDouble("looter_target_z") : shadow.getZ();
            if (Double.isFinite(targetX) && Double.isFinite(targetY) && Double.isFinite(targetZ) && Double.isFinite(startY)) {
               shadow.setNoAi(true);
               shadow.noPhysics = true;
               shadow.setTarget(null);
               int tick = Math.max(0, Math.min(40, data.getInt("looter_rise_tick")));
               ANIMATIONS.putIfAbsent(
                  shadow.getUUID(), new LooterManager.AnimationState(shadow.getUUID(), getShadowOwnerUUID(shadow), targetX, targetY, targetZ, startY, tick)
               );
            } else {
               data.remove("tno_tensura_extras_looter_summoning");
               shadow.noPhysics = false;
               shadow.setNoAi(false);
            }
         } else {
            data.remove("tno_tensura_extras_looter_summoning");
            shadow.noPhysics = false;
            shadow.setNoAi(false);
         }
      }
   }

   private static void removeAnimation(UUID entityId) {
      ANIMATIONS.remove(entityId);
   }

   private static ServerLevel serverLevelOf(Entity entity) {
      return entity.level() instanceof ServerLevel level ? level : null;
   }

   private static void broadcastShadowState(MinecraftServer server, UUID entityId, boolean shadow) {
      if (server != null && entityId != null) {
         LooterPackets.sendShadowState(server.getPlayerList().getPlayers(), entityId, shadow);
      }
   }

   private static ServerPlayer resolvePlayer(Entity entity) {
      Entity current = entity;

      for (int i = 0; i < 4 && current != null; i++) {
         if (current instanceof ServerPlayer player) {
            return player;
         }

         if (current instanceof Projectile projectile && projectile.getOwner() != null) {
            current = projectile.getOwner();
         } else {
            if (!(current instanceof OwnableEntity ownable) || ownable.getOwner() == null) {
               break;
            }

            current = ownable.getOwner();
         }
      }

      return null;
   }

   private static int countStored(ServerPlayer player) {
      return getEntries(player).size();
   }

   private static int getMaxActiveShadows(ServerPlayer player) {
      return isLooterMastered(player) ? 8 : 5;
   }

   private static int getMaxStoredEntries(ServerPlayer player) {
      return isLooterMastered(player) ? 20 : 12;
   }

   private static boolean isLooterMastered(ServerPlayer player) {
      ManasSkillInstance instance = getLooterInstance(player);

      try {
         return instance != null && instance.isMastered(player);
      } catch (RuntimeException ignored) {
         return false;
      }
   }

   private static void addLooterMasteryPoint(ServerPlayer player) {
      ManasSkillInstance instance = getLooterInstance(player);
      if (instance != null) {
         try {
            instance.addMasteryPoint(player);
         } catch (RuntimeException var3) {
         }
      }
   }

   private static ManasSkillInstance getLooterInstance(ServerPlayer player) {
      try {
         return (ManasSkillInstance)SkillAPI.getSkillsFrom(player).getSkill(LOOTER_SKILL_ID).orElse(null);
      } catch (RuntimeException ignored) {
         return null;
      }
   }

   private static int countActiveShadows(ServerPlayer player) {
      ListTag active = readActiveShadowList(player);
      Set<UUID> ids = new HashSet<>();
      ListTag valid = new ListTag();
      boolean hasLoadedShadow = false;

      for (Tag tag : active) {
         if (tag instanceof CompoundTag compound) {
            UUID id = readUUID(compound, "id");
            if (id != null && ids.add(id)) {
               Entity loaded = findEntity(player.server, id);
               if (loaded == null || loaded instanceof LivingEntity living && living.isAlive() && isShadow(living)) {
                  if (loaded instanceof LivingEntity living && living.isAlive() && isShadow(living)) {
                     hasLoadedShadow = true;
                  }

                  valid.add(compound.copy());
               } else {
                  ids.remove(id);
               }
            }
         }
      }

      boolean freshSingleplayerWorld = player.server.isSingleplayer() && player.serverLevel().getGameTime() < 200L;
      if (freshSingleplayerWorld && !hasLoadedShadow && !active.isEmpty()) {
         valid = new ListTag();
         ids.clear();
      }

      boolean changed = valid.size() != active.size();

      for (ServerLevel level : player.server.getAllLevels()) {
         for (Entity entity : level.getAllEntities()) {
            if (entity instanceof LivingEntity living
               && living.isAlive()
               && player.getUUID().equals(getShadowOwnerUUID(living))
               && isShadow(living)
               && ids.add(living.getUUID())) {
               CompoundTag marker = new CompoundTag();
               marker.putUUID("id", living.getUUID());
               valid.add(marker);
               changed = true;
            }
         }
      }

      if (changed) {
         writeActiveShadowList(player, valid);
      }

      return ids.size();
   }

   private static void clearAllyTargets(MinecraftServer server) {
      for (ServerLevel level : server.getAllLevels()) {
         for (Entity entity : level.getAllEntities()) {
            if (entity instanceof Mob shadow && isShadow(shadow)) {
               LivingEntity target = shadow.getTarget();
               if (target != null) {
                  UUID owner = getShadowOwnerUUID(shadow);
                  boolean ownerTarget = owner != null && owner.equals(target.getUUID());

                  boolean allied;
                  try {
                     allied = SubordinateHelper.isAlly(shadow, target);
                  } catch (RuntimeException ignored) {
                     allied = false;
                  }

                  if (ownerTarget || allied) {
                     try {
                        SubordinateHelper.removeTarget(shadow);
                     } catch (RuntimeException ignored) {
                        shadow.setTarget(null);
                     }
                  }
               }
            }
         }
      }
   }

   private static Entity findEntity(MinecraftServer server, UUID id) {
      if (server != null && id != null) {
         for (ServerLevel level : server.getAllLevels()) {
            Entity entity = level.getEntity(id);
            if (entity != null) {
               return entity;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private static void addActiveShadow(ServerPlayer player, UUID id) {
      if (id != null) {
         ListTag active = readActiveShadowList(player);

         for (Tag tag : active) {
            if (tag instanceof CompoundTag compound && id.equals(readUUID(compound, "id"))) {
               return;
            }
         }

         CompoundTag marker = new CompoundTag();
         marker.putUUID("id", id);
         active.add(marker);
         writeActiveShadowList(player, active);
      }
   }

   private static void removeActiveShadow(MinecraftServer server, UUID ownerId, UUID entityId) {
      if (server != null && entityId != null) {
         if (ownerId != null) {
            ServerPlayer owner = server.getPlayerList().getPlayer(ownerId);
            if (owner != null) {
               removeActiveShadow(owner, entityId);
               clearPendingActiveRemoval(ownerId, entityId);
               return;
            }
         }

         for (ServerPlayer player : server.getPlayerList().getPlayers()) {
            if (containsActiveShadow(player, entityId)) {
               removeActiveShadow(player, entityId);
               if (ownerId != null) {
                  clearPendingActiveRemoval(ownerId, entityId);
               }

               return;
            }
         }

         if (ownerId != null) {
            PENDING_ACTIVE_REMOVALS.computeIfAbsent(ownerId, ignored -> new HashSet<>()).add(entityId);
         }
      }
   }

   private static void clearPendingActiveRemoval(UUID ownerId, UUID entityId) {
      Set<UUID> pending = PENDING_ACTIVE_REMOVALS.get(ownerId);
      if (pending != null) {
         pending.remove(entityId);
         if (pending.isEmpty()) {
            PENDING_ACTIVE_REMOVALS.remove(ownerId);
         }
      }
   }

   private static boolean containsActiveShadow(ServerPlayer player, UUID entityId) {
      for (Tag tag : readActiveShadowList(player)) {
         if (tag instanceof CompoundTag compound && entityId.equals(readUUID(compound, "id"))) {
            return true;
         }
      }

      return false;
   }

   private static void removeActiveShadow(ServerPlayer player, UUID entityId) {
      ListTag old = readActiveShadowList(player);
      ListTag remaining = new ListTag();

      for (Tag tag : old) {
         if (!(tag instanceof CompoundTag compound && entityId.equals(readUUID(compound, "id")))) {
            remaining.add(tag.copy());
         }
      }

      if (remaining.size() != old.size()) {
         writeActiveShadowList(player, remaining);
      }
   }

   private static List<CompoundTag> getEntries(ServerPlayer player) {
      List<CompoundTag> result = new ArrayList<>();
      Set<String> seen = new HashSet<>();
      boolean changed = false;

      for (Tag tag : readEntryList(player)) {
         if (tag instanceof CompoundTag compound && readUUID(compound, "id") != null && !compound.getString("type").isBlank()) {
            CompoundTag copy = compound.copy();
            if (!seen.add(entryDedupKey(copy))) {
               changed = true;
            } else {
               result.add(copy);
            }
         } else {
            changed = true;
         }
      }

      if (changed) {
         ListTag normalized = new ListTag();

         for (CompoundTag entry : result) {
            normalized.add(entry.copy());
         }

         writeEntryList(player, normalized);
      }

      return result;
   }

   private static String entryDedupKey(CompoundTag entry) {
      UUID sourceUuid = readUUID(entry, "source_uuid");
      if (sourceUuid != null) {
         return "source:" + sourceUuid;
      }

      CompoundTag legacy = entry.copy();
      legacy.remove("id");
      return "legacy:" + legacy;
   }

   private static CompoundTag findEntry(ServerPlayer player, UUID id) {
      if (id == null) {
         return null;
      }

      for (CompoundTag entry : getEntries(player)) {
         if (id.equals(readUUID(entry, "id"))) {
            return entry.copy();
         }
      }

      return null;
   }

   private static void removeEntry(ServerPlayer player, UUID id) {
      List<CompoundTag> old = getEntries(player);
      ListTag remaining = new ListTag();

      for (CompoundTag entry : old) {
         if (id != null && id.equals(readUUID(entry, "id"))) {
            releaseCaptureLock(entry);
         } else {
            remaining.add(entry.copy());
         }
      }

      writeEntryList(player, remaining);
   }

   private static ListTag readEntryList(ServerPlayer player) {
      CompoundTag root = player.getPersistentData().getCompound("tno_tensura_extras_looter");
      return root.getList("entries", 10);
   }

   private static ListTag readActiveShadowList(ServerPlayer player) {
      CompoundTag root = player.getPersistentData().getCompound("tno_tensura_extras_looter");
      return root.getList("active_shadows", 10);
   }

   private static void writeEntryList(ServerPlayer player, ListTag entries) {
      CompoundTag root = player.getPersistentData().getCompound("tno_tensura_extras_looter");
      root.put("entries", entries);
      player.getPersistentData().put("tno_tensura_extras_looter", root);
   }

   private static void writeActiveShadowList(ServerPlayer player, ListTag active) {
      CompoundTag root = player.getPersistentData().getCompound("tno_tensura_extras_looter");
      root.put("active_shadows", active);
      player.getPersistentData().put("tno_tensura_extras_looter", root);
   }

   private static UUID readUUID(CompoundTag tag, String key) {
      return tag.hasUUID(key) ? tag.getUUID(key) : null;
   }

   private static void sanitizeSnapshot(CompoundTag snapshot) {
      String[] keys = new String[]{
         "UUID",
         "Pos",
         "Motion",
         "Rotation",
         "Passengers",
         "Leash",
         "Vehicle",
         "Dimension",
         "PortalCooldown",
         "Owner",
         "Target",
         "AngerTime",
         "AngryAt",
         "HurtBy",
         "HurtByTimestamp",
         "HurtTime",
         "DeathTime",
         "LoveCause",
         "Brain"
      };

      for (String key : keys) {
         snapshot.remove(key);
      }
   }

   private static void removeLooterSkill(CompoundTag skills) {
      ListTag list = skills.getList("skills", 10);
      ListTag filtered = new ListTag();

      for (Tag tag : list) {
         if (!(tag instanceof CompoundTag skill && "tno_tensura_extras:looter".equals(skill.getString("skill")))) {
            filtered.add(tag.copy());
         }
      }

      skills.put("skills", filtered);
   }

   private static final class AnimationState {
      private final UUID entityId;
      private final UUID ownerId;
      private final double x;
      private final double targetY;
      private final double z;
      private final double startY;
      private int tick;

      private AnimationState(UUID entityId, UUID ownerId, double x, double targetY, double z, double startY, int tick) {
         this.entityId = entityId;
         this.ownerId = ownerId;
         this.x = x;
         this.targetY = targetY;
         this.z = z;
         this.startY = startY;
         this.tick = tick;
      }
   }

   private static final class AttributeSetter {
      private static void set(Mob entity, Holder<Attribute> attribute, double value) {
         if (Double.isFinite(value) && !(value < 0.0)) {
            AttributeInstance instance = entity.getAttribute(attribute);
            if (instance != null) {
               instance.setBaseValue(value);
            }
         }
      }
   }
}
